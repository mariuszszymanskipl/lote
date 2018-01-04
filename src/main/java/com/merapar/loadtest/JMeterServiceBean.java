package com.merapar.loadtest;

import com.merapar.loadtest.utils.AsyncResponse;
import com.merapar.loadtest.web.controller.State;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Future;

@Service
public class JMeterServiceBean implements JMeterService{

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public static final List<SseEmitter> sseEmitter = new LinkedList<>();

    private State state = new State("state 0");

    private enum LongRequestStatus {
        INITIALIZING, PHASE1, DONE
    }
    private DeferredResult<String> waitingResponse = null;
    private LongRequestStatus currentStatus = LongRequestStatus.INITIALIZING;

    private synchronized void publishStatus(LongRequestStatus nextToReturn) {
        if (waitingResponse != null) {
            waitingResponse.setResult(nextToReturn.toString());
        }
        waitingResponse = null;
    }

    @Override
    public synchronized DeferredResult<String> getStatus() {
        waitingResponse = new DeferredResult<>();

        // If the request completed, immediately return the status because it will never change
        if (currentStatus == LongRequestStatus.DONE) {
            waitingResponse.setResult(currentStatus.toString());
        }
        return waitingResponse;
    }



    @Async
    @Override
    public boolean execute(Test test) {

        long startTime = System.currentTimeMillis();
        String newState = "Starting jmeter test load_test_2.jmx";
        state = new State(newState);
        sendUpdate(state);
        logger.info(newState);

        Boolean success;

        File testFile = null;
        try {
            testFile = new ClassPathResource("load_test_2.jmx").getFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String loadTestJmx = testFile != null ? testFile.getPath() : null;

        String loadTestCommand = "jmeter -n -t " + loadTestJmx + " -l load-test-results.csv";

        Process process;
        try {

//            currentStatus = LongRequestStatus.PHASE1;
//            publishStatus(currentStatus);

            process = Runtime.getRuntime().exec(loadTestCommand, null);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(process.getInputStream()));
            String line;
            try {
                while ((line = in.readLine()) != null) {
                    newState = state.getText() + "<p>" + line + "</p>";
                    state = new State(newState);
                    sendUpdate(state);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        long endTime = System.currentTimeMillis();
        long durationTime = endTime - startTime;

        logger.info("Processing time was {} seconds.", durationTime / 1000);
        newState = state.getText() + "<p>" +" Processing time was " + durationTime / 1000 + " seconds." + "</p>";
        state = new State(newState);
        sendUpdate(state);

        success = Boolean.TRUE;

        logger.info("< executed");



        return success;
    }

    @Async
    @Override
    public void executeAsync(Test test) {
        currentStatus = LongRequestStatus.INITIALIZING;
        publishStatus(currentStatus);

        logger.info("> executeAsync");
        try {
            currentStatus = LongRequestStatus.PHASE1;
            publishStatus(currentStatus);
            execute(test);
        } catch (Exception e) {
            logger.warn("Exception caught executing asynchronous test.", e);
        }
        logger.info("< executedAsync");

        currentStatus = LongRequestStatus.DONE;
        publishStatus(currentStatus);
    }

    @Async
    @Override
    public Future<Boolean> executeAsyncWithResult(Test test) {
        logger.info("> executeAsyncWithResult");

        AsyncResponse<Boolean> response = new AsyncResponse<>();

        try {
            Boolean success = execute(test);
            response.complete(success);
        } catch (Exception e) {
            logger.warn("Exception caught executing asynchronous test", e);
            response.completeExceptionally(e);
        }

        logger.info("< executedAsyncWithResult");
        return response;
    }

    private void sendUpdate(State state) {
        synchronized (sseEmitter) {
            sseEmitter.forEach((SseEmitter emitter) -> {
                try {
                    emitter.send(state, MediaType.APPLICATION_JSON);
                } catch (IOException e) {
                    emitter.complete();
                    sseEmitter.remove(emitter);
                }
            });
        }
    }
}
