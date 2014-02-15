package com.dtv.oss.service.dao.system;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.dtv.oss.dto.SystemEventDTO;
import com.dtv.oss.service.dao.GenericDAO;
import com.dtv.oss.util.DtoFiller;

/**
 * <p>Title: BOSS_P5 for SCND</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: SHDV</p>
 * @author chen jiang
 * @version 1.0
 */

public class SytemEventDAO extends GenericDAO {
  protected List prepareResult(ResultSet rs) throws SQLException {
     
        ArrayList list = new ArrayList();
         while(rs.next()) {
         	SystemEventDTO dto = DtoFiller.fillSystemEventDTO(rs);
            list.add(dto);
        }
        return list;
    }
}
    

 