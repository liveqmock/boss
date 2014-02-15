<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="osstags" prefix="d" %>
<%@ taglib uri="bookmark" prefix="bk" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="operator" prefix="op" %>
<%@ page import="com.dtv.oss.dto.DeviceClassDTO" %>
<%@ page import="com.dtv.oss.web.util.CommonKeys" %>
<%@ page import="com.dtv.oss.util.Postern" %>
<%@ page import="com.dtv.oss.web.util.WebUtil" %>
<%@ page import="com.dtv.oss.service.util.CsrBusinessUtility" %>

<%
String ProductList =(request.getParameter("ProductList")==null) ? "" : request.getParameter("ProductList");
String CampaignList =(request.getParameter("CampaignList")==null) ? "" : request.getParameter("CampaignList");
String install = "";
if(request.getParameter("txtIsInstall") != null)
	install = request.getParameter("txtIsInstall");
else
	install = CommonKeys.CUSTSERVICEINTERACTIONIN_STYPE_SELFINSTALL;

String deviceClassList=(request.getParameter("DeviceClassList") == null) ? "" :request.getParameter("DeviceClassList");  ; 
String terminalDeviceList=(request.getParameter("TerminalDeviceList") == null) ? "" :request.getParameter("TerminalDeviceList");
String deviceProductIds=(request.getParameter("DeviceProductIds")==null) ? "" :request.getParameter("DeviceProductIds");
String sspanList =(request.getParameter("sspanList") == null) ? "" :request.getParameter("sspanList");
String txtID=request.getParameter("txtID");

String [] aTerminalDevice=terminalDeviceList.split(";");
String [] aTerminalProductId =deviceProductIds.split(";");
String [] asspan=sspanList.split(";");
double txtForceDepostMoney =(double) ((request.getParameter("txtForceDepostMoney")==null) ? 0.0 :Double.parseDouble(request.getParameter("txtForceDepostMoney")));    

String txtServiceCodeList = request.getParameter("txtServiceCodeList");
String [] phoneNo = null;
if(txtServiceCodeList!=null)
{
	phoneNo=txtServiceCodeList.split(",");
}
%>

<div id="updselwaitlayer" style="position:absolute; left:650px; top:150; width:250px; height:24px; display:none"></div>

<iframe SRC="get_mac_intermac.screen" name="FrameVendorDeviceUS" width="0" height="0" frameborder="0" scrolling="0"> </iframe>

<form name="frmPost" method="post" action="book_account_choose_device.do" >
	<input type="hidden" name="checkSelect" value="1" >
<table  border="0" align="center" cellpadding="0" cellspacing="10" >
  <tr>
    <td><img src="img/list_tit.gif" width="19" height="19"></td>
    <td class="title">新增业务帐户－－选择设备</td>
  </tr>
</table>
  <br>
  <table width="780"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
	<tr>
	  <td><img src="img/mao.gif" width="1" height="1"></td>
	</tr>
   </table>
   <br>
  <table width="780" border="0" align="center" cellpadding="4" cellspacing="1" class="list_bg">
	<tr>
	  <td width="98%" class="import_tit" align="center">选择相应硬件设备</td>
	</tr>
  </table>
      
 <table align="center" width="780" border="0" cellspacing="1" cellpadding="3" class="list_bg" >
 		<tr>
  	  <tbl:csiReason csiType="<%=CommonKeys.CUSTSERVICEINTERACTIONTYPE_BU%>" name="txtCsiCreateReason" action="N" showType="text"  checkScricptName="check_csiReason()" tdWordStyle="list_bg2" tdControlStyle="list_bg1" tdControlColspan="3" match="txtCsiCreateReason"  controlSize="25" />
    </tr>
