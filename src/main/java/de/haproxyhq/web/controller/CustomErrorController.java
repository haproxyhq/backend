package de.haproxyhq.web.controller;

import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = { "" })
public class CustomErrorController implements ErrorController {

	@RequestMapping(value = { "error" }, method = RequestMethod.GET)
	public String getErrorPage() {
		return "Error";
	}

	@Override
	public String getErrorPath() {
		return "/error";
	}
}
