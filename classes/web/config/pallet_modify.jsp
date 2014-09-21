<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl"%>
<%@ taglib uri="logic" prefix="lgc"%>
<%@ taglib uri="resultset" prefix="rs"%>
<%@ taglib uri="/WEB-INF/oss.tld" prefix="d"%>
<%@ taglib uri="bookmark" prefix="bk" %>

<%@ page import="com.dtv.oss.util.Postern,java.util.*"%>
<%@ page import="com.dtv.oss.dto.PalletDTO"%>

<SCRIPT language="JavaScript">
function check_form ()
{
    if (check_Blank(document.frmPost.txtPalletName, true, "��������"))
	    return false;
    if (check_Blank(document.frmPost.txtMaxNumber, true, "�������洢�豸��")||!check_Num(document.frmPost.txtMaxNumber, true, "�������洢�豸��"))
	    return false;
    if (check_Blank(document.frmPost.txtPalletStatus, true, "����״̬"))
	    return false;
    if (check_Blank(document.frmPost.txtOwner, true, "���������ֿ�"))
	    return false;

	return true;
}

function pallet_modify()
{
    if (check_form())
    {
    	
        document.frmPost.submit();
    }
}

function back_submit(){
    document.location.href= "<bk:backurl property='pallet_query.do'/>";
}

</SCRIPT>

<form name="frmPost" method="post" action="pallet_modify.do">
	<input type="hidden" name="txtFrom" size="20" value="1">
	<input type="hidden" name="txtTo" size="20" value="10">
	<!-- ���嵱ǰ���� -->
	<input type="hidden" name="txtActionType" size="20" value="MODIFY">
<table border="0" align="center" cellpadding="0" cellspacing="10">
	<tr>
		<td><img src="img/list_tit.gif" width="19" height="19"></td>
		<td class="title">�����޸�</td>
	</tr>
</table>
<table width="100%" border="0" align="center" cellpadding="0"
	cellspacing="0" background="img/line_01.gif">
	<tr>
		<td><img src="img/mao.gif" width="1" height="1"></td>
	</tr>
</table>
<br>
<rs:hasresultset>
	<lgc:bloop requestObjectName="ResponseQueryResult" item="oneline">
				<%PalletDTO dto = (PalletDTO) pageContext
							.getAttribute("oneline");
					String strPalletStatus=dto.getStatus();
					String strOwner=dto.getDepotID()+"";
					%>

<table width="810" align="center" border="0" cellspacing="1"
	cellpadding="3">
	<tr>
		<td class="list_bg2" align="right" width="17%">����ID</td>
		<td class="list_bg1" width="33%"><input name="txtPalletID" type="text"
			maxlength="100" size="25" readonly class="textgray" 
			value="<tbl:writenumber name="oneline" property="palletID" digit="8"/>"></td>
		<td class="list_bg2" align="right" width="17%">��������*</td>
		<td class="list_bg1" width="33%"><input name="txtPalletName"
			type="text" class="text" maxlength="25" size="25"
			value="<tbl:write name="oneline" property="palletName" />"></td>
	</tr>
	<tr>
		<td class="list_bg2" align="right">״̬*</td>
		<td class="list_bg1"><font size="2"><d:selcmn name="txtPalletStatus"
			mapName="SET_G_GENERALSTATUS" match="txtPalletStatus" width="23" defaultValue="<%=strPalletStatus%>"/> </font></td>
		<td class="list_bg2" align="right">���洢�豸��*</td>
		<td class="list_bg1"><input name="txtMaxNumber" type="text"
			class="text" maxlength="10" size="25"  
			value="<tbl:write name="oneline" property="maxNumberAllowed"/>"></td>
	</tr>
	<tr>
		<td class="list_bg2" align="right">�����ֿ�*</td>
		<td class="list_bg1" colspan="3"><font size="2"><d:seldepotbyoper  name="txtOwner" match="txtOwner" width="23" defaultValue="<%=strOwner%>"/></font></td>
	</tr>
	<tr>
		<td class="list_bg2" align="right">����</td>
		<td class="list_bg1" colspan='3'><font size="2"><input name="txtPalletDesc" type="text"
			class="text" maxlength="100" size="83"  
			value="<tbl:write name="oneline" property="palletDesc"/>"></font></td>
	</tr>
</table>
	</lgc:bloop>
</rs:hasresultset>

    <table width="100%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
	<tr align="center">
	     <td class="list_bg1">

	  <table align="center"  border="0" cellspacing="0" cellpadding="0">
         <tr>  
            <td><img src="img/button2_r.gif" width="22" height="20"></td>
			<td><input name="Submit" type="button" class="button"
					value="��&nbsp;��" onclick="javascript:back_submit()"></td>
            <td><img src="img/button2_l.gif" width="11" height="20"></td>
            <td width="20" ></td>  

            <td><img src="img/button_l.gif" width="11" height="20"></td>
            <td><input name="Submit" type="button" class="button"
					value="��&nbsp;��" onclick="javascript:pallet_modify()"></td>
            <td><img src="img/button_r.gif" width="22" height="20"></td>          
        </tr>
      </table>	
	   </td>
	</tr>
    </table>    

</form>
