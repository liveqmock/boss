<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="osstags" prefix="d" %>
<%@ taglib uri="bookmark" prefix="bk" %>
<%@ taglib uri="privilege" prefix="pri" %>

<%@ page import="com.dtv.oss.util.Postern,com.dtv.oss.web.util.CommonKeys,java.util.*" %>
<%@ page import="com.dtv.oss.dto.TerminalDeviceDTO,
                 com.dtv.oss.dto.CPCampaignMapDTO,
                 com.dtv.oss.dto.ProductDTO,
                 com.dtv.oss.dto.ProductPropertyDTO,
                 com.dtv.oss.dto.CustomerCampaignDTO" %>
<%@ page import="com.dtv.oss.util.Postern" %>
<%@ page import="com.dtv.oss.web.util.WebUtil" %>
<% 
	TerminalDeviceDTO tdDTO =null;
%>

<Script language=javascript>
function add_submit(psid,said,cid){
	document.location.href = "menu_add_customer_billingrule.do?txtPSID="+psid+"&&txtServiceAccountID="+said+"&&txtCustomerID="+cid;
}
<!--

function suspend_submit()
{
	if (check_Blank(document.frmPost.txtEventReason, true, "暂停原因"))
	{	
        	return;
        }
        
        if (window.confirm('您确认要暂停该产品吗?'))
	{
	    document.frmPost.action="customer_product_stop_create.screen";
	    document.frmPost.Action.value="suspend";
	    document.frmPost.submit();
	}
}

function resume_submit()
{
        if (window.confirm('您确认要恢复该产品吗?'))
	{
	    document.frmPost.action="customer_product_resume_op.do";
	    document.frmPost.Action.value="resume";
	    document.frmPost.submit();
	}
}

function cancel_submit()
{
        if (window.confirm('您确认要取消该产品吗?'))
	{
	    //document.frmPost.action="customer_product_cancel_create.screen";
	    document.frmPost.action="customer_product_cancel.do";
	    document.frmPost.txtActionType.value="<%=CommonKeys.CUSTOMER_PRODUCT_CANCEL%>";
	    document.frmPost.Action.value="cancel";
	    document.frmPost.submit();
	}
}

function device_swap_submit()
{       if (window.confirm('您确认要更换设备吗?'))
    {
     document.frmPost.action="customer_device_swap_op.do";
     document.frmPost.txtActionType.value="<%=CommonKeys.DEVICE_SWAP%>";
     document.frmPost.Action.value="suspend";
     document.frmPost.confirm_post.value ="false";
     document.frmPost.submit();
   }
}

function device_upgrade_submit()
{       if (window.confirm('您确认要设备升级吗?'))
    {
     document.frmPost.action="customer_device_upgrade_op.do";
     document.frmPost.txtActionType.value="<%=CommonKeys.DEVICE_UPGRADE%>";
     document.frmPost.Action.value="suspend";
     document.frmPost.confirm_post.value ="false";
     document.frmPost.submit();
   }
}

function auth_submit()
{
        if (window.confirm('您确认要重发受权吗?'))
	{
		document.frmPost.action="resendEMM_submit.do";
		document.frmPost.txtAccount.value=document.frmPost.txtAccountID.value;
	    document.frmPost.txtActionType.value="<%=CommonKeys.CUSTOMER_PRODUCT_RESEND%>";
		document.frmPost.submit();
	}
}

