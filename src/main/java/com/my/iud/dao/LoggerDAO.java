package com.my.iud.dao;

import com.my.iud.dto.LogFormBean;
import com.my.iud.dto.LogQueryParameter;
import com.my.iud.entity.GLog;
import com.my.iud.entity.Gtask;
import com.my.iud.entity.Log;
import java.util.List;
import org.guzz.Guzz;
import org.guzz.jdbc.ObjectBatcher;
import org.guzz.orm.se.SearchExpression;
import org.guzz.orm.se.Terms;
import org.guzz.transaction.TransactionManager;
import org.guzz.transaction.WriteTranSession;
import org.guzz.util.StringUtil;
import org.springframework.stereotype.Repository;

@Repository("loggerDAO")
public class LoggerDAO extends SpGuzzBaseDao {

    public void batchSaveLog(LogFormBean logFormBean) throws Exception {
        Guzz.setTableCondition(logFormBean.getUid());
        TransactionManager tm = super.getTransactionManager();
        WriteTranSession session = tm.openRWTran(false);
        ObjectBatcher batcher = session.createObjectBatcher();
        for (Log log : logFormBean.getLoggerList()) {
            batcher.insert(log);
        }
        batcher.executeBatch();
        session.commit();
        session.close();
    }

    public void batchSaveGLog(LogFormBean logFormBean) throws Exception {
        Guzz.setTableCondition(logFormBean.getUid());
        TransactionManager tm = super.getTransactionManager();
        WriteTranSession session = tm.openRWTran(false);
        ObjectBatcher batcher = session.createObjectBatcher();
        for (GLog log : logFormBean.getgLoggerList()) {
            batcher.insert(log);
        }
        batcher.executeBatch();
        session.commit();
        session.close();
    }

    public long getLogCount(LogQueryParameter parameter) throws Exception {
        Guzz.setTableCondition(parameter.getUid());
        SearchExpression se = SearchExpression.forClass(Log.class);

        se.and(Terms.eq("sellerId", parameter.getSellerId()));
        if (parameter.getStatus() == 0) {
            se.and(Terms.notEq("status", 0));
        } else {
            se.and(Terms.eq("status", parameter.getStatus()));
        }

        if (StringUtil.notEmpty(parameter.getItemName())) {
            se.and(Terms.like("itemName", "%" + parameter.getItemName() + "%", true));
        } else {
            se.and(Terms.notNull("itemName"));
        }

        if (StringUtil.notEmpty(parameter.getBeginTime())) {
            se.and(Terms.biggerOrEq("createTime", parameter.getBeginTime()));
        }

        if (StringUtil.notEmpty(parameter.getEndTime())) {
            se.and(Terms.smallerOrEq("createTime", parameter.getEndTime()));
        }

        return super.count(se);

    }

    public long getGLogcount(LogQueryParameter parameter) throws Exception {
        Guzz.setTableCondition(parameter.getUid());
        SearchExpression se = SearchExpression.forClass(GLog.class);

        se.and(Terms.eq("sellerId", parameter.getSellerId()));
        if (parameter.getStatus() == 0) {
            se.and(Terms.notEq("status", 0));
        } else {
            se.and(Terms.eq("status", parameter.getStatus()));
        }

        if (StringUtil.notEmpty(parameter.getItemName())) {
            se.and(Terms.like("itemName", "%" + parameter.getItemName() + "%", true));
        } else {
            se.and(Terms.notNull("itemName"));
        }

        if (StringUtil.notEmpty(parameter.getBeginTime())) {
            se.and(Terms.biggerOrEq("createTime", parameter.getBeginTime()));
        }

        if (StringUtil.notEmpty(parameter.getEndTime())) {
            se.and(Terms.smallerOrEq("createTime", parameter.getEndTime()));
        }

        return super.count(se);
    }

    @SuppressWarnings("unchecked")
    public List<Log> getLogByPage(LogQueryParameter parameter) throws Exception {
        Guzz.setTableCondition(parameter.getUid());

        SearchExpression se = SearchExpression.forClass(Log.class, (int) parameter.getCurrentPage(), (int) parameter.getPageSize());

        se.and(Terms.eq("sellerId", parameter.getSellerId()));
        if (parameter.getStatus() == 0) {
            se.and(Terms.notEq("status", 0));
        } else {
            se.and(Terms.eq("status", parameter.getStatus()));
        }

        if (StringUtil.notEmpty(parameter.getItemName())) {
            se.and(Terms.like("itemName", "%" + parameter.getItemName() + "%", true));
        } else {
            se.and(Terms.notNull("itemName"));
        }

        if (StringUtil.notEmpty(parameter.getBeginTime())) {
            se.and(Terms.biggerOrEq("createTime", parameter.getBeginTime()));
        }

        if (StringUtil.notEmpty(parameter.getEndTime())) {
            se.and(Terms.smallerOrEq("createTime", parameter.getEndTime()));
        }

        se.setOrderBy("id desc");

        return (List<Log>) super.list(se);
    }

    @SuppressWarnings("unchecked")
    public List<GLog> getGLogByPage(LogQueryParameter parameter) throws Exception {
        Guzz.setTableCondition(parameter.getUid());

        SearchExpression se = SearchExpression.forClass(GLog.class, (int) parameter.getCurrentPage(), (int) parameter.getPageSize());

        se.and(Terms.eq("sellerId", parameter.getSellerId()));
        if (parameter.getStatus() == 0) {
            se.and(Terms.notEq("status", 0));
        } else {
            se.and(Terms.eq("status", parameter.getStatus()));
        }

        if (StringUtil.notEmpty(parameter.getItemName())) {
            se.and(Terms.like("itemName", "%" + parameter.getItemName() + "%", true));
        } else {
            se.and(Terms.notNull("itemName"));
        }

        if (StringUtil.notEmpty(parameter.getBeginTime())) {
            se.and(Terms.biggerOrEq("createTime", parameter.getBeginTime()));
        }

        if (StringUtil.notEmpty(parameter.getEndTime())) {
            se.and(Terms.smallerOrEq("createTime", parameter.getEndTime()));
        }

        se.setOrderBy("id desc");

        return (List<GLog>) super.list(se);
    }

    public void delLogBySellerId(LogQueryParameter parameter) throws Exception {
        WriteTranSession session = super.getTransactionManager().openRWTran(true);
        Guzz.setTableCondition(parameter.getUid());
        SearchExpression se = SearchExpression.forLoadAll(Log.class);
        se.and(Terms.eq("sellerId", parameter.getSellerId()));
        session.delete(se);
        session.close();
    }

    public void delGLogBySellerId(LogQueryParameter parameter) throws Exception {
        WriteTranSession session = super.getTransactionManager().openRWTran(true);
        Guzz.setTableCondition(parameter.getUid());
        SearchExpression se = SearchExpression.forLoadAll(GLog.class);
        se.and(Terms.eq("sellerId", parameter.getSellerId()));
        session.delete(se);
        session.close();
    }
}
