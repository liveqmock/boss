<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="bookmark" prefix="bk" %>
<%@ taglib uri="resultset" prefix="rs" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="osstags" prefix="d" %>
<%@ page import="com.dtv.oss.dto.RoleOrganizationDTO" %>
<%@ page import="com.dtv.oss.util.Postern,java.util.*"%>
<%@ page import="com.dtv.oss.web.util.WebUtil" %>
 
<script language=javascript>


function query_submit(){
        document.frmPost.submit();
}


function modify_submit(txtID){
	self.location.href="role_organization_modify.do?txtId="+txtID;
}

function create_submit(){
	self.location.href="role_organization_create.do";
}

//-->
</script>
<TABLE border="0" cellspacing="0" cellpadding="0" width="820" >
<TR>
  <TD>
<table  border="0" align="center" cellpadding="0" cellspacing="10" >
  <tr>
    <td><img src="img/list_tit.gif" width="19" height="19"></td>
    <td class="title">��֯������ɫ��ѯ</td>
  </tr>
</table>
 
<table width="100%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>
<br>
    
<form name="frmPost" action="role_organization_query.do" method="post" > 
 <input type="hidden" name="txtFrom" value="1">
<input type="hidden" name="txtTo" value="10">   
   <table width="100%" align="center" border="0" cellspacing="1" cellpadding="3" class="list_bg">
    <tr>
    <td class="list_bg2" align="right">�ͻ���������</td>
    <td class="list_bg1">
    	<input type="hidden" name="txtDistrictId" value="<tbl:writeparam name="txtDistrictId" />">
	    <input type="text" name="txtDisDescrpition" size="25" maxlength="50" readonly value="<tbl:WriteDistrictInfo property="txtDistrictId" />" class="text">
	    <input name="selDistButton" type="button" class="button" value="ѡ��" onClick="javascript:sel_district('S,P,T','txtDistrictId','txtDisDescrpition')">
    </td>
    <td class="list_bg2" align="right">�ṩ�������֯</td>
       <td class="list_bg1">
    	<input type="hidden" name="txtServiceOrgId" value="<tbl:writeparam name="txtServiceOrgId" />">
	    <input type="text" name="txtOrgDesc" size="25" maxlength="50" readonly value="<tbl:WriteOrganizationInfo property="txtServiceOrgId" />" class="text">
	    <input name="selOrgButton" type="button" class="button" value="ѡ��" onClick="javascript:sel_organization('C,B,D,O,S,P','txtServiceOrgId','txtOrgDesc')">
    </td>	 	    
      	
    </tr>
     <tr>   
       <td class="list_bg2"><div align="right">�Ƿ����ȴ���</div></td>
       <td class="list_bg1"align="left"><d:selcmn name="txtIsFirst" mapName="SET_G_YESNOFLAG" match="txtIsFirst" width="23" /></td>
        <td class="list_bg2"><div align="right">��������</div></td>
      <td class="list_bg1"  align="left">
      <d:selcmn name="txtOrgRole" mapName="SET_S_ROLEORGNIZATIONORGROLE" match="txtOrgRole" width="23" />
      </td>    	
     
		 
    </tr>
       

    <tr>  
      <td class="list_bg2" colspan="4" align="center">
          <table  border="0" cellspacing="0" cellpadding="0">
			  <tr>
			      <td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
				<td><input name="Submit" type="button" class="button" value="�� ѯ" onclick="javascript:query_submit()"></td>
				<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
				<td width="20" ></td>
				 
				<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
				<td><input name="Submit" type="button" class="button" value="�� ��" onclick="javascript:create_submit()"></td>
				<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
			  </tr>
	      </table>
     </tr>
   </table>
   <br>
  <table width="100%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
    <tr>
      <td><img src="img/mao.gif" width="1" height="1"></td>
    </tr>
  </table>  
 <br>
 
