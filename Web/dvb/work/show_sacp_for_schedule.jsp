<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="resultset" prefix="rs" %>
<%@ taglib uri="/WEB-INF/oss.tld" prefix="d" %>
                 
<%@ page import="com.dtv.oss.util.Postern, java.util.*" %>
<%@ page import="com.dtv.oss.web.util.WebUtil" %>
<%@ page import="com.dtv.oss.web.util.CommonKeys" %>
<%@ page import="com.dtv.oss.web.util.WebOperationUtil" %>
<%@ page import="com.dtv.oss.dto.MethodOfPaymentDTO" %>
<%@ page import="com.dtv.oss.dto.OrganizationDTO" %>

<%@ page import="com.dtv.oss.service.commandresponse.QueryCommandResponseImpl"%>
<%@ page import="com.dtv.oss.dto.wrap.customer.ServiceAccountWrap"%>
<%@ page import="com.dtv.oss.dto.CustomerProductDTO, 
                 com.dtv.oss.dto.ServiceAccountDTO, 
                 com.dtv.oss.dto.CPCampaignMapDTO,
                 com.dtv.oss.dto.CustomerCampaignDTO"%>

<%
	String txtEventReason = request.getParameter("txtEventReason");
	String txtEventType = request.getParameter("txtEventType");
	String txtEventReasonName = "";
	if(txtEventReason!=null && !"".equals(txtEventReason) && txtEventType!=null && !"".equals(txtEventType) )
		txtEventReasonName = Postern.getCsiReasonByCsiTypeAndReason(txtEventType,txtEventReason);
		
	System.out.println("=======txtEventReason======="+txtEventReason);
	System.out.println("=======txtEventType======="+txtEventType);
	System.out.println("=======txtEventReasonName========"+txtEventReasonName);
%>
<script language=javascript>
   function AllChoose(){
     if (document.frmPost.allchoose.checked){
        if (document.frmPost.partchoose[0]){
            for (i=0 ;i< document.frmPost.partchoose.length ;i++){
              document.frmPost.partchoose[i].checked =true;
           }
       }
       else if(document.frmPost.partchoose!=null){
       		document.frmPost.partchoose.checked =true;
       }
     }
      else {
        if (document.frmPost.partchoose[0]){
            for (i=0 ;i< document.frmPost.partchoose.length ;i++){
               document.frmPost.partchoose[i].checked =false;
            }
       }
       else if(document.frmPost.partchoose!=null){
       		document.frmPost.partchoose.checked =false;
       }
     }   
  }

  //说明：通过优惠ID访问优惠详细信息
 function view_campaign_detail(strId)
{
   window.open('list_campaign_detail.do?txtCampaignID='+strId,'','toolbar=no,location=no,status=no,menubar=no,scrollbar=no,width=280,height=250');
}

function view_schedule(batchid){

}

function view_cp_detail_click(strId)
{
	self.location.href="customer_product_view.do?txtPsID="+strId;
}
 
