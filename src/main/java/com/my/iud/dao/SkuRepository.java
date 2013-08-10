package com.my.iud.dao;

import com.my.iud.dto.SkuParameter;
import com.my.iud.dto.SkuResult;
import com.taobao.api.ApiException;
import com.taobao.api.TaobaoClient;
import com.taobao.api.domain.Sku;
import com.taobao.api.request.ItemSkuAddRequest;
import com.taobao.api.response.ItemSkuAddResponse;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("skuRepository")
public class SkuRepository {
	
	@Autowired
	private TaobaoClient taoBaoClient;
	
	
	public SkuResult addSku(SkuParameter skuParameter) throws ApiException{
		List<Sku> skuList = skuParameter.getSkuList();
		SkuResult skuResult = new SkuResult();
		List<Sku> skuResultList = new ArrayList<>();
		for(Sku sku : skuList){
			ItemSkuAddRequest req=new ItemSkuAddRequest();
			req.setNumIid(skuParameter.getItemId());
			req.setProperties(sku.getProperties());
			req.setPrice(sku.getPrice());
			req.setQuantity(sku.getQuantity());
            ItemSkuAddResponse response = taoBaoClient.execute(req , skuParameter.getSessionKey());
            skuResultList.add(response.getSku());
		}
		skuResult.setSkuList(skuResultList);
		return skuResult;
	}

}
