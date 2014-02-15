package com.dtv.oss.service.dao.work;
/*
 * Created on 2004-8-9 @author Mac Wang
 */

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.dtv.oss.dto.CustomerProblemDTO;
import com.dtv.oss.service.dao.GenericDAO;
import com.dtv.oss.util.DtoFiller;

/**
 * DAO class to contact with DB and do JDBC job
 */
public class CustomerProblemDAO extends GenericDAO {

	public CustomerProblemDAO() {
	}

	protected List prepareResult(ResultSet rs) throws SQLException {
		ArrayList list = new ArrayList();
		while (rs.next()) {
			CustomerProblemDTO dto = new CustomerProblemDTO();
			dto = DtoFiller.fillCustomerProblemDTO(rs);
			list.add(dto);
		}
		return list;
	}
}