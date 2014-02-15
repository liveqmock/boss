<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="osstags" prefix="d" %>
<%@ page import="java.util.*" %>
<%@ page import="com.dtv.oss.dto.FinancialSettingDTO" %>
<%@ page import="com.dtv.oss.util.Postern" %>
<%@ page import="java.text.DecimalFormat" %>

<%
	boolean exist = false;
	String alreadyExist = (String)request.getAttribute("already_exist");
	if(alreadyExist!=null && (alreadyExist=alreadyExist.trim()).equals("true")){
		exist = true;
	}
	String updateButtonLabel = null;
	if(exist){
		updateButtonLabel = "修 改";
	}else{
		updateButtonLabel = "添 加";
	}
%>
<script>
function checkInput(){
	if(check_Blank(document.frmPost.txtName,true,"名称")){
		return false;
	}
	if(!check_Num(document.frmPost.txtInvoiceDueDate,true,"日常帐单过期天数(整数)")){
		return false;
	}
	if(!check_Num(document.frmPost.txtPunishmentStartDate,true,"滞纳金开始计算天数(整数)")){
		return false;
	}
	if(!check_Float(document.frmPost.txtPunishmentRate,true,"日滞纳金比率")){
		return false;
	}
	return true;
}

function update(){
	if(checkInput()){
	if (window.confirm('您确认要修改吗?')){
			document.frmPost.submit();
		}
	}
}
</script>

