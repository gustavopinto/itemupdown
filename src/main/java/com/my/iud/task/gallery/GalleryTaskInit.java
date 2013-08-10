package com.my.iud.task.gallery;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;




public class GalleryTaskInit implements ApplicationContextAware{
	
	private final static Logger logger = LoggerFactory.getLogger(GalleryTaskInit.class);
	
	private ApplicationContext applicationContext;

	private int runThreads;
	
	
    public  void init(){
    	logger.info("系统启动"+ runThreads + "个线程");
    	GalleryTaskExecute taskExecute = new GalleryTaskExecute(runThreads, applicationContext);
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
