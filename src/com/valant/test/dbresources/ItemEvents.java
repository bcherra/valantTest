package com.valant.test.dbresources;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.valant.test.dto.ItemEvent;
public class ItemEvents {
	
	public static final String ITEM_REMOVED_EVENT = "itemRemoved";
	public static final String ITEM_EXPIRED_EVENT = "itemExpired";

	private static Map<String, List<ItemEvent>> eventsContainer = new HashMap<>();
	
	private static Map<String, Set<String>> subscribers = new HashMap<>();
	
	static Set<String> events = new HashSet<>();
	
	static {
		events.add(ITEM_REMOVED_EVENT);
		events.add(ITEM_EXPIRED_EVENT);
	}
	/*
	 * @input eventName: the event to subscribe to.
	 * @subscriberId: the subscriber's id.
	 * Add a subscriber to item events by subscriber id.
	 */
	public static void addSubscriber(String eventName, String subscriberId) {
		if(!events.contains(eventName)) {
			return;
		}
		Set<String> eventSubscriberList;
		if(subscribers.containsKey(eventName)) {
			eventSubscriberList = subscribers.get(eventName);
		} else {
			eventSubscriberList = new HashSet<>();
			subscribers.put(eventName, eventSubscriberList);
		}
		eventSubscriberList.add(subscriberId);
		System.out.println("Subscriber " + subscriberId + " added for event " + eventName);
	}
	
	/*
	 * @input eventName: the event to subscribe to.
	 * @subscriberId: the subscriber's id.
	 * Remove a given subscriber from listening to events.
	 */
	
	public static void removeSubscriber(String eventName, String subscriberId) {
		if(subscribers.containsKey(eventName)) {
			Set<String> eventSubscriberList = subscribers.get(eventName);
			eventSubscriberList.remove(subscriberId);
			if(eventSubscriberList.size() == 0) {
				subscribers.remove(eventName);
			}
		}
		System.out.println("Subscriber " + subscriberId + " removed for event " + eventName);
	}
	/*
	 * @input subscriberId
	 * @return A list of unread events for the subscriber.
	 * All unread events will be returned. The events are removed from available events list.
	 */
	public static List<ItemEvent> getSubscriberEvents(String subscriberId) {
		List<ItemEvent> subscriberEvents = null;
		if(eventsContainer.containsKey(subscriberId)) {
			subscriberEvents = eventsContainer.get(subscriberId);
			eventsContainer.remove(subscriberId); // Assumption is that once events are read, those are removed.
		}
		return subscriberEvents;
	}
	
	public static void publishEvent(ItemEvent event) {
		Collection<String> eventSubscribers = null;
		if(subscribers.containsKey(event.getName())) {
			eventSubscribers = subscribers.get(event.getName());
		}
		for(String subscriber: eventSubscribers) {
			if(eventsContainer.containsKey(subscriber)) {
				eventsContainer.get(subscriber).add(event);
			} else{
				List<ItemEvent> _events = new ArrayList<>();
				_events.add(event);
				eventsContainer.put(subscriber, _events);
			}
		}
	}
	
	/*
	 * 
	 */
	public static Set<String> listItemEvents() {
		return events;
	}
}
