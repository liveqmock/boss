<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl"%>
<%@ taglib uri="osstags" prefix="d"%>
<%@ taglib uri="bookmark" prefix="bk"%>
<%@ taglib uri="logic" prefix="lgc"%>
<%@ taglib uri="operator" prefix="op"%>
<%@ page import="com.dtv.oss.web.util.CommonKeys"%>
<%@ page import="com.dtv.oss.util.Postern"%>

<form name="frmPost" method="post">
<table border="0" align="center" cellpadding="0" cellspacing="10">
	<tr>
		<td><img src="img/list_tit.gif" width="19" height="19"></td>
		<td class="title">修改电话号码</td>
	</tr>
</table>
<br>
<table width="100%" border="0" align="center" cellpadding="0"
	cellspacing="0" background="img/line_01.gif">
	<tr>
		<td><img src="img/mao.gif" width="1" height="1"></td>
	</tr>
</table>
<br>
<table width="100%" border="0" align="center" cellpadding="5"
	cellspacing="1" class="list_bg">
	<tr>
		<td class="list_bg2" width="17%">
		<div align="right">旧电话号码</div>
		</td>
		<td class="list_bg1">
			<input type="text" name="oldPhoneNo" size="25" readonly
			maxlength="50" value="<tbl:writeparam name="txtServiceCode" />"
			class="textgray"></td>
	</tr>
	<tr>
		<td class="list_bg2">
		<div align="right">号码级别</div>
		</td>
		<td class="list_bg1">
			<d:selcmn name="txtGrade" mapName="SET_R_PHONENOGRADE" match="txtGrade" width="23" />
		</td>
	</tr>
	<tr>
		<td class="list_bg2">
		<div align="right">新电话号码</div>
		</td>
		<td class="list_bg1">
			<input type="text" name="phoneNo" size="25" 
			maxlength="50" value=""	class="text"> <input name="Submit" type="button" class="button"
			value="查询" onclick="javascript:phoneNo_Search()">
		支持模糊查询，“_”代表一位，“%”代表多位。</td>
	</tr>
</table>

<br>
<table width="100%" border="0" align="center" cellpadding="0"
	cellspacing="0" background="img/line_01.gif">
	<tr>
		<td><img src="img/mao.gif" width="1" height="1"></td>
	</tr>
</table>
<br>
<table align="center" border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td width="20"></td>
		<td width="11" height="20"><img src="img/button2_r.gif" width="22"
			height="20"></td>
		<td height="20"><input name="prev" type="button" class="button"
			onClick="javascript:frm_back()" value="返回业务帐户信息"></td>
		<td><img src="img/button2_l.gif" width="11" height="20"></td>
		<td width="20">&nbsp;</td>
		<td width="11" height="20"><img src="img/button_l.gif" width="11"
			height="20"></td>
		<td height="20"><input name="next" type="button" class="button"
			onClick="javascript:update_submit()" value="确认修改"></td>
		<td width="22" height="20"><img src="img/button_r.gif" width="22"
			height="20"></td>

	</tr>
</table>
<tbl:hiddenparameters pass="txtDistrictID/txtServiceAccountID/txtAccountID/txtAccount/txtCustomerID/txtDeviceID/txtSystemEventProductID" />
	<INPUT TYPE="hidden" name="txtActionType" value="<%=CommonKeys.SERVICE_ACCOUNT_CODE_UPDATE%>"> 
	<INPUT TYPE="hidden" name="itemID" value=""> 
	<INPUT TYPE="hidden" name="func_flag" value="999"> 
</form>
<Script language=JavaScript>
 <!--
	function frm_back()
	{
		document.frmPost.txtActionType.value ="";
		document.frmPost.action="service_account_query_result_by_sa.do";
		document.frmPost.submit();
	}


	function phoneNo_Search()
	{
	  var  phoneNo =document.frmPost.phoneNo.value;
	  var  districtID =document.frmPost.txtDistrictID.value;
	  var  grade=document.frmPost.txtGrade.value;
	  var  strFeatures = "width=600px,height=500px,resizable=no,toolbar=no,scrollbars=yes,status=yes";
	  window.open("phoneNo_query.do?txtFrom=1&txtTo=10&phoneNo="+phoneNo+"&districtID="+districtID+"&grade="+grade,"电话号码查询",strFeatures);
	}
	function update_submit(){
		if(phoneNo_check()){
			document.frmPost.action="service_account_code_modify.do";
			document.frmPost.submit();
		}
	}
	function phoneNo_check()
	{
		if(document.frmPost.itemID.value =="")
		{
			alert("电话号码必须选择！");
			return false;
		}
		return true;
	}
-->
</Script>
