package com.dtv.oss.web.action.logistics;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.custom.CommonQueryConditionDTO;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.logistics.LogisticsQueryEJBEvent;
import com.dtv.oss.web.action.DownloadWebAction;
import com.dtv.oss.web.action.QueryWebAction;
import com.dtv.oss.web.util.WebUtil;
/**
 * @author caochenyi
 *
 * 更改所生成类型注释的模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
public class QueryStockDeviceWebAction extends DownloadWebAction {

	/* （非 Javadoc）
	 * @see com.dtv.oss.web.action.GeneralWebAction#encapsulateData(javax.servlet.http.HttpServletRequest)
	 */
	protected EJBEvent encapsulateData(HttpServletRequest request)
		throws Exception {

		 CommonQueryConditionDTO dto = new CommonQueryConditionDTO();
              if (WebUtil.StringHaveContent(request.getParameter("txtDeviceID")))
                      dto.setSpareStr1(request.getParameter("txtDeviceID"));
              if (WebUtil.StringHaveContent(request.getParameter("txtDeviceClass")))
                      dto.setSpareStr2(request.getParameter("txtDeviceClass"));
              if (WebUtil.StringHaveContent(request.getParameter("txtDeviceModel")))
                      dto.setSpareStr3(request.getParameter("txtDeviceModel"));
              if (WebUtil.StringHaveContent(request.getParameter("txtSerialNoBegin")))
                      dto.setBeginStr(request.getParameter("txtSerialNoBegin").trim());
              if (WebUtil.StringHaveContent(request.getParameter("txtSerialNoEnd")))
                      dto.setEndStr(request.getParameter("txtSerialNoEnd").trim());
              if (WebUtil.StringHaveContent(request.getParameter("txtStatus")))
                      dto.setSpareStr5(request.getParameter("txtStatus"));
              if (WebUtil.StringHaveContent(request.getParameter("txtDepotID")))
                      dto.setSpareStr6(request.getParameter("txtDepotID"));
              if (WebUtil.StringHaveContent(request.getParameter("txtUseFlag")))
                      dto.setSpareStr7(request.getParameter("txtUseFlag"));
              if (WebUtil.StringHaveContent(request.getParameter("txtPalletID")))
                      dto.setSpareStr8(request.getParameter("txtPalletID"));
              if (WebUtil.StringHaveContent(request.getParameter("txtAddressType")))
                      dto.setSpareStr9(request.getParameter("txtAddressType"));
              if (WebUtil.StringHaveContent(request.getParameter("txtAddressID")))
                      dto.setSpareStr10(request.getParameter("txtAddressID"));
              if (WebUtil.StringHaveContent(request.getParameter("txtMatchFlag")))
                dto.setSpareStr11(request.getParameter("txtMatchFlag"));
              if (WebUtil.StringHaveContent(request.getParameter("txtPreAuthFlag")))
                dto.setSpareStr12(request.getParameter("txtPreAuthFlag"));
              
              if (WebUtil.StringHaveContent(request.getParameter("txtMacAddress")))
                  dto.setSpareStr13(request.getParameter("txtMacAddress"));
              if (WebUtil.StringHaveContent(request.getParameter("txtInterMacAddress")))
                  dto.setSpareStr14(request.getParameter("txtInterMacAddress"));
              
              if (WebUtil.StringHaveContent(request.getParameter("txtBatchNo")))
                  dto.setSpareStr15(request.getParameter("txtBatchNo"));
              if (WebUtil.StringHaveContent(request.getParameter("txtOrgID")))
                dto.setSpareStr16(request.getParameter("txtOrgID"));
              if (WebUtil.StringHaveContent(request.getParameter("txtPurposeStrList")))
                  dto.setSpareStr17(request.getParameter("txtPurposeStrList"));
              //地址类型
              if (WebUtil.StringHaveContent(request.getParameter("txtZoneStation")))
                  dto.setSpareStr18(request.getParameter("txtZoneStation"));

              return new LogisticsQueryEJBEvent(dto, pageFrom, pageTo, LogisticsQueryEJBEvent.QUERY_TYPE_STOCKDEVICE);
	}

}
