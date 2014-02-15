<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="resultset" prefix="rs" %>
<%@ taglib uri="osstags" prefix="o" %>
<%@ page import="com.dtv.oss.util.*" %>
<%@ page import="com.dtv.oss.web.util.WebUtil" %>
<%@ page import="com.dtv.oss.service.commandresponse.QueryCommandResponseImpl"%>
<%@ page import="java.util.*" %>

<script language=javascript>
function checkData(){
	if (document.frmPost.txtStatBeginTime.value != ''){
		if (!check_TenDate(document.frmPost.txtStatBeginTime, true, "ͳ��ʱ��ο�ʼ����")){
			return false;
		}
	}
	if (document.frmPost.txtStatEndTime.value != ''){
		if (!check_TenDate(document.frmPost.txtStatEndTime, true, "ͳ��ʱ��ν�������")){
			return false;
		}
	}
	if(!compareDate(document.frmPost.txtStatBeginTime,document.frmPost.txtStatEndTime,"�������ڱ�����ڵ��ڿ�ʼ����")){
		 return false;
	}
 
  if (document.frmPost.txtStatBeginTime.value =='' || document.frmPost.txtStatEndTime.value == ''){
  	 alert("ͳ��ʱ��ο�ʼ������ͳ��ʱ��ν������ڲ���Ϊ��");
  	 return false;
  }
  if (document.frmPost.txtDistrictID.value ==''){
  	 alert("����������Ϊ�գ�");
  	 return false;
  }
  	
	return true;
}

function query_submit(){ 	
    if (checkData()){
		   document.frmPost.txtAct.value="query";
		   document.frmPost.submit();
		}
}

function out_submit(){
 	  var tempValue ="";
 	  var txtBatchStr =document.frmPost.txtBatchStr.value;
	  tempValue ="&txtBatchStr="+txtBatchStr;
	  var txtStatBeginTime =trim(document.frmPost.txtStatBeginTime.value);
	  if (txtStatBeginTime !='') tempValue =tempValue +"&txtStatBeginTime="+txtStatBeginTime;
	  var txtStatEndTime =trim(document.frmPost.txtStatEndTime.value);
	  if (txtStatEndTime !='') tempValue =tempValue +"&txtStatEndTime="+txtStatEndTime;
	  var txtDistrictID = document.frmPost.txtDistrictID.value;
	  tempValue =tempValue +"&txtDistrictID="+txtDistrictID;
	  
	  window.open('csistat_for_huairou_excel.do?pageCode=csistat_for_huairou_query_excel&txtAct=out'+tempValue,'',
                 'toolbar=no,location=no,status=no,menubar=no,scrollbars=yes,width=430,height=350');		
}
</script>
<%
  //���ڶα���
  String txtStatBeginTime = (request.getParameter("txtStatBeginTime")==null ) ? "" :request.getParameter("txtStatBeginTime");
  String txtStatEndTime = (request.getParameter("txtStatEndTime")==null) ? "" : request.getParameter("txtStatEndTime");
  if (txtStatBeginTime.equals("") && txtStatEndTime.equals("")){
     txtStatEndTime =WebUtil.TimestampToString(TimestampUtility.getSystemDate(),"yyyy-MM")+"-25";
     String tempDateStr =WebUtil.TimestampToString(TimestampUtility.getSystemDate(),"yyyy-MM")+"-26";
     java.sql.Timestamp tempDateTime =TimestampUtility.StringToTimestamp(tempDateStr);
     txtStatBeginTime =WebUtil.TimestampToString(TimestampUtility.getTimeEnd(tempDateTime,"M",-1),"yyyy-MM-dd");
  }
  String  txtBatchStr =(request.getAttribute("txtBatchStr")==null) ? "" :(String)request.getAttribute("txtBatchStr");
%>


<table width="820">
<table border="0" align="center" cellpadding="0" cellspacing="10">
  <tr>
    <td><img src="img/list_tit.gif" width="19" height="19"></td>
    <td  class="title">����ҵ������ͳ��</td>
  </tr>
</table>
<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
   <tr>
    	<td><img src="img/mao.gif" width="1" height="1"></td>
   </tr>
 </table>
