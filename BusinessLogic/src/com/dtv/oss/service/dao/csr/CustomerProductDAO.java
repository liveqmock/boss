package com.dtv.oss.service.dao.csr;

import java.util.ArrayList;
import java.util.List;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.dtv.oss.dto.CustomerProductDTO;
import com.dtv.oss.service.listhandler.ValueListHandler;
import com.dtv.oss.service.dao.GenericDAO;
import com.dtv.oss.util.DtoFiller;

/**
 *  DAO class to contact with DB and do JDBC job
 */
public class CustomerProductDAO extends GenericDAO {
	public CustomerProductDAO() {
	}
	
	public CustomerProductDAO(ValueListHandler listHandler) {
		super(listHandler);
	}

   protected List prepareResult(ResultSet rs) throws SQLException {
        ArrayList list = new ArrayList();
        while(rs.next()) {
			CustomerProductDTO dto =DtoFiller.fillCustomerProductDTO(rs);
            list.add(dto);
        }
        return list;
    }
}