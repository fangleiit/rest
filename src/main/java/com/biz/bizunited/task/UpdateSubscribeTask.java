package com.biz.bizunited.task;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.biz.bizunited.entity.WeiXinUserEntity;
import com.biz.bizunited.service.WeiXinUserService;

/**
 * 检查更新微信用户的关注
 *
 */
public class UpdateSubscribeTask {

	Logger logger = LoggerFactory.getLogger(UpdateSubscribeTask.class);
	
	@Autowired
	private  WeiXinUserService weiXinUserService;
	
	private ThreadPoolTaskExecutor threadPool;
	
	public ThreadPoolTaskExecutor getThreadPool() {
		return threadPool;
	}

	public void setThreadPool(ThreadPoolTaskExecutor threadPool) {
		this.threadPool = threadPool;
	}
	
	public void updateSubscribe(){
		//TODO 查询出距离上次检查超过七天的的用户   用户应该很少去取消关注   这个时间应该还是可以放长
		List<WeiXinUserEntity> users =  weiXinUserService.findByQueryString("from WeiXinUserEntity where datediff(now(),last_check_time) >= 7 or last_check_time is null");
		for (WeiXinUserEntity weiXinUserEntity : users) {
			exec(weiXinUserService, weiXinUserEntity);
		}
	}
	
	
	public void exec(final WeiXinUserService weiXinUserService,final WeiXinUserEntity user){
		threadPool.execute(new Runnable() {
            @Override
            public void run() {
            	try {
					Thread.currentThread().sleep(3000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            	weiXinUserService.updateSubscribe(user);
            }
        });
	}
	
}
