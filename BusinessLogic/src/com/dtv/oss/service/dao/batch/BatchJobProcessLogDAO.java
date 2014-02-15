package com.dtv.oss.service.dao.batch;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.dtv.oss.dto.BatchJobProcessLogDTO;
import com.dtv.oss.service.dao.GenericDAO;

public class BatchJobProcessLogDAO extends GenericDAO {

	protected List prepareResult(ResultSet rs) throws SQLException {
		ArrayList list=new ArrayList();
		BatchJobProcessLogDTO dto=null;
		while(rs.next()){
			dto=new BatchJobProcessLogDTO();
			
			dto.setAction(rs.getString("Action"));
			dto.setBatchId(rs.getInt("batchID"));
			dto.setComments(rs.getString("Comments"));
			dto.setItemNO(rs.getInt("ItemNo"));
			dto.setOperatorId(rs.getInt("OperatorID"));
			dto.setOrgId(rs.getInt("OrgID"));
			dto.setResult(rs.getString("Result"));
			dto.setSeqNO(rs.getInt("SeqNo"));
			dto.setCreateTime(rs.getTimestamp("CreateTime"));
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
