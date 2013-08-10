/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.my.iud.service.imp;

import com.my.iud.dao.TaoBaoTradeRateDAO;
import com.my.iud.dto.TradeRateQueryParameter;
import com.my.iud.service.TradeRateService;
import com.taobao.api.domain.TradeRate;
import com.taobao.api.response.TraderatesGetResponse;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author dell
 */
@Service("tradeRateService")
public class TradeRateServiceImpl implements TradeRateService{
    
    @Autowired
    private TaoBaoTradeRateDAO taoBaoTradeRateDAO;
    
    @Override
    public List<TradeRate> getTradeRate(TradeRateQueryParameter trqp) throws Exception {
       TraderatesGetResponse trgr = taoBaoTradeRateDAO.getTradeRate(trqp);
       List<TradeRate> tradeRateList = new ArrayList<>();
       if(trgr.isSuccess()){
          tradeRateList = trgr.getTradeRates();
       }
       return tradeRateList;
    }
    
}
