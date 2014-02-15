<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="resultset" prefix="rs" %>
<%@ taglib uri="/WEB-INF/oss.tld" prefix="d" %>
                 
<%@ page import="com.dtv.oss.util.Postern,java.sql.Timestamp, java.util.*,java.math.BigDecimal,java.text.DecimalFormat" %>
<%@ page import="com.dtv.oss.dto.wrap.customer.RealIncomeAndFeeCountWrap" %>
<%@ page import="com.dtv.oss.web.util.WebUtil" %>
<%@ page import="com.dtv.oss.web.util.CommonKeys" %>
<%@ page import="com.dtv.oss.web.util.WebOperationUtil" %>
<%@ page import="com.dtv.oss.service.commandresponse.QueryCommandResponseImpl"%>
<%@ page import="com.dtv.oss.dto.AccountItemDTO"%>
<%@ page import="com.dtv.oss.dto.CustomerDTO"%>
<%@ page import="com.dtv.oss.dto.AddressDTO"%>

<script language=javascript>
function query_submit(){
	if(checkData()){
		document.getElementById('search').disabled=true;
		if (document.getElementById('print_1') !=null){
		    document.getElementById('print_1').disabled=true;
		}
		if (document.getElementById('export') !=null){
		    document.getElementById('export').disabled=true;
	  }
		document.frmPost.txtActionType.value ="0";
		document.frmPost.action ="real_income_query.do";
		document.frmPost.submit();
  }
}
function print_submit(){
	 document.frmPost.target="_blank";
	 document.frmPost.txtActionType.value ="1";
	 document.frmPost.action ="real_income_print.do";
	 document.frmPost.submit();  	
   document.frmPost.target="_self";
}
function out_submit(){
	 var tempValue ="";
	 tempValue ="&txtActionType=2";
	 var txtCreateStartDatePart =trim(document.frmPost.txtCreateStartDatePart.value);
	 if (txtCreateStartDatePart !='') tempValue =tempValue+"&txtCreateStartDatePart="+txtCreateStartDatePart;
	 var txtEndDatePart =trim(document.frmPost.txtEndDatePart.value);
	 if (txtEndDatePart !='') tempValue =tempValue+"&txtEndDatePart="+txtEndDatePart;
	 
	 var txtCreateStartHourPart =trim(document.frmPost.txtCreateStartHourPart.value);
	 if (txtCreateStartHourPart !='') tempValue =tempValue+"&txtCreateStartHourPart="+txtCreateStartHourPart;
	 var txtEndHourPart =trim(document.frmPost.txtEndHourPart.value);
	 if (txtEndHourPart !='') tempValue =tempValue+"&txtEndHourPart="+txtEndHourPart;
	 
	 var txtCustID =trim(document.frmPost.txtCustID.value);
	 if (txtCustID !='') tempValue =tempValue+"&txtCustID="+txtCustID;
	 var txtAcctID =trim(document.frmPost.txtAcctID.value);
	 if (txtAcctID !='') tempValue =tempValue+"&txtAcctID="+txtAcctID;
	 var txtFeeType =trim(document.frmPost.txtFeeType.value);
	 if (txtFeeType !='') tempValue =tempValue+"&txtFeeType="+txtFeeType;
	 var txtAcctItemTypeID =trim(document.frmPost.txtAcctItemTypeID.value);
	 if (txtAcctItemTypeID !='') tempValue =tempValue+"&txtAcctItemTypeID="+txtAcctItemTypeID;
	 var txtOrgID =trim(document.frmPost.txtOrgID.value);
	 if (txtOrgID !='') tempValue =tempValue+"&txtOrgID="+txtOrgID;
	 var txtOrgDesc =trim(document.frmPost.txtOrgDesc.value);
	 if (txtOrgDesc !='') tempValue =tempValue+"&txtOrgDesc="+txtOrgDesc;
	 var txtCollectorID =trim(document.frmPost.txtCollectorID.value);
	 if (txtCollectorID !='') tempValue =tempValue+"&txtCollectorID="+txtCollectorID; 
	 var txtCollectorName =trim(document.frmPost.txtCollectorName.value);
	 if (txtCollectorName !='') tempValue =tempValue+"&txtCollectorName="+txtCollectorName;
	 var txtCustomerType =trim(document.frmPost.txtCustomerType.value);
	 if (txtCustomerType !='') tempValue =tempValue+"&txtCustomerType="+txtCustomerType;
	 var txtDistrictID =trim(document.frmPost.txtDistrictID.value);
	 if (txtDistrictID !='') tempValue =tempValue+"&txtDistrictID="+txtDistrictID;
	 var txtCountyDesc =trim(document.frmPost.txtCountyDesc.value);
	 if (txtCountyDesc !='') tempValue =tempValue+"&txtCountyDesc="+txtCountyDesc;
	 var txtTicketNo =trim(document.frmPost.txtTicketNo.value);
	 if (txtTicketNo !='') tempValue =tempValue+"&txtTicketNo="+txtTicketNo;
	 
	 window.open('real_income_excel.do?pageCode=real_income_excel'+tempValue,'',
                 'toolbar=no,location=no,status=no,menubar=no,scrollbars=yes,width=430,height=350');		 
}

