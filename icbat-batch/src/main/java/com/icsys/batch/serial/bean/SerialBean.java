package com.icsys.batch.serial.bean;
/** 
 * @author Runpu Hu  
 * @version 创建时间：2011-5-3 下午05:00:59 
 * 类说明 ：
 */

public class SerialBean {
	
	/**
	 * 流水索引
	 */
	private String serialIndex;
	
    /**
     * 当前流水号上限
     */
	private String serialLimit;
	
	/**
	 * 缓存数目
	 */
	private String cacheSum;
	
	/**
	 * 流水号长度
	 */
	private String serialLength;
	
	/**
	 * 备注
	 */
	private String remark;

	public String getSerialIndex() {
		return serialIndex;
	}

	public void setSerialIndex(String seriaIndex) {
		this.serialIndex = seriaIndex;
	}


	

	@Override
	public String toString() {
		return "SerialBean [cacheSum=" + cacheSum + ", remark=" + remark
				+ ", serialIndex=" + serialIndex + ", serialLength="
				+ serialLength + ", serialLimit=" + serialLimit + "]";
	}

	public String getSerialLength() {
		return serialLength;
	}

	public void setSerialLength(String serialLength) {
		this.serialLength = serialLength;
	}

	public String getSerialLimit() {
		return serialLimit;
	}

	public void setSerialLimit(String serialLimit) {
		this.serialLimit = serialLimit;
	}

	public String getCacheSum() {
		return cacheSum;
	}

	public void setCacheSum(String cacheSum) {
		this.cacheSum = cacheSum;
	}

	

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	

}
