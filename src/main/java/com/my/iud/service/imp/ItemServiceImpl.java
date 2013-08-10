package com.my.iud.service.imp;

import com.my.iud.comparator.ItemListingComparator;
import com.my.iud.dao.ItemRepository;
import com.my.iud.dao.LoggerDAO;
import com.my.iud.dao.SkuRepository;
import com.my.iud.dao.TaoBaoItemDAO;
import com.my.iud.dao.UserRepository;
import com.my.iud.dto.ItemFormBean;
import com.my.iud.dto.ItemParameter;
import com.my.iud.dto.ItemQueryParameter;
import com.my.iud.dto.ItemResult;
import com.my.iud.dto.LogFormBean;
import com.my.iud.dto.SkuParameter;
import com.my.iud.dto.UserParameter;
import com.my.iud.dto.UserResult;
import com.my.iud.entity.GLog;
import com.my.iud.entity.Log;
import com.my.iud.service.ItemService;
import com.my.iud.util.Constants;
import com.my.iud.util.NumberUtil;
import com.my.iud.util.Pagination;
import com.my.iud.util.Util;
import com.taobao.api.ApiException;
import com.taobao.api.domain.Item;
import com.taobao.api.domain.Location;
import com.taobao.api.domain.Sku;
import com.taobao.api.response.ItemGetResponse;
import com.taobao.api.response.ItemRecommendAddResponse;
import com.taobao.api.response.ItemRecommendDeleteResponse;
import com.taobao.api.response.ItemUpdateDelistingResponse;
import com.taobao.api.response.ItemUpdateListingResponse;
import com.taobao.api.response.ItemsInventoryGetResponse;
import com.taobao.api.response.ItemsListGetResponse;
import com.taobao.api.response.ItemsOnsaleGetResponse;
import com.taobao.api.response.ShopRemainshowcaseGetResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("itemService")
public class ItemServiceImpl implements ItemService {

    private final static Logger logging = LoggerFactory
            .getLogger(ItemServiceImpl.class);
    @Autowired
    private TaoBaoItemDAO taoBaoItemDAO;
    @Autowired
    private LoggerDAO loggerDAO;
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SkuRepository skuRepository;

    @Override
    public long getOnsaleItemsCount(ItemQueryParameter parameter)
            throws Exception {
        // TODO Auto-generated method stub
        ItemsOnsaleGetResponse itemsOnsaleGetResponse = taoBaoItemDAO
                .getOnsaleItemsCount(parameter);
        if (itemsOnsaleGetResponse.isSuccess()) {
            return itemsOnsaleGetResponse.getTotalResults();
        }
        return 0L;
    }

    @Override
    public Pagination<Item> getOnsaleItemsByPage(ItemQueryParameter parameter)
            throws Exception {
        ItemsOnsaleGetResponse itemsOnsaleGetResponse = taoBaoItemDAO
                .getOnsaleItemsCount(parameter);
        if (itemsOnsaleGetResponse.isSuccess()) {
            long rowsCount = itemsOnsaleGetResponse.getTotalResults();
            Pagination<Item> pagination = new Pagination<>(
                    parameter.getCurrentPage(), parameter.getPageSize(),
                    rowsCount);
            itemsOnsaleGetResponse = taoBaoItemDAO
                    .getOnsaleItemsByPage(parameter);
            if (itemsOnsaleGetResponse.isSuccess()) {
                List<Item> itemList = itemsOnsaleGetResponse.getItems();
                pagination.setList(itemList);
            }
            return pagination;
        }
        return null;
    }

    @Override
    public long getInventoryItemsCount(ItemQueryParameter parameter)
            throws Exception {
        // TODO Auto-generated method stub
        ItemsInventoryGetResponse itemsInventoryGetResponse = taoBaoItemDAO
                .getInventoryItemsCount(parameter);
        if (itemsInventoryGetResponse.isSuccess()) {
            return itemsInventoryGetResponse.getTotalResults();
        }
        return 0;
    }

    @Override
    public Pagination<Item> getInventoryItemsByPage(ItemQueryParameter parameter)
            throws Exception {
        // TODO Auto-generated method stub
        ItemsInventoryGetResponse itemsInventoryGetResponse = taoBaoItemDAO
                .getInventoryItemsCount(parameter);
        if (itemsInventoryGetResponse.isSuccess()) {
            long rowsCount = itemsInventoryGetResponse.getTotalResults();
            Pagination<Item> pagination = new Pagination<>(
                    parameter.getCurrentPage(), parameter.getPageSize(),
                    rowsCount);
            itemsInventoryGetResponse = taoBaoItemDAO
                    .getInventoryItemsByPage(parameter);
            if (itemsInventoryGetResponse.isSuccess()) {
                List<Item> itemList = itemsInventoryGetResponse.getItems();
                pagination.setList(itemList);
            }
            return pagination;
        }
        return null;
    }

