<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="resultset" prefix="rs" %>
<%@ taglib uri="osstags" prefix="d" %>
<%@ page import="com.dtv.oss.util.Postern,
               com.dtv.oss.dto.OpGroupDTO,
               java.util.*" %>
 

<script language=javascript>

function check_frm()
{
	
 	
    if (!checkPlainNum(document.frmPost.txtOpGroupID, true,9, "����Ա��ID"))
	    return false;
    
	    
	    
     return true;
}
 
function query_submit()
{
	     if(check_frm())
		document.frmPost.submit();
	 
}

function view_detail_click(strId)
{
	self.location.href="op_group_detail.do?txtOpGroupID="+strId;
} 
 
 
 
function create_opgroup_submit() {
    
   document.frmPost.action="create_opgroup_create.screen";
   document.frmPost.submit();
}

</script>
<table width="820" align="center" cellpadding="0" cellspacing="0">
<tr><td>
<table  border="0" align="center" cellpadding="0" cellspacing="10">
 <tr>
    <td colspan="2" height="8"></td>
  </tr>
  <tr>
    <td><img src="img/list_tit.gif" width="19" height="19"></td>
    <td  class="title">����Ա���ѯ</td>
  </tr>
  </table>
  <table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>
 

 
<form name="frmPost" method="post" action="opgroup_query.do" >
 
 
   <table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
      
        <tr> 
          <td  class="list_bg2"><div align="right">����Ա��ID</div></td>         
          <td class="list_bg1" align="left">
           <input type="text" name="txtOpGroupID" maxlength="10" size="22"  value="<tbl:writeparam name="txtOpGroupID" />" >
           </td>      
             <td  class="list_bg2"><div align="right">����Ա������</div></td>         
             <td class="list_bg1" align="left">
              <input type="text" name="txtOpGroupName" maxlength="25" size="22"  value="<tbl:writeparam name="txtOpGroupName" />" >
             </td>      
           
        </tr>
         
     
       <table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
	  <tr align="center">
	  <td class="list_bg1">
	         <table  border="0" cellspacing="0" cellpadding="0">							
		  <tr>
		       <td><img src="img/button_l.gif" border="0" ></td>
			<td><input name="Submit" type="button" class="button" value="�� ѯ" onclick="javascript:query_submit()"/></td>
			<td><img src="img/button_r.gif" border="0" ></td>
		  	 
			<td width=20 ></td>
			<td><img src="img/button_l.gif" border="0" ></td>
			<td><input name="Submit" type="button" class="button" value="�� ��" onclick="javascript:create_opgroup_submit()"/></td>
			<td><img src="img/button_r.gif" border="0" ></td>
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
            <td class="list_head">����Ա��ID</td>
            <td class="list_head">����Ա������</td>
            <td class="list_head">����Ա�鼶��</td>
             <td class="list_head">ϵͳ�ڲ����־</td>
            <td class="list_head">����Ա��Ȩ��</td>
            <td class="list_head">����Ա���Ա</td>
          </tr>

<lgc:bloop requestObjectName="ResponseQueryResult" item="oneline" >
 
 <%
        OpGroupDTO opGroupDto = (OpGroupDTO)pageContext.getAttribute("oneline");
        
 %>
 
	 <tr class="list_bg1" onmouseover="this.className='list_over'" onmouseout="this.className='list_bg1'">
	     <td align="center"><a href="javascript:view_detail_click('<tbl:write name="oneline" property="opGroupID"/>')" class="link12" ><tbl:writenumber name="oneline" property="opGroupID" digit="7"/></a></td>
      	     <td align="center"><tbl:write name="oneline" property="opGroupName"/></td>
      	     <td align="center"><d:getcmnname typeName="SET_S_OPGROUPLEVEL" match="oneline:opGroupLevel"/></td> 
      	     <td align="center"><d:getcmnname typeName="SET_G_YESNOFLAG" match="oneline:systemFlag"/></td> 
      	     <td align="center">
      	      <a href="operator_group_privilege.screen?txtOpGroupID=<tbl:write name="oneline" property="opGroupID"/>
&txtOpGroupName=<tbl:write name="oneline" property="opGroupName"/>&txtSystemFlag=<tbl:write name="oneline" property="systemFlag"/>&txtOpGroupDesc=<tbl:write name="oneline" property="opGroupDesc"/>"/>ӵ�е�Ȩ��</a>
      	     </td>
      	     <td align="center">
      	      <a href="operator_group_member.screen?txtOpGroupID=<tbl:write name="oneline" property="opGroupID"/>
&txtOpGroupName=<tbl:write name="oneline" property="opGroupName"/>&txtSystemFlag=<tbl:write name="oneline" property="systemFlag"/>&txtOpGroupDesc=<tbl:write name="oneline" property="opGroupDesc"/>">���Ա</a>
      	     </td>
      	   <tr>
 </lgc:bloop>  
    <td colspan="8" class="list_foot"></td>
  </tr>
 
            <tr>
              <td align="right" class="t12" colspan="8" >
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
 
