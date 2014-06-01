package com.icsys.batch.account;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.log4j.Logger;

import com.icsys.batch.util.ICSTATUS;
import com.icsys.batch.util.SystemConstans;
import com.icsys.platform.dao.template.NamedQueryTemplate;
import com.icsys.platform.dao.template.NamedUpdateTemplate;
import com.icsys.platform.dao.template.TemplateManager;
import com.icsys.platform.util.SystemProperties;

/**
 * @author Runpu Hu
 * @version 创建时间：2011-4-12 上午11:10:31 类说明 ：
 */

public class IcInfoRegDao {

	public static final String DS_NAME = SystemConstans.DB_SOURCE_NAME;

	private static Logger LOG = Logger.getLogger(IcInfoRegDao.class);

	/**
	 * 新加IC卡登记簿记录
	 * 
	 * @param icInfoReg
	 * @return
	 */
	public int addIcInfoReg(IcInfoReg icInfoReg) {
		NamedUpdateTemplate template = TemplateManager
				.getNamedUpdateTemplate(DS_NAME);
		String sql = "insert into IC_INFO_REG (CARD_NO,CARD_INDEX,PRODUCT_NO,ISSUER_BRANCH,ACCT_NO_ID,CUSTOMER_NO,ANNUAL_FEE,MAGNETIC_STRIPE_CARD_NO,SUPPLEMENT_CARD_FLAG,CARTER_FLAG,PIN_DATA,IC_STATUS,MAGNETIC_STATUS,ISSUER_DATE,STATUS_CHANGE_DATE,CANCEL_DATE,CARD_VALID_DATE,CERTIFICATE_VALID_DATE,RECLAIM_NUMBER,PASSWORD_MANAGE,DAC,PARAM_MOD_FLAG) values (#cardNo#,#cardIndex#,#productNO#,#issuerBranch#,#acctNoId#,#customerNo#,#annualFee#,#magneticStripeCardNo#,#supplementCardFlag#,#carterFlag#,#pinData#,#icStatus#,#magneticStatus#,#issuerDate#,#statusChangeDate#,#cancelDate#,#cardValidDate#,#certificateValidDate#,#reclaimNumber#,#passwordManage#,#dac#,#paramModFlag#)";
		if (LOG.isDebugEnabled()) {
			LOG.debug("add IcInfoReg" + icInfoReg);
		}
		int result = template.update(sql, icInfoReg);
		return result;
	}

	/**
	 * 根据卡号获取IC卡信息
	 * @param cardNo
	 * @param cardIndex
	 * @return
	 */
	public IcInfoReg getIcInfo(String cardNo,String cardIndex) {
		NamedQueryTemplate template = TemplateManager
				.getNamedQueryTemplate(DS_NAME);
		String sql = "select acct_no as acct_no_id,ic_class as product_no,ic_branch as issuer_branch,ic_cid as customer_no,year_f_code as annual_fee," +
				"card_no as magnetic_stripe_card_no,ic_flag as supplement_card_flag,ic_stat as ic_status,use_dt as issuer_date,chg_dt as status_change_date," +
				"canc_dt as cancel_date,ic_valid_dt as card_valid_date,cf_valid_dt as certificate_valid_date  from  ic_cardinfo_req where ic_NO =#cardNo# and ic_INDEX=#cardIndex#";
		IcInfoReg reg = new IcInfoReg();
		reg.setCardNo(cardNo);
		reg.setCardIndex(cardIndex);		
		IcInfoReg result = template.queryRow(sql, reg, IcInfoReg.class);
		return result;
	}
	
//	/**
//	 * 根据卡号获取IC卡信息(光大)
//	 * @param cardNo
//	 * @return
//	 */
//	public IcInfoReg getIcInfo(String cardNo) {
//		NamedQueryTemplate template = TemplateManager
//				.getNamedQueryTemplate(DS_NAME);
//		String sql = "select * from  IC_INFO_REG where CARD_NO =#cardNo#";
//		IcInfoReg reg = new IcInfoReg();
//		reg.setCardNo(cardNo);
//		if (LOG.isDebugEnabled()) {
//			LOG.debug("SELECT DB" + sql);
//		}
//		IcInfoReg result = template.queryRow(sql, reg, IcInfoReg.class);
//		return result;
//	}

