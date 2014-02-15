<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="resultset" prefix="rs" %>
<%@ taglib uri="/WEB-INF/oss.tld" prefix="d" %>

<%@ page import="com.dtv.oss.util.Postern,
                 java.util.HashMap,
                 java.util.Map,
                 java.util.Iterator" %>
<%@ page import="com.dtv.oss.dto.MethodOfPaymentDTO" %>
<script language=javascript>
function check_form(){
   if (document.frmPost.txtCreateStartDate.value != ''){
		if (!check_TenDate(document.frmPost.txtCreateStartDate, true, "开始日期")){
			return false;
		}
	}
	if (document.frmPost.txtCreateEndDatevalue != ''){
		if (!check_TenDate(document.frmPost.txtCreateEndDate, true, "结束日期")){
			return false;
		}
	}
	if(!compareDate(document.frmPost.txtCreateStartDate,document.frmPost.txtCreateEndDate,"结束日期必须大于等于开始日期")){
		
		return false;
	}
	   if (document.frmPost.txtCancleStartDate.value != ''){
		if (!check_TenDate(document.frmPost.txtCancleStartDate, true, "开始日期")){
			return false;
		}
	}
	if (document.frmPost.txtCancleEndDatevalue != ''){
		if (!check_TenDate(document.frmPost.txtCancleEndDate, true, "结束日期")){
			return false;
		}
	}
	if(!compareDate(document.frmPost.txtCancleStartDate,document.frmPost.txtCancleEndDate,"结束日期必须大于等于开始日期")){
		
		return false;
	}
	if(!checkPlainNum(document.frmPost.txtCustomerID,true,9,"客户证号"))
		return false;
	return true;
}

function query_submit(){
        if (check_form()) document.frmPost.submit();
}

function view_detail_click(strId){
	self.location.href="customer_view.do?txtCustomerID="+strId+"&txtStatus=C";
}

function back_query(){
	self.location.href="menu_customer_query.do";
}

function ChangeOpenSourceType(){
    document.FrameUS.submit_update_select('osttoid', document.frmPost.txtOpenSourceType.value, 'txtOpenSourceID', '');
}

function form_Reset(){
	var els=document.frmPost.elements;
	for( i=0;i<els.length;i++){
		var control=els[i];
		if(control.type!='button' && control.type!='submit'&&control.name!='txtTo'&&control.name!='txtFrom'&&control.name!='txtStatus')
			els[i].value="";
	}
}


</script>

<div id="updselwaitlayer" style="position:absolute; left:650px; top:170px; width:250px; height:24px; display:none">
    <table width="100%" border="0" cellspacing="1" cellpadding="3">
        <tr>
          <td width="100%"><div align="center">
          <font size="2">
          正在获取内容。。。
          </font>
          </td>
        </tr>
    </table>
</div>
<TABLE border="0" cellspacing="0" cellpadding="0" width="820" >
<TR>
	<TD>

<iframe SRC="update_select.screen" name="FrameUS" width="0" height="0" frameborder="0" scrolling="0"  >
</iframe>
<table  border="0" align="center" cellpadding="0" cellspacing="10" >
  <tr>
    <td><img src="img/list_tit.gif" width="19" height="19"></td>
    <td class="title">已销户客户查询</td>
  </tr>
</table>
<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>
<br>
<form name="frmPost" action="customer_cancle_query.do" method="post" >    
<table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
  <tr>
    <td class="list_bg2" width=17% align="right">客户姓名</td>
    <td class="list_bg1" width=33% align="left"><input name="txtName" type="text" class="text" maxlength="20" size="22" value="<tbl:writeparam name="txtName" />"></td>
    <td class="list_bg2" width=17% align="right">客户证号</td>
    <td class="list_bg1" width=33% align="left"><input name="txtCustomerID" type="text" class="text" maxlength="9" size="22" value="<tbl:writeparam name="txtCustomerID" />">
	  &nbsp;&nbsp;<A href="javascript:drawSubMenu('1')"><IMG id="arr1" alt="展开下级查询条件" src="img/icon_bottom.gif" border=0></A></td>
  </tr>
