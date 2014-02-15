package com.dtv.oss.service.dao.statistics;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.dtv.oss.dto.stat.CustomerProblemSumStatDTO;
import com.dtv.oss.service.dao.GenericDAO;
import com.dtv.oss.service.util.CommonConstDefinition;

public class CustomerProblemSumStatDAO extends GenericDAO {
	public CustomerProblemSumStatDAO(){
	}
	protected List prepareResult(ResultSet rs) throws SQLException{
		Map map = new LinkedHashMap();
		while(rs.next()){
			CustomerProblemSumStatDTO dto = new CustomerProblemSumStatDTO();
			dto.setOperatorID(rs.getInt("OperatorID"));
			dto.setName(rs.getString("OperatorName"));
			String type = rs.getString("ProblemCategory");
			if (type == null || "".equals(type)) continue;
			int actionNum = rs.getInt("curCount");
			
		    if(map.containsKey(new Integer(dto.getOperatorID()))) 
		    	dto = (CustomerProblemSumStatDTO)map.get(new Integer(dto.getOperatorID()));
		 	else
		        map.put(new Integer(dto.getOperatorID()), dto);
		    
	    	if(type.equals(CommonConstDefinition.CPPROBLEMCATEGORY_NORMAL))
				dto.setCatNormalNum(actionNum);
			else if(type.equals(CommonConstDefinition.CPPROBLEMCATEGORY_SELF_INSTALL_FAIL))
				dto.setCatSelfInstallNum(actionNum);
			else if(type.equals(CommonConstDefinition.CPPROBLEMCATEGORY_INSTALL_FAIL))
				dto.setCatInOneWeekNum(actionNum);		    	
		}

		return new ArrayList(map.values());
	}
}
