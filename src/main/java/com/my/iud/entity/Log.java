package com.my.iud.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

import org.guzz.annotations.Table;

import com.my.iud.shadow.CommonShadowView;

@javax.persistence.Entity
@org.guzz.annotations.Entity(businessName="log")
@Table(name = "log",dbGroup = "iud",shadow = CommonShadowView.class)

public class Log implements Serializable{

	private static final long serialVersionUID = 6523928141436694426L;

	@javax.persistence.Id        
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
    
	@Column(name="seller_id")
	private long sellerId;
	
	@Column(name="task_id")
	private long taskId;
	
	@Column(name="item_id")
    private long itemId;
	
	@Column(name="item_name")
	private String itemName;
	
	@Column(name="status")
	private int status;
	
	@Column(name="result_info")
	private String resultinfo;
	
	@Column(name="create_time")
	private Date createTime;
    
	@Column(name="modify_time")
	private Date modifyTime;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getSellerId() {
		return sellerId;
	}

	public void setSellerId(long sellerId) {
		this.sellerId = sellerId;
	}

	public long getTaskId() {
		return taskId;
	}

	public void setTaskId(long taskId) {
		this.taskId = taskId;
	}

	public long getItemId() {
		return itemId;
	}

	public void setItemId(long itemId) {
		this.itemId = itemId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getResultinfo() {
		return resultinfo;
	}

	public void setResultinfo(String resultinfo) {
		this.resultinfo = resultinfo;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
    
}
