/*
 * 创建日期 2005-10-28
 *
 * 更改所生成文件模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
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
 * 更改所生成类型注释的模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
public class AssignRepairCreWebAction extends CheckTokenWebAction {

  /* （非 Javadoc）
   * @see com.dtv.oss.web.action.WebAction#perform(javax.servlet.http.HttpServletRequest)
   */
  protected EJBEvent encapsulateData(HttpServletRequest request) throws WebActionException {
    // TODO 自动生成方法存根
   
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