<rs:hasresultset>

   <table width="100%" border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg" >     
      <tr class="list_head"> 
          <td width="10%" class="list_head"><div align="center">��ˮ��</div></td>
          <td width="20%" class="list_head"><div align="center">�ͻ���������</div></td>                    
          <td width="17%" class="list_head"><div align="center">��������</div></td> 
          <td width="17%" class="list_head"><div align="center">����ҵ���ʻ��Ĳ�Ʒ</div></td>         
          <td width="20%" class="list_head"><div align="center">�ṩ�������֯</div></td>
          <td width="15%" class="list_head"><div align="center">�Ƿ����ȴ���</div></td>
      </tr> 
     
<lgc:bloop requestObjectName="ResponseQueryResult" item="oneline" >
    <tbl:printcsstr style1="list_bg1" style2="list_bg2" overStyle="list_over" >
    <%
    	RoleOrganizationDTO roleOrganizationDTO=(RoleOrganizationDTO)pageContext.getAttribute("oneline");
    	String prodName=Postern.getProductNameByFlag(roleOrganizationDTO.getSaProductId());
    	if(prodName==null||prodName.endsWith("null"))
    		prodName="";
    	 
    	 
    	
    %>
    	 <td align="center" ><a href="javascript:modify_submit('<tbl:write name="oneline" property="id"/>')" class="link12" ><tbl:write name="oneline" property="id"/></a></td>
    	  <td align="center" ><tbl:WriteDistrictInfo name="oneline" property="districtId" /></td>
       
      	 <td align="center" ><d:getcmnname typeName="SET_S_ROLEORGNIZATIONORGROLE" match="oneline:orgRole" /></td>      		
      	 <td align="center" ><%=prodName%></td>  
      	 <td align="center" ><tbl:WriteOrganizationInfo name="oneline" property="serviceOrgId" /></td>   	 
      	 
      	 <td align="center" ><d:getcmnname typeName="SET_G_YESNOFLAG" match="oneline:isFirst" /></td>
    </tbl:printcsstr>
</lgc:bloop> 
  <tr>
    <td colspan="6" class="list_foot"></td>
  </tr>
  </table>

<table  border="0" align="right" cellpadding="0" cellspacing="8">
  <tr>
    <td>��<span class="en_8pt"><rs:prop property="curpageno" /></span>ҳ<span class="en_8pt">/</span>��<span class="en_8pt"><rs:prop property="pageamount" /><span>ҳ</td>
    <td>��<span class="en_8pt"><rs:prop property="recordcount" /></span>����¼</td>
<rs:notfirst>
    <td align="right"><img src="img/dot_top.gif" width="17" height="11"></td>
    <td><a href="javascript:firstPage_onClick(document.frmPost, <rs:prop property="firstto" />)" class="link12">��ҳ</a></td>
    <td align="right"><img src="img/dot_pre.gif" width="6" height="11"></td>
    <td><a href="javascript:previous_onClick(document.frmPost, <rs:prop property="prefrom" />, <rs:prop property="preto" />)" class="link12">��һҳ</a></td>
</rs:notfirst>
<rs:notlast>
    <td align="right"><img src="img/dot_next.gif" width="6" height="11"></td>
    <td><a href="javascript:next_onClick(document.frmPost, <rs:prop property="nextfrom" />, <rs:prop property="nextto" />)" class="link12">��һҳ</a></td>
    <td align="right"><img src="img/dot_end.gif" width="17" height="11"></td>
    <td><a href="javascript:lastPage_onClick(document.frmPost, <rs:prop property="lastfrom" />, <rs:prop property="lastto" />)" class="link12">ĩҳ</a></td>
</rs:notlast>
    <td align="right">��ת��</td>
    <td><input name="txtPage" type="text" class="page_txt"></td>
    <td>ҳ</td>
    <td><input name="imageField" type="image" src="img/button_go.gif" width="28" height="15" border="0" onclick="return goto_onClick(document.frmPost, <rs:prop property="pageamount" />)"/></td>
  </tr>
</table>
 
 </rs:hasresultset>
  
  
    

       
</form> 
 
 

     
      
      
      
      
      