package com.my.iud.service.imp;

import com.my.iud.dao.TaskDAO;
import com.my.iud.dto.TaskQueryParameter;
import com.my.iud.entity.Gtask;
import com.my.iud.entity.Task;
import com.my.iud.service.TaskService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("taskService")
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskDAO taskDAO;

    @Override
    public List<Task> getTaskListBySellerId(TaskQueryParameter parameter) throws Exception {
        // TODO Auto-generated method stub
        return taskDAO.getTaskListBySellerId(parameter);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void saveTask(Task task, long uid) throws Exception {
        // TODO Auto-generated method stub
        taskDAO.saveTask(task, uid);
    }

    @Override
    public Task getTask(TaskQueryParameter parameter) throws Exception {
        return taskDAO.getTask(parameter);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void delTask(TaskQueryParameter parameter) throws Exception {
        taskDAO.deleteTask(parameter);
    }

    @Override
    public List<Task> getExecuteTaskList(String tableName) throws Exception {
        // TODO Auto-generated method stub
        return taskDAO.getExecuteTaskList(tableName);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void updateTask(Task task, long uid) throws Exception {
        // TODO Auto-generated method stub
        taskDAO.update(task, uid);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void updateGtask(Gtask task, long uid) throws Exception {
        // TODO Auto-generated method stub
        taskDAO.update(task, uid);
    }

    @Override
    public List<Gtask> getExecuteGtaskList(String tableName) throws Exception {
        // TODO Auto-generated method stub
        return taskDAO.getExecuteGtaskList(tableName);
    }

    @Override
    public List<Gtask> getGtaskListBySellerId(TaskQueryParameter parameter)
            throws Exception {
        // TODO Auto-generated method stub
        return taskDAO.getGtaskListBySellerId(parameter);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void saveGtask(Gtask task, long uid) throws Exception {
        // TODO Auto-generated method stub
        taskDAO.saveGtask(task, uid);
    }

    @Override
    public Gtask getGtask(TaskQueryParameter parameter) throws Exception {
        // TODO Auto-generated method stub
        return taskDAO.getGtask(parameter);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void delGtask(TaskQueryParameter parameter) throws Exception {
         taskDAO.deleteGtask(parameter);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteTaskBySellerId(TaskQueryParameter parameter) throws Exception {
        taskDAO.deleteTaskBySellerId(parameter);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteGtaskBySellerId(TaskQueryParameter parameter) throws Exception {
        taskDAO.deleteGTaskBySellerId(parameter);
    }

    @Override
    public void updateGTaskStatusBySellerId(TaskQueryParameter parameter) throws Exception {
        taskDAO.updateGTaskStatusBySellerId(parameter);
    }

    @Override
    public void updateTaskStatusBySellerId(TaskQueryParameter parameter) throws Exception {
        taskDAO.updateTaskStatusBySellerId(parameter);
    }
    
}
