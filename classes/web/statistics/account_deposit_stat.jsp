<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="resultset" prefix="rs" %>
<%@ taglib uri="/WEB-INF/oss.tld" prefix="d" %>
<%@ taglib uri="privilege" prefix="pri" %>
                 
<%@ page import="com.dtv.oss.util.Postern, java.util.*,java.text.DecimalFormat" %>
<%@ page import="com.dtv.oss.web.util.WebUtil" %>
<%@ page import="com.dtv.oss.web.util.CommonKeys" %>
<%@ page import="com.dtv.oss.dto.stat.CommonStatDTO,com.dtv.oss.dto.OperatorDTO" %>
<%@ page import="com.dtv.oss.web.util.CurrentOperator,com.dtv.oss.web.util.LogonWebCurrentOperator" %>

<%
	pageContext.setAttribute("AllMOPList", Postern.getOpeningMop());
%>
<script language=javascript>
<!--
function query_submit(){
	document.frmPost.action="account_deposit_stat.do";
	if(checkDate()){  
		document.frmPost.submit();
	}
}
function checkDate(){
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
	if(!compareDate(document.frmPost.txtCreateTime1,document.frmPost.txtCreateTime2,"�������ڱ�����ڵ��ڿ�ʼ����"))
		return false;
		
	if (check_Blank(document.frmPost.txtDistrictID, true, "����"))
		return false;
		
	return true;
}
//-->
</script>
<SCRIPT LANGUAGE="JavaScript">
<!--
function save_query_result(fname,isOpen){
	var intable=document.getElementById("inTable");
	if(intable!=null){
	intable.border=1;
	var fc=document.getElementById("fileContent");
	document.frmPost.excelTable.value=fc.innerHTML;
	intable.border=0;
	document.frmPost.excelFileName.value=fname;
	document.frmPost.action="excel_download.screen";
	document.frmPost.submit();
	}
}
//-->
</SCRIPT>
<form name="frmPost" method="post" action="account_deposit_stat.do">
<input type="hidden" name="excelTable" value="" >
<input type="hidden" name="excelFileName" value="" >
<!--
<input type="hidden" name="txtFrom" size="20" value="1">
<input type="hidden" name="txtTo" size="20" value="10">
-->

<input type="hidden" name="txtActionType" size="20" value="all">
<table width="822">
<table  border="0" align="center" cellpadding="0" cellspacing="10" >
  <tr>
    <td><img src="img/list_tit.gif" width="19" height="19"></td>
    <td class="title">�ʻ�Ԥ��ͳ��</td>
  </tr>
</table>

<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>
<br>

<table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
  <tr>
    <td class="list_bg2"><div align="right">�ͻ�����</div></td>
    <td class="list_bg1"><d:selcmn  name="txtCustomerType" mapName="SET_C_CUSTOMERTYPE" match="txtCustomerType" width="23" /> 
    </td>
    <td class="list_bg2"><div align="right">����*</div></td>
    <td class="list_bg1">
	    <input type="hidden" name="txtDistrictID" value="<tbl:writeparam name="txtDistrictID" />">
	    <input type="text" name="txtCountyDesc" size="25" maxlength="50" readonly value="<tbl:writeparam name="txtCountyDesc" />" class="text">
	    <input name="selDistButton" type="button" class="button" value="ѡ��" onClick="javascript:sel_district('all','txtDistrictID','txtCountyDesc')">
    </td>		 
  </tr>
  
  <tr>
    <td class="list_bg2"><div align="right">�ʻ�����</div></td>
    <td class="list_bg1"><d:selcmn  name="txtAccountType" mapName="SET_F_ACCOUNTTYPE" match="txtAccountType" width="23" /></td>
    <td class="list_bg2"><div align="right">��������</div></td>
    <td class="list_bg1"><tbl:select name="txtMopID" set="AllMOPList" match="txtMopID" width="23" /></td>
  </tr>
  
  <tr>
    <td class="list_bg2"><div align="right">����ʱ��</div></td>
    <td class="list_bg1" colspan="3">
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
			
			<pri:authorized name="account_deposit_stat_excel.do" >
			<td width="22" height="20">
			<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
			<td><input name="Submit" type="button" class="button" value="������" onclick="javascript:save_query_result('�ʻ�Ԥ��ͳ��',true)"></td>
			<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
			</pri:authorized>
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
</table>
<rs:hasresultset>
<div id="fileContent">
<table width="98%" border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg" id="inTable">
	<tr class="list_head">
		<td class="list_head" nowrap><div align="center">����</div></td>
