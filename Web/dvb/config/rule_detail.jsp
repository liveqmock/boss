<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl"%>
<%@ taglib uri="logic" prefix="lgc"%>
<%@ taglib uri="osstags" prefix="d"%>
<%@ taglib uri="bookmark" prefix="bk"%>
<%@ page import="com.dtv.oss.util.Postern"%>
<%@ page import="com.dtv.oss.dto.UserPointsExchangeActivityDTO"%>

<script language=javascript> function check_frm() {

	
    if (check_Blank(document.frmPost.txtGoodTypeID, true, "兑换物品名称")) 
    	return false;

    if (check_Blank(document.frmPost.txtStatus, true, "状态")) 		
    	return false;

    if (check_Blank(document.frmPost.txtExchangeGoodsAmount, true, "每次兑换数量")||!check_Num(document.frmPost.txtExchangeGoodsAmount, true, "每次兑换数量")) 
    	return false; 
    if(check_Blank(document.frmPost.txtExchangeGoodsValue, true,  "所需要积分数")||!check_Num(document.frmPost.txtExchangeGoodsValue, true, "所需要积分数")) 
    	return false;	 

	return true; 
} 
function rule_modify_submit(){ 
	if (check_frm()){ 
		if(window.confirm('您确认要修改该规则信息吗?')){ 
			document.frmPost.action="rule_edit_done.do"; 
			document.frmPost.Action.value="update"; document.frmPost.submit(); 
		} 
	} 
} 
function open_select(type,typeName,typeValue,parentType,parentTypeName,parentTypeValue)
	{ var param="batch_query_select.screen?"; 
		param=param + "type="+type; 
	param=param + "&typeName="+typeName; 
	param=param + "&typeValue=" + document.frmPost.elements(typeName).value; 
	param=param + "&parentType="+parentType; 
	param=param + "&parentTypeName="+parentTypeName;

	if(parentTypeName!=null && parentTypeName!="") 		
	param=param + "&parentTypeValue="+document.frmPost.elements(parentTypeName).value;

	var windowFeatures="toolbar=no,location=no,status=no,menubar=no,scrollbars=yes,width=330,height=250,screenX=340,screenY=270";

	window.open(param,"",windowFeatures); }


</script>
<table border="0" align="center" cellpadding="0" cellspacing="10">
	<tr>
		<td><img src="img/list_tit.gif" width="19" height="19"></td>
		<td class="title">积分兑换规则详细信息</td>
	</tr>
</table>
<table width="98%" border="0" align="center" cellpadding="0"
	cellspacing="0" background="img/line_01.gif">
	<tr>
		<td><img src="img/mao.gif" width="1" height="1"></td>
	</tr>
</table>
<br>

<form name="frmPost" method="post"><lgc:bloop
	requestObjectName="ResponseQueryResult" item="oneline" IsOne="true">
	<%com.dtv.oss.dto.UserPointsExchangeRuleDTO dto = (com.dtv.oss.dto.UserPointsExchangeRuleDTO) pageContext
						.getAttribute("oneline");
				int activityID = dto.getActivityId();
				Map goodsMap = null;
				goodsMap = Postern.getGoodNameByActivityID(activityID);
				pageContext.setAttribute("GOODNAME", goodsMap);
				String custTypeList = dto.getCustTypeList();
				String totalValue = "";

				if (custTypeList != null) {
					String[] custArray = custTypeList.split(",");
					for (int j = 0; j < custArray.length; j++) {

						String value = Postern.getHashValueListByNameKey(
								"SET_C_CUSTOMERTYPE", custArray[j]);
						if (totalValue == "")
							totalValue = value;
						else
							totalValue = totalValue + "," + value;
					}

				}

				%>

	<table width="98%" border="0" align="center" cellpadding="5"
		cellspacing="1" class="list_bg">
		<tr>
			<td class="list_bg2" align="right">兑换规则ID</td>
			<td class="list_bg1"><input type="text" name="txtID" size="25"
				value="<tbl:write  name="oneline" property="Id" />"
				class="textgray" readonly></td>
			<td class="list_bg2" align="right">兑换物品名称*</td>
			<td class="list_bg1"><tbl:select name="txtGoodTypeID" set="GOODNAME"
				match="oneline:exchangeGoodsTypeId" width="23" /></td>
		</tr>

		<tr>
			<td class="list_bg2" align="right">状态*</td>
			<td class="list_bg1"><d:selcmn name="txtStatus"
				mapName="SET_G_GENERALSTATUS" match="oneline:Status" width="23" />
			</td>

			<td class="list_bg2" align="right">每次兑换数量*</td>
			<td class="list_bg1"><input type="text" name="txtExchangeGoodsAmount"
				size="25"
				value="<tbl:write 
		name="oneline" property="exchangeGoodsAmount" />">
			</td>
		</tr>

		</tr>

		<tr>
			<td class="list_bg2" align="right">所需要积分数*</td>
			<td class="list_bg1"><input type="text" name="txtExchangeGoodsValue"
				size="25"
				value="<tbl:write 
	name="oneline" property="exchangePointsValue" />">
			</td>

			<td valign="middle" class="list_bg2" align="right">客户类型</td>
			<td class="list_bg1"><input name="txtCustTypeListValue" type="text"
				class="text" maxlength="200" size="25" value="<%=totalValue%>" /> <input
				name="txtCustTypeList" type="hidden" value="<tbl:write name="oneline" 	property="custTypeList" />"> <input	name="checkbutton" type="button" class="button" value="请选择"
				onClick="javascript:open_select('SET_C_CUSTOMERTYPE','txtCustTypeList',document.frmPost.txtCustTypeList.value,'','','')">
			</td>
		</tr>



	</table>
	<input type="hidden" name="txtDtLastmod"
		value="<tbl:write 
  name="oneline" property="DtLastmod" />">
	<input type="hidden" name="txtActivityID"
		value="<tbl:write name="oneline" property="ActivityId" 
  />">
	<input type="hidden" name="func_flag" value="5054">
	<input type="hidden" name="Action" value="">
	<table width="98%" border="0" align="center" cellpadding="5"
		cellspacing="1" class="list_bg">
		<tr align="center">
			<td class="list_bg1">
			<table align="center" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td><img src="img/button2_r.gif" border="0"></td>
					<td background="img/button_bg.gif"><a
						href="<bk:backurl property="query_points_rule.do" />"
						class="btn12">返&nbsp;回</a></td>
					<td><img src="img/button2_l.gif" border="0"></td>

					<td width="20"></td>
					<td><img src="img/button_l.gif" border="0"></td>
					<td background="img/button_bg.gif"><a
						href="javascript:rule_modify_submit()" class="btn12">修&nbsp; 改</a></td>
					<td><img src="img/button_r.gif" border="0"></td>
					<td width="20"></td>


				</tr>
			</table>
			</td>
		</tr>
	</table>
	<table width="98%" border="0" align="center" cellpadding="0"
		cellspacing="0" background="img/line_01.gif">
		<tr>
			<td><img src="img/mao.gif" width="1" height="1"></td>
		</tr>
	</table>
	<br>
</lgc:bloop></form>
