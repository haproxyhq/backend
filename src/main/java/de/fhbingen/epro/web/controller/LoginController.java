package de.fhbingen.epro.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = { "" })
public class LoginController {

	@RequestMapping(value = { "login" }, method = RequestMethod.GET)
	public String getLoginPage() {
		return "Login";
	}

}
