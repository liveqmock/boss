<%@ page import="com.dtv.oss.util.Postern"%>
<%@ page import="com.dtv.oss.dto.CatvTerminalDTO" %>

<%@ page import="java.util.*"%>

<script language=javascript>
<!--
function submit_update_catv_info(catvid){
	if(catvid==""){
		parent.document.frmPost.txtCATVInfo_ID.value='';
		parent.document.frmPost.txtCATVInfo_District.value='';
		parent.document.frmPost.txtCATVInfo_DetailAddress.value='';
		parent.document.frmPost.txtCATVInfo_PortNum.value='';
		parent.document.all("tbl_catv_info").style.display ="none";
		
		return;
	}
	parent.updselwaitlayer.style.display = "";
	document.formUpdateSelect.Action.value="U";
	document.formUpdateSelect.txtCATVID.value=catvid;
	document.formUpdateSelect.submit();
}

<%
	//有请求发生
	if(!(request.getParameter("Action")==null || "".equals(request.getParameter("Action")))){
		CatvTerminalDTO dto=Postern.getCatvTerminalByID(request.getParameter("txtCATVID"));
		String strCatvID="";
		String strDistrict="";
		String strDetailAddr="";
		String strPortNum="";
		
		if(dto!=null){
			strCatvID=dto.getId();
			strDetailAddr=dto.getDetailedAddress();
			strPortNum="" + dto.getPortNo();
			strDistrict=Postern.getDistrictDesc(dto.getDistrictID());
			if(strDistrict==null)
				strDistrict="";
			if(strCatvID==null)
				strCatvID="";
			if(strDetailAddr==null)
				strDetailAddr="";		
		}
%>
		parent.updselwaitlayer.style.display = "none";		
		//更新客户终端信息
		update_catv_info('<%=strCatvID %>','<%=strDistrict %>','<%=strDetailAddr %>','<%=strPortNum %>');
<%	
	}  
%>

function update_catv_info(catvid,district,detailaddr,portNum){
	if(catvid!=""){
		parent.document.frmPost.txtCATVInfo_ID.value=catvid;
		parent.document.frmPost.txtCATVInfo_District.value=district;
		parent.document.frmPost.txtCATVInfo_DetailAddress.value=detailaddr;
		parent.document.frmPost.txtCATVInfo_PortNum.value=portNum;
		
		parent.document.all("tbl_catv_info").style.display ="";
	}
	else{
		parent.document.frmPost.txtCATVInfo_ID.value='';
		parent.document.frmPost.txtCATVInfo_District.value='';
		parent.document.frmPost.txtCATVInfo_DetailAddress.value='';
		parent.document.frmPost.txtCATVInfo_PortNum.value='';
		
		parent.document.all("tbl_catv_info").style.display ="none";
	}
}
//-->
</script>
parent.updselwaitlayer.style.display = "none";
<form name="formUpdateSelect" method="post" action="update_catv_info.screen">
	<input type="hidden" name="Action" value="">
	<input type="hidden" name="txtCATVID" value="">
</form>
