<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="resultset" prefix="rs" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="osstags" prefix="d" %>
<%@ page import="com.dtv.oss.dto.PrepaymentDeductionRecordDTO" %>
<%@ page import="com.dtv.oss.web.util.CommonKeys" %>
 
<script language=javascript>
function check_form(){
   if (document.frmPost.txtCreateStartDate.value != ''){
	    if (!check_TenDate(document.frmPost.txtCreateStartDate, true, "��ʼ����")){
	        return false;
	    }
   }
   if (document.frmPost.txtCreateEndDate.value != ''){
	    if (!check_TenDate(document.frmPost.txtCreateEndDate, true, "��������")){
	        return false;
	    }
   }
    
   
       
    return true;
}

function query_submit(){
        if (check_form()) document.frmPost.submit();
}

function prepayment_deduction_record_detail(seqNO){
	var strURL="prepayment_deduction_record_detail.do?txtSeqNO="+seqNO+"&txtActionType=detail";
	self.location.href=strURL;
}

</script>


<table  border="0" align="center" cellpadding="0" cellspacing="10">
  <tr>
    <td><img src="img/list_tit.gif" width="19" height="19"></td>
    <td class="title">�ͻ�Ԥ��ֿۼ�¼�б�</td>
  </tr>
</table>
<table width="100%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>
<br>
<form name="frmPost" method="post" action="account_prepaymentdeduction.do" >
   <input type="hidden" name="txtFrom"  value="1">
   <input type="hidden" name="txtTo"  value="10">
   <tbl:hiddenparameters pass="txtCustomerID/txtAccountID" />
   <table width="804"  border="0" align="center" cellpadding="4" cellspacing="1" class="list_bg">
        <tr>
           <td  class="list_bg2" width="17%" align="right">����ʱ��</td>
           <td  class="list_bg1" width="33%" align="left"><font size="2">
            <input onfocus="viewModule(this)" onKeydown="inputDate(document.frmPost.txtCreateStartDate)" onblur="lostFocus(this)" type="text" size="10" maxlength="10" name="txtCreateStartDate" value="<tbl:writeparam name="txtCreateStartDate"/>" class="text"><IMG onclick="MyCalendar.SetDate(this,document.frmPost.txtCreateStartDate,'Y')" src="img/calendar.gif" style=cursor:hand border="0">
             - 
            <input onfocus="viewModule(this)" onKeydown="inputDate(document.frmPost.txtCreateEndDate)" onblur="lostFocus(this)" type="text" size="10" maxlength="10" name="txtCreateEndDate" value="<tbl:writeparam name="txtCreateEndDate" />" class="text"><IMG onclick="MyCalendar.SetDate(this,document.frmPost.txtCreateEndDate,'Y')" src="img/calendar.gif" style=cursor:hand border="0">
            
           </font></td>                    
            <td class="list_bg2" align="right" width="17%">�ʵ���</td>
         	<td class="list_bg1" align="left" width="83%">
  	        <input name="txtInvoiceNo" type="text" class="text" maxlength="10" size="25" value="<tbl:writeparam name="txtInvoiceNo" />" >
          </td>
        </tr> 
        
        <tr>
           <td  class="list_bg2" align="right">Ԥ�������</td>
           <td  class="list_bg1" align="left" colspan="3"><font size="2">
             <d:selcmn name="txtPrePaymentType" mapName="SET_F_PREPAYMENTTYPE" match="txtPrePaymentType" width="25" />  
           </td>
         
       </tr>  
        <tr>
           <td  class="list_bg2" align="center" colspan="4">
             <table  border="0" cellspacing="0" cellpadding="0">
             <tr>
               <td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
               <td align="center"><input name="Submit2" type="button" class="button" onClick="javascript:query_submit()"  value="�� ѯ"></td>
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
     <br>
<rs:hasresultset>
     <table width="804"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
        <tr class="list_head">                
           <td class="list_head" width="82" nowrap>��ˮ��</td>
           <td class="list_head" width="160" nowrap>����ʱ��</td>
           <td class="list_head" width="80" nowrap>Ԥ������</td>
           <td class="list_head" width="82" nowrap>���</td> 
           <td class="list_head" width="80" nowrap>״̬</td>
           <td class="list_head" width="80" nowrap>�ֿ۷�ʽ</td>
           <td class="list_head" width="80" nowrap>�ֿ۶���</td>
           <td class="list_head" width="160" nowrap>������Դ</td>
        </tr>
        
    <lgc:bloop requestObjectName="ResponseQueryResult" item="oneline" >
    <%
        PrepaymentDeductionRecordDTO dto = (PrepaymentDeductionRecordDTO)pageContext.getAttribute("oneline");
        pageContext.setAttribute("DTO",dto);
    %>				    	                 
      <tbl:printcsstr style1="list_bg1" style2="list_bg2" overStyle="list_over" >
       <td align="center"><a href="javascript:prepayment_deduction_record_detail('<tbl:write name="DTO" property="seqNo"/>')" class="link12" ><tbl:write name="DTO" property="seqNo"/></a></td>
       <td align="center"><tbl:writedate name="DTO" property="createTime"/><br><tbl:writedate name="DTO" property="createTime" onlyTime="true"/></td>
       <td align="center"><d:getcmnname typeName="SET_F_PREPAYMENTTYPE" match="DTO:prepaymentType" /></td>
       <td align="center"><tbl:write name="DTO" property="amount"/></td>
       <td align="center"><d:getcmnname typeName="SET_F_FTSTATUS" match="DTO:status" /></td>
       <td align="center"><d:getcmnname typeName="SET_F_PDR_REFERRECORDTYPE" match="DTO:referRecordType" /></td>
       <td align="center"><tbl:write name="DTO" property="referRecordId"/></td>
       <td align="center"><d:getcmnname typeName="SET_F_FTCREATINGMETHOD" match="DTO:creatingMethod" /></td>
      </tbl:printcsstr>

    </lgc:bloop>
    <tr>
	<td colspan="8" class="list_foot"></td>
    </tr>

    <tr class="trline" >
         <td align="right" class="t12" colspan="8" >
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
