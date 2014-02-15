package com.dtv.oss.service.dao.config;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.dtv.oss.dto.UserPointsCumulatedRuleDTO;
import com.dtv.oss.service.dao.GenericDAO;
import com.dtv.oss.util.DtoFiller;

/**
 *  author :Chen jiang
 */
public class PointsCumulateRuleDAO extends GenericDAO {

    public PointsCumulateRuleDAO() {
    }

    protected List prepareResult(ResultSet rs) throws SQLException {
        ArrayList list = new ArrayList();
        while(rs.next()) {
        	UserPointsCumulatedRuleDTO dto = DtoFiller.fillUserPointsCumulatedRuleDTO(rs);		
            list.add(dto);		
        }
        return list;
    }
}