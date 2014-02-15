<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="resultset" prefix="rs" %>
<%@ taglib uri="/WEB-INF/oss.tld" prefix="d" %>
<%@ taglib uri="privilege" prefix="pri" %>
                 
<%@ page import="com.dtv.oss.util.Postern, java.util.*,java.text.DecimalFormat" %>
<%@ page import="com.dtv.oss.web.util.WebUtil" %>
<%@ page import="com.dtv.oss.web.util.CommonKeys" %>
<%@ page import="com.dtv.oss.web.util.WebOperationUtil" %>

<%@ page import="com.dtv.oss.dto.stat.CommonStatDTO"%>

<script language=javascript>
function query_submit(){
	document.frmPost.action="business_cash_stat.do";
	if(checkData())
		document.frmPost.submit();
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
	
	if (check_Blank(document.frmPost.txtOrgID, true, "�շѻ���"))
		return false;
  	
	return true;
}
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
<form name="frmPost" method="post" action="business_cash_stat.do">
<input type="hidden" name="excelTable" value="" >
<input type="hidden" name="excelFileName" value="" >
<!--input type="hidden" name="txtFrom" size="20" value="1">
<input type="hidden" name="txtTo" size="20" value="10"-->

<input type="hidden" name="txtActionType" size="20" value="all">

<!--������ͳ�� д��-->
<input type="hidden" name="selStatType" value="O">
<table width="822">
<table  border="0" align="center" cellpadding="0" cellspacing="10" >
  <tr>
    <td><img src="img/list_tit.gif" width="19" height="19"></td>
    <td class="title">Ӫҵ�շ�ͳ��</td>
  </tr>
</table>

<table width="822"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>
<br>

<table width="822"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
  <tr>
    <td class="list_bg2"><div align="right">�ͻ�����</div></td>
    <td class="list_bg1"><d:selcmn  name="txtCustType" mapName="SET_C_CUSTOMERTYPE" match="txtCustType" width="23" /> 
    </td>
    
    <td class="list_bg2" align="right">�շѻ���*</td>
   		 <td class="list_bg1">
	    	<input type="hidden" name="txtOrgID" value="<tbl:writeparam name="txtOrgID" />">
			<input type="text" name="txtOrgDesc" size="25" maxlength="50" readonly value="<tbl:writeparam name="txtOrgDesc" />" class="text">
			<input name="selOrgButton" type="button" class="button" value="ѡ��" onClick="javascript:sel_organization('C,B,O,S,D,P','txtOrgID','txtOrgDesc')">
	   </td>
  </tr>
  <tr>
    <td class="list_bg2"><div align="right">�ʻ�����</div></td>
    <td class="list_bg1"><d:selcmn  name="txtAccountType" mapName="SET_F_ACCOUNTTYPE" match="txtAccountType" width="23" />
    </td>
    <td class="list_bg2"><div align="right">�շ�����</div></td>
    <td class="list_bg1"><d:selcmn  name="txtPayType" mapName="SET_F_PAYMENTRECORDTYPE" match="txtPayType" width="23" />
    </td>
   </tr>
  <tr>
  	<%
  	//���� not 'I' ��֧����ʽ 
    pageContext.setAttribute("MOPSet", Postern.getAllValidMOPOnlyIdAndName());
		%>     
  	<td class="list_bg2"><div align="right">֧����ʽ</div></td>
    <td class="list_bg1"> <tbl:select name="txtMOPId" set="MOPSet" match="txtMOPId" width="23" />
    </td>
    
    <td class="list_bg2"><div align="right">�շ�ʱ��</div></td>
    <td class="list_bg1">
    	<input onfocus="viewModule(this)" onKeydown="inputDate(document.frmPost.txtCreateTime1)" onblur="lostFocus(this)" name="txtCreateTime1" type="text" class="text" maxlength="10" size="10" value="<tbl:writeparam name="txtCreateTime1" />">    	<IMG onclick="MyCalendar.SetDate(this,document.frmPost.txtCreateTime1,'Y')" src="img/calendar.gif" style=cursor:hand border="0">
    	---
    	<input onfocus="viewModule(this)" onKeydown="inputDate(document.frmPost.txtCreateTime2)" onblur="lostFocus(this)" name="txtCreateTime2" type="text" class="text" maxlength="10" size="10" value="<tbl:writeparam name="txtCreateTime2" />">    	<IMG onclick="MyCalendar.SetDate(this,document.frmPost.txtCreateTime2,'Y')" src="img/calendar.gif" style=cursor:hand border="0">
    </td>
  </tr>

</table>

<table width="822"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
	<tr align="center">
	  <td class="list_bg1"><table  border="0" cellspacing="0" cellpadding="0">
		  <tr>
			<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
			<td><input name="Submit" type="button" class="button" value="��&nbsp;ѯ" onclick="javascript:query_submit()"></td>
			<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
			
			<pri:authorized name="business_cash_stat_excel.do" >
			<td width="22" height="20">
			<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
			<td><input name="Submit" type="button" class="button" value="������" onclick="javascript:save_query_result('Ӫҵ�շ�ͳ��',true)"></td>
			<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
			</pri:authorized>
		  </tr>
	  </table></td>
	</tr>
</table> 

