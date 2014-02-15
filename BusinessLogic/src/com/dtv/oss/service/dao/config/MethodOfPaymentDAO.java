package com.dtv.oss.service.dao.config;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.dtv.oss.dto.MethodOfPaymentDTO;
import com.dtv.oss.service.dao.GenericDAO;
import com.dtv.oss.util.DtoFiller;

/**
 *  author :Chen jiang
 */
public class MethodOfPaymentDAO extends GenericDAO {

    public MethodOfPaymentDAO() {
    }

    protected List prepareResult(ResultSet rs) throws SQLException {
        ArrayList list = new ArrayList();
        while(rs.next()) {
        	MethodOfPaymentDTO dto = DtoFiller.fillMethodOfPaymentDTO(rs);		
            list.add(dto);		
        }
        return list;
    }
}