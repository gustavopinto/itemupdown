package com.my.iud.service;

import java.util.List;

import com.my.iud.dto.ItemFormBean;
import com.my.iud.dto.ItemParameter;
import com.my.iud.dto.ItemQueryParameter;
import com.my.iud.dto.ItemResult;
import com.my.iud.util.Pagination;
import com.taobao.api.ApiException;
import com.taobao.api.domain.Item;
import com.taobao.api.response.ItemGetResponse;

public interface ItemService {
    
	/**
	 * 获取出售的商品
	 * @param parameter
	 * @return
	 * @throws ApiException
	 */
	long getOnsaleItemsCount(ItemQueryParameter parameter) throws Exception;
   
	/**
	 * 获取库存中的商品
	 * @param parameter
	 * @return
	 * @throws ApiException
	 */
	long getInventoryItemsCount(ItemQueryParameter parameter) throws Exception;
    
	
	/**
	 * 分页出售中的商品
	 * @param parameter
	 * @return
	 * @throws ApiException
	 */
	Pagination<Item> getOnsaleItemsByPage(ItemQueryParameter parameter) throws Exception;
    
	/**
	 * 分页库存中的商品
	 * @param parameter
	 * @return
	 * @throws ApiException
	 */
	Pagination<Item> getInventoryItemsByPage(ItemQueryParameter parameter) throws Exception;
	
	/**
	 * 商品下架
	 * @param itemFormBean
	 */
	void itemUpdateDelisting(ItemFormBean itemFormBean) throws Exception;
	
	
	
	/**
	 * 商品上架
	 * @param itemFormBean
	 */
    void itemUpdateListing(ItemFormBean itemFormBean) throws Exception;
    
    
    
    /**
     * 出售中的商品下架
     * @param itemFormBean
     */
    void onsaleItemsDelisting(ItemFormBean itemFormBean) throws Exception;
    
    
    /**
     * 出售中的商品上架
     * @param itemFormBean
     */
    void onsaleItemsUpdateListing(ItemFormBean itemFormBean) throws Exception;

    
    
    /**
     * 库存中的商品上架
     * @param itemFormBean
     */
    void onInventoryItemsListing(ItemFormBean itemFormBean) throws Exception;
    
    
    
    
    /**
     * 获取宝贝
     * @param parameter
     * @return
     * @throws ApiException
     */
    ItemGetResponse getItem(ItemQueryParameter parameter) throws Exception;
    
    
    /**
     * 批量获取宝贝
     * @param parameter
     * @return
     * @throws ApiException
     */
    List<Item> getBatchItem(ItemQueryParameter parameter) throws Exception;
    
    
	
    /**
     * 复制宝贝
     * @param itemParameter
     * @param path
     * @throws Exception
     */
	ItemResult copyItem(ItemParameter itemParameter, String path) throws Exception;
	
	/**
	 * 获取热卖宝贝
	 * @param keyWord
	 * @return
	 * @throws Exception
	 */
	public List<Item> searchHotItem(String keyWord) throws Exception;
	
	/**
	 * 选择宝贝橱窗推荐
	 * @param parameter
	 * @throws Exception
	 */
	public void sItemRecommend(ItemFormBean itemFormBean) throws Exception;
	
	/**
	 * 选择宝贝橱窗推荐取消
	 * @param parameter
	 * @throws Exception
	 */
	public void sItemRecommendDelete(ItemFormBean itemFormBean) throws Exception;
	
	
	/**
	 * 架上宝贝推荐
	 * @throws Exception
	 */
	public void onsaleItemsRecommend(ItemFormBean ifb) throws Exception;
	
	
	/**
	 *  架上宝贝橱窗取消
	 * @throws Exception
	 */
	public void onsaleItemsRecommendDelete(ItemFormBean ifb) throws Exception;

}
