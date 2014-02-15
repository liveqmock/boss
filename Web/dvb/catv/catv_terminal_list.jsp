<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="resultset" prefix="rs" %>
<%@ taglib uri="osstags" prefix="d" %>
<%@ taglib uri="privilege" prefix="pri" %>
<%@ page import="com.dtv.oss.dto.CatvTerminalDTO" %>
<%@ page import="java.sql.Timestamp" %>
<%@ page import="com.dtv.oss.web.util.WebUtil" %>
<%@ page import="com.dtv.oss.util.Postern" %>

<%
	String id = request.getParameter("id");
	String txtUserName = request.getParameter("txtUserName");
	String txtDetailedAddress = request.getParameter("txtDetailedAddress");
	String txtPortNo = request.getParameter("txtPortNo");
	String txtDistrictID = request.getParameter("txtDistrictID");
	String txtDistrictIDDesc = request.getParameter("txtDistrictIDDesc");
	String txtFiberNodeID = request.getParameter("txtFiberNodeID");
	String txtFiberNode = request.getParameter("txtFiberNode");
	String txtStatus = request.getParameter("txtStatus");
	String txtCableType = request.getParameter("txtCableType");
	String txtRecordSource = request.getParameter("txtRecordSource");
	String txtBiDirectionFlag = request.getParameter("txtBiDirectionFlag");
	
	String txtCreateTimeStart = request.getParameter("txtCreateTimeStart");
	String txtCreateTimeEnd = request.getParameter("txtCreateTimeEnd");
	
	String txtPauseTimeStart = request.getParameter("txtPauseTimeStart");
	String txtPauseTimeEnd = request.getParameter("txtPauseTimeEnd");
	
	String txtActiveTimeStart = request.getParameter("txtActiveTimeStart");
	String txtActiveTimeEnd = request.getParameter("txtActiveTimeEnd");
	
	String txtCancelTimeStart = request.getParameter("txtCancelTimeStart");
	String txtCancelTimeEnd = request.getParameter("txtCancelTimeEnd");
	
	String txtOrderBy = request.getParameter("txtOrderBy");	
	String txtCatvTermType = request.getParameter("txtCatvTermType");	
%>

<Script language=JavaScript>
<!--
//�޸�
function update_click(strId)
{
   self.location.href="catv_terminal_update.screen?id="+strId;
}
function check_form(){
	//��������
	if (document.frmPost.txtCreateTimeStart.value != ''){
		if (!check_TenDate(document.frmPost.txtCreateTimeStart, true, "��ʼ����")){
			return false;
		}
	}
	if (document.frmPost.txtCreateTimeEnd.value != ''){
		if (!check_TenDate(document.frmPost.txtCreateTimeEnd, true, "��������")){
			return false;
		}
	}
	if(!compareDate(document.frmPost.txtCreateTimeStart,document.frmPost.txtCreateTimeEnd,"�������ڱ�����ڵ��ڿ�ʼ����")){
		
		return false;
	}
	
	//�ض�����
	if (document.frmPost.txtPauseTimeStart.value != ''){
		if (!check_TenDate(document.frmPost.txtPauseTimeStart, true, "��ʼ����")){
			return false;
		}
	}
	if (document.frmPost.txtPauseTimeEnd.value != ''){
		if (!check_TenDate(document.frmPost.txtPauseTimeEnd, true, "��������")){
			return false;
		}
	}
	if(!compareDate(document.frmPost.txtPauseTimeStart,document.frmPost.txtPauseTimeEnd,"�������ڱ�����ڵ��ڿ�ʼ����")){
		
		return false;
	}
	
	//��ͨ����
	if (document.frmPost.txtActiveTimeStart.value != ''){
		if (!check_TenDate(document.frmPost.txtActiveTimeStart, true, "��ʼ����")){
			return false;
		}
	}
	if (document.frmPost.txtActiveTimeEnd.value != ''){
		if (!check_TenDate(document.frmPost.txtActiveTimeEnd, true, "��������")){
			return false;
		}
	}
	if(!compareDate(document.frmPost.txtActiveTimeStart,document.frmPost.txtActiveTimeEnd,"�������ڱ�����ڵ��ڿ�ʼ����")){
		
		return false;
	}
	
	//��������
	if (document.frmPost.txtCancelTimeStart.value != ''){
		if (!check_TenDate(document.frmPost.txtCancelTimeStart, true, "��ʼ����")){
			return false;
		}
	}
	if (document.frmPost.txtCancelTimeEnd.value != ''){
		if (!check_TenDate(document.frmPost.txtCancelTimeEnd, true, "��������")){
			return false;
		}
	}
	if(!compareDate(document.frmPost.txtCancelTimeStart,document.frmPost.txtCancelTimeEnd,"�������ڱ�����ڵ��ڿ�ʼ����")){
		
		return false;
	}

  if(!checkPlainNum(document.frmPost.txtPortNo,true,9,"�˿���")){
		return false;
  }
  
	return true;
}
//��ѯ
function query_submit()
{
	if (check_form()){
    document.frmPost.submit();
	}
  
}
//����
function create_submit(){
	self.location.href = "catv_terminal_create.screen";
}

