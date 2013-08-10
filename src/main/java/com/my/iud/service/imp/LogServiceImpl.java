package com.my.iud.service.imp;

import com.my.iud.dao.LoggerDAO;
import com.my.iud.dao.TaoBaoItemDAO;
import com.my.iud.dto.ItemQueryParameter;
import com.my.iud.dto.LogQueryParameter;
import com.my.iud.entity.GLog;
import com.my.iud.entity.Log;
import com.my.iud.service.LogService;
import com.my.iud.util.Pagination;
import com.taobao.api.response.ItemGetResponse;
import java.util.List;
import org.guzz.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("logService")
public class LogServiceImpl implements LogService {

    @Autowired
    private LoggerDAO loggerDAO;
    @Autowired
    private TaoBaoItemDAO taoBaoItemDAO;

    @Override
    public Pagination<Log> getLogByPage(LogQueryParameter parameter) throws Exception {
        long rowsCount = loggerDAO.getLogCount(parameter);

        Pagination<Log> pagination = new Pagination<>(
                parameter.getCurrentPage(), parameter.getPageSize(), rowsCount);
        List<Log> logList = loggerDAO.getLogByPage(parameter);
        pagination.setList(logList);
        return pagination;
    }

    @Override
    public Pagination<GLog> getGLogByPage(LogQueryParameter parameter)
            throws Exception {
        // TODO Auto-generated method stub
        ItemQueryParameter iqp = new ItemQueryParameter();
        long rowsCount = loggerDAO.getGLogcount(parameter);

        Pagination<GLog> pagination = new Pagination<>(
                parameter.getCurrentPage(), parameter.getPageSize(), rowsCount);
        List<GLog> logList = loggerDAO.getGLogByPage(parameter);

        for (GLog glog : logList) {
            if (StringUtil.isEmpty(glog.getItemName())) {
                iqp.setItemId(glog.getItemId());
                ItemGetResponse igr = taoBaoItemDAO.getItem(iqp);
                if (igr.isSuccess()) {
                    glog.setItemName(igr.getItem().getTitle());
                }
            }
        }
        pagination.setList(logList);
        return pagination;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void delLogBySellerId(LogQueryParameter parameter) throws Exception {
        loggerDAO.delLogBySellerId(parameter);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void delGLogBySellerId(LogQueryParameter parameter) throws Exception {
        loggerDAO.delGLogBySellerId(parameter);
    }
}
