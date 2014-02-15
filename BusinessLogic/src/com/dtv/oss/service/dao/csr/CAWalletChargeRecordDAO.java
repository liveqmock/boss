package com.dtv.oss.service.dao.csr;

import java.util.ArrayList;
import java.util.List;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.dtv.oss.dto.CAWalletChargeRecordDTO;

import com.dtv.oss.service.dao.GenericDAO;
import com.dtv.oss.util.DtoFiller;

public class CAWalletChargeRecordDAO extends GenericDAO {
	public CAWalletChargeRecordDAO() {
    }

    protected List prepareResult(ResultSet rs) throws SQLException {
        ArrayList list = new ArrayList();
        while(rs.next()) {
        	CAWalletChargeRecordDTO dto = DtoFiller.fillCAWalletChargeRecordDTO(rs);
            list.add(dto);         	
        }
        return list;
    }
}
