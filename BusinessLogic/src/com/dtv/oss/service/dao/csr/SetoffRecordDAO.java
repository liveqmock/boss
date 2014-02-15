package com.dtv.oss.service.dao.csr;

import java.util.ArrayList;
import java.util.List;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.dtv.oss.service.dao.GenericDAO;
import com.dtv.oss.util.DtoFiller;
import com.dtv.oss.dto.FinanceSetOffMapDTO;
import com.dtv.oss.dto.wrap.customer.FinanceSetoffMap2AcctItemTypeWrap;

/**
 * Ô¤´æµÖ¿Û¼ÇÂ¼²éÑ¯DAO
 * author     £ºJason.Zhou 
 * date       : 2006-2-14
 * description:
 * @author 250713z
 *
 */
public class SetoffRecordDAO extends GenericDAO {

	public SetoffRecordDAO() {
		super();
	}

	protected List prepareResult(ResultSet rs) throws SQLException {
		ArrayList list = new ArrayList();
		
		while (rs.next()) {
			FinanceSetoffMap2AcctItemTypeWrap rsDTO=new FinanceSetoffMap2AcctItemTypeWrap();
			
			FinanceSetOffMapDTO dto = DtoFiller.fillFinanceSetOffMapDTO(rs);
			rsDTO.setSetOffDTO(dto);
			
			rsDTO.setFeeAcctItemType(rs.getString("feeAcctItemType"));
			rsDTO.setFeeAmount(rs.getDouble("feeAmount"));
			rsDTO.setFeeTime(rs.getTimestamp("feeTime"));
			
			rsDTO.setPaymentAmount(rs.getDouble("paymentAmount"));
			rsDTO.setPaymentTime(rs.getTimestamp("paymentTime"));
			
			if("SET_F_PREPAYMENTTYPE".equals(rs.getString("payCommonSettingValue"))){
				rsDTO.setPaymentCommonSettingName("SET_F_PREPAYMENTTYPE");
				rsDTO.setPayCommonSettingValue(rs.getString("payAcctItemType"));
			}
			else{
				rsDTO.setPayAcctItemType(rs.getString("payAcctItemType"));
			}

			list.add(rsDTO);
		}
		return list;
	}
}