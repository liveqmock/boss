<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="privilege" prefix="pri" %>
<%@ taglib uri="osstags" prefix="d" %>
<%@ taglib uri="bookmark" prefix="bk" %>
<%@ page import="com.dtv.oss.web.util.CommonKeys" %>
<%@ page import="com.dtv.oss.util.Postern" %>
<%@ page import="com.dtv.oss.web.util.WebOperationUtil" %>

<SCRIPT language="JAVASCRIPT">
	function next_submit(){
		if(check_from()){
			var strUrl='account_adjust_fee.do';
			document.frmPost.action=strUrl;
			document.frmPost.submit();
		}
	}
	function back_submit(){
		document.frmPost.action='account_adjust.do';
		document.frmPost.submit();
	}
	function check_from(){
		if(check_Blank(document.frmPost.txtAmount, true, "金额")||!check_Float(document.frmPost.txtAmount,true,"金额")){
			return false;
		}
		if(check_Blank(document.frmPost.txtAdjustReason,true,"调帐原因")){
			return false;
		}
		if(check_Blank(document.frmPost.MOP,true,"支付方式")){
			return false;
		}
		if(check_Blank(document.frmPost.txtAdjustDate, true, "调帐日期")||!check_TenDate(document.frmPost.txtAdjustDate,true,"调帐日期")){
			return false;
		}
		if(document.frmPost.txtCollectorID.value==null||document.frmPost.txtCollectorID.value==''){
			alert("收费人信息必填!");
			return false;
		}
		if(check_Blank(document.frmPost.txtCollectorDate, true, "收费日期")||!check_TenDate(document.frmPost.txtCollectorDate,true,"收费日期")){
			return false;
		}

		return true;
	}	
</SCRIPT>
<%
	pageContext.setAttribute("acctItemTypeMap",Postern.getAcctItemTypeWithAdjust());
	pageContext.setAttribute("AllMOPList" ,Postern.getOpeningPaymentMop(CommonKeys.METHODOFPAYMENT_ZHTZ));
%>
<table width="820" align="center" cellpadding="0" cellspacing="0">
	<tr> 
		<td align="center" valign="top">
		<table  border="0" align="center" cellpadding="0" cellspacing="10">
      <tr>
        <td><img src="img/list_tit.gif" width="19" height="19"></td>
        <td class="title">帐户调帐--预存抵扣</td>
      </tr>
		</table>
		<table  border="0" align="center" cellpadding="0" cellspacing="10">
      <tr>
        <td><font size="2" color="red">+代表增加分类预存 -代表减少分类预存</font></td>
      </tr>
		</table>
		<table width="100%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
      <tr>
         <td><img src="img/mao.gif" width="1" height="1"></td>
      </tr>
		</table>		
		<form name="frmPost" method="post" action="account_adjust.do">
		<tbl:hiddenparameters pass="txtAdjustType/txtCustomerID" />

		<table align="center" width="100%" border="0" cellspacing="1" cellpadding="5" class="fulltable" >      
	   	<tr>
				<td class="list_bg2" align="right" width='17%'>
					帐户号
				</td>
				<td class="list_bg1" width='33%'>
					<input type="text" name="txtAccountID" size="25"  value="<tbl:writeparam name="txtAccountID" />" class="textgray" readonly >
				</td>
				<td class="list_bg2" align="right" width='17%'>
					帐户名称
				</td>
				<td class="list_bg1" width='33%'>
					<input type="text" name="txtAccountName" size="25" maxlength="25" value="<tbl:writeparam name="txtAccountName" />" class="textgray" readonly>
				</td>
			</tr>
	   	<tr>
				<td class="list_bg2"  align="right">
					金额*
				</td>
				<td class="list_bg1">
					<input type='text' name='txtAmount' size='25' maxlength=='25' value='<tbl:writeparam name="txtAmount" />'>
				</td>
        <td class="list_bg2" align="right">
        	调账原因*
        </td>
        <td class="list_bg1" align="left">
          <d:selcmn name="txtAdjustReason" mapName="SET_F_ACCOUNTADJUSTMENTREASON" match="txtAdjustReason" width="22" />
        </td>
			</tr>
	   	<tr>
				<td class="list_bg2"  align="right">
					支付方式*
				</td>
				<td class="list_bg1">
					<tbl:select name="MOP" set="AllMOPList" match="MOP" width="25" />
				</td>
        <td class="list_bg2" align="right">
        	调账日期*
        </td>
        <td class="list_bg1" align="left">
          <input maxlength="10" onfocus="viewModule(this)" onKeydown="inputDate(document.frmPost.txtAdjustDate)" onblur="lostFocus(this)" type="text" name="txtAdjustDate" size="25" value="<tbl:writeparam name="txtAdjustDate"/>" >&nbsp;<IMG onclick="MyCalendar.SetDate(this,document.frmPost.txtAdjustDate,'Y')" src="img/calendar.gif" style=cursor:hand border="0">
        </td>
			</tr>
	   	<tr>
				<td class="list_bg2"  align="right">
					收费人*
				</td>
				<td class="list_bg1">
					<input type="hidden" name="txtCollectorID" size="25" value="<tbl:writeparam name="txtCollectorID"/>" >
					<input type="text" name="txtCollectorName" readonly size="25" value="<tbl:writeparam name="txtCollectorName"/>" >
					<input name="selOperButton" type="button" class="button" value="查询" 
					onClick="javascript:query_window('收费人查询','txtCollectorID','txtCollectorName','query_operator.do?txtStatus=V')">
				</td>
        <td class="list_bg2" align="right">
        	收费日期*
        </td>
        <td class="list_bg1" align="left">
          <input maxlength="10" onfocus="viewModule(this)" onKeydown="inputDate(document.frmPost.txtCollectorDate)" onblur="lostFocus(this)" type="text" name="txtCollectorDate" size="25" value="<tbl:writeparam name="txtCollectorDate"/>" >&nbsp;<IMG onclick="MyCalendar.SetDate(this,document.frmPost.txtCollectorDate,'Y')" src="img/calendar.gif" style=cursor:hand border="0">
        </td>
			</tr>
      <tr>
        <td class="list_bg2" align="right">
        	调账备注
        </td>
        <td class="list_bg1" colspan="3" align="left">
          <input type="text" name="txtAdjustReMark" value="<tbl:writeparam name="txtadjustReMark" />" size="83" maxlength='100' class="text">
        </td>
			</tr>
		</table>
		<br>
		<table align="center" border="0" cellspacing="0" cellpadding="0">
    	<tr>      
        <td><img src="img/button2_r.gif" border="0" ></td>
        <td background="img/button_bg.gif"><a href="javascript:back_submit()" class="btn12">上一步</a></td>
        <td><img src="img/button2_l.gif" border="0" ></td>
        <td width="20" ></td>
        <td><img src="img/button_l.gif" border="0" ></td>
        <td background="img/button_bg.gif"><a href="javascript:next_submit()" class="btn12">确&nbsp&nbsp认</a></td>
        <td><img src="img/button_r.gif" border="0" ></td>
			</tr>
		</table>
		</form>
		</td>
	</tr>
</table>


 