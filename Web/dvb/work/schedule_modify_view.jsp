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
   	alert("û���ų̱�־�����ܽ��в�����");
   	return;
   }
      
   if (document.frmPost.txtExecuteDate.value !=""){
       if (check_date(document.frmPost.txtExecuteDate.value)) { 
           var currentDate =getCurrentDate(); 
           if(replaceStr(currentDate,"0") > replaceStr(document.frmPost.txtExecuteDate.value,"0")){
	           alert("ִ��ʱ�䲻��С�ڵ����ʱ�䣡");
	           return ;
	         }                 
       }
        else {
           return;
       }
   }
    else{
         alert("��ѡ��ִ��ʱ�䣡");
         return;
   }  
      
   document.frmPost.txtActionType.value="modify";

   //ȡ������ԭ��
   if(document.frmPost.txtEventReason.value !="")
     document.frmPost.txtMemo.value=document.frmPost.txtEventReason.options[document.frmPost.txtEventReason.selectedIndex].text;
   document.frmPost.action="modify_schedule.do";

   document.frmPost.submit();
 }
 
 
 

//-->
</script>

<%
	boolean canDoAction=false;
	BatchJobDTO batchJobDTO = null;
%>
<form name="frmPost" method="post" action="">
<input type="hidden" name="txtActionType" value="" >
<input type="hidden" name="txtMemo" value="" >
<input type="hidden" name="func_flag" value="11011">
<input type="hidden" name="txtScheduleIDs" value="<tbl:writeparam name="txtBatchID" />">
<input type="hidden" name="txtServiceAccountID" value="<tbl:writeparam name="txtServiceAccountID" />">
<input type="hidden" name="txtCustomerID" value="<tbl:writeparam name="txtCustomerID" />">

<br>
<table  border="0" align="center" cellpadding="0" cellspacing="10" >
  <tr>
    <td><img src="img/list_tit.gif" width="19" height="19"></td>
    <td class="title">�޸��ų�</td>
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
    batchJobDTO = wrap.getBatchJobDTO();
    
    Collection  batchJobItemDTOList=wrap.getBatchJobItemDTOList();
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
        	<td colspan="8" class="import_tit" align="center"><font size="3">������ͷ��¼</font></td>
        </tr>
        <tr>
            <td class="list_bg2"><div align="right">�������</div></td>
            <td class="list_bg1"><tbl:write name="BatchJob" property="batchId"/></td>
            <td class="list_bg2"><div align="right">������</div></td>
            <td class="list_bg1"><%=strOpName %></td>
        </tr>
        <tr>
        	  <td class="list_bg2"><div align="right">�ų�����</div></td>
            <td class="list_bg1"><tbl:write name="BatchJob" property="name"/></td>
            <td class="list_bg2"><div align="right">�ų���������</div></td>
            <td class="list_bg1"><d:getcmnname typeName="SET_B_BATCHJOBTYPE" match="BatchJob:jobType" /></td>
        </tr>
        
        <tr>
            <td class="list_bg2"><div align="right">����ʱ��</div></td>
            <td class="list_bg1"><tbl:writedate name="BatchJob" property="createTime" />&nbsp;<tbl:writedate name="BatchJob" property="createTime" onlyTime="true"/></td>
            <td class="list_bg2"><div align="right">�趨ִ��ʱ��</div></td>
            <td class="list_bg1"><tbl:writedate name="BatchJob" property="scheduleTime" />&nbsp;<tbl:writedate name="BatchJob" property="scheduleTime" onlyTime="true"/>	
            </td>
        </tr>
        <tr>
            <td class="list_bg2"><div align="right">ִ�п�ʼʱ��</div></td>
            <td class="list_bg1"><tbl:writedate name="BatchJob" property="executeStartTime" />&nbsp;<tbl:writedate name="BatchJob" property="executeStartTime" onlyTime="true"/>	
            </td>
            <td class="list_bg2"><div align="right">ִ�н���ʱ��</div></td>
            <td class="list_bg1"><tbl:writedate name="BatchJob" property="executeEndTime" />&nbsp;<tbl:writedate name="BatchJob" property="executeEndTime" onlyTime="true"/>
            </td>
        </tr>
        <tr>
            <td class="list_bg2"><div align="right">������������</div></td>
            <td class="list_bg1">
            	<d:getcmnname typeName="SET_B_BATCHJOBREFERTYPE" match="BatchJob:referType" />
            </td>
            <td class="list_bg2"><div align="right">��������ID</div></td>
            <td class="list_bg1"><tbl:write name="BatchJob" property="referId"/>
            </td>
        </tr>
        <tr>
            <td class="list_bg2"><div align="right">����״̬</div></td>
            <td class="list_bg1"><d:getcmnname typeName="SET_B_BATCHJOBSTATUS" match="BatchJob:status" /></td>
            <td class="list_bg2"><div align="right">����</div></td>
            <td class="list_bg1"><tbl:write name="BatchJob" property="totoalRecordNo"/></td>
        </tr>
        <tr>
            <td class="list_bg2"><div align="right">ִ�гɹ�����</div></td>
            <td class="list_bg1"><tbl:write name="BatchJob" property="successRecordNo"/></td>
            <td class="list_bg2"><div align="right">ִ��ʧ������</div></td>
            <td class="list_bg1"><tbl:write name="BatchJob" property="failureRecordNo"/></td>
        </tr>
        <tr>
            <td class="list_bg2" colspan="1"><div align="right">ԭ��</div></td>
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
	   <td colspan="8" class="import_tit" align="center"><font size="3">��ϸ��¼</font></td>
  </tr>
	<tr> 
  <tr class="list_head">
  	<td class="list_head">��ˮ��</td>
    <td class="list_head">�ͻ�����</td>
    <td class="list_head">ҵ���ʻ���</td>
    <td class="list_head">PSID</td>
    <td class="list_head">��Ʒ����</td>
    <td class="list_head">��Ʒ����</td>
    <td class="list_head">ִ��״̬</td>  
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


