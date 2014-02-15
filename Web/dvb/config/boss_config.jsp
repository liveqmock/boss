<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="resultset" prefix="rs" %>
<%@ taglib uri="osstags" prefix="d" %>
<%@ taglib uri="bookmark" prefix="bk" %>
<%@ page import="com.dtv.oss.util.Postern,
              com.dtv.oss.dto.BossConfigurationDTO, 
               java.util.*" %>
 

<script language=javascript>
 

 
 
  
function boss_config_edit_submit() {
  
           if (window.confirm('您确认要修改该系统配置吗?')){
	    document.frmPost.action="boss_config_edit.do";
	    document.frmPost.Action.value="update";
	    document.frmPost.submit();
	  
   
  }
  
}
function boss_config_create_submit() {
  
	    document.frmPost.action="boss_config_create.do";
	    document.frmPost.Action.value="create";
	    document.frmPost.submit();
	 
 }
 
 
</script>
  
 
 
 
 
<table width="820" align="center" cellpadding="0" cellspacing="0">
<tr><td>
<table  border="0" align="center" cellpadding="0" cellspacing="10">
  <tr>
    <td><img src="img/list_tit.gif" width="19" height="19"></td>
    <td  class="title">全局配置信息</td>
  </tr>
  </table>
  <table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>
  

 