<table width="820" align="center" cellpadding="0" cellspacing="0">
	<tr>
		<td>
			<table  border="0" align="center" cellpadding="0" cellspacing="10" >
  				<tr>
    				<td><img src="img/list_tit.gif" width="19" height="19"></td>
    				<td class="title"> 全局帐务参数 </td>
  				</tr>
			</table>
			<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
				<tr>
			    	<td><img src="img/mao.gif" width="1" height="1"></td>
			  	</tr>
			</table>
			<br>			 
			<form name="frmPost" method="post" action="financial_setting_modify.do" >
			<lgc:bloop requestObjectName="ResponseQueryResult" item="oneline" IsOne="true"> 
				<%			 	 	
			     	FinancialSettingDTO dto = (FinancialSettingDTO) pageContext.findAttribute("oneline");
			     	if(dto == null){
			     		dto = new FinancialSettingDTO();
			     	}
			     	pageContext.setAttribute("OnlyOneObject",dto);
			     	long dtLastmod = (dto.getDtLastmod()==null)? 0 : dto.getDtLastmod().getTime();	
			     	Map nameMap = null;
			     	nameMap=Postern.getAllAcctItemType();
			     	pageContext.setAttribute("AllAccountType",nameMap);
			     	
 

			     	double punishmenTrate=dto.getPunishmenTrate();			     	
			     	DecimalFormat df = new DecimalFormat("0.000"); 
						String punishmenTrateStr = df.format(punishmenTrate); 

			     	
				%>				
				<table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg" >
					<tr>
						<td class="list_bg2" align="right" >名称*</td>
						<% if(exist){ %>
						<td class="list_bg1" align="left" ><input type="text" value="<tbl:write name="OnlyOneObject" property="name" />" readonly size="25" /></td>
						<%}else{ %>
						<td class="list_bg1" align="left" ><input type="text" name="txtName" value="<tbl:write name="OnlyOneObject" property="name" />" size="25" /></td>
						<%}%>
						<td class="list_bg2" align="right" >使用全局统一帐期类型</td>
						<td class="list_bg1" align="left"><d:selcmn name="txtUnifiedCycleFlag" mapName="SET_G_YESNOFLAG" match="OnlyOneObject:unifiedCycleFlag" width="23" /></td>
					</tr>
					<tr>
						<td class="list_bg2" align="right" >日常帐单过期天数*</td>
						<td class="list_bg1" align="left" ><input type="text" name="txtInvoiceDueDate" value="<tbl:write name="OnlyOneObject" property="invoiceDueDate" />" size="25" /></td>
						<td class="list_bg2" align="right" >欠费付清自动恢复标志</td>
						<td class="list_bg1" align="left" ><d:selcmn name="txtAutoResumeCpFlag" mapName="SET_G_YESNOFLAG" match="OnlyOneObject:autoResumeCpFlag" width="23" /></td>
					</tr>
					<tr>
						<td class="list_bg2" align="right" >零头处理模式</td>
						<td class="list_bg1" align="left" ><d:selcmn name="txtSmallChangeProcessMode" mapName="SET_F_SMALLCHANGEPROCESSMODE" match="OnlyOneObject:smallchangeProcessMode" width="23" /></td>
						<td class="list_bg2" align="right" >预存抵扣方式</td>	
						<td class="list_bg1" align="left" ><d:selcmn name="txtPrepaymentDeductionMode" mapName="SET_F_PREPAYMENTDEDUCTIONMODE" match="OnlyOneObject:prepaymentDeductionMode" width="23" /></td>
					</tr>
					<tr>
						<td class="list_bg2" align="right" >帐单累计模式</td>
						<td class="list_bg1" align="left" ><d:selcmn name="txtInvoiceAccumulateMode" mapName="SET_F_INVOICEACCUMULATEMODE" match="OnlyOneObject:invoiceAccumulateMode" width="23" /></td>
						<td class="list_bg2" align="right" ></td>	
						<td class="list_bg1" align="left" ></td>
					</tr> 
					</table>
					<table width="98%" border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
					<tr>
						<td class="import_tit" align="center" colspan="7"><font size="2">滞纳金配置</font></td>
					</tr>
					<tr>
						<td class="list_bg2" align="right" >是否计算滞纳金</td>
						<td class="list_bg1" align="left" ><d:selcmn name="txtCalculatePunishmentFlag" mapName="SET_G_YESNOFLAG" match="OnlyOneObject:calculatePunishmentFlag" width="23" /></td>
						<td class="list_bg2" align="right" >滞纳金开始计算天数*</td>
						<td class="list_bg1" align="left" ><input type="text" name="txtPunishmentStartDate" size="25" value="<tbl:write name="OnlyOneObject" property="punishmentStartDate" />"/> </td>
					</tr>
					<tr>
						<td class="list_bg2" align="right" >滞纳金帐目类型</td>
						<td class="list_bg1" align="left" ><tbl:select name="txtPunishmentAcctItemTypeID" set="AllAccountType" match="OnlyOneObject:punishmentAcctItemTypeID" width="23" /> </td>
						<td class="list_bg2" align="right" >日滞纳金比率*</td>
						<td class="list_bg1" align="left" ><input type="text" name="txtPunishmentRate" size="25" value="<%=punishmenTrateStr%>"/></td>						
					</tr>
					<tr>
						<td colspan=4 >
							<table width="100%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
								<tr>
							    	<td><img src="img/mao.gif" width="1" height="1"></td>
							  	</tr>
							</table>
						</td>
					</tr>
					
				
				<input type="hidden" name="txtDtLastMod" value="<%=dtLastmod%>"/>
				<input type="hidden" name="action_type" value="general_update"/>
				<input type="hidden" name="func_flag" value="1044"/>
				<% if(exist){ %>
				<input type="hidden" name="txtName" value="<tbl:write name="OnlyOneObject" property="name" />"/>
				<%}%>
			</lgc:bloop>				
				<table align="center" border="0" cellspacing="0" cellpadding="0">
					<tr>
						  <td><img src="img/button_l.gif" border="0" ></td>
						  <td background="img/button_bg.gif"><a href="javascript:update()"  class="btn12"><%=updateButtonLabel%></a></td>
						  <td><img src="img/button_r.gif" border="0" ></td>
					</tr>
				</table>
				</table>
			</form>
		<td>
	<tr>
</table>