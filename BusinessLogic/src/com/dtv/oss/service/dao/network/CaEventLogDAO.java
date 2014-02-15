package com.dtv.oss.service.dao.network;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.dtv.oss.dto.CAQueueDTO;
import com.dtv.oss.dto.wrap.CAEventWrap;
 
import com.dtv.oss.service.dao.GenericDAO;
import com.dtv.oss.util.DtoFiller;

/**
 * 
 * @author 260327h
 *
 */
public class CaEventLogDAO extends GenericDAO {

	protected List prepareResult(ResultSet rs) throws SQLException {
		ArrayList list = new ArrayList();
		while (rs.next()) {
			 
			CAEventWrap wrap = new CAEventWrap();
			CAQueueDTO	dto = DtoFiller.fillCAQueueDTO(rs);
			 
			wrap.setQueueDTO(dto); 
			list.add(wrap);
		}
		return list;
	}

}
