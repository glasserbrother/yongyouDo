package com.zgcfo.ezg.app.commond;

import java.util.List;

import com.alibaba.fastjson.JSON;
import com.zgcfo.ezg.app.constant.YongYouConstants;
import com.zgcfo.ezg.app.data.YongYouDataGet;
import com.zgcfo.ezg.entity.yongyou.DetailAccountReport;
import com.zgcfo.ezg.util.RedisUtil;

public class YongYouCommondGet {
	private YongYouDataGet yyGet;
	private int commond;
	
	
	
	public YongYouDataGet getYyGet() {
		return yyGet;
	}



	public void setYyGet(YongYouDataGet yyGet) {
		this.yyGet = yyGet;
	}



	public int getCommond() {
		return commond;
	}



	public void setCommond(int commond) {
		this.commond = commond;
	}



	public YongYouCommondGet(YongYouDataGet yyGet, int commond) {
		super();
		this.yyGet = yyGet;
		this.commond = commond;
	}
	
	public byte[] getCommondRedisKey(){
		return RedisUtil.getCommondRedisKey(commond);
	}


	public Object getCommondAndGetList(){
		Object list = null;
		switch(commond){
		case YongYouConstants.CASH_REPORT_INT : 
			list = yyGet.getCashReport();//开始导入现金流量表
			break;
		case YongYouConstants.INCOME_REPORT_INT : 
			list = yyGet.getIncomeReport();//开始导入利润表
			break;
		case YongYouConstants.BALANCE_REPORT_INT : 
			list = yyGet.getBalanceReport();//开始导入资产负债表
			break;
		case YongYouConstants.SUBJECT_INT : 
			list = yyGet.getSubjects();//开始导入科目
			break;
		case YongYouConstants.BALANCE_INT : 
			list = yyGet.getBalance();//开始导入余额
			break;
		case YongYouConstants.INCOME_QUARTER_REPORT_INT :
			list = yyGet.getIncomeQuarterReport();//开始导入利润季报表
			break;
		case YongYouConstants.TAX_REPORT_INT : 
			list = yyGet.getTaxReports();//开始导入纳税统计表
			break;
		default : break;
		}
		return list;
	}



	public Object getSpecialAndGetList(Object obj) {
		
		List<Object> list = (List<Object>) obj;
		if (list != null && list.size() > 0){
			return yyGet.getDetailAccountReport(list);
		}
		return null;
	}
 
}
