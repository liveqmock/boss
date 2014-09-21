<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="bookmark" prefix="bk" %>
<%@ page import="com.dtv.oss.util.Postern,
                 com.dtv.oss.dto.InvoiceDTO,
                 com.dtv.oss.service.commandresponse.CommandResponse,
                 com.dtv.oss.web.util.WebUtil,
                 java.util.*" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gbk">
<title>帐户帐单明细</title>
</head>
<table align="center" width="95%" border="0" cellspacing="5" cellpadding="5">
    <tr>
       <td align="center"> 
         <font size="3"><strong><tbl:writeparam name="txtAccountID" />帐单明细</strong></font> 
      </td>
    </tr>
</table>
<table align="center" width="95%" border="0" cellspacing="1" cellpadding="3" class="list_bg" >
   <tr class="list_head">
     <td width="70%"  align="center" class="list_head">帐期</td>
     <td width="30%"  align="center" class="list_head">金额</td>          
   </tr>
   <%
      int acctId =WebUtil.StringToInt(request.getParameter("txtAccountID"));
      Collection invoiceCols =Postern.getInvoiceColByAcctId(acctId);
      request.setAttribute("ResponseQueryResult",new CommandResponse(invoiceCols));
   %>    
   <lgc:bloop requestObjectName="ResponseQueryResult" item="oneline"> 
   <%  
	    InvoiceDTO invoiceDto=(InvoiceDTO)pageContext.getAttribute("oneline");
   %>
      <tbl:printcsstr style1="list_bg1" style2="list_bg2" overStyle="list_over" >
        <td  align="center" ><%=Postern.getBillingCycleNameByID(invoiceDto.getInvoiceCycleId())%></td>
        <td  align="center" ><%=invoiceDto.getAmount()%></td>
     </tbl:printcsstr>
   </lgc:bloop>
</table>
<br>
 <p align="center"><a href="#"onClick="window.close()"><font size="2">关闭窗口</font></a></p>
</body>
</html>