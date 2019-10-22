package com.vlm.ui.config.vm;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.hyperledger.fabric.sdk.exception.InvalidArgumentException;
import org.hyperledger.fabric.sdk.exception.ProposalException;
import org.springframework.beans.factory.annotation.Autowired;
import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.spring.DelegatingVariableResolver;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.ListModel;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vlm.ui.chaincode.HlfClient;
import com.vlm.ui.dto.CreationEvent;
import com.vlm.ui.dto.RegistrationEvent;
import com.vlm.ui.dto.RepairEvent;
import com.vlm.ui.dto.SalesEvent;
import com.vlm.ui.dto.ScrapEvent;
import com.vlm.ui.support.PropertyVmSupport;
import com.vlm.ui.support.entities.Manufacturer;
import com.vlm.ui.support.entities.Vehicle;

@VariableResolver(DelegatingVariableResolver.class)

public class VehiclesVM extends PropertyVmSupport {

	@Autowired
	private HlfClient hlfClient;
	
 
	@Autowired
	private ObjectMapper objectMapper;

	private CreationEvent carInfo;
	

	private  List<RepairEvent> repairs ;

	private List<RegistrationEvent> registrations;

	private SalesEvent salesInfo;
	
	
	private ScrapEvent scrapInfo;
	
	@Command("goToSearch")
	public void goToSearch()
	{
		Executions.sendRedirect("dashboard.html");
	}
	
	@Command("goToRepair")
	public void goToRepair()
	{
		Executions.sendRedirect("repair.html");
	}
	
	@Command("goToSales")
	public void goToSales()
	{
		Executions.sendRedirect("sales.html");
	}
	
	@Command("goToApi")
	public void goToApi()
	{
		Executions.getCurrent().sendRedirect("swagger-ui.html", "_blank");
	}
	
	
	
	
	@Init
	public void init() throws ProposalException, InvalidArgumentException
	{
		String id = Executions.getCurrent().getParameter("vin");
		String payload=hlfClient.queryBlockChain("getCreation", id);
		Optional<CreationEvent> event=parseCreationEvent(payload);
		if (event.isPresent())
		{
			this.carInfo=event.get();
			BindUtils.postNotifyChange(null, null,this, "carInfo");

			Optional<SalesEvent> salesEvent =parseSalesEvent(hlfClient.queryBlockChain("getSales", id));
			if (salesEvent.isPresent())
			{
				this.salesInfo=salesEvent.get();
				BindUtils.postNotifyChange(null, null,this, "salesInfo");

			}

			Optional<List<RegistrationEvent>> optionalRegistration =parseRegistrationEvent(hlfClient.queryBlockChain("getRegistration", id));
			if (optionalRegistration.isPresent())
			{
				this.registrations=optionalRegistration.get();
				BindUtils.postNotifyChange(null, null,this, "registrations");

			}

			Optional<List<RepairEvent>> optionalRepairs =parseRepairEvent(hlfClient.queryBlockChain("getRepair", id));
			if (optionalRepairs.isPresent())
			{
				this.repairs=optionalRepairs.get();
				BindUtils.postNotifyChange(null, null,this, "repairs");
 			}
			
			Optional<ScrapEvent> scrapEvent =parseScrapEventEvent(hlfClient.queryBlockChain("getScrap", id));
			if (scrapEvent.isPresent())
			{
				this.scrapInfo=scrapEvent.get();
				BindUtils.postNotifyChange(null, null,this, "scrapInfo");

			}
			
			
		}
		else
		{
	 		Messagebox.show("Car not found", "Info", Messagebox.OK, Messagebox.INFORMATION, new EventListener<Event>()
			{
	
				@Override
				public void onEvent(Event event) throws Exception {
					Executions.sendRedirect("dashboard.html");
				}
				
			});
		}
	}
	
	Optional<CreationEvent> parseCreationEvent(String payload)
	{
		try {
			return Optional.of(objectMapper.readValue(payload, CreationEvent.class));
		} catch (Exception e) {
			// TODO: handle exception
		}
		return Optional.ofNullable(null);
	}
	
	Optional<SalesEvent> parseSalesEvent(String payload)
	{
		try {
			return Optional.of(objectMapper.readValue(payload, SalesEvent.class));
		} catch (Exception e) {
			// TODO: handle exception
		}
		return Optional.ofNullable(null);
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
	
	Optional<ScrapEvent> parseScrapEventEvent(String payload)
	{
		try {
			return Optional.of(objectMapper.readValue(payload, ScrapEvent.class));
		} catch (Exception e) {
			// TODO: handle exception
		}
		return Optional.ofNullable(null);
	}
	
	
	
	Optional<List<RepairEvent>> parseRepairEvent(String payload)
	{
		try {
			return Optional.of(objectMapper.readValue(payload, objectMapper.getTypeFactory().constructParametricType(List.class, RepairEvent.class)));
		} catch (Exception e) {
			// TODO: handle exception
		}
		return Optional.ofNullable(null);
	}
	
	
	
	public ListModel<Vehicle> getVehicles()
	{
 		List<Vehicle> vehicles = new LinkedList<Vehicle>();
 		Vehicle vehicle = new Vehicle();
 		vehicle.setId(UUID.randomUUID().toString());
 		vehicle.setName("Polo");
 		
 		Manufacturer manufacturer = new Manufacturer();
 		manufacturer.setId(UUID.randomUUID().toString());
 		manufacturer.setDateofEstablishment(new Date());
 		manufacturer.setLegalEntity("Voklswagen India Pvt. Ltd");
 		manufacturer.setCountry("India");
 		manufacturer.setName("Volkswagen");
  		vehicle.setManifacturer(manufacturer);
   		vehicles.add(vehicle);
  		return new ListModelList<Vehicle>(vehicles);
 	}
 

	@Command("create")
	public void create()
	{
			Clients.showNotification("Sender Created",	Clients.NOTIFICATION_TYPE_INFO,	null, "top_center", 3100);

	}

	public CreationEvent getCarInfo() {
		return carInfo;
	}

	public SalesEvent getSalesInfo() {
		return salesInfo;
	}

	public List<RepairEvent> getRepairs() {
		return repairs;
	}

	public List<RegistrationEvent> getRegistrations() {
		return registrations;
	}

	public ScrapEvent getScrapInfo() {
		return scrapInfo;
	}

 
	
}
