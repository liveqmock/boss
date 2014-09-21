<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="resultset" prefix="rs" %>
<%@ taglib uri="osstags" prefix="d" %>
<%@ taglib uri="bookmark" prefix="bk" %>

<%@ page import="com.dtv.oss.util.Postern,java.util.*"%>
 
 

<SCRIPT language="JavaScript">
function add_submit()
{
	if(check_frm())
		document.frmPost.submit();
}
function back_submit(){
	document.frmPost.action="voip_product_list.do?&txtQueryType=P";
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
	if (check_Blank(document.frmPost.txtserviceCode, true, "语音服务编码"))
		return false;
	return true;
}
</SCRIPT>

<form name="frmPost" method="post" action="voip_product_add.do">
<INPUT TYPE="hidden" name="actionFlag" value="add">
<input type="hidden" name="txtFrom"  value="1" > 
<input type="hidden" name="txtTo"  value="10" >
<table border="0" align="center" cellpadding="0" cellspacing="10">
	<tr>
		<td><img src="img/list_tit.gif" width="19px" height="19px"></td>
		<td class="title">语音产品新增</td>
	</tr>
</table>
<table width="100%" border="0" align="center" cellpadding="0"
	cellspacing="0" background="img/line_01.gif">
	<tr>
		<td><img src="img/mao.gif" width="1px" height="1px"></td>
	</tr>
</table>
<br>
<%Map mapAllProductName=Postern.getAllProductName();
  pageContext.setAttribute("AllProductName",mapAllProductName);
  Map mapAllVOIPProduct=Postern.getAllVOIPProduct();
  pageContext.setAttribute("AllVOIPProduct",mapAllVOIPProduct);
%>
<table width="810" align="center" border="0" cellspacing="1"
	cellpadding="3">
	<tr>
		<td class="list_bg2" align="right" width="17%">SMS产品名称*</td>
		<td class="list_bg1" width="33%"><font size="2"><tbl:select name="txtSMSName" set="AllProductName" width="25" match="txtSMSName"/></font></td>
		<td class="list_bg2" align="right" width="17%">语音产品名称*</td>
		
		<td class="list_bg1" width="33%">
		 <d:selcmn name="txtvoipProductName" mapName="SET_SSIF_PRODUCTVOIP" match="txtvoipProductName" width="25" />
		</td>
	</tr>
	<tr>
		<td class="list_bg2" align="right">服务类型*</td>
		<td class="list_bg1">
		<d:selcmn name="txtserviceType"  mapName="SET_SSIF_TYPE" match="txtserviceType" width="25" />
		</td>
		<td class="list_bg2" align="right">语音服务编码*</td>
		<td class="list_bg1">
		<d:selcmn name="txtserviceCode"  mapName="SET_SSIF_CODE" match="txtserviceCode" width="25" />
		</td>
	</tr>
	<tr>
		<td class="list_bg2" align="right">描述</td>
		<td class="list_bg1" colspan="3"><input name="txtdescription"
			type="text" class="text" maxlength="100" size="83"
			value="<tbl:writeparam name="txtdescription" />"></td>
	</tr>
</table>
    <table width="100%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
	<tr align="center">
	     <td class="list_bg1">

	  <table align="center"  border="0" cellspacing="0" cellpadding="0">
         <tr>  
            <td><img src="img/button2_r.gif" width="22px" height="20px"></td>
			<td><input name="Submit" type="button" class="button"
					value="返&nbsp;回" onclick="javascript:back_submit()"></td>
            <td><img src="img/button2_l.gif" width="11px" height="20px"></td>
            <td width="20" ></td>  

            <td><img src="img/button_l.gif" width="11px" height="20px"></td>
            <td><input name="Submit" type="button" class="button"
					value="新&nbsp;增" onclick="javascript:add_submit()"></td>
            <td><img src="img/button_r.gif" width="22px" height="20px"></td>          
        </tr>
      </table>	
	   </td>
	</tr>
    </table>    
 <br>
<INPUT TYPE="hidden" name="func_flag" value="2001"> 
</form>
