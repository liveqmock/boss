<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="resultset" prefix="rs" %>
<%@ taglib uri="osstags" prefix="o" %>
<%@ taglib uri="bookmark" prefix="bk" %>

<%@ page import="com.dtv.oss.dto.JobCardDTO" %>
<%@ page import="com.dtv.oss.util.Postern" %>

<script language=javascript>

function check_frm()
{
   return true;
}

 function historyRecord(){
        var txtJobCardID =document.frmPost.txtJobCardID.value;
       
        document.frmPost.action ="job_card_process_log.do?txtJobCardID="+txtJobCardID;
        document.frmPost.submit();
        }
function frm_submit()
{
    if (check_frm())document.frmPost.submit();
    
} 
 

 
</script>
 <table width="820" align="center" cellpadding="0" cellspacing="0">
<tr> 
<td align="center" valign="top"><table  border="0" align="center" cellpadding="0" cellspacing="10">
      <tr>
        <td><img src="img/list_tit.gif" width="19" height="19"></td>
        <td class="title">����ʩ����</td>
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
    com.dtv.oss.dto.wrap.work.JobCard2JobCardProcessWrap   wrap=(com.dtv.oss.dto.wrap.work.JobCard2JobCardProcessWrap)pageContext.getAttribute("oneline");
    pageContext.setAttribute("oneline", wrap.getJobCardDto());
    pageContext.setAttribute("jobcardprocess",  wrap.getJobCardProcessDto());
    JobCardDTO dto=wrap.getJobCardDto();
    pageContext.setAttribute("jobcardprocess",  wrap.getJobCardProcessDto());
    String strAddress = Postern.getAddressById(dto.getAddressId());
    if (strAddress==null) strAddress="";
    
%>
<form name="frmPost" method="post" action="install_close.do" >
 <table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
        <tr>
        <td class="list_bg2"><div align="right">�������</div></td>
        <td class="list_bg1">
	<input type="text" name="txtJobCardID" size="25"  value="<tbl:write name="oneline" property="JobCardId" />" class="textgray" readonly   >
	<td class="list_bg2"><div align="right">��ϸ��ַ</div></td>
		 <td class="list_bg1">
			<input type="text" name="txtAddressId" size="25"  value="<%=strAddress%>" class="textgray" readonly   >
		 </td> 
	</tr>
	 
	<tr>
		<td class="list_bg2" ><div align="right">��ϵ��*</div></td>
		<td class="list_bg1"> 
			<input type="text" name="txtContactPerson" size="25"  value="<tbl:write name="oneline" property="contactPerson" />"  class="textgray" readonly  >
		 </td>
		<td class="list_bg2"><div align="right">��ϵ�绰*</div></td>
		<td class="list_bg1"><input type="text" name="txtContactPhone" size="25"  value="<tbl:write name="oneline" property="contactPhone" />"  class="textgray" readonly  >
		</td>
	</tr>
	 
	 <tr>
	        <td class="list_bg2" ><div align="right">��ע��Ϣ</div></td>
		<td class="list_bg1" colspan="3"> 
			<input type="text" name="txtMemo" size="83" maxlength="200" value="<tbl:write name="jobcardprocess" property="Memo" />">
		 </td>
	</tr>
        
  </table>
  <input type="hidden" name="func_flag" value="104" >
<br> 
</lgc:bloop> 
 <table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
        <tr>
          <td><img src="img/mao.gif" width="1" height="1"></td>
        </tr>
      </table>
  <br>      
 <table align="center" border="0" cellspacing="0" cellpadding="0">
        <tr> 
           <td><img src="img/button2_r.gif" width="22" height="20"></td>
            <td background="img/button_bg.gif"  ><a href="<bk:backurl property="catv_query_result_for_close.do/job_card_view.do" />" class="btn12">��&nbsp;��</a></td>           
            <td><img src="img/button2_l.gif" width="11" height="20"></td>
            <td width="20" ></td>
          <td><img src="img/button_l.gif" border="0" ></td>
          <td background="img/button_bg.gif"  ><a href="javascript:frm_submit()" class="btn12">����ʩ��</a></td>
          <td><img src="img/button_r.gif" border="0" ></td>
          
        
 
          <td width="20"></td>
	  <td><img src="img/button_l.gif" border="0" ></td>
          <td background="img/button_bg.gif"  ><a href="javascript:historyRecord()"  class="btn12">��ʷ��¼</a></td>
          <td><img src="img/button_r.gif" border="0" ></td>
 
        </tr>
      </table>    
     <input type="hidden" name="txtFrom"  value="1">
     <input type="hidden" name="txtTo"  value="10">
        
     
     
  
 </form>  

 
 

   

 