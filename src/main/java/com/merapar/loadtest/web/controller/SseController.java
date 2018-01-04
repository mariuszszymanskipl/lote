package com.merapar.loadtest.web.controller;

import com.merapar.loadtest.JMeterServiceBean;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.List;
import java.util.Map;


@Controller
public class SseController {

    private static final Logger log = Logger.getLogger(SseController.class);

    private final List<SseEmitter> sseEmitter = JMeterServiceBean.sseEmitter;

    private State state = new State("state 0");

    @RequestMapping(path = "/demo", method = RequestMethod.GET)
    public String sseExamplePage(Map<String, Object> model) {
        model.put("state", state);
        return "server-update";
    }

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
