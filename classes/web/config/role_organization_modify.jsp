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
function modify_submit(){
	if(check_from()&& confirm("确定要修改该组织机构角色吗？")){
	        document.frmPost.action="role_organization_modifyAction.do";
		document.frmPost.submit();
	}
	
}
function delete_submit(txtID){
	if(confirm("确定要删除该组织机构角色吗？")){
		self.location.href="role_organization_delete.do?txtId="+txtID+"&Action=DELETE&func_flag=119";
		 
	}
}

//-->
</script>
<TABLE border="0" cellspacing="0" cellpadding="0" width="820" >
<TR>
  <TD>
<table  border="0" align="center" cellpadding="0" cellspacing="10" >
  <tr>
    <td><img src="img/list_tit.gif" width="19" height="19"></td>
    <td class="title">修改组织机构角色</td>
  </tr>
</table>
 
<table width="100%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>
<br>
    
<form name="frmPost" action="" method="post" >   
<lgc:bloop requestObjectName="ResponseQueryResult" item="oneline" isOne="true" > 
<input type="hidden" name="txtDtCreate" value="<tbl:write name="oneline" property="dtCreate" />"  >
<input type="hidden" name="txtDtLastmod" value="<tbl:write name="oneline" property="dtLastmod" />"  >
<input type="hidden" name="func_flag" value="107" >
   <table width="100%" align="center" border="0" cellspacing="1" cellpadding="3" class="list_bg">
    <tr>   
    	<td class="list_bg2"><div align="right">流水号</div></td>
    	<td class="list_bg1"  align="left">
    	<input type="text" name="txtId" size="25" maxlength="10" value="<tbl:write name="oneline" property="id" />" class="textgray" readonly >
    	</td>
    	 <td class="list_bg2"><div align="right">客户所属区域*</div></td>
    	 <td class="list_bg1">         
            <input type="hidden" name="txtDistrictId" value="<tbl:write name="oneline" property="districtId" />">
	    <input type="text" name="txtDisDescrpition" size="25" readonly maxlength="50"  value="<tbl:WriteDistrictInfo name="oneline" property="districtId" />">
          <input name="selDistButton" type="button" class="button" value="选择" onClick="javascript:sel_district('S,P,T','txtDistrictId','txtDisDescrpition')">
        </td>
   </tr>
   <tr>	    	  		   
      <td class="list_bg2"><div align="right">故障类别</div></td>
      <td class="list_bg1"  align="left"><d:selcmn name="txtTroubleType" mapName="SET_C_CPPROBLEMLEVEL" match="oneline:troubleType" width="23" /></td>  
       <td class="list_bg2"><div align="right">故障子类别</div></td>
      <td class="list_bg1"  align="left"><d:selcmn name="txtTroubleSubType" mapName="SET_C_CPPROBLEMCATEGORY" match="oneline:troubleSubType" width="23" /></td>    	  	  	  	
    </tr>
    <tr> 
         <td class="list_bg2"><div align="right">提供服务的组织</div></td>
    	 <td class="list_bg1">         
          <input type="hidden" name="txtServiceOrgId" value="<tbl:write name="oneline" property="serviceOrgId" />">
	  <input type="text" name="txtOrgDes" size="25"   readonly value="<tbl:WriteOrganizationInfo name="oneline" property="serviceOrgId" />">
          <input name="selDistButton" type="button" class="button" value="选择" onClick="javascript:sel_organization('C,B,D,O,S,P','txtServiceOrgId','txtOrgDes')">
        </td>
         <td class="list_bg2"><div align="right">单据类型*</div></td>
      	 <td class="list_bg1"  align="left"><d:selcmn name="txtOrgRole" mapName="SET_S_ROLEORGNIZATIONORGROLE" match="oneline:orgRole" width="23" /></td>  
     </tr> 
      <tr>   
      <td class="list_bg2"><div align="right">创建业务帐户的产品</div></td>
      <td class="list_bg1" align="left">
      <tbl:select name="txtSaProductId" set="PRODUCTNAME" match="oneline:saProductId" width="23"/>
     </td>
      <td class="list_bg2"><div align="right">诊断结果</div></td>
        <td class="list_bg1"  align="left">
         <d:selcmn name="txtDiagnosisResult" mapName="SET_W_ROLEORGANIZATIONDIAGNOSISRESULT" match="oneline:diagnosisResult" width="23" />
      </td>    	  	
     
   </tr>	   	  	     
    <tr> 
        <td class="list_bg2"><div align="right">是否优先处理</div></td>
     	<td class="list_bg1" colspan="3" align="left"><d:selcmn name="txtIsFirst" mapName="SET_G_YESNOFLAG" match="oneline:isFirst" width="23" /></td>
   </tr>
  
		      
      	
    <tr>  
      <td class="list_bg2" colspan="4" align="center">
          <table  border="0" cellspacing="0" cellpadding="0">
			  <tr>
				<td><img src="img/button2_r.gif" width="22" height="20"></td>
	   			<td background="img/button_bg.gif"><a href="<bk:backurl property="role_organization_query.do" />" class="btn12">返    回</a></td> 
	   			<td><img src="img/button2_l.gif" width="11" height="20"></td> 
				<td width="22"></td>
				<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
				<td><input name="Submit" type="button" class="button" value="保  存" onclick="javascript:modify_submit()"></td>
				<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
				 
				<td width="22"></td>
				<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
				<td><input name="Submit" type="button" class="button" value="删 除" onclick="javascript:delete_submit('<tbl:write name="oneline" property="id" />')"></td>
				<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
				 
			  </tr>
	      </table>
     </tr>
   </table>
   </lgc:bloop>  
   <br>
  <table width="100%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
    <tr>
      <td><img src="img/mao.gif" width="1" height="1"></td>
    </tr>
  </table>  
   <input type="hidden" name="Action" value="MODIFY" >
  </form>