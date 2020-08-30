package com.joniks.loyalty.api.controller.view;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.joniks.loyalty.api.entity.User;

@Controller(value = "indexController")
@RequestMapping(value = "/")
public class IndexController {

	@RequestMapping(method = RequestMethod.GET)
	public String getCustomerList(ModelMap map, User searchFilter) {
		return "index";
	}

	@RequestMapping(method = RequestMethod.GET, value = "invalidsession")
	@ResponseBody
	public String invalidSession() {
		return "invalidsession";
	}
}
