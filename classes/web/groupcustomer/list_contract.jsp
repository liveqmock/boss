<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="resultset" prefix="rs" %>
<%@ taglib uri="osstags" prefix="d" %>
<%@ page import="com.dtv.oss.dto.ContractDTO" %>
 
 
<script language=javascript>
 
function check_form(){
 
	if (document.frmPost.txtBeginTime.value != ''){
		if (!check_TenDate(document.frmPost.txtBeginTime, true, "��Ч��ʼ���ڣ���")){
			return false;
		}
	}
	 
	if (document.frmPost.txtEndTime.value != ''){
		if (!check_TenDate(document.frmPost.txtEndTime, true, "��Ч��ʼ���ڣ�ֹ��")){
			return false;
		}
	}
	 
	if(!compareDate(document.frmPost.txtBeginTime,document.frmPost.txtEndTime,"��Ч��ʼ���ڣ�ֹ��������ڵ�����Ч��ʼ���ڣ���")){
		
		return false;
	}
	  if (document.frmPost.txtAvailableStartDate.value != ''){
		if (!check_TenDate(document.frmPost.txtAvailableStartDate, true, "��Ч�������ڣ���")){
			return false;
		}
	}
	if (document.frmPost.txtAvailableEndDate.value != ''){
		if (!check_TenDate(document.frmPost.txtAvailableEndDate, true, "��Ч�������ڣ�ֹ��")){
			return false;
		}
	}
	if(!compareDate(document.frmPost.txtAvailableStartDate,document.frmPost.txtAvailableEndDate,"��Ч�������ڣ�ֹ��������ڵ�����Ч�������ڣ���")){
		
		return false;
	}
	            
	return true;
}


function query_submit()
{
        if (check_form()) document.frmPost.submit();
}
function frmPost_close(){
   var contractNo=eval('window.opener.frmPost.txtContractNo');
   l_contractNo=document.getElementsByName("choosedID") ;
   l_contractStatus=document.getElementsByName("txtContractStatus");
   l_usedCustomerID=document.getElementsByName("txtUsedCustomerID");
	for(i=0;i<l_contractNo.length;i++){  
		if(l_contractNo[i].checked){
	           contractNo.value=l_contractNo[i].value;
	           
		} 
	}
   if(contractNo.value==''){
   	alert("δѡ����Ч�ĺ�ͬ�ţ�");
   	return;
   }
	window.close();
}

</script>

 
<TABLE border="0" cellspacing="0" cellpadding="0" width="820" >
<TR>
	<TD>
 
 
<table  border="0" align="center" cellpadding="0" cellspacing="10" >
  <tr>
    <td><img src="img/list_tit.gif" width="19" height="19"></td>
    <td class="title">��ͬ��ѯ</td>
  </tr>
</table>
 
<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>
<br>
<form name="frmPost" action="contract_query_result_list.do" method="post" >   
<input type="hidden" name="txtGroupCustID" value="<tbl:writeparam name="txtGroupCustID" />">
<table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
  <tr>
    <td class="list_bg2"><div align="right">��ͬ���</div></td>
    <td class="list_bg1">
    <input name="txtContractNo" type="text" class="text" maxlength="10" size="25" value="<tbl:writeparam name="txtContractNo" />"></td>
    <td class="list_bg2"><div align="right">��ͬ����</div></td>         
    <td class="list_bg1">
        <input type="text" name="txtName" maxlength="10" size="25" value="<tbl:writeparam name="txtName" />" >
    </td>   
  </tr>
   
  <tr>
          <td  class="list_bg2"><div align="right">��Ч��ʼ����</div></td> 
           <td class="list_bg1" > 
                
            <d:datetime name="txtBeginTime" size="10" match="txtBeginTime"  onclick="MyCalendar.SetDate(this,document.frmPost.txtBeginTime,'Y')" picURL="img/calendar.gif" style="cursor:hand" />            
             - 
             <d:datetime name="txtEndTime" size="10" match="txtEndTime"  onclick="MyCalendar.SetDate(this,document.frmPost.txtEndTime,'Y')" picURL="img/calendar.gif" style="cursor:hand" />           
             </td> 
              <td  class="list_bg2"><div align="right">��Ч��������</div></td>
            <td class="list_bg1"> 
            <d:datetime name="txtAvailableStartDate" size="10" match="txtAvailableStartDate" onclick="MyCalendar.SetDate(this,document.frmPost.txtAvailableStartDate,'Y')" picURL="img/calendar.gif" style="cursor:hand" />            
             - 
            <d:datetime name="txtAvailableEndDate" size="10" match="txtAvailableEndDate" onclick="MyCalendar.SetDate(this,document.frmPost.txtAvailableEndDate,'Y')" picURL="img/calendar.gif" style="cursor:hand" />
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
	  </table></td>
	</tr>
