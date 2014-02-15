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
   if (check_Blank(document.frmPost.txtProcessMan, true, "安装人员"))
	  	return false;
   if (check_Blank(document.frmPost.txtWorkDate, true, "上门时间"))
		  return false;	
	 if (!check_TenDate(document.frmPost.txtWorkDate, true, "上门时间"))
			return false;
	  
   if (check_Blank(document.frmPost.txtWorkTime, true, "上门时间段"))
		  return false;				
   if (document.frmPost.txtFaildComment.value != '' && document.frmPost.txtMemo.value != '') {
        alert("不允许同时输入安装成功及失败原因描述");
        return false;
   }

   return true;
}
function frm_submit1(csipaymentOption){
	var csi_payment_option_sp ='<%=CommonKeys.CSI_PAYMENT_OPTION_SP%>';

  if (check_frm()&& check_Bidimconfig()){
	   if (document.frmPost.txtWorkResultReason.value!=''){
	      alert('安装成功，请不要选择安装失败原因。');
	      document.frmPost.txtWorkResultReason.focus();
	      return;
	   }
	   if (document.frmPost.txtFaildComment.value!=''){
	     alert('安装成功，请不要填写失败原因描述。');
	     document.frmPost.txtFaildComment.focus();
	     document.frmPost.txtFaildComment.select();
	     return;
	   }
	   
	   document.frmPost.txtIsSuccess.value='true';
	   if (csi_payment_option_sp == csipaymentOption){
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
	    if (check_Blank(document.frmPost.txtWorkResultReason, true, "安装失败原因"))
		      return;

	    if (check_Blank(document.frmPost.txtFaildComment, true, "失败原因描述"))
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
        <td class="title">录入安装反馈信息</td>
      </tr>
    </table>
    <table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
       <tr>
         <td><img src="img/mao.gif" width="1" height="1"></td>
       </tr>
    </table>
    <br>
 
<form name="frmPost" method="post" action="" >
<lgc:bloop requestObjectName="ResponseQueryResult" item="oneline" isOne="true">
  <%
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
     String serviceID = Postern.getServiceIDByCSIID(wrap.getJobCardDto().getReferSheetId());
  %>

 <table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
   <tr>
      <td class="list_bg2" align="right" width="17%">工单编号</td>
      <td class="list_bg1" align="left"  width="33%">
	      <input type="text" name="txtJobCardID" size="25"  value="<tbl:write name="oneline" property="JobCardId" />" class="textgray" readonly   >
	    </td>
	    <td class="list_bg2" align="right" width="17%">受理单编号</td>
      <td class="list_bg1" align="left"  width="33%">
	       <input type="text" name="txtReferSheetID" size="25"  value="<tbl:write name="oneline" property="ReferSheetId" />" class="textgray" readonly   >
	    </td>
	 </tr>
	 <tr>
		<td class="list_bg2"  align="right" width="17%">预约上门时间</td>
		<td class="list_bg1"  align="left"  width="33%"> 
			  <input type="text" name="txtPreferedDate" size="25"  value="<tbl:writedate name="oneline" property="PreferedDate"/>" class="textgray" readonly>   
    </font></td>
		<td class="list_bg2"  align="right" width="17%">预约上门时间段</td>
		<td class="list_bg1"  align="left"  width="33%">
		   <input type="text" name="txtPreferedTime"  size="25"  value="<o:getcmnname typeName="SET_C_CSIPREFEREDTIME" match="oneline:PreferedTime" />" class="textgray" readonly > 
		</td>
	 </tr>
	 <tr>
		<td class="list_bg2"  align="right" width="17%">上门时间*</td>
		<td class="list_bg1"  align="left"  width="33%"> 
			<input maxlength="10" onfocus="viewModule(this)" onKeydown="inputDate(document.frmPost.txtWorkDate)" onblur="lostFocus(this)" type="text" name="txtWorkDate" size="25"  value="<tbl:writeparam name="txtWorkDate" />"/> <IMG onclick="MyCalendar.SetDate(this,document.frmPost.txtWorkDate,'Y')" src="img/calendar.gif" style=cursor:hand border="0">        
		</td>
		<td class="list_bg2" align="right" width="17%">上门时间段*</td>
		<td class="list_bg1" align="left"  width="33%"> <tbl:select name="txtWorkTime" set="AllPREFEREDTIME" match="txtWorkTime" width="25" /></td>
	 </tr>
	 <tr>
		<td class="list_bg2" align="right" width="17%">安装人员*</td>
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
		<td class="list_bg2" align="right" width="17%">安装成功描述</td>
		<td class="list_bg1" colspan="3" align="left" width="83%"> 
			<input type="text" name="txtMemo" size="83" maxlength="200" value="<tbl:writeparam name="txtMemo" />">
		</td>
	</tr>
	<tr>
		<td class="list_bg2" align="right" width="17%">安装失败原因</td>
		<td class="list_bg1" align="left"  width="33%"> 
			<o:selcmn name="txtWorkResultReason" mapName="SET_W_INSTALLFAILREASON" width="25" />
		</td>
		<td class="list_bg2" align="right" width="17%">逾期原因描述</td>
    <td class="list_bg1" align="left"  width="33%"> 
      <o:selcmn name="txtOutOfDateReason" mapName="SET_W_JOBOUTOFDATEREASON" match="txtOutOfDateReason" width="25" />
    </td>	 
	</tr>
	<tr>
		<td class="list_bg2" align="right" width="17%">安装失败描述</td>
		<td class="list_bg1" colspan="3" align="left" width="83%"> 
			<input type="text" name="txtFaildComment" size="83" maxlength="200" value="">
		</td>
	</tr> 
   
  <tbl:dynamicservey serviceID="<%=serviceID%>" serveyName="txtDynamicServey" serveyType="U"  serveySubType="IU" showType="text" tdWordStyle="list_bg2" tdControlStyle="list_bg1" controlSize="23" tdWidth1="17%" tdWidth2="33%" defaultFlag="true" checkScricptName ="check_Bidimconfig()"/>  
        
 </table>
  <input type="hidden" name="func_flag" value="102" >
  <input type="hidden" name="txtIsSuccess" value="" >
  <input type="hidden" name="txtDoPost" value="true" >
  <input type="hidden" name="txtDtLastmod" value="<tbl:write name="oneline" property="DtLastmod" />" >   
  <input type="hidden" name="txtcsiPayOption" value="<%=csiDto.getBillCollectionMethod()%>" >
  <input type="hidden" name="txtCustomerID" value="<%=csiDto.getCustomerID()%>" >
  <input type="hidden" name="txtAccountID" value="<%=csiDto.getAccountID()%>" >
  <input type="hidden" name="txtCsiType" value="<%=csiDto.getType()%>" >
  <input type="hidden" name="txtServiceID" value="<%=serviceID%>">
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
       <td background="img/button_bg.gif"><a href="<bk:backurl property="pi_query_result_for_register.do/job_card_view.do/job_card_query_result_for_contact_of_install.do" />" class="btn12">返&nbsp;回</a></td>           
       <td><img src="img/button2_l.gif" width="11" height="20"></td>
       <td width="20" ></td>
       <td><img src="img/button_l.gif" border="0" ></td>
       <td background="img/button_bg.gif"  ><a href="javascript:frm_submit1('<%=csiDto.getBillCollectionMethod()%>')" class="btn12">安装成功</a></td>
       <td><img src="img/button_r.gif" border="0" ></td>
       <td width="20"></td>
       <td><img src="img/button_l.gif" border="0" ></td>
       <td background="img/button_bg.gif"  >
	         <a href="javascript:frm_submit2()" class="btn12">安装失败</a></td>
       <td><img src="img/button_r.gif" border="0" ></td>
           
    </tr>
  </table>    
 </lgc:bloop> 
 <tbl:generatetoken />
 </form>  

 
 

   

 