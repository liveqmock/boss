<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="osstags" prefix="d" %>
<%@ taglib uri="bookmark" prefix="bk" %>
<%@ page import="com.dtv.oss.dto.AcctItemTypeDTO" %>
<%@ page import="com.dtv.oss.util.Postern" %>
<%@ page import="java.util.Map" %>
<%
	boolean newAdded = false;
	String editingType = (String)request.getParameter("editing_type");
	String actionType = "account_type_update";
	if(editingType==null || (editingType = editingType.trim()).length() ==0){
		newAdded = true;
		actionType="account_type_new";
	}else{ 
		if(editingType.equals("new")){
			newAdded = true;
			actionType="account_type_new";
		}
	}
	String updateButtonLabel = "保存";	
	String title = "帐目类型";
	if(newAdded){
		title = "新增"+title;
	}else{
		title = "修改"+title;
	}
	
		AcctItemTypeDTO filterDto = (AcctItemTypeDTO)request.getAttribute("QueryFilter");
		if(filterDto==null){
			filterDto = new AcctItemTypeDTO();
		}
		String acctItemTypeId = filterDto.getAcctItemTypeID();
		String acctItemTypeName = filterDto.getAcctItemTypeName();
		String feeType = filterDto.getFeeType();
		String summaryAiFlag = filterDto.getSummaryAiFlag();
		String summaryTo = filterDto.getSummaryTo();
		String specialSetOffFlag = filterDto.getSpecialSetOffFlag();
		String showLevel = filterDto.getShowLevel();
		String status = filterDto.getStatus();
		if (acctItemTypeId == null) {
			acctItemTypeId = "";
		}
		if (acctItemTypeName == null) {
			acctItemTypeName = "";
		}
		if (feeType == null) {
			feeType = "";
		}
		if (summaryAiFlag == null) {
			summaryAiFlag = "";
		}
		if (summaryTo == null) {
			summaryTo = "";
		}
		if (specialSetOffFlag == null) {
			specialSetOffFlag = "";
		}
		if (showLevel == null) {
			showLevel = "";
		}
		if (status == null) {
			status = "";
		}
		String from = (String)request.getAttribute("txtFrom");
		String to = (String)request.getAttribute("txtTo");
	
%>
<script>
var newAction = <%= newAdded%>;
 
function checkInput(){
	if(check_Blank(document.frmPost.txtAcctItemTypeID,true,"帐目类型标识")){
		return false;
	}
	if(check_Blank(document.frmPost.txtAcctItemTypeName,true,"帐目类型名称")){
		return false;
	}
	if(check_Blank(document.frmPost.txtFeeType,true,"费用类型")){
		return false;
	}
	if(check_Blank(document.frmPost.txtSummaryAiFlag,true,"归类帐目标志")){
		return false;
	}
	if("N"==document.frmPost.txtSummaryAiFlag.value){
		if(check_Blank(document.frmPost.txtSummaryTo,true,"归类到()")){
			return false;
		}
	}
	return true;
}

function addObject(){
	document.frmPost.action_type.value = "account_type_new"; 
	document.frmPost.submit();
}

function updateObject(){
	document.frmPost.action_type.value = "account_type_update"; 
	document.frmPost.submit();
}

function modifyObject(){
	if(!checkInput()){
		return;
	}
	if(newAction){
		addObject();
	}else{
		updateObject();
	}
}

function deleteObject(){
	document.frmPost.action_type.value = "account_type_delete"; 
	document.frmPost.submit();
}

function changeSummaryToStatus(){
	var value=document.frmPost.txtSummaryAiFlag.value;
	if(value==null){
		value='N';
	}
	if(value=='N'){
		document.frmPost.txtSummaryTo.disabled =false;
	}else{
		document.frmPost.txtSummaryTo.disabled=true;
		document.frmPost.txtSummaryTo.value="";
	}
}
 
 function back(){
   if(newAction) 
    document.location.href= "account_type_brief_rusult.do?txtFrom=1&txtTo=10"; 
    
    else
     document.location.href= "<bk:backurl property='account_type_brief_rusult.do' />";
    
}
 

