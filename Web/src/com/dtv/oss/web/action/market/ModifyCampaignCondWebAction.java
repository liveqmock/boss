package com.dtv.oss.web.action.market;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.BundlePaymentMethodDTO;
import com.dtv.oss.dto.BundlePrepaymentDTO;
import com.dtv.oss.dto.CampaignAgmtCampaignDTO;
import com.dtv.oss.dto.CampaignCondCampaignDTO;
import com.dtv.oss.dto.CampaignCondPackageDTO;
import com.dtv.oss.dto.CampaignCondProductDTO;
import com.dtv.oss.dto.CampaignDTO;
import com.dtv.oss.dto.CampaignPaymentAwardDTO;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.market.MarketEJBEvent;
import com.dtv.oss.web.action.GeneralWebAction;
import com.dtv.oss.web.exception.WebActionException;
import com.dtv.oss.web.util.WebUtil;

/**
 * <p>
 * Title: BOSS P5
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2006
 * </p>
 * <p>
 * Company: Digivision
 * </p>
 * User: chen jiang Date: 2006-3-20 Time: 15:22:51 To change this template use
 * File | Settings | File Templates.
 */
public class ModifyCampaignCondWebAction extends GeneralWebAction {

	 
	 
	protected EJBEvent encapsulateData(HttpServletRequest request)
			throws WebActionException {

		CampaignDTO campaignDTO = new CampaignDTO();

		MarketEJBEvent event = new MarketEJBEvent();
		 
		if ("deleteproduct".equals(request.getParameter("txtActionType")))
			event.setActionType(EJBEvent.CAMPAIGN_COND_PRODUCT);
		if ("deletepackage".equals(request.getParameter("txtActionType")))
			event.setActionType(EJBEvent.CAMPAIGN_COND_PACKAGE);
		if ("deletecampaign".equals(request.getParameter("txtActionType")))
			event.setActionType(EJBEvent.CAMPAIGN_COND_CAMPAIGN);
		if ("create_cond".equals(request.getParameter("txtActionType")))
			event.setActionType(EJBEvent.CAMPAIGN_COND_CREATE);
		if ("modify_cond".equals(request.getParameter("txtActionType")))
			event.setActionType(EJBEvent.CAMPAIGN_COND_UPDATE);
		if (WebUtil.StringHaveContent(request.getParameter("txtSeqNo")))
			event.setSeqNo(WebUtil.StringToInt(request
					.getParameter("txtSeqNo")));
		if (WebUtil.StringHaveContent(request.getParameter("txtCampaignID")))
			campaignDTO.setCampaignID(WebUtil.StringToInt(request
					.getParameter("txtCampaignID")));
		if ("deletepaymentmethod".equals(request.getParameter("txtActionType"))){
			event.setActionType(MarketEJBEvent.CAMPAIGN_PAYMENTMETHOD_DELETE); 
			BundlePaymentMethodDTO bundlePayMethDto = new BundlePaymentMethodDTO();
			bundlePayMethDto.setBundleID(WebUtil.StringToInt(request
					.getParameter("txtCampaignID")));
	    	bundlePayMethDto.setRfBillingCycleFlag(request
					.getParameter("txtRfBillingCycleFlag"));
	    	event.setBundlePayMethodDto(bundlePayMethDto);
		}  
	    if("modify_payment_method".equals(request.getParameter("txtActionType"))||
	    		"create_paymentmethod".equals(request.getParameter("txtActionType"))){
	    	
	    	if("modify_payment_method".equals(request.getParameter("txtActionType")))
	    	  event.setActionType(EJBEvent.CAMPAIGN_COND_UPDATE);
	    	if("create_paymentmethod".equals(request.getParameter("txtActionType")))
		    	  event.setActionType(EJBEvent.CAMPAIGN_COND_CREATE);
	    	BundlePaymentMethodDTO bundlePayMethDto = new BundlePaymentMethodDTO();
	    	if (WebUtil.StringHaveContent(request.getParameter("txtDtLastmod"))) 
	    	bundlePayMethDto.setDtLastmod(WebUtil.StringToTimestamp(request
						.getParameter("txtDtLastmod")));
	    	bundlePayMethDto.setBundleID(WebUtil.StringToInt(request
					.getParameter("txtBundleID")));
	    	bundlePayMethDto.setRfBillingCycleFlag(request
					.getParameter("txtRfBillingCycleFlag"));
	    	bundlePayMethDto.setTimeUnitLengthNumber(WebUtil.StringToInt(request
					.getParameter("txtTimeUnitLengthNumber")));
	    	bundlePayMethDto.setTimeUnitLengthType(request
					.getParameter("txtTimeUnitLengthType"));
	    	bundlePayMethDto.setBundlePaymentName(request
					.getParameter("txtBundlePaymentName"));
	    	event.setBundlePayMethodDto(bundlePayMethDto);
	    	
	    } 
	    if ("deleteagmtcampaign".equals(request.getParameter("txtActionType"))){
			event.setActionType(MarketEJBEvent.AGMT_CAMPAIGN_DELETE); 
			CampaignAgmtCampaignDTO bundlePayMethDto = new CampaignAgmtCampaignDTO();
			bundlePayMethDto.setCampaignId(WebUtil.StringToInt(request
					.getParameter("txtCampaignID")));
	    	bundlePayMethDto.setTargetBundleId(WebUtil.StringToInt(request
					.getParameter("txtTargetBundleId")));
	    	 
	    	event.setAgmtCampaignDto(bundlePayMethDto);
		}  
	    if("modify_agmt_campaign".equals(request.getParameter("txtActionType"))||
	    		"create_agmt_campaign".equals(request.getParameter("txtActionType"))){
	    	
	    	if("modify_agmt_campaign".equals(request.getParameter("txtActionType")))
	    	  event.setActionType(EJBEvent.CAMPAIGN_COND_UPDATE);
	    	if("create_agmt_campaign".equals(request.getParameter("txtActionType")))
		    	  event.setActionType(EJBEvent.CAMPAIGN_COND_CREATE);
	    	CampaignAgmtCampaignDTO agmtCampaignDto = new CampaignAgmtCampaignDTO();
	    	if (WebUtil.StringHaveContent(request.getParameter("txtDtLastmod"))) 
	    		agmtCampaignDto.setDtLastmod(WebUtil.StringToTimestamp(request
						.getParameter("txtDtLastmod")));
	    	agmtCampaignDto.setTargetBundleId(WebUtil.StringToInt(request
					.getParameter("txtTargetBundleId")));
	    	agmtCampaignDto.setCampaignId(WebUtil.StringToInt(request
					.getParameter("txtCampaignID")));
	    	agmtCampaignDto.setTimeLengthUnitNumber(WebUtil.StringToInt(request
					.getParameter("txtTimeLengthUnitNumber")));
	    	agmtCampaignDto.setTimeLengthUnitType(request
					.getParameter("txtTimeLengthUnitType"));
	    	 
	    	event.setAgmtCampaignDto(agmtCampaignDto);
	    	
	    } 
	    if("create_bundle_prepayment".equals(request.getParameter("txtActionType"))||
	    		"modify_bundle_prepayment".equals(request.getParameter("txtActionType"))){
	    	
	    	if("modify_bundle_prepayment".equals(request.getParameter("txtActionType")))
	    	   event.setActionType(EJBEvent.CAMPAIGN_COND_UPDATE);
	    	if("create_bundle_prepayment".equals(request.getParameter("txtActionType")))
		       event.setActionType(EJBEvent.CAMPAIGN_COND_CREATE);
	    	BundlePrepaymentDTO bundlePreDto = new BundlePrepaymentDTO();
	    	if (WebUtil.StringHaveContent(request.getParameter("txtDtLastmod"))) 
	    		bundlePreDto.setDtLastmod(WebUtil.StringToTimestamp(request
						.getParameter("txtDtLastmod")));
	    	    bundlePreDto.setCampaignId(WebUtil.StringToInt(request
					.getParameter("txtCampaignID")));
	    	    bundlePreDto.setTargetBundleId(WebUtil.StringToInt(request
						.getParameter("txtCampaignID")));
	    	    bundlePreDto.setBundlePrepaymentPlanId(WebUtil.StringToInt(request
					.getParameter("txtBundlePrepaymentPlanId")));
	    	    bundlePreDto.setTimeUnitLengthNumber(WebUtil.StringToInt(request
					.getParameter("txtTimeUnitLengthNumber")));
	    	    bundlePreDto.setTimeUnitLengthType(request
					.getParameter("txtTimeUnitLengthType"));
	    	    bundlePreDto.setAmount(WebUtil.StringTodouble(request
						.getParameter("txtAmount")));
	    	    bundlePreDto.setAcctItemTypeId(request
					.getParameter("txtAcctItemTypeId"));
	    	    bundlePreDto.setFeeType(request
						.getParameter("txtFeeType"));
	    	event.setBundPreMethDto(bundlePreDto);
	    	
	    } 
		 
		if (WebUtil.StringHaveContent(request
				.getParameter("txtProductPackageList"))) {

			CampaignCondPackageDTO camToPackageDto = new CampaignCondPackageDTO();
			if (WebUtil
					.StringHaveContent(request.getParameter("txtCampaignID")))
				camToPackageDto.setCampaignID(WebUtil.StringToInt(request
						.getParameter("txtCampaignID")));
			if (WebUtil.StringHaveContent(request.getParameter("txtDtLastmod")))
				camToPackageDto.setDtLastmod(WebUtil.StringToTimestamp(request
						.getParameter("txtDtLastmod")));
			if (WebUtil.StringHaveContent(request.getParameter("txtSeqNO")))
				camToPackageDto.setSeqNo(WebUtil.StringToInt(request
						.getParameter("txtSeqNO")));
			camToPackageDto.setExistingFlag(request
					.getParameter("txtExistingPackageFlag"));
			camToPackageDto.setHasAllFlag(request
					.getParameter("txtHasAllPackageFlag"));
			camToPackageDto.setPackageNum(WebUtil.StringToInt(request
					.getParameter("txtPackageNum")));
			camToPackageDto.setPackageIdList(request
					.getParameter("txtProductPackageList"));
			camToPackageDto.setNewPurchaseFlag(request
					.getParameter("txtNewPurchaseFlag"));
			event.setCondPackDto(camToPackageDto);
		}

		if (WebUtil.StringHaveContent(request.getParameter("txtProductList"))) {

			CampaignCondProductDTO camToProDto = new CampaignCondProductDTO();
			if (WebUtil.StringHaveContent(request.getParameter("txtDtLastmod")))
				camToProDto.setDtLastmod(WebUtil.StringToTimestamp(request
						.getParameter("txtDtLastmod")));
			if (WebUtil.StringHaveContent(request.getParameter("txtSeqNO")))
				camToProDto.setSeqNo(WebUtil.StringToInt(request
						.getParameter("txtSeqNO")));
			camToProDto.setCampaignID(WebUtil.StringToInt(request
					.getParameter("txtCampaignID")));
			camToProDto.setExistingFlag(request
					.getParameter("txtExistingProductFlag"));
			camToProDto.setHasAllFlag(request
					.getParameter("txtHasAllProductFlag"));
			camToProDto.setNewCaptureFlag(request
					.getParameter("txtNewCaptureProductFlag"));
			camToProDto.setProductNum(WebUtil.StringToInt(request
					.getParameter("txtProductNum")));
			camToProDto
					.setProductIdList(request.getParameter("txtProductList"));

			event.setCondProdDto(camToProDto);

		}
		if (WebUtil.StringHaveContent(request.getParameter("txtCampaignIDList"))) {

			CampaignCondCampaignDTO camToCamDto = new CampaignCondCampaignDTO();
			if (WebUtil.StringHaveContent(request.getParameter("txtDtLastmod")))
				camToCamDto.setDtLastmod(WebUtil.StringToTimestamp(request
						.getParameter("txtDtLastmod")));
			if (WebUtil.StringHaveContent(request.getParameter("txtSeqNO")))
				camToCamDto.setSeqNo(WebUtil.StringToInt(request
						.getParameter("txtSeqNO")));
			camToCamDto.setCampaignID(WebUtil.StringToInt(request
					.getParameter("txtCampaignID")));
			camToCamDto.setExistingFlag(request
					.getParameter("txtExistingCampaignFlag"));
			camToCamDto.setHasAllFlag(request
					.getParameter("txtHasAllCampaignFlag"));
			camToCamDto.setNewCaptureFlag(request
					.getParameter("txtNewCaptureFlag"));
			camToCamDto.setCampaignNum(WebUtil.StringToInt(request
					.getParameter("txtCampaignNum")));
			camToCamDto.setCampaignIDList(request.getParameter("txtCampaignIDList"));

			event.setCondCampDto(camToCamDto);

		}

		//系统配置，套餐、优惠活动对应的奖励金额 新增
		if("create_campaign_payment_award".equals(request.getParameter("txtActionType"))){
	    	
	    	//if("modify_bundle_prepayment".equals(request.getParameter("txtActionType")))
	    	   //event.setActionType(EJBEvent.CAMPAIGN_COND_UPDATE);
	    	if("create_campaign_payment_award".equals(request.getParameter("txtActionType")))
		       event.setActionType(EJBEvent.CAMPAIGN_PAYMENT_AWARD_CREATE);
	    	CampaignPaymentAwardDTO campaignPaymentAwardDTO = new CampaignPaymentAwardDTO();
	    		campaignPaymentAwardDTO.setCampaignID(WebUtil.StringToInt(request
	    				.getParameter("txtCampaignID")));
	    		campaignPaymentAwardDTO.setMopID(WebUtil.StringToInt(request
	    				.getParameter("mopID")));
	    		campaignPaymentAwardDTO.setValue(WebUtil.StringTodouble(request
	    				.getParameter("value")));
	    	event.setCampaignPaymentAwardDto(campaignPaymentAwardDTO);
	    	
	    } 
		
		//系统配置，套餐、优惠活动对应的奖励金额 删除
		if ("deletePaymentAward".equals(request.getParameter("txtActionType"))){
			event.setActionType(EJBEvent.CAMPAIGN_PAYMENT_AWARD_DELETE);
			CampaignPaymentAwardDTO campaignPaymentAwardDTO = new CampaignPaymentAwardDTO();
			campaignPaymentAwardDTO.setSeqNo(WebUtil.StringToInt(request
					.getParameter("seqNo")));
	    	 
	    	event.setCampaignPaymentAwardDto(campaignPaymentAwardDTO);
		}
		 
		System.out.println("the action type is " + event.getActionType());
		return event;
	}
}
