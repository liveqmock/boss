<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="bookmark" prefix="bk" %>
<%@ taglib uri="resultset" prefix="rs" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="osstags" prefix="d" %>

<%@ page import="com.dtv.oss.dto.CPCampaignMapDTO" %>
<%@ page import="com.dtv.oss.dto.CustomerProductDTO" %>
<%@ page import="com.dtv.oss.util.Postern"%>
 
<table width="100%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>   
  <rs:hasresultset> 
  <table width="100%" align="center" border="0" cellspacing="1" cellpadding="5" class="list_bg">
     <tr>
         <td class="import_tit" align="center"><font size="3">�ͻ���Ʒ��Ϣ</font></td>
     </tr>
  </table>
   <table width="100%" border="0" cellpadding="2" cellspacing="1" class="list_bg" align="center" >     
     <tr> 
          <td width="10%"  class="list_head"  align="center">���</td>
          <td width="10%"  class="list_head"  align="center">PSID</td>                    
          <td width="40%"  class="list_head"  align="center">�ͻ���Ʒ����</td> 
          <td width="8%"  class="list_head"  align="center">�ʻ�</td> 
          <td width="10%"  class="list_head"  align="center">ҵ���ʻ�</td> 
          <td width="12%"  class="list_head"  align="center">�ͻ���Ʒ״̬</td>
          <td width="10%"  class="list_head"  align="center">��¼״̬</td>
     </tr> 
      
<lgc:bloop requestObjectName="ResponseQueryResult" item="oneline" >
<%
	CPCampaignMapDTO dto = (CPCampaignMapDTO)pageContext.getAttribute("oneline");
	CustomerProductDTO cpDto=Postern.getCustomerProductDTOByPSID(dto.getCustProductID());
	pageContext.setAttribute("cpDto",cpDto);
	String cpName=Postern.getProductNameByPSID(dto.getCustProductID());
	if(cpName=="")cpName="";
%>
    <tbl:printcsstr style1="list_bg1" style2="list_bg2" overStyle="list_over" > 
      	 <td align="center" ><tbl:write name="oneline" property="id"/></td>
      	 <td align="center" ><tbl:write name="oneline" property="custProductID"/></td>      		
      	 <td align="center" ><%=cpName%></td>
      	 <td align="center" ><tbl:write name="cpDto" property="accountID"/></td>      		
      	 <td align="center" ><tbl:write name="cpDto" property="serviceAccountID"/></td>      		
      	 <td align="center" ><d:getcmnname typeName="SET_C_CUSTOMERPRODUCTPSTATUS" match="cpDto:status" /></td>
      	 <td align="center" ><d:getcmnname typeName="SET_G_GENERALSTATUS" match="oneline:status" /></td>
    </tbl:printcsstr>
</lgc:bloop> 

  <tr>
    <td colspan="7" class="list_foot"></td>
  </tr>
</table>
 </rs:hasresultset>
  <input type="hidden" name="txtFrom" >
<input type="hidden" name="txtTo" > 
    
   