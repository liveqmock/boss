package com.dtv.oss.service.dao.csr;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.dtv.oss.dto.*;
import com.dtv.oss.service.dao.GenericDAO;
import com.dtv.oss.dto.wrap.customer.Customer2AddressWrap;
import com.dtv.oss.util.*;

/**
 *  DAO class to contact with DB and do JDBC job
 */
public class OneCustomerDAO extends GenericDAO {

    public OneCustomerDAO() {
    }

    protected List prepareResult(ResultSet rs) throws SQLException {
        ArrayList list = new ArrayList();
        Customer2AddressWrap wrap = new Customer2AddressWrap();
        CustomerDTO dto;
        AddressDTO addrDto;
        Map map = new HashMap();
        int i=0;
        while(rs.next()) {
        	i = i + 1;
            if(i == 1)
            {
            	dto = DtoFiller.fillCustomerDTO(rs);
            	wrap.setCustDto(dto);
             	addrDto = DtoFiller.fillAddressDTO(rs,"ADDR_");
            	wrap.setAddrDto(addrDto);
            }
            int accountID=rs.getInt("AccountID" );
            String accountName=rs.getString("AccountName");
            if(accountName!=null&&!accountName.equals("")){
    			map.put("" + accountID, accountName);
            }else{
            	map.put("" + accountID, "" + accountID);
            }
//			map.put("" + rs.getInt("AccountID" ), "" + rs.getInt("AccountID" ) + "£º" + rs.getString("AccountName" ));
        }
        wrap.setMarkInfoMap( map);
        if(i>=1)
        	list.add(wrap);
        return list;
    }
}