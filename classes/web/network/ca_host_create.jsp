<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl"%>
<%@ taglib uri="logic" prefix="lgc"%>
<%@ taglib uri="resultset" prefix="rs"%>
<%@ taglib uri="/WEB-INF/oss.tld" prefix="d"%>
<%@ taglib uri="bookmark" prefix="bk" %>

<%@ page import="com.dtv.oss.util.Postern,java.util.*"%>

<SCRIPT language="JavaScript">
function check_form ()
{
    if (check_Blank(document.frmPost.txtHostName, true, "��������"))
	    return false;
    if (check_Blank(document.frmPost.txtCaType, true, "CA����"))
	    return false;
    if (check_Blank(document.frmPost.txtIP, true, "��IP��ַ"))
	    return false;
    if (check_Blank(document.frmPost.txtIPBack, true, "����IP��ַ"))
	    return false;	    
	    
    if (check_Blank(document.frmPost.txtPort, true, "���˿�")||!check_Num(document.frmPost.txtPort, true, "���˿�"))
	    return false;
     if (check_Blank(document.frmPost.txtPortBack, true, "���ݶ˿�")||!check_Num(document.frmPost.txtPort, true, "���ݶ˿�"))
	    return false;
    	    	    	    
    if (check_Blank(document.frmPost.txtProtocolType, true, "�ӿ�Э������"))
	    return false;
    if (check_Blank(document.frmPost.txtLoopSize, true, "ѭ�����д�С")||!check_Num(document.frmPost.txtLoopSize, true, "ѭ�����д�С"))
	    return false;
    if (check_Blank(document.frmPost.txtLoopInterval, true, "ѭ��ʱ����")||!check_Num(document.frmPost.txtLoopInterval, true, "ѭ��ʱ����"))
	    return false;
    if (check_Blank(document.frmPost.txtTryTime, true, "�Զ���������")||!check_Num(document.frmPost.txtTryTime, true, "�Զ���������"))
	    return false;
    if (check_Blank(document.frmPost.txtStatus, true, "״̬"))
	    return false;
    if (check_Blank(document.frmPost.txtValidStartTime, true, "��Ч��ʼʱ��"))
	    return false;
    if (check_Blank(document.frmPost.txtValidEndTime, true, "��Ч����ʱ��"))
	    return false;	
    if (check_Blank(document.frmPost.txtOperatorID, true, "��½����Ա"))
	    return false;	        
	    
 
	<!-- ����ʱ�䲻��ѡ,��ѡ��ʱ������Ч��֤ -->
	if(!check_Blank(document.frmPost.txtValidStartTime, false,"")&&!check_TenDate(document.frmPost.txtValidStartTime, true,"��Ч������ʼʱ��"))
		return false;
	if(!check_Blank(document.frmPost.txtValidEndTime, false,"")&&!check_TenDate(document.frmPost.txtValidEndTime, true,"��Ч���н�ֹʱ��"))
		return false;

	var isValidStartTime=!check_Blank(document.frmPost.txtValidStartTime, false,"")&&check_TenDate(document.frmPost.txtValidStartTime, true,"��Ч������ʼʱ��");
	var isValidEndTime=!check_Blank(document.frmPost.txtValidEndTime, false,"")&&check_TenDate(document.frmPost.txtValidEndTime, true,"��Ч���н�ֹʱ��");

	if(isValidStartTime&&isValidEndTime&&!compareDate(document.frmPost.txtValidStartTime,document.frmPost.txtValidEndTime,"��ֹʱ�������ڵ�����ʼʱ��")){
			return false;
	}

	return true;
}

function ca_host_create()
{
    if (check_form())
    {
    	
        document.frmPost.submit();
    }
}

function back_submit(){
	url="<bk:backurl property='ca_host_query.do' />";
	if(url=="")
		url="ca_host_create.screen";
    document.location.href= url;

}

</SCRIPT>
<%
Map mapOperator=Postern.getAllOperator();
pageContext.setAttribute("operators",mapOperator);
%>
<form name="frmPost" method="post" action="ca_host_op.do">
	<!-- ���嵱ǰ���� -->
	<input type="hidden" name="txtActionType" size="20" value="CA_HOST_CREATE">
	<br>
	<br>
<table border="0" align="center" cellpadding="0" cellspacing="10">
	<tr>
		<td><img src="img/list_tit.gif" width="19" height="19"></td>
		<td class="title">����CA����</td>
	</tr>
</table>
<table width="100%" border="0" align="center" cellpadding="0"
	cellspacing="0" background="img/line_01.gif">
	<tr>
		<td><img src="img/mao.gif" width="1" height="1"></td>
	</tr>
</table>
<br>

