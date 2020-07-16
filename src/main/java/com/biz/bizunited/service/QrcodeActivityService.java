package com.biz.bizunited.service;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

import com.biz.bizunited.common.AjaxJson;
import com.biz.bizunited.common.service.CommonService;
import com.biz.bizunited.entity.AppealEntity;
import com.biz.bizunited.entity.TerminalInfoEntity;
import com.biz.bizunited.exception.CommonException;
import com.biz.bizunited.vo.QrcodeActInfoVo;
import com.biz.bizunited.vo.QrcodeChannelVo;
import com.biz.bizunited.vo.QrcodeParamVo;
import com.biz.bizunited.vo.QrcodeUserInfoSeriesVo;
import com.biz.bizunited.vo.RedpackageOpenVo;
import com.biz.bizunited.vo.RedpackageVo;


public interface QrcodeActivityService extends CommonService{
	
	public RedpackageOpenVo findRedpackageByQrcode(String qrcode);
	
	/**
	 * 开奖
	 * @param qrcode
	 * @param openId
	 * @param actId
	 * @param custCode
	 * @return
	 * @throws Exception 
	 */
	public AjaxJson openRedpackage(QrcodeParamVo paramVo) throws Exception;
	
	/**
	 * 根据用户openId查询所有已领取未发放的奖
	 * @param openid
	 * @param qrcode
	 * @return
	 */
	public List<RedpackageOpenVo> findRedpackageOpenList(QrcodeParamVo paramVo);
	
	/**
	 * 查询问卷配置
	 * @param qrcode
	 * @return
	 */
	public QrcodeUserInfoSeriesVo findInfoConfig(String actId);
	
	/**
	 * 更近活动查询用户在该活动的扫码次数
	 * @param openId
	 * @param actId
	 * @return
	 */
	public Integer findScanCodeByOpenId(String openId,String actId);
	public Integer findScanCodeByOpenId(String openId);
	/**
	 * 领取红包业务逻辑
	 * @param paramVo
	 * @return
	 * @throws CommonException 
	 */
	public void getRedPackageBonus(QrcodeParamVo paramVo) throws CommonException;
	
	/**
	 * 保存用户信息并发放红包
	 * @param infoEntity
	 * @param paramVo
	 * @return
	 */
	public AjaxJson saveInfoAndSendRed(TerminalInfoEntity infoEntity,QrcodeParamVo paramVo);
	
	/**
	 * 红包领取记录
	 * @param openId
	 * @return
	 */
	public List<RedpackageOpenVo> redPackageRecord(QrcodeParamVo paramVo);
	
	/**
	 * 中奖名单
	 * @return
	 */
	public List<RedpackageOpenVo> winningNameList(String actId);
	
	/**
	 * 排名
	 * @return
	 */
	public List<RedpackageOpenVo> rankingList(String actId);
	
	/**
	 * 终端渠道类型
	 * @return
	 */
	public List<QrcodeChannelVo> findChannel();
	
	/**
	 * 查询执行的活动
	 * @param custCode
	 * @param productCode
	 * @param deliveryTime 改为生产时间
	 * @return
	 * @throws CommonException 
	 */
	public QrcodeActInfoVo findActInfo(String custCode,String productCode,String deliveryTime) throws CommonException;
	
	/**
	 * 保存用户信息
	 * @param infoEntity
	 * @param openid
	 * @throws Exception 
	 */
	public void saveTerminalInfo(TerminalInfoEntity infoEntity, QrcodeParamVo paramVo) throws Exception;
	
	/**
	 * 不能提现时更改领取人
	 * @param paramVo
	 */
	public void changeTakeName(QrcodeParamVo paramVo);
	
	public void changeTakeName(QrcodeParamVo paramVo,TerminalInfoEntity infoEntity) throws Exception;
	
	/**
	 * 保存申诉信息
	 * @param appealEntity    申诉实体
	 * @param request
	 * @param filedName       标签name
	 * @throws IOException
	 */
	public void uploadPicture(AppealEntity appealEntity,HttpServletRequest request,String filedName) throws IOException;
	

	/**
	 * 保存申诉信息
	 * @param appealEntity    申诉实体
	 * @throws IOException
	 */
	public void uploadPicture(AppealEntity appealEntity,MultipartFile[] files,HttpServletRequest request) throws IOException;
	
	
	/**
	 * 上传图片
	 * @param request
	 * @param imgContent
	 * @return 
	 * @throws IOException
	 * @throws Exception 
	 */
	public Serializable uploadPicture(HttpServletRequest request,String imgContent) throws IOException, Exception;
	
	/**
	 *  保存申诉信息
	 * @param appealEntity
	 * @param refId
	 */
	public void saveAppeal(AppealEntity appealEntity,String[] refId);
	
	public TerminalInfoEntity drawShiWu(String redid,String openid) throws CommonException;
	/**
	 * 修改手机号码
	 * @param id 终端用户主键ID
	 * @param phone 需要更改的手机号
	 * @param redId 红包ID（用于给运维人员发送短信）
	 */
	public void updatePhone(String id,String phone,String redId);
	
	/**
	 * 根据openId获取红包记录
	 * @param openId
	 * @return
	 */
	public RedpackageVo getRedpackageByOpenId(String openId);
	
	public AppealEntity findAppealEntity(String redid,String openid);
	
	public void updateTerminalInfo(TerminalInfoEntity terminalInfoEntity,QrcodeParamVo paramVo) throws Exception;
}
