package com.dtv.oss.service.dao.network;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.dtv.oss.dto.CasubentitlementDTO;
import com.dtv.oss.service.dao.GenericDAO;
import com.dtv.oss.util.DtoFiller;

/**
 * 
 * @author 260710h
 *
 */
public class CASubentitlementDAO extends GenericDAO {

	protected List prepareResult(ResultSet rs) throws SQLException {
		ArrayList list = new ArrayList();
		while (rs.next()) {
			CasubentitlementDTO dto = DtoFiller.fillCasubentitlementDTO(rs);
			list.add(dto);
		}
		return list;
	}

}
