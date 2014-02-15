<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="osstags" prefix="d" %>
<%@ taglib uri="bookmark" prefix="bk" %>
<%@ page import="com.dtv.oss.dto.VODInterfaceProductDTO" %>
<%@ page import="com.dtv.oss.util.Postern" %>
<%@ taglib uri="privilege" prefix="pri" %>
<%@ page import="java.util.Map" %>
<%
	Map allAcctItemType = Postern.getAllAcctItemType();	
	pageContext.setAttribute("AllAcctItemType",allAcctItemType);
	
	Map allProduct = Postern.getAllProductIDAndName();
	pageContext.setAttribute("AllProduct",allProduct);
	
	boolean newAdded = false;
	String editingType = (String)request.getParameter("editing_type");
	if(editingType==null || (editingType = editingType.trim()).length() ==0){
		newAdded = true;
	}else{
		if(editingType.equals("new")){
			newAdded = true;
		}
	}
	
	String title = null;
	if(!newAdded){
		title = "�޸�VOD��Ʒ��Ϣ";		
	}else{
		title = "����VOD��Ʒ��Ϣ";
	}	
%>
<script>
var newAction = <%= newAdded%>;

function checkInput(){   
	if(check_Blank(document.frmPost.txtSmsProductID,true,"SMS��Ʒ")){
		return false;
	}
	if(check_Blank(document.frmPost.txtBillingCodeList,true,"VOD��Ȩ����")){
		return false;
	}
	if(check_Blank(document.frmPost.txtNewSAFlag,true,"�Ƿ񴴽�ҵ���ʻ�")){
		return false;
	}
	if(check_Blank(document.frmPost.txtAcctItemTypeID,true,"������Ŀ����")){
		return false;
	}
	if(check_Blank(document.frmPost.txtStatus,true,"״̬")){
		return false;
	}
	return true;
}

function addObject(){
	document.frmPost.modify_type.value = "vod_product_new"; 
	document.frmPost.submit();
}

function updateObject(){
	document.frmPost.modify_type.value = "vod_product_update"; 
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
function back(){
	self.location.href="vod_interface_product_query.do";
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
			 			
				<table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg" >
					<tr>  
						<td class="list_bg2" align="right" >SMS��Ʒ*</td>
						<%if(newAdded){%>
						<td class="list_bg1" align="left" ><tbl:select name="txtSmsProductID" set="AllProduct" match="oneline:smsProductID" width="23" /></td>						
						<%}else{%>
						<td class="list_bg1" align="left" ><tbl:select name="xx" set="AllProduct" match="oneline:smsProductID" width="23" /></td>						
						<script>
							document.frmPost.xx.disabled=true;
						</script>
						<%}%>
						<td class="list_bg2" align="right" >״̬*</td>
						<td class="list_bg1" align="left" ><d:selcmn name="txtStatus" mapName="SET_G_GENERALSTATUS" match="oneline:status" width="23" /></td>
					</tr>
					<tr> 
						<td class="list_bg2" align="right" >�Ƿ񴴽�ҵ���ʻ�*</td>
						<td class="list_bg1" align="left" ><d:selcmn name="txtNewSAFlag" mapName="SET_G_YESNOFLAG" match="oneline:newsaflag" width="25" /> </td>
						<td class="list_bg2" align="right" >������Ŀ����*</td>
						<td class="list_bg1" align="left" ><d:selcmn name="txtAcctItemTypeID" set="AllAcctItemType" match="oneline:acctItemTypeID" width="23" /></td>
					</tr>
					<tr> 
					        <td class="list_bg2" align="right" >VOD��Ȩ����*</td>
						<td class="list_bg1" align="left" colspan="3"><input type="text" size="80" name="txtBillingCodeList" maxlength="200" value="<tbl:write name="oneline" property="billingCodeList" />"  /></td>
						
						 
					</tr>	
					<tr>
						<td colspan=4 class="list_bg1" >	
							<table align="center" border="0" cellspacing="0" cellpadding="0">
								<tr>
								    <td><img src="img/button2_r.gif" border="0" width=22 height=20 ></td>
								    <td background="img/button_bg.gif"><a href="javascript:back()"  class="btn12">��&nbsp;�� </a></td>
								    <td><img src="img/button2_l.gif" border="0" width=11 height=20 ></td>
								    <td width="20" ></td>  
								     <pri:authorized name="vod_interface_modify.do" >          
								    <td><img src="img/button_l.gif" border="0" ></td>
								    <td background="img/button_bg.gif"><a href="javascript:modifyObject()" class="btn12">��&nbsp��</a></td>
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
				<script>						
						
				</script>
				<%if(!newAdded){%>
					<input type="hidden" name="txtSmsProductID" value="<tbl:write name="oneline" property="smsProductID" />" />
				<%}%>				
				<input type="hidden" name="txtLastMod" value="<tbl:write name="oneline" property="dtLastMod" />"/>				
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
									