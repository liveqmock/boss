<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>

<%@ page import="com.dtv.oss.util.Postern"%>
<%@ page import="com.dtv.oss.dto.TerminalDeviceDTO" %>
<%@ page import="com.dtv.oss.web.util.WebUtil" %>
<%@ taglib uri="bookmark" prefix="bk" %>
<%@ taglib uri="resultset" prefix="rs" %>
<%@ taglib uri="logic" prefix="lgc"%>
<%@ taglib uri="osstags" prefix="d"%>
<script language=javascript>
function check_frm()
{
		if (check_Blank(document.frmPost.txtReleaseBatchID, true, "操作批号"))
		return false;
		
		return true;
	} 
	
function back_submit()
{

	document.frmPost.action='<bk:backurl property="dev_detail.do" />';
	document.frmPost.submit();
	
} 
 
function release_submit()
{
	 if(check_frm()&&confirm("确定要解除对该设备的预授权吗?")){          
	document.frmPost.action="release_device_for_one.do";
	document.frmPost.submit();
}
} 
</script>

<table border="0" cellspacing="0" cellpadding="0" width="820" >
<tr>
<td>
<table  border="0" align="center" cellpadding="0" cellspacing="10" >
  <tr>
    <td><img src="img/list_tit.gif" width="19" height="19"></td>
    <td class="title">解除预授权信息</td>
  </tr>
</table>
<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>
<br>
<form name="frmPost" method="post">
<%    
        String deviceId=request.getParameter("txtDeviceID");
       
	 
	
	 
%>
     

    <table align="center" width="98%" border="0" cellspacing="1" cellpadding="5" class="list_bg">
     <input type="hidden" name="txtDeviceId" size="20" value="<%=deviceId%>">  
     <input type="hidden" name="func_flag" size="20" value="1009">  
        
       
    
     
      <tr> 
       <td class="list_bg2" align="right">操作批号*</td>
       <td class="list_bg1" colspan="3">
        <input type="text" name="txtReleaseBatchID" size="25"  value="<tbl:writeparam name="txtReleaseBatchID" />" >
        </td>
    </tr>    
   
    </table>
    <input type="hidden" name="func_flag" size="22" value="1008">
    <table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
	<tr align="center">
	   <td class="list_bg1">
	      <table  border="0" cellspacing="0" cellpadding="0">
	         <tr>
			     <td><img src="img/button2_r.gif" border="0" ></td>
			        <td background="img/button_bg.gif"><a href="javascript:back_submit()" class="btn12">返&nbsp;回</a></td>
			       <td><img src="img/button2_l.gif" border="0" ></td>
			        <td width="20"></td>	         	
	         	
		     <td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
		     <td background="img/button_bg.gif"><a href="javascript:release_submit()" class="btn12">清除预授权</a></td>
		     <td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
		 </tr>
	      </table>
	   </td>
	</tr>
    </table>
</form>
</td>
</tr>
</table>