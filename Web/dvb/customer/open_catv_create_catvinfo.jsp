<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl"%>
<%@ taglib uri="osstags" prefix="d"%>
<%@ taglib uri="bookmark" prefix="bk"%>

<%@ page import="com.dtv.oss.util.Postern"%>
<%@ page import="com.dtv.oss.dto.CatvTerminalDTO" %>
<%@ page import="com.dtv.oss.web.util.CommonKeys" %>
                 
<%
	String title = "模拟电视开户－终端信息";
	
	CatvTerminalDTO dto=Postern.getCatvTerminalByID(request.getParameter("txtCatvID"));
	String strCatvID="";
	String strDistrict="";
	String strDetailAddr="";
	String strPortNum="";
	boolean bExistCatv=false;	
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
		bExistCatv=true;	
	}
%>


<Script language=JavaScript>
<!--
//是否存在有效的终端,1存在，0不存在
var bExitCatv=0;
<%if(bExistCatv){%>
	bExitCatv=1;
<%}%>

function checkCustomerType(){
	if (check_Blank(document.frmPost.txtCustType, true, "用户类型"))
		return false;
	return true;	
}
 
function check_catvAddPortNum(){
	if(document.frmPost.txtAddPortNum.value==''){
		if(bExitCatv==1)
			document.frmPost.txtAddPortNum.value='0';
		else
			document.frmPost.txtAddPortNum.value='1';
	}
	if (!check_Num(document.frmPost.txtAddPortNum, true, "安装端口数"))
		return false;
	return true;	
}

function check_CatvInfo(){
	if(bExitCatv==0){
		if (check_Blank(document.frmPost.txtCatvTermType, true, "终端类型"))
			return false;
		//if (check_Blank(document.frmPost.txtFiberNodeID, true, "所属光节点"))
		//	return false;	
		//if(document.frmPost.txtFiberNodeID.value==''){
		//	alert("所属光节点不能为空");
		//	return false;
		//}
		if (check_Blank(document.frmPost.txtCableType, true, "线缆类型"))
			return false;	
		if (check_Blank(document.frmPost.txtBiDirectionFlag, true, "双向网改造标记"))
			return false;				
		return true;
	}
	return true;
}    

function frm_submit(){
  	if (check_CatvInfo()&&check_catvAddPortNum()) { 
  	  	document.frmPost.submit();
  	}  	
}
function query_fiber_node(){
	strFeatures = "dialogWidth=530px;dialogHeight=450px;center=yes;help=no";
	var strUrl="fiber_node_query.do?txtFrom=1&txtTo=10";
	var result=showModalDialog(strUrl,window,strFeatures);
	if (result!=null){
		var index = result.indexOf(";");
		if(index != -1){
			var resultID = result.substring(0,result.indexOf(";"));
			var resultDesc = result.substring(result.indexOf(";")+1,result.length);
		
	 	document.frmPost.txtFiberNodeID.value=resultID;
	 	document.frmPost.txtFiberNode.value=resultDesc;
	 	}
	 }
}
function back_submit(){
	document.frmPost.action='open_catv_create_info.screen';
	document.frmPost.submit();
}
//-->
</Script>


<form name="frmPost" method="post" action="open_catv_create_fee.do">
	
	<table border="0" align="center" cellpadding="0" cellspacing="5">
		<tr>
			<td><img src="img/list_tit.gif" width="19" height="19"></td>
			<td class="title">
				<%=title%>
			</td>
		</tr>
	</table>
	<br>
	<table width="98%" border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
		<tr>
			<td><img src="img/mao.gif" width="1" height="1"></td>
		</tr>
	</table>
	<br>

