package com.dtv.oss.web.action.market;

import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.CampaignAgmtPackageDTO;
import com.dtv.oss.dto.CampaignAgmtProductDTO;
import com.dtv.oss.dto.CampaignDTO;
import com.dtv.oss.dto.CampaignToMarketSegmentDTO;
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
public class ModifyCampaignWebAction extends GeneralWebAction {

	boolean confirmPost = false;

	protected boolean needCheckToken() {
		return confirmPost;
	}

	public void doStart(HttpServletRequest request) {
		super.doStart(request);

		confirmPost = false;
		if (WebUtil.StringHaveContent(request.getParameter("confirm_post"))) {
			if (request.getParameter("confirm_post").equalsIgnoreCase("true"))
				confirmPost = true;
		}

	}

	protected EJBEvent encapsulateData(HttpServletRequest request)
			throws WebActionException {

		CampaignDTO campaignDTO = new CampaignDTO();

		MarketEJBEvent event = new MarketEJBEvent();
		if ("create".equals(request.getParameter("Action")))
			event.setActionType(EJBEvent.CAMPAIGN_CREATE);
		if ("update".equals(request.getParameter("Action")))
			event.setActionType(EJBEvent.CAMPAIGN_UPDATE);
		 
		if (WebUtil.StringHaveContent(request.getParameter("txtSeqNo")))
			event.setSeqNo(WebUtil.StringToInt(request
					.getParameter("txtSeqNo")));
		if (WebUtil.StringHaveContent(request.getParameter("txtCampaignID")))
			campaignDTO.setCampaignID(WebUtil.StringToInt(request
					.getParameter("txtCampaignID")));
		campaignDTO.setCampaignName(request.getParameter("txtCampaignName"));
		campaignDTO.setStatus(request.getParameter("txtStatus"));
		campaignDTO.setDescription(request.getParameter("txtDescription"));
		campaignDTO.setOpenSourceTypeList(request
				.getParameter("txtOpenSourceTypeList"));
		campaignDTO.setCustTypeList(request.getParameter("txtCustTypeList"));
		//add by chenjiang
		campaignDTO.setCsiTypeList(request.getParameter("txtCsiTypeList"));
		campaignDTO.setRfBillingFlag(request.getParameter("txtRfBillFlag"));
		campaignDTO.setRfBillingCycleFlag(request.getParameter("txtRfBillingCycleFlag"));
		campaignDTO.setBundlePrePaymentFlag(request.getParameter("txtBundlePrePaymentFlag"));
		campaignDTO.setGroupBargainFlag(request
				.getParameter("txtGroupBargainFlag"));
		campaignDTO
				.setAutoExtendFlag(request.getParameter("txtAutoExtendFlag"));
		campaignDTO
		.setPaymentAwardFlag(request.getParameter("txtPaymentAwardFlag"));
		// end
		campaignDTO.setCampaignType(request.getParameter("txtCampaignType"));
		campaignDTO.setTimeLengthUnitNumber(WebUtil.StringToInt(request
				.getParameter("txtTimeLenUnitNumeber")));
		if (WebUtil.StringHaveContent(request.getParameter("dtLastmod")))
			campaignDTO.setDtLastmod(WebUtil.StringToTimestamp(request
					.getParameter("dtLastmod")));
		campaignDTO.setDateFrom(WebUtil.StringToTimestamp(request
				.getParameter("txtDateFrom")));
		campaignDTO.setDateTo(WebUtil.StringToTimestamp(request
				.getParameter("txtDateTo")));
		campaignDTO.setKeepBilling(request.getParameter("txtKeepBilling"));
		campaignDTO.setAllowPause(request.getParameter("txtAllowPause"));
		campaignDTO.setAllowTransition(request
				.getParameter("txtAllowTransition"));

		campaignDTO.setAllowTransfer(request.getParameter("txtAllowTransfer"));

		campaignDTO.setAllowClose(request.getParameter("txtAllowClose"));

		campaignDTO.setAllowAlter(request.getParameter("txtAllowAlter"));
		campaignDTO.setObligationFlag(request.getParameter("txtObligationFlag"));
		
		campaignDTO.setCampainpriority(WebUtil.StringToInt(request.getParameter("txtCampainpriority")));

		if (WebUtil.StringHaveContent(request
				.getParameter("txtMarketSegmentList"))) {
			Collection segCol = new ArrayList();
			String[] marketSegList = request.getParameter(
					"txtMarketSegmentList").split(",");
			for (int i = 0; i < marketSegList.length; i++) {
				if (marketSegList[i] == null)
					continue;
				CampaignToMarketSegmentDTO camToSegDto = new CampaignToMarketSegmentDTO();

				camToSegDto.setCampaignId(WebUtil.StringToInt(request
						.getParameter("txtCampaignID")));
				camToSegDto.setMarketSegmentId(WebUtil
						.StringToInt(marketSegList[i]));

				segCol.add(camToSegDto);
			}
			event.setCampaignID(WebUtil.StringToInt(request
					.getParameter("txtCampaignID")));
			event.setCondMarketDtoCol(segCol);
		}
		else{
			event.setCampaignID(WebUtil.StringToInt(request
					.getParameter("txtCampaignID")));
			event.setCondMarketDtoCol(null);
		}
		if (WebUtil.StringHaveContent(request
				.getParameter("txtAgmtProdcutList"))) {
			Collection prodCol = new ArrayList();
			String[] productList = request.getParameter(
					"txtAgmtProdcutList").split(",");
			for (int i = 0; i < productList.length; i++) {
				if (productList[i] == null)
					continue;
				CampaignAgmtProductDTO productDto = new CampaignAgmtProductDTO();

				productDto.setCampaignID(WebUtil.StringToInt(request
						.getParameter("txtCampaignID")));
				productDto.setProductID(WebUtil
						.StringToInt(productList[i]));

				prodCol.add(productDto);
			}
			event.setCampaignID(WebUtil.StringToInt(request
					.getParameter("txtCampaignID")));
			event.setAgmtProdDtoCol(prodCol);
		} 
		
		
		else{
			event.setCampaignID(WebUtil.StringToInt(request
					.getParameter("txtCampaignID")));
			event.setAgmtProdDtoCol(null);
		}
		
		if (WebUtil.StringHaveContent(request
				.getParameter("txtAgmtPackageList"))) {
			Collection packCol = new ArrayList();
			String[] productList = request.getParameter(
					"txtAgmtPackageList").split(",");
			for (int i = 0; i < productList.length; i++) {
				if (productList[i] == null)
					continue;
				CampaignAgmtPackageDTO packageDto = new CampaignAgmtPackageDTO();

				packageDto.setCampaignID(WebUtil.StringToInt(request
						.getParameter("txtCampaignID")));
				packageDto.setPackageID(WebUtil
						.StringToInt(productList[i]));

				packCol.add(packageDto);
			}
			event.setCampaignID(WebUtil.StringToInt(request
					.getParameter("txtCampaignID")));
			event.setAgmtPackDtoCol(packCol);
		}else{
			event.setCampaignID(WebUtil.StringToInt(request
					.getParameter("txtCampaignID")));
			event.setAgmtPackDtoCol(null);
		}
		
		event.setDoPost(confirmPost);
		event.setCampaignDto(campaignDTO);
		System.out.println("the action type is " + event.getActionType());
		return event;
	}
}