//��ѯ��ڵ�
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


function form_Reset(){
	var els=document.frmPost.elements;
	for( i=0;i<els.length;i++){
		var control=els[i];
		if(control.type!='button' && control.type!='submit' && control.name!='func_flag' && control.name!='txtActionType' && control.name!='txtCCID' && control.name!='txtFrom' && control.name!='txtTo' && control.name!='txtDoPost')
			els[i].value="";
	}
}

-->
</Script>


<table  border="0" align="center" cellpadding="0" cellspacing="5">
      <tr>
        <td><img src="img/list_tit.gif" width="19" height="19"></td>
        <td class="title">�ն˲�ѯ</td>
      </tr>
</table>

<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
      <tr>
        <td><img src="img/mao.gif" width="1" height="1"></td>
      </tr>
</table>
<br>
      
<form name="frmPost" method="post" action="catv_terminal_list.do" >
<tbl:hiddenparameters pass="txtCustomerID" />
<input type="hidden" name="txtCCID" value="">
<input type="hidden" name="txtFrom" value="1">
<input type="hidden" name="txtTo" value="10">
<input type="hidden" name="func_flag" value=""> 
<input type="hidden" name="txtActionType" size="22" value="">
<input type="hidden" name="txtDoPost" size="22" value="FALSE">

