package com.dtv.oss.service.dao.network;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.dtv.oss.dto.VODInterfaceProductDTO;
import com.dtv.oss.service.dao.GenericDAO;
import com.dtv.oss.util.DtoFiller;

/**
 *  author :Chen jiang
 */
public class VODInterfaceProductDAO extends GenericDAO {

    public VODInterfaceProductDAO() {
    }

    protected List prepareResult(ResultSet rs) throws SQLException {
        ArrayList list = new ArrayList();
        while(rs.next()) {
        	VODInterfaceProductDTO dto = DtoFiller.fillVODInterfaceProductDTO(rs);		
            list.add(dto);		
        }
        return list;
    }
}