<%@ page import="com.dtv.oss.service.commandresponse.QueryCommandResponseImpl"%>
<%@ page import="com.dtv.oss.dto.TerminalDeviceDTO" %>

<%@ page import="java.util.*"%>

<script language=javascript>
<!--
function submit_update_vendor_device(deviceClassValue,deviceModelValue,serialNoValue){
	if(serialNoValue==""){
		alert("请录入设备序列号！");
		return;
	}
	parent.updselwaitlayer.style.display = "";
	document.formUpdateSelect.Action.value="S";
	document.formUpdateSelect.txtDeviceClass.value=deviceClassValue;
	document.formUpdateSelect.txtDeviceModel.value=deviceModelValue;
	document.formUpdateSelect.txtSerialNo.value=serialNoValue;
	document.formUpdateSelect.submit();
}

<%
	//有请求发生
	if(!(request.getParameter("Action")==null || "".equals(request.getParameter("Action")))){
		QueryCommandResponseImpl RepCmd = (QueryCommandResponseImpl)pageContext.getRequest().getAttribute("ResponseQueryResult");
		Collection vendorDeviceList = (Collection) RepCmd.getPayload();
		String strVendorDeviceInfo ="";
		if(!(vendorDeviceList==null || vendorDeviceList.isEmpty())){
			Iterator itVendorDevice = vendorDeviceList.iterator();
			if(itVendorDevice.hasNext()){
				TerminalDeviceDTO terminalDeviceDTO=(TerminalDeviceDTO)itVendorDevice.next();
				strVendorDeviceInfo=terminalDeviceDTO.getSerialNo();
				
				if(!(terminalDeviceDTO.getMACAddress()==null || "".equals(terminalDeviceDTO.getMACAddress())))
					strVendorDeviceInfo=strVendorDeviceInfo +"|" + terminalDeviceDTO.getMACAddress();
				if(!(terminalDeviceDTO.getInterMACAddress()==null || "".equals(terminalDeviceDTO.getInterMACAddress())))
					strVendorDeviceInfo=strVendorDeviceInfo +"|" + terminalDeviceDTO.getInterMACAddress();				
			}
		}
%>
	parent.updselwaitlayer.style.display = "none";		
	//更新设备列表值
	update_vendor_device('<%=request.getParameter("txtSerialNo") %>','<%=strVendorDeviceInfo %>');
<%	}  %>

function update_vendor_device(orientValue,targetValue){
	if(targetValue==""){
		if(!confirm("您扫描的设备在供应商设备号码资源库中不存在，您是否确认添加此设备？")){
			parent.document.frmPost.txtSerialNo.value="";
			return;
		}
		else{
			targetValue=orientValue;
			if(parent.document.frmPost.txtNotExistSerialNo.value=="")
				parent.document.frmPost.txtNotExistSerialNo.value=";";
			if(parent.document.frmPost.txtNotExistSerialNo.value.indexOf(";" + targetValue +";")==-1)
				parent.document.frmPost.txtNotExistSerialNo.value=parent.document.frmPost.txtNotExistSerialNo.value +targetValue +";";
		}
	}
	
	serialCol=parent.document.frmPost.txtTerminalDevices.value;
	if(serialCol!="")
		serialCol=serialCol+"\n";
          serialCol+=targetValue;
          parent.document.frmPost.txtTerminalDevices.value=serialCol;
          parent.document.frmPost.txtSerialNo.value="";
          //调用父窗口的检查
          parent.check_serials();
}
//-->
</script>
parent.updselwaitlayer.style.display = "none";
<form name="formUpdateSelect" method="post" action="update_vendor_device.do">
	<input type="hidden" name="Action" value="">
	<input type="hidden" name="txtDeviceClass" value="">
	<input type="hidden" name="txtDeviceModel" value="">
	<input type="hidden" name="txtSerialNo" value="">
</form>
