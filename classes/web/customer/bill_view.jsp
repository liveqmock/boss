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
<%@ page import="com.dtv.oss.dto.MethodOfPaymentDTO"%>
<%@ page import="com.dtv.oss.dto.AccountItemDTO,
                 com.dtv.oss.dto.AcctItemTypeDTO,
                 com.dtv.oss.dto.PaymentRecordDTO,
                 com.dtv.oss.dto.PrepaymentDeductionRecordDTO" %>
<%@ page import="java.util.*" %>
<%@ page import="com.dtv.oss.dto.CustomerDTO" %>

<Script language=javascript>
<!--

function check_payment_record_submit()
{
     document.frmPost.action="fee_record_query.do";
     document.frmPost.submit();

}
function back_billquery(){
	  document.frmPost.action="menu_bill_query.do";
	  document.frmPost.submit(); 
}

function pay_submit(){
     document.frmPost.action="bill_pay_op.do";
     document.frmPost.submit();
}

function bill_adjust(){
    document.frmPost.action="bill_adjust.do";
    document.frmPost.submit();
}

function view_fee_detail_click(aiNO) {
    self.location.href = "fee_detailrecord_query.do?txtID="+aiNO;
}

function view_pay_detail_click(seqNO) {
    self.location.href = "payment_record_detail.do?txtID="+seqNO;
}

function view_prepaydeduction_detail_click(seqNO){
	var strURL="prepayment_deduction_record_detail.do?txtSeqNO="+seqNO+"&txtActionType=detail";
	self.location.href=strURL;
}
//-->
</SCRIPT>
<table border="0" cellspacing="0" cellpadding="0" width="820" >
<tr>
<td>
<table  border="0" align="center" cellpadding="0" cellspacing="10" >
  <tr>
    <td><img src="img/list_tit.gif" width="19" height="19"></td>
    <td class="title">查看账单</td>
</tr>
</table>
<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>

 
<br>

<form name="frmPost" method="post" action="bill_pay_prepare.screen" >
<%
       String strStatus=null;
%>    
<lgc:bloop requestObjectName="ResponseQueryResult" item="oneline">
<% 
  com.dtv.oss.dto.InvoiceDTO invoiceDTO=(com.dtv.oss.dto.InvoiceDTO)pageContext.getAttribute("oneline");
	strStatus = invoiceDTO.getStatus();
	if (strStatus==null) strStatus="";
        String operatorName =Postern.getOperatorNameByID(invoiceDTO.getPayOpId());
        String orgName = Postern.getOrgNameByID(invoiceDTO.getCreateOrgId());
        if (operatorName == null) operatorName = "";
        if (orgName == null) orgName = "";
	
    String strBillingCycleName = Postern.getBillingCycleNameByID(invoiceDTO.getInvoiceCycleId());
    if (strBillingCycleName==null) strBillingCycleName="";
    
    CustomerDTO custDto=Postern.getCustomerByID(invoiceDTO.getCustID());
    pageContext.setAttribute("customerDTO",custDto);
    String strName = custDto.getName();
    if (strName==null) strName="";
    
    int distinctID =0;
    String tempID =Postern.getAddressCountyByCustomerID(invoiceDTO.getCustID());
    if (tempID !=null && !"".equals(tempID))
        distinctID =Integer.parseInt(tempID);
