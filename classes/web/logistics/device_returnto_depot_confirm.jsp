<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ page import="java.util.*"%>
<%@ page import="com.dtv.oss.util.Postern"%>
<%@ page import="com.dtv.oss.dto.TerminalDeviceDTO"%>
<%@ page import="com.dtv.oss.service.util.CommonConstDefinition"%>
<%@ page import=" com.dtv.oss.web.util.WebUtil"%>
  
<%@ page import="com.dtv.oss.service.commandresponse.CommandResponse" %>
 
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="osstags" prefix="d" %>
<%@ taglib uri="bookmark" prefix="bk" %>

<script language=javascript>
 
function repair_create()
{
     document.frmPost.action="device_return_confirm.do";
    document.frmPost.submit();    
}
function back_submit()
{
	document.frmPost.action="device_return.do" ;
	document.frmPost.submit();
}
  
</script>
 

<table  border="0" align="center" cellpadding="0" cellspacing="10" >
  <tr>
    <td><img src="img/list_tit.gif" width="19" height="19"></td>
    <td class="title"><font size="3">设备回库信息确认</font></td>
  </tr>
</table>
<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>
<br>
 <form name="frmPost" method="post" >    
  <table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">  
   <input type="hidden" name="confirm_post" value="true">
    <input type="hidden" name="func_flag" value="1012">
     
   
   <tr> 
     
    <td class="list_bg2" width="20%"><div align="right">操作批号</td>
    <td class="list_bg1">
        <input type="text" name="txtBatchNo1" size="25"  class="textgray" readonly value="<tbl:writeparam name="txtBatchNo" />" >
        </td>
       
    <%
     int depotId = WebUtil.StringToInt(request.getParameter("txtDepotID"));
     String depotName = Postern.getDepotNameByDepotID(depotId);
    %>
    <td class="list_bg2" width="20%"><div align="right">仓库</td>
    <td class="list_bg1">
        <input type="text" name="txtDepotName" size="25"  class="textgray" readonly value="<%=depotName%>" >
        </td>
        
    <tr>
      <td class="list_bg2"><div align="right">备注</div></td>
        <td class="list_bg1" colspan="3">
        <input type="text" name="txtComments" size="82.5" class="textgray" readonly value="<tbl:writeparam name="txtComments" />" >          
      </td>
     </tr> 
    </table>
     <table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
         <tr class="list_head">
     
          <td class="list_head" >设备ID</td>
          <td class="list_head">设备序列号</td>
          <td class="list_head">设备状态</td>          
          <td class="list_head">设备地址类型</td>
          <td class="list_head">设备地址</td>
          <td class="list_head">配对标志</td>
          <td class="list_head">配对设备ID</td>  
        </tr> 
   
 
<% 
   CommandResponse RepCmd = (CommandResponse)(request.getAttribute("ResponseFromEJBEvent"));
    int account=0;
    Map mapDepots = Postern.getAllDepot();
    if (RepCmd ==null) RepCmd =new CommandResponse(new ArrayList());
    ArrayList deviceList=(ArrayList)RepCmd.getPayload();
    account=deviceList.size();
    for(int i=0;i<deviceList.size();i++){
    
     TerminalDeviceDTO  terDevDto = Postern.getTerminalDeviceBySerialNo((String)deviceList.get(i));
     String status=terDevDto.getStatus();
     int deviceID=terDevDto.getDeviceID();
     String strAddressType = terDevDto.getAddressType();
     if (strAddressType==null) strAddressType="";
     String matchDeviceID="" + terDevDto.getMatchDeviceID();
     if(matchDeviceID==null || "0".equals(matchDeviceID) || "null".equals(matchDeviceID))
     	matchDeviceID="";
     	
     pageContext.setAttribute("oneline", terDevDto);
        String strAddress = null;
	
	if (strAddressType.equals("D"))  //仓库
	{
		strAddress = (String)mapDepots.get(String.valueOf(terDevDto.getAddressID()));
	}
	else if (strAddressType.equals("T"))  //组织机构
	{
		strAddress = Postern.getOrgNameByID(terDevDto.getAddressID());
	}
	else if (strAddressType.equals("B"))  //用户
	{
		strAddress = Postern.getCustomerNameById(terDevDto.getAddressID());
	}
	
	if (strAddress==null) strAddress="";
	 
	
%>
	 <tr class="list_bg1" onmouseover="this.className='list_over'" onmouseout="this.className='list_bg1'">
	     <td align="center"><tbl:write name="oneline" property="deviceID" /></td>
      	     <td align="center"><tbl:write name="oneline" property="serialNo" /></td>
      	     <td align="center"><d:getcmnname typeName="SET_D_DEVICESTATUS" match="oneline:status"/></td>
      	     <td align="center"><d:getcmnname typeName="SET_D_DEVICEADDRESSTYPE" match="oneline:AddressType"/></td>
      	     <td align="center"><%=strAddress%></td>   
      	     <td align="center"><d:getcmnname typeName="SET_G_YESNOFLAG" match="oneline:MatchFlag"/></td>
      	     <td align="center"><%=matchDeviceID %></td>
    	</tr>
    	 
 <%}%> 
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
		<td><img src="img/button2_r.gif" border="0"></td>
		<td background="img/button_bg.gif"><a href="javascript:back_submit()"
			class="btn12">上一步</a></td>
		<td><img src="img/button2_l.gif" border="0"></td>
		<td width="20"></td>           	
            <td><img src="img/button_l.gif" border="0" ></td>
            <td background="img/button_bg.gif"><a href="javascript:repair_create()" class="btn12">确认</a></td>
           <td><img src="img/button_r.gif" border="0" ></td>
      </tr>
   </table>
				</td>
			</tr>
		</table>      
   <tbl:hiddenparameters pass="txtBatchNo/txtStatusReason/txtDepotID/txtSerialNoCollection" />
   <tbl:generatetoken />
</form>  

     
 

 

  
 
 

     
      
      
      
      
      