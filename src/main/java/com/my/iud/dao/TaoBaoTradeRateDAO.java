/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.my.iud.dao;

import com.my.iud.dto.TradeRateQueryParameter;
import com.taobao.api.ApiException;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.TraderatesGetRequest;
import com.taobao.api.response.TraderatesGetResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author dell
 */
@Repository("taoBaoTradeRateDAO")
public class TaoBaoTradeRateDAO {
    
        @Autowired
	private TaobaoClient taoBaoClient;
        
        public TraderatesGetResponse getTradeRate(TradeRateQueryParameter trqp) throws ApiException{
            TraderatesGetRequest req = new TraderatesGetRequest();
            req.setFields("tid,oid,role,nick,result,created,rated_nick,item_title,item_price,content,reply,num_iid");
            req.setRateType(trqp.getRateType());
            req.setRole(trqp.getRole());
            req.setResult(trqp.getResult());
            req.setPageNo(trqp.getCurrentPage());
            req.setPageSize(trqp.getPageSize());
            TraderatesGetResponse trar = taoBaoClient.execute(req, trqp.getSessionKey());
            return trar;
        }
      
}
