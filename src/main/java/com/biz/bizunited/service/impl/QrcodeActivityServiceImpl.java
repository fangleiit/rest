package com.biz.bizunited.service.impl;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSONObject;
import org.jeecgframework.minidao.hibernate.dao.IGenericBaseCommonDao;
import org.jeecgframework.minidao.util.MyBeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.biz.bizunited.common.AjaxJson;
import com.biz.bizunited.common.service.impl.CommonServiceImpl;
import com.biz.bizunited.constants.Constants;
import com.biz.bizunited.dao.QrcodeActivityDao;
import com.biz.bizunited.entity.AppealEntity;
import com.biz.bizunited.entity.DrawShiWuEntity;
import com.biz.bizunited.entity.PictrueEntity;
import com.biz.bizunited.entity.PrizeEntity;
import com.biz.bizunited.entity.PutinPrizeEntity;
import com.biz.bizunited.entity.QrcodeInfoEntity;
import com.biz.bizunited.entity.RedpackageOpenEntity;
import com.biz.bizunited.entity.TerminalInfoEntity;
import com.biz.bizunited.entity.WeChatPaymentRecordEntity;
import com.biz.bizunited.exception.CommonException;
import com.biz.bizunited.exception.CommonRuntimeException;
import com.biz.bizunited.service.QrcodeActivityService;
import com.biz.bizunited.service.TransferService;
import com.biz.bizunited.service.ValidateRuleService;
import com.biz.bizunited.util.CollectionUtil;
import com.biz.bizunited.util.DateUtils;
import com.biz.bizunited.util.FileUtils;
import com.biz.bizunited.util.MapsUtil;
import com.biz.bizunited.util.SMSUtil;
import com.biz.bizunited.util.StringUtil;
import com.biz.bizunited.util.UploadFileUtil;
import com.biz.bizunited.vo.AddressVo;
import com.biz.bizunited.vo.PrizeVo;
import com.biz.bizunited.vo.QrcodeActInfoVo;
import com.biz.bizunited.vo.QrcodeChannelVo;
import com.biz.bizunited.vo.QrcodeParamVo;
import com.biz.bizunited.vo.QrcodeUserInfoSeriesVo;
import com.biz.bizunited.vo.RedpackageOpenVo;
import com.biz.bizunited.vo.RedpackageVo;
import com.biz.bizunited.vo.TerminalInfoVo;
import com.biz.bizunited.wechat.payment.res.TransferResponse;
import com.biz.bizunited.wechat.util.WeixinUtil;




@Service("qrcodeActivityService")
@Transactional
public class QrcodeActivityServiceImpl extends CommonServiceImpl implements QrcodeActivityService {
	
	private static final Logger logger = LoggerFactory.getLogger(QrcodeActivityServiceImpl.class);
	@Autowired
	private QrcodeActivityDao qrcodeActivityDao;
	@Autowired
	private IGenericBaseCommonDao commonDao;
	@Autowired
	private TransferService transferService;
	@Autowired
	private ValidateRuleService validateRuleService;
	@Value("${str.pay}")
    protected String strPay;
	@Override
	public RedpackageOpenVo findRedpackageByQrcode(String qrcode) {
		RedpackageOpenVo redpackageOpenVo = qrcodeActivityDao.findRedpackageByQrcode(qrcode);
		return redpackageOpenVo;
	}
	
