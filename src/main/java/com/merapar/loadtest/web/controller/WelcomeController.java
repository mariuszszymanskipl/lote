package com.merapar.loadtest.web.controller;

import com.merapar.loadtest.JMeterParameters;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class WelcomeController {

	@RequestMapping("/")
	public String welcome(Model model) {

		JMeterParameters param = new JMeterParameters();
		model.addAttribute("param", param );
		return "welcome";
	}

}