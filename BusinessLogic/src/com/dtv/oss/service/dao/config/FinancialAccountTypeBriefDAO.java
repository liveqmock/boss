package com.dtv.oss.service.dao.config;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.dtv.oss.dto.AcctItemTypeDTO;
import com.dtv.oss.service.dao.GenericDAO;
import com.dtv.oss.util.DtoFiller;

/**
 * author :wanghao
 */
public class FinancialAccountTypeBriefDAO extends GenericDAO {

	public FinancialAccountTypeBriefDAO() {
	}

	protected List prepareResult(ResultSet rs) throws SQLException {
		ArrayList list = new ArrayList();
		while (rs.next()) {
			AcctItemTypeDTO dto = DtoFiller.fillAcctItemTypeDTO(rs);
			list.add(dto);
		}
		return list;
	}
}