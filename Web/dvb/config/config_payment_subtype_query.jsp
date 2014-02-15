<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="/WEB-INF/oss.tld" prefix="d"%>
<%@ page import="com.dtv.oss.dto.CommonSettingDataDTO,
					com.dtv.oss.util.Postern" %>

<%
	CommonSettingDataDTO commonSettingDataDTO=
	Postern.getCommonSettingDataDTOByNameAndByStatus("SET_V_CSIPAYMENTSUBTYPE","V");
	String defaultKey="",defaultName="";
 
	if(commonSettingDataDTO!=null){
		defaultKey=commonSettingDataDTO.getKey();
		if(defaultKey==null||defaultKey.equals("null"))
			defaultKey="";
		defaultName=commonSettingDataDTO.getValue();
	}
%>
<script language=javascript>

function check_frm()   {
    if(document.frmPost.defaultOrNot.value=="Y"){
			if(document.all.defaultKey.value!=""){
				if(confirm("已经存在默认值为“"+document.all.defaultName.value+"”，是否更改原有默认值？")){
					check_frmCheck();
					return;
				}else{
					document.frmPost.defaultOrNot.value="N"
				}
			}else{
				document.all.defaultKey.value="";
				check_frmCheck();
			}		
	}else{
		document.all.defaultKey.value="";
		check_frmCheck();
	}  
}
function check_frmCheck(){
	if(check_Blank(document.frmPost.key, true, "键")){
      return false;
    }
    
    if(check_Blank(document.frmPost.value, true, "值")){
    	  return false;
    }
    if(check_Blank(document.frmPost.txtStatus, true, "状态")){
    	  return false;
    }
    if(check_Blank(document.frmPost.defaultOrNot, true, "是否默认值")){
    	return false;
    }
	if(!check_Num(document.frmPost.priority,false,"排列顺序"))
		return false;
    document.frmPost.action="payment_type_create.do";
   document.frmPost.Action.value="create";
   document.frmPost.submit();
}

 
function view_detail_click(strId,defaultKey,defaultName)
{
	self.location.href="payment_detail.do?txtKey1="+strId+"&defaultKey="+defaultKey+"&defaultName="+defaultName;;
}
function detail_delete(strId) {
     
    if (window.confirm('您确认要删除抵扣券类型吗?')){
    document.frmPost.txtKey.value=strId;
    document.frmPost.action="payment_delete_detail.do?Action=deleteDetail";
    document.frmPost.submit();
} 
}
 
function create_submit() {
    check_frm();
}

</script>
<table  border="0" align="center" cellpadding="0" cellspacing="10">
  <tr>
    <td align="center"><img src="img/list_tit.gif" width="19" height="19"></td>
    <td class="title"> 抵扣券类型列表 </td>
  </tr>
</table>
<table width="820"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>

<table width="100%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
      <tr class="list_head">        
        <td class="list_head" width="15%">键</td>
        <td class="list_head" width="15%">值</td>
        <td class="list_head" width="15%">描述</td>
        <td class="list_head" width="15%">状态</td> 
        <td class="list_head" width="15%">是否默认值</td> 
        <td class="list_head" width="15%">排列顺序</td> 
        <td class="list_head" width="10%">操作</td> 
      </tr>
    <lgc:bloop requestObjectName="ResponseQueryResult" item="oneObject">
      	<%
          CommonSettingDataDTO dto =(CommonSettingDataDTO)pageContext.getAttribute("oneObject");
      	  
           
        %>
       <tbl:printcsstr style1="list_bg1" style2="list_bg2" overStyle="list_over" >
                <td align="center"><a href="javascript:view_detail_click('<tbl:write name="oneObject" property="key"/>','<%=defaultKey%>','<%=defaultName%>')" class="link12" ><tbl:write name="oneObject" property="key" /></a></td>
        	 
        	<td align="center" ><tbl:write name="oneObject" property="value" /></td>
        	<td align="center" ><tbl:write name="oneObject" property="description" /></td>
        	<td align="center" ><d:getcmnname typeName="SET_G_GENERALSTATUS" match="oneObject:status" /></td>
        	<td align="center" ><d:getcmnname typeName="SET_G_YESNOFLAG" match="oneObject:defaultOrNot" /></td>
        	<td align="center" >
        	<tbl:writenumber name="oneObject" property="priority" hideZero="true" /></td>
        	<td align="center" >
        	 <A href="javascript:detail_delete('<tbl:write name="oneObject" property="key"/>')">删除 </a></td>
      </tbl:printcsstr>
    </lgc:bloop>
</table>

<table width="820"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>

<form name="frmPost" method="post" >
	<input type="hidden" name="Action" value=""/> 
	<input type="hidden" name="func_flag" value="105"/> 
	<input type="hidden" name="txtKey" value=""/> 
	<input type="hidden" name="name" value="SET_V_CSIPAYMENTSUBTYPE"/>
	<input type="hidden" name="defaultKey" value="<%=defaultKey%>"/> 
	<input type="hidden" name="defaultName" value="<%=defaultName%>"/> 
	 
	<table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
         <tr>
	   <td class="list_bg2">键*</td>
	   <td class="list_bg1">
		<input type="text" name="key" size="25"  maxlength="5"  value="" />
	  </td>
	  <td class="list_bg2">值*</td>
	  <td class="list_bg1"> 
	   	<input type="text" name="value" maxlength="50" size="25" value="" />
	  </td>
	</tr>
        <tr>
          <td class="list_bg2">描述</td>
	  <td class="list_bg1" > 
	      <input type="text" name="description" maxlength="100" size="25" value="" />
	  <td class="list_bg2">状态*</td>
	  <td class="list_bg1" > 
	      <d:selcmn name="txtStatus"
			mapName="SET_G_GENERALSTATUS" match="txtStatus" width="23" defaultValue=""/>
	  </td>
	   <tr>        
	  <td class="list_bg2">是否默认值*</td>
	  <td class="list_bg1" > 
	      <d:selcmn name="defaultOrNot" mapName="SET_G_YESNOFLAG" match="defaultOrNot" width="23" defaultValue="N"/>
	  </td>
	  <td class="list_bg2">排列顺序</td>
	  <td class="list_bg1" > 
	  <input type="text" name="priority" maxlength="100" size="25" value="" maxlength="3"/></td>
	</tr>
	</tr>
	</table>
	<br>
	 <table align="center" border="0" cellspacing="0" cellpadding="0">
        <tr>
           <td><img src="img/button_l.gif" border="0" ></td>
           <td background="img/button_bg.gif"><a href="javascript:create_submit()" class="btn12">新  增</a></td>
           <td><img src="img/button_r.gif" border="0" ></td>
            <td width="20" ></td>
             
           
      </table>   
      
</form>