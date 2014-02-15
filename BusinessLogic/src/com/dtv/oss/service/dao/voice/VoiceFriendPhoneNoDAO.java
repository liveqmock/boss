package com.dtv.oss.service.dao.voice;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.dtv.oss.dto.VoiceFriendPhoneNoDTO;
import com.dtv.oss.service.dao.GenericDAO;
import com.dtv.oss.util.DtoFiller;

/**
 * @author fiona
 * @version 1.0
 */

public class VoiceFriendPhoneNoDAO extends GenericDAO {
  protected List prepareResult(ResultSet rs) throws SQLException {
     
        ArrayList list = new ArrayList();
         while(rs.next()) {
        	 VoiceFriendPhoneNoDTO dto = DtoFiller.fillVoiceFriendPhoneNoDTO(rs);
             list.add(dto);
        }
        return list;
    }
}
    

 