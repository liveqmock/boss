package com.dtv.oss.service.dao.csr;

import java.util.ArrayList;
import java.util.List;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.dtv.oss.dto.*;
import com.dtv.oss.service.dao.GenericDAO;
import com.dtv.oss.dto.wrap.customer.CustomerSAWrap;

/**
 *  DAO class to contact with DB and do JDBC job
 */
public class CustomerPhoneNoDAO extends GenericDAO {

    public CustomerPhoneNoDAO() {
    }

    protected List prepareResult(ResultSet rs) throws SQLException {
        ArrayList list = new ArrayList();
        
        while(rs.next()) {
            int i = 2;
            CustomerSAWrap wrap = new CustomerSAWrap();
            CustomerDTO dto = new CustomerDTO();
            ServiceAccountDTO saDto = new ServiceAccountDTO();
            
            dto.setCustomerID(rs.getInt("CustomerID"));
            dto.setName(rs.getString("name"));
            saDto.setServiceAccountID(rs.getInt("serviceAccountID"));
            saDto.setStatus(rs.getString("status"));
            saDto.setServiceCode(rs.getString("serviceCode"));
            
			wrap.setCustDto(dto);
			wrap.setSaDto(saDto);
            list.add(wrap);
            	
        }
        return list;
    }
}