/*
 * 创建日期 2006-1-11
 *
 * 更改所生成文件模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
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
 * 更改所生成类型注释的模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
public class QueryPointGoodsWebAction  extends QueryWebAction {

  /* （非 Javadoc）
   * @see com.dtv.oss.web.action.WebAction#perform(javax.servlet.http.HttpServletRequest)
   */
  protected EJBEvent encapsulateData(HttpServletRequest request) throws WebActionException {
    // TODO 自动生成方法存根
    
  	CommonQueryConditionDTO	dto = new CommonQueryConditionDTO();
  	//设置积分活动id
    dto.setCustomerID(WebUtil.StringToInt(request.getParameter("ListIDValue")));
//  客户当前所有可用积分
    dto.setStreet(WebUtil.StringToInt(request.getParameter("txtCurrentPoints")));
    return new CsrQueryEJBEvent(dto, pageFrom, pageTo, CsrQueryEJBEvent.QUERY_POINTS_GOODS);
    
     
  }

}
