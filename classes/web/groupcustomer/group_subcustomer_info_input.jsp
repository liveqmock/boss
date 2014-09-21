<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="resultset" prefix="rs" %>
<%@ taglib uri="bookmark" prefix="bk" %>
<%@ taglib uri="osstags" prefix="d" %>
<%@ taglib uri="operator" prefix="op" %>
<%@ page import="com.dtv.oss.dto.DeviceClassDTO"%>
<%@ page import="com.dtv.oss.dto.PackageLineDTO" %>
<%@ page import="com.dtv.oss.dto.ContractDTO" %>
<%@ page import="com.dtv.oss.web.util.CommonKeys" %>
<%@ page import="com.dtv.oss.util.Postern" %>
<%@ page import="java.util.*" %>
<%@ page import="com.dtv.oss.web.util.WebUtil" %>

<%
   String catvFieldName =Postern.getFieldNameByFieldInterID("CUSTOMER_CATVID");
   int    catvIdLength =Postern.getSystemsettingIntValue("SET_V_CATVIDLENGTH",10);		
%>
<script language=javascript>

function query_customer(){
	if(document.frmPost.txtCustomerID.value==""){
		alert("客户号不能为空！");
		return;
	}
	var strcustomerid = document.frmPost.txtCustomerID.value;
	var strFeatures = "width=600px,height=500px,resizable=no,toolbar=no,scrollbars=yes,status=yes";
  	window.open("query_complain_customer.do?newCustomerID="+strcustomerid,"电话号码查询",strFeatures);
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
  var  districtID =document.frmPost.txtCounty.value;
  var  grade=document.frmPost.txtGrade.value;  
  var  strFeatures = "width=600px,height=500px,resizable=no,toolbar=no,scrollbars=yes,status=yes";
  if(districtID==""){
  	alert("客户信息中选择所在区才能选择电话号码！");
  	return;
  }
  window.open("phoneNo_query.do?txtFrom=1&txtTo=10&phoneNo="+phoneNo+"&districtID="+districtID+"&grade="+grade,"电话号码查询",strFeatures);

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
function check_frm()
{
	var submitFlag=true;
	var terminalDeviceList="";
	var hasPhoneNo = false;
	//检查数据完整性和给TerminalDeviceList赋值
	for(i=0;i<document.frmPost.length;i++)
	{
		var element=document.frmPost.elements[i];
		if((""+element.type)=="text" && (""+element.name)!="txtPreferedDate")
		{
			if(element.name == "phoneNo")
				hasPhoneNo = true;
			if((element.value=="") && (element.name!="txtGender") 
			&& (element.name!="txtBirthday") && (element.name!="txtNationality")
			&& (element.name!="txtSocialSecCardID")&& (element.name!="txtMobileNumber")
			&& (element.name!="txtFaxNumber") && (element.name!="txtEmail")
			&& (element.name!="txtCatvID"))
			{
				alert("输入不完整，请重新检查！");
				element.focus();
				submitFlag=false;
				return false;
			}
			else
			{
				if(terminalDeviceList!="")
					terminalDeviceList=terminalDeviceList+";";
			        if ((""+element.name !="txtContactPhone" ) 
			             && (""+element.name !="txtForceDepostMoney")
			             && (""+element.name !="txtContactPerson")&& (element.name!="txtCountyDesc")
			             && (element.name!="phoneNo") && (element.name!="txtName")
			             && (element.name!="txtGender") && (element.name!="txtBirthday")
			             && (element.name!="txtNationality") 
			             && (element.name!="txtCardID") && (element.name!="txtSocialSecCardID")
			             && (element.name!="txtTelephone") && (element.name!="txtMobileNumber")
			             && (element.name!="txtFaxNumber") && (element.name!="txtEmail")
			             && (element.name!="txtCatvID") && (element.name!="txtCounty")
			             && (element.name!="txtPostcode") &&(element.name!="txtDetailAddress")
			             && (element.name!="txtContractNo") && (element.name!="txtContractName")
			             && (element.name!="txtContractDescription") && (element.name!="txtPackageName")){
				   terminalDeviceList=terminalDeviceList+element.value;
				}
			}
		}
	}
	if(document.frmPost.txtCardType.value==""){
		alert("请选择证件类型！");
		return false;
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
       
	return submitFlag;
}
function frm_submit()
{    //检查所有的设备是否填写完整

	if (check_frm()) document.frmPost.submit();
}
function back_submit(){
	var sel=document.frmPost.selStatus.value;
	document.frmPost.action="group_customer_contract_select_open.do?selStatus="+sel;
	document.frmPost.submit();
}
function check_addressOrcatv(){
   if (check_Blank(document.frmPost.txtCatvID, false, "<%=catvFieldName%>")&& check_Blank(document.frmPost.txtDetailAddress, false, "详细地址")){
	alert("请输入至少一个查询条件");		
	return false;
   }
   if(!check_Blank(document.frmPost.txtCatvID, false, "<%=catvFieldName%>")){
       if(document.frmPost.txtCatvID.value.length!=<%=catvIdLength%>){
	  alert("<%=catvFieldName%>必须输入<%=catvIdLength%>位");
	  return false;	
	}
    }
    if(!check_Blank(document.frmPost.txtDetailAddress, false, "详细地址")){
	if(document.frmPost.txtDetailAddress.value.length<=3){
	   alert("详细地址必须至少4位");
	   return false;		
	}
    }		
    return true;
}
function check_submit()
{
   if (check_addressOrcatv()) {
      var  catvId =document.frmPost.txtCatvID.value;
      var  detailAddress =document.frmPost.txtDetailAddress.value;
      var  strFeatures = "width=600px,height=500px,resizable=no,toolbar=no,scrollbars=yes";
      window.open("catv_terminal_query.do?txtFrom=1&txtTo=10&catvId="+catvId+"&detailAddress="+detailAddress,"<%=catvFieldName%>查询",strFeatures);
   }
}

</script>
<%
String ProductList =(request.getParameter("ProductList")==null) ? "" : request.getParameter("ProductList");
System.out.println("++++ProductList = " + ProductList);
String deviceClassList=(request.getParameter("DeviceClassList")==null) ? "" : request.getParameter("DeviceClassList")  ; 
String deviceClassDescription =(request.getParameter("DeviceClassDescription")==null) ? "" :request.getParameter("DeviceClassDescription");
String terminalDeviceList=(request.getParameter("TerminalDeviceList") == null) ? "" :request.getParameter("TerminalDeviceList");
String deviceProductIds=(request.getParameter("DeviceProductIds")==null) ? "" :request.getParameter("DeviceProductIds");
String [] aTerminalDevice=terminalDeviceList.split(";");
String [] aTerminalProductId =deviceProductIds.split(";");
String [] aTerminalDeviceName =deviceClassList.split(";");
String [] aTerminalDeviceDescription =deviceClassDescription.split(";");
double txtForceDepostMoney =(double) ((request.getParameter("txtForceDepostMoney")==null) ? 0.0 :Double.parseDouble(request.getParameter("txtForceDepostMoney")));    
%>
<form name="frmPost" method="post" action="group_subcustomer_device_confirm.do" >
<input type="hidden" name="selStatus" value="<tbl:writeparam name="selStatus"/>">
<table  border="0" align="center" cellpadding="0" cellspacing="10">
  <tr>
    <td><img src="img/list_tit.gif" width="19" height="19"></td>
    <td class="title">子客户信息录入</td>
  </tr>
</table>
<table width="820"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>
<br>

   <table width="820" border="0" align="center" cellpadding="4" cellspacing="1" class="list_bg">
	<tr>
	  <td width="100%" class="import_tit" align="center">子客户基本信息</td>
     </tr>
     </table>
   <table width="100%"  border="0" align="center" cellpadding="4" cellspacing="1" class="list_bg">
        <tr>
          <td width="17%" align="right" class="list_bg2">姓名*</td>
          <td width="33%" class="list_bg1" align="left"><font size="2">
             <input type="text" name="txtName" size="25" maxlength="20" value="<tbl:writeparam name="txtName" />"  class="text">
          </font> </td>                      
          <td width="17%" class="list_bg2" align="right">性别</td>          
          <td width="33%" class="list_bg1" align="left"><font size="2"> 
            <d:selcmn name="txtGender" mapName="SET_C_CUSTOMERGENDER" match="txtGender" width="23" />
          </font> </td>
        </tr> 
        <tr>  
           <td  class="list_bg2" align="right">证件类型*</td>
           <td  class="list_bg1" align="left"><font size="2">
             <d:selcmn name="txtCardType" mapName="SET_C_CUSTOMERCARDTYPE" match="txtCardType" width="23" />
           </font></td>  
           <td  class="list_bg2" align="right">证件号*</td>
           <td  class="list_bg1" align="left"><font size="2">        
             <input type="text" name="txtCardID" size="25" maxlength="25" value="<tbl:writeparam name="txtCardID" />" class="text">               
           </font></td>
        </tr>
        <tr>             
           <td  class="list_bg2" align="right">联系电话*</td>
           <td  class="list_bg1" align="left"><font size="2">        
             <input type="text" name="txtTelephone" size="25" maxlength="20" value="<tbl:writeparam name="txtTelephone"/>" class="text" >             
           </font></td>
           <td  class="list_bg2" align="right">移动电话</td>
           <td  class="list_bg1" align="left"><font size="2">
             <input type="text" name="txtMobileNumber" size="25" maxlength="20" value="<tbl:writeparam name="txtMobileNumber" />" class="text" >
           </font></td>
        </tr>
        <tr>  
           <td  class="list_bg2" align="right">所在区*</td>
           <td  class="list_bg1" align="left"><font size="2">
             <input type="hidden" name="txtCounty" value="<tbl:writeparam name="txtCounty" />">
             <input type="hidden" name="txtDistrictID" value="<tbl:writeparam name="txtCounty" />">
	       <input type="text" name="txtCountyDesc" size="25" maxlength="50" readonly value="<tbl:WriteDistrictInfo property="txtCounty" />" class="text">
               <input name="selDistButton" type="button" class="button" value="选择" onClick="javascript:sel_district('leaf','txtCounty','txtCountyDesc')">
           </font></td>  
           <td  class="list_bg2" align="right">详细地址*</td>
           <td  class="list_bg1" align="left"><font size="2">        
            <input type="text" name="txtDetailAddress" size="25" maxlength="100" value="<tbl:writeparam name="txtDetailAddress" />" class="text">            
           </font></td>
        </tr>
        <tr>  
           <td  class="list_bg2" align="right">邮编*</td>
           <td  class="list_bg1" align="left" colspan="3"><font size="2">
             <input type="text" name="txtPostcode" size="25" maxlength="6" value="<tbl:writeparam name="txtPostcode" />" class="text">
           </font></td>  
       </tr>
       <tr>             
           <td  class="list_bg2" align="right">&nbsp;<%=catvFieldName%></td>
           <td  class="list_bg1" align="left"><font size="2">        
             <input type="text" name="txtCatvID" size="25" maxlength="50" value="<tbl:writeparam name="txtCatvID" />" class="text">            
           </font>
           <input name="checkbutton" type="button" class="button" value="检查" onClick="javascript:check_submit()"></td>
           <td  class="list_bg2" align="right">出生日期</td>
           <td  class="list_bg1" align="left"><font size="2">
             <input onfocus="viewModule(this)" onKeydown="inputDate(document.frmPost.txtBirthday)" onblur="lostFocus(this)" type="text" name="txtBirthday" size="25" maxlength="10" value="<tbl:writeparam name="txtBirthday" />" class="text" >	     <IMG onclick="MyCalendar.SetDate(this,document.frmPost.txtBirthday,'Y')" src="img/calendar.gif" style=cursor:hand border="0">  
           </font></td>
        </tr>        
        <tr>             
           <td  class="list_bg2" align="right">国籍</td>
           <td  class="list_bg1" align="left"><font size="2">        
             <d:selcmn name="txtNationality" mapName="SET_C_NATIONALITY" match="txtNationality" width="23" />               
           </font></td>
           <td  class="list_bg2" align="right">电子邮件</td>
           <td  class="list_bg1" align="left"><font size="2">
             <input type="text" name="txtEmail" size="25" maxlength="100" value="<tbl:writeparam name="txtEmail" />"  class="text">
           </font></td>
        </tr>
        <tr>            
           <td  class="list_bg2" align="right">社保卡编号</td>
           <td  class="list_bg1" align="left"><font size="2">
             <input type="text" name="txtSocialSecCardID" size="25" maxlength="25" value="<tbl:writeparam name="txtSocialSecCardID" />" class="text">
           </font></td> 
           <td  class="list_bg2" align="right">传真</td>
           <td  class="list_bg1" align="left"><font size="2">        
             <input type="text" name="txtFaxNumber" size="25" maxlength="100" value="<tbl:writeparam name="txtFaxNumber" />" class="text">            
           </font></td>
        </tr>        
     </table>

      <table width="820" border="0" align="center" cellpadding="4" cellspacing="1" class="list_bg">
	<tr>
	  <td width="100%" class="import_tit" align="center">合同信息</td>
	</tr>
    </table>
	<%
	ContractDTO dto=Postern.getContractDTOByNo(request.getParameter("txtContractNo"));
	String cno=dto.getContractNo();
	String cname=dto.getName();
	String cdescription=dto.getDescription();
	if(cdescription==null)
		cdescription="";
	%>
   <table width="100%"  border="0" align="center" cellpadding="4" cellspacing="1" class="list_bg">
        <tr>  
           <td  width="17%" class="list_bg2" align="right">合同编号</td>
           <td  width="33%" class="list_bg1" align="left">
           <%=cno%> 
           </td>  
           <td  width="17%" class="list_bg2" align="right">合同名称</td>
           <td  width="33%" class="list_bg1" align="left">       
           <%=cname%>            
           </td>
        </tr>
        <tr>  
           <td  class="list_bg2" align="right">合同描述</td>
           <td  class="list_bg1" align="left" colspan="3">
           <%=cdescription%>   
           </td>  
        </tr>
        <%
         Collection packageIDCol=Postern.getProductPackageIDByContractNo(request.getParameter("txtContractNo"));
         Iterator itr=packageIDCol.iterator();
         String packageName="";
         String packageIDList = "";
         while(itr.hasNext()){
         	Integer packageID=(Integer)(itr.next());
         	if(!("".equals(packageName)))
				  {
				  	packageName+=";";
				  	packageIDList+=";";
				  }
         	packageName=packageName+Postern.getPackagenameByID(packageID.intValue());
         	packageIDList=packageIDList+packageID;
         }
        %>
	<tr>  
           <td  class="list_bg2" align="right">产品包</td>
           <td  class="list_bg1" align="left" colspan="3">
         <%=packageName%>    
          </td>  
        </tr>
     </table> 

<table width="820" border="0" align="center" cellpadding="4" cellspacing="1" class="list_bg">
	<tr>
	  <td width="100%" class="import_tit" align="center">选择相应硬件设备</td>
	</tr>
  </table>
      
 <table width="100%"  border="0" align="center" cellpadding="4" cellspacing="1" class="list_bg">
<%
if (terminalDeviceList.equals("")) {
 						deviceProductIds =""; 
 						deviceClassList="";
%>
<rs:hasresultset>
<lgc:bloop requestObjectName="ResponseQueryResult" item="oneline">

<%
	PackageLineDTO packageLineDTO=(PackageLineDTO)pageContext.getAttribute("oneline");
	int productId = packageLineDTO.getProductId();
	DeviceClassDTO deviceClassDTO=Postern.getDeviceClassByProductID(productId);
	ProductList=ProductList+packageLineDTO.getProductId()+";";
	if(!deviceClassList.equals("")){
		deviceProductIds=deviceProductIds+";";
	}
	if(deviceClassDTO.getDescription()!=null){
	
	if(!"".equals(deviceClassList))
	{
		deviceClassList=deviceClassList+";";
	}
	deviceClassList=deviceClassList + deviceClassDTO.getDeviceClass();
	deviceProductIds =deviceProductIds + productId;
	if(!"".equals(deviceClassDescription))
	{
		deviceClassDescription =deviceClassDescription +";";
	}
	deviceClassDescription =deviceClassDescription+deviceClassDTO.getDescription();
	
%>
	  <tr>
	      <td width="17%" align="right" class="list_bg2"><%=deviceClassDTO.getDescription()%></td>
	      <td  width="83%" align="left" class="list_bg1">
	 	 <input type="text" name="<%=deviceClassDTO.getDeviceClass()%>" size="20" maxlength="20"  value="" class="text">
	          <input type=button value="选择" onclick="javascript:query_device_item('<%=productId%>','<%=deviceClassDTO.getDeviceClass()%>',this);" class="text">
	      </td>
	   </tr>
	   <%}%>
  </lgc:bloop>
</rs:hasresultset>  	
<% 
} else 
{
      for (int i=0; i<aTerminalDevice.length; i++){
%>
	 <tr>
	    <td width="17%" align="right" class="list_bg2"><%=aTerminalDeviceDescription[i]%></td>
	    <td width="83%" align="left" class="list_bg1">
		 <input type="text" name="<%=aTerminalDeviceName[i]%>" size="20" maxlength="20"  value="<%=aTerminalDevice[i]%>" class="text">
	         <input type=button value="选择" onclick="javascript:query_device_item('<%=aTerminalProductId[i]%>','<%=aTerminalDeviceName[i]%>',this);" class="text">
	    </td>
	</tr>
<%  }
}
%>

      <tr>
         <td  align="right" class="list_bg2">押金*</td>
	 <td  align="left" class="list_bg1">
	      <input type="text" name="txtForceDepostMoney" size="20" maxlength="10" value="<%=WebUtil.bigDecimalFormat(txtForceDepostMoney)%>" class="text" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	 </td>
      </tr>

  </table> 

  <input type="hidden" name="DeviceClassDescription" value="<%=deviceClassDescription%>" >
  <input type="hidden" name="DeviceClassList" value="<%=deviceClassList%>">
  <input type="hidden" name="TerminalDeviceList" value="<%=terminalDeviceList%>">
  <input type="hidden" name="DeviceProductIds" value="<%=deviceProductIds%>">
  <input type="hidden" name="packageIDList" value="<%=packageIDList%>">

 
<%
//======这里应当是产品包id 的list才对  chaoqiu 2007-04-10
ProductList = packageIDList;
//====== chaoqiu 2007-04-10
//T_Service.ServiceID == 3，语音业务，需要选择电话号码
boolean bNeedPhoneNo = false;
System.out.println("ProductList = " + ProductList);
String strServiceIDs = Postern.getServiceIDByProductPackageIDs(ProductList);
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
			<div align="right">号码级别</div>
			</td>
			<td class="list_bg1" width="83%">
				<d:selcmn name="txtGrade" mapName="SET_R_PHONENOGRADE" match="txtGrade" width="23" />
			</td>
		</tr>
		<tr>
			<td class="list_bg2"><div align="right">电话号码</div></td>
			<td class="list_bg1">
			<input type="text" name="phoneNo" size="25" maxlength="50" value="<tbl:writeparam name="phoneNo" />" class="text">
			<input name="Submit" type="button" class="button" value="查询" onclick="javascript:phoneNo_Search()"> 支持模糊查询，“_”代表一位，“%”代表多位。</td>
			
		</tr>
	</table>
	<input type="hidden" name="itemID" value="<tbl:writeparam name="itemID" />">
<%	}
}%> 
<br>
  <table width="100%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
	<tr>
	  <td><img src="img/mao.gif" width="1" height="1"></td>
	</tr>
   </table>
