/*
 * �������� 2006-1-11
 *
 * �����������ļ�ģ��Ϊ
 * ���� > ��ѡ�� > Java > �������� > �����ע��
 */
package com.dtv.oss.web.action.market;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.custom.CommonQueryConditionDTO;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.csr.CsrQueryEJBEvent;
import com.dtv.oss.web.action.QueryWebAction;
import com.dtv.oss.web.exception.WebActionException;
import com.dtv.oss.web.util.WebUtil;
 


/**
 * @author chen jiang
 *
 * ��������������ע�͵�ģ��Ϊ
 * ���� > ��ѡ�� > Java > �������� > �����ע��
 */
public class QueryPointGoodsWebAction  extends QueryWebAction {

  /* ���� Javadoc��
   * @see com.dtv.oss.web.action.WebAction#perform(javax.servlet.http.HttpServletRequest)
   */
  protected EJBEvent encapsulateData(HttpServletRequest request) throws WebActionException {
    // TODO �Զ����ɷ������
    
  	CommonQueryConditionDTO	dto = new CommonQueryConditionDTO();
  	//���û��ֻid
    dto.setCustomerID(WebUtil.StringToInt(request.getParameter("ListIDValue")));
//  �ͻ���ǰ���п��û���
    dto.setStreet(WebUtil.StringToInt(request.getParameter("txtCurrentPoints")));
    return new CsrQueryEJBEvent(dto, pageFrom, pageTo, CsrQueryEJBEvent.QUERY_POINTS_GOODS);
    
     
  }

}
