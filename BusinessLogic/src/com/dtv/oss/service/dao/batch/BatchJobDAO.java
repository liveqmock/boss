package com.dtv.oss.service.dao.batch;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.dtv.oss.dto.BatchJobDTO;
import com.dtv.oss.service.dao.GenericDAO;

public class BatchJobDAO extends GenericDAO {

	protected List prepareResult(ResultSet rs) throws SQLException {
		ArrayList list=new ArrayList();
		BatchJobDTO dto=null;
		while(rs.next()){
			dto=new BatchJobDTO();

			dto.setBatchId(rs.getInt("batchid"));
			dto.setCreateOpId(rs.getInt("createopid"));
			dto.setCreateTime(rs.getTimestamp("createtime"));
			dto.setDescription(rs.getString("description"));
			dto.setDtCreate(rs.getTimestamp("dt_create"));
			dto.setDtLastmod(rs.getTimestamp("dt_lastmod"));
			dto.setExecuteEndTime(rs.getTimestamp("executeendtime"));
			dto.setExecuteStartTime(rs.getTimestamp("executestarttime"));
			dto.setFailureRecordNo(rs.getInt("failurerecordno"));
			dto.setJobType(rs.getString("jobtype"));
			dto.setName(rs.getString("name"));
			dto.setReasonCode(rs.getString("reasoncode"));
			dto.setReferId(rs.getInt("referid"));
			dto.setReferType(rs.getString("refertype"));
			dto.setScheduleTime(rs.getTimestamp("scheduletime"));
			dto.setScheduleType(rs.getString("scheduletype"));
			dto.setStatus(rs.getString("status"));
			dto.setSuccessRecordNo(rs.getInt("successrecordno"));
			dto.setTotoalRecordNo(rs.getInt("totoalrecordno"));
			list.add(dto);
		}
		return list;
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
	}
}
