<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="resultset" prefix="rs" %>
<%@ taglib uri="osstags" prefix="o" %>
<%@ taglib uri="bookmark" prefix="bk" %>
<%@ taglib uri="operator" prefix="op" %>

<%@ page import="com.dtv.oss.dto.JobCardDTO" %>
<%@ page import="com.dtv.oss.dto.CustomerProblemDTO" %>
<%@ page import="com.dtv.oss.util.Postern" %>
<%@ page import="com.dtv.oss.web.util.WebUtil"%>

<script language=javascript>
 
function checkform(){
  if (!document.frmPost.txtID[0].checked && !document.frmPost.txtID[1].checked ) {
       alert("��ѡ��ά�޳ɹ�����ά��ʧ��");
       return false;
  }  
       
  if (check_Blank(document.frmPost.txtProcessMan, true, "ά����Ա")){
		return false;
	} 
	if (check_Blank(document.frmPost.txtWorkDate, true, "����ʱ��"))
	  	return false;	
  if (check_Blank(document.frmPost.txtWorkTime, true, "����ʱ���"))
		 return false;		
		 
   return true;
} 
 
function sucess_submit(){
   if (document.frmPost.txtID[0].checked){
       document.frmPost.txtWorkResultReason.disabled=true;
	     document.frmPost.txtFaildComment.disabled=true;
	     document.frmPost.txtTroubleType.disabled=false;
	     document.frmPost.txtTroubleSubType.disabled=false;
	      
	     document.frmPost.txtResolutionType.disabled=false;
	     document.frmPost.txtSucessComment.disabled=false;
	  } else {
       	     document.frmPost.txtWorkResultReason.disabled=false;
	     document.frmPost.txtFaildComment.disabled=false;
       	     document.frmPost.txtTroubleType.disabled=true;
	     document.frmPost.txtTroubleSubType.disabled=true;
	     
	     document.frmPost.txtResolutionType.disabled=true;
	     document.frmPost.txtSucessComment.disabled=true;
    }
 
} 

function frm_submit1(){ 
  if(checkform()){ 
    if (document.frmPost.txtID[0].checked){
	     document.frmPost.isSuccess.value="true";
	     document.frmPost.txtDoPost.value="FALSE";
	     document.frmPost.action="register_repair_fee.do";
	     document.frmPost.submit();
    }

   if (document.frmPost.txtID[1].checked){
   	  if (check_reason() && check_Bidimconfig() && check_Bidimconfig1()){
		document.frmPost.isSuccess.value="false";
         	document.frmPost.txtDoPost.value="TRUE";
         	document.frmPost.action="register_repair_op.do"
	        document.frmPost.submit();
	    }
    }
  }
}

function check_reason(){
	if(document.frmPost.txtWorkResultReason.value == '' || document.frmPost.txtFaildComment.value == ''){
		alert("��ѡ��ά��ʧ��ԭ�������!");
		return false;
	}
	return true;
} 
 
</script>
 <table width="820" align="center" cellpadding="0" cellspacing="0">
<tr> 
<td align="center" valign="top"><table  border="0" align="center" cellpadding="0" cellspacing="10">
      <tr>
        <td><img src="img/list_tit.gif" width="19" height="19"></td>
        <td class="title">¼��ά����Ϣ</td>
      </tr>
    </table>
      <table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
        <tr>
          <td><img src="img/mao.gif" width="1" height="1"></td>
        </tr>
      </table>
      <br>
<%
      String jobcardID = request.getParameter("txtJobCardID");
      
      JobCardDTO   dto=Postern.getJobCardDto(WebUtil.StringToInt(jobcardID));
      String serviceID = Postern.getServiceIDByAcctServiceID(dto.getCustServiceAccountId())+"";
      int custId = dto.getCustId();
      pageContext.setAttribute("oneline", dto);
       
      String strAddress = Postern.getAddressById(dto.getAddressId());
      if (strAddress == null) strAddress="";
      String timeFlag = "SET_C_CSIPREFEREDTIME";
      Map mapPreferedTime = Postern.getAllPreferedTime(timeFlag);
     
      pageContext.setAttribute("AllPREFEREDTIME", mapPreferedTime);
      
      CustomerProblemDTO custProblemDto =Postern.getCustomerProblemDto(dto.getReferSheetId());
  
