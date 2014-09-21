<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="osstags" prefix="d" %>
<%@ taglib uri="bookmark" prefix="bk" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="operator" prefix="op" %>

<%@ page import="java.util.*" %>
<%@ page import="com.dtv.oss.dto.DeviceClassDTO" %>
<%@ page import="com.dtv.oss.dto.CustomerDTO" %>
<%@ page import="com.dtv.oss.web.util.CommonKeys" %>
<%@ page import="com.dtv.oss.util.Postern" %>
<%@ page import="com.dtv.oss.service.commandresponse.CommandResponseImp" %>
<%@ page import="com.dtv.oss.web.util.WebUtil" %>
<%@ page import="com.dtv.oss.service.util.CsrBusinessUtility" %>

<%
String doubleInput =Postern.getSystemsettingValueByName("SET_V_DEVICESDOUBLEINPUT");
if(doubleInput==null&&"".equalsIgnoreCase(doubleInput))
	doubleInput="";
String txtCustomerID = request.getParameter("txtCustomerID");
CustomerDTO customerDTO = Postern.getCustomerByID(Integer.parseInt(txtCustomerID));
String custName = customerDTO.getName();              //联系人
String custTelephone = customerDTO.getTelephone();    //联系电话

String ProductList = request.getParameter("ProductList");
if(ProductList == null) ProductList="";
String CampaignList = request.getParameter("CampaignList");
if(CampaignList == null) CampaignList="";
String install = "";
if(request.getParameter("txtIsInstall") != null)

	install = request.getParameter("txtIsInstall");
else
	install = CommonKeys.CUSTSERVICEINTERACTIONIN_STYPE_SELFINSTALL;

String deviceClassList=(request.getParameter("DeviceClassList") == null) ? "" :request.getParameter("DeviceClassList");  ; 
String terminalDeviceList=(request.getParameter("TerminalDeviceList") == null) ? "" :request.getParameter("TerminalDeviceList");
String deviceProductIds=(request.getParameter("DeviceProductIds")==null) ? "" :request.getParameter("DeviceProductIds");
String sspanList=(request.getParameter("sspanList") == null) ? "" :request.getParameter("sspanList");

String [] aTerminalDevice=terminalDeviceList.split(";");
String [] aTerminalProductId =deviceProductIds.split(";");
String [] asspan=sspanList.split(";");


double txtForceDepostMoney =(double) ((request.getParameter("txtForceDepostMoney")==null) ? 0.0 :Double.parseDouble(request.getParameter("txtForceDepostMoney")));    

%>
<div id="updselwaitlayer" style="position:absolute; left:650px; top:150; width:250px; height:24px; display:none"></div>

<iframe SRC="get_mac_intermac.screen" name="FrameVendorDeviceUS" width="0" height="0" frameborder="0" scrolling="0"> </iframe>
<form name="frmPost" method="post" action="open_service_account_device.do" >
	<input type="hidden" name="checkSelect" value="1" >
	<%if("Y".equalsIgnoreCase(doubleInput)){ %>
	<input type="hidden" name="choose" value="" >
	<%} %>
	<input type="hidden" name="doubleInput" value="<%=doubleInput %>" >
<table  border="0" align="center" cellpadding="0" cellspacing="10" >
  <tr>
    <td><img src="img/list_tit.gif" width="19" height="19"></td>
    <td class="title">创建业务帐户</td>
  </tr>
</table>
  <br>
  <table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
	<tr>
	  <td><img src="img/mao.gif" width="1" height="1"></td>
	</tr>
   </table>
   <br>
   
  <table align="center" width="100%" border="0" cellspacing="1" cellpadding="3" class="list_bg" >
<%

