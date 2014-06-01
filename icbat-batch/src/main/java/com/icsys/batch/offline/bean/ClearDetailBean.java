package com.icsys.batch.offline.bean;

import org.apache.commons.lang.StringUtils;

import com.icsys.batch.util.Utils;

/** 
 * 62个字段
 * 银联清算交易记录格式
 * @author LiuYongLi
 * 电子现金脱机清算原始明细数据
 */
public class ClearDetailBean {
    //cups 银联卡跨行信息交换系统
    
    public ClearDetailBean() {
    }

    /* 与文件解析无关*/
    private String clearDate;//清算日期
    private String batchNo;
    private String batchSn;
    /* 与文件解析无关*/
    //表29 段0-基本清算信息+
    private String tranCode;//交易代码
    private String bitMap;//段位图
    private String acctNo;//主账号
    private String tranAmount;//交易金额
    private String txCurr;//交易货币代码
    private String key7;//交易传输时间
    private String key11;//系统跟踪号
    private String key38;//授权应答标识码
    private String authorizeDate;//授权日期
    private String key37;//检索参考号
    private String key32;//代理机构标识码
    private String key33;//发送机构标识码
    private String key18;//商户类型
    private String key41;//受卡机终端标识码
    private String key42;//受卡方标识码
    private String key43;//受卡方名称地址
    private String orginalInf;//原始交易信息
    private String reasonCode;//报文原因代码
    private String singleDoubleFlag;//单双信息标志
    private String cupsSerial;//CUPS流水号
    private String receiveBranch;//接收机构代码
    private String issuerBranch;//发卡机构代码
    private String cupsNotify;//CUPS通知标志
    private String channelNo;//交易发起渠道
    private String feature;//交易特征标识
    private String cpusReserved;//CUPS保留使用
    private String posSvr;//POS服务点条件代码
    private String fee;//本方手续费
    private String crossBorderFlag;//交易跨境标志
    private String reserved0;//保留使用
    
    private String tag9f26;//应用密文
    private String key22;//服务点输入方式码
    private String key23;//卡片序列号
    private String key6022;//终端读取能力
    private String key6023;//IC卡条件代码
    private String tag9f33;//终端性能
    private String tag95;//终端验证结果
    private String tag9f37;//不可预知数
    private String tag9f1e;//接口设备序列号(9F一E)
    private String tag9f10;//发卡行应用数据(cvr)
    private String tag9f36;//应用交易记数器
    private String tag82;//应用交互特征
    private String tag9a;//交易日期
    private String tag9f1a;//终端国家代码
    private String tranResponseCode;//交易响应码
    private String tag9c;//交易类型
    private String tag9f02;//授权金额
    private String tag5f2a;//交易币种代码
    private String checkTcResults;//应用密文校验结果
    private String key14;//卡有效期
    private String tag9f27;//密文信息数据
    private String tag9f03;//其它金额
    private String tag9f34;//持卡人验证方法结果
    private String tag9f35;//终端类型
    private String tag84;//专用文件名称
    private String tag8f09;//应用版本号
    private String tag9f41;//交易序列计数器
    private String reserved2;//保留使用
    
    private String origSysNo;//原始交易的系统跟踪号
    private String origTranTime;//原始交易日期时间
    
    /* 与文件解析无关*/
    private String amount;//实际交易金额
    private String status;//校验状态
    private String loseFlag;//挂账标志 0-未挂账 1-挂账
    private String returnNumber;//退货次数
    private String sumReturnAmount;//累计退货金额(数据库中为number类型)
    private String acctingDate;//IC平台记账日期
    private String platformSerial;//IC平台流水号
    private String acctingBranch;//IC卡账务机构
    private String fileSrc;//文件来源 1：本行 ；2：银联 
    private String productNo;//产品编号
    private String orgCoreAccount;//该条明细所对应的机构集中户账户（核心汇总时用到）
    private String billOrg;//文件来源机构
    /* 与文件解析无关*/
 
	public String getBillOrg() {
		return billOrg;
	}

	public void setBillOrg(String billOrg) {
		this.billOrg = billOrg;
	}

	public String getClearDate() {
		return clearDate;
	}

