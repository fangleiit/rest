package com.biz.bizunited.controller;

import java.sql.Connection;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.biz.bizunited.service.DatatransferExecutorService;
import com.biz.bizunited.util.DataTransferUtil;

/**
 * 接收传输过来需要存储的数据
 * @author xiaogang
 *
 */
@Controller
@RequestMapping(value="dataTransfer")
public class DataTransferController extends BaseController{

	@Autowired
	private DataSource dataSource;
	
	@Autowired
	private DatatransferExecutorService datatransferExecutorService;
	
	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> transfer(HttpServletRequest request) {
		logger.info("transfer");
		Map<String, Object> result = new LinkedHashMap<>();
		result.put("code", "0");
		result.put("msg", "transfer fail");
		try {
			String jsonData = request.getParameter("jsonData");
			logger.info("接收到传输的同步json字符串数据："+jsonData);
			if(jsonData == null || "".equals(jsonData.trim())){
				result.put("code", "0");
				result.put("msg", "transfer fail:jsonData is empty！！！");
			}
			
			Connection connection = dataSource.getConnection();
			DataTransferUtil dataTransferUtil = new DataTransferUtil(connection, jsonData);
			//构建SQL
			dataTransferUtil.buildStorageSqlList();
			//存储数据
			int i = dataTransferUtil.storageData();
			result.put("code", "1");
			result.put("msg", "success transfer data row :"+i);
			logger.info("response data:{}",result.toString());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("data transfer fail:{}",e);
			result.put("code", "0");
			result.put("msg", "transfer fail:"+e.getMessage());
		}
		
		return result;
	}
	
	@RequestMapping(value = "ts", method = RequestMethod.GET)
	public ModelAndView name(HttpServletRequest request) {
		logger.info("name");
		ModelAndView mv = new ModelAndView("");
		try {
			
			//
			
			/**
			 * 数据传输步骤，
			 * 1.获取需要传输的数据
			 * 2.将要传输的数据转换成json格式
			 * 3.通过httpclient发起请求，传输数据
			 * 4.获取传输结果，如果反馈结果表示传输成功
			 * 5.通过反馈结果，修改数据库数据的状态
			 */
			datatransferExecutorService.executor();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return mv;
	}
}

