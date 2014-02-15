<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="osstags" prefix="d" %>
<%@ taglib uri="bookmark" prefix="bk" %>
 
 

<script language=javascript>
function check_frm()
{
	
	if (check_Blank(document.frmPost.txtName, true, "参数名称"))
		return false;
		
	if (check_Blank(document.frmPost.txtValueType, true, "取值类型"))
		return false;
		
	if (check_Blank(document.frmPost.txtStatus, true, "状态"))
		return false;	
		
	if (check_Blank(document.frmPost.txtValue, true, "取值"))
		return false;
		
	if (check_Blank(document.frmPost.txtDescription, true, "描述"))
		return false;

	return true;
}

function goods_modify_submit(){
     if (check_frm()){
  if (window.confirm('您确认要修改该配置信息吗?')){
	    document.frmPost.action="system_setting_edit_done.do";
	   
	    document.frmPost.submit();
	  }
	 }
}
 

</script>
<table  border="0" align="center" cellpadding="0" cellspacing="10" >
  <tr>
    <td><img src="img/list_tit.gif" width="19" height="19"></td>
    <td class="title">系统全局配置信息修改</td>
  </tr>
</table>
<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>
<br>
 
<form name="frmPost" method="post" >   
  
      
<lgc:bloop requestObjectName="ResponseQueryResult" item="oneline" IsOne="true"> 
 
 <table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
         <tr>
	        <td class="list_bg2" align="right">参数名称*</td>
		 <td class="list_bg1">
			<input type="text" name="txtName" size="25"  value="<tbl:write name="oneline" property="name" />" class="textgray" readonly >
		 </td>
		<td class="list_bg2" align="right">取值类型*</td>
		 <td class="list_bg1">
		     <d:selcmn name="txtValueType" mapName="SET_G_PARAMETERTYPE" match="oneline:valueType" width="23"/>
		</td>
	</tr>
	  
	<tr>
		<td class="list_bg2" align="right">状态*</td>
		 <td class="list_bg1">
			 <d:selcmn name="txtStatus" mapName="SET_G_GENERALSTATUS" match="oneline:Status" width="23"  />
		 </td>
		
		<td class="list_bg2" align="right">取值*</td>
		 <td class="list_bg1">
		     <input type="text" name="txtValue"  maxlength="100" size="25"  value="<tbl:write name="oneline" property="value" />" >
		</td>
	</tr>
        
       <tr>   
		<td class="list_bg2" align="right">描述*</td>
		<td class="list_bg1" colspan="3"> 
		 <input type="text" name="txtDescription" size="83"  maxlength="100"  value="<tbl:write name="oneline" property="description" />" >
		</td>
	</tr>
  </table>

 
<input type="hidden" name="func_flag" value="255" >
 
   <table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
	  <tr align="center">
	  <td class="list_bg1"> 
     <table align="center" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td><img src="img/button2_r.gif" border="0" ></td>
          <td background="img/button_bg.gif"><a href="<bk:backurl property="system_setting_query.do" />" class="btn12">返&nbsp;回</a></td>
          <td><img src="img/button2_l.gif" border="0" ></td>
          <td width="20" ></td>         	
           <td><img src="img/button_l.gif" border="0" ></td>
          <td background="img/button_bg.gif"><a href="javascript:goods_modify_submit()" class="btn12">修&nbsp; 改</a></td>
          <td><img src="img/button_r.gif" border="0" ></td>
          <td width="20" ></td>
        
	
        </tr>
      </table>   
</td>
        </tr>
      </table>      
      <table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
        <tr>
          <td><img src="img/mao.gif" width="1" height="1"></td>
        </tr>
      </table>
       <br>      
</lgc:bloop>   
</form>
