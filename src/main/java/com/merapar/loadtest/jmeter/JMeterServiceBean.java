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
    public boolean execute(JMeterParameters jMeterParameters) {

        long startTime = System.currentTimeMillis();

        generateNewState("Load jMeterTest file: " + jMeterParameters.getjMeterTest());
        generateNewState("Removing old HTTP report");

        removeOldHTMLReport();

        File testsDir = new File(resourcesConfiguration.getJMeterTestsFolder());
        File testFile = new File(testsDir.getAbsolutePath() + File.separator + jMeterParameters.getjMeterTest());

        String loadTestJmx = testFile.getPath();

        String[] cmdArray = new String[9];

        cmdArray[0] = "jmeter";
        cmdArray[1] = "-n";
        cmdArray[2] = "-t";
        cmdArray[3] = loadTestJmx;
        cmdArray[4] = "-Jusers=" + jMeterParameters.getNumberOfUsers();
        cmdArray[5] = "-Jduration=" + jMeterParameters.getDuration();
        cmdArray[6] = "-Jimage=" + jMeterParameters.getImageName();
        cmdArray[7] = "-l";
        cmdArray[8] = "load-jMeterTest-results.csv";

        Process process;
        try {

//            currentStatus = LongRequestStatus.PHASE1;
//            publishStatus(currentStatus);

            process = Runtime.getRuntime().exec(cmdArray, null);
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
        generateNewState("End of the jMeterTest");

        return Boolean.TRUE;
    }



    @Async
    @Override
    public void executeAsync(JMeterParameters jMeterParameters) {
        currentStatus = LongRequestStatus.INITIALIZING;
        publishStatus(currentStatus);

        logger.info("> executeAsync");
        try {
            currentStatus = LongRequestStatus.PHASE1;
            publishStatus(currentStatus);
            execute(jMeterParameters);
        } catch (Exception e) {
            logger.warn("Exception caught executing asynchronous jMeterTest.", e);
        }
        logger.info("< executedAsync");

        currentStatus = LongRequestStatus.DONE;
        publishStatus(currentStatus);
    }

    @Override
    public Future<Boolean> executeAsyncWithResult(JMeterParameters parameters) {
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
