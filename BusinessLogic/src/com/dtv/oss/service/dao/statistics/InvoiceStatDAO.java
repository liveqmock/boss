/*
 * Created on 2008-1-8
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
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
 * @author 260904l
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class InvoiceStatDAO extends GenericDAO{
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

				mapValue.put(rs.getString("subName"), String.valueOf(rs.getDouble("amount"))+"/"+String.valueOf(rs.getDouble("secSubName")));
				dto.setKeyValue( mapValue);
			}	
			return new ArrayList(map.values());
	}
}