	/**
	 * 根据卡号获取IC卡信息（带锁）
	 * 
	 * @param icno
	 * @return
	 */
	public IcInfoReg getIcInfoWithLock(String cardNo,String cardIndex) {
		NamedQueryTemplate template = TemplateManager
				.getNamedQueryTemplate(DS_NAME);
		String dbName = SystemProperties.get("database", "Oracle");
		String endSql = "";
		if ("DB2".equals(dbName) || "db2".equals(dbName) || "Db2".equals(dbName)) {
			endSql = " with RS";
		}		
		String sql = "select * from  IC_INFO_REG where CARD_NO =#CARD_NO# and CARD_INDEX = #cardIndex# for update" + endSql;
		IcInfoReg reg = new IcInfoReg();
		reg.setCardNo(cardNo);
		reg.setCardIndex(cardIndex);
		if (LOG.isDebugEnabled()) {
			LOG.debug("SELECT DB" + sql);
		}
		IcInfoReg result = template.queryRow(sql, reg, IcInfoReg.class);
		return result;
	}

	/**
	 * 更新IC卡信息
	 * 
	 * @param icInfoReg
	 * @return
	 */
	public int updateIcInfoRegByCardNo(IcInfoReg icInfoReg) {
		NamedUpdateTemplate template = TemplateManager
				.getNamedUpdateTemplate(DS_NAME);
		String sql = "update IC_INFO_REG set " +
					"PRODUCT_NO= #productNO#,ISSUER_BRANCH= #issuerBranch#," +
					"ACCT_NO_ID= #acctNoId#,CUSTOMER_NO= #customerNo#," +
					"ANNUAL_FEE= #annualFee#," +
					"MAGNETIC_STRIPE_CARD_NO= #magneticStripeCardNo#," +
					"SUPPLEMENT_CARD_FLAG= #supplementCardFlag#," +
					"CARTER_FLAG= #carterFlag#,PIN_DATA= #pinData#," +
					"IC_STATUS= #icStatus#,MAGNETIC_STATUS= #magneticStatus#," +
					"ISSUER_DATE= #issuerDate#,STATUS_CHANGE_DATE= #statusChangeDate#," +
					"CANCEL_DATE= #cancelDate#,CARD_VALID_DATE= #cardValidDate#," +
					"CERTIFICATE_VALID_DATE= #certificateValidDate#," +
					"RECLAIM_NUMBER= #reclaimNumber#," +
					"PASSWORD_MANAGE= #passwordManage#," +
					"DAC= #dac# " +
					"where " +
					"CARD_NO=#cardNo# and CARD_INDEX=#cardIndex#";
		if (LOG.isDebugEnabled()) {
			LOG.debug("update DB" + sql);
		}
		return template.update(sql, icInfoReg);
	}

	/**
	 * 更新卡登记簿(开卡专用) 
	 * @param cardNo
	 * @param cardIndex
	 * @param issuerBranch
	 * @param acctNoId
	 * @param customerNo
	 * @param icStatus
	 * @param issuerDate
	 * @param statusChangeDate
	 * @return
	 */
	public int updateIcInfoRegByOpenAccout(String cardNo,String cardIndex,String issuerBranch,String acctNoId,String customerNo,String icStatus,String issuerDate,String statusChangeDate) {
		NamedUpdateTemplate template = TemplateManager
				.getNamedUpdateTemplate(DS_NAME);
		String sql = "update IC_INFO_REG set ISSUER_BRANCH= #issuerBranch#,ACCT_NO_ID= #acctNoId#,CUSTOMER_NO= #customerNo#,IC_STATUS= #icStatus#,ISSUER_DATE= #issuerDate#,STATUS_CHANGE_DATE= #statusChangeDate# where CARD_NO=#cardNo# and CARD_INDEX=#cardIndex#";
		IcInfoReg icInfoReg = new IcInfoReg();
		icInfoReg.setAcctNoId(acctNoId);
		icInfoReg.setIcStatus(icStatus);
		icInfoReg.setIssuerBranch(issuerBranch);
		icInfoReg.setIssuerDate(issuerDate);
		icInfoReg.setCustomerNo(customerNo);
		icInfoReg.setStatusChangeDate(statusChangeDate);
		icInfoReg.setCardNo(cardNo);
		icInfoReg.setCardIndex(cardIndex);
		if (LOG.isDebugEnabled()) {
			LOG.debug("update DB" + sql);
		}
		return template.update(sql, icInfoReg);
	}

