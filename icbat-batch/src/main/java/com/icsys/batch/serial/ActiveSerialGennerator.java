package com.icsys.batch.serial;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.log4j.Logger;

import com.icsys.batch.serial.bean.SerialBean;
import com.icsys.batch.serial.dao.SerialBeanDao;
import com.icsys.platform.dao.TXHelper;

/**
 * @author Runpu Hu
 * @version 创建时间：2011-5-3 下午05:10:34 类说明 ：动态流水号生成器
 */

public class ActiveSerialGennerator {
	
	private static Logger LOG = Logger.getLogger(ActiveSerialGennerator.class);


	private static AtomicLong serial = new AtomicLong(1);

	private static AtomicLong limit = new AtomicLong();
	
	private static AtomicInteger length = new AtomicInteger();

	private String index;

	private String table;

	public ActiveSerialGennerator(String index, String table) {
		super();
		this.index = index;
		this.table = table;
	}

	/**
	 * 获取流水号
	 * 
	 * @return
	 * @throws SerialGenneratorException 
	 */
	protected String getSerial() throws Exception {

		synchronized (serial) {
			long sn = serial.getAndIncrement();
			if (sn + 1 > this.getLimit()) {
				serial.set(this.allocate());
				sn = serial.getAndIncrement();
			}
			// System.out.println(Thread.currentThread().getId()+"------"+sn);
			return seiralFormat(sn, length);
		}
	}

	/**
	 * 分配流水号
	 * 
	 * @return
	 * @throws SerialGenneratorException 
	 */
	protected long allocate() throws Exception {

		try {
			TXHelper.beginTX();
			SerialBeanDao dao = new SerialBeanDao();
			SerialBean icSerial = dao.getSerialBeanForUpdate(index, table);
			String curentLimit = icSerial.getSerialLimit();
			String sum = icSerial.getCacheSum();
			String serialLength = icSerial.getSerialLength();
			int iSeriallength = Integer.parseInt(serialLength);
			long lCurentLimit = Long.parseLong(curentLimit);
			long lSum = Long.parseLong(sum);
			long lFutureLimit = lCurentLimit + lSum;
			length.set(iSeriallength);
			limit.set(lFutureLimit);
			String futureLimit = lFutureLimit + "";
			if(lFutureLimit > Math.pow(10, iSeriallength)){
				//如果是未来的上限大于长度最大的值,则初始化流水号
				futureLimit = "1";
				limit.set((long) Math.pow(10, iSeriallength));
			}
			dao.updateLimitByApp(index, table, futureLimit);
			TXHelper.commit();
			return lCurentLimit;
		} catch (Exception e) {
			// TODO: handle exception
			LOG.error(e.getMessage());
			TXHelper.rollback();
			throw new Exception("生成记账流水号失败！");
		}finally{
			TXHelper.close();
		}
	}
	
	/**
	 * 采取前补0的方式来格式化流水号
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
	 * 获取流水号上限
	 * 
	 * @return
	 */
	protected long getLimit() {
		return limit.get();
	}

}
