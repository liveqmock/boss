package com.dtv.oss.service.dao.csr;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.dtv.oss.dto.AddressDTO;
import com.dtv.oss.dto.CustServiceInteractionDTO;
import com.dtv.oss.dto.NewCustomerInfoDTO;
import com.dtv.oss.dto.wrap.customer.CustServiceInteractionWrap;
import com.dtv.oss.service.dao.GenericDAO;
import com.dtv.oss.util.DtoFiller;

/**
 *  * @author david.Yang
 */
public class AgentServiceInteractionDAO extends GenericDAO {

    public AgentServiceInteractionDAO() {
    }

	protected List prepareResult(ResultSet rs) throws SQLException {
        ArrayList list = new ArrayList();
        while(rs.next()) {
            CustServiceInteractionDTO csidto =DtoFiller.fillCustServiceInteractionDTO(rs);
            NewCustomerInfoDTO ncidto =DtoFiller.fillNewCustomerInfoDTO(rs,"nci_");
            AddressDTO addressdto =DtoFiller.fillAddressDTO(rs,"ADDR_");
            CustServiceInteractionWrap wrap =new CustServiceInteractionWrap();
            wrap.setCsiDto(csidto);
            wrap.setNciDto(ncidto);
            wrap.setCustAddrDto(addressdto);
            list.add(wrap);           	
        }
        return list;
    }
}