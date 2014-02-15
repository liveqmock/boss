<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="resultset" prefix="rs" %>
<%@ taglib uri="osstags" prefix="o" %>
<%@ page import="com.dtv.oss.util.Postern" %>
<%@ page import="com.dtv.oss.web.util.WebUtil" %>
<%@ page import="com.dtv.oss.service.commandresponse.QueryCommandResponseImpl"%>
<%@ page import="java.util.*" %>
<%@ page import="java.io.*" %>
<%@ page import="com.dtv.oss.util.* " %>

<script language=javascript>
	 function check_form(){
	 	  //��������
	 	  if (document.frmPost.txtDistrictID.value ==''){
	 	     alert("��������ѡ����Ϊ��!");
	 	     return false;
	 	  }
	 	  if (document.frmPost.txtStatBeginTime.value ==''){
	 	  	 alert("ͳ��ʱ��ο�ʼ���ڲ���Ϊ�գ�");
	 	  	 return false;
	 	  }
	 	  if (document.frmPost.txtStatEndTime.value ==''){
	 	  	 alert("ͳ��ʱ��ν������ڲ���Ϊ�գ�");
	 	  	 return false;
	 	  }
	 	  
	    //ͳ��ʱ���
      if (!check_TenDate(document.frmPost.txtStatBeginTime, true, "ͳ��ʱ��ο�ʼ����")){
			    return false;
		  }
 
      if (!check_TenDate(document.frmPost.txtStatEndTime, true, "ͳ��ʱ��ν�������")){
			    return false;
		  }
		  
	    if (!compareDate(document.frmPost.txtStatBeginTime,document.frmPost.txtStatEndTime,"ͳ��ʱ��������ڱ�����ڵ���ͳ��ʱ�俪ʼ����")){	
		       return false;
	    }
	    return true;
	 }
	 function query_submit(){
	 	  document.frmPost.txtAct.value="query";
      if (check_form()) document.frmPost.submit();
	 }
	 function out_submit(){ 
	 	  if (check_form()){
	 	  	 var tempValue ="";
	 	  	 var txtDistrictID =document.frmPost.txtDistrictID.value;
	  	   tempValue ="&txtDistrictID="+txtDistrictID;
	  	   var txtpayStyle =document.frmPost.txtpayStyle.value;
	  	   if(txtpayStyle !=''){
	          tempValue =tempValue+"&txtpayStyle="+txtpayStyle;
	       }
	       var txtStatBeginTime =document.frmPost.txtStatBeginTime.value;
	       tempValue =tempValue+"&txtStatBeginTime="+txtStatBeginTime;
	       var txtStatEndTime =document.frmPost.txtStatEndTime.value;
	       tempValue =tempValue+"&txtStatEndTime="+txtStatEndTime;
	       var txtCollectorID =document.frmPost.txtCollectorID.value;
	       if (txtCollectorID !=''){
	       	   tempValue =tempValue+"&txtCollectorID="+txtCollectorID;
	       }
         window.open('freetaxi_report_excel.do?pageCode=freetaxi_report_excel&txtAct=out'+tempValue,'',
                 'toolbar=no,location=no,status=no,menubar=no,scrollbars=yes,width=430,height=350');		

	 	  }
	 }
	 function clear_select(id,name){
	    var idobj=eval("document.frmPost."+id);
	    idobj.value="";
	    var nameobj =eval("document.frmPost."+name);
	    nameobj.value="";
   }
	 
</script>
<%
  String txtStatBeginTime = (request.getParameter("txtStatBeginTime")==null ) ? "" :request.getParameter("txtStatBeginTime");
  String txtStatEndTime = (request.getParameter("txtStatEndTime")==null) ? "" : request.getParameter("txtStatEndTime");
  if (txtStatBeginTime.equals("") && txtStatEndTime.equals("")){
     txtStatEndTime =WebUtil.TimestampToString(TimestampUtility.getSystemDate(),"yyyy-MM")+"-25";
     String tempDateStr =WebUtil.TimestampToString(TimestampUtility.getSystemDate(),"yyyy-MM")+"-26";
     java.sql.Timestamp tempDateTime =TimestampUtility.StringToTimestamp(tempDateStr);
     txtStatBeginTime =WebUtil.TimestampToString(TimestampUtility.getTimeEnd(tempDateTime,"M",-1),"yyyy-MM-dd");
  }
  
  Map paystyleMp =new HashMap();
  paystyleMp.put("A","�ֽ�ɷ�");
  paystyleMp.put("B","���пۿ�");
  pageContext.setAttribute("PAY_STYLE",paystyleMp);
