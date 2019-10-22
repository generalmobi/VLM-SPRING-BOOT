<%@ taglib prefix="zk" uri="http://www.zkoss.org/jsp/zul"%>
<%@ taglib uri="/WEB-INF/security.tld" prefix="sec"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://www.zkoss.org/dsp/web/core" prefix="zc"%>


<zk:page>
	<zk:space height="125px"></zk:space>
	<zk:div apply="org.zkoss.bind.BindComposer"
		viewModel="@id('vm') @init('com.vlm.ui.config.vm.RegistrationVm')   @save(vm.user, before='register')" form="@id('fx') 
 @load(vm.event) @save(vm.event, before='register')
 ">
 				 
 	 	<zk:window height="600px">
			<zk:groupbox >
				<zk:caption style="font-weight: bold">Enter Registration Information</zk:caption>
			 	 <zk:grid>
			 	 	<zk:rows>
			 	 		<zk:row>
			 	 			<zk:cell>Chasis Number</zk:cell>
			 	 			<zk:cell>
			 	 				<zk:textbox value="@bind(fx.chasisNumber)" ></zk:textbox>
			 	 			</zk:cell>
			 	 		</zk:row>
 			 	 		<zk:row>
			 	 			<zk:cell>Valid From</zk:cell>
			 	 			<zk:cell>
			 	 				<zk:datebox value="@bind(fx.validFrom)" ></zk:datebox>
			 	 			</zk:cell>
			 	 		</zk:row>
			 	 		
 			 	 		<zk:row>
			 	 			<zk:cell>Valid To</zk:cell>
			 	 			<zk:cell>
			 	 				<zk:datebox value="@bind(fx.validTill)" ></zk:datebox>
			 	 			</zk:cell>
			 	 		</zk:row>
			 	 		<zk:row>
			 	 			<zk:cell colspan="2">
			 	 								<zk:button label="Simulate Repair Event"
						onClick="@command('register')"></zk:button>
			 	 			
			 	 			</zk:cell>
			 	 		</zk:row>
			 	 	</zk:rows>
			 	 	
			 	 </zk:grid>
			</zk:groupbox>
 
		</zk:window>
	</zk:div>
</zk:page>
