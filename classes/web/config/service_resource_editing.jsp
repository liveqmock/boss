<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="osstags" prefix="d" %>
<%@ page import="com.dtv.oss.dto.ServiceResourceDTO" %>
<%@ page import="java.util.Map" %>
<%   
	boolean newAdded = false;
	String editingType = (String)request.getAttribute("editing_type");
	String actionType = "resource_update";
	if(editingType==null || (editingType = editingType.trim()).length() ==0){
		newAdded = true;
		actionType="resource_new";
	}else{
		if(editingType.equals("new")){
			newAdded = true;
			actionType="resource_new";
		}
	}
	String updateButtonLabel = "确  认";	
	String backUrl = "config_service_resource_query.do";
	String page_title = "业务资源基本信息";
	if(newAdded){
		page_title = "新增"+page_title;
	}else{
		page_title = "修改"+page_title;
	}
	
%>
<script>
var newAction = <%= newAdded%>;

function checkInput(){	
	if(check_Blank(document.frmPost.txtResourceName,true,"资源名称")){
		return false;
	}
	return true;
}

function addObject(){
	if(checkInput){
		document.frmPost.modify_type.value = "resource_new"; 
		document.frmPost.submit();
	}
}

function updateObject(){
	document.frmPost.modify_type.value = "resource_update"; 
	document.frmPost.submit();
}

function modifyObject(){
	if(newAction){
		addObject();
	}else{
		updateObject();
	}
}

function deleteObject(){
	document.frmPost.modify_type.value = "resource_delete"; 
	document.frmPost.submit();
}

function enter_sub(resourceName){
	var resourceType = document.frmPost.txtResourceType.value;
	if("PHONE"==resourceType){
		document.frmPost.action="config_service_resource_detail_entrance.do?txtResourceName="+resourceName;
		document.frmPost.submit();
	}else{
		alert("只有在资源类型是“号码资源”时，才可以进入电话号码资源明细页面！");
	}
}

</script>

<table width="820" align="center" cellpadding="0" cellspacing="0">
	<tr>
		<td>
			<table  border="0" align="center" cellpadding="0" cellspacing="10" >
  				<tr>
    				<td><img src="img/list_tit.gif" width="19" height="19"></td>
    				<td class="title"><%=page_title%></td>
  				</tr>
			</table>
			<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
				<tr>
			    	<td><img src="img/mao.gif" width="1" height="1"></td>
			  	</tr>
			</table>
			<br>			 
			<form name="frmPost" method="post" action="config_service_resource_modify.do" > 
			<lgc:bloop requestObjectName="ResponseQueryResult" item="oneline" IsOne="true"> 
				<%
			     	ServiceResourceDTO dto = (ServiceResourceDTO) pageContext.findAttribute("oneline");
			     	if(dto == null){
			     		dto = new ServiceResourceDTO();
			     	}
			     	String resourceName = dto.getResourceName();
			     	if(resourceName==null){
			     		resourceName="";
			     	}
			     	String resourceType = dto.getResourceType();
			     	if(resourceType==null){
			     		resourceType = "";
			     	}
			     	pageContext.setAttribute("OnlyOneObject",dto);
			    	Map nameMap = null;
			     	long dtLastmod = (dto.getDtLastmod()==null)? System.currentTimeMillis() : dto.getDtLastmod().getTime();
				%>
				<table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg" >
					<tr>
						<td class="list_bg2" align="right" width="20%" >资源名称*</td>
						<%if(newAdded){%>
							<td class="list_bg1" align="left" width="30%"><input type="text" name="txtResourceName" value="<tbl:write name='OnlyOneObject' property='resourceName' />" size="25" /></td>
							<td class="list_bg2" align="right" width="20%">资源类型</td>
						<td class="list_bg1" align="left" width="30%"><d:selcmn name="txtResourceType" mapName="SET_R_RESOURCETYPE" match="OnlyOneObject:resourceType" width="23" /></td>
						<%}else{%>
							<td class="list_bg1" align="left" width="30%"><input type="text" value="<tbl:write name='OnlyOneObject' property='resourceName' />" disabled=true size="25" /></td>
							<td class="list_bg2" align="right" width="20%">资源类型</td>
							<td class="list_bg1" align="left" width="30%"><d:selcmn name="txtResourceType1" mapName="SET_R_RESOURCETYPE" match="OnlyOneObject:resourceType" width="23" /></td>
							<input type="hidden" name="txtResourceType" value="<tbl:write name='OnlyOneObject' property='resourceType' />" />
							<script>							
								document.frmPost.txtResourceType1.disabled=true;							
							</script>
						<%}%>						
					</tr>
					<tr>
						<td class="list_bg2" align="right" width="20%">状态</td>
						<td class="list_bg1" align="left" colspan=3><d:selcmn name="txtStatus" mapName="SET_G_GENERALSTATUS" match="OnlyOneObject:status" width="23" /></td>						
					</tr>	
					<tr>
						<td class="list_bg2" align="right" width="20%">资源描述</td>
						<td class="list_bg1" align="left" colspan="3" width="80%" ><input type="text" size=83 name="txtResourceDesc" value="<tbl:write name='OnlyOneObject' property='resourceDesc' />" /></td>
					</tr>				
				</table>
				<input type="hidden" name="txtDtLastMod" value="<%=dtLastmod%>"/>	
				<input type="hidden" name="modify_type" value="<%=actionType%>"/>	
				<%if(!newAdded){%>
				<input type="hidden" name="txtResourceName" value="<tbl:write name='OnlyOneObject' property='resourceName' />" />
				<%}%>
<table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
	<tr align="center">
	  <td class="list_bg1">											
				<table align="center" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td><img src="img/button2_r.gif" width="22" height="20"></td>
			    		<td background="img/button_bg.gif"><a href="<%=backUrl %>"  class="btn12">返&nbsp;回 </a></td>
		            	<td><img src="img/button2_l.gif" width="11" height="20"></td>
						<td width="20" ></td>						
						<td><img src="img/button_l.gif" border="0" ></td>
						<td background="img/button_bg.gif"><a href="javascript:modifyObject()" class="btn12"><%=updateButtonLabel%></a></td>
					    <td><img src="img/button_r.gif" border="0" ></td>	
					<% if(!newAdded && "PHONE".equals(resourceType)){ %>				    
					    <!--<td><img src="img/button_l.gif" border="0" ></td>
					    <td background="img/button_bg.gif"><a href="javascript:deleteObject()" class="btn12" >删&nbsp;除 </a></td>
					    <td><img src="img/button_r.gif" border="0" ></td>-->
					    <td width="20" ></td>          
					    <td><img src="img/button_l.gif" border="0" ></td>
					    <td background="img/button_bg.gif"><a href="javascript:enter_sub('<%=resourceName%>')" class="btn12" >电话号码资源明细信息</a></td>
					    <td><img src="img/button_r.gif" border="0" ></td>					    
					<% } %>					    
					</tr>
				</table>
</td></tr></table>	
<br>			
				<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
					<tr>
						<td><img src="img/mao.gif" width="1" height="1"></td>
				   	</tr>
				</table>
				</lgc:bloop> 
			</form>
		</td>
	</tr>
</table>
									