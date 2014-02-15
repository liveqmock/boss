package com.dtv.oss.web.action.customer;

import java.util.ArrayList;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.CpConfigedPropertyDTO;
import com.dtv.oss.dto.ProductPropertyDTO;
import com.dtv.oss.dto.SystemEventDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.csr.CpConfigedPropertyEJBEvent;
import com.dtv.oss.service.util.BusinessUtility;
import com.dtv.oss.web.action.PayFeeWebAction;
import com.dtv.oss.web.exception.WebActionException;
import com.dtv.oss.web.util.CommonKeys;
import com.dtv.oss.web.util.WebUtil;
import com.dtv.oss.util.Postern;
import com.dtv.oss.util.TimestampUtility;

/**
 * 客户产品属性修改
 * @author 260327h
 *
 */
public class CpConfigedPropertyWebAction extends PayFeeWebAction{
	private static final String tagName="productProperty";

	protected EJBEvent encapsulateData(HttpServletRequest request) throws WebActionException
    {
		CpConfigedPropertyEJBEvent ejbEvent =new CpConfigedPropertyEJBEvent();
		ArrayList propertyList=new ArrayList();
		SystemEventDTO sDto=encapsulateSystemEventDto(request);
		ejbEvent.setSystemEventDto(sDto);
		
		String actionType=request.getParameter("txtActionType");
		if(actionType.equalsIgnoreCase("update")){
			ejbEvent.setActionType(CpConfigedPropertyEJBEvent.CPCONFIGEDPROPERTY_UPDATE);
		}
		else if(actionType.equalsIgnoreCase("add")){
			ejbEvent.setActionType(CpConfigedPropertyEJBEvent.CPCONFIGEDPROPERTY_ADD);
		}
		else if(actionType.equalsIgnoreCase("delete")){
			ejbEvent.setActionType(CpConfigedPropertyEJBEvent.CPCONFIGEDPROPERTY_DELETE);
		}else{
			throw new WebActionException("操作动作不明确");
		}
		
		String strPsID=request.getParameter("txtPsID");
		String strPropertyCode=request.getParameter("txtPropertyCode");
		String strPropertyValue=request.getParameter("txtPropertyValue");
		String strProductID=request.getParameter("txtProductID");
		String strPropertyName=request.getParameter("txtPropertyName");
		
		LogUtility.log(this.getClass(),LogLevel.DEBUG,"actionType"+actionType);
		LogUtility.log(this.getClass(),LogLevel.DEBUG,"strPropertyCode"+strPropertyCode);
		LogUtility.log(this.getClass(),LogLevel.DEBUG,"strProductID"+strProductID);
		LogUtility.log(this.getClass(),LogLevel.DEBUG,"strPropertyValue"+strPropertyValue);
		//产品属性,用户名统一性校验 2008-03-25 hjp
		if(actionType.equalsIgnoreCase("update") || actionType.equalsIgnoreCase("add")){
			if(strPropertyCode.equalsIgnoreCase("30002")){
				LogUtility.log(this.getClass(),LogLevel.DEBUG,"strPropertyCode==="+strPropertyCode);
				if(BusinessUtility.getISExistCpConfigedPropertyByPropertyCodeAndValue(strProductID,"30002",strPropertyValue))
					throw new WebActionException(strPropertyName+"重复");
					//throw new WebActionException("用户名重复");
					
			}
		}
		
		CpConfigedPropertyDTO proDto = new CpConfigedPropertyDTO();
		proDto.setPsID(Integer.parseInt(strPsID));
		proDto.setPropertyCode(strPropertyCode);
		proDto.setPropertyValue(strPropertyValue);
		proDto.setDtLastmod(TimestampUtility.getCurrentDate());
		propertyList.add(proDto);
		
		/**
		String nameList=request.getParameter(tagName);
		if(WebUtil.StringHaveContent(nameList)){
			System.out.println("nameList===="+nameList);
			String[] controls=nameList.split(";");
			for (int i = 0; i < controls.length; i++) {
				String[] codes = controls[i].split("_");
				if (codes.length > 0) {
					CpConfigedPropertyDTO dto = new CpConfigedPropertyDTO();
					String propertyCode = codes[1];
					String controlName = tagName + "_" + controls[i];
					String propertyValue = request.getParameter(controlName);
					dto.setPsID(sDto.getPsID());
					dto.setPropertyCode(propertyCode);
					dto.setPropertyValue(propertyValue);
					if(propertyCode.equalsIgnoreCase("30003")){
						String pass=getPasswordString(request);
						if(!pass.equals(""))
							dto.setPropertyValue(pass);
					}
					dto.setDtLastmod(TimestampUtility.getCurrentDate());
					ProductPropertyDTO tmpDto = Postern
							.getProductPropertyDTObyPrimaryKey(sDto
									.getProductID()
									+ "", propertyCode);
					//2008-03-25 hjp 注释此if语句。产品属性的"取值方式"为固定时，也要维护客户产品属性表(t_cpconfigedproperty)表记录
					//if (tmpDto == null || !tmpDto.getPropertyMode().equals(CommonKeys.PRODUCT_PROPERTY_MODE_FIXED)) {
					if (tmpDto == null){
						propertyList.add(dto);
					}
				}
			}
		}
		**/
		
		ejbEvent.setDtoList(propertyList);
		
		return ejbEvent;
    }
	
