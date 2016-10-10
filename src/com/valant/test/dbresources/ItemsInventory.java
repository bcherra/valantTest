package com.valant.test.dbresources;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.valant.test.dto.Item;

public class ItemsInventory {
	private static Map<String, Item> items = new HashMap<>();
	
	/*
	 * @input item Object
	 * The passed item will be stored in the items map by label key of the item.
	 */
	public static void addItem(Item item) {
		if(item == null) {
			return;
		}
		items.put(item.getLabel(),item);
	}
	/*
	 * @return Current Collection of Item objects
	 * 
	 */
	public static List<Item> getItems() {
		Collection<Item> list = items.values();
		return new ArrayList<Item>(list);
	}
	/*
	 * @input item's label String 
	 * Removes the item from item map collection.
	 */
	public static  void removeItem(String itemLabel) {
		items.remove(itemLabel);
	}
	/*
	 * @input item's label String 
	 * get the item from item map collection.
	 */
	public static  Item getItemByLabel(String itemLabel) {
		return items.get(itemLabel);
	}
}
