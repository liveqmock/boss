package com.dtv.oss.web.action.customer;

/**
 * <p>Title: BOSS</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: Digivision</p>
 * @author Yangyong
 * @version 1.0
 */
import javax.servlet.http.HttpServletRequest;

//import oss class
import com.dtv.oss.web.action.QueryWebAction;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.web.exception.WebActionException;
import com.dtv.oss.web.util.WebUtil;
import com.dtv.oss.dto.custom.CommonQueryConditionDTO;
import com.dtv.oss.service.ejbevent.csr.CsrQueryEJBEvent;
import com.dtv.oss.service.listhandler.csr.CatvTerminalListHandler;

public class QueryCatvTerminalWebAction extends QueryWebAction {

	protected EJBEvent encapsulateData(HttpServletRequest request)
			throws WebActionException {
		CommonQueryConditionDTO theDTO = new CommonQueryConditionDTO();
		//终端ID
		if (WebUtil.StringHaveContent(request.getParameter("id")))
	        theDTO.setSpareStr1(request.getParameter("id"));
		//终端编号
		//if (WebUtil.StringHaveContent(request.getParameter("txtCatvCode")))
		//	theDTO.setSpareStr2(request.getParameter("txtCatvCode"));
		//终端地址
		if (WebUtil.StringHaveContent(request.getParameter("txtDetailedAddress")))
			theDTO.setSpareStr3(request.getParameter("txtDetailedAddress"));
		//端口数
		if (WebUtil.StringHaveContent(request.getParameter("txtPortNo")))
			theDTO.setSpareStr4(request.getParameter("txtPortNo").trim());
		//所在区域
		if (WebUtil.StringHaveContent(request.getParameter("txtDistrictID")))
			theDTO.setSpareStr5(request.getParameter("txtDistrictID"));
		//光节点
		if (WebUtil.StringHaveContent(request.getParameter("txtFiberNodeID")))
			theDTO.setSpareStr6(request.getParameter("txtFiberNodeID"));
		//终端状态
		if (WebUtil.StringHaveContent(request.getParameter("txtStatus")))
			theDTO.setSpareStr7(request.getParameter("txtStatus"));
		//线缆类型
		if (WebUtil.StringHaveContent(request.getParameter("txtCableType")))
			theDTO.setSpareStr8(request.getParameter("txtCableType"));
		//终端来源
		if (WebUtil.StringHaveContent(request.getParameter("txtRecordSource")))
			theDTO.setSpareStr9(request.getParameter("txtRecordSource"));
		//双向网标记
		if (WebUtil.StringHaveContent(request.getParameter("txtBiDirectionFlag")))
			theDTO.setSpareStr10(request.getParameter("txtBiDirectionFlag"));
		
		//创建日期(起)
		if (WebUtil.StringHaveContent(request.getParameter("txtCreateTimeStart")))
			theDTO.setSpareStr11(request.getParameter("txtCreateTimeStart"));
		//创建日期(止)
		if (WebUtil.StringHaveContent(request.getParameter("txtCreateTimeEnd")))
			theDTO.setSpareStr12(request.getParameter("txtCreateTimeEnd"));
		
		//关断日期(起)
		if (WebUtil.StringHaveContent(request.getParameter("txtPauseTimeStart")))
			theDTO.setSpareStr13(request.getParameter("txtPauseTimeStart"));
		//关断日期(止)
		if (WebUtil.StringHaveContent(request.getParameter("txtPauseTimeEnd")))
			theDTO.setSpareStr14(request.getParameter("txtPauseTimeEnd"));
		
		//开通日期(起)
		if (WebUtil.StringHaveContent(request.getParameter("txtActiveTimeStart")))
			theDTO.setSpareStr15(request.getParameter("txtActiveTimeStart"));
		//开通日期(止)
		if (WebUtil.StringHaveContent(request.getParameter("txtActiveTimeEnd")))
			theDTO.setSpareStr16(request.getParameter("txtActiveTimeEnd"));
		
		//销号日期(起)
		if (WebUtil.StringHaveContent(request.getParameter("txtCancelTimeStart")))
			theDTO.setSpareStr17(request.getParameter("txtCancelTimeStart"));
		//销号日期(止)
		if (WebUtil.StringHaveContent(request.getParameter("txtCancelTimeEnd")))
			theDTO.setSpareStr18(request.getParameter("txtCancelTimeEnd"));
		
		//用户姓名
		if (WebUtil.StringHaveContent(request.getParameter("txtUserName")))
			theDTO.setSpareStr19(request.getParameter("txtUserName"));
		
		//排序方式
		if (WebUtil.StringHaveContent(request.getParameter("txtOrderBy")))
			theDTO.setSpareStr20(request.getParameter("txtOrderBy"));
		
		//只查询终端标志 ,客户开户时选择终端，弹出对话框用到。
		if (WebUtil.StringHaveContent(request.getParameter("select")))
			theDTO.setSpareStr21(request.getParameter("select"));
		
		//只查询终端标志
		if (WebUtil.StringHaveContent(request.getParameter("txtCatvTermType")))
			theDTO.setSpareStr22(request.getParameter("txtCatvTermType"));
		
		return new CsrQueryEJBEvent(theDTO, pageFrom, pageTo,
				CsrQueryEJBEvent.QUERY_TYPE_CatvTerminal);

	}

}