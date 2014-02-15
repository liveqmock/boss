<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="osstags" prefix="d" %>
<%@ taglib uri="bookmark" prefix="bk" %>
<%@ page import="com.dtv.oss.dto.AccountItemDTO ,
                 com.dtv.oss.dto.AcctItemTypeDTO ,
                 com.dtv.oss.dto.AccountDTO ,
                 com.dtv.oss.util.Postern ,
                 com.dtv.oss.service.util.CommonConstDefinition " %>
<script language="Javascript" > 
   function fee_adjust(){
       document.frmPost.action ="account_adjust_pay_fee.screen";
       document.frmPost.submit();
   }
                
</script>
<table  border="0" align="center" cellpadding="0" cellspacing="10">
  <tr>
    <td><img src="img/list_tit.gif" width="19" height="19"></td>
    <td class="title">费用详细信息</td>
  </tr>
</table>
<table width="100%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>
<br>  
 
<form name="frmPost" method="post">
<lgc:bloop requestObjectName="ResponseQueryResult" item="oneline" isOne="true">
<%
       AccountItemDTO dto=(AccountItemDTO)pageContext.getAttribute("oneline");
       String accItemTypeName = "";
       String strBillingCycleName = "";
       AcctItemTypeDTO acctemTypeDto =Postern.getAcctItemTypeDTOByAcctItemTypeID(dto.getAcctItemTypeID()); 
       accItemTypeName = acctemTypeDto.getAcctItemTypeName();
       if (accItemTypeName ==null) accItemTypeName ="";
       strBillingCycleName = Postern.getBillingCycleNameByID(dto.getBillingCycleID());
       if (strBillingCycleName ==null) strBillingCycleName ="";
       AccountDTO accountDto =Postern.getAccountDto(dto.getAcctID());
        String operatorName =Postern.getOperatorNameByID(dto.getOperatorID());
       String orgName = Postern.getOrgNameByID(dto.getOrgID());
       if (operatorName == null) operatorName = "";
       if (orgName == null) orgName = "";
             	
   
    
