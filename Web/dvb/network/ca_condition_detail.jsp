<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl"%>
<%@ taglib uri="logic" prefix="lgc"%>
<%@ taglib uri="resultset" prefix="rs"%>
<%@ taglib uri="/WEB-INF/oss.tld" prefix="d"%>
<%@ taglib uri="bookmark" prefix="bk" %>

<%@ page import="com.dtv.oss.util.Postern,java.util.*"%>

<SCRIPT language="JavaScript">
function check_form ()
{
    if (check_Blank(document.frmPost.txtCondName, true, "条件名称"))
	    return false;
    if (check_Blank(document.frmPost.txtHostID, true, "CA主机"))
	    return false;
    if (check_Blank(document.frmPost.txtCondString, true, "条件内容"))
	    return false;
    
	return true;
}

function ca_condition_modify()
{
    if (check_form())
    {
    	
        document.frmPost.submit();
    }
}

function back_submit(){
	url="<bk:backurl property='ca_condition_query.do' />";
    document.location.href= url;

}

</SCRIPT>
<%
Map mapCAHost=Postern.getAllCAHost();
pageContext.setAttribute("hosts",mapCAHost);
%>
<form name="frmPost" method="post" action="ca_condition_op.do">
	<!-- 定义当前操作 -->
	<input type="hidden" name="txtActionType" size="20" value="CA_CONDITION_MODIFY">
	<INPUT TYPE="hidden" name="txtHiddenValues" value="txtCondName/txtHostID/txtCondString/txtDesc">

<table border="0" align="center" cellpadding="0" cellspacing="10">
	<tr>
		<td><img src="img/list_tit.gif" width="19" height="19"></td>
		<td class="title">修改CA条件</td>
	</tr>
</table>
<table width="100%" border="0" align="center" cellpadding="0"
	cellspacing="0" background="img/line_01.gif">
	<tr>
		<td><img src="img/mao.gif" width="1" height="1"></td>
	</tr>
</table>
<br>
 
	<lgc:bloop requestObjectName="ResponseQueryResult" item="oneline">

<table width="100%" align="center" border="0" cellspacing="1"
	cellpadding="3">
	<tr>
		<td class="list_bg2" align="right" width="17%"></td>
		<td class="list_bg1" width="33%"></td>
		<td class="list_bg2" align="left" width="17%"></td>
		<td class="list_bg1" width="33%"></td>
	</tr>
	<tr>
		<td class="list_bg2" align="right">条件ID</td>
		<td class="list_bg1" colspan="3"><input name="txtCondID"
			type="text" class="textgray" maxlength="10" size="25" readonly
			value="<tbl:writenumber name="oneline" property="condID" digit="8"/>"></td>
	</tr>
	<tr>
		<td class="list_bg2" align="right">条件名称*</td>
		<td class="list_bg1"><input name="txtCondName"
			type="text" class="text" maxlength="50" size="25"
			value="<tbl:write name="oneline" property="condName" />"></td>
		<td class="list_bg2" align="right">CA主机*</td>
		<td class="list_bg1"><font size="2"><tbl:select name="txtHostID" set="hosts" 
			width="23" match="oneline:hostID"/></font></td>
	</tr>
	<tr>
		<td class="list_bg2" align="right">条件内容*</td>
		<td class="list_bg1" colspan="3"><input name="txtCondString"
			type="text" class="text" maxlength="800" size="83"
			value="<tbl:write name="oneline" property="condString"/>"></td>
	</tr>
	<tr>
		<td class="list_bg2" align="right">描述</td>
		<td class="list_bg1" colspan="3"><input name="txtDesc"
			type="text" class="text" maxlength="200" size="83"
			value="<tbl:write name="oneline" property="description"/>"></td>
	</tr>
</table>
	</lgc:bloop>
 
	<table width="100%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
	<tr align="center">
	     <td class="list_bg1">

	  <table align="center"  border="0" cellspacing="0" cellpadding="0">
         <tr>  
            <td><img src="img/button2_r.gif" width="22" height="20"></td>
			<td><input name="Submit" type="button" class="button"
					value="返&nbsp;回" onclick="javascript:back_submit()"></td>
            <td><img src="img/button2_l.gif" width="11" height="20"></td>

            <td width="20" ></td>  

            <td><img src="img/button_l.gif" width="11" height="20"></td>
            <td><input name="Submit" type="button" class="button"
					value="保&nbsp;存" onclick="javascript:ca_condition_modify()"></td>
            <td><img src="img/button_r.gif" width="22" height="20"></td>          
        </tr>
      </table>	
	   </td>
	</tr>
    </table>    

</form>