function clear_pwd_submit()
{
        if (window.confirm('您确认要清除该产品密码么?'))
	{
	    document.frmPost.action="cancel_password.do";
	    document.frmPost.txtAccount.value=document.frmPost.txtAccountID.value;
	    document.frmPost.txtActionType.value="<%=CommonKeys.CUSTOMER_PRODUCT_CLEARPASSWORD%>";
	    document.frmPost.submit();
	}
}
function rel_submit()
{
        if (window.confirm('您确认要对该产品配对么?'))
	{
	    document.frmPost.action="product_partnership.do";
	    document.frmPost.txtAccount.value=document.frmPost.txtAccountID.value;
	    document.frmPost.txtActionType.value="<%=CommonKeys.CUSTOMER_PRODUCT_PARTNERSHIP%>";
	    document.frmPost.submit();
	}
}
function cancel_rel_submit()
{
        if (window.confirm('您确认要取消该产品配对么?'))
	{
	    document.frmPost.action="cancel_product_partnership.do";
	    document.frmPost.txtAccount.value=document.frmPost.txtAccountID.value;
	    document.frmPost.txtActionType.value="<%=CommonKeys.CUSTOMER_PRODUCT_CANCELPARTNERSHIP%>";
	    document.frmPost.submit();
	}
}
function send_mail_submit()
{
        if (window.confirm('您确认要对该产品发送消息么?'))
	{
	    document.frmPost.action="send_mail.do";
	    document.frmPost.txtActionType.value="<%=CommonKeys.CUSTOMER_PRODUCT_SENDMAIL%>";
	    document.frmPost.submit();
	}
}
function recommand_submit()
{
        if (window.confirm('您确认要对产品进行重发CA命令么?'))
	{
	    document.frmPost.action="send_special_ca.do";
	    document.frmPost.Action.value="send_special_ca";
	    document.frmPost.submit();
	}
}

function upgrade_submit()
{
      if(ruleCheck()){
        if (window.confirm('您确认要对产品进行升级么?'))
	{
	    document.frmPost.action="customer_product_updown_grade.screen";
	    document.frmPost.txtActionType.value="<%=CommonKeys.CUSTOMER_PRODUCT_UPGRADE%>";
	    document.frmPost.txtAccount.value=document.frmPost.txtAccountID.value;
	    document.frmPost.submit();
	}
     }
}
function ruleCheck()
{
      
      if(document.frmPost.txtAllowAlter.value =="N"){
         alert("该产品在促销活动期间不允许做升降级处理");
        return false;
       }
       return true;
}
function downgrade_submit()
{       
        if(ruleCheck()){
        if (window.confirm('您确认要对产品进行降级么?'))
	{
	    document.frmPost.action="customer_product_updown_grade.screen";
	    document.frmPost.txtActionType.value="<%=CommonKeys.CUSTOMER_PRODUCT_DOWNGRADE%>";
	    document.frmPost.txtAccount.value=document.frmPost.txtAccountID.value;
	    document.frmPost.submit();
	}
	}
}
function product_property_view(productID){
	   document.frmPost.action="cpconfigedproperty_view.screen?txtProductID="+productID;
     document.frmPost.submit();
}
function modifyDeviceProvide(){
	var psid=document.frmPost.txtPsID.value;
	var dProvideFlag=document.frmPost.txtDeviceProvideKey.value;

	if(psid!=null&&psid!=''){
	  var  strFeatures = "top=120px,left=120px,width=350px,height=350px,resizable=no,toolbar=no,scrollbars=no";
	  window.open("customer_product_provide_modify.screen?txtPsID="+psid+"&txtDeviceProvide="+dProvideFlag,"客户设备来源修改",strFeatures);
  }
}
function modifyAccount(){
	var  psid =document.frmPost.txtPsID.value;
	var  accountId =document.frmPost.txtAccountID.value;
	
	var  strFeatures = "top=120px,left=120px,width=350px,height=350px,resizable=no,toolbar=no,scrollbars=no";
	window.open("customer_product_account_modify.screen?txtPsID="+psid+"&txtAccountID="+accountId,"客户付费账户修改",strFeatures);
}
function product_property_edit(productID,psID,propertyCode){
	 document.frmPost.action="cpconfigedproperty_edit.screen?txtProductID="+productID+"&txtPsID="+psID+"&txtPropertyCode="+propertyCode;
   document.frmPost.submit();
}
//-->
</SCRIPT>
<%
//是否起用设备用途：
	String devicePurpose = Postern.getSystemsettingValueByName("SET_D_DEVICEPURPOSE");
%>

