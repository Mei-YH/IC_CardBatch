package com.icsys.batch.task.report.proxy.bean;

public class GrBusTrloglist {
	private String logTrcode;
	private String logTrdate;
	private String logTrtime ;
	private String logPlserial;
	private String logChno ;
	private String logChdate ;
	private String logChserial;
	private String logHdate;
	private String logHserial ;
	private String logTrbranch;
	private String icCardno;
	private String cardIndex;
	private String logCcard_no;
	private String appId;
	private String logOpteller;
	private String logTerno;
	private String logCheteller ;
	private String logAutteller ;
	private String logTrtype;
	private String logTrfuction ;
	private String logWrcstate ;
	private String logCertype ;
	private String logCerno;
	private String logRemark;
	private String logComresults ;
	private String  logTrstatus ;
	private String logOdtrdate;
	private String logOtserial ;
	private String atcNo ;
	private String requestId ;
	private String channelNo;
	public String getLogTrcode() {
		return logTrcode;
	}
	public void setLogTrcode(String logTrcode) {
		this.logTrcode = logTrcode;
	}
	public String getLogTrdate() {
		return logTrdate;
	}
	public void setLogTrdate(String logTrdate) {
		this.logTrdate = logTrdate;
	}
	public String getLogTrtime() {
		return logTrtime;
	}
	public void setLogTrtime(String logTrtime) {
		this.logTrtime = logTrtime;
	}
	public String getLogPlserial() {
		return logPlserial;
	}
	public void setLogPlserial(String logPlserial) {
		this.logPlserial = logPlserial;
	}
	public String getLogChno() {
		return logChno;
	}
	public void setLogChno(String logChno) {
		this.logChno = logChno;
	}
	public String getLogChdate() {
		return logChdate;
	}
	public void setLogChdate(String logChdate) {
		this.logChdate = logChdate;
	}
	public String getLogChserial() {
		return logChserial;
	}
	public void setLogChserial(String logChserial) {
		this.logChserial = logChserial;
	}
	public String getLogHdate() {
		return logHdate;
	}
	public void setLogHdate(String logHdate) {
		this.logHdate = logHdate;
	}
	public String getLogHserial() {
		return logHserial;
	}
	public void setLogHserial(String logHserial) {
		this.logHserial = logHserial;
	}
	public String getLogTrbranch() {
		return logTrbranch;
	}
	public void setLogTrbranch(String logTrbranch) {
		this.logTrbranch = logTrbranch;
	}
	public String getIcCardno() {
		return icCardno;
	}
	public void setIcCardno(String icCardno) {
		this.icCardno = icCardno;
	}
	public String getCardIndex() {
		return cardIndex;
	}
	public void setCardIndex(String cardIndex) {
		this.cardIndex = cardIndex;
	}
	public String getLogCcard_no() {
		return logCcard_no;
	}
	public void setLogCcard_no(String logCcardNo) {
		logCcard_no = logCcardNo;
	}
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public String getLogOpteller() {
		return logOpteller;
	}
	public void setLogOpteller(String logOpteller) {
		this.logOpteller = logOpteller;
	}
	public String getLogTerno() {
		return logTerno;
	}
	public void setLogTerno(String logTerno) {
		this.logTerno = logTerno;
	}
	public String getLogCheteller() {
		return logCheteller;
	}
	public void setLogCheteller(String logCheteller) {
		this.logCheteller = logCheteller;
	}
	public String getLogAutteller() {
		return logAutteller;
	}
	public void setLogAutteller(String logAutteller) {
		this.logAutteller = logAutteller;
	}
	public String getLogTrtype() {
		return logTrtype;
	}
	public void setLogTrtype(String logTrtype) {
		this.logTrtype = logTrtype;
	}
	public String getLogTrfuction() {
		return logTrfuction;
	}
	public void setLogTrfuction(String logTrfuction) {
		this.logTrfuction = logTrfuction;
	}
	public String getLogWrcstate() {
		return logWrcstate;
	}
	public void setLogWrcstate(String logWrcstate) {
		this.logWrcstate = logWrcstate;
	}
	public String getLogCertype() {
		return logCertype;
	}
	public void setLogCertype(String logCertype) {
		this.logCertype = logCertype;
	}
	public String getLogCerno() {
		return logCerno;
	}
	public void setLogCerno(String logCerno) {
		this.logCerno = logCerno;
	}
	public String getLogRemark() {
		return logRemark;
	}
	public void setLogRemark(String logRemark) {
		this.logRemark = logRemark;
	}
	public String getLogComresults() {
		return logComresults;
	}
	public void setLogComresults(String logComresults) {
		this.logComresults = logComresults;
	}
	public String getLogTrstatus() {
		return logTrstatus;
	}
	public void setLogTrstatus(String logTrstatus) {
		this.logTrstatus = logTrstatus;
	}
	public String getLogOdtrdate() {
		return logOdtrdate;
	}
	public void setLogOdtrdate(String logOdtrdate) {
		this.logOdtrdate = logOdtrdate;
	}
	public String getLogOtserial() {
		return logOtserial;
	}
	public void setLogOtserial(String logOtserial) {
		this.logOtserial = logOtserial;
	}
	public String getAtcNo() {
		return atcNo;
	}
	public void setAtcNo(String atcNo) {
		this.atcNo = atcNo;
	}
	public String getRequestId() {
		return requestId;
	}
	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}
	public String getChannelNo() {
		return channelNo;
	}
	public void setChannelNo(String channelNo) {
		this.channelNo = channelNo;
	}
	
	public String toString(){
		StringBuilder sBuilder = new StringBuilder();

		sBuilder.append(addStr(this.logTrcode));
		sBuilder.append(",");
		sBuilder.append(addStr(this.logTrdate));
		sBuilder.append(",");
		sBuilder.append(addStr(this.logTrtime));
		sBuilder.append(",");
		sBuilder.append(addStr(this.logPlserial));
		sBuilder.append(",");
		sBuilder.append(addStr(this.logChno));
		sBuilder.append(",");
		sBuilder.append(addStr(this.logChdate));
		sBuilder.append(",");
		sBuilder.append(addStr(this.logChserial));
		sBuilder.append(",");
		sBuilder.append(addStr(this.logHdate));
		sBuilder.append(",");
		sBuilder.append(addStr(this.logHserial));
		sBuilder.append(",");
		sBuilder.append(addStr(this.logTrbranch));
		sBuilder.append(",");
		sBuilder.append(addStr(this.icCardno));
		sBuilder.append(",");
		sBuilder.append(addStr(this.cardIndex));
		sBuilder.append(",");
		sBuilder.append(addStr(this.logCcard_no));
		sBuilder.append(",");
		sBuilder.append(addStr(this.appId));
		sBuilder.append(",");
		sBuilder.append(addStr(this.logOpteller));
		sBuilder.append(",");
		sBuilder.append(addStr(this.logTerno));
		sBuilder.append(",");
		sBuilder.append(addStr(this.logCheteller));
		sBuilder.append(",");
		sBuilder.append(addStr(this.logAutteller));
		sBuilder.append(",");
		sBuilder.append(addStr(this.logTrtype));
		sBuilder.append(",");
		sBuilder.append(addStr(this.logTrfuction));
		sBuilder.append(",");
		sBuilder.append(addStr(this.logWrcstate));
		sBuilder.append(",");
		sBuilder.append(addStr(this.logCertype));
		sBuilder.append(",");
		sBuilder.append(addStr(this.logCerno));
		sBuilder.append(",");
		sBuilder.append(addStr(this.logRemark));
		sBuilder.append(",");
		sBuilder.append(addStr(this.logComresults));
		sBuilder.append(",");
		sBuilder.append(addStr(this.logTrstatus));
		sBuilder.append(",");
		sBuilder.append(addStr(this.logOdtrdate));
		sBuilder.append(",");
		sBuilder.append(addStr(this.logOtserial));
		sBuilder.append(",");
		sBuilder.append(addStr(this.atcNo));
		sBuilder.append(",");
		sBuilder.append(addStr(this.requestId));
		sBuilder.append(",");
		sBuilder.append(addStr(this.channelNo));
		// 换行
		sBuilder.append("\n");
		return sBuilder.toString();
	}


	/**
	 * @param 给字符串添加转义字符 \"
	 * @return 返回带引号的字符串
	 */
	private String addStr (String str ){

		if (str==null){
			return "";
		}
		return "\""+str+"\"";
	}
}