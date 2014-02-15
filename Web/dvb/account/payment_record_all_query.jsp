<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="resultset" prefix="rs" %>
<%@ taglib uri="/WEB-INF/oss.tld" prefix="d" %>
<%@ taglib uri="privilege" prefix="pri" %>
                 
<%@ page import="com.dtv.oss.util.Postern, java.util.*,java.math.BigDecimal" %>
<%@ page import="com.dtv.oss.web.util.WebUtil" %>
<%@ page import="com.dtv.oss.web.util.CommonKeys" %>
<%@ page import="com.dtv.oss.web.util.WebOperationUtil" %>
<%@ page import="com.dtv.oss.service.commandresponse.QueryCommandResponseImpl"%>

<%@ page import="com.dtv.oss.dto.PaymentRecordDTO"%>
<%@ page import="com.dtv.oss.dto.MethodOfPaymentDTO"%>

<%
	pageContext.setAttribute("AllMOPList" , Postern.getOpeningPaymentNoWrapMop()); 
%>
<script language=javascript>
function query_submit(){
	if(checkData())
		document.frmPost.submit();
}

function payment_record_all_detail(seqNO){
	var strURL="payment_record_all_detail.do?txtSeqNO="+seqNO+"&txtActionType=detail";
	self.location.href=strURL;
}

function checkData(){
	if (document.frmPost.txtCreateStartDateDatePart.value != ''){
		if (!check_TenDate(document.frmPost.txtCreateStartDateDatePart, true, "开始日期")){
			return false;
		}
	}
	 
	if (document.frmPost.txtCreateEndDateDatePart.value != ''){
		if (!check_TenDate(document.frmPost.txtCreateEndDateDatePart, true, "结束日期")){
			return false;
		}
	}
	 
	if(!compareDate(document.frmPost.txtCreateStartDateDatePart,document.frmPost.txtCreateEndDateDatePart,"结束日期必须大于等于开始日期")){
		
		return false;
	}
     if(!checkPlainNum(document.frmPost.txtCustID,true,9,"客户证号"))
		return false;
	if(!checkPlainNum(document.frmPost.txtAcctID,true,9,"帐户号"))
		return false;
	return true;
}
function clear_select(id,name){
	var idobj=eval("document.frmPost."+id);
	idobj.value="";
	var nameobj =eval("document.frmPost."+name);
	nameobj.value="";
}
</script>

<form name="frmPost" method="post" action="payment_record_all_query.do">

<input type="hidden" name="txtFrom" size="20" value="1">
<input type="hidden" name="txtTo" size="20" value="10">

<input type="hidden" name="txtActionType" size="20" value="all">

<table  border="0" align="center" cellpadding="0" cellspacing="10" >
  <tr>
    <td><img src="img/list_tit.gif" width="19" height="19"></td>
    <td class="title">支付记录查询</td>
  </tr>
</table>

<table width="804"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>
<br>

