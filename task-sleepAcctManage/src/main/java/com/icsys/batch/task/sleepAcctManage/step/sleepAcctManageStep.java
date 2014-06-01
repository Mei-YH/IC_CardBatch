package com.icsys.batch.task.sleepAcctManage.step;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.icsys.batch.api.Executor;
import com.icsys.batch.api.JobContext;
import com.icsys.batch.api.JobException;
import com.icsys.batch.api.Step;
import com.icsys.batch.task.sleepAcctManage.proxy.bean.GrAccAcct;
import com.icsys.batch.task.sleepAcctManage.proxy.dao.GrAccAcctDao;
import com.icsys.batch.util.BatchDateUtil;
import com.icsys.batch.util.DateUtil;

public class sleepAcctManageStep implements Step {
	private Logger LOG = Logger.getLogger(sleepAcctManageStep.class);

	public long estimate(JobContext arg0) throws JobException {
		return 0;
	}

	public void perform(JobContext context, Executor executor)
			throws JobException {
		LOG.debug("睡眠账户管理扣收任务开始执行");
		String resultFilePath = (String) context.getAttribute("resultFilePath");// 获取写入本地文件根路径
		String listPath = (String) context.getAttribute("listPath");
		int limitDay = Integer.valueOf((String) context.getAttribute("cycle"));// 无业务间隔时间
		BigDecimal limitAcct = new BigDecimal((String) context
				.getAttribute("minLimit"));// 最低金额
		BigDecimal removeAcct = new BigDecimal((String) context
				.getAttribute("remAmount"));// 扣收金额
		String sysDate = context.getJobDate();
		// SleepAcctManageService service = new SleepAcctManageService();

		try {
			String lastDate = BatchDateUtil
					.getLastDateWithStringFormat(sysDate);
			// List<IcCardInfoReq> cardList = icInfoReqDao.getWellStatAcc();//
			// 获取所有状态为正常的卡集合
			/**
			 * 初始化装文件名和装类容的集合
			 */
			Map<String, String> adressMap = MapInit.getAdressMap();
			Map<String, StringBuilder> fileContentMap = MapInit
					.getFileContent();
			Set<String> adressSet = adressMap.keySet();// 两个集合的keySet都是一样的
			// 获取以上参数信息
			if (limitAcct.compareTo(BigDecimal.ZERO) > 0) {
				GrAccAcctDao grAccAcctDao = new GrAccAcctDao();
				List<GrAccAcct> acctList = grAccAcctDao.getSleepAccList();
				for (GrAccAcct grAcc : acctList) {
					boolean timeFlag = null == grAcc.getAccltdate() ? false
							: ((DateUtil.getdaysbetween(grAcc.getAccltdate(),lastDate) - 1)
									% limitDay == 0);
					boolean acctFlag = grAcc.getAccBalance().compareTo(limitAcct) < 0;
					LOG.info("icNo[" + grAcc.getAccNo() + "],acctFlag[" + acctFlag
							+ "],timeFlag[" + timeFlag + "]");
					if (acctFlag == true && timeFlag == true) {// 如果满足条件就向下执行,进行字符串的拼接
						StringBuilder sb = new StringBuilder();
						sb.append(StringUtils.rightPad(grAcc.getAccNo(), 19,
								" "));// 卡号
						sb.append("22");// 卡状态
						sb.append(StringUtils.rightPad(limitDay + "", 8, " "));// 未使用时间，按固定参数365返回
						sb.append(StringUtils.leftPad(grAcc.getAccBalance()
								.movePointRight(2).toString(), 15, "0"));// 脱机电子现金金额
						sb.append(StringUtils.leftPad(removeAcct
								.movePointRight(2).toString(), 15, "0"));

						/**
						 * 依次比较看卡属地市是属于哪个地区，在将内容放入相应的文件中
						 */
						String key = MapInit.getAreaMap().get(grAcc.getMac());
						fileContentMap.get(key).append(
								sb.append("\n").toString());
						fileContentMap.put(key, fileContentMap.get(key));
					}

				}
			}

			// 写入本地文件

			try {
				List<String> res = new ArrayList<String>();
				for (String key : adressSet) {
					File resultFile = new File(resultFilePath + File.separator
							+ sysDate + File.separator + key, adressMap
							.get(key)
							+ sysDate);// ICSleepAcctDeductFile_8位日期
					if (!resultFile.exists()) {
						resultFile.getParentFile().mkdirs();
						resultFile.createNewFile();
					}
					FileUtils.writeStringToFile(resultFile, fileContentMap.get(
							key).toString(), "GBK");
					res.add(resultFilePath + File.separator + sysDate + " "
							+ key + " " + resultFile.getName() + " "
							+ resultFile.length());
					// 生成验证文件
					File chkFile = new File(resultFile.getAbsolutePath()
							+ ".CHK");// ICSleepAcctDeductFile_8.CHK位日期
					if (!chkFile.exists()) {
						chkFile.getParentFile().mkdirs();
						chkFile.createNewFile();
					}
					FileUtils.writeStringToFile(chkFile, resultFile.getName()
							+ " " + resultFile.length() + "\n", "GBK");
					res.add(resultFilePath + File.separator + sysDate + " "
							+ key + " " + chkFile.getName() + " "
							+ chkFile.length());

				}
				FileUtils.writeLines(new File(listPath, "ica_tosleep_cbod_"
						+ sysDate + ".list"), res);

			} catch (Exception ex) {
				throw new JobException("写入文件失败！任务挂起。" + ex.getMessage());
			}
		} catch (Exception e) {
			// TODO: handle exception
			throw new JobException("任务失败挂起：" + e.getMessage());
		}
		LOG.debug("睡眠账户管理费扣收任务执行结束.");
	}

}
