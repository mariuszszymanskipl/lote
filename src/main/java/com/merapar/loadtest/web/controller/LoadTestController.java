package com.merapar.loadtest.web.controller;

import com.merapar.loadtest.jmeter.JMeterCommand;
import com.merapar.loadtest.jmeter.JMeterParameters;
import com.merapar.loadtest.jmeter.JMeterService;
import com.merapar.loadtest.jmeter.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;

import java.io.IOException;
import java.util.concurrent.Future;

@Controller
public class LoadTestController {

    @Autowired
    private JMeterService jMeterService;


    private Logger logger = LoggerFactory.getLogger(LoadTestController.class);

    @RequestMapping("/start1")
    public String startLoadTest1() throws IOException, InterruptedException {
        String loadTestJmx = "load_test_1.jmx";
        JMeterCommand.runLoadTest(loadTestJmx);
        return "results";
    }

    @RequestMapping("/start2")
    public String startLoadTest2() throws IOException, InterruptedException {
        String loadTestJmx = "load_test_2.jmx";
        JMeterCommand.runLoadTest(loadTestJmx);
        return "results";
    }

    @RequestMapping(value = "/start3", method = RequestMethod.POST)
    public String startLoadTest3(@ModelAttribute("param") JMeterParameters parameters) throws IOException, InterruptedException {
        logger.info("Number of users: {}", parameters.getNumberOfUsers());
        logger.info("Duration: {}", parameters.getDuration());
        JMeterCommand.runLoadTest3(parameters);
        return "results";
    }

    @RequestMapping(value = "/demotest/execute", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public String executeTest(@ModelAttribute("param") JMeterParameters parameters) {

        logger.info("Number of users: {}", parameters.getNumberOfUsers());
        logger.info("Duration: {}", parameters.getDuration());
        logger.info("Image: {}", parameters.getImageName());
        logger.info("JMeter test: {}", parameters.getjMeterTest());

        Test test = new Test(parameters.getjMeterTest());

        try {
            jMeterService.executeAsync(test);
        } catch (Exception e) {
            logger.error("A problem occurred executing test.", e);
            return "error";
        }
        return "redirect:/demo";
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
