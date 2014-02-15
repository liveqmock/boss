package com.dtv.oss.web.action.customer;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.CampaignDTO;
import com.dtv.oss.service.commandresponse.CommandResponse;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.csr.CsrQueryEJBEvent;
import com.dtv.oss.web.action.QueryWebAction;
import com.dtv.oss.web.util.WebUtil;
/**
 * author     ��zhouxushun 
 * date       : 2005-10-11
 * description:
 * @author 250713z
 *
 */
public class QueryCampaignWebAction extends QueryWebAction {

	protected EJBEvent encapsulateData(HttpServletRequest request)
			throws Exception {
		
		CampaignDTO dto=new CampaignDTO();
		if (WebUtil.StringHaveContent(request.getParameter("txtCampaignID"))){
			dto.setCampaignID(WebUtil.StringToInt(request.getParameter("txtCampaignID")));
		}	
		if (WebUtil.StringHaveContent(request.getParameter("txtCampaignType"))){
			dto.setCampaignType(request.getParameter("txtCampaignType"));
		}
		//�������ר���ڿͻ������ֹ��������ʱ���˵�����/�Ź�����,��û�ж���NEWProduct�Ĵ���.DTO��û�п����ֶ�,��ռ����NAME
		if (WebUtil.StringHaveContent(request.getParameter("apply"))){
			dto.setCampaignName(request.getParameter("apply"));
		}
		//����������ڹ��˿ͻ����Ͷ�Ӧ�Ĳ�Ʒ
		if (WebUtil.StringHaveContent(request.getParameter("custtype"))){
			dto.setCustTypeList(request.getParameter("custtype"));
		}
		//����������ڹ��˿ͻ���Դ������Ӧ��Ʒ
		if (WebUtil.StringHaveContent(request.getParameter("opensourcetype"))){
			dto.setOpenSourceTypeList(request.getParameter("opensourcetype"));
		}
		//��������
		if (WebUtil.StringHaveContent(request.getParameter("txtObligationFlag"))){
			dto.setObligationFlag(request.getParameter("txtObligationFlag"));
		}

		return new CsrQueryEJBEvent(dto,pageFrom,pageTo,CsrQueryEJBEvent.QUERY_TYPE_CAMPAIGN);
	}
}
