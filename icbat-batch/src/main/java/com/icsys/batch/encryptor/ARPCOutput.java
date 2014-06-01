package com.icsys.batch.encryptor;

public class ARPCOutput {
	
	/**
	 * 响应代码,‘0000’成功，其他失败
	 */
	private String thdRetCod;
	
	/**
	 * 16位ARPC数据密文和4位授权码
	 * 当上传包报文中的SMKGZMS!=’0’时，此值可以为空。以ASCII码方式提供
	 */
	private String ARPC;

	public String getThdRetCod() {
		return thdRetCod;
	}

	public void setThdRetCod(String thdRetCod) {
		this.thdRetCod = thdRetCod;
	}

	public String getARPC() {
		return ARPC;
	}

	public void setARPC(String aRPC) {
		ARPC = aRPC;
	}

	@Override
	public String toString() {
		return "ARPC [ARPC=" + ARPC + ", thdRetCod=" + thdRetCod + "]";
	}
	
	
	
    
}