</table>
<table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg" id="mnu1" style="display:none">
  <tr>
    <td class="list_bg2" width=17% align="right">客户类型</td>
    <td class="list_bg1" width=33% align="left"><d:selcmn name="txtCustomerType" mapName="SET_C_CUSTOMERTYPE" match="txtCustomerType" width="20" /></td>
    <td class="list_bg2" width=17% align="right">&nbsp;<%=Postern.getFieldNameByFieldInterID("CUSTOMER_CATVID")%></td>
    <td class="list_bg1" width=33% ><font size="2">
    <input type="text" name="txtCatvID" size="22" maxlength="20" value="<tbl:writeparam name="txtCatvID" />" class="text"> </font>
	</td>
  </tr>    
    <input name="txtStatus" value="C" type="hidden" />
  </tr>
  <tr>
    <td class="list_bg2" width="17%" align="right">证件号</td>
    <td class="list_bg1" width="33%" align="left">
    	  <input name="txtCardID" type="text" class="text" size="22"  maxlength="25" value="<tbl:writeparam name="txtCardID" />">
    </td>
    <td class="list_bg2" width="17%" align="right">社保卡编号</td>
    <td class="list_bg1" width="33%" align="left"><input name="txtSocialSecCardID" type="text" class="text" size="22" maxlength="25" value="<tbl:writeparam name="txtSocialSecCardID" />"></td>
  </tr>
  <tr>
    <td class="list_bg2" align="right">固定电话</td>
    <td class="list_bg1" align="left"><input name="txtTelephone" type="text" class="text" maxlength="50" size="22" value="<tbl:writeparam name="txtTelephone" />"></td>
    <td class="list_bg2" align="right">移动电话</td>
    <td class="list_bg1" align="left"><input name="txtTelephoneMobile" type="text" class="text" maxlength="50" size="22" value="<tbl:writeparam name="txtTelephoneMobile" />"></td>
  </tr>
  <tr>
    <td class="list_bg2" align="right">来源渠道</td>
    <td class="list_bg1" align="left"><d:selcmn name="txtOpenSourceType" match="txtOpenSourceType" mapName="SET_C_CUSTOPENSOURCETYPE" width="20" onchange="ChangeOpenSourceType()" /></td>
    <td class="list_bg2" align="right">安装地址</td>
    <td class="list_bg1" align="left"><input name="txtDetailAddress" type="text" class="text" maxlength="100" size="22"  value="<tbl:writeparam name="txtDetailAddress" />"></td>
  </tr>
			
  <%
    
    Map mapBankMop=Postern.getAllMethodOfPayments();
    Iterator itBank = mapBankMop.entrySet().iterator();
    Map mapMop=new HashMap();
    MethodOfPaymentDTO dtoMOP=null;
     
    while (itBank.hasNext())
    {
        Map.Entry BankItem = (Map.Entry)itBank.next();
        dtoMOP =(MethodOfPaymentDTO)BankItem.getValue();
        mapMop.put(BankItem.getKey(), dtoMOP.getName());
    }  
    
    //pageContext.setAttribute("AllMOPList" , mapMop); 
    pageContext.setAttribute("AllMOPList" , Postern.getOpeningMop()); 

