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
   String csiType ="";
   if (Integer.parseInt(actionType) == CommonKeys.CUSTOMER_PRODUCT_UPGRADE){
       actionType ="A";
       csiType =CommonKeys.CUSTSERVICEINTERACTIONTYPE_PU;
   } else if (Integer.parseInt(actionType) == CommonKeys.CUSTOMER_PRODUCT_DOWNGRADE){
       actionType ="F";
       csiType =CommonKeys.CUSTSERVICEINTERACTIONTYPE_PD;
   }

   Map productList = Postern.getDescendAndAscendProductMap(WebUtil.StringToInt(productid),actionType);
   String txtObjectProduct =request.getParameter("txtObjectProduct");
   String txtObjectProductName =(String)productList.get(txtObjectProduct);

   if ("A".equals(actionType))
	    name = "客户产品升级";
   if ("F".equals(actionType))
	    name = "客户产品降级";
%>
<Script language=javascript>

function frm_next(csiPayOption){
		document.frmPost.txtcsiPayOption.value =csiPayOption;
		if (check_fee()){
	     document.frmPost.action="customer_product_updown_grade_pay.screen";
	  }else{
	 	  //document.frmPost.action="customer_product_updown_grade_commit.do";
	 	  //document.frmPost.txtDoPost.value="true";
	 	  document.frmPost.action="customer_product_updown_grade_confirm.screen";
	 	  document.frmPost.txtDoPost.value="false";
	 	  document.frmPost.txtConfirmBackFlag.value="caculatefee";
	  }
	  document.frmPost.submit();
}

function frm_finish(csiPayOption){
	 document.frmPost.txtcsiPayOption.value =csiPayOption;
	 //document.frmPost.action="customer_product_updown_grade_commit.do";
	 //document.frmPost.txtDoPost.value="true";
	 document.frmPost.action="customer_product_updown_grade_confirm.screen";
	 document.frmPost.txtDoPost.value="false";
	 document.frmPost.txtConfirmBackFlag.value="caculatefee";
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
<input type="hidden" name="txtConfirmBackFlag" value="" />
<tbl:hiddenparameters pass="txtActionType/txtProdID/txtPsID" />
<table align="center" width="98%" border="0" cellspacing="1" cellpadding="5" class="list_bg">
	<tr>
		<td class="list_bg2" align="right" width="17%">客户证号</td>
		<td class="list_bg1" width="33%"><tbl:writeparam name="txtCustomerID"/></td>
		<td class="list_bg2" align="right" width="17%">客户姓名</td>
		<td class="list_bg1" width="33%"><tbl:writeparam name="txtCustomerName"/></td>
	</tr>
	<tr>
		<td class="list_bg2" align="right" width="17%">客户产品名称</td>
		<td class="list_bg1" width="33%"><tbl:writeparam name="txtProductName"/></td>
		<td class="list_bg2" align="right" width="17%">客户产品状态</td>
		<td class="list_bg1" width="33%"><tbl:writeparam name="txtStatusShow"/></td>
	</tr>
	<tr>
		<td class="list_bg2" align="right" width="17%">产品包名称</td>
		<td class="list_bg1" width="33%"><tbl:writeparam name="txtPackageName"/></td>
		<td class="list_bg2" align="right" width="17%">创建时间</td>
		<td class="list_bg1" width="33%"><tbl:writeparam name="txtCreateTime"/></td>
	</tr>
	<tr>
		<td class="list_bg2" align="right" width="17%">业务账号</td>
		<td class="list_bg1" width="33%"><tbl:writeparam name="txtServiceAccountID"/></td>
		<td class="list_bg2" align="right" width="17%">账号</td>
		<td class="list_bg1" width="33%"><tbl:writeparam name="txtAccount"/></td>
	</tr>
	<tr>
		<td class="list_bg2" align="right" width="17%">目标产品</td>
		<td class="list_bg1" colspan="3" width="83%">
		  <%=txtObjectProductName%>
		</td>
	</tr>
</table>

<tbl:CommonFeeAndPaymentInfo includeParameter="Fee" payCsiType="<%=csiType%>" acctshowFlag ="true"  />	

<tbl:hiddenparameters pass="txtCustomerID/txtCustomerName/txtProductName/txtStatusShow/txtPackageName/txtCreateTime/txtServiceAccountID/txtAccount/txtObjectProduct" />
<input type="hidden" name="txtcsiPayOption" >

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
		
		<jsp:include page = "/fee/common_control.jsp"/>
	</tr>
</table>
<tbl:generatetoken />
</form>