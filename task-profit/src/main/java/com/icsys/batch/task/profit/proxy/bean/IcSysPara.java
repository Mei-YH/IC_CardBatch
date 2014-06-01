package com.icsys.batch.task.profit.proxy.bean;
/**
 * 系统参数表
 * @author SDNX
 *
 */
public class IcSysPara {
	private String praKey;//参数索引
	private String paraDesc;//参数名称
	private String paraValues;//参数值
	private String useDesc;//参数使用说明
	private String paraCtrl;//参数控制标志
	public String getPraKey() {
		return praKey;
	}
	public void setPraKey(String praKey) {
		this.praKey = praKey;
	}
	public String getParaDesc() {
		return paraDesc;
	}
	public void setParaDesc(String paraDesc) {
		this.paraDesc = paraDesc;
	}
	public String getParaValues() {
		return paraValues;
	}
	public void setParaValues(String paraValues) {
		this.paraValues = paraValues;
	}
	public String getUseDesc() {
		return useDesc;
	}
	public void setUseDesc(String useDesc) {
		this.useDesc = useDesc;
	}
	public String getParaCtrl() {
		return paraCtrl;
	}
	public void setParaCtrl(String paraCtrl) {
		this.paraCtrl = paraCtrl;
	}
	
	

}
