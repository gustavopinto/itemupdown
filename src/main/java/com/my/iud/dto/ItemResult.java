package com.my.iud.dto;

import com.taobao.api.domain.Item;


public class ItemResult extends BaseResult {
	
	private Item item;

	public ItemResult() {
		super();
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}
	
}