String divID="";
String inputID="";
String again="";
//选择产品包后进入页面
if (terminalDeviceList.equals("")) { 

    deviceProductIds ="";
    deviceClassList ="";
    sspanList="";
    int num=0;
%>
<lgc:bloop requestObjectName="ResponseFromEJBEvent" item="oneline">
<%
		//无硬件的增机 update chaoqiu 20070530 begin
		if(!(pageContext.getAttribute("oneline") instanceof Integer))
		{
			break;
		}
		if(num++ == 0)
		{
		%>      
 	<tr>
	  <td width="98%" class="import_tit" align="center" colspan="4">选择相应硬件设备</td>
	</tr>
	<%
	}
	//无硬件的增机 update chaoqiu 20070530 end
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
    if("Y".equalsIgnoreCase(doubleInput))
        again="again"+deviceClassDto.getDeviceClass();
%>
	<tr>
	  <td align="right" class="list_bg2" width="17%"><%=deviceClassDto.getDescription()%>序列号*</td>
    <td align="left"  colspan="3" class="list_bg1">
        <input type="text" name="<%=deviceClassDto.getDeviceClass()%>" size="25" maxlength="20"  value="" class="text" onchange="javascript:getMacInmac('<%=deviceClassDto.getDeviceClass()%>');" >
	     <%if("Y".equalsIgnoreCase(doubleInput)) {%>
	            <input type="text" name="<%=again%>" size="25" maxlength="20"  value="" class="text" onchange="javascript:getAgainMacInmac('<%=deviceClassDto.getDeviceClass()%>');">
	            <%} %> 
	      <input type=button value="选择" class="button" onclick="javascript:query_device_item('<%=deviceProductId%>','<%=deviceClassDto.getDeviceClass()%>','',this);">
			<input name="<%=inputID%>" title="inputID" type="hidden" >
	      <span  id="<%=divID%>" name="sspan"></span>
	  </td>
	 </tr>

</lgc:bloop>	
<% 
} else {
	%>      
 	<tr>
	  <td width="98%" class="import_tit" align="center" colspan="4">选择相应硬件设备</td>
	</tr>
	<%
      for (int i=0; i<aTerminalDevice.length; i++){
         DeviceClassDTO deviceClassDto =Postern.getDeviceClassByProductID(Integer.parseInt(aTerminalProductId[i]));
         String devicemodel =Postern.getTerminalDeviceBySerialNo(aTerminalDevice[i]).getDeviceModel();
         divID="div"+deviceClassDto.getDeviceClass();
         inputID="input"+deviceClassDto.getDeviceClass();
         again="again"+deviceClassDto.getDeviceClass();
%>
	 <tr>
	   <td align="right" class="list_bg2" width="17%"><%=deviceClassDto.getDescription()%>序列号*</td>
     <td align="left" colspan="3" class="list_bg1">
         <input type="text" name="<%=deviceClassDto.getDeviceClass()%>" size="25" maxlength="20"  value="<%=aTerminalDevice[i]%>" class="text" onchange="javascript:getMacInmac('<%=deviceClassDto.getDeviceClass()%>');">
	        <%if("Y".equalsIgnoreCase(doubleInput)) {%>
	               <input type="text" name="<%=again%>" size="25" maxlength="20"  value="<%=aTerminalDevice[i]%>" class="text" onchange="javascript:getAgainMacInmac('<%=deviceClassDto.getDeviceClass()%>');">
	               <%} %>
	       <input type="button" value="选择" class="button" onclick="javascript:query_device_item('<%=aTerminalProductId[i]%>','<%=deviceClassDto.getDeviceClass()%>','<%=devicemodel%>',this);">
	   		<input name="<%=inputID%>" title="inputID" type="hidden" value="<%=asspan[i]%>">
	   		<span  id="<%=divID%>" name="sspan"><%=asspan[i]%></span>
	   </td>   
	</tr>
<%  	}
		}	
