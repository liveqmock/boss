<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="osstags" prefix="d" %>
<%@ page import="com.dtv.oss.dto.ResourcePhoneNoDTO" %>
<%@ page import="java.util.Map" %>
<%@ page import="com.dtv.oss.util.Postern" %>
<%
	boolean newAdded = false;
	String editingType = (String)request.getAttribute("editing_type");
	String actionType = "resource_detail_update";
	if(editingType==null || (editingType = editingType.trim()).length() ==0){
		newAdded = true;
		actionType="resource_detail_new";
	}else{
		if(editingType.equals("new")){
			newAdded = true;
			actionType="resource_detail_new";
		}
	}
	String updateButtonLabel = "保存";
	String backUrl = "config_resource_query.do";
	String resourceName = (String)request.getAttribute("txtResourceName");
	String resourceType = (String)request.getAttribute("txtResourceType");			     	
	if(resourceName==null){
		resourceName = "";
	}
	if(resourceType==null){
		resourceType = "";
	}
%>
<script>
var newAction = <%= newAdded%>;

function checkInput(){
	var phoneNo=document.frmPost.txtPhoneNo.value;
	if(check_Blank(document.frmPost.txtPhoneNo,true,"电话号码")){
		return false;
	}	
	if(document.frmPost.txtBatchAdded.checked){		
		if(check_Blank(document.frmPost.txtNumber,true,"数量") || !check_Num(document.frmPost.txtNumber,false,"数量")){
			return false;
		}else if("0"==document.frmPost.txtNumber.value){
			alert("数量必须大于等于1");
			return false;
		}
	}
	if (check_Blank(document.frmPost.txtStatus, true, "状态"))
		return false;
	return true;
}

function addObject(){
	if(checkInput()){
		document.frmPost.modify_type.value = "phone_no_new"; 
		document.frmPost.submit();
	}
}

function updateObject(){
	document.frmPost.modify_type.value = "phone_no_update"; 
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
	document.frmPost.modify_type.value = "phone_no_delete"; 
	document.frmPost.submit();
}
</script>

<table width="820" align="center" cellpadding="0" cellspacing="0">
	<tr>
		<td>
			<table  border="0" align="center" cellpadding="0" cellspacing="10" >
  				<tr>
    				<td><img src="img/list_tit.gif" width="19" height="19"></td>
    				<td class="title">新增电话号码资源</td>
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
			     	ResourcePhoneNoDTO dto = (ResourcePhoneNoDTO) request.getAttribute("oneline");
			     	if(dto == null){
			     		dto = new ResourcePhoneNoDTO();
			     	}
			     	pageContext.setAttribute("OnlyOneObject",dto);
			    	Map nameMap = null;
			     	long dtLastmod = (dto.getDtLastmod()==null)? System.currentTimeMillis() : dto.getDtLastmod().getTime();
			     	String resourceTypeName=Postern.getHashValueByNameKey("SET_R_RESOURCETYPE", resourceType);
				%>
				<table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg" >
					<tr>
						<td class="list_bg2" align="right" width="17%" >资源名称*</td>						
						<td class="list_bg1" align="left" width="33%" ><input type="text" value="<%=resourceName%>" size="25" disabled=true /></td>						
						<td class="list_bg2" align="right" width="17%" >资源类型</td>
						<td class="list_bg1" align="left" width="33%" ><input type="text" value="<%=resourceTypeName%>" size="25" disabled=true /></td>
					</tr>
					<tr>
						<td class="list_bg2" align="right" >国家代码</td>
						<td class="list_bg1" align="left" ><input type="text" name="txtCountryCode" value="<tbl:write name='OnlyOneObject' property='countryCode' />" size="25" /></td>						
						<td class="list_bg2" align="right" >地区代码</td>
						<td class="list_bg1" align="left" ><input type="text" name="txtAreaCode" value="<tbl:write name='OnlyOneObject' property='areaCode' />" size="25" /></td>						
					</tr>	
					<tr>
						<td class="list_bg2" align="right" >区域代码</td>
						<td class="list_bg1" align="left" >
			        <input type="hidden" name="txtCounty" value="<tbl:writeparam name="txtCounty" />">
			        <input type="text" name="txtCountyDesc" size="25" maxlength="50" readonly value="<tbl:WriteDistrictInfo property="txtCounty" />" class="text">
		          <input name="selDistButton" type="button" class="button" value="选择" onClick="javascript:sel_district('T','txtCounty','txtCountyDesc')">
						</td>						
						<td class="list_bg2" align="right" >状态*</td>
						<td class="list_bg1" align="left" ><d:selcmn name="txtStatus" mapName="SET_R_PHONENOSTATUS"  width="23" /></td>						
					</tr>	
					<tr>
						<td class="list_bg2" align="right" >号码级别</td>
						<td class="list_bg1" align="left" colspan="3" ><d:selcmn name="txtGrade" mapName="SET_R_PHONENOGRADE" match="txtGrade" width="23" /></td>
					</tr>
					<%if(newAdded){%>
					<tr>
						<td class="list_bg2" align="right" >批量增加</td>
						<td class="list_bg1" align="left" colspan="3"><input type="checkbox" name="txtBatchAdded" value="true" /></td>
					</tr>
					<%}%>
					<tr>
						<td class="list_bg2" align="right" >电话号码*</td>
						<td class="list_bg1" align="left" ><input type="text" name="txtPhoneNo" value="<tbl:write name='OnlyOneObject' property='phoneNo' />" size="25" /></td>						
						<%if(newAdded){%>
						<td class="list_bg2" align="right" >数量</td>
						<td class="list_bg1" align="left" ><input type="text" name="txtNumber" value="1" size="25" /></td>						
						<%}else{%>
						<td class="list_bg2" align="right" ></td>
						<td class="list_bg1" align="left" ></td>
						<%}%>
					</tr>
					<tr>
						<td class="list_bg2" align="right" >资源描述</td>
						<td class="list_bg1" align="left" colspan="3" width="80%" ><input type="text" size=83 name="txtComments" value="<tbl:write name='OnlyOneObject' property='comments' />"  /></td>
					</tr>
				</table>
				<input type="hidden" name="txtResourceName" value="<%=resourceName%>"/>
				<input type="hidden" name="txtDtLastMod" value="<%=dtLastmod%>"/>	
				<input type="hidden" name="modify_type" value="<%=actionType%>"/>
				<%if(!newAdded){%>
				<input type="hidden" name="txtItemID" value="<%=dto.getItemID()%>"/>
				<%}%>							  											
				<table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
				<tr align="center">
	  			<td class="list_bg1"><table  border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td><img src="img/button2_r.gif" width="22" height="20"></td>
						<td background="img/button_bg.gif"><a href="config_service_resource_detail_entrance.do?txtResourceName=<%=resourceName%>"  class="btn12">返&nbsp;回 </a></td>
		            	<td><img src="img/button2_l.gif" width="11" height="20"></td>
					<% if(!newAdded){ %>							    
					    <!--<td><img src="img/button_l.gif" border="0" ></td>
					    <td background="img/button_bg.gif"><a href="javascript:deleteObject()" class="btn12" >删&nbsp;除 </a></td>
					    <td><img src="img/button_r.gif" border="0" ></td>-->					    
					<% } %>
					    <td width="20" ></td>          
					    <td><img src="img/button_l.gif" border="0" ></td>					    
			    		<td background="img/button_bg.gif"><a href="javascript:modifyObject()" class="btn12"><%=updateButtonLabel%></a></td>
					    <td><img src="img/button_r.gif" border="0" ></td>
					</tr>
				</table></td>
				</tr>	
				</table>
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
									