<table  border="0" align="center" cellpadding="0" cellspacing="10" >
  <tr>
    <td><img src="img/list_tit.gif" width="19" height="19"></td>
    <td class="title">查看用户产品信息</td>
  </tr>
</table>
<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>
<br>
<form name="frmPost" method="post">
<% 
  String allowAlter="";
  String cpStatusCol="";
%>
<lgc:bloop requestObjectName="ResponseQueryResult" item="oneline" isOne="true">
<%
          com.dtv.oss.dto.CustomerProductDTO productDTO=(com.dtv.oss.dto.CustomerProductDTO)pageContext.getAttribute("oneline");
          String strProdName = Postern.getProductNameByID(productDTO.getProductID());
          
          if (strProdName==null) strProdName="";
          
          String strName = Postern.getCustomerNameByID(productDTO.getCustomerID());
          if (strName==null) strName="";
          String strPackageName =Postern.getPackagenameByID(productDTO.getReferPackageID());
          
          String strStatus = productDTO.getStatus();
          if (strStatus==null) strStatus="";
          
         // String custStatus = Postern.getCustomerStatusByID(productDTO.getCustomerID());
         // pageContext.setAttribute("custStatus",custStatus);
          
          CPCampaignMapDTO cPCampaignMapDTO = Postern.getCPCampaignMapByCustProductID(productDTO.getPsID());
          CustomerCampaignDTO custCampaignDto =Postern.getCustomerCampaignByccID(cPCampaignMapDTO.getCcId());
          if (custCampaignDto ==null) custCampaignDto =new CustomerCampaignDTO();
          allowAlter = custCampaignDto.getAllowAlter();
          
          java.util.ArrayList propertyList=Postern.getProductPropertyListByProductID(productDTO.getProductID());
          
          ProductDTO pDto = Postern.getProductDTOByProductID(productDTO.getProductID());
          String productClass = "";
          String newsaFlag = "";
          if(pDto != null) 
          {
          	newsaFlag=pDto.getNewsaFlag();
          	productClass = pDto.getProductClass();
          }
					cpStatusCol=cpStatusCol+","+productDTO.getStatus()+"-"+(newsaFlag==null?"N":newsaFlag)+"-"+productClass;
					pageContext.setAttribute("cpStatusCol", cpStatusCol);
					
          
%>     
       <table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
        <tr> 
          <td colspan="4" class="import_tit" align="left"><font size="3">客户产品信息</font></td>
        </tr>
        <tr>
          <td valign="middle" class="list_bg2"  align="right" width="17%">PSID</td>
          <td class="list_bg1" width="33%"><font size="2">
            <input type="text" name="txtPsID" size="25"  value="<tbl:write name="oneline" property="psID" />" class="textgray" readonly >     
          </font></td> 
          <td valign="middle" class="list_bg2"  align="right" width="17%">产品名称</td>
          <td class="list_bg1" width="33%"><font size="2">
            <input type="text" name="txtProductName" size="25" readonly value="<%=strProdName%>" class="textgray" readonly >        
          </td>
        </tr>
        <tr>
          <td valign="middle" class="list_bg2"  align="right" >产品包名称</td>
          <td class="list_bg1"><font size="2">
            <input type="text" name="txtPackageName" size="25" value="<%=strPackageName%>" class="textgray" readonly >         
          </font></td>
          <td valign="middle" class="list_bg2"  align="right" >合同号</td>
	        <td class="list_bg1" ><font size="2">
	         <input type="text" name="txtContractNo" size="25"  value="<tbl:write name="oneline" property="contractNo" />" class="textgray" readonly >
	        </font></td>
        </tr>
        <tr>
          <td valign="middle" class="list_bg2"  align="right" >用户名</td>
          <td class="list_bg1"><font size="2">
            <input type="text" name="txtCustomerName" size="25" value="<%=strName%>" class="textgray" readonly >         
          </font></td>
          <td valign="middle" class="list_bg2"   align="right" >状态</td>
          <td class="list_bg1"><font size="2">
            <input type="text" name="txtStatusShow" size="25" value="<d:getcmnname typeName="SET_C_CUSTOMERPRODUCTPSTATUS" match="oneline:status" />" class="textgray" readonly >
          </font>
          	<input type="hidden" name="txtStatus" value="<tbl:write name="oneline" property="status"/>" class="textgray" readonly >
          </td>
        </tr> 
        <tr>
          <td valign="middle" class="list_bg2"  align="right" >业务帐号</td>
          <td class="list_bg1"><font size="2">
            <input type="text" name="txtServiceAccountID" size="25"  value="<tbl:write name="oneline" property="serviceAccountID" />" class="textgray" readonly >
          </font></td>
          <td valign="middle" class="list_bg2"   align="right">帐号</td>
          <td class="list_bg1"><font size="2">
            <input type="text" name="txtAccountID" size="25"  value="<tbl:write name="oneline" property="accountID" />" class="textgray" readonly >
            </font>
		    	<d:displayControl id="button_customer_product_view_account_modify" bean="tdDTO,oneline">
		    	<pri:authorized name="customer_product_account_modify.screen" >
            	<input name="modifyAccountButton" type="button" class="button" value="修改" onClick="javascript:modifyAccount()">
				 </pri:authorized>
				  </d:displayControl> 
          </td>  
        </tr>