function checkData(){
	if (document.frmPost.txtCreateStartDatePart.value != ''){
		 if (!check_TenDate(document.frmPost.txtCreateStartDatePart, true, "开始日期")){
			 return false;
		 }
	}
	 
	if (document.frmPost.txtEndDatePart.value != ''){
		if (!check_TenDate(document.frmPost.txtEndDatePart, true, "结束日期")){
			return false;
		}
	}
	 
	if(!compareDate(document.frmPost.txtCreateStartDatePart,document.frmPost.txtEndDatePart,"结束日期必须大于等于开始日期")){
		  return false;
	}
  if(!checkPlainNum(document.frmPost.txtCustID,true,9,"客户证号"))
		return false;
	if(!checkPlainNum(document.frmPost.txtAcctID,true,9,"帐户号"))
		return false;
	return true;
}
function ChangeFeeType()
{
   document.FrameUS.submit_update_select('feetype', document.frmPost.txtFeeType.value, 'txtAcctItemTypeID', '');
}
function clear_select(id,name){
	var idobj=eval("document.frmPost."+id);
	idobj.value="";
	var nameobj =eval("document.frmPost."+name);
	nameobj.value="";
}
function view_detail_click(strId){
	self.location.href="customer_view.do?txtCustomerID="+strId;
}
</script>
<%
	Map allFeeTypeMap=Postern.getAllFeeType();
	pageContext.setAttribute("ACCTITEMTYPEMAP_STORE_100",allFeeTypeMap);
%>
<div id="updselwaitlayer" style="position:absolute; left:650px; top:150px; width:250px; height:24px; display:none">
	<table width="98%" border="0" cellspacing="1" cellpadding="3">
		<tr>
			<td width="100%" align="center">
				<font size="2"> 正在获取内容。。。 </font>
			</td>
		</tr>
	</table>
</div>

<iframe SRC="update_select.screen" name="FrameUS" width="0" height="0" frameborder="0" scrolling="0">
</iframe>
<form name="frmPost" method="post" action="real_income_query.do">

<input type="hidden" name="txtFrom" size="20" value="1">
<input type="hidden" name="txtTo" size="20" value="10">

<input type="hidden" name="txtActionType" size="20" value="0">

<table  border="0" align="center" cellpadding="0" cellspacing="10" >
  <tr>
    <td><img src="img/list_tit.gif" width="19" height="19"></td>
    <td class="title">实收明细及分类合计</td>
  </tr>
