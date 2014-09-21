<%@ page import="com.dtv.oss.util.Postern,
                 com.dtv.oss.dto.PaymentRecordDTO,
                 com.dtv.oss.dto.MethodOfPaymentDTO"%>
<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="resultset" prefix="rs" %>
<%@ taglib uri="privilege" prefix="pri" %>
<%@ page import="java.util.*" %>
<%@ taglib uri="osstags" prefix="d" %>
<%@ page import="com.dtv.oss.service.commandresponse.QueryCommandResponseImpl"%>
<!-- ���Ѽ�¼��ѯ -->
<Script language="JavaScript" >
 function view_detail_click(seqNO) {
    self.location.href = "payment_record_all_detail.do?txtSeqNO="+seqNO+"&txtActionType=detail";
 }
 function chek_frm(){
	if (document.frmPost.txtCreateStartDateDatePart.value != ''){
		if (!check_TenDate(document.frmPost.txtCreateStartDateDatePart, true, "��ʼ����")){
			return false;
		}
	}
	 
	if (document.frmPost.txtCreateEndDateDatePart.value != ''){
		if (!check_TenDate(document.frmPost.txtCreateEndDateDatePart, true, "��������")){
			return false;
		}
	}
	 
	if(!compareDate(document.frmPost.txtCreateStartDateDatePart,document.frmPost.txtCreateEndDateDatePart,"�������ڱ�����ڵ��ڿ�ʼ����")){
		
		return false;
	}
	return true;
 }
 
 function query_submit(){
   if (chek_frm()){
       document.frmPost.action ="payment_record_conditionQuery.do";
       document.frmPost.submit();
   }
 }
 
 function checkSelected(){
 	 document.frmPost.txtAcctPayIDList.value ="";
 	 var selectCout =0;
	 var returnValue=false;
	 if(document.frmPost.ListID==null){
	 	  alert("û����Ҫ��ӡ��֧����¼");
		  return false;
	 }else{
	 	  if (document.frmPost.ListID.length!=null){
			  for (var i=0;i<document.frmPost.ListID.length;i++){
				  if (document.frmPost.ListID[i].checked){
				  	 selectCout =selectCout +1;
					   if(document.frmPost.txtAcctPayIDList.value == ""){
						    document.frmPost.txtAcctPayIDList.value = document.frmPost.ListID[i].value;						
					   }else{
					      document.frmPost.txtAcctPayIDList.value = document.frmPost.txtAcctPayIDList.value+","+document.frmPost.ListID[i].value;	
				     }
				     returnValue= true;
				  }
			  }
		  }else{
		   	if(document.frmPost.ListID.checked){
		   		 selectCout =selectCout +1;
				   document.frmPost.txtAcctPayIDList.value=document.frmPost.ListID.value;
			     returnValue= true;
			  }
		  }
	  }
	  if(!returnValue){
	  	 alert("��ָ����ӡ��֧����¼");
	  }
	  if (selectCout >5){
	  	 alert("���ֻ��ѡ��5����¼��ӡ��");
	  	 returnValue =false;
	  }
	  return returnValue;
 }
 
 function print_submit(){
   if (checkSelected()){
   	   var txtAcctPayIDList =document.frmPost.txtAcctPayIDList.value;
   	   var txtCustomerID =document.frmPost.txtCustomerID.value;
   	   document.frmPost.target="_blank";
   	   document.frmPost.action ="print_pay_receipt.do?txtAcctPayIDList="+txtAcctPayIDList+"&txtCustomerID="+txtCustomerID;
       document.frmPost.submit();
       document.frmPost.target="_self";
   }
 }

</Script>


<table  border="0" align="center" cellpadding="0" cellspacing="10">
  <tr>
    <td><img src="img/list_tit.gif" width="19" height="19"></td>
    <td class="title">�ͻ�֧����¼�б�</td>
  </tr>
</table>
<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>
<br>

