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
		//到费用页面
		document.all.txtActionType.value="15";
		document.all.frmPost.action="groupcust_open_fee.screen";
		document.all.frmPost.submit(); 
	}
	function groupcust_open(){
		//到支付确认页面
		document.all.frmPost.action="groupcust_open_fee_confirm.screen";
		document.all.frmPost.submit(); 
	}
</script>
<table  border="0" align="center" cellpadding="0" cellspacing="10" >
  <tr>
    <td><img src="img/list_tit.gif" width="19" height="19"></td>
    <td class="title">收费信息</td>
  </tr>
</table>
 
<table width="100%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table> 
<form name="frmPost" action="cust_complain_query.do" method="post" > 
<input type="hidden" name="txtActionType" value="<%=CommonKeys.OPEN_CHILD_CUSTOMER%>" >
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
       <tbl:CommonFeeAndPaymentInfo includeParameter="Fee_PayAndPrep" payCsiType="<%=CommonKeys.CUSTSERVICEINTERACTIONTYPE_GO%>" acctshowFlag ="false" confirmFlag="false" />
    </td></tr> 
</table>
  <br>
 <table align="center"  border="0" cellspacing="0" cellpadding="0">
      <tr>  
        	<td><img src="img/button2_r.gif" width="22" height="20"></td>
	   		<td background="img/button_bg.gif"><a href="javascript:last_step()" class="btn12">上一步</a></td> 
	   		<td><img src="img/button2_l.gif" width="11" height="20"></td> 
           	<td width="20" ></td>    
           	<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
	   		<td background="img/button_bg.gif"><a href="javascript:groupcust_open()" class="btn12">下一步</a></td> 
	   		<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td> 
      </tr>
  </table> 
</form> 

    
   