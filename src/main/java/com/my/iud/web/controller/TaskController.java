package com.my.iud.web.controller;

import com.my.iud.dto.Message;
import com.my.iud.dto.TaskFormBean;
import com.my.iud.dto.TaskQueryParameter;
import com.my.iud.entity.Gtask;
import com.my.iud.entity.Task;
import com.my.iud.entity.User;
import com.my.iud.service.TaskService;
import com.my.iud.util.Constants;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.guzz.util.DateUtil;
import org.guzz.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping("/task")
public class TaskController {
    
	private final static Logger logger = LoggerFactory.getLogger(TaskController.class);


	private static List<String> timeList;
	
	private static List<Integer> timeListV;
	
	private static Map<Integer,String> dayMap;
	
	@Autowired
	private TaskService taskService;
	
	@RequestMapping(value="/itemUpDown")
	public String itemupdown(HttpServletRequest request, ModelMap modelMap){
		User user = (User)request.getSession().getAttribute("user");
		TaskQueryParameter taskQueryParameter = new TaskQueryParameter();
		taskQueryParameter.setUid(user.getId());
		taskQueryParameter.setSellerId(user.getSellerId());
		List<Task> taskList = null;
		try {
			taskList = taskService.getTaskListBySellerId(taskQueryParameter);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage(),e);
		}
		modelMap.put("taskList", taskList);
		return "index_n";
	}
	
	
	@RequestMapping(value="/itemGallery")
	public String itemGallery(HttpServletRequest request, ModelMap modelMap){
		User user = (User)request.getSession().getAttribute("user");
		TaskQueryParameter taskQueryParameter = new TaskQueryParameter();
		taskQueryParameter.setUid(user.getId());
		taskQueryParameter.setSellerId(user.getSellerId());
		List<Gtask> taskList = null;
		try {
			taskList = taskService.getGtaskListBySellerId(taskQueryParameter);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage(),e);
		}
		modelMap.put("taskList", taskList);
		return "item_gallery";
	}
	
	
	
	
	@RequestMapping(value="/addTask")
	public String addTask(TaskFormBean taskFormBean, HttpServletRequest request, ModelMap modelMap){
		User user = (User)request.getSession().getAttribute("user");
        
		Task task = new Task();
		task.setSellerId(user.getSellerId());
		task.setTaskName(taskFormBean.getTaskName());
		task.setKind(taskFormBean.getKind());
		if(taskFormBean.getSitem() == 0 && StringUtil.notEmpty(taskFormBean.getSelectItems())){
			task.setItemType(4);
			task.setSelItems(taskFormBean.getSelectItems().substring(0,taskFormBean.getSelectItems().length()-1));
		}else{
			task.setItemType(taskFormBean.getSitem());
		}
		task.setTaskStatus(taskFormBean.getTaskStatus());
		
		String execDay = "";
		for(int tday : taskFormBean.getDay()){
			execDay = execDay + tday + ":";
		}
		task.setExecDay(execDay.substring(0, execDay.length() - 1));
		
		Date date = Calendar.getInstance().getTime();
		task.setCreateTime(date);
		task.setModifyTime(date);
		
		String execTime = "";
		for(int temp : taskFormBean.getTime()){
			execTime = execTime + temp + ":";
		}  
		task.setExecTime(execTime.substring(0, execTime.length()-1));
		
		
		String nextExecTime = com.my.iud.util.DateUtil.getNextExecuteDay(taskFormBean.getDay(), taskFormBean.getTime());
		task.setNexecTime(DateUtil.stringToDate(nextExecTime,"yyyy-MM-dd HH:mm:ss"));
		try {
			taskService.saveTask(task, user.getId());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage(), e);
		}
		
		String rsInfo = Constants.ADD_TASK_SUCESS;
		if (task.getTaskStatus() == 1) {
			rsInfo = rsInfo
					+ "------你的任务将于 "
					+ DateUtil.date2String(task.getNexecTime(),
							"yyyy-MM-dd HH:mm:ss") + "之后开始执行";
		}
		Message message = new Message();
		message.setStatus(true);
		message.setRs(rsInfo);
		modelMap.put("message", message);
		
		return "forward";
	}
	

	@RequestMapping(value="/addGtask")
	public String addGtask(TaskFormBean taskFormBean, HttpServletRequest request, ModelMap modelMap){
		User user = (User)request.getSession().getAttribute("user");
        
		Gtask task = new Gtask();
		task.setSellerId(user.getSellerId());
		task.setTaskName(taskFormBean.getTaskName());
		task.setKind(taskFormBean.getKind());
		if(taskFormBean.getSitem() == 0 && StringUtil.notEmpty(taskFormBean.getSelectItems())){
			task.setItemType(4);
			task.setSelItems(taskFormBean.getSelectItems().substring(0,taskFormBean.getSelectItems().length()-1));
		}else{
			task.setItemType(taskFormBean.getSitem());
		}
		task.setTaskStatus(taskFormBean.getTaskStatus());
		
		String execDay = "";
		for(int tday : taskFormBean.getDay()){
			execDay = execDay + tday + ":";
		}
		task.setExecDay(execDay.substring(0, execDay.length() - 1));
		
		Date date = Calendar.getInstance().getTime();
		task.setCreateTime(date);
		task.setModifyTime(date);
		
		String execTime = "";
		for(int temp : taskFormBean.getTime()){
			execTime = execTime + temp + ":";
		}  
		task.setExecTime(execTime.substring(0, execTime.length()-1));
		
		
		String nextExecTime = com.my.iud.util.DateUtil.getNextExecuteDay(taskFormBean.getDay(), taskFormBean.getTime());
		task.setNexecTime(DateUtil.stringToDate(nextExecTime,"yyyy-MM-dd HH:mm:ss"));
		try {
			taskService.saveGtask(task, user.getId());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage(), e);
		}
		
		String rsInfo = Constants.ADD_TASK_SUCESS;
		if (task.getTaskStatus() == 1) {
			rsInfo = rsInfo
					+ "------你的任务将于 "
					+ DateUtil.date2String(task.getNexecTime(),
							"yyyy-MM-dd HH:mm:ss") + "之后开始执行";
		}
		Message message = new Message();
		message.setStatus(true);
		message.setRs(rsInfo);
		modelMap.put("message", message);
		
		return "forward";
	}
	
	@RequestMapping(value="/modifyTask")
	public String modifyTask(TaskFormBean taskFormBean, HttpServletRequest request, ModelMap modelMap){
		User user = (User)request.getSession().getAttribute("user");
        
		Task task = new Task();
		task.setId(taskFormBean.getTaskId());
		task.setSellerId(user.getSellerId());
		task.setTaskName(taskFormBean.getTaskName());
		task.setKind(taskFormBean.getKind());
		if(taskFormBean.getSitem() == 0 && StringUtil.notEmpty(taskFormBean.getSelectItems())){
			task.setItemType(4);
			task.setSelItems(taskFormBean.getSelectItems().substring(0,taskFormBean.getSelectItems().length()-1));
		}else{
			task.setItemType(taskFormBean.getSitem());
		}
		task.setTaskStatus(taskFormBean.getTaskStatus());
		
		String execDay = "";
		for(int tday : taskFormBean.getDay()){
			execDay = execDay + tday + ":";
		}
		task.setExecDay(execDay.substring(0, execDay.length() - 1));
		
		Date date = Calendar.getInstance().getTime();
		task.setCreateTime(date);
		task.setModifyTime(date);
		
		String execTime = "";

		for(int temp : taskFormBean.getTime()){
			execTime = execTime + temp + ":" ;
		}
		task.setExecTime(execTime.substring(0, execTime.length()-1));
		
		String nextExecTime = com.my.iud.util.DateUtil.getNextExecuteDay(taskFormBean.getDay(), taskFormBean.getTime());

		task.setNexecTime(DateUtil.stringToDate(nextExecTime, "yyyy-MM-dd HH:mm:ss"));
		try {
			taskService.updateTask(task, user.getId());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage(), e);
		}
		
		String rsInfo = Constants.MODIFY_TASK_SUCESS;
		if (task.getTaskStatus() == 1) {
			rsInfo = rsInfo
					+ "------你的任务将于 "
					+ DateUtil.date2String(task.getNexecTime(),
							"yyyy-MM-dd HH:mm:ss") + "之后开始执行";
		}
		Message message = new Message();
		message.setStatus(true);
		message.setRs(rsInfo);
		modelMap.put("message", message);
		
		return "forward";
	}
	
	@RequestMapping(value="/modifyGtask")
	public String modifyGtask(TaskFormBean taskFormBean, HttpServletRequest request, ModelMap modelMap){
		User user = (User)request.getSession().getAttribute("user");
        
		Gtask task = new Gtask();
		task.setId(taskFormBean.getTaskId());
		task.setSellerId(user.getSellerId());
		task.setTaskName(taskFormBean.getTaskName());
		task.setKind(taskFormBean.getKind());
		if(taskFormBean.getSitem() == 0 && StringUtil.notEmpty(taskFormBean.getSelectItems())){
			task.setItemType(4);
			task.setSelItems(taskFormBean.getSelectItems().substring(0,taskFormBean.getSelectItems().length()-1));
		}else{
			task.setItemType(taskFormBean.getSitem());
		}
		task.setTaskStatus(taskFormBean.getTaskStatus());
		
		String execDay = "";
		for(int tday : taskFormBean.getDay()){
			execDay = execDay + tday + ":";
		}
		task.setExecDay(execDay.substring(0, execDay.length() - 1));
		
		Date date = Calendar.getInstance().getTime();
		task.setCreateTime(date);
		task.setModifyTime(date);
		
		String execTime = "";

		for(int temp : taskFormBean.getTime()){
			execTime = execTime + temp + ":" ;
		}
		task.setExecTime(execTime.substring(0, execTime.length()-1));
		
		String nextExecTime = com.my.iud.util.DateUtil.getNextExecuteDay(taskFormBean.getDay(), taskFormBean.getTime());

		task.setNexecTime(DateUtil.stringToDate(nextExecTime, "yyyy-MM-dd HH:mm:ss"));
		try {
			taskService.updateGtask(task, user.getId());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage(), e);
		}
		
		String rsInfo = Constants.MODIFY_TASK_SUCESS;
		if (task.getTaskStatus() == 1) {
			rsInfo = rsInfo
					+ "------你的任务将于 "
					+ DateUtil.date2String(task.getNexecTime(),
							"yyyy-MM-dd HH:mm:ss") + "之后开始执行";
		}
		Message message = new Message();
		message.setStatus(true);
		message.setRs(rsInfo);
		modelMap.put("message", message);
		
		return "forward";
	}
	
	
	@RequestMapping(value="/delTask")
	public String delTask(@RequestParam("taskId") long taskId, HttpServletRequest request, ModelMap modelMap){
		if(taskId != 0){
			User user = (User)request.getSession().getAttribute("user");
			TaskQueryParameter taskQueryParameter = new TaskQueryParameter();
			taskQueryParameter.setUid(user.getId());
			taskQueryParameter.setTaskId(taskId);
			try {
				taskService.delTask(taskQueryParameter);
			} catch (Exception e) {
				// TODO Auto-generated catch block
			    logger.error(e.getMessage(), e);
			}
		}	
		Message message = new Message();
		message.setStatus(true);
                message.setRs("删除成功");
		modelMap.put("message", message);
		return "forward";
	}
        
        @RequestMapping(value="/delGtask")
	public String delGtask(@RequestParam("taskId") long taskId, HttpServletRequest request, ModelMap modelMap){
		if(taskId != 0){
			User user = (User)request.getSession().getAttribute("user");
			TaskQueryParameter taskQueryParameter = new TaskQueryParameter();
			taskQueryParameter.setUid(user.getId());
			taskQueryParameter.setTaskId(taskId);
			try {
				taskService.delGtask(taskQueryParameter);
			} catch (Exception e) {
				// TODO Auto-generated catch block
			    logger.error(e.getMessage(), e);
			}
		}	
		Message message = new Message();
		message.setStatus(true);
                message.setRs("删除成功");
		modelMap.put("message", message);
		return "forward";
	}
	
	@RequestMapping(value="/prepareAddTask")
	public String prepareAddTask(HttpServletRequest request, ModelMap modelMap){
		initTiemList();
		modelMap.put("itemList", timeList);
		modelMap.put("dayMap", dayMap);
		modelMap.put("timeListV", timeListV);
		return "add_task";
	}
	
	@RequestMapping(value="/prepareAddGtask")
	public String prepareAddGtask(HttpServletRequest request, ModelMap modelMap){
		initTiemList();
		modelMap.put("itemList", timeList);
		modelMap.put("dayMap", dayMap);
		modelMap.put("timeListV", timeListV);
		return "add_gtask";
	}
	
	@RequestMapping(value="/prepareModifyTask")
	public String prepareModifyTask(@RequestParam("taskId") long taskId, HttpServletRequest request, ModelMap modelMap){
		if(taskId != 0){
			String execTime = "";
			User user = (User)request.getSession().getAttribute("user");
			TaskQueryParameter taskQueryParameter = new TaskQueryParameter();
			taskQueryParameter.setUid(user.getId());
			taskQueryParameter.setTaskId(taskId);
			Task task = null;
			try {
				task = taskService.getTask(taskQueryParameter);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				logger.error(e.getMessage(), e);
				return "modify_task";
			}
			String time[] =task.getExecTime().split(":");
			for(String ttime :time){
				if(Integer.valueOf(ttime) < 10){
					execTime = execTime + "0" + ttime + ":";
				}else{
					execTime = execTime + ttime + ":";
				}
			}
			task.setExecTime(execTime);
			initTiemList();

			modelMap.put("task", task);
			modelMap.put("itemList", timeList);
			modelMap.put("dayMap", dayMap);
			modelMap.put("timeListV", timeListV);
		}	
		return "modify_task";
	}
	
	@RequestMapping(value="/prepareModifyGtask")
	public String prepareModifyGtask(@RequestParam("taskId") long taskId, HttpServletRequest request, ModelMap modelMap){
		if(taskId != 0){
			String execTime = "";
			User user = (User)request.getSession().getAttribute("user");
			TaskQueryParameter taskQueryParameter = new TaskQueryParameter();
			taskQueryParameter.setUid(user.getId());
			taskQueryParameter.setTaskId(taskId);
			Gtask task = null;
			try {
				task = taskService.getGtask(taskQueryParameter);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				logger.error(e.getMessage(), e);
				return "modify_gtask";
			}
			String time[] =task.getExecTime().split(":");
			for(String ttime :time){
				if(Integer.valueOf(ttime) < 10){
					execTime = execTime + "0" + ttime + ":";
				}else{
					execTime = execTime + ttime + ":";
				}
			}
			task.setExecTime(execTime);
			initTiemList();

			modelMap.put("task", task);
			modelMap.put("itemList", timeList);
			modelMap.put("dayMap", dayMap);
			modelMap.put("timeListV", timeListV);
		}	
		return "modify_gtask";
	}
	

	
	private void initTiemList(){
		if(timeList == null){
			timeList = new ArrayList<>();
			for(int i = 0 ; i < 24 ; i++){
				if(i < 10){
					timeList.add("0" + i );
				}else{
					timeList.add(""+ i);
				}
			}
		}
		
		if(timeListV == null){
			timeListV = new ArrayList<>();
			for(int i = 0 ; i < 24 ; i++){
				timeListV.add(i);
			}
		}
		
		if(dayMap == null){
			dayMap = new HashMap<>();
			dayMap.put(1, "星期一");
			dayMap.put(2, "星期二");
			dayMap.put(3, "星期三");
			dayMap.put(4, "星期四");
			dayMap.put(5, "星期五");
			dayMap.put(6, "星期六");
			dayMap.put(7, "星期天");
		}
	}
}
