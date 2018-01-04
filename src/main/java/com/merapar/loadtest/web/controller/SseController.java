package com.merapar.loadtest.web.controller;

import com.merapar.loadtest.JMeterCommand;
import com.merapar.loadtest.JMeterService;
import com.merapar.loadtest.JMeterServiceBean;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Controller
public class SseController {

    private static final Logger log = Logger.getLogger(SseController.class);

//    private final List<SseEmitter> sseEmitter = new LinkedList<>();
    private final List<SseEmitter> sseEmitter = JMeterServiceBean.sseEmitter;

    private State state = new State("state 0");



//    @Async
//    private void executeJMeterCommand() {
//    private final Runnable executeJMeterCommand = () -> {
//
//        File testFile = null;
//        try {
//            testFile = new ClassPathResource("load_test_2.jmx").getFile();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        String loadTestJmx = testFile.getPath();
//
//        log.info("Starting jmeter test load_test_2.jmx");
//        String loadTestCommand = "jmeter -n -t " + loadTestJmx + " -l load-test-results.jtl";
//        Process process;
//        try {
//            process = Runtime.getRuntime().exec(loadTestCommand, null);
//            BufferedReader in = new BufferedReader(
//                    new InputStreamReader(process.getInputStream()));
//            String line = null;
//            try {
//                while ((line = in.readLine()) != null) {
//                    String newState = state.getText() + "\n" + line;
//                    state = new State(newState);
//                    sendUpdate(state);
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//
//    };

//    private void sendUpdate(State state) {
//        synchronized (sseEmitter) {
//            sseEmitter.forEach((SseEmitter emitter) -> {
//                try {
//                    emitter.send(state, MediaType.APPLICATION_JSON);
//                } catch (IOException e) {
//                    emitter.complete();
//                    sseEmitter.remove(emitter);
//                }
//            });
//        }
//    }

//    private final Runnable changeState = () -> {
//        log.info("Sending auto message " + counter);
//        state = new State("state " + counter++);
//
//        synchronized (sseEmitter) {
//            sseEmitter.forEach((SseEmitter emitter) -> {
//                try {
//                    emitter.send(state, MediaType.APPLICATION_JSON);
//                } catch (IOException e) {
//                    emitter.complete();
//                    sseEmitter.remove(emitter);
//                }
//            });
//        }
//    };

//    /**
//     * Initialize the controller and start a repeated task to model state changes.
//     */
//    public SseController() {
//        ScheduledExecutorService scheduledPool = Executors.newScheduledThreadPool(4);
//        scheduledPool.scheduleWithFixedDelay(changeState, 10, 5, TimeUnit.SECONDS);


//        ScheduledExecutorService scheduledPool = Executors.newScheduledThreadPool(4);
//        scheduledPool.scheduleWithFixedDelay(executeJMeterCommand, 5, 30, TimeUnit.SECONDS);
//    }

    /**
     * Some website.
     *
     * @param model model in spring mvc
     * @return a .jsp ressource
     */
    @RequestMapping(path = "/demo", method = RequestMethod.GET)
    public String sseExamplePage(Map<String, Object> model) {
        model.put("state", state);
//        executeJMeterCommand();
//        executeJMeterCommand.run();
        return "server-update";
    }


//    @RequestMapping(name = "/demo", method = RequestMethod.GET)
//    @ResponseBody
//    public DeferredResult<String> startRequest(Map<String, Object> model) {
//        model.put("state", state);
//        // Prepare already for the first state change
//        DeferredResult<String> result = new DeferredResult<>();
//        result.setResult("server-update");
//
//        // Actually let the asynchronous service do something
//        executeJMeterCommand();
//
//        // Return the deferred result that will be set in the above asynchronous call
//        return result;
//    }



    /**
     * Viewer can register here to get sse messages.
     *
     * @return an server state event emitter
     * @throws IOException if registering the new emitter fails
     */
    @RequestMapping(path = "/register", method = RequestMethod.GET)
    public SseEmitter register() throws IOException {
        log.info("Registering a stream.");

        SseEmitter emitter = new SseEmitter(600000L);

        synchronized (sseEmitter) {
            sseEmitter.add(emitter);
        }
        emitter.onCompletion(() -> sseEmitter.remove(emitter));
        emitter.onTimeout(() -> log.info("Timeout"));

        return emitter;
    }


}