<br>
<form name="frmPost" method="post" action="csistat_for_huairou.do" >
   <table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
      <tr>
			   <td class="list_bg2" align="right" width="30%">ͳ��ʱ���</td>
         <td class="list_bg1">
    	    <input name="txtStatBeginTime" type="text" class="text" maxlength="10" size="10" value="<%=txtStatBeginTime%>">
    	    <IMG onclick="calendar(document.frmPost.txtStatBeginTime)" src="img/calendar.gif" style=cursor:hand border="0">
    	     ---
    	    <input name="txtStatEndTime" type="text" class="text" maxlength="10" size="10" value="<%=txtStatEndTime%>">
    	    <IMG onclick="calendar(document.frmPost.txtStatEndTime)" src="img/calendar.gif" style=cursor:hand border="0">
         </td>
       </tr>
       <tr>
          <td class="list_bg2" align="right">��������</td> 
          <td class="list_bg1"  valign="middle">
            <input type="hidden" name="txtDistrictID" value="<tbl:writeparam name="txtDistrictID" />">
            <textarea name="txtCountyDesc" length="5" cols=100 rows=9 readonly ><tbl:writeparam name="txtCountyDesc" /></textarea>
	          <input name="selDistButton" type="button" class="button" value="ѡ��" onClick="javascript:sel_districts('all','txtDistrictID','txtCountyDesc','checkbox')">
          </td>
       </tr>
   </table>
    
   <table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
	   <tr align="center">
	      <td class="list_bg1">
	      <table  border="0" cellspacing="0" cellpadding="0">
		      <tr>
				    <td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
			      <td><input name="search" type="button" class="button" value="�� ѯ" onclick="javascript:query_submit()"></td>
		  	    <td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
			<%
			   Collection returnList =null;
			   QueryCommandResponseImpl RepCmd = (QueryCommandResponseImpl)pageContext.getRequest().getAttribute("ResponseQueryResult");
			   if (RepCmd !=null) returnList = (Collection) RepCmd.getPayload();
			   if (returnList !=null && !returnList.isEmpty()){
			%>
			      <td width="20" ></td>
			      <td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
            <td align="center"><input name="Submit2" type="button" class="button" onClick="javascript:out_submit()"  value="�� ��"></td>
            <td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
      <%
         } else {
       %>
            <td colspan="4">&nbsp;</td>
       <%
         }
       %>
		      </tr>
		   </table>
	  </td>

	</tr>
 </table> 
       
 <input type="hidden" name="txtFrom"  value="1">
 <input type="hidden" name="txtTo"  value="10">
 <input type="hidden" name="txtAct"  value="">
 <input type="hidden" name="txtBatchStr" value="<%=txtBatchStr%>" >
  
 <table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
   <tr>
    	<td><img src="img/mao.gif" width="1" height="1"></td>
   </tr>
 </table>

 <rs:hasresultset>
 <br>  
 <table width="1010"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
     <tr class="list_head">   
     	  <td class="list_head" rowspan="3">����</td>
        <td class="list_head" colspan="8">�¿��û� </td> 
        <td class="list_head" colspan="16">���û��ɷ� </td>        
        <td class="list_head" rowspan="3">����������ͣҵ���ʻ��� </td>
        <td class="list_head" rowspan="3" width=40 nowrap>�ͻ��� </td>
        <td class="list_head" rowspan="3" width=40 nowrap>����ҵ���ʻ��� </td>
        <td class="list_head" rowspan="3">������ͣҵ���ʻ��� </td>
        <td class="list_head" rowspan="3">ǿ����ͣҵ���ʻ��� </td>
       </tr>
       <tr class="list_head">
          <td class="list_head" colspan="3">��װ��>0 </td>
          <td class="list_head" colspan="3">��װ��=0,���ӷѡ�0 </td>
          <td class="list_head" colspan="2">��װ��=0�����ӷ�=0 </td>
       	  <td class="list_head" colspan="2">���ӷ� </td>
          <td class="list_head" colspan="2">�źŶ�ͨ�� </td>
          <td class="list_head" colspan="2">�˿ڷ� </td>
          <td class="list_head" colspan="2">�ն˷� </td>
          <td class="list_head" colspan="2">������ </td>
          <td class="list_head" colspan="2">Ǩ�Ʒ� </td>
          <td class="list_head" colspan="2">������ </td>
          <td class="list_head" colspan="2">С�� </td>
       </tr>
		   <tr class="list_head">
          <td class="list_head" >�ͻ��� </td>
          <td class="list_head" >ҵ���ʻ��� </td>
          <td class="list_head" width=40 nowrap>�ɷѽ�� </td>
          <td class="list_head" >�ͻ��� </td>
          <td class="list_head" >ҵ���ʻ��� </td>
          <td class="list_head" width=40 nowrap>�ɷѽ�� </td>
          <td class="list_head" >�ͻ��� </td>
          <td class="list_head" >ҵ���ʻ��� </td>  
          
          <td class="list_head" width=40 nowrap>��� </td>
          <td class="list_head" width=40 nowrap>���� </td>
          <td class="list_head" width=40 nowrap>��� </td>
          <td class="list_head" >���� </td>
          <td class="list_head" >��� </td>
          <td class="list_head" >���� </td>
          <td class="list_head" >��� </td>
          <td class="list_head" >���� </td>
          <td class="list_head" >��� </td>
          <td class="list_head" >���� </td>
          <td class="list_head" >��� </td>
          <td class="list_head" >���� </td>
          <td class="list_head" >��� </td>
          <td class="list_head" >���� </td>
          <td class="list_head" width=40 nowrap>��� </td>
          <td class="list_head" width=40 nowrap>���� </td>
       </tr>
       <lgc:bloop requestObjectName="ResponseQueryResult" item="oneline" >
       	<%
       	  Collection resultCols = (Collection)pageContext.getAttribute("oneline");
       	  Iterator resultIter =resultCols.iterator();
       	  while (resultIter.hasNext()) {
       %>
        <tbl:printcsstr style1="list_bg1" style2="list_bg2" overStyle="list_over" > 
        	<td align="center" ><%=resultIter.next()%></td>  
          <td align="center" ><%=resultIter.next()%></td>  
          <td align="center" ><%=resultIter.next()%></td>
          <td align="center" ><%=resultIter.next()%></td>
          <td align="center" ><%=resultIter.next()%></td>
          <td align="center" ><%=resultIter.next()%></td>
          <td align="center" ><%=resultIter.next()%></td>	  
          <td align="center" ><%=resultIter.next()%></td>
          <td align="center" ><%=resultIter.next()%></td>
          <td align="center" ><%=resultIter.next()%></td>
          <td align="center" ><%=resultIter.next()%></td>
          <td align="center" ><%=resultIter.next()%></td>
          <td align="center" ><%=resultIter.next()%></td>
          <td align="center" ><%=resultIter.next()%></td>	  
          <td align="center" ><%=resultIter.next()%></td>
          <td align="center" ><%=resultIter.next()%></td>       
          <td align="center" ><%=resultIter.next()%></td>	  
          <td align="center" ><%=resultIter.next()%></td>
          <td align="center" ><%=resultIter.next()%></td>
          <td align="center" ><%=resultIter.next()%></td>
          <td align="center" ><%=resultIter.next()%></td>
          <td align="center" ><%=resultIter.next()%></td>
          <td align="center" ><%=resultIter.next()%></td>
          <td align="center" ><%=resultIter.next()%></td>	  
          <td align="center" ><%=resultIter.next()%></td>
          <td align="center" ><%=resultIter.next()%></td>
          <td align="center" ><%=resultIter.next()%></td>	  
          <td align="center" ><%=resultIter.next()%></td>
          <td align="center" ><%=resultIter.next()%></td>
           <td align="center" ><%=resultIter.next()%></td>
         </tbl:printcsstr>
       <%
           }    
       %>
        </lgc:bloop>
       <tr>
         <td colspan="30" class="list_foot"></td>
       </tr>
      <tr class="trline" >
        <td align="right" class="t12" colspan="30" >
          ��<span class="en_8pt"><rs:prop property="curpageno" /></span>ҳ
          <span class="en_8pt">/</span>��<span class="en_8pt"><rs:prop property="pageamount" />ҳ
          ����<span class="en_8pt"><rs:prop property="recordcount" /></span>����¼&nbsp;&nbsp;&nbsp;&nbsp;             
          <rs:notfirst>
          <img src="img/dot_top.gif" width="17" height="11">
          <a href="javascript:firstPage_onClick(document.frmPost, <rs:prop property="firstto" />)" >��ҳ </a>
          <img src="img/dot_pre.gif" width="6" height="11">
          <a href="javascript:previous_onClick(document.frmPost, <rs:prop property="prefrom" />, <rs:prop property="preto" />)" >��һҳ</a>
          </rs:notfirst>
          
          <rs:notlast>
          &nbsp;
          <img src="img/dot_next.gif" width="6" height="11">
          <a href="javascript:next_onClick(document.frmPost, <rs:prop property="nextfrom" />, <rs:prop property="nextto" />)" >��һҳ</a>
          <img src="img/dot_end.gif" width="17" height="11">
          <a href="javascript:lastPage_onClick(document.frmPost, <rs:prop property="lastfrom" />, <rs:prop property="lastto" />)" >ĩҳ</a>
          </rs:notlast>
          &nbsp;
          ת��
          <input type="text" name="txtPage" class="page_txt">ҳ 
          <a href="javascript:goto_onClick(document.frmPost, <rs:prop property="pageamount" />)" >
               <input name="imageField" type="image" src="img/button_go.gif" width="28" height="15" border="0">
          </a>
        </td>
      </tr>    
      </table> 
    </rs:hasresultset>
</form>  


