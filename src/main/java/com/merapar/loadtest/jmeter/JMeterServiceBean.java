package com.merapar.loadtest.jmeter;

import com.merapar.loadtest.configuration.ResourcesConfiguration;
import com.merapar.loadtest.web.controller.State;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
public class JMeterServiceBean implements JMeterService {

    @Autowired
    private ResourcesConfiguration resourcesConfiguration;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public static final List<SseEmitter> sseEmitter = new LinkedList<>();

    private State state = new State("Starting JMeter load test...");

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

        generateNewState("Load test file: " + test.getName());
        generateNewState("Removing old HTTP report");

        removeOldHTMLReport();

        File testsDir = new File(resourcesConfiguration.getjMeterTestsFolder());
        File testFile = new File(testsDir.getAbsolutePath() + File.separator + test.getName());

        String loadTestJmx = testFile.getPath();

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
                    generateNewState(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        long endTime = System.currentTimeMillis();
        long durationTime = endTime - startTime;

        generateNewState(" Processing time was " + durationTime / 1000 + " seconds");
        generateNewState("End of the test");

        return Boolean.TRUE;
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

    @Override
    public Future<Boolean> executeAsyncWithResult(Test test) {
        return null;
    }

    private void generateNewState(String message) {
        state = new State(state.getText() + "<p>" + message + "</p>");
        sendUpdate(state);
        logger.info(message);
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

    private static void removeOldHTMLReport() {

        System.out.println("Removing old HTTP report");

        try {
            String[] cmdArray = new String[3];

            cmdArray[0] = "rm";
            cmdArray[1] = "-r";
            cmdArray[2] = "results";

            Process process = Runtime.getRuntime().exec(cmdArray, null);

            systemPrintLnOut(process);

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private static void systemPrintLnOut(Process process) throws IOException {
        BufferedReader in = new BufferedReader(
                new InputStreamReader(process.getInputStream()));
        String line = null;
        while ((line = in.readLine()) != null) {
            System.out.println(line);
        }
    }
}