function hasSelect(){
    var flag =false;
    if (document.frmPost.partchoose!=null){
    	if(document.frmPost.partchoose.length!=null){
       		for (i=0 ;i< document.frmPost.partchoose.length ;i++){
           		if (document.frmPost.partchoose[i].checked){
               			flag =true;
               			break;
               		}
           	}
       	}
       	else{
       		if (document.frmPost.partchoose.checked){
               		flag =true;
               	}
       	}
    }
    if(!flag){
    	return false;
    }
    return true;
 }
 
 function create_submit(){
 	
    document.frmPost.txtCPIDs.value="";
    
    if(!hasSelect()){
      	alert("没有选择产品，不能提交")
    	  return;
    }
 
   if (document.frmPost.txtEventType.value ==""){ 
       alert("请选择事件类型!");
       return;
   }
       
   if (document.frmPost.txtExecuteDate.value !=""){
       if (check_date(document.frmPost.txtExecuteDate.value)) { 
           var currentDate =getCurrentDate(); 
           if(replaceStr(currentDate,"0") > replaceStr(document.frmPost.txtExecuteDate.value,"0")){
	            alert("执行时间不能小于当天的时间！");
	            return ;
	         }                 
       } else {
           return;
       }
   } else{
         alert("请选择执行时间！");
         return;
   }  
      
  if (document.frmPost.partchoose[0]){
    	for (j=0; j<document.frmPost.partchoose.length; j++){
       		if (document.frmPost.partchoose[j].checked) {
           		if (document.frmPost.txtCPIDs.value!="") document.frmPost.txtCPIDs.value = document.frmPost.txtCPIDs.value + ";";
	            	document.frmPost.txtCPIDs.value=document.frmPost.txtCPIDs.value + document.frmPost.partchoose[j].value;
       		}
   	}
  }
  else if(document.frmPost.partchoose){
   	   if (document.frmPost.partchoose.checked) document.frmPost.txtCPIDs.value=document.frmPost.partchoose.value;
  }
  
    document.frmPost.action="create_schedule_step1.do";
    document.frmPost.submit();
 }
 
function ChangeCsiType()
{
   if(document.frmPost.txtEventType!=null)
   	document.FrameUS.submit_update_select('csitypetoreason', document.frmPost.txtEventType.value, 'txtEventReason', '');
}
</script>
<div id="updselwaitlayer" style="position:absolute; left:650px; top:180px; width:250px; height:24px; display:none">
	<table width="100%" border="0" cellspacing="1" cellpadding="3">
		<tr>
			<td width="100%" align="center">
				<font size="2"> 正在获取内容。。。 </font>
			</td>
		</tr>
	</table>
</div>
<iframe SRC="update_select.screen" name="FrameUS" width="0" height="0" frameborder="0" scrolling="0">
</iframe>
<form name="frmPost" method="post" action="show_sacp_for_schedule.do">
<input type="hidden" name="txtCustomerID" value="<tbl:writeparam name="txtCustomerID" />">
<input type="hidden" name="txtServiceAccountID" value="<tbl:writeparam name="txtServiceAccountID" />"> 
<input type="hidden" name="txtFrom" value="1">
<input type="hidden" name="txtTo" value="500">
<input type="hidden" name="func_flag" value="11008" >

<table  border="0" align="center" cellpadding="0" cellspacing="10" >
  <tr>
    <td><img src="img/list_tit.gif" width="19" height="19"></td>
    <td class="title">排程任务创建----选择产品</td>
  </tr>
</table>

<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>
<br>

<%
	int accountID=0;
	HashMap actionTypeMap=new HashMap();
	actionTypeMap.put("PS","暂停");	
	actionTypeMap.put("PR","恢复");
	actionTypeMap.put("PC","取消");
	pageContext.setAttribute("EventType",actionTypeMap);
%>

<table width="804"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
  <tr>
    <td class="list_bg2"><div align="right">事件类型*</div></td>
    <td class="list_bg1">
    <tbl:select name="txtEventType" set="EventType" match="txtEventType" width="23" onchange="ChangeCsiType()" /></td>
    <td class="list_bg2"><div align="right">执行日期*</div></td>
    <td class="list_bg1"><input onfocus="viewModule(this)" onKeydown="inputDate(document.frmPost.txtExecuteDate)" onblur="lostFocus(this)" name="txtExecuteDate" type="text" class="text" maxlength="10" size="22" value="<tbl:writeparam name="txtExecuteDate" />">    	<IMG onclick="MyCalendar.SetDate(this,document.frmPost.txtExecuteDate,'Y')" src="img/calendar.gif" style=cursor:hand border="0">
    </td>
  </tr>
  <tr>
    <td class="list_bg2"><div align="right">事件原因</div></td>
    <td class="list_bg1"><select name="txtEventReason"><option value="" >-----------------------</option></select></td>
    <td class="list_bg2"><div align="right"></div></td>
    <td class="list_bg1"></td>
  </tr>
</table>

