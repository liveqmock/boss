<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl"%>
<%@ taglib uri="logic" prefix="lgc"%>
<%@ taglib uri="resultset" prefix="rs"%>
<%@ taglib uri="/WEB-INF/oss.tld" prefix="d"%>
<%@ taglib uri="bookmark" prefix="bk" %>

<%@ page import="com.dtv.oss.util.Postern,java.util.*"%>

<SCRIPT language="JavaScript">
function check_form ()
{
    if (check_Blank(document.frmPost.txtEventID, true, "�¼�ID"))
	    return false;
    if (check_Blank(document.frmPost.txtCondID, true, "����ID"))
	    return false;
    if (check_Blank(document.frmPost.txtCmdID, true, "����ID"))
	    return false;
    if (check_Blank(document.frmPost.txtPriority, true, "���ȼ�")||!check_Num(document.frmPost.txtPriority, true, "���ȼ�"))
	    return false;
    if (check_Blank(document.frmPost.txtStatus, true, "״̬"))
	    return false;
    
	return true;
}

function ca_eventcmdmap_modify()
{
    if (check_form())
    {
    	
        document.frmPost.submit();
    }
}

function back_submit(){
	url="<bk:backurl property='ca_eventcmdmap_query.do' />";
    document.location.href= url;

}

</SCRIPT>
<%
Map mapSystemEvent=Postern.getAllSystemClass();
Map mapCACondition=Postern.getAllCaConditions();
Map mapCACommand=Postern.getAllCaCommands();
pageContext.setAttribute("events",mapSystemEvent);
pageContext.setAttribute("conditions",mapCACondition);
pageContext.setAttribute("commands",mapCACommand);
%>
<form name="frmPost" method="post" action="ca_eventcmdmap_op.do">
	<!-- ���嵱ǰ���� -->
	<input type="hidden" name="txtActionType" size="20" value="CA_EVENTCMDMAP_MODIFY">
	<INPUT TYPE="hidden" name="txtHiddenValues" value="txtEventID/txtCondID/txtCmdID/txtStatus/txtPriority/txtDesc">
<br>
<table border="0" align="center" cellpadding="0" cellspacing="10">
	<tr>
		<td><img src="img/list_tit.gif" width="19" height="19"></td>
		<td class="title">�޸�CA�¼�����ӳ��</td>
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
		<td class="list_bg2" align="right">��ӦID</td>
		<td class="list_bg1"><input name="txtMapID"
			type="text" class="textgray" maxlength="200" size="25" readonly
			value="<tbl:writenumber name="oneline" property="mapID" digit="8"/>"></td>
		<td class="list_bg2" align="right">�¼�ID*</td>
		<td class="list_bg1"><font size="2"><tbl:select name="txtEventID" set="events" 
			width="23" match="oneline:mapEventID"/></font></td>	
	</tr>
	<tr>
		
		<td class="list_bg2" align="right">����ID*</td>
		<td class="list_bg1"><font size="2"><tbl:select name="txtCondID" set="conditions" 
			width="23" match="oneline:mapConditionID"/></font></td>
		<td class="list_bg2" align="right">����ID*</td>
		<td class="list_bg1"><font size="2"><tbl:select name="txtCmdID" set="commands" 
			width="23" match="oneline:mapCmdID"/></font></td>	
	</tr>
	<tr>
		
		<td class="list_bg2" align="right">���ȼ�*</td>
		<td class="list_bg1"><input name="txtPriority"
			type="text" class="text" maxlength="10" size="25"
			value="<tbl:write name="oneline" property="priority"/>"></td>
		<td class="list_bg2" align="right">״̬*</td>
		<td class="list_bg1"><font size="2"><d:selcmn name="txtStatus"
			mapName="SET_G_GENERALSTATUS" match="oneline:status" width="23"/> </font></td>	
	</tr>
	 
	<tr>
		<td class="list_bg2" align="right">����</td>
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
					value="��&nbsp;��" onclick="javascript:back_submit()"></td>
            <td><img src="img/button2_l.gif" width="11" height="20"></td>

            <td width="20" ></td>  

            <td><img src="img/button_l.gif" width="11" height="20"></td>
            <td><input name="Submit" type="button" class="button"
					value="��&nbsp;��" onclick="javascript:ca_eventcmdmap_modify()"></td>
            <td><img src="img/button_r.gif" width="22" height="20"></td>          
        </tr>
      </table>	
	   </td>
	</tr>
    </table>    

</form>
