package com.biz.bizunited.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.biz.bizunited.constants.Constants;
import com.biz.bizunited.entity.PictrueEntity;
import com.biz.bizunited.service.QrcodeActivityService;

@Controller
@RequestMapping("sys_file")
public class ImageController extends BaseController{

	@Autowired
	private QrcodeActivityService qrcodeActivityService;
	
	//??????????????????????
	@RequestMapping(value = "/{pictureId}", method = RequestMethod.GET)
	public void defaultImgFile(@PathVariable String pictureId,HttpServletRequest req,HttpServletResponse resp) {
		ServletOutputStream outputStream = null;
		try {
			logger.info("defaultImgFile");
			resp.setContentType("image/gif");
			
			PictrueEntity entity = qrcodeActivityService.findUniqueByProperty(PictrueEntity.class, "id", pictureId);
			String picName = entity.getPicName();
			String path = req.getSession().getServletContext().getRealPath(Constants.imgBaseProjectSuffPath);
			File file = new File(path+File.separator+picName);
			
			InputStream inputStream = new FileInputStream(file);
			
			int available = inputStream.available();
			byte[] buf = new byte[available];
			inputStream.read(buf);
			inputStream.close();
			
			outputStream = resp.getOutputStream();
			outputStream.write(buf);
			outputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
			
		}
		
	}
	
	@RequestMapping(value = "/uploadImg", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> uploadImg(HttpServletRequest request,String imgContent) {
		logger.info("uploadImg");
		Map<String, Object> map = new HashMap<String, Object>();
		
		try {
			if (imgContent != null && imgContent.lastIndexOf(",") > -1) {
				int lastIndexOf = imgContent.lastIndexOf(",");
				imgContent = imgContent.substring(lastIndexOf + 1);
			}
			
			Serializable id = qrcodeActivityService.uploadPicture(request, imgContent);
			map.put("id", id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return map;
	}
	
	@RequestMapping(value = "/delImg", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> delImg(HttpServletRequest request) {
		logger.info("delImg");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("code", 0);
		map.put("msg", "fail");
		String imgId = request.getParameter("imgId");
		
		try {
			if (imgId != null && !"".equals(imgId)) {
				PictrueEntity pictrueEntity = new PictrueEntity();
				pictrueEntity.setId(imgId);
				qrcodeActivityService.delete(pictrueEntity);
				map.put("code", 1);
				map.put("msg", "ok");
			}
		} catch (Exception e) {
			e.printStackTrace();
			map.put("msg", e.toString());
		}
		return map;
	}
	
}