%>          
   <table width="100%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
      <input type="hidden" name="txtInvoiceNo" value="<tbl:write name="oneline" property="InvoiceNo"/>" >
      <input type="hidden" name="txtCustomerID" value="<tbl:write name="oneline" property="custID"/>">   
      <input type="hidden" name="txtBarCode" value="<tbl:write name="oneline" property="barCode"/>">	
      <input type="hidden" name="txtAccountID" value="<tbl:write name="oneline" property="AcctID"/>">	
      <input type="hidden" name="txtBillAmount" value="<tbl:write name="oneline" property="amount"/>" >	
     	<input type="hidden" name="txtBillBcf" value="<tbl:write name="oneline" property="Bcf" />" >
     	<input type="hidden" name="txtBillFeeTotal" value="<tbl:write name="oneline" property="feeTotal" />" >	
      <input type="hidden" name="txtStatus" value="<tbl:write name="oneline" property="status" />" >
      	
        <tr>
          <td class="list_bg2" align="right">账单条形码</td>
          <td class="list_bg1" colspan="3"><font size="2">
             <tbl:write name="oneline" property="barCode"/> 
          </font></td>
        </tr>
        <tr> 
          <td valign="middle" class="list_bg2" align="right" width="17%" >账单号</td>
          <td width="33%" class="list_bg1"><font size="2">
           <tbl:write name="oneline" property="InvoiceNo"/> 
          </font></td>
          <td valign="middle" class="list_bg2" align="right" width="17%" >帐户号</td>
          <td  class="list_bg1" width="33%"><font size="2">
            <tbl:write name="oneline" property="AcctID"/> 
          </font></td>                   
        </tr> 
        <tr>
        	 <td valign="middle" class="list_bg2" align="right" >客户证号</td>
           <td class="list_bg1" ><tbl:write name="oneline" property="custID" /></td>
	         <td valign="middle" class="list_bg2" align="right" >客户姓名</td>
           <td class="list_bg1" ><font size="2"><%=strName%></font></td>    
        </tr>
        <tr>
          <td valign="middle" class="list_bg2" align="right" width="17%">帐务周期</td>
          <td class="list_bg1" ><font size="2">
             <%=strBillingCycleName%>
          </font></td>
          <td valign="middle" class="list_bg2" align="right">帐单应付金额</td>
          <td class="list_bg1"><font size="2">
             <tbl:write name="oneline" property="amount"/> 
          </font></td>
        </tr>
        <tr> 
        	<td valign="middle" class="list_bg2" align="right">帐单生成日期</td>
          <td class="list_bg1"><font size="2">
             <tbl:writedate name="oneline" property="CreateDate" includeTime="true" /> 
           </font></td>
          <td valign="middle" class="list_bg2" align="right">帐单逾期日</td>
          <td class="list_bg1"><font size="2">
             <tbl:writedate name="oneline" property="DueDate" includeTime="true" /> 
          </font></td>     
        </tr>
        <tr>          
          <td valign="middle" class="list_bg2" align="right">上期余额结转</td>
          <td class="list_bg1"><font size="2">
           <tbl:write name="oneline" property="Bcf"/> 
          </font></td>
          <td valign="middle" class="list_bg2" align="right">本期费用累计</td>
          <td class="list_bg1"><font size="2">
           <tbl:write name="oneline" property="feeTotal"/> 
          </font></td>
        </tr>
        <tr>          
          <td valign="middle" class="list_bg2" align="right">本期支付累计</td>
          <td class="list_bg1"><font size="2">
           <tbl:write name="oneline" property="paymentTotal"/> 
          </font></td>
          <td valign="middle" class="list_bg2" align="right">本期预存累计</td>
          <td class="list_bg1"><font size="2">
           <tbl:write name="oneline" property="prepaymentTotal"/> 
          </font></td>
        </tr>
        <tr>          
          <td valign="middle" class="list_bg2" align="right">本期预存抵扣累计</td>
          <td class="list_bg1"><font size="2">
           <tbl:write name="oneline" property="prepaymentDeductionTotal"/> 
          </font></td>
          <td valign="middle" class="list_bg2" align="right" >零头结转</td>
          <td class="list_bg1" ><font size="2"><tbl:write name="oneline" property="smallChange" /></font></td>
        </tr>
        <tr>
        	 <td class="list_bg2" align="right">帐单来源</td>
           <td class="list_bg1"><font size="2">
              <d:getcmnname typeName="SET_F_INVOICESOURCETYPE" match="oneline:invoiceSourceType" /> 
           </font></td> 
        	 <td class="list_bg2" align="right">滞纳金</td>
           <td class="list_bg1"><font size="2">
              <tbl:write name="oneline" property="punishment" /> 
           </font></td>       
        </tr> 
        <tr> 	
        	<td valign="middle" class="list_bg2" align="right" >关联号</td>
          <td class="list_bg1" ><font size="2"><tbl:write name="oneline" property="referenceNo"/></font></td>
	        <td valign="middle" class="list_bg2" align="right" >处理批号</td>
          <td class="list_bg1" ><font size="2"><tbl:write name="oneline" property="batchNo"/> </font></td>
        </tr>
        <tr>  
        	<td valign="middle" class="list_bg2" align="right">账单支付日</td>
          <td class="list_bg1"><font size="2">
            <tbl:writedate name="oneline" property="PayDate" includeTime="true"/> 
          </font></td>         
          <td valign="middle" class="list_bg2" align="right">账单支付总额</td>
          <td class="list_bg1"><font size="2">
           <tbl:write name="oneline" property="payAmount"  /> 
          </font></td>	               
        </tr>
        <tr> 
           <td valign="middle" class="list_bg2" align="right">账单支付人</td>
          <td class="list_bg1"><font size="2">   
            <%=operatorName%>
          </font></td>   
           <td valign="middle" class="list_bg2" align="right">账单支付机构</td>
          <td class="list_bg1"><font size="2">         
             <tbl:WriteOrganizationInfo name="oneline" property="payOrgId" /> 
          </font></td>          
        </tr>  
        <tr>
        	<td valign="middle" class="list_bg2" align="right">客户所在区</td>
          <td class="list_bg1" ><font size="2">  
           <%=Postern.getDistrictDesc(distinctID)%>        
          </font></td>
        	<td valign="middle" class="list_bg2" align="right">账单状态</td>
          <td class="list_bg1" ><font size="2">          
           <d:getcmnname typeName="SET_F_INVOICESTATUS" match="oneline:status" /> 
          </font></td>
        </tr>
        <tr>
        	<td valign="middle" class="list_bg2" align="right">备注信息</td>
          <td class="list_bg1" colspan="3"><font size="2">  
            <tbl:write name="oneline" property="comments" />       
          </font></td>
        </tr>
    </table>  