	/**
	 * 更新卡登记簿(更新卡参数修改标志)
	 * @param cardNo
	 * @return
	 */
	public int updateParamModFlag(String cardNo, String cardIndex, String paramModFlag) {
		NamedUpdateTemplate template = TemplateManager
				.getNamedUpdateTemplate(DS_NAME);
		String sql = "update IC_INFO_REG set PARAM_MOD_FLAG = #paramModFlag# where CARD_NO=#cardNo# and CARD_INDEX=#cardIndex#";
		if (LOG.isDebugEnabled()) {
			LOG.debug("update DB" + sql);
		}
		IcInfoReg icInfoReg = new IcInfoReg();
		icInfoReg.setCardNo(cardNo);
		icInfoReg.setCardIndex(cardIndex);
		icInfoReg.setParamModFlag(paramModFlag);
		return template.update(sql, icInfoReg);
	}
	
	/**
	 * 更新卡登记簿(更新状态)
	 * @param cardNo
	 * @param icInfoReg
	 * @return
	 */
	public int updateIcInfoRegNature(String cardNo, String cardIndex,String statusChangeDate,
			String icStatus) {
		NamedUpdateTemplate template = TemplateManager
				.getNamedUpdateTemplate(DS_NAME);
		String sql = "update IC_INFO_REG set "
				+ "IC_STATUS= #icStatus#,STATUS_CHANGE_DATE= #statusChangeDate# where CARD_NO=#cardNo# and CARD_INDEX=#cardIndex#";
		if (LOG.isDebugEnabled()) {
			LOG.debug("update DB" + sql);
		}
		IcInfoReg icInfoReg = new IcInfoReg();
		icInfoReg.setCardNo(cardNo);
		icInfoReg.setCardIndex(cardIndex);
		icInfoReg.setStatusChangeDate(statusChangeDate);
		icInfoReg.setIcStatus(icStatus);
		return template.update(sql, icInfoReg);
	}

	/**
	 * 更新卡登记簿(销卡专用)
	 * 
	 * @param icInfoReg
	 * @return
	 */
	public int updateIcInfoRegCancle(String cardNo,String cardIndex,String icStatus,String statusChangeDate,String cancelDate) {
		NamedUpdateTemplate template = TemplateManager
				.getNamedUpdateTemplate(DS_NAME);
		String sql = "update IC_INFO_REG set IC_STATUS= #icStatus#,STATUS_CHANGE_DATE= #statusChangeDate#,CANCEL_DATE=#cancelDate# where CARD_NO=#cardNo# and CARD_INDEX=#cardIndex#";
		if (LOG.isDebugEnabled()) {
			LOG.debug("update DB" + sql);
		}
		IcInfoReg icInfoReg = new IcInfoReg();
		icInfoReg.setCardIndex(cardIndex);
		icInfoReg.setCardNo(cardNo);
		icInfoReg.setStatusChangeDate(statusChangeDate);
		icInfoReg.setCancelDate(cancelDate);
		icInfoReg.setIcStatus(icStatus);
		
		return template.update(sql, icInfoReg);
	}

