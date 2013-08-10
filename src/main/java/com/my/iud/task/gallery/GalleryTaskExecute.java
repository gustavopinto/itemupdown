package com.my.iud.task.gallery;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


import org.springframework.context.ApplicationContext;

public class GalleryTaskExecute {
	

	private ApplicationContext applicationContext;
	
	private ExecutorService executorService;
	
	private int threadNum;

	public GalleryTaskExecute(int threadNum, ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
		this.threadNum = threadNum;
		executorService = Executors.newFixedThreadPool(threadNum);
	}
	
	
	public void executeTask(){
		for(int i =1 ; i <= threadNum; i++){
			Thread task = new Thread(new GalleryTaskThread("橱窗推荐任务-" + i,"gtask_" + i,applicationContext));
			task.setName("橱窗推荐任务-" + i);
			executorService.execute(task);
		}
			
	}

}
