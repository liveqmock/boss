<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="resultset" prefix="rs" %>
<%@ taglib uri="osstags" prefix="o" %>
<%@ taglib uri="bookmark" prefix="bk" %>

<%@ page import="com.dtv.oss.dto.JobCardDTO" %>
<%@ page import="com.dtv.oss.util.Postern" %>
<%@ page import="com.dtv.oss.web.util.WebUtil"%>
<%@ page import="com.dtv.oss.web.util.CommonKeys"%> 
<script language=javascript>
function check_frm()
{
	 if (check_Blank(document.frmPost.txtContactPerson, true, "联系人"))
		return false;

	if (check_Blank(document.frmPost.txtContactPhone, true, "联系电话"))
		return false;
	 
	if (check_Blank(document.frmPost.txtPreferedDate, true, "预约上门时间"))
		return false;
    if (!check_TenDate(document.frmPost.txtPreferedDate, true, "预约上门时间")) {
        return false;
    }
    if(!compareDateAndToday(getCurrentDate(),document.frmPost.txtPreferedDate,"预约上门日期必须在今天以后"))
	    return false;
    if (check_Blank(document.frmPost.txtPreferedTime, true, "预约上门时间段"))
		return false;

	return true;
}
function frm_submit()
{

	if (check_frm()) 
	{
		document.frmPost.txtWorkResult.value="";
		document.frmPost.submit();
	}
}

 
</script>
 <table width="820" align="center" cellpadding="0" cellspacing="0">
<tr> 
<td align="center" valign="top"><table  border="0" align="center" cellpadding="0" cellspacing="10">
      <tr>
        <td><img src="img/list_tit.gif" width="19" height="19"></td>
        <td class="title">施工预约</td>
      </tr>
    </table>
      <table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
        <tr>
          <td><img src="img/mao.gif" width="1" height="1"></td>
        </tr>
      </table>
         <br>
 
<% 
  String status= null; 
  String type = null;
%>
<lgc:bloop requestObjectName="ResponseQueryResult" item="oneline" >

<%	
    com.dtv.oss.dto.wrap.work.JobCard2JobCardProcessWrap   wrap=(com.dtv.oss.dto.wrap.work.JobCard2JobCardProcessWrap)pageContext.getAttribute("oneline");
    pageContext.setAttribute("oneline", wrap.getJobCardDto());
    pageContext.setAttribute("jobcardprocess",  wrap.getJobCardProcessDto());
    JobCardDTO jcdto = wrap.getJobCardDto();
    type = Postern.getCSITypeByID(jcdto.getReferSheetId());
    String preferTime = Postern.getPreferedTime(jcdto.getReferSheetId());
    String timeFlag="SET_C_CSIPREFEREDTIME";
    Map mapPreferedTime = Postern.getAllPreferedTime(timeFlag);
    pageContext.setAttribute("AllPREFEREDTIME", mapPreferedTime);
    status = jcdto.getStatus();
    String preferdate1 = null;
    String preferDate = Postern.getPreferedDate(jcdto.getReferSheetId());
    
    if(preferDate!=null){
    int len=preferDate.indexOf(":");
    
      preferdate1=preferDate.substring(0,len-2).trim();
     
    } else {
    preferdate1="";
    }
    
