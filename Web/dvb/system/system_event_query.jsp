<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>

<%@ taglib uri="resultset" prefix="rs" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="osstags" prefix="d" %>
 
<%@ page import="com.dtv.oss.util.Postern"%>

<script language=javascript>
 
function view_detail_click(aiNO) {
    self.location.href = "system_event_detail.do?txtSequenceNo="+aiNO;
 }

function query_submit()
{
       if(checkDate())
       document.frmPost.submit();
}
function checkDate(){
 
	if (document.frmPost.txtCreateStartDateDatePart.value != ''){
		if (!check_TenDate(document.frmPost.txtCreateStartDateDatePart, true, "��ʼʱ��")){
			return false;
		}
	}
	 
	if (document.frmPost.txtCreateEndDateDatePart.value != ''){
		if (!check_TenDate(document.frmPost.txtCreateEndDateDatePart, true, "��ֹʱ��")){
			return false;
		}
	}
	 
	if(!compareDate(document.frmPost.txtCreateStartDateDatePart,document.frmPost.txtCreateEndDateDatePart,"�������ڱ�����ڵ��ڿ�ʼ����")){
		
		return false;
	}
	 
	            
	return true;
}


 
 
</script>
<div id="updselwaitlayer" style="position:absolute; left:650px; top:170px; width:250px; height:24px; display:none">
    <table width="100%" border="0" cellspacing="1" cellpadding="3">
        <tr>
          <td width="100%"><div align="center">
          <font size="2">
          ���ڻ�ȡ���ݡ�����
          </font>
          </td>
        </tr>
    </table>
</div>
<TABLE border="0" cellspacing="0" cellpadding="0" width="820" >
<TR>
  <TD>
<table  border="0" align="center" cellpadding="0" cellspacing="10" >
  <tr>
    <td><img src="img/list_tit.gif" width="19" height="19"></td>
    <td class="title">ϵͳ�¼���ѯ</td>
  </tr>
</table>
 
<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>
<br>

<%
	pageContext.setAttribute("allSysyemEvent",Postern.getAllSystemEvent());
	pageContext.setAttribute("allSystemReason",Postern.getEventReason());
	pageContext.setAttribute("allOperatorName",Postern.getAllOperator());
	System.out.println("****************"+Postern.getAllOperator());
	pageContext.setAttribute("allProductName",Postern.getAllProductName());
	
%>
<form name="frmPost" action="system_event_result.do" method="post" >    
  <table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
   <tr>   
         <td class="list_bg2"><div align="right">�¼�����</div></td>
         <td class="list_bg1"  align="left">
         <tbl:select name="txtEventClass" set="allSysyemEvent" match="txtEventClass" width="23" />
         </td> 
           <td class="list_bg2"><div align="right">�¼�ԭ��</div></td>
           <td class="list_bg1" align="left">
          <tbl:select name="txtEventReason" set="allSystemReason" match="txtEventReason" width="23" />
     	  </td>
    </tr>
     <tr>  
          <td class="list_bg2"><div align="right">����ԱLoginID</div></td>
           <td class="list_bg1" align="left">           
          <input type="text" name="txtOperatorName" size="25" value="<tbl:writeparam name="txtOperatorName"/>" >					
     	  </td> 
         <td class="list_bg2"><div align="right">�ͻ�֤��</div></td>
         <td class="list_bg1"  align="left">
         <input type="text" name="txtCustomerID" size="25"   value="<tbl:writeparam name="txtCustomerID" />"  >
         </td> 
    </tr>
    <tr>
      <td class="list_bg2"><div align="right">�ʽ��˺�</div></td>
      <td class="list_bg1" align="left">
        <input type="text" name="txtAccountID" size="25" value="<tbl:writeparam name="txtAccountID"/>" >
      </td>
       <td class="list_bg2"><div align="right">ҵ���˺�</div></td>
        <td class="list_bg1" align="left">
	<input type="text" name="txtServiceAccountID" size="25" value="<tbl:writeparam name="txtServiceAccountID"/>" >
      </td>	
   </tr>
      <tr>
      <td class="list_bg2"><div align="right">�ͻ���Ʒ��</div></td>
      <td class="list_bg1" align="left">
        <input type="text" name="txtPSID" size="25" value="<tbl:writeparam name="txtPSID"/>" >
      </td>
       <td class="list_bg2"><div align="right">��Ӫ�̲�Ʒ</div></td>
        <td class="list_bg1" align="left">
	   <tbl:select name="txtproductID" set="allProductName" match="txtproductID" width="23" />
      </td>	
   </tr>
    <tr>  
        <td class="list_bg2"><div align="right">������</div></td>
        <td class="list_bg1" align="left">
          <input type="text" name="txtCSIID" size="25" value="<tbl:writeparam name="txtCSIID"/>" >
     	</td>
        <td class="list_bg2"><div align="right">�豸ID</div></td>
        <td class="list_bg1" align="left">
	 <input type="text" name="txtDeviceID" size="25" value="<tbl:writeparam name="txtDeviceID"/>" >
        </td>
    </tr>
      <tr>
          <td  class="list_bg2"><div align="right">��ʼʱ��</div></td> 
           <td class="list_bg1" > 
            <d:datetime name="txtCreateStartDate" size="16" match="txtCreateStartDate" includeHour="true" onclick="MyCalendar.SetDate(this,document.frmPost.txtCreateStartDateDatePart,'Y')" picURL="img/calendar.gif" style="cursor:hand" />                     </td> 
          <td  class="list_bg2"><div align="right">����ʱ��</div></td> 
           <td class="list_bg1" > 
             <d:datetime name="txtCreateEndDate" size="16" match="txtCreateEndDate" includeHour="true" onclick="MyCalendar.SetDate(this,document.frmPost.txtCreateEndDateDatePart,'Y')" picURL="img/calendar.gif" style="cursor:hand" />                       </td> 
     </tr>
     <tr>  
        <td class="list_bg2"><div align="right">�豸���к�</div></td>
        <td class="list_bg1" align="left" colspan="3">
          <input type="text" name="txtSerialNo" size="25" value="<tbl:writeparam name="txtSerialNo"/>" >
     	</td>
      
    </tr>
       
   </table>
    <table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
	  <tr align="center">
	  <td class="list_bg1"><table  border="0" cellspacing="0" cellpadding="0">
		  <tr>
			<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
			<td><input name="Submit" type="button" class="button" value="�� ѯ" onclick="javascript:query_submit()"></td>
			<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
		  </tr>
	  </table> 
	 </td>
  </tr>
  </table>
      <input type="hidden" name="txtFrom" size="20" value="1">
      <input type="hidden" name="txtTo" size="20" value="10">
         
   
 <table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
   <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
 </table>
