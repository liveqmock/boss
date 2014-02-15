package com.dtv.oss.service.dao.network;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.dtv.oss.dto.LdapEventCmdMapDTO;
import com.dtv.oss.service.dao.GenericDAO;
import com.dtv.oss.util.DtoFiller;
/**
 * LdapEventCmdMapDAO
 * @author chen jiang
 *
 */
public class LdapEventCmdMapDAO  extends GenericDAO {

	protected List prepareResult(ResultSet rs) throws SQLException {
	       ArrayList list = new ArrayList();
	        while(rs.next()) {
	        	LdapEventCmdMapDTO dto = DtoFiller.fillLdapEventCmdMapDTO(rs);
	            list.add(dto);		
	        }
	        return list;
	}

}
