package com.dtv.oss.service.dao.csr;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.dtv.oss.dto.ProductPackageDTO;
import com.dtv.oss.service.dao.GenericDAO;
import com.dtv.oss.util.DtoFiller;

public class ProductPackageDAO extends GenericDAO {

	protected List prepareResult(ResultSet rs) throws SQLException {
		ArrayList list=new ArrayList();
		while(rs.next()){
			ProductPackageDTO dto=DtoFiller.fillProductPackageDTO(rs);
			list.add(dto);			
		}
		return list;
	}
}