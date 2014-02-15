package com.dtv.oss.service.dao.csr;
/*
 * Created on 2004-8-9
 * @author Mac Wang
 */

import java.util.ArrayList;
import java.util.List;
import java.sql.ResultSet;
import java.sql.SQLException;

 
import com.dtv.oss.service.dao.GenericDAO;
import com.dtv.oss.util.DtoFiller;
import com.dtv.oss.dto.*;
import com.dtv.oss.dto.wrap.customer.Account2AddressWrap;

/**
 *  DAO class to contact with DB and do JDBC job
 */
public class AccountDAO extends GenericDAO {

    public AccountDAO() {
    }

    protected List prepareResult(ResultSet rs) throws SQLException {
        ArrayList list = new ArrayList();
        while(rs.next()) {
            int i = 2;
            Account2AddressWrap wrap = new  Account2AddressWrap();
			AccountDTO dto = new AccountDTO();
			dto = DtoFiller.fillAccountDTO(rs);
			AddressDTO addrDto = new AddressDTO();
			addrDto = DtoFiller.fillAddressDTO(rs,"addr_");
			
			wrap.setAddrDto(addrDto);
			wrap.setAcctDto(dto);
			list.add(wrap);
        }
        return list;
    }
}