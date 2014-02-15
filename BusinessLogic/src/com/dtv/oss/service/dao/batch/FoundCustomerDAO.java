package com.dtv.oss.service.dao.batch;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.dtv.oss.dto.FoundCustomerDetaiDTO;
import com.dtv.oss.service.dao.GenericDAO;
import com.dtv.oss.util.DtoFiller;

public class FoundCustomerDAO extends GenericDAO {

	protected List prepareResult(ResultSet rs) throws SQLException {
		ArrayList list=new ArrayList();
		while(rs.next()){
		   FoundCustomerDetaiDTO dto =DtoFiller.fillFoundCustomerDetaiDTO(rs);
		   list.add(dto);	
		}
		return list;
	}
}
