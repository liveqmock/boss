package com.dtv.oss.service.dao.config;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.dtv.oss.dto.ResourcePhoneNoDTO;
import com.dtv.oss.service.dao.GenericDAO;
import com.dtv.oss.util.DtoFiller;

public class ResourcePhoneNoDAO extends GenericDAO {
	protected List prepareResult(ResultSet rs) throws SQLException {
		ArrayList list = new ArrayList();
		while (rs.next()) {
			ResourcePhoneNoDTO dto = DtoFiller.fillResourcePhoneNoDTO(rs);
			list.add(dto);
		}
		return list;
	}

}
