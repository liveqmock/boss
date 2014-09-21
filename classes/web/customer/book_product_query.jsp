<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="resultset" prefix="rs" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="osstags" prefix="d" %>

<%@ page import="com.dtv.oss.util.Postern" %>

<%@ page import="com.dtv.oss.dto.*" %>
<%@ page import="com.dtv.oss.web.util.CommonKeys" %>
<%@ page import="com.dtv.oss.web.util.*" %>

<script language=javascript>
function check_form(){
       	if (document.frmPost.txtCreateTimeStart.value != ''){
		if (!check_TenDate(document.frmPost.txtCreateTimeStart, true, "ԤԼ��ʼʱ��")){
			return false;
		}
	}
	if (document.frmPost.txtCreateTimeEnd.value != ''){
		if (!check_TenDate(document.frmPost.txtCreateTimeEnd, true, "ԤԼ��������")){
			return false;
		}
	}
	
       if(!compareDate(document.frmPost.txtCreateTimeStart,document.frmPost.txtCreateTimeEnd,"ԤԼ�������ڱ�����ڵ��ڿ�ʼ����")){

	    return false;
        }
       
       if (document.frmPost.txtScheduleTimeStart.value != ''){
	   if (!check_TenDate(document.frmPost.txtScheduleTimeStart, true, "ԤԼ��Ч��ʼ����")){
		return false;
	   }
       }
       
       if (document.frmPost.txtPreferedEndDate.value != ''){
	  if (!check_TenDate(document.frmPost.txtPreferedEndDate, true, "ԤԼ��Ч��������")){
		return false;
	  }
       }
       if(!compareDate(document.frmPost.txtPreferedStartDate,document.frmPost.txtPreferedEndDate,"ԤԼ��Ч�������ڱ�����ڵ��ڿ�ʼ����")){
        	return false;
       }

	return true;
}

function query_submit()
{
        document.frmPost.submit();
}

function modify_submit(strId)
{
       document.frmPost.action="view_book_product_detail.do?txtCSIID="+strId;
       document.frmPost.submit();
}
</script>
<table  border="0" align="center" cellpadding="0" cellspacing="10">
  <tr>
    <td><img src="img/list_tit.gif" width="19" height="19"></td>
    <td class="title">ԤԼ������Ʒ������ѯ</td>
  </tr>
</table>
<table width="810"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>
<br>
<form name="frmPost" method="post" action="query_book_product.do" >
   <input type="hidden" name="txtFrom"  value="1">
   <input type="hidden" name="txtTo"  value="10">
   <tbl:hiddenparameters pass="txtID/txtCustomerID" />
   <table width="810"  border="0" align="center" cellpadding="4" cellspacing="1" class="list_bg">
        <tr>
          <td width="12%" align="right" class="list_bg2">������</td>
          <td width="33%" class="list_bg1" align="left"><font size="2">
             <input type="text" name="txtCSIID" maxlength="10" size="25"  value="<tbl:writeparam name="txtCSIID" />" class="text">          
          </font> </td>                      
          <td width="11%" class="list_bg2" align="right">״̬</td>          
          <td width="33%" class="list_bg1" align="left"><font size="2"> 
            <d:selcmn name="txtStatus" mapName="SET_V_CUSTSERVICEINTERACTIONSTATUS" match="txtStatus" width="20" />
          </font> </td>
        </tr> 
        <tr>  
           <td  class="list_bg2" align="right">ҵ���ʻ�ID</td>
           <td  class="list_bg1" align="left"><font size="2">
             <input type="text" name="txtSAID" maxlength="10" size="25"  value="<tbl:writeparam name="txtSAID" />" class="text">  
           </font></td>  
           <td  class="list_bg2" align="right">�豸���к�</td>
           <td  class="list_bg1" align="left"><font size="2">        
             <input type="text" name="txtSerialNo" maxlength="25" size="25"  value="<tbl:writeparam name="txtSerialNo" />" class="text">               
           </font></td>
        </tr>
        <tr>
            <td class="list_bg2" align="right">ԤԼʱ��</td>
            <td class="list_bg1" align="left" ><font size="2">
             <d:datetime name="txtCreateTimeStart" size="10" match="txtCreateTimeStart"  onclick="MyCalendar.SetDate(this,document.frmPost.txtCreateTimeStart,'Y')" picURL="img/calendar.gif" style="cursor:hand" />
              --
             <d:datetime name="txtCreateTimeEnd" size="10" match="txtCreateTimeEnd"  onclick="MyCalendar.SetDate(this,document.frmPost.txtCreateTimeEnd,'Y')" picURL="img/calendar.gif" style="cursor:hand" />
            </font></td>
            <td class="list_bg2" align="right">ԤԼ��Ч����</td>
            <td class="list_bg1" align="left" ><font size="2">
             <d:datetime name="txtScheduleTimeStart" size="10" match="txtScheduleTimeStart"  onclick="MyCalendar.SetDate(this,document.frmPost.txtScheduleTimeStart,'Y')" picURL="img/calendar.gif" style="cursor:hand" />
              --
             <d:datetime name="txtScheduleTimeEnd" size="10" match="txtScheduleTimeEnd"  onclick="MyCalendar.SetDate(this,document.frmPost.txtScheduleTimeEnd,'Y')" picURL="img/calendar.gif" style="cursor:hand" />
            </font></td>
        </tr>              
        <tr> 
           <td  class="list_bg2" colspan="4">
             <table  border="0" cellspacing="0" cellpadding="0" align="center">
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
    <rs:hasresultset>
    <br>
    <table width="810"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
       <tr class="list_head">                
         <td class="list_head">������</td>
         <td class="list_head">ԤԼʱ��</td>
         <td class="list_head">����Ա</td>
         <td class="list_head">ԤԼ��Ч����</td>
         <td class="list_head">״̬</td>
        </tr>
     <lgc:bloop requestObjectName="ResponseQueryResult" item="oneline" >
     <%CustServiceInteractionDTO cDto=(CustServiceInteractionDTO)pageContext.getAttribute("oneline");
       String oper=Postern.getOperatorNameByID(cDto.getCreateOperatorId());
       if(oper==null)oper="";%>
     <tbl:printcsstr style1="list_bg1" style2="list_bg2" overStyle="list_over" >
      <td align="center" ><a href="javascript:modify_submit('<tbl:write name="oneline" property="id" />')" class="link12"><tbl:write name="oneline" property="id" /></a></td>
      <td align="center" ><tbl:write name="oneline" property="dtCreate" /></td>
      <td align="center" ><%=oper%></td>
      <td align="center" ><tbl:write name="oneline" property="scheduleTime" /></td>
      <td align="center" ><d:getcmnname typeName="SET_V_CUSTSERVICEINTERACTIONSTATUS" match="oneline:status" /></td>
     </tbl:printcsstr>
    </lgc:bloop>
    
    <tr>
      <td colspan="5" class="list_foot"></td>
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



