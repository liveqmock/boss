package com.dtv.oss.web.action.customer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.dtv.oss.web.action.GeneralWebAction;
import com.dtv.oss.web.action.PayFeeWebAction;
import com.dtv.oss.web.exception.WebActionException;
import com.dtv.oss.web.util.CommonKeys;
import com.dtv.oss.web.util.WebKeys;
import com.dtv.oss.web.util.WebUtil;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.csr.AccountEJBEvent;
import com.dtv.oss.service.util.CommonConstDefinition;
import com.dtv.oss.service.commandresponse.CommandResponse;
import com.dtv.oss.service.commandresponse.CommandResponseImp;
import com.dtv.oss.service.component.ImmediateCountFeeInfo;
import com.dtv.oss.dto.AccountDTO;
import com.dtv.oss.dto.AccountItemDTO;
import com.dtv.oss.dto.AddressDTO;
import com.dtv.oss.dto.CustServiceInteractionDTO;
import com.dtv.oss.dto.PrepaymentDeductionRecordDTO;

import javax.servlet.http.HttpServletRequest;


public class FeeAdjustWebAction extends GeneralWebAction {
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
	protected EJBEvent encapsulateData(HttpServletRequest request) throws WebActionException {
		try{
//		String action=request.getParameter("txtAction");
//		LogUtility.log(this.getClass(), LogLevel.DEBUG, "txtAction"+action);
		
		CommandResponse RepCmd = (CommandResponse)(request.getSession().getAttribute(CommonKeys.FEE_SESSION_NAME));
   		if(RepCmd==null){
   			RepCmd=new CommandResponse(new ArrayList());
   			request.getSession().setAttribute(CommonKeys.FEE_SESSION_NAME, RepCmd);
   		}

   		if(RepCmd.getPayload()==null){
   			RepCmd.setPayload(new ArrayList());
   		}
   		ArrayList infoList=(ArrayList) RepCmd.getPayload();
		ImmediateCountFeeInfo feeInfo=(ImmediateCountFeeInfo)infoList.get(0);

   		Collection feeList=new ArrayList();
   		String rowIds=request.getParameter("rowid");
		LogUtility.log(this.getClass(), LogLevel.DEBUG, "rowIds",rowIds);
   		String[] arrRowId=rowIds.split(";");
   		double totalFee=0.0f;

		for(int index=0;index<arrRowId.length;index++){
			String rid=arrRowId[index];
			if(!WebUtil.StringHaveContent(rid)){
				continue;
			}
			String feeTypeName = "txtFeeTypeList_" + rid;
			String itemName = "txtItemTypeList_" + rid;
			String amountName = "txtAmountList_" + rid;
			String productName="txtListProductID_"+rid;
			String groupNoName="txtAccountItemGroupNo"+rid;
			String sheafNoName="txtAccountItemSheafNo"+rid;
			String accountItemCcid="txtAccountItemCcID"+rid;
			LogUtility.log(this.getClass(), LogLevel.DEBUG, "feeTypeName",feeTypeName);
			LogUtility.log(this.getClass(), LogLevel.DEBUG, "itemName",itemName);
			LogUtility.log(this.getClass(), LogLevel.DEBUG, "amountName",amountName);
			LogUtility.log(this.getClass(), LogLevel.DEBUG, "arrProducts",productName);
			LogUtility.log(this.getClass(), LogLevel.DEBUG, "groupNoName",groupNoName);
			LogUtility.log(this.getClass(), LogLevel.DEBUG, "sheafNoName",sheafNoName);

			String feeType = request.getParameter(feeTypeName);
			String itemType = request.getParameter(itemName);
			String amount = request.getParameter(amountName);
			String productID =  request.getParameter(productName);
			if (!WebUtil.StringHaveContent(feeType)) {
				throw new WebActionException("费用类型必填");
			}
			if (!WebUtil.StringHaveContent(itemType)) {
				throw new WebActionException("帐目类型必填");
			}
			if (!WebUtil.StringHaveContent(amount)) {
				throw new WebActionException("金额必填");
			}

			AccountItemDTO acctDto =new AccountItemDTO();

			feeList.add(acctDto);
			acctDto.setFeeType(feeType);
			acctDto.setAcctItemTypeID(itemType);
			acctDto.setProductID(WebUtil.StringToInt(productID));
			acctDto.setValue(WebUtil.StringTodouble(amount));
			String groupNo=request.getParameter(groupNoName);
			if(WebUtil.StringHaveContent(groupNo)){
				acctDto.setGroupNo(WebUtil.StringToInt(groupNo));
			}
			String sheafNo=request.getParameter(sheafNoName);
			if(WebUtil.StringHaveContent(sheafNo)){
				acctDto.setSheafNo(WebUtil.StringToInt(sheafNo));
			}
			String ccid=request.getParameter(accountItemCcid);
			if(WebUtil.StringHaveContent(ccid)){
				acctDto.setCcID(WebUtil.StringToInt(ccid));
			}
			acctDto.setForcedDepositFlag("P".equals(feeType)?CommonKeys.FORCEDDEPOSITFLAG_Y:CommonKeys.FORCEDDEPOSITFLAG_N);
			totalFee = WebUtil.doubleFormat(totalFee+acctDto.getValue());
			LogUtility.log(this.getClass(), LogLevel.DEBUG, "acctDto",acctDto);
		}
		feeInfo.setAcctItemList(feeList);
		feeInfo.setTotalValueAccountItem(totalFee);
//		LogUtility.log(this.getClass(), LogLevel.DEBUG, "feeList",feeList);
//		LogUtility.log(this.getClass(), LogLevel.DEBUG, "feeInfo",feeInfo);
        return null;
		}catch(Exception e){
			e.printStackTrace();
			throw new WebActionException(e.getMessage());
		}
    }
	

}
