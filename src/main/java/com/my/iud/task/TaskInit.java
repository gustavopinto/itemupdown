package com.my.iud.task;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;




public class TaskInit implements ApplicationContextAware{
	
	private final static Logger logger = LoggerFactory.getLogger(TaskInit.class);
	
	private ApplicationContext applicationContext;

	private int runThreads;
	
	
    public  void init(){
    	logger.info("系统启动"+ runThreads + "个线程");
    	TaskExecute taskExecute = new TaskExecute(runThreads, applicationContext);
    	taskExecute.executeTask();
    }

	public int getRunThreads() {
		return runThreads;
	}

	public void setRunThreads(int runThreads) {
		this.runThreads = runThreads;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		// TODO Auto-generated method stub
		this.applicationContext = applicationContext;
	}


}
