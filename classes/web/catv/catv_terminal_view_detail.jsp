<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="osstags" prefix="d" %>
<%@ taglib uri="bookmark" prefix="bk" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="resultset" prefix="rs" %>
<%@ taglib uri="privilege" prefix="pri" %>

<%@ page import="java.util.ArrayList,
                 java.util.Iterator,
                 java.util.HashMap" %>
<%@ page import="com.dtv.oss.util.Postern" %>
<%@ page import="com.dtv.oss.web.util.WebUtil" %>
<%@ page import="com.dtv.oss.web.util.CommonKeys" %>
<%@ page import="com.dtv.oss.web.util.WebOperationUtil" %>
<%@ page import="com.dtv.oss.dto.CatvTerminalDTO" %>
<%@ page import="java.util.LinkedList" %>
<%@ page import="com.dtv.oss.dto.*" %>
<%@ page import="java.sql.Timestamp" %>
<%@ page import="com.dtv.oss.util.Postern" %>
<%@ page import="com.dtv.oss.web.util.WebUtil" %>

<% 
   String txtID = request.getParameter("id");
   CatvTerminalDTO dto = Postern.getCatvTerminalByID(txtID);
   //所在区域
   int txtDistrictID = dto.getDistrictID();
   String txtDistrictIDDesc = Postern.getDistrictDesc(txtDistrictID);
   //编号
   //String txtCatvCode = dto.getCatvCode();
   //端口数
   int txtPortNo = dto.getPortNo();
   //邮编
   String txtPostCode = dto.getPostcode();
   //终端状态
   String txtStatus = dto.getStatus();
   //详细地址
   String txtDetailedAddress = dto.getDetailedAddress();
   //线缆类型
   String txtCableType = dto.getCableType();
   //双向网改造标记
   String txtBiDirectionFlag = dto.getBiDirectionFlag();
   //所属光节点
   int txtFiberNodeID = dto.getFiberNodeID();
   //备注信息
   String txtComments = dto.getComments();
   if(txtComments == null) txtComments = "";
   Timestamp txtDtLastmod = dto.getDtLastmod();
   //终端来源
   String txtRecordSource = dto.getRecordSource();
   //创建日期
   String txtCreateDate = WebUtil.TimestampToString(dto.getCreateDate(),"yyyy-MM-dd");
   //关断日期
   String txtPauseDate = WebUtil.TimestampToString(dto.getPauseDate(),"yyyy-MM-dd");
   //开通日期
   String txtActiveDate = WebUtil.TimestampToString(dto.getActiveDate(),"yyyy-MM-dd");
   //销号日期
   String txtCancelDate = WebUtil.TimestampToString(dto.getCancelDate(),"yyyy-MM-dd");
%>

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
	if (!checkPlainNum(document.frmPost.txtPostCode,false,64,"邮编"))
		return false;

	if (check_Blank(document.frmPost.txtStatus, true, "终端状态"))
		return false;

	if (check_Blank(document.frmPost.txtDetailedAddress, true, "详细地址"))
		return false;	

	if (check_Blank(document.frmPost.txtCableType, true, "线缆类型"))
		return false;	

	if (check_Blank(document.frmPost.txtBiDirectionFlag, true, "双向网改造标记"))
		return false;	

	if (check_Blank(document.frmPost.txtFiberNodeID, true, "所属光节点"))
		return false;	

    document.frmPost.submit();
}

function cancel_submit()
{
        document.frmPost.action="customer_billing_rule_query.do";
        document.frmPost.submit();
}

function query_fiber_node(){
	strFeatures = "dialogWidth=530px;dialogHeight=450px;center=yes;help=no";
	var strUrl="fiber_node_query.do?txtFrom=1&txtTo=10";
	var result=showModalDialog(strUrl,window,strFeatures);
	if (result!=null) document.frmPost.txtFiberNodeID.value=result;
}

//-->
</Script>


<table  border="0" align="center" cellpadding="0" cellspacing="10">
  <tr>
    <td><img src="img/list_tit.gif" width="19" height="19"></td>
    <td class="title">查看模拟终端信息</td>
  </tr>
</table>

<table width="100%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>
<br>  

