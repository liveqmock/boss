/*
 * Created on 2006-1-5
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.dtv.oss.service.ejbevent.config;

 
import java.util.Collection;

import com.dtv.oss.dto.CustomerDTO;
import com.dtv.oss.dto.MarketSegmentDTO;
import com.dtv.oss.dto.MarketSegmentToDistrictDTO;
import com.dtv.oss.dto.UserPointsCumulatedRuleDTO;
import com.dtv.oss.dto.UserPointsExchangeActivityDTO;
import com.dtv.oss.dto.UserPointsExchangeCondDTO;
import com.dtv.oss.dto.UserPointsExchangeGoodsDTO;
import com.dtv.oss.dto.UserPointsExchangeRuleDTO;
import com.dtv.oss.dto.UserPointsExchangerCdDTO;
import com.dtv.oss.service.ejbevent.AbstractEJBEvent;

/**
 * @author Chen jiang
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ConfigActivityEJBEvent extends AbstractEJBEvent {
	
	 private CustomerDTO custDto;
	 
	 private UserPointsExchangeRuleDTO userPointsExchRuleDto;
	 
	 private UserPointsExchangeCondDTO userPointsExchCondDto;
	 
	/**
	 * @return Returns the userPointsExchCondDto.
	 */
	public UserPointsExchangeCondDTO getUserPointsExchCondDto() {
		return userPointsExchCondDto;
	}
	/**
	 * @param userPointsExchCondDto The userPointsExchCondDto to set.
	 */
	public void setUserPointsExchCondDto(
			UserPointsExchangeCondDTO userPointsExchCondDto) {
		this.userPointsExchCondDto = userPointsExchCondDto;
	}
	 private UserPointsExchangerCdDTO recordDto;
	 
	 private UserPointsExchangeActivityDTO activityDto;
	 
	 private UserPointsCumulatedRuleDTO cumulatedRuleDto;
	 
	 private MarketSegmentDTO marketSegmentDTO;
	 
	/**
	 * @return Returns the segmentId.
	 */
	public int getSegmentId() {
		return segmentId;
	}
	/**
	 * @param segmentId The segmentId to set.
	 */
	public void setSegmentId(int segmentId) {
		this.segmentId = segmentId;
	}
	 private int segmentId;
	 
	 private Collection marketSegmentToDistrictDtoCol;
	 
	/**
	 * @return Returns the marketSegmentToDistrictDtoCol.
	 */
	public Collection getMarketSegmentToDistrictDtoCol() {
		return marketSegmentToDistrictDtoCol;
	}
	/**
	 * @param marketSegmentToDistrictDtoCol The marketSegmentToDistrictDtoCol to set.
	 */
	public void setMarketSegmentToDistrictDtoCol(
			Collection marketSegmentToDistrictDtoCol) {
		this.marketSegmentToDistrictDtoCol = marketSegmentToDistrictDtoCol;
	}
	/**
	 * @return Returns the marketSegmentToDistrictDTO.
	 */
	public MarketSegmentToDistrictDTO getMarketSegmentToDistrictDTO() {
		return marketSegmentToDistrictDTO;
	}
	/**
	 * @param marketSegmentToDistrictDTO The marketSegmentToDistrictDTO to set.
	 */
	public void setMarketSegmentToDistrictDTO(
			MarketSegmentToDistrictDTO marketSegmentToDistrictDTO) {
		this.marketSegmentToDistrictDTO = marketSegmentToDistrictDTO;
	}
	 private MarketSegmentToDistrictDTO marketSegmentToDistrictDTO;
	 
	/**
	 * @return Returns the goodsDto.
	 */
	public UserPointsExchangeGoodsDTO getGoodsDto() {
		return goodsDto;
	}
	/**
	 * @param goodsDto The goodsDto to set.
	 */
	public void setGoodsDto(UserPointsExchangeGoodsDTO goodsDto) {
		this.goodsDto = goodsDto;
	}
	 private UserPointsExchangeGoodsDTO goodsDto;
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
	/**
	 * @return Returns the activityDto.
	 */
	public UserPointsExchangeActivityDTO getActivityDto() {
		return activityDto;
	}
	/**
	 * @param activityDto The activityDto to set.
	 */
	public void setActivityDto(UserPointsExchangeActivityDTO activityDto) {
		this.activityDto = activityDto;
	}
	/**
	 * @return Returns the cumulatedRuleDto.
	 */
	public UserPointsCumulatedRuleDTO getCumulatedRuleDto() {
		return cumulatedRuleDto;
	}
	/**
	 * @param cumulatedRuleDto The cumulatedRuleDto to set.
	 */
	public void setCumulatedRuleDto(UserPointsCumulatedRuleDTO cumulatedRuleDto) {
		this.cumulatedRuleDto = cumulatedRuleDto;
	}
	/**
	 * @return Returns the marketSegmentDTO.
	 */
	public MarketSegmentDTO getMarketSegmentDTO() {
		return marketSegmentDTO;
	}
	/**
	 * @param marketSegmentDTO The marketSegmentDTO to set.
	 */
	public void setMarketSegmentDTO(MarketSegmentDTO marketSegmentDTO) {
		this.marketSegmentDTO = marketSegmentDTO;
	}
}
