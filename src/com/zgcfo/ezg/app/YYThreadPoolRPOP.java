package com.zgcfo.ezg.app;

import java.io.Serializable;
import java.util.Date;

import com.zgcfo.ezg.app.commond.YongYouCommondGet;
import com.zgcfo.ezg.app.commond.YongYouCommondRPOP;
import com.zgcfo.ezg.app.constant.YongYouConstants;
import com.zgcfo.ezg.util.RedisUtil;

public class YYThreadPoolRPOP  implements Runnable, Serializable {
	
	private static final long serialVersionUID = 0;
	private static int consumeTaskSleepTime = 2000;
	
	private YongYouCommondRPOP yyCommondRPOP;
	private boolean singleFlag ;
	private int len;




	public YYThreadPoolRPOP(YongYouCommondRPOP yyCommondRPOP) {
		super();
		this.yyCommondRPOP = yyCommondRPOP;
	}
	public YYThreadPoolRPOP(YongYouCommondRPOP yyCommondRPOP, boolean singleFlag) {
		super();
		this.yyCommondRPOP = yyCommondRPOP;
		this.singleFlag = singleFlag;
	}
	public YYThreadPoolRPOP(YongYouCommondRPOP yyCommondRPOP, boolean singleFlag, int len) {
		super();
		this.yyCommondRPOP = yyCommondRPOP;
		this.singleFlag = singleFlag;
		this.len = len;
	}
	
	public void doBatch(byte[] b){
		if ( b != null && b.length > 0){
			if (yyCommondRPOP.getCommond() != YongYouConstants.DETAIL_ACCOUNT_REPORT_INT){
				yyCommondRPOP.deleteCommond(b);
			}
			
			yyCommondRPOP.executeCommond(b);
			System.out.println(new Date()+"  rpop-----  ["+new String(RedisUtil.getCommondRedisKey(yyCommondRPOP.getCommond()))+"]  "+Thread.currentThread().getName()+"执行完成");
		}
	}

	public void doBatch(){
		try {
			byte[] b =  yyCommondRPOP.getRPOPByte();
			if ( b == null){
				Thread.sleep(consumeTaskSleepTime);
			}else{
				doBatch(b);
			}
			
			
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private boolean batchProp(){
		if (len == 0)
			len = 10;
		return batchProp(len);
	}
	/**
	 * 多线程处理明细账
	 * @param threadLen
	 * @return
	 */
	private boolean batchProp(int threadLen){
		byte[] bb = yyCommondRPOP.getRPOPByte();
		boolean flag = true;
		if (bb == null ){
			if (singleFlag){
				return true;
			}else{
				try {
					flag = false;
					Thread.sleep(consumeTaskSleepTime);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
				
		}else{
			doBatch(bb);
			bb = null;
		}
		
		//线程没有暂停，就多线程prop;
		//线程暂停说明redis没数据，没必要多线程再跑
		if (false){
			for (int index = 0; index < threadLen; index ++){
				
				new Thread(new Runnable(){
					public void run(){
						byte[] b = yyCommondRPOP.getRPOPByte();
						doBatch(b);
						b = null;
					}
				})
				.start();
			}
		}
		
		return false;
	}


	@Override
	public void run() {
		System.out.println(new Date()+Thread.currentThread().getName()+"----------------------------------开始执行....");
		if (len == 0)
			len = 5;
		boolean flag = false;
		while (true){
			
			if (singleFlag){
				flag = batchProp();
				if (flag){
					break;
				}
			}else{
				doBatch();
			}
			
			
		}
		System.out.println(new Date()+Thread.currentThread().getName()+"----------------------------------执行完成....");
	}

}
