<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="resultset" prefix="rs" %>
<%@ taglib uri="/WEB-INF/oss.tld" prefix="d" %>
                 
<%@ page import="com.dtv.oss.util.Postern, java.util.*" %>
<%@ page import="com.dtv.oss.web.util.WebUtil" %>
<%@ page import="com.dtv.oss.dto.stat.CommonStatDTO"%>
<%@ page import="com.dtv.oss.service.commandresponse.QueryCommandResponseImpl"%>

<script language=javascript>
function query_submit(){
	if(checkDate()){  
		document.frmPost.submit();
	}
}
function checkDate(){
if (check_Blank(document.frmPost.txtDistrictID, true, "����"))
		return false;
	return true;
}
</script>

<%!
	public CommonStatDTO getCommonStatDTOByDTOID(String dtoID,Collection col){
		if(dtoID==null || "".equals(dtoID) || col==null)
			return null;
		Iterator itCol=col.iterator();
		while(itCol.hasNext()){
			CommonStatDTO dto=(CommonStatDTO)itCol.next();
			if(dtoID.equals(dto.getId()))
				return dto;
		}
		return null;
	}

%>
<form name="frmPost" method="post" action="failed_bank_acct_check_stat.do">

<input type="hidden" name="txtFrom" size="20" value="1">
<input type="hidden" name="txtTo" size="20" value="10">

<input type="hidden" name="txtActionType" size="20" value="all">

<table  border="0" align="center" cellpadding="0" cellspacing="10" >
  <tr>
    <td><img src="img/list_tit.gif" width="19" height="19"></td>
    <td class="title">�Ǵ���ɹ��û���������</td>
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
    <td class="list_bg2"><div align="right">����*</div></td>
    <td class="list_bg1">
    <input type="hidden" name="txtDistrictID" value="<tbl:writeparam name="txtDistrictID" />">
    <input type="text" name="txtCountyDesc" size="25" maxlength="50" readonly value="<tbl:writeparam name="txtCountyDesc" />" class="text">
    <input name="selDistButton" type="button" class="button" value="ѡ��" onClick="javascript:sel_district('all','txtDistrictID','txtCountyDesc')">
   
    </td>
    <td class="list_bg2"><div align="right">�ͻ�����</div></td>
    <td class="list_bg1"><d:selcmn  name="txtCustomerType" mapName="SET_C_CUSTOMERTYPE" match="txtCustomerType" width="23" /></td>
  </tr>
  
  <tr>
    <td class="list_bg2"><div align="right">�ʻ�״̬</div></td>
    <td class="list_bg1"><d:selcmn  name="txtStatus" mapName="SET_F_ACCOUNTSTATUS" match="txtStatus" width="23" /> </td>
    <td class="list_bg2"></td>
    <td class="list_bg1"></td>
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


<%
	QueryCommandResponseImpl RepCmd = (QueryCommandResponseImpl)request.getAttribute("ResponseQueryResult");
   	Collection amountCommonStatDTOList =(Collection)RepCmd.getExtraPayLoad();
%>

<table width="98%" border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
	<tr class="list_head">
		<td class="list_head"><div align="center">����</div></td>
<%
	
	Map mapShow = new HashMap();
	//��������
	mapShow.put("Y","�ѿ���1������(����/���)");
	mapShow.put("Q","�ѿ���7~12����(����/���)");
	mapShow.put("L","�ѿ���4~6����(����/���)");
	mapShow.put("S","�ѿ���3����(����/���)");
	mapShow.put("T","�ѿ���2����(����/���)");
	mapShow.put("O","�ѿ���1����(����/���)");
	mapShow.put("C","���¿���(����/���)");
	
	long allValue[];
	double allCount[];
	
	int colCount = 0;
	if (!mapShow.isEmpty())
	{
		Iterator itKey = mapShow.keySet().iterator();
		colCount = mapShow.size();
		allValue = new long[colCount];
		allCount = new double[colCount]; 
		for (int i=0;i<colCount;i++)
		{
				String strKey = (String)itKey.next();
%>
			<td class="list_head" colspan="2"><div align="center"><%=mapShow.get(strKey)%></div></td>
<%
		}
	}
	else{
		allValue = new long[1];
		allCount = new double[1];
	}
%>
			<td class="list_head" colspan="2" ><div align="center">�ܼ�(����/���)</div></td>
		</tr>

<lgc:bloop requestObjectName="ResponseQueryResult" item="oneline" >
	<tbl:printcsstr style1="list_bg1" style2="list_bg2" overStyle="list_over" > 
      	<td align="center" class="t12"><tbl:write name="oneline" property="name" /></td>
<%
	CommonStatDTO dto = (CommonStatDTO)pageContext.getAttribute("oneline");
	CommonStatDTO amountDTO=getCommonStatDTOByDTOID(dto.getId(),amountCommonStatDTOList);
		
	HashMap mapValue = dto.getKeyValue();
	HashMap mapCount=null;
	
	if(amountDTO!=null)
		mapCount=amountDTO.getKeyValue();
		
	long dAllValue = 0;
	
	double dAllCount =0;
	
	if (!mapShow.isEmpty())
	{
		Iterator itKey = mapShow.keySet().iterator();
		for (int i=0;i<colCount;i++)
		{
				String strKey = (String)itKey.next();
				long dValue = 0;
				double dCount = 0;
				if((mapValue.get(strKey) != null) && (mapValue.get(strKey) != ""))
				{
					dValue = Math.round(Double.parseDouble((String)mapValue.get(strKey))*10)/10;
				}
				if(mapCount!=null && mapCount.get(strKey)!=null && mapCount.get(strKey) !="")
					dAllCount = Math.round(Double.parseDouble((String)mapCount.get(strKey))*10)/10;
					
				allValue[i] = allValue[i]+ dValue;
				dAllValue = dAllValue + dValue;
				
				allCount[i] = allCount[i]+ dCount;
				dAllCount = dAllCount + dCount;
%>
		<td align="center" class="t12"><%=dValue%></td>
		<td align="center" class="t12"><%=dCount%></td>
<%
		}
	}
%>
		<td align="center" class="t12"><%=dAllValue%></td>
		<td align="center" class="t12"><%=dAllCount%></td>
  </tbl:printcsstr>
</lgc:bloop>  
	<tr class="trline" > 
      	<td align="center" class="t12">�ܼ�(����/���)</td>
<%
	long dSumAll = 0;
	double dSumCount=0;
	
	if (!mapShow.isEmpty())
	{
		Iterator itKey = mapShow.keySet().iterator();
		for (int i=0;i<mapShow.size();i++)
		{
			long dValue = Math.round(allValue[i]*100)/100;
			dSumAll = dSumAll + dValue;
			
			double dCount = Math.round(allCount[i]*100)/100;
			dSumCount = dSumCount + dCount;
%>
		<td align="center" class="t12"> <%=dValue%> </td>
		<td align="center" class="t12"> <%=dCount%> </td>
<%
		}
	}
%>					
	<td align="center" class="t12"> <%=dSumAll%> </td>
	<td align="center" class="t12"> <%=dSumCount%> </td>
  </tr>
    <tr>
    <td colspan="<%=colCount*2+3%>" class="list_foot"></td>
  </tr>
</table>		
</rs:hasresultset>
</form>  
         

      