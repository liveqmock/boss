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
					modify_check();
					return;
				}else{
					document.frmPost.defaultOrNot.value="N"
					document.all.defaultKey.value="";
					modify_check();
				}
			}else{
				document.all.defaultKey.value="";
				modify_check();
			}	
	}else{
		document.all.defaultKey.value="";
		modify_check();
	}  
}
function modify_check(){
	if(check_Blank(document.frmPost.key, true, "��")){
    	return;
    }
    if(document.frmPost.key.value.length>5){
    	alert("���ĳ��Ȳ��ܴ���5!");
    	return;
    }
     if(check_Blank(document.frmPost.operaterLevel, true, "�ɲ���Ա����")){
    	return;
    }
    if(check_Blank(document.frmPost.value, true, "ֵ")){
    	return;
    }
    if(check_Blank(document.frmPost.status, true, "״̬")){
    	return;
    }
    if(check_Blank(document.frmPost.defaultOrNot, true, "�Ƿ�Ĭ��ֵ")){
    	return;
    }
    if(!check_Num(document.frmPost.priority,false,"����˳��"))
		return false;
    document.frmPost.submit();   
}

function back(){
    document.location.href= "<%= (String)request.getAttribute("back_url") %>"
}

</SCRIPT>

<table  border="0" align="center" cellpadding="0" cellspacing="10">
  <tr>
    <td align="center"><img src="img/list_tit.gif" width="19" height="19"></td>
    <td class="title"><%= (String)request.getAttribute("title") %></td>
  </tr>
</table>
<table width="820"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>


<form name="frmPost" method="post" action="config_customer_modify.do">
	<input type="hidden" name="modify_type" value="modify_type_update"/> 
	<input type="hidden" name="name" value="<%= (String)request.getParameter("name")%>"/>	
	<input type="hidden" name="defaultKey" value="<tbl:writeparam name="defaultKey" />"/> 
	<input type="hidden" name="defaultName" value="<tbl:writeparam name="defaultName" />"/> 
	
    <rs:hasresultset>
	<lgc:bloop requestObjectName="ResponseQueryResult" item="oneObject">
	    <%CommonSettingDataDTO dto = (CommonSettingDataDTO) pageContext.getAttribute("oneObject"); 
	      long modifyTime = 0;
	      if(dto.getDtLastmod()!=null){
	      	modifyTime=dto.getDtLastmod().getTime();
	      }
	    %>
	<table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
         <tr>
	   <td  class="list_bg2" align="right">��*</td>
	   <td class="list_bg1">
		<input type="text" name="key" size="25" maxlength="5"  value="<tbl:write name="oneObject" property="key"  />"  class="textgray" readonly />
	  </td>
	  <td class="list_bg2" align="right">ֵ*</td>
	  <td class="list_bg1"> 
	   	<input type="text" name="value" size="25"  maxlength="25" value="<tbl:write name="oneObject" property="value" />" />
	  </td>
	</tr>
        <tr>
          <td class="list_bg2" align="right">����</td>
	  <td class="list_bg1" > 
	      <input type="text" name="description" size="25" maxlength="100" value="<tbl:write name="oneObject" property="description" />" />
	  <td class="list_bg2" align="right">״̬*</td>
	  <td class="list_bg1" > 
	      <d:selcmn name="status"
			mapName="SET_G_GENERALSTATUS" match="status" width="23" defaultValue="<%=dto.getStatus()%>"/>
	  </td>
	</tr>
	 <tr>        
	 <td class="list_bg2" align="right">�Ƿ�Ĭ��ֵ*</td>
	  <td class="list_bg1" > 
	      <d:selcmn name="defaultOrNot" mapName="SET_G_YESNOFLAG" match="oneObject:defaultOrNot" width="23" defaultValue=""/>
	  </td>
	  <td class="list_bg2" align="right">����˳��</td>
	  <td class="list_bg1" > 
	  <input type="text" name="priority" maxlength="10" size="25" value="<tbl:write name="oneObject" property="priority"  />" /></td>
	</tr>
	<tr>        
	  <td class="list_bg2" align="right">�ɲ���Ա����*</td>
	  <td class="list_bg1" colspan="3"> 
	      <d:selcmn name="operaterLevel" mapName="SET_S_OPERATORLEVEL" match="oneObject:grade" width="23"/>
	  </td>
	  
	</tr>
	</table>
	<input type="hidden" name="old_key" value="<tbl:write name="oneObject" property="key" />"/>
	<input type="hidden" name="dt_lastmod" value="<%=modifyTime%>"/>  
	</lgc:bloop>
</rs:hasresultset>
	<br>

	  <table align="center"  border="0" cellspacing="0" cellpadding="0">
         <tr>  
            <td><img src="img/button2_r.gif" width="22" height="20"></td>
	    <td><input name="Submit" type="button" class="button"
					value="��  ��" onclick="javascript:back()"></td>
            <td><img src="img/button2_l.gif" width="11" height="20"></td>
            <td width="20" > </td>
            <td><img src="img/button_l.gif" width="11" height="20"></td>
            <td><input name="Submit" type="button" class="button"
					value="ȷ  ��" onclick="javascript:modify()"></td>
            <td><img src="img/button_r.gif" width="22" height="20"></td>   
        </tr>
      </table>	
</form>