package com.dtv.oss.service.dao.batch;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.dtv.oss.dto.BatchJobItemDTO;
import com.dtv.oss.service.dao.GenericDAO;

public class BatchJobResultDAO extends GenericDAO {

	protected List prepareResult(ResultSet rs) throws SQLException {
		ArrayList list=new ArrayList();
		BatchJobItemDTO dto=null;
		while(rs.next()){
			dto=new BatchJobItemDTO();

			dto.setAccountId(rs.getInt("AccountID"));
			dto.setCcId(rs.getInt("CCID"));
			dto.setCustomerId(rs.getInt("CustomerID"));
			dto.setDtCreate(rs.getTimestamp("dt_create"));
			dto.setDtLastmod(rs.getTimestamp("dt_lastmod"));
			dto.setItemNO(rs.getInt("ItemNO"));
			dto.setCustPackageId(rs.getInt("custPackageID"));
			dto.setPsId(rs.getInt("PSID"));
			dto.setBatchId(rs.getInt("batchid"));
			dto.setStatus(rs.getString("Status"));
			dto.setStatusTime(rs.getTimestamp("statustime"));
			dto.setRcdData(rs.getString("rcddata"));
			dto.setUserId(rs.getInt("UserID"));
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
