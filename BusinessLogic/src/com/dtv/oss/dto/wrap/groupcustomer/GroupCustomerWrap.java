package com.dtv.oss.dto.wrap.groupcustomer;

import java.io.Serializable;

import com.dtv.oss.dto.AddressDTO;
import com.dtv.oss.dto.CustServiceInteractionDTO;
import com.dtv.oss.dto.NewCustAccountInfoDTO;
import com.dtv.oss.dto.NewCustomerInfoDTO;
/**
 * 这个wrap用于集团子客户批量导入 ,封装从文件中解析的数据,一条记录对应一个wrap
 * @author 260327h
 *
 */
public class GroupCustomerWrap implements Serializable {
	private String No=null;
	private NewCustomerInfoDTO newCustomerInfoDTO=null;
	private NewCustAccountInfoDTO newCustAccountInfoDTO =null;
	private AddressDTO addressDTO=null;
	private CustServiceInteractionDTO custServiceInteractionDTO=null;
	
	private int serviceID=0;
	private String sCSerialNo=null;
	private String sTBSerialNo=null;
	private String iPPSerialNo=null;
	private String serviceCode=null;
	private String dataFilePath=null;
	private int customerID=0;
	private int accountID=0;
	private String contractNo=null;
	private String errString=null;
	private int phoneNoItemID=0;
	/**
	 * @return the phoneNoItemID
	 */
	public int getPhoneNoItemID() {
		return phoneNoItemID;
	}
	/**
	 * @param phoneNoItemID the phoneNoItemID to set
	 */
	public void setPhoneNoItemID(int phoneNoItemID) {
		this.phoneNoItemID = phoneNoItemID;
	}
	/**
	 * @return the errString
	 */
	public String getErrString() {
		return errString;
	}
	/**
	 * @param errString the errString to set
	 */
	public void setErrString(String errString) {
		this.errString = errString;
	}
	/**
	 * @return the dataFilePath
	 */
	public String getDataFilePath() {
		return dataFilePath;
	}
	/**
	 * @param dataFilePath the dataFilePath to set
	 */
	public void setDataFilePath(String dataFilePath) {
		this.dataFilePath = dataFilePath;
	}
	/**
	 * @return the newCustAccountInfoDTO
	 */
	public NewCustAccountInfoDTO getNewCustAccountInfoDTO() {
		return newCustAccountInfoDTO;
	}
	/**
	 * @param newCustAccountInfoDTO the newCustAccountInfoDTO to set
	 */
	public void setNewCustAccountInfoDTO(NewCustAccountInfoDTO newCustAccountInfoDTO) {
		this.newCustAccountInfoDTO = newCustAccountInfoDTO;
	}
	/**
	 * @return the newCustomerInfoDTO
	 */
	public NewCustomerInfoDTO getNewCustomerInfoDTO() {
		return newCustomerInfoDTO;
	}
	/**
	 * @param newCustomerInfoDTO the newCustomerInfoDTO to set
	 */
	public void setNewCustomerInfoDTO(NewCustomerInfoDTO newCustomerInfoDTO) {
		this.newCustomerInfoDTO = newCustomerInfoDTO;
	}
	public GroupCustomerWrap(){
		newCustomerInfoDTO=new NewCustomerInfoDTO();
		newCustAccountInfoDTO=new NewCustAccountInfoDTO();
		addressDTO= new AddressDTO();
		custServiceInteractionDTO=new CustServiceInteractionDTO();
	}
	/**
	 * @return the serviceCode
	 */
	public String getServiceCode() {
		return serviceCode;
	}
	/**
	 * @param serviceCode the serviceCode to set
	 */
	public void setServiceCode(String serviceCode) {
		this.serviceCode = serviceCode;
	}
	/**
	 * @return the sCSerialNo
	 */
	public String getSCSerialNo() {
		return sCSerialNo;
	}
	/**
	 * @param serialNo the sCSerialNo to set
	 */
	public void setSCSerialNo(String serialNo) {
		sCSerialNo = serialNo;
	}
	/**
	 * @return the sTBSerialNo
	 */
	public String getSTBSerialNo() {
		return sTBSerialNo;
	}
	/**
	 * @param serialNo the sTBSerialNo to set
	 */
	public void setSTBSerialNo(String serialNo) {
		sTBSerialNo = serialNo;
	}


//	private ContractDTO contractDTO=null;
	
