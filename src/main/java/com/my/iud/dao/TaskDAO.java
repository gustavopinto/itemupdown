package com.my.iud.dao;

import com.my.iud.dto.TaskQueryParameter;
import com.my.iud.entity.Gtask;
import com.my.iud.entity.Task;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.guzz.Guzz;
import org.guzz.jdbc.JDBCTemplate;
import org.guzz.orm.se.SearchExpression;
import org.guzz.orm.se.Terms;
import org.guzz.orm.sql.CompiledSQL;
import org.guzz.orm.sql.SQLQueryCallBack;
import org.guzz.transaction.ReadonlyTranSession;
import org.guzz.transaction.TransactionManager;
import org.guzz.transaction.WriteTranSession;
import org.guzz.util.DateUtil;
import org.springframework.stereotype.Repository;

@Repository("taskDAO")
@SuppressWarnings("unchecked")
public class TaskDAO extends SpGuzzBaseDao {

    public List<Task> getTaskListBySellerId(TaskQueryParameter parameter) throws Exception {
        Guzz.setTableCondition(parameter.getUid());

        SearchExpression se = SearchExpression.forLoadAll(Task.class);
        se.and(Terms.eq("sellerId", parameter.getSellerId()));

        return super.list(se);

    }

    public List<Gtask> getGtaskListBySellerId(TaskQueryParameter parameter) throws Exception {
        Guzz.setTableCondition(parameter.getUid());

        SearchExpression se = SearchExpression.forLoadAll(Gtask.class);
        se.and(Terms.eq("sellerId", parameter.getSellerId()));

        return super.list(se);
    }

    public void saveTask(Task task, long uid) throws Exception {
        super.insert(task, uid);
    }

    public void saveGtask(Gtask gtask, long uid) throws Exception {
        super.insert(gtask, uid);
    }

    public void updateTask(Task task, long uid) throws Exception {
        super.update(task, uid);
    }

    public void updateGtask(Gtask gtask, long uid) throws Exception {
        super.update(gtask, uid);
    }

    public Task getTask(TaskQueryParameter parameter) throws Exception {
        Guzz.setTableCondition(parameter.getUid());
        return (Task) super.getForRead(Task.class, parameter.getTaskId());
    }

    public Gtask getGtask(TaskQueryParameter parameter) throws Exception {
        Guzz.setTableCondition(parameter.getUid());
        return (Gtask) super.getForRead(Gtask.class, parameter.getTaskId());
    }

    public void deleteTask(TaskQueryParameter parameter) throws Exception {
        Task task = getTask(parameter);
        super.delete(task, parameter.getUid());
    }

    public void deleteGtask(TaskQueryParameter parameter) throws Exception {
        Gtask gTask = getGtask(parameter);
        super.delete(gTask, parameter.getUid());
    }

    public void deleteTaskBySellerId(TaskQueryParameter parameter) throws Exception {
        WriteTranSession session = super.getTransactionManager().openRWTran(true);
        Guzz.setTableCondition(parameter.getUid());
        SearchExpression se = SearchExpression.forLoadAll(Task.class);
        se.and(Terms.eq("sellerId", parameter.getSellerId()));
        session.delete(se);
        session.close();
    }

    public void updateTaskStatusBySellerId(TaskQueryParameter parameter) throws Exception {
        Guzz.setTableCondition(parameter.getUid());
        TransactionManager tm = super.getTransactionManager();
        WriteTranSession session = tm.openRWTran(true);
        
        String sql = "update @@task set @taskStatus = 0 where @sellerId = :sellerId";
        CompiledSQL cs = tm.getCompiledSQLBuilder().buildCompiledSQL(Task.class, sql);
        session.executeUpdate(cs.bind("sellerId", parameter.getSellerId()));
        session.close();
    }

    public void deleteGTaskBySellerId(TaskQueryParameter parameter) throws Exception {
        WriteTranSession session = super.getTransactionManager().openRWTran(true);
        Guzz.setTableCondition(parameter.getUid());
        SearchExpression se = SearchExpression.forLoadAll(Gtask.class);
        se.and(Terms.eq("sellerId", parameter.getSellerId()));
        session.delete(se);
        session.close();
    }
    
