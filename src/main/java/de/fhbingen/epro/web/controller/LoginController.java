package de.fhbingen.epro.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(value = { "" })
public class LoginController {

	@RequestMapping(value = { "login" }, method = RequestMethod.GET)
	public String getLoginPage(@RequestParam(required = false) String error, @RequestParam(required = false) String logout) {
		return "Login";
	}
	
	@RequestMapping(value = { "login" }, method = RequestMethod.POST)
	public String login(@RequestBody String formBody) {
		return null;
	}
}
