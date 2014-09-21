<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>

<%@ page import="com.dtv.oss.util.Postern"%>
<%@ page import="com.dtv.oss.dto.TerminalDeviceDTO" %>
<%@ taglib uri="bookmark" prefix="bk" %>
<%@ taglib uri="resultset" prefix="rs" %>
<%@ taglib uri="logic" prefix="lgc"%>
<%@ taglib uri="osstags" prefix="d"%>
<%
  //是否起用设备用途：
	String devicePurpose = Postern.getSystemsettingValueByName("SET_D_DEVICEPURPOSE");
	//地址类型
	String txtZoneStation = request.getParameter("txtZoneStation");
 %>
<script language=javascript>

function edit_submit()
{
  if(!check_options())return;  
  
	document.frmPost.action="device_info_modify_confirm.do";
	document.frmPost.submit();
}

function select_onclick(a){
	tmpSelectValue=a.value;
}
 
function ChangeDeviceClass(){
   document.FrameUS.submit_update_select('devclasstomodel',document.frmPost.txtDeviceClass.value, 'txtDeviceModel', '');
}
//根据型号筛选制造商
function ChangeDeviceProvider(){
    document.FrameUS.submit_update_select('devprovider',document.frmPost.txtDeviceModel.value, 'txtDeviceProvider', '');
}

function open_select(type,typeName,typeValue,parentType,parentTypeName,parentTypeValue){
	var param="batch_query_select.screen?";
	param=param + "type="+type;
	param=param + "&typeName="+typeName;
	param=param + "&typeValue=" + document.frmPost.elements(typeName).value;
	param=param + "&parentType="+parentType;
	param=param + "&parentTypeName="+parentTypeName;
	
	if(parentTypeName!=null && parentTypeName!="")
		param=param + "&parentTypeValue="+document.frmPost.elements(parentTypeName).value;
		
	var windowFeatures="toolbar=no,location=no,status=no,menubar=no,scrollbars=yes,width=330,height=250,screenX=340,screenY=270";

	window.open(param,"",windowFeatures);
}

function check_options()
{
	//if(check_Blank(document.frmPost.txtDeviceClass, true, "类型"))
	//		return false;
	//if(check_Blank(document.frmPost.txtDeviceModel, true, "型号"))
	//		return false;
	//if(check_Blank(document.frmPost.txtSerialNo, true, "序列号"))
	//		return false;
			
			
	//var modelName=document.frmPost.txtDeviceModel.value;
	//if(!check_serialLength(modelName,document.frmPost.txtSerialNo.checkValue))
	//		return false;
	
	//if(check_Blank(document.frmPost.txtZoneStation, true, "地址类型"))
	//		return false;
	//if(check_Blank(document.frmPost.txtStatus, true, "状态"))
	//		return false;
	
	if("D"==document.frmPost.txtZoneStation.value)
	{
		//if(check_Blank(document.frmPost.txtDepotID, true, "运入仓库"))
		//	return false;
	}
	if("B"==document.frmPost.txtZoneStation.value)
	{
		//if(check_Blank(document.frmPost.txtOrgDesc, true, "地址名称"))
		//	return false;
		document.frmPost.txtAddressID.value = document.frmPost.txtOrgDesc.value;
	}
	if("T"==document.frmPost.txtZoneStation.value)
	{
		//if(check_Blank(document.frmPost.txtOrgDesc, true, "地址名称"))
		//	return false;
	}
	
	return true;
}
function check_serialLength(modelName,para){
	var serLength=document.frmPost.seriallength.value;	
	checkValue=trim(para);
	if(checkValue.length!=serLength){
	   alert(modelName+"的序列号长度应该为"+serLength);
	   return false;
	}	
	return true;
}
</script>
<div id="updselwaitlayer" style="position:absolute; left:650px; top:150; width:250px; height:24px; display:none">
<table width="100%" border="0" cellspacing="1" cellpadding="3">
	<tr>
	<td width="100%"><div align="center"><font size="2"> 正在获取内容。。。 </font></td>
	</tr>
</table>
</div>
<iframe SRC="update_select.screen" name="FrameUS" width="0" height="0" frameborder="0" scrolling="0"> </iframe>
     
