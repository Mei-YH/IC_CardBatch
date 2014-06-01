package com.icsys.batch.task.offline.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.nio.ByteBuffer;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

import com.icsys.batch.api.JobContext;
import com.icsys.batch.api.JobException;
import com.icsys.batch.offline.bean.ClearDetailBean;
import com.icsys.batch.offline.bean.ClearTaskBean;
import com.icsys.batch.offline.dao.IcOfflineChkDetDao;
import com.icsys.batch.offline.dao.IcOfflineChkTaskDao;
import com.icsys.batch.util.Constants;
import com.icsys.platform.dao.TXHelper;


/**
 * 文件处理类，将文件解析为数据插入数据库表中
 * 
 * @author Administrator
 * 
 */
public class FileServiceImpl{
	private static final Logger LOG = Logger.getLogger(FileServiceImpl.class);
	IcOfflineChkTaskDao taskDao = new IcOfflineChkTaskDao();
	IcOfflineChkDetDao dao = new IcOfflineChkDetDao();
//	BranchManager branchDao = ServiceLocator.getSerivce(BranchManager.class);

	/**
	 * 根据文件名，生成命令文件（ctl文件）
	 * 
	 * @param dateFileName
	 *            脱机文件名
	 */
	/*public boolean createOfflineFile(String dataFileName, String sqlldrFileName,
			String clearDate, String batchNo, String fileSrc) {
		String billOrg = "";// 账单发送机构（各银联前置代码）
		if (dataFileName.contains(Constants.Unionpay)) {
			billOrg = getOrgCode(dataFileName);
			if (billOrg == null) {
				saveTask(dataFileName, clearDate, batchNo,
						Constants.STATUS_FAILURE);// 账单发送机构获取失败直接返回
				return false;
			}
		}
		StringBuffer content = new StringBuffer();
		content
				.append("LOAD DATA   CHARACTERSET ZHS16GBK  INFILE \n")
				.append(
						"'"
								+ dataFileName
								+ "'  \"fix 526\" \n Append  \n INTO TABLE IC_OFFLINE_CHK_DET ( \n")
				.append("CLEAR_DATE CONSTANT '" + clearDate + "',\n")
				.append("BATCH_NO CONSTANT '" + batchNo + "',\n")
				.append("BATCH_SN sequence(1,1),\n")
				.append("FILE_SRC CONSTANT   '" + fileSrc + "', \n")
				.append("BILL_ORG CONSTANT   '" + billOrg + "', \n")
				.append("STATUS CONSTANT   '0000', \n")
				.append("TRAN_CODE POSITION(01:03)   CHAR, \n")
				.append("BIT_MAP POSITION(04:07)   CHAR, \n")
				.append("ACCT_NO POSITION(8: 26) CHAR, \n")
				.append(
						"TRAN_AMOUNT POSITION(27: 38) \":TRAN_AMOUNT*0.01\", \n")
				.append("TXCURR POSITION(39: 41) DECIMAL EXTERNAL, \n").append(
						"key7 POSITION(42: 51) CHAR, \n").append(
						"key11 POSITION(52: 57) CHAR, \n").append(
						"key_38 POSITION(58: 63)CHAR, \n").append(
						"AUTHORIZE_DATE POSITION(64: 67)CHAR, \n").append(
						"key_37 POSITION(68: 79)CHAR, \n").append(
						"key32 POSITION(80: 90) CHAR, \n").append(
						"key33 POSITION(91: 101) CHAR, \n").append(
						"key18 POSITION(102: 105) CHAR, \n").append(
						"key41 POSITION(106: 113) CHAR, \n").append(
						"key42 POSITION(114: 128) CHAR, \n").append(
						"key43 POSITION(129: 168) CHAR, \n").append(
						"ORGINAL_INF POSITION(169: 191) CHAR, \n").append(
						"ORIG_TRAN_TIME POSITION(172: 181) CHAR, \n").append(
						"ORIGSYSNO POSITION(182: 187) CHAR, \n").append(
						"REASON_CODE POSITION(192: 195) CHAR, \n").append(
						"SINGLE_DOUBLE_FLAG POSITION(196: 196) CHAR, \n")
				.append("CUPS_SERIAL POSITION(197: 205) CHAR, \n").append(
						"RECEIVE_BRANCH POSITION(206: 216)CHAR, \n").append(
						"ISSUER_BRANCH POSITION(217: 227)CHAR, \n").append(
						"CUPS_NOTIFY POSITION(228: 228)CHAR, \n").append(
						"CHANNEL_NO POSITION(229: 230) CHAR, \n").append(
						"FEATURE POSITION(231: 231) CHAR, \n").append(
						"CPUS_RESERVED POSITION(232: 239) CHAR, \n").append(
						"POSSVR POSITION(240: 241) CHAR, \n").append(
						"FEE POSITION(242: 253) CHAR, \n").append(
						"CROSS_BORDER_FLAG POSITION(254: 254) CHAR, \n")
				.append("RESERVED0 POSITION(255: 269) CHAR, \n").append(
						"tag9f26 POSITION(270: 285) CHAR, \n").append(
						"key22 POSITION(286: 288) CHAR, \n").append(
						"key23 POSITION(289: 291) CHAR, \n").append(
						"key6022 POSITION(292: 292) CHAR, \n").append(
						"key6023 POSITION(293: 293) CHAR, \n").append(
						"tag9f33 POSITION(294: 299) CHAR, \n").append(
						"tag95 POSITION(300: 309) CHAR, \n").append(
						"tag9f37 POSITION(310: 317) CHAR, \n").append(
						"tag9f1e POSITION(318: 325) CHAR, \n").append(
						"tag9f10 POSITION(326: 389) CHAR, \n").append(
						"tag9f36 POSITION(390: 393) CHAR, \n").append(
						"tag82 POSITION(394: 397) CHAR, \n").append(
						"tag9a POSITION(398: 403) CHAR, \n").append(
						"tag9f1a POSITION(404: 406) CHAR, \n").append(
						"TRAN_RESPONSE_CODE POSITION(407: 408) CHAR, \n")
				.append("tag9c POSITION(409: 410) CHAR, \n").append(
						"tag9f02 POSITION(411: 422) CHAR, \n").append(
						"tag5f2a POSITION(423: 425) CHAR, \n").append(
						"CHECK_TC_RESULTS POSITION(426: 426) CHAR, \n").append(
						"key14 POSITION(427: 430) CHAR, \n").append(
						"tag9f27 POSITION(431: 432) CHAR, \n").append(
						"tag9f03 POSITION(433: 444) CHAR, \n").append(
						"tag9f34 POSITION(445: 450) CHAR, \n").append(
						"tag9f35 POSITION(451: 452) CHAR, \n").append(
						"tag84 POSITION(453: 484) CHAR, \n").append(
						"tag8f09 POSITION(485: 488) CHAR, \n").append(
						"tag9f41 POSITION(489: 496) CHAR, \n").append(
						"RESERVED2 POSITION(497: 526) CHAR)");// 这里保留使用做了修改,但由于字符串的长度没变,所以不再做任何修改
		String contentStr = content.toString();
		try {
			File file = new File(sqlldrFileName);
			OutputStream os = new FileOutputStream(file);
			os.write(contentStr.getBytes("UTF-8"));
			os.flush();
			os.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/*public void createOnlineFile(String dataFileName, String sqlldrFileName,
			String clearDate, String batchNo, String fileSrc) {
		String billOrg = "";// 账单发送机构（各银联前置代码）
		if (dataFileName.contains(Constants.LocalBank)) {
			fileSrc = Constants.CLEARING_SELF;
			;// 文件来自本行
		} else if (dataFileName.contains(Constants.Unionpay)) {
			fileSrc = Constants.CLEARING_UNIONPAY;
			;// 文件来自银联
			billOrg = getOrgCode(dataFileName);
			if (billOrg == null) {
				saveTask(dataFileName, clearDate, batchNo,
						Constants.STATUS_FAILURE);// 账单发送机构获取失败直接返回
				return;
			}
		}
		StringBuffer content = new StringBuffer();
		content.append("LOAD DATA   CHARACTERSET ZHS16GBK  INFILE \n").append(
				"'" + dataFileName
						+ "'  \n Append  \n INTO TABLE IC_ONLINE_CHK_DET (\n")
				.append("CLEAR_DATE CONSTANT '" + clearDate + "',\n").append(
						"BATCH_NO CONSTANT '" + batchNo + "',\n").append(
						"BATCH_SN sequence(1,1),\n").append(
						"FILE_SRC CONSTANT   '" + fileSrc + "', \n").append(
						"BILL_ORG CONSTANT   '" + billOrg + "', \n").append(
						"STATUS CONSTANT   '0000', \n").append(
						"KEY32 POSITION(01: 11) CHAR, \n").append(
						"KEY33 POSITION(13: 23) CHAR, \n").append(
						"KEY11 POSITION(25: 30) CHAR, \n").append(
						"KEY7 POSITION(32: 41) CHAR, \n").append(
						"ACCT_NO POSITION(43: 61) CHAR, \n").append(
						"TRAN_AMOUNT POSITION(63: 74) CHAR, \n").append(
						"CARD_HOLDER_FEE POSITION(89: 100) CHAR, \n").append(
						"DOC_TYPE POSITION(102: 105) CHAR, \n").append(
						"TRAN_CODE POSITION(107: 112) CHAR, \n").append(
						"KEY18 POSITION(114: 117) CHAR, \n").append(
						"KEY41 POSITION(119: 126) CHAR, \n").append(
						"KEY42 POSITION(128: 142) CHAR, \n").append(
						"KEY_37 POSITION(144: 155) CHAR, \n").append(
						"KEY25 POSITION(157: 158) CHAR, \n").append(
						"KEY_38 POSITION(160: 165) CHAR, \n").append(
						"RECEIVE_BRANCH POSITION(167: 177) CHAR, \n").append(
						"ORIGSYSNO POSITION(179: 184) CHAR, \n").append(
						"TRAN_RESPONSE_CODE POSITION(186: 187) CHAR, \n")
				.append("KEY22 POSITION(189: 191) CHAR, \n").append(
						"RECE_FEE POSITION(193: 204) CHAR, \n").append(
						"PAY_FEE POSITION(206: 217) CHAR, \n").append(
						"TRAN_FEE POSITION(219: 230) CHAR, \n").append(
						"SINGLE_DOUBLE_FLAG POSITION(232: 232) CHAR, \n")
				.append("KEY23 POSITION(234: 236) CHAR, \n").append(
						"KEY6022 POSITION(238: 238) CHAR, \n").append(
						"KEY6023 POSITION(240: 240) CHAR, \n").append(
						"ORIG_TRAN_TIME POSITION(242: 251) CHAR, \n").append(
						"ISSUER_BRANCH POSITION(253: 263) CHAR, \n").append(
						"CROSS_BORDER_FLAG POSITION(265: 265) CHAR, \n")
				.append("CHANNEL_NO POSITION(267: 268) CHAR, \n").append(
						"RESERVED POSITION(270:296) CHAR)");// 升级后
		// .append("RESERVED POSITION(270:299) CHAR)" );
		String contentStr = content.toString();
		try {
			File file = new File(sqlldrFileName);
			OutputStream os = new FileOutputStream(file);
			os.write(contentStr.getBytes("UTF-8"));
			os.flush();
			os.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 执行命令,将文件数据明细入库
	
	public boolean excuteCMD(String dataFileName, String sqlldrFileName,
			String clearDate, String batchNo, String logPath) {
		saveTask(dataFileName, clearDate, batchNo, Constants.STATUS_EXECUTING);// 插入脱机消费任务列表
		File logDir = new File(logPath);
		if (!logDir.exists()) {
			logDir.mkdirs();// 如果指定的日志目录不存在，则新创建一个
		}

		String strcmd = "sqlldr userid=" + userid + " control="
				+ sqlldrFileName + " log=" + logPath
				+ dataFileName.substring(dataFileName.lastIndexOf("/"))
				+ ".log";
		LOG.info("================sqlLoadCMD为[" + strcmd
				+ "]==================");
		Runtime r = Runtime.getRuntime();
		try {
			Process process = r.exec(strcmd);// 执行命令，将数据导入到数据库
			InputStream standardOutput = process.getInputStream();
			int c;
			String strOut = "";
			while ((c = standardOutput.read()) != -1) {
				strOut += (char) c;
			}
			System.out.print(strOut);
			standardOutput.close();
//			OfflineFileServiceImpl fileService = new OfflineFileServiceImpl();
			boolean failed = isFailed(dataFileName);
			if (failed) {
				// 如果数据解析有误，则把已经插入的部分数据删除
				taskDao.changeTaskStatus(batchNo, clearDate,
						Constants.STATUS_FAILURE);// 更改相应的任务状态为执行失败
				return false;
			} else {
				taskDao.changeTaskStatus(batchNo, clearDate,
						Constants.STATUS_SUCCESS);// 更改相应的任务状态为执行成功
				return true;
			}
		} catch (Exception e) {
			saveTask(dataFileName, clearDate, batchNo, Constants.STATUS_FAILURE);// 插入脱机消费任务列表失败
			e.printStackTrace();
			return false;
		}
	} */

