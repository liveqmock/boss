<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="resultset" prefix="rs" %>
<%@ taglib uri="/WEB-INF/oss.tld" prefix="d" %>
<%@ taglib uri="bookmark" prefix="bk" %>
<%@ page import="com.dtv.oss.util.Postern,
                 java.util.HashMap,
                 java.util.Map,
                 java.util.Iterator" %>
<%@ page import="com.dtv.oss.dto.BankDeductionHeaderDTO" %>
<%@ page import="com.dtv.oss.web.util.WebOperationUtil" %>
<%@ page import="java.sql.Timestamp" %>
<%@ page import="com.dtv.oss.web.util.CommonKeys" %>

<script language=javascript>
function query_submit()
{
	if(!compareDate(document.frmPost.txtDtCreate1,document.frmPost.txtCreateTime2,"�������ڱ�����ڵ��ڿ�ʼ����")){
		return false;
	}
	document.frmPost.submit();
}

</script>	
<%
 	pageContext.setAttribute("BILLINGCYCLE",Postern.getAllAccountCycle());
	pageContext.setAttribute("AllMOPList" ,Postern.getMethodBankDeductionOfPaymentMap());
	pageContext.setAttribute("AllAccountCycleType" ,Postern.getAllAccountCycleType());
%>
<table  border="0" align="center" cellpadding="0" cellspacing="10" >
  <tr>
    <td><img src="img/list_tit.gif" width="19" height="19"></td>
    <td class="title">�鿴�������ռ�¼��Ϣ</td>
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
	BankDeductionHeaderDTO dto = (BankDeductionHeaderDTO) pageContext.getAttribute("oneline");
	
	String strOpName=Postern.getOperatorNameByID(dto.getCreatOpId());
       if(strOpName==null)
    	   strOpName="";
       	   
       String strOrgName=Postern.getOrganizationDesc(dto.getCreateOrgId());
       if(strOrgName==null)
       	   strOrgName="";
%>       	   
  	<tr>
    	<td class="list_bg2"><div align="right">��������</div></td>
    	<td class="list_bg1">
    	<tbl:write name="oneline" property="batchNo" /></td>
    	<td  class="list_bg2"><div align="right">֧����ʽ</div></td>
		<td class="list_bg1">
		<d:getcmnname typeName="AllMOPList" match="oneline:mopId"/></td>
  	</tr>
  	<tr>
		<td class="list_bg2"><div align="right">�����̺�</div></td>
    	<td class="list_bg1" colspan="3" ><tbl:write name="oneline" property="exchangeId"/></td>
	</tr>
  	<tr>
  		<td class="list_bg2"><div align="right">��������</div></td>
	  	<td class="list_bg1">
	  		<d:getcmnname typeName="BILLINGCYCLE" match="oneline:billingCycle"/>
		</td>
    	<td  class="list_bg2"><div align="right">������������</div></td>
		<td class="list_bg1">
            <d:getcmnname typeName="AllAccountCycleType" match="oneline:billingCycleType"/>
        </td>
  	</tr>
  	<tr>
  		<td class="list_bg2"><div align="right">������</div></td>
    	<td class="list_bg1"><%=strOpName%></td>
    	<td class="list_bg2"><div align="right">��������</div></td>
    	<td class="list_bg1"><%=strOrgName%></td>
  	</tr>
	<tr>
		<td class="list_bg2"><div align="right">��������</div></td>
    	<td class="list_bg1"><tbl:writedate name="oneline" property="dtCreate"/></td>
		<td class="list_bg2"><div align="right">����״̬</div></td>
    	<td class="list_bg1">
    		<d:getcmnname typeName="SET_I_BANKDEDUCTIONHEADERSTATUS" match="oneline:processStatus"/>
    	</td>
	</tr>
	<tr>
		<td class="list_bg2"><div align="right">�����ܱ���</div></td>
    	<td class="list_bg1"><tbl:write name="oneline" property="totalRecordNo"/></td>
    	<td class="list_bg2"><div align="right">�����ܽ��</div></td>
    	<td class="list_bg1"><tbl:write name="oneline" property="totalAmount"/></td>
	</tr>
	<tr>
		<td class="list_bg2"><div align="right">�ɹ��ܱ���</div></td>
    	<td class="list_bg1"><tbl:write name="oneline" property="sucessRecordNo"/></td>
    	<td class="list_bg2"><div align="right">�ɹ��ܽ��</div></td>
    	<td class="list_bg1"><tbl:write name="oneline" property="sucessAmount"/></td>
	</tr>
	<tr>
		<td class="list_bg2"><div align="right">ʧ���ܱ���</div></td>
    	<td class="list_bg1"><tbl:write name="oneline" property="failedRecordNo"/></td>
    	<td class="list_bg2"><div align="right">ʧ���ܽ��</div></td>
    	<td class="list_bg1"><tbl:write name="oneline" property="failedAmount"/></td>
	</tr>
	<tr>
		<td class="list_bg2"><div align="right">��Ч�ܱ���</div></td>
    	<td class="list_bg1"><tbl:write name="oneline" property="invalidRecordNo"/></td>
    	<td class="list_bg2"><div align="right">��������</div></td>
    	<td class="list_bg1"><tbl:writedate name="oneline" property="bankDealDate"/></td>
	</tr>
	<tr>
		<td class="list_bg2"><div align="right">�����ļ�</div></td>
    	<td class="list_bg1"><tbl:write name="oneline" property="exportFileName"/></td>
    	<td class="list_bg2"><div align="right">�ɹ��ļ�</div></td>
    	<td class="list_bg1"><tbl:write name="oneline" property="sucessFileName"/></td>
	</tr>
	<tr>
		<td class="list_bg2"><div align="right">ʧ���ļ�</div></td>
    	<td class="list_bg1"><tbl:write name="oneline" property="failedFileName"/></td>
    	<td class="list_bg2"><div align="right">�����ļ�</div></td>
    	<td class="list_bg1"><tbl:write name="oneline" property="returnBackFileName"/></td>
	</tr>
	<tr>
		<td class="list_bg2"><div align="right">��������</div></td>
    	<td class="list_bg1"><tbl:write name="oneline" property="czTotalCount"/></td>
    	<td class="list_bg2"><div align="right">�������</div></td>
    	<td class="list_bg1"><tbl:write name="oneline" property="czTotalValue"/></td>
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
      	<a href="<bk:backurl property="bank_deduction_header_query.do" />" class="btn12">��&nbsp;��</a></td>
      	<td><img src="img/button2_l.gif" width="13" height="20"></td>
</tr>
</table>     
 
 </form>