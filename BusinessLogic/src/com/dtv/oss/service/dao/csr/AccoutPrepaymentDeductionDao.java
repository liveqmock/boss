package com.dtv.oss.service.dao.csr;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.dtv.oss.dto.PrepaymentDeductionRecordDTO;
import com.dtv.oss.service.dao.GenericDAO;
import com.dtv.oss.util.DtoFiller;

public class AccoutPrepaymentDeductionDao extends GenericDAO {
    public AccoutPrepaymentDeductionDao() {
    }
    protected List prepareResult(ResultSet rs) throws SQLException {
        ArrayList list = new ArrayList();
        while(rs.next()) {
        	PrepaymentDeductionRecordDTO dto =DtoFiller.fillPrepaymentDeductionRecordDTO(rs);
            list.add(dto);
        }
        return list;
    }
}
