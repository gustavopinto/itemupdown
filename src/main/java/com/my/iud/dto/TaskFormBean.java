package com.my.iud.dto;



public class TaskFormBean extends BaseFormBean{
	
	private long taskId;
	
	private String taskName;
	
	private int day[];
	
	private int time[];
	
	private String selectItems;
	
	private int kind;
	
	private int sitem;
	
	private int taskStatus;
	

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

    
	public int[] getTime() {
		return time;
	}

	public void setTime(int[] time) {
		this.time = time;
	}

	public void setDay(int[] day) {
		this.day = day;
	}

	public int getKind() {
		return kind;
	}

	public void setKind(int kind) {
		this.kind = kind;
	}

	public int getSitem() {
		return sitem;
	}

	public void setSitem(int sitem) {
		this.sitem = sitem;
	}

	public int getTaskStatus() {
		return taskStatus;
	}

	public void setTaskStatus(int taskStatus) {
		this.taskStatus = taskStatus;
	}
	
	
	public String getSelectItems() {
		return selectItems;
	}

	public void setSelectItems(String selectItems) {
		this.selectItems = selectItems;
	}

	public int[] getDay() {
		return day;
	}

	public long getTaskId() {
		return taskId;
	}

	public void setTaskId(long taskId) {
		this.taskId = taskId;
	}
	
	
}
