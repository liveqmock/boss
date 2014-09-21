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

function add_submit(){
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
    <td class="title">新增组织机构</td>
  </tr>
</table>
<form name="frmPost" action="" method="post" >    
      <input type="hidden" name="Action" size="22" value="add">
	  <tbl:hiddenparameters pass="txtQryBelongTo" />
<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>
<br>
<table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
<%
String txtQryBelongTo=request.getParameter("txtQryBelongTo");
System.out.println("page out txtQryBelongTo:"+txtQryBelongTo);
if(txtQryBelongTo==null)txtQryBelongTo="";
String[] arrBelongTo=txtQryBelongTo.split("_");
String strParentOrgID=txtQryBelongTo;
String strOrgType="";
if(arrBelongTo.length==3){
	strParentOrgID=arrBelongTo[1];
	strOrgType=arrBelongTo[2];
}
System.out.println("page out strParentOrgID:"+strParentOrgID);
System.out.println("page out strOrgType:"+strOrgType);

%>
	<tr>
		<td class="list_bg2" align="right" width="17%">组织名称*</td>
		<td class="list_bg1" width="33%"><input name="txtOrgName"
			type="text" class="text" maxlength="50" size="25"
			value="<tbl:writeparam name="txtOrgName"/>"></td>
		<td class="list_bg2" align="right" width="17%">组织类型*</td>
		<td class="list_bg1" width="33%"><d:selcmn name="txtOrgType"
			mapName="SET_S_ORGANIZATIONORGTYPE" match="<%=strOrgType%>" width="23" onchange="ChangeOrgType()"/></td>
	</tr>
	<tr>
		<td class="list_bg2" align="right" width="17%">组织子类型</td>
		<td class="list_bg1" width="33%"><d:selcmn name="txtOrgSubType"
			mapName="SET_S_ORGANIZATIONPARTNERCATEGORY" match="txtOrgSubType" width="23" onclick="ChangeOrgType()"/></td>
		<td class="list_bg2" align="right" width="17%">组织机构编码</td>
		<td class="list_bg1" width="33%"><input name="txtOrgCode"
			type="text" class="text" maxlength="20" size="25"
			value="<tbl:writeparam name="txtOrgCode"/>"></td>
	</tr>
	<tr>
		<td class="list_bg2" align="right" width="17%">上级组织</td>
		<td class="list_bg1" width="33%"><d:selorgparent name="txtParentOrgID"
			match="<%=strParentOrgID%>" width="23" disabled="true"/><input type="hidden" name="txtParentOrgID" size="22" value="<%=strParentOrgID%>"></td>
		<td class="list_bg2" align="right" width="17%">组织描述</td>
		<td class="list_bg1" width="33%"><input name="txtOrgDesc"

			type="text" class="text" maxlength="100" size="25"
			value="<tbl:writeparam name="txtOrgDesc"/>"></td>
	</tr>
	<tr>
		<td class="list_bg2" align="right" width="17%">评级</td>
		<td class="list_bg1" width="33%"><d:selcmn name="txtRank"
			mapName="SET_S_ORGNIZATIONRANK" match="txtRank" width="23"/></td>
		<td class="list_bg2" align="right" width="17%">税务登记号码</td>
		<td class="list_bg1" width="33%"><input name="txtRegisterNo"
			type="text" class="text" maxlength="50" size="25"
			value="<tbl:writeparam name="txtRegisterNo"/>"></td>
	</tr>
	<tr>
		<td class="list_bg2" align="right">是否定位客户</td>
		<td class="list_bg1"><d:selcmn name="txtHasCustomerFlag"
			mapName="SET_G_YESNOFLAG" match="txtHasCustomerFlag" width="23"/></td>
		<td class="list_bg2" align="right">状态*</td>
		<td class="list_bg1"><d:selcmn name="txtStatus"
			mapName="SET_G_GENERALSTATUS" match="<tbl:writeparam name='txtStatus'/>" width="23" defaultValue="V"/>  
			</td>
	</tr>

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
		<td background="img/button_bg.gif"><a href="javascript:add_submit()" class="btn12">保&nbsp;存</a></td>
		<td><img src="img/button_r.gif" border="0" ></td>
	</tr>
</table>  

</TD>
</TR>
</TABLE>
</form> 



      
      
      
      