    public void updateGTaskStatusBySellerId(TaskQueryParameter parameter) throws Exception {
        Guzz.setTableCondition(parameter.getUid());
        TransactionManager tm = super.getTransactionManager();
        WriteTranSession session = tm.openRWTran(true);
        
        String sql = "update @@gtask set @taskStatus = 0 where @sellerId = :sellerId";
        CompiledSQL cs = tm.getCompiledSQLBuilder().buildCompiledSQL(Gtask.class, sql);
        session.executeUpdate(cs.bind("sellerId", parameter.getSellerId()));
        session.close();
    }

    public List<Task> getExecuteTaskList(String tableName) throws Exception {
        ReadonlyTranSession rts = super.getTransactionManager().openDelayReadTran();
        JDBCTemplate jDBCTemplate = rts.createJDBCTemplateByDbGroup("iud");

        StringBuilder sql = new StringBuilder("select * from ");
        sql.append(tableName);
        sql.append(" where status = 1 and nexec_time <= now() ");
        List<Task> taskList = (List<Task>) jDBCTemplate.executeQuery(sql.toString(), new SQLQueryCallBack() {
            @Override
            public Object iteratorResultSet(ResultSet rs)
                    throws Exception {
                // TODO Auto-generated method stub
                List<Task> taskList = new ArrayList<>();
                while (rs.next()) {
                    Task task = new Task();

                    long id = rs.getLong("id");
                    String taskName = rs.getString("task_name");
                    long sellerId = rs.getLong("seller_id");
                    int status = rs.getInt("status");
                    int kind = rs.getInt("kind");
                    int itemType = rs.getInt("item_type");
                    String selItems = rs.getString("sel_items");
                    String executeDay = rs.getString("exec_day");
                    String executeTime = rs.getString("exec_time");
                    String nexecTime = rs.getString("nexec_time");
                    Date createTime = rs.getDate("create_time");
                    Date modifyTime = rs.getDate("modify_time");

                    task.setId(id);
                    task.setTaskStatus(status);
                    task.setTaskName(taskName);
                    task.setNexecTime(DateUtil.stringToDate(nexecTime, "yyyy-MM-dd HH:mm:ss"));
                    task.setKind(kind);
                    task.setItemType(itemType);
                    task.setSelItems(selItems);
                    task.setSellerId(sellerId);
                    task.setExecDay(executeDay);
                    task.setExecTime(executeTime);
                    task.setCreateTime(createTime);
                    task.setModifyTime(modifyTime);
                    taskList.add(task);
                }
                return taskList;
            }
        });
        rts.close();
        return taskList;
    }

    public List<Gtask> getExecuteGtaskList(String tableName) throws Exception {
        ReadonlyTranSession rts = super.getTransactionManager().openDelayReadTran();
        JDBCTemplate jDBCTemplate = rts.createJDBCTemplateByDbGroup("iud");

        StringBuilder sql = new StringBuilder("select * from ");
        sql.append(tableName);
        sql.append(" where status = 1 and nexec_time <= now() ");
        List<Gtask> taskList = (List<Gtask>) jDBCTemplate.executeQuery(sql.toString(), new SQLQueryCallBack() {
            @Override
            public Object iteratorResultSet(ResultSet rs)
                    throws Exception {
                // TODO Auto-generated method stub
                List<Gtask> taskList = new ArrayList<>();
                while (rs.next()) {
                    Gtask task = new Gtask();

                    long id = rs.getLong("id");
                    String taskName = rs.getString("task_name");
                    long sellerId = rs.getLong("seller_id");
                    int status = rs.getInt("status");
                    int kind = rs.getInt("kind");
                    int itemType = rs.getInt("item_type");
                    String selItems = rs.getString("sel_items");
                    String executeDay = rs.getString("exec_day");
                    String executeTime = rs.getString("exec_time");
                    String nexecTime = rs.getString("nexec_time");
                    Date createTime = rs.getDate("create_time");
                    Date modifyTime = rs.getDate("modify_time");

                    task.setId(id);
                    task.setTaskStatus(status);
                    task.setTaskName(taskName);
                    task.setNexecTime(DateUtil.stringToDate(nexecTime, "yyyy-MM-dd HH:mm:ss"));
                    task.setKind(kind);
                    task.setItemType(itemType);
                    task.setSelItems(selItems);
                    task.setSellerId(sellerId);
                    task.setExecDay(executeDay);
                    task.setExecTime(executeTime);
                    task.setCreateTime(createTime);
                    task.setModifyTime(modifyTime);
                    taskList.add(task);
                }
                return taskList;
            }
        });
        rts.close();
        return taskList;
    }
}
