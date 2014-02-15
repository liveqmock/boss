<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="resultset" prefix="rs" %>
<%@ taglib uri="/WEB-INF/oss.tld" prefix="d" %>
<%@ taglib uri="bookmark" prefix="bk" %>

<%@ page import="com.dtv.oss.util.Postern,
                 java.util.*" %>
<%@ page import="com.dtv.oss.web.util.WebUtil" %>
<%@ page import="com.dtv.oss.dto.AccountItemDTO,
                 com.dtv.oss.dto.AcctItemTypeDTO,
                 com.dtv.oss.dto.PaymentRecordDTO,
                 com.dtv.oss.dto.AccountDTO,
                 com.dtv.oss.dto.MethodOfPaymentDTO" %>
<%@ page import="com.dtv.oss.service.commandresponse.CommandResponse" %>

<script language=javascript>

function check_form(){
	if (document.frmPost.txtCreateStartDate.value != ''){
		if (!check_TenDate(document.frmPost.txtCreateStartDate, true, "开始日期")){
			return false;
		}
	}
	 
	if (document.frmPost.txtCreateEndDate.value != ''){
		if (!check_TenDate(document.frmPost.txtCreateEndDate, true, "结束日期")){
			return false;
		}
	}
	 
	if(!compareDate(document.frmPost.txtCreateStartDate,document.frmPost.txtCreateEndDate,"结束日期必须大于等于开始日期")){
		
		return false;
	}
	return true;
}

function checkSelected(){
	var returnValue=false;
	if(document.frmPost.txtAcctPayID==null){
		alert("没有需要打印的支付记录");
		return false;
	}else{
		if (document.frmPost.txtAcctPayID.length!=null){
			for (var i=0;i<document.frmPost.txtAcctPayID.length;i++){
				if (document.frmPost.txtAcctPayID[i].checked){
					if(document.frmPost.txtAcctPayIDList.value == ""){
						document.frmPost.txtAcctPayIDList.value = document.frmPost.txtAcctPayID[i].value;						
					}
					else{
					  document.frmPost.txtAcctPayIDList.value = document.frmPost.txtAcctPayIDList.value+","+document.frmPost.txtAcctPayID[i].value;	
				  }
				  returnValue= true;
				}
			}
		}else{
			if(document.frmPost.txtAcctPayID.checked){
				document.frmPost.txtAcctPayIDList.value=document.frmPost.txtAcctPayID.value;
				returnValue= true;
			}
		}
	}
	if(!returnValue){
	  	alert("请指定打印的支付记录");
	}
	
	if (document.frmPost.txtAcctFeeID !=null){
	    if (document.frmPost.txtAcctFeeID.length!=null){
			  for (var i=0;i<document.frmPost.txtAcctFeeID.length;i++){
				   if (document.frmPost.txtAcctFeeID[i].checked){
	            if(document.frmPost.txtAcctFeeIDList.value == "")
						     document.frmPost.txtAcctFeeIDList.value = document.frmPost.txtAcctFeeID[i].value;
					    else
					       document.frmPost.txtAcctFeeIDList.value = document.frmPost.txtAcctFeeIDList.value+","+document.frmPost.txtAcctFeeID[i].value;
           }
        }
      } else {
         if(document.frmPost.txtAcctFeeID.checked){
				    document.frmPost.txtAcctFeeIDList.value=document.frmPost.txtAcctFeeID.value;
			   }
	    }
	}
  return returnValue;
}

function query_submit(){
  if (check_form()) {
  	  document.frmPost.action ="account_feepay_query.do";
     	document.frmPost.submit();
  }
}

function print_submit(printflag){
    document.frmPost.txtAcctFeeIDList.value = "";
    document.frmPost.txtAcctPayIDList.value = "";
    if(checkSelected()){
		 	document.frmPost.target="_blank";
		 	if (printflag ==true){
		 		 document.frmPost.action="feepay_print_for_huairou.do";
		 	} else{
	  	   document.frmPost.action="account_feepay_print.do";
	  	}
	  	document.frmPost.submit();
		  document.frmPost.target="_self";
    }
}
</script>
<%
    String  txtCreateStartDate =request.getParameter("txtCreateStartDate");
    String  txtCreateEndDate =request.getParameter("txtCreateEndDate");
    int     acctId =WebUtil.StringToInt(request.getParameter("txtacctID"));
    AccountDTO acctDto =Postern.getAccountDto(acctId);
    int     acctDistId =Postern.getAddressDtoByID(acctDto.getAddressID()).getDistrictID();
    int hrorgid =(Postern.getSystemSettingValue("SET_HRORGID_FOR_PRINT")==null) ? -1 :Integer.parseInt(Postern.getSystemSettingValue("SET_HRORGID_FOR_PRINT"));
    boolean ishuairou =Postern.getDistrictSettingByOrgID(hrorgid).containsKey(String.valueOf(acctDistId));
