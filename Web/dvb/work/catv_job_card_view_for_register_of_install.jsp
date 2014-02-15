<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="resultset" prefix="rs" %>
<%@ taglib uri="osstags" prefix="o" %>
<%@ taglib uri="bookmark" prefix="bk" %>
<%@ taglib uri="operator" prefix="op" %>

<%@ page import="com.dtv.oss.dto.JobCardDTO,
                 com.dtv.oss.dto.CustServiceInteractionDTO" %>
<%@ page import="com.dtv.oss.util.Postern" %>
<%@ page import="com.dtv.oss.web.util.CommonKeys" %>

<script language=javascript>

function check_frm(){
	if (check_Blank(document.frmPost.txtProcessMan, true, "��װ��Ա"))
	  	return false;
	if (check_Blank(document.frmPost.txtWorkDate, true, "����ʱ��"))
		  return false;	
	if (!check_TenDate(document.frmPost.txtWorkDate, true, "����ʱ��"))
			return false;
	if (check_Blank(document.frmPost.txtWorkTime, true, "����ʱ���"))
		  return false;				
	if (document.frmPost.txtFaildComment.value != '' && document.frmPost.txtMemo.value != '') {
        alert("������ͬʱ���밲װ�ɹ���ʧ��ԭ������");
        return false;
   }

   return true;
}
function frm_submit1(csipaymentOption){
	var csi_payment_option_sp ='<%=CommonKeys.CSI_PAYMENT_OPTION_SP%>';
  if (check_frm()){
	   if (document.frmPost.txtWorkResultReason.value!=''){
	      alert('��װ�ɹ����벻Ҫѡ��װʧ��ԭ��');
	      document.frmPost.txtWorkResultReason.focus();
	      return;
	   }
	   if (document.frmPost.txtFaildComment.value!=''){
	     alert('��װ�ɹ����벻Ҫ��дʧ��ԭ��������');
	     document.frmPost.txtFaildComment.focus();
	     document.frmPost.txtFaildComment.select();
	     return;
	   }
	   
	   document.frmPost.txtIsSuccess.value='true';
	   if (csi_payment_option_sp ==csipaymentOption){
	   	  document.frmPost.txtDoPost.value ="false";
        document.frmPost.action ="register_of_install_success_fee.do";
     } else{
     	  document.frmPost.action ="install_info_update.do";
     }
	   document.frmPost.submit();
  }
}

function frm_submit2(){
   if (check_frm() && check_Bidimconfig()){
	    if (check_Blank(document.frmPost.txtWorkResultReason, true, "��װʧ��ԭ��"))
		      return;

	    if (check_Blank(document.frmPost.txtFaildComment, true, "ʧ��ԭ������"))
		      return;

      document.frmPost.action ="install_info_update.do";
      document.frmPost.txtIsSuccess.value='false';
      document.frmPost.submit();
   }
}
 
</script>

<table width="810" align="center" cellpadding="0" cellspacing="0">
<tr> 
<td align="center" valign="top">
	 <table  border="0" align="center" cellpadding="0" cellspacing="10">
       <tr>
        <td><img src="img/list_tit.gif" width="19" height="19"></td>
        <td class="title">¼��ʩ��������Ϣ</td>
      </tr>
    </table>
    <table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
       <tr>
         <td><img src="img/mao.gif" width="1" height="1"></td>
       </tr>
    </table>
    <br>
 
