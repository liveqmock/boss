<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="resultset" prefix="rs" %>
<%@ taglib uri="osstags" prefix="d" %>
<%@ taglib uri="bookmark" prefix="bk" %>
<%@ page import="com.dtv.oss.service.commandresponse.CommandResponse" %>
<%@ page import="com.dtv.oss.dto.JobCardDTO" %>
<%@ page import="com.dtv.oss.util.Postern" %>
<%@ page import="com.dtv.oss.web.util.WebUtil"%>

<script language=javascript>

function mod_organization(){
	var strFeatures = "width=480px,height=240px,resizable=no,toolbar=no,scrollbars=yes,status=yes";
  	window.open("transfer_org_list.do?strRole=R","流转部门",strFeatures);
} 
function frm_submit(){
	if(frm_check1())
		document.frmPost.submit();
}
function frm_check1(){
	if(document.frmPost.txtAutoNextOrgID.value == ''){
		alert("请选择流转部门！");
		return false;
	}
	
	return true;
}
</script>
 <table width="820" align="center" cellpadding="0" cellspacing="0">
<tr> 
<td align="center" valign="top"><table  border="0" align="center" cellpadding="0" cellspacing="10">
      <tr>
        <td><img src="img/list_tit.gif" width="19" height="19"></td>
        <td class="title">报修单——诊断流转</td>
      </tr>
    </table>
      <table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
        <tr>
          <td><img src="img/mao.gif" width="1" height="1"></td>
        </tr>
      </table>
         <br>
<form name="frmPost" method="post" action="cp_diagnosis_create.do">
 <input type="hidden" name="confirm_post" value="TRUE">
 <input type="hidden" name="txtProblemLevel" value="S">
 <input type="hidden" name="txtActionType" value="diagnosis">
 <input type="hidden" name="func_flag" value="1100">
 <input type="hidden" name="transferType" value="auto">
  <tbl:hiddenparameters pass="txtAccount/txtDeviceId/txtProblemCategory/txtAddressId/txtFeeClass"/>
 <table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
        <tr>
        <td class="list_bg2"><div align="right">报修单编号</div></td>
        <td class="list_bg1">
	<input type="text" name="txtID" size="25"  value="<tbl:writeparam name="txtID" />" class="textgray" readonly   >
	</td>
	<td class="list_bg2"><div align="right">业务帐户</div></td>
	<td class="list_bg1">
         <input type="text" name="txtServiceAccountID" size="25"  value="<tbl:writeparam name="txtServiceAccountID" />" class="textgray" readonly   >
	</td>
	</tr>
	<tr>
	  <td class="list_bg2"><div align="right">收费类别</div></td>
	  <td class="list_bg1"> 
		<input type="text" name="txtFeeClass" size="25"  value="<tbl:writeparam name="txtFeeClass"/>" class="textgray" readonly>   
           </font></td>
		 
	<td class="list_bg2"><div align="right">报修类别</div></td>
	<td class="list_bg1">
	 <input type="text" name="txtProblemCategoryShow"  size="25"  value="<tbl:writeparam name="txtProblemCategoryShow"/>" class="textgray" readonly > 
	</td>
		 
	</tr>
	<tr>		 
		<td class="list_bg2"><div align="right">故障描述</div></td>
		<td class="list_bg1" colspan="3"> 
			<textarea  name="txtProblemDesc"  class="textareagray" readonly  cols="80" rows="3"><tbl:writeparam name="txtProblemDesc" /></textarea>
            	</td>		 
	</tr>	
  <tr> 
	   <td colspan="4" class="import_tit" align="center"><font size="3">诊断信息</font></td>
  </tr> 
  <%  
	CommandResponse RepCmd = (CommandResponse)(request.getAttribute("ResponseFromEJBEvent"));
	  int serviceid=Integer.parseInt(RepCmd.getPayload()+"");System.out.println("%%%%%%%%%%%%%%%%%%%%"+serviceid);
	  String strServiceID="";
	  if(serviceid>0)
	     	strServiceID=serviceid+"";System.out.println("%%%%%%%%%%%%%%%%%%%%"+strServiceID);
	  pageContext.setAttribute("txtServiceID",strServiceID);
	     int  saId =  WebUtil.StringToInt(request.getParameter("txtServiceAccountID"));
	     int serviceID = Postern.getServiceIDByAcctServiceID(saId);	     	
	  %> 
  <tr>
  <td colspan="4">
  <tbl:dynamicservey serveyName="txtDiaName"  serviceID="<%=String.valueOf(serviceID)%>"  serveyType="D"  serveySubType="N"  showType="label" tdWordStyle="list_bg2" tdControlStyle="list_bg1" controlSize="25"/> 
  </td>
  </tr>
  <tr>		 
		<td class="list_bg2"><div align="right">诊断结果</div></td>
		<td class="list_bg1" colspan="3"> 
			<input type="text" name="txtDiagnosisResult" size="25"  class="textgray" value="<d:getcmnname typeName="SET_W_ROLEORGANIZATIONDIAGNOSISRESULT" match="txtDiagnosisResult" />" readonly>
            	</td>		 
	</tr>
	<tr>		 
		<td class="list_bg2" width="17%"><div align="right">备&nbsp&nbsp注</div></td>
		<td class="list_bg1" colspan="3"> 
			<textarea name="txtMemo"  length="5" cols=80 rows=3 readonly class="textareagray"><tbl:writeSpeCharParam name="txtMemo" /></textarea>
            	</td>		 
	</tr>
	<tr>
	    <td class="list_bg2"><div align="right">流转部门</td>
	    <td class="list_bg1" colspan="3">
		    <input type="hidden" name="txtAutoNextOrgID" value="<%=strServiceID%>" >
		 <input type="text" name="txtNextOrgName" size="30" maxlength="50" readonly value="<%=Postern.getOrganizationDesc(serviceid)%>" class="text">
		 <input name="selOrgButton" type="button" class="button" value="修改" onClick="javascript:mod_organization()">
	    </td>		
	  </tr>
  </table> 
  <tbl:generatetoken /> 
      <table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
        <tr>
          <td><img src="img/mao.gif" width="1" height="1"></td>
        </tr>
      </table>
     
<br>
 <table align="center" border="0" cellspacing="0" cellpadding="0">
        <tr> 
        <td><img src="img/button2_r.gif" width="22" height="20"></td>  
          <td background="img/button_bg.gif">
	  <a href="<bk:backurl property="menu_cp_diagnosis.do"/>" class="btn12">上一步</a></td>
          <td><img src="img/button2_l.gif" width="13" height="20"></td>
          <td width="20" ></td>         
          <td><img src="img/button_l.gif" border="0" ></td> 
          <td background="img/button_bg.gif"  ><a href="javascript:frm_submit()" class="btn12">提&nbsp交</a></td>
          <td><img src="img/button_r.gif" border="0" ></td>         
      </tr>
</table>      
       <br>    
 </form>  

 
 

   

 