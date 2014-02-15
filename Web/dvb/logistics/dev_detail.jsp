<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>

<%@ page import="com.dtv.oss.util.Postern"%>
<%@ page import="com.dtv.oss.dto.TerminalDeviceDTO" %>
<%@ taglib uri="bookmark" prefix="bk" %>
<%@ taglib uri="resultset" prefix="rs" %>
<%@ taglib uri="logic" prefix="lgc"%>
<%@ taglib uri="osstags" prefix="d"%>
<script language=javascript>
function preauth_submit(SerialNo,DeviceID)
{
    //document.frmPost.action="preauth_device_for_one.screen";
	document.frmPost.action="device_auth.do?txtSCSerialNoCollection="+SerialNo+"&txtDeviceID="+DeviceID;
	document.frmPost.txtBatchID.value="";
	document.frmPost.submit();
}
function unmatch_submit()
{
	document.frmPost.action="unmatch_device_for_one.screen";
	document.frmPost.submit();
}
function dismatch_submit(SerialNo,DeviceID)
{
	//document.frmPost.action="unmatch_device_for_one.screen";
	document.frmPost.action="device_dismatch.do?txtSCSerialNoCollection="+SerialNo+"&txtDeviceID="+DeviceID;
	document.frmPost.txtBatchID.value="";
	document.frmPost.submit();
} 
function scdevice_release_submit(SerialNo,DeviceID)
{
    //document.frmPost.action="device_release_for_one.screen"; 
	document.frmPost.action="device_disauth.do?txtSCSerialNoCollection="+SerialNo+"&txtDeviceID="+DeviceID;
	document.frmPost.txtBatchID.value="";
	document.frmPost.submit();
} 
function device_info_modify()
{    
	document.frmPost.action="device_info_modify.do";
	document.frmPost.submit();   
}
function device_trans_query()
{    
  var txtSerialNoBegin = document.frmPost.txtSerialNo.value;
  self.location.href="device_trans_query.do?txtSerialNoBegin="+txtSerialNoBegin;  
}
function device_preauth_query()
{    
  var txtDeviceID = document.frmPost.txtDeviceID.value;
  self.location.href="device_preauth_history_query.do?txtDeviceID="+txtDeviceID;  
}
function device_prematch_query()
{    
  var txtDeviceID = document.frmPost.txtDeviceID.value;
  self.location.href="device_prematch_history_query.do?txtDeviceID="+txtDeviceID;  
}
function refresh_preauth_submit(SerialNo)
{
	document.frmPost.action="device_refresh_preauth.do?txtSCSerialNo="+SerialNo;
	document.frmPost.submit();
}
</script>

     
<table  border="0" align="center" cellpadding="0" cellspacing="10" >
  <tr>
    <td><img src="img/list_tit.gif" width="19" height="19"></td>
    <td class="title">设备信息明细</td>
  </tr>
</table>
<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>
<form name="frmPost" method="post" > 
<input type="hidden" name="func_flag" value="1015">
<%
   String strClass=null;
   String deviceClass=null;
   String status=null;
   String matchFlag=null;
   String preAuthorization = null;
  	//是否起用设备用途：
	String devicePurpose = Postern.getSystemsettingValueByName("SET_D_DEVICEPURPOSE");
	String strProductListNames = "";
 %>
