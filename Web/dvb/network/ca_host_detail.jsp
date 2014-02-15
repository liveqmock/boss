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
    if (check_Blank(document.frmPost.txtHostName, true, "主机名称"))
	    return false;
    if (check_Blank(document.frmPost.txtCaType, true, "CA类型"))
	    return false;
    if (check_Blank(document.frmPost.txtIP, true, "主IP地址"))
	    return false;
    if (check_Blank(document.frmPost.txtIPBack, true, "备份IP地址"))
	    return false;	    
    if (check_Blank(document.frmPost.txtPort, true, "主端口")||!check_Num(document.frmPost.txtPort, true, "主端口"))
	    return false;
    if (check_Blank(document.frmPost.txtPortBack, true, "备份端口")||!check_Num(document.frmPost.txtPort, true, "备份端口"))
	    return false;	    
    if (check_Blank(document.frmPost.txtProtocolType, true, "接口协议类型"))
	    return false;
    if (check_Blank(document.frmPost.txtLoopSize, true, "循环队列大小")||!check_Num(document.frmPost.txtLoopSize, true, "循环队列大小"))
	    return false;
    if (check_Blank(document.frmPost.txtLoopInterval, true, "循环时间间隔")||!check_Num(document.frmPost.txtLoopInterval, true, "循环时间间隔"))
	    return false;
    if (check_Blank(document.frmPost.txtTryTime, true, "自动重连次数")||!check_Num(document.frmPost.txtTryTime, true, "自动重连次数"))
	    return false;
    if (check_Blank(document.frmPost.txtStatus, true, "状态"))
            return false;
     if (check_Blank(document.frmPost.txtValidStartTime, true, "有效起始时间"))
	    return false;
    if (check_Blank(document.frmPost.txtValidEndTime, true, "有效结束时间"))
	    return false;
    if (check_Blank(document.frmPost.txtOperatorID, true, "登陆操作员"))
	    return false;

	<!-- 两个时间不必选,当选中时进行有效验证 -->
	if(!check_Blank(document.frmPost.txtValidStartTime, false,"")&&!check_TenDate(document.frmPost.txtValidStartTime, true,"有效运行起始时间"))
		return false;
	if(!check_Blank(document.frmPost.txtValidEndTime, false,"")&&!check_TenDate(document.frmPost.txtValidEndTime, true,"有效运行截止时间"))
		return false;

	var isValidStartTime=!check_Blank(document.frmPost.txtValidStartTime, false,"")&&check_TenDate(document.frmPost.txtValidStartTime, false,"有效运行起始时间");
	var isValidEndTime=!check_Blank(document.frmPost.txtValidEndTime, false,"")&&check_TenDate(document.frmPost.txtValidEndTime, false,"有效运行截止时间");

	if(isValidStartTime&&isValidEndTime&&!compareDate(document.frmPost.txtValidStartTime,document.frmPost.txtValidEndTime,"截止时间必须大于等于起始时间")){
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
	<!-- 定义当前操作 -->
	<input type="hidden" name="txtActionType" size="20" value="CA_HOST_MODIFY">
	<br>
	<br>
<table border="0" align="center" cellpadding="0" cellspacing="10">
	<tr>
		<td><img src="img/list_tit.gif" width="19" height="19"></td>
		<td class="title">修改CA主机信息</td>
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
		<td class="list_bg2" align="right">主机名称*</td>
		<td class="list_bg1"><input name="txtHostName"
			type="text" class="text" maxlength="200" size="25"
			value="<tbl:write name="oneline" property="hostName" />"></td>
		<td class="list_bg2" align="right">状态*</td>
		<td class="list_bg1"><font size="2"><d:selcmn name="txtStatus"
			mapName="SET_G_GENERALSTATUS" match="oneline:status" width="25"/> </font></td>	
		
	</tr>
	<tr>
		
		<td class="list_bg2" align="right">描述</td>
		<td class="list_bg1" colspan="3"><input name="txtDesc"
			type="text" class="text" maxlength="200" size="83"
			value="<tbl:write name="oneline" property="description"/>"></td>
	</tr>
	<tr>
	<td class="list_bg2" align="right">CA类型*</td>
		<td class="list_bg1"><font size="2"><input name="txtCaType"
			type="text" class="text" maxlength="200" size="25"
			value="<tbl:write name="oneline" property="caType" />"></font></td>
			 
		<td class="list_bg2" align="right">接口协议类型*</td>
		<td class="list_bg1" ><d:selcmn name="txtProtocolType"
			mapName="SET_N_CAHOSTPROTOCOL" match="oneline:protocolType" width="25"/></td>	
	</tr>		
	<tr>
		<td class="list_bg2" align="right">主IP地址*</td>
		<td class="list_bg1" ><input name="txtIP"
			type="text" class="text" maxlength="16" size="25"
			value="<tbl:write name="oneline" property="ip"/>"></td>
		<td class="list_bg2" align="right">备份IP地址*</td>
		<td class="list_bg1" ><input name="txtIPBack"
			type="text" class="text" maxlength="16" size="25"
			value="<tbl:write name="oneline" property="ipBack"/>"></td>	
		
	</tr>
	<tr>
		<td class="list_bg2" align="right">主端口*</td>
		<td class="list_bg1" ><font size="2"><input name="txtPort"
			type="text" class="text" maxlength="50" size="25"
			value="<tbl:write name="oneline" property="port"/>"></font></td>
		<td class="list_bg2" align="right">备份端口*</td>
		<td class="list_bg1" ><font size="2"><input name="txtPortBack"
			type="text" class="text" maxlength="50" size="25"
			value="<tbl:write name="oneline" property="portBack"/>"></font></td>
	</tr>
	<tr>
		
		<td class="list_bg2" align="right">循环队列大小*</td>
		<td class="list_bg1" ><font size="2"><input name="txtLoopSize"
			type="text" class="text" maxlength="10" size="25"
			value="<tbl:write name="oneline" property="loopSize"/>"></font></td>
	       <td class="list_bg2" align="right">循环时间间隔(秒)*</td>
		<td class="list_bg1" ><input name="txtLoopInterval"
			type="text" class="text" maxlength="10" size="25"
			value="<tbl:write name="oneline" property="loopInterval"/>"></td>		
	</tr>
	<tr>
		
		<td class="list_bg2" align="right">自动重连次数*</td>
		<td class="list_bg1" ><font size="2"><input name="txtTryTime"
			type="text" class="text" maxlength="10" size="25"
			value="<tbl:write name="oneline" property="tryTime"/>"></font></td>
		<td class="list_bg2" align="right">登陆操作员*</td>
			<td class="list_bg1" align="left">
		<input type="hidden" name="txtOperatorID" size="25" value="<tbl:write name="oneline" property="operatorID"/>" >
		<input type="text" name="txtOperatorName" readonly size="24" value="<%=operatorName%>" >
		<input name="selOperButton" type="button" class="button" value="查询" 
		onClick="javascript:query_window('操作员查询','txtOperatorID','txtOperatorName','query_operator.do?showPerson=操作员')">
		
    </td>
	</tr>
	<tr>
		
		<td class="list_bg2" align="right">签名密钥</td>
		<td class="list_bg1" ><font size="2"><input name="txtMd5Key"
			type="text" class="text" maxlength="200" size="25"
			value="<tbl:write name="oneline" property="md5key"/>"></font></td>
		<td class="list_bg2" align="right">有效起始时间*</td>
		<td class="list_bg1" ><font size="2"><input onfocus="viewModule(this)" onKeydown="inputDate(document.frmPost.txtValidStartTime)" onblur="lostFocus(this)" name="txtValidStartTime"			type="text" class="text" maxlength="10" size="25"			value="<tbl:writedate name="oneline" property="validStartTime"/>"><IMG onclick="MyCalendar.SetDate(this,document.frmPost.txtValidStartTime,'Y')" src="img/calendar.gif" style=cursor:hand border="0"></font>
			</td>	
	</tr>
	<tr>
	        <td class="list_bg2" align="right">上次运行时间</td>
		<td class="list_bg1" ><font size="2"><input name="txtLastRunTime"
			type="text" readonly class="textgray" maxlength="200" size="25"
			value="<tbl:writedate name="oneline" property="lastRunTime" includeTime="true"/>"  > </font>
			</td>	
		
		<td class="list_bg2" align="right">有效结束时间*</td>
		<td class="list_bg1"><font size="2"><input onfocus="viewModule(this)" onKeydown="inputDate(document.frmPost.txtValidEndTime)" onblur="lostFocus(this)" name="txtValidEndTime"			type="text" class="text" maxlength="10" size="25"			value="<tbl:writedate name="oneline" property="validEndTime"/>"><IMG onclick="MyCalendar.SetDate(this,document.frmPost.txtValidEndTime,'Y')" src="img/calendar.gif" style=cursor:hand border="0"></font></td>
	</tr>
	<tr>
	        
		
		<td class="list_bg2" align="right">上次停止时间</td>
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
					value="返&nbsp;回" onclick="javascript:back_submit()"></td>
            <td><img src="img/button2_l.gif" width="11" height="20"></td>

            <td width="20" ></td>  

            <td><img src="img/button_l.gif" width="11" height="20"></td>
            <td><input name="Submit" type="button" class="button"
					value="保&nbsp;存" onclick="javascript:ca_host_modify()"></td>
            <td><img src="img/button_r.gif" width="22" height="20"></td>          
        </tr>
      </table>	
	    

</form>