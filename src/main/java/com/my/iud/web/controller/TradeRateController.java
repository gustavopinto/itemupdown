/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.my.iud.web.controller;

import com.my.iud.dto.TradeRateQueryParameter;
import com.my.iud.entity.User;
import com.my.iud.service.TradeRateService;
import com.taobao.api.domain.TradeRate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author dell
 */
@Controller

public class TradeRateController {
        
        @Autowired
        private TradeRateService tradeRateService;
    
    	@RequestMapping(value="/getRemoteTradeRate",method=RequestMethod.GET)
	public @ResponseBody List<TradeRate> getItemCount(TradeRateQueryParameter trpq, HttpServletRequest request){
            List<TradeRate> tradeRateList = new ArrayList<>();
            
            User user = (User)request.getSession().getAttribute("user");
            trpq.setSessionKey(user.getSessionKey());

            try {
                tradeRateList = tradeRateService.getTradeRate(trpq);
                return tradeRateList;
            } catch (Exception ex) {
                Logger.getLogger(TradeRateController.class.getName()).log(Level.SEVERE, null, ex);
            }
            return tradeRateList;
        }
}
