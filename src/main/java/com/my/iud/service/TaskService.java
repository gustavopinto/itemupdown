package com.my.iud.service;

import com.my.iud.dto.TaskQueryParameter;
import com.my.iud.entity.Gtask;
import com.my.iud.entity.Task;
import java.util.List;

public interface TaskService {

    List<Task> getTaskListBySellerId(TaskQueryParameter parameter) throws Exception;

    List<Gtask> getGtaskListBySellerId(TaskQueryParameter parameter) throws Exception;

    void saveTask(Task task, long uid) throws Exception;

    void saveGtask(Gtask task, long uid) throws Exception;

    void updateTask(Task task, long uid) throws Exception;

    List<Task> getExecuteTaskList(String tableName) throws Exception;

    Task getTask(TaskQueryParameter parameter) throws Exception;

    Gtask getGtask(TaskQueryParameter parameter) throws Exception;

    void delTask(TaskQueryParameter parameter) throws Exception;

    void delGtask(TaskQueryParameter parameter) throws Exception;

    void updateGtask(Gtask task, long uid) throws Exception;

    public List<Gtask> getExecuteGtaskList(String tableName) throws Exception;

    void deleteTaskBySellerId(TaskQueryParameter parameter) throws Exception;

    void deleteGtaskBySellerId(TaskQueryParameter parameter) throws Exception;

    void updateGTaskStatusBySellerId(TaskQueryParameter parameter) throws Exception;

    void updateTaskStatusBySellerId(TaskQueryParameter parameter) throws Exception;
}