<table width="822"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>
<br>
</table>

<rs:hasresultset>
<div id="fileContent">
<table width="100%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg" id="inTable">
  <tr class="list_head">
    <td class="list_head" rowspan="2" nowrap>�շѻ���</td>
    
    <%
    //������ʽ��Double���͵����ݣ���ֹ���ֿ�ѧ������
		DecimalFormat decimalFormat=new DecimalFormat();
		decimalFormat.setGroupingUsed(false);
	
    	Map mapTemShow=Postern.getHashKeyValueByName("SET_F_PAYMENTRECORDTYPE");
    	Map mapShow = new LinkedHashMap();
    	if(request.getParameter("txtPayType")!=null && !"".equals(request.getParameter("txtPayType")))
    	{
    		mapShow.put(request.getParameter("txtPayType"),mapTemShow.get(request.getParameter("txtPayType")));
    	}
    else
    	{
    		mapShow = mapTemShow;
    	}
    	double allValue[];
    	long allCount[];
    	int colCount = 0;
    	if (mapShow != null)
	{
		Iterator itKey = mapShow.keySet().iterator();
		colCount = mapShow.size();
		allValue = new double[colCount];
		allCount = new long[colCount];
		for (int i=0,m=mapShow.size();i<m;i++)
		{
				String strKey = (String)itKey.next();
	%>
		<td class="list_head" colspan="2" nowrap><div align="center"><%=mapShow.get(strKey)%></div></td>
	<%
		}
	}
	else
		{
		allValue = new double[1];   
		allCount = new long[1];
		} 	
    %>
    <td class="list_head" colspan="2" nowrap><div align="center">�ܼ�</div></td>
  </tr>
  <tr>
<%
	if (mapShow != null)
	{
		for (int i=0;i<colCount;i++)
		{
%>
			<td class="list_head"><div align="center">����</div></td>
			<td class="list_head"><div align="center">���</div></td>
			
<%}}%>
		<td class="list_head"><div align="center">����</div></td>
		<td class="list_head"><div align="center">���</div></td>

		</tr>

   
<lgc:bloop requestObjectName="ResponseQueryResult" item="oneline" >

<tbl:printcsstr style1="list_bg1" style2="list_bg2" overStyle="list_over" >
    
    <td align="center" class="t12" nowrap><tbl:write name="oneline" property="name" /></td>
    <%
	CommonStatDTO dto = (CommonStatDTO)pageContext.getAttribute("oneline");
	HashMap mapValue = dto.getKeyValue();
	if(mapValue == null) mapValue = new HashMap();
	double dAllValue = 0;
	long dAllCount = 0;
	if (mapShow != null)
	{
		Iterator itKey = mapShow.keySet().iterator();
		//����ͳ��
		for (int i=0,m=mapShow.size();i<m;i++)
		{
				String strKey = (String)itKey.next();
				double dValue = 0;
				long dCount = 0;
				if((mapValue.get(strKey) != null) && (mapValue.get(strKey) != ""))
				{
					Map temMap = (Map)mapValue.get(strKey);
					dValue = Double.parseDouble((String)(temMap.get("amount")));
					dCount = Integer.parseInt((String)(temMap.get("batchnumber")));
				}
				allValue[i] = allValue[i]+ dValue;
				allCount[i] = allCount[i]+ dCount;
				
				dAllValue = dAllValue + dValue;
				dAllCount = dAllCount + dCount;
    %>
    <td align="center" class="t12" nowrap><%=dCount%></td>
		<td align="center" class="t12" nowrap><%=decimalFormat.format(Math.round(dValue*100)/100.0)%></td>
		
    <%
		}
	}
    %>
    <td align="center" class="t12" nowrap><%=dAllCount%></td>
    <td align="center" class="t12" nowrap><%=decimalFormat.format(Math.round(dAllValue*100)/100.0)%></td>
</tbl:printcsstr>
</lgc:bloop>

  <tr>
    <tbl:printcsstr style1="list_bg1" style2="list_bg2" overStyle="list_over" >
    	<td align="center" nowrap>�ܼ�</td>
    	<%
		double dSumAll = 0;
		long dSumAllCount = 0;
		if (mapShow != null)
		{
			Iterator itKey = mapShow.keySet().iterator();
			for (int i=0;i<mapShow.size();i++)
			{
				//double dValue = Math.round(allValue[i]*100)/100.0;
				dSumAll = dSumAll + allValue[i];
				dSumAllCount = dSumAllCount + allCount[i];
	%>
		<td align="center" class="t12"> <%=allCount[i]%> </td>
		<td align="center" class="t12" nowrap> <%=decimalFormat.format(Math.round(allValue[i]*100)/100.0)%> </td>
	<%
			}
		}
	%>	
	<td align="center" class="t12 nowrap"> <%=dSumAllCount%> </td>				
	<td align="center" class="t12" nowrap> <%=decimalFormat.format(Math.round(dSumAll*100)/100.0)%> </td>
    	
    </tbl:printcsstr>
  </tr>
  
  <tr>
    <td colspan="<%=colCount*2+2*2%>" class="list_foot"></td>
  </tr>

</table>
</div>
 </rs:hasresultset>                 
<BR>

</form>  
         

      