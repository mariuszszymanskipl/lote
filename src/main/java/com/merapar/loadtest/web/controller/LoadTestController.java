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

//    @RequestMapping(value = "/demotest/{id}/execute", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<Test> executeTest(@PathVariable("id") Long id,
//                                            @RequestParam(value = "wait", defaultValue = "false") boolean waitForAsyncResult) {

    @RequestMapping(value = "/demotest/{id}/execute", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
//    @ResponseBody
    public String executeTest(@PathVariable("id") Long id,
                              @RequestParam(value = "wait", defaultValue = "false") boolean waitForAsyncResult,
                              @ModelAttribute("param") JMeterParameters parameters,
                              @ModelAttribute("image") String image) {

        logger.info("> executeTest id:{}", id);
        logger.info("Number of users: {}", parameters.getNumberOfUsers());
        logger.info("Duration: {}", parameters.getDuration());
        logger.info("Image: {}", parameters.getTest());
//        logger.info("Image: {}", image);

        String loadTestJmx = "load_test_" + id + ".jmx";
        Test test = new Test(loadTestJmx);

        try {
            if (waitForAsyncResult) {
                Future<Boolean> asyncResponse = jMeterService.executeAsyncWithResult(test);
                boolean testExecute = asyncResponse.get();
                logger.info("- test executed? {}", testExecute);
            } else {
                jMeterService.executeAsync(test);
            }
        } catch (Exception e) {
            logger.error("A problem occurred executing test.", e);
            return "error";
        }

        logger.info("< execute Test id:{}", id);
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