    @Override
    public void itemUpdateDelisting(ItemFormBean itemFormBean) throws Exception {
        String[] itemsArray = itemFormBean.getItemIds();
        List<Log> loggerList = new ArrayList<>();
        String itemName = "";
        ItemGetResponse itemGetResponse = null;
        ItemUpdateDelistingResponse itemUpdateDelistingResponse = null;
        ItemQueryParameter itemQueryParameter = new ItemQueryParameter();
        itemQueryParameter.setSellerId(itemFormBean.getSellerId());
        itemQueryParameter.setSessionKey(itemFormBean.getSessionKey());

        for (String itemid : itemsArray) {
            itemFormBean.setItemId(Long.valueOf(itemid));
            itemQueryParameter.setItemId(Long.valueOf(itemid));
            try {
                itemGetResponse = taoBaoItemDAO.getItem(itemQueryParameter);
                if (itemGetResponse.isSuccess()) {
                    Item item = itemGetResponse.getItem();
                    itemName = item.getTitle();
                    String approve = item.getApproveStatus();
                    if ("onsale".equals(approve)) {
                        itemUpdateDelistingResponse = taoBaoItemDAO
                                .itemUpdateDelisting(itemFormBean);
                    } else {
                        Date time = Calendar.getInstance().getTime();
                        Log logger = new Log();
                        logger.setItemId(itemFormBean.getItemId());
                        logger.setItemName(itemName);
                        logger.setStatus(2);
                        logger.setTaskId(itemFormBean.getTaskId());
                        logger.setResultinfo("下架失败(<font color='red'>此商品正在库存中，无法下架</font>)");
                        logger.setSellerId(itemFormBean.getSellerId());
                        logger.setTaskId(itemFormBean.getTaskId());
                        logger.setCreateTime(time);
                        logger.setModifyTime(time);
                        loggerList.add(logger);
                    }
                } else {
                    continue;
                }
            } catch (ApiException e) {
                // TODO Auto-generated catch block
                logging.error(e.getMessage(), e);
                continue;
            }
            if (itemUpdateDelistingResponse != null) {
                Log logger = new Log();
                Date time = Calendar.getInstance().getTime();
                if (itemUpdateDelistingResponse.isSuccess()) {
                    logger.setItemId(itemUpdateDelistingResponse.getItem()
                            .getNumIid());
                    logger.setItemName(itemName);
                    logger.setStatus(1);
                    logger.setTaskId(itemFormBean.getTaskId());
                    logger.setResultinfo("下架成功");
                    logger.setCreateTime(itemUpdateDelistingResponse.getItem()
                            .getModified());
                    logger.setSellerId(itemFormBean.getSellerId());
                    logger.setTaskId(itemFormBean.getTaskId());
                    logger.setModifyTime(time);
                    loggerList.add(logger);
                } else {
                    String subMsg = itemUpdateDelistingResponse.getSubMsg();
                    String msg = "";
                    if (subMsg == null) {
                        msg = itemUpdateDelistingResponse.getMsg();
                    } else {
                        msg = subMsg;
                    }
                    logger.setItemId(itemFormBean.getItemId());
                    logger.setItemName(itemName);
                    logger.setStatus(2);
                    logger.setTaskId(itemFormBean.getTaskId());
                    logger.setResultinfo("下架失败(<font color='red'>" + msg
                            + "</font>)");
                    logger.setSellerId(itemFormBean.getSellerId());
                    logger.setTaskId(itemFormBean.getTaskId());
                    logger.setCreateTime(time);
                    logger.setModifyTime(time);
                    loggerList.add(logger);
                }
            }

        }
        LogFormBean logFormBean = new LogFormBean();
        logFormBean.setSellerId(itemFormBean.getSellerId());
        logFormBean.setUid(itemFormBean.getUid());
        logFormBean.setSessionKey(itemFormBean.getSessionKey());
        logFormBean.setLoggerList(loggerList);
        loggerDAO.batchSaveLog(logFormBean);
    }

    @Override
    public void itemUpdateListing(ItemFormBean itemFormBean) throws Exception {
        // TODO Auto-generated method stub
        String[] itemsArray = itemFormBean.getItemIds();
        List<Log> loggerList = new ArrayList<>();
        ItemGetResponse itemGetResponse = null;
        ItemUpdateListingResponse itemUpdateListingResponse = null;
        Item item = null;

        for (String itemid : itemsArray) {
            ItemQueryParameter itemQueryParameter = new ItemQueryParameter();
            itemQueryParameter.setItemId(Long.valueOf(itemid));
            try {
                itemGetResponse = taoBaoItemDAO.getItem(itemQueryParameter);
            } catch (ApiException e) {
                // TODO Auto-generated catch block
                logging.error(e.getMessage(), e);
                continue;
            }
            if (itemGetResponse.isSuccess()) {
                item = itemGetResponse.getItem();
                if (item.getNum() == 0) {
                    continue;
                }
                itemFormBean.setItemNum(item.getNum());
            } else {
                continue;
            }

            itemFormBean.setItemId(Long.valueOf(itemid));
            try {
                itemUpdateListingResponse = taoBaoItemDAO
                        .itemUpdateListing(itemFormBean);
            } catch (ApiException e) {
                // TODO Auto-generated catch block
                logging.error(e.getMessage(), e);
                continue;
            }
            Log logger = new Log();
            Date time = Calendar.getInstance().getTime();
            if (itemUpdateListingResponse.isSuccess()) {
                logger.setItemId(itemUpdateListingResponse.getItem()
                        .getNumIid());
                logger.setItemName(item.getTitle());
                logger.setStatus(1);
                logger.setTaskId(itemFormBean.getTaskId());
                logger.setResultinfo("上架成功");
                logger.setCreateTime(itemUpdateListingResponse.getItem()
                        .getModified());
                logger.setSellerId(itemFormBean.getSellerId());
                logger.setTaskId(itemFormBean.getTaskId());
                logger.setModifyTime(time);
                loggerList.add(logger);
            } else {
                String subMsg = itemUpdateListingResponse.getSubMsg();
                String msg = "";
                if (subMsg == null) {
                    msg = itemUpdateListingResponse.getMsg();
                } else {
                    msg = subMsg;
                }
                logger.setItemId(itemFormBean.getItemId());
                logger.setItemName(item.getTitle());
                logger.setStatus(2);
                logger.setTaskId(itemFormBean.getTaskId());
                logger.setResultinfo("上架失败(<font color='red'>" + msg
                        + "</font>)");
                logger.setSellerId(itemFormBean.getSellerId());
                logger.setTaskId(itemFormBean.getTaskId());
                logger.setCreateTime(time);
                logger.setModifyTime(time);
                loggerList.add(logger);
                logging.error(itemUpdateListingResponse.getBody());
            }
        }
        LogFormBean logFormBean = new LogFormBean();
        logFormBean.setSellerId(itemFormBean.getSellerId());
        logFormBean.setUid(itemFormBean.getUid());
        logFormBean.setSessionKey(itemFormBean.getSessionKey());
        logFormBean.setLoggerList(loggerList);
        loggerDAO.batchSaveLog(logFormBean);
    }

