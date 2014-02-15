<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ page import="java.util.*"%>
<%@ page import="com.dtv.oss.util.Postern"%>
<%@ page import="com.dtv.oss.dto.TerminalDeviceDTO"%>
<%@ page import="com.dtv.oss.service.util.CommonConstDefinition"%>
 
<%@ page import="com.dtv.oss.service.commandresponse.CommandResponse" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="osstags" prefix="d" %>

<script language=javascript>
 
 
function create()
{
     
    document.frmPost.submit();    
}
 
function back_submit()
{
	document.frmPost.action="match_and_preauth.do";
	document.frmPost.submit();
} 
</script>


<table  border="0" align="center" cellpadding="0" cellspacing="10" >
  <tr>
    <td><img src="img/list_tit.gif" width="19" height="19"></td>
    <td class="title">机卡配对信息确认</td>
  </tr>
</table>
<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>
<br>
 <form name="frmPost" action="match_and_preauth_confirm.do" method="post" >    
  <table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">  
   <input type="hidden" name="confirm_post" value="true">
    <input type="hidden" name="func_flag" value="1006">
   <tr> 
    <td class="list_bg2" align="left" width="30%">操作批号</td>
    <td class="list_bg1" width="60%" colspan="3">
        <input type="text" name="txtBatchID" size="20"  class="textgray" readonly value="<tbl:writeparam name="txtBatchID" />" >
        </td>
    </tr>    
    </table>
     <table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
         <tr class="list_head">
     
          <td class="list_head" >主设备ID</td>
          <td class="list_head">主设备序列号</td>
          <td class="list_head">主设备状态</td>          
          <td class="list_head">主设备型号</td>
          <td class="list_head">配对设备ID</td>
          <td class="list_head">配对设备序列号</td>
          <td class="list_head">配对设备状态</td>  
          <td class="list_head">配对设备型号</td>
        </tr> 
   
 
<% 
    CommandResponse RepCmd = (CommandResponse)(request.getAttribute("ResponseFromEJBEvent"));
    int account=0;
    if (RepCmd ==null) RepCmd =new CommandResponse(new ArrayList());
    ArrayList deviceList=(ArrayList)RepCmd.getPayload();
    account=deviceList.size();
    for(int i=0;i<deviceList.size();i++){
    
     HashMap  deviceMap = (HashMap) deviceList.get(i);
     Iterator itDevice = deviceMap.entrySet().iterator();
     while (itDevice.hasNext())
        {
            
            Map.Entry deviceMapSet = (Map.Entry)itDevice.next();
            TerminalDeviceDTO mainDto =(TerminalDeviceDTO) deviceMapSet.getKey();
            TerminalDeviceDTO subDto =(TerminalDeviceDTO) deviceMapSet.getValue();
            
            pageContext.setAttribute("oneline", mainDto);
            pageContext.setAttribute("subline", subDto);
            
        
%>
	 <tr class="list_bg1" onmouseover="this.className='list_over'" onmouseout="this.className='list_bg1'">
	     <td align="center"><tbl:write name="oneline" property="deviceID" /></td>
      	     <td align="center"><tbl:write name="oneline" property="serialNo" /></td>
      	      <td align="center"><d:getcmnname typeName="SET_D_DEVICESTATUS" match="oneline:status"/></td>
      	     <td align="center"><tbl:write name="oneline" property="deviceModel" /></td>
      	    
      	      <td align="center"><tbl:write name="subline" property="deviceID" /></td>
      	     <td align="center"><tbl:write name="subline" property="serialNo" /></td>
      	      <td align="center"><d:getcmnname typeName="SET_D_DEVICESTATUS" match="subline:status"/></td>
      	     <td align="center"><tbl:write name="subline" property="deviceModel" /></td>
      	     
    	</tr>
 <%}}%> 
 				<tr>
					<td colspan="8" class="list_foot"></td>
				</tr>		
     <tr>
      <td class="list_bg2" align="right" colspan="8">总共<%=account%>条记录</td>
    </tr>

    </table>
		<table width="98%" border="0" align="center" cellpadding="5"
			cellspacing="1" class="list_bg">
			<tr align="center">
				<td class="list_bg1">   
    <table border="0" cellspacing="0" cellpadding="0" align=center>
        <tr>
			     <td><img src="img/button2_r.gif" border="0" ></td>
			        <td background="img/button_bg.gif"><a href="javascript:back_submit()" class="btn12">返&nbsp;回</a></td>
			       <td><img src="img/button2_l.gif" border="0" ></td>
			        <td width="20"></td>

            <td><img src="img/button_l.gif" border="0" ></td>
            <td background="img/button_bg.gif"><a href="javascript:create()" class="btn12">确&nbsp;认</a></td>
           <td><img src="img/button_r.gif" border="0" ></td>
      </tr>
   </table>
				</td>
			</tr>
		</table>     
   <tbl:hiddenparameters pass="txtPreAuth/ProductList/txtSerialNoCollection/txtBatchID" />
   <tbl:generatetoken />
</form>  

     
 

 

  
 
 

     
      
      
      
      
      