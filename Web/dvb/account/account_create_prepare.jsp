<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="osstags" prefix="d" %>
<%@ taglib uri="bookmark" prefix="bk" %>
<%@ taglib uri="logic" prefix="lgc" %>

<%@ page import="java.util.ArrayList,
                 java.util.Iterator,
                 java.util.HashMap" %>
<%@ page import="com.dtv.oss.util.Postern" %>
<%@ page import="com.dtv.oss.web.util.WebUtil" %>
<%@ page import="com.dtv.oss.web.util.CommonKeys" %>
<%@ page import="com.dtv.oss.web.util.WebOperationUtil" %>
<%@ page import="com.dtv.oss.dto.MethodOfPaymentDTO" %>
<%@ page import="com.dtv.oss.dto.wrap.customer.Customer2AddressWrap" %>
<form name="frmPost" method="post" action="" >    
<lgc:bloop requestObjectName="ResponseQueryResult" item="oneline" IsOne="true">      
  <%  
       Customer2AddressWrap wrap = (Customer2AddressWrap)pageContext.getAttribute("oneline");
       pageContext.setAttribute("cust", wrap.getCustDto());
       pageContext.setAttribute("addr", wrap.getAddrDto());
    %>
    <input type="hidden" name="txtStatus" value="<tbl:write name="cust" property="Status" />" >
    <input type="hidden" name="confirm_post"  value="false" >
    <tbl:hiddenparameters pass="forwardFlag" /> 
    <input type="hidden" name="txtCustomerID" value="<tbl:write name="cust" property="customerID" />" >
    <input type="hidden" name="txtName" size="25" value="<tbl:write name="cust" property="name" />" >
    <input type="hidden" name="txtCounty" value="<tbl:write name="addr" property="districtID" />">
    <input type="hidden" name="txtDetailAddress" value="<tbl:write name="addr" property="detailAddress"  />">
    <input type="hidden" name="txtPostcode" value="<tbl:write name="addr" property="postcode" />">
</lgc:bloop>
</form>
<Script language=Javascript>
    document.frmPost.action ="account_create.do" ;
    document.frmPost.submit(); 
</Script>