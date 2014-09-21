<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="resultset" prefix="rs" %>
<%@ taglib uri="/WEB-INF/oss.tld" prefix="d" %>
<%@ taglib uri="bookmark" prefix="bk" %>

<%@ page import="com.dtv.oss.util.Postern,
                 java.util.HashMap,
                 java.util.Map,
                 java.util.Iterator" %>
<%@ page import="com.dtv.oss.web.util.WebOperationUtil" %>
<SCRIPT language=javascript>
<!--
function check_form(){
    if (check_Blank(document.frmPost.txtOrgName, true, "组织机构名称"))
	    return false;
    if (check_Blank(document.frmPost.txtOrgType, true, "组织机构类型"))
	    return false;
    if (check_Blank(document.frmPost.txtStatus, true, "状态"))
	    return false;

	return true;
}
function update_submit(){
	if(check_form()){
		document.frmPost.action = "organization_op.do";
		document.frmPost.submit();
	}
}
	
function ChangeOrgType(){
	var orgType=document.frmPost.txtOrgType.value;
	if(orgType=='P')
		document.frmPost.txtOrgSubType.disabled=false;
	else{
		document.frmPost.txtOrgSubType.disabled=true;
		document.frmPost.txtOrgSubType.value="";
	}
}


function back_submit(){
    document.location.href= "<bk:backurl property='organization_query_result.do' />";
}
//-->
</SCRIPT>
<br>
<br>

<TABLE border="0" cellspacing="0" cellpadding="0" width="800" >
<TR>
	<TD>
<table  border="0" align="center" cellpadding="0" cellspacing="10" >
  <tr>
    <td><img src="img/list_tit.gif" width="19" height="19"></td>
    <td class="title">修改组织机构</td>
  </tr>
</table>
<form name="frmPost" action="" method="post" >    
      <input type="hidden" name="Action" size="22" value="update">
  	  <tbl:hiddenparameters pass="txtQryBelongTo" />
<rs:hasresultset>
<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>
<br>
<table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
<lgc:bloop requestObjectName="ResponseQueryResult" item="oneline" >
 
	<input type="hidden" name="txtDtLastmod" size="22" value="<tbl:write name="oneline" property="dtLastmod" />">
	<input type="hidden" name="txtOPID" size="22" value="<tbl:write name="oneline" property="id" />">
	<tr>
		<td class="list_bg2" align="right" width="17%"></td>
		<td class="list_bg1" width="33%"></td>
		<td class="list_bg2" align="right" width="17%"></td>
		<td class="list_bg1" width="33%"></td>
	</tr>
	<tr>
		<td class="list_bg2" align="right" width="17%">组织ID</td>
		<td class="list_bg1" width="33%" colspan="3"><input name="txtOrgID"
			type="text" class="textgray" maxlength="200" size="25" readonly
			value="<tbl:write name="oneline" property="orgID"/>"></td>
	</tr>
	<tr>
		<td class="list_bg2" align="right" width="17%">组织名称*</td>
		<td class="list_bg1" width="33%"><input name="txtOrgName"
			type="text" class="text" maxlength="50" size="25"
			value="<tbl:write name="oneline" property="orgName"/>"></td>
		<td class="list_bg2" align="right" width="17%">组织类型*</td>
		<td class="list_bg1" width="33%"><d:selcmn name="txtOrgType" mapName="SET_S_ORGANIZATIONORGTYPE" match="oneline:orgType" width="23" onchange="ChangeOrgType()"/></td>
	</tr>
	<tr>
		<td class="list_bg2" align="right" width="17%">组织子类型</td>
		<td class="list_bg1" width="33%"><d:selcmn name="txtOrgSubType" mapName="SET_S_ORGANIZATIONPARTNERCATEGORY" match="oneline:orgSubType" width="23" onclick="ChangeOrgType()"/></td>
		<td class="list_bg2" align="right" width="17%">组织机构编码</td>
		<td class="list_bg1" width="33%"><input name="txtOrgCode"
			type="text" class="text" maxlength="20" size="25"
			value="<tbl:write name="oneline" property="orgCode"/>"></td>
	</tr>
	<tr>
	     <td class="list_bg2"><div align="right">上级组织</div></td>
    <td class="list_bg1">         
      <input type="hidden" name="txtParentOrgID" value="<tbl:write name="oneline" property="parentOrgID" />">
	    <input type="text" name="txtParentOrgDesc" size="25" maxlength="50" readonly value="<tbl:WriteOrganizationInfo name="oneline" property="parentOrgID" />" class="text">
     <input name="selOrgButton" type="button" class="button" value="选择" onClick="javascript:sel_organization('C,D,P,B,O,S','txtParentOrgID','txtParentOrgDesc')">
   </td>
	
	<!--
		<td class="list_bg2" align="right" width="17%">上级组织</td>
		<td class="list_bg1" width="33%"><d:selorgparent name="txtParentOrgID"
			match="oneline:parentOrgID" width="23"/></td>
			-->
		<td class="list_bg2" align="right" width="17%">组织描述</td>
		<td class="list_bg1" width="33%"><input name="txtOrgDesc"
			type="text" class="text" maxlength="100" size="25"
			value="<tbl:write name="oneline" property="orgDesc"/>"></td>
	</tr>
	<tr>
		<td class="list_bg2" align="right" width="17%">评级</td>
		<td class="list_bg1" width="33%"><d:selcmn name="txtRank"
			mapName="SET_S_ORGNIZATIONRANK" match="oneline:rank" width="23"/></td>
		<td class="list_bg2" align="right" width="17%">税务登记号码</td>
		<td class="list_bg1" width="33%"><input name="txtRegisterNo"
			type="text" class="text" maxlength="50" size="25"
			value="<tbl:write name="oneline" property="registerNo"/>"></td>
	</tr>
	<tr>
		<td class="list_bg2" align="right">是否定位客户</td>
		<td class="list_bg1"><d:selcmn name="txtHasCustomerFlag"
			mapName="SET_G_YESNOFLAG" match="oneline:hasCustomerFlag" width="23"/></td>
		<td class="list_bg2" align="right">状态*</td>
		<td class="list_bg1"><d:selcmn name="txtStatus"
			mapName="SET_G_GENERALSTATUS" match="oneline:status" width="23"/>  
			</td>
	</tr>

</lgc:bloop>

</table>
<br>
<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>
<br>

<table align="center" border="0" cellspacing="0" cellpadding="0">
	<tr>
        <td><img src="img/button2_r.gif" width="22" height="20"></td>
		<td><input name="Submit" type="button" class="button"
			value="返&nbsp;回" onclick="javascript:back_submit()"></td>
        <td><img src="img/button2_l.gif" width="11" height="20"></td>
		<td width="20" ></td>
		<td><img src="img/button_l.gif" border="0" ></td>
		<td background="img/button_bg.gif"><a href="javascript:update_submit()" class="btn12">保&nbsp;存</a></td>
		<td><img src="img/button_r.gif" border="0" ></td>
	</tr>
</table>  

</rs:hasresultset>                  
</TD>
</TR>
</TABLE>
</form> 
<SCRIPT language=javascript>
	ChangeOrgType();

</SCRIPT>    
      
      
      