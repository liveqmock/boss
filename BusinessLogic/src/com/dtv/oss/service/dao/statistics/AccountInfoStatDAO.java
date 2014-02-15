package com.dtv.oss.service.dao.statistics;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.dtv.oss.dto.stat.CommonStatDTO;
import com.dtv.oss.service.dao.GenericDAO;

/**
 * 通用统计DAO
 * author     ：Jason.Zhou 
 * date       : 2006-3-14
 * description:
 * @author 250713z
 *
 */
public class AccountInfoStatDAO extends GenericDAO {

	protected List prepareResult(ResultSet rs) throws SQLException {
		Map map = new LinkedHashMap();
		
		while(rs.next()){
			CommonStatDTO dto = new CommonStatDTO();
			dto.setId( rs.getString("ID") + rs.getString("bank"));
			dto.setName( rs.getString("name"));
			if(map.containsKey(dto.getId())){
		    	dto = (CommonStatDTO)map.get(dto.getId());
		 	} 
			else{
		 		map.put(dto.getId(), dto);
		    }
			HashMap mapValue = dto.getKeyValue();
			
			mapValue.put(rs.getString("subName"), String.valueOf(rs.getDouble ("amount")));
			
		 	dto.setKeyValue( mapValue);
		}	
		return new ArrayList(map.values());
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
