package com.my.iud.dao;

import com.my.iud.dto.ItemFormBean;
import com.my.iud.dto.ItemQueryParameter;
import com.taobao.api.ApiException;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.ItemGetRequest;
import com.taobao.api.request.ItemRecommendAddRequest;
import com.taobao.api.request.ItemRecommendDeleteRequest;
import com.taobao.api.request.ItemUpdateDelistingRequest;
import com.taobao.api.request.ItemUpdateListingRequest;
import com.taobao.api.request.ItemsInventoryGetRequest;
import com.taobao.api.request.ItemsListGetRequest;
import com.taobao.api.request.ItemsOnsaleGetRequest;
import com.taobao.api.request.ShopRemainshowcaseGetRequest;
import com.taobao.api.response.ItemGetResponse;
import com.taobao.api.response.ItemRecommendAddResponse;
import com.taobao.api.response.ItemRecommendDeleteResponse;
import com.taobao.api.response.ItemUpdateDelistingResponse;
import com.taobao.api.response.ItemUpdateListingResponse;
import com.taobao.api.response.ItemsInventoryGetResponse;
import com.taobao.api.response.ItemsListGetResponse;
import com.taobao.api.response.ItemsOnsaleGetResponse;
import com.taobao.api.response.ShopRemainshowcaseGetResponse;
import org.guzz.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("taoBaoItemDAO")
public class TaoBaoItemDAO {

    @Autowired
    private TaobaoClient taoBaoClient;

    public ItemsOnsaleGetResponse getOnsaleItemsCount(ItemQueryParameter parameter) throws ApiException {
        ItemsOnsaleGetRequest req = new ItemsOnsaleGetRequest();
        String fields = "num_iid";
        req.setFields(fields);
        req.setPageSize(1L);
        if (StringUtil.notEmpty(parameter.getTitle())) {
            req.setQ(parameter.getTitle());
        }
        if (parameter.getCid() != 0) {
            req.setCid(parameter.getCid());
        }
        
        if(StringUtil.notEmpty(parameter.getHasShowcase())){
            req.setHasShowcase(Boolean.valueOf(parameter.getHasShowcase()));
        }
        
        ItemsOnsaleGetResponse response = taoBaoClient.execute(req, parameter.getSessionKey());
        return response;
    }

    public ItemsOnsaleGetResponse getOnsaleItemsByPage(ItemQueryParameter parameter) throws ApiException {
        ItemsOnsaleGetRequest req = new ItemsOnsaleGetRequest();
        String fields = "num_iid,title,pic_url,title,price,delist_time";
        req.setFields(fields);
        if (StringUtil.notEmpty(parameter.getTitle())) {
            req.setQ(parameter.getTitle());
        }
        if (parameter.getCid() != 0) {
            req.setCid(parameter.getCid());
        }
        if(StringUtil.notEmpty(parameter.getHasShowcase())){
            req.setHasShowcase(Boolean.valueOf(parameter.getHasShowcase()));
        }
        req.setPageNo(parameter.getCurrentPage());
        req.setPageSize(parameter.getPageSize());
        ItemsOnsaleGetResponse response = taoBaoClient.execute(req, parameter.getSessionKey());
        return response;
    }

    public ItemsInventoryGetResponse getInventoryItemsCount(ItemQueryParameter parameter) throws ApiException {
        ItemsInventoryGetRequest req = new ItemsInventoryGetRequest();
        String fields = "num_iid";
        req.setFields(fields);
        req.setPageSize(1L);
        if (StringUtil.notEmpty(parameter.getTitle())) {
            req.setQ(parameter.getTitle());
        }
        if (parameter.getCid() != 0) {
            req.setCid(parameter.getCid());
        }
        ItemsInventoryGetResponse response = taoBaoClient.execute(req, parameter.getSessionKey());
        return response;
    }

