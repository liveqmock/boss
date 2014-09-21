<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl"%>
<%@ taglib uri="logic" prefix="lgc"%>
<%@ taglib uri="resultset" prefix="rs"%>
<%@ taglib uri="/WEB-INF/oss.tld" prefix="d"%>
<%@ taglib uri="bookmark" prefix="bk" %>

<%@ page import="com.dtv.oss.util.Postern,java.util.*"%>
<%@ page import="com.dtv.oss.dto.DepotDTO"%>

<SCRIPT language="JavaScript">
function check_form ()
{
    if (check_Blank(document.frmPost.txtDepotName, true, "仓库名称"))
	    return false;
    if (check_Blank(document.frmPost.txtDetailAddress, true, "仓库地址"))
	    return false;
    if (check_Blank(document.frmPost.txtDepotStatus, true, "状态"))
	    return false;

	return true;
}

function depot_modify()
{
    if (check_form())
    {
    	
        document.frmPost.submit();
    }
}

function back_submit(){
    document.location.href= "<bk:backurl property='depot_query.do' />";
}

</SCRIPT>

<form name="frmPost" method="post" action="depot_modify.do">
	<input type="hidden" name="txtFrom" size="20" value="1">
	<input type="hidden" name="txtTo" size="20" value="10">
	<!-- 定义当前操作 -->
	<input type="hidden" name="txtActionType" size="20" value="MODIFY">
<table border="0" align="center" cellpadding="0" cellspacing="10">
	<tr>
		<td><img src="img/list_tit.gif" width="19" height="19"></td>
		<td class="title">仓库修改</td>
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
				<%DepotDTO dto = (DepotDTO) pageContext
							.getAttribute("oneline");
					String strDepotStatus=dto.getStatus();
					String strOwner=dto.getOwnerID()+"";
					%>

<table width="810" align="center" border="0" cellspacing="1"
	cellpadding="3">
	<tr>
		<td class="list_bg2" align="right" width="17%">仓库ID</td>
		<td class="list_bg1" width="33%"><input name="txtDepotID" type="text"
			maxlength="10" size="25" readonly  class="textgray" 
			value="<tbl:writenumber name="oneline" property="depotID" digit="6"/>"></td>
		<td class="list_bg2" align="right" width="17%">仓库名称*</td>
		<td class="list_bg1" width="33%"><input name="txtDepotName"
			type="text" class="text" maxlength="25" size="25"
			value="<tbl:write name="oneline" property="depotName" />"></td>
	</tr>
	<tr>
		<td class="list_bg2" align="right">地址*</td>
		<td class="list_bg1" colspan="3"><font size="2"><input name="txtDetailAddress"
			type="text" class="text" maxlength="100" size="83"
			value="<tbl:write name="oneline" property="detailAddress" />"></font></td>
	</tr>
	<tr>
		<td class="list_bg2" align="right">状态*</td>
		<td class="list_bg1"><font size="2"><d:selcmn name="txtDepotStatus"
			mapName="SET_G_GENERALSTATUS" match="txtDepotStatus" width="23" defaultValue="<%=strDepotStatus%>"/> </font></td>
		<td class="list_bg2" align="right">仓库所有者</td>
		<td class="list_bg1">
      <input type="hidden" name="txtOwner" value="<tbl:write name="oneline"  property="ownerID" />">
      <input type="text" name="txtOwnerDesc" size="25" maxlength="50" readonly value="<tbl:WriteOrganizationInfo name="oneline" property="ownerID" />" class="text">
      <input name="selOrgButton" type="button" class="button" value="选择" onClick="javascript:sel_organization('C,B,D','txtOwner','txtOwnerDesc')">
		</td>
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
					value="修&nbsp;改" onclick="javascript:depot_modify()"></td>
            <td><img src="img/button_r.gif" width="22" height="20"></td>          
        </tr>
      </table>	
	   </td>
	</tr>
    </table>    

</form>
