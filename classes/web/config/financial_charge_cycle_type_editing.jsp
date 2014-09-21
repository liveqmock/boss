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
		actionType="charge_cycle_type_new";
	}else{
		if(editingType.equals("new")){
			newAdded = true;
			actionType="charge_cycle_type_new";
		}
	}
	String updateButtonLabel = "����";	
	String title = "�Ʒ���������";
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
	String backUrl = "charge_cycle_type_brief.do";
%>
<script>
var newAction = <%= newAdded%>;
function checkInput(){
	//����
	if(check_Blank(document.frmPost.txtName,true,"����")){
		return false;
	}	
	//�Ƿ�����Ԥ�Ʒ�ALLOWPREBILLINGFLAG	8		N	VARCHAR2 (5)	NULL 
	if(check_Blank(document.frmPost.txtAllowPrebillingFlag,true,"�Ƿ�����Ԥ�Ʒ�")){
		return false;
	}
	//��ʼ�Ʒѱ�־STARTBILLINGFLAG	9		N	VARCHAR2 (5)	NULL 
	if(check_Blank(document.frmPost.txtStartBillingFlag,true,"��ʼ�Ʒѱ�־")){
		return false;
	}
	//������
	if(!check_TenDate(document.frmPost.txtRentDividingDate,true,"������")){
		return false;
	}
	//��ѼƷ���ʼ�� RENTCYCLEBDATE	6		N	DATE	NULL 
	if(!check_TenDate(document.frmPost.txtRentCycleBDate,false,"��ѼƷ���ʼ��")){
		return false;
	}
	//�����ѼƷ���ʼ��
	if(!check_TenDate(document.frmPost.txtOtherCycleBDate,false,"�����ѼƷ���ʼ��")){
		return false;
	}
	//ͳһ���ڱ�־UNIFIEDDISCTFLAG	14		N	VARCHAR2 (5)	NULL
	if(check_Blank(document.frmPost.txtUnifiedDisctFlag,true,"ͳһ���ڱ�־")){
		return false;
	}
	if(!check_Num(document.frmPost.txtCycleCount,true,"ʱ�䳤��")){
		return false;
	}
	if(check_Blank(document.frmPost.txtUnifiedCycleFlag,true,"ͳһ�����־")){
		return false;
	} 
	 
	return true;
}

function addObject(){
	document.frmPost.action_type.value = "charge_cycle_type_new";
	document.frmPost.submit();
}

