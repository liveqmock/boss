<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="resultset" prefix="rs" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="osstags" prefix="d" %>
<%@ page import="java.util.*" %>
<%@ page import="com.dtv.oss.service.component.ImmediateCountFeeInfo" %>
<%@ page import="com.dtv.oss.service.commandresponse.CommandResponse" %>
<%@ page import="com.dtv.oss.util.Postern" %>
<%@ page import="com.dtv.oss.web.util.CommonKeys" %>

<%@ page import="com.dtv.oss.web.util.WebUtil" %>
<%@ page import="com.dtv.oss.dto.PrepaymentDeductionRecordDTO" %>
<%@ page import="com.dtv.oss.dto.PaymentRecordDTO" %>
<%@ page import="java.util.*" %> 


<%
    CommandResponse Rep_Cmd = (CommandResponse)(request.getAttribute("ResponseFromEJBEvent"));
    if (Rep_Cmd ==null) Rep_Cmd = (CommandResponse)(request.getSession().getAttribute(CommonKeys.FEE_SESSION_NAME));
    if (Rep_Cmd ==null) Rep_Cmd =new CommandResponse(new ArrayList());
    request.getSession().setAttribute(CommonKeys.FEE_SESSION_NAME,Rep_Cmd);
    request.setAttribute("ResponseFromEJBEvent",Rep_Cmd);
%>

<lgc:bloop requestObjectName="ResponseFromEJBEvent" item="oneline" isOne="true">
<%
    ImmediateCountFeeInfo immeCountFeeInfo = (ImmediateCountFeeInfo)pageContext.getAttribute("oneline"); 
    Collection newPrepayRecordList =(immeCountFeeInfo.getNewPrepayRecordList()==null) ? new ArrayList() : immeCountFeeInfo.getNewPrepayRecordList();
    Iterator  newPrepayRecordIter =newPrepayRecordList.iterator();
    
    Collection newPaymentRecordList =(immeCountFeeInfo.getNewPaymentRecordList()==null) ? new ArrayList() : immeCountFeeInfo.getNewPaymentRecordList();
    Iterator  newPaymentRecordIter =newPaymentRecordList.iterator();
    
    if (!newPaymentRecordList.isEmpty() || !newPrepayRecordList.isEmpty()){ 
%>
<table align="center" width="100%" border="0" cellpadding="4" cellspacing="1" class="list_bg">
   <tr> 
      <td class="import_tit" align="center" colspan="4"><font size="3">支付清单</font></td>
   </tr>
   <tr class="list_head">
      <td class="list_head" width="30%" align="center">支付方式</td>
      <td class="list_head" width="40%" align="center">票据编号</td>
      <td class="list_head" width="30%" align="center">费用支付金额</td>
   </tr>
<%
    double paymentRecordTotal =0;
    double prePaymentRecordTotoal =0;
    String csiType =(String)request.getAttribute("PayCsiType");
    //付费方式
    Map mapBankMop1 =Postern.getOpeningPaymentMop(csiType);
    while (newPrepayRecordIter.hasNext()){    
         PrepaymentDeductionRecordDTO dto =(PrepaymentDeductionRecordDTO)newPrepayRecordIter.next();
         prePaymentRecordTotoal = prePaymentRecordTotoal +dto.getAmount();
         String strMopName1 = null;
         //获得支付方式的中文名
         strMopName1 = (String)mapBankMop1.get(dto.getComments());
         if (strMopName1==null) strMopName1="";
   %>
    <tbl:printcsstr style1="list_bg1" style2="list_bg2" overStyle="list_over">
        <td align="center"><%=strMopName1%>  </td>
        <td align="center">&nbsp;  </td>
        <td align="center"><%=WebUtil.bigDecimalFormat(dto.getAmount())%> </td>
        <input type="hidden" name="wrap_pay_realMOP"  value ="<%=dto.getComments()%>">
        <input type="hidden" name="wrap_pay_realaddInfo" value="" >
        <input type="hidden" name="wrap_pay_realPay" value="<%=WebUtil.bigDecimalFormat(dto.getAmount())%>" >
    </tbl:printcsstr> 
        
<%
    }
    while (newPaymentRecordIter.hasNext()){    
         PaymentRecordDTO dto =(PaymentRecordDTO)newPaymentRecordIter.next();
         paymentRecordTotal =paymentRecordTotal +dto.getAmount();
         String strMopName1 = null;
         //获得支付方式的中文名 
         String prepaymentType ="";
         if (dto.getPrepaymentType().equals(CommonKeys.PREPAYMENTTYPE_CASH))
             prepaymentType ="Y";
         else
          	 prepaymentType ="N";
         strMopName1 = (String)mapBankMop1.get(dto.getMopID()+"-"+dto.getTicketType()+"_"+prepaymentType);
         if (strMopName1==null) strMopName1="";
         
         String MopIDStr = dto.getMopID()+"-"+dto.getTicketType()+"_"+prepaymentType;
%>
    <tbl:printcsstr style1="list_bg1" style2="list_bg2" overStyle="list_over">
        <td align="center"><%=strMopName1%>  </td>
        <td align="center"><%=dto.getTicketNo()%>  </td>
        <td align="center"><%=WebUtil.bigDecimalFormat(dto.getAmount())%>  </td>
        <input type="hidden" name="wrap_pay_realMOP"  value="<%=MopIDStr%>">
        <input type="hidden" name="wrap_pay_realaddInfo" value="<%=dto.getTicketNo()%>" >
        <input type="hidden" name="wrap_pay_realPay" value="<%=WebUtil.bigDecimalFormat(dto.getAmount())%>" >
    </tbl:printcsstr> 
<%
    }
%>
<table align="center" width="100%" border="0" cellpadding="4" cellspacing="1" class="list_bg"> 	
	 <tr class="list_bg2">
	 	 	<td  valign="middle" align="right" >支付金额总计:
	 	 		<input type="text"  size="12" style="text-align:right" name="payFeeTotal"  value="<%=WebUtil.bigDecimalFormat(paymentRecordTotal+prePaymentRecordTotoal)%>" class="textgray" readonly>
	 	 	</td>
 	 </tr>
</table>
<%
  }
