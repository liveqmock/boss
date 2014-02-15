<%@ page import="com.dtv.oss.service.commandresponse.QueryCommandResponseImpl"%>
<%@ page import="com.dtv.oss.dto.TerminalDeviceDTO" %>
<%@ page import="com.dtv.oss.util.Postern" %>
<%@ page import="java.util.*"%>

<script language=javascript>
<!--
var divid;
function submit_update_vendor_device(serialNoValue,deviceclass){
	parent.document.frmPost.txtHiddenMacInmac.value=deviceclass;
	deviceClassParameter = deviceclass;
	parent.updselwaitlayer.style.display = "";
	document.formUpdateSelect.Action.value="S";
	document.formUpdateSelect.txtSerialNoBegin.value=serialNoValue;
	document.formUpdateSelect.txtSerialNoEnd.value=serialNoValue;
	document.formUpdateSelect.submit();
}
<%
	//有请求发生
	if(!(request.getParameter("Action")==null || "".equals(request.getParameter("Action")))){
		QueryCommandResponseImpl RepCmd = (QueryCommandResponseImpl)pageContext.getRequest().getAttribute("ResponseQueryResult");
		Collection vendorDeviceList = (Collection) RepCmd.getPayload();
		String macInmac ="";
		String divid="";
		String inputid="";
		String deviceclassname="";
		String status = "";  //设备状态
		
		String matchSerialNo = "";  //配对的设备序列号
		String macInmacMatch =""; //
		
		if(!(vendorDeviceList==null || vendorDeviceList.isEmpty())){
			Iterator itVendorDevice = vendorDeviceList.iterator();
			if(vendorDeviceList.size()>1){
				
			}else{
				if(itVendorDevice.hasNext()){
					TerminalDeviceDTO terminalDeviceDTO=(TerminalDeviceDTO)itVendorDevice.next();
					status = terminalDeviceDTO.getStatus();
					if(!(terminalDeviceDTO.getDeviceClass()==null || "".equals(terminalDeviceDTO.getDeviceClass()))){
						divid="div"+terminalDeviceDTO.getDeviceClass();
						inputid="input"+terminalDeviceDTO.getDeviceClass();
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
					
					//取得配对设备的序列号
					if( "SC".equals(terminalDeviceDTO.getDeviceClass()) || "STB".equals(terminalDeviceDTO.getDeviceClass()) ){
						if(terminalDeviceDTO.getMatchDeviceID()!=0){
							TerminalDeviceDTO matchDto = Postern.getTerminalDeviceByID(terminalDeviceDTO.getMatchDeviceID());
							if(matchDto != null) matchSerialNo=matchDto.getSerialNo();
							
							if("W".equals(matchDto.getStatus())){
								if(!(matchDto.getMACAddress()==null || "".equals(matchDto.getMACAddress())))
									macInmacMatch="  CM MAC地址:" + matchDto.getMACAddress();
								if(!(matchDto.getInterMACAddress()==null || "".equals(matchDto.getInterMACAddress())))
									macInmacMatch=macInmacMatch +"  终端MAC地址:" + matchDto.getInterMACAddress();
							}else{
								  macInmacMatch=" 该设备不是待售状态!";
							}
	   				}
					}
				}
			}
			
		}
		
%>

parent.updselwaitlayer.style.display = "none";		
//更新设备列表值
update_vendor_device('<%=macInmac%>','<%=divid%>','<%=inputid%>','<%=deviceclassname%>','<%=status%>','<%=matchSerialNo%>','<%=macInmacMatch%>');
<%	}  %>
function update_vendor_device(macInmac,divid,inputid,deviceclassname,status,matchSerialNo,macInmacMatch){
	var deviceclass=parent.document.frmPost.txtHiddenMacInmac.value;
	var deviceclassMatch = ""
	if(deviceclass=="SC") deviceclassMatch="STB";
	if(deviceclass=="STB") deviceclassMatch="SC";
	if(parent.document.frmPost.elements[deviceclass].value==""){
		return;
	}
	if(!(deviceclassname==deviceclass)){
		divid=""
	}
	if(divid==""){
		var deviceclass=parent.document.frmPost.txtHiddenMacInmac.value;
		divid="div"+deviceclass;
		parent.document.getElementById(divid).innerHTML="  没有此设备";
		divid="input"+deviceclass;
		parent.document.frmPost.elements[divid].value="  没有此设备";
		return;
	}
	parent.document.frmPost.elements[inputid].value=macInmac;
	parent.document.getElementById(divid).innerHTML=macInmac;


/* modify by david.Yang 因为现在STB的配对关系不是指SC，而是指CM
	//待售状态时，自动设置配对设备的序列号
	if(deviceclassMatch!=""){
		if(status=="W"){  							//待售状态时
			if(matchSerialNo!=""){  				//设置
				if(parent.document.frmPost.elements[deviceclassMatch]!=null)
					parent.document.frmPost.elements[deviceclassMatch].value=matchSerialNo;
				dividMatch="div"+deviceclassMatch;
				if(parent.document.frmPost.elements[dividMatch]!=null)
					parent.document.getElementById(dividMatch).innerHTML=macInmacMatch;
				dividMatch="input"+deviceclassMatch;
				if(parent.document.frmPost.elements[dividMatch]!=null)
					parent.document.frmPost.elements[dividMatch].value=macInmacMatch;	
			}
		}
	}
*/
	
}

</script>
parent.updselwaitlayer.style.display = "none";
<form name="formUpdateSelect" method="post" action="get_mac_intermac.do">
	<input type="hidden" name="Action" value="">
	<input type="hidden" name="txtSerialNoBegin" value="">
	<input type="hidden" name="txtSerialNoEnd" value="">
</form>
