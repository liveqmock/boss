package com.dtv.oss.service.dao.market;
/*
 * Created on 2006-3-20 @author Chen jiang
 */

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.dtv.oss.dto.GroupBargainDTO;
import com.dtv.oss.service.dao.GenericDAO;
import com.dtv.oss.util.DtoFiller;

/**
 * DAO class to contact with DB and do JDBC job
 */
public class GroupBargainDAO extends GenericDAO {

	public GroupBargainDAO() {
	}

	protected List prepareResult(ResultSet rs) throws SQLException {
		ArrayList list = new ArrayList();
		while (rs.next()) {
			GroupBargainDTO dto = new GroupBargainDTO();
			  dto = DtoFiller.fillGroupBargainDTO(rs);
		 
			list.add(dto);
		}
		return list;
	}
}