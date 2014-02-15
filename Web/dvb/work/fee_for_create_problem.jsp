<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
 
 <%@ taglib uri="privilege" prefix="pri" %>
<%@ taglib uri="/WEB-INF/oss.tld" prefix="o" %>
<%@ taglib uri="logic" prefix="lgc" %> 
<%@ page import="com.dtv.oss.dto.CustomerDTO"%>
<%@ taglib uri="osstags" prefix="d" %> 
<%@ page import="com.dtv.oss.util.Postern"%>
<%@ page import="java.util.*"%> 
<%@ page import="com.dtv.oss.web.util.*"%>
<%@ page import="com.dtv.oss.service.commandresponse.CommandResponse" %>
 <%@ page import="com.dtv.oss.web.util.CommonKeys" %>
 <%@ page import="com.dtv.oss.dto.AccountItemDTO" %>
<%@ page import="com.dtv.oss.dto.AcctItemTypeDTO" %>
<%@ page import="com.dtv.oss.web.util.CurrentLogonOperator"%>

<script language=javascript>
 
 
 
function create_submit()
{
	if(check_transfertype()){
		document.frmPost.submit();
	}
}
function back_submit() {
        
	document.frmPost.action="cp_create.do";
	document.frmPost.submit();
}
function check_transfertype(){
	var str=document.frmPost.selType!=null?document.frmPost.selType.value:""; 
		if(  'auto'==str && (''==document.frmPost.txtAutoNextOrgID.value||0==document.frmPost.txtAutoNextOrgID.value)){
			alert("无法匹配合适的处理部门，请手工流转并指定流转部门！");
			return false;
		}
		if('manual'==str){
			if(''==document.frmPost.txtNextOrgID.value){
			       	alert("手工流转需指定流转部门！");
			       	return false;
		       	}
		}
	document.frmPost.transferType.value=str;
	return true;
}
function mod_organization(){
	var strFeatures = "width=480px,height=240px,resizable=no,toolbar=no,scrollbars=yes,status=yes";
  	window.open("transfer_org_list.do?strRole=R","流转部门",strFeatures);
}
</script>

<table  border="0" align="center" cellpadding="0" cellspacing="10">
  <tr>
    <td><img src="img/list_tit.gif" width="19" height="19"></td>
    <td  class="title">报修单-费用明细</td>
  </tr>
  </table>
  <table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>
 
