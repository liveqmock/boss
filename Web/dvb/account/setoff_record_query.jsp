<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="resultset" prefix="rs" %>
<%@ taglib uri="/WEB-INF/oss.tld" prefix="d" %>
                 
<%@ page import="com.dtv.oss.util.Postern, java.util.*,java.math.BigDecimal" %>
<%@ page import="com.dtv.oss.web.util.WebUtil" %>
<%@ page import="com.dtv.oss.web.util.CommonKeys" %>
<%@ page import="com.dtv.oss.service.commandresponse.QueryCommandResponseImpl"%>
<%@ page import="com.dtv.oss.dto.FinanceSetOffMapDTO"%>
<%@ page import="com.dtv.oss.dto.wrap.customer.FinanceSetoffMap2AcctItemTypeWrap" %>

<%
    	Map acctItemTypeMap=Postern.getAcctitemtype(null);
    	pageContext.setAttribute("ACCTITEMTYPEMAP_STORE_100",acctItemTypeMap);
%>

<script language=javascript>

function query_submit(){
	if(checkData())
		document.frmPost.submit();
}

function setoff_record_detail(seqNo){
	var strURL="setoff_record_detail.do?txtSeqNo="+seqNo+"&txtActionType=detail";
	self.location.href=strURL;
} 



function displayAllSel(){
	document.frmPost.txtMOPID.style.display="none";
	document.frmPost.txtPrepaymentType.style.display="none";
	document.frmPost.txtPaymentAcctItemType.style.display="none";
	document.frmPost.txtOtherSel.style.display="none";
}

