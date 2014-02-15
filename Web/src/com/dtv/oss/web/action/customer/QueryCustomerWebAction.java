package com.dtv.oss.web.action.customer;

/**
 * <p>Title: BOSS</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: Digivision</p>
 * @author Horace Lin
 * @version 1.0
 */

import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.CustomerDTO;
import com.dtv.oss.dto.custom.CommonQueryConditionDTO;
import com.dtv.oss.dto.wrap.customer.Customer2AddressWrap;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.csr.CsrQueryEJBEvent;
import com.dtv.oss.service.util.CommonConstDefinition;
import com.dtv.oss.util.DynamicServey;
import com.dtv.oss.util.Postern;
import com.dtv.oss.web.action.QueryGeneralCustomerWebAction;
import com.dtv.oss.web.exception.WebActionException;
import com.dtv.oss.web.util.CommonKeys;
import com.dtv.oss.web.util.CurrentCustomer;
import com.dtv.oss.web.util.WebUtil;

/**
 * 此action被调用的地方:
 * 1,客户查询
 * 2,客户明细,
 * 3,客户树,客户明细,
 * 4,取消客户查询,
 * 5,取消客户明细,
 * 6,取消客户,客户树,客户明细,
 * 7,集团客户查询,
 * 8,....
 * 1-6,需要根据客户状态来判断,
 * 其中1-3,页面不传入客户状态(客户状态多样),需要从session中取,区分取消还是非取消客户查询,避免查出取消状态客户.
 * 当页面没有设置,且session中客户状态为非取消时,传一个boolean值到listhandler中,标志当前查找的
 * 非取消状态客户,
 * 当页面有明确客户状态设置,则直接传到listhandler,做为查询条件拼接,
 * 其中4-6查询页面有明确客户状态设置,直接拼接到查询条件.
 * 7-N,需要从session中取'SET_G_SYSTEMPLATFORM'属性来判断,
 * 
 * 从session中取客户状态,客户查询/销户客户查询/客户树客户资料都用到这个action,前两个页面上设置了status,客户资料上没有,会丢失查询结果,
 * 当页面上没有明确的status设置的时候,尝试从session中获取
 * 2007-4-6侯瑞军增加,解决1客户查询>2客户信息(正常客户)>3回退>4客户信息(潜在客户)时,显示不正常,
 * 异常出现原因:直接回退时,session没有清除,回退到查询列表,再打开客户信息时,此action会从session中取客户状态,
 *  
 */
public class QueryCustomerWebAction extends QueryGeneralCustomerWebAction {
    protected int getCurrentCustomerID(Object obj) {
        //if return 0,
        if (obj == null) return 0;

        if (!(obj instanceof Customer2AddressWrap)) return 0;

        Customer2AddressWrap wrap = (Customer2AddressWrap) obj;

        return wrap.getCustDto().getCustomerID();


    }