	/**
	 * 保存一条批处理任务
	 * 
	 * @param dataFileName
	 *            脱机消费文件路径
	 * @throws Exception 
	 */
	public void saveTask(String dataFileName, String clearDate, String batchNo,
			String taskStatus,Integer batchSn,String cityCode) {
/*		ClearTaskBean taskBean = new ClearTaskBean();
		taskBean.setClearDate(clearDate);// 设置清算日期
		taskBean.setBatchNo(Integer.parseInt(batchNo));// 设置批次号
		taskBean.setTranDate(Utils.getClearDate());// 设置交易日期，getWorkbenchDate()
		taskBean.setBranchPro("1");// 提交省市代码
		taskBean.setSubmitBranch("branch");// 提交机构号
		taskBean.setSubmitTeller("1");// 提交柜员号
		taskBean.setOriginalFile(dataFileName);// 原始文件名
		taskBean.setOriginalFileCount(0);// 原始文件总笔数
		taskBean.setOriginalFileSumAmount(new BigDecimal(0.00));// 原始文件总金额
		taskBean.setClearFile(dataFileName);// 清算文件名
		taskBean.setClearFileCount(0);// 清算文件总笔数
		taskBean.setClearFileSumAmount(new BigDecimal(0.00));// 清算文件总金额
		taskBean.setFailCount(0);// 失败笔数
		taskBean.setFailAmount(new BigDecimal(0.00));// 失败金额
		// acc_type 1-本行，2-银联
//		if (dataFileName.contains(Constants.LocalBank)) {
//			taskBean.setClearType(Constants.CLEARING_SELF);// 清算类型//文件来自本行
//		} else if (dataFileName.contains(Constants.Unionpay)) {
			taskBean.setClearType(Constants.CLEARING_UNIONPAY);// 文件来自银联
//		}
		// stage 0-入库，1-清算周期检查，2-交易计数器，3-TC验证，4-单笔平台记账，5-汇总机构，6-机构核心记账，7-生成结果文件
		taskBean.setTreatmentStage(Constants.STAGE_PUTIN);// 阶段
		// status 0-未执行，1-正在执行，2-失败，3-执行成功
		taskBean.setStatus(taskStatus);// 状态
		IcOfflineChkTaskDao dao = new IcOfflineChkTaskDao();
		dao.updateOrInsertClearTask(taskBean);
		*/

		ClearTaskBean taskBean = new ClearTaskBean();
		taskBean.setClearDate(clearDate);// 设置清算日期
		taskBean.setBatchNo(Integer.parseInt(batchNo));// 设置批次号
		taskBean.setTranDate(clearDate);// 设置交易日期，getWorkbenchDate()
		taskBean.setBranchPro(cityCode);// 提交省市代码
		taskBean.setSubmitBranch("000000");// 提交机构号
		taskBean.setSubmitTeller("000000");// 提交柜员号
		taskBean.setOriginalFile(dataFileName);// 原始文件名
		taskBean.setOriginalFileCount(batchSn);// 原始文件总笔数
		taskBean.setOriginalFileSumAmount(new BigDecimal(0.00));// 原始文件总金额
		taskBean.setClearFile(dataFileName);// 清算文件名
		taskBean.setClearFileCount(0);// 清算文件总笔数
		taskBean.setClearFileSumAmount(new BigDecimal(0.00));// 清算文件总金额
		taskBean.setFailCount(0);// 失败笔数
		taskBean.setFailAmount(new BigDecimal(0.00));// 失败金额
		// acc_type 1-本行，2-银联
//		if (dataFileName.contains(Constants.LocalBank)) {
//			taskBean.setClearType(Constants.CLEARING_SELF);// 清算类型//文件来自本行
//		} else if (dataFileName.contains(Constants.Unionpay)) {
			taskBean.setClearType(Constants.CLEARING_UNIONPAY);// 文件来自银联
//		}
		// stage 0-入库，1-清算周期检查，2-交易计数器，3-TC验证，4-单笔平台记账，5-汇总机构，6-机构核心记账，7-生成结果文件
		taskBean.setTreatmentStage(Constants.STAGE_PUTIN);// 阶段
		// status 0-未执行，1-正在执行，2-失败，3-执行成功
		taskBean.setStatus(taskStatus);// 状态
		IcOfflineChkTaskDao dao = new IcOfflineChkTaskDao();
		dao.updateOrInsertClearTask(taskBean);
	}

