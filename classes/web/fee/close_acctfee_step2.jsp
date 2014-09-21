<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="osstags" prefix="d" %>
<%@ page import="com.dtv.oss.service.component.ImmediateCountFeeInfo" %>
<%@ page import="com.dtv.oss.service.commandresponse.CommandResponse" %>
<%@ page import="com.dtv.oss.util.Postern" %>
<%@ page import="com.dtv.oss.web.util.CommonKeys" %>
<%@ page import="com.dtv.oss.web.util.WebUtil" %>

<%@ page import="com.dtv.oss.dto.AccountItemDTO" %>
<%@ page import="com.dtv.oss.dto.PrepaymentDeductionRecordDTO" %>
<%@ page import="com.dtv.oss.dto.PaymentRecordDTO" %>
<%@ page import="java.util.*" %>    
<%
   int acctItemCount =0;
   double newAcctItemToatl =0;
   CommandResponse RepCmd = (CommandResponse)(request.getAttribute("ResponseFromEJBEvent"));
   if (RepCmd ==null) RepCmd = (CommandResponse)(request.getSession().getAttribute(CommonKeys.FEE_SESSION_NAME));
   if (RepCmd ==null) RepCmd =new CommandResponse(new ArrayList());
   request.getSession().setAttribute(CommonKeys.FEE_SESSION_NAME,RepCmd);
   request.setAttribute("ResponseFromEJBEvent",RepCmd);
%>

<lgc:bloop requestObjectName="ResponseFromEJBEvent" item="oneline" isOne="true">
	 <table align="center" width="100%" border="0" cellspacing="1" cellpadding="3" class="list_bg">
    <tr> 
       <td class="import_tit" align="center" colspan="5"><font size="3">帐户费用清单</font></td>
    </tr>
   </table>  
   <table align="center" width="100%" border="0" cellspacing="1" cellpadding="3" class="list_bg" >	
	    <tr class='list_head'> 
        <td width="20%" class='list_head'   align="center" >帐户ID</td>
        <td width="20%" class='list_head'   align="center" >帐户名称</td>
        <td width="20%" class='list_head'   align="center" >现金余额</td> 
        <td width="20%" class='list_head'   align="center" >虚拟货币余额</td> 
        <td width="20%" class='list_head'   align="center" >帐户金额总计</td>  
     </tr>	
     <%
        ImmediateCountFeeInfo immeCountFeeInfo = (ImmediateCountFeeInfo)pageContext.getAttribute("oneline");
        Collection newPrepayRecordList =(immeCountFeeInfo.getNewPrepayRecordList()==null) ? new ArrayList() : immeCountFeeInfo.getNewPrepayRecordList();
        Iterator  newPrepayRecordIter =newPrepayRecordList.iterator();
        double newPrepayCashTotal =0 ,newPrepayTokenTotal =0;
        while (newPrepayRecordIter.hasNext()){
            PrepaymentDeductionRecordDTO dto =(PrepaymentDeductionRecordDTO)newPrepayRecordIter.next();
            if (dto.getPrepaymentType().equals(CommonKeys.PREPAYMENTTYPE_CASH)){
                newPrepayCashTotal =newPrepayCashTotal +dto.getAmount();
            } else {
            	  newPrepayTokenTotal =newPrepayTokenTotal +dto.getAmount();
            }
        }
        double generalFeeTotal =immeCountFeeInfo.getPreCashDoposit()+immeCountFeeInfo.getPreTokenDoposit()-(newPrepayCashTotal+newPrepayTokenTotal);
      %>   
     <tr> 
     	 <input type="hidden" name="insideAcctPreCashDoposit" value="<%=WebUtil.bigDecimalFormat(immeCountFeeInfo.getPreCashDoposit()-newPrepayCashTotal) %>" >
       <input type="hidden" name="insideAcctPreTokenDoposit" value="<%=WebUtil.bigDecimalFormat(immeCountFeeInfo.getPreTokenDoposit()-newPrepayTokenTotal)%>" >
    	 <td width="20%" class='list_bg1'   align="center" ><tbl:write name="oneline" property="accountid" /></td>
       <td width="20%" class='list_bg1'   align="center" ><tbl:write name="oneline" property="accountName" /></td>
       <td width="20%" class='list_bg1'   align="center" ><%=WebUtil.bigDecimalFormat(immeCountFeeInfo.getPreCashDoposit()-newPrepayCashTotal)%></td> 
       <td width="20%" class='list_bg1'   align="center" ><%=WebUtil.bigDecimalFormat(immeCountFeeInfo.getPreTokenDoposit()-newPrepayTokenTotal)%></td> 
       <td width="20%" class='list_bg1'   align="center" ><%=WebUtil.bigDecimalFormat(generalFeeTotal)%></td>  
       <input type="hidden" name="generalFeeTotal"  value="<%=WebUtil.bigDecimalFormat(generalFeeTotal)%>">
     </tr>
     <%
        newPrepayRecordIter =newPrepayRecordList.iterator();
        while (newPrepayRecordIter.hasNext()){
           PrepaymentDeductionRecordDTO dto =(PrepaymentDeductionRecordDTO)newPrepayRecordIter.next();
           if (dto ==null) continue;
     %>
           <input type="hidden" name="wrap_pay_realMOP"  value ="<%=dto.getComments()%>">
           <input type="hidden" name="wrap_pay_realaddInfo" value="" >
           <input type="hidden" name="wrap_pay_realPay" value="<%=WebUtil.bigDecimalFormat(dto.getAmount())%>" >
     <%
        }
        Collection newpaymentRecordList =(immeCountFeeInfo.getNewPaymentRecordList()==null) ? new ArrayList() : immeCountFeeInfo.getNewPaymentRecordList();
        Iterator  newpaymentRecordIter =newpaymentRecordList.iterator();
        while (newpaymentRecordIter.hasNext()){
           PaymentRecordDTO  dto =(PaymentRecordDTO)newpaymentRecordIter.next();
           if (dto ==null) continue;
           String prepaymentType ="";
           if (dto.getPrepaymentType().equals(CommonKeys.PREPAYMENTTYPE_CASH))
              prepaymentType ="Y";
           else
           	  prepaymentType ="N";
              
           String MopIDStr = dto.getMopID()+"-"+dto.getTicketType()+"_"+prepaymentType;
     %>
          <input type="hidden" name="wrap_pay_realMOP"  value="<%=MopIDStr%>">
          <input type="hidden" name="wrap_pay_realaddInfo" value="<%=dto.getTicketNo()%>" >
          <input type="hidden" name="wrap_pay_realPay" value="<%=WebUtil.bigDecimalFormat(dto.getAmount())%>" >
     <%
        }
     %>
     
   </table> 
</lgc:bloop>


