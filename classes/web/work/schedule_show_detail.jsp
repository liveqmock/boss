<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="/WEB-INF/oss.tld" prefix="d" %>
<%@ taglib uri="bookmark" prefix="bk" %>

<%@ page import="com.dtv.oss.util.Postern, java.util.*" %>
<%@ page import="com.dtv.oss.web.util.WebUtil" %>
<%@ page import="com.dtv.oss.web.util.CommonKeys" %>
<%@ page import="com.dtv.oss.web.util.WebOperationUtil" %>
<%@ page import="com.dtv.oss.dto.OrganizationDTO" %>
<%@ page import="com.dtv.oss.dto.BatchJobDTO,com.dtv.oss.dto.CustomerProductDTO"%>
<%@ page import="com.dtv.oss.dto.wrap.work.Schedule2CP2CCWrap,
                 com.dtv.oss.dto.CPCampaignMapDTO,
                 com.dtv.oss.dto.CustomerCampaignDTO"%>
<%@ page import="com.dtv.oss.dto.BatchJobItemDTO" %>
<%@ page import="com.dtv.oss.service.commandresponse.CommandResponseImp" %>


<%@page import="java.sql.Timestamp"%>
<script language=javascript>
<!--

 
 function modify_submit(){	
   
   if(document.frmPost.txtScheduleIDs.value==""){
   	alert("没有排程标志，不能进行操作！");
   	return;
   }
      
   document.frmPost.txtActionType.value="modify";
   document.frmPost.action='schedule_modify_view.do?txtBatchID=<tbl:writeparam name="txtBatchID" />&txtActionType=detail&showmodel=customer&txtCustomerID=<tbl:writeparam name="txtCustomerID" />';

   document.frmPost.submit();
 }
 
 function cancel_submit(){
 	
   if(document.frmPost.txtScheduleIDs.value==""){
   	alert("没有排程标志，不能进行操作！");
   	return;
   }
   document.frmPost.txtActionType.value="cancel";
   //document.frmPost.txtMemo.value=document.frmPost.txtEventReason.options[document.frmPost.txtEventReason.selectedIndex].text;
   document.frmPost.action="cancel_schedule.do";

   document.frmPost.submit();
 }
 
 function process_log_submit(queryID){	
   window.location.href="batch_job_process_log.do?txtBatchID=" + queryID;
 }
//-->
</script>

<%
	boolean canDoAction=false;
%>
<form name="frmPost" method="post" action="">
<input type="hidden" name="txtActionType" value="" >
<input type="hidden" name="txtMemo" value="" >
<input type="hidden" name="func_flag" value="11010">
<input type="hidden" name="txtScheduleIDs" value="<tbl:writeparam name="txtBatchID" />">
<input type="hidden" name="txtServiceAccountID" value="<tbl:writeparam name="txtServiceAccountID" />">
<input type="hidden" name="txtCustomerID" value="<tbl:writeparam name="txtCustomerID" />">

<br>
<table  border="0" align="center" cellpadding="0" cellspacing="10" >
  <tr>
    <td><img src="img/list_tit.gif" width="19" height="19"></td>
    <td class="title">排程任务详情</td>
  </tr>
</table>
<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>
<br>

<lgc:bloop requestObjectName="ResponseQueryResult" item="oneline" >
<%

    Schedule2CP2CCWrap wrap = (Schedule2CP2CCWrap)pageContext.getAttribute("oneline");
    BatchJobDTO batchJobDTO = wrap.getBatchJobDTO();

    
    Collection  batchJobItemDTOList=wrap.getBatchJobItemDTOList();
    
    if(batchJobDTO== null )batchJobDTO = new BatchJobDTO();
    pageContext.setAttribute("BatchJob",batchJobDTO);
    
      
    
    CommandResponseImp batchJobItemImp= new CommandResponseImp(batchJobItemDTOList);
    request.setAttribute("BatchJobItem",batchJobItemImp);
    
    
    String strOpName="";
    String strCustomerName="";
    String strProdName="";
    String strPackName="";
    String strCCMap="";
    
    strOpName=Postern.getOperatorNameByID(batchJobDTO.getCreateOpId());
    if(strOpName==null)
    	strOpName="";
    
    if(CommonKeys.BATCH_JOB_STATUS_WAIT.equals(batchJobDTO.getStatus()))
    	canDoAction=true;
    	
    	String strEventReason = Postern.getCsiReasonDesByCon(batchJobDTO.getJobType(),null,batchJobDTO.getReasonCode());
    	if(strEventReason == null) strEventReason = "";
    	
    	Map eventResonMap = Postern.getCsiReasonByCsitype(batchJobDTO.getJobType()); 
    	pageContext.setAttribute("eventResonMap", eventResonMap);
    
