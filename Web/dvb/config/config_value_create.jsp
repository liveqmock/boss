<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="osstags" prefix="d" %>
<%@ taglib uri="bookmark" prefix="bk" %>
 


<script language=javascript>
function check_frm()
{
	
	
    if (check_Blank(document.frmPost.txtCode, true, "取值代码"))
	    return false;
    
    if (check_Blank(document.frmPost.txtStatus, true, "状态"))
		return false;

	 
		
	if (check_Blank(document.frmPost.txtDescription, true, "取值描述"))
		return false;
	 
	 
     		
	 

	return true;
}
 function value_create_submit(){
  if (check_frm()){
	    document.frmPost.action="create_config_value.do";
	    document.frmPost.Action.value="create";
	    document.frmPost.submit();
	  }
}
 

</script>
<table  border="0" align="center" cellpadding="0" cellspacing="10" >
  <tr>
    <td><img src="img/list_tit.gif" width="19" height="19"></td>
    <td class="title">创建配置选项值</td>
  </tr>
</table>
<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>
<br>
 
<form name="frmPost" method="post" >   
 <tbl:generatetoken />  
      
 
 <table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
         <tr>
	   <td class="list_bg2">取值代码*</td>
	   <td class="list_bg1">
		<input type="text" name="txtCode" size="25" value="<tbl:writeparam name="txtCode" />" >
	  </td>
	  <td class="list_bg2">状态*</td>
	  <td class="list_bg1"> 
	   <d:selcmn name="txtStatus" mapName="SET_G_GENERALSTATUS" match="txtStatus" width="23" />
	  </td>
	</tr>
        <tr>   
		<td class="list_bg2">取值描述*</td>
		<td class="list_bg1" colspan="3"> 
		<input type="text" name="txtDescription" size="25" value="<tbl:writeparam name="txtDescription" />" >
		  
		</td>
	</tr>
  </table>
 
<input type="hidden" name="txtSettingID"  value="<tbl:writeparam name="txtvarSettingID" />">
<input type="hidden" name="func_flag" value="5066" >
<input type="hidden" name="Action" value="">
 <input type="hidden" name="confirm" value ="true"/>
<br>

     <table align="center" border="0" cellspacing="0" cellpadding="0">
        <tr>
           <td><img src="img/button_l.gif" border="0" ></td>
          <td background="img/button_bg.gif"><a href="javascript:value_create_submit()" class="btn12">确&nbsp; 认</a></td>
          <td><img src="img/button_r.gif" border="0" ></td>
          <td width="20" ></td>
        
	
        </tr>
      </table>   
      <table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
        <tr>
          <td><img src="img/mao.gif" width="1" height="1"></td>
        </tr>
      </table>
       <br>      
 
</form>