<%
    if (productDTO.getDeviceID()!=0)
    {
        tdDTO = Postern.getTerminalDeviceByID(productDTO.getDeviceID());
        if (tdDTO != null)
        { 
				    pageContext.setAttribute("tdDTO",tdDTO);
        	
            String strSerialNo = tdDTO.getSerialNo();
            String strDeviceClass = tdDTO.getDeviceClass();
	    String strDeviceModel = tdDTO.getDeviceModel();
	    
	    String macAddress=tdDTO.getMACAddress();
	    String interMacAddress=tdDTO.getInterMACAddress();
	    if(macAddress==null)macAddress="";
	    if(interMacAddress==null)interMacAddress="";
	    
	    Map mapDeviceClasses = Postern.getAllDeviceClasses();
    	    String strDeviceClassShow = (String)mapDeviceClasses.get(strDeviceClass);
    	    Map mapDeviceModels = Postern.getAllDeviceModels();
    	    String strDeviceModelShow = (String)mapDeviceModels.get(strDeviceModel);
    	    if(strDeviceModelShow==null){
    	    	strDeviceModelShow="";
    	    }
    	    
    	    //设备用途 begin
					String purposeStrList = tdDTO.getPurposeStrList();
					String totalValue="";
					if(purposeStrList!=null)
				  { 
				       String[] purposeArray=purposeStrList.split(",");
				       for(int j=0;j<purposeArray.length;j++){
				       String value = Postern.getHashValueByNameKey("SET_D_DEVICEUSEFORPURPOSE",purposeArray[j]);
				       if(value != null && !"".equals(value))
				       {
					       if(totalValue=="")
					        totalValue=value;
					       else 
					         totalValue=totalValue+","+value;
					       }
				       }
				      
				   }
				  //设备用途 end
%>         
        <tr>
          <td valign="middle" class="list_bg2"  align="right" >设备序列号</td>
          <td class="list_bg1"><font size="2">
            <input type="text" name="txtSerialNo" size="25"  value="<%=strSerialNo%>" class="textgray" readonly >
          </font></td>
          <td valign="middle" class="list_bg2"  align="right" >设备号</td>
          <td class="list_bg1"><font size="2">
            <input type="text" name="txtDeviceID" size="25"  value="<tbl:write name="oneline" property="deviceID" />" class="textgray" readonly >
          </font></td>
        </tr> 
        <tr>
          <td valign="middle" class="list_bg2"  align="right" >设备类型</td>
          <td class="list_bg1"><font size="2">
             <input type="hidden" name="txtDeviceClass"  value="<%=strDeviceClass%>"  >
             <input type="text" name="txtDeviceClassShow" size="25"  value="<%=strDeviceClassShow%>" class="textgray" readonly >
          </font></td>
          <td valign="middle" class="list_bg2"  align="right" >设备型号</td>
          <td class="list_bg1"><font size="2">
             <input type="text" name="txtDeviceModelShow" size="25"  value="<tbl:write name="tdDTO" property="deviceModel" />" class="textgray" readonly >
          </font></td>
        </tr>  
        <tr>
          <td valign="middle" class="list_bg2"  align="right" >CM MAC地址</td>
          <td class="list_bg1"><font size="2">
             <input type="text" name="txtMacAddress" size="25"  value="<%=macAddress%>" class="textgray" readonly >
          </font></td>
          <td valign="middle" class="list_bg2"  align="right" >终端MAC地址</td>
          <td class="list_bg1"><font size="2">
             <input type="text" name="txtInterMacAddress" size="25"  value="<%=interMacAddress%>" class="textgray" readonly >
          </font></td>
        </tr>
        <%if("Y".equals(devicePurpose)){%>
                 
        <tr>
          <!--
          <td valign="middle" class="list_bg2"  align="right" >设备来源</td>
          <td class="list_bg1">
          	<input name="txtDeviceProvideFlag" type="text"
          class="textgray" readonly maxlength="20" size="25"
	  value="<d:getcmnname typeName="SET_C_CP_DEVICEPROVIDEFLAG" match="oneline:deviceProvideFlag" />">
       	<d:displayControl id="button_customer_product_view_deviceProvidemodify" bean="oneline">
       	<pri:authorized name="customer_product_provide_modify.screen" >
  					<input name="modifyDeviceProvideButton" type="button" class="button" value="修改" onClick="javascript:modifyDeviceProvide()">
  		</pri:authorized>			
  					<input type="hidden" name="txtDeviceProvideKey" value="<tbl:write name="oneline" property="deviceProvideFlag" />"  >
				</d:displayControl>
	  </td>
	  -->
	  <td class="list_bg2"><div align="right">设备用途</div></td>
          <td class="list_bg1" colspan="3">
        	<input type="text" name="txtPurposeStrListValue" size="82"  value="<%=totalValue%>" class="textgray" readonly >
        	<input type="hidden" name="txtPurposeStrList" value="<tbl:write name="oneline" property="PurposeStrList" />"          
         </td>
        </tr> 
        <%} else{%> 
        <tr>
          <td valign="middle" class="list_bg2"  align="right" >设备来源</td>
          <td class="list_bg1" colspan="3">
          	<input name="txtDeviceProvideFlag" type="text"
          class="textgray" readonly maxlength="20" size="25"
	  value="<d:getcmnname typeName="SET_C_CP_DEVICEPROVIDEFLAG" match="oneline:deviceProvideFlag" />">
       	<d:displayControl id="button_customer_product_view_deviceProvidemodify" bean="oneline">
       		<pri:authorized name="customer_product_provide_modify.screen" >
  					<input name="modifyDeviceProvideButton" type="button" class="button" value="修改" onClick="javascript:modifyDeviceProvide()">
  			</pri:authorized>		
  					<input type="hidden" name="txtDeviceProvideKey" value="<tbl:write name="oneline" property="deviceProvideFlag" />"  >
				</d:displayControl>
	  			</td>
	  		
        </tr>          
<%        }
        }       
    }
