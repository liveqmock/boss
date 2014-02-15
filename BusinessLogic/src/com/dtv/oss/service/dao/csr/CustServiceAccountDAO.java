/**
 *
 * <p>Title: BOSS 2</p>
 * <p>Description: BOSS second iterative development project</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: Shanghai DigiVision Technology Co. Ltd</p>
 * @author Chen jiang
 * @version 1.2
 */

package com.dtv.oss.service.dao.csr;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.dtv.oss.dto.ServiceAccountDTO;
import com.dtv.oss.service.dao.GenericDAO;
import com.dtv.oss.util.DtoFiller;

/**
 *  DAO class to contact with DB and do JDBC job
 */
public class CustServiceAccountDAO extends GenericDAO{

    public CustServiceAccountDAO() {
    }

    protected List prepareResult(ResultSet rs) throws SQLException {
        ArrayList list = new ArrayList();
        while(rs.next()) {
            ServiceAccountDTO dto = DtoFiller.fillServiceAccountDTO(rs);
            list.add(dto);         	
        }
        return list;
    }
}