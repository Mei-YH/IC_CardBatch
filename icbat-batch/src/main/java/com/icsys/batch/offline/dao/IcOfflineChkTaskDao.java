package com.icsys.batch.offline.dao;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.icsys.batch.offline.bean.ClearTaskBean;
import com.icsys.batch.util.Constants;
import com.icsys.batch.util.SystemConstans;
import com.icsys.platform.dao.template.NamedQueryTemplate;
import com.icsys.platform.dao.template.NamedUpdateTemplate;
import com.icsys.platform.dao.template.TemplateManager;

public class IcOfflineChkTaskDao {
	
//	private static Logger LOG = Logger.getLogger(IcOfflineChkTaskDao.class);
	private NamedUpdateTemplate dm = TemplateManager.getNamedUpdateTemplate(SystemConstans.DB_SOURCE_NAME);
	private NamedQueryTemplate dq = TemplateManager.getNamedQueryTemplate(SystemConstans.DB_SOURCE_NAME);

	/**
	 * 根据清算日期，批次号，插入或更新脱机清算任务列表
	 * @param task 脱机清算任务对象
	 */
	public void updateOrInsertClearTask(ClearTaskBean task){
		String clearDate = task.getClearDate();
		int branchNo = task.getBatchNo();
		ClearTaskBean temp = queryTaskByPK(clearDate,branchNo);
		String sql = "";
		if(temp!=null){
			//如果该条记录已经存在，则更新
			sql = "update gr_bus_offtask set ota_trdate=#TRAN_DATE#,ota_subbranch=#SUBMIT_BRANCH#,ota_steller=#SUBMIT_TELLER#,ota_ofile=#ORIGINAL_FILE#," +
				"ota_original_file_count=#ORIGINAL_FILE_COUNT#,ota_ofoamount=#ORIGINAL_FILE_SUM_AMOUNT#,ota_clfile=#CLEAR_FILE#," +
				"ota_cfcount=#CLEAR_FILE_COUNT#,ota_cfsamount=#CLEAR_FILE_SUM_AMOUNT#,ota_fcount=#FAIL_COUNT#,ota_famount=#FAIL_AMOUNT#," +
				"ota_cltype=#CLEAR_TYPE#,ota_trstage=#TREATMENT_STAGE#,ota_status=#STATUS#,ota_brpro=#BRANCH_PRO# where ota_cldate=#CLEAR_DATE# and ota_batno=#BATCH_NO#";
		}else{
			//如果该条记录不存在，则插入
			sql = "insert into gr_bus_offtask(ota_cldate,ota_batno,ota_trdate,ota_subbranch,ota_steller,ota_ofile,ota_original_file_count" +
					",ota_ofoamount,ota_clfile,ota_cfcount,ota_cfsamount,ota_fcount,ota_famount,ota_cltype,ota_trstage,ota_status,ota_brpro)" +
					" values(#CLEAR_DATE#,#BATCH_NO#,#TRAN_DATE#,#SUBMIT_BRANCH#,#SUBMIT_TELLER#,#ORIGINAL_FILE#,#ORIGINAL_FILE_COUNT#,#ORIGINAL_FILE_SUM_AMOUNT#," +
					"#CLEAR_FILE#,#CLEAR_FILE_COUNT#,#CLEAR_FILE_SUM_AMOUNT#,#FAIL_COUNT#,#FAIL_AMOUNT#,#CLEAR_TYPE#,#TREATMENT_STAGE#,#STATUS#,#BRANCH_PRO#)";
		}
		dm.update(sql, task);
	}
	
	/**
	 * 根据主键（清算日期与批次号）查询清算任务
	 * @param clearDate 清算日期
	 * @param batch_no  批次号
	 * @return 脱机清算任务对象
	 */
	public ClearTaskBean queryTaskByPK(String clearDate,Integer batch_no){
		String  querySql = "select ota_cldate as clear_date,ota_batno as batch_no,ota_trdate as tran_date,ota_subbranch as submit_branch,ota_steller as submit_teller,ota_ofile as original_file," +
				"ota_original_file_count as original_file_count,ota_ofoamount as original_file_sum_amount,ota_clfile as clear_file,ota_cfcount as clear_file_count,ota_fcount as fail_count," +
				"ota_famount as fail_amount,ota_cfsamount as clear_file_sum_amount,ota_cltype as clear_type,ota_trstage as treatment_stage,ota_status as status,ota_brpro as branch_pro from gr_bus_offtask where ota_cldate=#CLEAR_DATE# and ota_batno=#BATCH_NO#";
		ClearTaskBean params = new ClearTaskBean();
		params.setBatchNo(batch_no);
		params.setClearDate(clearDate);
		ClearTaskBean task  = dq.queryRow(querySql, params, ClearTaskBean.class);
		return task;
	}
	
	/**
	 * 更改任务的状态
	 * @param batchNo 批次号
	 * @param clearDate  清算日期
	 * @param status 任务状态
	 */
	public void changeTaskStatus(String batchNo,String clearDate,String status){
		ClearTaskBean clearTaskBean = queryTaskByPK(clearDate, Integer.parseInt(batchNo));;
		//-----修改任务状态开始------//
		clearTaskBean.setStatus(status);
		updateOrInsertClearTask(clearTaskBean);
		//-----修改任务状态结束------//
	}
	
	/**
	 * 批处理任务的第二次赋值，如该任务成功/失败的交易笔数，成功/失败的交易金额等。。
	 * @param clearDate
	 * @param batchNo
	 */
	public void updateTask(String clearDate,Integer batchNo){
		ClearTaskBean task = queryTaskByPK(clearDate,batchNo);
		String sql = "select * from ic_offline_task_result_view t where t.clear_date='"+clearDate+"' and t.batch_no="+batchNo;
		List<Map<String,Object>> result = dq.query(sql, new HashMap<String,Object>());
		if(result.size()<0)return;
		Map<String,Object> data = result.get(0);
		task.setOriginalFileCount(Integer.parseInt(data.get("TOTALCOUNT").toString()));//原始文件总笔数
		task.setOriginalFileSumAmount(new BigDecimal(data.get("TOTALAMT").toString()));//原始文件总金额
		task.setClearFileCount(Integer.parseInt(data.get("SUCCESSCOUNT").toString()));//清算文件总笔数
		task.setClearFileSumAmount(new BigDecimal(data.get("SUCCESSAMT").toString()));//清算文件总金额
		task.setFailCount(Integer.parseInt(data.get("FAILEDCOUNT").toString()));//失败笔数
		task.setFailAmount(new BigDecimal(data.get("FAILEDAMT").toString()));//失败金额
		task.setStatus(Constants.STATUS_SUCCESS);
		updateOrInsertClearTask(task);
	}
	
	public String querySumByCityCode(String clearDate,String cityCode){
		Map<String,Object> para = new HashMap<String, Object>();
		para.put("clearDate", clearDate);
		para.put("cityCode", cityCode);
		String sql = "select sum(ota_original_file_count) as NUM from gr_bus_offtask " +
				"where ota_cldate=#clearDate# and ota_brpro=#cityCode# group by ota_brpro";
		Map<String,Object> re = dq.queryRow(sql, para);
		if(re == null || re.size() < 1){
			return "0";
		}
		return String.valueOf(re.get("NUM"));
	}
	
}