<%@ taglib prefix="zk" uri="http://www.zkoss.org/jsp/zul"%>
<%@ taglib uri="/WEB-INF/security.tld" prefix="sec"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://www.zkoss.org/dsp/web/core" prefix="zc"%>


<zk:page>
	<zk:space height="125px"></zk:space>
	<zk:div apply="org.zkoss.bind.BindComposer"
		viewModel="@id('vm') @init('com.vlm.ui.config.vm.RepairVm')   @save(vm.user, before='repair')"
		form="@id('fx') 
 @load(vm.event) @save(vm.event, before='repair')
 ">

		<zk:window  contentStyle="overflow:auto" height="600px">
			<zk:groupbox>
				<zk:caption style="font-weight: bold">Enter Registration Information</zk:caption>
				<zk:grid>
					<zk:rows>
						<zk:row>
							<zk:cell>Chasis Number</zk:cell>
							<zk:cell>
								<zk:textbox value="@bind(fx.chasisNumber)"></zk:textbox>
							</zk:cell>
						</zk:row>
						<zk:row>
							<zk:cell>Date</zk:cell>
							<zk:cell>
								<zk:datebox value="@bind(fx.date)"></zk:datebox>
							</zk:cell>
						</zk:row>
						
								<zk:row>
							<zk:cell>Workshop Name</zk:cell>
							<zk:cell>
								<zk:textbox value="@bind(fx.dealerName)"></zk:textbox>
							</zk:cell>
						</zk:row>
						
						
							<zk:row>
							<zk:cell>KM Driven</zk:cell>
							<zk:cell>
								<zk:intbox value="@bind(fx.totalDriven)"></zk:intbox>
							</zk:cell>
						</zk:row>

						<zk:row>
							<zk:cell>Job Id</zk:cell>
							<zk:cell>
								<zk:textbox value="@bind(fx.jobId)"></zk:textbox>
							</zk:cell>
						</zk:row>
						
						<zk:row>
							<zk:cell>Job Summary</zk:cell>
							<zk:cell>
								<zk:textbox value="@bind(fx.jobDescription)"></zk:textbox>
							</zk:cell>
						</zk:row>
						
								
						<zk:row>
							<zk:cell>Work Done</zk:cell>
							<zk:cell>
								<zk:textbox multiline="true" height="200px" value="@bind(fx.workDone)"></zk:textbox>
							</zk:cell>
						</zk:row>
						
						<zk:row>
							<zk:cell colspan="2">
								<zk:button label="Simulate Repair Event"
									onClick="@command('repair')"></zk:button>

							</zk:cell>
						</zk:row>
					</zk:rows>

				</zk:grid>
			</zk:groupbox>

		</zk:window>
	</zk:div>
</zk:page>
