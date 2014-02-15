package com.dtv.oss.service.dao.work;
/*
 * Created on 2006-1-4 @author chen jiang
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
public class CustProblemProcessLogDAO extends GenericDAO {

	public CustProblemProcessLogDAO() {
	}

	protected List prepareResult(ResultSet rs) throws SQLException {
		ArrayList list = new ArrayList();
		while (rs.next()) {
			 
			CustProblemProcessDTO dto = DtoFiller.fillCustProblemProcessDTOAndOther(rs);
			list.add(dto);
		}
		return list;
	}
}