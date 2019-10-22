package com.vlm.ui.config.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vlm.ui.chaincode.HlfClient;
import com.vlm.ui.dto.Response;
import com.vlm.ui.dto.ScrapEvent;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api/v1")

public class ScrapApiController {

	@Autowired
	private HlfClient hlfClient;
	

	@Autowired
	private ObjectMapper objectMapper;
	
	
	
	@ApiOperation(value = "Capture scrap event")
	@RequestMapping(value = "/scrap", method = RequestMethod.POST)
	public Response<Boolean> create( @RequestBody ScrapEvent scrapEvent) throws Exception
	{
		
		 String payload = objectMapper.writeValueAsString(scrapEvent);
			hlfClient.invokeBlockChain("scrapEvent", payload);
	 		 return Response.instance(Response.SUCCESS, "");
   	}
	
}
