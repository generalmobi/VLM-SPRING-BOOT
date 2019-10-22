package com.vlm.ui.config.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vlm.ui.chaincode.HlfClient;
import com.vlm.ui.dto.RepairEvent;
import com.vlm.ui.dto.Response;
import com.vlm.ui.dto.SalesEvent;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api/v1")
public class DealerApiController {


	@Autowired
	private HlfClient hlfClient;
	

	@Autowired
	private ObjectMapper objectMapper;
	
 	@ApiOperation(value = "Capture sales event")
	@RequestMapping(value = "/sales", method = RequestMethod.POST)
	public Response<Boolean> captureSales( @RequestBody SalesEvent salesEvent) throws Exception
	{
 		 String payload = objectMapper.writeValueAsString(salesEvent);
  		hlfClient.invokeBlockChain("salesEvent", payload);
 		 return Response.instance(Response.SUCCESS, "");
  	}
 	
 	@ApiOperation(value = "Capture repair event")
	@RequestMapping(value = "/repair", method = RequestMethod.POST)
	public Response<Boolean> captureRepair( @RequestBody RepairEvent repairEvent) throws Exception
	{

		 String payload = objectMapper.writeValueAsString(repairEvent);
 		hlfClient.invokeBlockChain("repairEvent", payload);
		 return Response.instance(Response.SUCCESS, "");
  	}
	
}
