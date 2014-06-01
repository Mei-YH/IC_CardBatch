/**
 * 
 */
package com.icsys.batch.account;

import java.util.List;

import com.icsys.platform.dao.template.NamedQueryTemplate;
import com.icsys.platform.dao.template.NamedUpdateTemplate;
import com.icsys.platform.dao.template.TemplateManager;

/**
 * @author zhaosongpeng
 *
 */
public class IcProdAppDao {
	public static final String DS_NAME = "IC_DATASOURCE";
	
	/**
	 * 添加新配置
	 */
	public void addNewAppConfig(ProductAppBinding productAppConfig){
		NamedUpdateTemplate template = TemplateManager.getNamedUpdateTemplate(DS_NAME);
		String sql = "INSERT INTO IC_PROD_APP "+
			"("+
				"PRODUCT_NO,AID,APPLICATION_ACCOUNT_TYPE,"+
				"MAIN_AID,BRANCH_CODE,BRANCH_FLAG,PLATFORM_ACCT_NO,"+
				"HOST_ACCT_NO,FEE_ACCT_NO,LOSE_ACCT_NO,STATUS,DAC" +
			")"+
		  " VALUES "+
			"("+
				"#PRODUCT_NO#,#AID#,#APPLICATION_ACCOUNT_TYPE#,"+
				"#MAIN_AID#,#BRANCH_CODE#,#BRANCH_FLAG#,#PLATFORM_ACCT_NO#,"+
				"#HOST_ACCT_NO#,#FEE_ACCT_NO#,#LOSE_ACCT_NO#,#STATUS#,#DAC#"+
			")";
		template.update(sql,productAppConfig);
	}
	
	/**
	  * 变更配置
	  */
	 public int updateAppConfig(ProductAppBinding productAppConfig){
		 NamedUpdateTemplate template = TemplateManager.getNamedUpdateTemplate(DS_NAME);
		 String sql = "";
		 String sql_start = "UPDATE IC_PROD_APP SET ";
		 String sql_end = " WHERE PRODUCT_NO = #PRODUCT_NO# " +
		 						"AND AID = #AID#";
		 Boolean bool = false;
		 
		 if(null == productAppConfig.getProductNo())
			 productAppConfig.setProductNo("");
		 if(null == productAppConfig.getAid())
			 productAppConfig.setAid("");
		 
		 if(null != productAppConfig.getApplicationAccountType() && !"".equals(productAppConfig.getApplicationAccountType().trim())){
			 sql_start = sql_start + "APPLICATION_ACCOUNT_TYPE=#APPLICATION_ACCOUNT_TYPE# ";
			 bool = true;
		 }
		 if(null != productAppConfig.getMainAid() && !"".equals(productAppConfig.getMainAid().trim())){
			 if(bool){
				 sql_start = sql_start + ",MAIN_AID=#MAIN_AID# ";
			 }else{
				 sql_start = sql_start + "MAIN_AID=#MAIN_AID# ";
				 bool = true;
			 }
		 }
		 if(null != productAppConfig.getBranchCode() && !"".equals(productAppConfig.getBranchCode().trim())){
			 if(bool){
				 sql_start = sql_start + ",BRANCH_CODE=#BRANCH_CODE# ";
			 }else{
				 sql_start = sql_start + "BRANCH_CODE=#BRANCH_CODE# ";
				 bool = true;
			 }
		 }
		 if(null != productAppConfig.getBranchFlag() && !"".equals(productAppConfig.getBranchFlag().trim())){
			 if(bool){
				 sql_start = sql_start + ",BRANCH_FLAG=#BRANCH_FLAG# ";
			 }else{
				 sql_start = sql_start + "BRANCH_FLAG=#BRANCH_FLAG# ";
				 bool = true;
			 }
		 }
		 if(null != productAppConfig.getPlatformAcctNo() && !"".equals(productAppConfig.getPlatformAcctNo().trim())){
			 if(bool){
				 sql_start = sql_start + ",PLATFORM_ACCT_NO=#PLATFORM_ACCT_NO# ";
			 }else{
				 sql_start = sql_start + "PLATFORM_ACCT_NO=#PLATFORM_ACCT_NO# ";
				 bool = true;
			 }
		 }
		 if(null != productAppConfig.getHostAcctNo() && !"".equals(productAppConfig.getHostAcctNo().trim())){
			 if(bool){
				 sql_start = sql_start + ",HOST_ACCT_NO=#HOST_ACCT_NO# ";
			 }else{
				 sql_start = sql_start + "HOST_ACCT_NO=#HOST_ACCT_NO# ";
				 bool = true;
			 }
		 }
		 if(null != productAppConfig.getFeeAcctNo() && !"".equals(productAppConfig.getFeeAcctNo().trim())){
			 if(bool){
				 sql_start = sql_start + ",FEE_ACCT_NO=#FEE_ACCT_NO# ";
			 }else{
				 sql_start = sql_start + "FEE_ACCT_NO=#FEE_ACCT_NO# ";
				 bool = true;
			 }
		 }
		 if(null != productAppConfig.getLoseAcctNo() && !"".equals(productAppConfig.getLoseAcctNo().trim())){
			 if(bool){
				 sql_start = sql_start + ",LOSE_ACCT_NO=#LOSE_ACCT_NO# ";
			 }else{
				 sql_start = sql_start + "LOSE_ACCT_NO=#LOSE_ACCT_NO# ";
				 bool = true;
			 }
		 }
		 if(null != productAppConfig.getStatus() && !"".equals(productAppConfig.getStatus().trim())){
			 if(bool){
				 sql_start = sql_start + ",STATUS=#STATUS# ";
			 }else{
				 sql_start = sql_start + "STATUS=#STATUS# ";
				 bool = true;
			 }
		 }
		 if(null != productAppConfig.getDac() && !"".equals(productAppConfig.getDac().trim())){
			 if(bool){
				 sql_start = sql_start + ",DAC=#DAC# ";
			 }else{
				 sql_start = sql_start + "DAC=#DAC# ";
				 bool = true;
			 }
		 }
		 
		 sql = sql_start + sql_end;
		 
		 return template.update(sql, productAppConfig);
	 }
	 
