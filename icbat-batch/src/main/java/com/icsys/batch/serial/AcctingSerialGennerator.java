package com.icsys.batch.serial;




/**
 * @author Runpu Hu
 * @version 创建时间：2011-4-28 下午02:29:37 类说明 ：账务系统流水号生成器
 */

public class AcctingSerialGennerator {

	private final static String INDEX = "01";

	private final static String TABLE = "GR_SYS_SERCTRL";

	public static String getSerial(String acctDate) throws Exception {
		ActiveSerialGennerator gennerator = new ActiveSerialGennerator(INDEX,
				TABLE);
		String serial = gennerator.getSerial();
		//流水号为日期加流水号
		return acctDate + serial;
	}
	
	


}
