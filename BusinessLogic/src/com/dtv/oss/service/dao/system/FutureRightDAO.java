package com.dtv.oss.service.dao.system;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.dtv.oss.dto.CPCampaignMapDTO;
import com.dtv.oss.dto.FutureRightDTO;
import com.dtv.oss.service.dao.GenericDAO;
import com.dtv.oss.util.DtoFiller;

public class FutureRightDAO extends GenericDAO {

    public FutureRightDAO(){
    	
    }
	
    protected List prepareResult(ResultSet rs) throws SQLException {
    	ArrayList list = new ArrayList();
		while (rs.next()) {
			FutureRightDTO dto = DtoFiller.fillFutureRightDTO(rs);
			list.add(dto);
		}
		return list;
    }

}
