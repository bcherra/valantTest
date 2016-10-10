package com.valant.test.inventory.services;

import java.util.Collection;
import java.util.List;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.valant.test.dbresources.ItemEvents;
import com.valant.test.dto.ItemEvent;

/*
 * Events Service provide REST interface to subscribe and access Events. These events are generated when items
 * are managed via InventoryService.
 */

@Path("/events")
public class EventsService {

	/*
	 * Return all unread events available for the subscriber. The Subscriber must register before events can
	 * be read for the subscriber.
	 */
	@GET
	@Path("/getEvents/{subscriberId}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<ItemEvent> getEvents(@PathParam("subscriberId") String subscriberId) {
		return ItemEvents.getSubscriberEvents(subscriberId);
	}
	
	/*
	 * Returns a list of all the event names that are available/produced by the ItemInventory system.
	 */
	@GET
	@Path("/itemEvents")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<String> listItemEvents() {
		return ItemEvents.listItemEvents();
	}
	/*
	 * @input eventName - the name of the event in which subscriber want to be notified about.
	 * @input subscriberId by which the subscriber would read the generated events.
	 * Adds the subscriber to the list of listeners for the event.
	 */
	@PUT
	@Path("/subscribe/{eventName}/{subscriberId}")
	@Produces(MediaType.APPLICATION_JSON)
	public void subscribeToEvent(@PathParam("eventName") String eventName, @PathParam("subscriberId") String subscriberId) {
		ItemEvents.addSubscriber(eventName, subscriberId);;
	}
	
	/*
	 * @input eventName - the name of the event from which subscriber want un-subscribe.
	 * @input subscriberId by which the subscriber had registered
	 * The subscriber will be removed from list of listeners of the said event.
	 */
	@DELETE
	@Path("/unsubscribe/{eventName}/{subscriberId}")
	@Produces(MediaType.APPLICATION_JSON)
	public void unsubscribeToEvent(@PathParam("eventName") String eventName, @PathParam("subscriberId") String subscriberId) {
		ItemEvents.removeSubscriber(eventName, subscriberId);;
	}
}