	/**
	 * 根据客户账户标识查询正常卡片(可能多条)
	 * 
	 * @param acctNoId
	 * @return
	 */
	public List<IcInfoReg> getNatureCardByAcctNoId(String acctNoId) {
		NamedQueryTemplate template = TemplateManager
				.getNamedQueryTemplate(DS_NAME);
		String sql = "select * from  IC_INFO_REG where ACCT_NO_ID =#ACCT_NO_ID# and IC_STATUS=#IC_STATUS#";
		IcInfoReg reg = new IcInfoReg();
		reg.setAcctNoId(acctNoId);
		reg.setIcStatus(ICSTATUS.NATURE.getFlag());
		if (LOG.isDebugEnabled()) {
			LOG.debug("SELECT DB" + sql);
		}
		List<IcInfoReg> list = template.query(sql, reg, IcInfoReg.class);
		return list;
	}

	/**
	 * 由卡号前18位和开卡数量获取真实卡号的对象IcInfoReg List
	 * 
	 * @param cardNoPrefix
	 *            起始卡号的前18位
	 * @param sum
	 *            数量
	 * @return
	 */
	public List<IcInfoReg> getIcInfoRegByBatch(String cardNo, int sum) {
		NamedQueryTemplate template = TemplateManager
				.getNamedQueryTemplate(DS_NAME);
		// 前六位是卡bin，然后是2位城市代码，然后是10位序号
		String cardBinAndBankCode = cardNo.substring(0, 8);
		String cardSerialNo = cardNo.substring(8, 18);
		long icardSerialNo = Long.parseLong(cardSerialNo);
		long iPreFixMax = icardSerialNo + sum - 1;
		String cardSerialNoAfter = seiralFormat(icardSerialNo,
				new AtomicInteger(10));
		String PreFixMaxAfter = seiralFormat(iPreFixMax, new AtomicInteger(10));
		String sql = "select * from  IC_INFO_REG where card_No <= '"
				+ cardBinAndBankCode + PreFixMaxAfter + "9' and card_No >= '"
				+ cardBinAndBankCode + cardSerialNoAfter + "0'";

		IcInfoReg reg = new IcInfoReg();
		if (LOG.isDebugEnabled()) {
			LOG.debug("SELECT DB" + sql);
		}
		List<IcInfoReg> result = template.query(sql, reg, IcInfoReg.class);
		return result;

	}

	/**
	 * 采取前补0的方式来格式化
	 * 
	 * @param serial
	 * @param length
	 * @return
	 */
	private static String seiralFormat(long serial, AtomicInteger serialLength) {
		String sSerial = serial + "";
		StringBuffer sbSerial = new StringBuffer();
		for (int i = 0; i < serialLength.intValue() - sSerial.length(); i++) {
			sbSerial.append("0");
		}
		return sbSerial.append(sSerial).toString();
	}

	/**
	 * 根据起始卡号、结束卡号批量查询卡状态不是未启用的IC卡信息（只返回一条） 批量入库卡号校验交易使用（光大）
	 * 
	 * @author zhaosongpeng
	 * @param startCardNo
	 *            起始卡号
	 * @param endCardNo
	 *            结束卡号
	 * @return IcInfoReg
	 */
	public IcInfoReg getIcInfoRegAndUnUseStatusByBatch(String startCardNo,
			String endCardNo) {
		NamedQueryTemplate template = TemplateManager
				.getNamedQueryTemplate(DS_NAME);
		IcInfoReg reg = new IcInfoReg();

		String sql = "SELECT * FROM IC_INFO_REG WHERE CARD_NO = "
				+ "(SELECT MIN(CARD_NO) FROM IC_INFO_REG WHERE CARD_NO >='"
				+ startCardNo + "' " + "AND CARD_NO<='" + endCardNo + "' "
				+ "AND IC_STATUS <> '1')";

		if (LOG.isDebugEnabled()) {
			LOG.debug("SELECT DB" + sql);
		}
		return template.queryRow(sql, reg, IcInfoReg.class);

	}

	/**
	 * 根据手机SIM卡ID查询IC卡登记簿
	 * 
	 * @author zhaosongpeng
	 * @param phomeSim
	 *            手机SIM卡ID
	 * @return List<IcInfoReg>
	 */
	public List<IcInfoReg> getIcCardNoByPhoneSim(String phoneSim) {
		NamedQueryTemplate template = TemplateManager
				.getNamedQueryTemplate(DS_NAME);
		IcInfoReg reg = new IcInfoReg();

		String sql = "SELECT * FROM IC_INFO_REG "
				+ "WHERE MAGNETIC_STRIPE_CARD_NO=" + "'" + phoneSim + "'";

		if (LOG.isDebugEnabled()) {
			LOG.debug("SELECT DB" + sql);
		}
		List<IcInfoReg> result = template.query(sql, reg, IcInfoReg.class);
		return result;
	}

