<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="osstags" prefix="d" %>
<%@ taglib uri="bookmark" prefix="bk" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="resultset" prefix="rs" %>
<%@ taglib uri="privilege" prefix="pri" %>

<%@ page import="java.util.ArrayList,
                 java.util.Iterator,
                 java.util.HashMap" %>
<%@ page import="com.dtv.oss.web.util.WebUtil" %>
<%@ page import="com.dtv.oss.web.util.CommonKeys" %>
<%@ page import="com.dtv.oss.web.util.WebOperationUtil" %>
<%@ page import="com.dtv.oss.dto.CustomerBillingRuleDTO" %>
<%@ page import="java.util.LinkedList" %>
<%@ page import="com.dtv.oss.dto.*" %>
<%@ page import="java.sql.Timestamp" %>
<%@ page import="com.dtv.oss.dto.CustomerProductDTO" %> 


<Script language=JavaScript>
<!--

function create_submit()
{
	if (check_Blank(document.frmPost.txtDistrictIDDesc, true, "��������"))
		return false;

	if (check_Blank(document.frmPost.txtPortNo, true, "�˿���"))
		return false;
	if (!checkPlainNum(document.frmPost.txtPortNo,false,64,"�˿���"))
		return false;

	if (check_Blank(document.frmPost.txtPostCode, true, "�ʱ�"))
		return false;
	if (!checkPlainNum(document.frmPost.txtPostCode,false,6,"�ʱ�"))
		return false;

	if (check_Blank(document.frmPost.txtStatus, true, "�ն�״̬"))
		return false;

	if (check_Blank(document.frmPost.txtDetailedAddress, true, "��ϸ��ַ"))
		return false;	

	//if (check_Blank(document.frmPost.txtCableType, true, "��������"))
	//	return false;

	//if (check_Blank(document.frmPost.txtBiDirectionFlag, true, "˫����������"))
	//	return false;
	
	//if (check_Blank(document.frmPost.txtFiberNode, true, "������ڵ�"))
	//	return false;
	
	if (check_Blank(document.frmPost.txtRecordSource, true, "�ն���Դ"))
		return false;

    document.frmPost.submit();
}


function query_fiber_node(){
	strFeatures = "dialogWidth=530px;dialogHeight=450px;center=yes;help=no";
	var strUrl="fiber_node_query.do?txtFrom=1&txtTo=10";
	var result=showModalDialog(strUrl,window,strFeatures);
	if (result!=null){
		var index = result.indexOf(";");
		if(index != -1){
		var resultID = result.substring(0,result.indexOf(";"));
		var resultDesc = result.substring(result.indexOf(";")+1,result.length);
		
	 document.frmPost.txtFiberNodeID.value=resultID;
	 document.frmPost.txtFiberNode.value=resultDesc;
	 }
	 }
}

//-->
</Script>


<table  border="0" align="center" cellpadding="0" cellspacing="10">
  <tr>
    <td><img src="img/list_tit.gif" width="19" height="19"></td>
    <td class="title">¼��ģ���ն���Ϣ</td>
  </tr>
</table>

<table width="100%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>
<br>  