<%
   String divID="";
   String inputID="";
   if (terminalDeviceList.equals("")) { 
      deviceProductIds ="";
      deviceClassList ="";
%>
<lgc:bloop requestObjectName="ResponseFromEJBEvent" item="oneline">
<%
		int deviceProductId=((Integer)pageContext.getAttribute("oneline")).intValue();
    DeviceClassDTO deviceClassDto =Postern.getDeviceClassByProductID(deviceProductId);
    if (deviceProductIds.equals("")){
    	  deviceProductIds = deviceProductIds + String.valueOf(deviceProductId) ;
    	  deviceClassList=deviceClassList + deviceClassDto.getDeviceClass();
    }
    else {
  	    deviceProductIds =deviceProductIds+";"+ deviceProductId;
  	    deviceClassList=deviceClassList+";"+ deviceClassDto.getDeviceClass();;  
  	}
  	divID="div"+deviceClassDto.getDeviceClass();
    inputID="input"+deviceClassDto.getDeviceClass();
%>
	  <tr>
	     <td width="20%" align="right" class="list_bg2"><%=deviceClassDto.getDescription()%>序列号*</td>
	     <td  width="80%" align="left" class="list_bg1">
	 	      <input type="text" name="<%=deviceClassDto.getDeviceClass()%>" size="25" maxlength="20"  value="" class="text" onchange="javascript:getMacInmac('<%=deviceClassDto.getDeviceClass()%>');">
	        <input type=button value="选择" onclick="javascript:query_device_item('<%=deviceProductId%>','<%=deviceClassDto.getDeviceClass()%>','',this);" class="button">
	        <input name="<%=inputID%>" title="inputID" type="hidden" >
	        <span  id="<%=divID%>" name="sspan"></span>
	     </td>
	 </tr>
</lgc:bloop>	
<% 
} else {
      for (int i=0; i<aTerminalDevice.length; i++){
         DeviceClassDTO deviceClassDto =Postern.getDeviceClassByProductID(Integer.parseInt(aTerminalProductId[i]));
         String devicemodel =Postern.getTerminalDeviceBySerialNo(aTerminalDevice[i]).getDeviceModel();
         divID="div"+deviceClassDto.getDeviceClass();
         inputID="input"+deviceClassDto.getDeviceClass();
%>
	 <tr>
	    <td width="20%" align="right" class="list_bg2"><%=deviceClassDto.getDescription()%>序列号*</td>
	    <td width="80%" align="left" class="list_bg1">
		    <input type="text" name="<%=deviceClassDto.getDeviceClass()%>" size="25" maxlength="20"  value="<%=aTerminalDevice[i]%>" class="text" onchange="javascript:getMacInmac('<%=deviceClassDto.getDeviceClass()%>');">
	      <input type=button value="选择" onclick="javascript:query_device_item('<%=aTerminalProductId[i]%>','<%=deviceClassDto.getDeviceClass()%>','<%=devicemodel%>',this);" class="button">
	      <input name="<%=inputID%>" title="inputID" type="hidden" value="<%=asspan[i]%>">
	   		<span  id="<%=divID%>" name="sspan"><%=asspan[i]%></span>
	    </td>
	</tr>
<%  }
}
%>
   <tr>
     <td width="20%" align="right" class="list_bg2">押金*</td>
	   <td width="80%" align="left" class="list_bg1">
	      <input type="text" name="txtForceDepostMoney" size="25" maxlength="10" value="<%=WebUtil.bigDecimalFormat(txtForceDepostMoney)%>" class="text" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	   </td>
   </tr>

 </table>  
  <input type="hidden" name="txtID" value="<%=txtID%>">
  <input type="hidden" name="DeviceProductIds" value="<%=deviceProductIds%>" > 
  <input type="hidden" name="DeviceClassList" value="<%=deviceClassList%>">
  <input type="hidden" name="TerminalDeviceList" value="">
  <input type="hidden" name="sspanList" value="">
  <input type="hidden" name="txtHiddenMacInmac" value="" >
<%
//T_Service.ServiceID == 3，语音业务，需要选择电话号码
boolean bNeedPhoneNo = false;
String campTempList = "";
if(CampaignList != null && CampaignList.length()>0 && ';'==CampaignList.charAt(CampaignList.length()-1))
	campTempList = CampaignList.substring(0,CampaignList.length()-1);
