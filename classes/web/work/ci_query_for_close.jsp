<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="resultset" prefix="rs" %>
<%@ taglib uri="osstags" prefix="o" %>
<%@ page import="com.dtv.oss.dto.AddressDTO" %>
<%@ page import="com.dtv.oss.dto.JobCardDTO" %>
<%@ page import="com.dtv.oss.util.Postern" %>
<script language=javascript>
function query_submit()
{
	if(checkDate()){
	    
		document.frmPost.submit();
	}
}
function checkDate(){


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
	 if (document.frmPost.txtPreferedStartDate.value != ''){
		if (!check_TenDate(document.frmPost.txtPreferedStartDate, true, "��ʼ����")){
			return false;
		}
	}
	if (document.frmPost.txtPreferedEndDate.value != ''){
		if (!check_TenDate(document.frmPost.txtPreferedEndDate, true, "��������")){
			return false;
		}
	}
	if(!compareDate(document.frmPost.txtPreferedStartDate,document.frmPost.txtPreferedEndDate,"�������ڱ�����ڵ��ڿ�ʼ����")){
		
		return false;
	}
 	 if(!checkPlainNum(document.frmPost.txtJobCardID,true,9,"�������"))
		return false; 
	    
	return true;
}
	
function view_detail_click(strId)
{
	self.location.href="ci_view_for_close.do?txtJobCardID="+strId;
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
<table width="820" align="center" cellpadding="0" cellspacing="0">
<tr><td>
 
 
<table  border="0" align="center" cellpadding="0" cellspacing="10">
  <tr>
    <td><img src="img/list_tit.gif" width="19" height="19"></td>
    <td  class="title">������װ--������ѯ</td>
  </tr>
  </table>
  <table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>
<br>
<form name="frmPost" method="post" action="ci_query_result_for_close.do" >
   <table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
        <tr>
            <td  class="list_bg2"><div align="right">�������</div></td>
            <td class="list_bg1">
            <input type="text" name="txtJobCardID" maxlength="9" size="25" value="<tbl:writeparam name="txtJobCardID" />" >
            </td>
            <td class="list_bg2"><div align="right">��������</div></td> 
            <td class="list_bg1"  valign="middle">
            <input type="hidden" name="txtCounty" value="<tbl:writeparam name="txtCounty" />">
	    <input type="text" name="txtCountyDesc" size="25" maxlength="50" readonly value="<tbl:WriteDistrictInfo property="txtCounty" />" class="text">
            <input name="selDistButton" type="button" class="button" value="ѡ��" onClick="javascript:sel_district('all','txtCounty','txtCountyDesc')">
            </td>
        </tr>
         <tr>
          <td  class="list_bg2"><div align="right">����ʱ��</div></td> 
           <td class="list_bg1"> 
                
            <o:datetime name="txtCreateStartDate" size="10" match="txtCreateStartDate" includeHour="true" onclick="calendar(document.frmPost.txtCreateStartDateDatePart)" picURL="img/calendar.gif" style="cursor:hand" />            
             - 
             <o:datetime name="txtCreateEndDate" size="10" match="txtCreateEndDate" includeHour="true" onclick="calendar(document.frmPost.txtCreateEndDateDatePart)" picURL="img/calendar.gif" style="cursor:hand" />           
             </td> 
              <td  class="list_bg2"><div align="right">ԤԼ����ʱ��</div></td>
            <td class="list_bg1"> 
            <o:datetime name="txtPreferedStartDate" size="10" match="txtPreferedStartDate" onclick="calendar(document.frmPost.txtPreferedStartDate)" picURL="img/calendar.gif" style="cursor:hand" />            
             - 
            <o:datetime name="txtPreferedEndDate" size="10" match="txtPreferedEndDate" onclick="calendar(document.frmPost.txtPreferedEndDate)" picURL="img/calendar.gif" style="cursor:hand" />
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
      <input type="hidden" name="txtFrom"  value="1">
      <input type="hidden" name="txtTo"  value="10">
       <input type="hidden" name="txtStatus"  value="F">
       <input type="hidden" name="txtType"  value="I">
       <input type="hidden" name="actiontype" value="closeinstall">
      <rs:hasresultset>
    <table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
   <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>
  <br>
<table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
         <tr class="list_head">
          <td class="list_head" nowrap>������</td>
          <td class="list_head" nowrap>����ʱ��</td> 
          <td class="list_head" nowrap>ԤԼ����ʱ��</td> 
          <td class="list_head" nowrap>��ϵ��</td>        
          <td class="list_head" nowrap>��ϵ�绰</td>
          <td class="list_head" nowrap>��ϵ��ַ</td>
          <td class="list_head" nowrap>��������</td>
          <td class="list_head"></td>
           </tr>

        <% 
          String url1 = null;
           %>
<lgc:bloop requestObjectName="ResponseQueryResult" item="oneline" >
<%
  
	com.dtv.oss.dto.wrap.work.JobCard2JobCardProcessWrap wrap = (com.dtv.oss.dto.wrap.work.JobCard2JobCardProcessWrap)pageContext.getAttribute("oneline");
	JobCardDTO dto=wrap.getJobCardDto();
	 pageContext.setAttribute("oneline", wrap.getJobCardDto());
        url1="job_card_view.do";
	String strAddress = Postern.getAddressById(dto.getAddressId());
	 AddressDTO addrDto = Postern.getAddressDtoByID(dto.getAddressId());
         pageContext.setAttribute("custaddr", addrDto);
	if (strAddress == null) strAddress="";
	 
%>
		<tr class="list_bg1" onmouseover="this.className='list_over'" onmouseout="this.className='list_bg1'">
		    <td align="center"><tbl:writenumber name="oneline" property="JobCardId" digit="7" /></a></td>
		     <td align="center"><tbl:writedate name="oneline" property="dtCreate"  includeTime="true"/></td>
      		    <td align="center"><tbl:writedate name="oneline" property="preferedDate"  includeTime="false"/></td>      
      		    <td align="center"><tbl:write name="oneline" property="ContactPerson"/></td>
      		    <td align="center"><tbl:write name="oneline" property="ContactPhone"/></td>      
      		    <td align="center"><%=strAddress%></td>
      		    <td align="center"><tbl:WriteDistrictInfo name="custaddr" property="districtID" /></td>
      		    <td align="center"><a href="javascript:view_detail_click('<tbl:write name="oneline" property="JobCardId"/>')" class="link12" >������װ����</a></td>
    		</tr>
 
</lgc:bloop>
  <tr>
    <td colspan="9" class="list_foot"></td>
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
<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
 </table>
</rs:hasresultset> 
  </td>
  </tr>
  </table>