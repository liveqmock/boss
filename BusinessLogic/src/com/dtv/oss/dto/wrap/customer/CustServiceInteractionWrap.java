package com.dtv.oss.dto.wrap.customer;

import com.dtv.oss.dto.AddressDTO;
import com.dtv.oss.dto.CustServiceInteractionDTO;
import com.dtv.oss.dto.NewCustAccountInfoDTO;
import com.dtv.oss.dto.NewCustomerInfoDTO;
/**
 * @author david.Yang
 * Created on 2005-10-19
 */
public class CustServiceInteractionWrap implements java.io.Serializable {
	private CustServiceInteractionDTO csiDto;
	private AddressDTO custAddrDto;
	private AddressDTO acctAddrDto;
	private NewCustomerInfoDTO nciDto;
	private NewCustAccountInfoDTO ncaiDto;
	
	public CustServiceInteractionWrap(CustServiceInteractionDTO csiDto, 
			                          AddressDTO custAddrDto, 
			                          AddressDTO acctAddrDto, 
			                          NewCustomerInfoDTO nciDto, 
			                          NewCustAccountInfoDTO ncaiDto) {
           this.csiDto = csiDto;
           this.custAddrDto = custAddrDto;
           this.acctAddrDto = acctAddrDto;
           this.nciDto = nciDto;
           this.ncaiDto = ncaiDto;
    }

    public CustServiceInteractionWrap() {
           this.csiDto = new CustServiceInteractionDTO();
           this.custAddrDto = new AddressDTO();
           this.acctAddrDto = new AddressDTO();
           this.nciDto = new NewCustomerInfoDTO();
           this.ncaiDto = new NewCustAccountInfoDTO();
    }
    
    /**
	 * @return
	 */
	public AddressDTO getAcctAddrDto() {
		return acctAddrDto;
	}

	/**
	 * @return
	 */
	public CustServiceInteractionDTO getCsiDto() {
		return csiDto;
	}

	/**
	 * @return
	 */
	public AddressDTO getCustAddrDto() {
		return custAddrDto;
	}

	/**
	 * @return
	 */
	public NewCustAccountInfoDTO getNcaiDto() {
		return ncaiDto;
	}

	/**
	 * @return
	 */
	public NewCustomerInfoDTO getNciDto() {
		return nciDto;
	}

	/**
	 * @param addressDTO
	 */
	public void setAcctAddrDto(AddressDTO addressDTO) {
		acctAddrDto = addressDTO;
	}

	/**
	 * @param interactionDTO
	 */
	public void setCsiDto(CustServiceInteractionDTO interactionDTO) {
		csiDto = interactionDTO;
	}

	/**
	 * @param addressDTO
	 */
	public void setCustAddrDto(AddressDTO addressDTO) {
		custAddrDto = addressDTO;
	}

	/**
	 * @param infoDTO
	 */
	public void setNcaiDto(NewCustAccountInfoDTO infoDTO) {
		ncaiDto = infoDTO;
	}

	/**
	 * @param infoDTO
	 */
	public void setNciDto(NewCustomerInfoDTO infoDTO) {
		nciDto = infoDTO;
	}

}
