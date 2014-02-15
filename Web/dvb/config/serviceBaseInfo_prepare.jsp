<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ page import="com.dtv.oss.dto.ServiceDTO" %>

<form name="frmPost" method="post" action="config_serviceBaseInfo_update.screen" >
<lgc:bloop requestObjectName="ResponseQueryResult" item="oneline" IsOne="true">      
  <tbl:hidden name="txtServiceID" value="oneline:ServiceID" />
  <tbl:hidden name="txtServiceName" value="oneline:ServiceName" />
  <tbl:hidden name="txtDtLastmod" value ="oneline:DtLastmod" />
  <tbl:hidden name="txtStatus" value ="oneline:status" />
  <tbl:hidden name="txtDescription" value="oneline:description" />
  <input type="hidden" name="txtDateFrom" value="<tbl:writedate name="oneline" property="DateFrom" />" />
  <input type="hidden" name="txtDateTo" value="<tbl:writedate name="oneline" property="DateTo" />" />
</lgc:bloop> 
</form>

<Script language=Javascript>
  document.frmPost.submit();   
</Script>
