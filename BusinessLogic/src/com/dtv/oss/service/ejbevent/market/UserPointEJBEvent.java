/*
 * Created on 2006-1-5
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.dtv.oss.service.ejbevent.market;

 
import com.dtv.oss.dto.CustomerDTO;
import com.dtv.oss.dto.UserPointsExchangeRuleDTO;
import com.dtv.oss.dto.UserPointsExchangerCdDTO;
import com.dtv.oss.service.ejbevent.AbstractEJBEvent;

/**
 * @author Chen jiang
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class UserPointEJBEvent extends AbstractEJBEvent {
	
	 private CustomerDTO custDto;
	 
	 private UserPointsExchangeRuleDTO userPointsExchRuleDto;
	 
	 private UserPointsExchangerCdDTO recordDto;
	/**
	 * @return Returns the custDto.
	 */
	public CustomerDTO getCustDto() {
		return custDto;
	}
	/**
	 * @param custDto The custDto to set.
	 */
	public void setCustDto(CustomerDTO custDto) {
		this.custDto = custDto;
	}
	/**
	 * @return Returns the userPointsExchGoodDto.
	 */
	public UserPointsExchangeRuleDTO getUserPointsExchRuleDto() {
		return userPointsExchRuleDto;
	}
	/**
	 * @param userPointsExchGoodDto The userPointsExchGoodDto to set.
	 */
	public void setUserPointsExchRuleDto(
			UserPointsExchangeRuleDTO userPointsExchRuleDto) {
		this.userPointsExchRuleDto = userPointsExchRuleDto;
	}
	
	 
	 

	 
	/**
	 * @return Returns the recordDto.
	 */
	public UserPointsExchangerCdDTO getRecordDto() {
		return recordDto;
	}
	/**
	 * @param recordDto The recordDto to set.
	 */
	public void setRecordDto(UserPointsExchangerCdDTO recordDto) {
		this.recordDto = recordDto;
	}
}