%>
<form name="frmPost" method="post" action="catv_contact_user_for_construction.do" >
 <table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
        <tr>
        <td class="list_bg2"><div align="right">工单编号</div></td>
        <td class="list_bg1">
	<input type="text" name="txtJobCardID" size="25"  value="<tbl:write name="oneline" property="JobCardId" />" class="textgray" readonly   >
	</td>
	<td class="list_bg2"><div align="right">受理单编号</div></td>
        <td class="list_bg1">
	<input type="text" name="txtReferSheetID" size="25"  value="<tbl:write name="oneline" property="ReferSheetId" />" class="textgray" readonly   >
	</td>
	</tr>
	<tr>
	<td class="list_bg2"><div align="right">新增端口数</div></td>
        <td class="list_bg1">
	<input type="text" name="txtAddPortNumber" size="25"  value="<tbl:write name="oneline" property="addPortNumber" />" class="textgray" readonly   >
	</td>
	  <td class="list_bg2"><div align="right">创建时间</div></td>
	  <td class="list_bg1"  > 
			<input type="text" name="txtActionTime" size="25"  value="<tbl:writedate name="oneline"   property="dtCreate" includeTime="true"/>" class="textgray" readonly   >
	</td>
	</tr>
	<tr>
	<td class="list_bg2"><div align="right">工单类型</div></td>
        <td class="list_bg1">
		<input type="text" name="txtJobType" size="25"  value="<o:getcmnname typeName="SET_W_JOBCARDTYPE" match="oneline:jobType"/>" class="textgray" readonly   >
	</td>
	  <td class="list_bg2"><div align="right">工单子类型</div></td>
	  <td class="list_bg1"  > 
		<input type="text" name="txtSubType" size="25"  value="<o:getcmnname typeName="SET_W_JOBCARDSUBTYPE" match="oneline:subType"/>" class="textgray" readonly  >
	</td>
	</tr> 
	<tr>
		<td class="list_bg2" ><div align="right">预约上门时间*</div></td>
		<td class="list_bg1"> 
			<input type="text" name="txtPreferedDate" size="25"  value="<tbl:writedate name="oneline" property="preferedDate"/>"/> <IMG onclick="calendar(document.frmPost.txtPreferedDate)" src="img/calendar.gif" style=cursor:hand border="0">
            
		</td>
		<td class="list_bg2" ><div align="right">预约上门时间段*</div></td>
		<td class="list_bg1"> 
			<tbl:select name="txtPreferedTime" set="AllPREFEREDTIME" match="oneline:preferedTime" width="23" /> 
		</td>
	</tr>
	<tr>
		<td class="list_bg2" ><div align="right">联系人*</div></td>
		<td class="list_bg1"> 
			<input type="text" name="txtContactPerson" size="25"  value="<tbl:write name="oneline" property="contactPerson" />" >
		 </td>
		<td class="list_bg2" ><div align="right">联系电话*</div></td>
		<td class="list_bg1"> <input type="text" name="txtContactPhone" size="25"  value="<tbl:write name="oneline" property="contactPhone" />">
		</td>
	</tr>
	
         <tr>
		<td class="list_bg2" ><div align="right">描述信息</div></td>
		<td class="list_bg1" colspan="3"> 
			<input type="text" name="txtMemo" size="84" maxlength="100" value="<tbl:write name="jobcardprocess" property="Memo" />">
		 </td>
	</tr>
  	
	
   <input type="hidden" name="txtJobCardDtLastmod"   value="<tbl:write name="oneline" property="DtLastmod" />"    >
  <input type="hidden" name="txtWorkResult" value="" >   
  <input type="hidden" name="func_flag" value="101" >
</lgc:bloop>  

 </form> 
 
  <%//if(CommonKeys.CUSTSERVICEINTERACTIONTYPE_BK.equals(type)||
  		//CommonKeys.CUSTSERVICEINTERACTIONTYPE_BU.equals(type)){%>
 
 <form name="frmDelPost" method="post" action="cancel_booking_install_op.do" >
   <input type="hidden" name="txtID"  value="<tbl:write name="oneline" property="JobCardId" />">
   <input type="hidden" name="func_flag"  value="111">
 <!--  <tr>
    td class="list_bg2" ><div align="right">取消安装单的原因</div></td>
    <td class="list_bg1" colspan="3"> 
    <o:selcmn name="txtWorkResultReason" mapName="SET_W_JOBRESULTREASON" width="25" /> </td
    <tbl:csiReason csiType="BK" name="txtWorkResultReason" action="C" showType="text"  checkScricptName="check_csiReason()" tdWordStyle="list_bg2" tdControlStyle="list_bg1" forceDisplayTD="true" match="txtWorkResultReason"  controlSize="25" tdControlColspan="3" />
    </tr> -->
 
  </form>
  <%//}%>
  </table>
  <br>
  <table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
        <tr>
          <td><img src="img/mao.gif" width="1" height="1"></td>
        </tr>
      </table>
      <br>
 <table align="center" border="0" cellspacing="0" cellpadding="0">
        <tr> 
           <td><img src="img/button2_r.gif" width="22" height="20"></td>
            <td background="img/button_bg.gif"><a href="<bk:backurl property="job_card_query_result_for_contact_of_install.do/job_card_query_result_for_contact_of_install_redo.do/catv_job_card_view.do/catv_job_card_query_result_for_contact_of_install.do/job_card_view.do" />" class="btn12">返&nbsp;回</a></td>           
           <td><img src="img/button2_l.gif" width="11" height="20"></td>
            <td width="20" ></td>
          <td><img src="img/button_l.gif" border="0" ></td>
          <td background="img/button_bg.gif"  ><a href="javascript:frm_submit()" class="btn12">确&nbsp;认</a></td>
          <td><img src="img/button_r.gif" border="0" ></td>
    <%if(CommonKeys.CUSTSERVICEINTERACTIONTYPE_BK.equals(type)||
  		CommonKeys.CUSTSERVICEINTERACTIONTYPE_BU.equals(type)){%>
          <pri:authorized name="cancel_booking_install_op.do" >
          <td width="20"></td>
	  <td><img src="img/button_l.gif" border="0" ></td>
          <td background="img/button_bg.gif"  >
	  <a href="javascript:cancel_submit()" class="btn12">取消安装</a></td>
          <td><img src="img/button_r.gif" border="0" ></td>
</pri:authorized>
 <%}%>
 

        </tr>
      </table>    
 
    
       <br>  
   
  
 


  </td>
  </tr>
  </table>


   

<Script language="JavaScript">
<!--
function cancel_submit()
{
    if(!check_csiReason())
		return;

    if (window.confirm('您确认要取消该安装单吗?'))
    {
	document.frmDelPost.submit();
    }
}
//-->
</Script>