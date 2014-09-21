<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="bookmark" prefix="bk" %>
<%@ taglib uri="resultset" prefix="rs" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="osstags" prefix="d" %>
<%@ page import="com.dtv.oss.dto.RoleOrganizationDTO" %>
<%@ page import="com.dtv.oss.util.Postern,java.util.*"%>
<%@ page import="com.dtv.oss.web.util.WebUtil" %>
<%
	pageContext.setAttribute("PRODUCTNAME",Postern.getProductNameByFlag());
%>
<script language=javascript>

function check_from(){
	if (check_Blank(document.frmPost.txtDistrictId, true, "客户所属区域"))
		return false;
	if (check_Blank(document.frmPost.txtServiceOrgId, true, "提供服务的组织"))
		return false;
	if (check_Blank(document.frmPost.txtOrgRole, true, "单据类型"))
		return false;	
	
	return true;		
}

function create_submit(){
	if(check_from())
		document.frmPost.submit();
}
function back_submit(){
	url="role_organization_query.do?txtFrom=1&txtTo=10";
	document.location.href=url;
}

//-->
</script>
<TABLE border="0" cellspacing="0" cellpadding="0" width="820" >
<TR>
  <TD>
<table  border="0" align="center" cellpadding="0" cellspacing="10" >
  <tr>
    <td><img src="img/list_tit.gif" width="19" height="19"></td>
    <td class="title">新增组织机构角色</td>
  </tr>
</table>
 
<table width="100%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>
<br>
    
<form name="frmPost" action="role_organization_createAction.do" method="post" >    
   <table width="100%" align="center" border="0" cellspacing="1" cellpadding="3" class="list_bg">
    <tr>   
      <td class="list_bg2" align="right">客户所属区域*</td>
    <td class="list_bg1">
    	<input type="hidden" name="txtDistrictId" value="<tbl:writeparam name="txtDistrictId" />">
	    <input type="text" name="txtDisDescrpition" size="25" maxlength="50" readonly value="<tbl:WriteDistrictInfo property="txtDisDescrpition" />" >
	   <input name="selDistButton" type="button" class="button" value="选择" onClick="javascript:sel_district('S,P,T','txtDistrictId','txtDisDescrpition')">
    </td>
     <td class="list_bg2" align="right">提供服务的组织*</td>
     <td class="list_bg1">
    	<input type="hidden" name="txtServiceOrgId" value="<tbl:writeparam name="txtServiceOrgId" />">
	    <input type="text" name="txtOrgDesc" size="25" maxlength="50" readonly value="<tbl:WriteOrganizationInfo property="txtServiceOrgId" />">
	    <input name="selOrgButton" type="button" class="button" value="选择" onClick="javascript:sel_organization('C,B,D,O,S,P','txtServiceOrgId','txtOrgDesc')">
    </td>
    <tr>	    	  		   
      <td class="list_bg2"><div align="right">故障类别</div></td>
      <td class="list_bg1"  align="left"><d:selcmn name="txtTroubleType" mapName="SET_C_CPPROBLEMLEVEL" match="txtTroubleType" width="23" /></td>  
       <td class="list_bg2"><div align="right">故障子类别</div></td>
      <td class="list_bg1"  align="left"><d:selcmn name="txtTroubleSubType" mapName="SET_C_CPPROBLEMCATEGORY" match="txtTroubleSubType" width="23" /></td>    	  	  	  	
    </tr>
     <tr>   
        <td class="list_bg2"><div align="right">是否优先处理</div></td>
        <td class="list_bg1" align="left"><d:selcmn name="txtIsFirst" mapName="SET_G_YESNOFLAG" match="txtIsFirst" width="23" /></td>
        <td class="list_bg2"><div align="right">单据类型*</div></td>
        <td class="list_bg1"  align="left">
         <d:selcmn name="txtOrgRole" mapName="SET_S_ROLEORGNIZATIONORGROLE" match="txtOrgRole" width="23" />
      </td>    	  	
     
    </tr>
    <tr>   
      <td class="list_bg2"><div align="right">创建业务帐户的产品</div></td>
      <td class="list_bg1" align="left">
      <tbl:select name="txtSaProductId" set="PRODUCTNAME" match="txtSaProductId" width="23"/>
     </td>
      <td class="list_bg2"><div align="right">诊断结果</div></td>
        <td class="list_bg1"  align="left">
         <d:selcmn name="txtDiagnosisResult" mapName="SET_W_ROLEORGANIZATIONDIAGNOSISRESULT" match="txtDiagnosisResult" width="23" />
      </td>    	  	
     
   </tr>

    <tr>  
      <td class="list_bg2" colspan="4" align="center">
          <table  border="0" cellspacing="0" cellpadding="0">
			  <tr>
				  <tr>
				  <td><img src="img/button2_r.gif" width="22px" height="20px"></td>
			<td><input name="Submit" type="button" class="button"
					value="返&nbsp;回" onclick="javascript:back_submit()"></td>
                        <td><img src="img/button2_l.gif" width="11px" height="20px"></td>
            <td width="20" ></td>	
				<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
				<td><input name="Submit" type="button" class="button" value="保  存" onclick="javascript:create_submit()"></td>
				<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
			  </tr>
	      </table>
     </tr>
   </table>
   <br>
  <table width="100%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
    <tr>
      <td><img src="img/mao.gif" width="1" height="1"></td>
    </tr>
  </table>  
   <input type="hidden" name="Action" value="CREATE" >
   <input type="hidden" name="func_flag" value="108" >
  </form>