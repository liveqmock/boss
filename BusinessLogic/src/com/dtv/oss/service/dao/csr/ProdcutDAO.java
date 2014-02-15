package com.dtv.oss.service.dao.csr;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;

import com.dtv.oss.dto.ProductDTO;
import com.dtv.oss.service.dao.GenericDAO;
import com.dtv.oss.util.DtoFiller;

public class ProdcutDAO extends GenericDAO {

	protected List prepareResult(ResultSet rs) throws SQLException {
		ArrayList list=new ArrayList();
		while(rs.next()){
			ProductDTO dto=DtoFiller.fillProductDTO(rs);
			list.add(dto);
		}
		return list;
	}

}
