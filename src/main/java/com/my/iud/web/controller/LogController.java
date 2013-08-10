package com.my.iud.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.my.iud.dto.LogQueryParameter;
import com.my.iud.entity.GLog;
import com.my.iud.entity.Log;
import com.my.iud.entity.User;
import com.my.iud.service.LogService;
import com.my.iud.util.Pagination;

@Controller
@RequestMapping("/log")
public class LogController {
	
	private final static Logger logger = LoggerFactory.getLogger(LogController.class);

	@Autowired
	private LogService logService;
	
	@RequestMapping(value="/searchLog")
	public String searchLog(@ModelAttribute("parameter") LogQueryParameter parameter,HttpServletRequest request, ModelMap modelMap){
		User user = (User)request.getSession().getAttribute("user");
        parameter.setUid(user.getId());
        parameter.setSellerId(user.getSellerId());
        parameter.setPageSize(15);
        
        Pagination<Log> pagination = null;
		try {
			pagination = logService.getLogByPage(parameter);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage(), e);
		}
        modelMap.put("pagination", pagination);	
        return "log";
	}
	
	@RequestMapping(value="/searchGLog")
	public String searchGLog(@ModelAttribute("parameter") LogQueryParameter parameter,HttpServletRequest request, ModelMap modelMap){
		User user = (User)request.getSession().getAttribute("user");
        parameter.setUid(user.getId());
        parameter.setSellerId(user.getSellerId());
        parameter.setPageSize(15);
        
        Pagination<GLog> pagination = null;
		try {
			pagination = logService.getGLogByPage(parameter);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage(), e);
		}
        modelMap.put("pagination", pagination);	
        return "glog";
	}
}
