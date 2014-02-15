package com.dtv.oss.service.dao.config;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.dtv.oss.dto.FiberTransmitterDTO;
import com.dtv.oss.service.dao.GenericDAO;
import com.dtv.oss.util.DtoFiller;

/**
 *  author :Chen jiang
 */
public class FiberTransmitterDAO extends GenericDAO {

    public FiberTransmitterDAO() {
    }

    protected List prepareResult(ResultSet rs) throws SQLException {
        ArrayList list = new ArrayList();
        while(rs.next()) {
        	FiberTransmitterDTO dto = DtoFiller.fillFiberTransmitterDTO(rs);		
            list.add(dto);		
        }
        return list;
    }
}