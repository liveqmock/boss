/*
 * Created on 2004-12-15
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.dtv.oss.service.dao.config;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.dtv.oss.dto.DistrictSettingDTO;
import com.dtv.oss.service.dao.GenericDAO;
/**
 * @author 220226
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class DistrictSettingDAO extends GenericDAO {
	public DistrictSettingDAO() {
	}

	protected List prepareResult(ResultSet rs) throws SQLException {
		ArrayList list = new ArrayList();
		while (rs.next()) {
			DistrictSettingDTO dto = new DistrictSettingDTO();
			dto.setId(rs.getInt("ID"));
			dto.setDistrictCode(rs.getString("DISTRICTCODE"));
			dto.setName(rs.getString("NAME"));
			dto.setType(rs.getString("TYPE"));
			dto.setBelongTo(rs.getInt("BELONGTO"));
			dto.setStatus(rs.getString("Status"));
			dto.setDtCreate(rs.getTimestamp("DT_CREATE"));
			dto.setDtLastmod(rs.getTimestamp("DT_LASTMOD"));
			
			list.add(dto);
		}
		return list;
	}

}
