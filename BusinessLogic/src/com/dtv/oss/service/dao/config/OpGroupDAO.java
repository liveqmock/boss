package com.dtv.oss.service.dao.config;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.dtv.oss.dto.OpGroupDTO;
import com.dtv.oss.service.dao.GenericDAO;
import com.dtv.oss.util.DtoFiller;

public class OpGroupDAO  extends GenericDAO {

	protected List prepareResult(ResultSet rs) throws SQLException {
	       ArrayList list = new ArrayList();
	        while(rs.next()) {
	        	OpGroupDTO dto = DtoFiller.fillOpGroupDTO(rs);
	            list.add(dto);		
	        }
	        return list;
	}

}
