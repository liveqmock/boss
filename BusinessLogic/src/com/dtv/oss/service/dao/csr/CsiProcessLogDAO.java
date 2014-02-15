package com.dtv.oss.service.dao.csr;
/*
 * Created on 2004-8-9 @author Mac Wang
 */

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.dtv.oss.dto.CsiProcessLogDTO;
import com.dtv.oss.service.dao.GenericDAO;
import com.dtv.oss.util.DtoFiller;
/**
 * DAO class to contact with DB and do JDBC job
 */
public class CsiProcessLogDAO extends GenericDAO {

	public CsiProcessLogDAO() {
	}

	protected List prepareResult(ResultSet rs) throws SQLException {
		ArrayList list = new ArrayList();
		while (rs.next()) {
			CsiProcessLogDTO dto = DtoFiller.fillCsiProcessLogDTO(rs);
			list.add(dto);
		}
		return list;
	}
}