String CampaignProList = Postern.getBundle2CampaignPackageColStr(campTempList);
String allProductList = CampaignProList+ProductList;
System.out.println("ProductList=============="+ProductList);
System.out.println("allProductList=============="+allProductList);
if(allProductList != null && allProductList.length()>0 && ';'==allProductList.charAt(allProductList.length()-1))
	allProductList = allProductList.substring(0,allProductList.length()-1);
String strServiceIDs = Postern.getServiceIDByProductPackageIDs(allProductList);
System.out.println("strServiceIDs = " + strServiceIDs);
int n=0;
String itemID="";
if(strServiceIDs != null && !("".equals(strServiceIDs)))
{
	String serviceIDs[] = strServiceIDs.split(";");
	for(int i=0;i<serviceIDs.length;i++)
	{
    System.out.println("serviceIDs[i] = " + serviceIDs[i]);	
		if("3".equals(serviceIDs[i]))
			bNeedPhoneNo = true;
	}
	
	if(bNeedPhoneNo)
	{
%>      
	<table width="780" border="0" align="center" cellpadding="4" cellspacing="1" class="list_bg">
		<tr>
			<td width="98%" class="import_tit" align="center">选择电话号码</td>
		</tr>
	</table>
	<table width="95.5%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
		<tr>
			<td class="list_bg2">
			<div align="right">号码级别</div>
			</td>
			<td class="list_bg1">
				<d:selcmn name="txtGrade" mapName="SET_R_PHONENOGRADE" match="txtGrade" width="23" />
			</td>
		</tr>
		<tr>
			<td class="list_bg2"><div align="right">电话号码</div></td>
			<td class="list_bg1">
				<%
					String phone = request.getParameter("phoneNo");
					if(phone==null && phoneNo!=null && phoneNo.length>n)
						phone = phoneNo[n++];
					if(phone==null || "null".equals(phone))
						phone="";
						//查出itemID
					itemID = Postern.getResourcePhoneNoItemIDWithPhoneNo(phone)+"";
				%>
			<input type="text" name="phoneNo" size="25" maxlength="50" value="<%=phone%>" class="text" onChange="javascript:setSelectFalse()" >
			<input name="Submit" type="button" class="button" value="查询" onclick="javascript:phoneNo_Search()"> 支持模糊查询，“_”代表一位，“%”代表多位。</td>
			
		</tr>
	</table>
	<input type="hidden" name="itemID" value="<tbl:writeparam name="itemID" />">
<%	}
}%> 


   
     <table align="center" width="100%" border="0" cellspacing="1" cellpadding="3" class="list_bg" id="mnu1" style="display:">
         <!--
         <tr> 
           <td colspan="4" class="import_tit" align="center"><font size="3">产品属性配置信息</font></td>
         </tr>
         -->
		     <tr ><td width="17%"></td><td width="33%"></td><td width="17%"></td><td width="33%"></td></tr>

<%

	String strGroupBargainDetailNo = request.getParameter("txtGroupBargainDetailNo");
	String lstProdPkg = request.getParameter("ProductList");
    String lstCamp = request.getParameter("CampaignList");
    int iTmp;           
    Collection colPackage = new ArrayList();
    if (WebUtil.StringHaveContent(strGroupBargainDetailNo)){
    	CsrBusinessUtility.fillPackageColByGroupBargainDetailNo(colPackage, strGroupBargainDetailNo);
    }
       
	//产品属性配置
	String packageList="";
    if (WebUtil.StringHaveContent(strGroupBargainDetailNo)){
    	//获取团购的产品包
        Iterator it = colPackage.iterator();
        while (it.hasNext()){
            Integer iPkgId = (Integer)it.next();
            packageList+=iPkgId.toString()+";";
%>
	<tbl:productproperty serveyName="txtProductProperty" packageID="<%=iPkgId.toString()%>"  showType="text" tdWordStyle="list_bg2" tdControlStyle="list_bg1"  controlSize="25" headStyle="list_head"/>
<%            
        }
    }
    else{
		String campaignList ="";
		if(lstCamp != null && lstCamp.length()>0 && ';'==lstCamp.charAt(lstCamp.length()-1)){
			campaignList = lstCamp.substring(0,lstCamp.length()-1);
		}
        String campaignProList = Postern.getBundle2CampaignPackageColStr(campaignList);
		allProductList = campaignProList+lstProdPkg;
		if (WebUtil.StringHaveContent(allProductList)){
			String[] aProdPkg = allProductList.split(";");
			for (int i=0; i<aProdPkg.length; i++){
				iTmp = WebUtil.StringToInt(aProdPkg[i]);
				String strPkgID = Integer.toString(iTmp);
				packageList+=strPkgID+";";
%>

	<tbl:productproperty serveyName="txtProductProperty" packageID="<%=strPkgID%>"  showType="text" tdWordStyle="list_bg2"  tdControlStyle="list_bg1" controlSize="25" headStyle="list_head" />
<%               
            }
        }    
    }

