package com.dtv.oss.web.flow;
/**
 * 套餐转换时,选择设备,如果有设备要选择,则进入设备选择页面,否则直接进入费用计算,异常进返回产品信息页面.
 */
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.util.Postern;
import com.dtv.oss.web.exception.FlowHandlerException;
import com.dtv.oss.web.util.CommonKeys;
import com.dtv.oss.web.util.WebKeys;

public class SelectTerminalFlowHandler implements FlowHandler{
    public void doStart(HttpServletRequest request) {};

    public String processFlow(HttpServletRequest request) throws FlowHandlerException {
    	try{
    	System.out.println("run SelectTerminalFlowHandler.processFlow()>>>>>>>>>>>>>>>>>>>>>>");
        if (request.getAttribute(WebKeys.EXCEPTION_REQUEST_ATTRIBUTE)!=null) 
        	return "0";
        else {
        	String productList = getProductList(request);
				if (productList != null && !productList.equals("")) {
					Map deviceMap = getDeviceClassInfo(productList);
					if (deviceMap != null && !deviceMap.isEmpty()) {
						request.setAttribute(CommonKeys.TERMINALDEVICESELECT,
								deviceMap);
						request.setAttribute(CommonKeys.SERVICEID,
								getServiceInfo(productList));
				    	System.out.println("run SelectTerminalFlowHandler.return device()>>>>>>>>>>>>>>>>>>>>>>");
						return "2";
					}
				}
        }
    	}catch(Exception e){
    		e.printStackTrace();
    		throw new FlowHandlerException(e.getMessage());
    	}
		return "1";
    }

    public void doEnd(HttpServletRequest request) {};
	private Map getDeviceClassInfo(String productList){
		return  Postern.getDeviceClassMapByProductIDList(productList);
	}
	private String getServiceInfo(String productList){
		return Postern.getServiceIDByProductIDs(productList.replaceAll(",",";"));
	}
	private String getProductList(HttpServletRequest request){
		String[] arrProductID=request.getParameterValues("txtProductID");
		if(arrProductID==null||arrProductID.length==0)return null;
		String strProductID="";
		for(int i=0;arrProductID!=null&&i<arrProductID.length;i++){
			strProductID+=arrProductID[i]+",";
		}
		if(strProductID.endsWith(","))
			strProductID=strProductID.substring(0,strProductID.length()-1);
		
		return strProductID;
	}
}