</table>
<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>
<br>
<table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
  <tr>
    <td class="list_bg2" align="right" width="17%">客户证号</td>
    <td class="list_bg1" align="left" width="33%">
      <input name="txtCustID" type="text" class="text" maxlength="9" size="25" value="<tbl:writeparam name="txtCustID" />">
    </td>
    <td class="list_bg2" align="right" width="17%">帐户号</td>
    <td class="list_bg1" align="left" width="33%">
       <input name="txtAcctID" type="text" class="text" maxlength="9" size="25" value="<tbl:writeparam name="txtAcctID" />">
    </td>
  </tr>
  <tr>
    <td class="list_bg2" align="right">费用类型</td>
    <td class="list_bg1" align="left">
       <d:selcmn name="txtFeeType" mapName="SET_F_BRFEETYPE"  match="txtFeeType" onchange="ChangeFeeType()" width="23" />
    </td>
    <td class="list_bg2" align="right">帐目类型</td>
    <td class="list_bg1" align="left">
       <d:selcmn name="txtAcctItemTypeID" set="ACCTITEMTYPEMAP_STORE_100"  match="txtAcctItemTypeID"  width="23" />
    </td>
  </tr>
  <tr>
    <td class="list_bg2" align="right">收费机构</td>
    <td class="list_bg1" align="left">
	     <input type="hidden" name="txtOrgID" value="<tbl:writeparam name="txtOrgID" />">
		   <input type="text" name="txtOrgDesc" size="25" maxlength="50" readonly value="<tbl:WriteOrganizationInfo property="txtOrgID" />" class="text">
			 <input name="selOrgButton" type="button" class="button" value="选择" onClick="javascript:sel_organization('C,B,O,S','txtOrgID','txtOrgDesc')">
	  </td>	
    <td class="list_bg2" align="right">收费人</td>
    <td class="list_bg1" align="left">
		<input type="hidden" name="txtCollectorID" size="25" value="<tbl:writeparam name="txtCollectorID"/>" >
		<input type="text" name="txtCollectorName" readonly size="24" value="<tbl:writeparam name="txtCollectorName"/>" >
		<input name="selOperButton" type="button" class="button" value="查询" 
		onClick="javascript:query_window('收费人查询','txtCollectorID','txtCollectorName','query_operator.do')">
		<input name="selClearButton" type="button" class="button" value="清除" 
		onClick="javascript:clear_select('txtCollectorID','txtCollectorName')">
    </td>
  </tr>
  <tr>
    <td class="list_bg2" align="right" width="17%">客户类型</td>
    <td class="list_bg1" align="left"  width="33%">
    	<d:selcmn name="txtCustomerType" mapName="SET_C_CUSTOMERTYPE" match="txtCustomerType" width="23" />
    </td>
    <td class="list_bg2" align="right" width="17%">客户所属区域</td>
    <td class="list_bg1" align="left"  width="33%">
    	<input type="hidden" name="txtDistrictID" value="<tbl:writeparam name="txtDistrictID" />">
	    <input type="text" name="txtCountyDesc" size="25" maxlength="50" readonly value="<tbl:WriteDistrictInfo property="txtDistrictID" />" class="text">
	    <input name="selDistButton" type="button" class="button" value="选择" onClick="javascript:sel_district('all','txtDistrictID','txtCountyDesc')">
    </td>
  </tr>
  <tr>
    <td class="list_bg2" align="right">收费时间</td>
    <td class="list_bg1" colspan="3">
      <d:datetime name="txtCreateStart" size="10" match="txtCreateStart" includeHour="true" onclick="calendar(document.frmPost.txtCreateStartDatePart)" picURL="img/calendar.gif" style="cursor:hand" />       
      ---
      <d:datetime name="txtEnd" size="10" match="txtEnd" includeHour="true" onclick="calendar(document.frmPost.txtEndDatePart)" picURL="img/calendar.gif" style="cursor:hand" />           
  </tr>
  <tr>
  	<td class="list_bg2" align="right">票据编号</td>
  	<td class="list_bg1" colspan="3">
  		<input name="txtTicketNo" type="text" class="text"  size="25" value="<tbl:writeparam name="txtTicketNo" />">
    </td>
  </tr>