%>

<table align="center" width="98%" border="0" cellspacing="1" cellpadding="5" class="list_bg">
        <tr>
        	<td colspan="8" class="import_tit" align="center"><font size="3">批处理头记录</font></td>
        </tr>
        <tr>
            <td class="list_bg2"><div align="right">批处理号</div></td>
            <td class="list_bg1"><tbl:write name="BatchJob" property="batchId"/></td>
            <td class="list_bg2"><div align="right">创建人</div></td>
            <td class="list_bg1"><%=strOpName %></td>
        </tr>
        <tr>
        	  <td class="list_bg2"><div align="right">排程名称</div></td>
            <td class="list_bg1"><tbl:write name="BatchJob" property="name"/></td>
            <td class="list_bg2"><div align="right">排程任务类型</div></td>
            <td class="list_bg1"><d:getcmnname typeName="SET_B_BATCHJOBTYPE" match="BatchJob:jobType" /></td>
        </tr>
        
        <tr>
            <td class="list_bg2"><div align="right">创建时间</div></td>
            <td class="list_bg1"><tbl:writedate name="BatchJob" property="createTime" />&nbsp;<tbl:writedate name="BatchJob" property="createTime" onlyTime="true"/></td>
            <td class="list_bg2"><div align="right">设定执行时间</div></td>
            <td class="list_bg1"><tbl:writedate name="BatchJob" property="scheduleTime" />&nbsp;<tbl:writedate name="BatchJob" property="scheduleTime" onlyTime="true"/>	
            </td>
        </tr>
        <tr>
            <td class="list_bg2"><div align="right">执行开始时间</div></td>
            <td class="list_bg1"><tbl:writedate name="BatchJob" property="executeStartTime" />&nbsp;<tbl:writedate name="BatchJob" property="executeStartTime" onlyTime="true"/>	
            </td>
            <td class="list_bg2"><div align="right">执行结束时间</div></td>
            <td class="list_bg1"><tbl:writedate name="BatchJob" property="executeEndTime" />&nbsp;<tbl:writedate name="BatchJob" property="executeEndTime" onlyTime="true"/>
            </td>
        </tr>
        <tr>
            <td class="list_bg2"><div align="right">关联单据类型</div></td>
            <td class="list_bg1">
            	<d:getcmnname typeName="SET_B_BATCHJOBREFERTYPE" match="BatchJob:referType" />
            </td>
            <td class="list_bg2"><div align="right">关联单据ID</div></td>
            <td class="list_bg1"><tbl:write name="BatchJob" property="referId"/>
            </td>
        </tr>
        <tr>
            <td class="list_bg2"><div align="right">任务状态</div></td>
            <td class="list_bg1"><d:getcmnname typeName="SET_B_BATCHJOBSTATUS" match="BatchJob:status" /></td>
            <td class="list_bg2"><div align="right">数量</div></td>
            <td class="list_bg1"><tbl:write name="BatchJob" property="totoalRecordNo"/></td>
        </tr>
        <tr>
            <td class="list_bg2"><div align="right">执行成功数量</div></td>
            <td class="list_bg1"><tbl:write name="BatchJob" property="successRecordNo"/></td>
            <td class="list_bg2"><div align="right">执行失败数量</div></td>
            <td class="list_bg1"><tbl:write name="BatchJob" property="failureRecordNo"/></td>
        </tr>
        <tr>
            <td class="list_bg2" colspan="1"><div align="right">原因</div></td>
            <td class="list_bg1" colspan="3"><%=strEventReason%></td>
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
	   <td colspan="8" class="import_tit" align="center"><font size="3">明细记录</font></td>
  </tr>
	<tr> 
  <tr class="list_head">
  	<td class="list_head">流水号</td>
    <td class="list_head">客户姓名</td>
    <td class="list_head">业务帐户号</td>
    <td class="list_head">PSID</td>
    <td class="list_head">产品名称</td>
    <td class="list_head">产品包名</td>
    <td class="list_head">执行状态</td>  
  </tr>

   