<lgc:bloop requestObjectName="ResponseQueryResult" item="oneline" IsOne="true">
<%    
        Map mapDepots = Postern.getAllDepot();
        pageContext.setAttribute("AllDepots",mapDepots);

	TerminalDeviceDTO dto = (TerminalDeviceDTO)pageContext.getAttribute("oneline");
	strProductListNames = Postern.getOSSIAuthorizationProductNameByDeviceSerialno(dto.getSerialNo());
	deviceClass = dto.getDeviceClass();
	strClass = Postern.getDeviceClassDesc(dto.getDeviceClass());
	String batchNo=null; 
	  batchNo =  Postern.getBatchNoByBatchId(dto.getBatchID());
	if(batchNo==null)
	batchNo="";
	String strModel = Postern.getDeviceModelDesc(dto.getDeviceModel());
	String strAddressType = dto.getAddressType();
        if (strAddressType==null) strAddressType="";
	String strAddress = null;
	String strDistrictDesc = null;
	String strDepotName = null;
	status=dto.getStatus();
	matchFlag=dto.getMatchFlag();
	preAuthorization=dto.getPreAuthorization();
	System.out.println("*************dto.getAddressID()===="+dto.getAddressID());
	
	System.out.println("******================******"+preAuthorization);
	String strCustomerName = "";
	if (strAddressType.equals("D"))  //运入仓库
	{
		strAddress = (String)mapDepots.get(String.valueOf(dto.getAddressID()));
	}
	else if (strAddressType.equals("T"))  //组织机构
	{
		strAddress = Postern.getOrganizationDesc(dto.getAddressID());
	}
	else if (strAddressType.equals("B"))  //用户
	{		
		strAddress = Postern.getCustomerByID(dto.getAddressID()).getCustomerID()+"";
		strCustomerName = Postern.getCustomerNameById(dto.getAddressID());
		if(strCustomerName==null)strCustomerName="";
		strDistrictDesc = Postern.getDistrictDescByCustomerID(dto.getAddressID());
	}
	
	if (dto.getDepotID()!=0)  strDepotName = (String)mapDepots.get(String.valueOf(dto.getDepotID()));
	if(!strAddressType.equals("D"))strDepotName="";
	    
	if (strAddress==null) strAddress="";
	if (strDepotName==null) strDepotName="";
	
	String purposeStrList = dto.getPurposeStrList();
	
	String totalValue="";
	if(purposeStrList!=null)
   { 
       String[] purposeArray=purposeStrList.split(",");
       for(int j=0;j<purposeArray.length;j++){
       System.out.println("every purposeArray is "+ purposeArray[j]);
       String value = Postern.getHashValueByNameKey("SET_D_DEVICEUSEFORPURPOSE",purposeArray[j]);
       if(value != null && !"".equals(value))
       {
	       if(totalValue=="")
	        totalValue=value;
	       else 
	         totalValue=totalValue+","+value;
	       }
       }
      
    }
