package com.my.iud.task;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


import org.springframework.context.ApplicationContext;

public class TaskExecute {
	

	private ApplicationContext applicationContext;
	
	private ExecutorService executorService;
	
	private int threadNum;

	public TaskExecute(int threadNum, ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
		this.threadNum = threadNum;
		executorService = Executors.newFixedThreadPool(threadNum);
	}
	
	
	public void executeTask(){
		for(int i =1 ; i <= threadNum; i++){
			Thread task = new Thread(new TaskThread("定时上下架任务-" + i,"task_" + i,applicationContext));
			task.setName("定时上下架任务-" + i);
			executorService.execute(task);
		}
			
	}

}
