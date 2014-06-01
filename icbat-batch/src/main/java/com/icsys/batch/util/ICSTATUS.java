package com.icsys.batch.util;


/**
 * @author 
 * @version 创建时间：2011-4-15 下午04:57:19 
 * 类说明 ：IC卡状态
 * 
 *
 */
public enum ICSTATUS {
	OVERDUE("0"), UNUSE("1"), NATURE("2"), REPORTLOST("3"), BACK("4"), REPEAL(
			"5"), RENEW("6"), RENEW_AFTER_RL("7"), REPEAL_AFTER_RL("8"), LOCKED(
			"9"), REPEAL_LOAD("a"), REPEAL_SETTLE("b"),REPORTLOSS_WHILE("c");

	private String flag;

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}
	public static ICSTATUS getEnum(String status) throws Exception {
		int iStatus = -1;
		if (status.equals("a")) {
			iStatus = 10;
		} else if (status.equals("b")) {
			iStatus = 11;
		} else if(status.equals("c")){
			iStatus = 12;
		}else {
			try {
				iStatus = Integer.parseInt(status);
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				throw new Exception(status);
			}
		}
		//System.out.println(iStatus+"iStatus");
		if (iStatus < 0 || iStatus >= ICSTATUS.values().length) {
			throw new Exception(status);
		}

		return values()[iStatus];
	}
	ICSTATUS(String status) {
		this.flag = status;
	}
	
	public Boolean isNature() {
		if (flag.equals("2"))
			return true;
		else
			return false;
	}
	
	public Boolean isREPORTLOST() {
		if (flag.equals("3"))
			return true;
		else
			return false;
	}
}
