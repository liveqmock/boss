package com.dtv.oss.service.dao.csr;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.dtv.oss.dto.AccountAdjustmentDTO;
import com.dtv.oss.dto.wrap.customer.AccountAdjust2ReferRecordWrap;
import com.dtv.oss.service.dao.GenericDAO;
import com.dtv.oss.util.DtoFiller;

public class AdjustAccountDAO extends GenericDAO {

	protected List prepareResult(ResultSet rs) throws SQLException {
		ArrayList list=new ArrayList();
		AccountAdjust2ReferRecordWrap wrapDTO=null;
		AccountAdjustmentDTO dto=null;
		while(rs.next()){
			wrapDTO=new AccountAdjust2ReferRecordWrap();
			
			dto = DtoFiller.fillAccountAdjustmentDTO(rs);	
			
			wrapDTO.setAcctAdjDTO(dto);
			wrapDTO.setReferAmount(rs.getDouble("amount"));
			wrapDTO.setReferRecordTimer(rs.getTimestamp("originalTime"));
			
			list.add(wrapDTO);
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