<table width="804"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
  <tr>
    <td class="list_bg2" width="17%" align="right">客户证号</td>
    <td class="list_bg1" width="33%" align="left">
    	 <input name="txtCustID" type="text" class="text" maxlength="9" size="25" value="<tbl:writeparam name="txtCustID" />">
    </td>
    <td class="list_bg2" width="17%" align="right">帐户号</td>
    <td class="list_bg1" width="33%" align="left">
    	<input name="txtAcctID" type="text" class="text" maxlength="9" size="25" value="<tbl:writeparam name="txtAcctID" />">
    </td>
  </tr>
  <tr>
    <td class="list_bg2" align="right">客户所属区域</td>
    <td class="list_bg1" align="left">
    	<input type="hidden" name="txtDistrictID" value="<tbl:writeparam name="txtDistrictID" />">
	    <input type="text" name="txtCountyDesc" size="25" maxlength="50" readonly value="<tbl:WriteDistrictInfo property="txtDistrictID" />" class="text">
	    <input name="selDistButton" type="button" class="button" value="选择" onClick="javascript:sel_district('all','txtDistrictID','txtCountyDesc')">
    </td>
        <td class="list_bg2" align="right">客户地址</td>
    <td class="list_bg1" align="left">
    	<input name="txtAddress" type="text" class="text" maxlength="9" size="25" value="<tbl:writeparam name="txtAddress" />">
    </td>
  </tr>
  <tr>
    <td class="list_bg2" align="right">收费机构</td>
    <td class="list_bg1" align="left">
	     <input type="hidden" name="txtOrgID" value="<tbl:writeparam name="txtOrgID" />">
		   <input type="text" name="txtOrgDesc" size="25" maxlength="50" readonly value="<tbl:WriteOrganizationInfo property="txtOrgID" />" class="text">
			 <input name="selOrgButton" type="button" class="button" value="选择" onClick="javascript:sel_organization('C,B,O,S','txtOrgID','txtOrgDesc')">
	  </td>	
    <td class="list_bg2" align="right">收费人</td>
    <td class="list_bg1" align="left">
		<input type="hidden" name="txtCollectorID" size="25" value="<tbl:writeparam name="txtCollectorID"/>" >
		<input type="text" name="txtCollectorName" readonly size="24" value="<tbl:writeparam name="txtCollectorName"/>" >
		<input name="selOperButton" type="button" class="button" value="查询" 
		onClick="javascript:query_window('收费人查询','txtCollectorID','txtCollectorName','query_operator.do')">
		<input name="selClearButton" type="button" class="button" value="清除" 
		onClick="javascript:clear_select('txtCollectorID','txtCollectorName')">
    </td>
  </tr>
  <tr>
    <td class="list_bg2" align="right">支付方式</td>
    <td class="list_bg1" align="left">
    	<tbl:select name="txtMOPID" set="AllMOPList"  match="txtMOPID"  width="23" />
    </td>
    <td class="list_bg2" align="right">支付类别</td>
    <td class="list_bg1" align="left">
    	<d:selcmn name="txtPayType" mapName="SET_F_PAYMENTRECORDTYPE"  match="txtPayType"  width="23" />
    </td>
  </tr>
  <tr>
    <td class="list_bg2" align="right">创建来源</td>
    <td class="list_bg1" align="left">
    	<d:selcmn name="txtCreatingMethod" mapName="SET_F_FTCREATINGMETHOD"  match="txtCreatingMethod"  width="23" />
    </td>
    <td class="list_bg2" align="right">预存款类型</td>
    <td class="list_bg1" align="left">
    	<d:selcmn name="txtPrePaymentType" mapName="SET_F_PREPAYMENTTYPE"  match="txtPrePaymentType"  width="23" />
    </td>
  </tr>
  <tr>
    <td class="list_bg2" align="right">状态</td>
    <td class="list_bg1" align="left" colspan="3">
    	<d:selcmn name="txtStatus" mapName="SET_F_FTSTATUS"  match="txtStatus"  width="23" />
    </td>
  </tr>
  <tr>
    <td class="list_bg2" align="right">创建时间</td>
    <td class="list_bg1" colspan="2"> 
            <d:datetime name="txtCreateStartDate" size="10" match="txtCreateStartDate" includeHour="true" onclick="MyCalendar.SetDate(this,document.frmPost.txtCreateStartDateDatePart,'Y')" picURL="img/calendar.gif" style="cursor:hand" />            
             - 
             <d:datetime name="txtCreateEndDate" size="10" match="txtCreateEndDate" includeHour="true" onclick="MyCalendar.SetDate(this,document.frmPost.txtCreateEndDateDatePart,'Y')" picURL="img/calendar.gif" style="cursor:hand" />           
   </td> 
   <td class="list_bg1" align="right" colspan="4"><A href="javascript:drawSubMenu('1')"><IMG id="arr1" alt="展开下级查询条件" src="img/icon_bottom.gif" border=0></A></td>
  </tr>
