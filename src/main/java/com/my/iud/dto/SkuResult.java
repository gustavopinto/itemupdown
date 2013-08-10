package com.my.iud.dto;

import java.util.List;

import com.taobao.api.domain.Sku;

public class SkuResult extends BaseResult{
	
	private List<Sku> skuList;

	public List<Sku> getSkuList() {
		return skuList;
	}

	public void setSkuList(List<Sku> skuList) {
		this.skuList = skuList;
	}

}