%>         
	</table>
	

<br>
  <table width="780"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
	<tr>
	  <td><img src="img/mao.gif" width="1" height="1"></td>
	</tr>
   </table>
<br>
<table align="center"  border="0" cellspacing="0" cellpadding="0">
	<tr>
	   <td width="20" ></td>
	   <td width="11" height="20"><img src="img/button2_r.gif" width="22" height="20"></td>
	   <td height="20">
		 <input name="prev" type="button" class="button" onClick="javascript:frm_back()" value="上一步">
	   </td>
	   <td><img src="img/button2_l.gif" width="11" height="20"></td> 
	   <td width="20" >&nbsp;</td> 
	   <td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
	   <td height="20">
		 <input name="next" type="button" class="button" onClick="javascript:frm_submit()" value="下一步">
	   </td>
	   <td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>

	</tr>
</table>

<tbl:hiddenparameters pass="txtCustomerID/txtAccount/txtServiceCodeList/txtCustType/txtOpenSourceType" />
<tbl:hiddenparameters pass="OpenCampaignList/MutiProductList/SingleProductList/GeneralCampaignList" /> 
<input type="hidden" name="txtActionType" value="<%=CommonKeys.CHECK_HARDPRODUCTINFO%>" />
<input type="hidden" name="ProductList" value="<%=ProductList%>">
<input type="hidden" name="CampaignList" value="<%=CampaignList%>">
<input type="hidden" name="txtDistrictID" value="<tbl:writeparam name="txtDistrictID" />">
<input type="hidden" name="<%=com.dtv.oss.web.util.WebKeys.PAGE_TOKEN_KEY%>" value="<tbl:writeparam name="<%=com.dtv.oss.web.util.WebKeys.PAGE_TOKEN_KEY%>" />" >   

</form>
<Script language=JavaScript>
 <!--
   
function check_frm()
{
	var submitFlag=true;
	var terminalDeviceList="";
	var hasPhoneNo = false;
	var sspanList="";
	//检查数据完整性和给TerminalDeviceList赋值
	for(i=0;i<document.frmPost.length;i++)
	{
		var element=document.frmPost.elements[i];
		if(element.title=="inputID"){
			sspanList=sspanList+element.value+" ;";
		}
		if((""+element.type)=="text" && (""+element.name)!="txtPreferedDate")
		{
			if(element.name == "phoneNo")
				hasPhoneNo = true;
			if(element.value=="")
			{
				alert("输入不完整，请重新检查！");
				element.focus();
				submitFlag=false;
				break;
			}
			else
			{
				if(terminalDeviceList!="")
					terminalDeviceList=terminalDeviceList+";";
			        if ((""+element.name !="txtContactPhone" ) 
			             && (""+element.name !="txtForceDepostMoney")
			             && (""+element.name !="txtContactPerson")
			             && element.name!="phoneNo"){
			      if(element.name.indexOf("txtProductProperty") == -1){ //过滤产品属性element      
				   terminalDeviceList=terminalDeviceList+element.value;
				  }
				}
			}
		}
	 }
	 if(hasPhoneNo)
	 {
		if(!phoneNo_check())
			return false;
	 }
	 document.frmPost.TerminalDeviceList.value=terminalDeviceList;
	
	 if (check_Blank(document.frmPost.txtForceDepostMoney, true, "押金"))
	    return false;
       
   if (!check_Float(document.frmPost.txtForceDepostMoney,true,"押金"))
      return false;
  
   if(!check_txtcsiReason()) return false;   
   document.frmPost.sspanList.value=sspanList;	
          
	 return submitFlag;
}

