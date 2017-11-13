package com.merapar.loadtest.web.controller;

import com.merapar.loadtest.JMeterCommand;
import com.merapar.loadtest.JMeterParameters;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.IOException;

@Controller
public class LoadTestController {

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
        JMeterCommand.runLoadTest3(parameters);
        return "results";
    }
}
