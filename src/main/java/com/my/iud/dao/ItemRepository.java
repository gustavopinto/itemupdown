package com.my.iud.dao;


import com.my.iud.dto.ItemParameter;
import com.my.iud.dto.ItemResult;
import com.taobao.api.ApiException;
import com.taobao.api.FileItem;
import com.taobao.api.TaobaoClient;
import com.taobao.api.domain.Item;
import com.taobao.api.request.ItemAddRequest;
import com.taobao.api.request.ItemGetRequest;
import com.taobao.api.response.ItemAddResponse;
import com.taobao.api.response.ItemGetResponse;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("itemRepository")
public class ItemRepository {
	
	private final static Logger logger = LoggerFactory.getLogger(ItemRepository.class);

	
	@Autowired
	private TaobaoClient taoBaoClient;
	

	

	public ItemResult getItemById(long itemId, String sessionKey) throws ApiException{
		ItemGetRequest req=new ItemGetRequest();
		ItemResult itemReslut = new ItemResult();
		
		req.setFields("num,price,type,stuff_status,title,desc,location,cid,pic_url,sku.quantity,sku.price,sku.properties,props");
		req.setNumIid(itemId);
		ItemGetResponse response = taoBaoClient.execute(req, sessionKey);
		
		itemReslut.setSucess(response.isSuccess());
		itemReslut.setErrorCode(response.getErrorCode());
		itemReslut.setMsg(response.getMsg());
		itemReslut.setSubCode(response.getSubCode());
		itemReslut.setSubMsg(response.getSubMsg());
		itemReslut.setItem(response.getItem());
		return itemReslut;
	}
	
	public ItemResult addItem(ItemParameter itemParameter, String filePath) throws ApiException{
		ItemAddRequest req=new ItemAddRequest();
		ItemResult itemReslut = new ItemResult();

		Item item = itemParameter.getItem();
		req.setNum(item.getNum());
		req.setPrice(item.getPrice());
		req.setType(item.getType());
		req.setStuffStatus(item.getStuffStatus());
		req.setTitle(item.getTitle());
		req.setProps(item.getProps());
		req.setDesc(item.getDesc());
		req.setLocationCity(item.getLocation().getCity());
		req.setLocationState(item.getLocation().getState());
		req.setCid(item.getCid());
		req.setApproveStatus("instock");
		
		if(StringUtils.isNotEmpty(filePath)){ 
			FileItem pic = new FileItem(filePath);
			req.setImage(pic);
		}
		
		ItemAddResponse response =taoBaoClient.execute(req, itemParameter.getSessionKey());
		logger.info("***************" + response.getBody());
		itemReslut.setSucess(response.isSuccess());
		itemReslut.setErrorCode(response.getErrorCode());
		itemReslut.setMsg(response.getMsg());
		itemReslut.setSubCode(response.getSubCode());
		itemReslut.setSubMsg(response.getSubMsg());
		itemReslut.setItem(response.getItem());
		return itemReslut;
	}
	
}
