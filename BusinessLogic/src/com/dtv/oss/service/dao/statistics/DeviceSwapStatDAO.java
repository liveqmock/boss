package com.dtv.oss.service.dao.statistics;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.dtv.oss.dto.stat.DeviceSwapStatDTO;
import com.dtv.oss.service.dao.GenericDAO;
import com.dtv.oss.service.util.CommonConstDefinition;

public class DeviceSwapStatDAO  extends GenericDAO {
	public DeviceSwapStatDAO(){
	}
	protected List prepareResult(ResultSet rs) throws SQLException{
		Map map = new LinkedHashMap();
		while(rs.next()){
			DeviceSwapStatDTO dto = new DeviceSwapStatDTO();
			dto.setId(rs.getInt("id"));
			dto.setCustomername(rs.getString("customername"));
			dto.setCreatereason(rs.getString("createreason"));
			dto.setOlddevicemodel(rs.getString("olddevicemodel"));
			dto.setOlddeviceid(rs.getString("olddeviceid"));
			dto.setDevicemodel(rs.getString("devicemodel"));
			dto.setDeviceid(rs.getString("deviceid"));
			dto.setValue(rs.getDouble("value"));
			dto.setOperatorname(rs.getString("operatorname"));
			dto.setWorkdate(rs.getTimestamp("workdate"));
			dto.setCreatetime(rs.getTimestamp("createtime"));
			
		    if(map.containsKey(new Integer(dto.getId())))
		    	dto = (DeviceSwapStatDTO)map.get(new Integer(dto.getId()));
		 	else
		        map.put(new Integer(dto.getId()), dto);
		    
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
