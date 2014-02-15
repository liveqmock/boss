<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="resultset" prefix="rs" %>
<%@ taglib uri="/WEB-INF/oss.tld" prefix="d" %>

<%@ page import="com.dtv.oss.util.Postern,
                 java.util.HashMap,
                 java.util.Map,
                 java.util.Iterator" %>
<%@ page import="com.dtv.oss.dto.BankDeductionDetailDTO" %>
<%@ page import="com.dtv.oss.web.util.WebOperationUtil" %>
<%@ page import="java.sql.Timestamp" %>
<%@ page import="com.dtv.oss.web.util.CommonKeys" %>

<script language=javascript>
function query_submit()
{
	if (document.frmPost.txtCreateTime1.value != ''){
		if (!check_TenDate(document.frmPost.txtCreateTime1, true, "��������")){
			return false;
		}
	}
	if (document.frmPost.txtCreateTime2.value != ''){
		if (!check_TenDate(document.frmPost.txtCreateTime2, true, "��������")){
			return false;
		}
	}
	if(!compareDate(document.frmPost.txtCreateTime1,document.frmPost.txtCreateTime2,"�������ڱ�����ڵ��ڿ�ʼ����")){
		return false;
	}
	document.frmPost.submit();
}
function view_detail_click(seqNo)
{
	self.location.href="bank_deduction_detail_view.do?txtActionType=detail&txtSeqNo="+seqNo;
}
</script>	
<%
 	pageContext.setAttribute("BILLINGCYCLE",Postern.getAllAccountCycle());
	pageContext.setAttribute("AllMOPList" ,Postern.getMethodBankDeductionOfPaymentMap());
%>
<table  border="0" align="center" cellpadding="0" cellspacing="10" >
  <tr>
    <td><img src="img/list_tit.gif" width="19" height="19"></td>
    <td class="title">����������ϸ��¼��ѯ</td>
  </tr>
</table>
<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>
<br>

<form name="frmPost" action="bank_deduction_detail_query.do" method="post" >
<table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
  	<tr>
    	<td class="list_bg2"><div align="right">��������</div></td>
    	<td class="list_bg1">
    		<input name="txtBatchNo" type="text" class="text" maxlength="9" size="25" value="<tbl:writeparam name="txtBatchNo" />"></td>
    	<td class="list_bg2"><div align="right">����״̬</div></td>       
    	<td class="list_bg1">
    		<d:selcmn name="txtProcessStatus" mapName="SET_I_BANKDEDUCTIONDETAILSTATUS" match="txtProcessStatus" width="23" />
    	</td>
  	</tr>
  	<tr>
  		<td  class="list_bg2"><div align="right">֧����ʽ</div></td>
		<td class="list_bg1">
			<tbl:select name="txtMopID" set="AllMOPList" match="txtMopID" width="23" />
    	<td  class="list_bg2"><div align="right">����ʱ��</div></td>
		<td class="list_bg1">
            <input maxlength="10" onfocus="viewModule(this)" onKeydown="inputDate(document.frmPost.txtCreateTime1)" onblur="lostFocus(this)" type="text" name="txtCreateTime1" size="10" value="<tbl:writeparam name="txtCreateTime1"/>" >&nbsp;<IMG onclick="MyCalendar.SetDate(this,document.frmPost.txtCreateTime1,'Y')" src="img/calendar.gif" style=cursor:hand border="0">
            - 
            <input maxlength="10" onfocus="viewModule(this)" onKeydown="inputDate(document.frmPost.txtCreateTime2)" onblur="lostFocus(this)" type="text" name="txtCreateTime2" size="10" value="<tbl:writeparam name="txtCreateTime2"/>" >&nbsp;<IMG onclick="MyCalendar.SetDate(this,document.frmPost.txtCreateTime2,'Y')" src="img/calendar.gif" style=cursor:hand border="0">
        </td>
  	</tr>
	<tr>
		<td class="list_bg2"><div align="right">��������</div></td>
	  	<td class="list_bg1">
	  		<tbl:select name="txtBillingCycleID" set="BILLINGCYCLE"  match="txtBillingCycleID" width="23" />
		</td>
		<td class="list_bg2"><div align="right">�ͻ�֤��</div></td>
	  	<td class="list_bg1">
	  		<input name="txtCustomerID" type="text" class="text" maxlength="9" size="25" value="<tbl:writeparam name="txtCustomerID" />"></td>
		</td>
	</tr>
	<tr>
		<td class="list_bg2"><div align="right">�ͻ�����</div></td>
    	<td class="list_bg1">
    		<d:selcmn name="txtCustomerType" mapName="SET_C_CUSTOMERTYPE" match="txtCustomerType" width="23" /></td>
    	<td class="list_bg2" align="right">�ͻ���������</td>
    	<td class="list_bg1">
    	<input type="hidden" name="txtCustDistrictID" value="<tbl:writeparam name="txtCustDistrictID" />">
	    <input type="text" name="txtDistrictDesc" size="22" maxlength="50" readonly value="<tbl:WriteDistrictInfo property="txtCustDistrictID" />" class="text">
	    <input name="selDistButton" type="button" class="button" value="ѡ��" onClick="javascript:sel_district('all','txtCustDistrictID','txtDistrictDesc')">
    </td>
</table>

