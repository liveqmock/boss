/*
 * 创建日期 2004-9-2
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


/**
 * @author chen jiang
 *
 * 更改所生成类型注释的模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
public class CustomerProblemManualTransferWebAction extends CheckTokenWebAction {

  /* （非 Javadoc）
   * @see com.dtv.oss.web.action.WebAction#perform(javax.servlet.http.HttpServletRequest)
   */
  protected EJBEvent encapsulateData(HttpServletRequest request) throws WebActionException {
    // TODO 自动生成方法存根
  	 
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