	public AjaxJson openRedpackage(QrcodeParamVo paramVo) throws Exception{
		logger.info("---开奖start---");
		String message = "";
		AjaxJson j = new AjaxJson();
		if(StringUtil.isEmpty(paramVo.getOpenId())){
			paramVo.setOpenId(WeixinUtil.getUserOpenId());
		}
		//红包记录
		RedpackageOpenVo redpackageOpenVo = qrcodeActivityDao.findRedpackageByQrcode(paramVo.getQrcode());
		//用户信息
		TerminalInfoVo terminalInfoVo = qrcodeActivityDao.findTerminalInfoByOpenId(paramVo.getOpenId());
		//查询奖项
		List<PrizeVo> prizeVos = qrcodeActivityDao.findPrizeListByActId(paramVo.getActId());
		if(!CollectionUtil.listNotEmptyNotSizeZero(prizeVos)){
			throw new CommonException("已无可中奖，请联系工作人员");
		}
		//判断扫码人是否是黑名单和黄名单
		if(terminalInfoVo != null){
			if(terminalInfoVo.getWhitelist() == 0){
				if(terminalInfoVo.getIsBlacklist() == 1){
					//黑名单
					j.setCode(-1);
					j.setMsg("黑名单");
					return j;
				}
				if(terminalInfoVo.getIsYellowlist() == 1){
					logger.info("黄名单中奖：" + paramVo.getOpenId());
					if(redpackageOpenVo ==null){
						//黄名单
						for (PrizeVo prizeVo : prizeVos) {
							if("1".equals(prizeVo.getYellowlist())){
								prizeVo = getBonus(prizeVo);
								//返回黄名单中奖金额
								String redId=this.saveOpenRedPackage(prizeVo, paramVo.getQrcode(), paramVo.getOpenId());
								prizeVo.setRedId(redId);
								updateOutCount(prizeVo);
								j.setObj(prizeVo);
								return j;
							}
						}
					}else{
						if(paramVo.getOpenId().equals(redpackageOpenVo.getLastName())){
							if(StringUtil.isNotEmpty(redpackageOpenVo.getTakeName())){
								PrizeEntity prizeEntity = commonDao.get(PrizeEntity.class, redpackageOpenVo.getPrize());
								PrizeVo vo = new PrizeVo();
								if(prizeEntity.getPtype() == 2){
									j.setCode(9);
									message = "恭喜您！您已获得"+prizeEntity.getRemark()+"实物奖";
									vo.setRedId(redpackageOpenVo.getId());
								}else{
									String takeTime = redpackageOpenVo.getTakeTime();
									BigDecimal money = redpackageOpenVo.getBonus();
									message = "您已于" + takeTime + "领取过此红包";
									vo.setTakeTime(takeTime);
									vo.setBonus(money);
									j.setCode(0);
								}
								j.setMsg(message);
								j.setObj(vo);
								return j;
							}else{
								//奖未被领取
								BigDecimal bonus = redpackageOpenVo.getBonus();
								PrizeVo prizeVo = new PrizeVo();
								prizeVo.setBonus(bonus);
								RedpackageOpenEntity openEntity = new RedpackageOpenEntity();
								openEntity = commonDao.get(RedpackageOpenEntity.class, redpackageOpenVo.getId());
								openEntity.setLastName(paramVo.getOpenId());
								openEntity.setScanCode(openEntity.getScanCode()+1);
								openEntity.setDataStatus("0");
								commonDao.saveOrUpdate(openEntity);
								prizeVo.setRedId(openEntity.getId());
								j.setObj(prizeVo);
								return j;
							}
						}else{
							if(StringUtil.isNotEmpty(redpackageOpenVo.getTakeName())){
								message += "红包已于" + redpackageOpenVo.getTakeTime() + "被领取";
								PrizeVo vo = new PrizeVo();
								vo.setBonus(redpackageOpenVo.getBonus());
								vo.setTakeTime(redpackageOpenVo.getTakeTime());
								vo.setTakeName(redpackageOpenVo.getTakeName());
								vo.setRedId(redpackageOpenVo.getId());
								j.setCode(2);
								j.setMsg(message);
								j.setObj(vo);
								return j;
							}else{
								for (PrizeVo prizeVo : prizeVos) {
									if("1".equals(prizeVo.getYellowlist())){
										prizeVo = getBonus(prizeVo);
										//返回黄名单中奖金额
										String redId=this.saveOpenRedPackage(prizeVo, paramVo.getQrcode(), paramVo.getOpenId());
										prizeVo.setRedId(redId);
										updateOutCount(prizeVo);
										j.setObj(prizeVo);
										return j;
									}
								}
							}
						}
					}
				}
				//验证是否可加入黄名单
				joinYellowList(terminalInfoVo);
			}
		//正常用户
		}else{
			//二维码未被扫过
			if(redpackageOpenVo == null){
				List<RedpackageOpenVo> openVos = qrcodeActivityDao.findRedpackageOpenByOpenId(paramVo.getOpenId());
				if(CollectionUtil.listEmpty(openVos)){
					logger.info("首次扫码中奖：" + paramVo.getOpenId());
					//该用户为第一次扫码
					List<PrizeVo> firstList = qrcodeActivityDao.findFirstPrize(paramVo.getActId());
					PrizeVo prizeVo = new PrizeVo();
					if(!CollectionUtil.listNotEmptyNotSizeZero(firstList)){
						firstList = prizeVos;
						prizeVo = getPrize(firstList);
					}else{
						prizeVo = getFirstPrize(firstList, paramVo.getActId());
					}
					prizeVo = getBonus(prizeVo);
					String redId = this.saveOpenRedPackage(prizeVo, paramVo.getQrcode(), paramVo.getOpenId());
					prizeVo.setRedId(redId);
					updateOutCount(prizeVo);
					j.setObj(prizeVo);
					return j;
				}
			//二维码已被扫过
			}else{
				if(redpackageOpenVo.getTakeName() == null || "".equals(redpackageOpenVo.getTakeName())){
					List<RedpackageOpenVo> openVos = qrcodeActivityDao.findRedpackageOpenByOpenId(paramVo.getOpenId());
					if(CollectionUtil.listEmpty(openVos)){
						logger.info("新用户开已被其他人扫过的码");
						//将已开出未领取的奖退回
						this.bakePrize(redpackageOpenVo.getPrize());
						//该用户为第一次扫码
						List<PrizeVo> firstList = qrcodeActivityDao.findFirstPrize(paramVo.getActId());
						//未设置首次扫码中奖奖项时在所有奖项中抽奖
						PrizeVo prizeVo = new PrizeVo();
						if(!CollectionUtil.listNotEmptyNotSizeZero(firstList)){
							firstList = prizeVos;
							prizeVo = getPrize(firstList);
						}else{
							prizeVo = getFirstPrize(firstList, paramVo.getActId());
						}
						//String bonus = getFistBonus(prizeVos);
						prizeVo = getBonus(prizeVo);
						String redId = this.saveOpenRedPackage(prizeVo, paramVo.getQrcode(), paramVo.getOpenId());
						prizeVo.setRedId(redId);
						updateOutCount(prizeVo);
						//result = new JSONResult(prizeVo);
						j.setObj(prizeVo);
						return j;
					}
				}
			}
		}
		if(redpackageOpenVo == null){
			//redpackageOpenVo为空时则该二维码未被扫过
			//先判断用户当天是否已中过大奖
			SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
			String nowDate = dateformat.format(new Date());
			Integer count = qrcodeActivityDao.whetherBig(paramVo.getOpenId(), nowDate);
			if(count >= 1 ){
				logger.info("正常用户，已中过大奖");
				//已中过大奖
				List<PrizeVo> nobigs= qrcodeActivityDao.findNoBigPrize(paramVo.getActId());
				//PrizeVo prizeVo = getPrizeNoBig(prizeList);
				PrizeVo prizeVo = getPrize(nobigs);
				if(prizeVo.getPtype() == 1){
					prizeVo = getBonus(prizeVo);
				}else{
					//实物奖直接修改领取人
					j.setCode(9);
					j.setMsg("恭喜你获得"+prizeVo.getRemark()+"实物奖");
				}
				
				String redId = this.saveOpenRedPackage(prizeVo, paramVo.getQrcode(), paramVo.getOpenId());
				prizeVo.setRedId(redId);
				updateOutCount(prizeVo);
				//result = new JSONResult(prizeVo);
				j.setObj(prizeVo);
				return j;
			}else{
				logger.info("正常用户，未中过大奖");
				//未中过大奖
				//先判断是否有大奖可中
				PrizeVo prizeVo = new PrizeVo();
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String date = format.format(new Date());
				String area = "";
				String phone = "";
				String city = "";
				if(terminalInfoVo != null){
					if(StringUtil.isNotEmpty(terminalInfoVo.getArea())){
						area = terminalInfoVo.getArea();
					}
					if(StringUtil.isNotEmpty(terminalInfoVo.getMobilePhone())){
						phone = terminalInfoVo.getMobilePhone();
					}
					if(StringUtil.isNotEmpty(terminalInfoVo.getCity())){
						city = terminalInfoVo.getCity();
					}
				}
				QrcodeInfoEntity qrcodeInfo = commonDao.findUniqueByProperty(QrcodeInfoEntity.class, "qrcode", paramVo.getQrcode());
				if(qrcodeInfo != null){
					if(qrcodeInfo.getCustomerCode() != null){
						paramVo.setCustCode(qrcodeInfo.getCustomerCode());
					}
				}
				List<PrizeVo> putinList = new ArrayList<PrizeVo>();
				putinList = qrcodeActivityDao.findPutinPrizeList(paramVo.getCustCode(),city,area, date, paramVo.getActId(),phone);
				if (CollectionUtil.listNotEmptyNotSizeZero(putinList)) {
					//有手工投放奖可中
					prizeVo = getPrize(putinList);
					//更新手工投放奖数量
					updatePutinNumber(prizeVo);
				}else{
					//无手工投放的奖
					prizeVo = getPrize(prizeVos);
					updateOutCount(prizeVo);
				}
				if(prizeVo.getPtype() != null){
					if(prizeVo.getPtype() == 1){
						//result = new JSONResult(prizeVo);
						prizeVo = getBonus(prizeVo);
					}else{
						//实物奖直接修改领取人
						j.setCode(9);
						j.setMsg("恭喜你获得"+prizeVo.getRemark()+"实物奖");
					}
				}
				String redId = this.saveOpenRedPackage(prizeVo, paramVo.getQrcode(), paramVo.getOpenId());
				prizeVo.setRedId(redId);
				//result = new JSONResult(prizeVo);
				j.setObj(prizeVo);
				return j;
			}
		}else{
			//码已被扫，奖已开出
			if(paramVo.getOpenId().equals(redpackageOpenVo.getLastName())){
				logger.info("码已被扫过，同一人扫码");
				//同一人扫码
				if(redpackageOpenVo.getTakeName() != null && !"".equals(redpackageOpenVo.getTakeName())){
					//满足条件时奖已被领,结束
					PrizeVo vo = new PrizeVo();
					PrizeEntity prizeEntity = commonDao.get(PrizeEntity.class, redpackageOpenVo.getPrize());
					if(prizeEntity.getPtype() == 2){
						j.setCode(9);
						j.setMsg("恭喜您！您已获得"+prizeEntity.getRemark()+"实物奖");
						vo.setRedId(redpackageOpenVo.getId());
					}else{
						String takeTime = redpackageOpenVo.getTakeTime();
						BigDecimal money = redpackageOpenVo.getBonus();
						message = "您已于" + takeTime + "领取过此红包";
						vo.setTakeTime(takeTime);
						vo.setBonus(money);
						j.setCode(0);
						j.setMsg(message);
					}
					j.setObj(vo);
					return j;
				}else{
					//奖未被领取
					BigDecimal bonus = redpackageOpenVo.getBonus();
					PrizeVo prizeVo = new PrizeVo();
					prizeVo.setBonus(bonus);
					RedpackageOpenEntity openEntity = new RedpackageOpenEntity();
					openEntity = commonDao.get(RedpackageOpenEntity.class, redpackageOpenVo.getId());
					openEntity.setLastName(paramVo.getOpenId());
					openEntity.setScanCode(openEntity.getScanCode()+1);
					commonDao.saveOrUpdate(openEntity);
					prizeVo.setRedId(openEntity.getId());
					//result = new JSONResult(prizeVo);
					j.setObj(prizeVo);
					return j;
				}
			}else{
				logger.info("码已被扫过，不是同一人扫码");
				//不是同一人扫码
				if(redpackageOpenVo.getTakeName() != null && !"".equals(redpackageOpenVo.getTakeName())){
					//奖已被领，弹出申诉页面
					//message += "中奖金额" + redpackageOpenVo.getBonus() + "元,";
					message += "红包已于" + redpackageOpenVo.getTakeTime() + "被领取";
					//result = new JSONResult(2, message);
					PrizeVo vo = new PrizeVo();
					vo.setBonus(redpackageOpenVo.getBonus());
					vo.setTakeTime(redpackageOpenVo.getTakeTime());
					vo.setTakeName(redpackageOpenVo.getTakeName());
					vo.setRedId(redpackageOpenVo.getId());
					j.setCode(2);
					j.setMsg(message);
					j.setObj(vo);
					return j;
				}else{
					//未被领取
					BigDecimal bonus = redpackageOpenVo.getBonus();
					PrizeVo prizeVo = new PrizeVo();
					prizeVo.setBonus(bonus);
					//码已被扫过，未被领取，更新新的最后扫码人
					//String updateSql = "update qrcode_redpackage_open set last_name='"+openId+"' where id='"+redpackageOpenVo.getId()+"'";
					RedpackageOpenEntity openEntity = new RedpackageOpenEntity();
					openEntity = commonDao.get(RedpackageOpenEntity.class, redpackageOpenVo.getId());
					openEntity.setLastName(paramVo.getOpenId());
					openEntity.setScanCode(openEntity.getScanCode()+1);
					commonDao.saveOrUpdate(openEntity);
					prizeVo.setRedId(openEntity.getId());
					//result = new JSONResult(prizeVo);
					j.setObj(prizeVo);
					return j;
				}
			}
		}
	}
	public void joinYellowList(TerminalInfoVo terminalInfoVo){
		/*if(terminalInfoVo.getLongitude() != null && !"".equals(terminalInfoVo.getLongitude()) 
				&& terminalInfoVo.getLatitude() != null && !"".equals(terminalInfoVo.getLatitude())){
			//验证用户是否可加入黄名单
			validateRuleService.joinYellowList(terminalInfoVo);
		}*/
		if(terminalInfoVo != null && terminalInfoVo.getIsYellowlist() != 1){
			validateRuleService.joinYellowList(terminalInfoVo);
		}
	}
	/**
	 * 
	 * @param id
	 */
	public void bakePrize(String id){
		PrizeEntity e = commonDao.getEntity(PrizeEntity.class, id);
		e.setOutCount(e.getOutCount()-1);
		commonDao.saveOrUpdate(e);
	}
	/**
	 * 更新手工投放奖的数量
	 * @param prizeVo
	 */
	public void updatePutinNumber(PrizeVo prizeVo){
		/*PrizeEntity prizeEntity = commonDao.get(PrizeEntity.class, prizeVo.getId());
		if(prizeEntity != null){
			int outCount = prizeEntity.getOutCount();
			prizeEntity.setOutCount(outCount+1);
			commonDao.saveOrUpdate(prizeEntity);
		}*/
		//PutinPrizeEntity putin = commonDao.findUniqueByProperty(PutinPrizeEntity.class, "putinId", prizeVo.getPutinId());
		PutinPrizeEntity putin = commonDao.getEntity(PutinPrizeEntity.class, prizeVo.getPutinId());
		if(putin != null){
			int num = putin.getSNumber();
			putin.setSNumber(num-1);
			putin.setDataStatus("0");
			commonDao.saveOrUpdate(putin);
		}
	}
	/**
	 * 更新奖项已投放数量
	 * @param prizeVo
	 */
	public void updateOutCount(PrizeVo prizeVo){
		logger.info("更新奖项数量：id{} ,pname{}",prizeVo.getId(),prizeVo.getPname());
		PrizeEntity prizeEntity = commonDao.get(PrizeEntity.class, prizeVo.getId());
		if(prizeEntity != null){
			int outCount = prizeEntity.getOutCount();
			prizeEntity.setOutCount(outCount+1);
			prizeEntity.setDataStatus("0");
			commonDao.saveOrUpdate(prizeEntity);
		}
	}
	/**
	 * 获取首次扫码中奖金额
	 * @param prizeVos
	 * @return
	 */
	@Deprecated
	public String getFistBonus(List<PrizeVo> prizeVos){
		String bonus = "";
		for (PrizeVo prizeVo: prizeVos) {
			if("1".equals(prizeVo.getFirst())){
				//bonus = getBonus(prizeVo);
			}
		}
		return bonus;
	}
	/**
	 * 获取奖金
	 * @param prizeVo
	 * @return
	 */
	public PrizeVo getBonus(PrizeVo prizeVo){
		logger.info("获取中奖金额开始");
		List<BigDecimal> moneys = new ArrayList<BigDecimal>();
		if(prizeVo.getMoney1() != null){
			moneys.add(prizeVo.getMoney1());
		}
		if(prizeVo.getMoney2() != null){
			moneys.add(prizeVo.getMoney2());
		}
		if(prizeVo.getMoney3() != null){
			moneys.add(prizeVo.getMoney3());
		}
		if(prizeVo.getMoney4() != null){
			moneys.add(prizeVo.getMoney4());
		}
		if(prizeVo.getMoney5() != null){
			moneys.add(prizeVo.getMoney5());
		}
		Random random = new Random();
		if(CollectionUtil.listNotEmptyNotSizeZero(moneys)){
			int len = moneys.size();
			int index = random.nextInt(len);
			BigDecimal bonus = moneys.get(index);
			prizeVo.setBonus(bonus);
		}else{
			//进入此步奖项设置有问题
			logger.debug("奖项金额设置错误");
			prizeVo.setBonus(new BigDecimal(0));
		}
		logger.info("获取中奖金额结束");
		return prizeVo;
	}
	/**
	 * 获取已中过大奖用户中奖奖项
	 * @param prizeVos
	 * @return
	 */
	@Deprecated
	public PrizeVo getPrizeNoBig(List<PrizeVo> prizeVos){
		PrizeVo prizeVo = new PrizeVo();
		List<PrizeVo> list = prizeVos;
		int count = 0;
		for (PrizeVo vo : list) {
			if(!"1".equals(vo.getBig())){
				count += vo.getCount()-vo.getOutCount();
			}else{
				list.remove(vo);
			}
		}
		prizeVo = getPrize(list);
		return prizeVo;
	}
	/**
	 * 获取中奖奖项
	 * @param prizeVos
	 * @return
	 */
	public PrizeVo getPrize(List<PrizeVo> prizeVos){
		logger.info("获取奖项开始");
		PrizeVo prizeVo = new PrizeVo();
		int count = 0 ;
		int num = 0;
		for (PrizeVo vo : prizeVos) {
			count += vo.getCount()-vo.getOutCount();
		}
		if(count <= 0){
			//已无奖可领,获取固定奖
			for (PrizeVo vo : prizeVos) {
				if("1".equals(vo.getFixed())){
					prizeVo = vo;
					break;
				}
			}
			return prizeVo;
		}
		Random rand = new Random();
		int randNum = rand.nextInt(count);
		for (PrizeVo vo : prizeVos) {
			num += vo.getCount()-vo.getOutCount();
			if(randNum < num){
				prizeVo = vo;
				break;
			}
		}
		logger.info("获取奖项完成");
		return prizeVo;
	}
	public PrizeVo getFirstPrize(List<PrizeVo> prizeVos,String actId){
		logger.info("获取首次中奖开始");
		PrizeVo prizeVo = null;
		int count = 0 ;
		int num = 0;
		for (PrizeVo vo : prizeVos) {
			count += vo.getCount()-vo.getOutCount();
		}
		if(count <= 0){
			//首次中奖金额已被领完，从其他奖项获取
			/*List<PrizeVo> list = qrcodeActivityDao.findPrizeListByActId(actId);
			prizeVo = getPrize(list);
			return prizeVo;*/
			//首次中奖不控制次数
			count = 2;
		}
		Random rand = new Random();
		int randNum = rand.nextInt(count);
		for (PrizeVo vo : prizeVos) {
			num += vo.getCount()-vo.getOutCount();
			if(randNum < num){
				prizeVo = vo;
				break;
			}
		}
		if(prizeVo == null){
			prizeVo = prizeVos.get(0);
		}
		logger.info("获取首次中奖完成");
		return prizeVo;
	}
	/**
	 * 保存红包开奖记录
	 * @throws Exception 
	 */
	public String saveOpenRedPackage(PrizeVo prizeVo,String qrcode,String openId) throws Exception {
		logger.info("保存红包记录开始：qrcode{} ",qrcode);
		RedpackageOpenEntity openEntity = new RedpackageOpenEntity();
		RedpackageOpenEntity t = commonDao.findUniqueByProperty(RedpackageOpenEntity.class, "qrcode", qrcode);
		Integer abnormalStatus = getAbnormalStatus(openId);
		openEntity.setBonus(prizeVo.getBonus());
		openEntity.setQrcode(qrcode);
		openEntity.setLastName(openId);
		openEntity.setPrize(prizeVo.getId());
		openEntity.setIsSendout(0);
		openEntity.setActId(prizeVo.getActId());
		openEntity.setCreateTime(DateUtils.getDate());
		openEntity.setScanCode(1);
		openEntity.setDataStatus("0");
		openEntity.setAbnormalStatus(abnormalStatus);
		if(prizeVo.getPtype() != null){
			if(prizeVo.getPtype() == 2){
				openEntity.setTakeName(openId);
				openEntity.setTakeTime(DateUtils.getDate());
				openEntity.setIsSendout(1);
				openEntity.setBonus(prizeVo.getPrice());
			}
		}
		String id = "";
		if(t != null){
			//对象拷贝
			MyBeanUtils.copyBeanNotNull2Bean(openEntity, t);
			commonDao.saveOrUpdate(t);
			id = t.getId();
			logger.info("保存红包记录完成");
		}else{
			commonDao.save(openEntity);
			id = openEntity.getId();
		}
		return id;
	}
	public Integer getAbnormalStatus(String openid){
		Integer abnormalStatus = 10;
		//当前活动所属名单状态 10:正常 20:白名单 30:黄名单 40:黑名单
		TerminalInfoEntity terminalInfo = commonDao.findUniqueByProperty(TerminalInfoEntity.class, "openid", openid);
		if(terminalInfo != null){
			if(terminalInfo.getWhitelist() == 1){
				abnormalStatus = 20;
			}else if(terminalInfo.getIsYellowlist() == 1){
				abnormalStatus = 30;
			}else if(terminalInfo.getIsBlacklist() == 1){
				abnormalStatus = 40;
			}
		}
		return abnormalStatus;
	}
	@Override
	public List<RedpackageOpenVo> findRedpackageOpenList(QrcodeParamVo paramVo) {
		paramVo.setActId("");//变更，不做活动限制
		List<RedpackageOpenVo> openVos = qrcodeActivityDao.findRedpackageOpenList(paramVo);
		for (RedpackageOpenVo redpackageOpenVo : openVos) {
			if(redpackageOpenVo.getBonus() == null || "".equals(redpackageOpenVo.getBonus())){
				redpackageOpenVo.setBonus(new BigDecimal(0));
			}
		}
		return openVos;
	}

