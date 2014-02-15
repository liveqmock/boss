<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="resultset" prefix="rs" %>
<%@ taglib uri="bookmark" prefix="bk" %>
<%@ taglib uri="osstags" prefix="d" %>
<%@ page import="com.dtv.oss.dto.wrap.work.CustomerProblem2CPProcessWrap" %>
<%@ page import="com.dtv.oss.dto.CustomerProblemDTO" %>
<%@ page import="com.dtv.oss.dto.CustProblemProcessDTO" %>
<%@ page import="com.dtv.oss.dto.AddressDTO" %>
<%@ page import="com.dtv.oss.util.Postern" %>
<%@ page import="com.dtv.oss.web.util.CurrentLogonOperator"%>
<%@ page import="com.dtv.oss.dto.OperatorDTO"%>
<%@ page import="com.dtv.oss.web.util.CommonKeys" %>

<script language=javascript>

function check_frm()
{
if (check_Blank(document.frmPost.txtAutoNextOrgID, true, "流转部门"))
		return false;
		
	//if (check_Blank(document.frmPost.txtProblemDesc, true, "问题描述"))
	//	return false;
   return true;
}
function frm_submit()
{
    if (check_frm())document.frmPost.submit();
    
} 
function mod_organization(){
	var strFeatures = "width=480px,height=240px,resizable=no,toolbar=no,scrollbars=yes,status=yes";
  	window.open("transfer_org_list.do?strRole=R","流转部门",strFeatures);
}
</script>
<table  border="0" align="center" cellpadding="0" cellspacing="10" >
  <tr>
    <td><img src="img/list_tit.gif" width="19" height="19"></td>
    <td class="title">创建报修单流转信息</td>
  </tr>
</table>
<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>
<br>
 
<form name="frmPost" method="post" action="cp_manual_trans.do" >

<lgc:bloop requestObjectName="ResponseQueryResult" item="oneline" IsOne="true"> 

<%
        
        //CustomerProblem2CPProcessWrap wrap = (CustomerProblem2CPProcessWrap)pageContext.getAttribute("oneline");
	CustomerProblemDTO dto=(CustomerProblemDTO)pageContext.getAttribute("oneline");
	//CustProblemProcessDTO cppDto=wrap.getCppDto();
	 String strAddress = Postern.getAddressById(dto.getAddressID());
	 String referType=null;
	 AddressDTO addrDto = Postern.getAddressDtoByID(dto.getAddressID());
	 int curid=Postern.getCurrentOrgIDByCPID(dto.getId());
	 pageContext.setAttribute("currentorgId",curid+"");
         
          //pageContext.setAttribute("oneline", dto);
          //pageContext.setAttribute("cpp", cppDto);
          pageContext.setAttribute("custaddr", addrDto);
          Map mapOrgName = Postern.getOrgNameMapByOrgRole("R");
          if (mapOrgName!=null)
          pageContext.setAttribute("ServiceRepOrg",mapOrgName);
       //  java.util.List diaType = Postern.getDiaTypeByCustProbID(dto.getId());
         int jobCardId = Postern.getJobCardIDByCustProblemId(dto.getId());
         if (jobCardId>0){
           referType = Postern.getReferSourceTypeByID(jobCardId);
         } 
         if (jobCardId == 0){
            referType = Postern.getReferSourceTypeByID(dto.getId());
         }
        
	if (strAddress==null) strAddress="";
	int operOrgID=CurrentLogonOperator.getOperator(request).getOrgID();
	int nextorgid=Postern.getNextOrgIDByCPID(dto.getId());
    
