<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="bookmark" prefix="bk" %>
<%@ taglib uri="privilege" prefix="pri" %>
<%@ taglib uri="/WEB-INF/oss.tld" prefix="d" %>
<%@ taglib uri="operator" prefix="oper" %>
<%@ page import="com.dtv.oss.util.Postern,
                 com.dtv.oss.dto.AccountItemDTO,
                 com.dtv.oss.dto.AcctItemTypeDTO,
                 com.dtv.oss.dto.PaymentRecordDTO,
                 com.dtv.oss.dto.MethodOfPaymentDTO,
                 com.dtv.oss.dto.AccountDTO,
                 com.dtv.oss.dto.CustomerDTO,
                 com.dtv.oss.dto.AddressDTO" %>
<%@ page import="com.dtv.oss.service.commandresponse.CommandResponse" %>
<%@ page import="com.dtv.oss.web.util.WebUtil,
                 com.dtv.oss.web.util.WebOperationUtil,
                 com.dtv.oss.util.TimestampUtility" %>
<%@ page import="java.util.*" %>


<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>中国邮政储蓄  通用凭证</title>
<style type="text/css">
body,tr,td {
	font-family: "宋体", System;
	font-size: 14px;
	color: #181818;
	text-decoration: none;
}
</style>
</head>
<body>
<%
    int acctId =WebUtil.StringToInt(request.getParameter("txtacctID"));
    AccountDTO acctDto =Postern.getAccountDto(acctId);
    CustomerDTO custDto =Postern.getCustomerByID(acctDto.getCustomerID());
    String tel ="";
    if (custDto.getTelephone() !=null && !custDto.getTelephone().equals("")){
        tel =custDto.getTelephone();
    }
    if (custDto.getTelephoneMobile() !=null && !custDto.getTelephoneMobile().equals("")){
       if (!tel.equals("")){
          tel =tel +"/"+custDto.getTelephoneMobile();
       }else{
       	  tel =custDto.getTelephoneMobile();
       }
    }
    
    
    
   //客户地址
   AddressDTO custAddressDTO=Postern.getAddressDtoByID(custDto.getAddressID());
 	 String custdistrictdes=Postern.getSimpleDetailAddress(custAddressDTO.getAddressID());
 	 if(custdistrictdes==null)custdistrictdes="";
    
   String txtAcctPayIDList =request.getParameter("txtAcctPayIDList");
   double printPaySum =0;
   Collection acctPayCols =Postern.getAcctPaymentRecordForPrint(txtAcctPayIDList,0,null,null,1,200);
   Iterator acctPayIter =acctPayCols.iterator();
   while (acctPayIter.hasNext()){
      PaymentRecordDTO dto = (PaymentRecordDTO) acctPayIter.next();
      printPaySum =printPaySum +dto.getAmount();
   }
   String overTime =WebOperationUtil.changeMoneyToDate(acctId);
   String overTime_Year =overTime.substring(0,overTime.indexOf("-"));
   String overTime_Month =overTime.substring(overTime.indexOf("-")+1,overTime.lastIndexOf("-"));   
   
   request.setAttribute("ResponseFromEJBEvent",new CommandResponse(acctPayCols));
%>
<br>
<table width="560"  align="left" border="0"  cellpadding="2" cellspacing="2">
<tr>
  <td width="560" height="35" align="left">	
    	  <%=WebUtil.TimestampToString(TimestampUtility.getSystemDate(),"yyyy")%>&nbsp;
    	  <%=WebUtil.TimestampToString(TimestampUtility.getSystemDate(),"MM")%>&nbsp;
    	  <%=WebUtil.TimestampToString(TimestampUtility.getSystemDate(),"dd")%>&nbsp;	
  </td>  
