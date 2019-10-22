<%@ taglib prefix="zk" uri="http://www.zkoss.org/jsp/zul"%>
<%@ taglib uri="/WEB-INF/security.tld" prefix="sec"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://www.zkoss.org/dsp/web/core" prefix="zc"%>


<zk:page>
	<zk:space height="135px"></zk:space>
	<zk:div apply="org.zkoss.bind.BindComposer"
		viewModel="@id('vm') @init('com.vlm.ui.config.vm.VehiclesVM') @bind(vm.carInfo)   ">
 				<zk:hbox align="center">
					<zk:button label="Search another car"
						onClick="@command('goToSearch')"></zk:button>
					<zk:button label="Simulate Repair Event"
						onClick="@command('goToRepair')"></zk:button>
					<zk:button label="Simulate Sale Event"
						onClick="@command('goToSales')"></zk:button>
					<zk:button label="REST APIs" onClick="@command('goToApi')"></zk:button>
					<zk:button label="Pay and Download Complete Details" ></zk:button>
				</zk:hbox>
 	 	<zk:window height="580px" contentStyle="overflow:auto">
			<zk:groupbox visible="@load(not empty vm.carInfo)">
				<zk:caption style="font-weight: bold"> Vehicle Information</zk:caption>
				<zk:grid>
					<zk:rows>
						<zk:row>
							<zk:cell>
  							Make
  						</zk:cell>
							<zk:cell style="background-color: #6DB33F; color: white;">
								<zk:label value="@load(vm.carInfo.manufacturer)"></zk:label>
							</zk:cell>

							<zk:cell>
  							Model
  						</zk:cell>
							<zk:cell style="background-color: #6DB33F; color: white;">
								<zk:label value="@load(vm.carInfo.model)"></zk:label>
							</zk:cell>

							<zk:cell>
  							Year
  						</zk:cell>
							<zk:cell style="background-color: #6DB33F; color: white;">
								<zk:label value="@load(vm.carInfo.creationDate.year)"></zk:label>
							</zk:cell>
						</zk:row>
						<zk:row>
							<zk:cell>
  							Color
  						</zk:cell>
							<zk:cell style="background-color: #6DB33F; color: white;">
								<zk:label value="@load(vm.carInfo.color)"></zk:label>
							</zk:cell>

							<zk:cell>
  							Transmission
  						</zk:cell>
							<zk:cell style="background-color: #6DB33F; color: white;">
								<zk:label value="@load(vm.carInfo.transmission)"></zk:label>
							</zk:cell>

							<zk:cell>
  							Doors
  						</zk:cell>
							<zk:cell style="background-color: #6DB33F; color: white;">
								<zk:label value="@load(vm.carInfo.doors)"></zk:label>
							</zk:cell>
						</zk:row>

						<zk:row>
							<zk:cell>
  							Type
  						</zk:cell>
							<zk:cell style="background-color: #6DB33F; color: white;">
								<zk:label value="@load(vm.carInfo.carType)"></zk:label>
							</zk:cell>

							<zk:cell>
  							Body Style
  						</zk:cell>
							<zk:cell style="background-color: #6DB33F; color: white;">
								<zk:label value="@load(vm.carInfo.bodyStyle)"></zk:label>
							</zk:cell>

							<zk:cell>
  							Fuel Type
  						</zk:cell>
							<zk:cell style="background-color: #6DB33F; color: white;">
								<zk:hbox>
									<zk:label value="@load(vm.carInfo.fuelType)"></zk:label>
								</zk:hbox>
							</zk:cell>
						</zk:row>

						<zk:row>
							<zk:cell>
  							Engine
  						</zk:cell>
							<zk:cell style="background-color: #6DB33F; color: white;">
								<zk:label value="@load(vm.carInfo.cubicCapacity)"></zk:label>
								<zk:label value="CC"></zk:label>

							</zk:cell>

							<zk:cell>
  							Variant
  						</zk:cell>
							<zk:cell style="background-color: #6DB33F; color: white;">
								<zk:label value="@load(vm.carInfo.variant)"></zk:label>
							</zk:cell>


						</zk:row>
					</zk:rows>
				</zk:grid>
			</zk:groupbox>

			<zk:groupbox visible="@load(not empty vm.salesInfo)">
				<zk:caption style="font-weight: bold">Dealer Information</zk:caption>
				<zk:grid>
					<zk:rows>
						<zk:row>
							<zk:cell>Dealer Name</zk:cell>
							<zk:cell style="background-color: #6DB33F; color: white;">
								<zk:label value="@load(vm.salesInfo.dealerName)"></zk:label>
							</zk:cell>
							<zk:cell>Date Of Purchase</zk:cell>
							<zk:cell style="background-color: #6DB33F; color: white;">
								<zk:label value="@load(vm.salesInfo.date)"></zk:label>
							</zk:cell>
						</zk:row>
					</zk:rows>
				</zk:grid>

			</zk:groupbox>

			<zk:groupbox visible="@load(not empty vm.registrations)">
				<zk:caption style="font-weight: bold">Registration Information</zk:caption>
				<zk:vbox>
					<zk:grid id="registration" model="@bind(vm.registrations)"
						hflex="1">
						<zk:columns>
							<zk:column label="Onwner#" />
							<zk:column label="Valid From" />
							<zk:column label="Valid Til" />
						</zk:columns>
						<zk:template name="model">
						<zk:row>
							<zk:label value="@bind(each.ownerNumber)" />
							<zk:label value="@bind(each.validFrom)" />
							<zk:label value="@bind(each.validTill)" />
      					</zk:row>
					</zk:template>
					</zk:grid>
				</zk:vbox>
			</zk:groupbox>

			<zk:groupbox visible="@load(not empty vm.repairs)">
				<zk:caption style="font-weight: bold">Repair Information</zk:caption>
				<zk:vbox>
					<zk:grid id="repairs" model="@bind(vm.repairs)" hflex="1">
						<zk:columns>
							<zk:column label="Date" />
							<zk:column label="Workshop" />
							<zk:column label="KM Driven" />
							<zk:column label="Repair Summary" />
							<zk:column label="Workdone" />

						</zk:columns>
						<zk:template name="model">
						<zk:row>
							<zk:label value="@bind(each.date)" />
							<zk:label value="@bind(each.dealerName)" />
							<zk:label value="@bind(each.totalDriven)" />
							<zk:label value="@bind(each.jobDescription)" />
							<zk:label value="@bind(each.workDone)" />
        					</zk:row>
					</zk:template>
					</zk:grid>
				</zk:vbox>
			</zk:groupbox>

			<zk:groupbox visible="@load(not empty vm.scrapInfo)">
				<zk:caption style="font-weight: bold">Scrap Information</zk:caption>
				<zk:grid>
					<zk:rows>
						<zk:row>
							<zk:cell>Date of Scrap</zk:cell>
							<zk:cell style="background-color: #6DB33F; color: white;">
								<zk:label value="@load(vm.scrapInfo.scrapDate)"></zk:label>
							</zk:cell>
							<zk:cell>Scrap place</zk:cell>
							<zk:cell style="background-color: #6DB33F; color: white;">
								<zk:label value="@load(vm.scrapInfo.place)"></zk:label>
							</zk:cell>
						</zk:row>
					</zk:rows>
				</zk:grid>

			</zk:groupbox>
		</zk:window>
	</zk:div>
</zk:page>
