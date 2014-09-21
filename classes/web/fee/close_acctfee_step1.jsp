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
<%@ page import="com.dtv.oss.dto.AcctItemTypeDTO" %>
<%@ page import="java.util.*" %>

<table align="center" width="100%" border="0" cellspacing="1" cellpadding="3" class="list_bg">
    <tr> 
       <td class="import_tit" align="center" colspan="5"><font size="3">帐户费用清单</font></td>
    </tr>
</table>     
<%
   int acctItemCount =0;
   String acctItemTotalStyle ="";
   double newAcctItemToatl =0;
   double acctItemTotal =0;
   double paymentRecordTotal =0;
   double prePaymentRecordTotoal =0;
   CommandResponse RepCmd = (CommandResponse)(request.getAttribute("ResponseFromEJBEvent"));
   if (RepCmd ==null) RepCmd = (CommandResponse)(request.getSession().getAttribute(CommonKeys.FEE_SESSION_NAME));
   if (RepCmd ==null) RepCmd =new CommandResponse(new ArrayList());
   request.getSession().setAttribute(CommonKeys.FEE_SESSION_NAME,RepCmd);
   request.setAttribute("ResponseFromEJBEvent",RepCmd);
%>

<lgc:bloop requestObjectName="ResponseFromEJBEvent" item="oneline" isOne="true">
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
        Collection acctItemList =(immeCountFeeInfo.getAcctItemList()==null) ? new ArrayList() : immeCountFeeInfo.getAcctItemList();
        Iterator   acctItemIter =acctItemList.iterator();
        while (acctItemIter.hasNext()){
            acctItemTotal =acctItemTotal+((AccountItemDTO)acctItemIter.next()).getValue();
            //acctItemTotal =(double)(Math.floor(acctItemTotal*100+((AccountItemDTO)acctItemIter.next()).getValue()*100+0.01)/100);
        }
       
        Collection paymentRecordList =(immeCountFeeInfo.getPaymentRecordList()==null) ? new ArrayList() : immeCountFeeInfo.getPaymentRecordList();
        Iterator  paymentRecordIter =paymentRecordList.iterator();
        while (paymentRecordIter.hasNext()){
            paymentRecordTotal =paymentRecordTotal +((PaymentRecordDTO)paymentRecordIter.next()).getAmount();
            //paymentRecordTotal =(double)(Math.floor(paymentRecordTotal*100+((PaymentRecordDTO)paymentRecordIter.next()).getAmount()*100+0.01)/100);
        }
        Collection prePaymentRecordList =(immeCountFeeInfo.getPrePaymentRecordList()==null) ? new ArrayList() : immeCountFeeInfo.getPrePaymentRecordList();
        Iterator  prePaymentRecordIter =prePaymentRecordList.iterator();
        while (prePaymentRecordIter.hasNext()){
            prePaymentRecordTotoal =prePaymentRecordTotoal+((PrepaymentDeductionRecordDTO)prePaymentRecordIter.next()).getAmount();
            //prePaymentRecordTotoal =(double)(Math.floor(prePaymentRecordTotoal*100+((PrepaymentDeductionRecordDTO)prePaymentRecordIter.next()).getAmount()*100+0.01)/100);
        }
     %>   
     <tr> 
     	 <input type="hidden" name="insideAcctPreCashDoposit" value="<%=WebUtil.bigDecimalFormat(immeCountFeeInfo.getPreCashDoposit())%>" >
       <input type="hidden" name="insideAcctPreTokenDoposit" value="<%=WebUtil.bigDecimalFormat(immeCountFeeInfo.getPreTokenDoposit())%>" >
    	 <td width="20%" class='list_bg1'   align="center" ><tbl:write name="oneline" property="accountid" /></td>
       <td width="20%" class='list_bg1'   align="center" ><tbl:write name="oneline" property="accountName" /></td>
       <td width="20%" class='list_bg1'   align="center" ><tbl:write name="oneline" property="preCashDoposit" /></td> 
       <td width="20%" class='list_bg1'   align="center" ><tbl:write name="oneline" property="preTokenDoposit" /></td>
       <td width="20%" class='list_bg1'   align="center" ><%=WebUtil.bigDecimalFormat(immeCountFeeInfo.getPreCashDoposit()+immeCountFeeInfo.getPreTokenDoposit())%></td>  
     </tr>
     <tr>
       <td width="80%" class='list_bg2'   align="right" colspan="4">未入帐的费用总计：</td>    
       <td width="20%" class='list_bg1'   align="center" >
       	  <%=WebUtil.bigDecimalFormat(acctItemTotal)%>
       	  <input type="hidden" name="acctItemTotal" value ="<%=WebUtil.bigDecimalFormat(acctItemTotal)%>" >
       </td>  
     </tr>
     <tr>
       <td width="80%" class='list_bg2'   align="right" colspan="4">未入帐的抵扣总计：</td>    
       <td width="20%" class='list_bg1'   align="center" >
       	   <%=WebUtil.bigDecimalFormat(prePaymentRecordTotoal)%>
       	   <input type="hidden" name="prePaymentRecordTotoal" value ="<%=WebUtil.bigDecimalFormat(prePaymentRecordTotoal)%>" >
       </td>  
     </tr>
     <tr>
       <td width="80%" class='list_bg2'   align="right" colspan="4">未入帐的支付总计：</td>    
       <td width="20%" class='list_bg1'   align="center" >
       	   <%=WebUtil.bigDecimalFormat(paymentRecordTotal)%>
       	   <input type="hidden" name="paymentRecordTotal"  value ="<%=WebUtil.bigDecimalFormat(paymentRecordTotal)%>" >
       </td>  
     </tr>
   </table> 