<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>
<br>

<table width="780"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
  <tr class="list_head">
    <td class="list_head" width="10" nowrap><div align="center"><input type="checkbox" name="allchoose" value="" onclick="AllChoose()"></div></td>
    <td class="list_head" width="100" nowrap>产品名称</td>
    <td class="list_head" width="40" nowrap>状态</td>
    <td class="list_head" width="60" nowrap>付费帐户</td>
    <td class="list_head" width="90" nowrap>产品包名称</td>
    <td class="list_head" width="80" nowrap>创建时间</td>
    <td class="list_head" width="80" nowrap>激活时间</td>
    <td class="list_head" width="80" nowrap>暂停时间</td>
    <td class="list_head" width="60" nowrap>是否已经排程</td>
    <td class="list_head" width="100" nowrap>优惠活动</td>
    <td class="list_head" width="80" nowrap>优惠起止日期</td>
  </tr>

<%
    String strProdName="";
    String strPackName="";
    Collection scheduleIDList=null;

%>
   
<lgc:bloop requestObjectName="ResponseQueryResult" item="oneline" >
<%
    ServiceAccountWrap wrap = (ServiceAccountWrap)pageContext.getAttribute("oneline");
    CustomerProductDTO cpDto = wrap.getCpDto();
    ServiceAccountDTO saDto = wrap.getSaDto();
    pageContext.setAttribute("sa", saDto);
    pageContext.setAttribute("cp", cpDto);

    strProdName = Postern.getProductNameByID(cpDto.getProductID());
    CPCampaignMapDTO cPCampaignMapDTO = Postern.getCPCampaignMapByCustProductID(cpDto.getPsID());
    CustomerCampaignDTO custCampaignDto =Postern.getCustomerCampaignByccID(cPCampaignMapDTO.getCcId());
    if (custCampaignDto ==null) custCampaignDto =new CustomerCampaignDTO();
    pageContext.setAttribute("CustomerCampaignDTO",custCampaignDto);
           
    strPackName = Postern.getPackagenameByID(cpDto.getReferPackageID());
    if (strProdName==null) {
	     strProdName="";
    }
    if (strPackName==null) {
	     strPackName="";
    }
	
    if(cpDto.getDeviceID()==0){

%>
<tbl:printcsstr style1="list_bg1" style2="list_bg2" overStyle="list_over" >
	  <td align="center" class="t12">
		<input type="checkbox" name="partchoose" value="<tbl:write name="cp" property="PsID"/>">
	  </td>     
    <td align="center"><a href="javascript:view_cp_detail_click('<tbl:write name="cp" property="PsID"/>')" class="link12" ><%=strProdName%></a></td>
    <td align="center"><d:getcmnname typeName="SET_C_CUSTOMERPRODUCTPSTATUS" match="cp:status" /></td>
    <td align="center"><tbl:write name="cp" property="accountID" />
    	<%
    		if (accountID==0){
    			 accountID=-1;
    	%>
    		<input type="hidden" name="txtAccountID" value="<tbl:write name="cp" property="accountID" />">
    	<%
    		}
    	%>
    </td>
    
    <td align="center"><%=strPackName%></td>
    <td align="center"><tbl:writedate name="cp" property="createTime" /></td>
    <td align="center"><tbl:writedate name="cp" property="activateTime" /></td>
    <td align="center"><tbl:writedate name="cp" property="pauseTime" /></td>
    <td align="center">
    	<%
    		scheduleIDList=Postern.getScheduleIDsByCPID(cpDto.getPsID());
    		if(scheduleIDList==null || scheduleIDList.size()==0){
    	%>否
    	<%
    		}
    		else{	
    	%>
    	是
    	<% } %>
    </td>
	
	<%
   		if (custCampaignDto == null ||  custCampaignDto.getCampaignID()==0) {
	%>
	<td align="center" class="t12">(没有优惠)</td>
	<%  } else {
		String capName = Postern.getCampaignNameByID(custCampaignDto.getCampaignID());
		if (capName == null) capName = ""; 
	%>
	<td align="center" class="t12">	
	<a href="JavaScript:view_campaign_detail('<%=custCampaignDto.getCampaignID()%>')" title="<%=capName%>"><%=capName%></a>
	</td>
	<%
		}
	%>

	<%if(custCampaignDto.getDateFrom()!=null||custCampaignDto.getDateTo() !=null){%>
    		<td><tbl:writedate  name="CustomerCampaignDTO" property="dateFrom" /><br>—<br><tbl:writedate  name="CustomerCampaignDTO" property="dateTo" /></td>
    		
    		<input type="hidden" name="txtStartDate"  value="<tbl:writedate  name="CustomerCampaignDTO" property="dateFrom" />">
    		<input type="hidden" name="txtEndDate"  value="<tbl:writedate  name="CustomerCampaignDTO" property="dateTo" />">
	<%} else {%>
		
		    <input type="hidden" name="txtStartDate"  value="1990-1-1">
    		<input type="hidden" name="txtEndDate"  value="1990-1-1">
    		
    		<td><br></td>
	<%
		}
	%>

</tbl:printcsstr>
<%
	}
