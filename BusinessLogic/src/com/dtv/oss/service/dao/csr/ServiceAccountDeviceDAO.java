package com.dtv.oss.service.dao.csr;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.dtv.oss.dto.ServiceAccountDeviceDTO;
import com.dtv.oss.service.dao.GenericDAO;
import com.dtv.oss.util.DtoFiller;

public class ServiceAccountDeviceDAO extends GenericDAO{

	protected List prepareResult(ResultSet rs) throws SQLException {
		ArrayList list=new ArrayList();
		while(rs.next()){
			ServiceAccountDeviceDTO dto = DtoFiller.fillServiceAccountDeviceDTO(rs);
			list.add(dto);			
		}
		return list;
	}

}
