<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="resultset" prefix="rs" %>
<%@ taglib uri="/WEB-INF/oss.tld" prefix="d" %>
<%@ taglib uri="bookmark" prefix="bk" %>
<%@ taglib uri="privilege" prefix="pri" %>
                 
<%@ page import="com.dtv.oss.util.Postern, java.util.*" %>
<%@ page import="com.dtv.oss.dto.BatchJobItemDTO"%>
<%@ page import="com.dtv.oss.dto.BatchJobDTO"%>
<%@ page import="com.dtv.oss.web.util.WebUtil"%>
<%@ page import="com.dtv.oss.web.util.WebKeys" %>

<%
    BatchJobDTO batchJob = Postern.getBatchJobDTOByBatchID(WebUtil.StringToInt(request.getParameter("txtBatchID")));
    pageContext.setAttribute("query", batchJob);
    String strOpName="";
    strOpName=Postern.getOperatorNameByID(batchJob.getCreateOpId());
    if(strOpName==null)
    	strOpName="";
    int waitNo = batchJob.getTotoalRecordNo() - batchJob.getFailureRecordNo() - batchJob.getSuccessRecordNo();
%>
<script language=javascript>
<!--
 function process_log_submit(queryID){	
   window.location.href="batch_job_process_log.do?txtBatchID=" + queryID+"&txtFrom=1&txtTo=10";
}


//-->
</script>
      
<form name="frmPost" method="post" action="batchJob_query_detail.do" >
<input type="hidden" name="txtFrom" size="20" value="1">
<input type="hidden" name="txtTo" size="20" value="10">
<input type="hidden" name="txtActionType" size="20" value="result">
<input type="hidden" name="txtBatchID" size="20" value="<tbl:writeparam name="txtBatchID"/>">
<table  border="0" align="center" cellpadding="0" cellspacing="10" >
  	<tr>
    	<td><img src="img/list_tit.gif" width="19" height="19"></td>
    	<td class="title">������������</td>
  	</tr>
</table>
<table width="100%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>
<br>
<table width="810" align="center" border="0" cellspacing="1" cellpadding="3">
	<tr>
	        <td colspan="4" class="import_tit" align="center"><font size="2">��������ͷ��¼</font></td>
        </tr>
	<tr>
		<td class="list_bg2" align="right" width="17%">������</td>
		<td class="list_bg1" width="33%"><tbl:writeparam name="txtBatchID" /></td>
		<td class="list_bg2" align="right" width="17%">��������</td>
		<td class="list_bg1" width="33%"><tbl:write name="query" property="Name"/></td>
 	</tr>
	<tr>		
		<td class="list_bg2" align="right" width="17%">����״̬</td>
		<td class="list_bg1" ><d:getcmnname typeName="SET_B_BATCHJOBSTATUS" match="query:Status" /></td>
		<td class="list_bg2" align="right" width="17%">��������</td>
		<td class="list_bg1"><d:getcmnname typeName="SET_B_BATCHJOBTYPE" match="query:JobType" /></td>
		<input type="hidden" name="txtJobType"  value="<tbl:write name="query" property="JobType"/>">
	</tr>
	<tr>		
		<td class="list_bg2" align="right" width="17%">������Դ</td>
		<td class="list_bg1" ><d:getcmnname typeName="SET_B_BATCHJOBREFERTYPE" match="query:referType" /></td>
		<td class="list_bg2" align="right" width="17%">��ԴID</td>
		<td class="list_bg1"><tbl:write name="query" property="referId"/></td>
	</tr>
	<tr>		
		<td class="list_bg2" align="right" width="17%">ִ�з�ʽ</td>
   		<td class="list_bg1"><d:getcmnname typeName="SET_B_SCHEDULETYPE" match="query:ScheduleType" /></td>
   		<td class="list_bg2" align="right" width="17%">�趨ִ��ʱ��</td>
   		<td class="list_bg1"><tbl:writedate name="query" property="ScheduleTime" includeTime="true"/></td>
	</tr>
	<tr>
		<td class="list_bg2" align="right" width="17%">������</td>
		<td class="list_bg1"><%=strOpName%></td>
		<td class="list_bg2" align="right" width="17%">����ʱ��</td>
   		<td class="list_bg1"><tbl:writedate name="query" property="CreateTime" includeTime="false"/></td>		
	</tr>
	<tr>
		<td class="list_bg2" align="right" width="17%">�ܼ�¼��</td>
		<td class="list_bg1"><tbl:write name="query" property="TotoalRecordNo"/></td>
		<td class="list_bg2" align="right" width="17%">�������¼��</td>
		<td class="list_bg1"><%=waitNo%></td>
	</tr>
	<tr>
		<td class="list_bg2" align="right" width="17%">�ɹ���¼��</td>
		<td class="list_bg1"><tbl:write name="query" property="successRecordNo"/></td>
		<td class="list_bg2" align="right" width="17%">ʧ�ܼ�¼��</td>
		<td class="list_bg1"><tbl:write name="query" property="failureRecordNo"/></td>
	</tr>
	<tr>
            <td class="list_bg2" align="right" width="17%">��ע��Ϣ</td>
            <td class="list_bg1" colspan="3">
            	<!--
            	<d:getcmnname typeName="SET_V_CC_CUSTSERVICEINTERACTIONSTATUSREASON" match="query:reasonCode" />
            	-->
            	<tbl:write name="query" property="description"/>
            </td>
        </tr>
