<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="resultset" prefix="rs" %>
<%@ taglib uri="/WEB-INF/oss.tld" prefix="d" %>
                 
<%@ page import="com.dtv.oss.util.Postern, java.util.*,java.math.BigDecimal" %>
<%@ page import="com.dtv.oss.web.util.WebUtil" %>
<%@ page import="com.dtv.oss.web.util.CommonKeys" %>
<%@ page import="com.dtv.oss.web.util.WebOperationUtil" %>
<%@ page import="com.dtv.oss.service.commandresponse.QueryCommandResponseImpl"%>
<%@ page import="com.dtv.oss.dto.PrepaymentDeductionRecordDTO"%>

<script language=javascript>
function query_submit(){
	if(checkData())
		document.frmPost.submit();
}

function prepayment_deduction_record_detail(seqNO){
	var strURL="prepayment_deduction_record_detail.do?txtSeqNO="+seqNO+"&txtActionType=detail";
	self.location.href=strURL;
}

function checkData(){
	if (document.frmPost.txtCreateStartDateDatePart.value != ''){
		if (!check_TenDate(document.frmPost.txtCreateStartDateDatePart, true, "��ʼ����")){
			return false;
		}
	}
	 
	if (document.frmPost.txtCreateEndDateDatePart.value != ''){
		if (!check_TenDate(document.frmPost.txtCreateEndDateDatePart, true, "��������")){
			return false;
		}
	}
	 
	if(!compareDate(document.frmPost.txtCreateStartDateDatePart,document.frmPost.txtCreateEndDateDatePart,"�������ڱ�����ڵ��ڿ�ʼ����")){
		
		return false;
	}
 	if(!checkPlainNum(document.frmPost.txtCustID,true,9,"�ͻ�֤��"))
		return false;
	if(!checkPlainNum(document.frmPost.txtAcctID,true,9,"�ʻ���"))
		return false;
	if(!checkPlainNum(document.frmPost.txtReferSheetID,true,9,"�������ݺ�"))
		return false;
		
	return true;
}
</script>

<form name="frmPost" method="post" action="prepayment_deduction_record_query.do">

<input type="hidden" name="txtFrom" size="20" value="1">
<input type="hidden" name="txtTo" size="20" value="10">

<input type="hidden" name="txtActionType" size="20" value="all">

<table  border="0" align="center" cellpadding="0" cellspacing="10" >
  <tr>
    <td><img src="img/list_tit.gif" width="19" height="19"></td>
    <td class="title">Ԥ��ֿۼ�¼��ѯ</td>
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
    <td class="list_bg1" align="left" width="33%">
       <input name="txtCustID" type="text" class="text" maxlength="9" size="25" value="<tbl:writeparam name="txtCustID" />">
    </td>
    <td class="list_bg2" align="right" width="17%">�ʻ���</td>
    <td class="list_bg1" align="left" width="33%">
       <input name="txtAcctID" type="text" class="text" maxlength="9" size="25" value="<tbl:writeparam name="txtAcctID" />">
    </td>
  </tr>
  
  <tr>
     <td class="list_bg2" align="right">��֯����</td>
   		 <td class="list_bg1">
	    	<input type="hidden" name="txtOrgID" value="<tbl:writeparam name="txtOrgID" />">
			<input type="text" name="txtOrgDesc" size="25" maxlength="50" readonly value="<tbl:WriteOrganizationInfo property="txtOrgID" />" class="text">
			<input name="selOrgButton" type="button" class="button" value="ѡ��" onClick="javascript:sel_organization('C,B,O,S','txtOrgID','txtOrgDesc')">
	    </td>	
    <td class="list_bg2" align="right">�ͻ���������</td>
    <td class="list_bg1" align="left">
        <input type="hidden" name="txtDistrictID" value="<tbl:writeparam name="txtDistrictID" />">
	<input type="text" name="txtDistrictIDDesc" size="25" maxlength="50" readonly value="<tbl:WriteDistrictInfo property="txtDistrictID" />" class="text">
        <input name="selDistButton" type="button" class="button" value="ѡ��" onClick="javascript:sel_district('all','txtDistrictID','txtDistrictIDDesc')">
    </td>
  </tr>
  <tr>
  	<td class="list_bg2" align="right">Ԥ������</td>
    <td class="list_bg1" align="left">
    	<d:selcmn name="txtPrePaymentType" mapName="SET_F_PREPAYMENTTYPE"  match="txtPrePaymentType"  width="23" />
    </td>
    <td class="list_bg2" align="right">������Դ</td>
    <td class="list_bg1" align="left" >
       <d:selcmn name="txtCreatingMethod" mapName="SET_F_FTCREATINGMETHOD"  match="txtCreatingMethod"  width="23" />
    </td>
  </tr>
  <tr>
    <td class="list_bg2" align="right">�ֿ۷�ʽ</td>
    <td class="list_bg1" align="left">
       <d:selcmn name="txtReferRecordType" mapName="SET_F_PDR_REFERRECORDTYPE"  match="txtReferRecordType"  width="23" />
    </td>
    <td class="list_bg2" align="right">�ֿ۶���</td>
    <td class="list_bg1" align="left">
       <input name="txtReferRecordID" type="text" class="text" maxlength="10" size="25" value="<tbl:writeparam name="txtReferRecordID" />">
    </td>
  </tr>
  <tr>
    <td class="list_bg2" align="right">������������</td>
    <td class="list_bg1" align="left">
       <d:selcmn name="txtReferSheetType" mapName="SET_F_FTREFERTYPE"  match="txtReferSheetType"  width="23" />
    </td>
    <td class="list_bg2" align="right">�������ݺ�</td>
    <td class="list_bg1" align="left">
       <input name="txtReferSheetID" type="text" class="text" maxlength="9" size="25" value="<tbl:writeparam name="txtReferSheetID" />">
    </td>
  </tr>
  <tr>
    <td class="list_bg2" align="right">����ʱ��</td>
    <td class="list_bg1" colspan="3"> 
            <d:datetime name="txtCreateStartDate" size="10" match="txtCreateStartDate" includeHour="true" onclick="MyCalendar.SetDate(this,document.frmPost.txtCreateStartDateDatePart,'Y')" picURL="img/calendar.gif" style="cursor:hand" />            
             - 
             <d:datetime name="txtCreateEndDate" size="10" match="txtCreateEndDate" includeHour="true" onclick="MyCalendar.SetDate(this,document.frmPost.txtCreateEndDateDatePart,'Y')" picURL="img/calendar.gif" style="cursor:hand" />           
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

