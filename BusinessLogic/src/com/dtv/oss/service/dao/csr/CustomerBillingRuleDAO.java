/*
 * Created on 2007-6-25
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.dtv.oss.service.dao.csr;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.dtv.oss.dto.CustomerBillingRuleDTO;
import com.dtv.oss.service.dao.GenericDAO;
import com.dtv.oss.util.DtoFiller;

/**
 * @author 260904l
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class CustomerBillingRuleDAO extends GenericDAO{

	public CustomerBillingRuleDAO(){		
	}
	
	protected List prepareResult(ResultSet rs) throws SQLException {
		ArrayList list = new ArrayList();
		while(rs.next()) {			
			CustomerBillingRuleDTO dto = new CustomerBillingRuleDTO();
			dto = DtoFiller.fillCustomerBillingRuleDTO(rs);			
			list.add(dto);
		}
		return list;
	}

}
