package com.dtv.oss.web.action.logistics;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.FaPiaoDTO;
import com.dtv.oss.dto.FapiaoVolumnDTO;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.logistics.FaPiaoEJBEvent;
import com.dtv.oss.service.ejbevent.logistics.LogisticsEJBEvent;
import com.dtv.oss.web.action.GeneralWebAction;
import com.dtv.oss.web.exception.WebActionException;
import com.dtv.oss.web.util.WebUtil;

/*
 * 发票运入
 */
public class FapiaoDiscardWebAction extends GeneralWebAction {
	boolean doPost = false;

	protected boolean needCheckToken() {
		return doPost;
	}

	public void doStart(HttpServletRequest request) {
		super.doStart(request);
		if (WebUtil.StringHaveContent(request.getParameter("confirm_post"))) {
			if (request.getParameter("confirm_post").equalsIgnoreCase("true")) {
				doPost = true;

			}
		}
	}

	protected EJBEvent encapsulateData(HttpServletRequest request)
			throws WebActionException {
		FaPiaoEJBEvent theEvent = new FaPiaoEJBEvent();
		FaPiaoDTO theDTO = new FaPiaoDTO();
		FapiaoVolumnDTO volumnDTO=new FapiaoVolumnDTO();
		theEvent.setDiscardStyle("2");
		//不管理发票册
		if(request.getParameter("volumnSetting").equals("N")){
			theEvent.setIsVolumnManage(false);
			theEvent.setSerailnos(request.getParameter("txtSerailnos"));
			theDTO.setType(request.getParameter("txtType2"));
			theDTO.setDiscardReason(request.getParameter("txtDiscardReason"));
		}
		//管理发票册
		if(request.getParameter("volumnSetting").equals("Y")){
			theEvent.setIsVolumnManage(true);
			//报废发票册
			if(request.getParameter("option").equals("1")){
				theEvent.setDiscardStyle("1");
				theDTO.setType(request.getParameter("txtType1"));
				volumnDTO.setVolumnSn(request.getParameter("txtVolumnSN1"));
				theDTO.setDiscardReason(request.getParameter("txtDiscardReason"));			
			}
			//报废发票
			if(request.getParameter("option").equals("2")){
				theEvent.setDiscardStyle("2");
				volumnDTO.setVolumnSn(request.getParameter("txtVolumnSN2"));
				theEvent.setSerailnos(request.getParameter("txtSerailnos"));				
				theDTO.setType(request.getParameter("txtType2"));
				theDTO.setDiscardReason(request.getParameter("txtDiscardReason"));			
			}			
		}
		theEvent.setActionType(LogisticsEJBEvent.FAPIAO_DISCARD);
		theEvent.setDoPost(doPost);
		theEvent.setFaPiaoDTO(theDTO);
		theEvent.setFapiaoVolumnDTO(volumnDTO);
		return theEvent;
	}
}
