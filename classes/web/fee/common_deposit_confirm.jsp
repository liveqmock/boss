<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="resultset" prefix="rs" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="osstags" prefix="d" %>
<%@ page import="com.dtv.oss.util.Postern" %>
<%@ page import="java.util.*" %>

<%
    String[] pay_realMOP =request.getParameterValues("pay_realMOP");
    String[] pay_realaddInfo =request.getParameterValues("pay_realaddInfo"); 
    String[] pay_realPay =request.getParameterValues("pay_realpay");
    if (pay_realPay !=null){
%>

<table align="center" width="100%" border="0" cellpadding="4" cellspacing="1" class="list_bg">
   <tr> 
      <td class="import_tit" align="center" colspan="4"><font size="3">预存清单</font></td>
   </tr>
   <tr class="list_head">
      <td class="list_head" width="30%" align="center">支付方式</td>
      <td class="list_head" width="40%" align="center">票据编号</td>
      <td class="list_head" width="30%" align="center">预存金额</td>
   </tr>
   <%
      //付费方式
      String csiType =(String)request.getAttribute("PayCsiType");
      Map mapBankMop1 =Postern.getOpeningPaymentMop(csiType);
      String strMopName1 = null;

      for (int i=0; i<pay_realPay.length; i++) {
          //获得支付方式的中文名
          if (pay_realMOP[i].equals("")) continue;
          strMopName1 = (String)mapBankMop1.get(pay_realMOP[i]);
          if (strMopName1==null) strMopName1="";
   %>
    <tbl:printcsstr style1="list_bg1" style2="list_bg2" overStyle="list_over">
        <td align="center"><%=strMopName1%>  </td>
        <td align="center"><%=pay_realaddInfo[i]%>  </td>
        <td align="center"><%=pay_realPay[i]%>  </td>
        <input type="hidden" name="pay_realMOP" value="<%=pay_realMOP[i]%>" >
	      <input type="hidden" name="pay_realaddInfo" value="<%=pay_realaddInfo[i]%>" >
        <input type="hidden" name="pay_realpay" value="<%=pay_realPay[i]%>" > 
     </tbl:printcsstr>
  <%
      }
  %> 
</table>
 <% } %>
 <table align="center" width="100%" border="0" cellpadding="4" cellspacing="1" class="list_bg"> 	
	 <tr class="list_bg2">
	 	 	<td  valign="middle" align="right"  width="60%" colspan="2">预存金额总计:</td>
      <td  valign="middle" align="center" width="30%"><input type="text" size="12" style="text-align:right" name="generalPayTotal" value="<tbl:writeparam name="generalPayTotal" />" class="textgray" readonly> </td>
 	    <td  valign="middle" align="center" width="10%" >&nbsp;</td>
 	 </tr>
</table>