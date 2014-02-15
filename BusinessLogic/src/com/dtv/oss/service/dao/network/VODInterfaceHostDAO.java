package com.dtv.oss.service.dao.network;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.dtv.oss.dto.VODInterfaceHostDTO;
import com.dtv.oss.service.dao.GenericDAO;
import com.dtv.oss.util.DtoFiller;

/**
 *  author :Chen jiang
 */
public class VODInterfaceHostDAO extends GenericDAO {

    public VODInterfaceHostDAO() {
    }

    protected List prepareResult(ResultSet rs) throws SQLException {
        ArrayList list = new ArrayList();
        while(rs.next()) {
        	VODInterfaceHostDTO dto = DtoFiller.fillVODInterfaceHostDTO(rs);		
            list.add(dto);		
        }
        return list;
    }
}