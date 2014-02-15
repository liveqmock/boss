/*
 * Created on 2006-2-15
 *
 * @author Stone Liang
 */
package com.dtv.oss.service.dao.statistics;

import java.util.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.dtv.oss.dto.stat.CommonStatDTO;
import com.dtv.oss.service.dao.GenericDAO;

public class FeeStatDAO extends GenericDAO {


	public FeeStatDAO(){
	}

	protected List prepareResult(ResultSet rs) throws SQLException{
		Map map = new LinkedHashMap();
		while(rs.next()){
			CommonStatDTO dto = new CommonStatDTO();
			dto.setId( String.valueOf(rs.getInt("id")));
			dto.setName( rs.getString("name"));
			if(map.containsKey(dto.getId())){
		    	dto = (CommonStatDTO)map.get(dto.getId());
		 	} 
			else{
		 		map.put(dto.getId(), dto);
		    }
			HashMap mapValue = dto.getKeyValue();
		 	mapValue.put( rs.getString("feetype"), rs.getString ("amount"));
		 	dto.setKeyValue( mapValue);

		}	
		return new ArrayList(map.values());
	}
}
