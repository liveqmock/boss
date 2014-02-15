package com.dtv.oss.service.dao.statistics;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.dtv.oss.dto.stat.BookInteractionSumStatDTO;
import com.dtv.oss.service.dao.GenericDAO;
import com.dtv.oss.service.util.CommonConstDefinition;

public class BookInteractionSumStatDAO extends GenericDAO {
	public BookInteractionSumStatDAO(){
	}

	protected List prepareResult(ResultSet rs) throws SQLException{
		Map map = new LinkedHashMap();
		
		while(rs.next()){
			BookInteractionSumStatDTO dto = new BookInteractionSumStatDTO();
			dto.setOperatorID(rs.getInt("OperatorID"));
			dto.setName(rs.getString("OperatorName"));
			String type = rs.getString("status");
			String installType = rs.getString("installationtype");
			if (type == null || "".equals(type)) continue;
			int actionNum = rs.getInt("curCount");
			
		    if(map.containsKey(new Integer(dto.getOperatorID()))) {
		    	dto = (BookInteractionSumStatDTO)map.get(new Integer(dto.getOperatorID()));
		 	} else{
		        map.put(new Integer(dto.getOperatorID()), dto);
		    }
		    
	    	if(type.equals(CommonConstDefinition.CUSTSERVICEINTERACTION_STATUS_SUCCESS)){
				dto.setSuccessNum(dto.getSuccessNum()+actionNum);
			}
	    	if(type.equals(CommonConstDefinition.CUSTSERVICEINTERACTION_STATUS_FAIL)){
	    		dto.setFailNum(dto.getFailNum()+actionNum);
	    	}
	    	if(type.equals(CommonConstDefinition.CUSTSERVICEINTERACTION_STATUS_INSTALLCANCEL)){
	    		dto.setC2Num(dto.getC2Num()+actionNum);
	    	}
	    	if(type.equals(CommonConstDefinition.CUSTSERVICEINTERACTION_STATUS_BOOKINGCANCEL)){
	    		dto.setC1Num(dto.getC1Num()+actionNum);
	    	}
	    	if(type.equals(CommonConstDefinition.CUSTSERVICEINTERACTION_STATUS_PROCESS)){
	    		dto.setProcessNum(dto.getProcessNum()+actionNum);
	    	}
	    	if(type.equals(CommonConstDefinition.CUSTSERVICEINTERACTION_STATUS_WAIT)){
	    		dto.setWaitNum(dto.getWaitNum()+actionNum);
	    	}
	    	if (installType.equals(CommonConstDefinition.CUSTSERVICEINTERACTIONIN_STYPE_INSTALL)){
	    		dto.setInstallNum(dto.getInstallNum()+actionNum);
	    	}
			dto.setTotalNum(dto.getTotalNum()+actionNum);
		}

		return new ArrayList(map.values());
	}
}