    @Override
    public void onsaleItemsDelisting(ItemFormBean itemFormBean) throws Exception {
        // TODO Auto-generated method stub
        ItemQueryParameter itemQueryParameter = new ItemQueryParameter();
        itemQueryParameter.setSessionKey(itemFormBean.getSessionKey());
        List<Item> itemList = new ArrayList<>();
        List<Log> loggerList = new ArrayList<>();
        ItemUpdateDelistingResponse itemUpdateDelistingResponse = null;
        String msg = "";

        try {
            ItemsOnsaleGetResponse itemsOnsaleGetResponse = taoBaoItemDAO
                    .getOnsaleItemsCount(itemQueryParameter);
            if (itemsOnsaleGetResponse.isSuccess()) {
                long totalItems = itemsOnsaleGetResponse.getTotalResults();
                int pageSize = 200;
                int page = (int) (totalItems % pageSize == 0 ? totalItems
                        / pageSize : totalItems / pageSize + 1);
                for (int i = 1; i <= page; i++) {
                    itemQueryParameter.setPageSize(pageSize);
                    itemQueryParameter.setCurrentPage(i);
                    ItemsOnsaleGetResponse titemsOnsaleGetResponse = taoBaoItemDAO
                            .getOnsaleItemsByPage(itemQueryParameter);
                    if (titemsOnsaleGetResponse.isSuccess()) {
                        itemList.addAll(titemsOnsaleGetResponse.getItems());
                    } else {
                        continue;
                    }
                }
            }
        } catch (ApiException e) {
            // TODO Auto-generated catch block
            logging.error(e.getMessage(), e);
        }

        for (Item item : itemList) {
            itemFormBean.setItemId(item.getNumIid());
            try {
                itemUpdateDelistingResponse = taoBaoItemDAO
                        .itemUpdateDelisting(itemFormBean);
            } catch (ApiException e) {
                // TODO Auto-generated catch block
                logging.error(e.getMessage(), e);
                continue;
            }
            Log logger = new Log();
            Date time = Calendar.getInstance().getTime();
            if (itemUpdateDelistingResponse.isSuccess()) {
                logger.setItemId(itemUpdateDelistingResponse.getItem()
                        .getNumIid());
                logger.setItemName(item.getTitle());
                logger.setStatus(1);
                logger.setTaskId(itemFormBean.getTaskId());
                logger.setResultinfo("下架成功");
                logger.setSellerId(itemFormBean.getSellerId());
                logger.setTaskId(itemFormBean.getTaskId());
                logger.setCreateTime(itemUpdateDelistingResponse.getItem()
                        .getModified());
                logger.setModifyTime(time);
                loggerList.add(logger);
            } else {
                String subMsg = itemUpdateDelistingResponse.getSubMsg();
                if (subMsg == null) {
                    msg = itemUpdateDelistingResponse.getMsg();
                } else {
                    msg = subMsg;
                }
                logger.setItemId(itemFormBean.getItemId());
                logger.setItemName(item.getTitle());
                logger.setStatus(2);
                logger.setTaskId(itemFormBean.getTaskId());
                logger.setSellerId(itemFormBean.getSellerId());
                logger.setTaskId(itemFormBean.getTaskId());
                logger.setResultinfo("下架失败(<font color='red'>" + msg
                        + "</font>)");
                logger.setCreateTime(time);
                logger.setModifyTime(time);
                loggerList.add(logger);
            }
        }
        LogFormBean logFormBean = new LogFormBean();
        logFormBean.setSellerId(itemFormBean.getSellerId());
        logFormBean.setUid(itemFormBean.getUid());
        logFormBean.setSessionKey(itemFormBean.getSessionKey());
        logFormBean.setLoggerList(loggerList);
        loggerDAO.batchSaveLog(logFormBean);
    }