</table>  
<table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
	<tr align="center">
	  <td class="list_bg1"><table  border="0" cellspacing="0" cellpadding="0">
		  <tr>
			<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
			<td><input name="search" type="button" class="button" value="查&nbsp;询" onclick="javascript:query_submit()"></td>
			<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
	 <%
	     Collection returnList =null;
			 QueryCommandResponseImpl RepCmd = (QueryCommandResponseImpl)pageContext.getRequest().getAttribute("ResponseQueryResult");
			 if (RepCmd !=null) returnList = (Collection) RepCmd.getPayload();
			 if (returnList !=null && !returnList.isEmpty()){
	 %>
			<td width="22">&nbsp;&nbsp;</td>
			<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
			<td><input name="print_1" type="button" class="button" value="打&nbsp;印" onclick="javascript:print_submit()"></td>
			<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
			<td width="22">&nbsp;&nbsp;</td>
			<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
			<td><input name="export" type="button" class="button" value="导出明细" onclick="javascript:out_submit()"></td>
			<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
	<%
	     }
	%>
		  </tr>
	  </table></td>
	</tr>
</table> 

<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>
<br>

<rs:hasresultset>
<table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
  <tr class="list_head">
    <td class="list_head" width="10%" nowrap>客户姓名</td>
    <td class="list_head" width="10%" nowrap>客户证号</td>
    <td class="list_head" width="10%" nowrap>所属区域</td>
    <td class="list_head" width="10%" nowrap>详细地址</td>
    <td class="list_head" width="10%" nowrap>帐号</td>
    <td class="list_head" width="10%" nowrap>创建时间</td>
    <td class="list_head" width="15%" nowrap>费用类型</td>
    <td class="list_head" width="15%">帐目类型</td>
    <td class="list_head" width="10%" nowrap>金额</td>
  </tr>

<% 
   Double fl=(Double)RepCmd.getExtraPayLoad();
   double totalAmount=0;
   if (fl!=null) totalAmount=fl.doubleValue();
   DecimalFormat to= new DecimalFormat("0.00");
%>
   
<lgc:bloop requestObjectName="ResponseQueryResult" item="oneline" >
<%
    Collection resultCols = (Collection)pageContext.getAttribute("oneline");
    Iterator resultIter =resultCols.iterator();
    while (resultIter.hasNext()) { 
      resultIter.next();
      Object custName = resultIter.next();
      Object cusId  =resultIter.next();
%>
<tbl:printcsstr style1="list_bg1" style2="list_bg2" overStyle="list_over" >
    <td align="center"><%=custName%></td>  
    <td align="center"><a href="javascript:view_detail_click('<%=cusId%>')" class="link12" ><%=cusId%></a></td>
    <td align="center"><%=resultIter.next()%></td>
    <td align="center"><%=resultIter.next()%></td>
    <td align="center"><%=resultIter.next()%></td>
    <%
      Timestamp createTime = (Timestamp)resultIter.next();
    %>
    <td align="center"><%=WebUtil.TimestampToString(createTime, "yyyy-MM-dd")%><br><%=WebUtil.TimestampToString(createTime, "HH:mm:ss")%></td>
    <td align="center"><%=resultIter.next()%></td>
    <td align="center"><%=resultIter.next()%></td>
    <td align="center"><%=resultIter.next()%></td>
</tbl:printcsstr>
 <%
    }
 %>
</lgc:bloop>
  <tr>
    <tbl:printcsstr style1="list_bg1" style2="list_bg2" overStyle="list_over" >
    	<td colspan="9">总计：<%=to.format(totalAmount) %></td>
    </tbl:printcsstr>
  </tr>
  <tr>
    <td colspan="9" class="list_foot"></td>
  </tr>
