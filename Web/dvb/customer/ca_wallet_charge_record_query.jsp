<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="osstags" prefix="d" %>
<%@ taglib uri="bookmark" prefix="bk" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="resultset" prefix="rs" %>
<%@ taglib uri="privilege" prefix="pri" %>

<%@ page import="com.dtv.oss.util.Postern,
                 java.util.HashMap,
                 java.util.Map,java.math.BigDecimal,
                 java.util.Iterator" %>
<%@ page import="com.dtv.oss.dto.CAWalletChargeRecordDTO" %>
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

</script>	


<%
	pageContext.setAttribute("AllMOPList" ,Postern.getOpeningPaymentMop("CAC"));
	pageContext.setAttribute("SelectAllMOPList" ,Postern.getOpeningPaymentNoWrapMop());
	
%>
<table  border="0" align="center" cellpadding="0" cellspacing="10" >
  <tr>
    <td><img src="img/list_tit.gif" width="19" height="19"></td>
    <td class="title">СǮ����ֵ��¼��ѯ</td>
  </tr>
</table>
<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>
<br>

<form name="frmPost" action="ca_wallet_charge_record_query.do" method="post" >
<input type="hidden" name="txtCustomerID" value="<tbl:writeparam name="txtCustomerID" />">
<input type="hidden" name="txtStatus" value="<tbl:writeparam name="txtStatus" />">
<input type="hidden" name="txtDtLastmod" value="<tbl:writeparam name="txtDtLastmod" />">
<table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
  	<tr>
    	<td class="list_bg2"><div align="right">ҵ���ʻ�ID</div></td>
    	<td class="list_bg1">
    	<input name="txtServiceAccountId"  readonly class="textgray" size="25" maxlength="10" value="<tbl:writeparam name="txtServiceAccountId" />"></td>
    	<td class="list_bg2"><div align="right">ҵ������</div></td>
    	<td class="list_bg1">
    	<input name="txtServiceAccountName"  readonly class="textgray" size="25" maxlength="10" value="<tbl:writeparam name="txtServiceAccountName" />"></td>
    </tr>
  	<tr>
    	<td class="list_bg2"><div align="right">СǮ�����</div></td>
    	<td class="list_bg1">
    	<input name="txtWalletId"  readonly class="textgray" size="25" maxlength="10" value="<tbl:writeparam name="txtWalletId" />"></td>
    	<td class="list_bg2"><div align="right">СǮ������</div></td>
    	<td class="list_bg1">
    	<input name="txtCaWalletName"  readonly class="textgray" size="25" maxlength="10" value="<tbl:writeparam name="txtCaWalletName" />"></td>
    </tr>
  	<tr>
  		<td class="list_bg2"><div align="right">�ѳ�ֵ����</div></td>
    	<td class="list_bg1">
    	<input name="txtTotalPoint"  readonly class="textgray" size="25" maxlength="10" value="<tbl:writeparam name="txtTotalPoint" />"></td>
    	<td class="list_bg2"><div align="right">״̬</div></td>
    	<td class="list_bg1">
    	<input name="status"  readonly class="textgray" size="25" maxlength="10" value="<tbl:writeparam name="status" />"></td>
    	</td>
  	</tr>
  	<tr>
  		<td  class="list_bg2"><div align="right">֧����ʽ</div></td>
		<td class="list_bg1">
		<tbl:select name="txtMopId" set="AllMOPList" match="txtMopId" width="23" />
    	<td  class="list_bg2"><div align="right">��ֵ����</div></td>
		<td class="list_bg1">
            <input maxlength="10" onfocus="viewModule(this)" onKeydown="inputDate(document.frmPost.txtCreateTime1)" onblur="lostFocus(this)" type="text" name="txtCreateTime1" size="10" value="<tbl:writeparam name="txtCreateTime1"/>" >&nbsp;<IMG onclick="MyCalendar.SetDate(this,document.frmPost.txtCreateTime1,'Y')" src="img/calendar.gif" style=cursor:hand border="0">
            - 
            <input maxlength="10" onfocus="viewModule(this)" onKeydown="inputDate(document.frmPost.txtCreateTime2)" onblur="lostFocus(this)" type="text" name="txtCreateTime2" size="10" value="<tbl:writeparam name="txtCreateTime2"/>" >&nbsp;<IMG onclick="MyCalendar.SetDate(this,document.frmPost.txtCreateTime2,'Y')" src="img/calendar.gif" style=cursor:hand border="0">
        </td>
  	</tr>
	<tr>
		<td class="list_bg2"><div align="right">�豸���к�</div></td>
	  	<td class="list_bg1" colspan="3">
	  	<input name="txtScSerialNo" type="text" class="text" maxlength="9" size="25" value="<tbl:writeparam name="txtScSerialNo"/>" ></td>
	</tr>
</table>

<table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
	<tr align="center">
	  <td class="list_bg1"><table  border="0" cellspacing="0" cellpadding="0">
		  <!--<pri:authorized name="bank_deduction_header_query.do">-->
		  <tr>
			<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
			<td><input name="Submit" type="button" class="button" value="�� ѯ" onclick="javascript:query_submit()"></td>
			<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
		  </tr>
		  <!--</pri:authorized>-->
	  </table></td>
	</tr>