	/**
	 * 根据文件名获得相应的机构编码
	 * 
	 * @param dataFile
	 * @param filePrefix
	 * @return
	 */
	/*public String getOrgCode(String dataFileName) {
		System.out.println("======dataFileName======" + dataFileName);
		if (!dataFileName.contains(Constants.Unionpay)) {
			return null;// 如果文件名中不带有银联标识，则直接返回，本行的文件不分机构
		}
		String org = "";
		File dataFile = new File(dataFileName);
		if (dataFile.exists()) {// 获取该文件的上级目录名即为该文件所属机构
			dataFileName = dataFile.getParent();
			File parentFile = new File(dataFileName);
			if (parentFile.exists()) {
				IcOfflineBranchInfo branchInfo;
				try {
					branchInfo = branchDao
							.findOfflineByLocalBranchCode(parentFile.getName());
					org = branchInfo.getBranchCode();
				} catch (AppException e) {
					LOG.error(
							"==============获取银联机构所对应的机构信息,出现错误===============",
							e);
					e.printStackTrace();
				}

			}
		}
		return org;
	}

	/**
	 * 判断文件是否入库失败（如果失败文件存在，则失败）
	 * 
	 * @param dataFileName
	 * @return
	 */
	public boolean isFailed(String dataFileName) {
		String badFileName = dataFileName + ".bad";
		File badFile = new File(badFileName);
		return badFile.exists();
	}
//	public String getUserid() {
//		return userid;
//	}
//
//	public void setUserid(String userid) {
//		this.userid = userid;
//	}

