package com.my.iud.dto;

public class ItemQueryParameter extends QueryParameter {

    private long itemId;
    private String title;
    private int type;
    private long cid;
    private String itemIdArray;
    private String hasShowcase;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public long getCid() {
        return cid;
    }

    public void setCid(long cid) {
        this.cid = cid;
    }

    public long getItemId() {
        return itemId;
    }

    public void setItemId(long itemId) {
        this.itemId = itemId;
    }

    public String getItemIdArray() {
        return itemIdArray;
    }

    public void setItemIdArray(String itemIdArray) {
        this.itemIdArray = itemIdArray;
    }

    public String getHasShowcase() {
        return hasShowcase;
    }

    public void setHasShowcase(String hasShowcase) {
        this.hasShowcase = hasShowcase;
    }
}