<% QueryCommandResponseImpl RepCmd = (QueryCommandResponseImpl)request.getAttribute("ResponseQueryResult");
   BigDecimal fl=(BigDecimal)RepCmd.getExtraPayLoad();
   double totalAmount=0;
   if(fl!=null)
   	totalAmount=fl.doubleValue();
%>

<table width="804"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
  <tr class="list_head">
    <td class="list_head" width="60" nowrap>��ˮ��</td>
    <td class="list_head" width="60" nowrap>����ʱ��</td>
    <td class="list_head" width="60" nowrap>�ͻ�֤��</td>
    <td class="list_head" nowrap>�ͻ�����</td>
    <td class="list_head" width="60" nowrap>�ͻ��ʺ�</td>
    <td class="list_head" width="60" nowrap>Ԥ������</td>
    <td class="list_head" width="60" nowrap>���</td> 
    <td class="list_head" width="60" nowrap>״̬</td>
    <td class="list_head" width="60" nowrap>�ֿ۷�ʽ</td>
    <td class="list_head" width="60" nowrap>�ֿ۶���</td>
    <td class="list_head" width="60" nowrap>������Դ</td>
  </tr>

   
<lgc:bloop requestObjectName="ResponseQueryResult" item="oneline" >
<%
    PrepaymentDeductionRecordDTO dto = (PrepaymentDeductionRecordDTO)pageContext.getAttribute("oneline");
    pageContext.setAttribute("DTO",dto);
        	
    String strCustName=Postern.getCustomerNameByID(dto.getCustId());
    if(strCustName==null)
    	strCustName="";
%>
<tbl:printcsstr style1="list_bg1" style2="list_bg2" overStyle="list_over" >
 
    <td align="center"><a href="javascript:prepayment_deduction_record_detail('<tbl:write name="DTO" property="seqNo"/>')" class="link12" ><tbl:write name="DTO" property="seqNo"/></a></td>
    <td align="center"><tbl:writedate name="DTO" property="createTime"/><br><tbl:writedate name="DTO" property="createTime" onlyTime="true"/></td>
    <td><tbl:write name="DTO" property="custId"/></td>  
    <td><%=strCustName%></td>  
    <td><tbl:write name="DTO" property="acctId"/></td>
    <td align="center"><d:getcmnname typeName="SET_F_PREPAYMENTTYPE" match="DTO:prepaymentType" /></td>
    <td align="center"><tbl:write name="DTO" property="amount"/></td>
    <td align="center"><d:getcmnname typeName="SET_F_FTSTATUS" match="DTO:status" /></td>
    <td align="center"><d:getcmnname typeName="SET_F_PDR_REFERRECORDTYPE" match="DTO:referRecordType" /></td>
    <td align="center"><tbl:write name="DTO" property="referRecordId"/></td>
    <td align="center"><d:getcmnname typeName="SET_F_FTCREATINGMETHOD" match="DTO:creatingMethod" /></td>
</tbl:printcsstr>
</lgc:bloop>

  <tr>
    <tbl:printcsstr style1="list_bg1" style2="list_bg2" overStyle="list_over" >
    	<td colspan="11">�ܼƣ�<%=WebUtil.bigDecimalFormat(totalAmount) %></td>
    </tbl:printcsstr>
  </tr>
  
  <tr>
    <td colspan="11" class="list_foot"></td>
  </tr>

</table>
<table  border="0" align="right" cellpadding="0" cellspacing="11">
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
         

      