<%if(bExistCatv){%>
	<table width="820" border="0" align="center" cellpadding="4" cellspacing="1" class="list_bg">
		<tr>
			<td colspan="4" class="import_tit" align="center">已有终端信息</td>	
		</tr>
		<tr>
			<td valign="middle" class="list_bg2" align="right" width="17%">终端ID</td>
			<td width="33%" class="list_bg1">
				<input type="text" name="txtCATVInfo_ID" size="25" maxlength="20" value="<%=strCatvID%>" class="textgray" readonly >
			</td>
			<td valign="middle" class="list_bg2" align="right" width="17%">所在区域</td>
			<td width="33%" class="list_bg1">
				<input type="text" name="txtCATVInfo_District" size="25" maxlength="20" value="<%=strDistrict%>" class="textgray" readonly >
			</td>
		<tr>
		<tr>
			<td valign="middle" class="list_bg2" align="right" width="17%">详细地址</td>
			<td width="33%" class="list_bg1">
				<input type="text" name="txtCATVInfo_DetailAddress" size="25" value="<%=strDetailAddr%>" class="textgray" readonly >
			</td>
			<td valign="middle" class="list_bg2" align="right" width="17%">已有端口数</td>
			<td width="33%" class="list_bg1">
				<input type="text" name="txtCATVInfo_PortNum" size="25" maxlength="20" value="<%=strPortNum%>" class="textgray" readonly >
			</td>
		<tr>
	</table>
	<table width="820" border="0" align="center" cellpadding="4" cellspacing="1" class="list_bg">
		<tr>
			<td colspan="4" class="import_tit" align="center">新增端口</td>	
		</tr>
		<tr>
			<td valign="middle" class="list_bg2" align="right" width="17%">新安装端口数*</td>
			<td width="33%" class="list_bg1">
				<input type="text" name="txtAddPortNum" size="25" maxlength="20" value="<tbl:writeparam name="txtAddPortNum" />" class="text" >
			</td>
			<td valign="middle" class="list_bg2" align="right" width="17%"></td>
			<td width="33%" class="list_bg1"></td>
		<tr>
	</table>
<%}else{%>
	<table width="820" border="0" align="center" cellpadding="4" cellspacing="1" class="list_bg">
		<tr>
			<td colspan="4" class="import_tit" align="center">新增端口</td>
		</tr>
		<tr>
			<td valign="middle" class="list_bg2" align="right" width="17%">终端类型*</td>
			<td width="33%" class="list_bg1">
				<d:selcmn name="txtCatvTermType" mapName="SET_A_CATVTERMTYPE" match="txtCatvTermType" width="23"/>
			</td>
			<td valign="middle" class="list_bg2" align="right" width="17%">所属光节点</td>
			<td width="33%" class="list_bg1">
				<input name="txtFiberNodeID" type="hidden" size="25" value="<tbl:writeparam name="txtFiberNodeID" />"  >
                		<input name="txtFiberNode" readonly type="text" size="25" value="<tbl:writeparam name="txtFiberNode" />"   class="text">
                		<input name="selFiberButton" type="button" class="button" value="选择" onClick="javascript:query_fiber_node()">
			</td>
		<tr>
		<tr>
			<td valign="middle" class="list_bg2" align="right" width="17%">线缆类型*</td>
			<td width="33%" class="list_bg1">
				<d:selcmn name="txtCableType" mapName="SET_A_CABLETYPE" match="txtCableType" width="23" />
			</td>
			<td valign="middle" class="list_bg2" align="right" width="17%">双向网是否改造*</td>
			<td width="33%" class="list_bg1">
				<d:selcmn name="txtBiDirectionFlag" mapName="SET_G_YESNOFLAG" width="23" match="txtBiDirectionFlag" />
			</td>
		<tr>
		<tr>
			<td valign="middle" class="list_bg2" align="right" width="17%">安装端口数*</td>
			<td width="33%" class="list_bg1">
				<input type="text" name="txtAddPortNum" size="25" maxlength="20" value="<tbl:writeparam name="txtAddPortNum" />" class="text" >
			</td>
			<td valign="middle" class="list_bg2" align="right" width="17%"></td>
			<td width="33%" class="list_bg1"></td>
		<tr>
	</table>
<%}%>
	<br/>
	<table align="center" border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td><img src="img/button2_r.gif" width="22" height="20"></td>
			<td background="img/button_bg.gif" height="20"><a href="javascript:back_submit()" class="btn12">上一步</a></td>
			<td><img src="img/button2_l.gif" width="13" height="20"></td>

			<td width="20"></td>
			<td><img src="img/button_l.gif" width="11" height="20"></td>
			<td><input name="next" type="button" class="button" onClick="frm_submit()" value="下一步"></td>
			<td><img src="img/button_r.gif" width="22" height="20"></td>
		</tr>
	</table>
	<br/><br/>
	
	<input type="hidden" name="ProductList" value="9999" >   
	<input type="hidden" name="txtActionType" value="<%=CommonKeys.CACULATE_OPEN_CATV_FEE%>" >   
	
	<tbl:hiddenparameters pass="txtCatvID/txtGroupBargainDetailNo/txtReferBookingSheetID/txtCsiCreateReason/txtCsiType" />
      	<tbl:hiddenparameters pass="txtCustType/txtGender/txtName/txtBirthday/txtCardType/txtCardID/txtTelephone/txtMobileNumber/txtEmail" />
      	<tbl:hiddenparameters pass="txtContractNo/txtCounty/txtDetailAddress/txtPostcode/txtOpenSourceType/txtOpenSourceID/txtComments/txtLoginID/txtLoginPwd" />
      	<tbl:hiddenparameters pass="txtPreferedTime/txtPreferedDate/txtSocialSecCardID/txtNationality/txtFaxNumber" />
      	<tbl:hiddenparameters pass="txtBillDetailAddress/txtBillPostcode/txtbillCounty/txtMopID/txtBankAccount/txtBankAccountName/txtAccountType/txtAccountName" />
      	<tbl:hiddenparameters pass="txtBuyMode/OpenFlag/txtIsInstall/txtForceDepostMoney/sspanList" />
      	<tbl:hiddenparameters pass="ProductList/CampaignList" />
      	<tbl:hiddenparameters pass="DeviceProductIds/DeviceClassList/TerminalDeviceList/phoneNo/itemID/txtGrade/txtcsiPayOption/txtServiceCodeList" />
      	<tbl:hiddenparameters pass="OpenCampaignList/MutiProductList/SingleProductList/GeneralCampaignList" />   	
      	<tbl:dynamicservey serveyType="M"  showType="hide" />
      	
</form>

