<%--Copyright 2003 Digivision, Inc. All rights reserved.--%>
<%--$Id: group_device.jsp,v 1.1.1.1 2010/01/25 09:11:10 yangyong Exp $--%>
<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="osstags" prefix="d" %>
<%@ taglib uri="bookmark" prefix="bk" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="operator" prefix="op" %>
<%@ page import="com.dtv.oss.dto.DeviceClassDTO" %>
<%@ page import="com.dtv.oss.dto.ProductPackageDTO" %>
<%@ page import="com.dtv.oss.web.util.CommonKeys" %>
<%@ page import="com.dtv.oss.util.Postern,java.util.*" %>

<Script language=JavaScript>
<!--
      
function next_step()
{
	var i=document.frmPost.length;
	var submitFlag=true;
	var terminalDeviceList="";
	
	var hasPhoneNo = false;
	//检查数据完整性和给TerminalDeviceList赋值
	for(i=0;i<document.frmPost.length;i++)
	{
		var element=document.frmPost.elements[i];
		if((""+element.type)=="text")
		{
			if(element.name == "phoneNo"){
				if(phoneNo_check()){
					element.focus();
					submitFlag=false;
					break;
				}
				
			}
				
			if(element.value=="")
			{
				alert("输入不完整，请重新检查！");
				element.focus();
				submitFlag=false;
				break;
			}
			else
			{
				if(element.name!="phoneNo"&&element.name.indexOf("txtProductProperty")!=0){
					if(terminalDeviceList!="")
						terminalDeviceList=terminalDeviceList+";";
					terminalDeviceList=terminalDeviceList+element.value;
				}
			}
		}
	}
		
	document.frmPost.TerDeviceList.value=terminalDeviceList;
	if(submitFlag)
	next_submit(terminalDeviceList);
	
}
function last_step(){
	var i=document.frmPost.length;
	var terminalDeviceList="";

	for(i=0;i<document.frmPost.length;i++)
	{
		var element=document.frmPost.elements[i];
		if((""+element.type)=="text")
		{
			if(element.value=="")
			{
			}
			else
			{
				if(element.name!="phoneNo"&&element.name.indexOf("txtProductProperty")!=0){
					if(terminalDeviceList!="")
						terminalDeviceList=terminalDeviceList+";";
					terminalDeviceList=terminalDeviceList+element.value;
				}
			}
		}
	}		
	document.frmPost.TerDeviceList.value=terminalDeviceList;
	last_submit(terminalDeviceList);
}
function last_submit(terminalDeviceList){
			document.all.frmPost.action="group_cust_open.do?TerminalDeviceList="+terminalDeviceList;
			document.all.frmPost.submit(); 
}
function next_submit(terminalDeviceList){
	document.all.frmPost.action="groupcust_open_fee.do?TerminalDeviceList="+terminalDeviceList+"&txtPageType=terSelect";
	document.frmPost.submit();
}


//deviceClass为查找设备类别，inputObject为输入框对象
function query_device_item(productId,deviceClass,inputObject)
{

	strFeatures = "dialogWidth=530px;dialogHeight=450px;center=yes;help=no";
	var result=showModalDialog("list_terminate_device.do?txtFrom=1&txtTo=10&txtStatus=W&txtDeviceClass="+deviceClass+"&txtProductId="+productId,window,strFeatures);
	if (result!=null) document.frmPost.elements[deviceClass].value=result;
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
		return true;
	}
	return false;
}
//-->
</Script>


<% 
String ProductList = request.getParameter("ProductList");
String deviceClassList=(request.getParameter("DeviceClassList")==null) ? "" : request.getParameter("DeviceClassList")  ; 
String deviceClassDescription =(request.getParameter("DeviceClassDescription")==null) ? "" :request.getParameter("DeviceClassDescription");
String terminalDeviceList=(request.getParameter("TerminalDeviceList") == null) ? "" :request.getParameter("TerminalDeviceList");
String deviceProductIds=(request.getParameter("DeviceProductIds")==null) ? "" :request.getParameter("DeviceProductIds");

