package com.biz.bizunited.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.biz.bizunited.common.service.CommonService;
import com.biz.bizunited.entity.QrcodeErrorEntity;
import com.biz.bizunited.json.JSONResult;
import com.biz.bizunited.service.QrcodeErrorService;
import com.biz.bizunited.util.StringUtil;

/**
 * 
 *给TPM提供API
 *
 *缺少签名
 */
@Controller
@RequestMapping("api")
public class TmpApi extends BaseController {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private QrcodeErrorService qrcodeErrorService;
	/**
	 * 删除
	 * @param response
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "deleteqrcodeError",method = RequestMethod.POST)
	@ResponseBody
	public JSONResult deleteQrcodeErorr(HttpServletResponse response, HttpServletRequest request, 
			String openId) {
		logger.info("进入删除QrcodeErorr信息 openID {}",openId);
		JSONResult result = new JSONResult();
		if(StringUtil.isEmpty(openId)) {
			result.setCode(JSONResult.CODE_ERROR);
			result.setMsg("传输的OPENID为空");
		} else {
			try {
				int i = qrcodeErrorService.deleteQrcodeErrorByOpenId(openId);
				logger.info("openID {} ,数据删除成功条数：{}",openId,i);
				result.setCode(JSONResult.CODE_SUCCESS);
				result.setMsg("数据删除成功");
			} catch (Exception e) {
				e.printStackTrace();
				result.setCode(JSONResult.CODE_ERROR);
				result.setMsg("数据删除失败");
			}
		}
		return result;
	}

}