%>
<table width="810" border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
     <input type="hidden" name="txtAccountID" value="<%=dto.getAcctID()%>" >
     <input type="hidden" name="txtTokenDeposit" value="<%=accountDto.getTokenDepositID()%>" >
     <input type="hidden" name="txtCashDeposit" value="<%=accountDto.getCashDeposit()%>" >
     
     <tr>
        <td valign="middle" class="list_bg2" align="right" width="17%" >流水号</td>
        <td width="33%" class="list_bg1"><font size="2">
             <tbl:write name="oneline" property="AiNO"/>
        </font></td>
        <td valign="middle" class="list_bg2" align="right" width="17%" >创建日期</td>
	      <td width="33%" class="list_bg1"><font size="2">
              <tbl:writedate name="oneline" property="createTime" />
        </font></td>
            
     </tr>
     <tr>
	 <td valign="middle" class="list_bg2" align="right" >操作员</td>
          <td class="list_bg1" ><font size="2"><%=operatorName%></font></td>
          <td valign="middle" class="list_bg2" align="right" >机构</td>
          <td class="list_bg1"><font size="2">
          <%=orgName%>
          </font></td>
          </tr>
       
      <tr>
       <td valign="middle" class="list_bg2" align="right" width="17%" >客户证号</td>
	      <td width="33%" class="list_bg1"><font size="2">
              <tbl:write name="oneline" property="custID"/>
              <input type="hidden" name="txtCustomerID" value="<tbl:write name="oneline" property="custID" />" >
        </font></td>
        <td valign="middle" class="list_bg2" align="right" width="17%" >帐单号</td>
         <td width="33%" class="list_bg1"><font size="2">
            <tbl:write name="oneline" property="InvoiceNO" /></td>
         </font></td>
      </tr>
      <tr>
       <td valign="middle" class="list_bg2" align="right" width="17%" >订户号</td>
	      <td width="33%" class="list_bg1"><font size="2">
              <tbl:write name="oneline" property="serviceAccountID"/>
              
        </font></td>
        <td valign="middle" class="list_bg2" align="right" width="17%" >客户产品ID</td>
         <td width="33%" class="list_bg1"><font size="2">
            <tbl:write name="oneline" property="psID" /></td>
         </font></td>
      </tr>
      <tr>
          <td valign="middle" class="list_bg2" align="right" width="17%" >Date1</div></td>
	        <td width="33%" class="list_bg1"><font size="2">
             <tbl:writedate name="oneline" property="date1" includeTime="true" />
          </font></td>
          <td valign="middle" class="list_bg2" align="right" width="17%" >Date2</div></td>
	        <td width="33%" class="list_bg1"><font size="2">
            <tbl:writedate name="oneline" property="date2" includeTime="true" />
          </font></td>
       </tr>
        <tr>
       <td valign="middle" class="list_bg2" align="right" width="17%" >BrID</td>
	      <td width="33%" class="list_bg1"><font size="2">
              <tbl:write name="oneline" property="BrID"/>
              
        </font></td>
       <td valign="middle" class="list_bg2" align="right" width="17%" >计费周期</td>
	      <td width="33%" class="list_bg1"><font size="2">
              <%=strBillingCycleName%>
        </font></td>
      </tr>
      <tr>
         <td valign="middle" class="list_bg2" align="right" width="17%" >费用类型</td>
	      <td width="33%" class="list_bg1"><font size="2">
              <d:getcmnname typeName="SET_F_BRFEETYPE" match="oneline:feeType" />
             
        </font></td>
        
        <td valign="middle" class="list_bg2" align="right" width="17%" >帐目类型</td>
	      <td width="33%" class="list_bg1"><font size="2">
              <%=accItemTypeName%>
        </font></td>
      </tr>
      
      <tr>
      <td valign="middle" class="list_bg2" align="right" width="17%" >金额</td>
        <td width="33%" class="list_bg1"><font size="2">
            <tbl:write name="oneline" property="Value"/>
        </font></td>   
        <td valign="middle" class="list_bg2" align="right" width="17%" >强制预存标记</td>
         <td width="33%" class="list_bg1"><font size="2">
            <d:getcmnname typeName="SET_G_YESNOFLAG" match="oneline:forcedDepositFlag" />
         </font></td> 
        </tr>  
        <tr>
          <td valign="middle" class="list_bg2" align="right" width="17%" >单据类型</td>
        <td width="33%" class="list_bg1"><font size="2">
          <d:getcmnname typeName="SET_F_FTREFERTYPE" match="oneline:referType" />
        </font></td>   
        <td valign="middle" class="list_bg2" align="right" width="17%" >单据号</td>
         <td width="33%" class="list_bg1"><font size="2">
            <tbl:write name="oneline" property="referID"/>
         </font></td> 
        </tr> 
          <tr>
         <td valign="middle" class="list_bg2" align="right" width="17%" >出帐标记</td>
         <td width="33%" class="list_bg1"><font size="2">
            <d:getcmnname typeName="SET_F_FTCREATINGMETHOD" match="oneline:invoiceFlag" />
         </font></td>
          <td valign="middle" class="list_bg2" align="right" width="17%" >帐单号</td>
	      <td width="33%" class="list_bg1"><font size="2">
             <tbl:write name="oneline" property="invoiceNO"/>
        </font></td>
        
      </tr> 
      <tr>
       <td valign="middle" class="list_bg2" align="right" width="17%" >销账金额 </td>
	      <td width="33%" class="list_bg1"><font size="2">
              <tbl:write name="oneline" property="setOffAmount" />
        </font></td>
         <td valign="middle" class="list_bg2" align="right" width="17%" >销账标志</td>
	      <td width="33%" class="list_bg1"><font size="2">
	         <input type="hidden" name="txtSetOffFlag" value ="<tbl:write name="oneline" property="SetOffFlag" />" >
	         <d:getcmnname typeName="SET_F_SETOFFFLAG" match="oneline:SetOffFlag"/>
        </font></td>
        
        
      </tr>
       <tr>
         <td valign="middle" class="list_bg2" align="right" width="17%" >创建来源</td>
         <td width="33%" class="list_bg1"><font size="2">
            <d:getcmnname typeName="SET_F_FTCREATINGMETHOD" match="oneline:CreatingMethod" />
         </font></td>
          <td valign="middle" class="list_bg2" align="right" width="17%" >批号</td>
	      <td width="33%" class="list_bg1"><font size="2">
             <tbl:write name="oneline" property="BatchNO"/>
        </font></td>
        
      </tr>
      
       <tr>
        <td valign="middle" class="list_bg2" align="right" width="17%" >来源记录ID</td>
	      <td width="33%" class="list_bg1"><font size="2">
             <tbl:write name="oneline" property="sourceRecordID"/>
        </font></td>
        <td valign="middle" class="list_bg2" align="right" width="17%" >状态</td>
	      <td width="33%" class="list_bg1"><font size="2">
            <d:getcmnname typeName="SET_F_FTSTATUS" match="oneline:Status"/>
         </font></td>
       
      </tr>
     
 </table>
     <BR>
     <table align="center" border="0" cellspacing="0" cellpadding="0">
        <tr>
	       <bk:canback url="bill_view.do/fee_record_query.do/fee_detailrecord_conditionQuery.do" >
	         <td><img src="img/button2_r.gif" width="22" height="20"></td>      
                <td background="img/button_bg.gif"  height="20" >
	              <a href="<bk:backurl property="bill_view.do/fee_record_query.do/fee_detailrecord_conditionQuery.do" />" class="btn12">返   回</a></td>
	         <td><img src="img/button2_l.gif" width="13" height="20"></td>
	       </bk:canback> 
	    
	 <%
	   if (dto.getInvoiceNO() ==0 && 
	        (dto.getCreatingMethod().equals(CommonConstDefinition.FTCREATINGMETHOD_I) || dto.getCreatingMethod().equals(CommonConstDefinition.FTCREATINGMETHOD_X))){
	 %>
	       <td width="20"></td>
	       <td><img src="img/button2_r.gif" width="22" height="20"></td>      
         <td background="img/button_bg.gif"  height="20" >
	            <a href="javascript:fee_adjust()" class="btn12">费用调帐</a></td>
	       <td><img src="img/button2_l.gif" width="13" height="20"></td>        
	  <% } %>       
        </tr>
      </table>  
      
</lgc:bloop>       
</form>