<table  border="0" align="center" cellpadding="0" cellspacing="10" >
  <tr>
    <td><img src="img/list_tit.gif" width="19" height="19"></td>
    <td class="title">修改设备信息</td>
  </tr>
</table>
<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>
<form name="frmPost" method="post" >  
<input type="hidden" name="func_flag" value="2005" >
<input type="hidden" name="seriallength" value="<tbl:writeparam   name="seriallength" />">
<input type="hidden" name="txtDeviceDtLastmod"   value="<tbl:writeparam   name="txtDeviceDtLastmod" />" >
 <table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
      <tr>
        <td class="list_bg2"><div align="right">设备ID</div></td>
        <td class="list_bg1">
        <input type="text" name="txtDeviceID" size="25"  value="<tbl:writeparam name="txtDeviceID" />" class="textgray" readonly >
        </td>
        
        <td class="list_bg2"><div align="right">设备序列号</div></td>
        <td class="list_bg1">
        <input type="text" name="txtSerialNo" size="25"  value="<tbl:writeparam name="txtSerialNo" />">         
        </td>
      </tr>
       <tr> 
    	  <td class="list_bg2"><div align="right">设备类型</div></td>
    	  <%
    	  	Map mapDeviceClasses = Postern.getAllDeviceClasses();
					pageContext.setAttribute("AllDeviceClasses", mapDeviceClasses);
    	  %>
    	  <td class="list_bg1">
    	  <tbl:select name="txtDeviceClass" set="AllDeviceClasses" match="txtDeviceClass" width="23" onchange="ChangeDeviceClass()" />
    	  </td>	
          <td class="list_bg2"><div align="right">设备型号</div></td>
          <td class="list_bg1">
          	<d:seldevicemodel name="txtDeviceModel" deviceClass="txtDeviceClass" match="txtDeviceModel" width="23" />
          </td>     
      </tr>
      <tr>  
        <td class="list_bg2"><div align="right">运入批号</div></td>
        <td class="list_bg1">
        <input type="text" name="txtBatchID" size="25" value="<tbl:writeparam name="txtBatchID" />" class="textgray" readonly>
         </td>
         <td class="list_bg2"><div align="right">运入仓库</div></td>
         <td class="list_bg1">
         <d:seldepotbyoper name="txtDepotID" match="txtDepotID" width="23" onchange="change_zone(document.frmPost.txtZoneStation.value,false)" />
         </td>
      </tr>
      <tr> 
        <td class="list_bg2"><div align="right">地址类型</div></td>
         <td class="list_bg1">
         <d:selcmn name="txtZoneStation" mapName="SET_D_DEVICEADDRESSTYPE" match="txtZoneStation" width="23" onchange="change_zone2(document.frmPost.txtZoneStation.value,false)" />
         </td>
        <!--根据属主类型显示仓库或分公司-->
        <td class="list_bg2"><div>&nbsp;</div><div align="right">地址名称</div></td>
	      <td class="list_bg1"><div id="modelFieldNameDesc" style="color:red">请填写客户ID</div>
	    	<input type="hidden" name="txtAddressID" value="<tbl:writeparam name="txtAddressID" />">
		    <input type="text" id="txtOrgDesc" name="txtOrgDesc" size="25" maxlength="50" value="<tbl:WriteOrganizationInfo property="txtAddressID" />" >
		    <input id="selOrgButton" name="selOrgButton" type="button" class="button" value="选择" onClick="javascript:sel_organization('all','txtAddressID','txtOrgDesc')">
    </td>			
      </tr>
       <tr> 
        <td class="list_bg2"><div align="right">租/售</div></td>
        <td class="list_bg1">
        <d:selcmn name="txtLeaseBuy" mapName="SET_D_DEVICESELLMETHOD" match="txtLeaseBuy" width="23" />         
         </td>
         <td class="list_bg2"><div align="right">租售日期</div></td>
         <td class="list_bg1">
         <d:datetime name="txtDateFrom" size="25" match="txtDateFrom" includeHour="false" onclick="MyCalendar.SetDate(this,document.frmPost.txtDateFrom,'Y')" picURL="img/calendar.gif" style="cursor:hand" />         
         </td>
      </tr>
     <tr>
        <td class="list_bg2"><div align="right">配对</div></td>
        <td class="list_bg1">
        <d:selcmn name="txtMatchFlag" mapName="SET_G_YESNOFLAG" match="txtMatchFlag" width="23" />          
         </td>
        <td class="list_bg2"><div align="right">配对设备号</div></td>
         <td class="list_bg1">
        <input type="text" name="txtMatchDeviceID" size="25"  value="<tbl:writeparam name="txtMatchDeviceID" />">
         </td>
      </tr>
      <tr>
        <td class="list_bg2"><div align="right">置位标记</div></td>
         <td class="list_bg1">
         <d:selcmn name="txtCaSetFlag" mapName="SET_G_YESNOFLAG" match="txtCaSetFlag" width="23" />         
         </td>
         <td class="list_bg2"><div align="right">置位日期</div></td>
         <td class="list_bg1">
        <d:datetime name="txtCaSetDate" size="25" match="txtCaSetDate" includeHour="false" onclick="MyCalendar.SetDate(this,document.frmPost.txtCaSetDate,'Y')" picURL="img/calendar.gif" style="cursor:hand" />
         </td>
      </tr>
       <tr>
          <td class="list_bg2"><div align="right">保修截止日期</div></td>
          <td class="list_bg1">
          <d:datetime name="txtDateTo" size="25" match="txtDateTo" includeHour="false" onclick="MyCalendar.SetDate(this,document.frmPost.txtDateTo,'Y')" picURL="img/calendar.gif" style="cursor:hand" />
         </td>
         <td class="list_bg2"><div align="right">保修期的长度(单位:月)</div></td>
         <td class="list_bg1">
        <input type="text" name="txtGuaranteeLength" size="25"  value="<tbl:writeparam name="txtGuaranteeLength" />">
         </td>
      </tr>
      
      <tr>
        <td class="list_bg2"><div align="right">CM MAC地址</div></td>
         <td class="list_bg1">
         <input type="text" name="txtMacAddress" size="25"  value="<tbl:writeparam name="txtMacAddress" />">
         </td>
         <td class="list_bg2"><div align="right">终端MAC地址</div></td>
         <td class="list_bg1">
         <input type="text" name="txtInterMacAddress" size="25"  value="<tbl:writeparam name="txtInterMacAddress" />" >
         </td>
      </tr>
      
    <tr>
         <td class="list_bg2"><div align="right">是否二手</div></td>
         <td class="list_bg1">
         	<d:selcmn name="txtUseFlag" mapName="SET_D_USEFLAG" match="txtUseFlag" width="23" />
         </td>
        <td class="list_bg2"><div align="right">状态</div></td>
        <td class="list_bg1">
        <d:selcmn name="txtStatus" mapName="SET_D_DEVICESTATUS" match="txtStatus" width="23" />          
         </td>
      </tr>
       <%if("Y".equals(devicePurpose)){%>
       <tr>
        <td class="list_bg2"><div align="right">设备预授权</div></td>
        <td class="list_bg1">
        <d:selcmn name="txtPreAuthorization" mapName="SET_G_YESNOFLAG" match="txtPreAuthorization" width="23" /> 
         </td>
         <td class="list_bg2"><div align="right">设备用途</div></td>
        <td class="list_bg1">
      <input name="txtPurposeStrListValue" type="text" maxlength="200" size="25" value="<tbl:writeparam name="txtPurposeStrListValue" />" readonly>
	    <input name="txtPurposeStrList" type="hidden" value="<tbl:writeparam name="txtPurposeStrList" />">
	   	<input name="checkbutton" type="button" class="button" value="选择" onClick="javascript:open_select('SET_D_DEVICEUSEFORPURPOSE','txtPurposeStrList',document.frmPost.txtPurposeStrList.value,'','','')"> 
         
         </td>
        
      </tr>
      <%} else{%>
       <tr>
        <td class="list_bg2"><div align="right">设备预授权</div></td>
        <td class="list_bg1" colspan="3">
        <d:selcmn name="txtPreAuthorization" mapName="SET_G_YESNOFLAG" match="txtPreAuthorization" width="25" />
         </td>
      </tr>
      <%}%>
      <tr>
      <td class="list_bg2"><div align="right">平移系统内部授权号</div></td>
        <td class="list_bg1">
        <input type="text" name="txtOkNumber" size="25"  value="<tbl:writeparam name="txtOkNumber" />" >          
      </td>
      <td class="list_bg2"><div align="right">备注</div></td>
        <td class="list_bg1">
        <input type="text" name="txtComments" size="25"  value="<tbl:writeparam name="txtComments" />" >          
      </td>
     </tr>
  </table>
   

 <table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
    <tr align="center">
    	<td class="list_bg1"><table  border="0" cellspacing="0" cellpadding="0">
     <tr>
     	 <td width="11" height="20"><img src="img/button2_r.gif"  height="20"></td>
      <td background="img/button_bg.gif"><a href="dev_detail.do?txtDeviceID=<tbl:writeparam name="txtDeviceID" /> " class="btn12">返&nbsp;回</a></td>
      <td width="22" height="20"><img src="img/button2_l.gif"  height="20"></td>     	
       <td width="20" ></td>
       
       <td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
       <td><input name="Submit" type="button" class="button" value="修 改" onclick="javascript:edit_submit()"></td>
       <td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
       <td width="20" ></td>
      </tr>
 </table> 
	  </td>
  </tr>
  </table>
  
