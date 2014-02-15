<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="resultset" prefix="rs" %>
<%@ taglib uri="osstags" prefix="d" %>
<%@ page import="com.dtv.oss.util.Postern" %>
<%@ page import="com.dtv.oss.web.util.WebUtil" %>
<%@ page import="com.dtv.oss.service.commandresponse.QueryCommandResponseImpl"%>
<%@ page import="java.util.*" %>
<%@ page import="java.io.*" %>
<%@ page import="com.dtv.oss.util.*" %>

<Script language=JavaScript>
function check_submit(){
   var returnCheck = checkselect();
   if(returnCheck == -1){
      alert("��ѡ��ҪԤ����ʻ�!");
	    return;
   }else if(returnCheck == ""){  
     document.frmPost.action="query_batch_pre_pay_confirm.do";
     document.frmPost.submit();
   }
}

function check_form(){
	 if (document.frmPost.txtAcctBalance1.value !=''){
	  	 if (!check_Money(document.frmPost.txtAcctBalance1,true,"�˻��ܽ����ʼ���")){
	  	 	  return false;
	  	 }
	  	 if (document.frmPost.txtAcctBalance1.value*1.0 <0){
	  	 	  alert("�˻��ܽ����ʼ����С��0");
	  	 	  return false;  
	  	 }
	  } else{
	  	 alert("�˻��ܽ����ʼ����Ϊ�գ�");
	  	 return false;  
	  }
	  if (document.frmPost.txtAcctBalance2.value !=''){
	  	 if (!check_Money(document.frmPost.txtAcctBalance2,true,"�˻��ܽ��������")){
	  	 	  return false;
	  	 }
	  }
	  
	  if (document.frmPost.txtAcctBalance1.value !='' && document.frmPost.txtAcctBalance2.value !=''){
	     if (document.frmPost.txtAcctBalance1.value*1 > document.frmPost.txtAcctBalance2.value*1){
	        alert("�˻��ܽ����ʼ�������˻��ܽ��������");
	        return false;
	     }	
	  }
	  return true;
}
function query_submit(){
	 if (check_form()){
	 	  document.getElementById('search').disabled=true;
	 	  if (document.getElementById('ack') !=null){
	   	 	 document.getElementById('ack').disabled=true;
	   	}
      document.frmPost.action="query_batch_pre_pay.do";
      document.frmPost.submit();
   }
}


