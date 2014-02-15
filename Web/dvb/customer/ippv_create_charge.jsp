<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="osstags" prefix="d" %>
<%@ taglib uri="bookmark" prefix="bk" %>

<%@ page import="com.dtv.oss.util.Postern" %>
<%@ page import="com.dtv.oss.web.util.CommonKeys" %>
<%@ page import="java.util.*" %>

<Script language=JavaScript>

function frm_submit(){

	if (check_Blank(document.frmPost.txtMopID, true, "֧����ʽ")){
		return false;	
	}

	if (!check_Float(document.frmPost.txtValue, true, "��ֵ���")){
		return false;	
	}
	if(document.frmPost.txtValue.value < 0){
		alert("��ֵ������Ч�Ľ��!");
		return false;
	}else if(document.frmPost.txtValue.value >999999999){
		alert("��ֵ���̫��!");
		return false;
	}
	
	document.frmPost.submit();
	
}
function back_submit(){
	document.frmPost.action = "<bk:backurl property='menu_ippv_create.do/ca_wallet_query.do' />";
	document.frmPost.submit();
}
function back_submit1(){
	document.frmPost.action = "menu_ippv_create.do";
	document.frmPost.submit();
}
</Script>
<% 	String title ="";
	if(request.getParameter("txtActionType")!=null&&"walletCreate".equals(request.getParameter("txtActionType")))
		title = "СǮ������������ֵ";
	else
		title = "СǮ����ֵ";
	
%>
<form name="frmPost" method="post" action="ippv_create_charge.do" >

    <table  border="0" align="center" cellpadding="0" cellspacing="5">
      <tr>
        <td><img src="img/list_tit.gif" width="19" height="19"></td>
        <td class="title"><%=title%></td>
      </tr>
    </table>
    <br>
    <table width="100%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
      <tr>
        <td><img src="img/mao.gif" width="1" height="1"></td>
      </tr>
    </table>
    <br>
 
    <table width="100%"  border="0" align="center" cellpadding="4" cellspacing="1" class="list_bg">
       <tr>
	        <td valign="middle" class="list_bg2" align="right" width="17%">ҵ���ʻ�ID</td>
	        <td class="list_bg1" width="33%">
	          <input name="txtServiceAccountID" value="<tbl:writeparam name="txtServiceAccountID"/>" size="25" readonly class="textgray"/>
	        </td>
	        <td valign="middle" class="list_bg2" align="right" width="17%">�豸���к�</td>
	        <td class="list_bg1" width="33%">
	          <input name="txtScSerialNo" value="<tbl:writeparam name="txtScSerialNo"/>" size="25" readonly class="textgray"/>
	        </td>
       </tr>
       <%Map walletMap = Postern.getCAWalletDefineMap();
         pageContext.setAttribute("mapWallet",walletMap);
         Map rateMap = Postern.getAllCAWalletRate();
         pageContext.setAttribute("mapRate",rateMap);%>
       <tr>
	        <td valign="middle" class="list_bg2" align="right" >СǮ������</td>
	        <td class="list_bg1" >
	          <input name="txtCAWalletName" value="<d:getcmnname  typeName="mapWallet"  match="txtCAWalletCode" />" size="25" readonly class="textgray"/>
	        </td>
	        <td valign="middle" class="list_bg2" align="right" >�һ�����</td>
	        <td class="list_bg1" >
	          <input name="txtRate" value="<d:getcmnname typeName="mapRate"  match="txtCAWalletCode" />" size="25" readonly class="textgray"/>
	        </td>	        
       </tr>
       <%	String csiType = (request.getParameter("txtCsiType")==null)?"":request.getParameter("txtCsiType");
       		Map mapMopID = Postern.getOpeningPaymentMop(csiType);
       		pageContext.setAttribute("mapMopID",mapMopID);%>

       <tr>
	        <td valign="middle" class="list_bg2" align="right" >֧����ʽ*</td>
	        <td class="list_bg1" >
	        <tbl:select name="txtMopID" set="mapMopID"  match="txtMopID" width="23" />
	        </td>
	        <td valign="middle" class="list_bg2" align="right" >��ֵ���*</td>
	        <td class="list_bg1" >
	          <input name="txtValue" value="<tbl:writeparam name="txtValue"/>" size="25" maxlength="9"/>
	        </td>	        
       </tr>

	  </table>  
	<tbl:hiddenparameters pass="txtCAWalletCode"/>
	<tbl:hiddenparameters pass="txtCustomerID/txtActionType/txtWalletId/txtCsiType"/>
	<input type="hidden" name="txtDoPost" value="false"/>
		<BR>  
     <table align="center"  border="0" cellspacing="0" cellpadding="0">
      
        <tr> 
        <%if(request.getParameter("txtActionType")!=null && "walletCreate".equals(request.getParameter("txtActionType"))){%> 
          <td width="20" ></td>
          <td><img src="img/button2_r.gif" width="22" height="20"></td>
	         <td><input name="prev" type="button" class="button" onClick="back_submit1()" value="��һ��"></td>
          <td><img src="img/button2_l.gif" width="11" height="20"></td>
          <%}else{%>
          <td width="20" ></td>
          <td><img src="img/button2_r.gif" width="22" height="20"></td>
          	<td><input name="prev" type="button" class="button" onClick="back_submit()" value="��&nbsp��"></td>	       
          <td><img src="img/button2_l.gif" width="11" height="20"></td>
          <%}%>
          <td width="20" ></td>
          <td><img src="img/button_l.gif" width="11" height="20"></td>
          <td><input name="bbb" type="button" class="button" onClick="frm_submit()" value="��һ��"></td>
          <td><img src="img/button_r.gif" width="22" height="20"></td>  
        </tr>
     </table>
</form>      
		 