</table>
	<input type="hidden" name="txtFrom" size="22" value="1">
	<input type="hidden" name="txtTo" size="22" value="10">
	<input type="hidden" name="txtActionType" value="wallet_charge">

<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>
<br>

<rs:hasresultset>
<table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
  <tr class="list_head">
    <td class="list_head">ID</td>
    <td class="list_head">����</td>
    <td class="list_head">��ֵʱ��</td>
    <td class="list_head">����Ա</td>
    <td class="list_head">��֯</td>
    <td class="list_head">֧����ʽ</td>
    <td class="list_head">��ֵ����</td>
    <td class="list_head">��ֵ���</td>
    <td class="list_head">�豸���к�</td>
    <td class="list_head">״̬</td>
  </tr>
	<lgc:bloop requestObjectName="ResponseQueryResult" item="oneline" >
<%
					CAWalletChargeRecordDTO dto = (CAWalletChargeRecordDTO) pageContext.getAttribute("oneline");
					String oprName =Postern.getOperatorNameByID(dto.getOpId());
        			if (oprName == null) oprName = "";
        			String orgName =Postern.getOrganizationDesc(dto.getOrgId());
        			if (orgName == null) orgName = "";
        			String strUrl="service_interaction_view.do?txtID="+dto.getCsiId();
%>
	<tbl:printcsstr style1="list_bg1" style2="list_bg2" overStyle="list_over">
    <td align="center"><tbl:write name="oneline" property="seqNo"/></td>
    <td align="center"><tbl:write name="oneline" property="csiId"/></td>
    <td align="center" width="10%"><tbl:writedate name="oneline" property="createTime" includeTime="true" /></td>
    <td align="center"><%=oprName%></td>
    <td align="center"><%=orgName%></td>
    <td align="center"><d:getcmnname typeName="SelectAllMOPList" match="oneline:mopId"/></td>
	<td align="right"><tbl:write name="oneline" property="point"/></td>
    <td align="right"><tbl:write name="oneline" property="value"/></td>
    <td align="center"><tbl:write name="oneline" property="scSerialNo"/></td>
    <td align="center"><d:getcmnname typeName="SET_G_GENERALSTATUS" match="oneline:status"/></td>
  </tr>
  
	</tbl:printcsstr>
	</lgc:bloop>
	<tbl:generatetoken />

  <tr>
    <td colspan="10" class="list_foot"></td>
  </tr>
  
            <tr>
              <td align="right" class="t12" colspan="10" >
                 ��<span class="en_8pt"><rs:prop property="curpageno" /></span>ҳ
                 <span class="en_8pt">/</span>��<span class="en_8pt"><rs:prop property="pageamount" />ҳ
                 ����<span class="en_8pt"><rs:prop property="recordcount" /></span>����¼&nbsp;&nbsp;&nbsp;&nbsp;             
                 <rs:notfirst>
                   <img src="img/dot_top.gif" width="17" height="11">
                   <a href="javascript:firstPage_onClick(document.frmPost, <rs:prop property="firstto" />)" >��ҳ </a>
                   <img src="img/dot_pre.gif" width="6" height="11">
                   <a href="javascript:previous_onClick(document.frmPost, <rs:prop property="prefrom" />, <rs:prop property="preto" />)" >��һҳ</a>
                 </rs:notfirst>
          
                <rs:notlast>
                 &nbsp;
                 <img src="img/dot_next.gif" width="6" height="11">
                 <a href="javascript:next_onClick(document.frmPost, <rs:prop property="nextfrom" />, <rs:prop property="nextto" />)" >��һҳ</a>
                 <img src="img/dot_end.gif" width="17" height="11">
                 <a href="javascript:lastPage_onClick(document.frmPost, <rs:prop property="lastfrom" />, <rs:prop property="lastto" />)" >ĩҳ</a>
                </rs:notlast>
                &nbsp;
                ת��
               <input type="text" name="txtPage" class="page_txt">ҳ 
               <a href="javascript:goto_onClick(document.frmPost, <rs:prop property="pageamount" />)" >
                 <input name="imageField" type="image" src="img/button_go.gif" width="28" height="15" border="0">
               </a>
             </td>
         </tr>
</table>   

	</rs:hasresultset>
	
	<table width="98%" border="0" align="center" cellpadding="5"
                                cellspacing="1" class="list_bg">
                                <tr align="center">
                                        <td >
                                        <table border="0" cellspacing="0" cellpadding="0">
                                                <tr>
                                                				<td width="20"></td>
                                                                <td width="11" height="20"><img src="img/button2_r.gif" width="22" height="20"></td>
                                                                <td background="img/button_bg.gif"><a href="<bk:backurl property='ca_wallet_query.do' />" class="btn12">��&nbsp;��</a>
                                                                </td>
                                                                <td width="22" height="20"><img src="img/button2_l.gif" width="11" height="20"></td>
                                                </tr>
                                        </table>
                                        </td>
                                </tr>
                        </table>
                        
                        
	</form>               
</td>
</tr>
</table>