String [] aTerminalDevice=terminalDeviceList.split(";");
String [] aTerminalProductId =deviceProductIds.split(";");
String [] aTerminalDeviceName =deviceClassList.split(";");
String [] aTerminalDeviceDescription =deviceClassDescription.split(";");
%>
<form name="frmPost" action="groupcust_device_foropen.do" method="post" > 
<rs:hasresultset>
      <table width="100%" border="0" align="center" cellpadding="4" cellspacing="1" class="list_bg">
        <tr>
          <td width="100%" class="import_tit" align="center">设备信息录入</td>
        </tr>
      </table>
      
      <table align="center" width="100%" border="0" cellspacing="1" cellpadding="3" class="list_bg" >
       <%
         if (terminalDeviceList.equals("")) { 
         		deviceProductIds =""; 
        	  deviceClassList="";
 			  deviceClassDescription ="";
       %>
	     <lgc:bloop requestObjectName="ResponseFromEJBEvent" item="oneline">
	     <%
	     Integer deviceProductID=(Integer)pageContext.getAttribute("oneline");
			 //DeviceClassDTO deviceClassDTO=(DeviceClassDTO)pageContext.getAttribute("oneline");
			  DeviceClassDTO deviceClassDTO=Postern.getDeviceClassByProductID(deviceProductID.intValue());
			
		if(!deviceClassList.equals("")){
			deviceClassList=deviceClassList+";";
			deviceProductIds=deviceProductIds+";";
			deviceClassDescription =deviceClassDescription +";";
	    }
		
			deviceClassList=deviceClassList + deviceClassDTO.getDeviceClass();
			deviceProductIds =deviceProductIds+ deviceProductID;
			deviceClassDescription =deviceClassDescription+deviceClassDTO.getDescription();
				
	    %>
            <tr>
               <td align="right" class="list_bg2" width="17%"><%=deviceClassDTO.getDescription()%>序列号*</td>
               <td align="left" class="list_bg1">
                 <input type="text" name="<%=deviceClassDTO.getDeviceClass()%>" size="25" maxlength="20"  value="" class="text">
	         <input type=button value="选择" class="button" onclick="javascript:query_device_item('<%=deviceProductID.intValue()%>','<%=deviceClassDTO.getDeviceClass()%>',this);">
	       </td>
            </tr>
	  </lgc:bloop>	
       <% 
          } else {//返回时使用
            for (int i=0; i<aTerminalDevice.length; i++){
            
       %>
            <tr>
              <td align="right" class="list_bg2" width="17%"><%=aTerminalDeviceDescription[i]%>序列号*</td>
              <td align="left" class="list_bg1">
                 <input type="text" name="<%=aTerminalDeviceName[i]%>" size="25" maxlength="20"  value="<%=aTerminalDevice[i]%>" class="text">
	         <input type="button" value="选择" class="button" onclick="javascript:query_device_item('<%=aTerminalProductId[i]%>','<%=aTerminalDeviceName[i]%>',this);">
	      </td>
            </tr>
        <%  }
          }
         %>
	   
      </table>  
      
      <input type="hidden" name="DeviceClassDescription" value="<%=deviceClassDescription%>" >
      <input type="hidden" name="DeviceClassList" value="<%=deviceClassList%>">
      <input type="hidden" name="DeviceProductIds" value="<%=deviceProductIds%>" > 
      <input type="hidden" name="TerDeviceList" value="<%=terminalDeviceList%>">
<%
//T_Service.ServiceID == 3，语音业务，需要选择电话号码
boolean bNeedPhoneNo = false;
Collection col=Postern.getPackageIDByContractID(request.getParameter("txtContractNo")==null?"":request.getParameter("txtContractNo"));
Iterator ite=col.iterator();	
ite=col.iterator();
ProductList="";
while(ite.hasNext())
{
	ProductList=ProductList+ite.next().toString()+";";
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
	<table width="100%" border="0" align="center" cellpadding="4" cellspacing="1" class="list_bg">
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
<table width="100%" border="0" align="center" cellpadding="4" cellspacing="1" class="list_bg">  
    <% 
    	ite=col.iterator();
		String strPkgID="";
		while(ite.hasNext())
	   {
	       strPkgID = ite.next().toString();
	%>

	<tbl:productproperty serveyName="txtProductProperty" packageID="<%=strPkgID%>"  showType="text" tdWordStyle="list_bg2"  tdControlStyle="list_bg1" controlSize="25" headStyle="list_head" />
	<% }%>
	</table>
  </rs:hasresultset>    
  <input type="hidden" name="txtActionType" value="<%=CommonKeys.GET_FEE_LIST%>" >
  
  <tbl:hiddenparameters pass="txtContractNo/txtContractName/txtDescription/txtCustPackage/txtCustomerID/txtCounty/txtChildCustID" /> 
  <tbl:hiddenparameters pass="txtTelephone/txtAccountID/txtAgentName/txtContactPhone/txtAgentCardType/txtAgentCardID/txtContactPerson/txtName" />     
</form> 