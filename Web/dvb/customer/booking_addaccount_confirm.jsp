<%@ page import="com.dtv.oss.util.Postern,com.dtv.oss.web.util.CommonKeys"%>
<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="osstags" prefix="d" %>
<%@ taglib uri="bookmark" prefix="bk" %>

<%@ page import="com.dtv.oss.util.Postern,
                 java.util.*" %>
<%@ page import="com.dtv.oss.service.util.CsrBusinessUtility" %>
<%@ page import="com.dtv.oss.web.util.WebUtil" %>
<%@ page import="com.dtv.oss.util.Postern" %>
<%@ page import="com.dtv.oss.web.util.CommonKeys" %>

<%
String timeFlag="SET_C_CSIPREFEREDTIME";
Map mapPreferedTime = Postern.getAllPreferedTime(timeFlag);
pageContext.setAttribute("AllPREFEREDTIME", mapPreferedTime);

String csiType =request.getParameter("txtCsiType");
String lstProdPkg = request.getParameter("ProductList");
String lstCamp = request.getParameter("CampaignList");

String phoneNo = request.getParameter("phoneNo");
if(phoneNo==null)phoneNo="";

String strPkgName = "";
String strCampName = "";
int iTmp;
if (WebUtil.StringHaveContent(lstProdPkg))
{
    String[] aProdPkg = lstProdPkg.split(";");
    for (int i=0; i<aProdPkg.length; i++)
    {
       iTmp = WebUtil.StringToInt(aProdPkg[i]);
       strPkgName = strPkgName + String.valueOf(i+1) + ":" + Postern.getPackagenameByID(iTmp)+" ";
    }
}    

if (WebUtil.StringHaveContent(lstCamp))
{
    String[] aCamp = lstCamp.split(";");
    for (int i=0; i<aCamp.length; i++)
    {
       iTmp = WebUtil.StringToInt(aCamp[i]);
       strCampName = strCampName + String.valueOf(i+1) + ":" + Postern.getCampaignNameByID(iTmp)+" ";
    }   
}   
%>
<Script language=JavaScript>

function frm_submit() {
	if(check_transfertype()){
		document.frmPost.confirm_post.value='true';
		document.frmPost.action= "booking_addaccount_create.do";   
		document.frmPost.submit();
   	document.getElementById('prev').disabled=true;
   	document.getElementById('next').disabled=true;		
	}
}
function frm_back(){
	
	document.frmPost.action= "booking_addacount_book.screen";
	if("<%=phoneNo%>"!="")
	{
		document.frmPost.action= "book_select_phone_number.screen?txtPage=bookingAddAccountBook";
	}
	document.frmPost.submit();
}
function check_transfertype(){
	var str=document.frmPost.selType!=null?document.frmPost.selType.value:""; 
		if(  'auto'==str && (''==document.frmPost.txtAutoNextOrgID.value||0==document.frmPost.txtAutoNextOrgID.value)){
			alert("无法匹配合适的处理部门，请手工流转并指定流转部门！");
			return false;
		}
		if('manual'==str){
			if(''==document.frmPost.txtNextOrgID.value){
			       	alert("手工流转需指定流转部门！");
			       	return false;
		       	}
		}
	document.frmPost.transferType.value=str;
	return true;
}
function mod_organization(){
	var strFeatures = "width=480px,height=240px,resizable=no,toolbar=no,scrollbars=yes,status=yes";
	window.open("transfer_org_list.do?strRole=I","流转部门",strFeatures);
}
</script>
<div id="updselwaitlayer" style="position:absolute; left:650px; top:170px; width:250px; height:24px; display:none">
    <table width="100%" border="0" cellspacing="1" cellpadding="3">
        <tr>
          <td width="100%"><div align="center">
          <font size="2">
          正在获取内容。。。
          </font>
          </td>
        </tr>
    </table>
</div>

<form name="frmPost" method="post" action="booking_addaccount_create.do" >
<input type="hidden" name="transferType" value="<tbl:writeparam name="transferType"/>"> 
<table  border="0" align="center" cellpadding="0" cellspacing="10" >
  <tr>
    <td><img src="img/list_tit.gif" width="19" height="19"></td>
    <td class="title">预约增机的预约</td>
  </tr>
</table>
  <br>
  <table width="780"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
	<tr>
	  <td><img src="img/mao.gif" width="1" height="1"></td>
	</tr>
   </table>
   <br>
   <table width="780"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
  <tr>
    <td class="list_bg2" width="17%"  align="right">联系人*</td>
    <td class="list_bg1" width="33%"  align="left"><tbl:writeparam name="txtContactPerson" /></td>
    <td class="list_bg2" width="17%"  align="right">联系电话*</td>
    <td class="list_bg1" width="33%"  align="left"><tbl:writeparam name="txtContactPhone" /></td>
  </tr>
  <tr>
    <td class="list_bg2" width="17%" align="right">预约上门日期*</td>
    <td class="list_bg1" width="33%" align="left"><tbl:writeparam name="txtPreferedDate" /></td>
    <td class="list_bg2" width="17%" align="right">预约上门时间段*</td>    
    <td class="list_bg1" width="33%" align="left"><d:getcmnname typeName="SET_C_CSIPREFEREDTIME" match="txtPreferedTime"/></td>
  </tr>
    <tr>
    <td class="list_bg2" width="17%" align="right">有效付费帐户*</td>
    <td class="list_bg1" width="33%" align="left"><tbl:writeparam name="txtAccount" /></td>
    <tbl:csiReason csiType="<%=csiType%>" name="txtCsiCreateReason" match="txtCsiCreateReason" action="N" forceDisplayTD="true" showType="label"  tdWordStyle="list_bg2" tdControlStyle="list_bg1" />
  </tr>
  </table>