	@Override
	public QrcodeUserInfoSeriesVo findInfoConfig(String actId) {
		QrcodeUserInfoSeriesVo vo = qrcodeActivityDao.findInfoConfig(actId);
		return vo;
	}

	@Override
	public Integer findScanCodeByOpenId(String openId, String actId) {
		Integer count = qrcodeActivityDao.findScanCodeByOpenId(openId, actId);
		return count;
	}
	@Override
	public Integer findScanCodeByOpenId(String openId) {
		Integer count = qrcodeActivityDao.findScanCodeByOpenId(openId);
		return count;
	}
	
	public void WeChatPay(QrcodeParamVo paramVo){
		//从配置文件获取appid
		ResourceBundle resource = ResourceBundle.getBundle("weixinConfig");
		String appid = resource.getString("weixin.appid");
		Integer amount = 0;
		WeChatPaymentRecordEntity recordEntity = new WeChatPaymentRecordEntity();
		if(StringUtil.isNotEmpty(paramVo.getRedId())){
			RedpackageOpenVo redpackage = qrcodeActivityDao.findAmountByRedId(paramVo.getRedId());
			if(redpackage != null){
				BigDecimal decimal = new BigDecimal(100);
				if(redpackage.getBonus() != null && !"".equals(redpackage.getBonus())){
					decimal = redpackage.getBonus().multiply(decimal);
					int money = decimal.intValue();
					amount = amount + money;
				}
			}
		}
		if("true".equals(paramVo.getFlag())){
			List<RedpackageOpenVo> redpackageList = qrcodeActivityDao.findAmount(paramVo);
			if(CollectionUtil.listNotEmptyNotSizeZero(redpackageList)){
				for (RedpackageOpenVo vo : redpackageList) {
					if(vo.getBonus() != null && !"".equals(vo.getBonus())){
						BigDecimal decimal = new BigDecimal(100);
						decimal = vo.getBonus().multiply(decimal);
						int money = decimal.intValue();
						amount = amount + money;
					}
				}
			}
		}
		recordEntity.setOpenid(paramVo.getOpenId());
		recordEntity.setAmount(amount);
		commonDao.save(recordEntity);
		String orderId = recordEntity.getId();
		String desc = "新品上市 扫码领红包";
		recordEntity.setPayTime(new Date());
		
		//调用微信支付接口
		if("pro".equals(strPay)){
			TransferResponse transferResponse = transferService.payment(appid, paramVo.getOpenId(), amount, orderId, desc);
			if(transferResponse != null){
				recordEntity.setPayCode(transferResponse.getPaymentNo());
				recordEntity.setPayTime(transferResponse.getPaymentTime());
			}
		}
		/*TransferResponse transferResponse = transferService.payment(appid, paramVo.getOpenId(), amount, orderId, desc);
		if(transferResponse != null){
			recordEntity.setPayCode(transferResponse.getPaymentNo());
			recordEntity.setPayTime(transferResponse.getPaymentTime());
		}*/
	}
	@Override
	public void getRedPackageBonus(QrcodeParamVo paramVo) throws CommonException {
		Integer abnormalStatus = getAbnormalStatus(paramVo.getOpenId());
		if(StringUtil.isNotEmpty(paramVo.getRedId())){
			//先修改红包记录领取人
			RedpackageOpenEntity redpackage = commonDao.get(RedpackageOpenEntity.class, paramVo.getRedId());
			if(!paramVo.getOpenId().equals(redpackage.getLastName())){
				String message = "该红包已属于其他人，请重新扫码";
				throw new CommonException(message);
			}
			
			WeChatPay(paramVo);
			redpackage.setTakeName(paramVo.getOpenId());
			redpackage.setTakeTime(DateUtils.getDate());
			redpackage.setIsSendout(1);
			redpackage.setLastName(paramVo.getOpenId());
			redpackage.setDataStatus("0");
			redpackage.setAbnormalStatus(abnormalStatus);
			commonDao.saveOrUpdate(redpackage);
		}else{
			//redId为空时微信公众号提交请求
			WeChatPay(paramVo);
		}
		//红包发放成功后将该用户的所有红包的发放状态改为已发放
		if("true".equals(paramVo.getFlag())){
			List<RedpackageOpenEntity> redpackageList = qrcodeActivityDao.findRedpackageOpenEntityList(paramVo);
			if(CollectionUtil.listNotEmptyNotSizeZero(redpackageList)){
				for (RedpackageOpenEntity redpackageOpenEntity : redpackageList) {
					redpackageOpenEntity.setIsSendout(1);
					redpackageOpenEntity.setTakeTime(DateUtils.getDate());
					redpackageOpenEntity.setDataStatus("0");
				}
			}
			commonDao.batchUpdate(redpackageList, 10);
		}
	}

