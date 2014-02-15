package com.dtv.oss.service.dao.network;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.dtv.oss.dto.CAProductDefDTO;
import com.dtv.oss.service.dao.GenericDAO;
import com.dtv.oss.util.DtoFiller;
/**
 * 设备管理配置
 * @author 260327h
 *
 */
public class CaProductDefDAO  extends GenericDAO {

	protected List prepareResult(ResultSet rs) throws SQLException {
	       ArrayList list = new ArrayList();
	        while(rs.next()) {
	        	CAProductDefDTO dto = DtoFiller.fillCAProductDefDTO(rs);
	            list.add(dto);		
	        }
	        return list;
	}

}
