<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="resultset" prefix="rs" %>
<%@ taglib uri="/WEB-INF/oss.tld" prefix="d" %>
<%@ taglib uri="bookmark" prefix="bk" %>

<%@ page import="com.dtv.oss.util.Postern,
                 com.dtv.oss.dto.InvoiceDTO,                 
                 java.text.DecimalFormat,
                 java.util.HashMap,
                 java.util.Map,
                 java.util.Iterator" %>
<%@ page import="com.dtv.oss.web.util.WebUtil" %>
<%@ page import="com.dtv.oss.web.util.CommonKeys" %>

 <Script language="JavaScript">
    function check_frm(){
       if (document.frmPost.billPayTotal.value*1.0 >document.frmPost.generalPayTotal.value*1.0){
          alert("支付金额必须大于等于帐单总额！");
          return false;
       }
       return true;
    }
    function prepare_before_submit(){
    	var l=document.getElementsByName("strAcctID") ;
    	document.frmPost.strAcctIds.value=l[0].value;
    	for(i=1;i<l.length;i++){
    		document.frmPost.strAcctIds.value+=(";"+l[i].value);
    	}
    	document.frmPost.custAveragePrePay.value =(document.frmPost.generalPayTotal.value*1.0 - document.frmPost.billPayTotal.value*1.0)/l.length;
    }
    function pay_submit(){
       if (check_frm()){
       	  prepare_before_submit();
       	  averagePrepay = document.frmPost.custAveragePrePay.value;
       	  if (validata_num(averagePrepay,0)){
       	  	  if (window.confirm("您支付帐单总金额为:"+document.frmPost.billPayTotal.value+";每个帐户上预存金额为："+averagePrepay)){
                  document.frmPost.submit();
              }
       	  }else{
       	  	 if (averagePrepay*1.0 ==0){
       	  	    if (window.confirm("您支付帐单总金额为:"+document.frmPost.billPayTotal.value)){
                    document.frmPost.submit();
                }
       	  	 }else{
       	  	    alert("每个帐户上预存金额为"+averagePrepay+",非整数！");
       	  	 }
       	  }
       }
    }
    function back_submit(){
    	 var strAcct=document.getElementsByName("strAcctID") ;
    	 if (strAcct[0] !=null){
    	    prepare_before_submit();
    	 }
    	 document.frmPost.action = "mergebill_batch_pay_query.do?txtFrom=1&txtTo=40";
       document.frmPost.submit();
    }
    
    
 </Script>
<table  border="0" align="center" cellpadding="0" cellspacing="10" >
  <tr>
    <td><img src="img/list_tit.gif" width="19" height="19"></td>
    <td class="title">帐单合并批量支付</td>
  </tr>
</table> 
<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>
<br>               
<form name="frmPost" method="post" action="mergebill_batch_pay.do">
<%double totalpay=0.0f;%> 
<br>
<table  width="98%" border="0" cellpadding="6" cellspacing="1" class="list_bg" >
   <tr class="list_head">    
      
      <td  class="list_head" align="center">帐户号</td> 
      <td  class="list_head" align="center">客户姓名</td>
      <td  class="list_head" align="center">客户证号</td>
      <td class="list_head"  align="center">客户地址</td>
      <td class="list_head"  align="center">帐单金额</td>   
    </tr>
    <lgc:bloop requestObjectName="ResponseQueryResult" item="oneline" >
    <% 
      Collection resultCols = (Collection)pageContext.getAttribute("oneline");
      Iterator resultIter =resultCols.iterator();
      while (resultIter.hasNext()) {
        Object acctObj =resultIter.next();
    %>            
      <tbl:printcsstr style1="list_bg1" style2="list_bg2" overStyle="list_over" >  
        <td align="center">
           <input type="hidden" name="strAcctID" value="<%=acctObj.toString()%>">      
           <%=acctObj.toString()%>
    <%
        Collection  invoiceCols =Postern.getInvoiceColByAcctId(Integer.parseInt(acctObj.toString()));
        Iterator    invoiceItr =invoiceCols.iterator();
        String invoiceIds ="";
        while (invoiceItr.hasNext()){
             InvoiceDTO dto =(InvoiceDTO)invoiceItr.next(); 
             if (invoiceIds.equals("")){
                 invoiceIds =String.valueOf(dto.getInvoiceNo());
             }else{
             	   invoiceIds =invoiceIds+";"+String.valueOf(dto.getInvoiceNo());
             }
        }
    %>
          <input type="hidden" name="invoiceIds" value="<%=invoiceIds%>" >
        </td>
        <td align="center"><%=resultIter.next()%></td>
        <td align="center"><%=resultIter.next()%></td>
        <td align="center"><%=resultIter.next()%></td>
        <%
          Object itemamountObj =resultIter.next();
          double  itemamount =WebUtil.StringTodouble(itemamountObj.toString());
          totalpay=(double)(Math.floor(totalpay*100+itemamount*100+0.01)/100);  
        %>
        <td align="center" style="text-align:right"><%=itemamountObj.toString()%></td>
      </tbl:printcsstr>
    <%
      }    
    %>
	  </lgc:bloop> 
	<% DecimalFormat to= new DecimalFormat("0.00");%> 
   <tr class="list_bg2">
   <td colspan="4" align="right">累计应付总额</td>
   <td align="right"><input type="text" style="text-align:right" size="12" name="billPayTotal" value="<%=to.format(totalpay)%>" readonly class="textgray"></td>
   </tr>
   <tr>
    <td colspan="5" class="list_foot"></td>
  </tr>    
</table>     
<br>                       
<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table> 	
   <input type="hidden" name="strAcctIds" value="">
   <input type="hidden" name="custAveragePrePay" value="">
   <input type="hidden" name="confirm_post"  value="true" >
   <input type="hidden" name="func_flag" value=""> 
   <%
      request.setAttribute("totalpay",String.valueOf(to.format(totalpay)));
   %>
   <tbl:hiddenparameters pass="txtCustomerID/txtCustomerType/txtServiceAcctStatus/txtCustomerName/txtqueryStyle/txtDistrictID/txtAcctBalance1/txtAcctBalance2/txtAddressDetail" />     
<br> 

<tbl:CommonFeeAndPaymentInfo includeParameter="SinglePay" payCsiType="<%=CommonKeys.CUSTSERVICEINTERACTIONTYPE_PI%>" confirmFlag="false" />		 

<table align="center" border="0" cellspacing="0" cellpadding="0">
   <tr>
     <td><img src="img/button2_r.gif" width="22" height="20"></td>
	   <td><input name="prev" type="button" class="button" onclick="javascript:back_submit()" value="上一步"></td>
	   <td><img src="img/button2_l.gif" width="11" height="20"></td>
	   <td width="20" ></td> 
     <td><img src="img/button_l.gif" border="0" ></td>
     <td><input name="next" type="button" class="button" onClick="javascript:pay_submit()" value="确&nbsp;认"></td>
     <td><img src="img/button_r.gif" border="0" ></td>           
   </tr>
</table> 
<tbl:generatetoken />
	
</form>

  