<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl"%>
<%@ taglib uri="logic" prefix="lgc"%>
<%@ taglib uri="resultset" prefix="rs"%>
<%@ taglib uri="/WEB-INF/oss.tld" prefix="d"%>

<%@ page import="com.dtv.oss.util.Postern,java.util.*"%>
<%@ page import="com.dtv.oss.dto.PalletDTO"%>

<SCRIPT language="JavaScript">
function check_form ()
{

   if (!checkPlainNum(document.frmPost.txtPalletID, true,8, "����ID"))
	    return false;
	return true;
}

function query_submit()
{
    if (check_form())
    {
    	
        document.frmPost.submit();
    }
}

function pallet_create()
{
	location.href = "pallet_create.screen";
}

function pallet_delete()
{
	if(check_chosen(document.frmPost,document.frmPost.ListID))
	{
		if(confirm("ȷ��Ҫɾ��ѡ��ļ�¼��?")){
			document.frmPost.action="pallet_delete.do";
			document.frmPost.submit();
		}
		return;
	}else{
		alert("ȱ��ѡ��,�޷���ɲ���!");
	}
}

function pallet_detail(strID)
{
	location.href = "pallet_detail_query.do?txtOPPalletID="+strID;
}
</SCRIPT>

<form name="frmPost" method="post" action="pallet_query.do"><input
	type="hidden" name="txtFrom" size="20" value="1"> <input type="hidden"
	name="txtTo" size="20" value="10">

	<input type="hidden" name="txtActionType" size="20" value="DELETE">

<table border="0" align="center" cellpadding="0" cellspacing="10">
	<tr>
		<td><img src="img/list_tit.gif" width="19" height="19"></td>
		<td class="title">���ܲ�ѯ</td>
	</tr>
</table>
<table width="100%" border="0" align="center" cellpadding="0"
	cellspacing="0" background="img/line_01.gif">
	<tr>
		<td><img src="img/mao.gif" width="1" height="1"></td>
	</tr>
</table>
<br>
<table width="810" align="center" border="0" cellspacing="1"
	cellpadding="3">
	<tr>
		<td class="list_bg2" align="right" width="17%">����ID</td>
		<td class="list_bg1" width="33%"><input name="txtPalletID" type="text"
			class="text" maxlength="10" size="25"
			value="<tbl:writeparam name="txtPalletID" />"></td>
		<td class="list_bg2" align="right" width="17%">��������</td>
		<td class="list_bg1" width="33%"><input name="txtPalletName"
			type="text" class="text" maxlength="200" size="25"
			value="<tbl:writeparam name="txtPalletName" />"></td>
	</tr>
	<tr>
		<td class="list_bg2" align="right">״̬</td>
		<td class="list_bg1"><font size="2"> <d:selcmn name="txtPalletStatus"
			mapName="SET_G_GENERALSTATUS" match="txtPalletStatus" width="23" /> </font></td>
		<td class="list_bg2" align="right">�����ֿ�</td>
		<td class="list_bg1"><d:seldepotbyoper  name="txtOwner" match="txtOwner" width="23" /></td>
	</tr>
</table>
    <table width="100%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
	<tr align="center">
	     <td class="list_bg1">

	  <table align="center"  border="0" cellspacing="0" cellpadding="0">
         <tr>  
            <td><img src="img/button2_r.gif" width="22" height="20"></td>
			<td><input name="Submit" type="button" class="button"
					value="��&nbsp;ѯ" onclick="javascript:query_submit()"></td>
            <td><img src="img/button2_l.gif" width="11" height="20"></td>
            <td width="20" ></td>  

            <td><img src="img/button_l.gif" width="11" height="20"></td>
            <td><input name="Submit" type="button" class="button"
					value="��&nbsp;��" onclick="javascript:pallet_create()"></td>
            <td><img src="img/button_r.gif" width="22" height="20"></td>          
        </tr>
      </table>	
	   </td>
	</tr>
    </table>    

