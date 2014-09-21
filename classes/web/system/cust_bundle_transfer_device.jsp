<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="bookmark" prefix="bk" %>
<%@ taglib uri="resultset" prefix="rs" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="osstags" prefix="d" %>
<%@ taglib uri="cur" prefix="cust" %>

<%@ page import="com.dtv.oss.util.Postern,java.util.*"%>
<%@ page import="com.dtv.oss.dto.DeviceClassDTO" %>
<%@ page import="com.dtv.oss.web.util.CommonKeys" %>

<Script language=JavaScript>
<!--
function check_frm()
{
	var submitFlag=true;
	var terminalDeviceList="";
	var sspanList="";
	var deviceModelSerialNo="";
	//检查数据完整性和给TerminalDeviceList赋值
	for(i=0;i<document.frmPost.length;i++)
	{	
		var element=document.frmPost.elements[i];
		if(element.title=="inputID"){
			sspanList=sspanList+element.value+" ;";
		}
		if((""+element.type)=="text")
		{
			if(element.name == "phoneNo")
				if (check_Blank(element, true, "电话号码"))
					return false;			
			if(element.value=="")
			{
				alert("输入不完整，请重新检查！");
				element.focus();
				submitFlag=false;
				break;
			}
			else
			{
				if(element.name!="phoneNo"){
					if(terminalDeviceList!=""){
						terminalDeviceList=terminalDeviceList+";";
						deviceModelSerialNo=deviceModelSerialNo+";";
					}
					terminalDeviceList=terminalDeviceList+element.value;
					deviceModelSerialNo=deviceModelSerialNo+element.name+"-"+element.value;
				}
			}
		}
	}
	document.frmPost.sspanList.value=sspanList;
	document.frmPost.TerminalDeviceList.value=terminalDeviceList;
	document.frmPost.deviceModelSerialNo.value=deviceModelSerialNo;
	
	
	return submitFlag;
}
function button_next(){
	if(check_frm()){
		document.frmPost.action="cust_bundle_transfer_fee.do";
		document.frmPost.submit();	
	}
}
	function back_submit(){
		document.frmPost.action='cust_bundle_transfer_product.do';
		document.frmPost.submit();
	}
function query_device_item(txtProductId,deviceClass,txtDevicemodel,inputObject){
	var txtCsiCreateReason=(document.frmPost.elements['txtCsiCreateReason']!=null)?(document.frmPost.elements['txtCsiCreateReason'].value):"";
	var csiType='BDS';
	strFeatures = "dialogWidth=530px;dialogHeight=450px;center=yes;help=no";
	var strUrl="list_terminate_device.do?txtFrom=1&txtTo=10&txtStatus=W&txtDeviceClass="+deviceClass
	+"&txtProductId="+txtProductId
	+"&txtDevicemodel="+txtDevicemodel;
	if(txtCsiCreateReason!=null&&txtCsiCreateReason!=''){
		strUrl+="&txtCsiCreateReason="+txtCsiCreateReason;
		strUrl+="&txtCsiType="+csiType;
	}
	var result=showModalDialog(strUrl,window,strFeatures);
	if (result!=null) document.frmPost.elements[deviceClass].value=result;
	getMacInmac(deviceClass);
}	
function getMacInmac(deviceClass){
	var inputid="input"+deviceClass;
	var divid="div"+deviceClass;
	document.frmPost.elements[inputid].value="";
	document.getElementById(divid).innerHTML="";
    document.FrameVendorDeviceUS.submit_update_vendor_device(document.frmPost.elements[deviceClass].value,deviceClass);
} 

function phoneNo_Search()
{
  var  phoneNo =document.frmPost.phoneNo.value;
  var  countyID ='<cust:curcustaddr property="districtID"/>';
  var  grade=document.frmPost.txtGrade.value;
  var  strFeatures = "width=600px,height=500px,resizable=no,toolbar=no,scrollbars=yes,status=yes";
  window.open("phoneNo_query.do?txtFrom=1&txtTo=10&phoneNo="+phoneNo+"&districtID="+countyID+"&grade="+grade,"电话号码查询",strFeatures);
}

//-->
</Script>
<div id="updselwaitlayer" style="position:absolute; left:650px; top:150; width:250px; height:24px; display:none"></div>

<iframe SRC="get_mac_intermac.screen" name="FrameVendorDeviceUS" width="0" height="0" frameborder="0" scrolling="0"> </iframe>

<table  border="0" align="center" cellpadding="0" cellspacing="10" >
  <tr>
    <td><img src="img/list_tit.gif" width="19" height="19"></td>
    <td class="title">客户套餐转换-设备选择</td>
  </tr>
</table>
 
<table width="100%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>