%>

<TABLE border="0" cellspacing="0" cellpadding="0" width="820" >
<TR>
	<TD>

<table  border="0" align="center" cellpadding="0" cellspacing="10" >
  <tr>
    <td><img src="img/list_tit.gif" width="19" height="19"></td>
    <td class="title">费用支付清单</td>
  </tr>
</table>
<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>
<br>
<form name="frmPost" action="" method="post">    
	<input type="hidden" name="txtacctID" value="<%=request.getParameter("txtacctID")%>" >
	<input type="hidden" name="txtAcctPayIDList" value="">
	<input type="hidden" name="txtAcctFeeIDList" value="">
<table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
  <tr>
		<td  class="list_bg2" align="right" width="40%">创建时间&nbsp;&nbsp;&nbsp;&nbsp;</td> 
		<td class="list_bg1"  align="left" width="60%">  
			<input type="text" size="10" maxlength="10" name="txtCreateStartDate" value="<tbl:writeparam name="txtCreateStartDate"/>" class="text"><IMG onclick="calendar(document.frmPost.txtCreateStartDate)" src="img/calendar.gif" style=cursor:hand border="0">           
             - 
			<input type="text" size="10" maxlength="10" name="txtCreateEndDate" value="<tbl:writeparam name="txtCreateEndDate" />" class="text"><IMG onclick="calendar(document.frmPost.txtCreateEndDate)" src="img/calendar.gif" style=cursor:hand border="0">
		</td>       
	</tr> 
</table>
<table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
	<tr align="center">
	  <td class="list_bg1"><table  border="0" cellspacing="0" cellpadding="0">
		  <tr>
	 <bk:canback url="account_view.do" >
      <td width="20" ></td>  
      <td><img src="img/button2_r.gif" width="22" height="20"></td>      
      <td background="img/button_bg.gif"  height="20" >
        <a href="<bk:backurl property="account_view.do" />" class="btn12">返&nbsp;回</a></td>
      <td><img src="img/button2_l.gif" width="13" height="20"></td>
   </bk:canback> 
      <td width="20%">
			<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
			<td><input name="Submit" type="button" class="button" value="查 询" onclick="javascript:query_submit()"></td>
			<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
			<td width="20%">
			<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
			<td><input name="Submit" type="button" class="button" value="确认打印" onclick="javascript:print_submit(<%=ishuairou%>)"></td>
			<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
		  </tr>
	  </table></td>
	</tr>
</table>      
<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>
<br>
<table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
	 <tr class="list_head">
	    <td class="import_tit" align="center" colspan="7">费用列表</td>
	 </tr>
  <tr class="list_head">
    <td class="list_head"><input type="checkbox" name="selall" onclick="javascript:select_all_by_name(document.frmPost,'txtAcctFeeID', this.checked)"></td>
    <td class="list_head" >费用流水号</td>
    <td class="list_head" >创建日期</td>
    <td class="list_head" >账目类型</td>
    <td class="list_head" >金额</td>
    <td class="list_head" >计费周期</td>
    <td class="list_head" >创建来源</td>
  </tr>
  <% 
     Collection acctFeeCols =Postern.getAcctFeeForPrint(null,acctId,txtCreateStartDate,txtCreateEndDate,1,200);
     request.setAttribute("ResponseFromEJBEvent",new CommandResponse(acctFeeCols));
  %>
  
  