	/**
	 * 更新运营商标识（手机支付卡专用）
	 * 
	 * @param cardNo
	 *            卡号
	 * @param logo
	 *            标识
	 * @return
	 */
	public int updateIcInfoRegForLogo(String cardNo, String logo) {
		NamedUpdateTemplate template = TemplateManager
				.getNamedUpdateTemplate(DS_NAME);
		String sql = "update IC_INFO_REG set "
				+ "MAGNETIC_STRIPE_CARD_NO= #MAGNETIC_STRIPE_CARD_NO# where CARD_NO=#CARD_NO#";
		IcInfoReg icInfoReg = new IcInfoReg();
		icInfoReg.setCardNo(cardNo);
		icInfoReg.setMagneticStripeCardNo(logo);
		if (LOG.isDebugEnabled()) {
			LOG.debug("update DB" + sql);
		}
		return template.update(sql, icInfoReg);
	}

	/**
	 * 根据账号获取ic卡信息列表
	 * 
	 * @author
	 * @param acctno
	 * @param pageNo 当前页数
	 * @param queryNum 每页显示条目数
	 * @return List<IcInfoReg>
	 */
	public List<IcInfoReg> getIcInfoListByAcctNo(String acctno, int pageNo,
			int queryNo) {
		NamedQueryTemplate template = TemplateManager
				.getNamedQueryTemplate(DS_NAME);
		IcInfoReg reg = new IcInfoReg();

		StringBuffer sqlBuilder = new StringBuffer();

		sqlBuilder.append("select t3.* from (select * from ic_info_reg t1 ");
		sqlBuilder.append(" where t1.acct_no_id in (select t2.ic_acct_no ");
		sqlBuilder.append(" from ic_dc_acct_reg t2 where t2.bind_acct_no = '");
		sqlBuilder.append(acctno);
		sqlBuilder.append("' and t2.bind_state='0')) t3, IC_PROD_ATTR t4 ");
		sqlBuilder
				.append(" where t3.PRODUCT_NO = t4.product_no and (t4.template_no='11' or t4.template_no='011')");

		LOG.debug("SQL:=======" + sqlBuilder.toString());
		List<IcInfoReg> result = template.query(sqlBuilder.toString(), reg,
				IcInfoReg.class,pageNo,queryNo);
		return result;
	}

	public List<IcInfoReg> getIcInfoListOfOpenCardByIssuerBranch(
			String tranBranch, String startDate, String endDate,
			String startPageNum, String queryPageNum) {
		NamedQueryTemplate template = TemplateManager
				.getNamedQueryTemplate(DS_NAME);
		IcInfoReg reg = new IcInfoReg();
		int pageNo=Integer.parseInt(startPageNum)/Integer.parseInt(queryPageNum)+1;
		StringBuffer sqlBuilder = new StringBuffer();
		sqlBuilder.append("SELECT * FROM IC_INFO_REG WHERE ISSUER_BRANCH=#ISSUER_BRANCH# AND ISSUER_DATE>='");
		sqlBuilder.append(startDate);
		sqlBuilder.append("' AND ISSUER_DATE<='");
		sqlBuilder.append(endDate);
		sqlBuilder.append("'");

		reg.setIssuerBranch(tranBranch);
		if (LOG.isDebugEnabled()) {
			LOG.debug("SQL:=======" + sqlBuilder.toString());
		}
		List<IcInfoReg> result = template.query(sqlBuilder.toString(), reg, IcInfoReg.class, pageNo, Integer.parseInt(queryPageNum));
		return result;
	}

