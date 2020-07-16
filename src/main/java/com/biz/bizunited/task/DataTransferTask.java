package com.biz.bizunited.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.biz.bizunited.service.DatatransferExecutorService;
import com.biz.bizunited.util.DataTransferUtil;

/**
 * 定时任务传输数据到其它接口
 * @author xiaogang
 *
 */
public class DataTransferTask {

	Logger logger = LoggerFactory.getLogger(DataTransferTask.class);
	
	@Autowired
	private DatatransferExecutorService datatransferExecutorService;
	
	/**
	 * 	扫码结果推送TPM
	 */
	public void transferCashGiftData(){
		System.out.println("DataTransferTask.transferCashGiftData()");
		logger.info("同步数据任务------------start");
		try {
			datatransferExecutorService.executor();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.toString());
		}
		logger.info("同步数据任务------------end");
	}
	
}
