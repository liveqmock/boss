package com.dtv.oss.service.dao.logistics;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.dtv.oss.dto.DeviceTransitionDetailDTO;
import com.dtv.oss.service.dao.GenericDAO;
import com.dtv.oss.util.DtoFiller;

/**
 * <p>Title: BOSS_P5 for SCN</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: SHDV</p>
 * @author chenjiang
 * @version 1.0
 */

public class DeviceTransDetailDAO extends GenericDAO {

  protected List prepareResult(ResultSet rs) throws SQLException {
  ArrayList list = new ArrayList();
  while(rs.next()) {

                  DeviceTransitionDetailDTO dto = new DeviceTransitionDetailDTO();
                  dto=DtoFiller.fillDeviceTransitionDetailDTO(rs);
                  list.add(dto);
  }
  return list;
}

}