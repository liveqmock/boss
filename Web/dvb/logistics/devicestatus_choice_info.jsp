<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl"%>
<%@ taglib uri="logic" prefix="lgc"%>
<%@ taglib uri="resultset" prefix="rs"%>
<%@ taglib uri="osstags" prefix="d"%>
<%@ page import="com.dtv.oss.util.Postern"%>
<%@ page import="com.dtv.oss.dto.TerminalDeviceDTO"%>
<%@ page import="com.dtv.oss.web.util.WebUtil"%>

<script language=javascript>
function back_submit()
{
	document.frmPost.action="devicestatus_query.do";
	document.frmPost.submit();
}
function next_submit()
{
	if(check_form()){
	document.frmPost.action="devicestatus_confirm.do";
	document.frmPost.submit();
}
}
function check_form(){
	
	if(check_chosen(document.frmPost,document.frmPost.DeviceList))
	{
			return true;

	}else{
		alert("缺少选择,无法完成操作!");
		return false;
	}
          	
	return false;
}

function query_submit()
{
	document.frmPost.action="devicestatus_choice_info.do";
        //if (check_form()) 
        document.frmPost.submit();
}


function del_selected_device_submit()
{
        document.frmPost.txtCurAction.value="delselected";
        document.frmPost.action="devicestatus_choice_info.do";
        document.frmPost.submit();
}

function ChangeDeviceClass()
{
    
    document.FrameUS.submit_update_select('devclasstomodel', document.frmPost.txtDeviceClass.value, 'txtDeviceModel', '');
}
</script>

<div id="updselwaitlayer"
	style="position:absolute; left:650px; top:180px; width:250px; height:24px; display:none">
<table width="100%" border="0" cellspacing="1" cellpadding="3">
	<tr>
		<td width="100%">
		<div align="center"><font size="2"> 正在获取内容。。。 </font>
		</td>
	</tr>
</table>
</div>

<iframe SRC="update_select.screen" name="FrameUS" width="0" height="0"
	frameborder="0" scrolling="0"> </iframe>
