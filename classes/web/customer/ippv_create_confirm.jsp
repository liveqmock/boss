<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="osstags" prefix="d" %>
<%@ taglib uri="bookmark" prefix="bk" %>

<%@ page import="com.dtv.oss.util.Postern" %>
<%@ page import="com.dtv.oss.web.util.CommonKeys" %>
<%@ page import ="java.util.*" %>
<%@ page import="com.dtv.oss.service.commandresponse.CommandResponse" %>

<Script language=JavaScript>
function back_submit(){
	document.frmPost.action="ippv_create.do";
	document.frmPost.submit();
}
function frm_submit(){
	
	document.frmPost.submit();
	
}
</Script>
<% 	String title ="";
	if(request.getParameter("txtActionType")!=null&&"walletCreate".equals(request.getParameter("txtActionType")))
		title = "小钱包创建——确认";
	else
		title = "小钱包充值——确认";
%>

<form name="frmPost" method="post" action="ippv_create_confirm.do" >

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
	        <td valign="middle" class="list_bg2" align="right" width="17%">业务帐户ID</td>
	        <td class="list_bg1" width="33%">
	          <input name="txtServiceAccountID" value="<tbl:writeparam name="txtServiceAccountID"/>" size="25" readonly class="textgray"/>
	        </td>
	        <td valign="middle" class="list_bg2" align="right" width="17%">设备序列号</td>
	        <td class="list_bg1" width="33%">
	          <input name="txtScSerialNo" value="<tbl:writeparam name="txtScSerialNo"/>" size="25" readonly class="textgray"/>
	        </td>
       </tr>
       <%Map walletMap = Postern.getCAWalletDefineMap();
         pageContext.setAttribute("mapWallet",walletMap);
         Map rateMap = Postern.getAllCAWalletRate();
         pageContext.setAttribute("mapRate",rateMap);%>
       <tr>
	        <td valign="middle" class="list_bg2" align="right" >小钱包名称</td>
	        <td class="list_bg1" >
	          <input name="txtCAWalletName" value="<tbl:writeparam name="txtCAWalletName"/>" size ="25" class="textgray" readonly/>
	        </td>
	        <td valign="middle" class="list_bg2" align="right" >兑换比率</td>
	        <td class="list_bg1" >
	          <input name="txtRate" value="<tbl:writeparam name="txtRate"/>" size ="25" class="textgray" readonly/>
	        </td>	        
       </tr>
       <%	String csiType = (request.getParameter("txtCsiType")==null)?"":request.getParameter("txtCsiType");
       		Map mapMopID = Postern.getOpeningPaymentMop(csiType);
       		pageContext.setAttribute("mapMopID",mapMopID);%>

       <tr>
	        <td valign="middle" class="list_bg2" align="right" >支付方式</td>
	        <td class="list_bg1" >
	        <input name="txtMopName" value="<d:getcmnname typeName="mapMopID"  match="txtMopID" />" size="25" readonly class="textgray">
	        </td>
	        <td valign="middle" class="list_bg2" align="right" >充值金额</td>
	        <td class="list_bg1" >
	          <input name="txtValue" value="<tbl:writeparam name="txtValue"/>" size="25" class="textgray" readonly />
	        </td>	        
       </tr>
       <%CommandResponse RepCmd = (CommandResponse)(request.getAttribute("ResponseFromEJBEvent"));
         Integer strPoint = null;
         if(RepCmd != null)
         	strPoint = (Integer)RepCmd.getPayload();
         int point = (strPoint == null) ? 0 : strPoint.intValue();
       %>
	<tr>
	        <td valign="middle" class="list_bg2" align="right" >充值点数</td>
	        <td class="list_bg1" colspan="3">
	          <input name="txtPoint" value="<%=point%>" size="25" class="textgray" readonly />
	        </td>	        
       </tr>
  </table>  
	<tbl:hiddenparameters pass="txtCAWalletCode"/>
	<tbl:hiddenparameters pass="txtCustomerID/txtActionType/txtWalletId/txtCsiType/txtMopID"/>
	<input type="hidden" name="txtDoPost" value="true"/>
	<input type="hidden" name="func_flag" value="11026"/>
		<BR>  
     <table align="center"  border="0" cellspacing="0" cellpadding="0">      
        <tr>  
          <td width="20" ></td>
          <td><img src="img/button2_r.gif" width="22" height="20"></td>
	        <td><input name="aaa" type="button" class="button" onClick="back_submit()" value="上一步"></td>           
          <td><img src="img/button2_l.gif" width="11" height="20"></td>
          <td width="20" ></td>
          <td><img src="img/button_l.gif" width="11" height="20"></td>
          <td><input name="bbb" type="button" class="button" onClick="frm_submit()" value="确&nbsp认"></td>
          <td><img src="img/button_r.gif" width="22" height="20"></td>  
        </tr>
     </table>
</form>      
		 


