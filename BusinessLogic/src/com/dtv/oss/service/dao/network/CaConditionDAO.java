package com.dtv.oss.service.dao.network;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.dtv.oss.dto.CAConditionDTO;
import com.dtv.oss.dto.CAParameterDTO;
import com.dtv.oss.service.dao.GenericDAO;
import com.dtv.oss.util.DtoFiller;
/**
 * 
 * @author 260327h
 *
 */
public class CaConditionDAO  extends GenericDAO {

	protected List prepareResult(ResultSet rs) throws SQLException {
	       ArrayList list = new ArrayList();
	        while(rs.next()) {
	        	CAConditionDTO dto = DtoFiller.fillCAConditionDTO(rs);
	            list.add(dto);		
	        }
	        return list;
	}

}
