package com.zgcfo.ezg.service;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.zgcfo.ezg.entity.yongyou.YongYouDataEntity;
import com.zgcfo.ezg.util.AppMySQLConn;

public class YongYouDoData {
	
	public List<YongYouDataEntity> getYongYouDataList(){
		List<YongYouDataEntity> datas = new ArrayList<YongYouDataEntity>();
		AppMySQLConn con = new AppMySQLConn();
		StringBuilder sql = new StringBuilder();
		sql.append(" select a.accountcode, a.accountpwd,b.bookid, b.yongyouid from yzg_accountbook b, yzg_accounter a where b.accounter = a.accounterid  ");
		
		PreparedStatement ps = con.prepareStatement(sql);
		ResultSet rs;
		try {
			rs = ps.executeQuery();
			String loginName;
			String pwd;
			String bookId;
			int yongyouId;
			String currMonth;
			
			YongYouDataEntity yyData;
			while(rs.next()){
				loginName = rs.getString("accountcode");
				pwd = rs.getString("accountpwd");
				bookId = rs.getString("bookid");
				yongyouId = rs.getInt("yongyouId");
				yyData = new YongYouDataEntity();
				yyData.setLoginName(loginName);
				yyData.setPwd(pwd);
				yyData.setBookId(bookId);
				yyData.setYongyouId(yongyouId);
				
				datas.add(yyData);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return datas;
	}
	
	public boolean getYongYouData(YongYouDataEntity yyData){
		
		
		
		return false;
	}
	
	
	public boolean doYongYouData(YongYouDataEntity yyData, AppMySQLConn conn){
		boolean flag = false;
		if (null != yyData){
			String loginName = yyData.getLoginName();
			String pwd = yyData.getPwd();
			String bookId = yyData.getBookId();
			int yongyouId = yyData.getYongyouId();
			String currMonth = yyData.getCurrMonth();
			
			try{
				YongyouDataUtil yongyouDataUtil = new YongyouDataUtil(loginName, pwd, conn);
				yongyouDataUtil.beginInsert(bookId, yongyouId, currMonth);
				flag= true;
			}catch(Exception e){
				e.printStackTrace();
				flag = false;
			}
			
		}

		
		return flag;
	}
}
