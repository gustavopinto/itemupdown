package com.my.iud.service;

import com.my.iud.dto.LogQueryParameter;
import com.my.iud.entity.GLog;
import com.my.iud.entity.Log;
import com.my.iud.util.Pagination;

public interface LogService {

    Pagination<Log> getLogByPage(LogQueryParameter parameter) throws Exception;

    Pagination<GLog> getGLogByPage(LogQueryParameter parameter) throws Exception;

    void delLogBySellerId(LogQueryParameter parameter) throws Exception;

    void delGLogBySellerId(LogQueryParameter parameter) throws Exception;
}