</form>
<script language=javascript>
change_zone("<%=txtZoneStation%>",true);
function change_zone(txtZoneStation,firEnter)
{
	if("D"==txtZoneStation)//属主类型是仓库
	{
		document.all["selOrgButton"].style.display="none";
		document.frmPost.txtDepotID.disabled = false;
		document.frmPost.txtOrgDesc.readOnly=true;
		if(document.frmPost.txtDepotID.value == "")
		{
			document.frmPost.txtOrgDesc.innerText = "";
		}
		else
		{
			document.frmPost.txtOrgDesc.innerText = get_select_text(document.frmPost.txtDepotID);
		}
		document.frmPost.txtAddressID.value = document.frmPost.txtDepotID.value;
		document.getElementById('modelFieldNameDesc').innerHTML='';
	}
	else if("T"==txtZoneStation)
	{
		document.frmPost.txtDepotID.disabled = true;
		document.all["selOrgButton"].style.display="";
		document.frmPost.txtOrgDesc.readOnly=true;
		document.getElementById('modelFieldNameDesc').innerHTML='';
		if(!firEnter)
		{
			document.frmPost.txtOrgDesc.innerText = "";
			document.frmPost.txtDepotID.selectedIndex = 0;
		}
	}
	else
	{	
		document.frmPost.txtDepotID.disabled = true;
		document.all["selOrgButton"].style.display="none";
		document.frmPost.txtOrgDesc.readOnly=false;
		document.getElementById('modelFieldNameDesc').innerHTML='请填写客户证号';
		if(!firEnter)
		{
		
			document.frmPost.txtOrgDesc.innerText = "";
			document.frmPost.txtDepotID.selectedIndex = 0;
		}
		else
		{
				document.frmPost.txtOrgDesc.value = document.frmPost.txtAddressID.value;
				document.frmPost.txtOrgDesc.innerText = document.frmPost.txtAddressID.value;
		}
	}
}

function get_select_text(selObj){   
  var retValue = "";   
  for(i = 0; i<selObj.options.length;i++)   
  {   
     if(selObj.options[i].selected)  
     { 
        retValue=selObj.options[i].text;
        break;   
     }
  }
  return retValue;
}   


function change_zone2(txtZoneStation,firEnter){
	//var customerId=document.frmPost.txtCustomerID.value;
	var strTitle = "";
	if("D"==txtZoneStation)//属主类型是仓库
			strTitle = "仓库选择";
	else if("T"==txtZoneStation)//属主类型是组织机构
			strTitle = "组织机构选择";
  else if("B"==txtZoneStation)
  		strTitle = "客户ID录入";
  else
     return;

	var strFeatures = "top=120px,left=120px,width=350px,height=350px,resizable=no,toolbar=no,scrollbars=no";
	
	window.open("change_zone.screen?txtZoneStation="+txtZoneStation+"&strTitle="+strTitle,strTitle,strFeatures);
}
	
</script>

