package com.merapar.loadtest.web.controller;

import com.merapar.loadtest.jmeter.JMeterCommand;
import com.merapar.loadtest.jmeter.JMeterParameters;
import com.merapar.loadtest.jmeter.JMeterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;

import java.io.IOException;

@Controller
public class LoadTestController {

    @Autowired
    private JMeterService jMeterService;

    private Logger logger = LoggerFactory.getLogger(LoadTestController.class);

    @RequestMapping(value = "/demotest/execute", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public String executeTest(@ModelAttribute("param") JMeterParameters parameters) {

        logger.info("Number of users: {}", parameters.getNumberOfUsers());
        logger.info("Duration: {}", parameters.getDuration());
        logger.info("Image: {}", parameters.getImageName());
        logger.info("JMeter jMeterTest: {}", parameters.getjMeterTest());

        try {
            jMeterService.executeAsync(parameters);
        } catch (Exception e) {
            logger.error("A problem occurred executing jMeterTest.", e);
            return "error";
        }
        return "redirect:/JMeterTest/123";
    }


    @RequestMapping(value = "/start", method = RequestMethod.POST)
    @ResponseBody
    public DeferredResult<String> startRequest() {

        return jMeterService.getStatus();
    }

    @RequestMapping(value = "/status", method = RequestMethod.GET)
    @ResponseBody
    public DeferredResult<String> requestStatus() {
        return jMeterService.getStatus();
    }

}
