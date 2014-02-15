/**
 *
 * <p>Title: BOSS 5</p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: Shanghai DigiVision Technology Co. Ltd</p>
 * @author Stone Liang

 */

package com.dtv.oss.service.dao.csr;

import java.util.ArrayList;
import java.util.List;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.dtv.oss.dto.ResourcePhoneNoDTO;
import com.dtv.oss.service.dao.GenericDAO;

import com.dtv.oss.util.DtoFiller;

/**
 *  DAO class to contact with DB and do JDBC job
 */
public class PhoneNoDAO extends GenericDAO {

    public PhoneNoDAO() {
    }

    protected List prepareResult(ResultSet rs) throws SQLException {
        ArrayList list = new ArrayList();
        while(rs.next()) {
        	ResourcePhoneNoDTO dto = DtoFiller.fillResourcePhoneNoDTO(rs,"");
            list.add(dto);         	
        }
        return list;
    }
}