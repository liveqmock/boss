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
public class FailedIntallationStatDAO extends GenericDAO {

	protected List prepareResult(ResultSet rs) throws SQLException {
		Map map = new LinkedHashMap();
		
		while(rs.next()){
			CommonStatDTO dto = new CommonStatDTO();
			dto.setId( rs.getString("ID"));
			dto.setName( rs.getString("name"));
			if(map.containsKey(dto.getId())){
		    	dto = (CommonStatDTO)map.get(dto.getId());
		 	} 
			else{
		 		map.put(dto.getId(), dto);
		    }
			HashMap mapValue = dto.getKeyValue();
			//2007-7-3 begin
//			if(mapValue.containsKey(rs.getString("prefix") + rs.getString("subName"))){
//				double newValue=double.parsedouble((String)mapValue.get(rs.getString("prefix") + rs.getString("subName")));
//				mapValue.remove(rs.getString("prefix") + rs.getString("subName"));
//				newValue=newValue + rs.getdouble("amount");
//				mapValue.put(rs.getString("prefix") + rs.getString("subName"), String.valueOf(newValue));
//			}
//			else
//				mapValue.put(rs.getString("prefix") + rs.getString("subName"), String.valueOf(rs.getdouble ("amount")));
//			2007-7-3 end
			//2007-7-3 begin
			if(mapValue.containsKey(rs.getString("subName"))){
				double newValue=Double.parseDouble((String)mapValue.get(rs.getString("subName")));
				mapValue.remove(rs.getString("subName"));
				newValue=newValue + rs.getDouble("amount");
				mapValue.put(rs.getString("subName"), String.valueOf(newValue));
			}
			else
				mapValue.put(rs.getString("subName"), String.valueOf(rs.getDouble ("amount")));
			//2007-7-3  end
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
