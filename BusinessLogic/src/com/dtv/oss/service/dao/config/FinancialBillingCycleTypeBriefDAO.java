package com.dtv.oss.service.dao.config;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.dtv.oss.dto.BillingCycleTypeDTO;
import com.dtv.oss.service.dao.GenericDAO;
import com.dtv.oss.util.DtoFiller;

/**
 * author :wanghaos
 */
public class FinancialBillingCycleTypeBriefDAO extends GenericDAO {

	public FinancialBillingCycleTypeBriefDAO() {
	}

	protected List prepareResult(ResultSet rs) throws SQLException {
		ArrayList list = new ArrayList();
		while (rs.next()) {
			BillingCycleTypeDTO dto = DtoFiller.fillBillingCycleTypeDTO(rs);
			list.add(dto);
		}
		return list;
	}
}