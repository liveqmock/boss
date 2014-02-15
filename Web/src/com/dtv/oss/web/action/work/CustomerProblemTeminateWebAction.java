/*
 * �������� 2005-11-14
 *
 * �����������ļ�ģ��Ϊ
 * ���� > ��ѡ�� > Java > �������� > �����ע��
 */
package com.dtv.oss.web.action.work;

import java.util.ArrayList;
import java.util.Collection;
 

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.CustProblemProcessDTO;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.work.RepairEJBEvent;
import com.dtv.oss.web.action.CheckTokenWebAction;
import com.dtv.oss.web.exception.WebActionException;
import com.dtv.oss.web.util.WebUtil;
 


/**
 * @author chenjiang
 *
 * ��������������ע�͵�ģ��Ϊ
 * ���� > ��ѡ�� > Java > �������� > �����ע��
 */
public class CustomerProblemTeminateWebAction extends CheckTokenWebAction {

  /* ���� Javadoc��
   * @see com.dtv.oss.web.action.WebAction#perform(javax.servlet.http.HttpServletRequest)
   */
  protected EJBEvent encapsulateData(HttpServletRequest request) throws WebActionException {
    // TODO �Զ����ɷ������
    
  	  Collection col= new ArrayList();
      CustProblemProcessDTO dto = new CustProblemProcessDTO();
      int intCustomerProbemID = WebUtil.StringToInt(request.getParameter("txtId"));
      dto.setWorkResultReason((String)request.getParameter("txtWorkResultReason"));
      dto.setCustProblemId(intCustomerProbemID);
      col.add(dto);
      RepairEJBEvent event = new RepairEJBEvent();
      event.setActionType(EJBEvent.TERMINATE_REPAIR_INFO);
      event.setCustProblemProcessDtos(col);
      return event;
  }

}
