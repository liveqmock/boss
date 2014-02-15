<%--Copyright 2003 Digivision, Inc. All rights reserved.--%>
<%--$Id: open_create_product_terminate_device.jsp,v 1.1.1.1 2010/01/25 09:11:09 yangyong Exp $--%>
<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="osstags" prefix="d" %>
<%@ taglib uri="bookmark" prefix="bk" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="operator" prefix="op" %>
<%@ page import="com.dtv.oss.dto.DeviceClassDTO" %>
<%@ page import="com.dtv.oss.web.util.WebUtil" %>
<%@ page import="com.dtv.oss.dto.ProductPackageDTO" %>
<%@ page import="com.dtv.oss.web.util.CommonKeys" %>
<%@ page import="com.dtv.oss.util.Postern" %>
<%@ page import="com.dtv.oss.service.util.CsrBusinessUtility" %>

<Script language=JavaScript>
<!--
function check_ProductProperty(){
	for(i=0;i<document.frmPost.length;i++){
		var element=document.frmPost.elements[i];
		if((""+element.type)=="text" ||(""+element.type)=="password"){
			var strEleName = "" + element.name;
			var arrEleName = element.name.split("_");
			if( arrEleName[0] == "txtProductProperty"){
				if(element.value==""){
				   alert("产品属性输入不完整，请重新检查！");
					 element.focus();
					 return false;
				}
			}
			
		}
	}
	return true;
}

      
function check_frm()
{
	var i=document.frmPost.length;
	var submitFlag=true;
	var terminalDeviceList="";
	var sspanList="";
	var hasPhoneNo = false;
	//检查数据完整性和给TerminalDeviceList赋值
	for(i=0;i<document.frmPost.length;i++)
	{	
		var element=document.frmPost.elements[i];
		//alert(element.name);
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
		if((""+element.type)=="text")
		{
			if(element.name == "phoneNo")
				hasPhoneNo = true;
			if(element.value=="")
			{
				alert("输入不完整，请重新检查！");
				element.focus();
				submitFlag=false;
				//break;
				return false;
			}
			else
			{
				if(element.name!="phoneNo"){
				//txtProductProperty_40_30003
				if(element.name.indexOf("txtProductProperty") == -1){ //过滤产品属性element
					if(terminalDeviceList!="")
						terminalDeviceList=terminalDeviceList+";";
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

function frm_submit()
{
        //检查所有的设备是否填写完整
	 

	if (check_frm()&&check_ProductProperty()) document.frmPost.submit();
}

function frm_back()
{
    document.frmPost.txtActionType.value ="";
	document.frmPost.action="check_address.do";
	document.frmPost.submit();
}

//deviceClass为查找设备类别，inputObject为输入框对象
function query_device_item(txtProductId,deviceClass,txtDevicemodel,inputObject){
	var txtCsiCreateReason=(document.frmPost.elements['txtCsiCreateReason']!=null)?(document.frmPost.elements['txtCsiCreateReason'].value):"";
	var csiType=document.frmPost.elements['txtCsiType'].value;
	strFeatures = "dialogWidth=530px;dialogHeight=450px;center=yes;help=no";
	var strUrl="list_terminate_device.do?txtFrom=1&txtTo=10&txtStatus=W&txtDeviceClass="+deviceClass
	+"&txtProductId="+txtProductId
	+"&txtDevicemodel="+txtDevicemodel;
	if(txtCsiCreateReason!=null&&txtCsiCreateReason!=''){
		strUrl+="&txtCsiCreateReason="+txtCsiCreateReason;
		strUrl+="&txtCsiType="+csiType;
	}
	if(document.frmPost.doubleInput.value=="Y"){
	document.frmPost.choose.value="choose";}
	var result=showModalDialog(strUrl,window,strFeatures);
	if (result!=null) document.frmPost.elements[deviceClass].value=result;
	getMacInmac(deviceClass);
}

function phoneNo_Search()
{
  var  phoneNo =document.frmPost.phoneNo.value;
  var  countyID =document.frmPost.txtCounty.value;
  var  grade=document.frmPost.txtGrade.value;
  var  strFeatures = "width=600px,height=500px,resizable=no,toolbar=no,scrollbars=yes,status=yes";
  window.open("phoneNo_query.do?txtFrom=1&txtTo=10&phoneNo="+phoneNo+"&districtID="+countyID+"&grade="+grade,"电话号码查询",strFeatures);
}


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
//-->
</Script>


<% 
String doubleInput =Postern.getSystemsettingValueByName("SET_V_DEVICESDOUBLEINPUT");
if(doubleInput==null&&"".equalsIgnoreCase(doubleInput))
	doubleInput="";
String ProductList = request.getParameter("ProductList");
String CampaignList = request.getParameter("CampaignList");
String deviceClassList=(request.getParameter("DeviceClassList") == null) ? "" :request.getParameter("DeviceClassList");  ; 
String terminalDeviceList=(request.getParameter("TerminalDeviceList") == null) ? "" :request.getParameter("TerminalDeviceList");
String deviceProductIds=(request.getParameter("DeviceProductIds")==null) ? "" :request.getParameter("DeviceProductIds");
String sspanList=(request.getParameter("sspanList") == null) ? "" :request.getParameter("sspanList");

String [] aTerminalDevice=terminalDeviceList.split(";");
String [] aTerminalProductId =deviceProductIds.split(";");
String [] asspan=sspanList.split(";");

String txtServiceCodeList = request.getParameter("txtServiceCodeList");
String [] phoneNo = null;
if(txtServiceCodeList!=null)
	phoneNo=txtServiceCodeList.split(",");

String open_flag = request.getParameter("OpenFlag");
System.out.println("+++++>>>>>open_flag="+open_flag);
System.out.println("+++++>>>>>txtServiceCodeList="+request.getParameter("txtServiceCodeList"));

%>
<div id="updselwaitlayer" style="position:absolute; left:650px; top:150; width:250px; height:24px; display:none"></div>

<iframe SRC="get_mac_intermac.screen" name="FrameVendorDeviceUS" width="0" height="0" frameborder="0" scrolling="0"> </iframe>

<form name="frmPost" method="post" action="open_create_product_fee.do" >
	<input type="hidden" name="checkSelect" value="1" >
	<%if("Y".equalsIgnoreCase(doubleInput)){ %>
	<input type="hidden" name="choose" value="" >
	<%} %>
	<input type="hidden" name="doubleInput" value="<%=doubleInput %>" >
      <table width="820" border="0" align="center" cellpadding="4" cellspacing="1" class="list_bg">
        <tr>
          <td width="98%" class="import_tit" align="center">开户－选择相应硬件设备</td>
        </tr>
      </table>
      
      <table align="center" width="100%" border="0" cellspacing="1" cellpadding="3" class="list_bg" >
      <%
      String divID="";
      String inputID="";
      String again="";
         if (terminalDeviceList.equals("")) { 
            deviceProductIds ="";
            deviceClassList ="";
      %>
	    <lgc:bloop requestObjectName="ResponseFromEJBEvent" item="oneline">
	    <%
	    System.out.println(">>>>>>>>>>pageContext.getAttribute(oneline)="+pageContext.getAttribute("oneline"));
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
            <td align="left" class="list_bg1">
              <input type="text" name="<%=deviceClassDto.getDeviceClass()%>" size="25" maxlength="20"  value="" class="text" onchange="javascript:getMacInmac('<%=deviceClassDto.getDeviceClass()%>');">
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
        	System.out.println("-----------------"+aTerminalDevice.length);
        	System.out.println("-----------------"+aTerminalProductId.length);
            for (int i=0; i<aTerminalDevice.length; i++){
               DeviceClassDTO deviceClassDto =Postern.getDeviceClassByProductID(Integer.parseInt(aTerminalProductId[i]));
               String devicemodel =Postern.getTerminalDeviceBySerialNo(aTerminalDevice[i]).getDeviceModel();
               divID="div"+deviceClassDto.getDeviceClass();
               inputID="input"+deviceClassDto.getDeviceClass();
               again="again"+deviceClassDto.getDeviceClass();
    %>
            <tr>
              <td align="right" class="list_bg2" width="17%"><%=deviceClassDto.getDescription()%>序列号*</td>
              <td align="left" class="list_bg1">
                 <input type="text" name="<%=deviceClassDto.getDeviceClass()%>" size="25" maxlength="20"  value="<%=aTerminalDevice[i]%>" class="text" onchange="javascript:getMacInmac('<%=deviceClassDto.getDeviceClass()%>');">
	               <%if("Y".equalsIgnoreCase(doubleInput)) {%>
	               <input type="text" name="<%=again%>" size="25" maxlength="20"  value="<%=aTerminalDevice[i]%>" class="text" onchange="javascript:getAgainMacInmac('<%=deviceClassDto.getDeviceClass()%>');">
	               <%} %>
	               <input type="button" value="选择" class="button" onclick="javascript:query_device_item('<%=aTerminalProductId[i]%>','<%=deviceClassDto.getDeviceClass()%>','<%=devicemodel%>',this);">
	            <input name="<%=inputID%>" title="inputID" type="hidden" value="<%=asspan[i]%>">
	   		<span  id="<%=divID%>" name="sspan"><%=asspan[i]%></span>
	            </td>
            </tr>
    <%    }
       }
    %>
      </table>  
      <input type="hidden" name="DeviceProductIds" value="<%=deviceProductIds%>" > 
      <input type="hidden" name="DeviceClassList" value="<%=deviceClassList%>">
      <input type="hidden" name="TerminalDeviceList" value="">
      <input type="hidden" name="sspanList" value="">
      <input type="hidden" name="txtHiddenMacInmac" value="" >
<%
//T_Service.ServiceID == 3，语音业务，需要选择电话号码
boolean bNeedPhoneNo = false;
String txtGroupBargainDetailNo =(request.getParameter("txtGroupBargainDetailNo") ==null ) ? "" :request.getParameter("txtGroupBargainDetailNo") ;
if (!txtGroupBargainDetailNo.equals("")){
     ProductList ="";
     ArrayList list = Postern.getProductPackageDTOByGroupDetail(txtGroupBargainDetailNo);
     Iterator currentPackageIter=list.iterator();
     while(currentPackageIter.hasNext()){
           ProductPackageDTO dto =(ProductPackageDTO)currentPackageIter.next();
           ProductList =ProductList+ dto.getPackageID()+";";     
     }
     if (ProductList !=null) ProductList =ProductList.substring(0,ProductList.length()-1);
}
if(CampaignList != null && CampaignList.length()>0 && ';'==CampaignList.charAt(CampaignList.length()-1))
	CampaignList = CampaignList.substring(0,CampaignList.length()-1);
   String CampaignProList = Postern.getBundle2CampaignPackageColStr(CampaignList);

   String allProductList = "";

	allProductList = CampaignProList+ProductList;
  if(allProductList != null && allProductList.length()>0 && ';'==allProductList.charAt(allProductList.length()-1))
	allProductList = allProductList.substring(0,allProductList.length()-1);
  String strServiceIDs = Postern.getServiceIDByProductPackageIDs(allProductList);
  
  int n=0;
  String itemID="";
  if(strServiceIDs != null && !("".equals(strServiceIDs))){
  	String serviceIDs[] = strServiceIDs.split(";");
   	for(int i=0;i<serviceIDs.length;i++)
  	{
	  	if("3".equals(serviceIDs[i]))
		   	bNeedPhoneNo = true;
	  }
	
	 if(bNeedPhoneNo){
%>      
	<table width="820" border="0" align="center" cellpadding="4" cellspacing="1" class="list_bg">
		<tr>
			<td width="100%" class="import_tit" align="center">选择电话号码</td>
		</tr>
	</table>
	<table width="100%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
		<tr>
			<td class="list_bg2" width="17%">
				<div align="right">号码级别</div></td>
			<td class="list_bg1">
				<d:selcmn name="txtGrade" mapName="SET_R_PHONENOGRADE" match="txtGrade" width="23" />
			</td>
		</tr>
		<tr>
			<td class="list_bg2" width="17%"><div align="right">电话号码</div></td>
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
      <tbl:hiddenparameters pass="txtCatvID/txtGroupBargainDetailNo/txtReferBookingSheetID/txtCsiCreateReason/txtCsiType" />
      <tbl:hiddenparameters pass="txtCustomerSerialNo/txtCustType/txtGender/txtName/txtBirthday/txtCardType/txtCardID/txtTelephone/txtMobileNumber/txtEmail" />
      <tbl:hiddenparameters pass="txtContractNo/txtCounty/txtDetailAddress/txtPostcode/txtOpenSourceType/txtOpenSourceID/txtComments/txtLoginID/txtLoginPwd" />
      <tbl:hiddenparameters pass="txtPreferedTime/txtPreferedDate/txtSocialSecCardID/txtNationality/txtFaxNumber" />
      <tbl:hiddenparameters pass="txtBillDetailAddress/txtBillPostcode/txtbillCounty/txtMopID/txtBankAccount/txtBankAccountName/txtAccountType/txtAccountName" />
      <input type="hidden" name="ProductList" value="<%=ProductList%>" >
      <tbl:hiddenparameters pass="CampaignList/OpenFlag/txtIsInstall/txtForceDepostMoney/txtServiceCodeList" />
      <tbl:hiddenparameters pass="<%=com.dtv.oss.web.util.WebKeys.PAGE_TOKEN_KEY%>"/>	
			<tbl:hiddenparameters pass="txtAgentName/txtAgentTelephone/txtAgentCardType/txtAgentCardId" />
      	
      <tbl:hiddenparameters pass="OpenCampaignList/MutiProductList/SingleProductList/GeneralCampaignList" />  
      <input type="hidden" name="txtActionType" value="<%=CommonKeys.CHECK_HARDPRODUCTINFO%>" />
      <tbl:dynamicservey serveyType="M"  showType="hide" />
      
</form>
<Script language=JavaScript>
<!--
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
//-->
</Script>