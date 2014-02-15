<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="resultset" prefix="rs" %>
<%@ taglib uri="/WEB-INF/oss.tld" prefix="d" %>

<script language=javascript>
function frm_submit(){
	document.frmPost.txtActionType.value="confirm";
	document.frmPost.action="construction_batch_import_confirm.do";
  document.frmPost.submit();
}
function formToGetUrl(formName){
//	var inputs=document.getElementsByName(formName)[0].getElementsByTagName("input");
	var inputs=document.getElementsByName(formName)[0].elements;
	var getString='';
	for(i=0;i<inputs.length;i++){	
		var vi=inputs[i];
		getString+=vi.name+'='+vi.value+'&';
 	}
 	return getString.substring(0,getString.length-1);
}
function back_submit(){
	url="construction_batch_import_ipnut.screen?"+formToGetUrl('frmPost');
	document.location.href=url;
} 	
</script>
<table  border="0" align="center" cellpadding="0" cellspacing="10" >
  <tr>
    <td><img src="img/list_tit.gif" width="19" height="19"></td>
    <td class="title">�ֹ�¼�������ն˽�����Ϣ-�����ȷ��</td>
  </tr>
</table>
<table width="100%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>
<br>
<form name="frmPost" action="construction_batch_import_query.do" method="post"> 
<input type="hidden" name="txtFrom" value="1">
<input type="hidden" name="txtTo" value="10">
<input type="hidden" name="txtActionType" value="query">
<input type="hidden" name="txtAction" value="construction_batch_import">
<input type="hidden" name="txtHiddenValues" value="txtFileName/txtSheetNo/txtConstructionName/txtDistrict/txtDistrictDesc/selDistButton/txtBuilderName/txtCatvTerminalType/txtCatvTerminalStatus/txtCableType/txtBiDirectionFlag/txtPostCode/txtTatolHouseNumber/txtAddressPrefix/txtFiberNode/txtFileName/txtDesc">

<input type="hidden" name="txtFileName" value="<%=request.getAttribute("constructionBatchFile") %>">
<tbl:generatetoken />
	
