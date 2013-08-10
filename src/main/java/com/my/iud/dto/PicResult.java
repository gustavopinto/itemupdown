package com.my.iud.dto;

import java.util.List;

import com.taobao.api.domain.Picture;

public class PicResult extends BaseResult {

	private List<Picture> picList;

	public List<Picture> getPicList() {
		return picList;
	}

	public void setPicList(List<Picture> picList) {
		this.picList = picList;
	}

}
