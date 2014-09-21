<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="resultset" prefix="rs" %>
<%@ taglib uri="bookmark" prefix="bk" %>
<%@ taglib uri="osstags" prefix="d" %>

<%@ page import="com.dtv.oss.dto.InvoiceDTO" %>
<%@ page import="com.dtv.oss.util.Postern" %>

<Script language=JavaScript>

function view_detail_click(strId)
{
	self.location.href="bill_view.do?txtInvoiceNo="+strId;
}
function chek_frm(){
 	if (document.frmPost.txtCreateTimeFrom.value != ''){
		if (!check_TenDate(document.frmPost.txtCreateTimeFrom, true, "��ʼ����")){
			return false;
		}
	}
	if (document.frmPost.txtCreateTimeTo.value != ''){
		if (!check_TenDate(document.frmPost.txtCreateTimeTo, true, "��������")){
			return false;
		}
	}
	if(!compareDate(document.frmPost.txtCreateTimeFrom,document.frmPost.txtCreateTimeTo,"�������ڱ�����ڵ��ڿ�ʼ����")){
        	return false;
        }
	return true;
 }
 function query_submit(){
    if (chek_frm()){
      document.frmPost.submit();
    }
}


</Script>

<table  border="0" align="center" cellpadding="0" cellspacing="10">
<tr>
    <td><img src="img/list_tit.gif" width="19" height="19"></td>
    <td class="title">�ͻ��ʵ���¼�б�</td>
  </tr>
</table>
<br>
<table width="820"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
   <tr>
      <td><img src="img/mao.gif" width="1" height="1"></td>
   </tr>
</table>
<br>
  
