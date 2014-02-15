package com.dtv.oss.service.dao.config;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.dtv.oss.dto.UserPointsExchangeRuleDTO;
import com.dtv.oss.service.dao.GenericDAO;
import com.dtv.oss.util.DtoFiller;

/**
 *  author :Chen jiang
 */
public class PointsRuleDAO extends GenericDAO {

    public PointsRuleDAO() {
    }

    protected List prepareResult(ResultSet rs) throws SQLException {
        ArrayList list = new ArrayList();
        while(rs.next()) {
        	UserPointsExchangeRuleDTO dto = DtoFiller.fillUserPointsExchangeRuleDTO(rs);		
            list.add(dto);		
        }
        return list;
    }
}