package com.valant.test.dto;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement 
public class Item {
	private  String label;
	private  String description;
	private  String expirationDate; //mm-dd-yyyy or empty
	private  String itemType;
	
	public Item(String label, String description, String expirationDate, String itemType) {
		this.label = label;
		this.description = description;
		this.expirationDate = expirationDate == null? "" : expirationDate;
		this.itemType = itemType;
	}
	
	public Item(){}
	
	public String getLabel() {
		return label;
	}

	public String getDescription() {
		return description;
	}

	public String getExpirationDate() {
		return expirationDate;
	}

	public String getItemType() {
		return itemType;
	}

	@Override
	public String toString() {
		return "Item [label=" + label + ", description=" + description + ", expirationDate=" + expirationDate
				+ ", itemType=" + itemType + "]";
	}
}
