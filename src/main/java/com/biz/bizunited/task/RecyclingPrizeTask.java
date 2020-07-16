package com.biz.bizunited.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.biz.bizunited.service.RecyclingPrizeService;

/**
 * @ClassName: RecyclingPrizeTask 
 * @Description: 回收奖定时任务
 * @author ian.zeng
 * @date 2017年8月19日 上午10:51:25
 */
public class RecyclingPrizeTask {
	
	Logger logger = LoggerFactory.getLogger(RecyclingPrizeTask.class);
	
	@Autowired
	private RecyclingPrizeService recyclingPrizeService;
	
	/**
	 * 扫描手工投放未被领取的奖
	 */
	public void RecyclingPrize(){
		logger.info("RecyclingPrize 扫描手工投放未被领取的奖");
		recyclingPrizeService.Recycling();
	}
}