</table>
<table width="100%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>
<br>
<rs:hasresultset>
<table width="100%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
  	<tr> 
	   <td colspan="10" class="import_tit" align="center"><font size="3">����������ϸ</font></td>
  	</tr>
  	<tr class="list_head">
  	    <td class="list_head">��ˮ��</td>
    	    <td class="list_head">�ͻ���</td>
    	    <td class="list_head">�ͻ�����</td>
	    <td class="list_head">�ʽ��ʺ�</td>
	    <td class="list_head">ҵ���˻�</td>
	    <td class="list_head">��Ʒ����</td>
	    <td class="list_head">PSID</td>
	    <td class="list_head">�����</td>
	    <td class="list_head">״̬</td>
	    <td class="list_head">״̬ʱ��</td>
  	</tr>
  	<lgc:bloop requestObjectName="ResponseQueryResult" item="oneline" >
  	<%
	    BatchJobItemDTO dto = (BatchJobItemDTO)pageContext.getAttribute("oneline");
	    
	    pageContext.setAttribute("BatchJobItemDTO",dto);
	    
	    String customerName="";
	    String userName="";
	    String packageName="";
	    String ccName="";
	    customerName=Postern.getCustomerNameByID(dto.getCustomerId());
	    userName=Postern.getServiceNameByID(Postern.getServiceIDByAcctServiceID(dto.getUserId()));
	    String productName=Postern.getProductNameByPSID(dto.getPsId());
	    ccName=Postern.getCampaignNameByID(dto.getCcId());
	    if(customerName==null)
	    	customerName="";
		if(userName==null)
	    	userName="";
	    if(packageName==null)
	    	packageName="";
	    if(ccName==null)
	    	ccName="";    	
	%>
	<tbl:printcsstr style1="list_bg1" style2="list_bg2" overStyle="list_over" >
	    <td><tbl:write name="BatchJobItemDTO" property="itemNO"/></td>
	    <td><tbl:write name="BatchJobItemDTO" property="customerId"/></td>
	    <td><%=customerName%></td>
	    <td><tbl:write name="BatchJobItemDTO" property="accountId"/></td>
	    <td><%=userName %></td>
	    <td><%=(productName==null?"":productName)%></td>
	    <td><tbl:write name="BatchJobItemDTO" property="psId"/></td>
	    <td><%=ccName %></td>
	    <td><d:getcmnname typeName="SET_B_BATCHJOBSTATUS" match="BatchJobItemDTO:status" /></td>
	    <td><tbl:writedate name="BatchJobItemDTO" property="StatusTime" includeTime="false"/>
	    	<br>
	    	<tbl:writedate name="BatchJobItemDTO" property="StatusTime" onlyTime="true"/>
	    </td>
	</tbl:printcsstr>
	</lgc:bloop>
	<tr>
    	<td colspan="10" class="list_foot"></td>
  	</tr>
</table>
<table  border="0" align="center" cellpadding="0" cellspacing="8">
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
<br>
<table width="100%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
	<tr align="center">
	  	<td><table  border="0" cellspacing="0" cellpadding="0">
		  	<tr>
				<bk:canback url="batchJob_query_result.do/batchJobStatus_query_result.do">
				<td width="20" ></td>  
				<td><img src="img/button2_r.gif" width="22" height="20"></td>
				<td background="img/button_bg.gif"  height="20" >
				<a href="<bk:backurl property="batchJob_query_result.do/batchJobStatus_query_result.do"/>" class="btn12">��&nbsp;��</a></td>
				<td><img src="img/button2_l.gif" width="13" height="20"></td>
				</bk:canback>

				<td width="20" ></td>  
				<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
				<td background="img/button_bg.gif"  height="20" >
				<a href="javascript:process_log_submit('<tbl:writeparam name="txtBatchID"/>');" class="btn12">�鿴������־</a></td>
				<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
		<pri:authorized name="batchJob_query_detail.export">
				<td width="20" ></td>  
				<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
				<td background="img/button_bg.gif"  height="20" >
				<a href="javascript:download(document.frmPost,'����������ϸ')" class="btn12">����������ϸ</a></td>
				<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
		</pri:authorized>

		  	</tr>
	  	</table></td>
	</tr>
</table>
</form>