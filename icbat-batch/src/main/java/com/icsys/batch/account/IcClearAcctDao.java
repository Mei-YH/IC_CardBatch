package com.icsys.batch.account;

import java.util.List;

import org.apache.log4j.Logger;

import com.icsys.batch.util.SystemConstans;
import com.icsys.platform.dao.template.NamedQueryTemplate;
import com.icsys.platform.dao.template.NamedUpdateTemplate;
import com.icsys.platform.dao.template.TemplateManager;

/**
 * @author Runpu Hu
 * @version 创建时间：2011-4-25 上午09:52:11 类说明 ：
 */

public class IcClearAcctDao {
	public static final String DS_NAME = SystemConstans.DB_SOURCE_NAME;

	private static Logger LOG = Logger.getLogger(IcClearAcctDao.class);

	/**
	 * IC卡异常卡待清算账户表新加记录
	 * @param icClearAcct
	 * @return
	 */
	public int addIcClearAcct(IcClearAcct icClearAcct) {
		NamedUpdateTemplate template = TemplateManager
				.getNamedUpdateTemplate(DS_NAME);
		String sql = "insert into IC_CLEAR_ACCT (CARD_NO,CARD_INDEX,AID,OFFLINE_CLEAR_SUB_ACCT,AMOUNT) values (#cardNo#,#cardIndex#,#aid#,#offlineClearSubAcct#,#amount#)";
		if (LOG.isDebugEnabled()) {
			LOG.debug("新加IC卡异常卡待清算账户表记录:" + sql + icClearAcct);
		}
		int result = template.update(sql, icClearAcct);
		return result;
	}
	
	/**
	 * 根据卡号获取待清算帐号
	 * @param cardNo 卡号
	 * @return
	 * @throws NoSuchCardWaitedClearAcctNoException 
	 */
	public List<IcClearAcct> getAcctBycardNO(String cardNo,String cardIndex) {
		NamedQueryTemplate template = TemplateManager.getNamedQueryTemplate(DS_NAME);
		String sql = "select * from IC_CLEAR_ACCT where CARD_NO=#cardNo# and CARD_INDEX=#cardIndex#";
		IcClearAcct reg = new IcClearAcct();
		reg.setCardNo(cardNo);
		reg.setCardIndex(cardIndex);
		if (LOG.isDebugEnabled()) {
			LOG.debug("SELECT DB" + sql);
		}
		List<IcClearAcct> list = template.query(sql, reg, IcClearAcct.class);
		return list;
	}
	
	/**
	 * 根据卡号应用编号获取待清算帐号
	 * @param cardNo 卡号
	 * @param appNo 应用编号
	 * @return
	 * @throws NoSuchCardWaitedClearAcctNoException 
	 */
	public IcClearAcct getIcClearAcct(String cardNo,String cardIndex, String appNo) {
		NamedQueryTemplate template = TemplateManager
				.getNamedQueryTemplate(DS_NAME);
		String sql = "select cag_offacct as offline_clear_sub_acct,cc_canmoney as amount,ic_cardno as card_no from gr_card_clear where app_id=#AID# and ic_cardno=#cardNo# and ic_index=#cardIndex#";
		IcClearAcct reg = new IcClearAcct();
		reg.setCardNo(cardNo);
		reg.setAid(appNo);
		reg.setCardIndex(cardIndex);
		if (LOG.isDebugEnabled()) {
			LOG.debug("SELECT DB" + sql);
		}
		IcClearAcct result = template.queryRow(sql, reg, IcClearAcct.class);
		
		return result;
	}
	/**
	 * 更新IC卡异常卡待清算账户表
	 * @param icInfoReg
	 * @return
	 */
	public int updateIcInfoRegByCardNo(IcClearAcct IcClearAcct) {
		NamedUpdateTemplate template = TemplateManager
				.getNamedUpdateTemplate(DS_NAME);
		String sql = "update IC_CLEAR_ACCT set OFFLINE_CLEAR_SUB_ACCT= #offlineClearSubAcct# where AID=#AID# and CARD_NO=#cardNo# amd CARD_INDEX=#cardIndex#";
		if (LOG.isDebugEnabled()) {
			LOG.debug("update DB" + sql);
		}
		return template.update(sql, IcClearAcct);
	}

}
