package com.dtv.oss.service.ejbevent.config;

import com.dtv.oss.dto.BillingRuleDTO;
import com.dtv.oss.dto.BossConfigurationDTO;
import com.dtv.oss.dto.BrconditionDTO;
import com.dtv.oss.dto.FeeSplitPlanDTO;
import com.dtv.oss.dto.FeeSplitPlanItemDTO;
import com.dtv.oss.dto.MethodOfPaymentDTO;
import com.dtv.oss.service.ejbevent.csr.CsrAbstractEJbevent;

public class ConfigBillingEJBEvent extends CsrAbstractEJbevent {

    private BrconditionDTO brDto;
    
    private MethodOfPaymentDTO methodOfPaymentDto; 
    
    private BillingRuleDTO billingRuleDto; 
    
    private BossConfigurationDTO bcDto;
    
    private FeeSplitPlanDTO feeSplitPlanDto;
    
    
    private FeeSplitPlanItemDTO feeSplitPlanItemDto;
    
    
    
    
	public FeeSplitPlanItemDTO getFeeSplitPlanItemDto() {
		return feeSplitPlanItemDto;
	}
	public void setFeeSplitPlanItemDto(FeeSplitPlanItemDTO feeSplitPlanItemDto) {
		this.feeSplitPlanItemDto = feeSplitPlanItemDto;
	}
  
	public FeeSplitPlanDTO getFeeSplitPlanDto() {
		return feeSplitPlanDto;
	}
	public void setFeeSplitPlanDto(FeeSplitPlanDTO feeSplitPlanDto) {
		this.feeSplitPlanDto = feeSplitPlanDto;
	}
	/**
	 * @return Returns the bcDto.
	 */
	public BossConfigurationDTO getBcDto() {
		return bcDto;
	}
	/**
	 * @param bcDto The bcDto to set.
	 */
	public void setBcDto(BossConfigurationDTO bcDto) {
		this.bcDto = bcDto;
	}
	/**
	 * @return Returns the billingRuleDto.
	 */
	public BillingRuleDTO getBillingRuleDto() {
		return billingRuleDto;
	}
	/**
	 * @param billingRuleDto The billingRuleDto to set.
	 */
	public void setBillingRuleDto(BillingRuleDTO billingRuleDto) {
		this.billingRuleDto = billingRuleDto;
	}
	/**
	 * @return Returns the brDto.
	 */
	public BrconditionDTO getBrDto() {
		return brDto;
	}
	/**
	 * @param brDto The brDto to set.
	 */
	public void setBrDto(BrconditionDTO brDto) {
		this.brDto = brDto;
	}
	/**
	 * @return Returns the methodOfPaymentDto.
	 */
	public MethodOfPaymentDTO getMethodOfPaymentDto() {
		return methodOfPaymentDto;
	}
	/**
	 * @param methodOfPaymentDto The methodOfPaymentDto to set.
	 */
	public void setMethodOfPaymentDto(MethodOfPaymentDTO methodOfPaymentDto) {
		this.methodOfPaymentDto = methodOfPaymentDto;
	}
}
