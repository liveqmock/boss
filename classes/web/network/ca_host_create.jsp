<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl"%>
<%@ taglib uri="logic" prefix="lgc"%>
<%@ taglib uri="resultset" prefix="rs"%>
<%@ taglib uri="/WEB-INF/oss.tld" prefix="d"%>
<%@ taglib uri="bookmark" prefix="bk" %>

<%@ page import="com.dtv.oss.util.Postern,java.util.*"%>

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

	var isValidStartTime=!check_Blank(document.frmPost.txtValidStartTime, false,"")&&check_TenDate(document.frmPost.txtValidStartTime, true,"有效运行起始时间");
	var isValidEndTime=!check_Blank(document.frmPost.txtValidEndTime, false,"")&&check_TenDate(document.frmPost.txtValidEndTime, true,"有效运行截止时间");

	if(isValidStartTime&&isValidEndTime&&!compareDate(document.frmPost.txtValidStartTime,document.frmPost.txtValidEndTime,"截止时间必须大于等于起始时间")){
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
	<!-- 定义当前操作 -->
	<input type="hidden" name="txtActionType" size="20" value="CA_HOST_CREATE">
	<br>
	<br>
<table border="0" align="center" cellpadding="0" cellspacing="10">
	<tr>
		<td><img src="img/list_tit.gif" width="19" height="19"></td>
		<td class="title">新增CA主机</td>
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
		<td class="list_bg2" align="right">主机名称*</td>
		<td class="list_bg1"><input name="txtHostName"
			type="text" class="text" maxlength="200" size="25"
			value="<tbl:writeparam name="txtHostName" />"></td>
		<td class="list_bg2" align="right">状态*</td>
		<td class="list_bg1"><font size="2"><d:selcmn name="txtStatus"
			mapName="SET_G_GENERALSTATUS" match="txtStatus" width="23" defaultValue="V"/> </font></td>	
		
	</tr>
	<tr>
		
		<td class="list_bg2" align="right">描述</td>
		<td class="list_bg1" colspan="3"><input name="txtDesc"
			type="text" class="text" maxlength="200" size="83"
			value="<tbl:writeparam name="txtDesc" />"></td>
	</tr>
	<tr>
	     <td class="list_bg2" align="right">CA类型*</td>
		<td class="list_bg1"><font size="2"><input name="txtCaType"
			type="text" class="text" maxlength="200" size="25"
			value="<tbl:writeparam name="txtCaType" />"></font></td>
	    <td class="list_bg2" align="right">接口协议类型*</td>
		<td class="list_bg1" ><d:selcmn name="txtProtocolType"
			mapName="SET_N_CAHOSTPROTOCOL" match="txtProtocolType" width="23"/></td>
	</tr>
	<tr>				
		<td class="list_bg2" align="right">主IP地址*</td>
		<td class="list_bg1" ><input name="txtIP"
			type="text" class="text" maxlength="16" size="25"
			value="<tbl:writeparam name="txtIP" />"></td>
		<td class="list_bg2" align="right">备份IP地址*</td>
		<td class="list_bg1" ><input name="txtIPBack"
			type="text" class="text" maxlength="16" size="25"
			value="<tbl:writeparam name="txtIPBack" />"></td>	
		
	</tr>
	<tr>
		<td class="list_bg2" align="right">主端口号*</td>
		<td class="list_bg1" ><font size="2"><input name="txtPort"
			type="text" class="text" maxlength="200" size="25"
			value="<tbl:writeparam name="txtPort" />"></font></td>
		<td class="list_bg2" align="right">备份端口号*</td>
		<td class="list_bg1" ><font size="2"><input name="txtPortBack"
			type="text" class="text" maxlength="200" size="25"
			value="<tbl:writeparam name="txtPortBack" />"></font></td>
	</tr>
	<tr>
		
		<td class="list_bg2" align="right">循环队列大小*</td>
		<td class="list_bg1" ><font size="2"><input name="txtLoopSize"
			type="text" class="text" maxlength="200" size="25"
			value="<tbl:writeparam name="txtLoopSize" />"></font></td>
		<td class="list_bg2" align="right">循环时间间隔(秒)*</td>
		<td class="list_bg1" ><input name="txtLoopInterval"
			type="text" class="text" maxlength="200" size="25"
			value="<tbl:writeparam name="txtLoopInterval" />"></td>	
	</tr>
	<tr>
		
		<td class="list_bg2" align="right">自动重连次数*</td>
		<td class="list_bg1" ><font size="2"><input name="txtTryTime"
			type="text" class="text" maxlength="200" size="25"
			value="<tbl:writeparam name="txtTryTime" />"></font></td>
		<td class="list_bg2" align="right">登陆操作员*</td>
		<td class="list_bg1" align="left">
		<input type="hidden" name="txtOperatorID" size="25" value="<tbl:writeparam name="txtOperatorID"/>" >
		<input type="text" name="txtOperatorName" readonly size="24" value="<tbl:writeparam name="txtOperatorName"/>" >
		<input name="selOperButton" type="button" class="button" value="查询" 
		onClick="javascript:query_window('操作员查询','txtOperatorID','txtOperatorName','query_operator.do?showPerson=操作员')">
		
    </td>
	</tr>
	<tr>
	        <td class="list_bg2" align="right">签名密钥</td>
		<td class="list_bg1" ><font size="2"><input name="txtMd5Key"
			type="text" class="text" maxlength="200" size="25"
			value="<tbl:writeparam name="txtMd5Key" />"></font></td>
		<td class="list_bg2" align="right">有效起始时间*</td>
		<td class="list_bg1" ><font size="2"><input onfocus="viewModule(this)" onKeydown="inputDate(document.frmPost.txtValidStartTime)" onblur="lostFocus(this)" name="txtValidStartTime"			type="text" class="text" maxlength="10" size="25"			value="<tbl:writeparam name="txtValidStartTime" />"><IMG onclick="MyCalendar.SetDate(this,document.frmPost.txtValidStartTime,'Y')" src="img/calendar.gif" style=cursor:hand border="0"></font>
			</td>
	</tr>	
	<tr>	
		<td class="list_bg2" align="right">有效截止时间*</td>
		<td class="list_bg1" colspan="3"><font size="2"><input onfocus="viewModule(this)" onKeydown="inputDate(document.frmPost.txtValidEndTime)" onblur="lostFocus(this)" name="txtValidEndTime"			type="text" class="text" maxlength="10" size="25"			value="<tbl:writeparam name="txtValidEndTime" />"><IMG onclick="MyCalendar.SetDate(this,document.frmPost.txtValidEndTime,'Y')" src="img/calendar.gif" style=cursor:hand border="0"></font></td>
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
					value="保&nbsp;存" onclick="javascript:ca_host_create()"></td>
            <td><img src="img/button_r.gif" width="22" height="20"></td>          
        </tr>
      </table>	
	 
          
	   </td>
	</tr>
    </table>    

</form>