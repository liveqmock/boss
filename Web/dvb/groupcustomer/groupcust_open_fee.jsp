<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="bookmark" prefix="bk" %>
<%@ taglib uri="resultset" prefix="rs" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="osstags" prefix="d" %>

<%@ page import="com.dtv.oss.dto.CustomerComplainDTO,com.dtv.oss.dto.ContractDTO,
                 com.dtv.oss.dto.CustComplainProcessDTO" %>
<%@ page import="com.dtv.oss.util.Postern,java.util.*"%>
<%@ page import="com.dtv.oss.web.util.WebUtil,
				 com.dtv.oss.web.util.CommonKeys" %>
<%
	String txtAccountIDName=Postern.getAcctNameById(WebUtil.StringToInt(request.getParameter("txtAccountID")));
	
	String txtAgentCardType=request.getParameter("txtAgentCardType");

	
%>				 
				 
<script language=javascript>

	function cust_camplain_transfer(){
		if(document.frmPost.txtNextOrgId.value==""){
			alert("请选择部门！");
			return;
		}
		
		document.all.frmPost.action="cust_camplain_transferAction.do";
		document.all.frmPost.submit(); 
		
	}
	function last_step(){
		if("terSelect" =="<%=request.getParameter("txtPageType")%>" )
		{
			document.all.frmPost.action="groupcust_device_foropen.screen";
		}
		else
		{
			document.all.frmPost.action="group_cust_open.screen";
		}
		document.all.frmPost.submit(); 
	}
	function groupcust_open(){
		//到支付页面
		document.all.frmPost.action="groupcust_fee_foropen.screen";
		document.all.frmPost.submit(); 
	}
// add by chaoqiu 2007-04-29 	begin
function frm_next(csiPayOption){
	 if (check_ProductProperty()){
	    document.frmPost.txtcsiPayOption.value =csiPayOption;
	    if (check_fee()){//到支付页面
	       document.frmPost.action="groupcust_fee_foropen.screen";
	    }else{//无强制预存 无费用 直接确认
	 	     document.frmPost.action="groupcust_open.do";
	 	     document.frmPost.confirm_post.value="true";
	    }
	    document.frmPost.submit();
	 }
}

function frm_finish(csiPayOption){
	 if (check_ProductProperty()){
	    document.frmPost.txtcsiPayOption.value =csiPayOption;
	    document.frmPost.action="groupcust_open.do";
	    document.frmPost.confirm_post.value="true";
	    document.frmPost.submit();
	 }
}

function check_ProductProperty()
{
	for(i=0;i<document.frmPost.length;i++)
	{
		var element=document.frmPost.elements[i];
		if((""+element.type)=="text" ||(""+element.type)=="password")
		{
			var strEleName = "" + element.name;
			var arrEleName = element.name.split("_");
			if( arrEleName[0] == "txtProductProperty")
			{
				if(element.value=="")
				{
					alert("产品属性输入不完整，请重新检查！");
					element.focus();
					return false;
				}
			}
			
		}
	}
	return true;
}
// add by chaoqiu 2007-04-29 	end

	
</script>
<table  border="0" align="center" cellpadding="0" cellspacing="10" >
  <tr>
    <td><img src="img/list_tit.gif" width="19" height="19"></td>
    <td class="title">费用信息</td>
  </tr>
</table>
 
<table width="100%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table> 
<form name="frmPost" action="cust_complain_query.do" method="post" > 
<input type="hidden" name="txtActionType" value="<%=CommonKeys.OPEN_CHILD_CUSTOMER%>" >
<input type="hidden" name="txtcsiPayOption" > 
<input type="hidden" name="confirm_post" value="false" >
<tbl:hiddenparameters pass="txtCustomerID/DeviceClassList/DeviceClassDescription/TerminalDeviceList/payFlag/txtCounty/phoneNo/itemID/txtAgentCardType/txtChildCustID/DeviceProductIds" /> 
<table border="0" cellpadding="0" cellspacing="0" width="100%" >
        <tr ><td> 
          <jsp:include page="group_contract.jsp"/>
        </td></tr> 