%>

</lgc:bloop>


  <tr>
    <td colspan="11" class="list_foot"></td>
  </tr>
<input type="hidden" name="txtCPIDs" size="22" value="">
<input type="hidden" name="txtActionType" size="22" value="create">
<input type="hidden" name="txtDoPost" value ="false" >

 
  <tr>
              <td align="right" class="t12" colspan="11" >
                 第<span class="en_8pt"><rs:prop property="curpageno" /></span>页
                 <span class="en_8pt">/</span>共<span class="en_8pt"><rs:prop property="pageamount" />页
                 共有<span class="en_8pt"><rs:prop property="recordcount" /></span>条记录&nbsp;&nbsp;&nbsp;&nbsp;             
                 <rs:notfirst>
                   <img src="img/dot_top.gif" width="17" height="11">
                   <a href="javascript:firstPage_onClick(document.frmPost, <rs:prop property="firstto" />)" >首页 </a>
                   <img src="img/dot_pre.gif" width="6" height="11">
                   <a href="javascript:previous_onClick(document.frmPost, <rs:prop property="prefrom" />, <rs:prop property="preto" />)" >上一页</a>
                 </rs:notfirst>
          
                <rs:notlast>
                 &nbsp;
                 <img src="img/dot_next.gif" width="6" height="11">
                 <a href="javascript:next_onClick(document.frmPost, <rs:prop property="nextfrom" />, <rs:prop property="nextto" />)" >下一页</a>
                 <img src="img/dot_end.gif" width="17" height="11">
                 <a href="javascript:lastPage_onClick(document.frmPost, <rs:prop property="lastfrom" />, <rs:prop property="lastto" />)" >末页</a>
                </rs:notlast>
                &nbsp;
                转到
               <input type="text" name="txtPage" class="page_txt">页 
               <a href="javascript:goto_onClick(document.frmPost, <rs:prop property="pageamount" />)" >
                 <input name="imageField" type="image" src="img/button_go.gif" width="28" height="15" border="0">
               </a>
             </td>
         </tr>
  </table>
  
                
<BR>
<br>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td align="center">
	<table border="0" cellspacing="0" cellpadding="0">
	  <tr>
	    <td width="20" ></td>		
	    <td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
	    <td><input name="Submit" type="button" class="button" value="下一步" onclick="javascript:create_submit()"></td>
	    <td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
		</tr>
	</table> 
		</td>		    
	</tr>
</table> 

</form>  
         

<script>

<%if(!"".equals(txtEventReasonName)){%>
	frmPost.txtEventReason.outerHTML="<SELECT name=txtEventReason width=23 ><OPTION value='<%=txtEventReason%>' selected>"+'<%=txtEventReasonName%>'+"</OPTION><option value='' >-----------------------</option></SELECT>";
<%}%>

</script>