<form name="frmPost" method="post" action="">
	<table align="center" width="100%" border="0" cellspacing="1" cellpadding="3" class="list_bg">
		<%
		//这段代码封装回传信息
			String strSerialNo=request.getParameter("deviceModelSerialNo");
			String[] arrSerialNo=new String[0];
			if(strSerialNo!=null&&!strSerialNo.equals("")){
				arrSerialNo=strSerialNo.split(";");
			}
			HashMap serialNoMap=new HashMap();
			for(int i=0;i<arrSerialNo.length;i++){
				String[] ele=arrSerialNo[i].split("-");
				if(ele.length==2){
					serialNoMap.put(ele[0].trim(),ele[1].trim());
				}
			}

			Map deviceClassMap=	(Map)request.getAttribute(CommonKeys.TERMINALDEVICESELECT);
			for(Iterator dcit=deviceClassMap.keySet().iterator();dcit.hasNext();){
				Integer dcpid=(Integer)dcit.next();
				DeviceClassDTO dcDto=(DeviceClassDTO)deviceClassMap.get(dcpid);
				String serialNo="";
				if(serialNoMap.containsKey(dcDto.getDeviceClass())){
					serialNo=(String)serialNoMap.get(dcDto.getDeviceClass());
				}
				pageContext.setAttribute("oneline",dcDto);
			%>
			<tr>
				<td align="right" class="list_bg2" width="17%">
					<tbl:write name="oneline" property="Description" />序列号*
				</td>
				<td align="left" class="list_bg1">
					<input type="text" name="<tbl:write name="oneline" property="DeviceClass" />" size="25" maxlength="20" value="<%=serialNo%>" class="text"
						onchange="javascript:getMacInmac('<tbl:write name="oneline" property="DeviceClass" />');">
					<input type=button value="选择" class="button"
						onclick="javascript:query_device_item('<%=dcpid%>','<tbl:write name="oneline" property="DeviceClass" />','',this);">
        	<input name="input<tbl:write name="oneline" property="DeviceClass" />" title="inputID" type="hidden" >
         	<span  id="div<tbl:write name="oneline" property="DeviceClass" />" name="sspan"></span>	
				</td>
			</tr>
			<%}%>

	</table>
<%
boolean bNeedPhoneNo=false;
String strServiceIDs = (String)request.getAttribute(CommonKeys.SERVICEID);
System.out.println("strServiceIDs=============="+strServiceIDs);
if(strServiceIDs != null && !("".equals(strServiceIDs)))
{
	String serviceIDs[] = strServiceIDs.split(";");
	for(int i=0;i<serviceIDs.length;i++)
	{
		if("3".equals(serviceIDs[i]))
			bNeedPhoneNo = true;
	}
	
	if(bNeedPhoneNo)
	{
%>      
	<table width="820" border="0" align="center" cellpadding="4" cellspacing="1" class="list_bg">
		<tr>
			<td width="100%" class="import_tit" align="center">选择电话号码</td>
		</tr>
	</table>
	<table width="100%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
		<tr>
			<td class="list_bg2" width="17%">
				<div align="right">号码级别</div></td>
			<td class="list_bg1">
				<d:selcmn name="txtGrade" mapName="SET_R_PHONENOGRADE" match="txtGrade" width="23" />
			</td>
		</tr>
		<tr>
			<td class="list_bg2" width="17%"><div align="right">电话号码</div></td>
			<td class="list_bg1">
			<input type="text" name="phoneNo" size="25" maxlength="50" value="<tbl:writeparam name="phoneNo" />" class="text">
			<input name="Submit" type="button" class="button" value="查询" onclick="javascript:phoneNo_Search()"> 支持模糊查询，“_”代表一位，“%”代表多位。</td>
		</tr>
	</table>
	<input type="hidden" name="itemID" value="<tbl:writeparam name="itemID" />">
<% }}%>
	<input type="hidden" name="TerminalDeviceList" value="">
	<input type="hidden" name="deviceModelSerialNo" value="">
	<input type="hidden" name="sspanList" value="">
	<input type="hidden" name="txtHiddenMacInmac" value="">


	<table align="center" border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td width="11" height="20"><img src="img/button2_r.gif" width="22" height="20"></td>
			<td background="img/button_bg.gif"><a href="javascript:back_submit()" class="btn12">上一步</a></td>
			<td width="22" height="20"><img src="img/button2_l.gif" width="11" height="20"></td>
			<td width="20"></td>
			<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
			<td background="img/button_bg.gif"><a href="javascript:button_next()" class="btn12">下一步</a></td>
			<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
		</tr>
	</table>
	<input type="hidden" name="txtActionType" value="bundleTransfer">
	<input type="hidden" name="txtDoPost" value="false">
<tbl:productproperty serveyName="txtProductProperty" showType="hide"  />	
	
<tbl:hiddenparameters pass="txtCcID/txtCampaignName/txtDtCreate/txtStatus/txtAccoutID/txtServiceAccoutID/txtDateFrom/txtDateTo/txtPaymentType/txtNbrDate/txtCustomerID"/>
<tbl:hiddenparameters pass="txtCampaignID/txtTransferDate/txtCampaignDateTo/cancelCpID/txtNewCampaignName/txtProductID2PackageID/txtProductID/txtCsiCreateReason/txtIsReturn/txtDeviceFee" />


</form>