function updateObject(){
	document.frmPost.action_type.value = "charge_cycle_type_update";
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
	document.frmPost.action_type.value = "charge_cycle_type_delete";
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
			     	
			    	Map nameMap =Postern.getAllChargeCycle();
			     	pageContext.setAttribute("BillingCycleObjects",nameMap);
			     				     	
			     	int id = dto.getId();
			     	long dtLastmod = (dto.getDtLastmod()==null)? System.currentTimeMillis() : dto.getDtLastmod().getTime();
			     	long dtCreate = (dto.getDtCreate()==null)? 0 : dto.getDtCreate().getTime();	
			     	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
					String rentCycleBDateStr = "";
			     	String otherCycleBDateStr = "";
			     	String rentDividingDateStr	= ""; 
			     	if(dto.getRentCyclebDate()!=null ){
			     		rentCycleBDateStr = format.format(dto.getRentCyclebDate());
			     	}
			     	if(dto.getOtherCycleBDate() !=null){
			     		otherCycleBDateStr = format.format(dto.getOtherCycleBDate());
			     	}
			     	if(dto.getRentDividingDate()!=null ){
			     		rentDividingDateStr = format.format(dto.getRentDividingDate());
			     	}
				%>
				<table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg" >
					<%if(newAdded){%>
					<tr>												
						<td class="list_bg2" align="right" >����*</td>
						<td class="list_bg1" align="left" ><input type="text" name="txtName" size="25" value="<tbl:write name="OnlyOneObject" property="name" />" width="23"  class="text" /></td>
						<td class="list_bg2" align="right" >״̬</td>
						<td class="list_bg1" align="left" ><d:selcmn name="txtStatus" mapName="SET_G_GENERALSTATUS" match="OnlyOneObject:status" width="23" /></td>
					</tr>
					<tr>
						<td class="list_bg2" align="right" >����</td>
						<td class="list_bg1" align="left" colspan=3 ><input type="text" size=85 name="txtDescription" value="<tbl:write name="OnlyOneObject" property="description" />" width="23"  class="text" /></td>
					<tr>
					<%}else{%>
					<tr>
						<td class="list_bg2" align="right" >���</td>
						<td class="list_bg1" align="left" ><input type="text" size="25" value="<tbl:write name="OnlyOneObject" property="id" />"  class="text" disabled="true" /></td>						
						<td class="list_bg2" align="right" >����*</td>
						<td class="list_bg1" align="left" ><input type="text" size="25" name="txtName" value="<tbl:write name="OnlyOneObject" property="name" />" width="23"  class="text" /></td>
					</tr>
					<tr>
						<td class="list_bg2" align="right" >����</td>
						<td class="list_bg1" align="left"><input type="text" size=25 name="txtDescription" value="<tbl:write name="OnlyOneObject" property="description" />" width="23"  class="text" /></td>
						<td class="list_bg2" align="right" >״̬</td>
						<td class="list_bg1" align="left" ><d:selcmn name="txtStatus" mapName="SET_G_GENERALSTATUS" match="OnlyOneObject:status" width="23" /></td>						
					</tr>
					<%}%>
					<tr>
						<td class="list_bg2" align="right" >�Ƿ�����Ԥ�Ʒ�*</td>
						<td class="list_bg1" align="left" ><d:selcmn name="txtAllowPrebillingFlag" mapName="SET_G_YESNOFLAG" match="OnlyOneObject:allowPrebillingFlag" width="23" /></td>
						<td class="list_bg2" align="right" >��ʼ�Ʒѱ�־*</td>
						<td class="list_bg1" align="left" ><d:selcmn name="txtStartBillingFlag" mapName="SET_G_YESNOFLAG" match="OnlyOneObject:startBillingFlag" width="23" /></td>
					</tr>
					<tr>
						<td class="list_bg2" align="right" >ͳһ�����־*</td>
						<td class="list_bg1" align="left" ><d:selcmn name="txtUnifiedCycleFlag" mapName="SET_G_YESNOFLAG" match="OnlyOneObject:unifiedCycleFlag" width="23" /></td>						
						<td class="list_bg2" align="right" >��һ���Ʒѵ�����</td>
						<td class="list_bg1" align="left" ><tbl:select name="txtFirstValidInvoiceCycleId" set="BillingCycleObjects" match="OnlyOneObject:firstValidInvoiceCycleId" width="23" /></td>						
						<%if(newAdded){%>
							<script>
								document.frmPost.txtFirstValidInvoiceCycleId.disabled=true;
							</script>
						<%}%>
					</tr>
					<tr>
						<td class="list_bg2" align="right" >����ģʽ</td>
						<td class="list_bg1" align="left" ><d:selcmn name="txtRentDisctMode" mapName="SET_F_RENTDISCTMODE" match="OnlyOneObject:rentDisctMode" width="23" /></td>
						<td class="list_bg2" align="right" >������</td>
						<td class="list_bg1" align="left" ><input onfocus="viewModule(this)" onKeydown="inputDate(document.frmPost.txtRentDividingDate)" onblur="lostFocus(this)" type="text" size="25" name="txtRentDividingDate" size="25" maxlength="10" value="<%=rentDividingDateStr %>" class="text" >	     					<IMG onclick="MyCalendar.SetDate(this,document.frmPost.txtRentDividingDate,'Y')" src="img/calendar.gif" style=cursor:hand border="0"></td>
					</tr>
					<tr>
						<td class="list_bg2" align="right" >ʱ�䳤��(��)</td>
						<td class="list_bg1" align="left" colspan=3 ><input type="text" size="25" name="txtCycleCount" size="25" maxlength="50" value="<tbl:write name="OnlyOneObject" property="cycleCount" />" class="text" ></td>
					</tr>
					<tr>
						<td colspan="4" class="import_tit" align="center">��һ������ʱ�������</td>
					</tr>
					<tr>
						<td class="list_bg2" align="right" >��ѼƷ���ʼ��*</td>
						<td class="list_bg1" align="left" ><input onfocus="viewModule(this)" onKeydown="inputDate(document.frmPost.txtRentCycleBDate)" onblur="lostFocus(this)" type="text" size="25" name="txtRentCycleBDate" size="25" maxlength="10" value="<%=rentCycleBDateStr %>" class="text" >	     					<IMG onclick="MyCalendar.SetDate(this,document.frmPost.txtRentCycleBDate,'Y')" src="img/calendar.gif" style=cursor:hand border="0"></td></td>
						<td class="list_bg2" align="right" >�����ѼƷ���ʼ��*</td>
						<td class="list_bg1" align="left" ><input onfocus="viewModule(this)" onKeydown="inputDate(document.frmPost.txtOtherCycleBDate)" onblur="lostFocus(this)" type="text" size="25" name="txtOtherCycleBDate" size="25" maxlength="10" value="<%=otherCycleBDateStr %>" class="text" >	     					<IMG onclick="MyCalendar.SetDate(this,document.frmPost.txtOtherCycleBDate,'Y')" src="img/calendar.gif" style=cursor:hand border="0"></td></td>
					</tr>
					<tr>
						<td class="list_bg2" align="right" >ͳһ���ڱ�־*</td>
						<td class="list_bg1" align="left" ><d:selcmn name="txtUnifiedDisctFlag" mapName="SET_G_YESNOFLAG" match="OnlyOneObject:unifiedDisctFlag" width="23" /></td>							
						<td class="list_bg2" align="right" ></td>
						<td class="list_bg1" align="left" ></td>
					</tr>						
					<tr>
						<td colspan=4 >
							<table width="100%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
								<tr>
							    	<td><img src="img/mao.gif" width="1" height="1"></td>
							  	</tr>
							</table>
						</td>
					</tr>						
				  <input type="hidden" name="txtDtLastMod"   value="<tbl:write name="oneline" property="DtLastmod" />">		
				 <input type="hidden" name="func_flag"  value="1011"/>
				<input type="hidden" name="action_type" value="<%=actionType%>"/>	
				<input type="hidden" name="txtId" value="<tbl:write name="OnlyOneObject" property="id" />"/>
				</lgc:bloop>   							
				<table align="center" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td><img src="img/button2_r.gif" border="0" width=22 height=20 ></td> 
					    <td background="img/button_bg.gif"><a href="<%=backUrl %>"  class="btn12">��&nbsp;�� </a></td>
					    <td><img src="img/button2_l.gif" border="0" width=11 height=20 ></td>
					<% if(!newAdded){ %>						
					    <!--<td width="20" ></td>          
					    <td><img src="img/button_l.gif" border="0" ></td>
					    <td background="img/button_bg.gif"><a href="javascript:deleteObject()" class="btn12" >ɾ&nbsp;�� </a></td>
					    <td><img src="img/button_r.gif" border="0" ></td>-->					    
					<% } %>
					    <td width="20" ></td>          
					    <td><img src="img/button_l.gif" border="0" ></td>
					    <td background="img/button_bg.gif"><a href="javascript:modifyObject()" class="btn12"><%=updateButtonLabel%></a></td>
					    <td><img src="img/button_r.gif" border="0" ></td>
					</tr>
				</table>
				 
			</form>
		</td>
	</tr>
</table>
									