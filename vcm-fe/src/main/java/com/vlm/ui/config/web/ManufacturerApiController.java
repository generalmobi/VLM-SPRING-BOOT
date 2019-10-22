package com.vlm.ui.config.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vlm.ui.chaincode.HlfClient;
import com.vlm.ui.dto.CreationEvent;
import com.vlm.ui.dto.Response;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api/v1")
public class ManufacturerApiController {


	@Autowired
	private HlfClient hlfClient;
	

	@Autowired
	private ObjectMapper objectMapper;
	
 	@ApiOperation(value = "Capture creation event")
	@RequestMapping(value = "/manufacturer", method = RequestMethod.POST)
	public Response<Boolean> create( @RequestBody CreationEvent createCreationEvent) throws Exception
	{
 		 String payload = objectMapper.writeValueAsString(createCreationEvent);
 		hlfClient.invokeBlockChain("creationEvent", payload);
		 return Response.instance(Response.SUCCESS, "");
  	}
	
	
}
