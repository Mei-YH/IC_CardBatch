package com.icsys.batch.encryptor;

public class ARQCInput {
	
	/**
	 * 表示加密机中保存的AC主密钥的索引
	 */
	private String acKeyIdx;
	
	/**
	 * 工作模式
	 * 1 为只进行验证ARQC/TC/AAC
	 * 2 为验证ARQC/TC/AAC，并产生ARPC
	 * 3 为产生ARPC
	 */
	private String trnMod;
	
	/**
	 * 卡片主账号
	 */
	private String acctNo;
	
	/**
	 * 应用序列号
	 */
	private String appNO;
	
	/**
	 * 卡片交易计数器
	 * 4位16进制格式
	 */
	private String icTrnCnt;
	
	/**
	 * 密文数据
	 * 以ASCII码方式提供
	 */
	private String arqc;
	
	/**
	 * 产生密文数据元
	 * 以ASCII码方式提供
	 */
	private String aqdt;
	
	/**
	 * ARC
	 * 
	 */
	private String arc;

	
	
	public String getArc() {
		return arc;
	}

	public void setArc(String arc) {
		this.arc = arc;
	}

	public String getAcKeyIdx() {
		return acKeyIdx;
	}

	public void setAcKeyIdx(String acKeyIdx) {
		this.acKeyIdx = acKeyIdx;
	}

	public String getTrnMod() {
		return trnMod;
	}

	public void setTrnMod(String trnMod) {
		this.trnMod = trnMod;
	}

	public String getAcctNo() {
		return acctNo;
	}

	public void setAcctNo(String acctNo) {
		this.acctNo = acctNo;
	}

	public String getAppNO() {
		return appNO;
	}

	public void setAppNO(String appNO) {
		this.appNO = appNO;
	}

	public String getIcTrnCnt() {
		return icTrnCnt;
	}

	public void setIcTrnCnt(String icTrnCnt) {
		this.icTrnCnt = icTrnCnt;
	}

	public String getArqc() {
		return arqc;
	}

	public void setArqc(String arqc) {
		this.arqc = arqc;
	}

	public String getAqdt() {
		return aqdt;
	}

	public void setAqdt(String aqdt) {
		this.aqdt = aqdt;
	}

	@Override
	public String toString() {
		return "ARQCInput [acKeyIdx=" + acKeyIdx + ", acctNo=" + acctNo
				+ ", appNO=" + appNO + ", aqdt=" + aqdt + ", arc=" + arc
				+ ", arqc=" + arqc + ", icTrnCnt=" + icTrnCnt + ", trnMod="
				+ trnMod + "]";
	}

	
	
	
	

}