%>
<table  border="0" align="center" cellpadding="0" cellspacing="10">
  <tr>
    <td><img src="img/list_tit.gif" width="19" height="19"></td>
    <td class="title">�ֹ�˾��ת��˰����ͳ�Ʊ�</td>
  </tr>
</table>
<table width="810"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>
<br>
<form name="frmPost" method="post" action="freetaxi_report.do" >
   <input type="hidden" name="txtFrom"  value="1">
   <input type="hidden" name="txtTo"  value="500">
   <input type="hidden" name="txtAct" value="">
   <table width="810"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
   	  <tr>
   	 	<td class="list_bg2" align="right" width="17%">�ͻ���������</td>
      <td class="list_bg1" align="left"  width="33%">
    	<input type="hidden" name="txtDistrictID" value="<tbl:writeparam name="txtDistrictID" />">
	    <input type="text" name="txtCountyDesc" size="25" maxlength="50" readonly value="<tbl:WriteDistrictInfo property="txtDistrictID" />" class="text">
	    <input name="selDistButton" type="button" class="button" value="ѡ��" onClick="javascript:sel_district('all','txtDistrictID','txtCountyDesc')">
      </td>
      <td class="list_bg2" align="right" width="17%">֧����ʽ</td>
      <td class="list_bg1" align="left" width="33%">
      	<tbl:select name="txtpayStyle" set="PAY_STYLE" match="txtpayStyle" width="23"  />
      </td>
     </tr>
     <tr>
      <td class="list_bg2" align="right" width="17%">ͳ��ʱ���</td>
      <td class="list_bg1" align="left" width="33%">
    	   <input name="txtStatBeginTime" type="text" class="text" maxlength="10" size="10" value="<%=txtStatBeginTime%>">
    	   <IMG onclick="calendar(document.frmPost.txtStatBeginTime)" src="img/calendar.gif" style=cursor:hand border="0">
    	   ---
    	   <input name="txtStatEndTime" type="text" class="text" maxlength="10" size="10" value="<%=txtStatEndTime%>">
    	   <IMG onclick="calendar(document.frmPost.txtStatEndTime)" src="img/calendar.gif" style=cursor:hand border="0">
      </td>
      <td class="list_bg2" align="right">�շ���</td>
      <td class="list_bg1" align="left">
		  <input type="hidden" name="txtCollectorID" size="25" value="<tbl:writeparam name="txtCollectorID"/>" >
		  <input type="text" name="txtCollectorName" readonly size="24" value="<tbl:writeparam name="txtCollectorName"/>" >
		  <input name="selOperButton" type="button" class="button" value="��ѯ" 
	     	onClick="javascript:query_window('�շ��˲�ѯ','txtCollectorID','txtCollectorName','query_operator.do')">
	    	<input name="selClearButton" type="button" class="button" value="���" 
	    	onClick="javascript:clear_select('txtCollectorID','txtCollectorName')">
      </td>
     </tr>
   </table> 
   <table width="810"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
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
    <br>
    <table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
	     <tr>
	       <td><img src="img/mao.gif" width="1" height="1"></td>
	     </tr>
    </table>
    <rs:hasresultset>
    <br>   
    <table width="810"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
       <tr class="list_head">   
         	<td class="list_head" >�ͻ�֤�� </td>
          <td class="list_head" >�ͻ����� </td> 
          <td class="list_head" >�ɷ����� </td> 
          <td class="list_head" >�ɷѽ�� </td>        
          <td class="list_head" >֧����ʽ </td>
          <td class="list_head" >�տ��� </td>
          <td class="list_head" >��Ʊ���� </td>
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
         </tbl:printcsstr>
       <%
           }    
       %>
        </lgc:bloop>
       <tr>
         <td colspan="7" class="list_foot"></td>
       </tr>
      <tr class="trline" >
        <td align="right" class="t12" colspan="7" >
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
