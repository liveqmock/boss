package com.dtv.oss.service.dao.batch;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.dtv.oss.dto.QueryResultItemDTO;
import com.dtv.oss.service.dao.GenericDAO;

public class BatchResultDAO extends GenericDAO {

	protected List prepareResult(ResultSet rs) throws SQLException {
		ArrayList list=new ArrayList();
		QueryResultItemDTO dto=null;
		while(rs.next()){
			dto=new QueryResultItemDTO();
			
			dto.setAccountId(rs.getInt("AccountID"));
			dto.setCcId(rs.getInt("CCID"));
			dto.setCustomerId(rs.getInt("CustomerID"));
			dto.setDtCreate(rs.getTimestamp("dt_create"));
			dto.setDtLastmod(rs.getTimestamp("dt_lastmod"));
			dto.setItemNO(rs.getInt("ItemNO"));
			dto.setPackageId(rs.getInt("PackageID"));
			dto.setProductId(rs.getInt("ProductID"));
			dto.setPsId(rs.getInt("PSID"));
			dto.setQueryId(rs.getInt("QueryID"));
			dto.setStatus(rs.getString("Status"));
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