</table>
<table width="804"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg" id="mnu1" style="display:none">  
  <tr>
    <td class="list_bg2" align="right" width="17%">代用券类型</td>
    <td class="list_bg1" align="left" width="33%" >
    	<d:selcmn name="txtTicketType" mapName="SET_F_PAYMENTTICKETTYPE"  match="txtTicketType"  width="23" />
    </td>
    <td class="list_bg2" align="right" width="17%">券号</td>
    <td class="list_bg1" align="left" width="33%" >
    	<input name="txtTicketNo" type="text" class="text" maxlength="10" size="25" value="<tbl:writeparam name="txtTicketNo" />">
    </td>
  </tr>
 
  <tr>
    <td class="list_bg2" align="right">关联单据类型</td>
    <td class="list_bg1" align="left">
    	<d:selcmn name="txtReferType" mapName="SET_F_FTREFERTYPE"  match="txtReferType"  width="23" />
    </td>
    <td class="list_bg2" align="right">关联单据号</td>
    <td class="list_bg1" align="left">
    	<input name="txtReferID" type="text" class="text" maxlength="10" size="25" value="<tbl:writeparam name="txtReferID" />">
    </td>
  </tr>
  <tr>
    <td class="list_bg2" align="right">源记录类型</td>
    <td class="list_bg1" align="left">
    	<d:selcmn name="txtSourceType" mapName="SET_F_PAYMENTRECORDSOURCETYPE"  match="txtSourceType"  width="23" />
    </td>
    <td class="list_bg2" align="right">源记录号</td>
    <td class="list_bg1" align="left">
    	<input name="txtSourceRecordID" type="text" class="text" maxlength="10" size="25" value="<tbl:writeparam name="txtSourceRecordID" />">
    </td>
  </tr>
  <tr>
    <td class="list_bg2" align="right">帐单标志</td>
    <td class="list_bg1" align="left">
    	<d:selcmn name="txtInvoicedFlag" mapName="SET_G_YESNOFLAG"  match="txtInvoicedFlag"  width="23" />
    </td>
    <td class="list_bg2" align="right">帐单号</td>
    <td class="list_bg1" align="left">
      <input name="txtInvoiceNo" type="text" class="text" maxlength="10" size="25" value="<tbl:writeparam name="txtInvoiceNo" />">
    </td>
  </tr>  
</table>
<table width="804"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
	<tr align="center">
	  <td class="list_bg1"><table  border="0" cellspacing="0" cellpadding="0">
		  <tr>
			<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
			<td><input name="Submit" type="button" class="button" value="查&nbsp;询" onclick="javascript:query_submit()"></td>
			<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
		<pri:authorized name="payment_record_all_query.export">
			<td width="20" ></td>
			<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
			<td background="img/button_bg.gif"  height="20" >
			<a href="javascript:download(document.frmPost,'支付记录查询')" class="btn12">导出查询结果</a></td>
			<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
		</pri:authorized>
		  </tr>
	  </table></td>
	</tr>
</table> 

<table width="804"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>
<br>

<rs:hasresultset>

<table width="804"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
  <tr class="list_head">
    <td class="list_head" width="6%" nowrap>流水号</td>
    <td class="list_head" width="10%" nowrap>创建时间</td>
    <td class="list_head" width="7%" nowrap>收费人</td>
    <td class="list_head" width="15%" nowrap>收费机构</td>
    <td class="list_head" width="7%"  nowrap>客户证号</td>
    <td class="list_head" width="8%" nowrap>支付方式</td>
    <td class="list_head" width="8%"  nowrap>类别</td>
    <td class="list_head"  nowrap>金额</td>
    <td class="list_head" width="7%"  nowrap>状态</td>
    <td class="list_head" width="7%"  nowrap>单据类型</td>
    <td class="list_head" width="7%"  nowrap>单据号</td>
    <td class="list_head" width="7%"  nowrap>来源类型</td>
  </tr>
  
<% QueryCommandResponseImpl RepCmd = (QueryCommandResponseImpl)request.getAttribute("ResponseQueryResult");
   BigDecimal fl=(BigDecimal)RepCmd.getExtraPayLoad();
   double totalAmount=0;
   if(fl!=null)
   	totalAmount=fl.doubleValue();
%>
   
<lgc:bloop requestObjectName="ResponseQueryResult" item="oneline" >
<%
    PaymentRecordDTO dto = (PaymentRecordDTO)pageContext.getAttribute("oneline");
    System.out.println("dto.getAmount()======="+dto.getAmount());
    
    pageContext.setAttribute("DTO",dto);
        
    String strOpName=Postern.getOperatorNameByID(dto.getOpID());
    if(strOpName==null)
    	strOpName="";
    	
    String strCustName=Postern.getCustomerNameByID(dto.getCustID());
    if(strCustName==null)
    	strCustName="";
    
    //付费方式
    Map mapBankMop=Postern.getAllMethodOfPayments();
    MethodOfPaymentDTO dtoMOP = (MethodOfPaymentDTO)mapBankMop.get("" + dto.getMopID());
    String strMopName = null;
    if (dtoMOP!=null) strMopName=dtoMOP.getName();
    if (strMopName==null) strMopName="";
    
    String strOrgName=Postern.getOrgNameByCustomerID(dto.getCustID());
       if(strOrgName==null)
       	   strOrgName="";
    
