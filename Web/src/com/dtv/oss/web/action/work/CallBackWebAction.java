/*
 * 创建日期 2005-11-10
 *
 * 更改所生成文件模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
package com.dtv.oss.web.action.work;

import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

 
import com.dtv.oss.dto.CallBackInfoDTO;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.csr.CallBackInfoEJBEvent;
import com.dtv.oss.util.DynamicServey;
 
import com.dtv.oss.web.action.CheckTokenWebAction;
import com.dtv.oss.web.exception.WebActionException;
import com.dtv.oss.web.util.WebUtil;
 
 

/**
 * @author chenjiang
 *
 * 更改所生成类型注释的模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
public class CallBackWebAction extends CheckTokenWebAction {

  /* （非 Javadoc）
   * @see com.dtv.oss.web.action.WebAction#perform(javax.servlet.http.HttpServletRequest)
   */
  protected EJBEvent encapsulateData(HttpServletRequest request) throws WebActionException {
    // TODO 自动生成方法存根
    
  	CallBackInfoEJBEvent  callBackEvent = new CallBackInfoEJBEvent(EJBEvent.CALL_CUSTOMER_FOR_REPAIR);
  	callBackEvent.setCustProblemID(WebUtil.StringToInt(request.getParameter("txtId")));
   
  	Collection	callBackInfoList =DynamicServey.getCallBackInfo(request,"txtDynamicServey","",0);
    
    callBackEvent.setCallBackInfoList(callBackInfoList);  
    return   callBackEvent;
  }

}
