package com.dtv.oss.service.dao.statistics;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Collections;

import com.dtv.oss.dto.stat.CommonStatDTO;
import com.dtv.oss.service.dao.GenericDAO;
 

/**
 * 通用统计DAO
 * 用于多维统计(三维)
 * subName 为第二维
 * secSubName 为第三维
 * author     ：
 * date       : 2007-1-10
 * description:
 * @author 250713z
 *
 */
public class CommonStatForMultiDAO extends GenericDAO {

	protected List prepareResult(ResultSet rs) throws SQLException {
		Map map = new LinkedHashMap();
		
		while(rs.next()){
			CommonStatDTO dto = new CommonStatDTO();
			dto.setId(rs.getString("ID"));
			dto.setName(rs.getString("name"));
			if(map.containsKey(dto.getId())){
		    	dto = (CommonStatDTO)map.get(dto.getId());
		 	} 
			else{
		 		map.put(dto.getId(), dto);
		    }
			String subName = rs.getString("subName");
			String secSubName = rs.getString("secSubName");
			HashMap mapValue = dto.getKeyValue();
			HashMap mapValueMulti = (HashMap)mapValue.get(subName);
			if(mapValueMulti == null){
				mapValueMulti = new HashMap();
				mapValue.put(subName, mapValueMulti);
			}
			mapValueMulti.put(secSubName, String.valueOf(rs.getDouble("amount")));
		}	
		return new ArrayList(map.values());
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
//		// TODO Auto-generated method stub
//		Map a = new HashMap();
//		a.put("1", "a");
//		a.put("2", "b");
//		a.put("3", "c");
//		
//		Object b[]= a.keySet().toArray();
//
//List cc = new ArrayList();
//
//Collections.reverse(cc);
//
//
//		for(int i=0;i<b.length;i++)
//		{
//			System.out.println(b[i]);
//		}

	}

}
