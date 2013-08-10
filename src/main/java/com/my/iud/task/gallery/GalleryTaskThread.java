package com.my.iud.task.gallery;

import java.util.Calendar;
import java.util.List;
import java.util.concurrent.ForkJoinPool;

import org.guzz.util.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import com.my.iud.entity.Gtask;
import com.my.iud.service.TaskService;


public class GalleryTaskThread implements Runnable {
	
	private final static Logger logger = LoggerFactory.getLogger(GalleryTaskThread.class);


	private static ApplicationContext applicationContext;
	
	private String taskTable;
	
	private String taskName;
	
	public GalleryTaskThread(String taskName, String taskTable, ApplicationContext applicationContext){
		this.taskName = taskName;
		this.taskTable = taskTable;
		setAp(applicationContext);
	}
    
	private void setAp(ApplicationContext ac){
		applicationContext = ac;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		ForkJoinPool pool = new ForkJoinPool();
		while(true){
			logger.info(this.taskName + "正在运行##########" + DateUtil.date2String(Calendar.getInstance().getTime()));
			TaskService taskService = (TaskService)applicationContext.getBean("taskService");
			List<Gtask> taskList = null;
			try {
				taskList = taskService.getExecuteGtaskList(taskTable);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				logger.error(e1.getMessage(), e1);
			}
			logger.info(this.taskName + "获取##########" + taskTable + "##########" +taskList.size() + "任务");
			if(taskList != null && taskList.size() != 0){
				pool.invoke(new GalleryTaskRecursive(taskList, applicationContext));
			}
			logger.info(this.taskName + "执行完毕##########" + DateUtil.date2String(Calendar.getInstance().getTime()));
			try {
				Thread.sleep(1000 * 60 * 5L);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				logger.error(e.getMessage(), e);
				continue;
			}
		}
	 
	}

}
