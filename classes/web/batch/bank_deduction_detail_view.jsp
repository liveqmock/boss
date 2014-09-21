<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="resultset" prefix="rs" %>
<%@ taglib uri="/WEB-INF/oss.tld" prefix="d" %>
<%@ taglib uri="bookmark" prefix="bk" %>
<%@ page import="com.dtv.oss.util.Postern,
                 java.util.HashMap,
                 java.util.Map,
                 java.util.Iterator" %>
<%@ page import="com.dtv.oss.dto.BankDeductionDetailDTO" %>
<%@ page import="com.dtv.oss.web.util.WebOperationUtil" %>
<%@ page import="java.sql.Timestamp" %>
<%@ page import="com.dtv.oss.web.util.CommonKeys" %>

<table  border="0" align="center" cellpadding="0" cellspacing="10" >
  <tr>
    <td><img src="img/list_tit.gif" width="19" height="19"></td>
    <td class="title">�鿴����������ϸ��¼��Ϣ</td>
  </tr>
</table>
<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>
<br>

<form name="frmPost" action="bank_deduction_header_query.do" method="post" >
<table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
	<lgc:bloop requestObjectName="ResponseQueryResult" item="oneline" >
<%
					BankDeductionDetailDTO dto = (BankDeductionDetailDTO) pageContext.getAttribute("oneline");
					String referInvoiceNo = Postern.getInvoiceIdBySeqNo(dto.getSeqNo());
%>
  	<tr>
    	<td class="list_bg2"><div align="right">��������</div></td>
    	<td class="list_bg1">
    	<tbl:write name="oneline" property="batchNo" /></td>
    	<td class="list_bg2"><div align="right">��ˮ��</div></td>
    	<td class="list_bg1">
    	<tbl:write name="oneline" property="batchNo" /></td>
  	</tr>
  	<tr>
    	<td class="list_bg2"><div align="right">�ͻ�֤��</div></td>
    	<td class="list_bg1">
    	<tbl:write name="oneline" property="customerId" /></td>
    	<td class="list_bg2"><div align="right">�ͻ�����</div></td>
    	<td class="list_bg1">
    	<%=Postern.getCustomerNameByID(dto.getCustomerId())%></td>
  	</tr>
  	<tr>
  		<td class="list_bg2"><div align="right">��������</div></td>
    	<td class="list_bg1"><tbl:writedate name="oneline" property="dtCreate" includeTime="true"/></td>
    	<td class="list_bg2"><div align="right">�˵���</div></td>
    	<td class="list_bg1"><%=referInvoiceNo%></td>
    </tr>	
    <tr>
  		<td class="list_bg2"><div align="right">�ͻ��ʺ�</div></td>
    	<td class="list_bg1"><tbl:write name="oneline" property="accountID"/></td>
    	<td class="list_bg2"><div align="right">���</div></td>
    	<td class="list_bg1"><tbl:write name="oneline" property="amount"/></td>
    </tr>
    <tr>
  		<td class="list_bg2"><div align="right">�����ʻ�����</div></td>
    	<td class="list_bg1"><tbl:write name="oneline" property="bankAccountName"/></td>
    	<td class="list_bg2"><div align="right">�����ʺ�</div></td>
    	<td class="list_bg1"><tbl:write name="oneline" property="bankAccountId"/></td>
    </tr>
    <tr>
    	<td class="list_bg2"><div align="right">��������</div></td>
    	<td class="list_bg1"><tbl:writedate name="oneline" property="returnTime" includeTime="true"/></td>
    	<td class="list_bg2"><div align="right">����״̬</div></td>
    	<td class="list_bg1">
			<d:getcmnname typeName="SET_I_BANKDEDUCTIONDETAILRETURNSTATUS" match="oneline:returnStatus"/>
    	</td>
    </tr>	
  	<tr>
  		<td class="list_bg2"><div align="right">���ش���</div></td>
	  	<td class="list_bg1">
			<d:getcmnname typeName="SET_I_BANKDEDUCTIONDETAILRETURNCODE" match="oneline:returnReasonCode"/>
		</td>
    	<td  class="list_bg2"><div align="right">����״̬</div></td>
		<td class="list_bg1">
            <d:getcmnname typeName="SET_I_BANKDEDUCTIONDETAILSTATUS" match="oneline:status"/>
        </td>
  	</tr>
	</lgc:bloop>
  
 </table>
 
 <table width="100%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>

<BR>

<table border="0" cellspacing="0" cellpadding="0">
  <tr>
  	<td><img src="img/button2_r.gif" width="22" height="20"></td>  
        <td background="img/button_bg.gif">
      	<a href="<bk:backurl property="bank_deduction_detail_query.do" />" class="btn12">��&nbsp;��</a></td>
      	<td><img src="img/button2_l.gif" width="13" height="20"></td>
</tr>
</table>     
 
 </form>