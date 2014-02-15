package com.dtv.oss.service.dao.work;
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
public class JobCardProcessLogDAO extends GenericDAO {

	public JobCardProcessLogDAO() {
	}

	protected List prepareResult(ResultSet rs) throws SQLException {
		ArrayList list = new ArrayList();
		while (rs.next()) {
			 
		    JobCardProcessDTO dto = DtoFiller.fillJobCardProcessDTO(rs);
			list.add(dto);
		}
		return list;
	}
}