<table width="810"  border="0" align="center" cellpadding="4" cellspacing="1" class="list_bg">
        <tr>
                <td class="list_bg2">
                <div align="right">�ն�ID</div>
                </td>
                <td class="list_bg1">
                	<input name="id" type="text"  maxlength="20" size="25" value="<%=WebUtil.NullToString(id)%>"></td>
                <td class="list_bg2">
                <div align="right">ģ���û�����</div>
                </td>
                <td class="list_bg1">
                	<input name="txtUserName" type="text"  maxlength="20" size="25" value="<%=WebUtil.NullToString(txtUserName)%>"></td></td>
        </tr>
        <tr>
                <td class="list_bg2">
                <div align="right">�ն˵�ַ</div>
                </td>
                <td class="list_bg1">
                	<input name="txtDetailedAddress" type="text"  maxlength="20" size="25" value="<%=WebUtil.NullToString(txtDetailedAddress)%>"></td>
                <td class="list_bg2">
                <div align="right">�˿���</div>
                </td>
                <td class="list_bg1">
                	<input name="txtPortNo" type="text"  maxlength="20" size="25" value="<%=WebUtil.NullToString(txtPortNo)%>"></td>
        </tr>
        <tr>
                <td class="list_bg2">
                <div align="right">��������</div>
                </td>
                <td class="list_bg1">
                	<input type="hidden" name="txtDistrictID" value="<%=WebUtil.NullToString(txtDistrictID)%>">
	    			<input type="text" name="txtDistrictIDDesc" size="25" maxlength="50" readonly value="<%=WebUtil.NullToString(txtDistrictIDDesc)%>" class="text">
	    			<input name="selDistButton" type="button" class="button" value="ѡ��" onClick="javascript:sel_district('all','txtDistrictID','txtDistrictIDDesc')">
                <td class="list_bg2">
                <div align="right">��ڵ�</div>
                </td>
                <td class="list_bg1">
                	<input name="txtFiberNodeID" type="hidden"  maxlength="20" size="25" value="<%=WebUtil.NullToString(txtFiberNodeID)%>">
                	<input name="txtFiberNode" readonly type="text"  maxlength="20" size="25" value="<%=WebUtil.NullToString(txtFiberNode)%>">
                	<input name="selFiberButton" type="button" class="button" value="ѡ��" onClick="javascript:query_fiber_node()"></td>
        </tr>
        <tr>
                <td class="list_bg2">
                <div align="right">�ն�״̬</div>
                </td>
                <td class="list_bg1">
	               <d:selcmn name="txtStatus" mapName="SET_A_CATVTERMINALSTATUS" match="<%=WebUtil.NullToString(txtStatus)%>" width="23" /></td>
                <td class="list_bg2">
                <div align="right">��������</div>
                </td>
                <td class="list_bg1">
                	<d:selcmn name="txtCableType" mapName="SET_A_CABLETYPE" match="<%=WebUtil.NullToString(txtCableType)%>" width="23" /></td>
        </tr>
        <tr>
                <td class="list_bg2">
                <div align="right">�ն���Դ</div>
                </td>
                <td class="list_bg1">
	               <d:selcmn name="txtRecordSource" mapName="SET_A_CATVTERMINALRECORDSOURCE" match="<%=WebUtil.NullToString(txtRecordSource)%>" width="23" /></td>
                <td class="list_bg2">
                <div align="right">˫�������</div>
                </td>
                <td class="list_bg1">
                	<d:selcmn name="txtBiDirectionFlag" mapName="SET_G_YESNOFLAG" match="<%=WebUtil.NullToString(txtBiDirectionFlag)%>" width="23" /></td>
        </tr>
        <tr>
        		<td class="list_bg2">
                <div align="right">�ն�����</div>
                </td>
                <td class="list_bg1">
	               <d:selcmn name="txtCatvTermType" mapName="SET_A_CATVTERMTYPE" match="<%=WebUtil.NullToString(txtCatvTermType)%>" width="23" /></td>
                <td class="list_bg2">
                <div align="right">��������</div>
                </td>
                <td class="list_bg1" >
                <input onfocus="viewModule(this)" onKeydown="inputDate(document.frmPost.txtCreateTimeStart)" onblur="lostFocus(this)" type="text" name="txtCreateTimeStart" size="10" maxlength="10" value="<%=WebUtil.NullToString(txtCreateTimeStart)%>"  class="text">					<IMG onclick="MyCalendar.SetDate(this,document.frmPost.txtCreateTimeStart,'Y')" src="img/calendar.gif" style=cursor:hand border="0">
					--
					<input onfocus="viewModule(this)" onKeydown="inputDate(document.frmPost.txtCreateTimeEnd)" onblur="lostFocus(this)" type="text" name="txtCreateTimeEnd" size="10" maxlength="10" value="<%=WebUtil.NullToString(txtCreateTimeEnd)%>"  class="text">					<IMG onclick="MyCalendar.SetDate(this,document.frmPost.txtCreateTimeEnd,'Y')" src="img/calendar.gif" style=cursor:hand border="0">
                </td>
		</tr>
        <tr>   
                <td class="list_bg2">
                <div align="right">�ض�����</div>
                </td>
                <td class="list_bg1" >
                <input onfocus="viewModule(this)" onKeydown="inputDate(document.frmPost.txtPauseTimeStart)" onblur="lostFocus(this)" type="text" name="txtPauseTimeStart" size="10" maxlength="10" value="<%=WebUtil.NullToString(txtPauseTimeStart)%>"  class="text">					<IMG onclick="MyCalendar.SetDate(this,document.frmPost.txtPauseTimeStart,'Y')" src="img/calendar.gif" style=cursor:hand border="0">
					--
					<input onfocus="viewModule(this)" onKeydown="inputDate(document.frmPost.txtPauseTimeEnd)" onblur="lostFocus(this)" type="text" name="txtPauseTimeEnd" size="10" maxlength="10" value="<%=WebUtil.NullToString(txtPauseTimeEnd)%>"  class="text">					<IMG onclick="MyCalendar.SetDate(this,document.frmPost.txtPauseTimeEnd,'Y')" src="img/calendar.gif" style=cursor:hand border="0">
                </td>

                <td class="list_bg2">
                <div align="right">��ͨ����</div>
                </td>
                <td class="list_bg1" >
                <input onfocus="viewModule(this)" onKeydown="inputDate(document.frmPost.txtActiveTimeStart)" onblur="lostFocus(this)" type="text" name="txtActiveTimeStart" size="10" maxlength="10" value="<%=WebUtil.NullToString(txtActiveTimeStart)%>" class="text">					<IMG onclick="MyCalendar.SetDate(this,document.frmPost.txtActiveTimeStart,'Y')" src="img/calendar.gif" style=cursor:hand border="0">
					--
					<input onfocus="viewModule(this)" onKeydown="inputDate(document.frmPost.txtActiveTimeEnd)" onblur="lostFocus(this)" type="text" name="txtActiveTimeEnd" size="10" maxlength="10" value="<%=WebUtil.NullToString(txtActiveTimeEnd)%>" class="text">					<IMG onclick="MyCalendar.SetDate(this,document.frmPost.txtActiveTimeEnd,'Y')" src="img/calendar.gif" style=cursor:hand border="0">
                </td>
		 </tr>
         <tr>  
                <td class="list_bg2">
                <div align="right">��������</div>
                </td>
                <td class="list_bg1" >
                <input onfocus="viewModule(this)" onKeydown="inputDate(document.frmPost.txtCancelTimeStart)" onblur="lostFocus(this)" type="text" name="txtCancelTimeStart" size="10" maxlength="10" value="<%=WebUtil.NullToString(txtCancelTimeStart)%>" class="text">					<IMG onclick="MyCalendar.SetDate(this,document.frmPost.txtCancelTimeStart,'Y')" src="img/calendar.gif" style=cursor:hand border="0">
					--
					<input onfocus="viewModule(this)" onKeydown="inputDate(document.frmPost.txtCancelTimeEnd)" onblur="lostFocus(this)" type="text" name="txtCancelTimeEnd" size="10" maxlength="10" value="<%=WebUtil.NullToString(txtCancelTimeEnd)%>" class="text">					<IMG onclick="MyCalendar.SetDate(this,document.frmPost.txtCancelTimeEnd,'Y')" src="img/calendar.gif" style=cursor:hand border="0">
                </td>

                <td class="list_bg2">
                <div align="right">��ѯ�������ʽ</div>
                </td>
                <td class="list_bg1" >
                	<select name="txtOrderBy" width="23" >
                		<option value="">-----------------------</option>
                		<option value="address_asc">��ַ������	</option>
						<option value="address_desc">��ַ������	</option>
						<option value="ID_asc">�ն�ID������&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</option>
						<option value="ID_desc">�ն�ID������	</option>
					</select>
				</td>
        </tr>