	@Override
	public AjaxJson saveInfoAndSendRed(TerminalInfoEntity infoEntity,
			QrcodeParamVo paramVo) {
		commonDao.saveOrUpdate(infoEntity);
		
		
		return null;
	}

	@Override
	public List<RedpackageOpenVo> redPackageRecord(QrcodeParamVo paramVo) {
		List<RedpackageOpenVo> openVos = qrcodeActivityDao.findRedpackageOpenList(paramVo);
		return openVos;
	}

	@Override
	public List<RedpackageOpenVo> winningNameList(String actId) {
		List<RedpackageOpenVo> openVos = qrcodeActivityDao.winningNameList(actId);
		return openVos;
	}

	@Override
	public List<RedpackageOpenVo> rankingList(String actId) {
		List<RedpackageOpenVo> openVos = qrcodeActivityDao.rankingList(actId);
		return openVos;
	}

	@Override
	public List<QrcodeChannelVo> findChannel() {
		List<QrcodeChannelVo> channelVos = qrcodeActivityDao.channelList();
		return channelVos;
	}

	@Override
	public QrcodeActInfoVo findActInfo(String custCode, String productCode,
			String deliveryTime) throws CommonException {
		SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String nowDate = dateformat.format(new Date());
		QrcodeActInfoVo actInfoVo = qrcodeActivityDao.findActInfo(null, productCode, deliveryTime,nowDate);
		//9月22日需求变更，未找到活动数据，跳转防窜平台页面
		/*if(actInfoVo == null){
			throw new CommonException("该二维码未找到活动");
		}*/
		if(actInfoVo != null){
			if("Y".equals(actInfoVo.getRelationCust())){
				actInfoVo = qrcodeActivityDao.findActInfo(custCode, productCode, deliveryTime,nowDate);
				/*if(actInfoVo == null){
					throw new CommonException("该二维码未找到活动");
				}*/
			}
		}
		return actInfoVo;
	}
	
