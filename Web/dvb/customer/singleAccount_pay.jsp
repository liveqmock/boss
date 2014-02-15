<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="resultset" prefix="rs" %>
<%@ taglib uri="/WEB-INF/oss.tld" prefix="d" %>
<%@ taglib uri="bookmark" prefix="bk" %>

<%@ page import="com.dtv.oss.util.Postern,                 
                 java.text.DecimalFormat,
                 java.util.HashMap,
                 java.util.Map,
                 java.util.Iterator" %>
<%@ page import="com.dtv.oss.web.util.WebUtil" %>
<%@ page import="com.dtv.oss.web.util.CommonKeys" %>

 <Script language="JavaScript"> 	
    function check_frm(){
    	var billPayTotal = document.frmPost.billPayTotal.value*1.0;
    	var pay_sum =document.frmPost.generalPayTotal.value*1.0;
      if (billPayTotal > pay_sum){
          alert("支付金额必须大于等于帐单总额!");
          return false;
      }
      document.frmPost.pay_realprepay.value =pay_sum - billPayTotal;
      document.frmPost.pay_realpay.value =billPayTotal;
      return true;
    }
    
    function prepare_before_submit(){
    	var l=document.getElementsByName("invoiceID") ;
    	document.frmPost.strInvoiceNo.value=l[0].value;
    	for(i=1;i<l.length;i++){
    		document.frmPost.strInvoiceNo.value+=(";"+l[i].value);
    	}
    }
    function next_submit(){
       if (check_frm()){
    	   prepare_before_submit();
    	   var realprepay =document.frmPost.pay_realprepay.value;
     	   var realpay =document.frmPost.pay_realpay.value;
     	   if (window.confirm("您支付帐单金额:"+realpay+";预存金额为："+realprepay)){
            document.frmPost.submit();
         }
       }
    }
    
    function back_submit(url){
    	 document.frmPost.action = url;
       document.frmPost.submit();
    }  
 </Script>
<table  border="0" align="center" cellpadding="0" cellspacing="10" >
  <tr>
    <td><img src="img/list_tit.gif" width="19" height="19"></td>
    <td class="title">批量支付帐单</td>
  </tr>
</table> 
<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>
<br>             
<form name="frmPost" method="post" action="singleAccount_pay_op.do">
  <input type="hidden" name="strInvoiceNo" value="">
  <input type="hidden" name="confirm_post"  value="true" >
  <input type="hidden" name="txtAccountID" value="<tbl:writeparam name="txtAccountID" />"> 

<rs:hasresultset> 
<%double totalpay=0.0;%> 
<br>
<table  width="98%" border="0" cellpadding="6" cellspacing="1" class="list_bg" >
   <tr class="list_head">    
      <td  class="list_head" align="center">客户证号</td>
      <td  class="list_head" align="center">客户姓名</td>
      <td  class="list_head" align="center">帐户号</td> 
      <td class="list_head"  align="center">客户地址</td>
      <td class="list_head"  align="center">帐期</td>
      <td class="list_head"  align="center">帐单号</td>
      <td class="list_head"  align="center">帐单金额</td>   
    </tr>
    <lgc:bloop requestObjectName="ResponseQueryResult" item="oneline" >
    <% 
      Collection resultCols = (Collection)pageContext.getAttribute("oneline");
      Iterator resultIter =resultCols.iterator();
      while (resultIter.hasNext()) {
        Object inVoiceObj =resultIter.next();
    %>            
      <tbl:printcsstr style1="list_bg1" style2="list_bg2" overStyle="list_over" >            
        <td align="center"><%=resultIter.next()%></td>
        <td align="center"><%=resultIter.next()%></td>
        <td align="center"><%=resultIter.next()%></td>
        <td align="center"><%=resultIter.next()%></td>
        <%
          Object invoiceCycleObj =resultIter.next();
          int invoiceCycleID =WebUtil.StringToInt(invoiceCycleObj.toString());
        %>
        <td align="center"><%=Postern.getBillingCycleNameByID(invoiceCycleID)%></td>
        <td align="center">
        <input type="hidden" name="invoiceID" value="<%=inVoiceObj.toString()%>">
        <%=inVoiceObj.toString()%>
        </td>   
        <%
          Object itemamountObj =resultIter.next();
          double  itemamount =WebUtil.StringToDouble(itemamountObj.toString());
          totalpay=(double)(Math.floor(totalpay*100+itemamount*100+0.01)/100);  
        %>
        <td align="center" style="text-align:right">
        <%=itemamountObj.toString()%>
        </td>
      </tbl:printcsstr>
    <%
      }    
    %>
	  </lgc:bloop> 
	<% DecimalFormat to= new DecimalFormat("0.00");%> 
   <tr class="list_bg2">
   <td colspan="6" align="right">累计应付总额</td>
   <td align="right"><input type="text" style="text-align:right" size="12" name="billPayTotal" value="<%=to.format(totalpay)%>" readonly class="textgray"></td>
   </tr>
   <tr>
    <td colspan="7" class="list_foot"></td>
  </tr>    
</table>  
<%
   request.setAttribute("totalpay",String.valueOf(to.format(totalpay)));
%>   
<br>                       
<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>
<br>
</rs:hasresultset>    
<input name="pay_realpay" type="hidden" value="" >
<input name="pay_realprepay" type="hidden" value="">

<tbl:CommonFeeAndPaymentInfo includeParameter="SinglePay" payCsiType="<%=CommonKeys.CUSTSERVICEINTERACTIONTYPE_PI%>" confirmFlag="false" />		 

<table align="center" border="0" cellspacing="0" cellpadding="0">
   <tr>
   	<bk:canback url="bill_batch_query_for_singleAccount.do">
     <td><img src="img/button2_r.gif" width="22" height="20"></td>
	   <td><input name="prev" type="button" class="button" onclick="javascript:back_submit('<bk:backurl property='bill_batch_query_for_singleAccount.do' />')" value="上一步"></td>
	   <td><img src="img/button2_l.gif" width="11" height="20"></td>
	  </bk:canback> 
	  <rs:hasresultset> 
	   <td width="20" ></td> 
     <td><img src="img/button_l.gif" border="0" ></td>
     <td><input name="next" type="button" class="button" onClick="javascript:next_submit()" value="确 认"></td>
     <td><img src="img/button_r.gif" border="0" ></td>    
    </rs:hasresultset>       
   </tr>
</table> 
 <tbl:generatetoken />
</form>

  