<form name="frmPost" method="post" action="bill_query_result_for_cust.do" >
    

    <tbl:hiddenparameters pass="txtCustomerID/txtAccountID" />
    <input type="hidden" name="txtFrom" size="20" value="1">
    <input type="hidden" name="txtTo" size="20" value="10">
    <table width="804" align="center" border="0" cellspacing="1" cellpadding="1" class="list_bg">
        <tr>
          <td align="right" width="80%" class="list_bg1">&nbsp;&nbsp;&nbsp;����
            <font size="2">
            <input onfocus="viewModule(this)" onKeydown="inputDate(document.frmPost.txtCreateTimeFrom)" onblur="lostFocus(this)" type="text" class="text" size="12" maxlength="10" name="txtCreateTimeFrom" value="<tbl:writeparam name="txtCreateTimeFrom"/>"><IMG onclick="MyCalendar.SetDate(this,document.frmPost.txtCreateTimeFrom,'Y')" src="img/calendar.gif" style=cursor:hand border="0">
             --
            <input onfocus="viewModule(this)" onKeydown="inputDate(document.frmPost.txtCreateTimeTo)" onblur="lostFocus(this)" type="text" class="text" size="12" maxlength="10" name="txtCreateTimeTo" value="<tbl:writeparam name="txtCreateTimeTo" />"><IMG onclick="MyCalendar.SetDate(this,document.frmPost.txtCreateTimeTo,'Y')" src="img/calendar.gif" style=cursor:hand border="0">
           </font>
           &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
           �ʵ�״̬
           <d:selcmn name="txtStatus" mapName="SET_F_INVOICESTATUS" match="txtStatus"   width="18" />
          </td>
          <td align="center" width="20%" class="list_bg1">
            <table  border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
                <td><input name="Submit2" type="button" class="button" onClick="javascript:query_submit()" value="�� ѯ"></td>
                <td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
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
    <table width="804"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
      <tr class="list_head">      
         <td class="list_head" width="100" nowrap>�˵���</td>
         <td class="list_head" width="100" nowrap>��������</td>
         <td class="list_head" width="100" nowrap>��������</td>
         <td class="list_head" width="80" nowrap>�ʵ�״̬</td>
         <td class="list_head" width="80" nowrap>Ӧ�����</td>
         <td class="list_head" width="80" nowrap>��������</td>
         <td class="list_head" width="80" nowrap>��������ת</td>
         <td class="list_head" width="80" nowrap>���ɽ�</td>  
         <td class="list_head" width="100" nowrap>֧����</td>    
      </tr>
 
     <lgc:bloop requestObjectName="ResponseQueryResult" item="oneline" >
      <%
        InvoiceDTO dto = (InvoiceDTO) pageContext.getAttribute("oneline");
        String strBillingCycleName = Postern.getBillingCycleNameByID(dto.getInvoiceCycleId());
        if (strBillingCycleName==null) strBillingCycleName="";
        String status=dto.getStatus();
        if(status.equalsIgnoreCase("w")||status.equalsIgnoreCase("o"))
        {
     %> 
       <tbl:printcsstr style1="list_bg1" style2="list_bg2" overStyle="list_over" >
          <td align="center" ><a href="javascript:view_detail_click('<tbl:write name="oneline" property="InvoiceNo"/>')" class="link12"><tbl:writenumber name="oneline" digit="7" property="InvoiceNo"/></a></td>
          <td align="center" ><tbl:writedate name="oneline" property="CreateDate"/></td>
           
          <td align="center" ><tbl:writedate name="oneline" property="DueDate"/></td>
            <td align="center" ><font color="red"><d:getcmnname typeName="SET_F_INVOICESTATUS" match="oneline:status" /></td>
             <td align="center" ><tbl:write name="oneline" property="Amount"/></td>
               <td align="center" ><%=strBillingCycleName%></td>
               
          <td align="center" ><tbl:write name="oneline" property="Bcf"/></td>
           <td align="center" ><tbl:write name="oneline" property="Punishment"/></td>
          <td align="center" ><tbl:writedate name="oneline" property="PayDate"/></td>
       </tbl:printcsstr>
     <%  } else { %> 
       <tbl:printcsstr style1="list_bg1" style2="list_bg2" overStyle="list_over" >
          <td align="center" ><a href="javascript:view_detail_click('<tbl:write name="oneline" property="InvoiceNo"/>')" class="link12"><tbl:writenumber name="oneline" digit="7" property="InvoiceNo"/></a></td>
          <td align="center" ><tbl:writedate name="oneline" property="CreateDate"/></td>  
          <td align="center" ><tbl:writedate name="oneline" property="DueDate"/></td>
           <td align="center" ><d:getcmnname typeName="SET_F_INVOICESTATUS" match="oneline:status" /></td>
            <td align="center" ><tbl:write name="oneline" property="Amount"/></td>
             <td align="center" ><%=strBillingCycleName%></td>
              <td align="center" ><tbl:write name="oneline" property="Bcf"/></td>
               <td align="center" ><tbl:write name="oneline" property="Punishment"/></td>
          <td align="center" ><tbl:writedate name="oneline" property="PayDate"/></td>
         </tbl:printcsstr>
     <%   } %>  
     </lgc:bloop>       
    <tr>
        <td colspan="9" class="list_foot"></td>
    </tr>
   </table>
  
   <table  border="0" align="right" cellpadding="0" cellspacing="9">
    <tr>
      <td>��<span class="en_8pt"><rs:prop property="curpageno" /></span>ҳ<span class="en_8pt">/</span>��<span class="en_8pt"><rs:prop property="pageamount" /><span>ҳ</td>
      <td>��<span class="en_8pt"><rs:prop property="recordcount" /></span>����¼</td>
     <rs:notfirst>
      <td align="right"><img src="img/dot_top.gif" width="17" height="11"></td>
      <td><a href="javascript:firstPage_onClick(document.frmPost, <rs:prop property="firstto" />)" class="link12">��ҳ</a></td>
      <td align="right"><img src="img/dot_pre.gif" width="6" height="11"></td>
      <td><a href="javascript:previous_onClick(document.frmPost, <rs:prop property="prefrom" />, <rs:prop property="preto" />)" class="link12">��һҳ</a></td>
    </rs:notfirst>
    <rs:notlast>
     <td align="right"><img src="img/dot_next.gif" width="6" height="11"></td>
     <td><a href="javascript:next_onClick(document.frmPost, <rs:prop property="nextfrom" />, <rs:prop property="nextto" />)" class="link12">��һҳ</a></td>
     <td align="right"><img src="img/dot_end.gif" width="17" height="11"></td>
     <td><a href="javascript:lastPage_onClick(document.frmPost, <rs:prop property="lastfrom" />, <rs:prop property="lastto" />)" class="link12">ĩҳ</a></td>
    </rs:notlast>
     <td align="right">��ת��</td>
     <td><input name="txtPage" type="text" class="page_txt"></td>
     <td>ҳ</td>
     <td><input name="imageField" type="image" src="img/button_go.gif" width="28" height="15" border="0" onclick="javascript:goto_onClick(document.frmPost, <rs:prop property="pageamount" />)"></td>
   </tr>
   </table>
   
  </rs:hasresultset> 

</form>  
   


        