function checkData(){
	if (document.frmPost.txtCreateTime1.value != ''){
		if (!check_TenDate(document.frmPost.txtCreateTime1, true, "��ʼ����")){
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
	
	if (document.frmPost.txtPaymentTime1.value != ''){
		if (!check_TenDate(document.frmPost.txtPaymentTime1, true, "��ʼ����")){
			return false;
		}
	}
	if (document.frmPost.txtPaymentTime2.value != ''){
		if (!check_TenDate(document.frmPost.txtPaymentTime2, true, "��������")){
			return false;
		}
	}
	if(!compareDate(document.frmPost.txtPaymentTime1,document.frmPost.txtPaymentTime2,"�������ڱ�����ڵ��ڿ�ʼ����")){
		
		return false;
	}
	
	if (document.frmPost.txtFeeTime1.value != ''){
		if (!check_TenDate(document.frmPost.txtFeeTime1, true, "��ʼ����")){
			return false;
		}
	}
	if (document.frmPost.txtFeeTime2.value != ''){
		if (!check_TenDate(document.frmPost.txtFeeTime2, true, "��������")){
			return false;
		}
	}
	if(!compareDate(document.frmPost.txtFeeTime1,document.frmPost.txtFeeTime2,"�������ڱ�����ڵ��ڿ�ʼ����")){
		
		return false;
	}
    if(!checkPlainNum(document.frmPost.txtCustID,true,9,"�ͻ�֤��"))
		return false;
	if(!checkPlainNum(document.frmPost.txtAcctID,true,9,"�ʻ���"))
		return false;
    if(!checkPlainNum(document.frmPost.txtPlusReferID,true,9,"֧����¼ID"))
		return false;
	if(!checkPlainNum(document.frmPost.txtMinusReferID,true,9,"���ü�¼ID"))
		return false;
 	
	return true;
}
</script>

<form name="frmPost" method="post" action="setoff_record_query.do">

<input type="hidden" name="txtFrom" size="20" value="1">
<input type="hidden" name="txtTo" size="20" value="10">

<input type="hidden" name="txtActionType" size="20" value="all">

<table  border="0" align="center" cellpadding="0" cellspacing="10" >
  <tr>
    <td><img src="img/list_tit.gif" width="19" height="19"></td>
    <td class="title">���˼�¼��ѯ</td>
  </tr>
</table>

<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>
<br>

<table width="804"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
  <tr>
    <td class="list_bg2" align="right" width="17%">�ͻ�֤��</td>
    <td class="list_bg1" align="left" width="33%"><input name="txtCustID" type="text" class="text" maxlength="9" size="25" value="<tbl:writeparam name="txtCustID" />"></td>
    <td class="list_bg2" align="right" width="17%">�ʻ���</td>
    <td class="list_bg1" align="left" width="33%"><input name="txtAcctID" type="text" class="text" maxlength="9" size="25" value="<tbl:writeparam name="txtAcctID" />"></td>
  </tr> 
  <tr>
     <td class="list_bg2" align="right">��֯����</td>
   		 <td class="list_bg1">
	    	<input type="hidden" name="txtOrgID" value="<tbl:writeparam name="txtOrgID" />">
			<input type="text" name="txtOrgDesc" size="25" maxlength="50" readonly value="<tbl:WriteOrganizationInfo property="txtOrgID" />" class="text">
			<input name="selOrgButton" type="button" class="button" value="ѡ��" onClick="javascript:sel_organization('C,B,O,S','txtOrgID','txtOrgDesc')">
	    </td>		
    <td class="list_bg2" align="right" width="17%"><div align="right">����</td>
    <td class="list_bg1" align="left" width="33%">
      <input type="hidden" name="txtDistrictID" value="<tbl:writeparam name="txtDistrictID" />">
      <input type="text" name="txtDistrictIDDesc" size="25" maxlength="50" readonly value="<tbl:WriteDistrictInfo property="txtDistrictID" />" class="text">
      <input name="selDistButton" type="button" class="button" value="ѡ��" onClick="javascript:sel_district('all','txtDistrictID','txtDistrictIDDesc')">
    </td>
  </tr>
  <tr>
    <td class="list_bg2" align="right">ʵ�չ�������</td>
    <td class="list_bg1" align="left"><d:selcmn name="txtPlusReferType" mapName="SET_F_SETOFFREFERTYPE"  match="txtPlusReferType"  width="23" /></td>
    <td class="list_bg2" align="right">֧����¼ID</td>
    <td class="list_bg1" align="left"><input name="txtPlusReferID" type="text" class="text" maxlength="9" size="25" value="<tbl:writeparam name="txtPlusReferID" />"></td>
  </tr>
  <tr>
    <td class="list_bg2" align="right">��������</td>
    <td class="list_bg1" algin="left">
       <d:selcmn name="txtMinusReferType" mapName="SET_F_SETOFFREFERTYPE"  match="txtMinusReferType"  width="23"  />
    </td>
    <td class="list_bg2" align="right">���ü�¼ID</td>
    <td class="list_bg1" align="left"><input name="txtMinusReferID" type="text" class="text" maxlength="9" size="25" value="<tbl:writeparam name="txtMinusReferID" />"></td>
  </tr>
  <tr>
    <td class="list_bg2" align="right">��Ŀ����</td>
    <td class="list_bg1" align="left"><d:selcmn name="txtAcctItemTypeID" set="ACCTITEMTYPEMAP_STORE_100"  match="txtAcctItemTypeID"  width="23" /></td>
    <td class="list_bg2" align="right">֧��ʱ��</td>
    <td class="list_bg1" align="left">
    	<input onfocus="viewModule(this)" onKeydown="inputDate(document.frmPost.txtPaymentTime1)" onblur="lostFocus(this)" name="txtPaymentTime1" type="text" class="text" maxlength="10" size="10" value="<tbl:writeparam name="txtPaymentTime1" />">    	<IMG onclick="MyCalendar.SetDate(this,document.frmPost.txtPaymentTime1,'Y')" src="img/calendar.gif" style=cursor:hand border="0">
    	---
    	<input onfocus="viewModule(this)" onKeydown="inputDate(document.frmPost.txtPaymentTime2)" onblur="lostFocus(this)" name="txtPaymentTime2" type="text" class="text" maxlength="10" size="10" value="<tbl:writeparam name="txtPaymentTime2" />">    	<IMG onclick="MyCalendar.SetDate(this,document.frmPost.txtPaymentTime2,'Y')" src="img/calendar.gif" style=cursor:hand border="0">
    </td>
  </tr>
  <tr>
    <td class="list_bg2" align="right">���ô���ʱ��</td>
    <td class="list_bg1" align="left">
    	<input onfocus="viewModule(this)" onKeydown="inputDate(document.frmPost.txtFeeTime1)" onblur="lostFocus(this)" name="txtFeeTime1" type="text" class="text" maxlength="10" size="10" value="<tbl:writeparam name="txtFeeTime1" />">    	<IMG onclick="MyCalendar.SetDate(this,document.frmPost.txtFeeTime1,'Y')" src="img/calendar.gif" style=cursor:hand border="0">
    	---
    	<input onfocus="viewModule(this)" onKeydown="inputDate(document.frmPost.txtFeeTime2)" onblur="lostFocus(this)" name="txtFeeTime2" type="text" class="text" maxlength="10" size="10" value="<tbl:writeparam name="txtFeeTime2" />">    	<IMG onclick="MyCalendar.SetDate(this,document.frmPost.txtFeeTime2,'Y')" src="img/calendar.gif" style=cursor:hand border="0">
    </td>
    <td class="list_bg2" align="right">����ʱ��</div></td>
    <td class="list_bg1" align="left">
    	<input onfocus="viewModule(this)" onKeydown="inputDate(document.frmPost.txtCreateTime1)" onblur="lostFocus(this)" name="txtCreateTime1" type="text" class="text" maxlength="10" size="10" value="<tbl:writeparam name="txtCreateTime1" />">    	<IMG onclick="MyCalendar.SetDate(this,document.frmPost.txtCreateTime1,'Y')" src="img/calendar.gif" style=cursor:hand border="0">
    	---
    	<input onfocus="viewModule(this)" onKeydown="inputDate(document.frmPost.txtCreateTime2)" onblur="lostFocus(this)" name="txtCreateTime2" type="text" class="text" maxlength="10" size="10" value="<tbl:writeparam name="txtCreateTime2" />">    	<IMG onclick="MyCalendar.SetDate(this,document.frmPost.txtCreateTime2,'Y')" src="img/calendar.gif" style=cursor:hand border="0">
    </td>
  </tr>
</table>

<table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
	<tr align="center">
	  <td class="list_bg1"><table  border="0" cellspacing="0" cellpadding="0">
		  <tr>
			<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
			<td><input name="Submit" type="button" class="button" value="��&nbsp;ѯ" onclick="javascript:query_submit()"></td>
			<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
		  </tr>
	  </table></td>
	</tr>
</table> 

<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>
<br>

<rs:hasresultset>

<table width="804"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
  <tr class="list_head">
    <td class="list_head" width="80" nowrap>���</td>
    <td class="list_head" width="80" nowrap>�ͻ�֤��</td>
    <td class="list_head" width="80" nowrap>�ͻ�����</td>
    <td class="list_head" width="60" nowrap>�ʺ�</td>
    <td class="list_head" width="60" nowrap>���</td>
    <td class="list_head" width="50" nowrap>����Ա</td>
    <td class="list_head" width="50" nowrap>״̬</td>
    <td class="list_head" width="80" nowrap>����ʱ��</td>
    <td class="list_head" width="50" nowrap>ʵ�չ�������</td>
    <td class="list_head" width="50" nowrap>֧����¼ID</td>
    <td class="list_head" width="50" nowrap>��������</td>
    <td class="list_head" width="40" nowrap>���ü�¼ID</td>
    <td class="list_head" width="70" nowrap>��Ŀ����</td> 
  </tr>


<% QueryCommandResponseImpl RepCmd = (QueryCommandResponseImpl)request.getAttribute("ResponseQueryResult");
   BigDecimal fl=(BigDecimal)RepCmd.getExtraPayLoad();
   double totalAmount=0;
   if(fl!=null)
   	totalAmount=fl.doubleValue();
%>
   
<lgc:bloop requestObjectName="ResponseQueryResult" item="oneline" >
<%
    FinanceSetoffMap2AcctItemTypeWrap dto = (FinanceSetoffMap2AcctItemTypeWrap)pageContext.getAttribute("oneline");
    FinanceSetOffMapDTO setoffDTO=dto.getSetOffDTO();
    pageContext.setAttribute("DTO",setoffDTO);
    pageContext.setAttribute("ALLDTO",dto);
    
    String strOpName=Postern.getOperatorNameByID(setoffDTO.getOpId());
    if(strOpName==null)
    	strOpName="";
    
    String strCustName=Postern.getCustomerNameByID(setoffDTO.getCustId());
    if(strCustName==null)
    	strCustName="";
%>
<tbl:printcsstr style1="list_bg1" style2="list_bg2" overStyle="list_over" >
 
    <td><a href="javascript:setoff_record_detail('<tbl:write name="DTO" property="seqNo"/>')" class="link12" ><tbl:write name="DTO" property="seqNo"/></a></td>
    <td><tbl:write name="DTO" property="custId"/></td>
    <td><%=strCustName%></td> 
    <td><tbl:write name="DTO" property="acctId"/></td> 
    <td><tbl:write name="DTO" property="value"/></td>
    <td><%=strOpName%></td>
    <td><d:getcmnname typeName="SET_F_FTSTATUS" match="DTO:status" /></td>
    <td><tbl:writedate name="DTO" property="createTime"/><br><tbl:writedate name="DTO" property="createTime" onlyTime="true"/></td>
    <td><d:getcmnname typeName="SET_F_SETOFFREFERTYPE" match="DTO:plusReferType" /></td>
    <td><tbl:write name="DTO" property="plusReferId"/></td>
    <td><d:getcmnname typeName="SET_F_SETOFFREFERTYPE" match="DTO:minusReferType" /></td>
    <td><tbl:write name="DTO" property="minusReferId"/></td>
   
    <% if(dto.getFeeAcctItemType()==null || "".equals(dto.getFeeAcctItemType())) {
    %>
    	<td><d:getcmnname typeName="<%=dto.getFeeCommonSettingName()%>" match="ALLDTO:feeCommonSettingValue" /></td>
    <%} else {%>
    	<td><tbl:write name="ALLDTO" property="feeAcctItemType" /></td>
    <%}%>
     
</tbl:printcsstr>
</lgc:bloop>

  <tr>
    <tbl:printcsstr style1="list_bg1" style2="list_bg2" overStyle="list_over" >
    	<td colspan="13">�ܼƣ�<%=WebUtil.bigDecimalFormat(totalAmount) %></td>
    </tbl:printcsstr>
  </tr>
  
  <tr>
    <td colspan="13" class="list_foot"></td>
  </tr>

</table>
<table  border="0" align="right" cellpadding="0" cellspacing="13">
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
    <td><input name="imageField" type="image" src="img/button_go.gif" width="28" height="15" border="0" onclick="javascript:goto_onClick(document.frmPost, <rs:prop property="pageamount" />)"></td>
  </tr>
  </table>
  
 </rs:hasresultset>                 
<BR>

</form>  
         

      