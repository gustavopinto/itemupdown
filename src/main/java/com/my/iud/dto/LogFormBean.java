package com.my.iud.dto;

import java.util.List;

import com.my.iud.entity.GLog;
import com.my.iud.entity.Log;

public class LogFormBean extends BaseFormBean {
	
    private List<Log> loggerList;
    
    private List<GLog> gLoggerList;

	public List<Log> getLoggerList() {
		return loggerList;
	}

	public void setLoggerList(List<Log> loggerList) {
		this.loggerList = loggerList;
	}

	public List<GLog> getgLoggerList() {
		return gLoggerList;
	}

	public void setgLoggerList(List<GLog> gLoggerList) {
		this.gLoggerList = gLoggerList;
	}
    
}
