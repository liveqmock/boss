package com.dtv.oss.service.dao.csr;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;

import com.dtv.oss.dto.PackageLineDTO;
import com.dtv.oss.service.dao.GenericDAO;
import com.dtv.oss.util.DtoFiller;

public class PackageLineDAO extends GenericDAO {

	protected List prepareResult(ResultSet rs) throws SQLException {
		ArrayList list=new ArrayList();
		while(rs.next()){
			PackageLineDTO dto=DtoFiller.fillPackageLineDTO(rs);
			list.add(dto);
		}
		return list;
	}

}
