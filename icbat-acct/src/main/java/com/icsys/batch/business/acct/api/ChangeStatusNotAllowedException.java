package com.icsys.batch.business.acct.api;

/**
 * 不允许改变状态异常类
 * 
 * @author kittyuu
 *
 */
public class ChangeStatusNotAllowedException extends AcctException {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1247641123654674417L;

	public ChangeStatusNotAllowedException(String acctNO, int status) {
		this.setErrorCode("00009");
		if (status == AcctStatus.CANCELD.getIntValue())
			this.setErrMsgArgs(new String[] { acctNO, "注销" });
		else
			this.setErrMsgArgs(new String[] { acctNO, "待结清" });
	}
}
