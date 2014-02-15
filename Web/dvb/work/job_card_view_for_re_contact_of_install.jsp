<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="resultset" prefix="rs" %>
<%@ taglib uri="osstags" prefix="o" %>
<%@ taglib uri="bookmark" prefix="bk" %>

<%@ page import="com.dtv.oss.dto.JobCardDTO" %>
<%@ page import="com.dtv.oss.util.Postern" %>
<%@ page import="com.dtv.oss.web.util.CommonKeys"%>
<%@ page import="com.dtv.oss.web.util.WebUtil"%>
 
<script language=javascript>
function check_frm()
{
	 if (check_Blank(document.frmPost.txtContactPerson, true, "��ϵ��"))
		return false;

	if (check_Blank(document.frmPost.txtContactPhone, true, "��ϵ�绰"))
		return false;
	 
	if (check_Blank(document.frmPost.txtPreferedDate, true, "ԤԼ����ʱ��"))
		return false;
    if (!check_TenDate(document.frmPost.txtPreferedDate, true, "ԤԼ����ʱ��")) {
        return false;
    }
    if(!compareDateAndToday(getCurrentDate(),document.frmPost.txtPreferedDate,"ԤԼ�������ڱ����ڽ����Ժ�"))
	    return false;
    if (check_Blank(document.frmPost.txtPreferedTime, true, "ԤԼ����ʱ���"))
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
function cancel_submit(){
	if(!check_csiReason())
		return;
	//if (check_Blank(document.frmDelPost.txtWorkResultReason, true, "ȡ����װ��ԭ��"))
       	//	return;

    if (window.confirm('��ȷ��Ҫȡ���ð�װ����?'))
    {
	document.frmDelPost.submit();
    }
}
 
</script>
 <table width="820" align="center" cellpadding="0" cellspacing="0">
<tr> 
<td align="center" valign="top"><table  border="0" align="center" cellpadding="0" cellspacing="10">
      <tr>
        <td><img src="img/list_tit.gif" width="19" height="19"></td>
        <td class="title">��װ��ԤԼ</td>
      </tr>
    </table>
      <table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
        <tr>
          <td><img src="img/mao.gif" width="1" height="1"></td>
        </tr>
      </table>
         <br>
 
<% String status = null;
   String type = null;
 %>
<lgc:bloop requestObjectName="ResponseQueryResult" item="oneline" >

<%
    com.dtv.oss.dto.wrap.work.JobCard2JobCardProcessWrap   wrap=(com.dtv.oss.dto.wrap.work.JobCard2JobCardProcessWrap)pageContext.getAttribute("oneline");
    pageContext.setAttribute("oneline", wrap.getJobCardDto());
    pageContext.setAttribute("jobcardprocess",  wrap.getJobCardProcessDto());
    JobCardDTO jcdto = wrap.getJobCardDto();
    //get the cis type 
     type = Postern.getCSITypeByID(jcdto.getReferSheetId());
    
   // String preferTime = Postern.getPreferedTime(jcdto.getReferSheetId());
    String timeFlag="SET_C_CSIPREFEREDTIME";
    Map mapPreferedTime = Postern.getAllPreferedTime(timeFlag);
    
    pageContext.setAttribute("AllPREFEREDTIME", mapPreferedTime);
    status = jcdto.getStatus() ; 
   
       
%>
<form name="frmPost" method="post" action="contact_user_for_install.do" >
 <table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
        <tr>
        <td class="list_bg2"><div align="right">�������</div></td>
        <td class="list_bg1">
	<input type="text" name="txtJobCardID" size="25"  value="<tbl:write name="oneline" property="JobCardId" />" class="textgray" readonly   >
	</td>
	<td class="list_bg2"><div align="right">�������</div></td>
        <td class="list_bg1">
	<input type="text" name="txtReferSheetID" size="25"  value="<tbl:write name="oneline" property="ReferSheetId" />" class="textgray" readonly   >
	</td>
	</tr>
	<tr>
	  <td class="list_bg2"><div align="right">����ʱ��</div></td>
	  <td class="list_bg1" colspan="3" > 
			<input type="text" name="txtActionTime" size="25"  value="<tbl:writedate name="oneline" property="dtCreate"/>" class="textgray" readonly   >
	</td>
	</tr> 
	<tr>
		<td class="list_bg2" ><div align="right">ԤԼ����ʱ��*</div></td>
		<td class="list_bg1"> 
			<input maxlength="10" onfocus="viewModule(this)" onKeydown="inputDate(document.frmPost.txtPreferedDate)" onblur="lostFocus(this)" type="text" name="txtPreferedDate" size="25"  value=""/> <IMG onclick="MyCalendar.SetDate(this,document.frmPost.txtPreferedDate,'Y')" src="img/calendar.gif" style=cursor:hand border="0">
            
		</td>
		<td class="list_bg2" ><div align="right">ԤԼ����ʱ���*</div></td>
		<td class="list_bg1"> 
			<tbl:select name="txtPreferedTime" set="AllPREFEREDTIME" match="txtPreferedTime" width="23" /> 
		</td>
	</tr>
	<tr>
		<td class="list_bg2" ><div align="right">��ϵ��*</div></td>
		<td class="list_bg1"> 
			<input type="text" name="txtContactPerson" size="25"  value="<tbl:write name="oneline" property="contactPerson" />" >
		 </td>
		<td class="list_bg2" ><div align="right">��ϵ�绰*</div></td>
		<td class="list_bg1"> <input type="text" name="txtContactPhone" size="25"  value="<tbl:write name="oneline" property="contactPhone" />">
		</td>
	</tr>
	
         <tr>
		<td class="list_bg2" ><div align="right">������Ϣ</div></td>
		<td class="list_bg1" colspan="3"> 
			<input type="text" name="txtMemo" size="84" maxlength="100" value="<tbl:write name="jobcardprocess" property="Memo" />">
		 </td>
	</tr>
  
   <input type="hidden" name="txtJobCardDtLastmod"   value="<tbl:write name="oneline" property="DtLastmod" />"    >
  <input type="hidden" name="txtWorkResult" value="" >   
  <input type="hidden" name="txtStatus"   value="<%=status%>">
  <input type="hidden" name="func_flag" value="101" >
 
</lgc:bloop> 
 </form> 
 <%if(CommonKeys.CUSTSERVICEINTERACTIONTYPE_BK.equals(type)||
  		CommonKeys.CUSTSERVICEINTERACTIONTYPE_BU.equals(type)){%>
 
 <form name="frmDelPost" method="post" action="cancel_booking_install_op.do" >
   <input type="hidden" name="txtID"  value="<tbl:write name="oneline" property="JobCardId" />">
   <input type="hidden" name="func_flag"  value="111">
   <tr>
    <!--
    <td class="list_bg2" ><div align="right">ȡ����װ����ԭ��</div></td>
    <td class="list_bg1" colspan="3"> 
    <o:selcmn name="txtWorkResultReason" mapName="SET_W_JOBRESULTREASON" width="25" /> </td>
    -->
    <tbl:csiReason csiType="BK" name="txtWorkResultReason" action="C" showType="text"  checkScricptName="check_csiReason()" tdWordStyle="list_bg2" tdControlStyle="list_bg1" forceDisplayTD="true" match="txtWorkResultReason"  controlSize="25" tdControlColspan="3" />
    </tr>
 
  </form>
  <%}%>
  
   
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
            <td background="img/button_bg.gif"><a href="<bk:backurl property="job_card_query_result_for_re_contact_of_install.do/job_card_query_result_for_contact_of_install_redo.do/job_card_view.do" />" class="btn12">��&nbsp;��</a></td>           
           <td><img src="img/button2_l.gif" width="11" height="20"></td>
            <td width="20" ></td>
          <td><img src="img/button_l.gif" border="0" ></td>
          <td background="img/button_bg.gif"  ><a href="javascript:frm_submit()" class="btn12">ȷ&nbsp;��</a></td>
          <td><img src="img/button_r.gif" border="0" ></td>
 <%if(CommonKeys.CUSTSERVICEINTERACTIONTYPE_BK.equals(type)||
  		CommonKeys.CUSTSERVICEINTERACTIONTYPE_BU.equals(type)){%>          
          <pri:authorized name="cancel_booking_install_op.do" >
          <td width="20"></td>
	  <td><img src="img/button_l.gif" border="0" ></td>
          <td background="img/button_bg.gif"  >
	  <a href="javascript:cancel_submit()" class="btn12">ȡ����װ</a></td>
          <td><img src="img/button_r.gif" border="0" ></td>
	  </pri:authorized>
 <%}%>

        </tr>
      </table>    
 
    
       <br>  
   
  
 


  </td>
  </tr>
  </table>


   
 