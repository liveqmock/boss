/**
 *
 * <p>Title: BOSS 2</p>
 * <p>Description: BOSS second iterative development project</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: Shanghai DigiVision Technology Co. Ltd</p>
 * @author Chen jiang
 * @version 1.2
 */

package com.dtv.oss.service.dao.groupcustomer;

import java.util.ArrayList;
import java.util.List;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.dtv.oss.dto.ContractDTO;

import com.dtv.oss.service.dao.GenericDAO;
import com.dtv.oss.util.DtoFiller;

/**
 *  DAO class to contact with DB and do JDBC job
 */
public class ContractDAO extends GenericDAO {

    public ContractDAO() {
    }

    protected List prepareResult(ResultSet rs) throws SQLException {
        ArrayList list = new ArrayList();
        while(rs.next()) {
        	ContractDTO dto = DtoFiller.fillContractDTO(rs);
            list.add(dto);         	
        }
        return list;
    }
}