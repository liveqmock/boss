/*
 * �������� 2004-8-28
 *
 * �����������ļ�ģ��Ϊ
 * ���� > ��ѡ�� > Java > �������� > �����ע��
 */
package com.dtv.oss.web.action.market;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.CustomerDTO;
import com.dtv.oss.dto.UserPointsExchangeRuleDTO;
import com.dtv.oss.dto.UserPointsExchangerCdDTO;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.market.UserPointEJBEvent;
import com.dtv.oss.web.action.CheckTokenWebAction;
import com.dtv.oss.web.exception.WebActionException;
import com.dtv.oss.web.util.WebUtil;
 


/**
 * @author Chen Jiang
 *
 * ��������������ע�͵�ģ��Ϊ
 * ���� > ��ѡ�� > Java > �������� > �����ע��
 */
public class CreatePointsWebAction extends CheckTokenWebAction {

  /* ���� Javadoc��
   * @see com.dtv.oss.web.action.WebAction#perform(javax.servlet.http.HttpServletRequest)
   */
  protected EJBEvent encapsulateData(HttpServletRequest request) throws WebActionException {
    // TODO �Զ����ɷ������
    CustomerDTO custDto = new CustomerDTO();
    UserPointsExchangeRuleDTO pointsRuleDto =new  UserPointsExchangeRuleDTO();
    //��¼dto
    UserPointsExchangerCdDTO  recordDto = new UserPointsExchangerCdDTO();
    
     //���ÿͻ�ID
    custDto.setCustomerID(WebUtil.StringToInt(request.getParameter("txtCustomerID")));
   
    //���õ�ǰ�ͻ��Ŀ��û���
    custDto.setCurrentAccumulatePoint(WebUtil.StringToInt(request.getParameter("txtCurrentPoints")));
    //���ûID
    pointsRuleDto.setActivityId(WebUtil.StringToInt(request.getParameter("txtActivityId")));
    //���öһ�����ID
    pointsRuleDto.setExchangeGoodsTypeId(WebUtil.StringToInt(request.getParameter("ListIDGoodValue")));
    //
    custDto.setCustomerType(request.getParameter("txtCustomerType"));
    UserPointEJBEvent userEjbEvent = new UserPointEJBEvent();
    userEjbEvent.setActionType(EJBEvent.USERPOINTS_EXCHANGE);
    userEjbEvent.setCustDto(custDto);
    userEjbEvent.setUserPointsExchRuleDto(pointsRuleDto);
    return userEjbEvent;
  }

}
