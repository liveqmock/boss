<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="osstags" prefix="d" %>
<%@ page import="com.dtv.oss.dto.BillingCycleTypeDTO" %>
<%@ page import="com.dtv.oss.util.Postern" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.text.SimpleDateFormat"%>
<%
	boolean newAdded = false;
	String editingType = (String)request.getParameter("editing_type");
	String actionType = "account_cycle_type_update";
	if(editingType==null || (editingType = editingType.trim()).length() ==0){
		newAdded = true;
		actionType="account_cycle_type_new";
	}else{
		if(editingType.equals("new")){
			newAdded = true;
			actionType="account_cycle_type_new";
		}
	}
	String updateButtonLabel = "����";	
	String title = "������������";
	if(newAdded){
		title = "����"+title;
	}else{
		title = "�޸�"+title;
	}
	if(!newAdded){
		actionType = "account_type_update";
	}else{
		actionType = "account_type_new";
	}
	String backUrl = "account_cycle_type_brief.do";
%>
<script>
var newAction = <%= newAdded%>;

function checkInput(){
	//����
	if(check_Blank(document.frmPost.txtName,true,"����")){
		return false;
	}	
	//ʱ�䳤��txtCycleCount integer
	//��ѿ�ʼʱ��� RENTCYCLEBDATE	6		N	DATE	NULL 
	if(!check_TenDate(document.frmPost.txtRentCycleBDate,false,"��ѿ�ʼʱ���(����)")){
		return false;
	}
	//ʹ�÷��ÿ�ʼʱ��
	if(!check_TenDate(document.frmPost.txtOtherCycleBDate,true,"ʹ�÷��ÿ�ʼʱ��")){
		return false;
	}
	//�ʵ����ɽ�ֹ��txtEndInvoicingDate
	if(!check_TenDate(document.frmPost.txtEndInvoicingDate,true,"�ʵ����ɽ�ֹ��")){
		return false;
	}
	//�ʵ���������txtInvoiceDueDate
	if(!check_TenDate(document.frmPost.txtInvoiceDueDate,true,"�ʵ���������")){
		return false;
	}
	if(!check_Num(document.frmPost.txtCycleCount,true,"ʱ�䳤��")){
		return false;
	}
	return true;
}

function addObject(){
	document.frmPost.action_type.value = "account_cycle_type_new"; 
	document.frmPost.submit();
}