	/**
	 * 实现解析脱机消费的文件入库的方法
	 * 
	 * @param batchNo
	 *            批次号
	 * @param clearDate
	 *            清算日期
	 * @param fileName
	 *            文件路径(绝对路径)
	 * @param fileSrc
	 *            文件来源(1-本行 2-银联)
	 * @param billOrg
	 *            (如果是本行的脱机消费文件,这个字段为空,如果是银联的则取得的是银联的机构号)
	 * @throws Exception
	 */
	public boolean resoluteFile(String batchNo, String clearDate, String fileName,
			String fileSrc, String billOrg) throws Exception {

		BufferedReader br = null;
		TXHelper.beginTX();
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(fileName), "GBK"));
			String context = br.readLine();
			byte[] buf = context.getBytes("GBK");
			ByteBuffer buffer = ByteBuffer.wrap(buf);
			if (buffer.capacity() <= 95) {
				LOG.info("脱机消费文件无效");
				return false;
			}
			buffer.position(46);
			int batchSN = 1;// 批次内序号
			
			while (true) {
				ClearDetailBean bean = parse(buffer, "GBK");
				bean.setBatchNo(batchNo);
				bean.setClearDate(clearDate);
				bean.setFileSrc(fileSrc);
				bean.setBatchSn(batchSN + "");
				bean.setBillOrg(billOrg);
				bean.setStatus("0000");// 初始化该明细为未处理状态
				dao.insertClearDetail(bean);
				batchSN++;
				// 入库
				if (buffer.capacity() - buffer.position() == 49) {
					break;
				}
			}
			TXHelper.commit();			
		}catch (Exception e) {
			TXHelper.rollback();
			LOG.info("文件解析入库出现异常");
			LOG.error(e.getMessage(), e);
			throw e;
		}finally{
			if(br != null)
				br.close();
			TXHelper.close();
		}
		return true;

	}

	/**
	 * 解析脱机消费文件的主要方法
	 * 
	 * @param input
	 * @param charset
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	private ClearDetailBean parse(ByteBuffer input, String charset)
			throws UnsupportedEncodingException {

		ClearDetailBean bean = new ClearDetailBean();
//		DecimalFormat format = new DecimalFormat("0.00");// 格式化金额保留两位小数
//		String origInf = "";// 原始交易信息
		// 交易码
		byte[] buf = new byte[3];
		input.get(buf);
		bean.setTranCode(new String(buf, charset));

		// 段位图
		buf = new byte[4];
		input.get(buf);
		bean.setBitMap(new String(buf, charset));

		// 主账号
		buf = new byte[19];
		input.get(buf);
		bean.setAcctNo(new String(buf, charset));

		// 交易金额
		buf = new byte[12];
		input.get(buf);
		String tranAmt = new String(buf, charset);
//		tranAmt = (format.format(Long.parseLong(tranAmt) * 0.01)) + "";
		tranAmt = new BigDecimal(tranAmt).movePointLeft(2) + "";
		bean.setTranAmount(tranAmt);

		// 交易货币代码
		buf = new byte[3];
		input.get(buf);
		bean.setTxCurr(new String(buf, charset));

		// 交易传输时间
		buf = new byte[10];
		input.get(buf);
		bean.setKey7(new String(buf, charset));

		// 系统跟踪号
		buf = new byte[6];
		input.get(buf);
		bean.setKey11(new String(buf, charset));

		// 授权应答标识码
		buf = new byte[6];
		input.get(buf);
		bean.setKey38(new String(buf, charset));

		// 授权日期
		buf = new byte[4];
		input.get(buf);
		bean.setAuthorizeDate(new String(buf, charset));

		// 检索参考号
		buf = new byte[12];
		input.get(buf);
		bean.setKey37(new String(buf, charset));

		// 代理机构标识码
		buf = new byte[11];
		input.get(buf);
		bean.setKey32(new String(buf, charset));

		// 发送机构标识码
		buf = new byte[11];
		input.get(buf);
		bean.setKey33(new String(buf, charset));

		// 商户类型
		buf = new byte[4];
		input.get(buf);
		bean.setKey18(new String(buf, charset));

		// 受卡机终端标识码
		buf = new byte[8];
		input.get(buf);
		bean.setKey41(new String(buf, charset));

		// 受卡方标识码
		buf = new byte[15];
		input.get(buf);
		bean.setKey42(new String(buf, charset));

		// 受卡方名称地址
		buf = new byte[40];
		input.get(buf);
		bean.setKey43(new String(buf, charset));

		// 原始交易信息
		buf = new byte[23];
		input.get(buf);
		String origInf = new String(buf, charset);
		bean.setOrginalInf(origInf);

		// 原始交易时间
		byte buf1[] = new byte[10];
		System.arraycopy(buf, 3, buf1, 0, 10);
		bean.setOrigTranTime(new String(buf1, charset));

		// 原系统跟踪号
		byte buf2[] = new byte[6];
		System.arraycopy(buf, 13, buf2, 0, 6);
		bean.setOrigSysNo(new String(buf2, charset));

		// 报文原因代码
		buf = new byte[4];
		input.get(buf);
		bean.setReasonCode(new String(buf, charset));

		// 单双信息标志
		buf = new byte[1];
		input.get(buf);
		bean.setSingleDoubleFlag(new String(buf, charset));

		// CUPS流水号
		buf = new byte[9];
		input.get(buf);
		bean.setCupsSerial(new String(buf, charset));

		// 接收机构代码
		buf = new byte[11];
		input.get(buf);
		bean.setReceiveBranch(new String(buf, charset));

		// 发卡机构代码
		buf = new byte[11];
		input.get(buf);
		bean.setIssuerBranch(new String(buf, charset));

		// CUPS通知标志
		buf = new byte[1];
		input.get(buf);
		bean.setCupsNotify(new String(buf, charset));

		// 交易发起渠道
		buf = new byte[2];
		input.get(buf);
		bean.setChannelNo(new String(buf, charset));

		// 交易特征标识
		buf = new byte[1];
		input.get(buf);
		bean.setFeature(new String(buf, charset));

		// CUPS保留使用
		buf = new byte[8];// 8
		input.get(buf);
		bean.setCpusReserved(new String(buf, charset));

		// POS服务点条件代码
		buf = new byte[2];
		input.get(buf);
		bean.setPosSvr(new String(buf, charset));

		// 本方手续费
		buf = new byte[12];
		input.get(buf);
		bean.setFee(new String(buf, charset));

		// 交易跨境标志
		buf = new byte[1];
		input.get(buf);
		bean.setCrossBorderFlag(new String(buf, charset));

		// 保留使用
		buf = new byte[15];
		input.get(buf);
		bean.setReserved0(new String(buf, charset));

		// 应用密文（TC）
		buf = new byte[16];
		input.get(buf);
		bean.setTag9f26(new String(buf, charset));

		// 服务点输入方式码
		buf = new byte[3];
		input.get(buf);
		bean.setKey22(new String(buf, charset));

		// 卡片序列号
		buf = new byte[3];
		input.get(buf);
		bean.setKey23(new String(buf, charset));

		// 终端读取能力
		buf = new byte[1];
		input.get(buf);
		bean.setKey6022(new String(buf, charset));

		// IC卡条件代码
		buf = new byte[1];
		input.get(buf);
		bean.setKey6023(new String(buf, charset));

		// 终端性能
		buf = new byte[6];
		input.get(buf);
		bean.setTag9f33(new String(buf, charset));

		// 终端验证结果
		buf = new byte[10];
		input.get(buf);
		bean.setTag95(new String(buf, charset));

		// 不可预知数
		buf = new byte[8];
		input.get(buf);
		bean.setTag9f37(new String(buf, charset));

		// 接口设备序列号
		buf = new byte[8];
		input.get(buf);
		bean.setTag9f1e(new String(buf, charset));

		// 发卡行应用数据(cvr)
		buf = new byte[64];
		input.get(buf);
		bean.setTag9f10(new String(buf, charset));

		// 应用交易记数器
		buf = new byte[4];
		input.get(buf);
		bean.setTag9f36(new String(buf, charset));

		// 应用交互特征
		buf = new byte[4];
		input.get(buf);
		bean.setTag82(new String(buf, charset));

		// 交易日期
		buf = new byte[6];
		input.get(buf);
		bean.setTag9a(new String(buf, charset));

		// 终端国家代码
		buf = new byte[3];
		input.get(buf);
		bean.setTag9f1a(new String(buf, charset));

		// 交易响应码
		buf = new byte[2];
		input.get(buf);
		bean.setTranResponseCode(new String(buf, charset));

		// 交易类型
		buf = new byte[2];
		input.get(buf);
		bean.setTag9c(new String(buf, charset));

		// 授权金额
		buf = new byte[12];
		input.get(buf);
		bean.setTag9f02(new String(buf, charset));

		// 交易币种代码
		buf = new byte[3];
		input.get(buf);
		bean.setTag5f2a(new String(buf, charset));

		// 应用密文校验结果
		buf = new byte[1];
		input.get(buf);
		bean.setCheckTcResults(new String(buf, charset));

		// 卡有效期
		buf = new byte[4];
		input.get(buf);
		bean.setKey14(new String(buf, charset));

		// 密文信息数据
		buf = new byte[2];
		input.get(buf);
		bean.setTag9f27(new String(buf, charset));

		// 其它金额
		buf = new byte[12];
		input.get(buf);
		bean.setTag9f03(new String(buf, charset));

		// 持卡人验证方法结果
		buf = new byte[6];
		input.get(buf);
		bean.setTag9f34(new String(buf, charset));

		// 总端类型
		buf = new byte[2];
		input.get(buf);
		bean.setTag9f35(new String(buf, charset));

		// 专用文件名称
		buf = new byte[32];// 32
		input.get(buf);
		bean.setTag84(new String(buf, charset));

		// 应用版本号
		buf = new byte[4];
		input.get(buf);
		bean.setTag8f09(new String(buf, charset));

		// 交易序列计数器
		buf = new byte[8];
		input.get(buf);
		bean.setTag9f41(new String(buf, charset));

		// 保留使用
		buf = new byte[30];
		input.get(buf);
		bean.setReserved2(new String(buf, charset));
		return bean;

	}

	/**
	 * 脱机清算完成后,给本行或银联生成的成功或失败结果文件
	 * 
	 * @param clearDate
	 *            清算日期
	 * @param batchNo
	 *            批次号
	 * @param filePath
	 *            要生成文件的绝对路径(不包含文件名)
	 */
	public void makeResultFile(String clearDate, String billOrg, String filePath,String cityCode,
			Integer sn,List<String> res) throws Exception{
		
//		ReturnFileDao returnFileDao = new ReturnFileDao();
//		StringBuilder succStr = new StringBuilder();
//		StringBuilder returnFailFileString = new StringBuilder();
//		String orgInfName = returnFileDao.getClearFilePath(clearDate, batchNo);
//		if ("".equals(orgInfName)) {
//			LOG.error("==============没有找到清算日期["+clearDate+"]批次["+batchNo+"]下的任务,不生成清算结果文件==============");
//			return null;
//		}
		File resultSuccessFile = new File(filePath + clearDate + File.separator + cityCode,"ISDA" + cityCode + "F.FCRECONS.PS." + clearDate);
//		File resultFailFile = new File(filePath + Constants.FAILED_SUFFIX);
		if (!resultSuccessFile.exists()) {
			String parent = resultSuccessFile.getParent();
			File dir = new File(parent);
			dir.mkdirs();
		}
//		if (!resultFailFile.exists()) {
//			String parent = resultFailFile.getParent();
//			File dir = new File(parent);
//			dir.mkdirs();
//		}
//		BigDecimal sumAmount = BigDecimal.ZERO;

		/*try {	
		    BufferedWriter resultSuccessFileWriter = new BufferedWriter(
					new OutputStreamWriter(new FileOutputStream(resultSuccessFile), "GBK"));
//			BufferedWriter resultFailFileWriter = new BufferedWriter(
//					new OutputStreamWriter(
//							new FileOutputStream(resultFailFile), "GBK"));
			List<ReturnFileBean> beans = returnFileDao.getDetailRecord(clearDate, batchNo);
			if(sn != beans.size())
				throw new Exception("当前日期[" + clearDate + "]当前批次号[" + batchNo +
						"]清算数据没处理完或转账处理有异常，请检查。");
			for (ReturnFileBean bean : beans) {
//				if (bean.getStatus().endsWith("1")) {
					//bean.setTranAmount(new BigDecimal(bean.getTranAmount()).toString());
//					LOG.info("交易金额为:"+bean.getOcdTramount());
					succStr.append(bean.toString() + "\n");
					
//					sumAmount = sumAmount.add(null==bean.getTranAmount()?new BigDecimal("0"):bean.getTranAmount());
//				} else {
//					
//					returnFailFileString.append((null==bean.getKey7()?"":bean.getKey7().trim())).append(
//							null==bean.getKEY11()?"":bean.getKEY11().trim() + "|")
//							.append((null==bean.getKey32()?"":bean.getKey32().trim()) + "|").append(
//									(null==bean.getKey33()?"":bean.getKey33().trim()) + "|").append(
//									(null==bean.getAcctNo()?"":bean.getAcctNo().trim())+ "|").append(
//									(null==bean.getTranAmount()?"":String.format("%.2f", bean.getTranAmount())) + "|").append(
//									(null==bean.getAcctingBranch()?"":bean.getAcctingBranch().trim()) + "|").append(
//									(null==bean.getStatus()?"":bean.getStatus().trim())+ "|").append(
//									(null==getFailReason(bean.getStatus())?"":getFailReason(bean.getStatus()).trim()) + "\n");
//				}
			}
			resultSuccessFileWriter.write(succStr.toString());
//			resultFailFileWriter.write(returnFailFileString.toString());
//			LOG.info("============失败结果文件生成[" + resultFailFile + "]============");
			resultSuccessFileWriter.flush();
			resultSuccessFileWriter.close();*/
//			resultFailFileWriter.flush();
//			resultFailFileWriter.close();
			try{	
//				List<ReturnFileBean> beans = returnFileDao.getDetailRecord(clearDate, batchNo);
				List<ClearDetailBean> beans = dao.getResultDetailBean(clearDate, billOrg);
				
				if(sn != beans.size()){
					LOG.error("查询总数[" + beans.size() + "]与实际文件总数[" + sn + "]不符，不能生成入账文件。");
					throw new Exception("查询总数[" + beans.size() + "]与实际文件总数[" + sn + "]不符，不能生成入账文件。");
				}
				
				FileUtils.writeLines(resultSuccessFile,"GBK", beans);
				res.add(filePath + clearDate + " " + cityCode + " " + resultSuccessFile.getName() + " " + resultSuccessFile.length());
				File chk = new File(resultSuccessFile.getParent(),resultSuccessFile.getName() + ".CHK");	
				FileUtils.writeStringToFile(chk, resultSuccessFile.getName() + " " + resultSuccessFile.length() + "\n","GBK");
				res.add(filePath + clearDate + " " + cityCode + " " + chk.getName() + " " + chk.length());

//				FileUtils.writeLines(new File(listpath,"ica_tooffline" + cityCode + "_cbod_" + clearDate + ".list"), res);
//				LOG.info("result path:" + resultSuccessFile);
//			}catch(Exception ex){
//				LOG.error("生成结果检查文件失败.任务继续." + ex.getMessage());
//			}

		} catch (Exception e) {
			LOG.error("结果文件生成异常:" + e.getMessage(), e);
			throw new Exception("结果文件生成异常:" + e.getMessage());
		}
//		return sumAmount.compareTo(new BigDecimal("0"))==0?null:sumAmount;
	}
