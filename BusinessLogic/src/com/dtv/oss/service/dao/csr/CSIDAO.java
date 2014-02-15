package com.dtv.oss.service.dao.csr;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.dtv.oss.dto.CustServiceInteractionDTO;
import com.dtv.oss.service.dao.GenericDAO;
import com.dtv.oss.util.DtoFiller;

public class CSIDAO extends GenericDAO{
	public CSIDAO() {
    }

    protected List prepareResult(ResultSet rs) throws SQLException {    	
        ArrayList list = new ArrayList();
        while(rs.next()) {
            CustServiceInteractionDTO dto = new CustServiceInteractionDTO();
            dto = DtoFiller.fillCSIDTO(rs);			
            list.add(dto);            	
        }
        return list;
    }
}