%>
<input type="hidden" name="txtDeviceDtLastmod"   value="<tbl:write name="oneline" property="dtLastmod" />" >
 <table width="100%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
      <tr>
        <td class="list_bg2"><div align="right">设备ID</div></td>
        <td class="list_bg1">
        <input type="text" name="txtDeviceID" size="25"  value="<tbl:write name="oneline" property="DeviceID" />" class="textgray" readonly ></td>
        <td class="list_bg2"><div align="right">序列号</div></td>
        <td class="list_bg1">
        <input type="text" name="txtSerialNo" size="25" maxlength="10" value="<tbl:write name="oneline" property="SerialNo" />" class="textgray" readonly >          
        </td>
      </tr>
       <tr> 
    	  <td class="list_bg2"><div align="right">设备类型</div></td>
          <td class="list_bg1">
          <input type="text" name="txtClass" size="25"  value="<%=strClass%>" class="textgray" readonly >
          <input type="hidden" name="txtDeviceClass" value="<tbl:write name="oneline" property="DeviceClass" />" >
          </td>
          <td class="list_bg2"><div align="right">设备型号</div></td>
          <td class="list_bg1">
          <input type="text" name="txtModel" size="25" maxlength="10" value="<tbl:write name="oneline" property="DeviceModel"/>" class="textgray" readonly >
          <input type="hidden" name="txtDeviceModel" value="<tbl:write name="oneline" property="DeviceModel" />" >
          </td>
      </tr>
      <tr>  
        <td class="list_bg2"><div align="right">运入批号</div></td>
        <td class="list_bg1">
        <input type="text" name="txtBatchID" size="25" value="<%=batchNo%>" class="textgray" readonly >
         </td>
         <td class="list_bg2"><div align="right">运入仓库</div></td>
         <td class="list_bg1">
        <input type="text" name="txtDepotShow" size="25" value="<%=strDepotName%>" class="textgray" readonly >
        <input type="hidden" name="txtDepotID" value="<tbl:write name="oneline" property="DepotID" />" >
         </td>
      </tr>
      <tr> 
        <td class="list_bg2"><div align="right">地址类型</div></td>
         <td class="list_bg1">
        <input type="text" name="txtZoneStationName" size="25"  value="<d:getcmnname typeName="SET_D_DEVICEADDRESSTYPE" match="oneline:AddressType"/>" class="textgray" readonly >
        <input type="hidden" name="txtZoneStation" value="<tbl:write name="oneline" property="AddressType" />" >
         </td>
         <% if (strAddressType.equals("B")){ %>
          <td class="list_bg2"><div align="right">客户证号</div></td>        
         <%}else{%>
        <td class="list_bg2"><div align="right">地址名称</div></td>
         <%}%>
        <td class="list_bg1">
        <input type="text" name="txtAddress" size="25" maxlength="10" value="<%=strAddress%>" class="textgray" readonly >
        <input type="hidden" name="txtAddressID" value="<tbl:write name="oneline" property="AddressID" />" >
        </td>       
      </tr>
      
      <% if (strAddressType.equals("B")){ %>
      <tr>
      <td class="list_bg2"><div align="right">所在区域</div></td>
        <td class="list_bg1">
        <input type="text" name="txtComments" size="25"  value="<%=strDistrictDesc%>" class="textgray" readonly >          
      </td>
      <td class="list_bg2"><div align="right">客户姓名</div></td>
        <td class="list_bg1">
        <input type="text" name="txtCustomerName" size="25"  value="<%=strCustomerName%>" class="textgray" readonly >          
      </td>
     </tr>
     <% } %>
       <tr> 
        <td class="list_bg2"><div align="right">租/售</div></td>
        <td class="list_bg1">
        <input type="text" name="txtLeaseBuyDes" size="25"  value="<d:getcmnname typeName="SET_D_DEVICESELLMETHOD" match="oneline:LeaseBuy"/>" class="textgray" readonly >          
        <input type="hidden" name="txtLeaseBuy" value="<tbl:write name="oneline" property="LeaseBuy" />" >
         </td>
         <td class="list_bg2"><div align="right">租售日期</div></td>
         <td class="list_bg1">
         <input type="text" name="txtDateFrom" size="25"  value="<tbl:writedate name="oneline" property="DateFrom" />" class="textgray" readonly >          
         </td>
      </tr>
     <tr>
        <td class="list_bg2"><div align="right">配对标记</div></td>
        <td class="list_bg1">
        <input type="text" name="txtMatchFlagDes" size="25"  value="<d:getcmnname typeName="SET_G_YESNOFLAG" match="oneline:MatchFlag"/>" class="textgray" readonly >          
        <input type="hidden" name="txtMatchFlag" value="<tbl:write name="oneline" property="MatchFlag" />" >
         </td>
        <td class="list_bg2"><div align="right">配对设备号</div></td>
         <td class="list_bg1">
        <input type="text" name="txtMatchDeviceID" size="25"  value="<tbl:write name="oneline" property="MatchDeviceID" />" class="textgray" readonly >
         </td>
      </tr>
      <tr>
        <td class="list_bg2"><div align="right">置位标记</div></td>
         <td class="list_bg1">
         <input type="text" name="txtCaSetFlagDes" size="25"  value="<d:getcmnname typeName="SET_G_YESNOFLAG" match="oneline:CaSetFlag"/>" class="textgray" readonly >          
         <input type="hidden" name="txtCaSetFlag" value="<tbl:write name="oneline" property="CaSetFlag" />" >
         </td>
         <td class="list_bg2"><div align="right">置位日期</div></td>
         <td class="list_bg1">
         <input type="text" name="txtCaSetDate" size="25"  value="<tbl:writedate name="oneline" property="CaSetDate" />" class="textgray" readonly >
         </td>
      </tr>
       <tr>
          <td class="list_bg2"><div align="right">保修截止日期</div></td>
          <td class="list_bg1">
        <input type="text" name="txtDateTo" size="25"  value="<tbl:writedate name="oneline" property="DateTo" />" class="textgray" readonly >
         </td>
         <td class="list_bg2"><div align="right">保修期的长度(单位:月)</div></td>
         <td class="list_bg1">
        <input type="text" name="txtGuaranteeLength" size="25"  value="<tbl:write name="oneline" property="GuaranteeLength" />" class="textgray" readonly >
         </td>
      </tr>
      
      <tr>
        <td class="list_bg2"><div align="right">CM MAC地址</div></td>
         <td class="list_bg1">
         <input type="text" name="txtMacAddress" size="25"  value="<tbl:write name="oneline" property="mACAddress" />" class="textgray" readonly >
         </td>
         <td class="list_bg2"><div align="right">终端MAC地址</div></td>
         <td class="list_bg1">
         <input type="text" name="txtInterMacAddress" size="25"  value="<tbl:write name="oneline" property="interMACAddress" />" class="textgray" readonly >
         </td>
      </tr>
      
    <tr>
         <td class="list_bg2"><div align="right">是否二手</div></td>
         <td class="list_bg1">
        <input type="text" name="txtUseFlagDes" size="25" value="<d:getcmnname typeName="SET_D_USEFLAG" match="oneline:UseFlag"/>" class="textgray" readonly >
        <input type="hidden" name="txtUseFlag" value="<tbl:write name="oneline" property="UseFlag" />" >
         </td>
        <td class="list_bg2"><div align="right">状态</div></td>
        <td class="list_bg1">
        <input type="text" name="txtStatusDes" size="25"  value="<d:getcmnname typeName="SET_D_DEVICESTATUS" match="oneline:Status"/>" class="textgray" readonly >          
        <input type="hidden" name="txtStatus" value="<tbl:write name="oneline" property="Status" />" >
         </td>
      </tr>
       <%if("Y".equals(devicePurpose)){%>
       <tr>
        <td class="list_bg2"><div align="right">设备预授权</div></td>
        <td class="list_bg1">
        <input type="text" name="txtPreAuthorizationDes" size="25"  value="<d:getcmnname typeName="SET_G_YESNOFLAG" match="oneline:PreAuthorization"/>" class="textgray" readonly >          
        <input type="hidden" name="txtPreAuthorization" value="<tbl:write name="oneline" property="PreAuthorization" />" >
         </td>
         <td class="list_bg2"><div align="right">设备用途</div></td>
        <td class="list_bg1">
        <input type="text" name="txtPurposeStrListValue" size="25"  value="<%=totalValue%>" class="textgray" readonly >
        <input type="hidden" name="txtPurposeStrList" value="<tbl:write name="oneline" property="PurposeStrList" />"  >        
         </td>
        
      </tr>
      <%} else{%>
       <tr>
        <td class="list_bg2"><div align="right">设备预授权</div></td>
        <td class="list_bg1" colspan="3">
        <input type="text" name="txtMatchFlag" size="25"  value="<d:getcmnname typeName="SET_G_YESNOFLAG" match="oneline:PreAuthorization"/>" class="textgray" readonly >          
        <input type="hidden" name="txtPreAuthorization" value="<tbl:write name="oneline" property="PreAuthorization" />"
         </td>
      </tr>
      <%}%>
     <tr>
     <td class="list_bg2"><div align="right">平移系统内部授权号</div></td>
        <td class="list_bg1">
        <input type="text" name="txtOkNumber" size="25"  value="<tbl:write name="oneline" property="okNumber" />" class="textgray" readonly >          
      </td>
      <td class="list_bg2"><div align="right">备注</div></td>
        <td class="list_bg1">
        <input type="text" name="txtComments" size="25"  value="<tbl:write name="oneline" property="comments" />" class="textgray" readonly >          
      </td>
     </tr>
     
     <%  
        if("SC".equals(deviceClass) && ("W".equals(status)||"S".equals(status)) && "Y".equals(preAuthorization)){ //库存,待售 
     %>
     <tr>
      <td class="list_bg2" nowrap><div align="right">预授权产品</div></td>
      <td class="list_bg1" colspan="3"><%=strProductListNames%>
      </td>
     </tr>
     <%}%>
     
  </table>
 </lgc:bloop>      

 <table width="100%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
    <tr align="center">
     <td class="list_bg1"><table  border="0" cellspacing="0" cellpadding="0">
     
     <tr>
      <td width="11" height="20"><img src="img/button2_r.gif"  height="20"></td>
      <td background="img/button_bg.gif"><a href="<bk:backurl property="dev_query_result.do/dev_batch_query_result.do" />" class="btn12">返回</a></td>
      <td width="22" height="20"><img src="img/button2_l.gif"  height="20"></td>     	
	   <!--<td width="20" ></td>-->
     <%  
        if("智能卡".equals(strClass) && ("W".equals(status)||"S".equals(status))){
     %>
     
      <%if(!"Y".equals(preAuthorization)){  %>
     	<pri:authorized name="device_auth.do">
     	<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
     	<td background="img/button_bg.gif"><a href="javascript:preauth_submit('<tbl:write name="oneline" property="SerialNo" />','<tbl:write name="oneline" property="DeviceID" />')" class="btn12">预授权</a></td>
      	<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
       	<!--<td width="20" ></td>-->
       	</pri:authorized>
       <%}%>
       
       <%if("Y".equals(preAuthorization)){%>
       	<pri:authorized name="device_disauth.do">
       	<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
        <td background="img/button_bg.gif"><a href="javascript:scdevice_release_submit('<tbl:write name="oneline" property="SerialNo" />','<tbl:write name="oneline" property="DeviceID" />')" class="btn12">清除预授权</a></td>
      	<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
       	<!--<td width="20" ></td>-->
       	</pri:authorized>
       	
		<pri:authorized name="resendEMM_submit.do">  
		<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
        <td background="img/button_bg.gif"><a href="javascript:refresh_preauth_submit('<tbl:write name="oneline" property="SerialNo" />')" class="btn12">刷新预授权</a></td>
		<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
		<!--<td width="20" ></td>-->
		</pri:authorized>
		
      <%}}%>
      
      <% if("Y".equals(matchFlag)){%>
		<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
        <td background="img/button_bg.gif"><a href="javascript:unmatch_submit()" class="btn12">配对信息</a></td>
		<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
		<!--<td width="20" ></td>-->
       <%}%>
       
     <%
       if("SC".equals(deviceClass) && "Y".equals(matchFlag)){
      %>
      	<pri:authorized name="device_dismatch.do">
		<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
		<td background="img/button_bg.gif"><a href="javascript:dismatch_submit('<tbl:write name="oneline" property="SerialNo" />','<tbl:write name="oneline" property="DeviceID" />')" class="btn12">解配对</a></td>
		<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
		<!--<td width="20" ></td>-->
		</pri:authorized>
       <%}%>
       
       <pri:authorized name="device_info_modify.do" >
		<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>                                                            
		<td background="img/button_bg.gif"><a href="javascript:device_info_modify()" class="btn12">修改</a></td>
		<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
		<!--<td width="20" ></td>-->
       </pri:authorized>
       
        <td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>                                                            
        <td background="img/button_bg.gif"><a href="javascript:device_trans_query()" class="btn12">设备流转历史</a></td>
		<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
		<!--<td width="20" ></td>-->
       
		<%if("智能卡".equals(strClass)){%>
		<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>                                                            
		<td background="img/button_bg.gif"><a href="javascript:device_preauth_query()" class="btn12">预授权历史</a></td>
		<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
		<!--<td width="20" ></td>-->
		<%}%>
		
		<%if (deviceClass.equals("SC")||deviceClass.equals("STB")){%>
      	<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>                                                            
        <td background="img/button_bg.gif"><a href="javascript:device_prematch_query()" class="btn12">配对历史</a></td>
      	<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
      	<!--<td width="20" ></td>-->
		<%}%>

      </tr>
      
 </table> 
	  </td>
  </tr>
  </table>
  
</form>

