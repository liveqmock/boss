<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="resultset" prefix="rs" %>
<%@ taglib uri="/WEB-INF/oss.tld" prefix="d" %>
<%@ taglib uri="privilege" prefix="pri" %>
                 
<%@ page import="com.dtv.oss.util.Postern, java.util.*" %>
<%@ page import="com.dtv.oss.web.util.WebUtil" %>
<%@ page import="com.dtv.oss.web.util.CommonKeys" %>
<%@ page import="com.dtv.oss.web.util.WebOperationUtil" %>

<%@ page import="com.dtv.oss.dto.QueryDTO"%>
<%@ page import="com.dtv.oss.dto.QueryResultItemDTO"%>

<script language=javascript>
<!--

//����˵����typeΪ���ͣ�typeNameΪ���֣�����value���֣���subTypeValueΪ�ò�����ֵ
function open_select(type,typeName,typeValue,parentType,parentTypeName,parentTypeValue){
	var param="batch_query_select.screen?";
	param=param + "type="+type;
	param=param + "&typeName="+typeName;
	param=param + "&typeValue=" + document.frmPost.elements(typeName).value;
	param=param + "&parentType="+parentType;
	param=param + "&parentTypeName="+parentTypeName;
	
	if(parentTypeName!=null && parentTypeName!="")
		param=param + "&parentTypeValue="+document.frmPost.elements(parentTypeName).value;
		
	var windowFeatures="toolbar=no,location=no,status=no,menubar=no,scrollbars=yes,width=330,height=250,screenX=340,screenY=270";

	window.open(param,"",windowFeatures);
}

function AllChoose(){
     if (document.frmPost.allchoose.checked){
        if (document.frmPost.partchoose[0]){
            for (i=1 ;i< document.frmPost.partchoose.length ;i++){
              document.frmPost.partchoose[i].checked =true;
           }
       }
     }
      else {
        if (document.frmPost.partchoose[0]){
            for (i=1 ;i< document.frmPost.partchoose.length ;i++){
               document.frmPost.partchoose[i].checked =false;
            }
       }
     }   
  }
  
function query_submit(){
	document.frmPost.action="batch_query_show_result.do";
	document.frmPost.txtActionType.value="result";
	document.frmPost.submit();
}

function change_submit(){
	var result="";
	
	if(document.frmPost.partchoose[0]){
		for(i=1 ;i< document.frmPost.partchoose.length ;i++){
			if(document.frmPost.partchoose[i].checked){
				if(result!="")
					result=result + ";";
				result=result + document.frmPost.partchoose[i].value;
			}
		}
	}
	if(result==""){
		alert("û��ѡ����ص��");
		return;
	}
	else 
		document.frmPost.txtQueryResultItemNOs.value=result;
		
	document.frmPost.action="batch_query_result_change.do";
	document.frmPost.txtActionType.value="changeResult";
	document.frmPost.func_flag.value="8005";
	document.frmPost.submit();
}

function back_submit(url){
	if(url=="")
		return;
	document.frmPost.txtQueryName.value="";
	document.frmPost.action = url;
        document.frmPost.submit();
}

//-->
</script>

<%
	QueryDTO queryDTO=Postern.getQueryDTOByQueryID(WebUtil.StringToInt(request.getParameter("txtQueryID")));
	pageContext.setAttribute("QueryDTO",queryDTO);
	
%>
<form name="frmPost" method="post" action="batch_query_show_result.do">

<input type="hidden" name="txtFrom" size="20" value="1">
<input type="hidden" name="txtTo" size="20" value="10">
<input type="hidden" name="txtActionType" size="20" value="result">
<input type="hidden" name="partchoose" value="">
<input type="hidden" name="txtQueryResultItemNOs" value="">
<input type="hidden" name="func_flag" value="">

<table  border="0" align="center" cellpadding="0" cellspacing="10" >
  <tr>
    <td><img src="img/list_tit.gif" width="19" height="19"></td>
    <td class="title">������ѯִ�н����Ϣ</td>
  </tr>
</table>

<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>
<br>

