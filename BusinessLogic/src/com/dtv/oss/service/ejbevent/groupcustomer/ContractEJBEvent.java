package com.dtv.oss.service.ejbevent.groupcustomer;

import java.util.Collection;

import com.dtv.oss.dto.ContractDTO;
import com.dtv.oss.service.ejbevent.AbstractEJBEvent;

public class ContractEJBEvent extends AbstractEJBEvent {

  
 
	 
	private ContractDTO contractDTO=null;
 
	 

	private Collection contractToPackageIDCol = null;

	 
	private int actionType;

	 
 
  
	 
 
	public static final int CONTRACT_CREATE = 2;

	public static final int CONTRACT_MODIFY = 3;

	/**
	 * @return Returns the contractToPackageIDCol.
	 */
	public Collection getContractToPackageIDCol() {
		return contractToPackageIDCol;
	}

	/**
	 * @param contractToPackageIDCol
	 *            The contractToPackageIDCol to set.
	 */
	public void setContractToPackageIDCol(Collection contractToPackageIDCol) {
		this.contractToPackageIDCol = contractToPackageIDCol;
	}

	 
	/**
	 * @return the actionType
	 */
	public int getActionType() {
		return actionType;
	}

	/**
	 * @param actionType
	 *            the actionType to set
	 */
	public void setActionType(int actionType) {
		this.actionType = actionType;
	}

	 
	/**
	 * @return the contractDTO
	 */
	public ContractDTO getContractDTO() {
		return contractDTO;
	}

	/**
	 * @param contractDTO
	 *            the contractDTO to set
	 */
	public void setContractDTO(ContractDTO contractDTO) {
		this.contractDTO = contractDTO;
 
	}

 
	 

	 
	
 
}
