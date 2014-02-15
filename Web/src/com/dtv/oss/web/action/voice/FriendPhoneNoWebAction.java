package com.dtv.oss.web.action.voice;


import javax.servlet.http.HttpServletRequest;
import com.dtv.oss.dto.VoiceFriendPhoneNoDTO;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.voice.VoiceEJBEvent;
import com.dtv.oss.web.action.PayFeeWebAction;
import com.dtv.oss.web.exception.WebActionException;
import com.dtv.oss.web.util.CommonKeys;
import com.dtv.oss.web.util.WebUtil;


public class FriendPhoneNoWebAction extends PayFeeWebAction {

	protected EJBEvent encapsulateData(HttpServletRequest request)
			throws WebActionException {   
		String txtActionType=request.getParameter("txtActionType");
		if (txtActionType ==null){
			return null;
		}
		VoiceEJBEvent ejbEvent =new VoiceEJBEvent();
		int eJbActionType =0;
		switch (Integer.parseInt(txtActionType)){
			//亲情号码的增加
			case CommonKeys.FRIEND_PHONENO_CREATE:
				setFriendPhoneNo(request,ejbEvent);
				eJbActionType =VoiceEJBEvent.FRIEND_PHONENO_CREATE;
				break;
			//亲情号码的删除
			case CommonKeys.FRIEND_PHONENO_DELETE:
				getSeqNO(request,ejbEvent);
				eJbActionType =VoiceEJBEvent.FRIEND_PHONENO_DELETE;
				break;
		    default :
		    	break;
		}
		ejbEvent.setActionType(eJbActionType );
		return ejbEvent;

	}
	
	private void setFriendPhoneNo(HttpServletRequest request, VoiceEJBEvent ejbEvent)
	{
		//受理单信息
		VoiceFriendPhoneNoDTO phoneNoDTO = new VoiceFriendPhoneNoDTO();
		
		if (WebUtil.StringHaveContent(request.getParameter("txtCustomerID")))
        	phoneNoDTO.setCustomerID(WebUtil.StringToInt(request.getParameter("txtCustomerID")));
        if (WebUtil.StringHaveContent(request.getParameter("txtServiceAccountID")))
        	phoneNoDTO.setServiceAccountID(WebUtil.StringToInt(request.getParameter("txtServiceAccountID")));
        if (WebUtil.StringHaveContent(request.getParameter("txtCountryCode")))
        	phoneNoDTO.setCountryCode(request.getParameter("txtCountryCode"));
        if (WebUtil.StringHaveContent(request.getParameter("txtAreaCode")))
        	phoneNoDTO.setAreaCode(request.getParameter("txtAreaCode"));
        if (WebUtil.StringHaveContent(request.getParameter("txtPhoneNo")))
        	phoneNoDTO.setPhoneNo(request.getParameter("txtPhoneNo"));

		ejbEvent.setVoiceFriendPhoneNoDTO(phoneNoDTO);
	} 
	private void getSeqNO(HttpServletRequest request, VoiceEJBEvent ejbEvent){
		//封装业务帐户
		VoiceFriendPhoneNoDTO phoneNoDTO = new VoiceFriendPhoneNoDTO();
		if (WebUtil.StringHaveContent(request.getParameter("txtCustomerID")))
        	phoneNoDTO.setCustomerID(WebUtil.StringToInt(request.getParameter("txtCustomerID")));
        if (WebUtil.StringHaveContent(request.getParameter("txtServiceAccountID")))
        	phoneNoDTO.setServiceAccountID(WebUtil.StringToInt(request.getParameter("txtServiceAccountID")));
        ejbEvent.setVoiceFriendPhoneNoDTO(phoneNoDTO);
        //封装亲情号码和seqID
		String[] seqNoRets = request.getParameterValues("txtSeqNO");
		String seqNo="";
		for(int i=0; i<seqNoRets.length;i++){
			seqNo=seqNo+seqNoRets[i]+";";
		}
		ejbEvent.setSeqNo(seqNo);
	}
	
}