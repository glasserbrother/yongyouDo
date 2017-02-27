package com.zgcfo.ezg.app.data;


import java.util.ArrayList;
import java.util.List;

import com.zgcfo.ezg.entity.yongyou.Balance;
import com.zgcfo.ezg.entity.yongyou.BalanceReport;
import com.zgcfo.ezg.entity.yongyou.CashReport;
import com.zgcfo.ezg.entity.yongyou.DetailAccountReport;
import com.zgcfo.ezg.entity.yongyou.IncomeQuarterReport;
import com.zgcfo.ezg.entity.yongyou.IncomeReport;
import com.zgcfo.ezg.entity.yongyou.Subject;
import com.zgcfo.ezg.entity.yongyou.TaxReport;
import com.zgcfo.ezg.service.YongYouService;

public class YongYouDataGet {
	
	private String accountantLoginName;	
	private String accountantPwd;
	private String accountBookId;
	private Integer yongyouBookId;
	private String period;
	private YongYouService yongyouService;

	
	@Override
	public String toString() {
		return "YongYouDataGet [accountantLoginName=" + accountantLoginName
				+ ", accountantPwd=" + accountantPwd + ", accountBookId="
				+ accountBookId + ", yongyouBookId=" + yongyouBookId
				+ ", period=" + period + "]";
	}


