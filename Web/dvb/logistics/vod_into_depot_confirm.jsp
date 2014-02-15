<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="resultset" prefix="rs" %>
<%@ taglib uri="/WEB-INF/oss.tld" prefix="d" %>
<%@ taglib uri="bookmark" prefix="bk" %>
                 
<%@ page import="com.dtv.oss.util.Postern, java.util.*" %>
<%@ page import="com.dtv.oss.service.commandresponse.CommandResponseImp" %>
<%@ page import="com.dtv.oss.dto.VodstbDeviceImportHeadDTO" %>

<SCRIPT language="JavaScript">
  function back_submit(){
  	 document.frmPost.action ="dev_into_depot.do";
  	 document.frmPost.submit();
  }
  function frm_submit(){
  	 if (window.confirm("�Ƿ��ύ��������ʽ���ݣ�")){
  		document.frmPost.action ="vod_into_depot_confirm.do";
  	 	  document.frmPost.submit();
  	 }
  }

</SCRIPT>
<%
  int batchNo =0;
  CommandResponseImp CmdRep = (CommandResponseImp)request.getAttribute("ResponseFromEJBEvent");  
  VodstbDeviceImportHeadDTO dto =new  VodstbDeviceImportHeadDTO();
  if (CmdRep!=null){
      dto = (VodstbDeviceImportHeadDTO)CmdRep.getPayload();
  }
  Map mapDepots = Postern.getAllDepot();
  Map mapDeviceClasses = Postern.getAllDeviceClasses();
  Map allDeviceModels =Postern.getAllDeviceModels();
  Map allProvider =Postern.getAllProvider();
  
  
  String deviceClass  =(String)mapDeviceClasses.get(dto.getDeviceClass()); 
  String deviceModel  =(String)allDeviceModels.get(dto.getDeviceModel());
  String providerName =(String)allProvider.get(String.valueOf(dto.getProviderID()));
  String deoptName    =(String)mapDepots.get(String.valueOf(dto.getDepotID()));
  String inAndOut     =(String)Postern.getCommonSettingDataValueByNameAndKey("SET_G_YESNOFLAG",dto.getInAndOut());
  String outOrg       =(String)Postern.getOrganizationDesc(dto.getOutOrgID());
%>
<table  border="0" align="center" cellpadding="0" cellspacing="10" >
  	<tr>
    	<td><img src="img/list_tit.gif" width="19" height="19"></td>
    	<td class="title">���豸���</td>
  	</tr>
</table>
<table width="100%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>
<br>

<form name="frmPost" method="post" action="list_dev_into_depot.do" >
  <input type="hidden" name="confirm_post" value="true">
  <input type="hidden" name="bathID" value="<%=dto.getBatchID()%>">
  <input type="hidden" name="func_flag" value="1003">
	<input type="hidden" name="confirm_flag" value="true">
	<input type="hidden" name="txtBatchID" value="<%=dto.getBatchID()%>">
<table width="810" align="center" border="0" cellspacing="1" cellpadding="3">
  <tr>
		<td class="list_bg2" width="17%" align="right">�豸����</td>
		<td class="list_bg1" width="33%"><%=deviceClass%></td>
		<td class="list_bg2" width="17%" align="right">�豸�ͺ�</td>
		<td class="list_bg1" width="33%"><%=deviceModel%></td>     
	</tr>
	<tr>
		<td class="list_bg2" align="right">�豸������</td>
		<td class="list_bg1"><%=providerName%></td>
		<td class="list_bg2" align="right">����*</td>
		<td class="list_bg1"><%=dto.getBatchNo()%></td>
	</tr>
	<tr>
		<td class="list_bg2" align="right">�����ڵĳ���(��λ:��)</td>
		<td class="list_bg1"><%=dto.getGuaranteeLength()%></td>
		<td class="list_bg2" align="right">�ֿ�</td>
		<td class="list_bg1"><%=deoptName%></td>
	</tr>
	<tr>
	  <td class="list_bg2" align="right">�Ƿ���������</td>
		<td class="list_bg1"><%=inAndOut%></td>
		<td class="list_bg2" align="right">���⵽��֯</td>
		<td class="list_bg1"><%=outOrg%></td>
	</tr>
	<tr>
	  <td  class="list_bg2" align="right">�豸��;</td>
	  <td  class="list_bg1" >&nbsp;<%=dto.getPurposeStrListValue()%></td>
	  <td  class="list_bg2" align="right">��ϸ����</td>
	  <td class="list_bg1"><%=Postern.getVodstbDeviceImportCount(dto.getBatchID())%></td>
	</tr>
	<tr>
		<td  class="list_bg2" align="right">��ע</td>
		<td  class="list_bg1" colspan="3"><%=dto.getComments()%></td>
	</tr>
	
</table>

<br>
<table width="100%"  border="0" align="center" cellpadding="5" cellspacing="1">
	<tr align="center">
	  <td>
			<table border="0" cellspacing="0" cellpadding="0">
			  <tr>	
			    <td width="20" ></td>		
			    <td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
			    <td><input name="Submit" type="button" class="button" value="��������" onclick="javascript:frm_submit()"></td>
			    <td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
			  	
			  	<td width="20" ></td>		  			   
				<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
				<td background="img/button_bg.gif"  height="20" >
				<a href="javascript:download(document.frmPost,'���豸���')" class="btn12">����</a></td>
				<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
				</tr>
			</table>     
 		</td>
  </tr>
</table>     
</form>
<iframe name=head width=100% height=640 frameborder=0 scrolling=no src="list_dev_into_depot.do?txtFrom=1&txtTo=10&txtBatchID=<%=dto.getBatchID()%>" ></iframe>  
 
