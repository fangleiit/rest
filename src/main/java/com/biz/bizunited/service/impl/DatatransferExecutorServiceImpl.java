package com.biz.bizunited.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.biz.bizunited.constants.Constants;
import com.biz.bizunited.entity.AppealEntity;
import com.biz.bizunited.entity.DrawShiWuEntity;
import com.biz.bizunited.entity.PictrueEntity;
import com.biz.bizunited.entity.PrizeEntity;
import com.biz.bizunited.entity.PutinPrizeEntity;
import com.biz.bizunited.entity.QrcodeInfoEntity;
import com.biz.bizunited.entity.RedpackageOpenEntity;
import com.biz.bizunited.entity.TerminalInfoEntity;
import com.biz.bizunited.entity.WeiXinUserEntity;
import com.biz.bizunited.exception.CommonException;
import com.biz.bizunited.service.AppealService;
import com.biz.bizunited.service.DatatransferExecutorService;
import com.biz.bizunited.service.DrawShiWuService;
import com.biz.bizunited.service.PictrueService;
import com.biz.bizunited.service.PrizeConfigService;
import com.biz.bizunited.service.QrcodeInfoService;
import com.biz.bizunited.service.RedpackageOpenService;
import com.biz.bizunited.service.TerminalInfoService;
import com.biz.bizunited.service.WeiXinUserService;
import com.biz.bizunited.util.CollectionUtil;
import com.biz.bizunited.util.DataTransferUtil;
import com.biz.bizunited.util.HttpClientUtil;
import com.biz.bizunited.util.StringUtil;
import com.biz.bizunited.vo.DataTransferVO;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
@Transactional(rollbackFor={CommonException.class,RuntimeException.class,Exception.class})
public class DatatransferExecutorServiceImpl implements DatatransferExecutorService {

	private Logger logger = LoggerFactory.getLogger(DatatransferExecutorServiceImpl.class);
	
	List<DataTransferVO> list = null;
	
	@Autowired
	private DrawShiWuService drawShiWuService;
	
	
	@Autowired
	private AppealService appealService;
	
	@Autowired
	private QrcodeInfoService qrcodeInfoService;
	
	@Autowired
	private RedpackageOpenService redpackageOpenService;
	
	@Autowired
	private TerminalInfoService terminalInfoService;
	
	@Autowired
	private WeiXinUserService weiXinUserService;
	
	@Autowired
	private PictrueService pictrueService;
	
	@Autowired
	private PrizeConfigService prizeConfigService;
	
