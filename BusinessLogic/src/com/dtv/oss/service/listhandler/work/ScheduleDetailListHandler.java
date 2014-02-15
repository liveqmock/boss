package com.dtv.oss.service.listhandler.work;

import java.util.ArrayList;

import com.dtv.oss.dto.custom.CommonQueryConditionDTO;
import com.dtv.oss.dto.wrap.work.Schedule2CP2CCWrap;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.dao.work.ScheduleDetailDAO;
import com.dtv.oss.service.listhandler.ListHandlerException;
import com.dtv.oss.service.listhandler.ValueListHandler;


public class ScheduleDetailListHandler extends ValueListHandler {

	private CommonQueryConditionDTO dto = null;
	
	private ScheduleDetailDAO batchDAO = null;

	private static final Class clazz = ScheduleDetailListHandler.class;
	
	final private String tableName = " T_BatchJob BatchJob,T_BatchJobItem BatchJobItem,T_BatchJobProcessLog BatchJobProcessLog ";
	
	public ScheduleDetailListHandler(){
		batchDAO=new ScheduleDetailDAO();
	}
	
	public void setCriteria(Object dto1) throws ListHandlerException {
		LogUtility.log(clazz, LogLevel.DEBUG,"in setCriteria begin setCriteria...");
		if (dto1 instanceof CommonQueryConditionDTO)
			this.dto = (CommonQueryConditionDTO) dto1;
		else {
			LogUtility.log(JobCardListHandler.class, LogLevel.DEBUG,"the dto type is not proper...");
			throw new ListHandlerException("the dto type is not proper...");
		}

		if(dto.getSpareStr1()==null || "".equals(dto.getSpareStr1()))
				throw new ListHandlerException("查找排程的参数错误");
		
		//构造批处理头记录查询字符串
		constructSelectQueryString(dto);
		//执行数据查询
		executeSearch(batchDAO,false, false);
		

	}

