/*
 * �������� 2005-10-28
 *
 * �����������ļ�ģ��Ϊ
 * ���� > ��ѡ�� > Java > �������� > �����ע��
 */
package com.dtv.oss.web.action.work;

import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.web.action.CheckTokenWebAction;
import com.dtv.oss.web.exception.WebActionException;
import com.dtv.oss.web.util.WebUtil;
import com.dtv.oss.dto.JobCardDTO;
import com.dtv.oss.service.ejbevent.work.RepairEJBEvent;
 


/**
 * @author Chen jiang
 *
 * ��������������ע�͵�ģ��Ϊ
 * ���� > ��ѡ�� > Java > �������� > �����ע��
 */
public class AssignRepairCreWebAction extends CheckTokenWebAction {

  /* ���� Javadoc��
   * @see com.dtv.oss.web.action.WebAction#perform(javax.servlet.http.HttpServletRequest)
   */
  protected EJBEvent encapsulateData(HttpServletRequest request) throws WebActionException {
    // TODO �Զ����ɷ������
   
    Collection col = new ArrayList();
    RepairEJBEvent repairEjbEvent = new RepairEJBEvent();
    String strCustomerProblemID[] = request.getParameterValues("ListID");
    
    for (int i=0;i<strCustomerProblemID.length;i++)
    {
      JobCardDTO dto = new JobCardDTO();
      int intCustomerProbemID = WebUtil.StringToInt(strCustomerProblemID[i]);
      dto.setReferSheetId(intCustomerProbemID);      
      col.add(dto);
    }
    if (WebUtil.StringHaveContent(request.getParameter("transferType"))){
  	  if(("auto").equalsIgnoreCase(request.getParameter("transferType"))){
  		  System.out.println("******auto*******"+request.getParameter("txtAutoNextOrgID")+"*********");
  		  repairEjbEvent.setNextOrgID(WebUtil.StringToInt(request.getParameter("txtAutoNextOrgID")));
  	  }else if(("manual").equalsIgnoreCase(request.getParameter("transferType"))){
  		  repairEjbEvent.setNextOrgID(WebUtil.StringToInt(request.getParameter("txtNextOrgID")));
  	  }
    }
    repairEjbEvent.setJobCardDtos(col);
    repairEjbEvent.setActionType(EJBEvent.ASSIGN_REPAIR);
    return repairEjbEvent;
  }

}