</lgc:bloop>

<lgc:bloop requestObjectName="ResponseFromEJBEvent" item="oneline" isOne="true">
<%
   ImmediateCountFeeInfo immeCountFeeInfo = (ImmediateCountFeeInfo)pageContext.getAttribute("oneline");
   Collection newAcctItemList = immeCountFeeInfo.getNewAccountItem();
   if (newAcctItemList !=null && newAcctItemList.size() >0){ 
       CommandResponse acctItemImp= new CommandResponse(newAcctItemList);
       request.setAttribute("acctItemImp",acctItemImp);
%>
    <table align="center" width="100%" border="0" cellspacing="1" cellpadding="3" class="list_bg">
      <tr> 
         <td class="import_tit" align="center" colspan="5"><font size="3">新产生的费用清单</font></td>
      </tr>
    </table>     
    <table align="center" width="100%" border="0" cellspacing="1" cellpadding="3" class="list_bg" >    
	     <tr class='list_head'> 
       		<td width="40%" class='list_head'   align="center" >账目类型</td>
       		<td width="20%" class='list_head'   align="center" >费用类型</td>
       		<td width="20%" class='list_head'   align="center" >产品</td> 
       		<td width="20%" class='list_head'   align="center" >金额(元)</td>   
    	 </tr>
      <lgc:bloop requestObjectName="acctItemImp" item="acctItem">  
       <%
          acctItemCount =acctItemCount +1;
          AccountItemDTO acctItemDto =(AccountItemDTO)pageContext.getAttribute("acctItem");
          AcctItemTypeDTO acctItemTypeDto = Postern.getAcctItemTypeDTOByAcctItemTypeID(acctItemDto.getAcctItemTypeID());
          String  feeType = acctItemTypeDto.getAcctItemTypeName();
          String  productName =Postern.getProductNameByID(acctItemDto.getProductID());
          newAcctItemToatl =newAcctItemToatl +acctItemDto.getValue();
          //newAcctItemToatl =(double)(Math.floor(newAcctItemToatl*100+acctItemDto.getValue()*100+0.01)/100);
       %>
       <tbl:printcsstr style1="list_bg1" style2="list_bg2" overStyle="list_over" >             
         <td  valign="middle" align="center" ><%=(feeType==null) ? "" :feeType %></td>
         <td  valign="middle" align="center" ><d:getcmnname typeName="SET_F_BRFEETYPE" match="<%=acctItemTypeDto.getFeeType()%>" /></td>
         <td  valign="middle" align="center" ><%=(productName ==null) ? "" : productName%></td>          
         <td  valign="middle" align="center" ><tbl:write name="acctItem" property="Value" /></td>
       </tbl:printcsstr> 
      </lgc:bloop> 
    </table>
<% 
    if (acctItemCount %2 ==0) 
      acctItemTotalStyle ="list_bg1";
    else 
      acctItemTotalStyle ="list_bg2"; 
    acctItemCount =acctItemCount +1;

    //if (newAcctItemToatl !=0){
    if (Math.abs(newAcctItemToatl)>0.0001){
%>
   <table align="center" width="100%" border="0" cellspacing="1" cellpadding="3" class="list_bg" >
	   <tr class='<%=acctItemTotalStyle%>'>
       <td  valign="middle" align="right"> 新费用总计: 
        <input type="text"  size="12" style="text-align:right" name="newAcctItemToatl" value="<%=WebUtil.bigDecimalFormat(newAcctItemToatl)%>" class="textgray" readonly>
       </td>
     </tr>
  </table>
<%
    }
 }
%>
</lgc:bloop>
<% 
    if (acctItemCount %2 ==0) 
      acctItemTotalStyle ="list_bg1";
    else 
      acctItemTotalStyle ="list_bg2"; 
            
    double  feeTotal = newAcctItemToatl + acctItemTotal - paymentRecordTotal - prePaymentRecordTotoal;
    //double  feeTotal =(double)(Math.floor(newAcctItemToatl*100+acctItemTotal*100-paymentRecordTotal*100- prePaymentRecordTotoal*100+0.01)/100);
    
    request.setAttribute("defaultGeneralFeeTotal",new Double(WebUtil.bigDecimalFormat(feeTotal).doubleValue()));
%>
<table align="center" width="100%" border="0" cellspacing="1" cellpadding="3" class="list_bg" >
	  <tr class='<%=acctItemTotalStyle%>'>
       <td  valign="middle" align="right">应收费用总计: 
        <input type="text"  size="12" style="text-align:right" name="generalFeeTotal" value="<%=WebUtil.bigDecimalFormat(feeTotal)%>" class="textgray" readonly>
       </td>
    </tr>
</table>




