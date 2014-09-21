<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="resultset" prefix="rs" %>
<%@ taglib uri="osstags" prefix="d" %>
<%@ page import="com.dtv.oss.dto.wrap.work.CustomerProblem2CPProcessWrap" %>
<%@ page import="com.dtv.oss.dto.CustomerProblemDTO" %>
<%@ page import="com.dtv.oss.dto.CustProblemProcessDTO" %>
<%@ page import="com.dtv.oss.dto.AddressDTO" %>
<%@ page import="com.dtv.oss.util.Postern" %>
<%@ page import="com.dtv.oss.web.util.CurrentLogonOperator"%>
<%@ page import="com.dtv.oss.dto.OperatorDTO"%>

<script language=javascript>
function query_submit()
{	if(checkDate()){
		
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
	if(!checkPlainNum(document.frmPost.txtCustomerProblemID,true,9,"���޵����"))
		return false; 
	return true;
}
function diagnosis_click(strId,csaid)
{
	self.location.href="menu_cp_diagnosis.do?txtCustomerProblemID="+strId+"&&txtServiceAccountID="+csaid;
}
function view_detail_click(strId)
{
	self.location.href="cp_query_detail.do?txtCustomerProblemID="+strId;
}
</script>
<div id="updselwaitlayer"
	style="position:absolute; left:650px; top:170px; width:250px; height:24px; display:none">
<table width="100%" border="0" cellspacing="1" cellpadding="3">
	<tr>
		<td width="100%">
		<div align="center"><font size="2"> ���ڻ�ȡ���ݡ����� </font>
		</td>
	</tr>
</table>
</div>
<table width="820" align="center" cellpadding="0" cellspacing="0">
<tr><td>
<table  border="0" align="center" cellpadding="0" cellspacing="10">
  <tr>
    <td><img src="img/list_tit.gif" width="19" height="19"></td>
    <td  class="title">���޵���ϲ�ѯ</td>
  </tr>
  </table>
  <table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>
<table align="center" border="0" cellpadding="0" cellspacing="0" width="820">
  <tr>
    <td aglin="center">
<iframe SRC="update_select.screen" name="FrameUS" width="0" height="0"
	frameborder="0" scrolling="0"> </iframe>
	    </td>
  </tr>
</table>
<form name="frmPost" method="post" action="cp_diagnosis_query.do" >
 
      <input type="hidden" name="txtStatus" value="W">
   <table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
        <tr> 
          <td class="list_bg2"><div align="right">���޵����</div></td>  
          <td class="list_bg1">
          <input type="text" name="txtCustomerProblemID" maxlength="9" size="25"  value="<tbl:writeparam name="txtCustomerProblemID" />" >
         </td>
          <td  class="list_bg2"><div align="right">����ʱ��</div></td> 
           <td class="list_bg1" > 
            <d:datetime name="txtCreateStartDate" size="10" match="txtCreateStartDate" includeHour="false" onclick="MyCalendar.SetDate(this,document.frmPost.txtCreateStartDate,'Y')" picURL="img/calendar.gif" style="cursor:hand" />            
             - 
             <d:datetime name="txtCreateEndDate" size="10" match="txtCreateEndDate" includeHour="false" onclick="MyCalendar.SetDate(this,document.frmPost.txtCreateEndDate,'Y')" picURL="img/calendar.gif" style="cursor:hand" />           
            </td>
        </tr>
         <tr>         
           <td  class="list_bg2"><div align="right">�������</div></td> 
           <td class="list_bg1"> 
            <d:selcmn name="txtProblemCategory" mapName="SET_C_CPPROBLEMCATEGORY" match="txtProblemCategory" width="25"/> 
             </td>
           <td  class="list_bg2"><div align="right">�շ����</div></td>
	  <td class="list_bg1"> 
	 <d:selcmn name="txtFeeClass" mapName="SET_C_CPFEECLASS" match="txtFeeClass" width="25" />
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
    <input type="hidden" name="txtFrom" size="20" value="1">
      <input type="hidden" name="txtTo" size="20" value="10">
     
      
       
    
  
  <table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
   <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
 </table>
 
  
  <rs:hasresultset>
     <input type="hidden" name="txtFrom"  value="1">
      <input type="hidden" name="txtTo"  value="10">
 <table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
		 <tr class="list_head">		      
			<td class="list_head">���޵���</td>
			<td class="list_head">��������</td>
			<td class="list_head">�������</td>
			 <td class="list_head">�շ����</td>
			<td class="list_head">��ַ</td>
			<td class="list_head">��������</td> 
			<td class="list_head"></td>     
	       </tr>
	<lgc:bloop requestObjectName="ResponseQueryResult" item="oneline" >
	<%
	  //CustomerProblem2CPProcessWrap wrap = (CustomerProblem2CPProcessWrap)pageContext.getAttribute("oneline");
	  CustomerProblemDTO dto=(CustomerProblemDTO)pageContext.getAttribute("oneline");;
	   
	  //CustProblemProcessDTO cppDto=wrap.getCppDto();
	  String strAddress = Postern.getAddressById(dto.getAddressID());
	  AddressDTO addrDto = Postern.getAddressDtoByID(dto.getAddressID());
          pageContext.setAttribute("oneline", dto);
         // pageContext.setAttribute("cpp", cppDto);
          pageContext.setAttribute("custaddr", addrDto);
	if (strAddress==null) strAddress="";
	int operOrgID=CurrentLogonOperator.getOperator(request).getOrgID();
	int nextorgid=Postern.getNextOrgIDByCPID(dto.getId());	
				    
	%>
		<tr class="list_bg1" onmouseover="this.className='list_over'" onmouseout="this.className='list_bg1'">		        
		        <td align="center"><a href="javascript:view_detail_click('<tbl:write name="oneline" property="Id"/>')" class="link12" ><tbl:writenumber name="oneline" property="Id" digit="7"/></a></td>
      			<td align="center"><tbl:WriteDistrictInfo name="custaddr" property="districtID" /></td>
      			<td align="center"><d:getcmnname typeName="SET_C_CPPROBLEMCATEGORY" match="oneline:ProblemCategory"/></td>
      			<td align="center"><d:getcmnname typeName="SET_C_CPFEECLASS" match="oneline:FeeClass"/></td>
      			<td align="center"><%=strAddress%></td> 
      			<td align="center"><tbl:write name="oneline" property="ProblemDesc"/></td>      			
      		        <td align="center"><a href="javascript:diagnosis_click('<tbl:write name="oneline" property="Id"/>','<tbl:write name="oneline" property="custServiceAccountID"/>')" class="link12" >�������</a></td>      			
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
	 
    </td>
  </tr>
</form>  
</table>  