	public YongYouDataGet(String accountantLoginName, String accountantPwd,
			String accountBookId, Integer yongyouBookId, String period) {
		super();
		this.accountantLoginName = accountantLoginName;
		this.accountantPwd = accountantPwd;
		this.accountBookId = accountBookId;
		this.yongyouBookId = yongyouBookId;
		this.period = period;
		init();
	}

	
	public void init(){
		yongyouService = new YongYouService();
		while (! yongyouService.initService(accountantLoginName, accountantPwd)){
			try {
				System.out.println("登录失败，3秒后重试");
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	//开始导入现金流量表
	public List<CashReport> getCashReport(){
		return getCashReport(yongyouBookId, period);
	}
	public List<CashReport> getCashReport(Integer yongyouBookId, String period){
		List<Object> cashObjs = yongyouService.getCashReport(yongyouBookId, period);
		List<CashReport> objs = null;
		if(null != cashObjs && cashObjs.size() > 0){
			objs = new ArrayList<CashReport>();
			Object obj;
			String uniqueId;
			CashReport cashReport;
			for(int i = 0;i<cashObjs.size(); i++ ){
				obj = cashObjs.get(i);
				cashReport = (CashReport) obj;
				uniqueId = accountBookId + period+i;
				cashReport.setUniqueId(uniqueId);
				cashReport.setPeriod(period);
				cashReport.setAccountBookId(accountBookId);
				objs.add(cashReport);
			}
		}
		return objs;
	}
	
	//开始导入利润表
	public List<IncomeReport> getIncomeReport(){
		return getIncomeReport(yongyouBookId, period);
	}
	public List<IncomeReport> getIncomeReport(Integer yongyouBookId, String period){
		List<Object> incomeReports = yongyouService.getIncomeReport(yongyouBookId, period);
		List<IncomeReport> objs = null;
		if(incomeReports != null && incomeReports.size() > 0){
			objs = new ArrayList<IncomeReport>();
			IncomeReport incomeReport;
			String uniqueId;
			for(int i=0;i<incomeReports.size();i++){
				
				incomeReport = (IncomeReport) incomeReports.get(i);
				uniqueId = accountBookId + period+i;
				incomeReport.setUniqueId(uniqueId);
				incomeReport.setPeriod(period);
				incomeReport.setAccountBookId(accountBookId);
				objs.add(incomeReport);
				
			}
		}
		
		return objs;		
	}
	
	//开始导入资产负债表
	public List<BalanceReport> getBalanceReport(){
		return getBalanceReport(yongyouBookId, period);
	}
	public List<BalanceReport> getBalanceReport(Integer yongyouBookId, String period){
		List<Object> balanceReports = yongyouService.getBalanceReport(yongyouBookId, period);
		List<BalanceReport> objs = null;
		if(balanceReports != null && balanceReports.size() > 0){
			objs = new ArrayList<BalanceReport>();
			BalanceReport balancReport;
			String uniqueId ;
			for(int i=0; i<balanceReports.size();i++){
				
				balancReport = (BalanceReport) balanceReports.get(i);
				
				uniqueId = accountBookId + period+i;
				balancReport.setUnique_id(uniqueId);
				balancReport.setPeriod(period);
				balancReport.setAccountBookId(accountBookId);
				objs.add(balancReport);
			}
		}
		return objs;
	}
	
	//开始导入科目
	public List<Subject> getSubjects(){
		return getSubjects(yongyouBookId, period);
	}
	public List<Subject> getSubjects(Integer yongyouBookId, String period){
		List<Object> subjects = yongyouService.getSubjects(yongyouBookId);
		List<Subject> objs = null;
		if(subjects != null && subjects.size() > 0){
			objs = new ArrayList<Subject>();
			
			Subject sbj;
			for(Object obj:subjects){
				
				sbj = (Subject) obj;
				sbj.setAccountBookId(accountBookId);
				sbj.setUniqueId(accountBookId+sbj.getYongyouId());
				objs.add(sbj);
			}
		}
		return objs;
	}
	
	//开始导入余额表
	public List<Balance> getBalance(){
		return getBalance(yongyouBookId, period);
	}
	public List<Balance> getBalance(Integer yongyouBookId, String period){
		List<Object> balances = yongyouService.getBalance(yongyouBookId, period, period);
		List<Balance> objs = null;
		if(balances != null && balances.size()>0){
			objs = new ArrayList<Balance>();
			Balance balance;
			String uniqueId;
			for(int i =0; i<balances.size();i++){
				balance = (Balance) balances.get(i);
				uniqueId =accountBookId+period+period+i;
				balance.setUniqueId(uniqueId);
				balance.setStartPeriod(period);
				balance.setEndPeriod(period);
				balance.setAccountBookId(accountBookId);
				objs.add(balance);
			}
		}
		return objs;
	}
	
	//开始导入利润季报表
	public List<IncomeQuarterReport> getIncomeQuarterReport(){
		return getIncomeQuarterReport(yongyouBookId, period);
	}
	public List<IncomeQuarterReport> getIncomeQuarterReport(Integer yongyouBookId, String period){
		List<Object> incomeQuarters = yongyouService.getIncomeQuarterReport(yongyouBookId, period);
		List<IncomeQuarterReport> objs = null;
		if(incomeQuarters != null && incomeQuarters.size() > 0){
			objs = new ArrayList<IncomeQuarterReport>();
			IncomeQuarterReport incomeQuarterReport;
			String uniqueId;
			for(int i=0; i<incomeQuarters.size();i++){
				
				incomeQuarterReport = (IncomeQuarterReport) incomeQuarters.get(i);
				uniqueId = accountBookId+period+i;
				incomeQuarterReport.setAccountBookId(accountBookId);
				incomeQuarterReport.setUniqueId(uniqueId);
				incomeQuarterReport.setPeriod(period);
				objs.add(incomeQuarterReport);
			}
			
		}
		return objs;
	}

	//开始导入纳税统计表
	public List<TaxReport> getTaxReports(){
		return getTaxReports(yongyouBookId, period);
	}
	public List<TaxReport> getTaxReports(Integer yongyouBookId, String period){
		List<Object> taxReports = yongyouService.getTaxReports(yongyouBookId, period);
		List<TaxReport> objs = null;
		if(taxReports != null && taxReports.size() > 0){
			objs = new ArrayList<TaxReport>();
			TaxReport taxReport;
			String uniqueId;
			for(int i=0; i<taxReports.size();i++){
				taxReport = (TaxReport) taxReports.get(i);
				uniqueId = accountBookId+period+i;
				taxReport.setAccountBookId(accountBookId);
				taxReport.setUniqueId(uniqueId);
				taxReport.setPeriod(period);
				objs.add(taxReport);
			}
		}
		return objs;
	}
	
	//开始导入明细账
	public List<List<DetailAccountReport>> getDetailAccountReport(List<Object> list){
		List<List<DetailAccountReport>> total = null;
		List<DetailAccountReport> subList;
		if (list !=null && list.size() > 0){
			total = new ArrayList<List<DetailAccountReport>>();
			for (int i = 0;i< list.size(); i++){
				Subject sbj = (Subject) list.get(i);
				sbj.getYongyouId();
				subList = getDetailAccountReport(yongyouBookId, period, sbj.getYongyouId()+"");
				total.add(subList);
			}
		}
		return total;
	}
	public List<DetailAccountReport> getDetailAccountReport(Integer yongyouBookId, String period, String subjectId){
		List<Object> detailAccounts = yongyouService.getDetailAccountReport(yongyouBookId, period, period, subjectId);
		List<DetailAccountReport> objs = null;
		if(detailAccounts != null && detailAccounts.size() > 0){
			objs = new ArrayList<DetailAccountReport>();
			DetailAccountReport detailAccount;
			String uniqueId;
			
			for(int i = 0;i<detailAccounts.size();i++){
				detailAccount = (DetailAccountReport) detailAccounts.get(i);
				uniqueId = accountBookId+subjectId+period+period+i;
				detailAccount.setStartPeriod(period);
				detailAccount.setEndPeriod(period);
				detailAccount.setUniqueId(uniqueId);
				detailAccount.setAccountBookId(accountBookId);
				detailAccount.setSubjectId(subjectId+"");
				objs.add(detailAccount);
			}
		}
		return objs;
	}		

}