<br>     
<tbl:hiddenparameters pass="txtAccount/txtGroupCustID/txtType/txtOpenSourceType" />
<tbl:hiddenparameters pass="OpenCampaignList/MutiProductList/SingleProductList/GeneralCampaignList" /> 
<input type="hidden" name="ProductList" value="<%=ProductList%>">
<input type="hidden" name="txtPackageName" value="<%=packageName%>" >
<input type="hidden" name="txtContractNo" value="<%=cno%>"> 
<input type="hidden" name="txtContractDescription" value="<%=cdescription%>"> 
<input type="hidden" name="txtContractName" value="<%=cname%>">        
</form>   
       <br>
        <table align="center" border="0" cellspacing="0" cellpadding="0" >
	<tr >
	   <td><img src="img/button2_r.gif" width="22" height="20"></td>
	   <td><input name="prev" type="button" class="button" onClick="javascript:back_submit()" value="上一步"></td>
	   <td><img src="img/button2_l.gif" width="11" height="20"></td>
	   <td width="20" ></td>
	   <td><img src="img/button_l.gif" border="0" width="11" height="20"></td>
	   <td><input name="next" type="button" class="button" onClick="javascript:frm_submit()" value="下一步"></td>
	   <td><img src="img/button_r.gif" border="0" width="22" height="20"></td>
	</tr>
    </table>
    
     
    <br>  
    <table width="100%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
	  <tr>
	     <td><img src="img/mao.gif" width="1" height="1"></td>
	   </tr>
    </table>