<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="osstags" prefix="d" %>
<%@ taglib uri="bookmark" prefix="bk" %>
<%@ taglib uri="privilege" prefix="pri" %>
<%@ page import="com.dtv.oss.dto.VODInterfaceHostDTO" %>
<%
	boolean newAdded = false;
	String editingType = (String)request.getAttribute("editing_type");	
	if(editingType==null || (editingType = editingType.trim()).length() ==0){
		newAdded = true;		
	}else{
		if(editingType.equals("new")){
			newAdded = true;
		}
	}
	
	String title = null;
	if(!newAdded){
		title = "修改VOD主机信息";		
	}else{
		title = "新增VOD主机信息";
	}	
%>
<script>
var newAction = <%= newAdded%>;

function checkInput(){
	 
	if(check_Blank(document.frmPost.txtHostName,true,"主机名")){
		return false;
	}
	if(check_Blank(document.frmPost.txtVodType,true,"系统类型")){
		return false;
	}
	 
	if(check_Blank(document.frmPost.txtStatus,true,"状态")){
		return false;
	}
	if(check_Blank(document.frmPost.txtIp,true,"主机IP")){
		return false;
	}
	if(check_Blank(document.frmPost.txtPort,true,"主机端口号")){
		return false;
	}
	if(check_Blank(document.frmPost.txtProtocolType,true,"协议类型")){
		return false;
	}
	if(document.frmPost.txtProtocolType.value.length > 20){
		alert("协议类型长度不能大于20!");
		return false;
	}
	if(!check_Num(document.frmPost.txtLoopSize,true,"事件处理队列大小")){
		return false;
	}
	if(!check_Num(document.frmPost.txtLoopInterval,true,"事件处理时间间隔")){
		return false;
	}
	if(!check_Num(document.frmPost.txtTryTime,true,"重连次数")){
		return false;
	}
	if(!check_TenDate(document.frmPost.txtValidStartTime,true,"有效运行起始时间")){
		return false;
	}
	if(!check_TenDate(document.frmPost.txtValidEndTime,true,"有效运行截止时间")){
		return false;
	}
	if(!check_Blank(document.frmPost.txtValidStartTime,false,"") && !check_Blank(document.frmPost.txtValidEndTime,false,"")){
		if(replaceStr(document.frmPost.txtValidStartTime.value,"0") > replaceStr(document.frmPost.txtValidEndTime.value,"0")){
			alert("有效运行截止时间不能小于有效运行起始时间！");
			return false;
     	} 
	}
	      
	return true;
}

function addObject(){
	document.frmPost.modify_type.value = "vod_host_new"; 
	document.frmPost.submit();
}

function updateObject(){
	document.frmPost.modify_type.value = "vod_host_update"; 
	document.frmPost.submit();
}

function saveModify(){
	if(!checkInput()){
		return;
	}
	if(newAction){
		addObject();
	}else{
		updateObject();
	}
}

function back(){
	self.location.href="vod_interface_host_query.do";
}

