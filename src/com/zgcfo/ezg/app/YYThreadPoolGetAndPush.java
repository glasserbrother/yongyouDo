package com.zgcfo.ezg.app;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.zgcfo.ezg.app.commond.YongYouCommondGet;
import com.zgcfo.ezg.app.constant.YongYouConstants;
import com.zgcfo.ezg.entity.yongyou.YongYouDataEntity;
import com.zgcfo.ezg.redis.JedisUtil;
import com.zgcfo.ezg.redis.ObjectUtil;
import com.zgcfo.ezg.util.RedisUtil;

public class YYThreadPoolGetAndPush implements Runnable, Serializable {

	private static final long serialVersionUID = 0;
	private static int consumeTaskSleepTime = 2000;
	private YongYouCommondGet yyCommondGet;

	public YYThreadPoolGetAndPush(YongYouCommondGet yyCommondGet) {
		super();
		this.yyCommondGet = yyCommondGet;
	}



	@Override
	public void run() {
		Object obj = null;
		byte[] redisKey;
		byte[] objs;
		
		try {
			obj = yyCommondGet.getCommondAndGetList();
			if (obj != null){
				redisKey = yyCommondGet.getCommondRedisKey();
				objs = ObjectUtil.objectToBytes(obj);
				JedisUtil.lpush(redisKey, objs);
				
				if (yyCommondGet.getCommond() == YongYouConstants.SUBJECT_INT){
					Object detailList = yyCommondGet.getSpecialAndGetList(obj);
					redisKey = RedisUtil.getCommondRedisKey(YongYouConstants.DETAIL_ACCOUNT_REPORT_INT);
					JedisUtil.lpush(redisKey, ObjectUtil.objectToBytes(detailList));
				}
				
				System.out.println(new Date()+"  get-----  ["+new String(redisKey)+"]  "+Thread.currentThread().getName()+"执行完成");
			}
			
			Thread.sleep(consumeTaskSleepTime);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		

		
		
	}
}
