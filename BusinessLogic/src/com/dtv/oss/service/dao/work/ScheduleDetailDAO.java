package com.dtv.oss.service.dao.work;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.dtv.oss.dto.BatchJobDTO;
import com.dtv.oss.dto.BatchJobItemDTO;
import com.dtv.oss.dto.BatchJobProcessLogDTO;
import com.dtv.oss.dto.wrap.work.Schedule2CP2CCWrap;
import com.dtv.oss.service.dao.GenericDAO;

public class ScheduleDetailDAO extends GenericDAO {

	protected List prepareResult(ResultSet rs) throws SQLException {
		ArrayList list = new ArrayList();
		Schedule2CP2CCWrap wrap =new Schedule2CP2CCWrap();
		
		BatchJobDTO batchJobDTO=null;
		BatchJobItemDTO batchJobItemDTO=null;
		BatchJobProcessLogDTO batchJobProcessLogDTO=null;
		
		Collection batchJobItemList=new ArrayList();
		Collection batchJobProcessList=new ArrayList();
		
		while(rs.next()){
			batchJobDTO=new BatchJobDTO();
			batchJobItemDTO=new BatchJobItemDTO();
			batchJobProcessLogDTO=new BatchJobProcessLogDTO();

			//添加头记录
			batchJobDTO.setBatchId(rs.getInt("BatchJob_batchID"));
			batchJobDTO.setCreateOpId(rs.getInt("BatchJob_CreateOpID"));
			batchJobDTO.setCreateTime(rs.getTimestamp("BatchJob_CreateTime"));
			batchJobDTO.setDescription(rs.getString("BatchJob_Description"));
			batchJobDTO.setExecuteEndTime(rs.getTimestamp("BatchJob_ExecuteEndTime"));
			batchJobDTO.setExecuteStartTime(rs.getTimestamp("BatchJob_ExecuteStartTime"));
			batchJobDTO.setFailureRecordNo(rs.getInt("BatchJob_FailureRecordNo"));
			batchJobDTO.setJobType(rs.getString("BatchJob_JobType"));
			batchJobDTO.setReasonCode(rs.getString("BatchJob_ReasonCode"));
			batchJobDTO.setReferId(rs.getInt("BatchJob_ReferID"));
			batchJobDTO.setReferType(rs.getString("BatchJob_ReferType"));
			batchJobDTO.setScheduleTime(rs.getTimestamp("BatchJob_ScheduleTime"));
			batchJobDTO.setScheduleType(rs.getString("BatchJob_ScheduleType"));
			batchJobDTO.setStatus(rs.getString("BatchJob_Status"));
			batchJobDTO.setSuccessRecordNo(rs.getInt("BatchJob_SuccessRecordNo"));
			batchJobDTO.setTotoalRecordNo(rs.getInt("BatchJob_TotoalRecordNo"));
			batchJobDTO.setName(rs.getString("BatchJob_name"));
			if(wrap.getBatchJobDTO()==null)
				wrap.setBatchJobDTO(batchJobDTO);

			//添加明细
			batchJobItemDTO.setAccountId(rs.getInt("BatchJobItem_AccountID"));
			batchJobItemDTO.setBatchId(rs.getInt("BatchJob_batchID"));
			batchJobItemDTO.setCcId(rs.getInt("BatchJobItem_CCID"));
			batchJobItemDTO.setCustomerId(rs.getInt("BatchJobItem_CustomerID"));
			batchJobItemDTO.setCustPackageId(rs.getInt("BatchJobItem_CustPackageID"));
			batchJobItemDTO.setItemNO(rs.getInt("BatchJobItem_ItemNo"));
			batchJobItemDTO.setStatus(rs.getString("BatchJobItem_Status"));
			batchJobItemDTO.setPsId(rs.getInt("BatchJobItem_PSID"));
			if(!batchJobItemList.contains(batchJobItemDTO)){
				batchJobItemList.add(batchJobItemDTO);
			}
			
			
			//添加日志
			batchJobProcessLogDTO.setAction(rs.getString("BatchJobProcessLog_Action"));
			batchJobProcessLogDTO.setBatchId(rs.getInt("BatchJob_batchID"));
			batchJobProcessLogDTO.setComments(rs.getString("BatchJobProcessLog_Comments"));
			//batchJobProcessLogDTO.setItemNO(rs.getInt("BatchJobItem_ItemNo"));
			batchJobProcessLogDTO.setOperatorId(rs.getInt("BatchJobProcessLog_OperatorID"));
			batchJobProcessLogDTO.setOrgId(rs.getInt("BatchJobProcessLog_OrgID"));
			batchJobProcessLogDTO.setResult(rs.getString("BatchJobProcessLog_Result"));
			batchJobProcessLogDTO.setSeqNO(rs.getInt("BatchJobProcessLog_SeqNo"));
			batchJobProcessLogDTO.setItemNO(rs.getInt("BatchJobProcessLog_ItemNO"));
			if(!batchJobProcessList.contains(batchJobProcessLogDTO)){
				batchJobProcessList.add(batchJobProcessLogDTO);
				//System.out.println(batchJobProcessLogDTO);
			}
				

		}
		wrap.setBatchJobItemDTOList(batchJobItemList);
		wrap.setBatchJobProcessLogDTOList(batchJobProcessList);
		list.add(wrap);
		
		return list;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