	@Override
	public void saveTerminalInfo(TerminalInfoEntity infoEntity, QrcodeParamVo paramVo) throws Exception {
		infoEntity.setCreateTime(DateUtils.getDate());
		//根据经纬度获取地址
		AddressVo addressVo = MapsUtil.getAddress(infoEntity.getLongitude(), infoEntity.getLatitude());
		if(addressVo != null){
			infoEntity.setProvince(addressVo.getProvince());
			infoEntity.setCity(addressVo.getCity());
			infoEntity.setArea(addressVo.getDistrict());
			infoEntity.setAddress(addressVo.getStreet());
			infoEntity.setLongitude(addressVo.getLongitude());
			infoEntity.setLatitude(addressVo.getLatitude());
		}
		TerminalInfoEntity entity = new TerminalInfoEntity();
		entity = this.findUniqueByProperty(TerminalInfoEntity.class, "openid", paramVo.getOpenId());
		if(entity == null){
			if(infoEntity.getMobilePhone() != null && infoEntity.getMobilePhone() != ""){
				entity = this.findUniqueByProperty(TerminalInfoEntity.class, "mobilePhone", infoEntity.getMobilePhone());
			}
		}
		if(entity == null){
			entity = new TerminalInfoEntity();
		}
		infoEntity.setIsBlacklist(null);
		infoEntity.setIsYellowlist(null);
		infoEntity.setWhitelist(null);
		MyBeanUtils.copyBeanNotNull2Bean(infoEntity, entity);
		entity.setOpenid(paramVo.getOpenId());
		commonDao.saveOrUpdate(entity);
		if("true".equals(paramVo.getBool())){
			this.getRedPackageBonus(paramVo);
		}
	}
	
