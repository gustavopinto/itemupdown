package com.my.iud.dto;

import java.util.List;

import com.taobao.api.domain.Sku;

public class SkuParameter extends BaseParameter {

	private List<Sku> skuList;
	
	public long itemId;


	public List<Sku> getSkuList() {
		return skuList;
	}

	public void setSkuList(List<Sku> skuList) {
		this.skuList = skuList;
	}

	public long getItemId() {
		return itemId;
	}

	public void setItemId(long itemId) {
		this.itemId = itemId;
	}
	
}
