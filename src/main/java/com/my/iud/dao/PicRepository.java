package com.my.iud.dao;

import com.my.iud.dto.PicParameter;
import com.my.iud.dto.PicResult;
import com.taobao.api.FileItem;
import com.taobao.api.TaobaoClient;
import com.taobao.api.domain.Picture;
import com.taobao.api.request.PictureUploadRequest;
import com.taobao.api.response.PictureUploadResponse;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("picRepository")
public class PicRepository {
	
	@Autowired
	private TaobaoClient taoBaoClient;
	
	public PicResult addPic(PicParameter picParameter) throws Exception{
		PictureUploadRequest req=new PictureUploadRequest();
		List<String> filePath = picParameter.getFilePath();
	    List<Picture> picList = new ArrayList<Picture>();
		for(String file : filePath){
			req.setPictureCategoryId(0L);
			FileItem fItem = new FileItem(new File(file));
			req.setImg(fItem);
			req.setTitle("暂无");
			req.setImageInputTitle(picParameter.getSessionKey() + System.currentTimeMillis());
			PictureUploadResponse response = taoBaoClient.execute(req , picParameter.getSessionKey());
			picList.add(response.getPicture());
		}
		PicResult picResult = new PicResult();
		picResult.setPicList(picList);
		return picResult;
	}

}
