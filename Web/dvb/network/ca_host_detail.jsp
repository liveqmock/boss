<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl"%>
<%@ taglib uri="logic" prefix="lgc"%>
<%@ taglib uri="resultset" prefix="rs"%>
<%@ taglib uri="/WEB-INF/oss.tld" prefix="d"%>
<%@ taglib uri="bookmark" prefix="bk" %>
<%@ page import="com.dtv.oss.util.Postern,java.util.*"%>
<%@ page import="com.dtv.oss.dto.CAHostDTO"%>
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

	var isValidStartTime=!check_Blank(document.frmPost.txtValidStartTime, false,"")&&check_TenDate(document.frmPost.txtValidStartTime, false,"��Ч������ʼʱ��");
	var isValidEndTime=!check_Blank(document.frmPost.txtValidEndTime, false,"")&&check_TenDate(document.frmPost.txtValidEndTime, false,"��Ч���н�ֹʱ��");

	if(isValidStartTime&&isValidEndTime&&!compareDate(document.frmPost.txtValidStartTime,document.frmPost.txtValidEndTime,"��ֹʱ�������ڵ�����ʼʱ��")){
			return false;
	}

	return true;
}

function ca_host_modify()
{
    if (check_form())
    {
    	
        document.frmPost.submit();
    }
}

function back_submit(){
	url="<bk:backurl property='ca_host_query.do' />";
	if(url=="")
		url="ca_product_query.screen";
    document.location.href= url;

}

</SCRIPT>
<%
Map mapOperator=Postern.getAllOperator();
pageContext.setAttribute("operators",mapOperator);
%>
<form name="frmPost" method="post" action="ca_host_op.do">
	<!-- ���嵱ǰ���� -->
	<input type="hidden" name="txtActionType" size="20" value="CA_HOST_MODIFY">
	<br>
	<br>
<table border="0" align="center" cellpadding="0" cellspacing="10">
	<tr>
		<td><img src="img/list_tit.gif" width="19" height="19"></td>
		<td class="title">�޸�CA������Ϣ</td>
	</tr>
</table>
<table width="98%" border="0" align="center" cellpadding="0"
	 cellspacing="0" background="img/line_01.gif">
	<tr>
		<td><img src="img/mao.gif" width="1" height="1"></td>
	</tr>
</table>
<br>
 
	<lgc:bloop requestObjectName="ResponseQueryResult" item="oneline">
	<%
	CAHostDTO dto=(CAHostDTO)pageContext.getAttribute("oneline");

				String operatorName=Postern.getOperatorNameByID(dto.getOperatorID());
				if(operatorName==null)
					operatorName="";
		%>
