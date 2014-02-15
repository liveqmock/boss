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
	parent.document.getElementById(divid).innerHTML="���Ե�....";
	document.formUpdateSelect.Action.value="S";
	document.formUpdateSelect.txtSerialNoBegin.value=parent.document.getElementById(serialNoId).value;
	document.formUpdateSelect.txtSerialNoEnd.value=parent.document.getElementById(serialNoId).value;

	document.formUpdateSelect.submit();
}
<%
	//��������
	if(!(request.getParameter("Action")==null || "".equals(request.getParameter("Action")))){
		QueryCommandResponseImpl RepCmd = (QueryCommandResponseImpl)pageContext.getRequest().getAttribute("ResponseQueryResult");
		Collection vendorDeviceList = (Collection) RepCmd.getPayload();
		String macInmac ="";
		
		String macInfoId=request.getParameter("txtMacInfoId");
		
		String matchSerialNo = "";  //��Ե��豸���к�
		String matchMacInmac =""; // �������������Ϣ.
		
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
							macInmac="  CM MAC��ַ:" + terminalDeviceDTO.getMACAddress();
						if(!(terminalDeviceDTO.getInterMACAddress()==null || "".equals(terminalDeviceDTO.getInterMACAddress())))
							macInmac=macInmac +"  �ն�MAC��ַ:" + terminalDeviceDTO.getInterMACAddress();
					}else{
						macInmac=" ���豸���Ǵ���״̬!";
					}
				
				/* modify by david.Yang ��Ϊ����STB����Թ�ϵ����ָSC������ָCM		
					//ȡ������豸�����к�
					if( "SC".equals(terminalDeviceDTO.getDeviceClass()) || "STB".equals(terminalDeviceDTO.getDeviceClass()) ){
						if(terminalDeviceDTO.getMatchDeviceID()!=0){
							TerminalDeviceDTO matchDto = Postern.getTerminalDeviceByID(terminalDeviceDTO.getMatchDeviceID());
							if(matchDto != null){
								matchSerialNo=matchDto.getSerialNo();
							}
							
							if("W".equals(matchDto.getStatus())){
								if(!(matchDto.getMACAddress()==null || "".equals(matchDto.getMACAddress())))
									matchMacInmac="  CM MAC��ַ:" + matchDto.getMACAddress();
								if(!(matchDto.getInterMACAddress()==null || "".equals(matchDto.getInterMACAddress())))
									matchMacInmac=matchMacInmac +"  �ն�MAC��ַ:" + matchDto.getInterMACAddress();
							}else{
								matchMacInmac=" ���豸���Ǵ���״̬!";
							}
	   				}
					}
					*/
					System.out.println("matchMacInmac>>>>>>>>>>>>>"+matchMacInmac);
					System.out.println("matchSerialNo>>>>>>>>>>>>>"+matchSerialNo);
				}
			}
		}else{
			macInmac=" ��Ч�豸!";
		}
		
%>

//�����豸�б�ֵ
update_vendor_device('<%=macInmac%>','<%=macInfoId%>','<%=matchSerialNo%>','<%=matchMacInmac%>');
<%	}  %>
function update_vendor_device(macInmac,macInfoId,matchSerialNo,matchMacInmac){
	//����������Ϣ
	parent.document.getElementById(macInfoId).innerHTML=macInmac;
	//����ƥ������
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