<rs:hasresultset>
	<table width="100%" border="0" align="center" cellpadding="5"
		cellspacing="1" class="list_bg">
		<tr class="list_head">
			<!-- <td class="list_head"><input type="checkbox" name="selall" onclick="javascript:select_all_by_name(document.frmPost,'ListID', this.checked)"></td> -->
			<td class="list_head">����ID</td>
			<td class="list_head">��������</td>
			
			<td class="list_head">���洢�豸��</td>
			<td class="list_head">״̬</td>
			<td class="list_head">�����ֿ�</td>
			<td class="list_head">��������</td>
		</tr>
		<lgc:bloop requestObjectName="ResponseQueryResult" item="oneline">
			<%PalletDTO dto = (PalletDTO) pageContext
							.getAttribute("oneline");
					String strDepotName=Postern.getDepotNameByID(dto.getDepotID());
					%>
			<tbl:printcsstr style1="list_bg1" style2="list_bg2"
				overStyle="list_over">
				<!-- <td align="center"><input type="checkbox" name="ListID" value="<tbl:write name="oneline" property="palletID"/>"></td> -->
				<td align="center"><a
					href="javascript:pallet_detail('<tbl:write name="oneline" property="palletID"/>')"
					class="link12"><tbl:writenumber name="oneline" property="palletID" digit="8"/></a></td>
				<td><tbl:write name="oneline" property="palletName" /></td>
				
				<td align="center"><tbl:write name="oneline" property="maxNumberAllowed"/></td>
				<td align="center"><d:getcmnname typeName="SET_G_GENERALSTATUS"
					match="oneline:status" /></td>
				<td align="center"><%=strDepotName==null?"":strDepotName%></td>
				<td><tbl:write name="oneline" property="palletDesc" /></td>
			</tbl:printcsstr>
		</lgc:bloop>
		<tr>
    					<td colspan="6" class="list_foot"></td>
  					</tr>
				 
            		<tr>
              			<td align="right" class="t12" colspan="6" >
                 			��<span class="en_8pt"><rs:prop property="curpageno" /></span>ҳ
                 			<span class="en_8pt">/</span>��<span class="en_8pt"><rs:prop property="pageamount" />ҳ
                 			����<span class="en_8pt"><rs:prop property="recordcount" /></span>����¼&nbsp;&nbsp;&nbsp;&nbsp;             
                 		<rs:notfirst>
                   			<img src="img/dot_top.gif" width="17" height="11">
                   			<a href="javascript:firstPage_onClick(document.frmPost, <rs:prop property="firstto" />)" >��ҳ </a>
                   			<img src="img/dot_pre.gif" width="6" height="11">
                   			<a href="javascript:previous_onClick(document.frmPost, <rs:prop property="prefrom" />, <rs:prop property="preto" />)" >��һҳ </a>
                 		</rs:notfirst>          
                		<rs:notlast>
                 				&nbsp;
                 			<img src="img/dot_next.gif" width="6" height="11">
                 			<a href="javascript:next_onClick(document.frmPost, <rs:prop property="nextfrom" />, <rs:prop property="nextto" />)" >��һҳ </a>
                 			<img src="img/dot_end.gif" width="17" height="11">
                 			<a href="javascript:lastPage_onClick(document.frmPost, <rs:prop property="lastfrom" />, <rs:prop property="lastto" />)" >ĩҳ </a>
                		</rs:notlast>
                			&nbsp;
                			ת��
               				<input type="text" name="txtPage" class="page_txt">ҳ 
               				<a href="javascript:return goto_onClick(document.frmPost, <rs:prop property="pageamount" />)" >
                 			<input name="imageField" type="image" src="img/button_go.gif" width="28" height="15" border="0">
               				</a>
             			</td>
         			</tr>
	</table>
<br>

<!-- 	  <table align="center"  border="0" cellspacing="0" cellpadding="0">
         <tr>  
            <td><img src="img/button_l.gif" width="11" height="20"></td>
            <td><input name="Submit" type="button" class="button"
					value="ɾ&nbsp;��" onclick="javascript:pallet_delete()"></td>
            <td><img src="img/button_r.gif" width="22" height="20"></td>          
        </tr>
      </table>	 -->
<br>

</rs:hasresultset> <br>
</form>
