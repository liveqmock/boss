package com.dtv.oss.service.dao.config;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.dtv.oss.dto.CommonSettingDataDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.dao.GenericDAO;
import com.dtv.oss.util.DtoFiller;

public class ConfigCustomerValueDao extends GenericDAO {
	
	protected List prepareResult(ResultSet rs) throws SQLException {
	       ArrayList list = new ArrayList();
	        while(rs.next()) {
	        	CommonSettingDataDTO dto = DtoFiller.fillCommonSettingDataDTO(rs);
	            list.add(dto);		
	        }
	        LogUtility.log(this.getClass(), LogLevel.DEBUG,
			"≤È—ØDAOµ√µΩlist.size():"+list.size());
	        return list;
	}

}

	 

	 

 