	 /**
	  * 查询应用配置（根据产品号,应用编号）
	  */
	public ProductAppBinding getConfigByPorductNoAid(String productNo,String aid){
		NamedQueryTemplate template = TemplateManager.getNamedQueryTemplate(DS_NAME);
		
		ProductAppBinding appConfig = new ProductAppBinding();
		appConfig.setProductNo(productNo);
		appConfig.setAid(aid);
		
		String sql = "SELECT pro_acctype as application_account_type,pro_aid as main_aid,pro_brcode as branch_code,pro_brflag as branch_flag," +
				"pro_plaacctno as platform_acct_no, pro_haccno as host_acct_no,pro_accfee as fee_acct_no,pro_laccno as lose_acct_no," +
				"pro_status as status,att_prono as product_no,app_appid as aid FROM gr_pro_prodapp WHERE att_prono = #PRODUCT_NO# AND app_appid = #AID#";
		
		return template.queryRow(sql, appConfig, ProductAppBinding.class);
	}
	
	/**
	  * 查询应用产品号,应用编号（根据产品号,应用编号查询配置是否已存在）
	  */
	public String getPorductNoAid(String productNo,String aid){
		NamedQueryTemplate template = TemplateManager.getNamedQueryTemplate(DS_NAME);
		
		ProductAppBinding appConfig = new ProductAppBinding();
		appConfig.setProductNo(productNo);
		appConfig.setAid(aid);
		
		String sql = "SELECT PRODUCT_NO, AID "+
		  			 "FROM IC_PROD_APP "+
		  			 "WHERE PRODUCT_NO = #PRODUCT_NO# " +
		  			 "AND AID = #AID#";
		appConfig = template.queryRow(sql, appConfig, ProductAppBinding.class);
		return (appConfig == null || appConfig.getProductNo()==null || appConfig.getAid()==null)
				? null : appConfig.getProductNo()+","+appConfig.getAid();
	}
	
	/**
	 * 查询某个产品全部应用配置(根据产品号)
	 */
	public List<ProductAppBinding> getProductAllConfig(String productNo){
		NamedQueryTemplate template = TemplateManager.getNamedQueryTemplate(DS_NAME);
		ProductAppBinding appConfig = new ProductAppBinding();
		appConfig.setProductNo(productNo);
		
		String sql = "SELECT * "+
		  			 "FROM IC_PROD_APP "+
		  			 "WHERE PRODUCT_NO = #PRODUCT_NO#";
		
		return template.query(sql,appConfig,ProductAppBinding.class);
	}
	
}
