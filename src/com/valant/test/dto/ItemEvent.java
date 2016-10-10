package com.valant.test.dto;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ItemEvent {

	private String name;
	private Item eventObject;
	
	public ItemEvent(String name, Item eventObject) {
		this.name = name;
		this.eventObject = eventObject;
	}
	public ItemEvent() {}
	public String getName() {
		return name;
	}
	public Item getEventObject() {
		return eventObject;
	}
	@Override
	public String toString() {
		return "ItemEvent [name=" + name + ", eventObject=" + eventObject.toString() + "]";
	}
}
