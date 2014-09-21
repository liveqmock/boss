<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl"%>
<%@ taglib uri="logic" prefix="lgc"%>
<%@ taglib uri="resultset" prefix="rs"%>
<%@ taglib uri="/WEB-INF/oss.tld" prefix="d"%>
<%@ taglib uri="bookmark" prefix="bk" %>

<%@ page import="com.dtv.oss.util.Postern,java.util.*"%>
<%@ page import="com.dtv.oss.dto.CAProductDTO"%>

<SCRIPT language="JavaScript">
function check_form ()
{
    if (check_Blank(document.frmPost.txtEntitlement, true, "CA授权信息"))
 	    return false;
      if (!check_Num(document.frmPost.txtOPI_ID, true, "OPI_ID"))
	    return false;	    
	    
	return true;
}

function ca_product_modify()
{
	if(check_form()){
		 document.frmPost.submit();
	}
}

function back_submit(){
	url="<bk:backurl property='ca_sms_query.do' />";
	if(url=="")
		url="ca_sms_query.screen";
    document.location.href= url;
}
</SCRIPT>
<br>
<form name="frmPost" method="post" action="ca_productsms_op.do"> 
	<input type="hidden" name="txtActionType" size="20" value="CA_PRODUCTSMS_MODIFY">
	 
<table border="0" align="center" cellpadding="0" cellspacing="10">
	<tr>
		<td><img src="img/list_tit.gif" width="19" height="19"></td>
		<td class="title">修改SMS－CA产品映射关系</td>
	</tr>
</table>
 
<table width="100%" border="0" align="center" cellpadding="0"
	cellspacing="0" background="img/line_01.gif">
	<tr>
		<td><img src="img/mao.gif" width="1" height="1"></td>
	</tr>
</table>
<br>
 
<%
Map mapProducts=Postern.getAllProducts();
pageContext.setAttribute("products",mapProducts);
Map mapConditions=Postern.getAllCaConditions();
pageContext.setAttribute("conditions",mapConditions);

%>
		<lgc:bloop requestObjectName="ResponseQueryResult" item="oneline">
		<%
		CAProductDTO dto=(CAProductDTO)pageContext.getAttribute("oneline");

				String productName=Postern.getSMSProductNameByID(dto.getProductId());
				if(productName==null)
				productName="";
		%>
		<input type="hidden" name="txtDtLastmod" size="20" value="<tbl:write name="oneline" property="dtLastmod" />">
				<table width="100%" align="center" border="0" cellspacing="1"
					cellpadding="3">
					<tr>
						<td class="list_bg2" align="right" width="17%"></td>
						<td class="list_bg1" width="33%"></td>
						<td class="list_bg2" align="left" width="17%"></td>
						<td class="list_bg1" width="33%"></td>
					</tr>
					<tr>
						<td class="list_bg2" align="right">SMS产品名称</td>
						<td class="list_bg1"><input name="productName"
							type="text" class="textgray" maxlength="200" size="25"
							value="<%=productName%>" readonly ></font></td>
			                       <INPUT TYPE="hidden" name="txtProductID" value="<tbl:write name="oneline" property="productId" />"></td>
			                       <td class="list_bg2" align="right">条件ID*</td>
						<td class="list_bg1"><font size="2"><input name="txtConditionID"
							type="text" class="textgray" maxlength="10" size="25" readonly 
							value="<tbl:write name="oneline" property="conditionId" />"></font></td>
			                      
						
					</tr>
					<tr>
						<td class="list_bg2" align="right">CA授权信息*</td>
						<td class="list_bg1"><font size="2"><input name="txtEntitlement"
							type="text" class="text" maxlength="200" size="25"
							value="<tbl:write name="oneline" property="entitlement" />"></font></td>
						 <td class="list_bg2" align="right">OPI_ID</td>
						<td class="list_bg1"><font size="2"><input name="txtOPI_ID"
							type="text" class="text" maxlength="10" size="25"
							value="<tbl:write name="oneline" property="opiID" />"></font></td>
			                         
					</tr>
					<tr>
						<td class="list_bg2" align="right">描述</td>
						<td class="list_bg1" colspan="3"><input name="txtDesc"
							type="text" class="text" maxlength="150" size="83"
							value="<tbl:write name="oneline" property="description"/>"></td>
					</tr>
				</table>
		</lgc:bloop>
 
	<table width="100%" border="0" align="center" cellpadding="5"
		cellspacing="1" class="list_bg">
		<tr align="center">
			<td class="list_bg1">

			<table align="center" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td><img src="img/button2_r.gif" width="22" height="20"></td>
					<td><input name="Submit" type="button" class="button"
						value="返&nbsp;回" onclick="javascript:back_submit()"></td>
					<td><img src="img/button2_l.gif" width="11" height="20"></td>

					<td width="20"></td>
					 
						<td><img src="img/button_l.gif" width="11" height="20"></td>
						<td><input name="Submit" type="button" class="button"
							value="保&nbsp;存" onclick="javascript:ca_product_modify()"></td>
						<td><img src="img/button_r.gif" width="22" height="20"></td>
					 
				</tr>
			</table>
			</td>
		</tr>
	</table>
	<table width="100%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  		<tr>
    		  <td><img src="img/mao.gif" width="1" height="1"></td>
 		</tr>
 	</table>
</form>