<%
	
	//Map mapShow = Postern.getHashKeyValueByName("SET_F_ACCOUNTSTATUS");
	Map mapShow = new HashMap();
	mapShow.put("C","�ֽ�");
	mapShow.put("T","�������");
	
	//������ʽ��Double���͵����ݣ���ֹ���ֿ�ѧ������
	DecimalFormat decimalFormat=new DecimalFormat();
	decimalFormat.setGroupingUsed(false);
	
	double allValue[];
	long allAccount = 0;
	int colCount = 0;
	if (mapShow != null)
	{
		Iterator itKey = mapShow.keySet().iterator();
		colCount = mapShow.size();
		allValue = new double[colCount];

		for (int i=0;i<colCount;i++)
		{
				String strKey = (String)itKey.next();
%>
			<td class="list_head" nowrap><div align="center"><%=mapShow.get(strKey)%></div></td>
<%
		}
	}
	else
		{
		allValue = new double[1];

		}
%>
			<td class="list_head" nowrap><div align="center">�ܼ�</div></td>
			<td class="list_head" nowrap><div align="center">�ʻ���</div></td>
		</tr>

<lgc:bloop requestObjectName="ResponseQueryResult" item="oneline" >
	<tbl:printcsstr style1="list_bg1" style2="list_bg2" overStyle="list_over" > 
      	<td align="center" class="t12" nowrap><tbl:write name="oneline" property="name" /></td>
<%
	CommonStatDTO dto = (CommonStatDTO)pageContext.getAttribute("oneline");
	
	HashMap mapValue = dto.getKeyValue();
	double dAllValue = 0;
	if (mapShow != null)
	{
		Iterator itKey = mapShow.keySet().iterator();
		for (int i=0;i<colCount;i++)
		{
				String strKey = (String)itKey.next();
				double dValue = 0;
				if((mapValue.get(strKey) != null) && (mapValue.get(strKey) != ""))
				{
					dValue = Double.parseDouble((String)mapValue.get(strKey));
				}
				allValue[i] = allValue[i]+ dValue;
				//allValue[i]=Math.round(allValue[i]*100)/100.00;
				dAllValue = dAllValue + dValue;
				//dAllValue=Math.round(dAllValue*100)/100.00;
%>
		<td align="center" class="t12" nowrap><%=decimalFormat.format(Math.round(dValue*100)/100.00)%></td>
<%
		}
	}
	if((mapValue.get("A") != null) && (mapValue.get("A") != ""))
	{
		allAccount += Math.round(Double.parseDouble((String)mapValue.get("A")));
	}
%>
		<td align="center" class="t12" nowrap><%=decimalFormat.format(Math.round(dAllValue*100)/100.00)%></td>
		<td align="center" class="t12" nowrap><%=Math.round(Double.parseDouble((String)mapValue.get("A")))%></td>
  </tbl:printcsstr>
</lgc:bloop>  
	<tr class="trline" > 
      	<td align="center" class="t12" nowrap>�ܼ�</td>
<%
	double dSumAll = 0;
	if (mapShow != null)
	{
		Iterator itKey = mapShow.keySet().iterator();
		for (int i=0;i<mapShow.size();i++)
		{
			dSumAll = dSumAll + allValue[i];
%>
		<td align="center" class="t12" nowrap> <%=decimalFormat.format(Math.round(allValue[i]*100)/100.00)%> </td>
<%
		}
	}
%>					
	<td align="center" class="t12" nowrap> <%=decimalFormat.format(Math.round(dSumAll*100)/100.00)%> </td>
	<td align="center" class="t12" nowrap> <%=allAccount%> </td>
  </tr>
    <tr>
    <td colspan="<%=colCount+3%>" class="list_foot"></td>
  </tr>
</table>		
</div>	
</rs:hasresultset>
</form>  
         

      