package com.dtv.oss.web.action.customer;

/**
 * <p>Title: BOSS</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: Digivision</p>
 * @author Liang Stone
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
import com.dtv.oss.dto.ResourcePhoneNoDTO;
import com.dtv.oss.service.ejbevent.csr.CsrQueryEJBEvent;
import com.dtv.oss.service.listhandler.csr.PhoneNoListHandler;

public class QueryPhoneNoWebAction extends QueryWebAction {

	protected EJBEvent encapsulateData(HttpServletRequest request)
			throws WebActionException {
		ResourcePhoneNoDTO theDTO = new ResourcePhoneNoDTO();
		
		if (WebUtil.StringHaveContent(request.getParameter("phoneNo")))
	        theDTO.setPhoneNo(request.getParameter("phoneNo"));
		if (WebUtil.StringHaveContent(request.getParameter("grade")))
	        theDTO.setGrade(request.getParameter("grade"));
		if (WebUtil.StringHaveContent(request.getParameter("districtID"))){
	        theDTO.setDistrictId(WebUtil.StringToInt(request.getParameter("districtID")));
		}else{
	    	LogUtility.log(this.getClass(),LogLevel.DEBUG,"缺少区域信息,districtID");
			throw new WebActionException("数据异常!");
		}

		return new CsrQueryEJBEvent(theDTO, pageFrom, pageTo,
				CsrQueryEJBEvent.QUERY_TYPE_PHONENO);

	}

}