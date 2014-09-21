<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="resultset" prefix="rs" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="osstags" prefix="d" %>

<%@ page import="com.dtv.oss.util.Postern" %>
<%@ page import="com.dtv.oss.web.util.CommonKeys" %>
<%@ page import="com.dtv.oss.service.commandresponse.QueryCommandResponseImpl"%>
<%@ page import="com.dtv.oss.dto.wrap.customer.Customer2AddressWrap, java.util.HashMap"%>
<%@ page import="com.dtv.oss.dto.CustomerDTO, com.dtv.oss.dto.AddressDTO"%>
<script language=javascript>
function frmPost_close(){
	var custid = eval('window.opener.frmPost.txtCustomerID');
	var custname = eval('window.opener.frmPost.txtCustomerName');
	var contactname = eval('window.opener.frmPost.txtContactPerson');
	var contactphone = eval('window.opener.frmPost.txtContactPhone');
	var autonextorgid = eval('window.opener.frmPost.txtAutoNextOrgID');
	var autonextorgname = eval('window.opener.frmPost.txtNextOrgName');
	if(custid==""){
	window.close();
	}
	custid.value = document.frmPost.txtCustomerID.value;
	custname.value = document.frmPost.txtCustomerName.value;
	contactname.value = document.frmPost.txtContactPerson.value;
	contactphone.value = document.frmPost.txtContactPhone.value;
	if(autonextorgid!=null&& autonextorgname !=null){
		autonextorgid.value = document.frmPost.txtAutoNextOrgID.value;	
		autonextorgname.value = document.frmPost.txtAutoNextOrgName.value;
	}
	window.close();
}
</script>
<form name="frmPost" action="" method="post" >  
	<br>
	<table width="580px" border="0" align="center" cellpadding="0" cellspacing="0" >
		<tr><td width="100%">
<table width="100%" border="0" align="center" cellpadding="4" cellspacing="1" class="list_bg">
	<tr>
	  <td width="100%" class="import_tit" align="center">客户信息</td>
  </tr>
</table>
<rs:hasresultset>
	<%boolean hasresult=false;%>
   <lgc:bloop requestObjectName="ResponseQueryResult" item="oneline" >
   <%
	Customer2AddressWrap wrap = new Customer2AddressWrap();
	QueryCommandResponseImpl RepCmd = (QueryCommandResponseImpl)pageContext.getRequest().getAttribute("ResponseQueryResult");
	Collection collection = (Collection) RepCmd.getPayload();
	Iterator iterator = collection.iterator();
	if(iterator.hasNext())
	{
	wrap = (Customer2AddressWrap)iterator.next();
	pageContext.setAttribute("cust", wrap.getCustDto());
	pageContext.setAttribute("addr", wrap.getAddrDto());
	Map acctMap = wrap.getMarkInfoMap();
	hasresult=true;
	int addressID = wrap.getCustDto().getAddressID();
        System.out.println("%%%%%%%%%%%%%%%%%%%"+addressID);
        int autoOrgid=Postern.getAutoNextOrgID("C",null,null,null,null,Postern.getDistrictIDByAddressID(addressID),0);
%>
   <table width="100%"  border="0" align="center" cellpadding="4" cellspacing="1" class="list_bg">
        <tr>
          <td width="12%" align="right" class="list_bg2">客户ID</td>
          <td width="33%" class="list_bg1" align="left"><font size="2">
             <input type="text" name="txtCustomerID" maxlength="10" size="25"  value="<tbl:write name="cust" property="customerID"/>" class="text" readonly>
          </font> </td>                      
          <td width="12%" class="list_bg2" align="right">客户名称</td>          
          <td width="33%" class="list_bg1" align="left"><font size="2"> 
            <input type="text" readonly name="txtCustomerName" maxlength="10" size="25"  value="<tbl:write name="cust" property="name"/>" class="text" readonly>
          </font> </td>
        </tr> 
        <tr>  
           <td  class="list_bg2" align="right">联系人*</td>
           <td  class="list_bg1" align="left"><font size="2">
             <input type="text" name="txtContactPerson" maxlength="10" size="25"  value="<tbl:write name="cust" property="name"/>" class="text" >  
           </font></td>  
           <td  class="list_bg2" align="right">联系电话</td>
           <td  class="list_bg1" align="left"><font size="2">        
             <input type="text" name="txtContactPhone" maxlength="10" size="25"  value="<tbl:write name="cust" property="telephoneMobile"/>" class="text" >               
           </font></td>
        </tr>
     </table>
     <input type="hidden" name="txtAutoNextOrgID" value="<%=autoOrgid%>">
     <input type="hidden" name="txtAutoNextOrgName" value="<%=Postern.getOrganizationDesc(autoOrgid)%>">
     <%}%>
     </lgc:bloop>
       <br>
       <%if(hasresult){%>
	<table align="center" border="0" cellspacing="0" cellpadding="0" >
		<tr >
		   <td><img src="img/button2_r.gif" width="22" height="20"></td>
		   <td><input name="prev" type="button" class="button" onClick="javascript:frmPost_close()" value="确 定"></td>
		   <td><img src="img/button2_l.gif" width="11" height="20"></td>
		</tr>
	</table>
	<%}%>
</rs:hasresultset> 

<rs:hasnoresultset> 
	<table align="center" border="0" cellspacing="0" cellpadding="0" >
		<tr>
		   <td><img src="img/button2_r.gif" width="22" height="20"></td>
		   <td><input name="closeButton" type="button" class="button" onClick="javascript:window.close();" value="没有结果,关闭窗口"></td>
		   <td><img src="img/button2_l.gif" width="11" height="20"></td>
		</tr>
	</table>
</rs:hasnoresultset> 
  </td></tr></table>
</form>