	private String getPasswordString(HttpServletRequest request){
		String[] pass=request.getParameterValues("passwordFieldName");
		if(pass!=null&&pass.length==2&&pass[0].equals(pass[1])){
			return pass[0];
		}
		return "";
	}
	
	private SystemEventDTO encapsulateSystemEventDto(HttpServletRequest request){
		SystemEventDTO dto = new SystemEventDTO();
		String serviceAccountID=request.getParameter("txtServiceAccountID");
		String accountID=request.getParameter("txtAccountID");
		String customerID=request.getParameter("txtCustomerID");
		String productID=request.getParameter("txtProductID");
		String psID=request.getParameter("txtPsID");
		String serialNo=request.getParameter("txtSerialNo");
		String deviceID=request.getParameter("txtDeviceID");
		
//		LogUtility.log(this.getClass(),LogLevel.DEBUG,"serviceAccountID"+serviceAccountID);
//		LogUtility.log(this.getClass(),LogLevel.DEBUG,"accountID"+accountID);
//		LogUtility.log(this.getClass(),LogLevel.DEBUG,"customerID"+customerID);
//		LogUtility.log(this.getClass(),LogLevel.DEBUG,"productID"+productID);
//		LogUtility.log(this.getClass(),LogLevel.DEBUG,"psID"+psID);
//		LogUtility.log(this.getClass(),LogLevel.DEBUG,"serialNo"+serialNo);
//		LogUtility.log(this.getClass(),LogLevel.DEBUG,"deviceID"+deviceID);
		
		if(WebUtil.StringHaveContent(serviceAccountID)){
			dto.setServiceAccountID(WebUtil.StringToInt(serviceAccountID));
		}
		if(WebUtil.StringHaveContent(accountID)){
			dto.setAccountID(WebUtil.StringToInt(accountID));
		}
		if(WebUtil.StringHaveContent(customerID)){
			dto.setCustomerID(WebUtil.StringToInt(customerID));
		}
		if(WebUtil.StringHaveContent(productID)){
			dto.setProductID(WebUtil.StringToInt(productID));
		}
		if(WebUtil.StringHaveContent(psID)){
			dto.setPsID(WebUtil.StringToInt(psID));
		}
		if(WebUtil.StringHaveContent(serialNo)){
			dto.setScSerialNo(serialNo);
		}
		if(WebUtil.StringHaveContent(deviceID)){
			dto.setScDeviceID(WebUtil.StringToInt(deviceID));
		}
		return dto;
	}
}