%>
<form name="frmPost" method="post" >
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
	  <td class="list_bg2"><div align="right">ԤԼ����ʱ��</div></td>
	  <td class="list_bg1"> 
		<input type="text" name="txtPreferedDate" size="25"  value="<tbl:writedate name="oneline" property="PreferedDate"/>" class="textgray" readonly>   
           </font></td>
		 
	<td class="list_bg2"><div align="right">ԤԼ����ʱ���</div></td>
	<td class="list_bg1">
	 <input type="text" name="txtPreferedTime"  size="25"  value="<o:getcmnname typeName="SET_C_CSIPREFEREDTIME" match="oneline:PreferedTime" />" class="textgray" readonly > 
	</td>
		 
	</tr>
	<tr>
		 
		<td class="list_bg2"><div align="right">����ʱ��*</div></td>
		<td class="list_bg1"> 
			<input type="text" name="txtWorkDate" size="25"  value="<tbl:writeparam name="txtWorkDate" />" > <IMG onclick="calendar(document.frmPost.txtWorkDate)" src="img/calendar.gif" style=cursor:hand border="0">
            
		</td>
		 
		<td class="list_bg2"><div align="right">����ʱ���*</div></td>
		<td class="list_bg1"> <tbl:select name="txtWorkTime" set="AllPREFEREDTIME" match="txtWorkTime" width="25" /></td>
		 
	</tr>
	 <tr>
	       <td class="list_bg2"><div align="right">��ϵ��*</div></td>
		 <td class="list_bg1">
		 <input type="text" name="txtContactPerson" size="25" maxlength="10" value="<tbl:write name="oneline" property="contactPerson" />" class="textgray" readonly  >
	       </td>
	       <td class="list_bg2"><div align="right">ά����Ա*</td>
               <td class="list_bg1"> 
                  <input type="text" name="txtProcessMan" size="25"  value="<op:curoper property="operatorName" />" >
               </td>	
	</tr> 
	<!--
	<tr>		
		<td class="list_bg2"><div align="right">����ԭ������</td>
		<td class="list_bg1" colspan="3" > 
			<o:selcmn name="txtOutofDateReason" mapName="SET_W_JOBOUTOFDATEREASON" match="txtOutofDateReason" width="25" />
		</td>
	</tr>
	-->
	 <tr>   
            <td colspan="4" class="import_tit" align="center"><input type="radio" name="txtID"  value= "success" onclick="javascript:sucess_submit()"/><font size="3">ά�޳ɹ�</font></td>
       </tr>
	 
	<tr>
	 
		<td class="list_bg2"><div align="right">��������</div></td>
		<td class="list_bg1"> 
			<o:selcmn name="txtTroubleType" mapName="SET_W_JOBCARDTROUBLETYPE" match="txtTroubleType" width="25" />
		 </td>
		 
		<td class="list_bg2"><div align="right">����ԭ��</div></td>
		<td class="list_bg1"> 
			<o:selcmn name="txtTroubleSubType" mapName="SET_W_JOBCARDTROUBLESUBTYPE" match="txtTroubleSubType"  width="25" />
		 </td>
	</tr>
	<tr>
		<td class="list_bg2"><div align="right">����ֶ�</div></td>
		<td class="list_bg1" colspan="3"> 
		<o:selcmn name="txtResolutionType" mapName="SET_W_JOBCARDRESOLUTIONTYPE" match="txtResolutionType" width="25" />
		</td>
		 
        </td>	
	<tr>
	 
	 
		<td class="list_bg2"><div align="right">ά�޳ɹ�����</div></td>
		<td class="list_bg1" colspan="3"> 
			<input type="text" name="txtSucessComment" size="83" maxlength="200" value="<tbl:writeparam name="txtSucessComment" />" >
		 </td>
	</tr>	 
	  <tr>    
            <td colspan="4" class="import_tit" align="center"><input type="radio" name="txtID"  value="failure" onclick="javascript:sucess_submit()" /><font size="3">ά��ʧ��</font></td>
       </tr>
	 <tr>
	 
	 
		<td class="list_bg2"><div align="right">ʧ��ԭ��</div></td>
		<td class="list_bg1" colspan="3">  
			 <o:selcmn name="txtWorkResultReason" mapName="SET_W_REPAIRFAILREASON" match="txtWorkResultReason" width="25" />
		 </td>
	</tr>
        
	 <tr>
	 
	 
		<td class="list_bg2"><div align="right">ά��ʧ������</div></td>
		<td class="list_bg1" colspan="3"> 
			<input type="text" name="txtFaildComment" size="83" maxlength="200" value="<tbl:writeparam name="txtFaildComment" />" >
		 </td>
	</tr>
  <tr> 
	   <td colspan="4" class="import_tit" align="center"><font size="3">�����Ϣ(���ά�޳ɹ�����Ҫ��д)</font></td>
  </tr>

	 <tbl:dynamicservey serviceID="<%=serviceID%>" serveyName="txtDiaName" serveyType="D"  serveySubType="P"  showType="text" tdWordStyle="list_bg2" tdControlStyle="list_bg1" controlSize="25" defaultFlag="true" checkScricptName ="check_Bidimconfig()"/>
  <tr> 
	   <td colspan="4" class="import_tit" align="center"><font size="3">����ʹ�����</font></td>
  </tr>
  <tbl:dynamicservey serveyName="txtMaterialName" serveyType="U" serveySubType="RU"  showType="text" tdWordStyle="list_bg2" tdControlStyle="list_bg1" controlSize="25" defaultFlag="true" checkScricptName ="check_Bidimconfig1()"/>
  
  </table>
  <input type="hidden" name="txtDtLastmod" value="<tbl:write name="oneline" property="DtLastmod" />" >  
  <input type="hidden" name="func_flag" value="1008" >  
  <input type="hidden" name="txtReferSheetId" value="<tbl:write name="oneline" property="ReferSheetId"/>" >  
  <input type="hidden" name="txtCustomerID" value="<tbl:write name="oneline" property="custId"/>" >  
  <input type="hidden" name="txtAccountID" value="<%=custProblemDto.getCustAccountID()%>" >  
  <input type="hidden" name="isSuccess" value="" >  
  <input type="hidden" name="txtDoPost" size="22" value="">
  <tbl:generatetoken />  
  	
  <table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
     <tr>
        <td><img src="img/mao.gif" width="1" height="1"></td>
     </tr>
  </table>
<br>
 <table align="center" border="0" cellspacing="0" cellpadding="0">
        <tr> 
         <td><img src="img/button2_r.gif" width="22" height="20"></td>  
          <td background="img/button_bg.gif">
	  <a href="<bk:backurl property="register_repair_info_query_result.do/job_card_view.do" />" class="btn12">��&nbsp;��</a></td>
          <td><img src="img/button2_l.gif" width="13" height="20"></td>
          <td width="20" ></td>
          <td><img src="img/button_l.gif" border="0" ></td> 
          <td background="img/button_bg.gif"  ><a href="javascript:frm_submit1()" class="btn12">�� ��</a></td>
          <td><img src="img/button_r.gif" border="0" ></td>
         
      </tr>
      </table>    
 
     
       <br>  
     
  
 </form>  

 
 

   

 