<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="resultset" prefix="rs" %>
<%@ taglib uri="/WEB-INF/oss.tld" prefix="d" %>
<%@ taglib uri="bookmark" prefix="bk" %>
                 
<%@ page import="com.dtv.oss.util.Postern, java.util.*" %>
<%@ page import="com.dtv.oss.dto.BatchJobDTO"%>
<%@ page import="com.dtv.oss.dto.BatchJobProcessLogDTO"%>
<%@ page import="com.dtv.oss.web.util.WebUtil"%>


<script language=javascript>
<!--
 function process_log_submit(queryID){	
   window.location.href="batch_job_process_log.do?txtQueryID=" + queryID;
}
//-->
</script>
      
<form name="frmPost" method="post" action="batch_job_process_log.do" >
<input type="hidden" name="txtFrom" size="20" value="1">
<input type="hidden" name="txtTo" size="20" value="10">
<input type="hidden" name="txtActionType" size="20" value="result">
<input type="hidden" name="txtBatchID" size="20" value="<tbl:writeparam name="txtBatchID"/>">
<table  border="0" align="center" cellpadding="0" cellspacing="10" >
  	<tr>
    	<td><img src="img/list_tit.gif" width="19" height="19"></td>
    	<td class="title">处理日志</td>
  	</tr>
</table>
<table width="100%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>
<br>

<table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
  <tr class="list_head">
    <td class="list_head">日志批号</td>
    <td class="list_head">记录明细号</td>  
    <td class="list_head">执行者</td>
    <td class="list_head">执行动作</td>
    <td class="list_head">执行结果</td>   
    <td class="list_head">执行开始时间</td> 
    <td class="list_head">执行结束时间</td> 
    <td class="list_head">备注</td>
  </tr>

<lgc:bloop requestObjectName="ResponseQueryResult" item="oneline" >
<%
    String strOpName="";
    BatchJobProcessLogDTO batchJobProcessLogDTO = (BatchJobProcessLogDTO)pageContext.getAttribute("oneline");

    strOpName=Postern.getOperatorNameByID(batchJobProcessLogDTO.getOperatorId());
    if(strOpName==null)
    	strOpName="";
    String result=batchJobProcessLogDTO.getResult();
    System.out.println("************result**********"+result);   
    pageContext.setAttribute("BatchJobProcessLogDTO",batchJobProcessLogDTO);
    BatchJobDTO aBatchJobDTO=Postern.getBatchJobDTOByBatchID(batchJobProcessLogDTO.getBatchId());
    String begindate="",enddate="";
    if(aBatchJobDTO.getExecuteStartTime()!=null)
    	begindate=aBatchJobDTO.getExecuteStartTime().toString();
   if(aBatchJobDTO.getExecuteEndTime()!=null)
    	enddate=aBatchJobDTO.getExecuteEndTime().toString();
    
%>
<tbl:printcsstr style1="list_bg1" style2="list_bg2" overStyle="list_over" >   
    <td align="center"><tbl:write name="BatchJobProcessLogDTO" property="seqNO"/></td>  
    <td align="center"><tbl:write name="BatchJobProcessLogDTO" property="itemNO"/></td>   
    <td align="center"><%=strOpName %></td>
    <td align="center"><d:getcmnname typeName="SET_B_BATCHJOBPROCESSACTION" match="BatchJobProcessLogDTO:action" /></td>
    <td align="center"><d:getcmnname typeName="SET_B_BATCHJOBSTATUS" match="BatchJobProcessLogDTO:result" /></td>    
    <td align="center"><%=begindate%></td>  
    <td align="center"><%=enddate%></td>      
    <td align="center"><tbl:write name="BatchJobProcessLogDTO" property="comments" /></td>    
</tbl:printcsstr>
</lgc:bloop>
</table>

<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>

<rs:hasresultset>
<table  border="0" align="center" cellpadding="0" cellspacing="8">
  	<tr>
    	<td>第<span class="en_8pt"><rs:prop property="curpageno" /></span>页<span class="en_8pt">/</span>共<span class="en_8pt"><rs:prop property="pageamount" /><span>页</td>
    	<td>共<span class="en_8pt"><rs:prop property="recordcount" /></span>条记录</td>
	<rs:notfirst>
	    <td align="right"><img src="img/dot_top.gif" width="17" height="11"></td>
	    <td><a href="javascript:firstPage_onClick(document.frmPost, <rs:prop property="firstto" />)" class="link12">首页</a></td>
	    <td align="right"><img src="img/dot_pre.gif" width="6" height="11"></td>
	    <td><a href="javascript:previous_onClick(document.frmPost, <rs:prop property="prefrom" />, <rs:prop property="preto" />)" class="link12">上一页</a></td>
	</rs:notfirst>
	<rs:notlast>
	    <td align="right"><img src="img/dot_next.gif" width="6" height="11"></td>
	    <td><a href="javascript:next_onClick(document.frmPost, <rs:prop property="nextfrom" />, <rs:prop property="nextto" />)" class="link12">下一页</a></td>
	    <td align="right"><img src="img/dot_end.gif" width="17" height="11"></td>
	    <td><a href="javascript:lastPage_onClick(document.frmPost, <rs:prop property="lastfrom" />, <rs:prop property="lastto" />)" class="link12">末页</a></td>
	</rs:notlast>
	    <td align="right">跳转到</td>
	    <td><input name="txtPage" type="text" class="page_txt"></td>
	    <td>页</td>
	    <td><input name="imageField" type="image" src="img/button_go.gif" width="28" height="15" border="0" onclick="javascript:goto_onClick(document.frmPost, <rs:prop property="pageamount" />)"></td>
  	</tr>
</table>

</rs:hasresultset>

<br>
<table width="100%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
	<tr align="center">
	  	<td><table  border="0" cellspacing="0" cellpadding="0">
		  	<tr>
				<bk:canback url="batchJob_query_detail.do/ownFeeBatch_query_detail.do/schedule_show_detail.do">
				<td width="20" ></td>  
				<td><img src="img/button2_r.gif" width="22" height="20"></td>
				<td background="img/button_bg.gif"  height="20" >
				<a href="<bk:backurl property="batchJob_query_detail.do/ownFeeBatch_query_detail.do/schedule_show_detail.do"/>" class="btn12">返&nbsp;回</a></td>
				<td><img src="img/button2_l.gif" width="13" height="20"></td>
				</bk:canback>			
		  	</tr>
	  	</table></td>
	</tr>
</table>
</form>