function updateObject(){
	document.frmPost.action_type.value = "account_cycle_type_update"; 
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
	document.frmPost.action_type.value = "account_cycle_type_delete"; 
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
			     	BillingCycleTypeDTO dto = (BillingCycleTypeDTO) pageContext.findAttribute("oneline");
			     	if(dto == null){
			     		dto = new BillingCycleTypeDTO();
			     	}
			     	pageContext.setAttribute("OnlyOneObject",dto);
			    	
			    	Map nameMap = Postern.getAllChargeCycleType();
			     	pageContext.setAttribute("BillingCycleTypeObjects",nameMap);
			     	
			     	nameMap =Postern.getAllAccountCycle();
			     	pageContext.setAttribute("AccountCycleObjects",nameMap);
			     	
			     	int id = dto.getId();
			     	 
				%>
				<table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg"  >
					<tr>
						<%if(newAdded){%>
						<td class="list_bg2" align="right" >����* </td>
						<td class="list_bg1" align="left" ><input type="text" name="txtName" value="<tbl:write name="OnlyOneObject" property="name" />" size="25"  class="text" /></td>
						<td class="list_bg2" align="right" ></td>
						<td class="list_bg1" align="left" ></td>
						<%}else{%>						
						<td class="list_bg2" align="right" >��� </td>
						<td class="list_bg1" align="left" ><input type="text" value="<tbl:write name="OnlyOneObject" property="id" />"  size="25"  class="text" disabled="true" /></td>
						<td class="list_bg2" align="right" >����* </td>
						<td class="list_bg1" align="left" ><input type="text" name="txtName" value="<tbl:write name="OnlyOneObject" property="name" />"  size="25"  class="text" /></td>
						<%}%>
					</tr>
					<tr>
						<td class="list_bg2" align="right" >���� </td>
						<td class="list_bg1" align="left" colspan=3 ><input type="text" size="81" name="txtDescription" value="<tbl:write name="OnlyOneObject" property="description" />" class="text" /></td>
					</tr>
					<tr>
						<td class="list_bg2" align="right" >״̬ </td>
						<td class="list_bg1" align="left" ><d:selcmn name="txtStatus" mapName="SET_G_GENERALSTATUS" match="OnlyOneObject:status" width="23" /></td>
						<td class="list_bg2" align="right" >��Ӧ�Ʒ��������� </td>
						<td class="list_bg1" align="left" ><tbl:select name="txtBillingCycleTypeId" set="BillingCycleTypeObjects" match="OnlyOneObject:billingCycleTypeId" width="23" /></td>
					</tr>
					<tr>
						<td class="list_bg2" align="right" >��һ���Ʒѵ�����</td>
						<td class="list_bg1" align="left" ><tbl:select name="txtFirstValidInvoiceCycleId" set="AccountCycleObjects" match="OnlyOneObject:firstValidInvoiceCycleId" width="23" /></td>						
						<td class="list_bg2" align="right" >ʱ�䳤��(��)</td>
						<td class="list_bg1" align="left" ><input type="text" name="txtCycleCount" value="<tbl:write name="OnlyOneObject" property="cycleCount" />"  size="25"  class="text" /></td>
					</tr>
					<%if(newAdded){%>
							<script>
								document.frmPost.txtFirstValidInvoiceCycleId.disabled=true;
							</script>
						<%}%>
					<tr>
						<td colspan="4" class="import_tit" align="center">��һ������ʱ�������</td>
					</tr>
					<tr>
						<td class="list_bg2" align="right" >��ѿ�ʼʱ���* </td>
						<td class="list_bg1" align="left" ><input onfocus="viewModule(this)" onKeydown="inputDate(document.frmPost.txtRentCycleBDate)" onblur="lostFocus(this)" type="text" name="txtRentCycleBDate" size="25" maxlength="10" value="<tbl:writedate name="oneline" property="rentCyclebDate"/>" class="text" >		     				<IMG onclick="MyCalendar.SetDate(this,document.frmPost.txtRentCycleBDate,'Y')" src="img/calendar.gif" style=cursor:hand border="0"></td>
						<td class="list_bg2" align="right" >ʹ�÷��ÿ�ʼʱ�� </td>
						<td class="list_bg1" align="left" ><input onfocus="viewModule(this)" onKeydown="inputDate(document.frmPost.txtOtherCycleBDate)" onblur="lostFocus(this)" type="text" name="txtOtherCycleBDate" size="25" maxlength="10" value="<tbl:writedate name="oneline" property="otherCycleBDate"/>" class="text" >		     				<IMG onclick="MyCalendar.SetDate(this,document.frmPost.txtOtherCycleBDate,'Y')" src="img/calendar.gif" style=cursor:hand border="0"></td>
					</tr>
					<tr>
						<td class="list_bg2" align="right" >�ʵ����ɽ�ֹ��</td>
						<td class="list_bg1" align="left" ><input onfocus="viewModule(this)" onKeydown="inputDate(document.frmPost.txtEndInvoicingDate)" onblur="lostFocus(this)" type="text" name="txtEndInvoicingDate" size="25" maxlength="10" value="<tbl:writedate name="oneline" property="endInvoicingDate"/>" class="text" >		     				<IMG onclick="MyCalendar.SetDate(this,document.frmPost.txtEndInvoicingDate,'Y')" src="img/calendar.gif" style=cursor:hand border="0"></td>
						<td class="list_bg2" align="right" >�ʵ��������� </td>
						<td class="list_bg1" align="left" ><input onfocus="viewModule(this)" onKeydown="inputDate(document.frmPost.txtInvoiceDueDate)" onblur="lostFocus(this)" type="text" name="txtInvoiceDueDate" size="25" maxlength="10" value="<tbl:writedate name="oneline" property="invoiceDueDate"/>" class="text" >		     				<IMG onclick="MyCalendar.SetDate(this,document.frmPost.txtInvoiceDueDate,'Y')" src="img/calendar.gif" style=cursor:hand border="0"></td>
					</tr>	
					<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
					<tr>
						<td><img src="img/mao.gif" width="1" height="1"></td>
				   	</tr>
				</table>
				<br>
					<tr>
						<td colspan=4 >
							<table align="center" border="0" cellspacing="0" cellpadding="0">
								<tr>
									<td><img src="img/button2_r.gif" border="0" width=22 height=20 ></td>
								    <td background="img/button_bg.gif"><a href="<%=backUrl %>"  class="btn12">��&nbsp;�� </a></td>
								    <td><img src="img/button2_l.gif" border="0" width=11 height=20 ></td>
								<% if(!newAdded){ %>		
									<!--<td><img src="img/button_l.gif" border="0" ></td>				
								    <td><img src="img/button_l.gif" border="0" ></td>
								    <td background="img/button_bg.gif"><a href="javascript:deleteObject()" class="btn12" >ɾ&nbsp;�� </a></td>
								    <td><img src="img/button_r.gif" border="0" ></td>	-->				    
								<% } %>
				 				    <td width="20" ></td>          
								    <td><img src="img/button_l.gif" border="0" ></td>
								    <td background="img/button_bg.gif"><a href="javascript:modifyObject()" class="btn12"><%=updateButtonLabel%></a></td>
								    <td><img src="img/button_r.gif" border="0" ></td>
								</tr>
							</table>
						</td>
					</tr>							
				</table>	
				 <input type="hidden" name="txtDtLastMod"   value="<tbl:write name="oneline" property="DtLastmod" />">			 
				  <input type="hidden" name="func_flag"  value="1012"/>
				<input type="hidden" name="action_type" value="<%=actionType%>"/>
				<input type="hidden" name="txtId" value="<tbl:write name="OnlyOneObject" property="id" />"/>				
				
				</lgc:bloop>  
			</form>
		</td>
	</tr>
</table>
									