<table width="98%" align="center" border="0" cellspacing="1"
	cellpadding="3">
	<tr>
		<td class="list_bg2" align="right" width="17%"></td>
		<td class="list_bg1" width="33%"></td>
		<td class="list_bg2" align="left" width="17%"></td>
		<td class="list_bg1" width="33%"></td>
	</tr>
	 <input type="hidden" name="txtHostID" size="20" value="<tbl:write name="oneline" property="hostID" />">
	<tr>
		<td class="list_bg2" align="right">��������*</td>
		<td class="list_bg1"><input name="txtHostName"
			type="text" class="text" maxlength="200" size="25"
			value="<tbl:write name="oneline" property="hostName" />"></td>
		<td class="list_bg2" align="right">״̬*</td>
		<td class="list_bg1"><font size="2"><d:selcmn name="txtStatus"
			mapName="SET_G_GENERALSTATUS" match="oneline:status" width="25"/> </font></td>	
		
	</tr>
	<tr>
		
		<td class="list_bg2" align="right">����</td>
		<td class="list_bg1" colspan="3"><input name="txtDesc"
			type="text" class="text" maxlength="200" size="83"
			value="<tbl:write name="oneline" property="description"/>"></td>
	</tr>
	<tr>
	<td class="list_bg2" align="right">CA����*</td>
		<td class="list_bg1"><font size="2"><input name="txtCaType"
			type="text" class="text" maxlength="200" size="25"
			value="<tbl:write name="oneline" property="caType" />"></font></td>
			 
		<td class="list_bg2" align="right">�ӿ�Э������*</td>
		<td class="list_bg1" ><d:selcmn name="txtProtocolType"
			mapName="SET_N_CAHOSTPROTOCOL" match="oneline:protocolType" width="25"/></td>	
	</tr>		
	<tr>
		<td class="list_bg2" align="right">��IP��ַ*</td>
		<td class="list_bg1" ><input name="txtIP"
			type="text" class="text" maxlength="16" size="25"
			value="<tbl:write name="oneline" property="ip"/>"></td>
		<td class="list_bg2" align="right">����IP��ַ*</td>
		<td class="list_bg1" ><input name="txtIPBack"
			type="text" class="text" maxlength="16" size="25"
			value="<tbl:write name="oneline" property="ipBack"/>"></td>	
		
	</tr>
	<tr>
		<td class="list_bg2" align="right">���˿�*</td>
		<td class="list_bg1" ><font size="2"><input name="txtPort"
			type="text" class="text" maxlength="50" size="25"
			value="<tbl:write name="oneline" property="port"/>"></font></td>
		<td class="list_bg2" align="right">���ݶ˿�*</td>
		<td class="list_bg1" ><font size="2"><input name="txtPortBack"
			type="text" class="text" maxlength="50" size="25"
			value="<tbl:write name="oneline" property="portBack"/>"></font></td>
	</tr>
	<tr>
		
		<td class="list_bg2" align="right">ѭ�����д�С*</td>
		<td class="list_bg1" ><font size="2"><input name="txtLoopSize"
			type="text" class="text" maxlength="10" size="25"
			value="<tbl:write name="oneline" property="loopSize"/>"></font></td>
	       <td class="list_bg2" align="right">ѭ��ʱ����(��)*</td>
		<td class="list_bg1" ><input name="txtLoopInterval"
			type="text" class="text" maxlength="10" size="25"
			value="<tbl:write name="oneline" property="loopInterval"/>"></td>		
	</tr>
	<tr>
		
		<td class="list_bg2" align="right">�Զ���������*</td>
		<td class="list_bg1" ><font size="2"><input name="txtTryTime"
			type="text" class="text" maxlength="10" size="25"
			value="<tbl:write name="oneline" property="tryTime"/>"></font></td>
		<td class="list_bg2" align="right">��½����Ա*</td>
			<td class="list_bg1" align="left">
		<input type="hidden" name="txtOperatorID" size="25" value="<tbl:write name="oneline" property="operatorID"/>" >
		<input type="text" name="txtOperatorName" readonly size="24" value="<%=operatorName%>" >
		<input name="selOperButton" type="button" class="button" value="��ѯ" 
		onClick="javascript:query_window('����Ա��ѯ','txtOperatorID','txtOperatorName','query_operator.do?showPerson=����Ա')">
		
    </td>
	</tr>
	<tr>
		
		<td class="list_bg2" align="right">ǩ����Կ</td>
		<td class="list_bg1" ><font size="2"><input name="txtMd5Key"
			type="text" class="text" maxlength="200" size="25"
			value="<tbl:write name="oneline" property="md5key"/>"></font></td>
		<td class="list_bg2" align="right">��Ч��ʼʱ��*</td>
		<td class="list_bg1" ><font size="2"><input onfocus="viewModule(this)" onKeydown="inputDate(document.frmPost.txtValidStartTime)" onblur="lostFocus(this)" name="txtValidStartTime"			type="text" class="text" maxlength="10" size="25"			value="<tbl:writedate name="oneline" property="validStartTime"/>"><IMG onclick="MyCalendar.SetDate(this,document.frmPost.txtValidStartTime,'Y')" src="img/calendar.gif" style=cursor:hand border="0"></font>
			</td>	
	</tr>
	<tr>
	        <td class="list_bg2" align="right">�ϴ�����ʱ��</td>
		<td class="list_bg1" ><font size="2"><input name="txtLastRunTime"
			type="text" readonly class="textgray" maxlength="200" size="25"
			value="<tbl:writedate name="oneline" property="lastRunTime" includeTime="true"/>"  > </font>
			</td>	
		
		<td class="list_bg2" align="right">��Ч����ʱ��*</td>
		<td class="list_bg1"><font size="2"><input onfocus="viewModule(this)" onKeydown="inputDate(document.frmPost.txtValidEndTime)" onblur="lostFocus(this)" name="txtValidEndTime"			type="text" class="text" maxlength="10" size="25"			value="<tbl:writedate name="oneline" property="validEndTime"/>"><IMG onclick="MyCalendar.SetDate(this,document.frmPost.txtValidEndTime,'Y')" src="img/calendar.gif" style=cursor:hand border="0"></font></td>
	</tr>
	<tr>
	        
		
		<td class="list_bg2" align="right">�ϴ�ֹͣʱ��</td>
		<td class="list_bg1" colspan="3" ><font size="2"><input name="txtLastStopTime"
			type="text" readonly class="textgray" maxlength="200" size="25"
			value="<tbl:writedate name="oneline" property="lastStopTime" includeTime="true"/>"></font></td>
	</tr>
	
	
</table>
	</lgc:bloop>
 
	<table width="98%" border="0" align="center" cellpadding="0"
	 cellspacing="0" background="img/line_01.gif">
	<tr>
		<td><img src="img/mao.gif" width="1" height="1"></td>
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
					value="��&nbsp;��" onclick="javascript:ca_host_modify()"></td>
            <td><img src="img/button_r.gif" width="22" height="20"></td>          
        </tr>
      </table>	
	    

</form>