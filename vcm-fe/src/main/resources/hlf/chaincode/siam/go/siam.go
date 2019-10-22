/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

/*
 * The sample smart contract for documentation topic:
 * Writing Your First Blockchain Application
 */

 package main

 /* Imports
  * 4 utility libraries for formatting, handling bytes, reading and writing JSON, and string manipulation
  * 2 specific Hyperledger Fabric specific libraries for Smart Contracts
  */
 import (
         "encoding/json"
         "fmt"
         "github.com/hyperledger/fabric/core/chaincode/shim"
         sc "github.com/hyperledger/fabric/protos/peer"
 )
 
 // Define the Smart Contract structure
 type SmartContract struct {
 }

 type CreationEvent struct {
        Model   string `json:model` 
        Manufacturer   string `json:manufacturer`
        CreationDate string  `json:creationDate`
        ChasisNumber string `json:chasisNumber`
        Color string `json:color`
        Transmission string `json:transmission`
        Doors int `json:doors`
        CarType string `json:carType`
        fuelType string `json:fuelType`
	variant string  `json:variant`
        bodyStyle int  `json:bodyStyle`
        cubicCapacity int  `json:cubicCapacity`
}

type RegistrationEvent struct {
        ChasisNumber string `json:chasisNumber`
        ValidFrom string  `json:validFrom`
        ValidTill string  `json:validTill`
        OwnerNumber int  `json:ownerNumber`
}
 
 // Define the car structure, with 4 properties.  Structure tags are used by encoding/json library
 type RepairEvent struct {
        ChasisNumber string  `json:chasisNumber`
	DealerName string  `json:dealerName`
	JobId string  `json:jobId`
	JobDescription string  `json:jobDescription`
	WorkDone string  `json:workDone`
	TotalDriven int  `json:totalDriven`
 }
 

  // Define the car structure, with 4 properties.  Structure tags are used by encoding/json library
  type SalesEvent struct {
        DealerName string  `json:dealerName`
        ChasisNumber string  `json:chasisNumber`
        Date string  `json:date`
}
 

  // Define the car structure, with 4 properties.  Structure tags are used by encoding/json library
  type ScrapEvent struct {
        ChasisNumber string  `json:chasisNumber`
        ScrapDate string  `json:scrapDate`
        Place string  `json:place`
}
 



 /*
  * The Init method is called when the Smart Contract "fabcar" is instantiated by the blockchain network
  * Best practice is to have any Ledger initialization in separate function -- see initLedger()
  */
 func (s *SmartContract) Init(APIstub shim.ChaincodeStubInterface) sc.Response {
         return shim.Success(nil)
 }
 
 /*
  * The Invoke method is called as a result of an application request to run the Smart Contract "fabcar"
  * The calling application program has also specified the particular smart contract function to be called, with arguments
  */
 func (s *SmartContract) Invoke(APIstub shim.ChaincodeStubInterface) sc.Response {
 
         // Retrieve the requested Smart Contract function and arguments
         function, args := APIstub.GetFunctionAndParameters()
         // Route to the appropriate handler function to interact with the ledger appropriately
         if function == "creationEvent" {
                 return s.handleCreationEvent(APIstub, args)
         } else if function == "registrationEvent" {
                 return s.handleRegistrationEvent(APIstub, args)
         } else if function == "salesEvent" {
                 return s.handleSalesEvent(APIstub,args)
         } else if function == "scrapEvent" {
                 return s.handleScrapEvent(APIstub, args)
         } else if function == "repairEvent" {
                return s.handleRepairEvent(APIstub, args)
         } else if function == "getCreation" {
                return s.getCreation(APIstub, args)
         } else if function == "getRegistration" {
                return s.getRegistration(APIstub, args)
         } else if function == "getSales" {
                return s.getSales(APIstub, args)
         } else if function == "getScrap" {
                return s.getScrap(APIstub, args)
         } else if function == "getRepair" {
                return s.getRepair(APIstub, args)
         }
         
         
         return shim.Error("Invalid Smart Contract function name.")
 }
 

 func (s *SmartContract) handleCreationEvent(APIstub shim.ChaincodeStubInterface, args []string) sc.Response {
 
         if len(args) != 1{
                 return shim.Error("Incorrect number of arguments. Expecting 1")
         }
 
         fmt.Printf("- start handleCarCreation for : %s\n", args[0])
         var creationEvent CreationEvent
         err := json.Unmarshal([]byte(args[0]),&creationEvent)
         if err != nil {
		fmt.Printf("Error in Unmarshal: %s", err)
	}
        fmt.Printf("- Saving : %s=>%s\n", creationEvent.ChasisNumber+"_CE",args[0])

         APIstub.PutState(creationEvent.ChasisNumber+"_CE", []byte(args[0]))
         return shim.Success(nil)
 }
 

 func (s *SmartContract) handleRegistrationEvent(APIstub shim.ChaincodeStubInterface, args []string) sc.Response {                
        if len(args) != 1{
                return shim.Error("Incorrect number of arguments. Expecting 1")
        }

        fmt.Printf("- start createCar for : %s\n", args[0])
        var registrationEvent RegistrationEvent;         
        err := json.Unmarshal([]byte(args[0]),&registrationEvent)
        if err != nil {
		fmt.Printf("Error in Unmarshal: %s", err)
	}
        registrationAsBytes, _ := APIstub.GetState(registrationEvent.ChasisNumber+"_REG")  
        events :="[]";    
        if registrationAsBytes == nil{
                events = args[0]
        }else {
          events = string(registrationAsBytes)+","+args[0]
        }
        APIstub.PutState(registrationEvent.ChasisNumber+"_REG", []byte(events))
        return shim.Success(nil)
}

