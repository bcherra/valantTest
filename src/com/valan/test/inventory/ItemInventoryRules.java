package com.valan.test.inventory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.valant.test.dbresources.ItemEvents;
import com.valant.test.dto.Item;
import com.valant.test.dto.ItemEvent;
public class ItemInventoryRules {

	public boolean isItemExpired(Item item) {
		Date today = new Date();
		SimpleDateFormat dateFormat1 = new SimpleDateFormat ("dd-MM-yyyy");//assumption : for now, only two date formats
		SimpleDateFormat dateFormat2 = new SimpleDateFormat ("dd/MM/yyyy");
		Date itemExpirationDate = null; 
		try {
			itemExpirationDate = dateFormat1.parse(item.getExpirationDate());
		} catch(ParseException pExp) {
			try {
				itemExpirationDate = dateFormat2.parse(item.getExpirationDate());
			} catch(ParseException p2Exp) {
				itemExpirationDate = new Date();
			}
		} finally {
			if (itemExpirationDate == null) {
				itemExpirationDate = new Date();
			}
		}
		
		return today.after(itemExpirationDate) || today.equals(itemExpirationDate);
	}
	
	public void publishItemRemovedEvent(Item item) {
		ItemEvent event = new ItemEvent(ItemEvents.ITEM_REMOVED_EVENT, item);
		ItemEvents.publishEvent(event);
	}
	public void publishItemExpiredEvent(Item item) {
		ItemEvent event = new ItemEvent(ItemEvents.ITEM_EXPIRED_EVENT, item);
		ItemEvents.publishEvent(event);
	}
	
}
