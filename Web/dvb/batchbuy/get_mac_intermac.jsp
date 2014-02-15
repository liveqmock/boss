<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>

<%@ page import="com.dtv.oss.service.commandresponse.QueryCommandResponseImpl"%>
<%@ page import="com.dtv.oss.dto.TerminalDeviceDTO" %>
<%@ page import="com.dtv.oss.util.Postern" %>
<%@ page import="java.util.*"%>

<script language=javascript>
<!--
function submit_update_vendor_device(serialNoId,deviceclass){
	var divid=serialNoId+"_div";
	document.formUpdateSelect.txtMacInfoId.value=divid;
	parent.document.getElementById(divid).innerHTML="请稍等....";
	document.formUpdateSelect.Action.value="S";
	document.formUpdateSelect.txtSerialNoBegin.value=parent.document.getElementById(serialNoId).value;
	document.formUpdateSelect.txtSerialNoEnd.value=parent.document.getElementById(serialNoId).value;

	document.formUpdateSelect.submit();
}
<%
	//有请求发生
	if(!(request.getParameter("Action")==null || "".equals(request.getParameter("Action")))){
		QueryCommandResponseImpl RepCmd = (QueryCommandResponseImpl)pageContext.getRequest().getAttribute("ResponseQueryResult");
		Collection vendorDeviceList = (Collection) RepCmd.getPayload();
		String macInmac ="";
		
		String macInfoId=request.getParameter("txtMacInfoId");
		
		String matchSerialNo = "";  //配对的设备序列号
		String matchMacInmac =""; // 配对设置描述信息.
		
		String deviceclassname="";
		if(!(vendorDeviceList==null || vendorDeviceList.isEmpty())){
			Iterator itVendorDevice = vendorDeviceList.iterator();
			if(vendorDeviceList.size()>1){
				
			}else{
				if(itVendorDevice.hasNext()){
					TerminalDeviceDTO terminalDeviceDTO=(TerminalDeviceDTO)itVendorDevice.next();
					if(!(terminalDeviceDTO.getDeviceClass()==null || "".equals(terminalDeviceDTO.getDeviceClass()))){
						deviceclassname=terminalDeviceDTO.getDeviceClass();
					}
					if("W".equals(terminalDeviceDTO.getStatus())){
						if(!(terminalDeviceDTO.getMACAddress()==null || "".equals(terminalDeviceDTO.getMACAddress())))
							macInmac="  CM MAC地址:" + terminalDeviceDTO.getMACAddress();
						if(!(terminalDeviceDTO.getInterMACAddress()==null || "".equals(terminalDeviceDTO.getInterMACAddress())))
							macInmac=macInmac +"  终端MAC地址:" + terminalDeviceDTO.getInterMACAddress();
					}else{
						macInmac=" 该设备不是待售状态!";
					}
				
				/* modify by david.Yang 因为现在STB的配对关系不是指SC，而是指CM		
					//取得配对设备的序列号
					if( "SC".equals(terminalDeviceDTO.getDeviceClass()) || "STB".equals(terminalDeviceDTO.getDeviceClass()) ){
						if(terminalDeviceDTO.getMatchDeviceID()!=0){
							TerminalDeviceDTO matchDto = Postern.getTerminalDeviceByID(terminalDeviceDTO.getMatchDeviceID());
							if(matchDto != null){
								matchSerialNo=matchDto.getSerialNo();
							}
							
							if("W".equals(matchDto.getStatus())){
								if(!(matchDto.getMACAddress()==null || "".equals(matchDto.getMACAddress())))
									matchMacInmac="  CM MAC地址:" + matchDto.getMACAddress();
								if(!(matchDto.getInterMACAddress()==null || "".equals(matchDto.getInterMACAddress())))
									matchMacInmac=matchMacInmac +"  终端MAC地址:" + matchDto.getInterMACAddress();
							}else{
								matchMacInmac=" 该设备不是待售状态!";
							}
	   				}
					}
					*/
					System.out.println("matchMacInmac>>>>>>>>>>>>>"+matchMacInmac);
					System.out.println("matchSerialNo>>>>>>>>>>>>>"+matchSerialNo);
				}
			}
		}else{
			macInmac=" 无效设备!";
		}
		
%>

//更新设备列表值
update_vendor_device('<%=macInmac%>','<%=macInfoId%>','<%=matchSerialNo%>','<%=matchMacInmac%>');
<%	}  %>
function update_vendor_device(macInmac,macInfoId,matchSerialNo,matchMacInmac){
	//更新描述信息
	parent.document.getElementById(macInfoId).innerHTML=macInmac;
	//更新匹配设置
	var matchInfoId=macInfoId.split('=')[0]+'='+(macInfoId.indexOf('SC')!=-1?'STB':'SC');
	if(matchSerialNo){
		parent.document.getElementById(matchInfoId).value=matchSerialNo;
		parent.document.getElementById(matchInfoId+'_div').innerHTML=matchMacInmac;
	}
	
}

</script>
<form name="formUpdateSelect" method="post" action="get_mac_intermac_batchbuy.do">
	<input type="hidden" name="Action" value="">
	<input type="hidden" name="txtMacInfoId" value="">
	<input type="hidden" name="txtSerialNoBegin" value="">
	<input type="hidden" name="txtSerialNoEnd" value="">
</form>