<table width="100%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
  <tr>
    <td class="list_bg2" width=17% align="right">���罨�赥��</td>
    <td class="list_bg1" width=33% align="left"><input type="text" name="txtSheetNo" size="25" value="<tbl:writeparam name="txtSheetNo"/>" maxlength="50" readonly class="textgray"></td>
    <td class="list_bg2" width=17% align="right">С������</td>
    <td class="list_bg1" width=33% align="left"><input type="text" name="txtConstructionName" size="25" value="<tbl:writeparam name="txtConstructionName"/>" maxlength="50" readonly class="textgray"></td>
  </tr>
  <tr>
    <td class="list_bg2"  align="right">��������</td>
    <td class="list_bg1"  align="left" >
     	<input type="hidden" name="txtDistrict" value="<tbl:writeparam name="txtDistrict" />">
			<input type="text" name="txtDistrictDesc" size="25" maxlength="50" readonly class="textgray"
							value="<tbl:WriteDistrictInfo property="txtDistrict" />" class="text">
    </td>
    <td class="list_bg2"  align="right">���̸���λ</td>
    <td class="list_bg1"  align="left"><input type="text" name="txtBuilderName" size="25" value="<tbl:writeparam name="txtBuilderName"/>" maxlength="50" readonly class="textgray">
	  </td>
	</tr>
  <tr>
    <td class="list_bg2"  align="right">�ն�����</td>
    <td class="list_bg1"  align="left">
     	<input type="hidden" name="txtCatvTerminalType" value="<tbl:writeparam name="txtCatvTerminalType" />">
     	<input type="text" name="txtCatvTerminalTypeDesc" size="25" value="<d:getcmnname typeName="SET_A_CATVTERMTYPE" match="txtCatvTerminalType" />" readonly class="textgray">
	  </td>
    <td class="list_bg2"  align="right">�ն�״̬</td>
    <td class="list_bg1"  align="left">
     	<input type="hidden" name="txtCatvTerminalStatus" value="<tbl:writeparam name="txtCatvTerminalStatus" />">
     	<input type="text" name="txtCatvTerminalStatusDesc" size="25" value="<d:getcmnname typeName="SET_A_CATVTERMINALSTATUS" match="txtCatvTerminalStatus" />" readonly class="textgray">
	  </td>
	</tr>
  <tr>
    <td class="list_bg2" align="right">��������</td>
    <td class="list_bg1" align="left">  
     	<input type="hidden" name="txtCableType" value="<tbl:writeparam name="txtCableType" />">
     	<input type="text" name="txtCableTypeDesc" size="25" value="<d:getcmnname typeName="SET_A_CABLETYPE" match="txtCableType" />" readonly class="textgray">
    </td>
    <td class="list_bg2" align="right">�Ƿ�˫����*</td>
    <td class="list_bg1" align="left">
     	<input type="hidden" name="txtBiDirectionFlag" value="<tbl:writeparam name="txtBiDirectionFlag" />">
     	<input type="text" name="txtBiDirectionFlagDesc" size="25" value="<d:getcmnname typeName="SET_G_YESNOFLAG" match="txtBiDirectionFlag" />" readonly class="textgray">
    </td>
	</tr>	
  <tr>
    <td class="list_bg2" align="right">�ʱ�</td>
    <td class="list_bg1" align="left"> 
     	<input type="text" name="txtPostCode" size="25" value="<tbl:writeparam name="txtPostCode"/>" maxlength="6" readonly class="textgray">
    </td>
    <td class="list_bg2" align="right">С���ܻ���</td>
    <td class="list_bg1" align="left">
     	<input type="text" name="txtTatolHouseNumber" size="25" value="<tbl:writeparam name="txtTatolHouseNumber"/>" maxlength="10" readonly class="textgray">
    </td>
	</tr>
  <tr>
    <td class="list_bg2" align="right">��ַ¼��ǰ�</td>
    <td class="list_bg1" align="left"> 
     	<input type="text" name="txtAddressPrefix" size="25" value="<tbl:writeparam name="txtAddressPrefix"/>" maxlength="200" readonly class="textgray">
    </td>
    <td class="list_bg2" align="right">������ڵ�</td>
    <td class="list_bg1" align="left"> 
     	<input type="text" name="txtFiberNode" size="25" value="<tbl:writeparam name="txtFiberNode"/>" maxlength="50" readonly class="textgray">
    	<input name="txtFiberNodeID" type="hidden" size="25" value="<tbl:writeparam name="txtFiberNodeID"/>"  >
    </td>
	</tr>
  <tr>
    <td class="list_bg2" align="right">��ע</td>
    <td class="list_bg1" align="left" colspan="3">
    	<input type="text" name="txtDesc" size="83" value="<tbl:writeparam name="txtDesc"/>" maxlength="200" readonly class="textgray" class="textgray" >
    </td>
	</tr>
</table>

<table width="100%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
        <tr class="list_head">
          <td class="list_head">���</td>
          <td class="list_head">��ϸ��ַ</td>
          <td class="list_head">�˿���</td>
          <td class="list_head">��ע</td>
        </tr>
	<lgc:bloop requestObjectName="ResponseQueryResult" item="oneline" >
		<tbl:printcsstr style1="list_bg1" style2="list_bg2" overStyle="list_over" >
			   <td align="center"><tbl:write name="oneline" property="ItemNo"/></td>
			   <td align="center"><tbl:write name="oneline" property="DetailAddress"/></td>
			   <td align="center"><tbl:write name="oneline" property="PortNumber"/></td>     
			   <td align="center"><tbl:write name="oneline" property="Comments"/></td>     
		</tbl:printcsstr> 		
	</lgc:bloop>
  <tr>
    <td colspan="4" class="list_foot"></td>
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
<br>
<br>
<table width="100%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
  <tr>
    <td align="center">
		<table align="center" border="0" cellspacing="0" cellpadding="0" height="20">
			<tr>
				<td>
					<tr height="20">
						<td width="11" height="20"><img src="img/button2_r.gif" width="22" height="20"></td>
						<td height="20"><input name="next" type="button" class="button" onClick="javascript:back_submit()" value="��һ��"></td>
						<td width="22" height="20"><img src="img/button2_l.gif" width="11" height="20"></td>
						<td width="20"></td>
						<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
						<td height="20"><input name="next" type="button" class="button" onClick="javascript:frm_submit()" value="ȷ&nbsp��"></td>
						<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
					</tr>
				</td>
			</tr>	
		</table>  
		</td>
	</tr>
</table>  

</form> 