</table>
<% Map  map=RepCmd.getMap();
if(map!=null && !map.isEmpty()){
%>
<table  width="98%" border="0" align="center" cellpadding="0" cellspacing="11">
 <tr class="list_head">
    <td class="list_head" nowrap  colspan="6">查询分类合计</td>
  </tr>
  <tr class="list_head">
    <td class="list_head" align="center"  width="25%">帐目类型</td>
    <td class="list_head" align="center" width="5%">笔数</td>
    <td class="list_head" align="center" width="20%">合计</td>
    <td class="list_head" align="center"  width="25%">帐目类型</td>
    <td class="list_head" align="center" width="5%">笔数</td>
    <td class="list_head" align="center" width="20%">合计</td>
  </tr>
  
  <%Iterator mapIterator=map.keySet().iterator();
    String key="";
    RealIncomeAndFeeCountWrap value=null;
	while(mapIterator.hasNext()){
		key=(String)mapIterator.next();
		value=(RealIncomeAndFeeCountWrap)map.get(key);
  %>
  <tr class="list_head">
    <td class="list_bg2" align="right" ><%=key%></td>
    <td class="list_bg1" align="center"  ><%=value.getRecordCount()%> </td>
	<td class="list_bg1" align="left"  ><%=value.getValue()%> </td>
    <%if(mapIterator.hasNext()){
        key=(String)mapIterator.next();
		value=(RealIncomeAndFeeCountWrap)map.get(key);
    %>
    <td class="list_bg2" align="right" ><%=key%></td>
	<td class="list_bg1" align="center"  ><%=value.getRecordCount()%> </td>
	<td class="list_bg1" align="left"  ><%=value.getValue()%> </td>
	<%}else{%>
	<td class="list_bg2" align="right" ><br></td>
	<td class="list_bg1" ><br></td>
	<td class="list_bg1"><br></td>
	<%}%>
  </tr>
  <%}%>
    <tr>
    <td colspan="6" class="list_foot"></td>
  </tr>
</table>
<%}%>
<table  border="0" align="right" cellpadding="0" cellspacing="11">
  <tr>
    <td>第<span class="en_8pt"><rs:prop property="curpageno" /></span>页<span class="en_8pt">/</span>共<span class="en_8pt"><rs:prop property="pageamount" /><span>页</td>
    <td>共<span class="en_8pt"><rs:prop property="recordcount" /></span>条记录</td>
<rs:notfirst>
    <td align="right"><img src="img/dot_top.gif" width="17" height="11"></td>
    <td><a href="javascript:firstPage_onClick(document.frmPost, <rs:prop property="firstto" />)" class="link12">首页</a></td>
    <td align="right"><img src="img/dot_pre.gif" width="6" height="11"></td>
    <td><a href="javascript:previous_onClick(document.frmPost, <rs:prop property="prefrom" />, <rs:prop property="preto" />)" class="link12">上一页</a></td>
</rs:notfirst>
<rs:notlast>
    <td align="right"><img src="img/dot_next.gif" width="6" height="11"></td>
    <td><a href="javascript:next_onClick(document.frmPost, <rs:prop property="nextfrom" />, <rs:prop property="nextto" />)" class="link12">下一页</a></td>
    <td align="right"><img src="img/dot_end.gif" width="17" height="11"></td>
    <td><a href="javascript:lastPage_onClick(document.frmPost, <rs:prop property="lastfrom" />, <rs:prop property="lastto" />)" class="link12">末页</a></td>
</rs:notlast>
    <td align="right">跳转到</td>
    <td><input name="txtPage" type="text" class="page_txt"></td>
    <td>页</td>
    <td><input name="imageField" type="image" src="img/button_go.gif" width="28" height="15" border="0" onclick="javascript:goto_onClick(document.frmPost, <rs:prop property="pageamount" />)"></td>
  </tr>
  </table>
  
 </rs:hasresultset>                 

</form>  
         

      