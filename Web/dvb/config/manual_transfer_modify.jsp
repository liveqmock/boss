<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl"%>
<%@ taglib uri="logic" prefix="lgc"%>
<%@ taglib uri="resultset" prefix="rs"%>
<%@ taglib uri="/WEB-INF/oss.tld" prefix="d"%>
<%@ taglib uri="bookmark" prefix="bk" %>

<%@ page import="com.dtv.oss.util.Postern,java.util.*"%>
<%@ page import="com.dtv.oss.dto.DepotDTO"%>
 
<%@ taglib uri="privilege" prefix="pri" %>
 

<SCRIPT language="JavaScript">
function check_form ()
{
    if (check_Blank(document.frmPost.txtSheetType, true, "��������"))
	    return false;
    if (check_Blank(document.frmPost.txtFromOrgId, true, "��ת��Դ��֯"))
	    return false;
    if (check_Blank(document.frmPost.txtToOrgId, true, "��תĿ����֯"))
	    return false;
    if (!check_Num(document.frmPost.txtPriority, true, "����˳��"))
	    return false;
	return true;
}

function manual_modify()
{
    if (check_form())
    {
    	
        document.frmPost.submit();
    }
}
function ChangeSubType(){    
     if(document.frmPost.txtSheetType.value=="M")
      
       document.frmPost.txtSubType.disabled=false;
        
        else {
        document.frmPost.txtSubType.disabled=true;
        document.frmPost.txtSubType.value="";
     }
}
function back_submit(){
	url="<bk:backurl property='manual_transfer_query.do' />";
	if(url=="")
		url="manual_transfer_query.screen";
    document.location.href= url;

}
function delete_submit(txtID){
	if(confirm("ȷ��Ҫɾ�����ֹ���ת������")){
		self.location.href="manual_transfer_delete.do?txtSeqNo="+txtID+"&txtActionType=DELETE&func_flag=1023";
		 
	}
}

</SCRIPT>

<form name="frmPost" method="post" action="manual_transfer_modify.do">
<table border="0" align="center" cellpadding="0" cellspacing="10">
	<tr>
		<td><img src="img/list_tit.gif" width="19" height="19"></td>
		<td class="title">�ֹ���ת����-�޸�</td>
	</tr>
</table>
<table width="100%" border="0" align="center" cellpadding="0"
	cellspacing="0" background="img/line_01.gif">
	<tr>
		<td><img src="img/mao.gif" width="1" height="1"></td>
	</tr>
</table>
<br>

<lgc:bloop requestObjectName="ResponseQueryResult" item="oneline" isOne="true" > 
	<input type="hidden" name="txtFrom" size="20" value="1">
	<input type="hidden" name="txtTo" size="20" value="10">
	<!-- ���嵱ǰ���� -->
	<input type="hidden" name="txtActionType" size="20" value="UPDATE">
	<input type="hidden" name="func_flag" size="20" value="1023">
	<input type="hidden" name="txtDtLastmod" value="<tbl:write name="oneline" property="dtLastmod" />"  >

<table width="810" align="center" border="0" cellspacing="1"
	cellpadding="3">
	<tr>
	       <td class="list_bg2"><div align="right">��ˮ��</div></td>
                <td class="list_bg1"  align="left">
              <input type="text" name="txtSeqNo" size="25"  readonly class="textgray" value="<tbl:write name="oneline" property="SeqNo" />" >
              </td>    	
              <td class="list_bg2" align="right">��ת��Դ��֯*</td>
              <td class="list_bg1">
                <input type="hidden" name="txtFromOrgId" value="<tbl:write name="oneline" property="fromOrgId" />">
    	       
	      <input type="text" name="txtFromOrgDesc" size="23" maxlength="50" readonly value="<tbl:WriteOrganizationInfo name="oneline" property="fromOrgId" />" >
	      <input name="selOrgButton" type="button" class="button" value="ѡ��" onClick="javascript:sel_organization('C,B,D,P,O','txtFromOrgId','txtFromOrgDesc')">
	      </td>
	</tr>
	 
	<tr>
		 <td class="list_bg2"><div align="right">��������*</div></td>
                <td class="list_bg1"  align="left">
                <d:selcmn name="txtSheetType" mapName="SET_S_ROLEORGNIZATIONORGROLE" match="oneline:sheetType" width="23" onchange="ChangeSubType()"/>
              </td>    	
              <td class="list_bg2" align="right">��תĿ����֯*</td>
		<td class="list_bg1"><font size="2">  
		<input type="hidden" name="txtToOrgId" value="<tbl:write name="oneline" property="toOrgId" />">
	      <input type="text" name="txtToOrgDesc" size="23" maxlength="50" readonly value="<tbl:WriteOrganizationInfo name="oneline" property="toOrgId" />" >
	      <input name="selOrgButton" type="button" class="button" value="ѡ��" onClick="javascript:sel_organization('C,B,D,P,O','txtToOrgId','txtToOrgDesc')">
	      </td> 
	</tr>
	<tr>
	       <td class="list_bg2"><div align="right">����������</div></td>
               <td class="list_bg1"  align="left">
               <d:selcmn name="txtSubType" mapName="SET_W_JOBCARDSUBTYPE" match="oneline:orgSubRole" width="23"  />
             </td>    	
	       <td class="list_bg2" align="right">����˳��</td>
		<td class="list_bg1"><input name="txtPriority"
			type="text"   maxlength="10" size="23"
			value="<tbl:write name="oneline" property="Priority" />" ></td>
		 
            
	</tr>
	 
</table>
</lgc:bloop>  

	<table width="100%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
	<tr align="center">
	     <td class="list_bg1">

	  <table align="center"  border="0" cellspacing="0" cellpadding="0">
         <tr>  
            <td><img src="img/button2_r.gif" width="22" height="20"></td>
			<td><input name="Submit" type="button" class="button"
					value="��  ��" onclick="javascript:back_submit()"></td>
            <td><img src="img/button2_l.gif" width="11" height="20"></td>

            <td width="20" ></td>  
           <pri:authorized name="manual_transfer_modify.do" > 
            <td><img src="img/button_l.gif" width="11" height="20"></td>
            <td><input name="Submit" type="button" class="button"
					value="��  ��" onclick="javascript:manual_modify()"></td>
            <td><img src="img/button_r.gif" width="22" height="20"></td>   
             <td width="20" ></td>    
            </pri:authorized>    
             <td><img src="img/button_l.gif" width="11" height="20"></td>
            <td><input name="Submit" type="button" class="button"
					value="ɾ  ��" onclick="javascript:delete_submit('<tbl:write name="oneline" property="seqNo" />')"></td>
            <td><img src="img/button_r.gif" width="22" height="20"></td>   
        </tr>
      </table>	
	   </td>
	</tr>
    </table>    

</form>