%>                      
  <tr>
      <td class="list_bg2" align="right">所属组织</td>
    <td class="list_bg1">
    	<input type="hidden" name="txtOrgID" value="<tbl:writeparam name="txtOrgID" />">
	    <input type="text" name="txtOrgDesc" size="22" maxlength="50" readonly value="<tbl:WriteOrganizationInfo property="txtOrgID" />" class="text">
	    <input name="selOrgButton" type="button" class="button" value="选择" onClick="javascript:sel_organization('C,B,O,S','txtOrgID','txtOrgDesc')">
    </td>	
    <td class="list_bg2" align="right">所属区域</td>
    <td class="list_bg1" align="left">
    	<input type="hidden" name="txtDistrictID" value="<tbl:writeparam name="txtDistrictID" />">
	    <input type="text" name="txtCountyDesc" size="22" maxlength="50" readonly value="<tbl:WriteDistrictInfo property="txtDistrictID" />" class="text">
	    <input name="selDistButton" type="button" class="button" value="选择" onClick="javascript:sel_district('all','txtDistrictID','txtCountyDesc')">
    </td>
  </tr>
  <tr>
    <td class="list_bg2" align="right">创建日期</td>
    <td class="list_bg1" align="left"><d:datetime name="txtCreateStartDate" size="10" myClass="text" match="txtCreateStartDate"  onclick="MyCalendar.SetDate(this,document.frmPost.txtCreateStartDate,'Y')" picURL="img/calendar.gif" style="cursor:hand" />
             -
             <d:datetime name="txtCreateEndDate" size="10" myClass="text" match="txtCreateEndDate"  onclick="MyCalendar.SetDate(this,document.frmPost.txtCreateEndDate,'Y')" picURL="img/calendar.gif" style="cursor:hand" />
		</td>
	<td class="list_bg2" align="right">销户日期</td>
    <td class="list_bg1" align="left"><d:datetime name="txtCancleStartDate" size="10" myClass="text" match="txtCancleStartDate"  onclick="MyCalendar.SetDate(this,document.frmPost.txtCancleStartDate,'Y')" picURL="img/calendar.gif" style="cursor:hand" />
             -
        <d:datetime name="txtCancleEndDate" size="10" myClass="text" match="txtCancleEndDate"  onclick="MyCalendar.SetDate(this,document.frmPost.txtCancleEndDate,'Y')" picURL="img/calendar.gif" style="cursor:hand" />
	</td>
		 
  </tr>
  </table>
  
	<table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
		<tr align="center">
		  <td class="list_bg1">
		  <table  border="0" cellspacing="0" cellpadding="0">
			  <tr>
				<td width="11" height="20"><img src="img/button2_r.gif" width="22" height="20"></td>
				<td><input name="Reset" type="button" class="button" value="重 置" onclick="javascript:form_Reset()" ></td>
				<td width="22" height="20"><img src="img/button2_l.gif" width="11" height="20"></td>
				<td width="20" ></td>
				<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
				<td><input name="Submit" type="button" class="button" value="查 询" onclick="javascript:query_submit()"></td>
				<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
			  </tr>
	  	</table></td>
		</tr>
	</table>      
	  <input type="hidden" name="txtFrom" size="22" value="1">
      <input type="hidden" name="txtTo" size="22" value="10">
<br>      
<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>
<rs:hasresultset>
<br>
<table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
  <tr class="list_head">
    <td class="list_head">客户证号</td>
    <td class="list_head">姓名</td>
    <td class="list_head">类型</td>
    <td class="list_head">证件号</td>
    <td class="list_head">所属分区</td>
    <td class="list_head">地址</td>
    <td class="list_head">创建日期</td>
    <td class="list_head">状态</td>
  </tr>
<lgc:bloop requestObjectName="ResponseQueryResult" item="oneline" >
<%
    com.dtv.oss.dto.wrap.customer.Customer2AddressWrap wrap = (com.dtv.oss.dto.wrap.customer.Customer2AddressWrap)pageContext.getAttribute("oneline");
    pageContext.setAttribute("oneline", wrap.getCustDto());
    pageContext.setAttribute("custaddr",  wrap.getAddrDto());
%>
<tbl:printcsstr style1="list_bg1" style2="list_bg2" overStyle="list_over" >
    <td><a href="javascript:view_detail_click('<tbl:write name="oneline" property="customerID" />')" class="link12" ><tbl:write name="oneline" property="customerID" /></a></td>
    <td><tbl:write name="oneline" property="name" /></td>
    <td><d:getcmnname typeName="SET_C_CUSTOMERTYPE" match="oneline:customerType" /></td>
    <td><tbl:write name="oneline" property="CardID" /></td>
    <td><tbl:WriteDistrictInfo name="custaddr" property="districtID" /></td>
    <td><tbl:write name="custaddr" property="detailAddress" /></td>
    <td><tbl:writedate name="oneline" property="CreateTime" /></td>
    <td><d:getcmnname typeName="SET_C_CUSTOMERSTATUS" match="oneline:status" /></td>
</tbl:printcsstr>
</lgc:bloop>

  <tr>
    <td colspan="8" class="list_foot"></td>
  </tr>
</table>
<table  border="0" align="right" cellpadding="0" cellspacing="8">
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
    <td><input name="imageField" type="image" src="img/button_go.gif" width="28" height="15" border="0" onclick="return goto_onClick(document.frmPost, <rs:prop property="pageamount" />)"/></td>
  </tr>
</table>

</rs:hasresultset>                  
</TD>
</TR>
</TABLE>
</form> 
