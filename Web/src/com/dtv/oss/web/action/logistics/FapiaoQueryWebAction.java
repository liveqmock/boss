
package com.dtv.oss.web.action.logistics;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.custom.CommonQueryConditionDTO;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.logistics.LogisticsQueryEJBEvent;
import com.dtv.oss.web.action.DownloadWebAction;
import com.dtv.oss.web.util.WebUtil;
import com.dtv.oss.service.util.BusinessUtility;
/**
 * @author 
 * @发票查询
 */
public class FapiaoQueryWebAction extends DownloadWebAction {

	/*
	 * 
	 */
	protected EJBEvent encapsulateData(HttpServletRequest request)
		throws Exception {
		      CommonQueryConditionDTO dto = new CommonQueryConditionDTO();
              if (WebUtil.StringHaveContent(request.getParameter("txtType")))
                      dto.setSpareStr1(request.getParameter("txtType").trim());
              if (WebUtil.StringHaveContent(request.getParameter("txtModuleName")))
                      dto.setSpareStr2(request.getParameter("txtModuleName").trim());
              if (WebUtil.StringHaveContent(request.getParameter("txtStatus")))
                      dto.setSpareStr3(request.getParameter("txtStatus").trim());
              if (WebUtil.StringHaveContent(request.getParameter("txtTitle")))
                      dto.setSpareStr4(request.getParameter("txtTitle").trim());
              if (WebUtil.StringHaveContent(request.getParameter("txtRecipientOpID")))
                      dto.setSpareStr5(request.getParameter("txtRecipientOpID").trim());
              if (WebUtil.StringHaveContent(request.getParameter("txtReOrgID")))
                      dto.setSpareStr6(request.getParameter("txtReOrgID").trim());
              if (WebUtil.StringHaveContent(request.getParameter("txtCreatorOpID")))
                      dto.setSpareStr7(request.getParameter("txtCreatorOpID").trim());
              if (WebUtil.StringHaveContent(request.getParameter("txtCrOrgID")))
                      dto.setSpareStr8(request.getParameter("txtCrOrgID").trim());
              if (WebUtil.StringHaveContent(request.getParameter("txtSysSerialNo")))
                      dto.setSpareStr9(request.getParameter("txtSysSerialNo").trim());
              
              
              if (WebUtil.StringHaveContent(request.getParameter("txtSerialNoBegin")))
            	  dto.setSpareStr10(request.getParameter("txtSerialNoBegin").trim());
              if (WebUtil.StringHaveContent(request.getParameter("txtSerialNoEnd")))
            	  dto.setSpareStr11(request.getParameter("txtSerialNoEnd").trim());
              if (WebUtil.StringHaveContent(request.getParameter("txtSumAmountBegin")))
            	  dto.setSpareStr12(request.getParameter("txtSumAmountBegin").trim());
              if (WebUtil.StringHaveContent(request.getParameter("txtSumAmountEnd")))
            	  dto.setSpareStr13(request.getParameter("txtSumAmountEnd").trim());
              //发票序号（主键）
              if (WebUtil.StringHaveContent(request.getParameter("txtSeqNo")))
                  dto.setSpareStr14(request.getParameter("txtSeqNo").trim());
              
              if (WebUtil.StringHaveContent(request.getParameter("txtLastPrintDateBegin")))
            	  dto.setSpareBeginTime(WebUtil.StringToTimestampWithDayBegin(request.getParameter("txtLastPrintDateBegin").trim()));
              if (WebUtil.StringHaveContent(request.getParameter("txtLastPrintDateEnd")))
            	  dto.setSpareEndTime(WebUtil.StringToTimestampWithDayBegin(request.getParameter("txtLastPrintDateEnd").trim()));
                  String isManageVolumn = BusinessUtility.getFaPiaoSettingByName("ISVOLUMNMANAGE");
        if(isManageVolumn != null && "Y".equals(isManageVolumn) && WebUtil.StringHaveContent(request.getParameter("textFapiaoVolumn")))
            dto.setSpareStr18(request.getParameter("textFapiaoVolumn").trim());

              //使用的是listHandler中的查询语句,不需要设定SubType及custsql[自定义sql] 查询条件来自界面,所以也不需要设定QueryParameter
              //setSubType(request.getParameter("txtSubType"));
//  			  setQueryParameter(new Object[]{request.getParameter("txtType")
//  			  ,request.getParameter("txtModuleName")
//  			  ,request.getParameter("txtStatus")
//  			  ,request.getParameter("txtTitle")
//			  ,request.getParameter("txtRecipientOpID")
//			  ,request.getParameter("txtReOrgID")
//  			  ,request.getParameter("txtCreatorOpID")
//  			  ,request.getParameter("txtCrOrgID")
//			  ,request.getParameter("txtSysSerialNo")
//			  ,request.getParameter("txtSerialNoBegin")
//  			  ,request.getParameter("txtSerialNoEnd")
//  			  ,request.getParameter("txtSumAmountBegin")
//			  ,request.getParameter("txtSumAmountEnd")
//			  ,request.getParameter("txtLastPrintDateBegin")
//			  ,request.getParameter("txtLastPrintDateEnd")
//  			  });
              return new LogisticsQueryEJBEvent(dto, pageFrom, pageTo,LogisticsQueryEJBEvent.QUERY_TYPE_FAPIAO);
	}

}
