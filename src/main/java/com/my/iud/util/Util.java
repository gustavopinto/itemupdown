package com.my.iud.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.codec.binary.Base64;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class Util {

	public static Map<String, String> convertBase64StringtoMap(String str,
			String encode) {
		if (str == null)
			return null;
		if (encode == null)
			encode = "GBK";
		String keyvalues = null;
		try {
			keyvalues = new String(Base64.decodeBase64(URLDecoder.decode(str,
					"utf-8").getBytes(encode)), encode);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		String[] keyvalueArray = keyvalues.split("\\&");
		Map<String, String> map = new HashMap<>();
		for (String keyvalue : keyvalueArray) {
			String[] s = keyvalue.split("\\=");
			if (s == null || s.length != 2)
				return null;
			map.put(s[0], s[1]);
		}
		return map;
	}
	
	public static String getImg(String path, String imgUrl) throws IOException {
		URL url = new URL(imgUrl);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		InputStream inStream = conn.getInputStream();

		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len = 0;
		while ((len = inStream.read(buffer)) != -1) {
			outStream.write(buffer, 0, len);
		}
		byte[] data = outStream.toByteArray();// 图片的二进制数据

		String split[] = imgUrl.split("/");
		int length = split.length;
		String fileName = split[length - 1];
		File file = new File(path, fileName);
		FileOutputStream fileoutStream = new FileOutputStream(file);
		fileoutStream.write(data);
		fileoutStream.close();
		inStream.close();

		return path + fileName;
	}

	public static List<String> getImageFormHtml(String content) {
		List<String> imgList = new ArrayList<String>();
		String patternStrs = "(?is)<img[^>]*?src=\"([^\"]*)\"[^>]*>(?!((?!</?a\\b).)*</a>)";
		Pattern p = Pattern.compile(patternStrs);
		Matcher m = p.matcher(content);
		while (m.find()) {
			imgList.add(m.group(1));
		}
		return imgList;
	}

	public static String replaceContentWithPic(List<String> oldPicUrl,
			List<String> newPicUrl, String context) {
		int size = newPicUrl.size();
		for (int i = 0; i < oldPicUrl.size(); i++) {
			String nPicUrl = (i >= size ? "" : newPicUrl.get(i));
			context = context.replace(oldPicUrl.get(i), nPicUrl);
		}
		return context;
	}

	public static String filterImageTag(String context) {
		Document doc = Jsoup.parse(context);
		doc.select("img").remove();
		doc.select("[background]").attr("background", "");
		return doc.html();		
	}
	

}