%>              
      <tr>
	  <td valign="middle" class="list_bg2"  align="right" >创建时间</td>
	  <td class="list_bg1"><font size="2">
		<input type="text" name="txtCreateTime" size="25"  value="<tbl:write name="oneline" property="createTime" />" class="textgray" readonly >
	  </font></td>
	  <td valign="middle" class="list_bg2"  align="right" >激活时间</td>
	  <td class="list_bg1"><font size="2">
		<input type="text" name="txtActivateTime" size="25"  value="<tbl:write name="oneline" property="activateTime" />" class="textgray" readonly >
	  </font></td>
	</tr>
        <tr>
	  <td valign="middle" class="list_bg2"  align="right" >暂停时间</td>
	  <td class="list_bg1"><font size="2">
		<input type="text" name="txtPauseTime" size="25"  value="<tbl:write name="oneline" property="pauseTime" />" class="textgray" readonly >
	  </font></td>
	  <td valign="middle" class="list_bg2"  align="right" >取消时间</td>
	  <td class="list_bg1"><font size="2">
		<input type="text" name="txtCancelTime" size="25"  value="<tbl:write name="oneline" property="cancelTime" />" class="textgray" readonly >
	  </font></td>
	</tr>
	<tr>
	   <td valign="middle" class="list_bg2"  align="right" >起始有效日期</td>
	   <td class="list_bg1"><font size="2">
		<input type="text" name="txtDateFrom" size="25"  value="<tbl:writedate name="oneline" property="dateFrom" />" class="textgray" readonly >
	   </font></td>
	   <td valign="middle" class="list_bg2"  align="right" >截止有效日期</td>
	   <td class="list_bg1"><font size="2">
		<input type="text" name="txtDateTo" size="25"  value="<tbl:writedate name="oneline" property="dateTo" />"  class="textgray" readonly >
	   </font></td>
	</tr>

	<input type="hidden" name="txtCustomerID"  value="<tbl:write name="oneline" property="CustomerID" />"  >
  <input type="hidden" name="txtProdID" value="<tbl:write name="oneline" property="ProductID" />">       