<form name="frmPost" method="post" action="payment_record_conditionQuery.do" >
    <input type="hidden" name="txtFrom" size="20" value="1">
    <input type="hidden" name="txtTo" size="20" value="10">
    <input type="hidden" name="txtActionType" value="all">
    <input type="hidden" name="txtAcctPayIDList" value="">
    <input type="hidden" name="txtCustomerID" value="<tbl:writeparam name="txtCustomerID" />" >
    <input type="hidden" name="txtAccountID" value="<tbl:writeparam name="txtAccountID" />" >	
   
    <table width="804" align="center" border="0" cellspacing="1" cellpadding="1" class="list_bg">
       <tr>
       	  <td valign="middle" class="list_bg2" align="right" width="17%" >��������</td>
         <td width="33%" class="list_bg1" ><font size="2">
         <d:selcmn name="txtReferType" mapName="SET_F_FTREFERTYPE"  match="txtReferType"  width="25" />
          </td>    
          <td valign="middle" class="list_bg2" align="right" width="17%" >���ݺ�</td>
	       <td width="33%" class="list_bg1"><font size="2">
             <input type="text"  size="25" maxlength="20" name="txtReferID" value="<tbl:writeparam name="txtReferID"/>" >  	
         </td>
       </tr>
       <tr>
		    <td class="list_bg2" align="right">�ʵ���־</td>
		    <td class="list_bg1" align="left">
		    	<d:selcmn name="txtInvoicedFlag" mapName="SET_G_YESNOFLAG"  match="txtInvoicedFlag"  width="23" />
		    </td>
		    <td class="list_bg2" align="right">�ʵ���</td>
		    <td class="list_bg1" align="left">
		      <input name="txtInvoiceNo" type="text" class="text" maxlength="10" size="25" value="<tbl:writeparam name="txtInvoiceNo" />">
		    </td>
		</tr>
       <tr>
	    <td class="list_bg2" align="right">����ʱ��</td>
	    <td class="list_bg1" colspan="3"> 
	            <d:datetime name="txtCreateStartDate" size="10" match="txtCreateStartDate" includeHour="true" onclick="MyCalendar.SetDate(this,document.frmPost.txtCreateStartDateDatePart,'Y')" picURL="img/calendar.gif" style="cursor:hand" />            
	             - 
	             <d:datetime name="txtCreateEndDate" size="10" match="txtCreateEndDate" includeHour="true" onclick="MyCalendar.SetDate(this,document.frmPost.txtCreateEndDateDatePart,'Y')" picURL="img/calendar.gif" style="cursor:hand" />           
	   </td> 
	  </tr>
       <tr>
          <td align="center" width="100%" class="list_bg1" colspan="4">
            <table  border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
                <td><input name="Submit2" type="button" class="button" onClick="javascript:query_submit()"  value="�� ѯ"></td>
                <td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
                <pri:authorized name="print_pay_receipt.do" >
                <td width="20" ></td>
                <%
			            Collection returnList =null;
			            QueryCommandResponseImpl RepCmd = (QueryCommandResponseImpl)pageContext.getRequest().getAttribute("ResponseQueryResult");
		              if (RepCmd !=null) returnList = (Collection) RepCmd.getPayload();
			            if (returnList !=null && !returnList.isEmpty()){
		           %>
			          
			          <td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
                <td align="center"><input name="Submit2" type="button" class="button" onClick="javascript:print_submit()"  value="�� ӡ"></td>
                <td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
              <%
                 } else {
               %>
                <td colspan="4">&nbsp;</td>
               <%
                 }
                %>
                </pri:authorized>
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
    <table width="804" border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
        <tr class="list_head">
        	<td class="list_head">
					<div align="center"><input type="checkbox" name="selall"
						onclick="javascript:javascript:select_all_by_name(document.frmPost,'ListID', this.checked)"></div>
					</td>
           <td class="list_head" width="80" nowrap>��ˮ��</td>
           <td class="list_head" width="80" nowrap>����ʱ��</td>
           <td class="list_head" width="60" nowrap>�շ���</td>
           <td class="list_head" width="60" nowrap>�շѻ���</td>
           <td class="list_head" nowrap>���ѷ�ʽ</td>
           <td class="list_head" width="60" nowrap>���</td>
           <td class="list_head" width="60" nowrap>���</td>
           <td class="list_head" width="40" nowrap>״̬</td>
           <td class="list_head" width="50" nowrap>�ʵ���</td>
           <td class="list_head" width="60" nowrap>��������</td>
           <td class="list_head" width="60" nowrap>���ݺ�</td>
           <td class="list_head" width="90" nowrap>��Ʊ��</td>
        </tr>
      <lgc:bloop requestObjectName="ResponseQueryResult" item="oneline" >
        <%
           Map  payMap =Postern.getAllMethodOfPayments();
   
           PaymentRecordDTO dto = (PaymentRecordDTO) pageContext.findAttribute("oneline");
           MethodOfPaymentDTO paymentDto =(MethodOfPaymentDTO)payMap.get(String.valueOf(dto.getMopID()));
           String payMopName =paymentDto!=null?paymentDto.getName():"";
           String operatorName =Postern.getOperatorNameByID(dto.getOpID());
           String orgName = Postern.getOrgNameByID(dto.getOrgID());
           if (orgName == null) orgName = "";

        %>
        <tbl:printcsstr style1="list_bg1" style2="list_bg2" overStyle="list_over" >
        	<td align="center" ><input type="checkbox" name="ListID" value="<tbl:write name="oneline" property="seqNo"/>"></td>
          <td align="center" ><a href="javascript:view_detail_click('<tbl:write name="oneline" property="seqNo"/>')" class="link12"><div align="center">
          <tbl:writenumber name="oneline" property="seqNo" digit="7" /></td>
          <td align="center" ><tbl:writedate name="oneline" property="createTime"/><br><tbl:writedate name="oneline" property="createTime" onlyTime="true"/></td>
          <td align="center" ><%=operatorName%></td>
          <td align="center" ><%=orgName%></td>
          <td align="center" ><%=payMopName%></td>
          <td align="center" ><d:getcmnname typeName="SET_F_PAYMENTRECORDTYPE" match="oneline:PayType"/></td>
          <td align="center" ><tbl:write name="oneline" property="Amount"/></td>
          <td align="center" ><d:getcmnname typeName="SET_F_FTSTATUS" match="oneline:Status" /></td>
          <td align="center" ><tbl:write name="oneline" property="invoiceNo"/></td>
          <td align="center" ><d:getcmnname typeName="SET_F_FTREFERTYPE" match="oneline:referType"/></td>
          <td align="center" ><tbl:write name="oneline" property="referID"/></td>
          <td align="center"><tbl:write name="oneline" property="faPiaoNo" /></td> 
        </tbl:printcsstr>
      </lgc:bloop>
      <tr>
        <td colspan="13" class="list_foot"></td>
      </tr>
   </table>
  
 <table  border="0" align="right" cellpadding="0" cellspacing="11">
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

