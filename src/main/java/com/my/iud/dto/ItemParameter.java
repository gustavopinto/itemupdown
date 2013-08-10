package com.my.iud.dto;

import com.taobao.api.domain.Item;

public class ItemParameter extends BaseParameter{
	
	private Item item;

    private long itemId;

	public long getItemId() {
		return itemId;
	}

	public void setItemId(long itemId) {
		this.itemId = itemId;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}
     
	
     
}