<form name="frmPost" method="post" action="catv_terminal_create.do" >
<table width="810"  border="0" align="center" cellpadding="4" cellspacing="1" class="list_bg">
	<input type="hidden" name="txtActionType" value="create" >
        <input type="hidden" name="func_flag" value="11025" >
         <!--
         <tr>
                <td class="list_bg2">
                <div align="right">���ݺ�*</div>
                </td>
                <td class="list_bg1">
                	<input name="txtPaperID" value=""  size="25" />
                <td class="list_bg2">
                <div align="right">С������*</div>
                </td>
                <td class="list_bg1">
                <input name="txtPortNumber" value=""  size="25" />
                </td>
        </tr>
        -->
        <tr>
                <td class="list_bg2">
                <div align="right">��������*</div>
                </td>
                <td class="list_bg1">
                	<input type="hidden" name="txtDistrictID" value="">
	    			<input type="text" name="txtDistrictIDDesc" size="25" maxlength="50" readonly value="" readonly class="text">
	    			<input name="selDistButton" type="button" class="button" value="ѡ��" onClick="javascript:sel_district('all','txtDistrictID','txtDistrictIDDesc')">
                </td>
                <td class="list_bg2">
                <div align="right">�˿���*</div>
                </td>
                <td class="list_bg1">
                <input name="txtPortNo" value=""  size="25" maxlength="10" />
                </td>
        </tr>
        <tr>
                <td class="list_bg2">
                <div align="right">�ʱ�*</div>
                </td>
                <td class="list_bg1">
                	<input name="txtPostCode" value="" size="25" maxlength="6" /></td>
                <td class="list_bg2">
                <div align="right">�ն�״̬*</div>
                </td>
                <td class="list_bg1">
                	<d:selcmn name="txtStatus" mapName="SET_A_CATVTERMINALSTATUS" width="23" /></td>
        </tr>
        <tr>
                <td class="list_bg2">
                <div align="right">��ϸ��ַ*</div>
                </td>
                <td class="list_bg1">
                	<input name="txtDetailedAddress" type="text"  maxlength="200" size="25" value="" ></td>
                <td class="list_bg2">
                <div align="right">��������</div>
                </td>
                <td class="list_bg1">
                	<d:selcmn name="txtCableType" mapName="SET_A_CABLETYPE" width="23" /></td>
        </tr>
        <tr>
        		<td class="list_bg2">
                <div align="right">˫����������</div>
                </td>
                <td class="list_bg1">
                	<d:selcmn name="txtBiDirectionFlag" mapName="SET_G_YESNOFLAG" width="23" /></td>
                <td class="list_bg2">
                <div align="right">������ڵ�</div>
                </td>
                <td class="list_bg1" >
                <input name="txtFiberNodeID" type="hidden" size="25" value=""  >
                <input name="txtFiberNode" readonly type="text" size="25" value=""  >
                <input name="selFiberButton" type="button" class="button" value="ѡ��" onClick="javascript:query_fiber_node()">
                </td>
        </tr>
        <tr>
                <td class="list_bg2">
                <div align="right">�ն���Դ*</div>
                </td>
                <td class="list_bg1" >
                	<d:selcmn name="txtRecordSource" mapName="SET_A_CATVTERMINALRECORDSOURCE" width="23" /></td>
                <td class="list_bg2">
                <div align="right">�ն�����*</div>
                </td>
                <td class="list_bg1" >
                	<d:selcmn name="txtCatvTermType" mapName="SET_A_CATVTERMTYPE" width="23" /></td>
        </tr>
        <tr>
                <td class="list_bg2">
                <div align="right">��ע��Ϣ</div>
                </td>
                <td class="list_bg1" colspan=3 >
                <input name="txtComments" type="text" size="60" value="" maxlength="500" ></td>
                
        </tr>
</table>
<br>

<table width="98%" border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
								<tr align="center">
                                        <td >
                                        <table border="0" cellspacing="0" cellpadding="0">
                                                <tr>
                                                		<!--
                                                				<td width="20"></td>
                                                                <td width="11" height="20">
                                                                <img src="img/button2_r.gif" width="22" height="20"></td>
                                                                <td background="img/button_bg.gif"  >
                                                                <a href="<bk:backurl property='catv_terminal_list.do' />" class="btn12">��&nbsp;��</a></td>
                                                                <td width="22" height="20">
                                                                <img src="img/button2_l.gif" width="11" height="20"></td>
                                                      	-->
                                                        <pri:authorized name="catv_terminal_create.do" >
                                                        		<td width="20"></td>
                                                                <td width="11" height="20">
                                                                <img src="img/button_l.gif"	width="11" height="20"></td>
                                                                <td>
                                                                <input name="Submit" type="button" class="button" value="��&nbsp;��" onclick="javascript:create_submit()"></td>
                                                                <td width="22" height="20">
                                                                <img src="img/button_r.gif"	width="22" height="20"></td>       
														</pri:authorized>
                                                                
                                                </tr>
                                        </table>
                                        </td>
                                </tr>
</table>
                        
</form>     