	@Override
	public void changeTakeName(QrcodeParamVo paramVo) {
		RedpackageOpenEntity entity = new RedpackageOpenEntity();
		if(StringUtil.isNotEmpty(paramVo.getRedId())){
			Integer abnormalStatus = getAbnormalStatus(paramVo.getOpenId());
			entity = commonDao.get(RedpackageOpenEntity.class, paramVo.getRedId());
			entity.setTakeName(paramVo.getOpenId());
			entity.setTakeTime(DateUtils.getDate());
			entity.setDataStatus("0");
			entity.setAbnormalStatus(abnormalStatus);
		}
		commonDao.saveOrUpdate(entity);
	}
	@Override
	public void changeTakeName(QrcodeParamVo paramVo,TerminalInfoEntity infoEntity) throws Exception {
		RedpackageOpenEntity entity = new RedpackageOpenEntity();
		if(StringUtil.isNotEmpty(paramVo.getRedId())){
			Integer abnormalStatus = getAbnormalStatus(paramVo.getOpenId());
			entity = commonDao.get(RedpackageOpenEntity.class, paramVo.getRedId());
			entity.setTakeName(paramVo.getOpenId());
			entity.setTakeTime(DateUtils.getDate());
			entity.setDataStatus("0");
			entity.setAbnormalStatus(abnormalStatus);
		}
		commonDao.saveOrUpdate(entity);
		if("update".equals(paramVo.getStatus())){
			this.updateTerminalInfo(infoEntity, paramVo);
		}else{
			this.saveTerminalInfo(infoEntity, paramVo);
		}
	}

