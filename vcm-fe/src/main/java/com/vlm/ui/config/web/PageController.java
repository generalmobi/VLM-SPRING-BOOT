package com.vlm.ui.config.web;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.hyperledger.fabric.sdk.exception.InvalidArgumentException;
import org.hyperledger.fabric.sdk.exception.ProposalException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class PageController {

	 
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView home(HttpServletRequest request) throws IOException
	{
 		return new ModelAndView("dashboard");
	}
	
	
	@RequestMapping(value = "/sales.html", method = RequestMethod.GET)
	public ModelAndView registration(HttpServletRequest request) throws IOException
	{
 		return new ModelAndView("registration");
	}
	
	

	
	
	@RequestMapping(value = "/repair.html", method = RequestMethod.GET)
	public ModelAndView repair(HttpServletRequest request) throws IOException
	{
 		return new ModelAndView("repair");
	}
	
	
	
	@RequestMapping(value = "/dashboard.html", method = RequestMethod.GET)
	public ModelAndView dashboard(HttpServletRequest request) throws IOException
	{
 		return new ModelAndView("dashboard");
	}
	
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public ModelAndView index(HttpServletRequest request) throws IOException
	{
 		return new ModelAndView("dashboard");
	}

	/*
	 * public Optional<CreateEvent> parseCreateEvent(String payload) { try {
	 * //return mapper.readValue(payload, CreateEvent.class); } catch (Exception e)
	 * { // TODO: handle exception } }
	 */
	
	@RequestMapping(value = "/result.html", method = RequestMethod.GET)
	public ModelAndView vehicles(HttpServletRequest request,@RequestParam("vin") String vin) throws IOException, ProposalException, InvalidArgumentException
	{
 	  	ModelAndView view =new ModelAndView("result");
	   	 
 	  	return view;
	  	
	}
}