	boolean isValid;
	/**
	 * @return the addressDTO
	 */
	public AddressDTO getAddressDTO() {
		return addressDTO;
	}
	/**
	 * @param addressDTO the addressDTO to set
	 */
	public void setAddressDTO(AddressDTO addressDTO) {
		this.addressDTO = addressDTO;
	}

	/**
	 * @return the isAvail
	 */
	public boolean isValid() {
		return isValid;
	}
	/**
	 * @param isAvail the isAvail to set
	 */
	public void setValid(boolean isAvail) {
		this.isValid = isAvail;
	}

	/**
	 * @return the custServiceInteractionDTO
	 */
	public CustServiceInteractionDTO getCustServiceInteractionDTO() {
		return custServiceInteractionDTO;
	}
	/**
	 * @param custServiceInteractionDTO the custServiceInteractionDTO to set
	 */
	public void setCustServiceInteractionDTO(
			CustServiceInteractionDTO custServiceInteractionDTO) {
		this.custServiceInteractionDTO = custServiceInteractionDTO;
	}
	/**
	 * @return the accountID
	 */
	public int getAccountID() {
		return accountID;
	}
	/**
	 * @param accountID the accountID to set
	 */
	public void setAccountID(int accountID) {
		this.accountID = accountID;
	}
	/**
	 * @return the contractNo
	 */
	public String getContractNo() {
		return contractNo;
	}
	/**
	 * @param contractNo the contractNo to set
	 */
	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}
	/**
	 * @return the customerID
	 */
	public int getCustomerID() {
		return customerID;
	}
	/**
	 * @param customerID the customerID to set
	 */
	public void setCustomerID(int customerID) {
		this.customerID = customerID;
	}
	/**
	 * @return the serviceID
	 */
	public int getServiceID() {
		return serviceID;
	}
	/**
	 * @param serviceID the serviceID to set
	 */
	public void setServiceID(int serviceID) {
		this.serviceID = serviceID;
	}
public String toString(){
	StringBuffer outStr=new StringBuffer();
	outStr.append("No:").append(No).append("\n");
	outStr.append("name:").append(getNewCustomerInfoDTO().getName()).append("\n");
	outStr.append("GENDER:").append(getNewCustomerInfoDTO().getGender()).append("\n");
	outStr.append("BIRTHDAY:").append(getNewCustomerInfoDTO().getBirthday()).append("\n");
	outStr.append("NATIONALITY:").append(getNewCustomerInfoDTO().getNationality()).append("\n");
	outStr.append("CARDTYPE:").append(getNewCustomerInfoDTO().getCardType()).append("\n");
	outStr.append("CARDID:").append(getNewCustomerInfoDTO().getCardID()).append("\n");
	outStr.append("SOCIALSECCARDID:").append(getNewCustomerInfoDTO().getSocialSecCardID()).append("\n");
	outStr.append("TELEPHONE:").append(getNewCustomerInfoDTO().getTelephone()).append("\n");
	outStr.append("TELEPHONEMOBILE:").append(getNewCustomerInfoDTO().getMobileNumber()).append("\n");
	outStr.append("FAX:").append(getNewCustomerInfoDTO().getFaxNumber()).append("\n");
	outStr.append("EMAIL:").append(getNewCustomerInfoDTO().getEmail()).append("\n");
	outStr.append("PostCode:").append(getAddressDTO().getPostcode()).append("\n");
	outStr.append("District:").append(getAddressDTO().getDistrictID()).append("\n");
	outStr.append("DetailAddress:").append(getAddressDTO().getDetailAddress()).append("\n");
	outStr.append("ServiceID:").append(getServiceID()).append("\n");
	outStr.append("SCSerialNo:").append(getSCSerialNo()).append("\n");
	outStr.append("STBSerialNo:").append(getSTBSerialNo()).append("\n");
	outStr.append("IPPSerialNo:").append(getIPPSerialNo()).append("\n");
	outStr.append("ServiceCode:").append(getServiceCode()).append("\n");
	outStr.append("phoneNoItemID:").append(getPhoneNoItemID()).append("\n");
	outStr.append("errString:").append(getErrString()).append("\n");
	outStr.append("isValid:").append(isValid()).append("\n");
	
	return outStr.toString();
}
/**
 * @return the no
 */
public String getNo() {
	return No;
}
/**
 * @param no the no to set
 */
public void setNo(String no) {
	No = no;
}
/**
 * @return the iPPSerialNo
 */
public String getIPPSerialNo() {
	return iPPSerialNo;
}
/**
 * @param serialNo the iPPSerialNo to set
 */
public void setIPPSerialNo(String serialNo) {
	iPPSerialNo = serialNo;
}
}