    private String getCustomerStatus(HttpServletRequest request){
    	CustomerDTO custDTO=CurrentCustomer.getCurrentCustomerDTO(request.getSession());
    	if(custDTO!=null){
    		return custDTO.getStatus();
    	}
    	return null;
    }
    protected EJBEvent encapsulateData(HttpServletRequest request) throws WebActionException {
    	
    	
        CommonQueryConditionDTO theDTO = new CommonQueryConditionDTO();
        
		//以下内容用于大客户受理单查询,
		String systemPlatform = (String) request.getSession().getAttribute(
				"SET_G_SYSTEMPLATFORM");
		if (WebUtil.StringHaveContent(systemPlatform)&&"F".equals(systemPlatform)) {
			System.out
					.println("SET_G_SYSTEMPLATFORM============="
							+ systemPlatform);
			theDTO.setSpareStr18(CommonConstDefinition.CUSTOMERSTYLE_GROUP);
		}else{
			theDTO.setSpareStr18(CommonConstDefinition.CUSTOMERSTYLE_SINGLE);
		}
		
	    

        //客户证号
        if (WebUtil.StringHaveContent(request.getParameter("txtCustomerID")))
            theDTO.setCustomerID(WebUtil.StringToInt(request.getParameter("txtCustomerID") ) );
        //客户条形码
        if (WebUtil.StringHaveContent(request.getParameter("txtCustomerSerialNo")))
            theDTO.setSpareStr27(request.getParameter("txtCustomerSerialNo"));
        	//theDTO.setNo(request.getParameter("txtCustomerID"));
        //客户姓名
        if (WebUtil.StringHaveContent(request.getParameter("txtName")))
            theDTO.setSpareStr1(request.getParameter("txtName"));
        //安装地址
        if (WebUtil.StringHaveContent(request.getParameter("txtDetailAddress")))
            theDTO.setSpareStr3(request.getParameter("txtDetailAddress"));
        //来源渠道
        if (WebUtil.StringHaveContent(request.getParameter("txtOpenSourceType")))
            theDTO.setSpareStr13(request.getParameter("txtOpenSourceType"));
        //证件号
        if (WebUtil.StringHaveContent(request.getParameter("txtCardID")))
            theDTO.setSpareStr5(request.getParameter("txtCardID"));
        //社保卡号
        if (WebUtil.StringHaveContent(request.getParameter("txtSocialSecCardID")))
            theDTO.setSpareStr6(request.getParameter("txtSocialSecCardID"));
        //固定电话
        if (WebUtil.StringHaveContent(request.getParameter("txtTelephone")))
            theDTO.setSpareStr7(request.getParameter("txtTelephone"));
        //移动电话
        if (WebUtil.StringHaveContent(request.getParameter("txtTelephoneMobile")))
            theDTO.setSpareStr8(request.getParameter("txtTelephoneMobile"));
        //付费方式
        if (WebUtil.StringHaveContent(request.getParameter("txtMopID")))
            theDTO.setSpareStr9(request.getParameter("txtMopID"));
        //银行账号
        if (WebUtil.StringHaveContent(request.getParameter("txtBankAccount")))
            theDTO.setSpareStr10(request.getParameter("txtBankAccount"));
        
        //add by jason 2006-7-3 
        //Mac地址
        if (WebUtil.StringHaveContent(request.getParameter("txtMacAddress")))
            theDTO.setSpareStr11(request.getParameter("txtMacAddress"));
        //内部Mac地址 
        if (WebUtil.StringHaveContent(request.getParameter("txtInterMacAddress")))
            theDTO.setSpareStr12(request.getParameter("txtInterMacAddress"));
        //end 
        
        //add by jason 2006-12-12
        //设备序列号
        if (WebUtil.StringHaveContent(request.getParameter("txtSerialNo")))
            theDTO.setSpareStr20(request.getParameter("txtSerialNo").toUpperCase());
        //设备序列号 精确查询
        if (WebUtil.StringHaveContent(request.getParameter("txtSerialNo1")))
        	theDTO.setSpareStr32(request.getParameter("txtSerialNo1").toUpperCase());
        
        // 服务号
        if (WebUtil.StringHaveContent(request.getParameter("txtServiceCode")))
        	theDTO.setSpareStr14(request.getParameter("txtServiceCode"));
        //区域
        if (WebUtil.StringHaveContent(request.getParameter("txtDistrictID")))
        	theDTO.setSpareStr15(request.getParameter("txtDistrictID"));
        //组织
        if (WebUtil.StringHaveContent(request.getParameter("txtOrgID")))
        	theDTO.setSpareStr16(request.getParameter("txtOrgID"));
        
         //客户类型
        if (WebUtil.StringHaveContent(request.getParameter("txtCustomerType")))
            theDTO.setType(request.getParameter("txtCustomerType"));
        //客户状态
        if (WebUtil.StringHaveContent(request.getParameter("txtStatus")))
            theDTO.setStatus(request.getParameter("txtStatus"));
        System.out.println("theDTO.setStatus>>>>>>>>>>>"+theDTO.getStatus());
        //取session中的客户状态,如果不空,则设置为当前查询要用的客户状态,
        String sessionCustomerStatus=getCustomerStatus(request);
        if (WebUtil.StringHaveContent(sessionCustomerStatus)
				&& !WebUtil.StringHaveContent(theDTO.getStatus())
				&& CommonKeys.CUSTOMER_STATUS_CANCEL
						.equals(sessionCustomerStatus)) {
			theDTO.setSpareBoolean(true);
		}

        //创建日期
        if (WebUtil.StringHaveContent(request.getParameter("txtStartDate")))
            theDTO.setBeginTime(WebUtil.StringToTimestampWithDayBegin(request.getParameter("txtStartDate")));
        if (WebUtil.StringHaveContent(request.getParameter("txtEndDate")))
            theDTO.setEndTime(WebUtil.StringToTimestampWithDayEnd(request.getParameter("txtEndDate")));

        if (WebUtil.StringHaveContent(request.getParameter("txtCustomerStyle")))
        	theDTO.setSpareStr2(request.getParameter("txtCustomerStyle"));
        if (WebUtil.StringHaveContent(request.getParameter("txtGroupCustID")))
        	theDTO.setSpareStr4(request.getParameter("txtGroupCustID"));
        if (WebUtil.StringHaveContent(request.getParameter("txtContractNo")))
        	theDTO.setSpareStr17(request.getParameter("txtContractNo"));
        if (WebUtil.StringHaveContent(request.getParameter("txtCatvID")))
        	theDTO.setSpareStr21(request.getParameter("txtCatvID"));
        
        //精确查询
        if (WebUtil.StringHaveContent(request.getParameter("txtCatvID1")))
              theDTO.setSpareStr33(request.getParameter("txtCatvID1"));
        
        //注销用户时间
        if (WebUtil.StringHaveContent(request.getParameter("txtCancleStartDate")))
            theDTO.setSpareStr22(request.getParameter("txtCancleStartDate"));
        if (WebUtil.StringHaveContent(request.getParameter("txtCancleEndDate")))
            theDTO.setSpareStr23(request.getParameter("txtCancleEndDate"));
        
        //  客户市场信息
        theDTO.setSpareStr19(getCustIdByMarketInfo(request));

        //合同号
        if(WebUtil.StringHaveContent(request.getParameter("txtContractNO")))
        	theDTO.setSpareStr26(request.getParameter("txtContractNO"));
        //帐户地址有效性
        if (WebUtil.StringHaveContent(request.getParameter("txtBillAddressFlag")))
        	theDTO.setSpareStr24(request.getParameter("txtBillAddressFlag"));
        // 退信原因
        if (WebUtil.StringHaveContent(request.getParameter("txtInvalidAddressReason")))
        	theDTO.setSpareStr25(request.getParameter("txtInvalidAddressReason"));
        //业务类型
        if (WebUtil.StringHaveContent(request.getParameter("txtServiceID")))
        	theDTO.setSpareStr28(request.getParameter("txtServiceID"));
        //宽带用户名
        if (WebUtil.StringHaveContent(request.getParameter("txtBroadbandName")))
        	theDTO.setSpareStr29(request.getParameter("txtBroadbandName"));
        
        String[] indexStr=request.getParameterValues("Index");
		String custids=null;
        if(indexStr!=null){
        	for(int i=0;i<indexStr.length;i++){
        		if(i==0){
        			custids=indexStr[i];
        		}else{
        			custids=custids+";"+indexStr[i];
        		}
        	}
        } 
        theDTO.setSpareStr31(custids);

        
        theDTO.setOrderField(orderByFieldName);
        theDTO.setIsAsc(!orderDescFlag);

        
        return new CsrQueryEJBEvent(theDTO, pageFrom, pageTo, CsrQueryEJBEvent.QUERY_TYPE_CUSTOMER);
        //return null;
        
       

    }
    
