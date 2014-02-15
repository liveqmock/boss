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
 * ��action�����õĵط�:
 * 1,�ͻ���ѯ
 * 2,�ͻ���ϸ,
 * 3,�ͻ���,�ͻ���ϸ,
 * 4,ȡ���ͻ���ѯ,
 * 5,ȡ���ͻ���ϸ,
 * 6,ȡ���ͻ�,�ͻ���,�ͻ���ϸ,
 * 7,���ſͻ���ѯ,
 * 8,....
 * 1-6,��Ҫ���ݿͻ�״̬���ж�,
 * ����1-3,ҳ�治����ͻ�״̬(�ͻ�״̬����),��Ҫ��session��ȡ,����ȡ�����Ƿ�ȡ���ͻ���ѯ,������ȡ��״̬�ͻ�.
 * ��ҳ��û������,��session�пͻ�״̬Ϊ��ȡ��ʱ,��һ��booleanֵ��listhandler��,��־��ǰ���ҵ�
 * ��ȡ��״̬�ͻ�,
 * ��ҳ������ȷ�ͻ�״̬����,��ֱ�Ӵ���listhandler,��Ϊ��ѯ����ƴ��,
 * ����4-6��ѯҳ������ȷ�ͻ�״̬����,ֱ��ƴ�ӵ���ѯ����.
 * 7-N,��Ҫ��session��ȡ'SET_G_SYSTEMPLATFORM'�������ж�,
 * 
 * ��session��ȡ�ͻ�״̬,�ͻ���ѯ/�����ͻ���ѯ/�ͻ����ͻ����϶��õ����action,ǰ����ҳ����������status,�ͻ�������û��,�ᶪʧ��ѯ���,
 * ��ҳ����û����ȷ��status���õ�ʱ��,���Դ�session�л�ȡ
 * 2007-4-6���������,���1�ͻ���ѯ>2�ͻ���Ϣ(�����ͻ�)>3����>4�ͻ���Ϣ(Ǳ�ڿͻ�)ʱ,��ʾ������,
 * �쳣����ԭ��:ֱ�ӻ���ʱ,sessionû�����,���˵���ѯ�б�,�ٴ򿪿ͻ���Ϣʱ,��action���session��ȡ�ͻ�״̬,
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
        
		//�����������ڴ�ͻ�������ѯ,
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
		
	    

        //�ͻ�֤��
        if (WebUtil.StringHaveContent(request.getParameter("txtCustomerID")))
            theDTO.setCustomerID(WebUtil.StringToInt(request.getParameter("txtCustomerID") ) );
        //�ͻ�������
        if (WebUtil.StringHaveContent(request.getParameter("txtCustomerSerialNo")))
            theDTO.setSpareStr27(request.getParameter("txtCustomerSerialNo"));
        	//theDTO.setNo(request.getParameter("txtCustomerID"));
        //�ͻ�����
        if (WebUtil.StringHaveContent(request.getParameter("txtName")))
            theDTO.setSpareStr1(request.getParameter("txtName"));
        //��װ��ַ
        if (WebUtil.StringHaveContent(request.getParameter("txtDetailAddress")))
            theDTO.setSpareStr3(request.getParameter("txtDetailAddress"));
        //��Դ����
        if (WebUtil.StringHaveContent(request.getParameter("txtOpenSourceType")))
            theDTO.setSpareStr13(request.getParameter("txtOpenSourceType"));
        //֤����
        if (WebUtil.StringHaveContent(request.getParameter("txtCardID")))
            theDTO.setSpareStr5(request.getParameter("txtCardID"));
        //�籣����
        if (WebUtil.StringHaveContent(request.getParameter("txtSocialSecCardID")))
            theDTO.setSpareStr6(request.getParameter("txtSocialSecCardID"));
        //�̶��绰
        if (WebUtil.StringHaveContent(request.getParameter("txtTelephone")))
            theDTO.setSpareStr7(request.getParameter("txtTelephone"));
        //�ƶ��绰
        if (WebUtil.StringHaveContent(request.getParameter("txtTelephoneMobile")))
            theDTO.setSpareStr8(request.getParameter("txtTelephoneMobile"));
        //���ѷ�ʽ
        if (WebUtil.StringHaveContent(request.getParameter("txtMopID")))
            theDTO.setSpareStr9(request.getParameter("txtMopID"));
        //�����˺�
        if (WebUtil.StringHaveContent(request.getParameter("txtBankAccount")))
            theDTO.setSpareStr10(request.getParameter("txtBankAccount"));
        
        //add by jason 2006-7-3 
        //Mac��ַ
        if (WebUtil.StringHaveContent(request.getParameter("txtMacAddress")))
            theDTO.setSpareStr11(request.getParameter("txtMacAddress"));
        //�ڲ�Mac��ַ 
        if (WebUtil.StringHaveContent(request.getParameter("txtInterMacAddress")))
            theDTO.setSpareStr12(request.getParameter("txtInterMacAddress"));
        //end 
        
        //add by jason 2006-12-12
        //�豸���к�
        if (WebUtil.StringHaveContent(request.getParameter("txtSerialNo")))
            theDTO.setSpareStr20(request.getParameter("txtSerialNo").toUpperCase());
        //�豸���к� ��ȷ��ѯ
        if (WebUtil.StringHaveContent(request.getParameter("txtSerialNo1")))
        	theDTO.setSpareStr32(request.getParameter("txtSerialNo1").toUpperCase());
        
        // �����
        if (WebUtil.StringHaveContent(request.getParameter("txtServiceCode")))
        	theDTO.setSpareStr14(request.getParameter("txtServiceCode"));
        //����
        if (WebUtil.StringHaveContent(request.getParameter("txtDistrictID")))
        	theDTO.setSpareStr15(request.getParameter("txtDistrictID"));
        //��֯
        if (WebUtil.StringHaveContent(request.getParameter("txtOrgID")))
        	theDTO.setSpareStr16(request.getParameter("txtOrgID"));
        
         //�ͻ�����
        if (WebUtil.StringHaveContent(request.getParameter("txtCustomerType")))
            theDTO.setType(request.getParameter("txtCustomerType"));
        //�ͻ�״̬
        if (WebUtil.StringHaveContent(request.getParameter("txtStatus")))
            theDTO.setStatus(request.getParameter("txtStatus"));
        System.out.println("theDTO.setStatus>>>>>>>>>>>"+theDTO.getStatus());
        //ȡsession�еĿͻ�״̬,�������,������Ϊ��ǰ��ѯҪ�õĿͻ�״̬,
        String sessionCustomerStatus=getCustomerStatus(request);
        if (WebUtil.StringHaveContent(sessionCustomerStatus)
				&& !WebUtil.StringHaveContent(theDTO.getStatus())
				&& CommonKeys.CUSTOMER_STATUS_CANCEL
						.equals(sessionCustomerStatus)) {
			theDTO.setSpareBoolean(true);
		}

        //��������
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
        
        //��ȷ��ѯ
        if (WebUtil.StringHaveContent(request.getParameter("txtCatvID1")))
              theDTO.setSpareStr33(request.getParameter("txtCatvID1"));
        
        //ע���û�ʱ��
        if (WebUtil.StringHaveContent(request.getParameter("txtCancleStartDate")))
            theDTO.setSpareStr22(request.getParameter("txtCancleStartDate"));
        if (WebUtil.StringHaveContent(request.getParameter("txtCancleEndDate")))
            theDTO.setSpareStr23(request.getParameter("txtCancleEndDate"));
        
        //  �ͻ��г���Ϣ
        theDTO.setSpareStr19(getCustIdByMarketInfo(request));

        //��ͬ��
        if(WebUtil.StringHaveContent(request.getParameter("txtContractNO")))
        	theDTO.setSpareStr26(request.getParameter("txtContractNO"));
        //�ʻ���ַ��Ч��
        if (WebUtil.StringHaveContent(request.getParameter("txtBillAddressFlag")))
        	theDTO.setSpareStr24(request.getParameter("txtBillAddressFlag"));
        // ����ԭ��
        if (WebUtil.StringHaveContent(request.getParameter("txtInvalidAddressReason")))
        	theDTO.setSpareStr25(request.getParameter("txtInvalidAddressReason"));
        //ҵ������
        if (WebUtil.StringHaveContent(request.getParameter("txtServiceID")))
        	theDTO.setSpareStr28(request.getParameter("txtServiceID"));
        //����û���
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
    
    
//	ע�⣺�÷�������������ʱ�ģ�ǿ�Ʒ��أ�Ϊ��ҳ�����ת
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