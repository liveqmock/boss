package com.dtv.oss.service.dao.csr;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.dtv.oss.dto.AddressDTO;
import com.dtv.oss.dto.CustServiceInteractionDTO;
import com.dtv.oss.dto.NewCustAccountInfoDTO;
import com.dtv.oss.dto.NewCustomerInfoDTO;
import com.dtv.oss.dto.wrap.customer.CustServiceInteractionWrap;
import com.dtv.oss.service.dao.GenericDAO;
import com.dtv.oss.util.DtoFiller;

/**
 * @author david.Yang
 * Created on 2005-10-19
 */
public class CustServiceInteractionDAO extends GenericDAO {
   
	protected List prepareResult(ResultSet rs) throws SQLException {
		ArrayList list = new ArrayList();
		
		while(rs.next()) {
		   CustServiceInteractionWrap wrap =new CustServiceInteractionWrap();
		   CustServiceInteractionDTO csidto =DtoFiller.fillCustServiceInteractionDTO(rs);
		   AddressDTO addressDto =DtoFiller.fillAddressDTO(rs,"ADDR_");
		   AddressDTO acctAddrDto =DtoFiller.fillAddressDTO(rs,"ADDR2_");
		   NewCustomerInfoDTO newCustomerInfoDto =DtoFiller.fillNewCustomerInfoDTO(rs,"nci_");
		   NewCustAccountInfoDTO newCustomerAccountInfoDto =DtoFiller.fillNewCustAccountInfoDTO(rs,"ncai_");
		   wrap.setCustAddrDto(addressDto);
		   wrap.setAcctAddrDto(acctAddrDto);
		   wrap.setCsiDto(csidto);
		   wrap.setNcaiDto(newCustomerAccountInfoDto);
		   wrap.setNciDto(newCustomerInfoDto);
		   list.add(wrap);
		}
	    return list;
    }

}
