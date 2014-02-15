package com.dtv.oss.service.dao.config;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.dtv.oss.dto.ProductDependencyDTO;
import com.dtv.oss.service.dao.GenericDAO;
import com.dtv.oss.util.DtoFiller;

public class ProductDependencyDAO extends GenericDAO {
	protected List prepareResult(ResultSet rs) throws SQLException {
	   ArrayList list = new ArrayList();
	   while(rs.next()) {
		   ProductDependencyDTO dto =DtoFiller.fillProductDependencyDTO(rs,"");
		   list.add(dto);
	   }
	   return list;
	}
}
