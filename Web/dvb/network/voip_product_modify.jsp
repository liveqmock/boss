<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="resultset" prefix="rs" %>
<%@ taglib uri="osstags" prefix="d" %>
<%@ taglib uri="bookmark" prefix="bk" %>

<%@ page import="com.dtv.oss.util.Postern,java.util.*,com.dtv.oss.web.util.WebUtil;"%>
 <%@ page import="com.dtv.oss.dto.VOIPProductDTO"%>

<SCRIPT language="JavaScript">
function modify_submit()
{
	if(check_frm())
		document.frmPost.submit();
}
 
function check_frm(){
	if(document.frmPost.txtSMSName.value==""){
		alert("没有选择SMS产品名称，请先做选择！");
		return false;
	}else if(document.frmPost.txtvoipProductName.value==""){
		alert("没有选择语音产品名称，请先做选择！");
		return false;
	}else if(document.frmPost.txtserviceType.value==""){
		alert("没有服务类型，请先做选择！");
		return false;
	}
	return true;
}

</SCRIPT>

<form name="frmPost" method="post" action="voip_product_modify.do">
<INPUT TYPE="hidden" name="actionFlag" value="modify">
<input type="hidden" name="txtFrom"  value="1" > 
<input type="hidden" name="txtTo"  value="10" >

<table border="0" align="center" cellpadding="0" cellspacing="10">
	<tr>
		<td><img src="img/list_tit.gif" width="19px" height="19px"></td>
		<td class="title">语音产品修改</td>
	</tr>
</table>
<table width="100%" border="0" align="center" cellpadding="0"
	cellspacing="0" background="img/line_01.gif">
	<tr>
		<td><img src="img/mao.gif" width="1px" height="1px"></td>
	</tr>
</table>
<br>
<lgc:bloop requestObjectName="ResponseQueryResult" item="oneline">
<%
   VOIPProductDTO dto = (VOIPProductDTO)pageContext.getAttribute("oneline");
   String sName=Postern.getProductNameByID(dto.getProductID());

%> 
<table width="810" align="center" border="0" cellspacing="1"
	cellpadding="3">
	<tr>
		<td class="list_bg2" align="right" width="17%">SMS产品名称*</td>
		<td class="list_bg1" width="33%"><input name="txtSMSName1"      maxlength="200" size="25" value="<%=sName%>" class="textgray" readonly ></td>
		
		<td class="list_bg2" align="right" width="17%">语音产品名称*</td>
		<td class="list_bg1" width="33%">
		<d:selcmn name="txtvoipProductName" mapName="SET_SSIF_PRODUCTVOIP" match="oneline:propertyName" width="23"/>
		 </td>
	</tr>
	<tr>
		<td class="list_bg2" align="right">服务类型*</td>
		<td class="list_bg1">
		 <d:selcmn name="txtserviceType" mapName="SET_SSIF_TYPE" match="oneline:sssrvType" width="23"/>
		 
		 
		</td>
		<td class="list_bg2" align="right">语音服务编码*</td>
		<td class="list_bg1">
		<d:selcmn name="txtserviceCode1" mapName="SET_SSIF_CODE"  disabled="true" match="oneline:sssrvCode" width="23"/>
		 
		 </td>
	</tr>
	 
	<tr>
		<td class="list_bg2" align="right">描述</td>
		<td class="list_bg1" colspan="3">
	        <font size="2"><input type="text" name="txtdescription" size="25"  value="<tbl:write name="oneline" property="description"/>"></font></td>
		 
	</tr>
</table>
<input type="hidden" name="txtserviceCode"   value="<tbl:write name="oneline" property="sssrvCode" />">
 <input type="hidden" name="txtSMSName"   value="<tbl:write name="oneline" property="productID" />">
 <input type="hidden" name="txtDtLastmod"   value="<tbl:write name="oneline" property="DtLastmod" />">
</lgc:bloop>  
    <table width="100%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
	<tr align="center">
	     <td class="list_bg1">

	  <table align="center"  border="0" cellspacing="0" cellpadding="0">
         <tr>  
            <td><img src="img/button2_r.gif" width="22" height="20"></td>
            <td background="img/button_bg.gif"><a href="<bk:backurl property="voip_product_list.do" />" class="btn12">返&nbsp;回</a></td>
            <td><img src="img/button2_l.gif" width="11" height="20"></td>
             
            <td width="20" ></td>  

            <td><img src="img/button_l.gif" width="11px" height="20px"></td>
            <td><input name="Submit" type="button" class="button"
					value="修&nbsp;改" onclick="javascript:modify_submit()"></td>
            <td><img src="img/button_r.gif" width="22px" height="20px"></td>          
        </tr>
      </table>	
	   </td>
	</tr>
    </table>    
 <br>
 <INPUT TYPE="hidden" name="func_flag" value="2001"> 
</form>
