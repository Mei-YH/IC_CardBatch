package com.icsys.batch.encryptor;

/**
 * 
 * @author liuyb
 *
 */
public class TbTsm {
	private Integer hsmId;
	private String hsmName;
	private Integer hsmType;
	private String hsmIp;
	private Integer hsmPort;
	private Integer humConn;
	private String hsmMemo;
	private Integer hsmState;
	
	public void setHsmId(Integer hsmId){
		this.hsmId = hsmId;
	}
	
	public Integer getHsmId(){
		return hsmId;
	}
	
	public void setHsmName(String hsmName){
		this.hsmName = hsmName;
	}
	
	public String getHsmName(){
		return hsmName;
	}
	
	public void setHsmType(Integer hsmType){
		this.hsmType = hsmType;
	}
	
	public Integer getHsmType(){
		return hsmType;
	}
	
	public void setHsmIp(String hsmIp){
		this.hsmIp = hsmIp;
	}
	
	public String getHsmIp(){
		return hsmIp;
	}
	
	public void setHsmPort(Integer hsmPort){
		this.hsmPort = hsmPort;
	}
	
	public Integer getHsmPort(){
		return hsmPort;
	}
	
	public void setHumConn(Integer humConn){
		this.humConn = humConn;
	}
	
	public Integer getHumConn(){
		return humConn;
	}
	
	public void setHsmMemo(String hsmMemo){
		this.hsmMemo = hsmMemo;
	}
	
	public String getHsmMemo(){
		return hsmMemo;
	}
	
	public void setHsmState(Integer hsmState){
		this.hsmState = hsmState;
	}
	
	public Integer getHsmState(){
		return hsmState;
	}
}
