<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="bookmark" prefix="bk" %>
<%@ taglib uri="resultset" prefix="rs" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="osstags" prefix="d" %>

<%@ page import="com.dtv.oss.dto.CustomerComplainDTO,
                 com.dtv.oss.dto.CustComplainProcessDTO" %>
<%@ page import="com.dtv.oss.util.Postern,java.util.*"%>
<%@ page import="com.dtv.oss.web.util.WebUtil,
				 com.dtv.oss.web.util.CommonKeys" %>

<%
	String txtCustomerID=request.getParameter("txtCustomerID");
	String txtCounty=Postern.getAddressCountyByCustomerID(WebUtil.StringToInt(txtCustomerID));
	

%>
<script language=javascript>
	function query_contract(){
		var strFeatures = "width=600px,height=500px,resizable=no,toolbar=no,scrollbars=yes,status=yes";
	  	window.open("menu_contract_query_result_list.do","合同查询",strFeatures);
	}
	function checkNull(){
		
		if (check_Blank(document.frmPost.txtName, true, "联系人"))
			return false;
		if (check_Blank(document.frmPost.txtTelephone, true, "联系人联系电话"))
			return false;
		if (check_Blank(document.frmPost.txtContractNo, true, "合同编号"))
			return false;
		if (check_Blank(document.frmPost.txtAccountID, true, "请选择帐户"))
			return false;

		if (document.frmPost.txtAgentCardType.value!=""&&document.frmPost.txtAgentCardID.value==""){
			alert("请输入证件号码！");
			return false;
		}
				
		if (document.frmPost.txtAgentCardType.value==""&&document.frmPost.txtAgentCardID.value!=""){
			alert("请选择证件类型！");
			return false;
		}
			
		return true;		
	
	}
	function next_step(){
		if(checkNull()){
			document.all.frmPost.action="groupcust_device_foropen.do";
			document.all.frmPost.submit(); 
		}
		
	}
</script>
<table  border="0" align="center" cellpadding="0" cellspacing="10" >
  <tr>
    <td><img src="img/list_tit.gif" width="19" height="19"></td>
    <td class="title">用户基本信息录入</td>
  </tr>
</table>
 
<table width="100%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table> 
<form name="frmPost" action="group_cust_open.do" method="post" > 
<tbl:hiddenparameters pass="txtCustomerID/DeviceClassList/DeviceClassDescription/TerminalDeviceList/DeviceProductIds" /> 
<tbl:hiddenparameters pass="txtContactPerson/txtChildCustID" />
  	<input type="hidden" name="txtActionType" value="<%=CommonKeys.GET_DEVICE_LIST%>" >
  	<input type="hidden" name="txtCounty" value="<%=txtCounty%>" >
  <table width="100%" align="center" border="0" cellspacing="1" cellpadding="5" class="list_bg">
     <tr>
         <td colspan="4" class="import_tit" align="center"><font size="3">用户信息</font></td>
     </tr>
     <tr>   
      <td class="list_bg2" width="17%"  align="right">联系人*</td>
      <td class="list_bg1" width="33%"  align="left">
		<input type="text" name="txtName" size="25" value="<tbl:writeparam name="txtName"/>" >
	  </td>
      <td class="list_bg2" width="17%"  align="right">联系电话*</td>
      <td class="list_bg1" width="33%"  align="left">
		<input type="text" name="txtTelephone" size="25" value="<tbl:writeparam name="txtTelephone"/>" >
	  </td>
    </tr>

     <tr>   
      <td class="list_bg2" width="17%"  align="right">合同编号*</td>
      <td class="list_bg1" width="33%"  align="left">
      	<input type="text" name="txtContractNo" size="25" value="<tbl:writeparam name="txtContractNo"/>" readonly>
        <input name="selOrgButton" type="button" class="button" value="查询" onClick="javascript:query_contract()" >
		
	  </td>
      <td class="list_bg2" width="17%"  align="right">帐户*</td>
      <td class="list_bg1" width="33%"  align="left">
      	<d:selAccByCustId name="txtAccountID" mapName="self"  match="txtAccountID" width="23" empty="false"  style="width:185px;"/>
	  </td>
    </tr>
 	<tr>
         <td colspan="4" class="import_tit" align="center"><font size="3">代理人信息</font></td>
     </tr>
     <tr>   
      <td class="list_bg2" width="17%"  align="right">姓名</td>
      <td class="list_bg1" width="33%"  align="left">
		<input type="text" name="txtAgentName" size="25" value="<tbl:writeparam name="txtAgentName"/>" >
	  </td>
      <td class="list_bg2" width="17%"  align="right">联系电话</td>
      <td class="list_bg1" width="33%"  align="left">
		<input type="text" name="txtContactPhone" size="25" value="<tbl:writeparam name="txtContactPhone"/>" >
	  </td>
    </tr>
    <tr>   
      <td class="list_bg2" width="17%"  align="right">证件类型</td>
      <td class="list_bg1" width="33%"  align="left">
      	<d:selcmn name="txtAgentCardType" mapName="SET_C_CUSTOMERCARDTYPE" match="txtAgentCardType" width="23" />	
	  </td>
      <td class="list_bg2" width="17%"  align="right">证件号码</td>
      <td class="list_bg1" width="33%"  align="left">
		<input type="text" name="txtAgentCardID" size="25" maxlength="25" value="<tbl:writeparam name="txtAgentCardID"/>" >
	  </td>
    </tr>
     
   </table>  
     <br>
     <table align="center"  border="0" cellspacing="0" cellpadding="0">
      <tr>  
        	<td><img src="img/button2_r.gif" width="22" height="20"></td>
	   		<td background="img/button_bg.gif"><a href="<bk:backurl property="group_cust_query.do" />" class="btn12">上一步</a></td> 
	   		<td><img src="img/button2_l.gif" width="11" height="20"></td> 
           	<td width="20" ></td>    
           	<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
	   		<td background="img/button_bg.gif"><a href="javascript:next_step()" class="btn12">下一步</a></td> 
	   		<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td> 
      </tr>
  </table> 
</form> 
    
   