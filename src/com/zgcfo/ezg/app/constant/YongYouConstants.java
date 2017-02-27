package com.zgcfo.ezg.app.constant;

import java.util.HashMap;
import java.util.Map;

public class YongYouConstants {
	
	public static  Map<Integer, byte[]> YYMAP = new HashMap<Integer, byte[]>();
	
	public static final byte[] CASH_REPORT_BYTE = "CashReport".getBytes();
	public static final byte[] INCOME_REPORT_BYTE = "IncomeReport".getBytes();
	public static final byte[] BALANCE_REPORT_BYTE = "BalanceReport".getBytes();
	public static final byte[] SUBJECT_BYTE = "Subject".getBytes();
	public static final byte[] BALANCE_BYTE = "Balance".getBytes();
	public static final byte[] INCOME_QUARTER_REPORT_BYTE = "IncomeQuarterReport".getBytes();
	public static final byte[] TAX_REPORT_BYTE = "TaxReport".getBytes();
	
	public static final byte[] DETAIL_ACCOUNT_REPORT_BYTE = "DetailAccountReport".getBytes();
	
	public static final int CASH_REPORT_INT = 0;
	public static final int INCOME_REPORT_INT = 1;
	public static final int BALANCE_REPORT_INT = 2;
	public static final int SUBJECT_INT = 3;
	public static final int BALANCE_INT = 4;
	public static final int INCOME_QUARTER_REPORT_INT = 5;
	public static final int TAX_REPORT_INT = 6;
	public static final int DETAIL_ACCOUNT_REPORT_INT = 7;
	
	static {
		YYMAP.put(new Integer(CASH_REPORT_INT), CASH_REPORT_BYTE);
		YYMAP.put(new Integer(INCOME_REPORT_INT), INCOME_REPORT_BYTE);
		YYMAP.put(new Integer(BALANCE_REPORT_INT), BALANCE_REPORT_BYTE);
		YYMAP.put(new Integer(SUBJECT_INT), SUBJECT_BYTE);
		YYMAP.put(new Integer(BALANCE_INT), BALANCE_BYTE);
		YYMAP.put(new Integer(INCOME_QUARTER_REPORT_INT), INCOME_QUARTER_REPORT_BYTE);
		YYMAP.put(new Integer(TAX_REPORT_INT), TAX_REPORT_BYTE);
		YYMAP.put(new Integer(DETAIL_ACCOUNT_REPORT_INT), DETAIL_ACCOUNT_REPORT_BYTE);
	}
	
	
	public static void main(String[] args) {
		for(Integer key: YYMAP.keySet()){
			System.out.println("key:"+key+" vallue:"+YYMAP.get(key));
		}
	}
	

	
	
}