<BR>
<%
    Collection accountItemCols =Postern.getAccountItemByInvoiceNo(invoiceDTO.getInvoiceNo());
    if (accountItemCols !=null && !accountItemCols.isEmpty()){
%>
<table width="100%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
  <tr>
    <td align="center" class="import_tit"><font size="3">帐单费用记录<A href="javascript:drawSelfMenu('1')"><IMG id="arr1" alt="展开下级查询条件" src="img/icon_bottom.gif" border=0></A></font></td>
  </tr>    
</table>
<table width="100%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg" id="mnu1" style="display:none">
    <tr class="list_head">
       <td class="list_head">流水号</td>
       <td class="list_head">创建时间</td>
       <td class="list_head">费用类型</td>
       <td class="list_head">帐目类型</td>
       <td class="list_head">金额</td>
       <td class="list_head">计费周期</td>
       <td class="list_head">单据类型</td>
       <td class="list_head">创建来源</td>
       <td class="list_head">状态</td>
    </tr>
       
    <%
       Iterator acctItemIter = accountItemCols.iterator();
       while(acctItemIter.hasNext()){
           AccountItemDTO dto = (AccountItemDTO)acctItemIter.next();
           pageContext.setAttribute("DTO",dto);
           AcctItemTypeDTO  acctItemTypeDto=Postern.getAcctItemTypeDTOByAcctItemTypeID(dto.getAcctItemTypeID());
           String acctItemTypeName =acctItemTypeDto.getAcctItemTypeName();      
           String strjfCycleName = Postern.getBillingCycleNameByID(dto.getBillingCycleID());
           if (strjfCycleName==null) strjfCycleName="";     
    %>
        
       <tbl:printcsstr style1="list_bg1" style2="list_bg2" overStyle="list_over" >
 
         <td align="center" ><a href="javascript:view_fee_detail_click('<tbl:write name="DTO" property="aiNO"/>')" class="link12" >
         <tbl:writenumber name="DTO" property="aiNO" digit="7" /></a></td>
         <td align="center" ><tbl:writedate name="DTO" property="createTime" /><br><tbl:writedate name="DTO" property="createTime" onlyTime="true"/></td>
         <td align="center" ><d:getcmnname typeName="SET_F_BRFEETYPE" match="DTO:feeType" /></td>
         <td align="center" ><%=acctItemTypeName%></td>
         <td align="center" ><tbl:write name="DTO" property="value"/></td>
         <td align="center" ><%=strjfCycleName%></td>
         <td align="center" ><d:getcmnname typeName="SET_F_FTREFERTYPE" match="DTO:ReferType" /></td>
         <td align="center" ><d:getcmnname typeName="SET_F_FTCREATINGMETHOD" match="DTO:CreatingMethod" /></td>
         <td align="center" ><d:getcmnname typeName="SET_F_FTSTATUS" match="DTO:Status"/></td>
       </tbl:printcsstr>
<%     
      }
%>
</table>
<% 
    }
%>

