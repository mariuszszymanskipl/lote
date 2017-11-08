package com.merapar.loadtest.web.controller;

import com.merapar.loadtest.jMeterCommand;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;

@Controller
public class LoadTestController {

    @RequestMapping("/start")
    public String startLoadTest() throws IOException, InterruptedException {
        jMeterCommand.run();
        return "results";
    }
}