	@Override
	public void uploadPicture(AppealEntity appealEntity,HttpServletRequest request,String filedName) throws IOException {
		List<String> pids = UploadFileUtil.uploadPicture(request, filedName);
		appealEntity.setCreateTime(DateUtils.getDate());
		commonDao.save(appealEntity);
		String appid = appealEntity.getId();
		List<PictrueEntity> pictrues = new ArrayList<PictrueEntity>();
		if(CollectionUtil.listNotEmptyNotSizeZero(pids)){
			for (String string : pids) {
				PictrueEntity pictrue = new PictrueEntity();
				pictrue.setRefId(appid);
				pictrue.setPicName(string);
				pictrue.setCreateTime(DateUtils.getDate());
				pictrues.add(pictrue);
			}
			commonDao.batchSave(pictrues);
		}
	}

	
	
	@Override
	public TerminalInfoEntity drawShiWu(String redid, String openid) throws CommonException {
		TerminalInfoEntity info = commonDao.findUniqueByProperty(TerminalInfoEntity.class, "openid", openid);
		DrawShiWuEntity shiwu = commonDao.findUniqueByProperty(DrawShiWuEntity.class, "redid", redid);
		RedpackageOpenEntity redpackage = commonDao.getEntity(RedpackageOpenEntity.class, redid);
		QrcodeActInfoVo actInfo = qrcodeActivityDao.getActInfoById(redpackage.getActId());
		if(shiwu == null){
			shiwu = new DrawShiWuEntity();
			shiwu.setOpenid(openid);
			shiwu.setRedid(redid);
			shiwu.setCreateTime(DateUtils.getDate());
			commonDao.saveOrUpdate(shiwu);
			//实物奖保存成功后发送短信通知工作人员
			String province = info.getProvince();
			String city = info.getCity();
			String phone = info.getMobilePhone();
			String staffPhone = actInfo.getStaffPhone();
			String msg = SMSUtil.sendNotice(province,city,phone,staffPhone);
			logger.info("drawShiWu 短信发送成功,status:{}",msg);
			if(!msg.equals("ok") && !msg.equals("OK")){
				throw new CommonRuntimeException("短信发送失败");
			}
		}
		return info;
	}
	