    public ItemsInventoryGetResponse getInventoryItemsByPage(ItemQueryParameter parameter) throws ApiException {
        ItemsInventoryGetRequest req = new ItemsInventoryGetRequest();
        String fields = "num_iid,title,pic_url,title,price";
        req.setFields(fields);
        if (StringUtil.notEmpty(parameter.getTitle())) {
            req.setQ(parameter.getTitle());
        }
        if (parameter.getCid() != 0) {
            req.setCid(parameter.getCid());
        }
        req.setPageNo(parameter.getCurrentPage());
        req.setPageSize(parameter.getPageSize());
        ItemsInventoryGetResponse response = taoBaoClient.execute(req, parameter.getSessionKey());
        return response;
    }

    /**
     * 商品下架
     *
     * @param parameter
     * @throws ApiException
     */
    public ItemUpdateDelistingResponse itemUpdateDelisting(ItemFormBean itemFormBean) throws ApiException {
        ItemUpdateDelistingRequest req = new ItemUpdateDelistingRequest();
        req.setNumIid(itemFormBean.getItemId());
        ItemUpdateDelistingResponse response = taoBaoClient.execute(req, itemFormBean.getSessionKey());
        return response;
    }

    /**
     * 商品上架
     *
     * @param parameter
     * @return
     */
    public ItemUpdateListingResponse itemUpdateListing(ItemFormBean itemFormBean) throws ApiException {
        ItemUpdateListingRequest req = new ItemUpdateListingRequest();
        req.setNumIid(itemFormBean.getItemId());
        req.setNum(itemFormBean.getItemNum());
        ItemUpdateListingResponse response = taoBaoClient.execute(req, itemFormBean.getSessionKey());
        return response;
    }

    /**
     * 获取宝贝
     *
     * @param parameter
     * @return
     * @throws ApiException
     */
    public ItemGetResponse getItem(ItemQueryParameter parameter) throws ApiException {
        ItemGetRequest req = new ItemGetRequest();
        req.setFields("num_iid,title,num,approve_status");
        req.setNumIid(parameter.getItemId());
        ItemGetResponse response = taoBaoClient.execute(req, parameter.getSessionKey());
        return response;
    }

    /**
     * 批量获取宝贝
     *
     * @param parameter
     * @return
     * @throws ApiException
     */
    public ItemsListGetResponse getBatchItem(ItemQueryParameter parameter)
            throws ApiException {
        ItemsListGetRequest req = new ItemsListGetRequest();
        String fields = "num_iid,title,pic_url,title,price";
        req.setFields(fields);
        req.setNumIids(parameter.getItemIdArray());
        ItemsListGetResponse response = taoBaoClient.execute(req, parameter.getSessionKey());
        return response;
    }

    /**
     * 宝贝橱窗推荐
     *
     * @param parameter
     * @return
     * @throws ApiException
     */
    public ItemRecommendAddResponse itemRecommend(ItemQueryParameter parameter) throws ApiException {
        ItemRecommendAddRequest req = new ItemRecommendAddRequest();
        req.setNumIid(parameter.getItemId());
        ItemRecommendAddResponse irar = taoBaoClient.execute(req, parameter.getSessionKey());
        return irar;
    }

    /**
     * 取消宝贝橱窗推荐
     *
     * @param parameter
     * @return
     * @throws ApiException
     */
    public ItemRecommendDeleteResponse itemRecommendDelete(ItemQueryParameter parameter) throws ApiException {
        ItemRecommendDeleteRequest req = new ItemRecommendDeleteRequest();
        req.setNumIid(parameter.getItemId());
        ItemRecommendDeleteResponse irdr = taoBaoClient.execute(req, parameter.getSessionKey());
        return irdr;
    }

    /**
     * 获取橱窗的推荐数
     *
     * @param sessionKey
     * @return
     */
    public ShopRemainshowcaseGetResponse getShowcaseNum(String sessionKey) throws ApiException {
        ShopRemainshowcaseGetRequest req = new ShopRemainshowcaseGetRequest();
        ShopRemainshowcaseGetResponse srsc = taoBaoClient.execute(req, sessionKey);
        return srsc;
    }
}