<br>
<table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">

  <tr>
     <td colspan="4" class="import_tit" align="center"><font size="3">�޸��ų�¼����Ϣ</font></td>
  </tr>
        
  <tr>
    <td class="list_bg2"><div align="right">ִ������*</div></td>
    <td class="list_bg1"><input onfocus="viewModule(this)" onKeydown="inputDate(document.frmPost.txtExecuteDate)" onblur="lostFocus(this)" name="txtExecuteDate" type="text" class="text" maxlength="10" size="22" value="<tbl:writedate name="BatchJob" property="scheduleTime" />">    	<IMG onclick="MyCalendar.SetDate(this,document.frmPost.txtExecuteDate,'Y')" src="img/calendar.gif" 
style=cursor:hand border="0">
    </td>
    <td class="list_bg2"><div align="right">�¼�ԭ��</div></td>
    <td class="list_bg1">
    <tbl:select name="txtEventReason" set="eventResonMap" match="<%=batchJobDTO.getReasonCode()%>" width="23"   />
    </td>
  </tr>
</table>



<br>
 <table   border="0" align="center" cellpadding="0" cellspacing="0" class="list_bg">
	    <tr align="center">
	    <bk:canback url="schedule_show.do/schedule_show_all.do/schedule_show_tree.do" >  
            <td width="20" ></td>         
            <td><img src="img/button2_r.gif" border="0" width="22" height="20" ></td>
            <td background="img/button_bg.gif" ><a href="<bk:backurl property="schedule_show_detail.do/schedule_show_all.do/schedule_show_tree.do" />" class="btn12">��&nbsp;��</a></td>
            <td><img src="img/button2_l.gif" border="0" width="11" height="20" ></td>
            <td width="20" ></td> 
	    </bk:canback> 
        	<td width="20" ></td>		
    		<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
    		<td><input name="Submit" type="button" class="button" value="�޸��ų�" onclick="javascript:modify_submit()"></td>
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