package com.dtv.oss.service.dao.csr;

import java.util.ArrayList;
import java.util.List;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.dtv.oss.dto.InvoiceDTO;
import com.dtv.oss.service.dao.GenericDAO;
import com.dtv.oss.util.DtoFiller;

/**
 *  author :david.Yang
 */
public class InvoiceDAO extends GenericDAO {

    public InvoiceDAO() {
    }

    protected List prepareResult(ResultSet rs) throws SQLException {
        ArrayList list = new ArrayList();
        while(rs.next()) {
			InvoiceDTO dto = DtoFiller.fillInvoiceDTO(rs);		
            list.add(dto);		
        }
        return list;
    }
}