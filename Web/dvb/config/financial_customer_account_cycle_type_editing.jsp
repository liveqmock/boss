<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="osstags" prefix="d" %>
<%@ page import="com.dtv.oss.dto.CustAcctCycleCfgDTO" %>
<%@ page import="com.dtv.oss.util.Postern" %>
<%@ page import="java.util.Map" %>
<%
	boolean newAdded = false;
	String editingType = (String)request.getParameter("editing_type");
	String actionType = "customer_account_cycle_update";
	if(editingType==null || (editingType = editingType.trim()).length() ==0){
		newAdded = true;
		actionType="customer_account_cycle_type_new";
	}else{
		if(editingType.equals("new")){
			newAdded = true;
			actionType="customer_account_cycle_type_new";
		}
	}
	String updateButtonLabel = "保存";	
	String title = "客户帐务周期";
	if(newAdded){
		title = "新增"+title;
	}else{
		title = "修改"+title;
	}
	String backUrl = "customer_account_cycle_type_brief.do";
%>
<script>
var newAction = <%= newAdded%>;

function chechInput(){
	if(check_Blank(document.frmPost.txtBillingCycleTypeId,true,"帐务周期类型")){
		return false;
	}
	
	return true;
}

function addObject(){
	document.frmPost.action_type.value = "customer_account_cycle_type_new"; 
	document.frmPost.submit();
}

function updateObject(){
	document.frmPost.action_type.value = "customer_account_cycle_type_update"; 
	document.frmPost.submit();
}

function modifyObject(){
	if(!chechInput()){
		return;
	}
	if(newAction){
		addObject();
	}else{
		updateObject();
	}
}

function deleteObject(){
	document.frmPost.action_type.value = "customer_account_cycle_type_delete"; 
	document.frmPost.submit();
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
			     	CustAcctCycleCfgDTO dto = (CustAcctCycleCfgDTO) pageContext.findAttribute("oneline");
			     	if(dto == null){
			     		dto = new CustAcctCycleCfgDTO();
			     	}
			     	pageContext.setAttribute("OnlyOneObject",dto);
			    	Map nameMap = null;
			     	nameMap=Postern.getAllAccountCycleType();
			     	pageContext.setAttribute("BillingCycleTypeObjects",nameMap);
			     	long dtLastmod = (dto.getDtLastmod()==null)? System.currentTimeMillis() : dto.getDtLastmod().getTime();
				%>
				<table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg" >
				<%
				   if(!newAdded){
				%>
				       <tr>
				               <td class="list_bg2"><div align="right">流水号</div></td>
		                                <td class="list_bg1"><font size="2"><input type="text" name="txtSeqNo" size="25"  value="<tbl:write name="OnlyOneObject" property="seqNo"/>" class="textgray" readonly ></font></td>
						<td class="list_bg2" align="right" >帐户类型</td>
						<td class="list_bg1" align="left" ><d:selcmn name="txtAccountType" mapName="SET_F_ACCOUNTTYPE" match="OnlyOneObject:accountType" width="23" /></td>
					</tr>
					<%} else {%>
					   <tr>
				               
						<td class="list_bg2" align="right" >帐户类型</td>
						<td class="list_bg1" colspan="3" align="left" ><d:selcmn name="txtAccountType" mapName="SET_F_ACCOUNTTYPE" match="OnlyOneObject:accountType" width="23" /></td>
					</tr>
					<%}%>
					<tr>
						<td class="list_bg2" align="right" >帐务周期类型*</td>
						<td class="list_bg1" align="left" ><tbl:select name="txtBillingCycleTypeId" set="BillingCycleTypeObjects" match="OnlyOneObject:billingCycleTypeId" width="23" /></td>
						<td class="list_bg2" align="right" >客户类型</td>
						<td class="list_bg1" align="left" ><d:selcmn name="txtCustomerType" mapName="SET_C_CUSTOMERTYPE" match="OnlyOneObject:customerType" width="23" /></td>
					</tr>
					
					<tr>
						<td colSpan="4"  class="list_bg1" align="left" >   											
							<table align="center" border="0" cellspacing="0" cellpadding="0">
								<tr>
									<td><img src="img/button2_r.gif" border="0" width=22 height=20 ></td>
								    <td background="img/button_bg.gif"><a href="<%=backUrl %>"  class="btn12">返&nbsp;回 </a></td>
								    <td><img src="img/button2_l.gif" border="0" width=11 height=20 ></td>
								<% if(!newAdded){ %>						
									<td width="20" ></td> 
								    <td><img src="img/button_l.gif" border="0" ></td>
								    <td background="img/button_bg.gif"><a href="javascript:deleteObject()" class="btn12" >删&nbsp;除 </a></td>
								    <td><img src="img/button_r.gif" border="0" ></td>
								 
								    <td width="20" ></td>          
								    <td><img src="img/button_l.gif" border="0" ></td>
								    <td background="img/button_bg.gif"><a href="javascript:modifyObject()" class="btn12"><%=updateButtonLabel%></a></td>
								    <td><img src="img/button_r.gif" border="0" ></td>
								<% }else { %>
								    
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
            <input type="hidden" name="txtDtLastMod"   value="<tbl:write name="oneline" property="DtLastmod" />">
			<input type="hidden" name="func_flag"  value="1013"/>	 
				<input type="hidden" name="action_type" value="<%=actionType%>"/>		
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
									