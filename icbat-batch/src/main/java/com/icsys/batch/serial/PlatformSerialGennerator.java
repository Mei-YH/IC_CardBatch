package com.icsys.batch.serial;

import java.util.concurrent.atomic.AtomicLong;

import org.apache.log4j.Logger;

import com.icsys.batch.serial.dao.SerialBeanDao;
import com.icsys.platform.dao.TXHelper;



public class PlatformSerialGennerator{

	private final static String INDEX = "ICDEALLOGSEQ";

	private final static String TABLE = "EOS_UNIQUE_TABLE";
	
	private static Logger LOG = Logger.getLogger(PlatformSerialGennerator.class);
	
	private static AtomicLong serial = new AtomicLong(1);
	
	private static AtomicLong limit = new AtomicLong();
	
	private static final int length = 8;
	
	private static final int CACHE_NUM = 1000;

	protected static long allocate() {
		// TODO Auto-generated method stub
		try {
			TXHelper.beginTX();
			SerialBeanDao dao = new SerialBeanDao();
			String icSerial = dao.getEosUnique(TABLE, INDEX);
//			int iSeriallength = 8;
			long lCurentLimit = Long.parseLong(icSerial);
			long lFutureLimit = lCurentLimit + CACHE_NUM;
//			length.set(iSeriallength);
			limit.set(lFutureLimit);
			if(lFutureLimit > Math.pow(10, length)){
				//如果是未来的上限大于长度最大的值,则初始化流水号
				lFutureLimit = 1;
				limit.set((long) Math.pow(10, length));
			}
			dao.updateEosUnique(INDEX, TABLE, lFutureLimit + "");
			TXHelper.commit();
			return lCurentLimit;
			
		} catch (Exception e) {
			// TODO: handle exception
			LOG.error(e.getMessage());
			TXHelper.rollback();
			throw new RuntimeException("生成流水号失败！");
		}finally{
			TXHelper.close();
		}
	}
	/**
	 * 获取流水号
	 * 
	 * @return
	 * @throws SerialGenneratorException 
	 */
	protected static String getSerial() throws Exception {

		synchronized (serial) {
			long sn = serial.getAndIncrement();
			if (sn >= getLimit()) {
				serial.set(allocate());
				sn = serial.getAndIncrement();
			}
			// System.out.println(Thread.currentThread().getId()+"------"+sn);
			return seiralFormat(sn, length);
		}
	}
	/**
	 * 采取前补0的方式来格式化流水号
	 * 
	 * @param serial
	 * @param length
	 * @return
	 */
	private static String seiralFormat(long serial, int serialLength) {
		String sSerial = serial + "";
		StringBuffer sbSerial = new StringBuffer();
		for (int i = 0; i < serialLength - sSerial.length(); i++) {
			sbSerial.append("0");
		}
		return sbSerial.append(sSerial).toString();
	}

	protected static long getLimit() {
		// TODO Auto-generated method stub
		return limit.get();
	}

	public static String getPlatformSerial(String sysdate)throws Exception{		
		//流水号为日期加流水号
		String temp = getSerial();
		return "0091" + sysdate.substring(2) + temp.substring(0,2) + "9" + temp.substring(2);
	}
	
}