<table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
  <tr>
    <td colspan="4" class="import_tit" align="center"><font size="2">������ѯ����������Ϣ</font></td>
  </tr>
      
  <tr>
    <td class="list_bg2"><div align="right">��ѯID</div></td>
    <td class="list_bg1"><input name="txtQueryID" type="text" size="25" value="<tbl:writeparam name="txtQueryID" />" class="textgray" readonly ></td>
    <td class="list_bg2"><div align="right">���������</div></td>
    <td class="list_bg1"><input name="txtQueryTypeValue" type="text" size="25" value="<d:getcmnname typeName="SET_B_QUERYTYPE" match="QueryDTO:queryType" />" class="textgray" readonly ></td>
  	<input name="txtQueryType" type="hidden"  value="<tbl:write name="QueryDTO" property="queryType"/>">
  </tr>
  
  <tr>
    <td class="list_bg2"><div align="right">��ѯ����</div></td>
    <td class="list_bg1"><input name="txtQueryName" type="text" size="25" value="<tbl:write name="QueryDTO" property="queryName"/>" class="textgray" readonly ></td>
    <td class="list_bg2"><div align="right">״̬</div></td>
    <td class="list_bg1"><input name="txtStatusValue" type="text" size="25" value="<d:getcmnname typeName="SET_B_QUERYSTATUS" match="QueryDTO:status" />" class="textgray" readonly ></td>
  </tr>
</table>
<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>

<table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
  <tr>
    <td colspan="4" class="import_tit" align="center"><font size="2">������Ĳ�ѯ����</font></td>
  </tr>
  
  <tr>
    <td class="list_bg2"><div align="right">״̬</div></td>
    <td class="list_bg1"><d:selcmn name="txtRusultItemStatus" mapName="SET_G_GENERALSTATUS"  match="txtRusultItemStatus"  width="23" /></td>
    <td class="list_bg2"><div align="right">�ͻ�֤��</div></td>
    <td class="list_bg1"><input name="txtCustomerID" type="text" class="text" maxlength="10" size="25" value="<tbl:writeparam name="txtCustomerID" />"></td>
  </tr>
  
  <tr>
    <td class="list_bg2"><div align="right">�ʽ��ʺ�</div></td>
    <td class="list_bg1"><input name="txtAccountID" type="text" class="text" maxlength="10" size="25" value="<tbl:writeparam name="txtAccountID" />"></td>
    <td class="list_bg2"><div align="right">ҵ���˺�</div></td>
    <td class="list_bg1"><input name="txtUserID" type="text" class="text" maxlength="10" size="25" value="<tbl:writeparam name="txtUserID" />"></td>
  </tr>
  
  <tr>
    <td class="list_bg2"><div align="right">��Ӫ�̲�Ʒ</div></td>
    <td class="list_bg1"><input name="txtCPProductIDListValue" type="text" class="text" maxlength="10" size="25" value="<tbl:writeparam name="txtCPProductIDListValue" />">
    	<input name="txtCPProductIDList" type="hidden"  value="<tbl:writeparam name="txtCPProductIDList" />">
    	<input name="checkbutton" type="button" class="button" value="��ѡ��" onClick="javascript:open_select('PRODUCT','txtCPProductIDList',document.frmPost.txtCPProductIDList.value,'','','')"> 
    </td>
    <td class="list_bg2"><div align="right">��Ӫ�̲�Ʒ��</div></td>
    <td class="list_bg1"><input name="txtCPProductPackageIDListValue" type="text" class="text" maxlength="10" size="25" value="<tbl:writeparam name="txtCPProductPackageIDListValue" />">
    	<input name="txtCPProductPackageIDList" type="hidden"  value="<tbl:writeparam name="txtCPProductPackageIDList" />">
    	<input name="checkbutton" type="button" class="button" value="��ѡ��" onClick="javascript:open_select('PRODUCTPACKAGE','txtCPProductPackageIDList',document.frmPost.txtCPProductPackageIDList.value,'','','')"> 
    </td>
  </tr>
  
  <tr>
    <td class="list_bg2"><div align="right">�����</div></td>
    <td class="list_bg1"><input name="txtCPCampaignIDListValue" type="text" class="text" maxlength="10" size="25" value="<tbl:writeparam name="txtCPCampaignIDListValue" />">
    	<input name="txtCPCampaignIDList" type="hidden"  value="<tbl:writeparam name="txtCPCampaignIDList" />">
    	<input name="checkbutton" type="button" class="button" value="��ѡ��" onClick="javascript:open_select('CAMPAIGN','txtCPCampaignIDList',document.frmPost.txtCPCampaignIDList.value,'','','')"> 
    </td>
    <td class="list_bg2"></td>
    <td class="list_bg1"></td>
  </tr>
 
</table>

