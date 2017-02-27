package com.zgcfo.ezg.app;

import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.zgcfo.ezg.app.commond.YongYouCommondRPOP;
import com.zgcfo.ezg.app.data.YongYouDo;
import com.zgcfo.ezg.entity.yongyou.YongYouDataEntity;
import com.zgcfo.ezg.service.YongYouDoData;
import com.zgcfo.ezg.util.AppMySQLConn;
import com.zgcfo.ezg.util.MyFormat;

public class YongYouDoApp {
	
	private static int produceTaskSleepSecond = 30;//队列等待30秒空闲
	private static int produceTaskMinNumber = 8;//最小8个线程
	private static int produceTaskMaxNumber = 10;//最大10个线程
	private static int queueDeep = 1;//允许队列深度2(等待)
	
	public static void main(String[] args) {
		String commondIndex=null;
		String commondLen = null;
		boolean singleFlag = false;
		int threadLenth = 0;
		if (args.length > 0 ){
			commondIndex = args[0];
			
		}
		if (args.length > 1 ){
			commondLen = args[1];
			threadLenth = MyFormat.formatInt(commondLen);
		}
		
		YongYouDo yyDo;
		AppMySQLConn conn;
		YongYouCommondRPOP yyCommondRPOP;
		
		ThreadPoolExecutor threadPoolRPOP = new ThreadPoolExecutor(produceTaskMinNumber, produceTaskMaxNumber, produceTaskSleepSecond,
				TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(queueDeep),
				new ThreadPoolExecutor.DiscardOldestPolicy());
		
		conn = new AppMySQLConn();
		yyDo = new YongYouDo(conn);
		
		if (!MyFormat.isStrNull(commondIndex)){
			singleFlag = true;
			yyCommondRPOP = new YongYouCommondRPOP(yyDo, MyFormat.formatInt(commondIndex));
			threadPoolRPOP.execute(new YYThreadPoolRPOP(yyCommondRPOP, singleFlag, threadLenth));
		}else{
			for(int j = 0; j<8; j++ ){
				yyCommondRPOP = new YongYouCommondRPOP(yyDo, j);
				threadPoolRPOP.execute(new YYThreadPoolRPOP(yyCommondRPOP));
			}
		}
		
		threadPoolRPOP.shutdown();
		
	}
	

}
