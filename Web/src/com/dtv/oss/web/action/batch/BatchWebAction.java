package com.dtv.oss.web.action.batch;

import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.QueryDTO;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.batch.BatchEJBEvent;
import com.dtv.oss.web.action.GeneralWebAction;
import com.dtv.oss.web.exception.WebActionException;
import com.dtv.oss.web.util.WebUtil;

public class BatchWebAction extends GeneralWebAction {

	boolean confirmPost = false;
	
	protected boolean needCheckToken()
	{
		return confirmPost;
	}
	
	public void doStart(HttpServletRequest request) {
		super.doStart(request);
		if (WebUtil.StringHaveContent(request.getParameter("txtActionType"))) {
			//创建、修改、取消、执行需要判断的
			if(!("changeResult".equalsIgnoreCase(request.getParameter("txtActionType"))))
			{
				confirmPost = true;
			}
		}
	}	
	
	protected EJBEvent encapsulateData(HttpServletRequest request)
			throws Exception {
		
		/**
		 * 动作定义（txtActionType）
		 * 批量查询创建:      create
		 * 批量查询修改:      modify
		 * 批量查询取消:      cancel
		 * 批量查询执行:      excute
		 * 批量查询结果翻转:  changeResult
		 */
		BatchEJBEvent event=new BatchEJBEvent();
		if("create".equals(request.getParameter("txtActionType")))
			event.setActionType(EJBEvent.BATCH_QUERY_CREATE);
		else if("modify".equals(request.getParameter("txtActionType")))
			event.setActionType(EJBEvent.BATCH_QUERY_MODIFY);
		else if("cancel".equals(request.getParameter("txtActionType")))
			event.setActionType(EJBEvent.BATCH_QUERY_CANCEL);
		else if("excute".equals(request.getParameter("txtActionType")))
			event.setActionType(EJBEvent.BATCH_QUERY_EXCUTE);
		else if("changeResult".equals(request.getParameter("txtActionType")))
			event.setActionType(EJBEvent.BATCH_QUERY_RESULT_CHANGE);
		else
			throw new WebActionException("批量查询动作类型未知！");
		
		QueryDTO dto =new QueryDTO();
		Collection queryIDList=new ArrayList();
		Collection resultItemIDList=new ArrayList();
		
		//填充DTO
		dto.setAccountAddress(request.getParameter("txtAccountAddress"));
		dto.setAccountCashBalance1(WebUtil.StringTodouble(request.getParameter("txtAccountCashBalance1")));
		dto.setAccountCashBalance2(WebUtil.StringTodouble(request.getParameter("txtAccountCashBalance2")));
		dto.setAccountCreateTime1(WebUtil.StringToTimestampWithDayBegin(request.getParameter("txtAccountCreateTime1")));
		dto.setAccountCreateTime2(WebUtil.StringToTimestampWithDayEnd(request.getParameter("txtAccountCreateTime2")));
		dto.setAccountDistrictIdList(request.getParameter("txtAccountDistirctIDList"));	
		dto.setAccountInvoiceCreateTime1(WebUtil.StringToTimestampWithDayBegin(request.getParameter("txtAccountInvoiceCreateTime1")));
		dto.setAccountInvoiceCreateTime2(WebUtil.StringToTimestampWithDayEnd(request.getParameter("txtAccountInvoiceCreateTime2")));
		dto.setAccountInvoiceStatusList(strChangeStyle(request.getParameter("txtAccountInvoiceStatusList")));
		dto.setAccountMopIdList(request.getParameter("txtAccountMOPIDList"));
		dto.setAccountStatusList(strChangeStyle(request.getParameter("txtAccountStatusList")));
		dto.setAccountTokenBalance1(WebUtil.StringTodouble(request.getParameter("txtAccountTokenBalance1")));
		dto.setAccountTokenBalance2(WebUtil.StringTodouble(request.getParameter("txtAccountTokenBalance2")));
		dto.setAccountTypeList(strChangeStyle(request.getParameter("txtAccountTypeList")));
		dto.setCpCampaignEndTime1(WebUtil.StringToTimestampWithDayBegin(request.getParameter("txtCPCampaignEndTime1")));
		dto.setCpCampaignEndTime2(WebUtil.StringToTimestampWithDayEnd(request.getParameter("txtCPCampaignEndTime2")));
		dto.setCpCampaignIdList(request.getParameter("txtCPCampaignIDList"));
		dto.setCpCampaignStartTime1(WebUtil.StringToTimestampWithDayBegin(request.getParameter("txtCPCampaignStartTime1")));
		dto.setCpCampaignStartTime2(WebUtil.StringToTimestampWithDayEnd(request.getParameter("txtCPCampaignStartTime2")));
		dto.setCpCreateTime1(WebUtil.StringToTimestampWithDayBegin(request.getParameter("txtCPCreateTime1")));
		dto.setCpCreateTime2(WebUtil.StringToTimestampWithDayEnd(request.getParameter("txtCPCreateTime2")));
		dto.setCpProductIdList(request.getParameter("txtCPProductIDList"));
		dto.setCpStatusList(strChangeStyle(request.getParameter("txtCPStatusList")));
		dto.setCreateOperatorId(WebUtil.StringToInt(request.getParameter("txtCreateOperatorID")));
		dto.setCreateTime(WebUtil.StringToTimestamp(request.getParameter("txtCreateTime")));
		dto.setCustAddress(request.getParameter("txtCustAddress"));
		dto.setCustCreateTime1(WebUtil.StringToTimestampWithDayBegin(request.getParameter("txtCustCreateTime1")));
		dto.setCustCreateTime2(WebUtil.StringToTimestampWithDayEnd(request.getParameter("txtCustCreateTime2")));
		dto.setCustCurrentPoints1(WebUtil.StringToInt(request.getParameter("txtCustCurrentPoints1")));
		dto.setCustCurrentPoints2(WebUtil.StringToInt(request.getParameter("txtCustCurrentPoints2")));
		dto.setCustDistrictIdList(request.getParameter("txtCustDistrictIDList"));
		dto.setCustName(request.getParameter("txtCustName"));
		dto.setCustomerId(WebUtil.StringToInt(request.getParameter("txtCustomerID")));
		dto.setCustOpenSourceIdList(request.getParameter("txtCustOpenSourceIDList"));
		dto.setCustOpenSourceTypeList(strChangeStyle(request.getParameter("txtCustOpenSourceTypeList")));
		dto.setCustStatusList(strChangeStyle(request.getParameter("txtCustStatusList")));
		dto.setCustTotalPoints1(WebUtil.StringToInt(request.getParameter("txtCustTotalPoints1")));
		dto.setCustTotalPoints2(WebUtil.StringToInt(request.getParameter("txtCustTotalPoints2")));
		dto.setCustTypeList(strChangeStyle(request.getParameter("txtCustTypeList")));
		dto.setPerformTime(WebUtil.StringToTimestamp(request.getParameter("txtPerformTime")));
		dto.setScheduleType(request.getParameter("txtScheduleType"));
		dto.setStatus(request.getParameter("txtStatus"));
		dto.setQueryDesc(request.getParameter("txtQueryDesc"));
		dto.setQueryId(WebUtil.StringToInt(request.getParameter("txtQueryID")));
		dto.setQueryName(request.getParameter("txtQueryName"));
		dto.setQueryType(request.getParameter("txtQueryType"));
		dto.setScheduleTime(WebUtil.StringToTimestamp(request.getParameter("txtScheduleTime")));
		dto.setDtLastmod(WebUtil.StringToTimestamp(request.getParameter("txtDtLastmod")));
		dto.setProductClassList(strChangeStyle(request.getParameter("txtCPClassIDList")));//侯8-03添加产品类型
		dto.setBankAccountStatusList(strChangeStyle(request.getParameter("txtAccountBankAccountStatusList")));//侯8-03添加银行帐户串配类型
		dto.setTemplateFlag(request.getParameter("txtTemplateFlag"));
		
		String strQueryIDs=request.getParameter("txtQueryIDs");
		if(!(strQueryIDs==null || "".equals(strQueryIDs))){
			String strQueryID[]=strQueryIDs.split(";");
			for(int i=0;i<strQueryID.length;i++)
				queryIDList.add(new Integer(WebUtil.StringToInt(strQueryID[i])));
		}
		
		String txtQueryResultItemNOs=request.getParameter("txtQueryResultItemNOs");
		if(!(txtQueryResultItemNOs==null || "".equals(txtQueryResultItemNOs))){
			String txtQueryResultItemNO[]=txtQueryResultItemNOs.split(";");
			for(int i=0;i<txtQueryResultItemNO.length;i++)
				resultItemIDList.add(new Integer(WebUtil.StringToInt(txtQueryResultItemNO[i])));
		}
		
		event.setQueryDTO(dto);
		event.setQueryIDList(queryIDList);
		event.setResultItemIDList(resultItemIDList);
		
		return event;
	}
	
	
	//处理字符串，把格式如 A,B,C 格式转化为 'A','B','C'
	private String strChangeStyle(String arg){
		if(arg==null || "".equals(arg))
			return "";
		
		String result="";
		
		 if(arg.indexOf("'")==-1){
			 String str[]=arg.split(",");
			 
			 for(int i=0;i<str.length;i++){
				 if(!"".equals(result))
					 result=result +",";
				 result=result + "'" + str[i] + "'";
			 }	
			 return result;
		 }
		 else
			 return arg;
		 
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
