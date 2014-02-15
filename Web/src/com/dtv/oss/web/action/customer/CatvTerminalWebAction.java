package com.dtv.oss.web.action.customer;

/**
 * <p>Title: BOSS</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: Digivision</p>
 * @author Jason.Zhou
 * @version 1.0
 */
import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.CatvTerminalDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.web.action.GeneralWebAction;
import com.dtv.oss.web.exception.WebActionException;
import com.dtv.oss.web.util.WebUtil;
import com.dtv.oss.service.ejbevent.csr.CustomerProductEJBEvent;
import com.dtv.oss.service.listhandler.csr.CatvTerminalListHandler;

/**
 * 客户计费规则维护
 * @author 250713z
 *
 */
public class CatvTerminalWebAction extends GeneralWebAction{
  
	protected int getSepecialAction(String pAction){
        if (pAction==null) return -1;
        if ("create".equalsIgnoreCase(pAction))
          return EJBEvent.CATV_TERMINAL_NEW;
        if("update".equalsIgnoreCase(pAction))
        	return EJBEvent.CATV_TERMINAL_UPDATE;
        return -1;
    }

    protected EJBEvent encapsulateData(HttpServletRequest request) throws WebActionException {
    	LogUtility.log(CatvTerminalWebAction.class,LogLevel.DEBUG,"终端维护...");  
    	CustomerProductEJBEvent event=new CustomerProductEJBEvent();
    	event.setActionType(getSepecialAction(request.getParameter("txtActionType")));
    	if(event.getActionType()==-1)
    		throw new WebActionException("操作类型未知，请检查参数是否完整！");
    						
    	CatvTerminalDTO dto=new CatvTerminalDTO();
    	if(WebUtil.StringHaveContent(request.getParameter("txtID")))
    		dto.setId(request.getParameter("txtID"));
    	if(WebUtil.StringHaveContent(request.getParameter("txtPortNo")))
    		dto.setPortNo(WebUtil.StringToInt(request.getParameter("txtPortNo")));
    	if(WebUtil.StringHaveContent(request.getParameter("txtDistrictID")))
    		dto.setDistrictID(WebUtil.StringToInt(request.getParameter("txtDistrictID")));
    	if(WebUtil.StringHaveContent(request.getParameter("txtPostCode")))
    		dto.setPostcode(request.getParameter("txtPostCode"));
    	if(WebUtil.StringHaveContent(request.getParameter("txtDetailedAddress")))
    		dto.setDetailedAddress(request.getParameter("txtDetailedAddress"));
    	if(WebUtil.StringHaveContent(request.getParameter("txtStatus")))
    		dto.setStatus(request.getParameter("txtStatus"));
    	//if(WebUtil.StringHaveContent(request.getParameter("txtBiDirectionFlag")))
    		dto.setBiDirectionFlag(request.getParameter("txtBiDirectionFlag"));
    	//if(WebUtil.StringHaveContent(request.getParameter("txtCableType")))
    		dto.setCableType(request.getParameter("txtCableType"));
    	//if(WebUtil.StringHaveContent(request.getParameter("txtFiberNodeID")))
    		dto.setFiberNodeID(WebUtil.StringToInt(request.getParameter("txtFiberNodeID")));
    	if(WebUtil.StringHaveContent(request.getParameter("txtRecordSource")))
    		dto.setRecordSource(request.getParameter("txtRecordSource"));
    	if(WebUtil.StringHaveContent(request.getParameter("txtComments")))
    		dto.setComments(request.getParameter("txtComments"));
    	if(WebUtil.StringHaveContent(request.getParameter("txtDtLastmod")))
    		dto.setDtLastmod(WebUtil.StringToTimestamp(request.getParameter("txtDtLastmod")));
    	if(WebUtil.StringHaveContent(request.getParameter("txtCatvTermType")))
    		dto.setCatvTermType(request.getParameter("txtCatvTermType"));
    	LogUtility.log(CatvTerminalWebAction.class,LogLevel.DEBUG,"终端维护...dto="+dto);  
    	event.setCatvTerminalDTO(dto);
        return event;
    }

}