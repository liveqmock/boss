package com.dtv.oss.service.dao.csr;
/*
 * Created on 2004-8-9 @author Mac Wang
 */

import java.util.ArrayList;
import java.util.List;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.dtv.oss.service.dao.GenericDAO;
import com.dtv.oss.util.DtoFiller;
import com.dtv.oss.dto.PaymentRecordDTO;

/**
 * author:david.yang
 */
public class PaymentRecordDAO extends GenericDAO {

	public PaymentRecordDAO() {
	}

	protected List prepareResult(ResultSet rs) throws SQLException {
		ArrayList list = new ArrayList();
		while (rs.next()) {
			PaymentRecordDTO dto = DtoFiller.fillPaymentRecordDTO(rs);			
			list.add(dto);
		}
		return list;
	}
}