%>
</lgc:bloop>
<%
    String[] pay_realMOP =request.getParameterValues("pay_realMOP");
    String[] pay_realaddInfo =request.getParameterValues("pay_realaddInfo"); 
    String[] pay_realPay =request.getParameterValues("pay_realpay");
    
    String acceptPayFlag =(request.getParameter("acceptPayFlag") ==null) ? "true" :request.getParameter("acceptPayFlag");
    if (pay_realPay !=null && acceptPayFlag.equalsIgnoreCase("true")){
%>
<table align="center" width="100%" border="0" cellpadding="4" cellspacing="1" class="list_bg">
   <tr> 
      <td class="import_tit" align="center" colspan="4">
      	<font size="3">退费清单("-":表示退给用户，"+":表示收取费用) </font>
      </td>
   </tr>
   <tr class="list_head">
      <td class="list_head" width="30%" align="center">支付方式</td>
      <td class="list_head" width="40%" align="center">票据编号</td>
      <td class="list_head" width="30%" align="center">预存退费金额</td>
   </tr>
   <%
      //付费方式
      String csiType =(String)request.getAttribute("PayCsiType");
      Map mapBankMop1 =Postern.getOpeningPaymentMop(csiType);
      String strMopName1 = null;

      for (int i=0; i<pay_realPay.length; i++) {
          //获得支付方式的中文名
          if (pay_realMOP[i].equals("")) continue;
          strMopName1 = (String)mapBankMop1.get(pay_realMOP[i]);
          if (strMopName1==null) strMopName1="";
   %>
    <tbl:printcsstr style1="list_bg1" style2="list_bg2" overStyle="list_over">
        <td align="center"><%=strMopName1%>  </td>
        <td align="center"><%=pay_realaddInfo[i]%>  </td>
        <td align="center"><%=pay_realPay[i]%>  </td>
        <input type="hidden" name="pay_realMOP" value="<%=pay_realMOP[i]%>" >
	      <input type="hidden" name="pay_realaddInfo" value="<%=pay_realaddInfo[i]%>" >
        <input type="hidden" name="pay_realpay" value="<%=pay_realPay[i]%>" > 
     </tbl:printcsstr>
  <%
      }
  %> 
</table>
<table align="center" width="100%" border="0" cellpadding="4" cellspacing="1" class="list_bg"> 	
	 <tr class="list_bg2">
	 	 	<td  valign="middle" align="right" >预存退费金额总计:
	 	 		<input type="text" size="12" style="text-align:right" name="returnFeeTotal" value="<tbl:writeparam name="generalPayTotal" />" class="textgray" readonly> 	
	 	 	</td>
 	 </tr>
</table>  
<% } %>