</script>
<br>
<table width="820" align="center" cellpadding="0" cellspacing="0">
	<tr>
		<td>
			<table  border="0" align="center" cellpadding="0" cellspacing="10" >
  				<tr>
    				<td><img src="img/list_tit.gif" width="19" height="19"></td>
    				<td class="title"><%=title%></td>
  				</tr>
			</table>
			<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
				<tr>
			    	<td><img src="img/mao.gif" width="1" height="1"></td>
			  	</tr>
			</table>
			<br>			 
			<form name="frmPost" method="post" action="vod_interface_modify.do" > 
			<lgc:bloop requestObjectName="ResponseQueryResult" item="oneline" IsOne="true"> 
				<%
			     	VODInterfaceHostDTO hostDTO = (VODInterfaceHostDTO)pageContext.getAttribute("oneline");
			     	long dt_lastmod = 0;
			     	if(hostDTO.getDtLastMod()!=null){
			     		dt_lastmod = hostDTO.getDtLastMod().getTime();
			     	}
			      
				%>
				<table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg" >
					<%if(newAdded){%>
					<tr>  
						<td class="list_bg2" align="right" >主机名称* </td>
						<td class="list_bg1" align="left" ><input type="text" size="25" name="txtHostName"  maxlength="25" value="<tbl:write name="oneline" property="hostName" />" /></td>
						<td class="list_bg2" align="right" >系统类型* </td>
						<td class="list_bg1" align="left" ><input type="text" size="25" name="txtVodType"  maxlength="10" value="<tbl:write name="oneline" property="vodType" />"/></td>
					</tr>
					<%}else{%>
						<tr>  
						<td class="list_bg2" align="right" >主机ID </td>
						<td class="list_bg1" align="left" ><input type="text" size="25" name="txtHostID" value="<tbl:write name="oneline" property="hostID" />"  class="textgray" readonly /></td>
						<td class="list_bg2" align="right" >主机名称* </td>
						<td class="list_bg1" align="left" ><input type="text" size="25" name="txtHostName"  maxlength="25" value="<tbl:write name="oneline" property="hostName" />"/></td>
					</tr>
					<tr>  
						<td class="list_bg2" align="right" >系统类型*</td>
						<td class="list_bg1" align="left" ><input type="text" size="25" name="txtVodType" maxlength="15" value="<tbl:write name="oneline" property="vodType" />"/></td>						
						<td class="list_bg2" align="right" >状态*</td>
						<td class="list_bg1" align="left" ><d:selcmn name="txtStatus" mapName="SET_G_GENERALSTATUS" match="oneline:status" width="23" /></td>
					</tr>
					<%}%>
					<tr>  
						<td class="list_bg2" align="right" >主IP地址*</td>
						<td class="list_bg1" align="left" ><input type="text" size="25" name="txtIp"  maxlength="20" value="<tbl:write name="oneline" property="ip" />" /></td>
						<td class="list_bg2" align="right" >主端口*</td>
						<td class="list_bg1" align="left" ><input type="text" size="25" name="txtPort" maxlength="20" value="<tbl:write name="oneline" property="port" />"  /></td>
					</tr>
					<tr> 
						<td class="list_bg2" align="right" >备份IP地址</td>
						<td class="list_bg1" align="left" ><input type="text" size="25" name="txtIpBack" maxlength="20" value="<tbl:write name="oneline" property="ipBack" />"  /></td>
						<td class="list_bg2" align="right" >备份端口</td>
						<td class="list_bg1" align="left" ><input type="text" size="25" name="txtPortBack" maxlength="20" value="<tbl:write name="oneline" property="portBack" />"  /></td>
					</tr>
					<tr> 
						<td class="list_bg2" align="right" >协议类型*</td>
						<td class="list_bg1" align="left" ><input type="text" size="25" name="txtProtocolType" maxlength="10" value="<tbl:write name="oneline" property="protocolType" />"  /></td>
						<td class="list_bg2" align="right" >事件处理队列大小</td>
						<td class="list_bg1" align="left" ><input type="text" size="25" name="txtLoopSize"  maxlength="10" value="<tbl:write  name="oneline" property="loopSize" />"  /></td>
					</tr>
					<tr> 
						<td class="list_bg2" align="right" >事件处理时间间隔 </td>
						<td class="list_bg1" align="left" ><input type="text" size="25" name="txtLoopInterval" maxlength="10" value="<tbl:write  name="oneline" property="loopInterval" />" width=23 /></td>
						<td class="list_bg2" align="right" >重连次数</td>
						<td class="list_bg1" align="left" ><input type="text" size="25" name="txtTryTime" maxlength="10" value="<tbl:write name="oneline" property="tryTime" />" /></td>
					</tr>
					<tr>  
						<td class="list_bg2" align="right" >有效运行起始时间 </td>
						<td class="list_bg1" align="left" ><input onfocus="viewModule(this)" onKeydown="inputDate(document.frmPost.txtValidStartTime)" onblur="lostFocus(this)" type="text" name="txtValidStartTime" size="25" maxlength="10" value="<tbl:writedate pattern="yyyy-MM-dd" name="oneline" property="validStartTime" />" class="text" >		     				<IMG onclick="MyCalendar.SetDate(this,document.frmPost.txtValidStartTime,'Y')" src="img/calendar.gif" style=cursor:hand border="0"></td>
						<td class="list_bg2" align="right" >有效运行截止时间 </td>
						<td class="list_bg1" align="left" ><input onfocus="viewModule(this)" onKeydown="inputDate(document.frmPost.txtValidEndTime)" onblur="lostFocus(this)" type="text" name="txtValidEndTime" size="25" maxlength="10" value="<tbl:writedate pattern="yyyy-MM-dd" name="oneline" property="validEndTime" />" class="text" >		     				<IMG onclick="MyCalendar.SetDate(this,document.frmPost.txtValidEndTime,'Y')" src="img/calendar.gif" style=cursor:hand border="0"></td>
					</tr>
					<%if(newAdded){%>
					<tr> 
						<td class="list_bg2" align="right" >状态* </td>
						<td class="list_bg1" align="left" ><d:selcmn name="txtStatus" mapName="SET_G_GENERALSTATUS" match="oneline:status" width="23" /></td>
						<td class="list_bg2" align="right" > </td>
						<td class="list_bg1" align="left" ></td>
					</tr>
					<%}else{%>
					<tr>   
						<td class="list_bg2" align="right" >上次运行时间 </td>
						<td class="list_bg1" align="left" ><input type="text" name="txtLastRunTime" size="25" maxlength="50" value="<tbl:writedate  includeTime="true" name="oneline" property="lastRunTime" />" class="textgray" readonly ></td>
						<td class="list_bg2" align="right" >上次停止时间 </td>
						<td class="list_bg1" align="left" ><input type="text" name="txtLastStopTime" size="25" maxlength="50" value="<tbl:writedate includeTime="true" name="oneline" property="lastStopTime" />" class="textgray" readonly ></td>
					</tr>
					<%}%>
					<tr>
						<td colspan=4 class="list_bg1" >
							<table align="center" border="0" cellspacing="0" cellpadding="0">
								<tr>
									<td><img src="img/button2_r.gif" border="0" width=22 height=20 ></td>
								    <td background="img/button_bg.gif"><a href="javascript:back()"  class="btn12">返&nbsp;回 </a></td>
								    <td><img src="img/button2_l.gif" border="0" width=11 height=20 ></td>
								    <td width="20" ></td>  
								     <pri:authorized name="vod_interface_modify.do" >        
								    <td><img src="img/button_l.gif" border="0" ></td>
								    <td background="img/button_bg.gif"><a href="javascript:saveModify()" class="btn12">保&nbsp存</a></td>
								    <td><img src="img/button_r.gif" border="0" ></td>
								      </pri:authorized>
								</tr>
							</table>
						</td>
					</tr>
					<tr>
						<td colspan=4 >&nbsp</td>
					</tr>								
				</table>
				<input type="hidden" name="txtLastMod" value="<tbl:write name="oneline" property="dtLastMod" />"/>
				<script>
					
				</script>								
				</lgc:bloop>
				<input type="hidden" name="modify_type" value=""/>
				<input type="hidden" name="txtFrom" value=""/>
				<input type="hidden" name="txtTo" value=""/>
					
				<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
					<tr>
						<td><img src="img/mao.gif" width="1" height="1"></td>
				   	</tr>
				</table>
			</form>
		</td>
	</tr>
</table>
									