    @Override
    public void onInventoryItemsListing(ItemFormBean itemFormBean) throws Exception {
        // TODO Auto-generated method stub
        // TODO Auto-generated method stub
        ItemQueryParameter itemQueryParameter = new ItemQueryParameter();
        itemQueryParameter.setSessionKey(itemFormBean.getSessionKey());
        List<Item> itemList = new ArrayList<>();
        List<Log> loggerList = new ArrayList<>();
        ItemGetResponse itemGetResponse = null;
        ItemUpdateListingResponse itemUpdateListingResponse = null;

        try {
            ItemsInventoryGetResponse itemsInventoryGetResponse = taoBaoItemDAO
                    .getInventoryItemsCount(itemQueryParameter);
            if (itemsInventoryGetResponse.isSuccess()) {
                long totalItems = itemsInventoryGetResponse.getTotalResults();
                int pageSize = 200;
                int page = (int) (totalItems % pageSize == 0 ? totalItems
                        / pageSize : totalItems / pageSize + 1);
                for (int i = 1; i <= page; i++) {
                    itemQueryParameter.setPageSize(pageSize);
                    itemQueryParameter.setCurrentPage(i);
                    ItemsInventoryGetResponse iitemsInventoryGetResponse = taoBaoItemDAO
                            .getInventoryItemsByPage(itemQueryParameter);
                    if (iitemsInventoryGetResponse.isSuccess()) {
                        itemList.addAll(iitemsInventoryGetResponse.getItems());
                    } else {
                        continue;
                    }
                }
            }
        } catch (ApiException e) {
            // TODO Auto-generated catch block
            logging.error(e.getMessage(), e);
        }

        itemQueryParameter = new ItemQueryParameter();
        itemQueryParameter.setSessionKey(itemFormBean.getSessionKey());
        for (Item item : itemList) {
            itemQueryParameter.setItemId(item.getNumIid());
            try {
                itemGetResponse = taoBaoItemDAO.getItem(itemQueryParameter);
            } catch (ApiException e) {
                // TODO Auto-generated catch block
                logging.error(e.getMessage(), e);
                continue;
            }
            if (itemGetResponse.isSuccess()) {
                item = itemGetResponse.getItem();
                if (item.getNum() == 0) {
                    continue;
                }
                itemFormBean.setItemNum(item.getNum());
            } else {
                continue;
            }

            itemFormBean.setItemId(Long.valueOf(item.getNumIid()));

            try {
                itemUpdateListingResponse = taoBaoItemDAO
                        .itemUpdateListing(itemFormBean);
            } catch (ApiException e) {
                // TODO Auto-generated catch block
                logging.error(e.getMessage(), e);
                continue;
            }
            Log logger = new Log();
            Date time = Calendar.getInstance().getTime();
            if (itemUpdateListingResponse.isSuccess()) {
                logger.setItemId(itemUpdateListingResponse.getItem()
                        .getNumIid());
                logger.setItemName(item.getTitle());
                logger.setStatus(1);
                logger.setTaskId(itemFormBean.getTaskId());
                logger.setResultinfo("上架成功");
                logger.setSellerId(itemFormBean.getSellerId());
                logger.setTaskId(itemFormBean.getTaskId());
                logger.setCreateTime(itemUpdateListingResponse.getItem()
                        .getModified());
                logger.setModifyTime(time);
                loggerList.add(logger);
            } else {
                String subMsg = itemUpdateListingResponse.getSubMsg();
                String msg = "";
                if (subMsg == null) {
                    msg = itemUpdateListingResponse.getMsg();
                } else {
                    msg = subMsg;
                }
                logger.setItemId(itemFormBean.getItemId());
                logger.setItemName(item.getTitle());
                logger.setStatus(2);
                logger.setTaskId(itemFormBean.getTaskId());
                logger.setResultinfo("上架失败(<font color='red'>" + msg
                        + "</font>)");
                logger.setSellerId(itemFormBean.getSellerId());
                logger.setTaskId(itemFormBean.getTaskId());
                logger.setCreateTime(time);
                logger.setModifyTime(time);
                loggerList.add(logger);
            }
        }
        LogFormBean logFormBean = new LogFormBean();
        logFormBean.setSellerId(itemFormBean.getSellerId());
        logFormBean.setUid(itemFormBean.getUid());
        logFormBean.setSessionKey(itemFormBean.getSessionKey());
        logFormBean.setLoggerList(loggerList);
        loggerDAO.batchSaveLog(logFormBean);
    }

    @Override
    public ItemGetResponse getItem(ItemQueryParameter parameter)
            throws Exception {
        // TODO Auto-generated method stub
        return taoBaoItemDAO.getItem(parameter);
    }

    @Override
    public List<Item> getBatchItem(ItemQueryParameter parameter)
            throws Exception {
        // TODO Auto-generated method stub
        List<Item> itemList = new ArrayList<>();
        String itemids = parameter.getItemIdArray();
        String[] itemidArray = itemids.split(":");
        int length = itemidArray.length;
        int pnum = length % 20 == 0 ? length / 20 : length / 20 + 1;

        for (int i = 1; i <= pnum; i++) {
            String queryItems = "";
            for (int j = (i - 1) * 20; j < i * 20; j++) {
                if (j > length - 1) {
                    break;
                }
                queryItems = queryItems + itemidArray[j] + ",";
            }
            queryItems = queryItems.substring(0, queryItems.length() - 1);
            parameter.setItemIdArray(queryItems);
            ItemsListGetResponse itemsListGetResponse = taoBaoItemDAO
                    .getBatchItem(parameter);
            if (itemsListGetResponse.isSuccess()) {
                if (itemsListGetResponse.getItems() != null) {
                    itemList.addAll(itemsListGetResponse.getItems());
                }

            }
        }
        return itemList;
    }

