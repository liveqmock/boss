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
	    name = "�ͻ���Ʒ����ȷ��";
   if ("F".equals(actionType))
	    name = "�ͻ���Ʒ����ȷ��";
%>
<Script language=javascript>
function back_submit(){
	if('caculatefee'=='<%=request.getParameter("txtConfirmBackFlag")%>'){
		document.frmPost.action="customer_product_updown_grade_fee.screen";
	}else{
	  document.frmPost.action="customer_product_updown_grade_pay.screen";
	}
	 document.frmPost.submit();
}

function frm_submit(){
	 document.frmPost.action="customer_product_updown_grade_commit.do";
	 document.frmPost.txtDoPost.value="true";
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
		<td class="list_bg2" align="right" width="17%">�ͻ�֤��</td>
		<td class="list_bg1" width="33%"><tbl:writeparam name="txtCustomerID"/></td>
		<td class="list_bg2" align="right" width="17%">�ͻ�����</td>
		<td class="list_bg1" width="33%"><tbl:writeparam name="txtCustomerName"/></td>
	</tr>
	<tr>
		<td class="list_bg2" align="right" width="17%">�ͻ���Ʒ����</td>
		<td class="list_bg1" width="33%"><tbl:writeparam name="txtProductName"/></td>
		<td class="list_bg2" align="right" width="17%">�ͻ���Ʒ״̬</td>
		<td class="list_bg1" width="33%"><tbl:writeparam name="txtStatusShow"/></td>
	</tr>
	<tr>
		<td class="list_bg2" align="right" width="17%">��Ʒ������</td>
		<td class="list_bg1" width="33%"><tbl:writeparam name="txtPackageName"/></td>
		<td class="list_bg2" align="right" width="17%">����ʱ��</td>
		<td class="list_bg1" width="33%"><tbl:writeparam name="txtCreateTime"/></td>
	</tr>
	<tr>
		<td class="list_bg2" align="right" width="17%">ҵ���˺�</td>
		<td class="list_bg1" width="33%"><tbl:writeparam name="txtServiceAccountID"/></td>
		<td class="list_bg2" align="right" width="17%">�˺�</td>
		<td class="list_bg1" width="33%"><tbl:writeparam name="txtAccount"/></td>
	</tr>
	<tr>
		<td class="list_bg2" align="right" width="17%">Ŀ���Ʒ</td>
		<td class="list_bg1" colspan="3" width="83%">
		  <%=txtObjectProductName%>
		</td>
	</tr>
</table>

<tbl:CommonFeeAndPaymentInfo includeParameter="Fee_Pay" payCsiType="<%=csiType%>" acctshowFlag ="true" confirmFlag="true" />		 	

<tbl:hiddenparameters pass="txtCustomerID/txtCustomerName/txtProductName/txtStatusShow/txtPackageName/txtCreateTime/txtServiceAccountID/txtAccount/txtObjectProduct" />
<input type="hidden" name="txtcsiPayOption" value="<tbl:writeparam name="txtcsiPayOption"/>" >

<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
	<tr>
		<td><img src="img/mao.gif" width="1" height="1"></td>
	</tr>
</table>
<br>
<table align="center" border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td width="20" ></td>  
		<td><img src="img/button2_r.gif" width="22" height="20"></td>
		<td background="img/button_bg.gif"  height="20" >
		<a href="javascript:back_submit()" class="btn12">��һ��</a></td>
		<td><img src="img/button2_l.gif" width="11" height="20"></td>
		<td width="20" ></td>
		<td><img src="img/button_l.gif" border="0" width="11" height="20"></td>
		<td><input name="next" type="button" class="button" onClick="javascript:frm_submit()" value="ȷ&nbsp;��"></td>
		<td><img src="img/button_r.gif" border="0" width="22" height="20"></td>
	</tr>
</table>
<tbl:generatetoken />
</form>