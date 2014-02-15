package com.dtv.oss.service.dao.groupcustomer;
/*
 * Created on 2004-8-9 @author Mac Wang
 */

import java.util.ArrayList;
import java.util.List;
import java.sql.ResultSet;
import java.sql.SQLException;

 
import com.dtv.oss.service.dao.GenericDAO;
import com.dtv.oss.util.DtoFiller;
import com.dtv.oss.dto.*;

/**
 * DAO class to contact with DB and do JDBC job
 */
public class ContractProcLogDAO extends GenericDAO {

	public ContractProcLogDAO() {
	}

	protected List prepareResult(ResultSet rs) throws SQLException {
		ArrayList list = new ArrayList();
		while (rs.next()) {
			 
			ContractProcessLogDTO dto = DtoFiller.fillContractProcessLogDTO(rs);
			list.add(dto);
		}
		return list;
	}
}