%>


  
  <input type="hidden" name="DeviceProductIds" value="<%=deviceProductIds%>" > 
  <input type="hidden" name="DeviceClassList" value="<%=deviceClassList%>">
  <input type="hidden" name="TerminalDeviceList" value="">
  <input type="hidden" name="sspanList" value="">
  

  <tr>
	  <td width="17%" align="right" class="list_bg2">押金*</td>
    <td colspan="3" align="left" class="list_bg1"><input type="text" size="25" name="txtForceDepostMoney" value="<%=WebUtil.bigDecimalFormat(txtForceDepostMoney)%>" class="text">
	</tr>
  <tr>     
     <td width="17%" align="right" class="list_bg2">联系电话*</td>
     <td width="33%" align="left" class="list_bg1"><input type="text" size="25"  name="txtContactPhone" value="<%=custTelephone%>" class="text"></td>
     <td width="17%" align="right" class="list_bg2">联系人*</td>
     <td width="33%" align="left" class="list_bg1"><input type="text" size="25" name="txtContactPerson" value="<%=custName%>" class="text"></td>
   </tr>
  
  <TR>
	   <TD width="17%" align="right" class="list_bg2" >是否上门安装</TD>
	   <TD width="33%" class="list_bg1" ><d:selcmn name="txtIsInstall" mapName="SET_V_CUSTSERVICEINTERACTIONINSTYPE" match="txtIsInstall" width="25"  defaultFlag ="true" showAllFlag="false"  empty="false" onchange="changePrefed()"/></TD>
     <%
       if ("gehua".equalsIgnoreCase(Postern.getSystemSymbolName())){
     %>
     <TD width="17%" align="right" class="list_bg2" >备注</TD>
     <TD width="33%" class="list_bg1" ><input type="text" size="25" name="txtComments" value ="<tbl:writeparam name="txtComments" />" class="text"></TD>
     <%
       } else{
     %>
     <TD width="17%" align="right" class="list_bg2" >&nbsp;</TD>
     <TD width="33%" class="list_bg1" >&nbsp;</TD>  
     <%
       }
     %>
  </TR>
	<tr id ="prefered" style="display:<%if(CommonKeys.CUSTSERVICEINTERACTIONIN_STYPE_SELFINSTALL.equals(install)){%>none<%}else{}%>">
		<td  width="17%" valign="middle" class="list_bg2"  align="right" width="17%" >预约上门日期*</div></td>
		<td  width="33%"  class="list_bg1"><font size="2">
		<input maxlength="10" onfocus="viewModule(this)" onKeydown="inputDate(document.frmPost.txtPreferedDate)" onblur="lostFocus(this)" type="text" name="txtPreferedDate" size="25"  value="<tbl:writeparam name="txtPreferedDate" />" class="text">		<IMG onclick="MyCalendar.SetDate(this,document.frmPost.txtPreferedDate,'Y')" src="img/calendar.gif" style=cursor:hand border="0"></font></td>
		<td  width="17%" class="list_bg2" align="right">预约上门时间段*</td>
		<td  width="33%" class="list_bg1"  ><font size="2">
		 <d:selcmn name="txtPreferedTime" mapName="SET_C_CSIPREFEREDTIME" match="txtPreferedTime" width="23" defaultFlag ="true" showAllFlag="false"/>
		</font></td>
	</tr>
</TABLE>     

<%
//T_Service.ServiceID == 3，语音业务，需要选择电话号码
boolean bNeedPhoneNo = false;
if(CampaignList != null && CampaignList.length()>0 && ';'==CampaignList.charAt(CampaignList.length()-1))
	CampaignList = CampaignList.substring(0,CampaignList.length()-1);
String CampaignProList = Postern.getBundle2CampaignPackageColStr(CampaignList);

String allProductList = CampaignProList+ProductList;
System.out.println("ProductList=============="+ProductList);
System.out.println("allProductList=============="+allProductList);
if(allProductList != null && allProductList.length()>0 && ';'==allProductList.charAt(allProductList.length()-1))
	allProductList = allProductList.substring(0,allProductList.length()-1);
