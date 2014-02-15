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
public class CustomerDAO extends GenericDAO {

    public CustomerDAO() {
    }

    protected List prepareResult(ResultSet rs) throws SQLException {
        ArrayList list = new ArrayList();
        
        while(rs.next()) {
            int i = 2;
            Customer2AddressWrap wrap = new Customer2AddressWrap();
            CustomerDTO dto = DtoFiller.fillCustomerDTO(rs);
			AddressDTO addrDto = DtoFiller.fillAddressDTO(rs,"ADDR_");
			wrap.setCustDto(dto);
			wrap.setAddrDto(addrDto);
            list.add(wrap);
            	
        }
        return list;
    }
}