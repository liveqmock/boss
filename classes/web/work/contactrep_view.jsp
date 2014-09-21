<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="osstags" prefix="d" %>
<%@ taglib uri="bookmark" prefix="bk" %>

<%@ page import="com.dtv.oss.util.Postern" %>
<%@ page import="com.dtv.oss.dto.JobCardDTO" %>

<script language=javascript>
function check_frm(){
	if (check_Blank(document.frmPost.txtWorkResult, true, "ԤԼ���"))
	    return false;
	if (document.frmPost.txtWorkResult.value=='U')
    {	
     if (check_Blank(document.frmPost.txtMemo, true, "ԤԼ��ע"))
		return false;
		
    }
    if (document.frmPost.txtWorkResult.value=='R')
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
        
    }		
	 

	return true;
}
function frm_submit()
{
        
	if (check_frm()){
	  
	 
	 document.frmPost.action="contact_user_for_repair.do"; 
	 document.frmPost.submit();
	 }
}
function back_submit(){

        document.frmPost.action="contactrep_query_result.do";
	  document.frmPost.submit();
} 


</script>
<table  border="0" align="center" cellpadding="0" cellspacing="10" >
  <tr>
    <td><img src="img/list_tit.gif" width="19" height="19"></td>
    <td class="title">ά��ԤԼ</td>
  </tr>
</table>
<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>
<br>
 
<form name="frmPost" method="post" >   
       
      
<lgc:bloop requestObjectName="ResponseQueryResult" item="oneline" IsOne="true"> 
<%
com.dtv.oss.dto.wrap.work.JobCard2JobCardProcessWrap wrap = (com.dtv.oss.dto.wrap.work.JobCard2JobCardProcessWrap)pageContext.getAttribute("oneline");
	 JobCardDTO dto=wrap.getJobCardDto();
	 pageContext.setAttribute("oneline", wrap.getJobCardDto());
	  pageContext.setAttribute("jobCardProcess", wrap.getJobCardProcessDto());
         String strAddress = Postern.getAddressById(dto.getAddressId());
	 if (strAddress == null) strAddress="";
	  String timeFlag = "SET_C_CSIPREFEREDTIME";
         Map mapPreferedTime = Postern.getAllPreferedTime(timeFlag);
        pageContext.setAttribute("AllPREFEREDTIME", mapPreferedTime);
     
%>
 <table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
         <tr>
	        <td class="list_bg2"><div align="right">�������</div></td>
		 <td class="list_bg1">
			<input type="text" name="txtJobCardID" size="25"  value="<tbl:write name="oneline" property="JobCardId" />" class="textgray" readonly   >
		 </td>
		<td class="list_bg2"><div align="right">��ϸ��ַ</div></td>
		 <td class="list_bg1">
		     <input type="text" name="txtAddressID" size="25"  value="<%=strAddress%>" class="textgray" readonly   >
		</td>
	</tr>
	  <tr>
	        <td class="list_bg2"><div align="right">��ϵ��*</div></td>
		 <td class="list_bg1">
		 <input type="text" name="txtContactPerson" size="25" maxlength="10" value="<tbl:write name="oneline" property="contactPerson" />"  >
		 </td>
		<td class="list_bg2"><div align="right">��ϵ�绰*</div></td>
		<td class="list_bg1">
			<input type="text" name="txtContactPhone" size="25" maxlength="25" value="<tbl:write name="oneline" property="contactPhone" />"  >
		</td>
	</tr>
	<tr>
		<td class="list_bg2"><div align="right">ԤԼ����ʱ��*</div></td>
		 <td class="list_bg1">
		 <input type="text" name="txtPreferedDate" size="25"  value=""/> <IMG onclick="calendar(document.frmPost.txtPreferedDate)" src="img/calendar.gif" style=cursor:hand border="0">
			 
		 </td>
		
		<td class="list_bg2"><div align="right">ԤԼ���*</div></td>
		 <td class="list_bg1">
		<d:selcmn name="txtWorkResult" mapName="SET_W_JOBCARDCONTACTRESULT" match="txtWorkResult" width="25" />
		 </td>
	</tr>
        <tr>
		<td class="list_bg2"><div align="right">ԤԼ����ʱ���*</div></td>
		<td class="list_bg1" colspan="3">
		<tbl:select name="txtPreferedTime" set="AllPREFEREDTIME" match="txtPreferedTime" width="25" /> 
		 
		</td>
	</tr>
       <tr>   
		<td class="list_bg2"><div align="right">ԤԼ��ע</div></td>
		<td class="list_bg1" colspan="3"> 
			<input type="text" name="txtMemo" size="83" maxlength="200" value="<tbl:writeparam name="txtMemo" />">
		 </td> 
	</tr>
  </table>
  <br>
  <table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>
<input type="hidden" name="txtJobCardDtLastmod"   value="<tbl:write name="oneline" property="DtLastmod" />"   >
  <input type="hidden" name="func_flag" value="1007" >
<br>

     <table align="center" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td><img src="img/button2_r.gif" width="22" height="20"></td>  
          <td background="img/button_bg.gif">
           
	  <a href="<bk:backurl property="contactrep_query_result.do/contactrep_query_result.do/job_card_view.do" />" class="btn12">��&nbsp;��</a></td>
          <td><img src="img/button2_l.gif" width="13" height="20"></td>
          <td width="20" ></td>
           <td><img src="img/button_l.gif" border="0" ></td>
          <td background="img/button_bg.gif"><a href="javascript:frm_submit()" class="btn12">Ԥ&nbsp;Լ</a></td>
          <td><img src="img/button_r.gif" border="0" ></td>
          
        </tr>
      </table>   
       
       
</lgc:bloop>   
</form>