</table>
  <table width="100%" align="center" border="0" cellspacing="1" cellpadding="5" class="list_bg">
    <tr>
         <td colspan="4" class="import_tit" align="center"><font size="3">基本信息</font></td>
     </tr>
     <tr>   
      <td class="list_bg2" width="17%"  align="right">帐户</td>
      <td class="list_bg1" width="33%"  align="left">
      	<input type="text" name="txtAccountIDName" size="25" value="<%=txtAccountIDName%>"  class="textgray" readonly >
		<input type="hidden" name="txtAccountID" size="25" value="<tbl:writeparam name="txtAccountID"/>"  class="textgray" readonly >
	  </td>
      <td class="list_bg2" width="17%"  align="right">联系人</td>
      <td class="list_bg1" width="33%"  align="left">
		<input type="text" name="txtName" size="25" value="<tbl:writeparam name="txtName"/>"   class="textgray" readonly>
	  </td>
    </tr>
    <tr>   
      <td class="list_bg2" width="17%"  align="right">联系电话</td>
      <td class="list_bg1" width="83%" colspan="3" align="left">
		<input type="text" name="txtTelephone" size="25" value="<tbl:writeparam name="txtTelephone"/>"  class="textgray" readonly >
	  </td>
    </tr>
    <tr>
         <td colspan="4" class="import_tit" align="center"><font size="3">代理人信息</font></td>
     </tr>
     <tr>   
      <td class="list_bg2" width="17%"  align="right">姓名</td>
      <td class="list_bg1" width="33%"  align="left">
		<input type="text" name="txtAgentName" size="25" value="<tbl:writeparam name="txtAgentName"/>" class="textgray" readonly>
	  </td>
      <td class="list_bg2" width="17%"  align="right">联系电话</td>
      <td class="list_bg1" width="33%"  align="left">
		<input type="text" name="txtContactPhone" size="25" value="<tbl:writeparam name="txtContactPhone"/>" class="textgray" readonly>
	  </td>
    </tr>
    <tr>   
      <td class="list_bg2" width="17%"  align="right">证件类型</td>
      <td class="list_bg1" width="33%"  align="left">
		<input type="text" name="txtAgentCardTypeName" size="25" value="<d:getcmnname typeName="SET_C_CUSTOMERCARDTYPE" match="<%=txtAgentCardType%>" /> " class="textgray" readonly>
	  </td>
      <td class="list_bg2" width="17%"  align="right">证件号码</td>
      <td class="list_bg1" width="33%"  align="left">
		<input type="text" name="txtAgentCardID" size="25" value="<tbl:writeparam name="txtAgentCardID"/>" class="textgray" readonly>
	  </td>
    </tr>
    <% 
    	Collection col=Postern.getPackageIDByContractID(request.getParameter("txtContractNo")==null?"":request.getParameter("txtContractNo"));
    	Iterator ite=col.iterator();	
    	ite=col.iterator();
		String strPkgID="";
		while(ite.hasNext())
	   {
	       strPkgID = ite.next().toString();
	%>

	<tbl:productproperty serveyName="txtProductProperty" packageID="<%=strPkgID%>"  showType="label" tdWordStyle="list_bg2"  tdControlStyle="list_bg1" controlSize="25" headStyle="list_head" />
	<% }%>
</table>

	
<table align="center" width="100%" border="0" cellspacing="1" cellpadding="3" class="list_bg" >
    <tr ><td> 
       <tbl:CommonFeeAndPaymentInfo includeParameter="Fee" payCsiType="<%=CommonKeys.CUSTSERVICEINTERACTIONTYPE_GO%>" acctshowFlag ="false" confirmFlag="false" />
       	
       	<!--
       	类型是
       CUSTSERVICEINTERACTIONTYPE_UO 还是 CUSTSERVICEINTERACTIONTYPE_GO ？？？？
       -->
       
    </td></tr> 
</table>
  <br>
 <table align="center"  border="0" cellspacing="0" cellpadding="0">
      <tr>  
        	<td><img src="img/button2_r.gif" width="22" height="20"></td>
	   		<td background="img/button_bg.gif"><a href="javascript:last_step()" class="btn12">上一步</a></td> 
	   		<td><img src="img/button2_l.gif" width="11" height="20"></td> 
	   		
	   		<!--
	   		   update by chaoqiu 2007-04-29 用下面的common_control.jsp代替 适用灵活的计费方式
           	<td width="20" ></td>    
           	<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
	   		<td background="img/button_bg.gif"><a href="javascript:groupcust_open()" class="btn12">立即支付<a></td> 
	   		<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td> 
	   		
	   		-->
	   		<jsp:include page = "/fee/common_control.jsp"/>
      </tr>
   
      
      
  </table> 
</form> 

    
   