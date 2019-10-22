package com.vlm.ui.config.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vlm.ui.chaincode.HlfClient;
import com.vlm.ui.dto.RegistrationEvent;
import com.vlm.ui.dto.Response;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api/v1")
public class RegistrationApiController {

	@Autowired
	private HlfClient hlfClient;
	

	@Autowired
	private ObjectMapper objectMapper;
	
	
	@ApiOperation(value = "Capture registration event")
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public Response<Boolean> create( @RequestBody RegistrationEvent registrationEvent) throws Exception
	{

		 String payload = objectMapper.writeValueAsString(registrationEvent);
		hlfClient.invokeBlockChain("registrationEvent", payload);
 		 return Response.instance(Response.SUCCESS, "");
  	}
	
	
}
