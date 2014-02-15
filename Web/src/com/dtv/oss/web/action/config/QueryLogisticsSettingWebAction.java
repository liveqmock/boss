package com.dtv.oss.web.action.config;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.LogisticsSettingDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.commandresponse.CommandResponse;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.config.QueryLogisticsSettingEJBEvent;
import com.dtv.oss.web.action.QueryWebAction;
/**
 * 设备管理配置查询
 * @author 260327h
 *
 */
public class QueryLogisticsSettingWebAction extends QueryWebAction {

	protected EJBEvent encapsulateData(HttpServletRequest request)
			throws Exception {

		LogisticsSettingDTO dto=new LogisticsSettingDTO();
		return new QueryLogisticsSettingEJBEvent(dto,0,0,0);
				//QueryDeviceModelEJBEvent.QUERY_TYPE_LIST_QUERY);
	}
    protected void afterSuccessfulResponse(HttpServletRequest request, CommandResponse cmdResponse){
    	List list=(List) cmdResponse.getPayload();
    	
		LogUtility.log(this.getClass(),LogLevel.DEBUG,"设置新的list\n"+list);
		
    	if(list.isEmpty()){
    		list.add(new LogisticsSettingDTO());
			LogUtility.log(this.getClass(),LogLevel.DEBUG,"list\n"+list);
    	}
    	super.afterSuccessfulResponse(request,cmdResponse);
    }

}