function frm_submit()
{    //检查所有的设备是否填写完整

	if (check_frm()) document.frmPost.submit();
}
function frm_back(){
    document.frmPost.txtActionType.value ="";
	  document.frmPost.action="book_create_account.screen?txtCsiType=BU";
	  document.frmPost.submit();
}
//deviceClass为查找设备类别，inputObject为输入框对象
function query_device_item(txtProductId,deviceClass,txtDevicemodel,inputObject){
	 if(!check_txtcsiReason())  return;
	 strFeatures = "dialogWidth=530px;dialogHeight=450px;center=yes;help=no";
	 txtCsiCreateReason="";
   if(document.frmPost.txtCsiCreateReason)
      txtCsiCreateReason=document.frmPost.txtCsiCreateReason.value;
	 var result=showModalDialog("list_terminate_device.do?txtFrom=1&txtTo=10&txtStatus=W&txtDeviceClass="+deviceClass
	                           +"&txtProductId="+txtProductId+"&txtDevicemodel="+txtDevicemodel
	                           +"&txtCsiType=BU&txtCsiCreateReason="+txtCsiCreateReason,window,strFeatures);
	 if (result!=null) document.frmPost.elements[deviceClass].value=result;
	 getMacInmac(deviceClass);
}

function check_txtcsiReason(){
	 if(document.frmPost.txtCsiCreateReason){
	 	  return check_csiReason();
   } else{
	 	  return true;
	 }     
}

function changePrefed(){
   var selfInstall ="<%=CommonKeys.CUSTSERVICEINTERACTIONIN_STYPE_SELFINSTALL%>" ;
   if (document.frmPost.txtIsInstall.value ==selfInstall){
      document.all("prefered").style.display ="none";
      document.frmPost.txtPreferedDate.value ="";
      document.frmPost.txtPreferedTime.value ="";
   } else{
      document.all("prefered").style.display ="";
   }
 
}
function phoneNo_Search()
{
  var  phoneNo =document.frmPost.phoneNo.value;
  var  districtID =document.frmPost.txtDistrictID.value;
  var  grade=document.frmPost.txtGrade.value;  
  var  strFeatures = "width=600px,height=500px,resizable=no,toolbar=no,scrollbars=yes,status=yes";
  window.open("phoneNo_query.do?txtFrom=1&txtTo=10&phoneNo="+phoneNo+"&districtID="+districtID+"&grade="+grade,"电话号码查询",strFeatures);

}

function getMacInmac(deviceClass){
	 var inputid="input"+deviceClass;
	 var divid="div"+deviceClass;
	 document.frmPost.elements[inputid].value="";
	 document.getElementById(divid).innerHTML="";
   document.FrameVendorDeviceUS.submit_update_vendor_device(document.frmPost.elements[deviceClass].value,deviceClass);
}

function phoneNo_check()
{
	if(document.frmPost.checkSelect.value != "1")
	{
			alert("电话号码必须选择！");
		return false;
	}
	if(document.frmPost.itemID.value =="")
	{
		document.frmPost.itemID.value = "<%=itemID%>";
	}
	if(document.frmPost.itemID.value =="" || document.frmPost.itemID.value =="0")
	{
		alert("电话号码必须选择！");
		return false;
	}
	//alert(document.frmPost.itemID.value);
	return true;
} 
function setSelectFalse()
{
	document.frmPost.checkSelect.value = "0";
}
-->
</Script>