<table align="center" width="780" border="0" cellspacing="1" cellpadding="3" class="list_bg">
   <tr> 
       <td colspan="2" class="import_tit" align="center"><font size="3">产品包购买信息</font></td>
   </tr>
	 <tr>
	  	<td class="list_bg2" width="17%"><div align="right">产品包</div></td>
	    <td class="list_bg1"><%=strPkgName%></td>
	 </tr>  
	 <tr>
		 <td class="list_bg2" width="17%"><div align="right">优惠活动</div></td>
	   <td class="list_bg1"><%=strCampName%></td>
	</tr>
	<%if(phoneNo != null && !"".equals(phoneNo)){%>
	<tr>
		 <td class="list_bg2" width="17%"><div align="right">电话号码</div></td>
	   <td class="list_bg1"><%=phoneNo%></td>
	</tr> 
	<%}%> 
	<tr>
		 <td colspan="2" >
		    <tbl:CommonFeeAndPaymentInfo includeParameter="Fee" payCsiType="<%=CommonKeys.CUSTSERVICEINTERACTIONTYPE_BU%>" acctshowFlag ="false" /> 
		 </td>
  </tr>
  <%	String systemSettingPrecise = Postern.getSystemsettingValueByName("SET_W_PRECISEWORK");
  	if(systemSettingPrecise!=null&&("Y").equals(systemSettingPrecise)){
        String systemSettingValue = Postern.getSystemsettingValueByName("SET_W_AUTOTRANSFER");
	if(systemSettingValue == null || ("N").equals(systemSettingValue)){%>
	<table width="100%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
			<tr> 
			    <td colspan="2" class="import_tit" align="center"><font size="3">工单手工流转</font><input type="hidden" name="selType" value="manual" ></td>
		        </tr>  
			<tr>
			    <td class="list_bg2"><div align="right">流转部门</td>
			    <td class="list_bg1" > 
				 <input type="hidden" name="txtNextOrgID" value="<tbl:writeparam name="txtNextOrgID" />">
			    	 <input type="text" name="txtOrgDesc" size="22" maxlength="50" readonly value="<tbl:WriteOrganizationInfo property="txtNextOrgID" />" class="text">
			    	 <input name="selOrgButton" type="button" class="button" value="选择" onClick="javascript:sel_organization('C,B,D,S,O','txtNextOrgID','txtOrgDesc')">
			    </td>		
			</tr>		
	</table> 
    <%}else if(systemSettingValue != null && ("Y").equals(systemSettingValue)){
       String custid = (request.getParameter("txtCustomerID") == null ? "0":request.getParameter("txtCustomerID"));
       int autoOrgid=Postern.getAutoNextOrgID("I",null,null,null,null,Postern.getDistrictIDByCustomerID(custid),0);%>
	<table width="100%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
			<tr> 
			    <td colspan="2" class="import_tit" align="center"><font size="3">工单流转</font><input type="hidden" name="selType" value="auto" ></td>
		        </tr>  
			<tr>
			    <td class="list_bg2"><div align="right">流转部门</td>
			    <td class="list_bg1" > 
			 	<input type="hidden" name="txtAutoNextOrgID" value="<%=autoOrgid%>">
		    		<input type="text" name="txtNextOrgName" size="22" maxlength="50" readonly value="<%=Postern.getOrganizationDesc(autoOrgid)%>" class="text">
		    		<input name="selOrgButton" type="button" class="button" value="选择" onClick="javascript:mod_organization()">
			    </td>		
			</tr>		
	</table>
    <%}}%>  
</TABLE> 

<input type="hidden" name="txtActionType" value="<%=CommonKeys.OPEN_BOOKINGUSER_SERVICE_ACCOUNT_FORBOOKING%>" />
<input type="hidden" name="func_flag" value="117">
<input type="hidden" name="confirm_post" value="">
<input type="hidden" name="<%=com.dtv.oss.web.util.WebKeys.PAGE_TOKEN_KEY%>" value="<tbl:writeparam name="<%=com.dtv.oss.web.util.WebKeys.PAGE_TOKEN_KEY%>" />" >   

<tbl:hiddenparameters pass="ProductList/CampaignList" />
<tbl:hiddenparameters pass="OpenCampaignList/MutiProductList/SingleProductList/GeneralCampaignList" /> 
<tbl:hiddenparameters pass="txtCustomerID/txtAccount/txtContactPerson/txtContactPhone/txtPreferedDate/txtPreferedTime" />
<tbl:hiddenparameters pass="phoneNo/itemID/txtCsiCreateReason/txtCsiType/txtCustType" />

</form> 
<br>
<table align="center"  border="0" cellspacing="0" cellpadding="0">
	<tr>
	   <td width="20" ></td>
	   <td width="11" height="20"><img src="img/button2_r.gif" width="22" height="20"></td>
	   <td height="20">
		 <input  id="prev" name="prev" type="button" class="button" onClick="javascript:frm_back()" value="上一步">
	   </td>
	   <td><img src="img/button2_l.gif" width="11" height="20"></td> 
	   <td width="20" >&nbsp;</td> 
	   <td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
	   <td height="20">
		 <input id="next" name="next" type="button" class="button" onClick="javascript:frm_submit()" value="确  认">
	   </td>
	   <td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>

	</tr>
</table>