</table>
	<%	ArrayList propsList=Postern.getProductPropertyListByProductID(productDTO.getProductID());					
	%>
	
	
	<%
		Map match=Postern.getProductPropertyValueByProductID("productProperty_"+String.valueOf(productDTO.getProductID())+"_",String.valueOf(productDTO.getPsID()));
		//System.out.println("match===================="+match);
		//pageContext.setAttribute("mapMatch",match);
	%>
	
	<!--
	<table width="98%"  border="0" align="center" border="0" cellspacing="1" cellpadding="5" class="list_bg">
    	<tr ><td width="17%"></td><td width="33%"></td><td width="17%"></td><td width="33%"></td></tr>
    	<tbl:productproperty serveyName="productProperty" match="mapMatch" showType="label" productID="<%=String.valueOf(productDTO.getProductID())%>"  headStyle="list_head"  tdWordStyle="list_bg2" tdControlStyle="list_bg1"/>
    	<tr>
			<td colspan="4" class="list_foot"></td>
		</tr>
    </table>
	-->
	
	<% if(!match.isEmpty()){ //客户产品属性不为空 t_cpconfigedproperty %>
	<table width="98%"  border="0" align="center" cellpadding="5" cellspacing="0" class="list_bg">
        <tr> 
          <td colspan="3" class="import_tit" align="left"><font size="3">客户产品属性</font></td>
        </tr>
    </table>
    
    <table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">       
        <tr class="list_head">
          <td class="list_head">属性名称</td>      
          <!--<td class="list_head">属性名称</td>-->
          <td class="list_head">属性取值</td>                           
        </tr>
        
        <%	for(Iterator itr=propsList.iterator();itr.hasNext();){
        		ProductPropertyDTO dto = (ProductPropertyDTO)itr.next();
        		if(Postern.getPropertyValueByPSIDAndPropertyCode(productDTO.getPsID(),dto.getPropertyCode()) != null){
        %>        
        <tbl:printcsstr style1="list_bg1" style2="list_bg2" overStyle="list_over" >
		<td align="center" >
		<!--<a href="javascript:product_property_edit('<tbl:write name="oneline" property="productID" />','<tbl:write name="oneline" property="psID" />','<%=dto.getPropertyCode()%>')"><d:getcmnname typeName="SET_P_PRODUCTPROPERTYCODE" match="<%=dto.getPropertyCode()%>" /></a>	        -->
			<a href="javascript:product_property_edit('<tbl:write name="oneline" property="productID" />','<tbl:write name="oneline" property="psID" />','<%=dto.getPropertyCode()%>')"><%=dto.getPropertyName()%></a>	        
		</td>
		<!--<td align="center" ><d:getcmnname typeName="SET_P_PRODUCTPROPERTYCODE" match="<%=dto.getPropertyCode()%>" /></td>-->
		<% if("30003".equals(dto.getPropertyCode())){ %>
		<td align="center" >******</td>
		<%}else{ %>
		<td align="center" ><%=Postern.getPropertyValueByPSIDAndPropertyCode(productDTO.getPsID(),dto.getPropertyCode())%></td>
		<%}%>
        </tbl:printcsstr>
        <%}
        }%>
        
		<tr>
			<td colspan="3" class="list_foot"></td>
		</tr>
        </table>   
   	  <%}%>
   
      <input type="hidden" name="func_flag" value="502">
      <input type="hidden" name="Action" value="">
      <input type="hidden" name="txtActionType" value="">
      <input type="hidden" name="txtAllowAlter" value="<%=allowAlter%>">
      <input type="hidden" name="txtAccount" value="">
      <input type="hidden" name="confirm_post" value="true">
      <input type="hidden" name="txtCPIDs" value="<tbl:write name="oneline" property="PsID"/>" >
      <br>
      <table width="98%"  border="0" align="center" cellpadding="5" cellspacing="0" class="list_bg">
        <tr> 
          <td width="90%" class="import_tit" align="left"><font size="3">客户产品个性化费率</font></td>
          <td width="8%" class="import_tit" align="left">
          <d:displayControl id="button_customer_billingrule_add" bean="<%=CommonKeys.CURRENT_CUSTOMER_SESSION_NAME%>">
          	<pri:authorized name="billing_rule_create.do">
          	<input name="Submit" type="button" class="button" onclick="javascript:add_submit('<%=productDTO.getPsID()%>','<tbl:write name="oneline" property="serviceAccountID" />','<tbl:write name="oneline" property="customerID" />')" value="新增" >
          	</pri:authorized>
          </d:displayControl>
          </td>
        </tr>
      </table>
      <table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" >
        <tr>
          <td> 
          <iframe name=cbrlist width=100%  frameborder=0  scrolling=no src="list_customer_billingrule.do?txtPSID=<%=productDTO.getPsID()%>&txtServiceAccountID=<%=productDTO.getServiceAccountID()%>&txtCustomerID=<%=productDTO.getCustomerID()%>" ></iframe>	
          </td>
        </tr>       
      </table>  
      <br>    
	<%if (productDTO.getDeviceID()!=0){%>
		<input type="hidden" name="deviceType" value="hardware">
	<%}else {%>
		<input type="hidden" name="deviceType" value="software">
	<%}%>
      
      	
      <table align="center" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <bk:canback url="service_account_query_result_by_sa.do/show_sacp_for_schedule.do" >  
            <td><img src="img/button2_r.gif" width="22" height="20"></td>  
             <td background="img/button_bg.gif">
             <a href="<bk:backurl property="service_account_query_result_by_sa.do/show_sacp_for_schedule.do/service_account_pause_view.do/service_account_resume_view.do" />" class="btn12">返回产品列表</a></td>
            <td><img src="img/button2_l.gif" width="13" height="20"></td>
        </bk:canback>                
		    <d:displayControl id="button_customer_product_view_upgrade" bean="oneline">
		          <td width="15" ></td>
		          <td><img src="img/button_l.gif" border="0" width="11" height="20"></td>
				      <td><input name="Submit" type="button" class="button" onclick="javascript:upgrade_submit()" value="产品升级"></td>
				      <td><img src="img/button_r.gif" border="0" width="22" height="20"></td>
				</d:displayControl> 
		    <d:displayControl id="button_customer_product_view_downgrade" bean="oneline">
		          <td width="15" ></td>
		          <td><img src="img/button_l.gif" border="0" width="11" height="20"></td>
				      <td><input name="Submit" type="button" class="button" onclick="javascript:downgrade_submit()" value="产品降级"></td>
				      <td><img src="img/button_r.gif" border="0" width="22" height="20"></td>
				</d:displayControl> 
        
    	<d:displayControl id="button_customer_product_view_device_swap" bean="oneline">
	         <td width="15" ></td>      
           <td><img src="img/button_l.gif" border="0" width="11" height="20" ></td>
           <td background="img/button_bg.gif"  ><a href="javascript:device_swap_submit()" class="btn12">更换设备</a></td>
           <td><img src="img/button_r.gif" border="0" width="22" height="20" ></td>
		  </d:displayControl> 
		  <d:displayControl id="button_customer_product_view_device_upgrade" bean="oneline">
		  <td width="15" ></td>      
           <td><img src="img/button_l.gif" border="0" width="11" height="20" ></td>
           <td background="img/button_bg.gif"  ><a href="javascript:device_upgrade_submit()" class="btn12">设备升级</a></td>
           <td><img src="img/button_r.gif" border="0" width="22" height="20" ></td>
           </d:displayControl>
		  <d:displayControl id="button_customer_product_view_cancel" bean="cpStatusCol">
							<pri:authorized name="customer_product_stop_op.do">
								<td width="15"></td>
								<td width="11" height="20"><img src="img/button_l.gif"
									width="11" height="20"></td>
								<td><input name="Submit" type="button" class="button"
									value="取消硬件产品" onclick="javascript:cancel_submit()"></td>
								<td width="22" height="20"><img src="img/button_r.gif"
									width="22" height="20"></td>
							</pri:authorized>
			</d:displayControl>
 	<d:displayControl id="body_customer_product_view_SC" bean="tdDTO,oneline">
    	<d:displayControl id="button_customer_product_view_auth" bean="tdDTO,oneline">
 	       <td width="15" ></td>
 	   	   <td><img src="img/button_l.gif" border="0" width="11" height="20"></td>
		     <td><input name="Submit" type="button" class="button" onclick="javascript:auth_submit()" value="重发授权"></td>
		     <td><img src="img/button_r.gif" border="0" width="22" height="20"></td>
		  </d:displayControl> 
    	<d:displayControl id="button_customer_product_view_clear_pwd" bean="tdDTO,oneline">
          <td width="15" ></td>
          <td><img src="img/button_l.gif" border="0" width="11" height="20"></td>
		      <td><input name="Submit" type="button" class="button" onclick="javascript:clear_pwd_submit()" value="清除密码"></td>
		      <td><img src="img/button_r.gif" border="0" width="22" height="20"></td>
		  </d:displayControl> 
    	<d:displayControl id="button_customer_product_view_rel" bean="tdDTO,oneline">
          <td width="15" ></td>
          <td><img src="img/button_l.gif" border="0" width="11" height="20"></td>
		      <td><input name="Submit" type="button" class="button" onclick="javascript:rel_submit()" value="配对"></td>
		      <td><img src="img/button_r.gif" border="0" width="22" height="20"></td>
		  </d:displayControl> 
    	<d:displayControl id="button_customer_product_view_cancel_rel" bean="tdDTO,oneline">
          <td width="15" ></td>
          <td><img src="img/button_l.gif" border="0" width="11" height="20"></td>
		      <td><input name="Submit" type="button" class="button" onclick="javascript:cancel_rel_submit()" value="解配对"></td>
		      <td><img src="img/button_r.gif" border="0" width="22" height="20"></td>
		  </d:displayControl> 
    	<d:displayControl id="button_customer_product_view_send_mail" bean="tdDTO,oneline">
		      <td width="15" ></td>
          <td><img src="img/button_l.gif" border="0" width="11" height="20"></td>
		      <td><input name="Submit" type="button" class="button" onclick="javascript:send_mail_submit()" value="发送消息"></td>
		      <td><img src="img/button_r.gif" border="0" width="22" height="20"></td>
		  </d:displayControl> 
	  </d:displayControl> 

        </tr>
      </table>  

      
</lgc:bloop>       
</form>





