package com.merapar.loadtest.web.controller;

import com.merapar.loadtest.JMeterCommand;
import com.merapar.loadtest.JMeterParameters;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.IOException;

@Controller
public class LoadTestController {

    @RequestMapping("/start1")
    public String startLoadTest1() throws IOException, InterruptedException {
        JMeterCommand.run1();
        return "results";
    }

    @RequestMapping("/start2")
    public String startLoadTest2() throws IOException, InterruptedException {
        JMeterCommand.run2();
        return "results";
    }

    @RequestMapping(value = "/start3", method = RequestMethod.POST)
    public String saveOrUpdateUser(@ModelAttribute("param") JMeterParameters parameters) throws IOException, InterruptedException {
        JMeterCommand.run3(parameters);
        return "results";
    }
}