</script>

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
			<form name="frmPost" method="post" action="financial_setting_modify.do" > 
			<lgc:bloop requestObjectName="ResponseQueryResult" item="oneline" IsOne="true"> 
				<%
			     	AcctItemTypeDTO dto = (AcctItemTypeDTO) pageContext.findAttribute("oneline");
			     	if(dto == null){
			     		dto = new AcctItemTypeDTO();
			     	}
			     	String systemFlag = dto.getSystemFlag();
			     	pageContext.setAttribute("OnlyOneObject",dto);
			    	Map nameMap = null;
			     	nameMap=Postern.getAcctItemTypeForConfig();
			     	pageContext.setAttribute("AcctItemTypeForConfig",nameMap);
			      
				%>
				<table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg" >
					<tr>
						<td class="list_bg2" align="right" >帐目类型标识*</td>
						<%if(newAdded){%>
						<input type="hidden" name="func_flag"  value="1003"/>
						<td class="list_bg1" align="left" ><input type="text" size="25" name="txtAcctItemTypeID" value="<tbl:write name="OnlyOneObject" property="acctItemTypeID" />"/></td>
						<input type="hidden" name="txtSystemFlag" value="N"/>
						<%}else{%>
						<input type="hidden" name="func_flag"  value="1008"/> 
						<td class="list_bg1" align="left" ><input type="text" size="25" name="txtAcctItemTypeID" value="<tbl:write name="OnlyOneObject" property="acctItemTypeID" />" class="textgray" readonly /></td>
						<%}%>
						<td class="list_bg2" align="right" >帐目类型名称*</td>
						<td class="list_bg1" align="left" ><input type="text" size="25" name="txtAcctItemTypeName" value="<tbl:write name="OnlyOneObject" property="acctItemTypeName" />"/></td>
					</tr>
					<tr>
						<td class="list_bg2" align="right" >费用类型*</td>
						<td class="list_bg1" align="left" ><d:selcmn name="txtFeeType" mapName="SET_F_BRFEETYPE" match="OnlyOneObject:feeType" width="23" /></td>
						<td class="list_bg2" align="right" >是否上级归类帐目*</td>
						<td class="list_bg1" align="left" ><d:selcmn name="txtSummaryAiFlag" mapName="SET_G_YESNOFLAG" match="OnlyOneObject:summaryAiFlag" width="23" onchange="javascript:changeSummaryToStatus()" /></td>
					</tr>
					<tr>
						<td class="list_bg2" align="right" >特殊销帐标识</td>
						<td class="list_bg1" align="left" ><d:selcmn name="txtSpecialSetoffFlag" mapName="SET_G_YESNOFLAG" match="OnlyOneObject:specialSetOffFlag" width="23" /></td>
						<td class="list_bg2" align="right" >归类到</td>
						<td class="list_bg1" align="left" ><tbl:select name="txtSummaryTo" set="AcctItemTypeForConfig" match="OnlyOneObject:summaryTo" width="23" /></td>
					</tr>
					<tr>
						<td class="list_bg2" align="right" >显示级别 </td>
						<td class="list_bg1" align="left" ><d:selcmn name="txtShowLevel" mapName="SET_F_AITSHOWLEVEL" match="OnlyOneObject:showLevel" width="23" /></td>
						<td class="list_bg2" align="right" >状态 </td>
						<td class="list_bg1" align="left" ><d:selcmn name="txtStatus" mapName="SET_G_GENERALSTATUS" match="OnlyOneObject:status" width="23" /></td>
					</tr>	
					 
					<%if(!newAdded){%>
					 
					<tr>
						  <td class="list_bg2"><div align="right">系统内部帐目标志</div></td>
                                                  <td class="list_bg1" align="left" colspan="3">
                                                   <input type="text" name="txtSystemFlagName" size="25"  class="textgray" readonly value="<d:getcmnname typeName="SET_G_YESNOFLAG" match="oneline:systemFlag"/>" >
						  <input type="hidden" name="txtSystemFlag"   value="<tbl:write name="oneline" property="systemFlag" />">
					</tr>	
					<%}%>		
					<tr>
						<td class="list_bg1" colspan=4 >										
							<table align="center" border="0" cellspacing="0" cellpadding="0">
								<tr>
									<td><img src="img/button2_r.gif" border="0" width=22 height=20 ></td>
								    <td background="img/button_bg.gif"><a href="javascript:back()"  class="btn12">返&nbsp;回 </a></td>
								    <td><img src="img/button2_l.gif" border="0" width=11 height=20 ></td>
								<% if(!newAdded){ %>						
								    <!--<td><img src="img/button_l.gif" border="0" ></td>
								    <td background="img/button_bg.gif"><a href="javascript:deleteObject()" class="btn12" >删&nbsp;除 </a></td>
								    <td><img src="img/button_r.gif" border="0" ></td>-->					    
								<% } %>
								  <%
                                                                  if(!"Y".equals(systemFlag)){
          
                                                                    %>
								    <td width="20" ></td>          
								    <td><img src="img/button_l.gif" border="0" ></td>
								    <td background="img/button_bg.gif"><a href="javascript:modifyObject()" class="btn12"><%=updateButtonLabel%></a></td>
								    <td><img src="img/button_r.gif" border="0" ></td>
								    <%}%>
								</tr>
							</table>
						</td>
					</tr>		
				</table>
				<script>
					changeSummaryToStatus();
				</script>  
				<input type="hidden" name="txtDtLastMod" value="<tbl:write name="OnlyOneObject" property="dtLastmod"/>" />
				<input type="hidden" name="action_type" value="<%=actionType%>"/>
				<input type="hidden" name="txtAcctItemTypeID1" value=""/>
				<input type="hidden" name="query_type"  value="result"/>
				 
  				 
				</lgc:bloop>
				<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
					<tr>
						<td><img src="img/mao.gif" width="1" height="1"></td>
				   	</tr>
				</table>
			</form>
		</td>
	</tr>
</table>
									