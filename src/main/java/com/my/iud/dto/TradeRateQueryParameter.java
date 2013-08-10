/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.my.iud.dto;

import java.util.Date;

/**
 *
 * @author dell
 */
public class TradeRateQueryParameter extends QueryParameter{
    
    private long itemId;
    
    private String rateType = "get";
    
    private String role = "buyer";
    
    private String result = "good";
    
    private Date startDate;
    
    private Date endDate;

    public long getItemId() {
        return itemId;
    }

    public void setItemId(long itemId) {
        this.itemId = itemId;
    }

    public String getRateType() {
        return rateType;
    }

    public void setRateType(String rateType) {
        this.rateType = rateType;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
    

}