<rs:hasresultset>
 <table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
         <tr class="list_head">
         <td width="8%" class="list_head"><div align="center">��ˮ��</div></td>
         <td  width="9%" class="list_head"><div align="center">�¼�����</div></td> 
          <td  width="9%" class="list_head"><div align="center">�¼�ԭ��</div></td>         
          <td  width="7%" class="list_head"><div align="center">�ͻ�֤��</div></td>
          <td width="10%" class="list_head"><div align="center">�ʽ��˻���</div></td>
          <td  width="10%"class="list_head"><div align="center">ҵ���˻���</div></td>
          <td width="10%" class="list_head"><div align="center">�ͻ���Ʒ</div></td>
          <td  width="10%" class="list_head"><div align="center">��Ӫ�̲�Ʒ</div></td> 
          <td  width="8%" class="list_head"><div align="center">������</div></td>    
          <td  width="10%" class="list_head"><div align="center">����ʱ��</div></td>    
            <td  width="10%" class="list_head"><div align="center">����Ա</div></td>  
     </tr> 
        
<lgc:bloop requestObjectName="ResponseQueryResult" item="oneline" >
 <%
   com.dtv.oss.dto.SystemEventDTO eventDto = (com.dtv.oss.dto.SystemEventDTO)pageContext.getAttribute("oneline");
            int eventClass = eventDto.getEventClass();
            String eventName = Postern.getEventNameByEventClass(eventClass);
            String eventReason = Postern.getReasonByReasonCode(eventDto.getEventReason(),eventClass);
            if(eventReason==null) eventReason="";
            String productName = Postern.getProductNameByID(eventDto.getProductID());
             if(productName==null) productName="";
            String operatorName = Postern.getOperatorNameByID(eventDto.getOperatorID());
 %>
    <tbl:printcsstr style1="list_bg1" style2="list_bg2" overStyle="list_over" >
           <td align="center" ><a href="javascript:view_detail_click('<tbl:write name="oneline" property="SequenceNo"/>')" class="link12">
              <div align="center">
               <tbl:writenumber name="oneline" property="SequenceNo" digit="8" />
             </div>    
          </td>
      		 
      		<td align="center"><%=eventName%></td>   
      		<td align="center"><%=eventReason%></td>  		
      		<td align="center" ><tbl:write name="oneline"  property="CustomerID"/></td>
      		<td align="center"><tbl:write name="oneline"  property="accountID"/></td>
      		<td align="center"><tbl:write name="oneline" property="serviceAccountID"/></td>
      		<td align="center"><tbl:write name="oneline"  property="psID"/></td>
      		<td align="center"><%=productName%></td>  
      		<td align="center"><tbl:write name="oneline"  property="csiID"/></td>
      		<td align="center"><tbl:writedate name="oneline" property="dtCreate" includeTime="true" /></td>
      		<td align="center"><%=operatorName%></td>  
      		
      		 
      		
     </tbl:printcsstr>
</lgc:bloop> 
   
     <tr>
    <td colspan="11" class="list_foot"></td>
  </tr>
 
            <tr>
              <td align="right" class="t12" colspan="11" >
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
	 
    </td>
  </tr>
</form>  
</table>  
 

      