</table>

     
<table width="810"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
	  <tr align="center">
	  <td class="list_bg1" >
	  	<table  border="0" cellspacing="0" cellpadding="0">
		  <tr align="right">
		  	<td width="11" height="20"><img src="img/button2_r.gif" width="22" height="20"></td>
			<td><input name="Reset" type="button" class="button" value="���ò�ѯ����" onclick="form_Reset()" ></td>
			<td width="11" height="20"><img src="img/button2_l.gif" width="11" height="20"></td>
			<td width="20" ></td>
		  	<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
			<td><input name="Submit" type="button" class="button" value="��&nbsp;ѯ" onclick="javascript:query_submit()"></td>
			<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
			<td width="22" height="20">&nbsp;&nbsp;</td>
		  </tr>
	   </table> 
	  </td>
  	  </tr>
</table> 

 <table width="810"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
      <tr>
        <td><img src="img/mao.gif" width="1" height="1"></td>
      </tr>
</table>
<br>     
   
    <rs:hasresultset>
      <table width="810" border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
         <tr class="list_head">     
           <!--<td>ѡ��</td>-->
           <td class="list_head" width="60" nowrap>�ն�ID</td>
           <td class="list_head" width="100" nowrap>����</td>
           <td class="list_head" width="160" nowrap>��ַ</td>
           <td class="list_head" width="40" nowrap>�˿���</td>
           <td class="list_head" width="60" nowrap>��ڵ�</td>
           <td class="list_head" width="60" nowrap>�ն���Դ</td>
           <td class="list_head" width="40" nowrap>˫����</td>
           <td class="list_head" width="30" nowrap>�ն�����</td>
           <td class="list_head" width="30" nowrap>�ն�״̬</td>
           <td class="list_head" width="60" nowrap>��������</td>
           <td class="list_head" width="70" nowrap>�û�����</td>
           <!--
           <td class="list_head" nowrap></td>
           -->
         </tr>    
        <lgc:bloop requestObjectName="ResponseQueryResult" item="oneline" >
        <%
					CatvTerminalDTO catvTerminalDTO = (CatvTerminalDTO) pageContext.getAttribute("oneline");
					Timestamp txtCreateTimeOneline = catvTerminalDTO.getCreateDate();
					String txtstatusNameOneline = Postern.getCommonSettingDataValueByNameAndKey("SET_A_CATVTERMINALSTATUS",catvTerminalDTO.getStatus());
					String txtRecordSourceOneline = Postern.getCommonSettingDataValueByNameAndKey("SET_A_CATVTERMINALRECORDSOURCE",catvTerminalDTO.getRecordSource());
					String txtBiDirectionFlagOneline = Postern.getCommonSettingDataValueByNameAndKey("SET_G_YESNOFLAG",catvTerminalDTO.getBiDirectionFlag());
					String txtCatvTermTypeOneline = Postern.getCommonSettingDataValueByNameAndKey("SET_A_CATVTERMTYPE",catvTerminalDTO.getCatvTermType());
					String txtDistrictIDDescOneline = Postern.getDistrictDesc(catvTerminalDTO.getDistrictID());
					String txtFiberNodeOneline = Postern.getFiberNodeCodeByID(catvTerminalDTO.getFiberNodeID());
					
		%>
          <tbl:printcsstr style1="list_bg1" style2="list_bg2" overStyle="list_over" > 
           <!--<td align="center"><input type="radio" name="signradio" value=""></td>-->
           		<td align="center"><a href="#" onClick="update_click('<tbl:write name="oneline" property="Id"/>')" class="link12" ><tbl:write name="oneline" property="id"/></a></td>
             	<td align="center"><%=txtDistrictIDDescOneline%></td>
             	<td align="center"><tbl:write name="oneline" property="DetailedAddress"/></td>
             	<td align="center"><tbl:write name="oneline" property="portNo"/></td>
             	<td align="center"><%=WebUtil.NullToString(txtFiberNodeOneline)%></td>
             	<td align="center"><%=WebUtil.NullToString(txtRecordSourceOneline)%></td>
             	<td align="center"><%=WebUtil.NullToString(txtBiDirectionFlagOneline)%></td>
             	<td align="center"><%=WebUtil.NullToString(txtCatvTermTypeOneline)%></td>
             	<td align="center"><%=WebUtil.NullToString(txtstatusNameOneline)%></td>
             	<td align="center"><%=WebUtil.TimestampToString(txtCreateTimeOneline,"yyyy-MM-dd")%></td>
             	<td align="center"><tbl:write name="oneline" property="comments"/></td>
           		</td>
          </tbl:printcsstr>
        </lgc:bloop>
		<tbl:generatetoken />
			<tr>
					<td colspan="11" class="list_foot"></td>
			</tr>
        	<tr>
              <td align="right" class="t12" colspan="11" >
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
</form>

<%
if(txtOrderBy != null){
%>
<Script language=JavaScript>
<!--
	document.frmPost.txtOrderBy.value = '<%=txtOrderBy%>';
-->
</Script>
<%}%>