<table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
	<tr align="center">
	  <td class="list_bg1"><table  border="0" cellspacing="0" cellpadding="0">
	  	<pri:authorized name="bank_deduction_detail_query.do">
		  <tr>
			<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
			<td><input name="Submit" type="button" class="button" value="�� ѯ" onclick="javascript:query_submit()"></td>
			<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
		  </tr>
		</pri:authorized>
	  </table></td>
	</tr>
</table>
	<input type="hidden" name="txtFrom" size="22" value="1">
	<input type="hidden" name="txtTo" size="22" value="10">
	<input type="hidden" name="txtActionType" value="detail">

<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>
<br>

<rs:hasresultset>
<table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
  <tr class="list_head">
  	<td class="list_head">��ˮ��</td>
    <td class="list_head">����</td>
    <td class="list_head">�ͻ�֤��</td>
    <td class="list_head">�ͻ�����</td>
    <td class="list_head">�����ʺ�</td>
    <td class="list_head">�����ʺ���</td>
    <td class="list_head">֧����ʽ</td>
    <td class="list_head">�˵���</td>
    <td class="list_head">���</td>
    <td class="list_head">��������</td>
    <td class="list_head">��������</td>
    <td class="list_head">����״̬</td>
  </tr>
	<lgc:bloop requestObjectName="ResponseQueryResult" item="oneline" >
<%
					BankDeductionDetailDTO dto = (BankDeductionDetailDTO) pageContext.getAttribute("oneline");
					String referInvoiceNo = Postern.getInvoiceIdBySeqNo(dto.getSeqNo());
					String mopName = Postern.getNameByMopIDForPay(Integer.parseInt(Postern.getMopIdByBatchNo(dto.getBatchNo())));
					//����
					String BillingCycle = Postern.getBillingCycleNameByID(Integer.parseInt(Postern.getBillingCycleByBatchNo(dto.getBatchNo())));
					String customerName = Postern.getCustomerNameById(dto.getCustomerId());
%>
	<tbl:printcsstr style1="list_bg1" style2="list_bg2" overStyle="list_over" > 
	<td align="center"><a href="javascript:view_detail_click('<tbl:write name="oneline" property="seqNo"/>')" class="link12" ><tbl:write name="oneline" property="seqNo" /></a></td>
    <td align="center"><tbl:write name="oneline" property="batchNo" /></td>
    <td align="center"><tbl:write name="oneline" property="customerId" /></td>
    <td align="center"><%=customerName%></td>
    <td align="center"><tbl:write name="oneline" property="bankAccountId"/></td>
    <td align="center"><tbl:write name="oneline" property="bankAccountName"/></td>
    <td align="center"><%=mopName%></td>
    <td align="center"><%=referInvoiceNo%></td>
    <td align="right"><tbl:write name="oneline" property="amount"/></td>
    <td align="center" width="10%"><tbl:writedate name="oneline" property="dtCreate" includeTime="true"/></td>
    <td align="center" width="10%"><tbl:writedate name="oneline" property="returnTime" includeTime="true"/></td>
    <td align="center"><d:getcmnname typeName="SET_I_BANKDEDUCTIONDETAILSTATUS" match="oneline:status"/></td>
  </tr>
  
	</tbl:printcsstr>
	</lgc:bloop>
	<tbl:generatetoken />

  <tr>
    <td colspan="12" class="list_foot"></td>
  </tr>
  </table> 
  
  <table  border="0" align="right" cellpadding="0" cellspacing="8">
  <tr>
    <td>��<span class="en_8pt"><rs:prop property="curpageno" /></span>ҳ<span class="en_8pt">/</span>��<span class="en_8pt"><rs:prop property="pageamount" /><span>ҳ</td>
    <td>��<span class="en_8pt"><rs:prop property="recordcount" /></span>����¼</td>
<rs:notfirst>
    <td align="right"><img src="img/dot_top.gif" width="17" height="11"></td>
    <td><a href="javascript:firstPage_onClick(document.frmPost, <rs:prop property="firstto" />)" class="link12">��ҳ</a></td>
    <td align="right"><img src="img/dot_pre.gif" width="6" height="11"></td>
    <td><a href="javascript:previous_onClick(document.frmPost, <rs:prop property="prefrom" />, <rs:prop property="preto" />)" class="link12">��һҳ</a></td>
</rs:notfirst>
<rs:notlast>
    <td align="right"><img src="img/dot_next.gif" width="6" height="11"></td>
    <td><a href="javascript:next_onClick(document.frmPost, <rs:prop property="nextfrom" />, <rs:prop property="nextto" />)" class="link12">��һҳ</a></td>
    <td align="right"><img src="img/dot_end.gif" width="17" height="11"></td>
    <td><a href="javascript:lastPage_onClick(document.frmPost, <rs:prop property="lastfrom" />, <rs:prop property="lastto" />)" class="link12">ĩҳ</a></td>
</rs:notlast>
    <td align="right">��ת��</td>
    <td><input name="txtPage" type="text" class="page_txt"></td>
    <td>ҳ</td>
    <td><input name="imageField" type="image" src="img/button_go.gif" width="28" height="15" border="0" onclick="return goto_onClick(document.frmPost, <rs:prop property="pageamount" />)"/></td>
  </tr>
</table>
            

	</rs:hasresultset>
	</form>               
</td>
</tr>
</table>