<table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
	<tr align="center">
	  <td class="list_bg1">
	  	<table  border="0" cellspacing="0" cellpadding="0">
		  <tr>
			<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
			<td><input name="Submit" type="button" class="button" value="��&nbsp;ѯ" onclick="javascript:query_submit()"></td>
			<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
		<pri:authorized name="batch_query_show_result.export">
			<td width="20" ></td>
			<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
			<td background="img/button_bg.gif"  height="20" >
			<a href="javascript:download(document.frmPost,'����������ѯִ�н��')" class="btn12">������ѯ���</a></td>
			<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>			
		</pri:authorized>

		  </tr>
	  </table>
	  </td>
	</tr>
</table> 

<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>


<rs:hasresultset>

<table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
  <tr class="list_head">
    <td class="list_head"><div align="center"><input type="checkbox" name="allchoose" value="" onclick="AllChoose()"></div></td>
    <td class="list_head">��¼ID</td>
    <td class="list_head">�ͻ�֤��</td>
    <td class="list_head">�ʽ��ʺ�</td>
    <td class="list_head">ҵ���˺�</td>
    <td class="list_head">��Ӫ�̲�Ʒ</td>
    <td class="list_head">��Ӫ�̲�Ʒ��</td>
    <td class="list_head">PSID</td>
    <td class="list_head">���������</td>
    <td class="list_head">״̬</td>
  </tr>

<%
	String strProdName="";
    	String strPackName="";
    	String strCapName="";
%>
   
<lgc:bloop requestObjectName="ResponseQueryResult" item="oneline" >
<%
    QueryResultItemDTO dto = (QueryResultItemDTO)pageContext.getAttribute("oneline");
    
    pageContext.setAttribute("QueryResultItemDTO",dto);

    strPackName = Postern.getPackagenameByID(dto.getPackageId());
    strProdName = Postern.getProductNameByPSID(dto.getPsId());
    strCapName = Postern.getCampaignNameByCustCampaignID(dto.getCcId());
    
    System.out.println("aaaaa ====" + dto.getCcId());
    
    if (strProdName==null)
	strProdName="";
    if (strPackName==null)
	strPackName="";
    if(strCapName==null)
    	strCapName="";
    
%>
<tbl:printcsstr style1="list_bg1" style2="list_bg2" overStyle="list_over" >

    <td align="center" class="t12"><input type="checkbox" name="partchoose" value="<tbl:write name="QueryResultItemDTO" property="itemNO"/>"></td>  
    <td><tbl:write name="QueryResultItemDTO" property="itemNO"/></td>
    <td><tbl:write name="QueryResultItemDTO" property="customerId"/></td>
    <td><tbl:write name="QueryResultItemDTO" property="accountId"/></td> 
    <td><tbl:write name="QueryResultItemDTO" property="userId"/></td>
    <td><%=strProdName %></td>
    <td><%=strPackName %></td>
    <td><tbl:write name="QueryResultItemDTO" property="psId"/></td>
    <td><%=strCapName %></td>
    <td><d:getcmnname typeName="SET_G_GENERALSTATUS" match="QueryResultItemDTO:status" /></td>    
</tbl:printcsstr>

</lgc:bloop>

  <tr>
    <td colspan="10" class="list_foot"></td>
  </tr>

</table>
<table  border="0" align="center" cellpadding="0" cellspacing="8">
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
    <td><input name="imageField" type="image" src="img/button_go.gif" width="28" height="15" border="0" onclick="javascript:goto_onClick(document.frmPost, <rs:prop property="pageamount" />)"></td>
  </tr>
  </table>
  
  <table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
    <tr>
      <td><img src="img/mao.gif" width="1" height="1"></td>
    </tr>
  </table>
  
  <BR>
<table border="0" cellspacing="0" cellpadding="0">
  <tr>
    <%
    	//����ͷ��¼��Ϊ�����õ�״̬ʱ�������޸Ľ����
  	if(!CommonKeys.QUERY_STATUS_REFERED.equals(queryDTO.getStatus())){
    %>
    <td width="20" ></td>		
    <td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
    <td><input name="Submit" type="button" class="button" value="�޸Ľ������¼״̬" onclick="javascript:change_submit()"></td>
    <td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
    
    <%
    	}
    %>
    
    <!--
    <td width="20" ></td>		
    <td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
    <td><input name="Submit" type="button" class="button" value="����" onclick="javascript:back_submit('goto_batch_query_show_result.do')"></td>
    <td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
    -->
    
    <td width="20" ></td>		
    <td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
    <td><input name="Submit" type="button" class="button" value="���ص�������ѯά��" onclick="javascript:back_submit('menu_batch_query_query.do')"></td>
    <td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
  </tr>
</table>

 </rs:hasresultset>                 


</form>  
         

      