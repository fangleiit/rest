package com.biz.bizunited.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value="rules")
public class RulesController extends BaseController{

	/**
	 * 规则说明
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="ruleDescription",method = RequestMethod.GET)
	public ModelAndView root(HttpServletRequest request) {
		logger.info("rules");
		ModelAndView mv = new ModelAndView("rules/ruleDescription");
		return mv;
	}
	
}
