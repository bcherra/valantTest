package com.valant.test.inventory.services;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.valant.test.dto.Item;
import com.valant.test.dbresources.ItemsInventory;
import com.valan.test.inventory.ItemInventoryRules;

@Path("/")
public class InventoryService {

	ItemInventoryRules rules = new ItemInventoryRules(); //TODO: it could be static or singleton for our current requirements
	/*
	 * List all the items from the ItemInventory in-memory data resource.
	 */
	@GET
	@Path("/listItems")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Item> listItems() {
		return ItemsInventory.getItems();
	}
	
	/*
	 * Get the item by its label from the ItemInventory in-memory data resource.
	 * The API could be called by using query form with 'label' as key and item label as its value.
	 */
	@GET
	@Path("/getItem")
	@Produces(MediaType.APPLICATION_JSON)
	public Item getItem(@QueryParam("label") String itemLabel) {
		Item item = ItemsInventory.getItemByLabel(itemLabel);
		
		if(item != null && rules.isItemExpired(item)) {
				rules.publishItemExpiredEvent(item);
		}
		
		return item;
	}
	/*
	 * Get the item by its label from the ItemInventory in-memory data resource.
	 * The API could be called by using url as /item/label instead of QueryForm. 
	 */
	@GET
	@Path("/item/{label}")
	@Produces(MediaType.APPLICATION_JSON)
	public Item getItem2(@PathParam("label") String itemLabel) {
		return getItem(itemLabel);
	}
	/*
	 * Adds an item to the in-memory ItemInventory data resource.
	 * The API could be accessed as host:port/addItem, with a post of JOSN string of item data.
	 * {"label" : "", "description":"", expirationDate: "mm-dd-yyyy", "itemType":""} 
	 */
	@POST
	@Path("/addItem")
	@Consumes(MediaType.APPLICATION_JSON)
	public void addItem(Item item) {
		System.out.println("Adding Item " + item.getLabel());
		//should we add expired item? Assume not.
		if(rules.isItemExpired(item)) {
			return;
		}
		ItemsInventory.addItem(item);
	} 
	/*
	 * Removes an item from in-memory ItemInventory data resource by it label. 
	 * Can access it as ip:port/removeItem?label="xyz"
	 */
	
	@DELETE
	@Path("/removeItem")
	@Produces(MediaType.APPLICATION_JSON)
	public Item removeItem(@QueryParam("label") String itemLabel) {
		//TODO: Should Published Unread Item Events be removed with removal of item?
		// Most likely not. Previous status may still be useful to the subscribers.
		System.out.println("Removing Item " + itemLabel);
		Item item = ItemsInventory.getItemByLabel(itemLabel);
		if (item != null) {
			ItemsInventory.removeItem(itemLabel);
			rules.publishItemRemovedEvent(item);
		}
		
		return item;
	} 
	/*
	 * Removes an item from in-memory ItemInventory data resource by it label. 
	 * Can access it as ip:port/removeItem/label
	 */
	@DELETE
	@Path("/item/{label}")
	@Produces(MediaType.APPLICATION_JSON)
	public Item removeItem2(@PathParam("label") String itemLabel) {
		return removeItem(itemLabel);
	} 
}