<form name="frmPost" method="post" action="cp_create_op.do">
 <input type="hidden" name="confirm_post" value="TRUE">
 <input type="hidden" name="transferType" value="<tbl:writeparam name="transferType"/>">
 <tbl:generatetoken />
	 
	 <input type="hidden" name="txtCustType" value="<tbl:write name="cust" property="customerType"/>">
	  <input type="hidden" name="func_flag" value="1001">
	<table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
	<tr> 
	      <td colspan="4" class="import_tit" align="center"><font size="3">客户基本信息</font></td>
          </tr>
	<tr> 
	     <td class="list_bg2"><div align="right">客户姓名</div></td>
	      <td class="list_bg1"> 
	      <input type="text" name="txtContactPerson" size="25"  value="<tbl:writeparam name="txtContactPerson"/>" class="textgray" readonly >
	      
	      <td class="list_bg2"><div align="right">客户地址</div></td>
              <td class="list_bg1">
              <input type="text" name="txtDetailAddress" size="25"  value="<tbl:writeparam name="txtDetailAddress"/>" class="textgray" readonly >
	</tr>
	<tr>
	
	     <td class="list_bg2"><div align="right">联系人</div></td>
             <td class="list_bg1"><input type="text" name="txtContactPersonBak" size="25"  value="<tbl:writeparam name="txtContactPersonBak"/>" class="textgray" readonly >
             <td class="list_bg2"><div align="right">联系电话</div></td>
             <td class="list_bg1"><input type="text" name="txtContactPhone" size="25"  value="<tbl:writeparam name="txtContactPhone"/>" class="textgray" readonly >
             
        </tr>  
        <tr> 
	      <td colspan="4" class="import_tit" align="center"><font size="3">报修信息</font></td>
          </tr>
          
        <tr> 
		<td class="list_bg2"><div align="right">报修类别</div></td>
		<td class="list_bg1">
		<input type="text" name="txtProblemCategory1" size="25" value="<o:getcmnname  typeName="SET_C_CPPROBLEMCATEGORY"  match="txtProblemCategory"/>" class="textgray" readonly >
		 
		</td>   
		<td class="list_bg2"><div align="right">收费类别</div></td>
		<td class="list_bg1"> 
		<input type="text" name="txtFeeClass1" size="25" value="<o:getcmnname  typeName="SET_C_CPFEECLASS"  match="txtFeeClass"/>" class="textgray" readonly >
	</td>   
	</tr>
	<tr>
	  <td class="list_bg2"><div align="right">故障现象</div></td>
	  <td class="list_bg1" colspan="3"> 
	    <input type="text" name="txtProblemPhenomenaShow" size="25" value="<o:getcmnname  typeName="SET_C_CPPROBLEMPHENOMENA"  match="txtProblemPhenomena"/>" class="textgray" readonly >
	  </td>
	</tr>  
	<tr>
	  <td class="list_bg2"><div align="right">故障描述</div></td>
	  <td class="list_bg1" colspan="3"> 
	  <textarea name="txtProblemDesc"  length="5" cols=80 rows=3 readonly><tbl:writeSpeCharParam name="txtProblemDesc" /></textarea>
	</tr> 	
	<%int addressid=Integer.parseInt(request.getParameter("txtAddressId"));	
	  String problemlevel=(String)request.getParameter("txtProblemLevel");
	  if(problemlevel==null||("").equals(problemlevel))
	  	problemlevel="";
	  
	  CommandResponse RepCmd = (CommandResponse)(request.getAttribute("ResponseFromEJBEvent"));
	  int serviceid=Integer.parseInt(RepCmd.getExtraPayLoad()+"");
	  System.out.println("***********serviceid********" +serviceid);
	  String strServiceID="";
	  if(serviceid>0)
	     	strServiceID=serviceid+"";
	  pageContext.setAttribute("txtServiceID",strServiceID);
	  //String orgname=Postern.getOrgNameByOrgID(serviceid);
	  //if(orgname==null)
	  //   	orgname="";
	  int  saId =  WebUtil.StringToInt(request.getParameter("txtServiceAccountID"));
	     int serviceID = Postern.getServiceIDByAcctServiceID(saId);	 

	  String diagnosisSettingValue = Postern.getSystemsettingValueByName("SET_W_DIAGIMME");
	  if(diagnosisSettingValue !=null && ("Y").equals(diagnosisSettingValue)){%>
	  <tr> 
	      <td colspan="4" class="import_tit" align="center"><font size="3">初步诊断信息</font></td>
    	   </tr>
    	   <tr>
	  <td colspan="4">
  		<tbl:dynamicservey serveyName="txtDiaName"  serviceID="<%=String.valueOf(serviceID)%>"  serveyType="D"  serveySubType="N"  showType="label" tdWordStyle="list_bg2" tdControlStyle="list_bg1" controlSize="25"/> 
  	  </td>
	 </tr>
	  <%}	  
	  String systemSettingPrecise = Postern.getSystemsettingValueByName("SET_W_PRECISEWORK");
	  if(systemSettingPrecise != null&&("Y").equals(systemSettingPrecise)){
	  String systemSettingValue = Postern.getSystemsettingValueByName("SET_W_AUTOTRANSFER");
	  if(systemSettingValue != null &&("Y").equals(systemSettingValue)){%>
	  <tr> 
	    <td colspan="4" class="import_tit" align="center"><font size="3"><input type="hidden" name="selType" value="auto" >工单自动流转</font></td>
        </tr>    
	 <tr>
	    <td class="list_bg2"><div align="right">流转部门*</td>
	    <td class="list_bg1" colspan="3">     
		 <input type="hidden" name="txtAutoNextOrgID" value="<%=strServiceID%>" >
		 <input type="text" name="txtNextOrgName" size="25" maxlength="50" readonly value="<%=Postern.getOrganizationDesc(serviceid)%>" class="text">
		 <input name="selOrgButton" type="button" class="button" value="修改" onClick="javascript:mod_organization()">
	    </td>		
	  </tr> 
	 <% }else {%> 
	  <tr> 
	    <td colspan="4" class="import_tit" align="center"><font size="3"><input type="hidden" name="selType" value="manual">工单手工流转</font></td>
        </tr>    
	 <tr>
	    <td class="list_bg2"><div align="right">流转部门*</td>
	    <td class="list_bg1" colspan="3">
		    <input type="hidden" name="txtNextOrgID" value="<tbl:writeparam name="txtNextOrgID" />">
		    <input type="text" name="txtOrgDesc" size="25" maxlength="50" readonly value="<tbl:WriteOrganizationInfo property="txtNextOrgID" />" class="text">
		    <input name="selOrgButton" type="button" class="button" value="选择" onClick="javascript:sel_organization('C,B,D,S,O','txtNextOrgID','txtOrgDesc')">
	    </td>		
	  </tr>
	  <%}}%>   
 </table> 

  <table width="810" border="0" align="center">
      <tr><td>
      	<tbl:CommonFeeAndPaymentInfo includeParameter="Fee"  payCsiType="<%=CommonKeys.METHODOFPAYMENT_KHBX%>"  acctshowFlag ="true"  />  
      </td></tr>
   </table>	
<!--<tbl:dynamicservey serveyName="txtDiaName"  serveyType="D"  serveySubType="N"  showType="hide"/>  -->
<tbl:hiddenparameters pass="txtAccount/txtServiceAccountID/txtDeviceId/txtAddressId/txtProblemDesc/txtCustomerID/txtCustType/txtProblemLevel/txtProblemCategory/txtFeeClass/transferType/txtCatvId/txtProblemPhenomena" />           

	<br>
   <table border="0" cellspacing="0" cellpadding="0" align=center>
       <tr> 
          <td><img src="img/button2_r.gif" width="22" height="20"></td>
          <td><input name="prev" type="button" class="button" onclick="javascript:back_submit()" value="上一步"></td>
          <td><img src="img/button2_l.gif" width="11" height="20"></td>
          <td width="20" ></td>
	        <td><img src="img/button_l.gif" border="0" ></td>
          <td background="img/button_bg.gif"><a href="javascript:create_submit();" class="btn12">创&nbsp;建</a></td>            
          <td><img src="img/button_r.gif" border="0" ></td>
      </tr>
	 </table> 
	
</form>
<script language=javascript>
function window_open(){
	var selstatus=document.frmPost.transferType.value;
	var l=document.getElementsByName("selType") ;
	for(i=0;i<l.length;i++){  
		if(l[i].value==selstatus){
			l[i].checked=true;
		} 
	}
}
window_open();

</script> 