<form name="frmPost" method="post" action="" >

  <%
  /**
     com.dtv.oss.dto.wrap.work.JobCard2JobCardProcessWrap   wrap=(com.dtv.oss.dto.wrap.work.JobCard2JobCardProcessWrap)pageContext.getAttribute("oneline");
     pageContext.setAttribute("oneline", wrap.getJobCardDto());
     String timeFlag = "SET_C_CSIPREFEREDTIME";
     Map mapPreferedTime = Postern.getAllPreferedTime(timeFlag);
     
     pageContext.setAttribute("AllPREFEREDTIME", mapPreferedTime);
     pageContext.setAttribute("jobcardprocess",  wrap.getJobCardProcessDto());
     CustServiceInteractionDTO csiDto =new CustServiceInteractionDTO();
     if (CommonKeys.JOBCARD_TYPE_INSTALLATION.equals(wrap.getJobCardDto().getJobType())){
        csiDto =Postern.getCsiDTOByCSIID(wrap.getJobCardDto().getReferSheetId());
     }
     **/
  %>

 <table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
   <tr>
      <td class="list_bg2" align="right" width="17%">�������</td>
      <td class="list_bg1" align="left"  width="33%">
	      <input type="text" name="txtJobCardID" size="25"  value="<tbl:write name="oneline" property="JobCardId" />" class="textgray" readonly   >
	    </td>
	    <td class="list_bg2" align="right" width="17%">�������</td>
      <td class="list_bg1" align="left"  width="33%">
	       <input type="text" name="txtReferSheetID" size="25"  value="<tbl:write name="oneline" property="ReferSheetId" />" class="textgray" readonly   >
	    </td>
	 </tr>
	 
	<tr>
	<td class="list_bg2"><div align="right">��������</div></td>
        <td class="list_bg1">
	<input type="text" name="txtPortNo" size="25"  value="<tbl:write name="oneline" property="portNo" />" class="textgray" readonly   >
	</td>
	  <td class="list_bg2"><div align="right">����������</div></td>
	  <td class="list_bg1"  > 
			<input type="text" name="txtActionTime" size="25"  value="<tbl:writedate name="oneline"   property="dtCreate" includeTime="true"/>" class="textgray" readonly   >
	</td>
	</tr> 
	
	<tr>
	<td class="list_bg2"><div align="right">�����˿���</div></td>
        <td class="list_bg1">
	<input type="text" name="txtPortNo" size="25"  value="<tbl:write name="oneline" property="portNo" />" class="textgray" readonly   >
	</td>
	  <td class="list_bg2"><div align="right">����ʱ��</div></td>
	  <td class="list_bg1"  > 
			<input type="text" name="txtActionTime" size="25"  value="<tbl:writedate name="oneline"   property="dtCreate" includeTime="true"/>" class="textgray" readonly   >
	</td>
	</tr>
	
	 <tr>
		<td class="list_bg2"  align="right" width="17%">ԤԼ����ʱ��</td>
		<td class="list_bg1"  align="left"  width="33%"> 
			  <input type="text" name="txtPreferedDate" size="25"  value="<tbl:writedate name="oneline" property="PreferedDate"/>" class="textgray" readonly>   
    </font></td>
		<td class="list_bg2"  align="right" width="17%">ԤԼ����ʱ���</td>
		<td class="list_bg1"  align="left"  width="33%">
		   <input type="text" name="txtPreferedTime"  size="25"  value="<o:getcmnname typeName="SET_C_CSIPREFEREDTIME" match="oneline:PreferedTime" />" class="textgray" readonly > 
		</td>
	 </tr>
	 <tr>
		<td class="list_bg2"  align="right" width="17%">����ʱ��*</td>
		<td class="list_bg1"  align="left"  width="33%"> 
			<input maxlength="10" onfocus="viewModule(this)" onKeydown="inputDate(document.frmPost.txtWorkDate)" onblur="lostFocus(this)" type="text" name="txtWorkDate" size="25"  value="<tbl:writeparam name="txtWorkDate" />"/> <IMG onclick="MyCalendar.SetDate(this,document.frmPost.txtWorkDate,'Y')" src="img/calendar.gif" style=cursor:hand border="0">        
		</td>
		<td class="list_bg2" align="right" width="17%">����ʱ���*</td>
		<td class="list_bg1" align="left"  width="33%"> 
		<tbl:select name="txtWorkTime" set="AllPREFEREDTIME" match="txtWorkTime" width="25" />
		</td>
	 </tr>
	 <tr>
		<td class="list_bg2" align="right" width="17%">ʩ����Ա*</td>
		<td class="list_bg1" colspan="3" align="left" width="83%"> 
		<% String copyOpSystemSetting=Postern.getSystemsettingValueByName("SET_W_COPYOPERATORNAME");
			if(copyOpSystemSetting!=null&&("Y").equals(copyOpSystemSetting)){%>
			<input type="text" name="txtProcessMan" size="25"  value="<op:curoper property="operatorName" />"   >
			<%}else{%>
			<input type="text" name="txtProcessMan" size="25"  value="<tbl:writeparam name="txtProcessMan" />"   >
			<%}%>
		</td>
	 </tr>
	 <tr>
		<td class="list_bg2" align="right" width="17%">ʩ���ɹ�����</td>
		<td class="list_bg1" colspan="3" align="left" width="83%"> 
			<input type="text" name="txtMemo" size="83" maxlength="200" value="<tbl:writeparam name="txtMemo" />">
		</td>
	</tr>
	<tr>
		<td class="list_bg2" align="right" width="17%">ʩ��ʧ��ԭ��</td>
		<td class="list_bg1" align="left"  width="33%"> 
			<o:selcmn name="txtWorkResultReason" mapName="SET_W_INSTALLFAILREASON" width="25" />
		</td>
		<td class="list_bg2" align="right" width="17%">����ԭ������</td>
    <td class="list_bg1" align="left"  width="33%"> 
      <o:selcmn name="txtOutOfDateReason" mapName="SET_W_JOBOUTOFDATEREASON" match="txtOutOfDateReason" width="25" />
    </td>	 
	</tr>
	<tr>
		<td class="list_bg2" align="right" width="17%">ʩ��ʧ������</td>
		<td class="list_bg1" colspan="3" align="left" width="83%"> 
			<input type="text" name="txtFaildComment" size="83" maxlength="200" value="">
		</td>
	</tr> 

        
 </table>
  <input type="hidden" name="func_flag" value="102" >
  <input type="hidden" name="txtIsSuccess" value="" >
  <input type="hidden" name="txtDoPost" value="true" >
  <input type="hidden" name="txtDtLastmod" value="<tbl:write name="oneline" property="DtLastmod" />" >   
  <input type="hidden" name="txtcsiPayOption" value="" >
  <input type="hidden" name="txtCustomerID" value="" >
  <input type="hidden" name="txtAccountID" value="" >
  <input type="hidden" name="txtCsiType" value="" >
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
       <td background="img/button_bg.gif"><a href="<bk:backurl property="pi_query_result_for_register.do/job_card_view.do" />" class="btn12">��&nbsp;��</a></td>           
       <td><img src="img/button2_l.gif" width="11" height="20"></td>
       <td width="20" ></td>
       <td><img src="img/button_l.gif" border="0" ></td>
       <td background="img/button_bg.gif"  ><a href="javascript:frm_submit1('')" class="btn12">ȷ&nbsp;��</a></td>
       <td><img src="img/button_r.gif" border="0" ></td>
       <td width="20"></td>
           
    </tr>
  </table>    


 </form>  

 
 

   

 