	@Override
	public void uploadPicture(AppealEntity appealEntity, MultipartFile[] files, HttpServletRequest request)
			throws IOException {
	    String path = request.getSession().getServletContext().getRealPath(Constants.imgBaseProjectSuffPath);   
		List<String> pids = UploadFileUtil.uploadPicture(path, files);
		appealEntity.setCreateTime(DateUtils.getDate());
		commonDao.save(appealEntity);
		String appid = appealEntity.getId();
		List<PictrueEntity> pictrues = new ArrayList<PictrueEntity>();
		if(CollectionUtil.listNotEmptyNotSizeZero(pids)){
			for (String string : pids) {
				PictrueEntity pictrue = new PictrueEntity();
				pictrue.setRefId(appid);
				pictrue.setPicName(string);
				pictrue.setCreateTime(DateUtils.getDate());
				pictrues.add(pictrue);
			}
			commonDao.batchSave(pictrues);
		}
		
	}

	/**
	 * 添加申述信息，将申述信息保存成功之后的id更新到图片实体的外键列中
	 */
	public void saveAppeal(AppealEntity appealEntity,String[] refId){
		appealEntity.setCreateTime(DateUtils.getDate());
		//添加申述信息
		Serializable appealId = commonDao.save(appealEntity);
		if(StringUtil.isNotEmpty(refId)){
			//将申述信息保存成功之后的id更新到图片实体的外键列中
			for (String string : refId) {
				PictrueEntity pic = commonDao.findUniqueByProperty(PictrueEntity.class, "id", string);
				pic.setRefId(appealId.toString());
				commonDao.saveOrUpdate(pic);
			}
		}
		
	}

	@Override
	public void updatePhone(String id,String phone,String redId) {
		TerminalInfoEntity info = commonDao.get(TerminalInfoEntity.class, id);
		if(info != null){
			String pastPhone = info.getMobilePhone();
			info.setMobilePhone(phone);
			commonDao.saveOrUpdate(info);
			//原电话号码有值，并且与更新的电话号码不一致，才会发短信给运维人员
			if(null != pastPhone && !pastPhone.equals(phone)){
				RedpackageOpenEntity redpackageOpen = this.getEntity(RedpackageOpenEntity.class, redId);
				if(null != redpackageOpen){
					QrcodeActInfoVo actInfoVo = qrcodeActivityDao.getActInfoById(redpackageOpen.getActId());
					if(null != actInfoVo){
						String province = info.getProvince();
						String city = info.getCity();
						String staffPhone = actInfoVo.getStaffPhone();
						JSONObject content = new JSONObject();
						content.put("province", province);//省
						content.put("city", city);//市
						content.put("pastPhone", pastPhone);//原电话号码
						content.put("phone", phone);//更改后电话号码
						String msg = SMSUtil.sendSms(staffPhone, content.toString(), SMSUtil.PHONE_CHANGE);
						logger.info("手机变更 短信发送成功,status:{}",msg);
						if(!msg.equals("ok") && !msg.equals("OK")){
							throw new CommonRuntimeException("给运维人员发送您的变更信息失败，请稍后再试！或者主动联系运维人员告知运维人员，运维人员电话号码：【"+staffPhone+"】");
						}
					}else{
						logger.info("红包ID【"+redId+"】,活动ID【"+redpackageOpen.getActId()+"】在【qrcode_act_info】表中没有找到对应的活动信息。");
					}
				}else{
					logger.info("红包ID【"+redId+"】,在【qrcode_redpackage_open】表中没有找到对应的信息，后台维护人员请仔细检查代码。");
				}
			}else{
				logger.info("终端用户【"+info.getOpenid()+"】,原手机号【"+pastPhone+"】与更新手机号【"+phone+"】一致，不会发短信给运维人员");
			}
		}else{
			logger.info("终端用户信息ID【"+id+"】,在【qrcode_terminal_info】表中没有找到对应的信息。");
		}
	}
	
	@Override
	public Serializable uploadPicture(HttpServletRequest request, String imgContent) throws Exception {
		
		
		String outPutFilePath = request.getSession().getServletContext().getRealPath(Constants.imgBaseProjectSuffPath);
		
		UUID randomUUID = UUID.randomUUID();
		String fileName = randomUUID.toString();
		logger.info("图片上传地址，filePath:{}",outPutFilePath);
		FileUtils.writeBase64File(outPutFilePath, fileName+".png", imgContent);
		
		PictrueEntity pictrue = new PictrueEntity();
		pictrue.setPicName(fileName);
		pictrue.setCreateTime(DateUtils.getDate());
		Serializable id = commonDao.save(pictrue);
		return id;
	}

	@Override
	public RedpackageVo getRedpackageByOpenId(String openId) {
		if(StringUtil.isNotEmpty(openId)){
			openId = WeixinUtil.getUserOpenId();
		}
		RedpackageVo vo = new RedpackageVo();
		vo.setTotalAmt(qrcodeActivityDao.getRedPackageAllAmt(openId));
		vo.setBalanceAmt(qrcodeActivityDao.getRedPackageAmt(openId, 0));
		QrcodeParamVo paramVo = new QrcodeParamVo();
		paramVo.setOpenId(openId);
		paramVo.setIsSendout(1);
		List<RedpackageOpenVo> openVos = this.findRedpackageOpenList(paramVo);
		vo.setRedpackageOpenVos(openVos);
		return vo;
	}

	@Override
	public AppealEntity findAppealEntity(String redid, String openid) {
		return null;
	}

	@Override
	public void updateTerminalInfo(TerminalInfoEntity terminalInfoEntity, QrcodeParamVo paramVo) throws Exception{
		commonDao.merge(terminalInfoEntity);
		if("true".equals(paramVo.getBool())){
			this.getRedPackageBonus(paramVo);
		}
	}
	
}
