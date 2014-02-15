package com.dtv.oss.service.dao.network;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.dtv.oss.dto.LdapProductDTO;
import com.dtv.oss.service.dao.GenericDAO;
import com.dtv.oss.util.DtoFiller;
/**
 * LDAPProductDao
 * @author chen jiang
 *
 */
public class LdapProductDAO  extends GenericDAO {

	protected List prepareResult(ResultSet rs) throws SQLException {
	       ArrayList list = new ArrayList();
	        while(rs.next()) {
	        	LdapProductDTO dto = DtoFiller.fillLdapProductDTO(rs);
	            list.add(dto);		
	        }
	        return list;
	}

}
