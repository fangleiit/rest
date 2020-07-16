package com.biz.bizunited.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.jeecgframework.minidao.hibernate.dao.IGenericBaseCommonDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.biz.bizunited.dao.RecyclingPrizeDao;
import com.biz.bizunited.entity.PrizeEntity;
import com.biz.bizunited.entity.PutinPrizeEntity;
import com.biz.bizunited.service.RecyclingPrizeService;
import com.biz.bizunited.util.CollectionUtil;

@Service
@Transactional
public class RecyclingPrizeServiceImpl implements RecyclingPrizeService {
	
	@Autowired
	private IGenericBaseCommonDao commonDao;
	@Autowired
	private RecyclingPrizeDao recyclingPrizeDao;
	
	@Override
	public void Recycling() {
		SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String nowdate = dateformat.format(new Date());
		String putinHql = "from PutinPrizeEntity p where date_format(p.EDate,'%Y-%m-%d %H:%i:%s')< '"+nowdate+"' and p.SNumber>0 and p.status='009'";
		List<PutinPrizeEntity> putinList = commonDao.findByQueryString(putinHql);
		List<PrizeEntity> prizeList = recyclingPrizeDao.findLosePrizeList(nowdate);
		if(CollectionUtil.listNotEmptyNotSizeZero(putinList) && CollectionUtil.listNotEmptyNotSizeZero(prizeList)){
			for (int i = 0; i < prizeList.size(); i++) {
				for (int j = 0; j < putinList.size(); j++) {
					if("009".equals(putinList.get(j).getStatus())){
						if(prizeList.get(i).getId().equals(putinList.get(j).getPId())){
							int count = prizeList.get(i).getCount();
							int outCount = prizeList.get(i).getOutCount();
//							System.out.println("数量："+count);
//							System.out.println("已发数量："+outCount);
							int snumber = putinList.get(j).getSNumber();
//							System.out.println("已发剩余数量："+snumber);
							//prizeList.get(i).setCount(count+snumber);
							prizeList.get(i).setOutCount(outCount-snumber);
							prizeList.get(i).setDataStatus("0");
							putinList.get(j).setStatus("003");
							putinList.get(j).setDataStatus("0");
//							System.out.println("回收后数量:"+prizeList.get(i).getCount());
//							System.out.println("回收后已发数量:"+prizeList.get(i).getOutCount());
							break;
						}
					}
				}
			}
			commonDao.batchUpdate(prizeList, 20);
			commonDao.batchUpdate(putinList, 20);
		}
	}

}
