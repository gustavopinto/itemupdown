package com.my.iud.test;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.my.iud.entity.Task;
import com.my.iud.util.DateUtil;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
        int[] day = new int[]{1,2,3,4};
        int[] time = new int[]{0,1,2,3,4,5,8,10,11,12,14};
        System.out.println("xxx"+DateUtil.getNextExecuteDay(day, time));
        
        List<Task> taskList  = new ArrayList<>();
        for(int i=0 ; i <= 30; i++){
        	Task task = new Task();
        	task.setId(Long.valueOf(i));
        	taskList.add(task);
        }
        System.out.println("!!!!!!!!!!!" + taskList.size());
    	List<Task> leftTaskList = taskList.subList(0, taskList.size()/2);
		List<Task> rightTaskList = taskList.subList(taskList.size()/2, taskList.size());
		
		for(Task task : leftTaskList){
			System.out.println("########################" +task.getId());
		}
		
		for(Task task : rightTaskList){
			System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$" + task.getId());
		}
		
		System.out.println("xx"+Calendar.getInstance().get(Calendar.DAY_OF_WEEK));
	}
	

}
