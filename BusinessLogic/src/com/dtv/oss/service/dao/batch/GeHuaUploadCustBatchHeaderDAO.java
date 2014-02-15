
package com.dtv.oss.service.dao.batch;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.dtv.oss.dto.custom.GeHuaUploadCustBatchHeaderDTO;
import com.dtv.oss.service.dao.GenericDAO;

public class GeHuaUploadCustBatchHeaderDAO extends GenericDAO {

	protected List prepareResult(ResultSet rs) throws SQLException {
		ArrayList list=new ArrayList();
		
		while(rs.next()){
			GeHuaUploadCustBatchHeaderDTO dto=new GeHuaUploadCustBatchHeaderDTO();
			dto.setBatchID(rs.getInt("BatchID"));
			dto.setBatchNo(rs.getInt("BatchNo"));
			dto.setCreateOpID(rs.getInt("CreateOpID"));
			dto.setCreateOrgID(rs.getInt("CreateOrgID"));
			dto.setCreateTime(rs.getTimestamp("CreateTime"));
			dto.setComments(rs.getString("Comments"));
			dto.setDtCreate(rs.getTimestamp("DT_CREATE"));
			dto.setDtLastmod(rs.getTimestamp("DT_LASTMOD"));
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