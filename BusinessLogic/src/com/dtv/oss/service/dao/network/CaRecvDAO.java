package com.dtv.oss.service.dao.network;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.dtv.oss.dto.CAProcessEventDTO;
import com.dtv.oss.dto.CARecvDTO;
import com.dtv.oss.dto.SystemEventDTO;
import com.dtv.oss.service.dao.GenericDAO;
import com.dtv.oss.util.DtoFiller;

/**
 * 
 * @author 260327h
 *
 */
public class CaRecvDAO extends GenericDAO {

	protected List prepareResult(ResultSet rs) throws SQLException {
		ArrayList list = new ArrayList();
		while (rs.next()) {
			CARecvDTO dto = DtoFiller.fillCARecvDTO(rs);
			list.add(dto);
		}
		return list;
	}

}
