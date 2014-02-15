package com.dtv.oss.service.dao.csr;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.dtv.oss.dto.CustomerProductDTO;
import com.dtv.oss.dto.ServiceAccountDTO;
import com.dtv.oss.dto.wrap.customer.ServiceAccountWrap;
import com.dtv.oss.service.dao.GenericDAO;
import com.dtv.oss.util.DtoFiller;

public class ServiceAccountDAO extends GenericDAO {

	protected List prepareResult(ResultSet rs) throws SQLException {
		ArrayList list=new ArrayList();
		while(rs.next()){
			CustomerProductDTO cpDTO = DtoFiller.fillCustomerProductDTO(rs);
			ServiceAccountDTO saDTO = DtoFiller.fillServiceAccountDTO( rs,"sa_");
			ServiceAccountWrap saWrap = new ServiceAccountWrap();
			saWrap.setCpDto(cpDTO);
			saWrap.setSaDto(saDTO);
			list.add(saWrap);			
		}
		return list;
	}
}