<lgc:bloop requestObjectName="BatchJobItem" item="oneline" >
<%
   BatchJobItemDTO batchJobItemDTO=(BatchJobItemDTO)pageContext.getAttribute("oneline");
   CustomerProductDTO cpDto = Postern.getCustomerProductDTOByPSID(batchJobItemDTO.getPsId());
   CPCampaignMapDTO cPCampaignMapDTO = Postern.getCPCampaignMapByCustProductID(batchJobItemDTO.getPsId());
   CustomerCampaignDTO custCampaignDto =Postern.getCustomerCampaignByccID(cPCampaignMapDTO.getCcId());
   if (custCampaignDto ==null) custCampaignDto =new CustomerCampaignDTO();
   pageContext.setAttribute("CustomerCampaignDTO",custCampaignDto);
   

    
    strProdName = Postern.getProductNameByID(cpDto.getProductID());
    strPackName = Postern.getPackagenameByID(cpDto.getReferPackageID());
    strCustomerName=Postern.getCustomerNameByID(batchJobItemDTO.getCustomerId());
    if (strProdName==null) strProdName="";
    if (strPackName==null) strPackName="";
    if(strCustomerName==null) strCustomerName="";
    
    int itemNO = batchJobItemDTO.getItemNO();
    int psId = batchJobItemDTO.getPsId();
    int serviceAccountID = cpDto.getServiceAccountID();

    pageContext.setAttribute("BatchJobItemDTO",batchJobItemDTO);      
    pageContext.setAttribute("CPCampaignMapDTO",cPCampaignMapDTO);
    pageContext.setAttribute("cp",cpDto);
%>

<tbl:printcsstr style1="list_bg1" style2="list_bg2" overStyle="list_over" >
    <td align="center"><tbl:write name="BatchJobItemDTO" property="itemNO" /></td>
    <td align="center"><%=strCustomerName %></td>    
    <td align="center"><tbl:write name="cp" property="serviceAccountID" /></td>
    <td align="center"><tbl:write name="cp" property="psID" /></td>
    <td align="center"><%=strProdName%></td>
    <td align="center"><%=strPackName%></td>
    <td align="center"><d:getcmnname typeName="SET_B_BATCHJOBSTATUS" match="BatchJobItemDTO:status" /></td>   
    
</tbl:printcsstr>
</lgc:bloop>
  <tr>
    <td colspan="8" class="list_foot"></td>
  </tr>
</table>
<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>

</lgc:bloop>


<BR>
<BR>
 <table   border="0" align="center" cellpadding="0" cellspacing="0" class="list_bg">
	    <tr align="center">
	    <bk:canback url="schedule_show.do/schedule_show_all.do/schedule_show_tree.do" >  
            <td width="20" ></td>         
            <td><img src="img/button2_r.gif" border="0" width="22" height="20" ></td>
            <td background="img/button_bg.gif" ><a href="<bk:backurl property="schedule_show.do/schedule_show_all.do/schedule_show_tree.do" />" class="btn12">返&nbsp;回</a></td>
            <td><img src="img/button2_l.gif" border="0" width="11" height="20" ></td>
            <td width="20" ></td> 
	    </bk:canback> 
	    
	    <%
	    	if(!(request.getParameter("showmodel")==null || "".equals(request.getParameter("showmodel"))) && canDoAction){
	    %> 
	    	<td width="20" ></td>		
    		<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
    		<td><input name="Submit" type="button" class="button" value="取消排程"  onclick="javascript:cancel_submit()"></td>
    		<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
            
        <td width="20" ></td>		
    		<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
    		<td><input name="Submit" type="button" class="button" value="修改排程" onclick="javascript:modify_submit()"></td>
    		<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
	    <% } %>
	      <td width="20" ></td>  
				<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
				<td background="img/button_bg.gif"  height="20" >
				<a href="javascript:process_log_submit('<tbl:writeparam name="txtBatchID"/>');" class="btn12">查看处理日志</a></td>
				<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
	    
	    </tr>
	    

 </table>

 <table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>
<br>
</form>  