	@Override
	public void executor() throws Exception {
		/**
		 * 数据传输步骤，
		 * 1.获取需要传输的数据
		 * 2.将要传输的数据转换成json格式
		 * 3.通过httpclient发起请求，传输数据
		 * 4.获取传输结果，如果反馈结果表示传输成功
		 * 5.通过反馈结果，修改数据库数据的状态
		 */
		//查询需要修改数据
		List<DrawShiWuEntity> drawShiWuEntities = drawShiWuService.findListByStatus("0", 1, Constants.SYNC_COUNT);
		List<AppealEntity> appealEntities = appealService.findListByStatus("0", 1, Constants.SYNC_COUNT);
		List<QrcodeInfoEntity> qrcodeInfoEntities = qrcodeInfoService.findListByStatus("0", 1, Constants.SYNC_COUNT);
		List<RedpackageOpenEntity> redpackageOpenEntities = redpackageOpenService.findListByStatus("0", 1, Constants.SYNC_COUNT);
		List<TerminalInfoEntity> terminalInfoEntities = terminalInfoService.findListByStatus("0", 1, Constants.SYNC_COUNT);
		List<WeiXinUserEntity> weiXinUserEntities = weiXinUserService.findListByStatus("0", 1, Constants.SYNC_COUNT);
		List<PictrueEntity> pictrueEntities = pictrueService.findListByStatus("0", 1, Constants.SYNC_COUNT);
		List<PrizeEntity> prizeEntities = prizeConfigService.findListByStatus("0", 1, Constants.SYNC_COUNT);
		List<PutinPrizeEntity> putinPrizeEntities = prizeConfigService.findPutinPrizeListByStatus("0", 1, Constants.SYNC_COUNT);
		//转换
		DataTransferUtil dataTransferUtil = new DataTransferUtil();
		//转json数据
		ObjectMapper objectMapper = new ObjectMapper();
		list = new ArrayList<>();
		if(CollectionUtil.listNotEmptyNotSizeZero(drawShiWuEntities)){
			DataTransferVO drawVO = dataTransferUtil.object2DataTransVo(drawShiWuEntities);
			add2List(drawVO);
		}
		if(CollectionUtil.listNotEmptyNotSizeZero(appealEntities)){
			DataTransferVO appealVO = dataTransferUtil.object2DataTransVo(appealEntities);
			add2List(appealVO);
		}
		if(CollectionUtil.listNotEmptyNotSizeZero(qrcodeInfoEntities)){
			DataTransferVO qrcodeVO = dataTransferUtil.object2DataTransVo(qrcodeInfoEntities);
			add2List(qrcodeVO);
		}
		if(CollectionUtil.listNotEmptyNotSizeZero(redpackageOpenEntities)){
			DataTransferVO redpackVO = dataTransferUtil.object2DataTransVo(redpackageOpenEntities);
			add2List(redpackVO);
		}
		if(CollectionUtil.listNotEmptyNotSizeZero(terminalInfoEntities)){
			DataTransferVO terminalVO = dataTransferUtil.object2DataTransVo(terminalInfoEntities);
			add2List(terminalVO);
		}
		if(CollectionUtil.listNotEmptyNotSizeZero(weiXinUserEntities)){
			for (WeiXinUserEntity weiXinUserEntity : weiXinUserEntities) {
				if(weiXinUserEntity.getNickname().indexOf("'") > -1){
					weiXinUserEntity.setNickname(weiXinUserEntity.getNickname().replaceAll("'", ""));
				}
			}
			DataTransferVO weixinUserVO = dataTransferUtil.object2DataTransVo(weiXinUserEntities);
			add2List(weixinUserVO);
		}
		if(CollectionUtil.listNotEmptyNotSizeZero(pictrueEntities)){
			DataTransferVO pictrueVO = dataTransferUtil.object2DataTransVo(pictrueEntities);
			add2List(pictrueVO);
		}
		if(CollectionUtil.listNotEmptyNotSizeZero(prizeEntities)){
			DataTransferVO prizeConfigVO = dataTransferUtil.object2DataTransVo(prizeEntities);
			add2List(prizeConfigVO);
		}
		if(CollectionUtil.listNotEmptyNotSizeZero(putinPrizeEntities)){
			DataTransferVO putinPrize = dataTransferUtil.object2DataTransVo(putinPrizeEntities);
			add2List(putinPrize);
		}
		if(CollectionUtil.listNotEmptyNotSizeZero(list)){
			String jsonData = objectMapper.writeValueAsString(list);
			logger.info("jsonData：{}",jsonData);
			String dataTransferUrl = ResourceBundle.getBundle("bizunited").getString("eisp.datatransfer.url");
			//请求
			Map<String, String> map = new HashMap<String, String>();
			map.put("jsonData", jsonData);
			String charset = "UTF-8";
			String resp = null;
			try {
				resp = HttpClientUtil.doPost(dataTransferUrl, map, charset);
			} catch (Exception e) {
				logger.error("同步数据失败,{}", e.getMessage());
				throw new CommonException("推送失败,"+e.getMessage());
			}
			logger.info("resp :{}",resp);
			if (resp != null && !"".equals(resp.trim())) {
				Map<String,String> respMap = objectMapper.readValue(resp, Map.class);
				String code = respMap.get("code");
				//String msg = respMap.get("msg");
				logger.info("resp code：{}",code);
				if (StringUtil.isEmpty(code) || (StringUtil.isNotEmpty(code)) && "1".equals(code)) {
					//修改状态
					logger.info("数据传输成功，开始修改已经传输成功的数据状态");
					drawShiWuService.updateStatus(drawShiWuEntities);
					appealService.updateStatus(appealEntities);
					qrcodeInfoService.updateStatus(qrcodeInfoEntities);
					redpackageOpenService.updateStatus(redpackageOpenEntities);
					terminalInfoService.updateStatus(terminalInfoEntities);
					weiXinUserService.updateStatus(weiXinUserEntities);
					pictrueService.updateStatus(pictrueEntities);
					prizeConfigService.updateStatus(prizeEntities);
					prizeConfigService.updatePutinPrizStatus(putinPrizeEntities);
					logger.info("修改已经传输成功的数据状态---完成");
				}
			}else{
				throw new CommonException("推送失败");
			}
		}
	}
	
	private void add2List(DataTransferVO e){
		if (e != null) {
			list.add(e);
		}
	}
}
