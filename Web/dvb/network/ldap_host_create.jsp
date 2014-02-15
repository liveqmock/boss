<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="resultset" prefix="rs" %>
<%@ taglib uri="osstags" prefix="d" %>
<%@ taglib uri="bookmark" prefix="bk" %>
<%@ page import="com.dtv.oss.util.Postern,
     com.dtv.oss.dto.* "%>
 

<script language=javascript>
 

 
function check_frm()
{
	
	
    if (check_Blank(document.frmPost.txtHostName, true, "主机名称"))
	    return false;
    
    if (check_Blank(document.frmPost.txtStatus, true, "状态"))
		return false;

   if (check_Blank(document.frmPost.txtProtocolType, true, "接口协议类型"))
		return false;
   if (check_Blank(document.frmPost.txtIpAddress, true, "IP地址"))
		return false;
		
   if (check_Blank(document.frmPost.txtPort, true, "端口"))
		return false;	
 if (check_Blank(document.frmPost.txtLoginID, true, "登录账号"))
		return false;
if (check_Blank(document.frmPost.txtLoginPWD, true, "登录密码"))
		return false;
if (check_Blank(document.frmPost.txtcmentrydir, true, "主目录路径"))
		return false;
		
  

	return true;
} 
 
function ldap_host_create_submit() {
    if(check_frm()){
   document.frmPost.action="ldap_host_create.do";
   document.frmPost.Action.value="create";
   document.frmPost.submit();
}
}
function back_submit(){
	url="<bk:backurl property='ldap_host_query.do' />";
    document.location.href= url;

}

</script>
<br>
<table width="820" align="center" cellpadding="0" cellspacing="0">
<tr><td>
<table  border="0" align="center" cellpadding="0" cellspacing="10">
 <tr>
    <td colspan="2" height="8"></td>
  </tr>
  <tr>
    <td><img src="img/list_tit.gif" width="19" height="19"></td>
    <td  class="title">新增LDAP主机信息</td>
  </tr>
  </table>
  <table width="100%" border="0" align="center" cellpadding="0"
	cellspacing="0" background="img/line_01.gif">
	<tr>
		<td><img src="img/mao.gif" width="1" height="1"></td>
	</tr>
</table>

 
<form name="frmPost" method="post" >
<table width="100%" align="center" border="0" cellspacing="1" cellpadding="3">
	<input type="hidden" name="func_flag" value="225" > 
	<input type="hidden" name="Action" value="" > 
	<tr>
	        <td  class="list_bg2"><div align="right">主机名称*</div></td>         
                 <td class="list_bg1" align="left">
                 <input type="text" name="txtHostName"  size="22"  value="<tbl:writeparam name="txtHostName" />" >
                 </td>        
		 <td  class="list_bg2"><div align="right">接口协议类型*</div></td>         
                 <td class="list_bg1" align="left">
                  <input type="text" name="txtProtocolType"  size="22"  value="<tbl:writeparam name="txtProtocolType" />" >
                </td> 
                
       </tr>
	 
	<tr>
		 <td class="list_bg2"><div align="right">IP地址*</div></td>
                 <td class="list_bg1" align="left">
               <input type="text" name="txtIpAddress"  size="22"  value="<tbl:writeparam name="txtIpAddress" />" >
                  <td class="list_bg2"><div align="right">端口*</div></td>
                 <td class="list_bg1" align="left">
               <input type="text" name="txtPort"  size="22"  value="<tbl:writeparam name="txtPort" />" >
		 
	</tr>
       
	<tr>
	  <td class="list_bg2"><div align="right">登录账号*</div></td>
          <td class="list_bg1" align="left">
          <input type="text" name="txtLoginID"  size="22"  value="<tbl:writeparam name="txtLoginID" />" >
          <td class="list_bg2"><div align="right">登录密码*</div></td>
          <td class="list_bg1" align="left">
          <input type="text" name="txtLoginPWD"  size="22"  value="<tbl:writeparam name="txtLoginPWD" />" >
	</tr>
	<tr>
	  <td class="list_bg2"><div align="right">主目录路径*</div></td>
          <td class="list_bg1" align="left">
          <input type="text" name="txtcmentrydir"  size="22"  value="<tbl:writeparam name="txtcmentrydir" />" >
          <td class="list_bg2"><div align="right">状态*</div></td>
          <td class="list_bg1" align="left">
           <d:selcmn name="txtStatus" mapName="SET_G_GENERALSTATUS" match="txtStatus" width="23"/>
	</tr>
	 
	 
          
</table>
<table width="100%" border="0" align="center" cellpadding="0"
	cellspacing="0" background="img/line_01.gif">
	<tr>
		<td><img src="img/mao.gif" width="1" height="1"></td>
	</tr>
</table>
  
<br>
  <table align="center"  border="0" cellspacing="0" cellpadding="0">
         <tr>  
            <td><img src="img/button2_r.gif" width="22" height="20"></td>
			<td><input name="Submit" type="button" class="button"
					value="返&nbsp;回" onclick="javascript:back_submit()"></td>
            <td><img src="img/button2_l.gif" width="11" height="20"></td>

            <td width="20" ></td>  

            <td><img src="img/button_l.gif" width="11" height="20"></td>
            <td><input name="Submit" type="button" class="button"
					value="保&nbsp;存" onclick="javascript:ldap_host_create_submit()"></td>
            <td><img src="img/button_r.gif" width="22" height="20"></td>          
        </tr>
      </table>	


 
   
 
	 
    </td>
  </tr>
</form>  
</table>  
 