String strServiceIDs = Postern.getServiceIDByProductPackageIDs(allProductList);
System.out.println("strServiceIDs = " + strServiceIDs);
if(strServiceIDs != null && !("".equals(strServiceIDs)))
{
	String serviceIDs[] = strServiceIDs.split(";");
	for(int i=0;i<serviceIDs.length;i++)
	{
System.out.println("serviceIDs[i] = " + serviceIDs[i]);	
		if("3".equals(serviceIDs[i]))
			bNeedPhoneNo = true;
	}
	
  if(bNeedPhoneNo){
%>      
	<table width="820" border="0" align="center" cellpadding="4" cellspacing="1" class="list_bg">
		<tr>
			<td width="98%" class="import_tit" align="center">选择电话号码</td>
		</tr>
	</table>
	<table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
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
			<input type="text" name="phoneNo" size="25" maxlength="50" value="<tbl:writeparam name="phoneNo" />" class="text" onChange="javascript:setSelectFalse()" >
			<input name="Submit" type="button" class="button" value="查询" onclick="javascript:phoneNo_Search()"> 支持模糊查询，“_”代表一位，“%”代表多位。</td>
			
		</tr>
	</table>
	<input type="hidden" name="itemID" value="<tbl:writeparam name="itemID" />">
<%	
  }
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
  <table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
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
<tbl:hiddenparameters pass="txtDistrictID/ProductList/CampaignList/txtCustomerID/txtAccount/txtCustType/txtOpenSourceType/txtCsiCreateReason" />
<tbl:hiddenparameters pass="OpenCampaignList/MutiProductList/SingleProductList/GeneralCampaignList" />
<input type="hidden" name="txtActionType" value="<%=CommonKeys.CHECK_HARDPRODUCTINFO%>" />
<input type="hidden" name="confirm_post" value="false" >
<input type="hidden" name="txtHiddenMacInmac" value="" >
<input type="hidden" name="<%=com.dtv.oss.web.util.WebKeys.PAGE_TOKEN_KEY%>" value="<tbl:writeparam name="<%=com.dtv.oss.web.util.WebKeys.PAGE_TOKEN_KEY%>" />" > 
</form>
<Script language=JavaScript>
 <!--
 var selfInstall ="<%=CommonKeys.CUSTSERVICEINTERACTIONIN_STYPE_SELFINSTALL%>" ;  
function check_frm(){
	if (check_Blank(document.frmPost.txtContactPhone, true, "联系电话"))
          return false;
	if (check_Blank(document.frmPost.txtContactPerson, true, "联系人"))
          return false; 
	if (check_Blank(document.frmPost.txtForceDepostMoney, true, "押金"))
	        return false;
  if (!check_Float(document.frmPost.txtForceDepostMoney,true,"押金"))
          return false;
  
            
	var submitFlag=true;
	var terminalDeviceList="";
	var hasPhoneNo = false;
	var sspanList="";
	//检查数据完整性和给TerminalDeviceList赋值
	for(i=0;i<document.frmPost.length;i++){
		 var element=document.frmPost.elements[i];
		 if(element.name.substring(0,5)=="again"){
				if(element.value==""){
				alert("输入不完整，请重新检查！");
				submitFlag=false;
				break;}
				else
				continue;
			}
			if(element.name=="choose"||element.name=="doubleInput")
				continue;
		 if(element.title=="inputID"){
			 sspanList=sspanList+element.value+" ;";
		 }
		 if((""+element.type)=="text" && (""+element.name)!="txtPreferedDate"){
			  if(element.name == "phoneNo")
				  hasPhoneNo = true;
			  if(element.value=="" && element.name!="txtComments"){
				  alert("输入不完整，请重新检查！");
				  element.focus();
				  submitFlag=false;
			  	break;
			 }else {
				if (terminalDeviceList!="")
					 terminalDeviceList=terminalDeviceList+";";
			        if ((""+element.name !="txtContactPhone" ) 
			             && (""+element.name !="txtForceDepostMoney")
			             && (""+element.name !="txtContactPerson")
			             && element.name!="phoneNo"
			             && element.name!="txtComments"
			             && element.name!="sspan"
			             && element.name!="inputID"){
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
	document.frmPost.sspanList.value=sspanList;
	document.frmPost.TerminalDeviceList.value=terminalDeviceList;
	return submitFlag;
}

function frm_submit(){
    if (document.all("prefered").style.display=="") {
       if (check_Blank(document.frmPost.txtPreferedDate, true, "预约上门日期"))
	         return false;
       if (check_Blank(document.frmPost.txtPreferedTime, true, "预约上门时间段"))
	         return false;
       if (!check_TenDate(document.frmPost.txtPreferedDate, true, "预约上门日期")) 
           return false;	
       if(!compareDateAndToday(getCurrentDate(),document.frmPost.txtPreferedDate,"预约上门日期必须在今天以后"))
			return false;   
           
    }
        //检查所有的设备是否填写完整

	  if (check_frm()) {
		   if (document.frmPost.txtIsInstall!=null && (document.frmPost.txtIsInstall.value ==selfInstall
  	                                              ||document.frmPost.txtIsInstall.value =="")){
  	      document.frmPost.txtPreferedDate.value ="";
			    document.frmPost.txtPreferedTime.value ="";
  	   }
	     document.frmPost.submit();
	  }
}
function frm_back(){
   document.frmPost.txtActionType.value ="";
	 document.frmPost.action="menu_open_service_account.do?txtCsiType=UO";
	 document.frmPost.submit();
}
//deviceClass为查找设备类别，inputObject为输入框对象
function query_device_item(txtProductId,deviceClass,txtDevicemodel,inputObject){
	 strFeatures = "dialogWidth=530px;dialogHeight=450px;center=yes;help=no";
	 txtCsiCreateReason="";
   if(document.frmPost.txtCsiCreateReason)
      txtCsiCreateReason=document.frmPost.txtCsiCreateReason.value;
	 var result=showModalDialog("list_terminate_device.do?txtFrom=1&txtTo=10&txtStatus=W&txtDeviceClass="+deviceClass
	                           +"&txtProductId="+txtProductId+"&txtDevicemodel="+txtDevicemodel
	                           +"&txtCsiType=UO&txtCsiCreateReason="+txtCsiCreateReason,window,strFeatures);
	 if(document.frmPost.doubleInput.value=="Y"){
			document.frmPost.choose.value="choose";}
	 if (result!=null) document.frmPost.elements[deviceClass].value=result;
	 getMacInmac(deviceClass);
}

function changePrefed(){
   if (document.frmPost.txtIsInstall.value ==selfInstall){
      document.all("prefered").style.display ="none";
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

function phoneNo_check()
{
	if(document.frmPost.checkSelect.value != "1")
	{
			alert("电话号码必须选择！");
		return false;
	}
	if(document.frmPost.itemID.value =="")
	{
		alert("电话号码必须选择！");
		return false;
	}
	return true;
}
 function installPrepare(){
     if(document.frmPost.txtIsInstall!=null){
	   	if (document.frmPost.txtIsInstall.value ==selfInstall||document.frmPost.txtIsInstall.value ==""){
		      document.all("prefered").style.display ="none";
		 } else{
		      document.all("prefered").style.display ="";
		 }
	}
 }
  installPrepare();
function getMacInmac(deviceClass){
	var inputid="input"+deviceClass;
	var divid="div"+deviceClass;
	var again="again"+deviceClass;
	if(document.frmPost.doubleInput.value=="Y"){
	var choose=document.frmPost.choose.value;
	if(document.frmPost.elements[again].value!=""){
		if(document.frmPost.elements[again].value!=document.frmPost.elements[deviceClass].value){
			alert("序列号值不一致！");
			document.frmPost.elements[again].value="";
			document.frmPost.elements[deviceClass].value="";
		    document.frmPost.elements[inputid].value="";
		    document.getElementById(divid).innerHTML="";
		}
	}
	
	if(choose=="choose"){
	document.frmPost.elements[again].value=document.frmPost.elements[deviceClass].value;
	document.frmPost.choose.value="";
	}
	}
	document.frmPost.elements[inputid].value="";
	document.getElementById(divid).innerHTML="";
       document.FrameVendorDeviceUS.submit_update_vendor_device(document.frmPost.elements[deviceClass].value,deviceClass);
} 
function getAgainMacInmac(deviceClass){
	var inputid="input"+deviceClass;
	var divid="div"+deviceClass;
	var again="again"+deviceClass;
	if(document.frmPost.doubleInput.value=="Y"){
	if(document.frmPost.elements[again].value!=document.frmPost.elements[deviceClass].value){
		alert("序列号值不一致！");
		document.frmPost.elements[again].value="";
		document.frmPost.elements[deviceClass].value="";
	    document.frmPost.elements[inputid].value="";
	    document.getElementById(divid).innerHTML="";
	}
	}
} 
function setSelectFalse()
{
	document.frmPost.checkSelect.value = "0";
}
 
-->
</Script>