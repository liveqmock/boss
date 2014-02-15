package com.dtv.oss.service.dao.config;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.dtv.oss.dto.SwapDeviceReason2StatusDTO;
import com.dtv.oss.service.dao.GenericDAO;
import com.dtv.oss.util.DtoFiller;

public class SwapDeviceReason2StatusDAO  extends GenericDAO {

	protected List prepareResult(ResultSet rs) throws SQLException {
	       ArrayList list = new ArrayList();
	        while(rs.next()) {
	        	SwapDeviceReason2StatusDTO dto = DtoFiller.fillSwapDeviceReason2StatusDTO(rs);
	            list.add(dto);		
	        }
	        return list;
	}

}