/*
	private String getFailReason(String status) {
		StringBuilder reason = new StringBuilder();
		if ('2' == status.charAt(0)) {
			reason.append("【清算周期验证失败】");
		}
		if ('2' == status.charAt(1)) {
			reason.append("【TC校验失败】");
		}
		if ('2' == status.charAt(2)) {
			reason.append("【交易计数器校验失败】");
		}
		char c = status.charAt(3);
		switch (c) {
		case '2':
			reason.append("【转账失败】");
			break;
		case '3':
			reason.append("【借贷账户余额不足,转账失败】");
			break;
		case '4':
			reason.append("【卡应用关系绑定错误】");
			break;
		case '5':
			reason.append("【卡状态异常】");
			break;
		case '6':
			reason.append("【机构集中户查找失败】");
			break;
		case '7':
			reason.append("【IC卡平台不存在此卡号】");
		}
		return reason.toString();
	}
*/	

	/**
	 * 实现解析脱机消费的文件入库的方法(记录文件位置)
	 * 
	 * @param batchNo
	 *            批次号
	 * @param clearDate
	 *            清算日期
	 * @param fileName
	 *            文件路径(绝对路径)
	 * @param fileSrc
	 *            文件来源(1-本行 2-银联)
	 * @param billOrg
	 *            (如果是本行的脱机消费文件,这个字段为空,如果是银联的则取得的是银联的机构号)
	 * @throws Exception
	 */
	public Integer resoluteFileRemember(String batchNo, String clearDate,
			String fileName, String fileSrc, String billOrg, JobContext context)
			throws Exception {
		RandomAccessFile randomAccessFile = new RandomAccessFile(new File(fileName), "r");
		if(LOG.isInfoEnabled()){
			LOG.info("File length["+randomAccessFile.length()+"].");
		}
		String batchSNS = (String)context.getAttribute("batchSn");
		if (null == batchSNS || "".equals(batchSNS)) {
			batchSNS = "0";
		}
		int batchSN = Integer.parseInt(batchSNS);
		String locationReadS = (String)context.getAttribute("locationRead") ;
		if (null == locationReadS || "".equals(locationReadS)) {
			locationReadS = "0";
		}
		long locationRead = Long.valueOf(locationReadS);
		if (locationRead == (randomAccessFile.length()-49)) {
			LOG.info("文件解析完成.");
			return batchSN;
		}
		randomAccessFile.seek(locationRead);// 跳过文件头
		TXHelper.beginTX();
		try {
			while (true) {
				try {
					LOG.debug("batchSn:["+batchSN+"],locationRead["+locationRead+"]");
					if (0 == batchSN) {
						randomAccessFile.skipBytes(46);// 略过文件头
						LOG.info("略过脱机消费文件头长度：46");
					}
		
					byte[] bytes = new byte[526];
					randomAccessFile.read(bytes);
					ClearDetailBean bean = parse(ByteBuffer.wrap(bytes), "GBK");
					bean.setBatchNo(batchNo);
					bean.setClearDate(clearDate);
					bean.setFileSrc(fileSrc);
					bean.setBatchSn(++batchSN + "");
					bean.setBillOrg(billOrg);
					bean.setStatus("0000");// 初始化该明细为未处理状态
					dao.insertClearDetail(bean);
					if (batchSN % 100 == 0) {
						TXHelper.commit();
						TXHelper.beginTX();
					}
					locationRead = randomAccessFile.getFilePointer();
					if (locationRead == (randomAccessFile.length()-49)) {
						LOG.debug("文件解析完成.略过文件尾长度：49");
						break;
					}
				
				} catch (Throwable e) {
					LOG.error(e.getMessage(),e);
					context.setAttribute("locationRead", locationRead + "");
					context.setAttribute("batchSn", (batchSN - 1) + "");
					throw new JobException("解析入库出现异常," + e.getMessage());
				}
			}
			TXHelper.commit();
			return batchSN;
		} catch (Throwable e) {
			TXHelper.rollback();
			LOG.error(e.getMessage(),e);
			throw new Exception(e.getMessage());
		} finally {
			TXHelper.close();
			randomAccessFile.close();
		}
	}
	
}