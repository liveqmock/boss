<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="bookmark" prefix="bk" %>
<%@ taglib uri="osstags" prefix="d" %>
<%@ taglib uri="privilege" prefix="pri" %>

<%@ page import="com.dtv.oss.util.Postern" %>
<%@ page import="com.dtv.oss.util.TimestampUtility" %>
<%@ page import="com.dtv.oss.web.util.WebUtil" %>
<%@ page import="com.dtv.oss.web.util.CommonKeys" %>
<%@ page import="com.dtv.oss.service.util.CommonConstDefinition" %>
<%@ page import="com.dtv.oss.web.util.WebOperationUtil" %>
<%@ page import="com.dtv.oss.dto.InvoiceDTO" %>
<%@ page import="com.dtv.oss.dto.AccountDTO" %>

<Script language=javascript> 
<!--


function back_bill(){
	 document.frmPost.action="bill_pay_op.do";
	 document.frmPost.txtDoPost.value ="false";
	 document.frmPost.bill_op.value ="check";
	 document.frmPost.submit();11
}

function pay_submit(){
   document.frmPost.action="bill_pay.do";
   document.frmPost.bill_op.value ="pay";
   document.frmPost.submit();
}

//-->
</SCRIPT>

<table  border="0" align="center" cellpadding="0" cellspacing="10">
<tr>
    <td><img src="img/list_tit.gif" width="19" height="19"></td>
    <td class="title">支付账单</td>
  </tr>
</table>
<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>
<br>

