package com.dtv.oss.service.dao.statistics;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.dtv.oss.dto.stat.DeviceDaySalesStatDTO;
import com.dtv.oss.service.dao.GenericDAO;

public class DeviceDaySalesStatDAO extends GenericDAO {
	public DeviceDaySalesStatDAO(){
	}
	protected List prepareResult(ResultSet rs) throws SQLException{
		Map map = new LinkedHashMap();
		int i = 1;
		while(rs.next()){
			DeviceDaySalesStatDTO dto = new DeviceDaySalesStatDTO();
			dto.setBatchNo(rs.getString("batchNo"));
			dto.setFilldate(rs.getString("filldate"));
			dto.setBelongName(rs.getString("belongName"));
			
			dto.setSxgq_2860(rs.getLong("sxgq_2860"));
			dto.setChgq_1888(rs.getLong("chgq_1888"));
			dto.setChgq_2360(rs.getLong("chgq_2360"));
			dto.setChbq_666(rs.getLong("chbq_666"));
			dto.setChbq_598(rs.getLong("chbq_598"));
			
			dto.setBzbq_666(rs.getLong("bzbq_666"));
			dto.setBzbq_598(rs.getLong("bzbq_598"));
			dto.setHwbq_666(rs.getLong("hwbq_666"));
			dto.setHwbq_598(rs.getLong("hwbq_598"));
			dto.setZnkbk(rs.getLong("znkbk"));
			
			dto.setZxpkbq(rs.getLong("zxpkbq"));
			dto.setZxpkgq(rs.getLong("zxpkgq"));
			dto.setTotalmoney(rs.getLong("totalmoney"));
			
			map.put(new Integer(i++), dto);
		}

		return new ArrayList(map.values());
	}
}