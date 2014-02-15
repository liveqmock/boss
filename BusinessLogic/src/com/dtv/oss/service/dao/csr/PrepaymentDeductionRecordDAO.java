package com.dtv.oss.service.dao.csr;


import java.util.ArrayList;
import java.util.List;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.dtv.oss.service.dao.GenericDAO;
import com.dtv.oss.util.DtoFiller;
import com.dtv.oss.dto.PrepaymentDeductionRecordDTO;

/**
 * Ô¤´æµÖ¿Û¼ÇÂ¼²éÑ¯DAO
 * author     £ºJason.Zhou 
 * date       : 2006-2-14
 * description:
 * @author 250713z
 *
 */
public class PrepaymentDeductionRecordDAO extends GenericDAO {

	public PrepaymentDeductionRecordDAO() {
		super();
	}

	protected List prepareResult(ResultSet rs) throws SQLException {
		ArrayList list = new ArrayList();
		while (rs.next()) {
			PrepaymentDeductionRecordDTO dto = DtoFiller.fillPrepaymentDeductionRecordDTO(rs);			
			list.add(dto);
		}
		return list;
	}
}