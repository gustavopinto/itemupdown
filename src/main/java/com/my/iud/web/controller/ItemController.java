package com.my.iud.web.controller;

import com.my.iud.dto.ItemParameter;
import com.my.iud.dto.ItemQueryParameter;
import com.my.iud.dto.ItemResult;
import com.my.iud.entity.User;
import com.my.iud.service.ItemService;
import com.my.iud.util.Constants;
import com.my.iud.util.Pagination;
import com.taobao.api.domain.Item;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.commons.lang.NumberUtils;
import org.apache.commons.lang.StringUtils;
import org.guzz.util.StringUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/item")
public class ItemController {
	
	private final static Logger logger = LoggerFactory.getLogger(ItemController.class);
	
	@Autowired
	private ItemService itemService;
	
	
	@RequestMapping(value="/getItemsCount",method=RequestMethod.GET)
	public @ResponseBody  Map<String, String> getItemCount(@RequestParam("sitem") int sitem, HttpServletRequest request){
		User user = (User)request.getSession().getAttribute("user");
		
		ItemQueryParameter itemQueryParameter = new ItemQueryParameter();
		itemQueryParameter.setSessionKey(user.getSessionKey());
		
		long itemcount = 0L;

		try {
			if(sitem == 2){
				itemcount = itemService.getOnsaleItemsCount(itemQueryParameter);
			}else if(sitem == 3){
				itemcount = itemService.getInventoryItemsCount(itemQueryParameter);
			}else if(sitem == 1){
				long onSaleItemCount = itemService.getOnsaleItemsCount(itemQueryParameter);
				long onInventoryItemCount = itemService.getInventoryItemsCount(itemQueryParameter);
				itemcount = onSaleItemCount + onInventoryItemCount;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage(), e);
		}
		Map<String, String> resultMap = new HashMap<>();
		resultMap.put("total", String.valueOf(itemcount));

		return  resultMap;
	}
	
	@RequestMapping(value="/prepareSearchItem")
	public String prepareSearchItem(@RequestParam(value="selectItems",required=false) String selectItems, 
			@RequestParam(value="gtask", required=false, defaultValue = "0") int gtask,
			HttpServletRequest request, ModelMap modelMap){
		User user = (User)request.getSession().getAttribute("user");
		ItemQueryParameter itemQueryParameter = new ItemQueryParameter();
		itemQueryParameter.setSessionKey(user.getSessionKey());
		
		try {
			List<Item> itemList = new ArrayList<>();
			Pagination<Item> page = itemService.getOnsaleItemsByPage(itemQueryParameter);
	
			modelMap.put("itemPagination", page);
			modelMap.put("queryPara", itemQueryParameter);
			modelMap.put("gtask", gtask);
			if(StringUtil.notEmpty(selectItems)){
				itemQueryParameter.setItemIdArray(selectItems);
				if(StringUtil.notEmpty(selectItems)){
					itemList = itemService.getBatchItem(itemQueryParameter);
				}
				modelMap.put("selectItems", itemList);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage(), e);
		}
		
        return "search_item";
	}
	
	
	
	@RequestMapping(value="/ajaxSearchItem")
	public @ResponseBody Pagination<Item> ajaxSearchItem(@ModelAttribute ItemQueryParameter itemQueryParameter, HttpServletRequest request, ModelMap modelMap){
		User user = (User)request.getSession().getAttribute("user");
		itemQueryParameter.setSessionKey(user.getSessionKey());	 
		Pagination<Item> page = new Pagination<>();
		try {
		     
			if(itemQueryParameter.getType() == 2){
				page = itemService.getOnsaleItemsByPage(itemQueryParameter);
			}else if(itemQueryParameter.getType() == 3){
				page = itemService.getInventoryItemsByPage(itemQueryParameter);
			}
			modelMap.put("itemPagination", page);
			modelMap.put("queryPara", itemQueryParameter);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage(), e);
		}
		
        return page;
	}
	
	@RequestMapping(value="/copyItem")
	public String prepareSearchItem(@RequestParam("itemid") long itemId,
			HttpServletRequest request, ModelMap modelMap) {
		User user = (User) request.getSession().getAttribute("user");

		String path = request.getSession().getServletContext().getRealPath("/")
				+ Constants.UPLOAD_PATH + "/";

		ItemParameter itemParameter = new ItemParameter();
		itemParameter.setItemId(itemId);
		itemParameter.setSessionKey(user.getSessionKey());
		try {
			ItemResult itemResult = itemService.copyItem(itemParameter, path);
			modelMap.put("itemResult", itemResult);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage(), e);
		}
		return "copy_item_n";
	}
	
	@RequestMapping(value="/ajaxCopyItem")
	public @ResponseBody ItemResult ajaxCopyItem(@RequestParam("itemurl") String itemUrl, HttpServletRequest request) {
		ItemResult itemResult = new ItemResult();
		try {
			Document doc = Jsoup.connect(itemUrl).get();
			String itemId = doc.select("input[name=item_id]").val();

			HttpSession session = request.getSession();
			User user = (User) session.getAttribute("user");
			String path = session.getServletContext().getRealPath("/")
					+ Constants.UPLOAD_PATH + "/";

			if (!StringUtil.isEmpty(itemId) && NumberUtils.isDigits(itemId)) {
				ItemParameter itemParameter = new ItemParameter();
				itemParameter.setItemId(Long.valueOf(itemId));
				itemParameter.setSessionKey(user.getSessionKey());
                itemResult = itemService.copyItem(itemParameter,path);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return itemResult;
	}
	
	@RequestMapping(value="/searchHotItem")
	public String searchHotItem(@ModelAttribute("itemName") String itemName, HttpServletRequest request, ModelMap modelMap) {
		try {
			if(StringUtils.isNotEmpty(itemName)){
				List<Item> hotItemList = itemService.searchHotItem(itemName);
				modelMap.put("hotItemSearchList", hotItemList);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage(), e);
		}
        return "hot_item_srs";
	}

	
	@RequestMapping(value="/prepareCopyItem")
	public String prepareSearchItem() {
		return "copy_item_n";
	}
	
	@RequestMapping(value="/prepareHotItem")
	public String prepareHotItem(){
		return "hot_item_n";
	}

}
