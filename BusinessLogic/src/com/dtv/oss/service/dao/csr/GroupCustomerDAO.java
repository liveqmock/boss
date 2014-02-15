package com.dtv.oss.service.dao.csr;

import java.util.ArrayList;
import java.util.List;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.dtv.oss.dto.*;
import com.dtv.oss.service.dao.GenericDAO;
import com.dtv.oss.dto.wrap.customer.Customer2AddressWrap;
import com.dtv.oss.util.*;

/**
 *  DAO class to contact with DB and do JDBC job
 */
public class GroupCustomerDAO extends GenericDAO {

    public GroupCustomerDAO() {
    }

    protected List prepareResult(ResultSet rs) throws SQLException {
        ArrayList list = new ArrayList();
        
        while(rs.next()) {
            CustomerDTO dto = DtoFiller.fillCustomerDTO(rs);
            list.add(dto);
           	
        }
        return list;
    }
}