    @Override
    public void onsaleItemsUpdateListing(ItemFormBean itemFormBean) throws Exception {
        // TODO Auto-generated method stub
        List<Log> loggerList = new ArrayList<>();
        List<Item> itemList = new ArrayList<>();
        ItemGetResponse itemGetResponse = null;
        ItemUpdateListingResponse itemUpdateListingResponse = null;
        ItemQueryParameter itemQueryParameter = new ItemQueryParameter();
        itemQueryParameter.setSessionKey(itemFormBean.getSessionKey());

        try {
            ItemsOnsaleGetResponse itemsOnsaleGetResponse = taoBaoItemDAO
                    .getOnsaleItemsCount(itemQueryParameter);
            if (itemsOnsaleGetResponse.isSuccess()) {
                long totalItems = itemsOnsaleGetResponse.getTotalResults();
                int pageSize = 200;
                int page = (int) (totalItems % pageSize == 0 ? totalItems
                        / pageSize : totalItems / pageSize + 1);
                for (int i = 1; i <= page; i++) {
                    itemQueryParameter.setPageSize(pageSize);
                    itemQueryParameter.setCurrentPage(i);
                    itemsOnsaleGetResponse = taoBaoItemDAO
                            .getOnsaleItemsByPage(itemQueryParameter);
                    if (itemsOnsaleGetResponse.isSuccess()) {
                        if (itemsOnsaleGetResponse.getItems() != null) {
                            itemList.addAll(itemsOnsaleGetResponse.getItems());
                        }
                    } else {
                        continue;
                    }
                }
            }
        } catch (ApiException e) {
            // TODO Auto-generated catch block
            logging.error(e.getMessage(), e);
        }

        itemQueryParameter = new ItemQueryParameter();
        itemQueryParameter.setSessionKey(itemFormBean.getSessionKey());
        for (Item item : itemList) {
            itemQueryParameter.setItemId(item.getNumIid());
            try {
                itemGetResponse = taoBaoItemDAO.getItem(itemQueryParameter);
            } catch (ApiException e) {
                // TODO Auto-generated catch block
                logging.error(e.getMessage(), e);
                continue;
            }
            if (itemGetResponse.isSuccess()) {
                item = itemGetResponse.getItem();
                if (item.getNum() == 0) {
                    continue;
                }
                itemFormBean.setItemNum(item.getNum());
            } else {
                continue;
            }

            itemFormBean.setItemId(Long.valueOf(item.getNumIid()));

            try {
                itemUpdateListingResponse = taoBaoItemDAO
                        .itemUpdateListing(itemFormBean);
            } catch (ApiException e) {
                // TODO Auto-generated catch block
                logging.error(e.getMessage(), e);
                continue;
            }
            Log logger = new Log();
            Date time = Calendar.getInstance().getTime();
            if (itemUpdateListingResponse.isSuccess()) {
                logger.setItemId(itemUpdateListingResponse.getItem()
                        .getNumIid());
                logger.setItemName(item.getTitle());
                logger.setStatus(1);
                logger.setTaskId(itemFormBean.getTaskId());
                logger.setResultinfo("上架成功");
                logger.setSellerId(itemFormBean.getSellerId());
                logger.setTaskId(itemFormBean.getTaskId());
                logger.setCreateTime(itemUpdateListingResponse.getItem()
                        .getModified());
                logger.setModifyTime(time);
                loggerList.add(logger);
            } else {
                String subMsg = itemUpdateListingResponse.getSubMsg();
                String msg = "";
                if (subMsg == null) {
                    msg = itemUpdateListingResponse.getMsg();
                } else {
                    msg = subMsg;
                }
                logger.setItemId(itemFormBean.getItemId());
                logger.setItemName(item.getTitle());
                logger.setStatus(2);
                logger.setTaskId(itemFormBean.getTaskId());
                logger.setResultinfo("上架失败(<font color='red'>" + msg
                        + "</font>)");
                logger.setSellerId(itemFormBean.getSellerId());
                logger.setTaskId(itemFormBean.getTaskId());
                logger.setCreateTime(time);
                logger.setModifyTime(time);
                loggerList.add(logger);
            }
        }
        LogFormBean logFormBean = new LogFormBean();
        logFormBean.setSellerId(itemFormBean.getSellerId());
        logFormBean.setUid(itemFormBean.getUid());
        logFormBean.setSessionKey(itemFormBean.getSessionKey());
        logFormBean.setLoggerList(loggerList);
        loggerDAO.batchSaveLog(logFormBean);

    }

    @Override
    public ItemResult copyItem(ItemParameter itemParameter, String path)
            throws Exception {// TODO Auto-generated method stub
        ItemResult itemResult = itemRepository.getItemById(
                itemParameter.getItemId(), itemParameter.getSessionKey());
        if (itemResult.isSucess()) {
            Item item = itemResult.getItem();
            String filePath = null;
            /**
             * 保存宝贝主图 *
             */
            String picUrl = item.getPicUrl();
            try {
                if (StringUtils.isNotEmpty(picUrl)) {
                    filePath = Util.getImg(path, picUrl);
                }
            } catch (IOException e) {
                logging.error(e.getMessage());
            }

            /**
             * 过滤image标签 *
             */
            String desc = item.getDesc();
            item.setDesc(Util.filterImageTag(desc));

            /**
             * 设置发货地址 *
             */
            UserParameter userParameter = new UserParameter();
            userParameter.setSessionKey(itemParameter.getSessionKey());
            UserResult userResult = userRepository
                    .getUserLocation(userParameter);
            if (userResult.isSucess()) {
                Location location = userResult.getUser().getLocation();
                if (location != null) {
                    item.getLocation().setCity(location.getCity());
                    item.getLocation().setState(location.getState());
                }
            }

            /**
             * 新增宝贝 *
             */
            itemParameter.setItem(item);
            itemResult = itemRepository.addItem(itemParameter, filePath);
            if (itemResult.isSucess()) {
                List<Sku> skuList = item.getSkus();
                if (skuList != null) {
                    Item itemReturn = itemResult.getItem();

                    SkuParameter skuParameter = new SkuParameter();
                    skuParameter.setItemId(itemReturn.getNumIid());
                    skuParameter.setSkuList(skuList);
                    skuParameter.setSessionKey(itemParameter.getSessionKey());

                    skuRepository.addSku(skuParameter);
                }
            } else {
                logging.error(itemResult.getSubMsg());
            }
        }
        return itemResult;
    }

