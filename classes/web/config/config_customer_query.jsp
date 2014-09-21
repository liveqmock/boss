<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="/WEB-INF/oss.tld" prefix="d"%>
<%@ page import="com.dtv.oss.dto.CommonSettingDataDTO,
					com.dtv.oss.util.Postern" %>
 
<script>
function add(){ 
	 
		add_check();
	       
    
}
 
function add_check(){
   
     
    if(check_Blank(document.frmPost.key, true, "键")){
    	return;
    }
    if(document.frmPost.key.value.length>5){
    	alert("键的长度不能大于5!");
    	return;
    }
    if(check_Blank(document.frmPost.value, true, "值")){
    	return;
    }
    if(check_Blank(document.frmPost.status, true, "状态")){
    	return;
    }
     if(check_Blank(document.frmPost.operaterLevel, true, "可操作员级别")){
    	return;
    }
    if(check_Blank(document.frmPost.defaultOrNot, true, "是否默认值")){
    	return;
    }
	if(!check_Num(document.frmPost.priority,false,"排列顺序"))
		return false;
    document.frmPost.submit();
}


function delete_object(strID1,strID2){
    if( confirm("确定要删除该记录吗?") ){
        document.frmPost.action="config_customer_modify.do";
        document.frmPost.modify_type.value="modify_type_delete";
        document.frmPost.name.value=strID2;
        document.frmPost.key.value=strID1;
        document.frmPost.submit();
    }
	
}
function type_detail(strID1,strID2)
{
        document.frmPost.action="config_customer_modify_edit.do";
        document.frmPost.modify_type.value="modify_type_update";
        document.frmPost.name.value=strID2;
        document.frmPost.key.value=strID1;
        document.frmPost.submit();
        
} 
</script>

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

<table width="100%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
      <tr class="list_head">        
        <td class="list_head" width="10%">键</td>
        <td class="list_head" width="10%">值</td>
        <td class="list_head" width="15%">描述</td>
        <td class="list_head" width="10%">状态</td> 
        <td class="list_head" width="10%">是否默认值</td> 
        <td class="list_head" width="10%">排列顺序</td>
        <td class="list_head" width="20%">可操作员级别</td>   
        <td class="list_head" width="10%">操作</td> 
      </tr>
    <lgc:bloop requestObjectName="ResponseQueryResult" item="oneObject">
      	<%
          CommonSettingDataDTO dto =(CommonSettingDataDTO)pageContext.getAttribute("oneObject");
          
          String strDeleteUrl ="config_customer_modify.do?modify_type=modify_type_delete&name="+dto.getName()+"&key="+dto.getKey();
        %>
      <tbl:printcsstr style1="list_bg1" style2="list_bg2" overStyle="list_over" >
               <td><a href="javascript:type_detail('<tbl:write name="oneObject" property="key"/>','<tbl:write name="oneObject" property="name"/>')"
		class="link12"><tbl:write name="oneObject" property="key"/></a></td>
        	 
        	<td align="center" ><tbl:write name="oneObject" property="value" /></td>
        	<td align="center" ><tbl:write name="oneObject" property="description" /></td>
        	<td align="center" ><d:getcmnname typeName="SET_G_GENERALSTATUS" match="oneObject:status" /></td>        	
        	<td align="center" ><d:getcmnname typeName="SET_G_YESNOFLAG" match="oneObject:defaultOrNot" /></td>
        	<td align="center" >
        	<tbl:write name="oneObject" property="priority"  /></td>
        	<td align="center" ><d:getcmnname typeName="SET_S_OPERATORLEVEL" match="oneObject:grade" /></td>
        	<td align="center" ><a href="javascript:delete_object('<tbl:write name="oneObject" property="key"/>','<tbl:write name="oneObject" property="name"/>')"  class="link12">删除</a></td>
      </tbl:printcsstr>      
    </lgc:bloop>
    <tr>
    	<td colspan="8" class="list_foot"></td>
 	</tr>
</table>

<table width="100%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>

<form name="frmPost" method="post" action="config_customer_modify.do">
	<input type="hidden" name="modify_type" value="modify_type_new"/> 
	<input type="hidden" name="name" value="<%= (String)request.getAttribute("name")%>"/>
	 
	<table width="100%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
         <tr>
	   <td  class="list_bg2" align="right">键*</td> 
	   <td class="list_bg1">
		<input type="text" name="key" size="25" maxlength="5" value="" />
	  </td>
	  <td  class="list_bg2" align="right">值*</td>
	  <td class="list_bg1"> 
	   	<input type="text" name="value" maxlength="25" size="25" value="" />
	  </td>
	</tr>
        <tr>
           <td  class="list_bg2" align="right">描述</td>
	   <td class="list_bg1" > 
	      <input type="text" name="description" maxlength="100" size="25" value="" />
	  <td  class="list_bg2" align="right">状态*</td>
	  <td class="list_bg1" > 
	      <d:selcmn name="status"
			mapName="SET_G_GENERALSTATUS" match="txtDepotStatus" width="23" defaultValue=""/>
	  </td>
	</tr>
	 
      <tr>        
	  <td  class="list_bg2" align="right">是否默认值*</td>
	  <td class="list_bg1" > 
	      <d:selcmn name="defaultOrNot" mapName="SET_G_YESNOFLAG" match="defaultOrNot" width="23" defaultValue="N"/>
	  </td>
	 <td  class="list_bg2" align="right">排列顺序</td>
	  <td class="list_bg1" > 
	  <input type="text" name="priority" maxlength="10" size="25" value=""/></td>
	</tr>
	 <tr>        
	  <td  class="list_bg2" align="right">可操作员级别*</td>
	  <td class="list_bg1" colspan="3"> 
	      <d:selcmn name="operaterLevel" mapName="SET_S_OPERATORLEVEL" match="operaterLevel" width="23" defaultValue="0"/>
	  </td>
	  
	</tr>
	
	</table>
	
	<br>

	  <table align="center"  border="0" cellspacing="0" cellpadding="0">
         <tr>                              
	    <td><img src="img/button_l.gif" width="11" height="20"></td>
            <td><input name="Submit" type="button" class="button"
					value="新&nbsp;增" onclick="javascript:add()"></td>
            <td><img src="img/button_r.gif" width="22" height="20"></td>
        </tr>
      </table>	
</form>