function checkselect() {
    var iCount = 0;
    var txtAccountIDs = "";
    var txtCustIDs = "";
    if(document.frmPost.ListAccountID==null)
     return -1;
    if (document.frmPost.ListAccountID.length!=null){
      for (var i=0;i<document.frmPost.ListAccountID.length;i++){
    	  if (document.frmPost.ListAccountID[i].checked){
    	       iCount++;
    	       if(iCount == 1){
		           txtAccountIDs = txtAccountIDs + document.frmPost.ListAccountID[i].value;
		           txtCustIDs = txtCustIDs + document.frmPost.ListCustID[i].value;
		         } else{
                 txtAccountIDs = txtAccountIDs + "," + document.frmPost.ListAccountID[i].value;
                 txtCustIDs = txtCustIDs + "," + document.frmPost.ListCustID[i].value;
             }
         }
       }
    }
    else{
       if (document.frmPost.ListAccountID.checked){
            iCount++;
            txtAccountIDs =document.frmPost.ListAccountID.value;
            txtCustIDs =document.frmPost.ListCustID.value;            
       }
    }
    if (iCount<=0) return -1;
    document.frmPost.txtAccountIDs.value =  txtAccountIDs;
    document.frmPost.txtCustIDs.value =  txtCustIDs;
    return "";
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

<form name="frmPost" method="post" action="">
	<input type="hidden" name="txtFrom"  value="1">
  <input type="hidden" name="txtTo"  value="200">
  <input type="hidden" name="txtAccountIDs" value="">
  <input type="hidden" name="txtCustIDs" value="">  
	 <table align="center" width="98%" border="0" cellspacing="1" cellpadding="3" class="fulltable" >      
       <tr>
		     <td class="list_bg2" align="right">��������</td>
	         <td class="list_bg1" align="left">
	    	   <input type="hidden" name="txtDistrictID" value="<tbl:writeparam name="txtDistrictID" />">
		       <input type="text" name="txtCountyDesc" size="22" maxlength="50" readonly value="<tbl:WriteDistrictInfo property="txtDistrictID" />" class="text">
		       <input name="selDistButton" type="button" class="button" value="ѡ��" onClick="javascript:sel_district('leaf','txtDistrictID','txtCountyDesc')">
         </td>
         <td class="list_bg2" width=17% align="right">�ͻ�����</td>
         <td class="list_bg1" width=33% align="left">
         <d:selcmn name="txtCustomerType" mapName="SET_C_CUSTOMERTYPE" match="txtCustomerType" width="20" />
         </td>
      </tr>
      <tr>
	      <td class="list_bg2" width=17% align="right">�ͻ�֤��</td>
	      <td class="list_bg1" width=33% align="left"><input name="txtCustomerID" type="text" class="text" maxlength="20" size="22" value="<tbl:writeparam name="txtCustomerID" />"></td>
	      <td class="list_bg2" width=17% align="right">�ʻ�����</td>
	      <td class="list_bg1" width=33% align="left"><input name="txtAcctName" type="text" class="text" maxlength="20" size="22" value="<tbl:writeparam name="txtAcctName" />"></td>
      </tr>
      <%
        Map queryStyle =new HashMap();
        queryStyle.put("A","�� ��ַ �ͻ�֤��");
        queryStyle.put("C","�� ���� �ͻ�֤��");
        pageContext.setAttribute("queryStyle",queryStyle);
        String txtqueryStyle =(request.getParameter("txtqueryStyle")==null) ? "1" :request.getParameter("txtqueryStyle");
        Map mapStatus = new LinkedHashMap();
        mapStatus.put("V", "����һ������");
        pageContext.setAttribute("mapStatus", mapStatus);
        String txtServiceAcctStatus =(request.getParameter("txtServiceAcctStatus")==null) ? "V" :request.getParameter("txtServiceAcctStatus");
      %>
      <tr>
        <td class="list_bg2" align="right">��ѯ��ʽ</td>
	      <td class="list_bg1" align="left"><tbl:select name="txtqueryStyle" set="queryStyle" match="<%=txtqueryStyle%>" width="20" /></td>
        <td class="list_bg2" align="right">�շ��ն�״̬</td>
        <td class="list_bg1" align="left"> <tbl:select name="txtServiceAcctStatus" set="mapStatus" match="<%=txtServiceAcctStatus%>" width="20"/></td>
      </tr>
      <%
        String txtAcctBalance1 =(request.getParameter("txtAcctBalance1")==null) ? "0" :request.getParameter("txtAcctBalance1");
      %>
	    <tr>
	    	<td class="list_bg2" align="center" colspan="2">
        	<input name="txtAcctBalance1" align="right" type="text" class="text" maxlength="10"  size="10" value="<%=txtAcctBalance1%>">
        	 <= �˻��ܽ�� <=
          <input name="txtAcctBalance2" type="text" class="text" maxlength="10" align="left" size="10" value="<tbl:writeparam name="txtAcctBalance2" />">
	      </td>
	      <td class="list_bg2" align="right">��ϸ��ַ</td>
	      <td class="list_bg1" align="left"><input name="txtAddressDetail" type="text" class="text" maxlength="20" size="22" value="<tbl:writeparam name="txtAddressDetail" />"></td>
	    </tr>
   </table>
   <table align="center" border="0" cellspacing="0" cellpadding="0">
        <tr>
           <td><img src="img/button_l.gif" width="11" height="20" ></td>
           <td><input name="search" type="button" class="button" value="�� ѯ" onclick="javascript:query_submit()"></td>
           <td><img src="img/button_r.gif" width="22" height="20" ></td>
        <%
			     Collection returnList =null;
			     QueryCommandResponseImpl RepCmd = (QueryCommandResponseImpl)pageContext.getRequest().getAttribute("ResponseQueryResult");
			     if (RepCmd !=null) returnList = (Collection) RepCmd.getPayload();
			     if (returnList !=null && !returnList.isEmpty()){
			  %>
			      <td width="20" ></td>
			      <td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
            <td align="center"><input name="ack" type="button" class="button" onClick="javascript:check_submit()"  value="ȷ ��"></td>
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
     	 <td class="list_head">
			   <div align="center">
     	   <input type="checkbox" name="selall" onclick="javascript:select_all_by_name(document.frmPost,'ListAccountID', this.checked)"> 
          </div>
		   </td>
		   <td class="list_head" width="80" nowrap>�ͻ�֤��</td>
       <td class="list_head" width="80" nowrap>�ͻ�����</td>
       <td class="list_head" width="80" nowrap>�ʻ���</td>
       <td class="list_head" width="80" nowrap>�ʻ�����</td>
       <td class="list_head" width="80" nowrap>�˻��ܽ��</td> 
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
       	     Object custName  =resultIter.next();
       	     Object acctId    =resultIter.next();            
      %>
      <tr>
       <tbl:printcsstr style1="list_bg1" style2="list_bg2" overStyle="list_over" > 
		      <td align="center"><input type="checkbox" name="ListAccountID" value="<%=acctId%>"></td>
		      <td align="center"><%=custId%>
		     		<input type="hidden" name="ListCustID" value="<%=custId%>">
		     	</td>	    
		     	<td align="center"><%=custName%></td>			
		      <td align="center"><%=acctId%></td>
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
</form>   