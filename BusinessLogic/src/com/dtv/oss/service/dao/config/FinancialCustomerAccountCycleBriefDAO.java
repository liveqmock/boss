package com.dtv.oss.service.dao.config;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.dtv.oss.dto.CustAcctCycleCfgDTO;
import com.dtv.oss.service.dao.GenericDAO;
import com.dtv.oss.util.DtoFiller;

/**
 * author :wanghao
 */
public class FinancialCustomerAccountCycleBriefDAO extends GenericDAO {

	public FinancialCustomerAccountCycleBriefDAO() {
	}

	protected List prepareResult(ResultSet rs) throws SQLException {
		ArrayList list = new ArrayList();
		while (rs.next()) {
			CustAcctCycleCfgDTO dto = DtoFiller.fillCustAcctCycleCfgDTO(rs);
			list.add(dto);
		}
		return list;
	}
}