/*
 * 创建日期 2005-11-14
 *
 * 更改所生成文件模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
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
 * 更改所生成类型注释的模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
public class CustomerProblemManualCloseWebAction extends CheckTokenWebAction {

  /* （非 Javadoc）
   * @see com.dtv.oss.web.action.WebAction#perform(javax.servlet.http.HttpServletRequest)
   */
  protected EJBEvent encapsulateData(HttpServletRequest request) throws WebActionException {
    // TODO 自动生成方法存根
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