	public int getIcInfoListOfOpenCardByIssuerBranchNum(String tranBranch,
			String startDate, String endDate) {
		NamedQueryTemplate template = TemplateManager
				.getNamedQueryTemplate(DS_NAME);
		Num num = new Num();

		StringBuffer sqlBuilder = new StringBuffer();
		sqlBuilder
				.append("SELECT  count(*) as rowCount FROM IC_INFO_REG WHERE ISSUER_BRANCH='"
						+ tranBranch
						+ "' AND ISSUER_DATE>='"
						+ startDate
						+ "' AND ISSUER_DATE<='" + endDate + "'");

		if (LOG.isDebugEnabled()) {
			LOG.debug("SQL:=======" + sqlBuilder.toString());
		}
		num = template.queryRow(sqlBuilder.toString(), null, Num.class);
		return num.getRowCount();
	}

	public List<IcInfoReg> getIcInfoListOfLossCardByIssuerBranch(
			String tranBranch, String startDate, String endDate,
			String startPageNum, String queryPageNum) {
		NamedQueryTemplate template = TemplateManager
				.getNamedQueryTemplate(DS_NAME);
		IcInfoReg reg = new IcInfoReg();

		int pageNo=Integer.parseInt(startPageNum)/Integer.parseInt(queryPageNum)+1;
		StringBuffer sqlBuilder = new StringBuffer();
		sqlBuilder.append("SELECT * FROM IC_INFO_REG WHERE ISSUER_BRANCH=#ISSUER_BRANCH# AND CARD_NO IN (SELECT DISTINCT CARD_NO FROM IC_ABN_INFO WHERE ABNORMAL_STATE='0' AND TRAN_DATE>='");
		sqlBuilder.append(startDate);
		sqlBuilder.append("' AND TRAN_DATE<='");
		sqlBuilder.append(endDate);
		sqlBuilder.append("')");
		reg.setIssuerBranch(tranBranch);
		if (LOG.isDebugEnabled()) {
			LOG.debug("SQL:=======" + sqlBuilder.toString());
		}
		List<IcInfoReg> result = template.query(sqlBuilder.toString(), reg,
				IcInfoReg.class,pageNo,Integer.parseInt(queryPageNum));
		return result;
	}

	public int getIcInfoListOfLossCardByIssuerBranchNum(String tranBranch,
			String startDate, String endDate) {
		NamedQueryTemplate template = TemplateManager
				.getNamedQueryTemplate(DS_NAME);
		Num num = new Num();

		StringBuffer sqlBuilder = new StringBuffer();
		sqlBuilder
				.append("SELECT count(*) as rowCount FROM IC_INFO_REG WHERE ISSUER_BRANCH='"
						+ tranBranch
						+ "' AND CARD_NO IN (SELECT DISTINCT CARD_NO FROM IC_ABN_INFO WHERE ABNORMAL_STATE='0' AND TRAN_DATE>='"
						+ startDate + "' AND TRAN_DATE<='" + endDate + "')");

		if (LOG.isDebugEnabled()) {
			LOG.debug("SQL:=======" + sqlBuilder.toString());
		}
		num = template.queryRow(sqlBuilder.toString(), null, Num.class);
		return num.getRowCount();
	}

	public List<IcInfoReg> getIcInfoListOfCancelCardByIssuerBranch(
			String tranBranch, String startDate, String endDate,
			String startPageNum, String queryPageNum) {
		NamedQueryTemplate template = TemplateManager
				.getNamedQueryTemplate(DS_NAME);
		IcInfoReg reg = new IcInfoReg();
		int pageNo=Integer.parseInt(startPageNum)/Integer.parseInt(queryPageNum)+1;
		String startNum = startPageNum;

		StringBuffer sqlBuilder = new StringBuffer();

		sqlBuilder.append("SELECT * FROM IC_INFO_REG WHERE ISSUER_BRANCH=#ISSUER_BRANCH# AND CANCEL_DATE>='");
		sqlBuilder.append(startDate);
		sqlBuilder.append("' AND CANCEL_DATE<='");
		sqlBuilder.append(endDate);
		sqlBuilder.append("'");

		reg.setIssuerBranch(tranBranch);
		if (LOG.isDebugEnabled()) {
			LOG.debug("SQL:=======" + sqlBuilder.toString());
		}
		List<IcInfoReg> result = template.query(sqlBuilder.toString(), reg, IcInfoReg.class, pageNo, Integer.parseInt(queryPageNum));
		return result;
	}

