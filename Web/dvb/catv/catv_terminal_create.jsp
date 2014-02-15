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
	if (check_Blank(document.frmPost.txtDistrictIDDesc, true, "所在区域"))
		return false;

	if (check_Blank(document.frmPost.txtPortNo, true, "端口数"))
		return false;
	if (!checkPlainNum(document.frmPost.txtPortNo,false,64,"端口数"))
		return false;

	if (check_Blank(document.frmPost.txtPostCode, true, "邮编"))
		return false;
	if (!checkPlainNum(document.frmPost.txtPostCode,false,6,"邮编"))
		return false;

	if (check_Blank(document.frmPost.txtStatus, true, "终端状态"))
		return false;

	if (check_Blank(document.frmPost.txtDetailedAddress, true, "详细地址"))
		return false;	

	//if (check_Blank(document.frmPost.txtCableType, true, "线缆类型"))
	//	return false;

	//if (check_Blank(document.frmPost.txtBiDirectionFlag, true, "双向网改造标记"))
	//	return false;
	
	//if (check_Blank(document.frmPost.txtFiberNode, true, "所属光节点"))
	//	return false;
	
	if (check_Blank(document.frmPost.txtRecordSource, true, "终端来源"))
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
    <td class="title">录入模拟终端信息</td>
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
                <div align="right">单据号*</div>
                </td>
                <td class="list_bg1">
                	<input name="txtPaperID" value=""  size="25" />
                <td class="list_bg2">
                <div align="right">小区名称*</div>
                </td>
                <td class="list_bg1">
                <input name="txtPortNumber" value=""  size="25" />
                </td>
        </tr>
        -->
        <tr>
                <td class="list_bg2">
                <div align="right">所在区域*</div>
                </td>
                <td class="list_bg1">
                	<input type="hidden" name="txtDistrictID" value="">
	    			<input type="text" name="txtDistrictIDDesc" size="25" maxlength="50" readonly value="" readonly class="text">
	    			<input name="selDistButton" type="button" class="button" value="选择" onClick="javascript:sel_district('all','txtDistrictID','txtDistrictIDDesc')">
                </td>
                <td class="list_bg2">
                <div align="right">端口数*</div>
                </td>
                <td class="list_bg1">
                <input name="txtPortNo" value=""  size="25" maxlength="10" />
                </td>
        </tr>
        <tr>
                <td class="list_bg2">
                <div align="right">邮编*</div>
                </td>
                <td class="list_bg1">
                	<input name="txtPostCode" value="" size="25" maxlength="6" /></td>
                <td class="list_bg2">
                <div align="right">终端状态*</div>
                </td>
                <td class="list_bg1">
                	<d:selcmn name="txtStatus" mapName="SET_A_CATVTERMINALSTATUS" width="23" /></td>
        </tr>
        <tr>
                <td class="list_bg2">
                <div align="right">详细地址*</div>
                </td>
                <td class="list_bg1">
                	<input name="txtDetailedAddress" type="text"  maxlength="200" size="25" value="" ></td>
                <td class="list_bg2">
                <div align="right">线缆类型</div>
                </td>
                <td class="list_bg1">
                	<d:selcmn name="txtCableType" mapName="SET_A_CABLETYPE" width="23" /></td>
        </tr>
        <tr>
        		<td class="list_bg2">
                <div align="right">双向网改造标记</div>
                </td>
                <td class="list_bg1">
                	<d:selcmn name="txtBiDirectionFlag" mapName="SET_G_YESNOFLAG" width="23" /></td>
                <td class="list_bg2">
                <div align="right">所属光节点</div>
                </td>
                <td class="list_bg1" >
                <input name="txtFiberNodeID" type="hidden" size="25" value=""  >
                <input name="txtFiberNode" readonly type="text" size="25" value=""  >
                <input name="selFiberButton" type="button" class="button" value="选择" onClick="javascript:query_fiber_node()">
                </td>
        </tr>
        <tr>
                <td class="list_bg2">
                <div align="right">终端来源*</div>
                </td>
                <td class="list_bg1" >
                	<d:selcmn name="txtRecordSource" mapName="SET_A_CATVTERMINALRECORDSOURCE" width="23" /></td>
                <td class="list_bg2">
                <div align="right">终端类型*</div>
                </td>
                <td class="list_bg1" >
                	<d:selcmn name="txtCatvTermType" mapName="SET_A_CATVTERMTYPE" width="23" /></td>
        </tr>
        <tr>
                <td class="list_bg2">
                <div align="right">备注信息</div>
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
                                                                <a href="<bk:backurl property='catv_terminal_list.do' />" class="btn12">返&nbsp;回</a></td>
                                                                <td width="22" height="20">
                                                                <img src="img/button2_l.gif" width="11" height="20"></td>
                                                      	-->
                                                        <pri:authorized name="catv_terminal_create.do" >
                                                        		<td width="20"></td>
                                                                <td width="11" height="20">
                                                                <img src="img/button_l.gif"	width="11" height="20"></td>
                                                                <td>
                                                                <input name="Submit" type="button" class="button" value="保&nbsp;存" onclick="javascript:create_submit()"></td>
                                                                <td width="22" height="20">
                                                                <img src="img/button_r.gif"	width="22" height="20"></td>       
														</pri:authorized>
                                                                
                                                </tr>
                                        </table>
                                        </td>
                                </tr>
</table>
                        
</form>     