<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl"%>
<%@ taglib uri="logic" prefix="lgc"%>
<%@ taglib uri="resultset" prefix="rs"%>
<%@ taglib uri="/WEB-INF/oss.tld" prefix="d"%>
<%@ page import="com.dtv.oss.dto.CommonSettingDataDTO" %>

<SCRIPT language="JavaScript">
function modify()
{
   if(document.frmPost.defaultOrNot.value=="Y"){
			if(document.all.defaultKey.value!=""){
				if(confirm("�Ѿ�����Ĭ��ֵΪ��"+document.all.defaultName.value+"�����Ƿ����ԭ��Ĭ��ֵ��")){
					check_frm();
					return;
				}else{
					document.frmPost.defaultOrNot.value="N"
				}
			}else{
				document.all.defaultKey.value="";
				check_frm();
			}		
	}else{
		document.all.defaultKey.value="";
		check_frm();
	}  
}

function back_submit(){
    document.location.href= "menu_payment_type.do";
} 
function check_frm()   {
   
    if(check_Blank(document.frmPost.value, true, "ֵ")){
    	  return false;
    }
    if(check_Blank(document.frmPost.txtStatus, true, "״̬")){
    	  return false;
    }
        if(check_Blank(document.frmPost.defaultOrNot, true, "�Ƿ�Ĭ��ֵ")){
    	return;
    }
   if(!check_Num(document.frmPost.priority,false,"����˳��"))
		return false;
    document.frmPost.submit(); 
}


</SCRIPT>

<table  border="0" align="center" cellpadding="0" cellspacing="10">
  <tr>
    <td align="center"><img src="img/list_tit.gif" width="19" height="19"></td>
    <td class="title"><%= (String)request.getAttribute("title") %></td>
  </tr>
</table>
<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>


<form name="frmPost" method="post" action="config_payment_modify.do">
	<input type="hidden" name="Action" value="update"/> 
	<input type="hidden" name="func_flag" value="105"/> 
		<input type="hidden" name="defaultKey" value="<tbl:writeparam name="defaultKey" />"/> 
	<input type="hidden" name="defaultName" value="<tbl:writeparam name="defaultName" />"/> 
	<input type="hidden" name="name" value="SET_V_CSIPAYMENTSUBTYPE"/>	
	
    <rs:hasresultset>
	<lgc:bloop requestObjectName="ResponseQueryResult" item="oneObject">
	    <%CommonSettingDataDTO dto = (CommonSettingDataDTO) pageContext.getAttribute("oneObject"); %>
	<table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
         <tr>
	   <td class="list_bg2">��</td>
	   <td class="list_bg1">
		<input type="text" name="key"   size="25" value="<tbl:write name="oneObject" property="key" />"  class="textgray" readonly/>
	  </td>
	  <td class="list_bg2">ֵ*</td>
	  <td class="list_bg1"> 
	   	<input type="text" name="value" maxlength="50" size="25" value="<tbl:write name="oneObject" property="value" />" />
	  </td>
	</tr>
        
          <tr>        
	  <td class="list_bg2">�Ƿ�Ĭ��ֵ*</td>
	  <td class="list_bg1" > 
	      <d:selcmn name="defaultOrNot" mapName="SET_G_YESNOFLAG" match="<%=dto.getDefaultOrNot()%>" width="23" defaultValue=""/>
	  </td>
	  <td class="list_bg2">״̬*</td>
	  <td class="list_bg1" > 
	      <d:selcmn name="txtStatus"
			mapName="SET_G_GENERALSTATUS" match="txtStatus" width="23" defaultValue="<%=dto.getStatus()%>"/>
	  </td>
	  </tr>
	  <tr>
	  <td class="list_bg2">����˳��</td>
	  <td class="list_bg1" colspan="3" > 
	  <input type="text" name="priority" maxlength="10" size="25" value="<tbl:writenumber name="oneObject" property="priority" hideZero="true" />" /></td>
	</tr>
	<tr>
          <td class="list_bg2">����</td>
	  <td class="list_bg1" colspan="3"> 
	      <input type="text" name="description" size="80" maxlength="100" value="<tbl:write name="oneObject" property="description" />" />
	  
	</tr>
	</table>
<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>
	  <input type="hidden" name="txtDtLastmod"   value="<tbl:write name="oneObject" property="DtLastmod" />">
	
	</lgc:bloop>
</rs:hasresultset>
	<br>

	  <table align="center"  border="0" cellspacing="0" cellpadding="0">
         <tr>  
            <td><img src="img/button2_r.gif" width="22" height="20"></td>
			<td><input name="Submit" type="button" class="button"
					value="��&nbsp;��" onclick="javascript:back_submit()"></td>
            <td><img src="img/button2_l.gif" width="11" height="20"></td>
            <td width="20" ></td>  

            <td><img src="img/button_l.gif" width="11" height="20"></td>
            <td><input name="Submit" type="button" class="button"
					value="��&nbsp;��" onclick="javascript:modify()"></td>
            <td><img src="img/button_r.gif" width="22" height="20"></td>          
        </tr>
      </table>	
</form>