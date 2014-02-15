<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl"%>
<%@ taglib uri="logic" prefix="lgc"%>
<%@ taglib uri="resultset" prefix="rs"%>
<%@ taglib uri="/WEB-INF/oss.tld" prefix="d"%>
<%@ taglib uri="bookmark" prefix="bk" %>

<%@ page import="com.dtv.oss.util.Postern,
                 com.dtv.oss.web.util.WebUtil,
                 com.dtv.oss.dto.BrconditionDTO,
                 java.util.*"%>
 

<SCRIPT language="JavaScript">
function check_frm ()
{
    if (check_Blank(document.frmPost.txtHostName, true, "主机名称"))
	    return false;
    if (check_Blank(document.frmPost.txtIpAddress, true, "IP地址"))
	    return false;
   if (check_Blank(document.frmPost.txtProtocolType, true, "接口协议类型"))
	    return false;
    if (check_Blank(document.frmPost.txtPort, true, "端口"))
	    return false;	
 	        
  if (check_Blank(document.frmPost.txtStatus, true, "状态"))
	    return false;
  if (check_Blank(document.frmPost.txtLoginID, true, "登录账号"))
	    return false;
 if (check_Blank(document.frmPost.txtLoginPWD, true, "登录密码"))
	    return false;
 if (check_Blank(document.frmPost.txtcmentrydir, true, "主目录路径"))
	    return false;
	    		    	    
	    
	return true;
}

function ldap_host_edit_submit(){
 if (window.confirm('您确认要修改主机信息吗?')){
  if (check_frm()){
	    document.frmPost.action="ldap_host_edit.do";
	    document.frmPost.Action.value="update";
	    document.frmPost.submit();
	  }
}
}
 
  
 

</SCRIPT>

<form name="frmPost" method="post">
	 
	
	<input type="hidden" name="Action"  value="">
	<input type="hidden" name="func_flag" value="225" >
	 
	<!-- 定义当前操作 -->
	 
    <table border="0" align="center" cellpadding="0" cellspacing="10">
     <tr>
    <td colspan="2" height="8"></td>
  </tr>
	<tr>
		<td><img src="img/list_tit.gif" width="19" height="19"></td>
		<td class="title">LDAP主机信息维护</td>
	</tr>
</table>
<table width="100%" border="0" align="center" cellpadding="0"
	cellspacing="0" background="img/line_01.gif">
	<tr>
		<td><img src="img/mao.gif" width="1" height="1"></td>
	</tr>
</table>
<br> 

<table width="100%" align="center" border="0" cellspacing="1" cellpadding="3">
 <lgc:bloop requestObjectName="ResponseQueryResult" item="oneline" IsOne="true"> 
	 
 
	<tr>
	          <td  class="list_bg2"><div align="right">主机ID</div></td>         
                  <td class="list_bg1" align="left">
                   <input type="text" name="txtHostID" size="25"  value="<tbl:write name="oneline" property="hostID" />" class="textgray" readonly >
                  </td>       
		 <td class="list_bg2"><div align="right">主机名称*</div></td>
                 <td class="list_bg1" align="left">
                   <input type="text" name="txtHostName" size="25"  value="<tbl:write name="oneline" property="hostName" />">
                  </td>       
                  
	</tr> 
	 <tr>
		<td  class="list_bg2"><div align="right">接口协议类型*</div></td>         
                <td class="list_bg1" align="left">
                  <input type="text" name="txtProtocolType" size="25"  value="<tbl:write name="oneline" property="protocolType" />">
                 
                </td> 
                 <td  class="list_bg2"><div align="right">IP地址*</div></td>         
                 <td class="list_bg1" align="left">
                 <input type="text" name="txtIpAddress" size="25"  value="<tbl:write name="oneline" property="ipAddress" />">
                 
                 </td>        
       </tr>
	 <input type="hidden" name="txtDtLastmod"   value="<tbl:write name="oneline" property="DtLastmod" />">
	 <tr>
		<td  class="list_bg2"><div align="right">端口*</div></td>         
                <td class="list_bg1" align="left">
                <input type="text" name="txtPort" size="25"  value="<tbl:write name="oneline" property="port" />">
                 
                </td> 
                <td  class="list_bg2"><div align="right">登录账号*</div></td>         
                <td class="list_bg1" align="left">
                <input type="text" name="txtLoginID" size="25"  value="<tbl:write name="oneline" property="loginID" />">
                 
                </td> 
       </tr>
       
	<tr>
	  <td class="list_bg2"><div align="right">登录密码*</div></td>
          <td class="list_bg1" align="left">
           <input type="text" name="txtLoginPWD" size="25"  value="<tbl:write name="oneline" property="loginPWD" />">
          <td class="list_bg2"><div align="right">主目录路径*</div></td>
          <td class="list_bg1" align="left">
          <input type="text" name="txtcmentrydir" size="25"  value="<tbl:write name="oneline" property="cmentrydir" />">
	</tr>
	  <tr>
		 <td  class="list_bg2"><div align="right">状态*</div></td>         
                 <td class="list_bg1" align="left" colspan="3">
                 <d:selcmn name="txtStatus" mapName="SET_G_GENERALSTATUS" match="oneline:status" width="23"/>
                 </td>        
	</tr>
	 
	  </lgc:bloop>  
</table>
<br>
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
            <td background="img/button_bg.gif"><a href="<bk:backurl property="ldap_host_query.do" />" class="btn12">返&nbsp;回</a></td>
            <td><img src="img/button2_l.gif" width="11" height="20"></td>
                      
            <td width="20" ></td>  
            <td><img src="img/button_l.gif" width="11px" height="20px"></td>
            <td><input name="Submit" type="button" class="button"
	    value="保&nbsp;存" onclick="javascript:ldap_host_edit_submit()"></td>
            <td><img src="img/button_r.gif" width="22px" height="20px"></td>    
             
                  
        </tr>
      </table>	

</form>
