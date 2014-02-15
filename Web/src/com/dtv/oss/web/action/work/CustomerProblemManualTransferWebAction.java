/*
 * �������� 2004-9-2
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


/**
 * @author chen jiang
 *
 * ��������������ע�͵�ģ��Ϊ
 * ���� > ��ѡ�� > Java > �������� > �����ע��
 */
public class CustomerProblemManualTransferWebAction extends CheckTokenWebAction {

  /* ���� Javadoc��
   * @see com.dtv.oss.web.action.WebAction#perform(javax.servlet.http.HttpServletRequest)
   */
  protected EJBEvent encapsulateData(HttpServletRequest request) throws WebActionException {
    // TODO �Զ����ɷ������
  	 
  	CustProblemProcessDTO cppDto = new CustProblemProcessDTO();
  	RepairEJBEvent repEvent = new RepairEJBEvent();
  	cppDto.setCustProblemId(WebUtil.StringToInt(request.getParameter("txtID")));
    cppDto.setNextOrgId(WebUtil.StringToInt(request.getParameter("txtAutoNextOrgID")));
    //cppDto.setWorkResultReason(request.getParameter("txtWorkResultReason"));
    cppDto.setMemo(request.getParameter("txtMemo"));
    repEvent.setActionType(EJBEvent.MANUAL_TRANSFER_REPAIR_SHEET);
    repEvent.setCustProblemProcessDto(cppDto);
    return  repEvent;

  }

}
