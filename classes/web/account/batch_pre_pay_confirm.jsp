<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="resultset" prefix="rs" %>
<%@ taglib uri="osstags" prefix="d" %>
<%@ taglib uri="bookmark" prefix="bk" %>
<%@ page import="com.dtv.oss.web.util.*" %>
<%@ page import="java.util.*" %>
<%@ page import="java.io.*" %>
<%@ page import="com.dtv.oss.util.*" %>

<Script language=JavaScript>
    function confirm_submit(){
        document.frmPost.submit();
    }
</SCRIPT>
<table  border="0" align="center" cellpadding="0" cellspacing="10">
  <tr>
    <td><img src="img/list_tit.gif" width="19" height="19"></td>
    <td class="title">����Ԥ��</td>
  </tr>
</table>
<table width="810"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>
<br>

<form name="frmPost" method="post" action="batch_pre_pay.do">
  <input type="hidden" name="txtAccountIDs" value="<tbl:writeparam name="txtAccountIDs" />">
  <input type="hidden" name="txtCustIDs" value="<tbl:writeparam name="txtCustIDs" />">
  <input type="hidden" name="func_flag" value="6013">
  <input type="hidden" name="txtDoPost" value="true">
  <tbl:hiddenparameters pass="txtqueryStyle" /> 
  <table align="center" width="98%" border="0" cellspacing="1" cellpadding="3" class="fulltable" >      
     <tr>
       	<td colspan="4">
	    		<tbl:CommonFeeAndPaymentInfo includeParameter="Fee_BatchDeposite" payCsiType="<%=CommonKeys.CUSTSERVICEINTERACTIONTYPE_AD%>" acctshowFlag ="false" confirmFlag="false" />
	      </td>
	   </tr>
  </table>
  <table align="center" border="0" cellspacing="0" cellpadding="0">
     <tr>
     	  <bk:canback url="query_batch_pre_pay.do">
           <td><img src="img/button2_r.gif" width="22" height="20"></td>
           <td background="img/button_bg.gif"  ><a href="<bk:backurl property='query_batch_pre_pay.do' />" class="btn12">��һ��</a></td>           
           <td><img src="img/button2_l.gif" width="11" height="20"></td>
           <td width="20" >&nbsp;&nbsp;&nbsp</td>
        </bk:canback>
     	
        <td><img src="img/button_l.gif" border="0" ></td>
        <td background="img/button_bg.gif"><a href="javascript:confirm_submit()" class="btn12">Ԥ&nbsp;��</a></td>
        <td><img src="img/button_r.gif" border="0" ></td>
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
     	 <td class="list_head" width="80" nowrap>�ͻ�֤��</td>
       <td class="list_head" width="80" nowrap>�ͻ�����</td> 
     	 <td class="list_head" width="80" nowrap>�ʻ���</td>
       <td class="list_head" width="80" nowrap>�ʻ�����</td>
       <td class="list_head" width="80" nowrap>�ʻ����</td>
       <td class="list_head" width="80" nowrap>�ͻ�����</td>
       <td class="list_head" width="80" nowrap>��ϵ�绰</td>
       <td class="list_head" width="160" nowrap>��ϸ��ַ</td>
       <td class="list_head" width="80" nowrap>�ͻ�״̬</td>
     </tr>
     <lgc:bloop requestObjectName="ResponseQueryResult" item="oneline" >
     <%
       	 Collection resultCols = (Collection)pageContext.getAttribute("oneline");
       	 Iterator resultIter =resultCols.iterator();
       	 while (resultIter.hasNext()) {
       	     Object custId    =resultIter.next();
      %>
      <tr>
       <tbl:printcsstr style1="list_bg1" style2="list_bg2" overStyle="list_over" >     			
		      <td align="center"><%=custId%></td>
		      <td align="center"><%=resultIter.next()%></td>
		     	<td align="center"><%=resultIter.next()%></td>
		     	<td align="center"><%=resultIter.next()%></td>
		     	<td align="center"><%=resultIter.next()%></td>
		    <%
		       String custType =(String)resultIter.next();
		       String tel =(String)resultIter.next();
		    %>
		      <td align="center"><d:getcmnname typeName="SET_C_CUSTOMERTYPE" match="<%=custType%>" /></td>
		     	<td align="center"><%=(tel==null) ? "" : tel%></td>
		     	<td align="center"><%=resultIter.next()%></td>
		    	<td align="center"><%=resultIter.next()%></td>
       </tbl:printcsstr>
      <%   
       }
      %>
      </lgc:bloop>
		<tr>
				<td colspan="10" class="list_foot"></td>
		</tr>
    <tr class="trline" >
       <td align="right" class="t12" colspan="10" >
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
  <tbl:generatetoken />   
</form>     