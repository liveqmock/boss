<%--Copyright 2003 Digivision, Inc. All rights reserved.--%>
<%--$Id: cp_add_produce_terminate_device.jsp,v 1.2 2010/03/16 05:13:00 yangyong Exp $--%>
<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="osstags" prefix="d" %>
<%@ taglib uri="bookmark" prefix="bk" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="operator" prefix="op" %>
<%@ page import="com.dtv.oss.dto.DeviceClassDTO" %>

<%@ page import="com.dtv.oss.dto.ProductPackageDTO" %>
<%@ page import="com.dtv.oss.web.util.CommonKeys" %>
<%@ page import="com.dtv.oss.util.Postern" %>

<%

      String deviceProductDescriptions=(request.getParameter("DeviceProductDescriptions")==null) ? "" :request.getParameter("DeviceProductDescriptions");    
      String terminalDeviceList=(request.getParameter("TerminalDeviceList")==null) ? "" : request.getParameter("TerminalDeviceList");  

%>
<Script language=JavaScript>
<!--
      
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
				break;
			}
			else
			{
				if(element.name!="phoneNo"){
					if(terminalDeviceList!="")
						terminalDeviceList=terminalDeviceList+";";
					terminalDeviceList=terminalDeviceList+element.value;
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

	if (check_frm()) document.frmPost.submit();
}

function frm_back()
{
    document.frmPost.txtActionType.value="CommonKeys.CHECK_PRODUCTPG_AND_INVOICE";
	document.frmPost.action="cp_add_product.do";
	document.frmPost.submit();
}

//deviceClass为查找设备类别，inputObject为输入框对象
function query_device_item(txtProductId,deviceClass,txtDevicemodel,inputObject){
	strFeatures = "dialogWidth=530px;dialogHeight=450px;center=yes;help=no";
	txtCsiCreateReason="";
	if(document.frmPost.txtCsiCreateReason)
		txtCsiCreateReason=document.frmPost.txtCsiCreateReason.value;
	var result=showModalDialog("list_terminate_device.do?txtFrom=1&txtTo=10&txtStatus=W&txtDeviceClass="+deviceClass+"&txtProductId="+txtProductId+"&txtDevicemodel="+txtDevicemodel + "&txtCsiType=PN&txtCsiCreateReason="+txtCsiCreateReason,window,strFeatures);
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

function phoneNo_check()
{
	if(document.frmPost.itemID.value =="")
	{
		alert("电话号码必须选择！");
		return false;
	}
	return true;
}
function getMacInmac(deviceClass){
	var inputid="input"+deviceClass;
	var divid="div"+deviceClass;
	document.frmPost.elements[inputid].value="";
	document.getElementById(divid).innerHTML="";
       document.FrameVendorDeviceUS.submit_update_vendor_device(document.frmPost.elements[deviceClass].value,deviceClass);
} 
//-->
</Script>


<% 
String ProductList = request.getParameter("ProductList");
String deviceClassList=(request.getParameter("DeviceClassList") == null) ? "" :request.getParameter("DeviceClassList");  


String deviceProductIds=(request.getParameter("DeviceProductIds")==null) ? "" :request.getParameter("DeviceProductIds");
String sspanList=(request.getParameter("sspanList") == null) ? "" :request.getParameter("sspanList");

String [] aTerminalDevice=terminalDeviceList.split(";");
String [] aTerminalProductId =deviceProductIds.split(";");
String [] asspan=sspanList.split(";");

%>
<div id="updselwaitlayer" style="position:absolute; left:650px; top:150; width:250px; height:24px; display:none"></div>

<iframe SRC="get_mac_intermac.screen" name="FrameVendorDeviceUS" width="0" height="0" frameborder="0" scrolling="0"> </iframe>

<form name="frmPost" method="post" action="cp_add_product_caculate.do" >
      <table width="820" border="0" align="center" cellpadding="4" cellspacing="1" class="list_bg">
        <tr>
          <td width="98%" class="import_tit" align="center">选择相应硬件设备</td>
        </tr>
      </table>
      
      <table align="center" width="100%" border="0" cellspacing="1" cellpadding="3" class="list_bg" >
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
    	      deviceProductDescriptions=deviceProductDescriptions+deviceClassDto.getDescription();
         }
         else {
  	        deviceProductIds =deviceProductIds+";"+ deviceProductId;
  	        deviceClassList=deviceClassList+";"+ deviceClassDto.getDeviceClass();
  	        deviceProductDescriptions=deviceProductDescriptions+";"+deviceClassDto.getDescription();
  	     }
         divID="div"+deviceClassDto.getDeviceClass();
         inputID="input"+deviceClassDto.getDeviceClass();
         
	    %>
         <tr>
            <td align="right" class="list_bg2" width="17%"><%=deviceClassDto.getDescription()%>序列号*</td>
            <td align="left" class="list_bg1">
              <input type="text" name="<%=deviceClassDto.getDeviceClass()%>" size="25" maxlength="20"  value="" class="text" onchange="javascript:getMacInmac('<%=deviceClassDto.getDeviceClass()%>');">
	            <input type=button value="选择" class="button" onclick="javascript:query_device_item('<%=deviceProductId%>','<%=deviceClassDto.getDeviceClass()%>','',this);">
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
              <td align="right" class="list_bg2" width="17%"><%=deviceClassDto.getDescription()%>序列号*</td>
              <td align="left" class="list_bg1">
                 <input type="text" name="<%=deviceClassDto.getDeviceClass()%>" size="25" maxlength="20"  value="<%=aTerminalDevice[i]%>" class="text" onchange="javascript:getMacInmac('<%=deviceClassDto.getDeviceClass()%>');">
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
      <input type="hidden" name="DeviceProductDescriptions" value="<%=deviceProductDescriptions%>">
      
      <input type="hidden" name="TerminalDeviceList" value="">
      <input type="hidden" name="txtSelectTerminalDeviceFlag" value="select">
      <input type="hidden" name="sspanList" value="">
      <input type="hidden" name="txtHiddenMacInmac" value="" >
<%
//T_Service.ServiceID == 3，语音业务，需要选择电话号码
boolean bNeedPhoneNo = false;
String txtGroupBargainDetailNo =(request.getParameter("txtGroupBargainDetailNo") ==null ) ? "" :request.getParameter("txtGroupBargainDetailNo") ;
if (!txtGroupBargainDetailNo.equals("")){
     ProductList ="";
     ArrayList list = Postern.getProductPackageDTOByGroupDetail(txtGroupBargainDetailNo);
     System.out.println("list.size()=================="+list.size());
     Iterator currentPackageIter=list.iterator();
     System.out.println("list==============="+list);
     while(currentPackageIter.hasNext()){
           ProductPackageDTO dto =(ProductPackageDTO)currentPackageIter.next();
           System.out.println("dto.getPackageID()**********"+dto.getPackageID());
           ProductList =ProductList+ dto.getPackageID()+";";     
     }
     if (ProductList !=null) ProductList =ProductList.substring(0,ProductList.length()-1);
}

System.out.println("ProductList=============="+ProductList);
String strServiceIDs = Postern.getServiceIDByProductPackageIDs(ProductList);
System.out.println("strServiceIDs=============="+strServiceIDs);
if(strServiceIDs != null && !("".equals(strServiceIDs)))
{
	String serviceIDs[] = strServiceIDs.split(";");
	for(int i=0;i<serviceIDs.length;i++)
	{
		if("3".equals(serviceIDs[i]))
			bNeedPhoneNo = true;
	}
	
	if(bNeedPhoneNo)
	{
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
			<input type="text" name="phoneNo" size="25" maxlength="50" value="<tbl:writeparam name="phoneNo" />" class="text">
			<input name="Submit" type="button" class="button" value="查询" onclick="javascript:phoneNo_Search()"> 支持模糊查询，“_”代表一位，“%”代表多位。</td>
		</tr>
	</table>
	<input type="hidden" name="itemID" value="<tbl:writeparam name="itemID" />">
<%	}
}%>      
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
      <input type="hidden" name="ProductList" value="<%=ProductList%>" >
      <tbl:hiddenparameters pass="OpenCampaignList/MutiProductList/SingleProductList/GeneralCampaignList/txtProductId/txtDeviceModel/txtDeviceClass" />
      <tbl:hiddenparameters pass="CampaignList/txtCustomerID/txtServiceAccountID/txtAccount/txtPreferedTime/txtPreferedDate/txtCsiCreateReason" />
      <tbl:hiddenparameters pass="txtUsedMonth" /> 	
      <input type="hidden" name="txtActionType" value="<%=CommonKeys.CHECK_HARDPRODUCTINFO%>" />
      <tbl:dynamicservey serveyType="M"  showType="hide" />
      <input type="hidden" name="<%=com.dtv.oss.web.util.WebKeys.PAGE_TOKEN_KEY%>" value="<tbl:writeparam name="<%=com.dtv.oss.web.util.WebKeys.PAGE_TOKEN_KEY%>" />" >  
</form>