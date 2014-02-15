<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="/WEB-INF/oss.tld" prefix="d" %>
<%@ taglib uri="bookmark" prefix="bk" %>
<%@ page import="java.util.*,
                 com.dtv.oss.web.util.WebUtil,
                 com.dtv.oss.web.util.CommonKeys,
                 com.dtv.oss.util.Postern" %>

<%
   String productid = request.getParameter("txtProdID");
   String actionType = request.getParameter("txtActionType"); 
   String name = "";
   if (Integer.parseInt(actionType) == CommonKeys.CUSTOMER_PRODUCT_UPGRADE){
       actionType ="A";
   } else if (Integer.parseInt(actionType) == CommonKeys.CUSTOMER_PRODUCT_DOWNGRADE){
       actionType ="F";
   }

   Map productList = Postern.getDescendAndAscendProductMap(WebUtil.StringToInt(productid),actionType);
   System.out.println("productList=========="+productList);
   pageContext.setAttribute("productList" ,productList);

   if ("A".equals(actionType))
	    name = "客户产品升级";
   if ("F".equals(actionType))
	    name = "客户产品降级";
%>
<Script language=javascript>
function frm_submit()
{
	if(document.frmPost.txtObjectProduct.value == "")
	{
		alert("目标产品不能为空！");
		document.frmPost.txtObjectProduct.focus();
		return false;
	}
	document.frmPost.action="customer_product_updown_grade_fee.do";
	document.frmPost.submit();
}
</SCRIPT>

<table  border="0" align="center" cellpadding="0" cellspacing="10" >
	<tr>
		<td><img src="img/list_tit.gif" width="19" height="19"></td>
		<td class="title"><%=name%></td>
	</tr>
</table>
<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
	<tr>
		<td><img src="img/mao.gif" width="1" height="1"></td>
	</tr>
</table>
<br>
<form name="frmPost" method="post">
<input type="hidden" name="txtDoPost" value="false">
<tbl:hiddenparameters pass="txtActionType/txtProdID/txtPsID" />
<table align="center" width="98%" border="0" cellspacing="1" cellpadding="5" class="list_bg">
	<tr>
		<td class="list_bg2"><div align="right">客户证号</div></td>
		<td class="list_bg1"><input type="text" name="txtCustomerID" size="25"  value="<tbl:writeparam name="txtCustomerID"/>" class="textgray" readonly ></td>
		<td class="list_bg2"><div align="right">客户姓名</div></td>
		<td class="list_bg1"><input type="text" name="txtCustomerName" size="25"  value="<tbl:writeparam name="txtCustomerName"/>" class="textgray" readonly ></td>
	</tr>
	<tr>
		<td class="list_bg2"><div align="right">客户产品名称</div></td>
		<td class="list_bg1"><input type="text" name="txtProductName" size="25"  value="<tbl:writeparam name="txtProductName"/>" class="textgray" readonly ></td>
		<td class="list_bg2"><div align="right">客户产品状态</div></td>
		<td class="list_bg1"><input type="text" name="txtStatusShow" size="25"  value="<tbl:writeparam name="txtStatusShow"/>" class="textgray" readonly ></td>
	</tr>
	<tr>
		<td class="list_bg2"><div align="right">产品包名称</div></td>
		<td class="list_bg1"><input type="text" name="txtPackageName" size="25"  value="<tbl:writeparam name="txtPackageName"/>" class="textgray" readonly ></td>
		<td class="list_bg2"><div align="right">创建时间</div></td>
		<td class="list_bg1"><input type="text" name="txtCreateTime" size="25"  value="<tbl:writeparam name="txtCreateTime"/>" class="textgray" readonly ></td>
	</tr>
	<tr>
		<td class="list_bg2"><div align="right">业务账号</div></td>
		<td class="list_bg1"><input type="text" name="txtServiceAccountID" size="25"  value="<tbl:writeparam name="txtServiceAccountID"/>" class="textgray" readonly ></td>
		<td class="list_bg2"><div align="right">账号</div></td>
		<td class="list_bg1"><input type="text" name="txtAccount" size="25"  value="<tbl:writeparam name="txtAccount"/>" class="textgray" readonly ></td>
	</tr>
	<tr>
		<td class="list_bg2"><div align="right">目标产品</div></td>
		<td class="list_bg1" colspan="3"><tbl:select name="txtObjectProduct" set="productList" match="txtObjectProduct" width="22"/></td>
	</tr>
</table>
<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
	<tr>
		<td><img src="img/mao.gif" width="1" height="1"></td>
	</tr>
</table>
<br>
<table align="center" border="0" cellspacing="0" cellpadding="0">
	<tr>
		<bk:canback url="customer_product_view.do">
		<td width="20" ></td>  
		<td><img src="img/button2_r.gif" width="22" height="20"></td>
		<td background="img/button_bg.gif"  height="20" >
		<a href="<bk:backurl property="customer_product_view.do"/>" class="btn12">上一步</a></td>
		<td><img src="img/button2_l.gif" width="11" height="20"></td>
		</bk:canback>
		<td width="20" ></td>
		<td><img src="img/button_l.gif" border="0" width="11" height="20"></td>
		<td><input name="next" type="button" class="button" onClick="javascript:frm_submit()" value="下一步"></td>
		<td><img src="img/button_r.gif" border="0" width="22" height="20"></td>
	</tr>
</table>
</form>