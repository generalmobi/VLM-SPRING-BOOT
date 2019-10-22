package com.vlm.ui.config.vm;

import java.util.List;
import java.util.Optional;

import org.apache.commons.lang.StringUtils;
import org.hyperledger.fabric.sdk.exception.InvalidArgumentException;
import org.hyperledger.fabric.sdk.exception.ProposalException;
import org.springframework.beans.factory.annotation.Autowired;
import org.zkoss.bind.annotation.Command;
import org.zkoss.spring.DelegatingVariableResolver;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Messagebox;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vlm.ui.chaincode.HlfClient;
import com.vlm.ui.dto.RegistrationEvent;
import com.vlm.ui.dto.RepairEvent;
import com.vlm.ui.support.PropertyVmSupport;

@VariableResolver(DelegatingVariableResolver.class)

public class RepairVm extends PropertyVmSupport{

	@Autowired
	private HlfClient hlfClient;
	
 
	@Autowired
	private ObjectMapper objectMapper;

	
	private RepairEvent event=new RepairEvent();

	public RepairEvent getEvent() {
		return event;
	}

	public void setEvent(RepairEvent event) {
		this.event = event;
	}
	
	Optional<List<RegistrationEvent>> parseRegistrationEvent(String payload)
	{
		try {
			return Optional.of(objectMapper.readValue(payload, objectMapper.getTypeFactory().constructParametricType(List.class, RegistrationEvent.class)));
		} catch (Exception e) {
			// TODO: handle exception
		}
		return Optional.ofNullable(null);
	}
	
	
	@Command("repair")
	public void register() throws JsonProcessingException, ProposalException, InvalidArgumentException
	{
		
		if (StringUtils.isBlank(event.getChasisNumber()))
		{
			Clients.showNotification("Please provide chasis number",	Clients.NOTIFICATION_TYPE_WARNING,null	, "top_center", 3100);
			return;
		}
		 
		
		 String payload = objectMapper.writeValueAsString(event);
			hlfClient.invokeBlockChain("repairEvent", payload);
			
			Messagebox.show("Proposal accepted", "Info", Messagebox.OK, Messagebox.INFORMATION, new EventListener<Event>()
			{
	
				@Override
				public void onEvent(Event event) throws Exception {
					Executions.sendRedirect("dashboard.html");
				}
				
			});
	}
	
	
	


}