    @Override
    public List<Item> searchHotItem(String keyWord) throws Exception {
        // TODO Auto-generated method stub
        List<Item> itemList = new ArrayList<>();
        Document doc = Jsoup.connect(Constants.HOT_SELL_SEARCH_URL + "?keyword=" + URLEncoder.encode(keyWord, "gbk")).get();
        Elements itemElement = doc.select("ul.list-view-enlarge > li");
        for (Element element : itemElement) {
            String itemName = element.select("h3.summary > a").text();
            Long monthSellerNum = NumberUtil.getLongFromString(element.select("ul.attribute > li > span > em").text());
            String price = element.select("li.price > em").text();
            String photoUrl = element.select("div.photo > a > img").attr("src");
            String itemUrl = element.select("div.photo > a").attr("href");

            Item item = new Item();
            item.setPrice(price);
            item.setNick(itemName);
            item.setPicUrl(photoUrl);
            item.setTitle(itemUrl);
            item.setNum(Long.valueOf(monthSellerNum));

            itemList.add(item);
        }
        return itemList;
    }

    @Override
    public void sItemRecommend(ItemFormBean itemFormBean) throws Exception{
        // TODO Auto-generated method stub
        String itemIds[] = itemFormBean.getItemIds();
        List<GLog> loggerList = new ArrayList<>();
        ItemQueryParameter iqp = new ItemQueryParameter();
        iqp.setSessionKey(itemFormBean.getSessionKey());
        Date time = Calendar.getInstance().getTime();

        ShopRemainshowcaseGetResponse srsr = taoBaoItemDAO.getShowcaseNum(itemFormBean.getSessionKey());
        long k = 0;
        if (srsr.isSuccess()) {
            long remainCount = srsr.getShop().getRemainCount();
            long totalCount = srsr.getShop().getAllCount();
            if (totalCount != -1 && itemIds.length > remainCount) {
                iqp.setCurrentPage(1);
                iqp.setHasShowcase("true");
                iqp.setPageSize(200);
                ItemsOnsaleGetResponse iogr = taoBaoItemDAO.getOnsaleItemsByPage(iqp);
                if (iogr.isSuccess()) {
                    List<Item> itemDelistingList = new ArrayList<>();
                    List<Item> ritemList = iogr.getItems();
                    Arrays.sort(itemIds);
                    long delistingCount = itemIds.length - remainCount;
                    if (delistingCount > totalCount) {
                        itemDelistingList = ritemList;
                        k = totalCount;
                    } else {
                        itemDelistingList = ritemList.subList(0, (int) (delistingCount - 1));
                    }
                    ItemQueryParameter iqprd = new ItemQueryParameter();
                    iqprd.setSessionKey(itemFormBean.getSessionKey());
                    for (Item showcaseItem : itemDelistingList) {
                        iqprd.setItemId(showcaseItem.getNumIid());
                        taoBaoItemDAO.itemRecommendDelete(iqprd);
                    }
                }
            }
        }
        int idx = 1;
        for (String ids : itemIds) {
            if (k != 0 && k > idx) {
                break;
            }
            iqp.setItemId(Long.valueOf(ids));
            ItemRecommendAddResponse irar = taoBaoItemDAO.itemRecommend(iqp);
            String itemName = "";
            ItemGetResponse itemGetResponse = taoBaoItemDAO.getItem(iqp);
            if (itemGetResponse.isSuccess()) {
                itemName = itemGetResponse.getItem().getTitle();
            } else {
                itemName = "";
            }
            if (irar.isSuccess()) {
                GLog glog = new GLog();
                glog.setItemId(irar.getItem().getNumIid());
                glog.setItemName(itemName);
                glog.setStatus(1);
                glog.setTaskId(itemFormBean.getTaskId());
                glog.setResultinfo("推荐成功");
                glog.setCreateTime(irar.getItem().getModified());
                glog.setSellerId(itemFormBean.getSellerId());
                glog.setTaskId(itemFormBean.getTaskId());
                glog.setModifyTime(time);
                loggerList.add(glog);
            } else {
                GLog glog = new GLog();
                String subMsg = irar.getSubMsg();
                String msg = "";
                if (subMsg == null) {
                    msg = irar.getMsg();
                } else {
                    msg = subMsg;
                }
                glog.setItemId(itemFormBean.getItemId());
                glog.setItemName(itemName);
                glog.setStatus(2);
                glog.setTaskId(itemFormBean.getTaskId());
                glog.setResultinfo("推荐失败(<font color='red'>" + msg
                        + "</font>)");
                glog.setSellerId(itemFormBean.getSellerId());
                glog.setTaskId(itemFormBean.getTaskId());
                glog.setCreateTime(time);
                glog.setModifyTime(time);
                loggerList.add(glog);
            }
            idx++;
        }

        LogFormBean logFormBean = new LogFormBean();
        logFormBean.setSellerId(itemFormBean.getSellerId());
        logFormBean.setUid(itemFormBean.getUid());
        logFormBean.setSessionKey(itemFormBean.getSessionKey());
        logFormBean.setgLoggerList(loggerList);
        loggerDAO.batchSaveGLog(logFormBean);
    }