<%  
     Collection paymentRecordCols =Postern.getPaymentRecordByInvoiceNo(invoiceDTO.getInvoiceNo());
     if (paymentRecordCols !=null && !paymentRecordCols.isEmpty()){
%>
<table width="100%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
  <tr>
    <td align="center" class="import_tit"><font size="3">帐单支付记录<A href="javascript:drawSelfMenu('2')"><IMG id="arr2" alt="展开下级查询条件" src="img/icon_bottom.gif" border=0></A></font></td>
  </tr>    
</table>
<table width="100%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg" id="mnu2" style="display:none">
    <tr class="list_head">
      <td class="list_head" nowrap>流水号</td>
      <td class="list_head">创建时间</td>
      <td class="list_head">收费人</td>
      <td class="list_head">收费机构</td>
      <td class="list_head">帐户号</td>
      <td class="list_head">付费方式</td>
      <td class="list_head">类别</td>
      <td class="list_head">金额</td>
      <td class="list_head">单据类型</td>
      <td class="list_head">来源类型</td>
      <td class="list_head">状态</td>
    </tr>
    
    <%
       Map  payMap =Postern.getAllMethodOfPayments();
       Iterator paymentRecordIter = paymentRecordCols.iterator();
       while(paymentRecordIter.hasNext()){
           PaymentRecordDTO dto = (PaymentRecordDTO) paymentRecordIter.next();
           pageContext.setAttribute("paymentRecord",dto);
           MethodOfPaymentDTO paymentDto =(MethodOfPaymentDTO)payMap.get(String.valueOf(dto.getMopID()));
           String payMopName =paymentDto.getName();
           if (payMopName==null) payMopName="";
           
           String strOpName=Postern.getOperatorNameByID(dto.getOpID());
           if(strOpName==null)  strOpName="";
           
           String strOrgName=Postern.getOrgNameByCustomerID(dto.getCustID());
           if(strOrgName==null) strOrgName="";
           
        %>
        <tbl:printcsstr style1="list_bg1" style2="list_bg2" overStyle="list_over" >
          <td align="center" ><a href="javascript:view_pay_detail_click('<tbl:write name="paymentRecord" property="seqNo"/>')" class="link12">
          <tbl:writenumber name="paymentRecord" property="seqNo" digit="7" /></a></td>
          <td align="center" ><tbl:writedate name="paymentRecord" property="createTime" /><br><tbl:writedate name="paymentRecord" property="createTime" onlyTime="true"/></td>
          <td align="center" ><%=strOpName %></td>
          <td align="center" ><%=strOrgName %></td>
          <td align="center" ><tbl:write name="paymentRecord" property="acctID"/></td>
          <td align="center" ><%=payMopName%></td>
          <td align="center" ><d:getcmnname typeName="SET_F_PAYMENTRECORDTYPE" match="paymentRecord:PayType"/></td>
          <td align="center" ><tbl:write name="paymentRecord" property="Amount"/></td>
          <td align="center" ><d:getcmnname typeName="SET_F_FTREFERTYPE" match="paymentRecord:referType" /></td>  
          <td align="center" ><d:getcmnname typeName="SET_F_PAYMENTRECORDSOURCETYPE" match="paymentRecord:SourceType"/></td>
          <td align="center" ><d:getcmnname typeName="SET_G_GENERALSTATUS" match="paymentRecord:Status" /></td>
      </tbl:printcsstr>
<%
      }
%>
</table>
<% 
   }
%>
 
