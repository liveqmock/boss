package com.dtv.oss.service.dao.network;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.dtv.oss.dto.VOIPCmdProDTO;
import com.dtv.oss.dto.VOIPEventCmdDTO;
import com.dtv.oss.service.dao.GenericDAO;
import com.dtv.oss.util.DtoFiller;

public class VOIPCmdProDAO extends GenericDAO{

	protected List prepareResult(ResultSet rs) throws SQLException {
		ArrayList list = new ArrayList();
		while (rs.next()) {
			VOIPCmdProDTO dto=DtoFiller.fillVOIPCmdListDTO(rs);
			list.add(dto);
		}
		return list;
	}

}
