 
 
 <%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="resultset" prefix="rs" %>
<%@ taglib uri="osstags" prefix="d" %>

 
 
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

function view_detail_click(strId)
{
	self.location.href="contract_view.do?txtContractNo="+strId;
}
 
 
function create_submit() {
   
   document.frmPost.action="contract_create.screen";
   document.frmPost.submit();
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
<form name="frmPost" action="contract_query_result.do" method="post" >   

<table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
  <tr>
    <td class="list_bg2"><div align="right">��ͬ���</div></td>
    <td class="list_bg1">
    <input name="txtContractNo" type="text" class="text" maxlength="17" size="25" value="<tbl:writeparam name="txtContractNo" />"></td>
    <td class="list_bg2"><div align="right">��ͬ����</div></td>         
    <td class="list_bg1">
        <input type="text" name="txtName" maxlength="100" size="25" value="<tbl:writeparam name="txtName" />" >
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
			 <td width="20" ></td>
			 <td><img src="img/button_l.gif" border="0" ></td>
                         <td background="img/button_bg.gif"><a href="javascript:create_submit()" class="btn12">��&nbsp;��</a></td>
                        <td><img src="img/button_r.gif" border="0" ></td>
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
    <td class="list_head">��ͬ���</td>
    <td class="list_head">��ͬ����</td>
    <td class="list_head">��Ч��ʼ����</td>
    <td class="list_head">��Ч��������</td>
    <td class="list_head">һ���Է��ܶ�</td>
    <td class="list_head">������ܶ�</td>
    <td class="list_head">Ԥ���ܶ�</td>
    <td class="list_head">�û���</td>
    <td class="list_head">״̬</td>
  </tr>
<lgc:bloop requestObjectName="ResponseQueryResult" item="oneline" >
 
 

 <tr class="list_bg1" onmouseover="this.className='list_over'" onmouseout="this.className='list_bg1'">
    <td align="center"><a href="javascript:view_detail_click('<tbl:write name="oneline" property="contractNo" />')" class="link12" ><tbl:write name="oneline" property="contractNo"/></a></td>
    <td align="center"><tbl:write name="oneline" property="name"/></td>
    <td align="center"><tbl:writedate name="oneline" property="datefrom"/></td>  
    <td align="center"><tbl:writedate name="oneline" property="dateto"/></td>  
    <td align="center"><tbl:write name="oneline" property="oneOffFeeTotal"/></td> 
    <td align="center"><tbl:write name="oneline" property="rentFeeTotal"/></td> 
    <td align="center"><tbl:write name="oneline" property="prepayAmount"/></td> 
    <td align="center"><tbl:write name="oneline" property="userCount"/></td> 
    <td align="center"><d:getcmnname typeName="SET_C_CONTRACTSTATUS" match="oneline:status" /></td>
  </tr>
 

</lgc:bloop>

  <tr>
    <td colspan="9" class="list_foot"></td>
  </tr>
 
            <tr>
              <td align="right" class="t12" colspan="9" >
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
</TD>
</TR>
</TABLE>
