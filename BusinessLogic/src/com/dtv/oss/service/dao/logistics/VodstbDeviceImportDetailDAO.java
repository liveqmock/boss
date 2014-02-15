package com.dtv.oss.service.dao.logistics;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.dtv.oss.dto.VodstbDeviceImportDetailDTO;
import com.dtv.oss.service.dao.GenericDAO;
import com.dtv.oss.util.DtoFiller;

public class VodstbDeviceImportDetailDAO extends GenericDAO {

	  protected List prepareResult(ResultSet rs) throws SQLException {
	  ArrayList list = new ArrayList();
	  while(rs.next()) {

		  VodstbDeviceImportDetailDTO dto = new VodstbDeviceImportDetailDTO();
	                  dto=DtoFiller.fillVodstbDeviceImportDetailDTO(rs);
	                  list.add(dto);
	  }
	  return list;
	}

	}