    @Override
    public void sItemRecommendDelete(ItemFormBean itemFormBean)
            throws Exception {
        // TODO Auto-generated method stub
        String itemIds[] = itemFormBean.getItemIds();
        List<GLog> loggerList = new ArrayList<>();
        ItemQueryParameter iqp = new ItemQueryParameter();
        iqp.setSessionKey(itemFormBean.getSessionKey());
        Date time = Calendar.getInstance().getTime();
        for (String ids : itemIds) {
            iqp.setItemId(Long.valueOf(ids));
            ItemRecommendDeleteResponse irdr = taoBaoItemDAO.itemRecommendDelete(iqp);
            String itemName = "";
            ItemGetResponse itemGetResponse = taoBaoItemDAO.getItem(iqp);
            if (itemGetResponse.isSuccess()) {
                itemName = itemGetResponse.getItem().getTitle();
            } else {
                itemName = "";
            }
            if (irdr.isSuccess()) {
                GLog glog = new GLog();
                glog.setItemId(irdr.getItem().getNumIid());
                glog.setItemName(itemName);
                glog.setStatus(1);
                glog.setTaskId(itemFormBean.getTaskId());
                glog.setResultinfo("取消推荐成功");
                glog.setCreateTime(irdr.getItem().getModified());
                glog.setSellerId(itemFormBean.getSellerId());
                glog.setTaskId(itemFormBean.getTaskId());
                glog.setModifyTime(time);
                loggerList.add(glog);
            } else {
                GLog glog = new GLog();
                String subMsg = irdr.getSubMsg();
                String msg = "";
                if (subMsg == null) {
                    msg = irdr.getMsg();
                } else {
                    msg = subMsg;
                }
                glog.setItemId(itemFormBean.getItemId());
                glog.setItemName(itemName);
                glog.setStatus(2);
                glog.setTaskId(itemFormBean.getTaskId());
                glog.setResultinfo("取消推荐失败(<font color='red'>" + msg
                        + "</font>)");
                glog.setSellerId(itemFormBean.getSellerId());
                glog.setTaskId(itemFormBean.getTaskId());
                glog.setCreateTime(time);
                glog.setModifyTime(time);
                loggerList.add(glog);
            }
        }

        LogFormBean logFormBean = new LogFormBean();
        logFormBean.setSellerId(itemFormBean.getSellerId());
        logFormBean.setUid(itemFormBean.getUid());
        logFormBean.setSessionKey(itemFormBean.getSessionKey());
        logFormBean.setgLoggerList(loggerList);
        loggerDAO.batchSaveGLog(logFormBean);
    }

    @Override
    public void onsaleItemsRecommend(ItemFormBean ifb) throws Exception {
        // TODO Auto-generated method stub
        List<Item> itemList = new ArrayList<>();
        ItemQueryParameter iqp = new ItemQueryParameter();
        List<GLog> loggerList = new ArrayList<>();
        iqp.setSessionKey(ifb.getSessionKey());

        try {
            ItemsOnsaleGetResponse itemsOnsaleGetResponse = taoBaoItemDAO.getOnsaleItemsCount(iqp);
            if (itemsOnsaleGetResponse.isSuccess()) {
                long totalItems = itemsOnsaleGetResponse.getTotalResults();
                int pageSize = 200;
                int page = (int) (totalItems % pageSize == 0 ? totalItems
                        / pageSize : totalItems / pageSize + 1);
                for (int i = 1; i <= page; i++) {
                    iqp.setPageSize(pageSize);
                    iqp.setCurrentPage(i);
                    ItemsOnsaleGetResponse titemsOnsaleGetResponse = taoBaoItemDAO.getOnsaleItemsByPage(iqp);
                    if (titemsOnsaleGetResponse.isSuccess()) {
                        itemList.addAll(titemsOnsaleGetResponse.getItems());
                    } else {
                        continue;
                    }
                }
            }
        } catch (ApiException e) {
            // TODO Auto-generated catch block
            logging.error(e.getMessage(), e);
        }

        Date time = Calendar.getInstance().getTime();
        ShopRemainshowcaseGetResponse srsr = taoBaoItemDAO.getShowcaseNum(ifb.getSessionKey());
        if (srsr.isSuccess()) {
            long remainCount = srsr.getShop().getRemainCount();
            long totalCount = srsr.getShop().getAllCount();
            if (totalCount != -1 && itemList.size() > remainCount) {
                iqp.setCurrentPage(1);
                iqp.setHasShowcase("true");
                iqp.setPageSize(200);
                ItemsOnsaleGetResponse iogr = taoBaoItemDAO.getOnsaleItemsByPage(iqp);
                if (iogr.isSuccess() && iogr.getItems() != null) {
                    List<Item> itemDelistingList = new ArrayList<>();
                    List<Item> ritemList = iogr.getItems();
                    Collections.sort(ritemList, new ItemListingComparator());
                    long delistingCount = itemList.size() - remainCount;
                    if (delistingCount > totalCount) {
                        itemDelistingList = ritemList;
                        itemList = itemList.subList(0, (int) (totalCount - 1));
                    } else {
                        itemDelistingList = ritemList.subList(0, (int) delistingCount - 1);
                    }
                    ItemQueryParameter iqprd = new ItemQueryParameter();
                    iqprd.setSessionKey(ifb.getSessionKey());
                    for (Item showcaseItem : itemDelistingList) {
                        iqprd.setItemId(showcaseItem.getNumIid());
                        taoBaoItemDAO.itemRecommendDelete(iqprd);
                    }
                }
            }
        }
        for (Item item : itemList) {
            iqp.setItemId(item.getNumIid());
            ItemRecommendAddResponse irar = taoBaoItemDAO.itemRecommend(iqp);
            String itemName = "";
            ItemGetResponse itemGetResponse = taoBaoItemDAO.getItem(iqp);
            if (itemGetResponse.isSuccess()) {
                itemName = itemGetResponse.getItem().getTitle();
            } else {
                itemName = "";
            }
            if (irar.isSuccess()) {
                GLog glog = new GLog();
                glog.setItemId(irar.getItem().getNumIid());
                glog.setItemName(itemName);
                glog.setStatus(1);
                glog.setTaskId(ifb.getTaskId());
                glog.setResultinfo("推荐成功");
                glog.setCreateTime(irar.getItem().getModified());
                glog.setSellerId(ifb.getSellerId());
                glog.setTaskId(ifb.getTaskId());
                glog.setModifyTime(time);
                loggerList.add(glog);
            } else {
                GLog glog = new GLog();
                String subMsg = irar.getSubMsg();
                String msg = "";
                if (subMsg == null) {
                    msg = irar.getMsg();
                } else {
                    msg = subMsg;
                }
                glog.setItemId(ifb.getItemId());
                glog.setItemName(itemName);
                glog.setStatus(2);
                glog.setTaskId(ifb.getTaskId());
                glog.setResultinfo("推荐失败(<font color='red'>" + msg
                        + "</font>)");
                glog.setSellerId(ifb.getSellerId());
                glog.setTaskId(ifb.getTaskId());
                glog.setCreateTime(time);
                glog.setModifyTime(time);
                loggerList.add(glog);
            }
        }
        LogFormBean logFormBean = new LogFormBean();
        logFormBean.setSellerId(ifb.getSellerId());
        logFormBean.setUid(ifb.getUid());
        logFormBean.setSessionKey(ifb.getSessionKey());
        logFormBean.setgLoggerList(loggerList);
        loggerDAO.batchSaveGLog(logFormBean);
    }

