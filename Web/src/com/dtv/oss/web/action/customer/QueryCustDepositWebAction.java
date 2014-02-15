package com.dtv.oss.web.action.customer;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.custom.CommonQueryConditionDTO;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.csr.QueryCustDepositEJBEvent;
import com.dtv.oss.web.action.QueryWebAction;
import com.dtv.oss.web.exception.WebActionException;
import com.dtv.oss.web.util.WebUtil;
import com.dtv.oss.util.Postern;
import com.dtv.oss.web.util.CommonKeys;

public class QueryCustDepositWebAction extends QueryWebAction{
	protected EJBEvent encapsulateData(HttpServletRequest request) throws Exception {
		//DTO��װ����˵��
		//------------------���ڿͻ�Ѻ���ѯ-------
		//SpareStr1:�˵���
		//SpareStr2:������
		//SpareStr4:�ͻ�֤��
		//SpareStr5:��Ŀ����
		//SpareTime1:������ʼʱ��
		//SpareTime2:������ֹʱ��
		//---------------�������ڿͻ�Ѻ���ѯ-------

		//------------���ڿͻ�Ѻ�������Ĳ�ѯ-----
		//SpareStr3:���ü�¼��ˮ��
		//---------�������ڿͻ�Ѻ�������Ĳ�ѯ-----

		CommonQueryConditionDTO	dto = new CommonQueryConditionDTO();

		//*************************************���ڿͻ�Ѻ���ѯ****************************************
		//SpareStr1:�˵���
		if(WebUtil.StringHaveContent(request.getParameter("txtAcctID")) && (!"0".equals(request.getParameter("txtAcctID")))){
			//���и�ʽ���
			if(WebUtil.StringToInt(request.getParameter("txtAcctID"))==0)
				throw new WebActionException("�˵��Ÿ�ʽ���󣬱���Ϊ������");
			dto.setSpareStr1(request.getParameter("txtAcctID"));
		}
		//SpareStr2:������
		if(WebUtil.StringHaveContent(request.getParameter("txtCSIID")) && (!"0".equals(request.getParameter("txtCSIID")))){
			//���и�ʽ���
			if(WebUtil.StringToInt(request.getParameter("txtCSIID"))==0)
				throw new WebActionException("�����Ÿ�ʽ���󣬱���Ϊ������");
			dto.setSpareStr2(request.getParameter("txtCSIID"));
		}
		//SpareStr4:�ͻ�֤��
		if(WebUtil.StringHaveContent(request.getParameter("txtCustomerID")) && (!"0".equals(request.getParameter("txtCustomerID")))){
			//���и�ʽ���
			if(WebUtil.StringToInt(request.getParameter("txtCustomerID"))==0)
				throw new WebActionException("�ͻ�֤�Ÿ�ʽ���󣬱���Ϊ������");
			dto.setSpareStr4(request.getParameter("txtCustomerID"));
		}
		//SpareStr5:��Ŀ����
		dto.setSpareStr5(Postern.getAcctItemType());
		//SpareStr6:״̬(��Ч)
		dto.setSpareStr6(CommonKeys.SET_F_FTSTATUS_V);
		//SpareTime1:������ʼʱ��
		if(WebUtil.StringHaveContent(request.getParameter("txtCreateTime1")))
			dto.setSpareTime1(WebUtil.StringToTimestampWithDayBegin(request.getParameter("txtCreateTime1")));
		//SpareTime2:������ֹʱ��
		if(WebUtil.StringHaveContent(request.getParameter("txtCreateTime2")))
			dto.setSpareTime2(WebUtil.StringToTimestampWithDayEnd(request.getParameter("txtCreateTime2")));
		//****************************���ڿͻ�Ѻ�������Ĳ�ѯ*****************************************
		//SpareStr3:���ü�¼��ˮ��
		if(WebUtil.StringHaveContent(request.getParameter("txtAiNo")) && (!"0".equals(request.getParameter("txtAiNo")))){
			//���и�ʽ���
			if(WebUtil.StringToInt(request.getParameter("txtAiNo"))==0)
				throw new WebActionException("���ü�¼��ˮ�Ÿ�ʽ���󣬱���Ϊ������");
			dto.setSpareStr3(request.getParameter("txtAiNo"));
		}
	    return new QueryCustDepositEJBEvent(dto,pageFrom,pageTo,0);
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
	}
}