	public int getIcInfoListOfCancelCardByIssuerBranchNum(String tranBranch,
			String startDate, String endDate) {
		NamedQueryTemplate template = TemplateManager
				.getNamedQueryTemplate(DS_NAME);
		Num num = new Num();

		StringBuffer sqlBuilder = new StringBuffer();
		sqlBuilder
				.append("SELECT count(*) as rowCount FROM IC_INFO_REG WHERE ISSUER_BRANCH='"
						+ tranBranch
						+ "' AND CANCEL_DATE>='"
						+ startDate
						+ "' AND CANCEL_DATE<='" + endDate + "'");

		if (LOG.isDebugEnabled()) {
			LOG.debug("SQL:=======" + sqlBuilder.toString());
		}
		num = template.queryRow(sqlBuilder.toString(), null, Num.class);
		return num.getRowCount();
	}

	/**
	 * 根据账号获取纯电子现金卡条目数
	 * 
	 * @author
	 * @param acctno
	 * @param startNum
	 * @param queryNum
	 * @return List<IcInfoReg>
	 */
	public int getPureIcNumByAcctNo(String acctno) {
		NamedQueryTemplate template = TemplateManager
				.getNamedQueryTemplate(DS_NAME);
		Num num = new Num();
		StringBuffer sqlBuilder = new StringBuffer();

		sqlBuilder
				.append(" select count(t3.card_No) as rowCount from (select * from ic_info_reg t1 ");
		sqlBuilder.append(" where t1.ic_status = '2' and t1.acct_no_id in (select t2.ic_acct_no ");
		sqlBuilder.append(" from ic_dc_acct_reg t2 where t2.bind_acct_no = '");
		sqlBuilder.append(acctno);
		sqlBuilder.append("' and t2.bind_state='0')) t3, IC_PROD_ATTR t4 ");
		sqlBuilder
				.append(" where t3.PRODUCT_NO = t4.product_no and (t4.template_no='11' or t4.template_no='011') ");

		if (LOG.isDebugEnabled()) {
			LOG.debug("SQL:=======" + sqlBuilder.toString());
		}
		num = template.queryRow(sqlBuilder.toString(), null, Num.class);
		return num.getRowCount();
	}

	/**
	 * 根据旧的发卡机构号,更新为新的发卡机构号.机构撤并时使用
	 * 
	 * @param preBranch
	 *            旧的发卡机构号
	 * @param lastBranch
	 *            新的发卡机构号
	 * @return
	 */
	public void updateIssueBranch(String preBranch, String lastBranch) {
		NamedUpdateTemplate template = TemplateManager
				.getNamedUpdateTemplate(DS_NAME);
		String sql = "update IC_INFO_REG set ISSUER_BRANCH='" + lastBranch
				+ "' where ISSUER_BRANCH='" + preBranch+"'";
		template.update(sql, null);
	}

	public static class Num {
		private Integer rowCount;

		public Integer getRowCount() {
			return rowCount;
		}

		public void setRowCount(Integer rowCount) {
			this.rowCount = rowCount;
		}
	}

	/**
	 * 根据银行卡号获取纯电子现金卡
	 * 
	 * @author
	 * @param acctno
	 * @param startNum
	 * @param queryNum
	 * @return List<IcInfoReg>
	 */

