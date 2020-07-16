package com.biz.bizunited.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

/**
 * 
 * @ClassName: UploadFielUtil 
 * @Description: 图片上传
 * @author ian.zeng
 * @date 2017年7月24日 上午10:05:21
 */
public class UploadFileUtil {
	
	public static List<String> uploadPicture(HttpServletRequest request, String filed) throws IOException {
		MultipartHttpServletRequest multipart = (MultipartHttpServletRequest) request;
		List<MultipartFile> files = multipart.getFiles(filed);
		String realPath = multipart.getSession().getServletContext().getRealPath("../../../appealPic");
		File dir = new File(realPath);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		List<String> rs = new ArrayList<String>();
		for (Iterator<MultipartFile> iter = files.iterator(); iter.hasNext();) {
			MultipartFile item =  (MultipartFile) iter.next();
			String fileName = item.getOriginalFilename();
			UUID randomUUID = UUID.randomUUID();
			int i = fileName.lastIndexOf(".");
			String picName = randomUUID.toString()+fileName.substring(i);
			if(!dir.exists()){
				dir.mkdir();
			}
			rs.add(picName);
			String path = realPath+"/"+picName;
			item.transferTo(new File(path));
		}
		return rs;
	}
	
	/**
	 * 
	 * @param destPath 文件添加到哪个位置
	 * @param files    
	 * @return
	 * @throws IOException
	 */
	public static List<String> uploadPicture(String destPath,MultipartFile[] files) throws IOException {
		File dir = new File(destPath);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		List<String> rs = new ArrayList<String>();
		for (MultipartFile item : files) {
			if(item.getSize() > 0){
				String fileName = item.getOriginalFilename();
				UUID randomUUID = UUID.randomUUID();
				int i = fileName.lastIndexOf(".");
				String picName = randomUUID.toString()+fileName.substring(i);
				rs.add(picName);
				String path = destPath+"/"+picName;
				item.transferTo(new File(path));
			}
		}
		return rs;
	}
	
}