	/**
	 * 
	 * BatchJob.batchID BatchJob_batchID,
	 * BatchJob.ReferType BatchJob_ReferType,
	 * BatchJob.ReferID BatchJob_ReferID,
	 * BatchJob.CreateOpID BatchJob_CreateOpID,
	 * BatchJob.CreateTime BatchJob_CreateTime,
	 * BatchJob.JobType BatchJob_JobType,
	 * BatchJob.ReasonCode BatchJob_ReasonCode,
	 * BatchJob.Description BatchJob_Description,
	 * BatchJob.ScheduleType BatchJob_ScheduleType,
	 * BatchJob.ScheduleTime BatchJob_ScheduleTime,
	 * BatchJob.ExecuteStartTime BatchJob_ExecuteStartTime,
	 * BatchJob.ExecuteEndTime BatchJob_ExecuteEndTime,
	 * BatchJob.Status BatchJob_Status,
	 * BatchJob.TotoalRecordNo BatchJob_TotoalRecordNo, 
	 * BatchJob.FailureRecordNo BatchJob_FailureRecordNo,
	 * BatchJob.SuccessRecordNo BatchJob_SuccessRecordNo,
	 * BatchJob.name BatchJob_name,
	 * 
	 * BatchJobItem.ItemNo BatchJobItem_ItemNo,
	 * BatchJobItem.CustomerID	BatchJobItem_CustomerID,
	 * BatchJobItem.AccountID BatchJobItem_AccountID,
	 * BatchJobItem.CustPackageID BatchJobItem_CustPackageID,
	 * BatchJobItem.PSID BatchJobItem_PSID,
	 * BatchJobItem.CCID BatchJobItem_CCID,
	 * BatchJobItem.Status BatchJobItem_Status,
	 * 
	 * BatchJobProcessLog.SeqNo BatchJobProcessLog_SeqNo,
	 * BatchJobProcessLog.Action BatchJobProcessLog_Action,
	 * BatchJobProcessLog.Result BatchJobProcessLog_Result,
	 * BatchJobProcessLog.Comments BatchJobProcessLog_Comments,
	 * BatchJobProcessLog.OperatorID BatchJobProcessLog_OperatorID,
	 * BatchJobProcessLog.OrgID BatchJobProcessLog_OrgID,
	 * BatchJobProcessLog.CreateTime BatchJobProcessLog_CreateTime

	 * @param dto2
	 */
	private void constructSelectQueryString(CommonQueryConditionDTO dto2) {
		StringBuffer begin = new StringBuffer();
		begin.append("select BatchJob.batchID BatchJob_batchID,	BatchJob.ReferType BatchJob_ReferType," +
				"BatchJob.ReferID BatchJob_ReferID,	BatchJob.CreateOpID BatchJob_CreateOpID," +
				"BatchJob.CreateTime BatchJob_CreateTime,BatchJob.JobType BatchJob_JobType," +
				"BatchJob.ReasonCode BatchJob_ReasonCode,BatchJob.Description BatchJob_Description," +
				"BatchJob.ScheduleType BatchJob_ScheduleType,BatchJob.ScheduleTime BatchJob_ScheduleTime," +
				"BatchJob.ExecuteStartTime BatchJob_ExecuteStartTime," +
				"BatchJob.ExecuteEndTime BatchJob_ExecuteEndTime,BatchJob.Status BatchJob_Status," +
				"BatchJob.TotoalRecordNo BatchJob_TotoalRecordNo,BatchJob.FailureRecordNo BatchJob_FailureRecordNo," +
				"BatchJob.SuccessRecordNo BatchJob_SuccessRecordNo,BatchJob.name BatchJob_name," +
				"BatchJobItem.ItemNo BatchJobItem_ItemNo,BatchJobItem.CustomerID BatchJobItem_CustomerID," +
				"BatchJobItem.AccountID BatchJobItem_AccountID," +
				"BatchJobItem.CustPackageID BatchJobItem_CustPackageID,BatchJobItem.PSID BatchJobItem_PSID," +
				"BatchJobItem.CCID BatchJobItem_CCID,BatchJobItem.Status BatchJobItem_Status," +
				"BatchJobProcessLog.SeqNo BatchJobProcessLog_SeqNo," +
				"BatchJobProcessLog.Action BatchJobProcessLog_Action," +
				"BatchJobProcessLog.Result BatchJobProcessLog_Result," +
				"BatchJobProcessLog.Comments BatchJobProcessLog_Comments," +
				"BatchJobProcessLog.OperatorID BatchJobProcessLog_OperatorID," +
				"BatchJobProcessLog.OrgID BatchJobProcessLog_OrgID," +
				"BatchJobProcessLog.CreateTime BatchJobProcessLog_CreateTime ," +
				"BatchJobProcessLog.ItemNO BatchJobProcessLog_ItemNO from ");
		begin.append(tableName);
		
		StringBuffer selectStatement = new StringBuffer(128);
		selectStatement.append(" where 1=1   and BatchJobItem.BATCHID=batchJob.BATCHID " +
				" and BatchJobProcessLog.BATCHID=batchJob.BATCHID ");
		//and batchJobProcessLog.itemNo=BatchJobItem.itemNo
		
		//1.批处理ID
		makeSQLByIntField("batchJob.batchid", Integer.valueOf(dto.getSpareStr1()).intValue(), selectStatement);
		
		//2.设置当前数据查询sql
		selectStatement.append(" order by BatchJobItem.ItemNo , BatchJobProcessLog.SeqNo desc");
		setRecordDataQueryBuffer(begin.append(selectStatement));
	}
	
	
	/**
	 * @param args
	 * @throws ListHandlerException 
	 */
	public static void main(String[] args) throws ListHandlerException {
		// TODO Auto-generated method stub
		
		CommonQueryConditionDTO dto=new CommonQueryConditionDTO();
		dto.setSpareStr1("109");
		ScheduleDetailListHandler handler=new ScheduleDetailListHandler();
		handler.setCriteria(dto);
		
		ArrayList list=(ArrayList) handler.getList();
		System.out.println("返回列表大小为:"+ list.size());
		
		Schedule2CP2CCWrap wrap=(Schedule2CP2CCWrap)list.iterator().next();
		System.out.println("得到明细列表大小为:"+ wrap.getBatchJobItemDTOList().size()+" ，数据为:"+ wrap.getBatchJobItemDTOList());
		System.out.println("得到处理日志大小为:"+ wrap.getBatchJobProcessLogDTOList().size()+", 数据为:"+ wrap.getBatchJobProcessLogDTOList());
		

	}

}