<%
    Collection prepaymentDeductionCols =Postern.getPrepaymentDeductionRecord(invoiceDTO.getInvoiceNo());
    if (prepaymentDeductionCols !=null && !prepaymentDeductionCols.isEmpty()){
%> 
<table width="100%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
  <tr>
    <td align="center" class="import_tit" ><font size="3">帐单预存抵扣记录<A href="javascript:drawSelfMenu('3')"><IMG id="arr3" alt="展开下级查询条件" src="img/icon_bottom.gif" border=0></A></font></td>
  </tr>    
</table>
<table width="100%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg" id="mnu3" style="display:none"> 
   <tr class="list_head">
     <td class="list_head">流水号</td>
     <td class="list_head">创建时间</td>
     <td class="list_head">帐户号</td>
     <td class="list_head">预存类型</td>
     <td class="list_head">金额</td>
     <td class="list_head">抵扣方式</td>
     <td class="list_head">抵扣对象</td>
     <td class="list_head">创建来源</td>
     <td class="list_head">状态</td>
   </tr>
   
  <%
     Iterator prepaymentDeductionIter = prepaymentDeductionCols.iterator();
     while(prepaymentDeductionIter.hasNext()){
         PrepaymentDeductionRecordDTO dto = (PrepaymentDeductionRecordDTO)prepaymentDeductionIter.next();
         pageContext.setAttribute("prepaymentDeduction",dto);
         String  referType = dto.getReferRecordType();
         int  referId =dto.getReferRecordId();	
    %>				    	                 
      <tbl:printcsstr style1="list_bg1" style2="list_bg2" overStyle="list_over" >
        <td align="center" ><a href="javascript:view_prepaydeduction_detail_click('<tbl:write name="prepaymentDeduction" property="seqNo"/>')" class="link12" >
        <tbl:writenumber name="prepaymentDeduction" property="seqNo"  digit="7" /></a></td>
        <td align="center" ><tbl:writedate name="prepaymentDeduction" property="createTime"/><br><tbl:writedate name="prepaymentDeduction" property="createTime" onlyTime="true"/></td>
        <td align="center" ><tbl:write name="prepaymentDeduction" property="acctId"  /></td>
        <td align="center" ><d:getcmnname typeName="SET_F_PREPAYMENTTYPE" match="prepaymentDeduction:prepaymentType" /></td>
        <td align="center" ><tbl:write name="prepaymentDeduction" property="amount" /></td>
        <td align="center" ><d:getcmnname typeName="SET_F_PDR_REFERRECORDTYPE" match="prepaymentDeduction:referRecordType" /></td>
        <td align="center" ><tbl:write name="prepaymentDeduction" property="referRecordId"/></td>
        <td align="center" ><d:getcmnname typeName="SET_F_FTCREATINGMETHOD" match="prepaymentDeduction:creatingMethod" /></td>    
        <td align="center" ><d:getcmnname typeName="SET_F_FTSTATUS" match="prepaymentDeduction:status" /></td>
      </tbl:printcsstr>
<%
      }
%>
</table>
<% 
   }
%>
<br>
<table align="center"  border="0" cellspacing="0" cellpadding="0">
   <tr>
   	<bk:canback url="bill_query_result_for_cust.do/bill_query.do" >
  	  <td><img src="img/button2_r.gif" width="22" height="20"></td>
      <td background="img/button_bg.gif"  ><a href="<bk:backurl property="bill_query_result_for_cust.do/bill_query.do" />"  class="btn12">返 回</a></td>
      <td><img src="img/button2_l.gif" width="12" height="20"></td>
    </bk:canback> 
<%
    String returnFlag =(request.getParameter("returnFlag")==null) ? "" : request.getParameter("returnFlag");
    if (returnFlag.equals("true")){
%>
      <td width="20" ></td>
      <td><img src="img/button2_r.gif" width="22" height="20"></td>
      <td background="img/button_bg.gif"  ><a href="javascript:pay_submit()" class="btn12">返 回</a></td>
      <td><img src="img/button2_l.gif" width="12" height="20"></td>  
<%  
    } 
    else{
%>	 
 <d:displayControl id="link_bill_view_bill_pay" bean="oneline,customerDTO">
 <pri:authorized name="bill_pay.do" >
           <td width="20" ></td>
       	   <td><img src="img/button_l.gif" width="11" height="20"></td>
           <td background="img/button_bg.gif"  ><a href="javascript:pay_submit()" class="btn12">立即支付</a></td>
           <td><img src="img/button_r.gif" width="22" height="20"></td>  
 </pri:authorized>	
	</d:displayControl>    
<%
   }
%>
	  
	<d:displayControl id="link_bill_view_bill_adjust" bean="oneline,customerDTO">
     <td width="20" ></td>
     <td><img src="img/button_l.gif" width="11" height="20"></td>
     <td background="img/button_bg.gif"  ><a href="javascript:bill_adjust()" class="btn12">账单调账</a></td>
     <td><img src="img/button_r.gif" width="22" height="20"></td>  
	</d:displayControl>
  </tr>
     
</table>		
</lgc:bloop>
 
  <input type="hidden" name="txtDoPost"  value="false" >
  <input type="hidden" name="bill_op" value ="check" >
  <tbl:hiddenparameters pass="returnFlag" />
</form> 
 </td></tr></table>