<table width="810" align="center" border="0" cellspacing="1"
	cellpadding="3">
	<tr>
		<td class="list_bg2" align="right" width="17%"></td>
		<td class="list_bg1" width="33%"></td>
		<td class="list_bg2" align="left" width="17%"></td>
		<td class="list_bg1" width="33%"></td>
	</tr>
	<tr>
		<td class="list_bg2" align="right">��������*</td>
		<td class="list_bg1"><input name="txtHostName"
			type="text" class="text" maxlength="200" size="25"
			value="<tbl:writeparam name="txtHostName" />"></td>
		<td class="list_bg2" align="right">״̬*</td>
		<td class="list_bg1"><font size="2"><d:selcmn name="txtStatus"
			mapName="SET_G_GENERALSTATUS" match="txtStatus" width="23" defaultValue="V"/> </font></td>	
		
	</tr>
	<tr>
		
		<td class="list_bg2" align="right">����</td>
		<td class="list_bg1" colspan="3"><input name="txtDesc"
			type="text" class="text" maxlength="200" size="83"
			value="<tbl:writeparam name="txtDesc" />"></td>
	</tr>
	<tr>
	     <td class="list_bg2" align="right">CA����*</td>
		<td class="list_bg1"><font size="2"><input name="txtCaType"
			type="text" class="text" maxlength="200" size="25"
			value="<tbl:writeparam name="txtCaType" />"></font></td>
	    <td class="list_bg2" align="right">�ӿ�Э������*</td>
		<td class="list_bg1" ><d:selcmn name="txtProtocolType"
			mapName="SET_N_CAHOSTPROTOCOL" match="txtProtocolType" width="23"/></td>
	</tr>
	<tr>				
		<td class="list_bg2" align="right">��IP��ַ*</td>
		<td class="list_bg1" ><input name="txtIP"
			type="text" class="text" maxlength="16" size="25"
			value="<tbl:writeparam name="txtIP" />"></td>
		<td class="list_bg2" align="right">����IP��ַ*</td>
		<td class="list_bg1" ><input name="txtIPBack"
			type="text" class="text" maxlength="16" size="25"
			value="<tbl:writeparam name="txtIPBack" />"></td>	
		
	</tr>
	<tr>
		<td class="list_bg2" align="right">���˿ں�*</td>
		<td class="list_bg1" ><font size="2"><input name="txtPort"
			type="text" class="text" maxlength="200" size="25"
			value="<tbl:writeparam name="txtPort" />"></font></td>
		<td class="list_bg2" align="right">���ݶ˿ں�*</td>
		<td class="list_bg1" ><font size="2"><input name="txtPortBack"
			type="text" class="text" maxlength="200" size="25"
			value="<tbl:writeparam name="txtPortBack" />"></font></td>
	</tr>
	<tr>
		
		<td class="list_bg2" align="right">ѭ�����д�С*</td>
		<td class="list_bg1" ><font size="2"><input name="txtLoopSize"
			type="text" class="text" maxlength="200" size="25"
			value="<tbl:writeparam name="txtLoopSize" />"></font></td>
		<td class="list_bg2" align="right">ѭ��ʱ����(��)*</td>
		<td class="list_bg1" ><input name="txtLoopInterval"
			type="text" class="text" maxlength="200" size="25"
			value="<tbl:writeparam name="txtLoopInterval" />"></td>	
	</tr>
	<tr>
		
		<td class="list_bg2" align="right">�Զ���������*</td>
		<td class="list_bg1" ><font size="2"><input name="txtTryTime"
			type="text" class="text" maxlength="200" size="25"
			value="<tbl:writeparam name="txtTryTime" />"></font></td>
		<td class="list_bg2" align="right">��½����Ա*</td>
		<td class="list_bg1" align="left">
		<input type="hidden" name="txtOperatorID" size="25" value="<tbl:writeparam name="txtOperatorID"/>" >
		<input type="text" name="txtOperatorName" readonly size="24" value="<tbl:writeparam name="txtOperatorName"/>" >
		<input name="selOperButton" type="button" class="button" value="��ѯ" 
		onClick="javascript:query_window('����Ա��ѯ','txtOperatorID','txtOperatorName','query_operator.do?showPerson=����Ա')">
		
    </td>
	</tr>
	<tr>
	        <td class="list_bg2" align="right">ǩ����Կ</td>
		<td class="list_bg1" ><font size="2"><input name="txtMd5Key"
			type="text" class="text" maxlength="200" size="25"
			value="<tbl:writeparam name="txtMd5Key" />"></font></td>
		<td class="list_bg2" align="right">��Ч��ʼʱ��*</td>
		<td class="list_bg1" ><font size="2"><input onfocus="viewModule(this)" onKeydown="inputDate(document.frmPost.txtValidStartTime)" onblur="lostFocus(this)" name="txtValidStartTime"			type="text" class="text" maxlength="10" size="25"			value="<tbl:writeparam name="txtValidStartTime" />"><IMG onclick="MyCalendar.SetDate(this,document.frmPost.txtValidStartTime,'Y')" src="img/calendar.gif" style=cursor:hand border="0"></font>
			</td>
	</tr>	
	<tr>	
		<td class="list_bg2" align="right">��Ч��ֹʱ��*</td>
		<td class="list_bg1" colspan="3"><font size="2"><input onfocus="viewModule(this)" onKeydown="inputDate(document.frmPost.txtValidEndTime)" onblur="lostFocus(this)" name="txtValidEndTime"			type="text" class="text" maxlength="10" size="25"			value="<tbl:writeparam name="txtValidEndTime" />"><IMG onclick="MyCalendar.SetDate(this,document.frmPost.txtValidEndTime,'Y')" src="img/calendar.gif" style=cursor:hand border="0"></font></td>
	</tr>
	 
</table>
<br>
  <table align="center"  border="0" cellspacing="0" cellpadding="0">
         <tr>  
            <td><img src="img/button2_r.gif" width="22" height="20"></td>
			<td><input name="Submit" type="button" class="button"
					value="��&nbsp;��" onclick="javascript:back_submit()"></td>
            <td><img src="img/button2_l.gif" width="11" height="20"></td>

            <td width="20" ></td>  

            <td><img src="img/button_l.gif" width="11" height="20"></td>
            <td><input name="Submit" type="button" class="button"
					value="��&nbsp;��" onclick="javascript:ca_host_create()"></td>
            <td><img src="img/button_r.gif" width="22" height="20"></td>          
        </tr>
      </table>	
	 
          
	   </td>
	</tr>
    </table>    

</form>