<form name="frmPost" method="post" action="catv_terminal_update.do" >
<table width="100%"  border="0" align="center" cellpadding="4" cellspacing="1" class="list_bg">
		<input type="hidden" name="txtID" value="<%=txtID%>" >
		<input type="hidden" name="txtDtLastmod" value="<%=txtDtLastmod%>" >
		<input type="hidden" name="txtActionType" value="update" >
        <input type="hidden" name="func_flag" value="11024" >
         <tr>
                <td class="list_bg2">
                <div align="right">终端ID</div>
                </td>
                <td class="list_bg1">
                	<input name="txtID" value="<%=txtID%>"  readonly class="textgray" size="25" /></td>
             	<td class="list_bg2">
                <div align="right">端口数</div>
                </td>
                <td class="list_bg1">
                <input name="txtPortNo" value="<%=txtPortNo%>" readonly class="textgray" size="25" maxlength="10" />
                </td>
        </tr>
        <tr>
                <td class="list_bg2">
                <div align="right">所在区域</div>
                </td>
                <td class="list_bg1">
                	<input type="hidden" name="txtDistrictID" value="<%=txtDistrictID%>">
	    			<input type="text" name="txtDistrictIDDesc" size="25" maxlength="50" readonly class="textgray" value="<%=txtDistrictIDDesc%>" class="text">
                </td>
                
                <td class="list_bg2">
                <div align="right">终端状态</div>
                </td>
                <td class="list_bg1">
                	<!--<d:selcmn name="txtStatus" mapName="SET_A_CATVTERMINALSTATUS" match="<%=txtStatus%>" readonly  disabled width="23" /></td>-->
                	<input name="txtStatus" value="<%=WebUtil.NullToString(Postern.getCommonSettingDataValueByNameAndKey("SET_A_CATVTERMINALSTATUS",txtStatus))%>"  readonly class="textgray" size="25" maxlength="20" /></td>
        </tr>
        <tr>
                <td class="list_bg2">
                <div align="right">邮编</div>
                </td>
                <td class="list_bg1">
                	<input name="txtPostCode" value="<%=txtPostCode%>"  readonly class="textgray" size="25" maxlength="20" /></td>
                
        		<td class="list_bg2">
                <div align="right">线缆类型</div>
                </td>
                <td class="list_bg1">
                	<!--<d:selcmn name="txtCableType" mapName="SET_A_CABLETYPE" match="<%=txtCableType%>" width="23" readonly disabled /></td>-->
                	<input name="txtCableType" value="<%=WebUtil.NullToString(Postern.getCommonSettingDataValueByNameAndKey("SET_A_CABLETYPE",txtCableType))%>"  readonly class="textgray" size="25" maxlength="20" /></td>
        </tr>
        <tr>
                <td class="list_bg2">
                <div align="right">详细地址</div>
                </td>
                <td class="list_bg1">
                	<input name="txtDetailedAddress" type="text"  maxlength="200" size="25" value="<%=txtDetailedAddress%>" readonly class="textgray" ></td>
                
        		<td class="list_bg2">
                <div align="right">所属光节点</div>
                </td>
                <td class="list_bg1" >
                <input name="txtFiberNodeID" type="text" size="25" value="<%=WebUtil.NullToString(Postern.getFiberNodeCodeByID(txtFiberNodeID))%>" readonly class="textgray">
                </td>
        </tr>
        <tr>
        		<td class="list_bg2">
                <div align="right">双向网改造标记</div>
                </td>
                <td class="list_bg1">
                	<!--<d:selcmn name="txtBiDirectionFlag" mapName="SET_G_YESNOFLAG" match="<%=txtBiDirectionFlag%>" width="23" readonly disabled /></td>-->
                	<input name="txtBiDirectionFlag" value="<%=Postern.getCommonSettingDataValueByNameAndKey("SET_G_YESNOFLAG",txtBiDirectionFlag)%>"  readonly class="textgray" size="25" maxlength="20" /></td>
                
                <td class="list_bg2">
                <div align="right">终端来源</div>
                </td>
                <td class="list_bg1" >
                	<input name="txtRecordSource" value="<%=Postern.getCommonSettingDataValueByNameAndKey("SET_A_CATVTERMINALRECORDSOURCE",txtRecordSource)%>"  readonly class="textgray" size="25" maxlength="20" /></td>
        </tr>
        <tr>
        		
                
                <td class="list_bg2">
                <div align="right">创建日期</div>
                </td>
                <td class="list_bg1" >
                	<input name="txtCreateDate" type="text" size="25" value="<%=txtCreateDate%>" readonly class="textgray">
                </td>
                
                <td class="list_bg2">
                <div align="right">关断日期</div>
                </td>
                <td class="list_bg1">
                	<input name="txtPauseDate" value="<%=txtPauseDate%>"  readonly class="textgray" size="25" /></td>
        </tr>
        <tr>
                <td class="list_bg2">
                <div align="right">开通日期</div>
                </td>
                <td class="list_bg1">
                	<input name="txtActiveDate" value="<%=txtActiveDate%>"  readonly class="textgray" size="25" /></td>
                <td class="list_bg2">
                <div align="right">销号日期</div>
                </td>
                <td class="list_bg1" >
                	<input name="txtCancelDate" type="text" size="25" value="<%=txtCancelDate%>" readonly class="textgray">
                </td>
        </tr>
        <tr>
                <td class="list_bg2">
                <div align="right">备注信息</div>
                </td>
                <td class="list_bg1" colspan=3 >
                <input name="txtComments" type="text" maxlength="500" size="60" value="<%=txtComments%>" readonly class="textgray"></td>
        </tr>
     </table>
     <br>

     <table width="98%" border="0" align="center" cellpadding="5"
                                cellspacing="1" class="list_bg">
                                <tr align="center">
                                        <td >
                                        <table border="0" cellspacing="0" cellpadding="0">
                                                <tr>
                                                		

                                                                <td width="20"></td>
                                                                <td width="11" height="20"><img src="img/button2_r.gif"
                                                                        width="22" height="20"></td>
                                                                <td background="img/button_bg.gif"  >
                                                                        	<a href="<bk:backurl property='catv_terminal_list.do/catv_terminal_query.do' />" class="btn12">返&nbsp;回</a>
                                                                        </td>
                                                                <td width="22" height="20"><img src="img/button2_l.gif"
                                                                        width="11" height="20"></td>
                                                </tr>
                                        </table>
                                        </td>
                                </tr>
                        </table>
                        
</form>     