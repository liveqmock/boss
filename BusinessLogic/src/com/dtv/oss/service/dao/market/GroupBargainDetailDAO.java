package com.dtv.oss.service.dao.market;
/*
 * Created on 2006-3-20 @author Chen jiang
 */

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.dtv.oss.dto.GroupBargainDetailDTO;
import com.dtv.oss.service.dao.GenericDAO;
import com.dtv.oss.util.DtoFiller;

/**
 * DAO class to contact with DB and do JDBC job
 */
public class GroupBargainDetailDAO extends GenericDAO {

	public GroupBargainDetailDAO () {
	}

	protected List prepareResult(ResultSet rs) throws SQLException {
		ArrayList list = new ArrayList();
		while (rs.next()) {
			GroupBargainDetailDTO dto = new GroupBargainDetailDTO ();
			  dto = DtoFiller.fillGroupBargainDetailDTO(rs);
		 
			list.add(dto);
		}
		return list;
	}
}