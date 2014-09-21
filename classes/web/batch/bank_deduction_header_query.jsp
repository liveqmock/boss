<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="resultset" prefix="rs" %>
<%@ taglib uri="/WEB-INF/oss.tld" prefix="d" %>

<%@ page import="com.dtv.oss.util.Postern,
                 java.util.HashMap,
                 java.util.Map,java.math.BigDecimal,
                 java.util.Iterator" %>
<%@ page import="com.dtv.oss.dto.BankDeductionHeaderDTO" %>
<%@ page import="com.dtv.oss.web.util.WebOperationUtil" %>
<%@ page import="java.sql.Timestamp" %>
<%@ page import="com.dtv.oss.web.util.CommonKeys" %>

<script language=javascript>
function query_submit()
{
	if (document.frmPost.txtCreateTime1.value != ''){
		if (!check_TenDate(document.frmPost.txtCreateTime1, true, "结束日期")){
			return false;
		}
	}
	if (document.frmPost.txtCreateTime2.value != ''){
		if (!check_TenDate(document.frmPost.txtCreateTime2, true, "结束日期")){
			return false;
		}
	}
	if(!compareDate(document.frmPost.txtCreateTime1,document.frmPost.txtCreateTime2,"结束日期必须大于等于开始日期")){
		return false;
	}
	document.frmPost.submit();
}
function view_detail_click(batchNo,mopId)
{
	self.location.href="bank_deduction_header_view.do?txtActionType=header&txtBatchNo="+batchNo+"&txtMopID="+mopId;
}
</script>	
<%
 	pageContext.setAttribute("BILLINGCYCLE",Postern.getAllAccountCycle());
	pageContext.setAttribute("AllMOPList" ,Postern.getMethodBankDeductionOfPaymentMap());
%>
<table  border="0" align="center" cellpadding="0" cellspacing="10" >
  <tr>
    <td><img src="img/list_tit.gif" width="19" height="19"></td>
    <td class="title">银行托收记录查询</td>
  </tr>
</table>
<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>
<br>

<form name="frmPost" action="bank_deduction_header_query.do" method="post" >
<table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
  	<tr>
    	<td class="list_bg2"><div align="right">操作批号</div></td>
    	<td class="list_bg1">
    	<input name="txtBatchNo" type="text" class="text" maxlength="9" size="25" value="<tbl:writeparam name="txtBatchNo" />"></td>
    	<td class="list_bg2"><div align="right">处理状态</div></td>
    	<td class="list_bg1">
    	<d:selcmn name="txtProcessStatus" mapName="SET_I_BANKDEDUCTIONHEADERSTATUS" match="txtProcessStatus" width="23" />
    	</td>
  	</tr>
  	<tr>
  		<td  class="list_bg2"><div align="right">支付方式</div></td>
		<td class="list_bg1">
		<tbl:select name="txtMopID" set="AllMOPList" match="txtMopID" width="23" />
    	<td  class="list_bg2"><div align="right">创建时间</div></td>
		<td class="list_bg1">
            <input maxlength="10" onfocus="viewModule(this)" onKeydown="inputDate(document.frmPost.txtCreateTime1)" onblur="lostFocus(this)" type="text" name="txtCreateTime1" size="10" value="<tbl:writeparam name="txtCreateTime1"/>" >&nbsp;<IMG onclick="MyCalendar.SetDate(this,document.frmPost.txtCreateTime1,'Y')" src="img/calendar.gif" style=cursor:hand border="0">
            - 
            <input maxlength="10" onfocus="viewModule(this)" onKeydown="inputDate(document.frmPost.txtCreateTime2)" onblur="lostFocus(this)" type="text" name="txtCreateTime2" size="10" value="<tbl:writeparam name="txtCreateTime2"/>" >&nbsp;<IMG onclick="MyCalendar.SetDate(this,document.frmPost.txtCreateTime2,'Y')" src="img/calendar.gif" style=cursor:hand border="0">
        </td>
  	</tr>
	<tr>
		<td class="list_bg2"><div align="right">帐务周期</div></td>
	  	<td class="list_bg1" colspan="3">
	  		<tbl:select name="txtBillingCycleID" set="BILLINGCYCLE"  match="txtBillingCycleID" width="23" />
		</td>
	</tr>
</table>

<table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
	<tr align="center">
	  <td class="list_bg1"><table  border="0" cellspacing="0" cellpadding="0">
		  <pri:authorized name="bank_deduction_header_query.do">
		  <tr>
			<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
			<td><input name="Submit" type="button" class="button" value="查 询" onclick="javascript:query_submit()"></td>
			<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
		  </tr>
		  </pri:authorized>
	  </table></td>
	</tr>
</table>
	<input type="hidden" name="txtFrom" size="22" value="1">
	<input type="hidden" name="txtTo" size="22" value="10">
	<input type="hidden" name="txtActionType" value="header">

<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>
<br>

<rs:hasresultset>
<table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
  <tr class="list_head">
    <td class="list_head">批号</td>
    <td class="list_head">托收盘号</td>
    <td class="list_head">支付方式</td>
    <td class="list_head">帐务周期</td>
    <td class="list_head">创建日期</td>
    <td class="list_head">处理状态</td>
    <td class="list_head">总笔数</td>
    <td class="list_head">总金额</td>
    <td class="list_head">成功笔数</td>
    <td class="list_head">返盘日期</td>
  </tr>
	<lgc:bloop requestObjectName="ResponseQueryResult" item="oneline" >
<%
					BankDeductionHeaderDTO dto = (BankDeductionHeaderDTO) pageContext.getAttribute("oneline");
%>
	<tbl:printcsstr style1="list_bg1" style2="list_bg2" overStyle="list_over" > 
    <td align="center"><a href="javascript:view_detail_click('<tbl:write name="oneline" property="batchNo"/>','<tbl:write name="oneline" property="mopId"/>')" class="link12" ><tbl:write name="oneline" property="batchNo"/></a></td>
    <td align="center"><tbl:write name="oneline" property="exchangeId"/></td>
    <td align="center"><d:getcmnname typeName="AllMOPList" match="oneline:mopId"/></td>
    <td align="center"><d:getcmnname typeName="BILLINGCYCLE" match="oneline:billingCycle"/></td>
    <td align="center" width="10%"><tbl:writedate name="oneline" property="dtCreate" includeTime="true" /></td>
    <td align="center"><d:getcmnname typeName="SET_I_BANKDEDUCTIONHEADERSTATUS" match="oneline:processStatus"/></td>
    <td align="center"><tbl:write name="oneline" property="totalRecordNo"/></td>
    <td align="right"><tbl:write name="oneline" property="totalAmount"/></td>
    <td align="center"><tbl:write name="oneline" property="sucessRecordNo"/></td>
    <td align="center" width="10%"><tbl:writedate name="oneline" property="bankDealDate" includeTime="true"/></td>
    
  </tr>
  
	</tbl:printcsstr>
	</lgc:bloop>
	<tbl:generatetoken />

  <tr>
    <td colspan="10" class="list_foot"></td>
  </tr>
  </table>
  <table  border="0" align="right" cellpadding="0" cellspacing="8">
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
    <td><input name="imageField" type="image" src="img/button_go.gif" width="28" height="15" border="0" onclick="return goto_onClick(document.frmPost, <rs:prop property="pageamount" />)"/></td>
  </tr>
</table>
            
   

	</rs:hasresultset>
	</form>               
</td>
</tr>
</table>