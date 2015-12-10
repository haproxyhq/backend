package de.fhbingen.epro.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import de.fhbingen.epro.sql.User;

@Controller
@RequestMapping(value = {""})
public class UserController {

	@RequestMapping(value = {"register"}, method = RequestMethod.GET)
	public ModelAndView registration() {
		ModelAndView model = new ModelAndView("Registration");
		model.addObject("user", new User());
		return model;
	}
}
