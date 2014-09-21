<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="resultset" prefix="rs" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="osstags" prefix="d" %>

<%@ page import="com.dtv.oss.web.util.CommonKeys" %>
<%@ page import="com.dtv.oss.web.util.WebOperationUtil" %>
<%@ page import="com.dtv.oss.util.Postern" %>
<%@ page import="java.util.Map" %>

<%
  String checkMoneyFlag =(pageContext.getRequest().getAttribute("checkMoneyFlag")==null) ? "2" :(String)pageContext.getRequest().getAttribute("checkMoneyFlag");
  String csiType =(String)request.getAttribute("PayCsiType");
  Map optSet =Postern.getOpeningPaymentMop(csiType);
%>
<table align="center" width="100%" border="0" cellpadding="4" cellspacing="1" class="list_bg"> 	
 	    <tr> 
         <td class="import_tit" align="center" colspan="5"><font size="3">֧���嵥</font></td>
      </tr>
      <tr class="list_head">
        <td class="list_head" width="30%" align="center">֧����ʽ</td>
        <td class="list_head" width="40%" align="center">Ʊ�ݱ��</td>
        <td class="list_head" width="30%" align="center">֧�����</td>
      </tr>
      <tr>
      	<%
      	pageContext.setAttribute("mopList",optSet);
      	%>
      	<td ><tbl:select name="pay_realMOP" set="mopList" match="pay_realMOP" width="23" defaultValue="1-CH_Y" empty="false" onChange="javaScript:move_addInfo();" /> </td>  
   		  <td ><input name="pay_realaddInfo" type="text" size="36" value="" class="text" onBlur="javaScript:check_addInfo();"> </td>
   		  <td >
   		  	<input name="generalPayTotal" type="text" size="30"  style="text-align:right" value="<%=(request.getAttribute("totalpay")==null) ? "0" :(String)request.getAttribute("totalpay")%>" class="text" >
   		  </td>
    	</tr>
</table>

<Script language="JavaScript">
	 var pay_paymentTicket="<%=CommonKeys.PAYMENTTICKETTY_DK%>";
   var pay_checkMoney='<%=checkMoneyFlag%>' ;
   function move_addInfo(){
   	  var pay_mop =document.getElementById("pay_realMOP").value; 
      var pay_mopid =pay_mop.substring(0,pay_mop.indexOf("-"));
      var pay_tickettype =pay_mop.substring(pay_mop.indexOf("-")+1,pay_mop.indexOf("_"));
	    var pay_paymentType =pay_mop.substr(pay_mop.indexOf("_")+1);
      var pay_realaddInfo =document.getElementById("pay_realaddInfo");
      
      pay_realaddInfo.value ="";
	    //�ֿ�ȯ֧��������Ʊ��
	    if (pay_tickettype ==pay_paymentTicket && pay_mopid !=10 && pay_mopid !=11){
	        pay_realaddInfo.focus();
      }
      //֧Ʊ������Ʊ��
      if (pay_mopid ==10000000){
	        pay_realaddInfo.focus();	
	    }
   }
    
   function check_addInfo(){
   	  var pay_mop =document.getElementById("pay_realMOP").value; 
      var pay_mopid =pay_mop.substring(0,pay_mop.indexOf("-"));
      var pay_tickettype =pay_mop.substring(pay_mop.indexOf("-")+1,pay_mop.indexOf("_"));
	    var pay_paymentType =pay_mop.substr(pay_mop.indexOf("_")+1);
      var pay_realaddInfo =document.getElementById("pay_realaddInfo");      
	    if (pay_tickettype ==pay_paymentTicket){
         if (check_Blank(pay_realaddInfo, true, "Ʊ�ݱ��")){
	          pay_realaddInfo.focus();
	       }
	    }
	    
	    //�����֧Ʊ����Ҫ������4λ
	    if (pay_mopid ==10000000){
	    	 if (check_Blank(pay_realaddInfo, true, "Ʊ�ݱ��")){
	    	 	   pay_realaddInfo.focus();	
	    	 }else{
	    	   if (!checkPlainNum_1(pay_realaddInfo,true,4,"Ʊ�ݱ��")){
	    	 	    pay_realaddInfo.focus();	
	    	   }
	    	 }
	    }else if (pay_mopid ==1){
	    	 if (pay_realaddInfo.value !='' && pay_realaddInfo.value.length !=12){
	    	 	   alert("�ֽ𸶷�,Ʊ�ݱ��ӦΪ12λ");
	    	 	   pay_realaddInfo.focus();	
	    	 }
	    }
	    check_Pay();  
   } 
   
   function check_Pay(){
   	  var pay_mop =document.getElementById("pay_realMOP").value; 
      var pay_mopid =pay_mop.substring(0,pay_mop.indexOf("-"));
   	  var generalPayTotal =document.getElementById("generalPayTotal");
	    if (check_Blank(generalPayTotal, true, "֧�����")){
	    	  generalPayTotal.focus();
	    }
	    if (!check_Float(generalPayTotal, true, "֧�����")){
	        generalPayTotal.focus();
	    }
	    	         
	    if (pay_checkMoney=='0'){
          if (generalPayTotal.value*1.0 <0){
              alert("֧������Ϊ����");
              generalPayTotal.focus();
          }
      } else if (pay_checkMoney=='1'){
      	 if (generalPayTotal.value*1.0 >0){
              alert("֧������Ϊ����");
              generalPayTotal.focus();
          }
      }
     
      if (pay_mopid ==10 || pay_mopid ==11){
      	  if (generalPayTotal.value*1.0 <0){
              alert("����Ԥ��ֿ۷�ʽ֧������Ϊ����");
              generalPayTotal.focus();
          }
      }
   }
</Script>