<TABLE border="0" cellspacing="0" cellpadding="0" width="820">
	<TR>
		<TD>
		<table border="0" align="center" cellpadding="0" cellspacing="10">
			<tr>
				<td><img src="img/list_tit.gif" width="19" height="19"></td>
				<td class="title">设备状态查询——选择设备清单</td>
			</tr>
		</table>
		<table width="98%" border="0" align="center" cellpadding="0"
			cellspacing="0" background="img/line_01.gif">
			<tr>
				<td><img src="img/mao.gif" width="1" height="1"></td>
			</tr>
		</table>
		<br>
		<form name="frmPost" method="post"
			action="devicestatus_choice_info.do">
		<table width="98%" border="0" align="center" cellpadding="5"
			cellspacing="1" class="list_bg">
			<tr>
				<td class="list_bg2" align="right">修改单编号</td>
				<td class="list_bg1"><input type="text" name="txtOperationNo" size="25"
					value="<tbl:writeparam name="txtOperationNo" />" class="textgray"
					readonly></td>
				<td class="list_bg2" align="right">描述</td>
				<td class="list_bg1"><input type="text" name="txtDescription"
					size="25" value="<tbl:writeparam name="txtDescription" />"
					class="textgray" readonly> </font></td>
			</tr>
			<%Map mapDepots = Postern.getAllDepot();

			%>
			<tr>
				<td class="list_bg2" align="right">设备原状态
				</div>
				</td>
				<td class="list_bg1"><input type="text"
					name="txtFromDeviceStatusShow" size="25"
					value="<d:getcmnname typeName="SET_D_DEVICESTATUS" match="txtFromDeviceStatus"/>"
					class="textgray" readonly></td>
				<td class="list_bg2" align="right">设备目标状态</td>
				<td class="list_bg1" colspan="3"><input type="text"
					name="txtToDeviceStatusShow" size="25"
					value="<d:getcmnname typeName="SET_D_DEVICESTATUS" match="txtToDeviceStatus"/>"
					class="textgray" readonly></td>
			</tr>
			<tr>
		</table>
		<tbl:hiddenparameters
			pass="txtFromID/txtFromDeviceStatus/txtToDeviceStatus" />


		<table width="98%" border="0" align="center" cellpadding="5"
			cellspacing="1" class="list_bg">
			<tr>
				<td colspan="4" class="import_tit" align="center"><font size="3">供选择的设备
				</font></td>
			</tr>
			<tr>
				<td class="list_bg2" align="right">设备序列号</td>
				<td class="list_bg1" colspan="3"><input type="text"
					name="txtSerialNoBegin" maxlength="25" size="25"
					value="<tbl:writeparam name="txtSerialNoBegin" />"> -- <input
					type="text" name="txtSerialNoEnd" maxlength="25" size="25"
					value="<tbl:writeparam name="txtSerialNoEnd" />">
				(只填写第一个框则为精确查询)</td>
			</tr>
			<tr>
				<td class="list_bg2" align="right">类型</td>
				<td class="list_bg1"><%Map mapDeviceClasses = Postern.getAllDeviceClasses();
			pageContext.setAttribute("AllDeviceClasses", mapDeviceClasses);

			%> <tbl:select name="txtDeviceClass" set="AllDeviceClasses"
					match="txtDeviceClass" width="23" onchange="ChangeDeviceClass()" />
				</td>
				<td class="list_bg2" align="right">型号
				</div>
				</td>
				<td class="list_bg1"><d:seldevicemodel name="txtDeviceModel"
					deviceClass="txtDeviceClass" match="txtDeviceModel" width="23" />
				</td>

			</tr>
		</table>
		<table width="98%" border="0" align="center" cellpadding="5"
			cellspacing="1" class="list_bg">
			<tr align="center">
				<td class="list_bg1">
				<table border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td width="11" height="20"><img src="img/button_l.gif" width="11"
							height="20"></td>
						<td><input name="Submit" type="button" class="button"
							value="查 询" onclick="javascript:query_submit()"></td>
						<td width="22" height="20"><img src="img/button_r.gif" width="22"
							height="20"></td>
					</tr>
				</table>
				</td>
			</tr>
		</table>
			<rs:hasresultset>
		
		<table width="98%" border="0" align="center" cellpadding="5"
			cellspacing="1" class="list_bg">
			<tr class="list_head">
				<td class="list_head"><input class="check" type="checkbox"
					name="selall"
					onclick="javascript:select_all_by_name(document.frmPost,'DeviceList', this.checked)">
				</td>
				<td class="list_head">编号</td>
				<td class="list_head">类型</td>
				<td class="list_head">型号</td>
				<td class="list_head">序列号</td>
				<td class="list_head">地址类型</td>
				<td class="list_head">地址</td>
				<td class="list_head">状态</td>
			</tr>

			<lgc:bloop requestObjectName="ResponseQueryResult" item="oneline">
				<%TerminalDeviceDTO dto = (TerminalDeviceDTO) pageContext
						.getAttribute("oneline");
				String strClass = Postern.getDeviceClassDesc(dto
						.getDeviceClass());
				String strModel = dto.getDeviceModel();
				String strAddressType = dto.getAddressType();
				if (strAddressType == null)
					strAddressType = "";
				String strAddress = null;

				if (strAddressType.equals("D")) //仓库
				{
					strAddress = (String) mapDepots.get(String.valueOf(dto
							.getAddressID()));
				} else if (strAddressType.equals("T")) //组织机构
				{
					strAddress = Postern.getOrgNameByID(dto.getAddressID());
				} else if (strAddressType.equals("B")) //用户
				{
					strAddress = Postern
							.getCustomerNameById(dto.getAddressID());
				}

				if (strAddress == null)
					strAddress = "";

				%>
				<tr class="list_bg1" onmouseover="this.className='list_over'"
					onmouseout="this.className='list_bg1'">
					<td align="center"><input type="checkbox" name="DeviceList"
						value="<tbl:write name="oneline" property="DeviceID"/>"></td>
					<td align="center"><tbl:write name="oneline" property="DeviceID" /></td>
					<td align="center"><%=strClass%></td>
					<td align="center"><%=strModel%></td>
					<td align="center"><tbl:write name="oneline" property="SerialNo" /></td>
					<td align="center"><d:getcmnname typeName="SET_D_DEVICEADDRESSTYPE"
						match="oneline:AddressType" /></td>
					<td align="center"><%=strAddress%></td>
					<td align="center"><d:getcmnname typeName="SET_D_DEVICESTATUS"
						match="oneline:Status" /></td>
				</tr>
			</lgc:bloop>
				<tr>
					<td colspan="9" class="list_foot"></td>
				</tr>
				<table border="0" align="center" cellpadding="0" cellspacing="8">
					<tr>
						<td>第<span class="en_8pt"><rs:prop property="curpageno" /></span>页<span
							class="en_8pt">/</span>共<span class="en_8pt"><rs:prop
							property="pageamount" /><span>页</td>
						<td>共<span class="en_8pt"><rs:prop property="recordcount" /></span>条记录</td>
						<rs:notfirst>
							<td align="right"><img src="img/dot_top.gif" width="17"
								height="11"></td>
							<td><a
								href="javascript:firstPage_onClick(document.frmPost, <rs:prop property="firstto" />)"
								class="link12">首页</a></td>
							<td align="right"><img src="img/dot_pre.gif" width="6"
								height="11"></td>
							<td><a
								href="javascript:previous_onClick(document.frmPost, <rs:prop property="prefrom" />, <rs:prop property="preto" />)"
								class="link12">上一页</a></td>
						</rs:notfirst>
						<rs:notlast>
							<td align="right"><img src="img/dot_next.gif" width="6"
								height="11"></td>
							<td><a
								href="javascript:next_onClick(document.frmPost, <rs:prop property="nextfrom" />, <rs:prop property="nextto" />)"
								class="link12">下一页</a></td>
							<td align="right"><img src="img/dot_end.gif" width="17"
								height="11"></td>
							<td><a
								href="javascript:lastPage_onClick(document.frmPost, <rs:prop property="lastfrom" />, <rs:prop property="lastto" />)"
								class="link12">末页</a></td>
						</rs:notlast>
						<td align="right">跳转到</td>
						<td><input name="txtPage" type="text" class="page_txt"></td>
						<td>页</td>
						<td><input name="imageField" type="image" src="img/button_go.gif"
							width="28" height="15" border="0"
							onclick="javascript:goto_onClick(document.frmPost, <rs:prop property="pageamount" />)"></td>
					</tr>
				</table>
			</rs:hasresultset>

		</table>

		<input type="hidden" name="txtFrom"
			value="<tbl:writeparam name="txtFrom" />"> <input type="hidden"
			name="txtTo" value="<tbl:writeparam name="txtTo" />"> <input
			type="hidden" name="txtCurAction" value=""> <input type="hidden"
			name="txtStatus"
			value="<tbl:writeparam name="txtFromDeviceStatus" />"> <input
			type="hidden" name="txtDepotID"
			value="<tbl:writeparam name="txtFromID" />">
		<table width="98%" border="0" align="center" cellpadding="0"
			cellspacing="0" background="img/line_01.gif">
			<tr>
				<td><img src="img/mao.gif" width="1" height="1"></td>
			</tr>
		</table>
		<table align="center" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td><img src="img/button2_r.gif" border="0"></td>
				<td background="img/button_bg.gif"><a
					href="javascript:back_submit()" class="btn12">上一步</a></td>
				<td><img src="img/button2_l.gif" border="0"></td>
				<td width="20"></td>
				<td><img src="img/button_l.gif" border="0"></td>
				<td background="img/button_bg.gif"><a
					href="javascript:next_submit()" class="btn12">下一步</a></td>
				<td><img src="img/button_r.gif" border="0"></td>
			</tr>
		</table>

		</form>
	</tr>
	</td>


</table>
