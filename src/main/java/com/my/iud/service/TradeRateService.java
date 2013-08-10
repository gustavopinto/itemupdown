/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.my.iud.service;

import com.my.iud.dto.TradeRateQueryParameter;
import com.taobao.api.domain.TradeRate;
import java.util.List;

/**
 *
 * @author dell
 */
public interface TradeRateService {
    
      List<TradeRate> getTradeRate(TradeRateQueryParameter trqp) throws Exception;
}
