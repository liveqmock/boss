<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="resultset" prefix="rs" %>
<%@ taglib uri="osstags" prefix="d" %>
<%@ page import="com.dtv.oss.dto.wrap.work.CustomerProblem2CPProcessWrap" %>
<%@ page import="com.dtv.oss.dto.CustomerProblemDTO" %>
<%@ page import="com.dtv.oss.dto.CustProblemProcessDTO" %>
<%@ page import="com.dtv.oss.dto.AddressDTO" %>
<%@ page import="com.dtv.oss.util.Postern" %>

<script language=javascript>
function query_submit()
{
	if(checkDate()){
		document.frmPost.submit();
	}
}

function view_detail_click(strId)
{
	self.location.href="activity_detail.do?txtActivityID="+strId;
}
function detail_delete(id) {
     
    if(confirm("ȷ��Ҫɾ������������?")){
    document.frmPost.vartxtActivityID.value = id ;
    document.frmPost.action="points_activity_delete_detail.do?Action=deleteDetail";
  document.frmPost.submit();}
} 

function checkDate(){
 
	if (document.frmPost.txtStartTime.value != ''){
		if (!check_TenDate(document.frmPost.txtStartTime, true, "��ʼ����")){
			return false;
		}
	}
	 
	if (document.frmPost.txtEndTime.value != ''){
		if (!check_TenDate(document.frmPost.txtEndTime, true, "��������")){
			return false;
		}
	}
	 
	if(!compareDate(document.frmPost.txtStartTime,document.frmPost.txtEndTime,"�������ڱ�����ڵ��ڿ�ʼ����")){
		
		return false;
	}
	 
	            
	return true;
}
function create_activity_submit() {
    
   document.frmPost.action="points_activity_create.screen";
   document.frmPost.submit();
}

</script>
<table width="820" align="center" cellpadding="0" cellspacing="0">
<tr><td>
<table  border="0" align="center" cellpadding="0" cellspacing="10">
  <tr>
    <td><img src="img/list_tit.gif" width="19" height="19"></td>
    <td  class="title">���ֻ��ѯ</td>
  </tr>
  </table>
  <table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>
 

 
<form name="frmPost" method="post" action="activity_query_result.do" >
 <input type="hidden" name="vartxtActivityID" value=""/>
 
   <table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
      
        <tr> 
          <td  class="list_bg2"><div align="right">�ID</div></td>         
          <td class="list_bg1" align="left">
           <input type="text" name="txtActivityID" maxlength="10" size="25"  value="<tbl:writeparam name="txtActivityID" />" >
           </td>      
           <td class="list_bg2"><div align="right">�����</div></td>
	      <td class="list_bg1" align="left">
              <input type="text" size="25" maxlength="25" name="txtName" value="<tbl:writeparam name="txtName"/>">
             </td>
           
        </tr>
          <tr>  
             <td class="list_bg2"><div align="right">���ʼʱ��</div></td>
             <td class="list_bg1" align="left">
            <input maxlength="10" onfocus="viewModule(this)" onKeydown="inputDate(document.frmPost.txtStartTime)" onblur="lostFocus(this)" type="text" name="txtStartTime" size="25" value="<tbl:writeparam name="txtStartTime"/>" ><IMG onclick="MyCalendar.SetDate(this,document.frmPost.txtStartTime,'Y')" src="img/calendar.gif" style=cursor:hand border="0">
           </td>
             <td class="list_bg2"><div align="right">�����ʱ��</div></td>
               <td class="list_bg1" align="left">
           <input maxlength="10" onfocus="viewModule(this)" onKeydown="inputDate(document.frmPost.txtEndTime)" onblur="lostFocus(this)" type="text" name="txtEndTime" size="25" value="<tbl:writeparam name="txtEndTime"/>" ><IMG onclick="MyCalendar.SetDate(this,document.frmPost.txtEndTime,'Y')" src="img/calendar.gif" style=cursor:hand border="0">
     	</td>
    </tr>
        
          
      </table>
    <table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
	  <tr align="center">
	  <td class="list_bg1">
       <table align="center" border="0" cellspacing="0" cellpadding="0">
         <tr>  
            <td><img src="img/button2_r.gif" width="22" height="20"></td>
			<td><input name="Submit" type="button" class="button"
					value="��&nbsp;ѯ" onclick="javascript:query_submit()"></td>
            <td><img src="img/button2_l.gif" width="11" height="20"></td>

            <td width="20" ></td>  

            <td><img src="img/button_l.gif" width="11" height="20"></td>
            <td><input name="Submit" type="button" class="button"
					value="��&nbsp;��" onclick="javascript:create_activity_submit()"></td>
            <td><img src="img/button_r.gif" width="22" height="20"></td>          
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
           <td class="list_head">�ID</td>
           <td class="list_head">�����</td>
           <td class="list_head">��ʼʱ��</td>
           <td class="list_head">����ʱ��</td>
           <td class="list_head">״̬</td>
           <td class="list_head"><div align="center">����</div></td>
           
        </tr>

<lgc:bloop requestObjectName="ResponseQueryResult" item="oneline" >
 
 
	 <tr class="list_bg1" onmouseover="this.className='list_over'" onmouseout="this.className='list_bg1'">
	     <td align="center"><a href="javascript:view_detail_click('<tbl:write name="oneline" property="Id"/>')" class="link12" ><tbl:writenumber name="oneline" property="Id" digit="7"/></a></td>
      	     <td align="center"><tbl:write name="oneline" property="name"/></td>
      	     <td align="center"><tbl:writedate name="oneline" property="dateStart" /></td>
      	     <td align="center"><tbl:writedate name="oneline" property="dateEnd" /></td>
      	     <td align="center"><d:getcmnname typeName="SET_G_GENERALSTATUS" match="oneline:Status"/></td>      			
      	     <td align="center">
             <A href="javascript:detail_delete('<tbl:write name="oneline" property="id"/>')">ɾ�� </A></td>
    	</tr>
</lgc:bloop>  

<tr>
    <td colspan="6" class="list_foot"></td>
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
 