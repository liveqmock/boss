package com.dtv.oss.web.action.config;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.ProductPackageDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.config.ConfigProductEJBEvent;
import com.dtv.oss.web.action.GeneralWebAction;
import com.dtv.oss.web.util.WebUtil;

public class ProductPackageManageWebAction extends GeneralWebAction {

	protected EJBEvent encapsulateData(HttpServletRequest request)
			throws Exception {
		
		ProductPackageDTO dto=new ProductPackageDTO();
		
		 
			dto.setDateFrom(WebUtil.StringToTimestamp(request.getParameter("txtDateFrom")));
		 
			dto.setDateTo(WebUtil.StringToTimestamp(request.getParameter("txtDateTo")));
	 
			dto.setStatus(request.getParameter("txtStatus"));
		 
			dto.setDtLastmod(WebUtil.StringToTimestamp(request.getParameter("txtDtLastmod")));
	 
		   dto.setDescription(request.getParameter("txtDescription"));
	 
		   dto.setPackageName(request.getParameter("txtPackageName"));
	 
		   dto.setPackageClass(request.getParameter("txtPackageClassify"));
	 
		   dto.setPackagePriority(WebUtil.StringToInt(request.getParameter("txtPackagePriority")));
	 
		  dto.setPackageID(WebUtil.StringToInt(request.getParameter("txtPackageID")));
		  
		  
		 String optionFlag = request.getParameter("txtHasOptionProductFlag");
		
		 dto.setHasOptionProductFlag(optionFlag);
		 if(!"S".equalsIgnoreCase(optionFlag)){
		 	 dto.setMaxSelProductNum(WebUtil.StringToInt(request.getParameter("txtMaxSelfProdNum")));
			 
			 dto.setMinSelProductNum(WebUtil.StringToInt(request.getParameter("txtMinSelfProdNum")));
		 
		 }
		
		dto.setGrade(WebUtil.StringToInt(request.getParameter("txtGrade")));
		
		dto.setCaptureFlag(request.getParameter("txtCaptureFlag"));
		
		dto.setCsiTypeList(request.getParameter("txtCsiTypeList"));
		
		dto.setCustTypeList(request.getParameter("txtCustTypeList"));
		
		ConfigProductEJBEvent event=new ConfigProductEJBEvent();
		if(WebUtil.StringHaveContent(request.getParameter("txtMarketSegmentList")))
			event.setSegmentIDstr(request.getParameter("txtMarketSegmentList"));
		 
		event.setPackageID(WebUtil.StringToInt(request.getParameter("txtPackageID")));
	 
		if("CREATE".equalsIgnoreCase(request.getParameter("Action")))
			event.setActionType(EJBEvent.PRODUCT_PACKAGE_CREATE);
		else if("MODIFY".equalsIgnoreCase(request.getParameter("Action")))
			event.setActionType(EJBEvent.PRODUCT_PACKAGE_MODIFY);
			 
		else{
			//throw new WebActionException("产品管理操作类型未知！");
			LogUtility.log(getClass(),LogLevel.WARN,"ProductPackageManageWebAction，没有发现动作类型!");
			return null;
		}
		 if("B".equalsIgnoreCase(request.getParameter("txtPackageClassify"))) 
		   event.setCampaignIdStr(request.getParameter("txtCampaignId"));
		 else
			 event.setCampaignIdStr(null);
		event.setProductPackageDTO(dto);
		
		return event;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