	public int getPureIcNumByMagneticStripeCardNo(String cardNo) {
		NamedQueryTemplate template = TemplateManager.getNamedQueryTemplate(DS_NAME);
			Num num = new Num();
			StringBuffer sqlBuilder = new StringBuffer();
			sqlBuilder.append(" select count(t3.card_No) as rowCount from (select * from ic_info_reg t1 ");
			sqlBuilder.append(" where t1.acct_no_id in (select t2.ic_acct_no ");
			sqlBuilder.append(" from ic_dc_acct_reg t2 where t2.bind_acct_no = '");
			sqlBuilder.append(cardNo);
			sqlBuilder.append("' and t2.bind_state='0') and  t1.card_no !='");
			sqlBuilder.append(cardNo);	
			sqlBuilder.append("') t3, IC_PROD_ATTR t4 ");
			sqlBuilder.append(" where t3.PRODUCT_NO = t4.product_no and (t4.template_no='11' or t4.template_no='011') ");
			if (LOG.isDebugEnabled()) {
				LOG.debug("SQL:=======" + sqlBuilder.toString());
			}
			num = template.queryRow(sqlBuilder.toString(), null, Num.class);
			return num.getRowCount();
		}
	
	/**
	 * 根据卡号，状态获取卡登记簿信息（无卡序号）
	 * @param cardNo
	 * @param icStatus
	 * @return
	 */
	public IcInfoReg getCardInfoOnNoIndex(String cardNo, String icStatus) {
		NamedQueryTemplate template = TemplateManager
				.getNamedQueryTemplate(DS_NAME);
		String sql = "select * from  IC_INFO_REG where CARD_NO =#cardNo# and IC_STATUS=#icStatus#";
		IcInfoReg reg = new IcInfoReg();
		reg.setCardNo(cardNo);
		reg.setIcStatus(icStatus);
		if (LOG.isDebugEnabled()) {
			LOG.debug("SELECT DB" + sql);
		}
		IcInfoReg result = template.queryRow(sql, reg, IcInfoReg.class);
		return result;

	}
	
	/**
	 * 根据卡号，状态获取卡序号最大的卡登记簿信息（无卡序号）
	 * @param cardNo
	 * @param icStatus
	 * @return
	 */
	public IcInfoReg getCardInfoMaxIndex(String cardNo, String icStatus) {
		NamedQueryTemplate template = TemplateManager
				.getNamedQueryTemplate(DS_NAME);
		String sql = "select * from  IC_INFO_REG where CARD_NO =#cardNo# and IC_STATUS=#icStatus# order by CARD_INDEX DESC";
		IcInfoReg reg = new IcInfoReg();
		reg.setCardNo(cardNo);
		reg.setIcStatus(icStatus);
		if (LOG.isDebugEnabled()) {
			LOG.debug("SELECT DB" + sql);
		}
		List<IcInfoReg> list = template.query(sql, reg, IcInfoReg.class);
		if(null == list || list.size()==0){
			return null;
		}
		IcInfoReg result = list.get(0);
		list.clear();
		return result;
	}
	
	/**
	 * 根据卡号获取卡序号最大的卡登记簿信息（无卡序号）
	 * @param cardNo
	 * @param icStatus
	 * @return
	 */
	public IcInfoReg getCardMaxIndexByNo(String cardNo) {
		NamedQueryTemplate template = TemplateManager
				.getNamedQueryTemplate(DS_NAME);
		String sql = "select * from  IC_INFO_REG where CARD_NO =#cardNo# order by CARD_INDEX DESC";
		IcInfoReg reg = new IcInfoReg();
		reg.setCardNo(cardNo);
		if (LOG.isDebugEnabled()) {
			LOG.debug("SELECT DB" + sql);
		}
		List<IcInfoReg> list = template.query(sql, reg, IcInfoReg.class);
		if(null == list || list.size()==0){
			return null;
		}
		IcInfoReg result = list.get(0);
		list.clear();
		return result;
	}
	
	/*
	 * 异常登记薄查询新卡号
	 */
	public Object[] getAbnNewCard(String cardNo,String cardIndex){
		NamedQueryTemplate template = TemplateManager.getNamedQueryTemplate(DS_NAME);
		String sql = "select new_ic_no from  ic_cardabn_req where ic_no=#cardNo# and ic_index=#cardIndex#";
		Map<String,Object> para = new HashMap<String, Object>();
		para.put("cardNo", cardNo);
		para.put("cardIndex", cardIndex);
		Map<String,Object> temp = template.queryRow(sql, para);
		return new Object[]{null == temp ? null:temp.get("new_ic_no"),cardIndex};
	}
}
