package com.dtv.oss.service.dao.system;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

 
import com.dtv.oss.dto.SystemLogDTO;
import com.dtv.oss.service.dao.GenericDAO;
import com.dtv.oss.util.DtoFiller;

import java.util.ArrayList;

/**
 * <p>Title: BOSS_P5 for SCND</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: SHDV</p>
 * @author chen jiang
 * @version 1.0
 */

public class LogDAO extends GenericDAO {
  protected List prepareResult(ResultSet rs) throws SQLException {
     
        ArrayList list = new ArrayList();
         while(rs.next()) {
        	SystemLogDTO dto = DtoFiller.fillSystemLogDTO(rs);
            list.add(dto);
        }
        return list;
    }
}
    

 