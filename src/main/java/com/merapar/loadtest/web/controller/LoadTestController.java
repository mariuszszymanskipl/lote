package com.merapar.loadtest.web.controller;

import com.merapar.loadtest.JMeterCommand;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

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
}