    @Override
    public void onsaleItemsRecommendDelete(ItemFormBean ifb) throws Exception {
        // TODO Auto-generated method stub

        // TODO Auto-generated method stub
        List<Item> itemList = new ArrayList<>();
        ItemQueryParameter iqp = new ItemQueryParameter();
        List<GLog> loggerList = new ArrayList<>();
        iqp.setSessionKey(ifb.getSessionKey());

        try {
            ItemsOnsaleGetResponse itemsOnsaleGetResponse = taoBaoItemDAO.getOnsaleItemsCount(iqp);
            if (itemsOnsaleGetResponse.isSuccess()) {
                long totalItems = itemsOnsaleGetResponse.getTotalResults();
                int pageSize = 200;
                int page = (int) (totalItems % pageSize == 0 ? totalItems
                        / pageSize : totalItems / pageSize + 1);
                for (int i = 1; i <= page; i++) {
                    iqp.setPageSize(pageSize);
                    iqp.setCurrentPage(i);
                    ItemsOnsaleGetResponse titemsOnsaleGetResponse = taoBaoItemDAO.getOnsaleItemsByPage(iqp);
                    if (titemsOnsaleGetResponse.isSuccess()) {
                        itemList.addAll(titemsOnsaleGetResponse.getItems());
                    } else {
                        continue;
                    }
                }
            }
        } catch (ApiException e) {
            // TODO Auto-generated catch block
            logging.error(e.getMessage(), e);
        }

        Date time = Calendar.getInstance().getTime();
        for (Item item : itemList) {
            iqp.setItemId(item.getNumIid());
            ItemRecommendDeleteResponse irdr = taoBaoItemDAO.itemRecommendDelete(iqp);
            String itemName = "";
            ItemGetResponse itemGetResponse = taoBaoItemDAO.getItem(iqp);
            if (itemGetResponse.isSuccess()) {
                itemName = itemGetResponse.getItem().getTitle();
            } else {
                itemName = "";
            }
            if (irdr.isSuccess()) {
                GLog glog = new GLog();
                glog.setItemId(irdr.getItem().getNumIid());
                glog.setItemName(itemName);
                glog.setStatus(1);
                glog.setTaskId(ifb.getTaskId());
                glog.setResultinfo("取消推荐成功");
                glog.setCreateTime(irdr.getItem().getModified());
                glog.setSellerId(ifb.getSellerId());
                glog.setTaskId(ifb.getTaskId());
                glog.setModifyTime(time);
                loggerList.add(glog);
            } else {
                GLog glog = new GLog();
                String subMsg = irdr.getSubMsg();
                String msg = "";
                if (subMsg == null) {
                    msg = irdr.getMsg();
                } else {
                    msg = subMsg;
                }
                glog.setItemId(ifb.getItemId());
                glog.setItemName(itemName);
                glog.setStatus(2);
                glog.setTaskId(ifb.getTaskId());
                glog.setResultinfo("取消推荐失败(<font color='red'>" + msg
                        + "</font>)");
                glog.setSellerId(ifb.getSellerId());
                glog.setTaskId(ifb.getTaskId());
                glog.setCreateTime(time);
                glog.setModifyTime(time);
                loggerList.add(glog);
            }
        }

        LogFormBean logFormBean = new LogFormBean();
        logFormBean.setSellerId(ifb.getSellerId());
        logFormBean.setUid(ifb.getUid());
        logFormBean.setSessionKey(ifb.getSessionKey());
        logFormBean.setgLoggerList(loggerList);
        loggerDAO.batchSaveGLog(logFormBean);

    }
}
