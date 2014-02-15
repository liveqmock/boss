package com.dtv.oss.service.dao.system;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.dtv.oss.dto.CustomerCampaignDTO;
import com.dtv.oss.service.dao.GenericDAO;
import com.dtv.oss.util.DtoFiller;

public class CustCampaignDAO extends GenericDAO {
  protected List prepareResult(ResultSet rs) throws SQLException {
        ArrayList list = new ArrayList();
         while(rs.next()) {
        	CustomerCampaignDTO dto = DtoFiller.fillCustomerCampaignDTO(rs);
            list.add(dto);
        }
        return list;
    }
}
    

 