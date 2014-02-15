<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="resultset" prefix="rs" %>
<%@ taglib uri="/WEB-INF/oss.tld" prefix="d" %>
<%@ taglib uri="bookmark" prefix="bk" %>

<%@ page import="com.dtv.oss.util.Postern,
                 java.util.HashMap,
                 java.util.Map,
                 java.util.Iterator" %>
<%@ page import="com.dtv.oss.dto.DistrictSettingDTO"%>
<%@ page import="com.dtv.oss.web.util.WebOperationUtil" %>
<SCRIPT language=javascript>
<!--
function check_form()
{
    if (check_Blank(document.frmPost.txtDistrictCode, true, "地区编码"))
	    return false;
    if (check_Blank(document.frmPost.txtDistrictName, true, "地区名称"))
	    return false;
    if (check_Blank(document.frmPost.txtStatus, true, "状态"))
	    return false;
    if (check_Blank(document.frmPost.txtDistrictType, true, "类型"))
	    return false;
     if (check_Blank(document.frmPost.txtDistrictDesc, true, "上级行政区"))
	    return false;	    
	    

	return true;
}

function update_submit(){
	if(check_form()){
		document.frmPost.action = "district_setting_op.do";
		document.frmPost.submit();
	}
}
	
function back_submit(){
    document.location.href= "<bk:backurl property='district_setting_query_result.do' />";
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
    <td class="title">修改行政区</td>
  </tr>
</table>
<form name="frmPost" action="" method="post" >    
      <input type="hidden" name="Action" size="22" value="update">
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
		<td class="list_bg2" align="right" width="17%">地区ID</td>
		<td class="list_bg1" width="33%"><input name="txtOPID"
			type="text" readonly  class="textgray" maxlength="200" size="25"
			value="<tbl:write name="oneline" property="id"/>"></td>
		<td class="list_bg2" align="right" width="17%">地区编码*</td>
		<td class="list_bg1" width="33%"><input name="txtDistrictCode"
			type="text" class="text" maxlength="20" size="25"
			value="<tbl:write name="oneline" property="districtCode"/>"></td>
		 
	</tr>
	<tr>
		
		<td class="list_bg2" align="right" width="17%">名称*</td>
		<td class="list_bg1" width="33%"><input name="txtDistrictName" type="text"
			class="text" maxlength="50" size="25"  
			value="<tbl:write name="oneline" property="name"/>"></td>
		<td class="list_bg2" align="right">类型*</td>
		<td class="list_bg1"><d:selcmn name="txtDistrictType"
			mapName="SET_G_DISTRICTTYPE" match="oneline:type" width="23"/></td>	
	</tr>
	
	
   
	<tr>
		 <td class="list_bg2"><div align="right">上级行政区*</div></td>
                <td class="list_bg1">         
                <input type="hidden" name="txtQryBelongTo" value="<tbl:write name="oneline" property="belongTo" />">
	        <input type="text" name="txtDistrictDesc" size="25" maxlength="50" readonly value="<tbl:WriteDistrictInfo name="oneline" property="belongTo" />" class="text">
                <input name="selDistButton" type="button" class="button" value="选择" onClick="javascript:sel_district('all','txtQryBelongTo','txtDistrictDesc')">
                </td> 
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
		<td background="img/button_bg.gif"><a href="javascript:update_submit()" class="btn12">修&nbsp;改</a></td>
		<td><img src="img/button_r.gif" border="0" ></td>
	</tr>
</table>  
   
</rs:hasresultset>                  
</TD>
</TR>
</TABLE>
</form> 



      
      
      
      