    private String getCustIdByMarketInfo(HttpServletRequest request) throws WebActionException{

    	Collection marketInfo=DynamicServey.getNewCustomerMarketInfo(request, "txtDynamicServey");
		String customerIds = Postern
		.getCustomerIdsBycustomerMarketInfo(marketInfo);	
		System.out
		.println("getCustomerIdsBycustomerMarketInfo===customerIds=="+customerIds);
		return customerIds;
	}
    
    
//	注意：该方法的作用是暂时的，强制返回，为了页面的流转
/*    public void doEnd(HttpServletRequest request, CommandResponse cmdResponse)
    {
    	WebPrint.PrintDebugInfo(this.getClass().getName(), "doEnd method enter ...");

        //check whether some exceptions are happened
        if (request.getAttribute(WebKeys.EXCEPTION_REQUEST_ATTRIBUTE)!=null)
        {
            afterExceptionHappen(request);
            WebPrint.PrintDebugInfo(this.getClass().getName(), "doEnd Exception out");
            return;
        }
        
    	ArrayList list = new ArrayList();
		Customer2AddressWrap wrap = new Customer2AddressWrap();
		Customer2AddressWrap wrap2 = new Customer2AddressWrap();
		CustomerDTO custDTO = new CustomerDTO();
		AddressDTO  addrDTO = new AddressDTO();
		CustomerDTO custDTO2 = new CustomerDTO();
		AddressDTO  addrDTO2 = new AddressDTO();
		
		custDTO.setName("MyName");
		addrDTO.setDetailAddress( "111111");
		
		wrap.setAddrDto( addrDTO);
		wrap.setCustDto( custDTO);
		
		list.add(wrap);
		
		custDTO2.setName("MyName2");
		addrDTO2.setDetailAddress("222222");
		
		wrap2.setAddrDto( addrDTO);
		wrap2.setCustDto( custDTO);
		
		list.add(wrap2);
		
		if(cmdResponse != null)
			cmdResponse.setPayload( list);
		else
			cmdResponse = new CommandResponse(list);
	
        request.setAttribute("ResponseQueryResult", cmdResponse);
        afterSuccessfulResponse(request, cmdResponse);
        
        WebPrint.PrintDebugInfo(this.getClass().getName(), "doEnd out");

      }
*/
}