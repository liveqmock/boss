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
	//��������
	if(!(request.getParameter("Action")==null || "".equals(request.getParameter("Action")))){
		QueryCommandResponseImpl RepCmd = (QueryCommandResponseImpl)pageContext.getRequest().getAttribute("ResponseQueryResult");
		Collection vendorDeviceList = (Collection) RepCmd.getPayload();
		String macInmac ="";
		String divid="";
		String inputid="";
		String deviceclassname="";
		String status = "";  //�豸״̬
		
		String matchSerialNo = "";  //��Ե��豸���к�
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
							macInmac="  CM MAC��ַ:" + terminalDeviceDTO.getMACAddress();
						if(!(terminalDeviceDTO.getInterMACAddress()==null || "".equals(terminalDeviceDTO.getInterMACAddress())))
							macInmac=macInmac +"  �ն�MAC��ַ:" + terminalDeviceDTO.getInterMACAddress();
					}else{
						macInmac=" ���豸���Ǵ���״̬!";
					}
					
					//ȡ������豸�����к�
					if( "SC".equals(terminalDeviceDTO.getDeviceClass()) || "STB".equals(terminalDeviceDTO.getDeviceClass()) ){
						if(terminalDeviceDTO.getMatchDeviceID()!=0){
							TerminalDeviceDTO matchDto = Postern.getTerminalDeviceByID(terminalDeviceDTO.getMatchDeviceID());
							if(matchDto != null) matchSerialNo=matchDto.getSerialNo();
							
							if("W".equals(matchDto.getStatus())){
								if(!(matchDto.getMACAddress()==null || "".equals(matchDto.getMACAddress())))
									macInmacMatch="  CM MAC��ַ:" + matchDto.getMACAddress();
								if(!(matchDto.getInterMACAddress()==null || "".equals(matchDto.getInterMACAddress())))
									macInmacMatch=macInmacMatch +"  �ն�MAC��ַ:" + matchDto.getInterMACAddress();
							}else{
								  macInmacMatch=" ���豸���Ǵ���״̬!";
							}
	   				}
					}
				}
			}
			
		}
		
%>

parent.updselwaitlayer.style.display = "none";		
//�����豸�б�ֵ
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
		parent.document.getElementById(divid).innerHTML="  û�д��豸";
		divid="input"+deviceclass;
		parent.document.frmPost.elements[divid].value="  û�д��豸";
		return;
	}
	parent.document.frmPost.elements[inputid].value=macInmac;
	parent.document.getElementById(divid).innerHTML=macInmac;


/* modify by david.Yang ��Ϊ����STB����Թ�ϵ����ָSC������ָCM
	//����״̬ʱ���Զ���������豸�����к�
	if(deviceclassMatch!=""){
		if(status=="W"){  							//����״̬ʱ
			if(matchSerialNo!=""){  				//����
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
