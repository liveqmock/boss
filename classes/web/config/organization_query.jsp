<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="resultset" prefix="rs" %>
<%@ taglib uri="/WEB-INF/oss.tld" prefix="d" %>

<%@ page import="com.dtv.oss.util.Postern,
                 java.util.HashMap,
                 java.util.Map,
                 java.util.Iterator" %>
<%@ page import="com.dtv.oss.web.util.WebOperationUtil" %>
<SCRIPT language=javascript>
<!--

function delete_submit() {
	if (checkSelected()) {
		if (window.confirm('��ȷ��Ҫ����ɾ����?')){
		document.frmPost.action = "organization_op.do";
	        document.frmPost.Action.value="delete";
			document.frmPost.submit();
		}
	}
}

function checkSelected(){

	var returnValue=false;

	if(document.frmPost.txtIndex!=null){
		if (document.frmPost.txtIndex.length!=null)
		{
			for (var i=0;i<document.frmPost.txtIndex.length;i++)
			{
				if (document.frmPost.txtIndex[i].checked)
				{
					returnValue= true;
				}
			}
			
		}
		else
		{
			if (document.frmPost.txtIndex.checked)
				{
					returnValue= true;
				}
		
		}
	}
	if(!returnValue){
		alert("��ָ��ɾ������µ�����");
	}
    return returnValue;

}

function operator_submit(id){
	document.frmPost.action = "operator_query.do?txtOrgID="+id;
	document.frmPost.submit();
}
function add_submit(id){
	document.frmPost.action = "organization_create.screen";
	document.frmPost.submit();
}
function update_submit(id){
	document.frmPost.action = "organization_query_detail.do?txtDetailID="+id;
	document.frmPost.submit();
}
	
//-->
</SCRIPT>
<br>
<br>
<TABLE border="0" cellspacing="0" cellpadding="0" width="800" >
<TR>
	<TD>
<table  border="0" align="center" cellpadding="0" cellspacing="10" >
  <tr>
    <td><img src="img/list_tit.gif" width="19" height="19"></td>
    <td class="title">ֱ���¼���֯����</td>
  </tr>
</table>
<form name="frmPost" action="organization_query_result.do" method="post" >    
	  <input type="hidden" name="txtFrom" size="22" value="1">
      <input type="hidden" name="txtTo" size="22" value="10">
      <input type="hidden" name="Action" size="22" value="">
	  <input type="hidden" name="txtQryBelongTo" value="<tbl:writeparam name="txtQryBelongTo"/>">
<rs:hasresultset>
<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>
<br>
<table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
  <tr class="list_head">
<!--     <td class="list_head"><div align="center"><input type="checkbox" name="selall" onclick="javascript:select_all_by_name(document.frmPost,'txtIndex', this.checked)"></div></td> -->
    <td class="list_head">��֯ID</td>
    <td class="list_head">��֯����</td>
    <td class="list_head">��֯����</td>
    <td class="list_head">��֯������</td>
    <td class="list_head">�Ƿ�λ�ͻ�</td>
    <td class="list_head">״̬</td>
    <td class="list_head">��������</td>
    <td class="list_head">����Ա</td>
  </tr>
<lgc:bloop requestObjectName="ResponseQueryResult" item="oneline" >
	<tbl:printcsstr style1="list_bg1" style2="list_bg2" overStyle="list_over" >
		<!-- <td><div align="center">
		<input type="checkbox" name="txtIndex" value="<tbl:write name="oneline" property="orgID"/>">
		</div></td> -->
		<td align="center" ><a href="javascript:update_submit('<tbl:write name="oneline" property="orgID"/>')"><tbl:writenumber name="oneline" property="orgID" digit="6"/></a></td>

		<td align="center" ><tbl:write name="oneline" property="orgName"/></td>
		<td align="center" ><d:getcmnname typeName="SET_S_ORGANIZATIONORGTYPE" match="oneline:orgType" /></td>
		
		<td align="center" ><d:getcmnname typeName="SET_S_ORGANIZATIONPARTNERCATEGORY" match="oneline:orgSubType" /></td>
		<td align="center" ><d:getcmnname typeName="SET_G_YESNOFLAG" match="oneline:hasCustomerFlag" /></td>
		<td align="center" ><d:getcmnname typeName="SET_G_GENERALSTATUS" match="oneline:status" /></td>
		<td align="center">
		 <a href="org_district_config.screen?txtOrgID=<tbl:write name="oneline" property="orgID"/>
&txtOrgName=<tbl:write name="oneline" property="orgName"/>&txtOrgDesc=<tbl:write name="oneline" property="orgDesc"/>"/>��������</a>
		 </td>
		<td align="center" ><a href="javascript:operator_submit('<tbl:write name="oneline" property="orgID"/>')">����Ա</a></td>
	</tbl:printcsstr>
</lgc:bloop>

 <tr>
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
<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>

<table align="center" border="0" cellspacing="0" cellpadding="0">
	<tr>
	 <td><img src="img/button_l.gif" width="11" height="20"></td>
            <td><input name="Submit" type="button" class="button"
					value="��&nbsp;��" onclick="javascript:add_submit()"></td>
            <td><img src="img/button_r.gif" width="22" height="20"></td>       
       
<!-- 		<td width="20" ></td>
		<td><img src="img/button_l.gif" border="0" ></td>
		<td background="img/button_bg.gif"><a  href="javascript:delete_submit()" class="btn12" >ɾ&nbsp;��</a></td>
		<td><img src="img/button_r.gif" border="0" ></td>
		<td width="20" ></td> -->
	</tr>
</table>  

</rs:hasresultset>                  
</TD>
</TR>
</TABLE>
</form> 



      
      
      
      