	public void setClearDate(String clearDate) {
		this.clearDate = clearDate;
	}

	public String getBatchSn() {
		return batchSn;
	}

	public void setBatchSn(String batchSn) {
		this.batchSn = batchSn;
	}

	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}

	public String getTranCode() {
		return tranCode;
	}

	public void setTranCode(String tranCode) {
		this.tranCode = tranCode;
	}

	public String getBitMap() {
		return bitMap;
	}

	public void setBitMap(String bitMap) {
		this.bitMap = bitMap;
	}

	public String getAcctNo() {
		return acctNo;
	}

	public void setAcctNo(String acctNo) {
		this.acctNo = acctNo;
	}

	public String getTranAmount() {
		return tranAmount;
	}

	public void setTranAmount(String tranAmount) {
		this.tranAmount = tranAmount;
	}

	public String getTxCurr() {
		return txCurr;
	}

	public void setTxCurr(String txCurr) {
		this.txCurr = txCurr;
	}

	public String getKey7() {
		return key7;
	}

	public void setKey7(String key7) {
		this.key7 = key7;
	}

	public String getKey11() {
		return key11;
	}

	public void setKey11(String key11) {
		this.key11 = key11;
	}

	public String getKey38() {
		return key38;
	}

	public void setKey38(String key38) {
		this.key38 = key38;
	}

	public String getAuthorizeDate() {
		return authorizeDate;
	}

	public void setAuthorizeDate(String authorizeDate) {
		this.authorizeDate = authorizeDate;
	}

	public String getKey37() {
		return key37;
	}

	public void setKey37(String key37) {
		this.key37 = key37;
	}

	public String getKey32() {
		return key32;
	}

	public void setKey32(String key32) {
		this.key32 = key32;
	}

	public String getKey33() {
		return key33;
	}

	public void setKey33(String key33) {
		this.key33 = key33;
	}

	public String getKey18() {
		return key18;
	}

	public void setKey18(String key18) {
		this.key18 = key18;
	}

	public String getKey41() {
		return key41;
	}

	public void setKey41(String key41) {
		this.key41 = key41;
	}

	public String getKey42() {
		return key42;
	}

	public void setKey42(String key42) {
		this.key42 = key42;
	}

	public String getKey43() {
		return key43;
	}

	public void setKey43(String key43) {
		this.key43 = key43;
	}

	public String getOrginalInf() {
		return orginalInf;
	}

	public void setOrginalInf(String orginalInf) {
		this.orginalInf = orginalInf;
	}

	public String getReasonCode() {
		return reasonCode;
	}

	public void setReasonCode(String reasonCode) {
		this.reasonCode = reasonCode;
	}

	public String getSingleDoubleFlag() {
		return singleDoubleFlag;
	}

	public void setSingleDoubleFlag(String singleDoubleFlag) {
		this.singleDoubleFlag = singleDoubleFlag;
	}

	public String getCupsSerial() {
		return cupsSerial;
	}

	public void setCupsSerial(String cupsSerial) {
		this.cupsSerial = cupsSerial;
	}

	public String getReceiveBranch() {
		return receiveBranch;
	}

	public void setReceiveBranch(String receiveBranch) {
		this.receiveBranch = receiveBranch;
	}

	public String getIssuerBranch() {
		return issuerBranch;
	}

	public void setIssuerBranch(String issuerBranch) {
		this.issuerBranch = issuerBranch;
	}

	public String getCupsNotify() {
		return cupsNotify;
	}

	public void setCupsNotify(String cupsNotify) {
		this.cupsNotify = cupsNotify;
	}

	public String getChannelNo() {
		return channelNo;
	}

	public void setChannelNo(String channelNo) {
		this.channelNo = channelNo;
	}

	public String getFeature() {
		return feature;
	}

	public void setFeature(String feature) {
		this.feature = feature;
	}

	public String getCpusReserved() {
		return cpusReserved;
	}

	public void setCpusReserved(String cpusReserved) {
		this.cpusReserved = cpusReserved;
	}

	public String getPosSvr() {
		return posSvr;
	}

	public void setPosSvr(String posSvr) {
		this.posSvr = posSvr;
	}

	public String getFee() {
		return fee;
	}

	public void setFee(String fee) {
		this.fee = fee;
	}

	public String getCrossBorderFlag() {
		return crossBorderFlag;
	}

	public void setCrossBorderFlag(String crossBorderFlag) {
		this.crossBorderFlag = crossBorderFlag;
	}

	public String getReserved0() {
		return reserved0;
	}

	public void setReserved0(String reserved0) {
		this.reserved0 = reserved0;
	}

	public String getTag9f26() {
		return tag9f26;
	}

	public void setTag9f26(String tag9f26) {
		this.tag9f26 = tag9f26;
	}

	public String getKey22() {
		return key22;
	}

	public void setKey22(String key22) {
		this.key22 = key22;
	}

	public String getKey23() {
		if(key23.length()>2){
			key23=key23.substring(1, 3);
			//System.out.println("----------------------------------------------卡序列号为:"+key23);
		}
		return key23;
	}

	public void setKey23(String key23) {
		this.key23 = key23;
	}

	public String getKey6022() {
		return key6022;
	}

	public void setKey6022(String key6022) {
		this.key6022 = key6022;
	}

	public String getKey6023() {
		return key6023;
	}

	public void setKey6023(String key6023) {
		this.key6023 = key6023;
	}

	public String getTag9f33() {
		return tag9f33;
	}

	public void setTag9f33(String tag9f33) {
		this.tag9f33 = tag9f33;
	}

	public String getTag95() {
		return tag95;
	}

	public void setTag95(String tag95) {
		this.tag95 = tag95;
	}

	public String getTag9f37() {
		return tag9f37;
	}

	public void setTag9f37(String tag9f37) {
		this.tag9f37 = tag9f37;
	}

	public String getTag9f1e() {
		return tag9f1e;
	}

	public void setTag9f1e(String tag9f1e) {
		this.tag9f1e = tag9f1e;
	}

	public String getTag9f10() {
		return tag9f10;
	}

	public void setTag9f10(String tag9f10) {
		this.tag9f10 = tag9f10;
	}

	public String getTag9f36() {
		return tag9f36;
	}

	public void setTag9f36(String tag9f36) {
		this.tag9f36 = tag9f36;
	}

	public String getTag82() {
		return tag82;
	}

	public void setTag82(String tag82) {
		this.tag82 = tag82;
	}

	public String getTag9a() {
		return tag9a;
	}

	public void setTag9a(String tag9a) {
		this.tag9a = tag9a;
	}

	public String getTag9f1a() {
		return tag9f1a;
	}

	public void setTag9f1a(String tag9f1a) {
		this.tag9f1a = tag9f1a;
	}

	public String getTranResponseCode() {
		return tranResponseCode;
	}

	public void setTranResponseCode(String tranResponseCode) {
		this.tranResponseCode = tranResponseCode;
	}

	public String getTag9c() {
		return tag9c;
	}

	public void setTag9c(String tag9c) {
		this.tag9c = tag9c;
	}

	public String getTag9f02() {
		return tag9f02;
	}

	public void setTag9f02(String tag9f02) {
		this.tag9f02 = tag9f02;
	}

	public String getTag5f2a() {
		return tag5f2a;
	}

	public void setTag5f2a(String tag5f2a) {
		this.tag5f2a = tag5f2a;
	}

	public String getCheckTcResults() {
		return checkTcResults;
	}

	public void setCheckTcResults(String checkTcResults) {
		this.checkTcResults = checkTcResults;
	}

	public String getKey14() {
		return key14;
	}

	public void setKey14(String key14) {
		this.key14 = key14;
	}

	public String getTag9f27() {
		return tag9f27;
	}

	public void setTag9f27(String tag9f27) {
		this.tag9f27 = tag9f27;
	}

	public String getTag9f03() {
		return tag9f03;
	}

	public void setTag9f03(String tag9f03) {
		this.tag9f03 = tag9f03;
	}

	public String getTag9f34() {
		return tag9f34;
	}

	public void setTag9f34(String tag9f34) {
		this.tag9f34 = tag9f34;
	}

	public String getTag9f35() {
		return tag9f35;
	}

	public void setTag9f35(String tag9f35) {
		this.tag9f35 = tag9f35;
	}

	public String getTag84() {
		return tag84;
	}

	public void setTag84(String tag84) {
		this.tag84 = tag84;
	}

	public String getTag8f09() {
		return tag8f09;
	}

	public void setTag8f09(String tag8f09) {
		this.tag8f09 = tag8f09;
	}

	public String getTag9f41() {
		return tag9f41;
	}

	public void setTag9f41(String tag9f41) {
		this.tag9f41 = tag9f41;
	}

	public String getReserved2() {
		return reserved2;
	}

	public void setReserved2(String reserved2) {
		this.reserved2 = reserved2;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getLoseFlag() {
		return loseFlag;
	}

	public void setLoseFlag(String loseFlag) {
		this.loseFlag = loseFlag;
	}

	public String getReturnNumber() {
		return returnNumber;
	}

	public void setReturnNumber(String returnNumber) {
		this.returnNumber = returnNumber;
	}

	public String getSumReturnAmount() {
		return sumReturnAmount;
	}

	public void setSumReturnAmount(String sumReturnAmount) {
		this.sumReturnAmount = sumReturnAmount;
	}

	public String getAcctingDate() {
		return acctingDate;
	}

	public void setAcctingDate(String acctingDate) {
		this.acctingDate = acctingDate;
	}

	public String getPlatformSerial() {
		return platformSerial;
	}

	public void setPlatformSerial(String platformSerial) {
		this.platformSerial = platformSerial;
	}

	public String getAcctingBranch() {
		return acctingBranch;
	}

	public void setAcctingBranch(String acctingBranch) {
		this.acctingBranch = acctingBranch;
	}

	public String getBatchNo() {
		return batchNo;
	}

	public String getFileSrc() {
		return fileSrc;
	}

	public void setFileSrc(String fileSrc) {
		this.fileSrc = fileSrc;
	}

	public String getOrigSysNo() {
		return origSysNo;
	}

	public void setOrigSysNo(String origSysNo) {
		this.origSysNo = origSysNo;
	}

	public String getOrigTranTime() {
		return origTranTime;
	}

	public void setOrigTranTime(String origTranTime) {
		this.origTranTime = origTranTime;
	}

	public String getProductNo() {
		return productNo;
	}

	public void setProductNo(String productNo) {
		this.productNo = productNo;
	}

	public String getOrgCoreAccount() {
		return orgCoreAccount;
	}

	public void setOrgCoreAccount(String orgCoreAccount) {
		this.orgCoreAccount = orgCoreAccount;
	}

	public String constructFile(){
		tranAmount = Utils.template(tranAmount, 12);
		StringBuffer result = new StringBuffer();
		result.append(Utils.filter(tranCode,3))//交易代码
	    .append(Utils.filter(bitMap,4))//段位图
	    .append(Utils.filter(acctNo,19))//主账号
	    .append(Utils.figureFilter(tranAmount,12))//交易金额
	    .append(Utils.filter(txCurr,3))//交易货币代码
	    .append(Utils.filter(key7,10))//交易传输时间
	    .append(Utils.filter(key11,6))//系统跟踪号
	    .append(Utils.filter(key38,6))//授权应答标识码
	    .append(Utils.filter(authorizeDate,4))//授权日期
	    .append(Utils.filter(key37,12))//检索参考号
	    .append(Utils.filter(key32,11))//代理机构标识码
	    .append(Utils.filter(key33,11))//发送机构标识码
	    .append(Utils.filter(key18,4))//商户类型
	    .append(Utils.filter(key41,8))//受卡机终端标识码
	    .append(Utils.filter(key42,15))//受卡方标识码
	    .append(Utils.filter(key43,40))//受卡方名称地址
	    .append(Utils.filter(orginalInf,23))//原始交易信息
	    .append(Utils.filter(reasonCode,4))//报文原因代码
	    .append(Utils.filter(singleDoubleFlag,1))//单双信息标志
	    .append(Utils.filter(cupsSerial,9))//CUPS流水号
	    .append(Utils.filter(receiveBranch,11))//接收机构代码
	    .append(Utils.filter(issuerBranch,11))//发卡机构代码
	    .append(Utils.filter(cupsNotify,1))//CUPS通知标志
	    .append(Utils.filter(channelNo,2))//交易发起渠道
	    .append(Utils.filter(feature,1))//交易特征标识
	    .append(Utils.filter(cpusReserved,8))//CUPS保留使用
	    .append(Utils.filter(posSvr,2))//POS服务点条件代码
	    .append(Utils.filter(fee,12))//本方手续费
	    .append(Utils.filter(crossBorderFlag,1))//交易跨境标志
	    .append(Utils.filter(reserved0,15))//保留使用
	    .append(Utils.filter(tag9f26,16))//应用密文
	    .append(Utils.filter(key22,3))//服务点输入方式码
	    .append(Utils.filter(key23,3))//卡片序列号
	    .append(Utils.filter(key6022,1))//终端读取能力
	    .append(Utils.filter(key6023,1))//IC卡条件代码
	    .append(Utils.filter(tag9f33,6))//终端性能
	    .append(Utils.filter(tag95,10))//终端验证结果
	    .append(Utils.filter(tag9f37,8))//不可预知数
	    .append(Utils.filter(tag9f1e,8))//接口设备序列号(9F一E)
	    .append(Utils.filter(tag9f10,64))//发卡行应用数据(cvr)
	    .append(Utils.filter(tag9f36,4))//应用交易记数器
	    .append(Utils.filter(tag82,4))//应用交互特征
	    .append(Utils.filter(tag9a,6))//交易日期
	    .append(Utils.filter(tag9f1a,3))//终端国家代码
	    .append(Utils.filter(tranResponseCode,2))//交易响应码
	    .append(Utils.filter(tag9c,2))//交易类型
	    .append(Utils.filter(tag9f02,12))//授权金额
	    .append(Utils.filter(tag5f2a,3))//交易币种代码
	    .append(Utils.filter(checkTcResults,1))//应用密文校验结果
	    .append(Utils.filter(key14,4))//卡有效期
	    .append(Utils.filter(tag9f27,2))//密文信息数据
	    .append(Utils.filter(tag9f03,12))//其它金额
	    .append(Utils.filter(tag9f34,6))//持卡人验证方法结果
	    .append(Utils.filter(tag9f35,2))//终端类型
	    .append(Utils.filter(tag84,32))//专用文件名称
	    .append(Utils.filter(tag8f09,4))//应用版本号
	    .append(Utils.filter(tag9f41,8))//交易序列计数器
	    .append(Utils.filter(reserved2,30));//保留使用
		return result.toString();
	}
	private String status(String ocdStatus){
		String tmp = ocdStatus.substring(ocdStatus.length() - 1);
		return "1".equals(tmp)?"0":"1";
	}
	
	private String getGBK(String str,int len){
		try{
			StringBuffer sb = new StringBuffer();
			int l = str.getBytes("GBK").length;
			if(l == len){
				return str;
			}
			sb.append(str);
			for(int i=0;i<len-l;i++){
				sb.append(" ");
			}
			return sb.toString();
		}catch (Exception e) {
			// TODO: handle exception
			return str;
		}
	}

	@Override
	public String toString() {
		return StringUtils.rightPad(this.acctNo, 19, " ") + this.tranCode + StringUtils.leftPad(this.tranAmount.replace(".", ""), 15, "0") + 
		status(this.status.trim()) + StringUtils.rightPad(this.tag9a,8," ") + ("300".equals(this.tranCode)?"          ":this.orginalInf.substring(3, 13)) + 
		"      " + StringUtils.rightPad(null==this.key11?"":this.key11,6," ") + 
		StringUtils.rightPad(null==this.key42?"":this.key42,15," ") + getGBK(null==this.key43?"":this.key43,40) + 
		("300".equals(this.tranCode)?StringUtils.leftPad(null==this.fee?"":this.fee.trim(),12,"0"):StringUtils.leftPad("",12," ")) +
		(!"300".equals(this.tranCode)?StringUtils.leftPad(null==this.fee?"":this.fee.trim(),12,"0"):StringUtils.leftPad("",12," ")) + this.status;
	}	
}

