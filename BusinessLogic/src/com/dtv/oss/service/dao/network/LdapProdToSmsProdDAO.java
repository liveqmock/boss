package com.dtv.oss.service.dao.network;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.dtv.oss.dto.LdapProdToSmsProdDTO;
import com.dtv.oss.service.dao.GenericDAO;
import com.dtv.oss.util.DtoFiller;
/**
 * LdapProdToSmsProdDAO
 * @author chen jiang
 *
 */
public class LdapProdToSmsProdDAO  extends GenericDAO {

	protected List prepareResult(ResultSet rs) throws SQLException {
	       ArrayList list = new ArrayList();
	        while(rs.next()) {
	        	LdapProdToSmsProdDTO dto = DtoFiller.fillfillLdapProdToSmsProdDTO(rs);
	            list.add(dto);		
	        }
	        return list;
	}

}
