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
	if (check_Blank(document.frmPost.txtDistrictID, true, "施工区域"))
		return false;
	if (check_Blank(document.frmPost.txtAutoNextOrgID, true, "流转部门"))
		return false;	
	return true;
}
function frm_submit()
{
    if (check_frm())document.frmPost.submit();
    
} 
function mod_organization(){
	var strFeatures = "width=480px,height=240px,resizable=no,toolbar=no,scrollbars=yes,status=yes";
  	window.open("transfer_org_list.do?strRole=M","流转部门",strFeatures);
}
</script>
<table  border="0" align="center" cellpadding="0" cellspacing="10" >
  <tr>
    <td><img src="img/list_tit.gif" width="19" height="19"></td>
    <td class="title">录入网络施工单</td>
  </tr>
</table>
<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>
 
<form name="frmPost" method="post" action="catv_manual_register_jobcard.do" >

 <table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
        
     <tr>
	<td class="list_bg2"><div align="right">工单类型</div></td>
        <td class="list_bg1">
		<d:selcmn name="txtJobCardType" mapName="SET_W_JOBCARDTYPE" width="23" />
	</td>
	  <td class="list_bg2"><div align="right">工单子类型</div></td>
	  <td class="list_bg1"  > 
			<d:selcmn name="txtSubType" mapName="SET_W_JOBCARDSUBTYPE" width="23" />
	</td>
	</tr> 
	
	<tr>
	<td class="list_bg2"><div align="right">端口数</div></td>
        <td class="list_bg1">
	<input type="text" name="txtPortNo" size="25"  value="<tbl:write name="oneline" property="portNo" />"     >
	</td>
	  <td class="list_bg2"><div align="right">施工区域*</div></td>
	  <td class="list_bg1"  > 
			<input type="hidden" name="txtDistrictID" value="">
	    	<input type="text" name="txtDistrictIDDesc" size="25" maxlength="50" readonly value="" class="text">
	    	<input name="selDistButton" type="button" class="button" value="选择" onClick="javascript:sel_district('leaf','txtDistrictID','txtDistrictIDDesc')">
	</td>
	</tr>
	
	<tr>
	  <td class="list_bg2"><div align="right">施工地址</div></td>
	  <td class="list_bg1" colspan="3" >
	  <input type="text" name="txtAddressId" size="50"  value=""   >
	</td> 
	
	</tr>
        <tr>
          <td class="list_bg2"><div align="right">联系人</div></td>
          <td class="list_bg1">
	  <input type="text" name="txtContactPerson" size="25"  value="<tbl:write name="oneline" property="ContactPerson" />"     >
	  <td class="list_bg2"><div align="right">联系电话</div></td>
	  <td class="list_bg1">
	  <input type="text" name="txtAddressId" size="25"  value=""   >
	</td> 
	</tr>
	
	<tr>
	  <td class="list_bg2"><div align="right">备注</div></td>
	  <td class="list_bg1" colspan="3">
	  <input type="text" name="txtAddressId" size="50"  value=""   >
	</td>
	</tr>

	<tr width="100%"> 
		<!--<td valign="middle" width="100%" >-->
		<td colspan="4" class="import_tit" align="center" width="100%"><font size="3">工单流转</strong></td>
        <!--
        <table width="100%" border="0" align="center">
            <tr>
              	<td class="import_tit" align="center" width="100%"><font size="3">工单流转</strong></td>
	    	</tr>
	 	</table>
   		</td>
   		-->
   </tr>   	
	    	
	<tr>
	   	<td class="list_bg2"><div align="right">流转部门*</div></td>
	  	<td class="list_bg1" colspan="3">
	    			<input type="hidden" name="txtNextOrgID" value="<tbl:writeparam name="txtNextOrgID" />">
	    			<input type="hidden" name="txtAutoNextOrgID" value="">
	    			<input type="text" name="txtNextOrgName" size="22" maxlength="50" readonly value="<tbl:WriteOrganizationInfo property="txtNextOrgID" />" class="text">
	    			<input name="selOrgButton" type="button" class="button" value="修改" onClick="javascript:mod_organization()">
	  	</td>
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
          <a href="javascript:frm_submit()" class="btn12">确&nbsp;认</a>
          </td>
          <td><img src="img/button_r.gif" border="0" ></td>
          
          <td width="20" ></td>
          
      </tr>
   </table>    

 </form>  
<script language=javascript>
function window_open(){
	document.frmPost.txtAutoNextOrgID.value = document.frmPost.txtNextOrgID.value;
}
window_open();

</script>
 
 

   

 