%>
 <tbl:printcsstr style1="list_bg1" style2="list_bg2" overStyle="list_over" >
    <td align="center"><a href="javascript:payment_record_all_detail('<tbl:write name="DTO" property="seqNo"/>')" class="link12" ><tbl:write name="DTO" property="seqNo"/></a></td>
    <td align="center"><tbl:writedate name="DTO" property="createTime"/><br><tbl:writedate name="DTO" property="createTime" onlyTime="true"/></td>
    <td align="center"><%=strOpName %></td>
    <td align="center"><tbl:WriteOrganizationInfo name="DTO" property="orgID" /></td>
    <td align="center"><tbl:write name="DTO" property="custID" /></td>
    <td align="center"><%=strMopName%></td>
    <td align="center"><d:getcmnname typeName="SET_F_PAYMENTRECORDTYPE" match="DTO:payType" /></td>
    <td align="center"><tbl:write name="DTO" property="amount"/></td>
    <td align="center"><d:getcmnname typeName="SET_F_FTSTATUS" match="DTO:status" /></td>
    <td align="center"><d:getcmnname typeName="SET_F_FTREFERTYPE" match="DTO:referType" /></td>
    <td align="center"><tbl:write name="DTO" property="referID"/></td>
    <td align="center"><d:getcmnname typeName="SET_F_PAYMENTRECORDSOURCETYPE" match="DTO:sourceType" /></td> 
 </tbl:printcsstr>
</lgc:bloop>
  <tr>
    <tbl:printcsstr style1="list_bg1" style2="list_bg2" overStyle="list_over" >
    	<td colspan="12">总计：<%=WebUtil.bigDecimalFormat(totalAmount) %></td>
    </tbl:printcsstr>
  </tr>
  
  <tr>
    <td colspan="12" class="list_foot"></td>
  </tr>

</table>
<table  border="0" align="right" cellpadding="0" cellspacing="13">
  <tr>
    <td>第<span class="en_8pt"><rs:prop property="curpageno" /></span>页<span class="en_8pt">/</span>共<span class="en_8pt"><rs:prop property="pageamount" /><span>页</td>
    <td>共<span class="en_8pt"><rs:prop property="recordcount" /></span>条记录</td>
<rs:notfirst>
    <td align="right"><img src="img/dot_top.gif" width="17" height="11"></td>
    <td><a href="javascript:firstPage_onClick(document.frmPost, <rs:prop property="firstto" />)" class="link12">首页</a></td>
    <td align="right"><img src="img/dot_pre.gif" width="6" height="11"></td>
    <td><a href="javascript:previous_onClick(document.frmPost, <rs:prop property="prefrom" />, <rs:prop property="preto" />)" class="link12">上一页</a></td>
</rs:notfirst>
<rs:notlast>
    <td align="right"><img src="img/dot_next.gif" width="6" height="11"></td>
    <td><a href="javascript:next_onClick(document.frmPost, <rs:prop property="nextfrom" />, <rs:prop property="nextto" />)" class="link12">下一页</a></td>
    <td align="right"><img src="img/dot_end.gif" width="17" height="11"></td>
    <td><a href="javascript:lastPage_onClick(document.frmPost, <rs:prop property="lastfrom" />, <rs:prop property="lastto" />)" class="link12">末页</a></td>
</rs:notlast>
    <td align="right">跳转到</td>
    <td><input name="txtPage" type="text" class="page_txt"></td>
    <td>页</td>
    <td><input name="imageField" type="image" src="img/button_go.gif" width="28" height="15" border="0" onclick="javascript:goto_onClick(document.frmPost, <rs:prop property="pageamount" />)"></td>
  </tr>
  </table>
  
 </rs:hasresultset>                 
<BR>

</form>  
         

      
