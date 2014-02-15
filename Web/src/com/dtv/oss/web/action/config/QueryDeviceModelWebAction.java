package com.dtv.oss.web.action.config;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.DeviceModelDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.config.QueryDeviceModelEJBEvent;
import com.dtv.oss.web.action.QueryWebAction;
import com.dtv.oss.web.util.WebUtil;
/**
 * 设备型号查询
 * @author 260327h
 *
 */
public class QueryDeviceModelWebAction extends QueryWebAction {

	protected EJBEvent encapsulateData(HttpServletRequest request)
			throws Exception {

		DeviceModelDTO dto = new DeviceModelDTO();
		
		//txtOPDeviceModel为修改ID标志,query页没有
		if (WebUtil.StringHaveContent(request.getParameter("txtOPDeviceModel"))) {
			String s=request.getParameter("txtOPDeviceModel");
		//	String code= toUtf8String(s);
			dto.setDeviceModel(s);
			 
		} else {
			if (WebUtil.StringHaveContent(request.getParameter("txtDeviceModelName")))
				dto.setDeviceModel(request.getParameter("txtDeviceModelName"));
			if (WebUtil.StringHaveContent(request.getParameter("txtDeviceClass")))
				dto.setDeviceClass(request.getParameter("txtDeviceClass"));
			if (WebUtil.StringHaveContent(request.getParameter("txtDeviceModelStatus")))
				dto.setStatus(request.getParameter("txtDeviceModelStatus"));
			if (WebUtil.StringHaveContent(request.getParameter("txtProvider")))
				dto.setProviderID(WebUtil.StringToInt(request
						.getParameter("txtProvider")));
		}

		LogUtility.log(this.getClass(), LogLevel.DEBUG, "查询设备型号 dto"
				+ dto.toString());
		return new QueryDeviceModelEJBEvent(dto, pageFrom, pageTo,
				QueryDeviceModelEJBEvent.QUERY_TYPE_LIST_QUERY);
	}
	 public String toUtf8String(String s) {

	    StringBuffer sb = new StringBuffer();

	    for (int i=0;i<s.length();i++) {

	        char c = s.charAt(i);

	        if (c >= 0 && c <= 255) {

	            sb.append(c);

	        } 

	        else {

	            byte[] b;

	            try {

	               b = Character.toString(c).getBytes();

	               //System.out.println("aaaa"  + new String(b,"GB2312"));

	               sb.append(new String(b,"UTF-8"));

	            } 

	            catch (Exception ex) {

	               System.out.println(ex);

	               b = new byte[0];

	            }

	            //for (int j = 0; j < b.length; j++) {

	            //  int k = b[j];

	            //  if (k < 0) k += 256;

	            //  sb.append("%" + Integer.toHexString(k).toUpperCase());

	            //}

	        }

	    }

	    return sb.toString();

	    }

}
