package com.dtv.oss.service.dao.logistics;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.dtv.oss.dto.TerminalDeviceDTO;
import com.dtv.oss.service.dao.GenericDAO;
import com.dtv.oss.util.DtoFiller;

import java.util.ArrayList;
 

/**
 * <p>Title: BOSS_P5 for SCND</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: SHDV</p>
 * @author Wangchunxia
 * @version 1.0
 */

public class DeviceDAO extends GenericDAO {
  protected List prepareResult(ResultSet rs) throws SQLException {
    ArrayList list = new ArrayList();
    while(rs.next()) {
          TerminalDeviceDTO dto = new TerminalDeviceDTO();
          dto=DtoFiller.fillTerminalDeviceDTO(rs);
          list.add(dto);
    }
    return list;
}

}