<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>

<%@ taglib uri="resultset" prefix="rs" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="osstags" prefix="d" %>
 
<%@ page import="com.dtv.oss.util.Postern"%>

<script language=javascript>
function check_form(){
            if (!check_TenDate(document.frmPost.txtStartTime, true, "��ʼʱ��"))
            {
                
        	return false;	
            }
            if (!check_TenDate(document.frmPost.txtEndTime, true, "��ֹʱ��"))
            {
                
        	return false;	
            }
            if(!compareDate(document.frmPost.txtStartTime,document.frmPost.txtEndTime,"�������ڱ�����ڵ��ڿ�ʼ����")){
		
		return false;
	}   	
	 
       	
	return true;
}

function query_submit()
{
        if (check_form()) document.frmPost.submit();
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
    <td class="title">��־��ѯ</td>
  </tr>
</table>
 
<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>
<br>

<%
	pageContext.setAttribute("allModule",Postern.getModueNameOfLog());
	pageContext.setAttribute("allOperation",Postern.getOperationOfLog());
%>
<form name="frmPost" action="log_query_by_custid.do" method="post" >    
  <table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
   
     <tr>   
           
          <td class="list_bg2"><div align="right">�ͻ�����</div></td>
          <td class="list_bg1" align="left">
          <input type="text" name="txtCustomerName" size="25" value="<tbl:writeparam name="txtCustomerName"/>" >
     	</td>
     	
            <td class="list_bg2"><div align="right">ģ����</div></td>
           <td class="list_bg1">
	  <tbl:select name="txtModuleName" set="allModule" match="txtModuleName" width="23" />
     	  </td>
          
           
          
      </tr>
     
       
      
    <tr>
      <td class="list_bg2"><div align="right">ִ�в����Ĳ���Ա</div></td>
      <td class="list_bg1" align="left">
          <input type="text" name="txtLoginID" size="25" value="<tbl:writeparam name="txtLoginID"/>" >
      </td>
       <td class="list_bg2"><div align="right">��ִ�еò���</div></td>
         <td class="list_bg1" align="left">
	<tbl:select name="txtOperation" set="allOperation" match="txtOperation" width="23" />
      </td>	
      
    </tr>
     
   
     <tr>  
      <td class="list_bg2"><div align="right">��ʼʱ��</div></td>
      <td class="list_bg1" align="left">
          <input maxlength="10" onfocus="viewModule(this)" onKeydown="inputDate(document.frmPost.txtStartTime)" onblur="lostFocus(this)" type="text" name="txtStartTime" size="25" value="<tbl:writeparam name="txtStartTime"/>" ><IMG onclick="MyCalendar.SetDate(this,document.frmPost.txtStartTime,'Y')" src="img/calendar.gif" style=cursor:hand border="0">
      </td>
      <td class="list_bg2"><div align="right">��ֹʱ��</div></td>
      <td class="list_bg1" align="left">
          <input maxlength="10" onfocus="viewModule(this)" onKeydown="inputDate(document.frmPost.txtEndTime)" onblur="lostFocus(this)" type="text" name="txtEndTime" size="25" value="<tbl:writeparam name="txtEndTime"/>" ><IMG onclick="MyCalendar.SetDate(this,document.frmPost.txtEndTime,'Y')" src="img/calendar.gif" style=cursor:hand border="0">
     	</td>
    </tr>
      <tr>  
        <td class="list_bg2"><div align="right">��������</div></td>
        <td class="list_bg1" align="left">
          <input type="text" name="txtMachineName" size="25" value="<tbl:writeparam name="txtMachineName"/>" >
     	</td>
        <td class="list_bg2"><div align="right">��־��Ҫ�Լ���</div></td>
        <td class="list_bg1" align="left">
	<d:selcmn name="txtLogClass" mapName="SET_S_LOGCLASS" match="txtLogClass" width="23" /> 
        </td>
    </tr>
    
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
         <td class="list_head"><div align="center">���</div></td>
         <td class="list_head"><div align="center">����Ա</div></td> 
                
          <td class="list_head"><div align="center">ģ��</div></td>
          <td class="list_head"><div align="center">ʱ��</div></td>
          <td class="list_head"><div align="center">����</div></td>
          <td class="list_head"><div align="center">����</div></td>
              
     </tr> 
        
<lgc:bloop requestObjectName="ResponseQueryResult" item="oneline" >
 
   <tr class="list_bg1" onmouseover="this.className='list_over'" onmouseout="this.className='list_bg1'">
      		<td align="center" class="t12"><tbl:write name="oneline" property="SequenceNo"/></td>
      		   		
      		<td align="center" class="t12"><tbl:write name="oneline" property="LoginID"/></td>
      		 
      		 
      		<td align="center" class="t12"><tbl:write name="oneline" property="ModuleName"/></td>
      		<td align="center" class="t12"><tbl:writedate name="oneline" property="CreateDateTime" includeTime="true" /></td>
      		<td align="center" class="t12"><tbl:write name="oneline" property="Operation"/></td>
      		<td align="center" class="t12"><tbl:write name="oneline" property="LogDesc"/></td>
      		 
      		
    </tr>
</lgc:bloop> 
   
     <tr>
    <td colspan="9" class="list_foot"></td>
  </tr>
</table>
<table  border="0" align="center" cellpadding="0" cellspacing="8">
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
<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
 </table>
</rs:hasresultset> 
	 
    </td>
  </tr>
</form>  
</table>  
 

      