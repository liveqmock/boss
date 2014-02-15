package com.dtv.oss.service.dao.config;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.dtv.oss.dto.SystemSettingDTO;
import com.dtv.oss.service.dao.GenericDAO;
import com.dtv.oss.util.DtoFiller;

/**
 * 
 * @author 260327h
 *
 */
public class SystemSettingDAO extends GenericDAO {

	protected List prepareResult(ResultSet rs) throws SQLException {
		ArrayList list = new ArrayList();
		while (rs.next()) {
			SystemSettingDTO dto = DtoFiller.fillSystemSettingDTO(rs);
			list.add(dto);
		}
		return list;
	}

}
