package com.dtv.oss.web.action.customer;

import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.CustomerDTO;
import com.dtv.oss.dto.ProtocolDTO;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.csr.CustomerEJBEvent;
import com.dtv.oss.web.action.GeneralWebAction;
import com.dtv.oss.web.exception.WebActionException;
import com.dtv.oss.web.util.WebUtil;

public class CustomerPackageProtocolWebAction extends GeneralWebAction {
	boolean confirmPost = false;

    protected boolean needCheckToken() {
	    return confirmPost;
	}
    
	public void doStart(HttpServletRequest request) {
		super.doStart(request);

		confirmPost = false;
		if (WebUtil.StringHaveContent(request.getParameter("confirm_post"))) {
			if (request.getParameter("confirm_post").equalsIgnoreCase("true"))
				confirmPost = true;
		}
	}

	protected EJBEvent encapsulateData(HttpServletRequest request)
			throws WebActionException {
		String[] packageId =request.getParameterValues("packageId");
		String[] brfeeId =request.getParameterValues("brfeeId");
		String[] feeTypeId =request.getParameterValues("feeTypeId");
		String[] singlePrice =request.getParameterValues("singlePrice");
		CustomerEJBEvent event=new CustomerEJBEvent(EJBEvent.CUSTOMER_PROTOCOL);
		String txtCustomerID =request.getParameter("txtCustomerID");
        if (txtCustomerID ==null || txtCustomerID.equals("")){
        	throw new WebActionException("没有传入客户ID，请与管理员联系！");
        }
        
		if (packageId !=null){
			Collection list =new ArrayList();
		    Collection packageIdCol =new ArrayList();
			for(int i=0;i<packageId.length;i++){
				System.out.println("packageId---------->"+packageId[i]);
				System.out.println("brfeeId---------->"+brfeeId[i]);
				System.out.println("feeTypeId---------->"+feeTypeId[i]);
				System.out.println("singlePrice---------->"+singlePrice[i]);
				
				if (packageId[i]==null || packageId[i].equals("")) continue;
				
				if  (packageIdCol.contains(packageId[i])){
					throw new WebActionException("协议产品包重复，请重新选择！");
				}else{
					packageIdCol.add(packageId[i]);
				}
				ProtocolDTO dto =new ProtocolDTO();
				dto.setCustomerID(WebUtil.StringToInt(txtCustomerID));
				dto.setAcctitemTypeID(feeTypeId[i]);
				dto.setFeeType(brfeeId[i]);
				dto.setProductPackageID(WebUtil.StringToInt(packageId[i]));
				dto.setSinglePrice(WebUtil.StringTodouble(singlePrice[i]));
				dto.setStatus("V");
				list.add(dto);
			}
			CustomerDTO custDto =new CustomerDTO();
			custDto.setCustomerID(WebUtil.StringToInt(txtCustomerID));
			event.setProtocolList(list);
			event.setCustomerDTO(custDto);
		}else{
			throw new WebActionException("没有产品包，不能创建和维护协议价");
		}
		return event;
	}

}
