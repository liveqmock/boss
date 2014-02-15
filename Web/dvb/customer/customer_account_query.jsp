<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="resultset" prefix="rs" %>
<%@ page import="com.dtv.oss.service.commandresponse.QueryCommandResponseImpl"%>
<%@ page import="com.dtv.oss.dto.wrap.customer.Customer2AddressWrap, java.util.HashMap"%>
<%@ page import="com.dtv.oss.dto.CustomerDTO, com.dtv.oss.dto.AddressDTO, com.dtv.oss.dto.AccountDTO"%>

<Script language=JavaScript>
function choose_confirm(){
   var check_flag =false;
   for (i=1 ;i< document.frmPost.signradio.length ;i++){
       if (document.frmPost.signradio[i].checked){
          
           self.opener.document.frmPost.txtNewAccountID.value =document.frmPost.signradio[i].value;
           check_flag =true;   
           window.close();
           return;
       }
   }
   if (check_flag==false){
      alert("��ѡ���ʻ���");
   }
}
</Script>

<table width="580px"  border="0" align="center" cellpadding="0" cellspacing="0" >
  <tr>
    <td width="590px">

 <form name="frmPost" method="post"  >
     <BR>
     <table  border="0" align="center" cellpadding="0" cellspacing="5">
      <tr>
        <td><img src="img/list_tit.gif" width="19" height="19"></td>
        <td class="title">ҵ���ʻ�����--�ͻ��ʻ���ѯ</td>
      </tr>
     </table>
     <br>
     <table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
      <tr>
        <td><img src="img/mao.gif" width="1" height="1"></td>
      </tr>
      </table>
      <br>
<rs:hasresultset>

<%
	Customer2AddressWrap wrap = new Customer2AddressWrap();
	QueryCommandResponseImpl RepCmd = (QueryCommandResponseImpl)pageContext.getRequest().getAttribute("ResponseQueryResult");
	Collection collection = (Collection) RepCmd.getPayload();
	Iterator iterator = collection.iterator();
	if(iterator.hasNext())
	{
	wrap = (Customer2AddressWrap)iterator.next();
	pageContext.setAttribute("cust", wrap.getCustDto());
	pageContext.setAttribute("addr", wrap.getAddrDto());
	Map acctMap = wrap.getMarkInfoMap();
%>
<table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
  <tr>
    <td class="list_bg2" width="17%"><div align="right">�ͻ�֤��</div></td>
    <td class="list_bg1" width="33%"><input name="txtNewCustomerID" type="text" class="textgray" maxlength="20" size="22" value="<tbl:write name='cust' property='customerID' />" readonly></td>
    <td class="list_bg2" width="17%"><div align="right">����</div></td>
    <td class="list_bg1" width="33%"><input name="txtNewName" type="text" class="textgray" readonly maxlength="10" size="22" value="<tbl:write name='cust' property='name' />"></td>
  </tr>
  <tr>
    <td class="list_bg2"><div align="right">��ַ</div></td>
    <td class="list_bg1"><input name="txtAddress" type="text" class="textgray" readonly maxlength="20" size="22" value="<tbl:write name='addr' property='detailAddress' />"></td>
    <td class="list_bg2"><div align="right">��������</div></td>
    <td class="list_bg1"><input name="txtSAC" type="text" class="textgray" readonly maxlength="10" size="22" value="<tbl:write name='cust' property='createTime' />"></td>
  </tr>
</table>
      <input type="hidden" name="txtFrom" size="20" value="1">
      <input type="hidden" name="txtTo" size="20" value="10">
      <input type="hidden" name="signradio" value="">
      <input type="hidden" name="CatvID" value="" >
      <table width="98%" border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
         <tr class="list_head">     
           <td>ѡ��</td> 
           <td>�ʻ���</td>
           <td>�ʻ���</td>     
         </tr>    
<%
	Set  keySet = acctMap.keySet();
	Iterator acctIt = keySet.iterator();
	while(acctIt.hasNext())
	{
		String acctID = (String)acctIt.next();
		String acctName = (String)acctMap.get(acctID);
%>
	<tbl:printcsstr style1="list_bg1" style2="list_bg2" overStyle="list_over" > 
           <td align="center"><input type="radio" name="signradio" value="<%=acctID%>"></td>
           <td align="center"><%=acctID%></td>
           <td  align="center"><%=acctName%></td>
     </tbl:printcsstr>
     

<%}%>
<tr>
	<td colspan="3" class="list_foot"></td>
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
<BR>
   <table align="center" border="0" cellspacing="0" cellpadding="0">
      <tr> 
        <td><img src="img/button2_r.gif" width="22" height="20"></td>
	<td><input name="next" type="button" class="button" onClick="javascript:choose_confirm()" value="ȷ ��"></td>
        <td><img src="img/button2_l.gif" width="11" height="20"></td>
        <td width="20" ></td>
        <td><img src="img/button_l.gif" width="11" height="20"></td>
	<td><input name="prev" type="button" class="button" onClick="javascript:window.close()" value="�رմ���"></td>
        <td><img src="img/button_r.gif" width="22" height="20"></td>
       </tr>
    </table>
<%}%>
</rs:hasresultset> 

       </form>  
<rs:hasnoresultset> 
<TABLE align="center" border="0" cellspacing="0" cellpadding="0" >
<TR>
	<TD >�ͻ�֤�ţ�<%=request.getParameter("newCustomerID")%><br><BR></td></tr>
<tr><td >û�в鵽�ÿͻ�!<BR><BR></td>
</TR>
</TABLE>
   <table align="center" border="0" cellspacing="0" cellpadding="0">
      <tr> 
        <td><img src="img/button_l.gif" width="11" height="20"></td>
	<td><input name="prev" type="button" class="button" onClick="javascript:window.close()" value="�رմ���"></td>
        <td><img src="img/button_r.gif" width="22" height="20"></td>
       </tr>
    </table>
    
  
</rs:hasnoresultset> 
    </td>
  </tr>
</table>  