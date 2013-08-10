package com.my.iud.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

import org.guzz.annotations.Table;

import com.my.iud.shadow.CommonShadowView;

@javax.persistence.Entity
@org.guzz.annotations.Entity(businessName="taskItem")
@Table(name = "task_item",dbGroup = "iud",shadow = CommonShadowView.class)

public class TaskItem implements Serializable{

	private static final long serialVersionUID = -83603260265242182L;

	@javax.persistence.Id        
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	
	@Column(name="itemid")
	private long itemId;
    
	@Column(name="seller_id")
	private long sellerId;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getItemId() {
		return itemId;
	}

	public void setItemId(long itemId) {
		this.itemId = itemId;
	}

	public long getSellerId() {
		return sellerId;
	}

	public void setSellerId(long sellerId) {
		this.sellerId = sellerId;
	}

}
