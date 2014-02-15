package com.dtv.oss.service.dao.config;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.dtv.oss.dto.UserPointsExchangeActivityDTO;
import com.dtv.oss.service.dao.GenericDAO;
import com.dtv.oss.util.DtoFiller;

/**
 *  author :Chen jiang
 */
public class PointsActivityDAO extends GenericDAO {

    public PointsActivityDAO() {
    }

    protected List prepareResult(ResultSet rs) throws SQLException {
        ArrayList list = new ArrayList();
        while(rs.next()) {
        	UserPointsExchangeActivityDTO dto = DtoFiller.fillUserPointsExchangeActivityDTO(rs);		
            list.add(dto);		
        }
        return list;
    }
}