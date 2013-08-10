package com.my.iud.entity;

import com.my.iud.shadow.CommonShadowView;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import org.guzz.annotations.Table;

@javax.persistence.Entity
@org.guzz.annotations.Entity(businessName="gtask")
@Table(name = "gtask",dbGroup = "iud",shadow = CommonShadowView.class)
public class Gtask implements Serializable {

	private static final long serialVersionUID = 466024780438712726L;

	@javax.persistence.Id        
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
    
	@Column(name="seller_id")
	private long sellerId;
	
	@Column(name="task_name")
	private String taskName;
	
	@Column(name="status")
	private int taskStatus;
	
	/** 上架   下架   **/
	@Column(name="kind")
	private int kind;
	
	/** 库中   架上  全部   **/
	@Column(name="item_type")
	private int itemType;
	
	
	/** 选中的宝贝  **/
	@Column(name="sel_items")
	private String selItems;
	
	@Column(name="exec_day")
	private String execDay;
	
	@Column(name="exec_time")
	private String execTime;
    
	@Column(name = "nexec_time")
	private Date nexecTime;
	
	@Column(name="create_time")
	private Date createTime;
    
	@Column(name="modify_time")
	private Date modifyTime;
	

	public int getItemType() {
		return itemType;
	}

	public void setItemType(int itemType) {
		this.itemType = itemType;
	}

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

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public int getTaskStatus() {
		return taskStatus;
	}

	public void setTaskStatus(int taskStatus) {
		this.taskStatus = taskStatus;
	}

	public int getKind() {
		return kind;
	}

	public void setKind(int kind) {
		this.kind = kind;
	}

	public String getExecTime() {
		return execTime;
	}

	public void setExecTime(String execTime) {
		this.execTime = execTime;
	}

	public Date getNexecTime() {
		return nexecTime;
	}

	public void setNexecTime(Date nexecTime) {
		this.nexecTime = nexecTime;
	}

	public String getSelItems() {
		return selItems;
	}

	public void setSelItems(String selItems) {
		this.selItems = selItems;
	}

	public String getExecDay() {
		return execDay;
	}

	public void setExecDay(String execDay) {
		this.execDay = execDay;
	}
	
}
