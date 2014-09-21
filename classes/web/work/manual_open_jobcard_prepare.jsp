<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="resultset" prefix="rs" %>
<%@ taglib uri="/WEB-INF/oss.tld" prefix="d" %>

<%@ page import="com.dtv.oss.util.Postern,
                 java.util.HashMap,
                 java.util.Map,
                 java.util.Iterator" %>
<%@ page import="com.dtv.oss.dto.AddressDTO" %>
<%@ page import="com.dtv.oss.dto.JobCardDTO" %>
<%@ page import="com.dtv.oss.web.util.WebOperationUtil" %>
<table width="98%" border="0" cellspacing="5" cellpadding="5">
<tr> 
  <td width="100%">
    <div align="center"> 
      <p align="center" class="title1">正在获取客户信息，请稍候。。。</strong></p>
    </div>
  </td>
</tr>
</table>
<form name="frmPost" method="post" >
    <lgc:bloop requestObjectName="ResponseQueryResult" item="oneline"  IsOne="true">
        <%
        com.dtv.oss.dto.wrap.customer.Customer2AddressWrap wrap = (com.dtv.oss.dto.wrap.customer.Customer2AddressWrap)pageContext.getAttribute("oneline");
        pageContext.setAttribute("cust", wrap.getCustDto());
        pageContext.setAttribute("custaddr",  wrap.getAddrDto()); 
        %>
        <input type="hidden" name="txtCustomerID" value="<tbl:write name="cust" property="customerID" />">
        <input type="hidden" name="txtCustomerType"   value="<tbl:write name="cust" property="customerType" />" >
        <input type="hidden" name="txtOpenSourceType"   value="<tbl:write name="cust" property="OpenSourceType" />" >
        <input type="hidden" name="txtCatvID"   value="<tbl:write name="cust" property="CatvID" />" >
        <input type="hidden" name="txtName" value="<tbl:write name="cust" property="name" />">
        <input type="hidden" name="txtTelephone" value="<tbl:write name="cust" property="telephone" />">
        <input type="hidden" name="txtTelephoneMobile" value="<tbl:write name="cust" property="telephoneMobile" />">
        
        <input type="hidden" name="txtAddressID" value="<tbl:write name="custaddr" property="addressID"/>">
        <input type="hidden" name="txtDistrictID" value="<tbl:write name="custaddr" property="districtID" />">
        <input type="hidden" name="txtDetailAddress" value="<tbl:write name="custaddr" property="DetailAddress" />">
    </lgc:bloop>
</form>
</TABLE>
<Script language=Javascript>
    document.frmPost.action ="manual_open_jobcard.do" ;
    document.frmPost.submit(); 
</Script>