<form name="frmPost" method="post" >
  <input type="hidden" name="func_flag" value="132" >
  <input type="hidden" name="Action" value=""/>
 <table width="810" align="center" border="0" cellspacing="1" cellpadding="3">
 
    <%
      
     BossConfigurationDTO dto=Postern.getBossConfigDto();
     
     if(dto!=null){
     
     pageContext.setAttribute("oneline",dto);
     
    
   %>
         <tr>  
              <td  class="list_bg2"><div align="right">软件名称</div></td>         
                  <td class="list_bg1" align="left">
                   <input type="text" name="txtSoftWareName" size="25"  value="<tbl:write name="oneline" property="softwareName" />"class="textgray" readonly >
                  </td>       
             
             <td class="list_bg2"><div align="right">软件版本号</div></td>
                <td class="list_bg1" align="left">
               <input type="text" name="txtSoftWareVersion" size="25"  value="<tbl:write name="oneline" property="softwarEversion" />" >
           
     	</td>
       </tr>  
        <input type="hidden" name="txtDtLastmod"   value="<tbl:write name="oneline" property="DtLastmod" />">
      <tr> 
          <td  class="list_bg2"><div align="right">数据库版本</div></td>         
          <td class="list_bg1" align="left">
           <input type="text" name="txtDatabaseVersion"   size="22"  value="<tbl:write name="oneline" property="databaseVersion" />" >
           </td>      
            <td class="list_bg2"><div align="right">开发商</div></td>
	    <td class="list_bg1" align="left">
	     <input type="text" name="txtDeveloper"   size="22"  value="<tbl:write name="oneline" property="developer" />" >
               
            </td>
      </tr>
       <tr> 
           
            <td class="list_bg2"><div align="right">系统名称</div></td>
	    <td class="list_bg1" align="left">
             <input type="text" name="txtMsoSystemName"   size="22"  value="<tbl:write name="oneline" property="msoSystemName" />" >
            </td>      
	    <td class="list_bg2"><div align="right">运营商名称</div></td>
	    <td class="list_bg1" align="left">
             <input type="text" name="txtMsoName"   size="22"  value="<tbl:write name="oneline" property="msoName" />" >
            </td>      
         </tr>
          <tr> 
           
            <td class="list_bg2"><div align="right">最大授权用户数目</div></td>
	    <td class="list_bg1" align="left">
             <input type="text" name="txtLicensedMaxUser"   size="22"  value="<tbl:write name="oneline" property="licensedMaxUser" />" >
            </td>      
	     <td class="list_bg2"><div align="right">许可证起始日期</div></td>
             <td class="list_bg1" align="left">
             <input type="text" name="txtLicenseValidFrom" size="25" maxlength="10" value="<tbl:write name="oneline" property="licenseValidFrom" />"> 
             </td>
         </tr>
          <tr> 
             <td class="list_bg2"><div align="right">许可证截止日期</div></td>
             <td class="list_bg1" align="left">
             <input type="text" name="txtLicenseValidTo" size="25" maxlength="10" value="<tbl:write name="oneline" property="licenseValidTo" />"> 
             </td>
              <td class="list_bg2"><div align="right">安装时间</div></td>
             <td class="list_bg1" align="left">
             <input onfocus="viewModule(this)" onKeydown="inputDate(document.frmPost.txtInstallTime)" onblur="lostFocus(this)" type="text" name="txtInstallTime" size="25" maxlength="10" value="<tbl:writedate name="oneline" property="installTime" />"    ><IMG onclick="MyCalendar.SetDate(this,document.frmPost.txtInstallTime,'Y')" src="img/calendar.gif" style=cursor:hand border="0">
             </td>
         </tr>
         <%} else {%>
          <tr>  
              <td  class="list_bg2"><div align="right">软件名称</div></td>         
                  <td class="list_bg1" align="left">
                   <input type="text" name="txtSoftWareName" size="25"  value="<tbl:writeparam name="txtSoftWareName" />" >
                  </td>       
             
             <td class="list_bg2"><div align="right">软件版本号</div></td>
                <td class="list_bg1" align="left">
               <input type="text" name="txtSoftWareVersion" size="25"  value="<tbl:writeparam name="txtSoftWareVersion" />" >
           
     	</td>
       </tr>  
         
      <tr> 
          <td  class="list_bg2"><div align="right">数据库版本</div></td>         
          <td class="list_bg1" align="left">
           <input type="text" name="txtDatabaseVersion"   size="22"  value="<tbl:writeparam name="txtDatabaseVersion" />" >
           </td>      
            <td class="list_bg2"><div align="right">开发商</div></td>
	    <td class="list_bg1" align="left">
	     <input type="text" name="txtDeveloper"   size="22"  value="<tbl:writeparam name="txtDeveloper" />" >
               
            </td>
      </tr>
       <tr> 
           
            <td class="list_bg2"><div align="right">系统名称</div></td>
	    <td class="list_bg1" align="left">
             <input type="text" name="txtMsoSystemName"   size="22" value="<tbl:writeparam name="txtMsoSystemName" />" >
            </td>      
	    <td class="list_bg2"><div align="right">运营商名称</div></td>
	    <td class="list_bg1" align="left">
             <input type="text" name="txtMsoName"   size="22"  value="<tbl:writeparam name="txtMsoName" />" >
            </td>      
         </tr>
          <tr> 
           
            <td class="list_bg2"><div align="right">最大授权用户数目</div></td>
	    <td class="list_bg1" align="left">
             <input type="text" name="txtLicensedMaxUser"   size="22"  value="<tbl:writeparam name="txtLicensedMaxUser" />" >
            </td>      
	    <td class="list_bg2"><div align="right">许可证起始日期</div></td>
             <td class="list_bg1" align="left">
            <input maxlength="10" onfocus="viewModule(this)" onKeydown="inputDate(document.frmPost.txtLicenseValidFrom)" onblur="lostFocus(this)" type="text" name="txtLicenseValidFrom" size="25" value="<tbl:writeparam name="txtLicenseValidFrom"/>" ><IMG onclick="MyCalendar.SetDate(this,document.frmPost.txtLicenseValidFrom,'Y')" src="img/calendar.gif" style=cursor:hand border="0">
            </td>
         </tr>
          
          <tr> 
             <td class="list_bg2"><div align="right">许可证截止日期</div></td>
             <td class="list_bg1" align="left">
             <input maxlength="10" onfocus="viewModule(this)" onKeydown="inputDate(document.frmPost.txtLicenseValidTo)" onblur="lostFocus(this)" type="text" name="txtLicenseValidTo" size="25"  value="<tbl:writeparam name="txtLicenseValidTo"/>" ><IMG onclick="MyCalendar.SetDate(this,document.frmPost.txtLicenseValidTo,'Y')" src="img/calendar.gif" style=cursor:hand border="0">
             </td>
              <td class="list_bg2"><div align="right">安装时间</div></td>
             <td class="list_bg1" align="left">
             <input maxlength="10" onfocus="viewModule(this)" onKeydown="inputDate(document.frmPost.txtInstallTime)" onblur="lostFocus(this)" type="text" name="txtInstallTime" size="25"  value="<tbl:writeparam name="txtInstallTime"/>" ><IMG onclick="MyCalendar.SetDate(this,document.frmPost.txtInstallTime,'Y')" src="img/calendar.gif" style=cursor:hand border="0">
             </td>
         </tr>
         <%}%>
          
         
     </table>
      <br>
        <table align="center"  border="0" cellspacing="0" cellpadding="0">
         <tr>  
            <%
             if(dto!=null){
            %>
            <td><img src="img/button_l.gif" width="11" height="20"></td>
            <td><input name="Submit" type="button" class="button"
					value="修&nbsp;改" onclick="javascript:boss_config_edit_submit()"></td>
            <td><img src="img/button_r.gif" width="22" height="20"></td> 
            <%} else {%>
             
            <td><img src="img/button_l.gif" width="11" height="20"></td>
            <td><input name="Submit" type="button" class="button"
					value="添&nbsp;加" onclick="javascript:boss_config_create_submit()"></td>
            <td><img src="img/button_r.gif" width="22" height="20"></td>  
            <%}%>             
        </tr>
      </table>	
      
     
     
  </td>
  </tr>
</form>  
</table>  
 