<form name="frmPost" method="post" action="bill_pay_prepare.screen" >
    <lgc:bloop requestObjectName="ResponseFromEJBEvent" item="oneline">
    <% 
	      InvoiceDTO invoiceDTO=(com.dtv.oss.dto.InvoiceDTO)pageContext.getAttribute("oneline");
	      String strStatus = invoiceDTO.getStatus();
	      if (strStatus==null) strStatus="";
	
        String strBillingCycleName = Postern.getBillingCycleNameByID(invoiceDTO.getInvoiceCycleId());
        if (strBillingCycleName==null) strBillingCycleName="";
    
        String strName = Postern.getCustomerNameByID(invoiceDTO.getCustID());
        if (strName==null) strName="";
        double payAll = 0;
        double punishment = 0;

        if (invoiceDTO.getDueDate() !=null){
             if(TimestampUtility.getCurrentDateWithoutTime().getTime() > invoiceDTO.getDueDate().getTime())
        	punishment = Postern.getPunishmentValue(invoiceDTO.getDueDate(),invoiceDTO.getAmount());
        }
        payAll = invoiceDTO.getAmount() + punishment;     
        payAll = (float)(Math.floor(payAll*100)/100); 
        
        AccountDTO acctDto = Postern.getAccountDto(invoiceDTO.getAcctID());
     %>          
    <table width="100%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
        <tr>
          <td class="list_bg2" align="right">帐单条形码</td>
          <td class="list_bg1" colspan="3"><font size="2">
            <input type="text" name="txtBarCode" size="48" class="textgray" readonly value="<tbl:write name="oneline" property="barCode"/>" >
          </font></td>
         
        </tr>
        <tr> 
          <td valign="middle" class="list_bg2" align="right" width="17%" >账单号</td>
          <td width="33%" class="list_bg1"><font size="2">
             <input type="text" name="txtInvoiceNo" size="25"  value="<tbl:write name="oneline" property="InvoiceNo"/>" class="textgray" readonly >
          </font></td>
          <td valign="middle" class="list_bg2" align="right" width="17%" >帐户号</td>
          <td width="33%" class="list_bg1"><font size="2">
             <input type="text" name="txtAccountID" size="25"  value="<tbl:write name="oneline" property="AcctID"/>" class="textgray" readonly >
          </font></td>         
        </tr>
        <tr> 
          <td valign="middle" class="list_bg2" align="right" width="17%" >帐户现金金额</td>
          <td width="33%" class="list_bg1"><font size="2">
             <input type="text" name="insideAcctPreCashDoposit" size="25"  value="<%=acctDto.getCashDeposit()%>" class="textgray" readonly >
          </font></td>
          <td valign="middle" class="list_bg2" align="right" width="17%" >帐户虚拟货币金额</td>
          <td width="33%" class="list_bg1"><font size="2">
             <input type="text" name="insideAcctPreTokenDoposit" size="25"  value="<%=acctDto.getTokenDeposit()%>" class="textgray" readonly >
          </font></td>         
        </tr>
        <tr> 
          <td valign="middle" class="list_bg2" align="right">客户姓名</td>
          <td class="list_bg1"><font size="2">
            <input type="text" name="txtCustomerName" size="25"  value="<%=strName%>" class="textgray" readonly >
            <input type="hidden" name="txtCustomerID" value="<%=invoiceDTO.getCustID()%>"/>
          </font></td>
          <td valign="middle" class="list_bg2" align="right">状态</td>
          <td class="list_bg1"><font size="2">          
            <input type="text" name="txtStatusShow" size="25" class="textgray" readonly value="<d:getcmnname typeName="SET_F_INVOICESTATUS" match="oneline:status" />" >
            <input type="hidden" name="txtStatus" value="<tbl:write name="oneline" property="status" />" >
          </font></td>
        </tr>
        <tr>
        	<td class="list_bg2" align="right">帐单来源</td>
          <td class="list_bg1"><font size="2">
            <input type="text" name="txtInvoiceSourceTypeShow" size="25" class="textgray" readonly value="<d:getcmnname typeName="SET_F_INVOICESOURCETYPE" match="oneline:invoiceSourceType" />" >
          </font></td> 
          <td valign="middle" class="list_bg2" align="right">帐务周期</td>
          <td class="list_bg1"><font size="2">
          <input type="text" name="txtBillingCycleIDShow" size="25" class="textgray" readonly value="<%=strBillingCycleName%>" >
          </font></td>
        </tr>
        <tr>  
          <td valign="middle" class="list_bg2" align="right">账单生成日期</td>
          <td class="list_bg1"><font size="2">
            <input type="text" name="txtCreateDate" size="25" class="textgray" readonly value="<tbl:writedate name="oneline" property="CreateDate" />" >
          </font></td>  
          <td valign="middle" class="list_bg2" align="right">账单逾期日</td>
          <td class="list_bg1"><font size="2">
            <input type="text" name="txtDueDate" size="25" class="textgray" readonly value="<tbl:writedate name="oneline" property="DueDate"/>" >
          </font></td>        
        </tr>
        <tr>
          <td valign="middle" class="list_bg2" align="right">上期余额结转</td>
          <td class="list_bg1"><font size="2">
            <input type="text" name="txtBcf" size="25" class="textgray" readonly value="<tbl:write name="oneline" property="bcf"/>" >
          </font></td>
          <td valign="middle" class="list_bg2" align="right">本期费用累计</td>
          <td class="list_bg1"><font size="2">
           <input type="text" name="txtFeeTotal" size="25" class="textgray" readonly value="<tbl:write name="oneline" property="feeTotal"/>" >
          </font></td>
        </tr>   
        <tr>          
          <td valign="middle" class="list_bg2" align="right">本期支付累计</td>
          <td class="list_bg1"><font size="2">
             <input type="text" name="txtPaymentTotal" size="25" class="textgray" readonly value="<tbl:write name="oneline" property="paymentTotal"/>" >
          </font></td>
          <td valign="middle" class="list_bg2" align="right">本期预存累计</td>
          <td class="list_bg1"><font size="2">
             <input type="text" name="txtPrepaymentTotal" size="25" class="textgray" readonly value="<tbl:write name="oneline" property="prepaymentTotal"/>" > 
          </font></td>
        </tr>   	
        <tr>          
          <td valign="middle" class="list_bg2" align="right">本期预存抵扣累计</td>
          <td class="list_bg1"><font size="2">
            <input type="text" name="txtPrepaymentDeductionTotal" size="25" class="textgray" readonly value="<tbl:write name="oneline" property="prepaymentDeductionTotal"/>" > 
          </font></td>   	
          <td valign="middle" class="list_bg2" align="right">零头结转</td>
          <td class="list_bg1"><font size="2">
            <input type="text" name="txtSmallChange" size="25" class="textgray" readonly value="<tbl:write name="oneline" property="smallChange"/>" >
          </font></td>  	
        </tr>
        <tr>
          <td valign="middle" class="list_bg2" align="right">帐单应付金额</td>
          <td class="list_bg1"><font size="2">
            <input type="text" name="txtAmount" size="25" class="textgray" readonly value="<tbl:write name="oneline" property="amount"/>" >
          </font></td>
            <td class="list_bg2" align="right">滞纳金</td>
          <td class="list_bg1"><font size="2">
           <input type="text" name="txtPunishment" size="25" class="textgray" readonly value="<tbl:writeparam name="txtPunishment" />" >
          </font></td>
        </tr>
     </table>
     <table width="100%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
        <tr>
          <td valign="middle"  align="right" class="list_bg1">本次应付金额:
            <input type="text" name="txtPayAmount"  style="text-align:right" size="10" class="textgray" readonly value="<tbl:writeparam name="txtPayAmount" />" >
          </td>
        </tr>
    </table>
    <tbl:CommonFeeAndPaymentInfo includeParameter="Pay" payCsiType="<%=CommonKeys.CUSTSERVICEINTERACTIONTYPE_PI%>" acctshowFlag ="false" confirmFlag="true" />		 
<BR>
     <table align="center"  border="0" cellspacing="0" cellpadding="0">
        <tr>        
	         <td><img src="img/button2_r.gif" width="22" height="20"></td>
           <td background="img/button_bg.gif"  ><a href="javascript:back_bill();" class="btn12">返&nbsp;&nbsp;回</a></td>
           <td><img src="img/button2_l.gif" width="12" height="20"></td>         
           <td width="20" ></td>
       	   <td><img src="img/button_l.gif" width="11" height="20"></td>
           <td background="img/button_bg.gif"  ><a href="javascript:pay_submit()" class="btn12">确&nbsp;&nbsp;定</a></td>
           <td><img src="img/button_r.gif" width="22" height="20"></td>   
       </tr>
     </table>		
 </lgc:bloop>
   
  <input type="hidden" name="txtDoPost"  value="true" >
  <input type="hidden" name="bill_op" value ="" >
 <tbl:generatetoken />
</form> 