func (s *SmartContract) handleSalesEvent(APIstub shim.ChaincodeStubInterface, args []string) sc.Response {                
        if len(args) != 1 {
                return shim.Error("Incorrect number of arguments. Expecting 1")
        }

        fmt.Printf("- start handleCarCreation for : %s\n", args[0])
        var salesEvent SalesEvent
        err := json.Unmarshal([]byte(args[0]),&salesEvent)
        if err != nil {
		fmt.Printf("Error in Unmarshal: %s", err)
	}
        APIstub.PutState(salesEvent.ChasisNumber+"_SLE", []byte(args[0]))
        return shim.Success(nil)
}

func (s *SmartContract) handleScrapEvent(APIstub shim.ChaincodeStubInterface, args []string) sc.Response {                
        if len(args) != 1{
                return shim.Error("Incorrect number of arguments. Expecting 1")
        }

        fmt.Printf("- start handleCarCreation for : %s\n", args[0])
        var scrapEvent ScrapEvent
        err := json.Unmarshal([]byte(args[0]),&scrapEvent)
        if err != nil {
		fmt.Printf("Error in Unmarshal: %s", err)
	}
        APIstub.PutState(scrapEvent.ChasisNumber+"_SCE", []byte(args[0]))
        return shim.Success(nil)
}

 
func (s *SmartContract) handleRepairEvent(APIstub shim.ChaincodeStubInterface, args []string) sc.Response {                
        if len(args) != 1{
                return shim.Error("Incorrect number of arguments. Expecting 1")
        }

        fmt.Printf("- start handleRepairEvent for : %s\n", args[0])
        var repairEvent RepairEvent;         
        err := json.Unmarshal([]byte(args[0]),&repairEvent)
        if err != nil {
		fmt.Printf("Error executing Unmarshal: %s", err)
	}
        repairAsBytes, _ := APIstub.GetState(repairEvent.ChasisNumber+"_REP")
        events := "[]"
        if repairAsBytes ==nil {
                events = args[0];        
        } else{ 
                events =  string(repairAsBytes)+","+ args[0];
        }

        fmt.Printf("- start saving : %s\n", events)

        APIstub.PutState(repairEvent.ChasisNumber+"_REP", []byte(events))
        return shim.Success(nil)
}

 

func (s *SmartContract) getCreation(APIstub shim.ChaincodeStubInterface, args []string) sc.Response {

        if len(args) != 1 {
                return shim.Error("Incorrect number of arguments. Expecting 1")
        }
        fmt.Printf("- Searching car by : %s=>%s\n",args[0]+"_CE")
        creationAsBytes, _ := APIstub.GetState(args[0]+"_CE")
        fmt.Printf("- Found car by : %s=>%s\n",creationAsBytes)

        return shim.Success(creationAsBytes)
}


func (s *SmartContract) getRegistration(APIstub shim.ChaincodeStubInterface, args []string) sc.Response {

        if len(args) != 1 {
                return shim.Error("Incorrect number of arguments. Expecting 1")
        }

        registationAsBytes, _ := APIstub.GetState(args[0]+"_REG")
        result := "[" +  string(registationAsBytes) +"]"
        return shim.Success([]byte(result))
}


func (s *SmartContract) getSales(APIstub shim.ChaincodeStubInterface, args []string) sc.Response {

        if len(args) != 1 {
                return shim.Error("Incorrect number of arguments. Expecting 1")
        }

        salesAsBytes, _ := APIstub.GetState(args[0]+"_SLE")
        return shim.Success(salesAsBytes)
}



func (s *SmartContract) getScrap(APIstub shim.ChaincodeStubInterface, args []string) sc.Response {

        if len(args) != 1 {
                return shim.Error("Incorrect number of arguments. Expecting 1")
        }

        scrapAsBytes, _ := APIstub.GetState(args[0]+"_SCE")
        return shim.Success(scrapAsBytes)
}

func (s *SmartContract) getRepair(APIstub shim.ChaincodeStubInterface, args []string) sc.Response {

        if len(args) != 1 {
                return shim.Error("Incorrect number of arguments. Expecting 1")
        }

        repairAsBytes, _ := APIstub.GetState(args[0]+"_REP")

        result := "[" +  string(repairAsBytes) +"]"
 
        return shim.Success([]byte(result))
}

   
   
 // The main function is only relevant in unit test mode. Only included here for completeness.
 func main() {
 
         // Create a new Smart Contract
         err := shim.Start(new(SmartContract))
         if err != nil {
                 fmt.Printf("Error creating new Smart Contract: %s", err)
         }
 }