</table>   
 
	  <input type="hidden" name="txtFrom" size="22" value="1">
          <input type="hidden" name="txtTo" size="22" value="10">
           
         

 
<rs:hasresultset>
<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>
<br>
<table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
  <tr class="list_head">
    <td class="list_head"></td>
    <td class="list_head" nowrap>��ͬ���</td>
    <td class="list_head" nowrap>��ͬ����</td>
    <td class="list_head" nowrap>��Ч��ʼ����</td>
    <td class="list_head" nowrap>��Ч��������</td>
    <td class="list_head" nowrap>һ���Է��ܶ�</td>
    <td class="list_head" nowrap>������ܶ�</td>
    <td class="list_head" nowrap>Ԥ���ܶ�</td>
    <td class="list_head" nowrap>�û���</td>
    <td class="list_head" nowrap>״̬</td>
  </tr>
<lgc:bloop requestObjectName="ResponseQueryResult" item="oneline" >
 
 

 <tr class="list_bg1" onmouseover="this.className='list_over'" onmouseout="this.className='list_bg1'">
 <%ContractDTO dto=(ContractDTO)pageContext.getAttribute("oneline");
   String cno=dto.getContractNo();%>
    <td align="center"><input type="radio" name="choosedID" value="<%=cno%>"/></td>
    <td align="center"><tbl:write name="oneline" property="contractNo" /></td>
    <td align="center"><tbl:write name="oneline" property="name"/></td>
    <td align="center"><tbl:writedate name="oneline" property="datefrom"/></td>  
    <td align="center"><tbl:writedate name="oneline" property="dateto"/></td>  
    <td align="center"><tbl:write name="oneline" property="oneOffFeeTotal"/></td> 
    <td align="center"><tbl:write name="oneline" property="rentFeeTotal"/></td> 
    <td align="center"><tbl:write name="oneline" property="prepayAmount"/></td> 
    <td align="center"><tbl:write name="oneline" property="userCount"/></td> 
    <td align="center"><d:getcmnname typeName="SET_C_CONTRACTSTATUS" match="oneline:status" />
    <input type="hidden" name="txtUsedCustomerID" value="<tbl:write name="oneline" property="usedCustomerID"/>">
    <input type="hidden" name="txtContractStatus" value="<tbl:write name="oneline" property="status"/>">
    </td>
  </tr>
 

</lgc:bloop>

  <tr>
    <td colspan="10" class="list_foot"></td>
  </tr>
 
            <tr>
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
<br>
<table align="center" border="0" cellspacing="0" cellpadding="0" >
	<tr >
	   <td><img src="img/button2_r.gif" width="22" height="20"></td>
	   <td><input name="prev" type="button" class="button" onClick="javascript:window.close()" value="�رմ���"></td>
	   <td><img src="img/button2_l.gif" width="11" height="20"></td>
	   <td width="20" ></td>
	   <td><img src="img/button_l.gif" border="0" width="11" height="20"></td>
	   <td><input name="next" type="button" class="button" onClick="javascript:frmPost_close()" value="ȷ ��"></td>
	   <td><img src="img/button_r.gif" border="0" width="22" height="20"></td>
	</tr>
</table>
</rs:hasresultset>  
     
</form>              
</TD>
</TR>
</TABLE>
