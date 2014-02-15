package com.dtv.oss.service.dao.statistics;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.dtv.oss.dto.stat.DeviceSalesStatDTO;
import com.dtv.oss.service.dao.GenericDAO;

public class DeviceSalesStatDAO   extends GenericDAO {
	public DeviceSalesStatDAO(){
	}
	protected List prepareResult(ResultSet rs) throws SQLException{
		Map map = new LinkedHashMap();
		int i = 1;
		while(rs.next()){
			DeviceSalesStatDTO dto = new DeviceSalesStatDTO();
			dto.setOrgname(rs.getString("orgname"));
			dto.setDevicemodel(rs.getString("devicemodel"));
			dto.setValue(rs.getDouble("value"));
			dto.setAmount(rs.getLong("amount"));
			
			map.put(new Integer(i++), dto);
		    
		    /**
	    	if(type.equals(CommonConstDefinition.CPPROBLEMCATEGORY_NORMAL))
				dto.setCatNormalNum(actionNum);
			else if(type.equals(CommonConstDefinition.CPPROBLEMCATEGORY_SELF_INSTALL_FAIL))
				dto.setCatSelfInstallNum(actionNum);
			else if(type.equals(CommonConstDefinition.CPPROBLEMCATEGORY_INSTALL_FAIL))
				dto.setCatInOneWeekNum(actionNum);	
		     **/	    
		    
		}

		return new ArrayList(map.values());
	}
}
