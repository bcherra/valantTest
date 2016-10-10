#Web APIs for Valant Test Exercise:

The Web REST APIs are designed as a stand alone Java Application that Embed Eclipse Jetty Web and application server 
in it. An open source implementation of JAX-RS Jersey is used to implement Rest services.
The application is built into a jar file using Maven build and dependency resolution system.

#Technical aspects not covered :
	Client Authentication, user security and user service level access. It would have required 3 to 4 hours of
	adding oAUth based APIs and generate security certificate etc.
	Automated Unit Testing infrastructure is not added.
	Response for each Web API response need to be handled in a better way by implementing a generic class 
	called ValantItemInventoryResponse. This needs handling and plumbing of some Jersey plug-ins and require
	some setup time and considerable testing.

#Application Feature Design:
	The data for items is stored in an in-memory model. At startup few items are added to help with testing.	
	User is able to list all Items.
	User is able to get a specific item by Label.
	User is able to add an item.
	User is able to Remove an item by Label.
	
	The operations performed by user generate Events in system that can be accessed by subscribing to the events.
	The events are also stored as in-memory data model.
	 
#Code Structuring:
The main entry point that start the services is implemented in com.valant.test.inventory.javaapp package.
The main class will start Jetty server at passed-in port and set Servlet Handler to Jersey Servlet handler.

com.valant.test.inventoryservices implement Inventory and Event services. All user operations are implemented in these
classes.

com.valant.test.dto package contain Business Data Objects. For our purposes Item and ItemEvent are implemented in this package.

com.valant.test.dbresorces contain classes that implement in-memory data storage for the application. The data is not persisted when application is closed.

com.valant.test.inventory package contain classes that implement business logic and rules.

#Assumption made based on requirements:

1. Item quantity is not considered for this implementation. Only one instance of item is considered.
2. Expired items are not stored in the stored when added by user. One expired item is added at the startup for testing.
3. Events are not pushed but are required to be pulled by the client.
4. Item expiration is checked when user gets an item. This could be done as independent daily job.
5. Since no-user log-in is implemented, event subscription is handled separately by EventsService. The user can
	subscribe to events "itemRemoved" and "itemExpired" by using Interface provided by EventService.

#User Guide:
	Pre-requisites: Java8 is installed on the machine for building or running of the app.
					Maven 3.1 or higher is installed (only for building)
	
#	To build: run mvn clean install
				ValantTest-0.0.1-SNAPSHOT.jar will be generated.
#	To run: javac -jar 		ValantTest-0.0.1-SNAPSHOT.jar xxxx 
			xxxx is port number. it is optional parameter, if not specified server will activate on 8080.
			
#	User Command URLs.
	Note: replace localhost with the ip of machine on which server app is run
	
#	How to Test or Use: 
	Use any REST API testing tool. You can use google REST Console. click https://chrome.google.com/webstore/detail/advanced-rest-client/hgmloofddffdnphfgcellkdfbfbjeloo?hl=en-US
	to know more about it. 
	
	To List all items:
		http://localhost:8080/listItems   			

	To add an item:
		http://localhost:8080/addItem
		POST: {
				"label": "Icyhot",
				"description" : "PAin Gel",
				"expirationDate": "10-10-2016",
				"itemType": "Pain"
				}
	
	To get an item by label:
		http://localhost:8080/getItem?label=Icyhot
		or
		http://localhost:8080/item/Icyhot			
	
	To remove an item:
		HTTP command: DELETE
		http://localhost:8080/getItem?label=Icyhot
		or
		http://localhost:8080/item/Icyhot
	
	Using Events Service:
		
		List all available Events:
		
			http://localhost:8080/events/itemEvents
		
		subscribe to an event:
		
			http://localhost:8080/events/subscribe/{eventName}/{subscriberId}
			replace {eventName} with "itemRemoved" or "itemExpired"
			replace {subscriberId} with a string id that you want to use to read/subscribe.
		
		Read Events:
		
			http://localhost:8080/events/getEvents/{subscriberId}
			replace {subscriberId} with a string id setup in previous step.
		
		to un-subscribe:
		
			http://localhost:8080/events/unsubscribe/{eventName}/{subscriberId}
		
		
		

	 