%>
 <table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
        <tr>
          <td class="list_bg2"><div align="right">联系人姓名</div></td>
          <td class="list_bg1">
	  <input type="text" name="txtContactPerson" size="25"  value="<tbl:write name="oneline" property="ContactPerson" />" class="textgray" readonly   >
	  <td class="list_bg2"><div align="right">详细地址</div></td>
	  <td class="list_bg1">
	  <input type="text" name="txtAddressId" size="25"  value="<%=strAddress%>" class="textgray" readonly >
	</td> 
	</tr>
	<tr>
	   <td class="list_bg2"><div align="right">联系电话</div></td>
	   <td class="list_bg1"> 
	   <input type="text" name="txtContactPhone" size="25"  value="<tbl:write name="oneline" property="contactphone" />"  class="textgray" readonly  >
	   </td>
	   <td class="list_bg2"><div align="right">客户所在区</div></td>
	   <td class="list_bg1"> 
	    
	   <input type="text" name="txtCounty" size="25" class="textgray" readonly value="<tbl:WriteDistrictInfo name="custaddr" property="DistrictID" />">
	   </td>
	 </tr>
	<tr>
	  <td class="list_bg2"><div align="right">报修类别</div></td>
	  <td class="list_bg1"> 
	  <input type="text" name="txtProblemCategory" size="25"  value="<d:getcmnname typeName="SET_C_CPPROBLEMCATEGORY" match="oneline:ProblemCategory" />"  class="textgray" readonly  >
	 </td>
	 
	 <td class="list_bg2"><div align="right">问题描述</div></td>
	 <td class="list_bg1"> 
	<input type="text" name="txtProblemDesc" size="25"  value="<tbl:write name="oneline" property="ProblemDesc" />"  class="textgray" readonly  >
	</td>
	</tr>
	 <tr>
	  <td class="list_bg2"><div align="right">当前部门</div></td>
	  <td class="list_bg1"><input type="text" name="txtCurrentOrgID" size="25"  value="<tbl:WriteOrganizationInfo name="oneline" property="processOrgId" />"  class="textgray" readonly  ></td>
	   <td class="list_bg2"><div align="right">流转部门*</div></td>
	  <td class="list_bg1">
	           <!-- <tbl:select name="txtNextOrgID" set="ServiceRepOrg" match="txtNextOrgID" width="23" />
	           -->
	    <input type="hidden" name="txtNextOrgID" value="<tbl:writeparam name="txtNextOrgID" />">
	    <input type="hidden" name="txtAutoNextOrgID" value="">
	    <input type="text" name="txtNextOrgName" size="22" maxlength="50" readonly value="<tbl:WriteOrganizationInfo property="txtNextOrgID" />" class="text">
	    <input name="selOrgButton" type="button" class="button" value="选择" onClick="javascript:mod_organization()">
	  </td>
	</tr>
	<tr>
	  <td class="list_bg2"><div align="right">备&nbsp&nbsp注</div></td>
	  <td class="list_bg1" colspan="3"><textarea name="txtMemo" cols="80" rows="3" ><tbl:writeparam name="txtMemo" /></textarea></td>	   
	</tr>
         
</table>
  <br>	  
  <input type="hidden" name="txtCustProblemDtLastmod"   value="<tbl:write name="oneline" property="DtLastmod" />"   >
  <input type="hidden" name="txtID" size="25"  value="<tbl:write name="oneline" property="Id"/>"  >
  <input type="hidden" name="func_flag" value="1006" >
  <table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
        <tr>
          <td><img src="img/mao.gif" width="1" height="1"></td>
        </tr>
   </table>
   <br>
  <table align="center" border="0" cellspacing="0" cellpadding="0">
        <tr> 
         <td><img src="img/button2_r.gif" width="22" height="20"></td>
             <td background="img/button_bg.gif"  ><a href="<bk:backurl property='sheetrep_query.do/cp_query_detail.do' />" class="btn12">返&nbsp;回</a></td>           
            <td><img src="img/button2_l.gif" width="11" height="20"></td>
              <td width="20" ></td>
              
          <td><img src="img/button_l.gif" border="0" ></td>
          <td background="img/button_bg.gif"  >
          <a href="javascript:frm_submit()" class="btn12">手工流转</a>
          </td>
          <td><img src="img/button_r.gif" border="0" ></td>
          
          <td width="20" ></td>
           <% if(referType!=null){ 
             if(referType.equals("E")){
           %>
        
        
         <td><img src="img/button_l.gif" border="0" ></td>
         <td background="img/button_bg.gif">
	  <a href="diagnosis_info_view_n.do?txtCustomerProblemID=<tbl:write name="oneline" property="id"/>" class="btn12">诊断信息</a></td>
         <td><img src="img/button_r.gif" border="0" ></td>
       
      <%} else {%>
       <td><img src="img/button_l.gif" border="0" ></td>
         <td background="img/button_bg.gif">
	  <a href="diagnosis_info_view_p.do?txtReferJobCardID=<tbl:write name="oneline" property="referJobCardID"/>" class="btn12">诊断信息</a></td>
         <td><img src="img/button_r.gif" border="0" ></td>
      
     <%} } %>
          
          
      </tr>
   </table>    
   
</lgc:bloop>   
<tbl:generatetoken />  
 </form>  
<script language=javascript>
function window_open(){
	document.frmPost.txtAutoNextOrgID.value = document.frmPost.txtNextOrgID.value;
}
window_open();

</script>
 
 

   

 