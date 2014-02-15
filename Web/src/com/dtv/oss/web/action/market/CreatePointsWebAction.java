/*
 * 创建日期 2004-8-28
 *
 * 更改所生成文件模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
package com.dtv.oss.web.action.market;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.CustomerDTO;
import com.dtv.oss.dto.UserPointsExchangeRuleDTO;
import com.dtv.oss.dto.UserPointsExchangerCdDTO;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.market.UserPointEJBEvent;
import com.dtv.oss.web.action.CheckTokenWebAction;
import com.dtv.oss.web.exception.WebActionException;
import com.dtv.oss.web.util.WebUtil;
 


/**
 * @author Chen Jiang
 *
 * 更改所生成类型注释的模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
public class CreatePointsWebAction extends CheckTokenWebAction {

  /* （非 Javadoc）
   * @see com.dtv.oss.web.action.WebAction#perform(javax.servlet.http.HttpServletRequest)
   */
  protected EJBEvent encapsulateData(HttpServletRequest request) throws WebActionException {
    // TODO 自动生成方法存根
    CustomerDTO custDto = new CustomerDTO();
    UserPointsExchangeRuleDTO pointsRuleDto =new  UserPointsExchangeRuleDTO();
    //纪录dto
    UserPointsExchangerCdDTO  recordDto = new UserPointsExchangerCdDTO();
    
     //设置客户ID
    custDto.setCustomerID(WebUtil.StringToInt(request.getParameter("txtCustomerID")));
   
    //设置当前客户的可用积分
    custDto.setCurrentAccumulatePoint(WebUtil.StringToInt(request.getParameter("txtCurrentPoints")));
    //设置活动ID
    pointsRuleDto.setActivityId(WebUtil.StringToInt(request.getParameter("txtActivityId")));
    //设置兑换货物ID
    pointsRuleDto.setExchangeGoodsTypeId(WebUtil.StringToInt(request.getParameter("ListIDGoodValue")));
    //
    custDto.setCustomerType(request.getParameter("txtCustomerType"));
    UserPointEJBEvent userEjbEvent = new UserPointEJBEvent();
    userEjbEvent.setActionType(EJBEvent.USERPOINTS_EXCHANGE);
    userEjbEvent.setCustDto(custDto);
    userEjbEvent.setUserPointsExchRuleDto(pointsRuleDto);
    return userEjbEvent;
  }

}