<lgc:bloop requestObjectName="ResponseFromEJBEvent" item="oneline" >
	<%
	    AccountItemDTO accountItemDto =(AccountItemDTO)pageContext.getAttribute("oneline");  
      AcctItemTypeDTO  acctItemTypeDto=Postern.getAcctItemTypeDTOByAcctItemTypeID(accountItemDto.getAcctItemTypeID());
      String acctItemTypeName =acctItemTypeDto.getAcctItemTypeName();
      String strBillingCycleName = Postern.getBillingCycleNameByID(accountItemDto.getBillingCycleID());
      if(strBillingCycleName==null) strBillingCycleName="";
	%>
 <tr class="list_bg1" onmouseover="this.className='list_over'" onmouseout="this.className='list_bg1'">
    <td align="center"><input type="checkbox" name="txtAcctFeeID" value="<tbl:write name="oneline" property="aiNO"/>"></td>
    <td align="center"><tbl:writenumber name="oneline" property="aiNO" digit="10" hideZero="true"/>&nbsp;
    </td>
    <td align="center"><tbl:writedate name="oneline" property="createTime" /><br><tbl:writedate name="oneline" property="createTime" onlyTime="true"/></td>
    <td align="center" ><%=acctItemTypeName%></td>
    <td align="center" nowrap><tbl:write name="oneline" property="Value" /></td>  
    <td align="center" nowrap><%=strBillingCycleName%></td>
    <td align="center" ><d:getcmnname typeName="SET_F_FTCREATINGMETHOD" match="oneline:CreatingMethod" /></td>
  </tr>
  
</lgc:bloop>
</table>
<br>
<table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
	 <tr class="list_head">
	   	<td class="import_tit" align="center" colspan="7"><font size="3">支付列表</strong></td>
	 </tr>
  <tr class="list_head">
    <td class="list_head"><input type="checkbox" name="selall" onclick="javascript:select_all_by_name(document.frmPost,'txtAcctPayID', this.checked)"></td>
    <td class="list_head" width="80" nowrap>流水号</td>
    <td class="list_head" >创建日期</td>
    <td class="list_head" >支付方式</td>
    <td class="list_head" >金额</td>
    <td class="list_head" >收费人</td>
    <td class="list_head" >收费机构</td>
  </tr>
  <%
     Collection acctPayCols =Postern.getAcctPaymentRecordForPrint(null,acctId,txtCreateStartDate,txtCreateEndDate,1,200);
     request.setAttribute("ResponseFromEJBEvent",new CommandResponse(acctPayCols));
     double checkSumPay =0;
  %>
  <lgc:bloop requestObjectName="ResponseFromEJBEvent" item="oneline" >
  <%
       PaymentRecordDTO paymentRecordDto =(PaymentRecordDTO)pageContext.getAttribute("oneline");  
       Map  payMap =Postern.getAllMethodOfPayments();
   
       PaymentRecordDTO dto = (PaymentRecordDTO) pageContext.findAttribute("oneline");
       MethodOfPaymentDTO paymentDto =(MethodOfPaymentDTO)payMap.get(String.valueOf(dto.getMopID()));
       String payMopName =paymentDto!=null?paymentDto.getName():"";
       String operatorName =Postern.getOperatorNameByID(dto.getOpID());
       String orgName = Postern.getOrgNameByID(dto.getOrgID());
       if (orgName == null) orgName = "";
       checkSumPay = checkSumPay +dto.getAmount();
  
  %>
  	<tr class="list_bg1" onmouseover="this.className='list_over'" onmouseout="this.className='list_bg1'">
    <td align="center"><input type="checkbox" name="txtAcctPayID" value="<tbl:write name="oneline" property="seqNo"/>" ></td>
    <td align="center"><tbl:writenumber name="oneline" property="seqNo" digit="10" /></td>
    <td align="center" ><tbl:writedate name="oneline" property="createTime"/><br><tbl:writedate name="oneline" property="createTime" onlyTime="true"/></td>
    <td align="center"><%=payMopName%></td>
    <td align="center"><tbl:write name="oneline" property="Amount"/>
    	 <input type="hidden" name ="txtPayAmount" value="<tbl:write name="oneline" property="Amount"/>" >  
    </td>
    <td align="center"><%=operatorName%></td>
    <td align="center"><%=orgName%></td>
  </tr>
  </lgc:bloop>
   <tr >
    <td colspan="6" class="list_bg1" align="right">支付合计</td>
    <td class="list_bg2" align="center"><input name="txtSumCheckPay" type="text" class="textgray" value="<%=checkSumPay%>" readonly ></td>
  </tr>
</table>
    
</form>               
</TD>
</TR>
</TABLE>