</tr>
<tr>
	<td>
    <TABLE width="560"  align="left" border="0" cellpadding="0" cellspacing="0">
      <tr>
   	    <td width="370" height="20" >
   	 	   <table width="100%" align="left" cellspacing="1" cellpadding="1">
   	 	  	<tr>
   	 	  		<td width="15%">&nbsp;</td>
   	 	  		<td width="85%">&nbsp;<%=custDto.getName()%></td>
   	 	  	</tr>
   	 	   </table>
   	    </td>
   	    <td width="190" height="20" >
   	 	    <table width="100%"  align="left" cellspacing="1" cellpadding="1">
   	 	  	 <tr>
   	 	  	 	  <td width="30%">&nbsp;</td>
   	 	  	 	  <td width="70%" align="center">&nbsp;<%=custDto.getCustomerID()%></td>
   	 	  	 </tr>
   	 	    </table>
   	    </td>
       </tr>
       <tr>
   	   <td width="370" height="20" >
   	 	   <table width="100%"  cellspacing="1" cellpadding="1">
   	 	  	 <tr>
   	 	  		<td width="15%">&nbsp;</td>
   	 	  		<td width="75%">&nbsp;</td>
   	 	  		<td width="10%">&nbsp;</td>
   	 	  	</tr>
   	 	   </table>
   	   </td>
   	   <td width="190" height="20">
   	 	   <table width="100%" cellspacing="1" cellpadding="1">
   	 	  	 <tr>
   	 	  	 	  <td width="50%" align="center">&nbsp;</td>
   	 	  	 	  <td width="50%">&nbsp;</td>
   	 	  	 </tr>
   	 	   </table> 
   	   </td>
      </tr>
      <tr>
   	  <td width="370" height="20">
   	 	  <table width="100%" cellspacing="1" cellpadding="1">
   	 	  	<tr>
   	 	  		<td width="15%">&nbsp;</td>
   	 	  		<td width="75%">&nbsp;<%=custdistrictdes%></td>
   	 	  		<td width="10%">&nbsp;</td>
   	 	  	</tr>
   	 	  </table>
   	 	</td>
   	  <td width="190" height="20">
   	 	  <table width="100%" cellspacing="1" cellpadding="1">
   	 	  	 <tr>
   	 	  	 	  <td width="50%" align="center">&nbsp;<%=tel%></td>
   	 	  	 	  <td width="50%">&nbsp;</td>
   	 	  	 </tr>
   	 	  </table> 
   	  </td>
     </tr> 
     <tr>
   	  <td width="370" height="20">
   	 	  <table width="100%" cellspacing="1" cellpadding="1">
   	 	  	<tr>
             <td width="15%">&nbsp;</td>
             <td width="65%">&nbsp;&nbsp;
              <%
                 int singalsServiceCount = Postern.getSaCountByAcctIDAndStatusAndServiceID(acctId,"'N','I'",6);
                 if (singalsServiceCount >0){
              %>
                   	收视费截至日期：
                   	<%=overTime_Year%>年<%=overTime_Month%>月                   
              <%
                 }
              %>
             </td>
             <td width="20%">&nbsp;</td>
          </tr>
        </table>
      </td>
      <td width="190" height="20">
   	 	  <table width="100%" cellspacing="1" cellpadding="1">
   	 	  	 <tr>
   	 	  	 	  <td width="50%" align="center">&nbsp;<%=WebUtil.TimestampToString(TimestampUtility.getSystemDate(),"yyyy-MM-dd")%>	</td>
   	 	  	 	  <td width="50%">&nbsp;</td>
   	 	  	 </tr>
   	 	  </table> 
   	  </td>
     </tr>  
     <tr>
   	   <td width="370" height="20" >
   	 	  <table width="100%" align="left" cellspacing="1" cellpadding="1">
   	 	  	<tr>
   	 	  		<td width="15%">&nbsp;</td>
   	 	  		<td width="85%">&nbsp;</td>
   	 	  	</tr>
   	 	  </table>
   	   </td>
   	   <td width="190" height="20" >
   	 	  <table width="100%"  align="left" cellspacing="1" cellpadding="1">
   	 	  	 <tr>
   	 	  	 	  <td width="30%">&nbsp;</td>
   	 	  	 	  <td width="70%" align="center">&nbsp;</td>
   	 	  	 </tr>
   	 	  </table>
   	  </td>
     </tr> 
      <tr>
   	   <td width="370" height="20" >
   	 	  <table width="100%" align="left" cellspacing="1" cellpadding="1">
   	 	  	<tr>
   	 	  		<td width="15%">&nbsp;</td>
   	 	  		<td width="85%">&nbsp;<%=printPaySum%></td>
   	 	  	</tr>
   	 	  </table>
   	   </td>
   	   <td width="190" height="20" >
   	 	  <table width="100%"  align="left" cellspacing="1" cellpadding="1">
   	 	  	 <tr>
   	 	  	 	  <td width="30%">&nbsp;</td>
   	 	  	 	  <td width="70%" align="center">&nbsp;</td>
   	 	  	 </tr>
   	 	  </table>
   	  </td>
     </tr> 
     <tr>
   	   <td width="370" height="20" >
   	 	  <table width="100%" align="left" cellspacing="1" cellpadding="1">
   	 	  	<tr>
   	 	  		<td width="15%">&nbsp;</td>
   	 	  		<td width="85%">&nbsp;</td>
   	 	  	</tr>
   	 	  </table>
   	   </td>
   	   <td width="190" height="20" >
   	 	  <table width="100%"  align="left" cellspacing="1" cellpadding="1">
   	 	  	 <tr>
   	 	  	 	  <td width="30%">&nbsp;</td>
   	 	  	 	  <td width="70%" align="center">&nbsp;</td>
   	 	  	 </tr>
   	 	  </table>
   	  </td>
     </tr> 
     <tr>
   	   <td width="370" height="20" >
   	 	  <table width="100%" align="left" cellspacing="1" cellpadding="1">
   	 	  	<%
   	 	  	   double tempPrintPaySum =0;
   	 	  	   if (printPaySum <0) tempPrintPaySum = -1*printPaySum;
   	 	  	%>
   	 	  	<tr>
   	 	  		<td width="15%">&nbsp;</td>
   	 	  		<td width="85%">&nbsp;<%=WebOperationUtil.convertToChineseNumber(tempPrintPaySum)%></td>
   	 	  	</tr>
   	 	  </table>
   	   </td>
   	   <td width="190" height="20" >
   	 	  <table width="100%"  align="left" cellspacing="1" cellpadding="1">
   	 	  	 <tr>
   	 	  	 	  <td width="30%">&nbsp;</td>
   	 	  	 	  <td width="70%" align="center">&nbsp;<%=printPaySum%></td>
   	 	  	 </tr>
   	 	  </table>
   	    </td>
      </tr> 
      <tr>
   	   <td width="370" height="35" >
   	 	  <table width="100%" align="left" cellspacing="1" cellpadding="1">
   	 	  	<tr>
   	 	  		<td width="15%">&nbsp;</td>
   	 	  		<td width="85%">&nbsp;<oper:curoper property="OperatorName" /></td>
   	 	  	</tr>
   	 	  </table>
   	   </td>
   	   <td width="190" height="35" >
   	 	  <table width="100%"  align="left" cellspacing="1" cellpadding="1">
   	 	  	 <tr>
   	 	  	 	  <td width="30%">&nbsp;</td>
   	 	  	 	  <td width="70%" align="center">&nbsp;</td>
   	 	  	 </tr>
   	 	  </table>
   	  </td>
     </tr>  
     </table>
  </td>
 </tr>
</table> 
</body>
<script language="javascript" >
function window.onload()
{
   window.print();
}
</script>
 
 
                