<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="resultset" prefix="rs" %>
<%@ taglib uri="osstags" prefix="d" %>
 
 
<%@ page import="com.dtv.oss.dto.UserPointsExchangerCdDTO" %>
<%@ page import="com.dtv.oss.dto.AddressDTO" %>
<%@ page import="com.dtv.oss.util.Postern" %>
<%@ page import="java.util.Map" %>

<script language=javascript>
function query_submit()
{
	if(checkDate()){
		document.frmPost.submit();
	}
}

 
 

function checkDate(){
 
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
	 
	if(!compareDate(document.frmPost.txtCreateStartDate,document.frmPost.txtCreateEndDate,"�������ڱ�����ڵ��ڿ�ʼ����")){
		
		return false;
	}
	 
	            
	return true;
}

</script>
  
     <%
    
      Map mapActivityName = Postern.getActivityName();
     
      pageContext.setAttribute("AllActivityName",mapActivityName);
      
     Map mapGoodsName = Postern.getGoodsName();
      
     pageContext.setAttribute("AllGoodsName",mapGoodsName);
     
   %>
        
<table width="820" align="center" cellpadding="0" cellspacing="0">
<tr><td>
<table  border="0" align="center" cellpadding="0" cellspacing="10">
  <tr>
    <td><img src="img/list_tit.gif" width="19" height="19"></td>
    <td  class="title">���ֶһ���¼��ѯ</td>
  </tr>
  </table>
  <table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>
 

 
<form name="frmPost" method="post" action="user_points_query.do" >
 
   <table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
    
      
        <tr> 
          <td  class="list_bg2">���ֶһ��</td>          
          <td class="list_bg1">
            <tbl:select name="txtActivityId" set="AllActivityName" match="txtActivityId" width="25" />
           </td>      
           <td class="list_bg2">�һ���Ʒ</td>
	      <td class="list_bg1">
              <tbl:select name="txtGoodsId" set="AllGoodsName" match="txtGoodsId" width="25" />
             </td>
           
        </tr>
         
        <tr>
              <td class="list_bg2">�û�֤��</td>
	      <td class="list_bg1"> 
	       <input type="text" name="txtCustId" maxlength="10" size="25"  value="<tbl:writeparam name="txtCustId" />" >
	     </td> 
	      <td class="list_bg2">�û�����</td>
	      <td class="list_bg1"> 
	       <input type="text" name="txtName" maxlength="10" size="25"  value="<tbl:writeparam name="txtName" />" >
	     </td>     
        </tr>
          
        <tr>
          <td  class="list_bg2">��������</td> 
          <td class="list_bg1">
         <d:seldistrict  name="txtCounty" set="SET_G_DISTRICTTYPE" match="txtCounty" width="25" />
         </td>
         <td class="list_bg2">��ϸ��ַ</td>
	 <td class="list_bg1">
            <input type="text" name="txtDetailAddress" maxlength="10" size="25"  value="<tbl:writeparam name="txtDetailAddress" />" >
         </td>
      </tr> 
       <tr>
          <td  class="list_bg2">��¼����ʱ��</td>
           <td class="list_bg1" colspan="3"> 
            <d:datetime name="txtCreateStartDate" size="10" match="txtCreateStartDate" includeHour="false" onclick="MyCalendar.SetDate(this,document.frmPost.txtCreateStartDate,'Y')" picURL="img/calendar.gif" style="cursor:hand" />            
             - 
             <d:datetime name="txtCreateEndDate" size="10" match="txtCreateEndDate" includeHour="false" onclick="MyCalendar.SetDate(this,document.frmPost.txtCreateEndDate,'Y')" picURL="img/calendar.gif" style="cursor:hand" />           
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
           <td class="list_head">��¼ID</td>
           <td class="list_head">�����</td>
           <td class="list_head">��Ʒ����</td>
           <td class="list_head">����ʱ��</td>
           <td class="list_head">�û�֤��</td>
           <td class="list_head">�һ�ǰ����</td>
            <td class="list_head">�һ������</td>
           <td class="list_head">���ζһ�����</td>
           <td class="list_head">����Ա����</td>
           
        </tr>

<lgc:bloop requestObjectName="ResponseQueryResult" item="oneline" >
 
<% 
	UserPointsExchangerCdDTO recordDto = (UserPointsExchangerCdDTO)pageContext.getAttribute("oneline");
	String activityName = Postern.getEveryActivityName(recordDto.getActivityId());
	String goodName = Postern.getEveryGoodsName(recordDto.getExchangeGoodsTypeId());
	String oprName =Postern.getOperatorNameByID(recordDto.getCreateOperatorId());
%>
	 <tr class="list_bg1" onmouseover="this.className='list_over'" onmouseout="this.className='list_bg1'">
	    <td align="center"><tbl:writenumber name="oneline" property="Id" digit="7" hideZero="true"/></td>
      	     <td align="center"><%=activityName%></td>
      	     <td align="center"><%=goodName%></td>
      	      <td align="center" ><tbl:writedate name="oneline" property="CreateTime" includeTime="true" /></td>
      	      <td align="center"><tbl:writenumber name="oneline" property="userId" digit="7" hideZero="true"/></td>
      	        <td align="center"><tbl:write name="oneline" property="userPointsBefore" /></td>
      	      <td align="center"><tbl:write name="oneline" property="userPointPost" /></td>
      	     <td align="center"><tbl:write name="oneline" property="exchangePoints" /></td>
      	     <td align="center"><%=oprName%></td>
      	     
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
 
