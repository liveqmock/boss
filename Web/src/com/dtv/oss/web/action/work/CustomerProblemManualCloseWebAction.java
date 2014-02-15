/*
 * �������� 2005-11-14
 *
 * �����������ļ�ģ��Ϊ
 * ���� > ��ѡ�� > Java > �������� > �����ע��
 */
package com.dtv.oss.web.action.work;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.CustProblemProcessDTO;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.work.RepairEJBEvent;
import com.dtv.oss.web.action.CheckTokenWebAction;
import com.dtv.oss.web.exception.WebActionException;
import com.dtv.oss.web.util.WebUtil;
import java.util.*;
 


/**
 * @author chenjiang
 *
 * ��������������ע�͵�ģ��Ϊ
 * ���� > ��ѡ�� > Java > �������� > �����ע��
 */
public class CustomerProblemManualCloseWebAction extends CheckTokenWebAction {

  /* ���� Javadoc��
   * @see com.dtv.oss.web.action.WebAction#perform(javax.servlet.http.HttpServletRequest)
   */
  protected EJBEvent encapsulateData(HttpServletRequest request) throws WebActionException {
    // TODO �Զ����ɷ������
    Collection col = new ArrayList();
    String strCustomerProblemID[] = request.getParameterValues("ListID");
    for (int i=0;i<strCustomerProblemID.length;i++)
    {
      CustProblemProcessDTO dto = new CustProblemProcessDTO();
      int intCustomerProbemID = WebUtil.StringToInt(strCustomerProblemID[i]);
      dto.setWorkResultReason((String)request.getParameter("txtWorkResultReason"));
      dto.setCustProblemId(intCustomerProbemID);
      dto.setMemo(request.getParameter("txtMemo"));     
      col.add(dto);
    }
    RepairEJBEvent event = new RepairEJBEvent();
    event.setActionType(EJBEvent.MANUAL_CLOSE_REPAIR_SHEET);
    event.setCustProblemProcessDtos(col);
    return event;
  }

}
