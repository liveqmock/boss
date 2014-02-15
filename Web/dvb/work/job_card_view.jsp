<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="resultset" prefix="rs" %>
<%@ taglib uri="osstags" prefix="o" %>
<%@ taglib uri="bookmark" prefix="bk" %>
<%@ taglib uri="privilege" prefix="pri" %>
<%@ page import="com.dtv.oss.web.util.CurrentLogonOperator"%>
<%@ page import="com.dtv.oss.dto.JobCardDTO"%>
<%@ page import="com.dtv.oss.dto.JobCardProcessDTO"%>
<%@ page import="com.dtv.oss.dto.AddressDTO"%>
<%@ page import="com.dtv.oss.util.Postern"%>
<%@ page import="com.dtv.oss.web.util.WebOperationUtil"%>
<%@ page import="com.dtv.oss.web.util.WebUtil"%>
<script language=javascript>
 function historyRecord(){
        var txtJobCardID =document.frmPost.txtJobCardID.value;
       
        document.frmPost.action ="job_card_process_log.do?txtJobCardID="+txtJobCardID;
        document.frmPost.submit();
}
function repair_contact(){
	var strId=document.frmPost.txtJobCardID.value ;
	self.location.href="contactrep_view.do?txtJobCardID="+strId;
}
function repair_register(){
	var strId=document.frmPost.txtJobCardID.value ;
	self.location.href="register_repair_info_view.do?txtJobCardID="+strId;
}
function repair_close(){
	var strId=document.frmPost.txtJobCardID.value ;
	self.location.href="close_repair_info_view.do?txtJobCardID="+strId+"&txtType="+"R"+"&txtStatus="+"F";
}
function install_contact(strId){
	self.location.href="job_card_view_for_contact_of_install.do?txtJobCardID="+strId;
}
function install_recontact(strId){
	self.location.href="job_card_view_for_re_contact_of_install.do?txtJobCardID="+strId;
}
function install_register(strId){
	self.location.href="job_card_view_for_register_of_install.do?txtJobCardID="+strId+"&txtStatus="+"B";
}
function install_close(strId){
	self.location.href="ci_view_for_close.do?txtJobCardID="+strId;
}

//模拟
function construction_contact_catv(strId){
	self.location.href="catv_job_card_view_for_contact.do?txtJobCardID="+strId;
}
function construction_register_catv(strId){
	self.location.href="catv_job_card_view_for_register.do?txtJobCardID="+strId+"&txtStatus="+"B";
}
function construction_close_catv(strId){
	self.location.href="catv_view_for_close.do?txtJobCardID="+strId;
}
function huairou_print(jbId,printFlag){
	  document.frmPost.target="_blank";
	  document.frmPost.action="construct_print_for_huairou.do?txtJobId="+jbId+"&txtprintFlag="+printFlag;
	  document.frmPost.submit();
	  document.frmPost.target="_self";
}

function maintain_mtg_print(){
	 var strId=document.frmPost.txtJobCardID.value ;
	 document.frmPost.target="_blank";
	// document.frmPost.action="maintain_print_for_mtg.do?txtJobCardIDList="+strId;
	 document.frmPost.action="customer_print_for_authorize.do?txtpagetype=maintain_Mtg&txtJobcardId="+strId;
	 document.frmPost.submit();
	 document.frmPost.target="_self";
}

function install_mtg_print(){
	 var strId=document.frmPost.txtJobCardID.value ;
	 document.frmPost.target="_blank";
	// document.frmPost.action="install_print_for_mtg.do?txtJobCardId="+strId;
	 document.frmPost.action="customer_print_for_authorize.do?txtpagetype=install_Mtg&txtJobCardId="+strId;
	 document.frmPost.submit();
	 document.frmPost.target="_self";
}

</Script>


<table  border="0" align="center" cellpadding="0" cellspacing="10">
      <tr>
        <td><img src="img/list_tit.gif" width="19" height="19"></td>
        <td class="title">工单信息</td>
      </tr>
    </table>
      <table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
        <tr>
          <td><img src="img/mao.gif" width="1" height="1"></td>
        </tr>
      </table>
         <br>
 
<form name="frmPost" method="post" action="install_info_update.do" >
<lgc:bloop requestObjectName="ResponseQueryResult" item="oneline" >

<%
    com.dtv.oss.dto.wrap.work.JobCard2JobCardProcessWrap   wrap=(com.dtv.oss.dto.wrap.work.JobCard2JobCardProcessWrap)pageContext.getAttribute("oneline");
    pageContext.setAttribute("oneline", wrap.getJobCardDto());
    JobCardDTO jcdto= wrap.getJobCardDto();
    String OpName = WebUtil.NullToString(Postern.getOperatorNameByID(jcdto.getCreateOpID()));
    String strJobType = jcdto.getJobType();
    if (strJobType == null) strJobType = "";
    String strCatvID = jcdto.getCatvID();
    if (strCatvID == null) strCatvID = "";
    String status = jcdto.getStatus();
    String strAddress = Postern.getAddressById(jcdto.getAddressId());
    AddressDTO addrDto = Postern.getAddressDtoByID(jcdto.getAddressId());
    pageContext.setAttribute("custaddr", addrDto);
    AddressDTO oldAddrDto = Postern.getAddressDtoByID(jcdto.getOldAddressId());
    pageContext.setAttribute("oldaddrdto", oldAddrDto);
    if (strAddress == null) strAddress="";
    pageContext.setAttribute("jobcardprocess",  wrap.getJobCardProcessDto());
    JobCardProcessDTO jbcDto= wrap.getJobCardProcessDto();
%>

<table width="810"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
	<tr>
        <td class="list_bg2" width=17% align="right">工单编号</div></td>
        <td class="list_bg1" width=33% align="left">
			<input type="text" name="txtJobCardID"  size="25"  value="<tbl:write name="oneline" property="JobCardId" />" class="textgray" readonly   >
		</td>
		<td class="list_bg2" width=17% align="right"><div align="right">工单类型</div></td>
		<td class="list_bg1" width=33% align="left">
			<input type="text" name="txtType" size="25" value="<o:getcmnname typeName="SET_W_JOBCARDTYPE" match="oneline:JobType"/>" class="textgray" readonly>
			&nbsp;&nbsp;<A href="javascript:drawSubMenu('1')"><IMG id="arr1" alt="展开下级查询条件" src="img/icon_bottom.gif" border=0></A></td>
    	</td>
	</tr>
	<tr>
		<td class="list_bg2" width="17%" align="right"><div align="right" nowrap>工单子类型</div></td>
		<td class="list_bg1" width="33%" align="left">
			<input type="text" name="txtType" size="25" value="<o:getcmnname typeName="SET_W_JOBCARDSUBTYPE" match="oneline:SubType"/>" class="textgray" readonly>
        </td>
        <td class="list_bg2"><div align="right">状态</div></td>
		<td class="list_bg1"> 
		<input type="text" name="txtStatus"  size="25"  value="<o:getcmnname typeName="SET_W_JOBCARDSTATUS" match="oneline:status" />" class="textgray" readonly >
		</td>
	</tr>
	<tr>
		<td class="list_bg2"><div align="right">当前处理部门</div></td>
		<td class="list_bg1" > 
		<input type="text" name="txtProcessOrgId" size="25" value="<tbl:WriteOrganizationInfo name="oneline" property="processOrgId" />" class="textgray" readonly>   
            </td>
        <td class="list_bg2" ><div align="right" nowrap>关联单据类型</div></td>
		<td class="list_bg1"> 
			<input type="text" name="txtType" size="25" value="<o:getcmnname typeName="SET_W_JOBCARDREFERTYPE" match="oneline:ReferType" />" class="textgray" readonly >
		</td> 
     </tr>
     <tr>
		<td class="list_bg2"><div align="right" nowrap>关联单据号</div></td>
        <td class="list_bg1">
		<input type="text" name="txtReferSheetID"  size="25"  value="<tbl:write name="oneline" property="ReferSheetId" />" class="textgray" readonly   >
		</td>
	
		<td class="list_bg2" ><div align="right" nowrap>客户证号</div></td>
		<td class="list_bg1"> 
		<input type="text" name="txtCustID" size="25" class="textgray" readonly value="<tbl:write name="oneline" property="CustId"/>" >
	    </td>
	</tr>
	<tr>
		<td class="list_bg2" ><div align="right" nowrap>业务帐户号</div></td>
		<td class="list_bg1" >
			<input type="text" name="txtType" class="textgray"  size="25" readonly value="<tbl:write name="oneline" property="custServiceAccountId"/>" >
		</td>
		<td class="list_bg2" ><div align="right" nowrap>帐户号</div></td>
		<td class="list_bg1" >
			<input type="text" name="txtType" class="textgray"  size="25" readonly value="<tbl:write name="oneline" property="accountID"/>" >
		</td>
	</tr>
	<tr>
		<td class="list_bg2" ><div align="right">所属区域</div></td>
		<td class="list_bg1">
			<input type="text" name="txtDistrictDesc" size="25" maxlength="50" class="textgray" readonly value="<tbl:WriteDistrictInfo name="custaddr" property="DistrictID" />" class="text">
		</td>
		<td class="list_bg2"><div align="right">联系地址</div></td>
		<td class="list_bg1">
		<input type="text" name="txtDetailAddress"  size="25" class="textgray" readonly value="<%=strAddress%>"></td>
	</tr>
	<tr>
		<td class="list_bg2" ><div align="right">原区域</div></td>
		<td class="list_bg1">
			<input type="text" name="txtOldDistrictDesc" size="25" maxlength="50" class="textgray" readonly value="<tbl:WriteDistrictInfo name="oldaddrdto" property="DistrictID" />" class="text">
		</td>
		<td class="list_bg2"><div align="right">原地址</div></td>
		<td class="list_bg1">
		<input type="text" name="txtOldDetailAddress"  size="25" class="textgray" readonly value="<tbl:write name="oldaddrdto" property="detailAddress"/>"></td>
	</tr>
	<tr>
         <td class="list_bg2" ><div align="right">联系人</div></td>
         <td class="list_bg1">
          <input type="text" name="txtType" class="textgray"  size="25" readonly value="<tbl:write name="oneline" property="ContactPerson"/>" >
         </td>
         <td class="list_bg2"><div align="right">联系电话</div></td>
         <td class="list_bg1">
         <input type="text" name="txtType"  class="textgray" size="25" readonly value="<tbl:write name="oneline" property="ContactPhone"/>" >
         </td>
	</tr>
	<tr>
		<td class="list_bg2" ><div align="right">预约上门时间</div></td>
		<td class="list_bg1"> 
		     <input type="text" name="txtPreferedDate"  size="25"  class="textgray" readonly value="<tbl:writedate name="oneline" property="preferedDate" includeTime="false"/>" >
		</td>
		</td>
		<td class="list_bg2" ><div align="right">预约上门时间段</div></td>
		<td class="list_bg1"> 
			<input type="text" name="txtWorkTime"  size="25" value="<o:getcmnname typeName="SET_C_CSIPREFEREDTIME" match="oneline:preferedTime" />" class="textgray" readonly >
		</td>
	</tr>
	
	<tr>
		<td class="list_bg2"><div align="right" nowrap>终端ID</div></td>
        <td class="list_bg1" colspan=3 >
		<input type="text" name="catvID"  size="25"  value="<tbl:write name="oneline" property="CatvID" />" class="textgray" readonly   >
	</td>
	</tr>
	<tr>
		<td class="list_bg2"><div align="right" nowrap>原有端口数</div></td>
        <td class="list_bg1">
		<input type="text" name="txtReferSheetID"  size="25"  value="<tbl:write name="oneline" property="OldPortNumber" />" class="textgray" readonly   >
		</td> 
        <td class="list_bg2" ><div align="right" nowrap>新增端口数</div></td>
		<td class="list_bg1"> 
			<input type="text" name="txtType" size="25" value="<tbl:write name="oneline" property="AddPortNumber" />" class="textgray" readonly>
                </td>        
	</tr>

	<tr> 
		<%
			if (strJobType.equals("R")){
		%>	       
		<td class="list_bg2" ><div align="right">预约结果</div></td>
		<td class="list_bg1"> 
		        <input type="text" name="txtWorkResult" size="25" class="textgray" readonly value="<o:getcmnname typeName="SET_W_JOBCARDCONTACTRESULT" match="oneline:workResult"/>" >
		</td>
		<% } else if(strJobType.equals("I")){ %>
		<td class="list_bg2" ><div align="right">安装结果</div></td>
		<td class="list_bg1"> 
		        <input type="text" name="txtWorkResult" size="25" class="textgray" readonly value="<o:getcmnname typeName="SET_W_JOBWORKRESULT" match="oneline:workResult"/>" >
		</td>
		<%} else{ %>
		<td class="list_bg2" ><div align="right" nowrap>施工结果</div></td>
		<td class="list_bg1"> 
		        <input type="text" name="txtWorkResult" size="25" class="textgray" readonly value="<o:getcmnname typeName="SET_W_JOBWORKRESULT" match="oneline:workResult"/>" >
	    </td>
		<%}%>
		<td class="list_bg2" ><div align="right">处理人员</div></td>
		<td class="list_bg1"> 
			<input type="text" name="txtProcessMan" size="25" class="textgray"  readonly value="<tbl:write name="jobcardprocess" property="ProcessMan" />"   >
		 </td>
	</tr>
	
	
	<%
	if (strJobType.equals("I")){
	if("S".equals(status)){
	%>
	<tr>
		<td class="list_bg2" ><div align="right">安装成功描述</div></td>
		<td class="list_bg1" colspan="3"> 
			<input type="text" name="txtType" size="83"  class="textgray" readonly value="<tbl:write name="jobcardprocess" property="Memo"/>" >
		</td>
	</tr>
	<%} else if(!("W".equals(status)||"B".equals(status))){
	%>
	<tr>
		<td class="list_bg2" ><div align="right">安装失败原因</div></td>
		<td class="list_bg1"> 
		<input type="text" name="txtWorkResultReason" size="25" value="<o:getcmnname typeName="SET_W_INSTALLFAILREASON" match="oneline:WorkResultReason"/>" class="textgray" readonly >
		</td>
		<td class="list_bg2"><div align="right">安装失败描述</div></td>
		<td class="list_bg1"> 
			<input type="text" name="txtType" size="25"  class="textgray" readonly value="<tbl:write name="jobcardprocess" property="Memo"/>" >
		 </td>
	</tr>
	<%}
	} 
	
	if (strJobType.equals("R")){
	if("S".equals(status)){
	%>
	<tr>
		<td class="list_bg2" ><div align="right">故障类型</div></td>
		<td class="list_bg1"> 
		        <input type="text" name="txtTroubleType"   size="25"  class="textgray" readonly value="<o:getcmnname typeName="SET_W_JOBCARDTROUBLETYPE" match="oneline:TroubleType"/>" >
	        </td>	        
	        <td class="list_bg2" ><div align="right">故障原因</div></td>
		<td class="list_bg1"> 
		        <input type="text" name="txtTroubleSubType"   size="25"  class="textgray" readonly value="<o:getcmnname typeName="SET_W_JOBCARDTROUBLESUBTYPE" match="oneline:troubleSubType"/>" >
	        </td>
	</tr>
	<tr>
		<td class="list_bg2" ><div align="right">解决手段</div></td>
		<td class="list_bg1" colspan="3"> 
		        <input type="text" name="txtResolutionType"   size="25"  class="textgray" readonly value="<o:getcmnname typeName="SET_W_JOBCARDRESOLUTIONTYPE" match="oneline:resolutionType"/>" >
	        </td>	        	        
	</tr>
	 <tr>
		<td class="list_bg2" ><div align="right">维修成功描述</div></td>
		<td class="list_bg1" colspan="3"> 
		<input type="text" name="txtType" size="83"  class="textgray" readonly value="<tbl:write name="jobcardprocess" property="Memo"/>" >
	        </td>
	</tr>
	<%} 
	 else if(!("W".equals(status)||"B".equals(status))){
	%>
	<tr>
		<td class="list_bg2" ><div align="right">维修失败原因</div></td>
		<td class="list_bg1"> 
		<input type="text" name="txtWorkResultReason" size="25" value="<o:getcmnname typeName="SET_W_REPAIRFAILREASON" match="oneline:WorkResultReason"/>" class="textgray" readonly >
		</td>
		<td class="list_bg2"><div align="right">维修失败描述</div></td>
		<td class="list_bg1"> 
			<input type="text" name="txtType" size="25"  class="textgray" readonly value="<tbl:write name="jobcardprocess" property="Memo"/>" >
		 </td>
		 
	</tr>
	<%}}
	
	if (strJobType.equals("C")){
	if("S".equals(status)){
	%>
	<tr>
		<td class="list_bg2" ><div align="right">施工成功描述</div></td>
		<td class="list_bg1" colspan="3"> 
			<input type="text" name="txtType" size="83"  class="textgray" readonly value="<tbl:write name="jobcardprocess" property="Memo"/>" >
		</td>
	</tr>
	<%} else if(!("W".equals(status)||"B".equals(status))){
	%>
	<tr>
		<td class="list_bg2" ><div align="right">施工失败原因</div></td>
		<td class="list_bg1"> 
			<input type="text" name="txtWorkResultReason" size="25" value="<o:getcmnname typeName="SET_W_INSTALLFAILREASON" match="oneline:WorkResultReason"/>" class="textgray" readonly >
		</td>
		<td class="list_bg2"><div align="right">施工失败描述</div></td>
		<td class="list_bg1"> 
			<input type="text" name="txtType" size="25"  class="textgray" readonly value="<tbl:write name="jobcardprocess" property="Memo"/>" >
		 </td>
	</tr>
	<%}}%>
	
	<tr>
	    <td class="list_bg2" ><div align="right">逾期原因描述</div></td>
      <td class="list_bg1"> 
          <input type="text" name="txtOutofDateReason" size="25"  value="<o:getcmnname typeName="SET_W_JOBOUTOFDATEREASON"  match="oneline:OutOfDateReason"/>" class="textgray" readonly >
      </td>	
	    <td valign="middle" class="list_bg2"  ><div align="right">当前处理动作</div></td>
      <td class="list_bg1"> <font size="2">
          <input type="text" name="txtShowAction" size="25"  value="<o:getcmnname typeName="SET_W_JOBCARDLOGTYPE" match="jobcardprocess:action"/>" class="textgray" readonly >
      </font></td>
	</tr>
	<tr>
         <td class="list_bg2" ><div align="right" nowrap>备注</div></td>
         <td class="list_bg1" colspan=3>
          <input type="text" name="txtType" class="textgray"  size="84" readonly value="<tbl:write name="oneline" property="description"/>" >
         </td>
    </tr>
</table>



<table width="810" border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg" id="mnu1" style="display:none">
	<tr>
         <td class="list_bg2" width=17% align="right">创建操作员</div></td>
         <td class="list_bg1" width=33% align="left">
          <input type="text" name="txtType" class="textgray"  size="25" readonly value="<%=OpName%>" >
         </td>
         <td class="list_bg2" width=17% align="right"><div align="right" nowrap>创建组织机构</div></td>
         <td class="list_bg1" width=33%>
         	<input type="text" name="txtType"  class="textgray" size="25" readonly value="<tbl:WriteOrganizationInfo name="oneline" property="CreateOrgID" />" >
         </td>
	</tr>
	<tr>
		<td class="list_bg2"><div align="right">创建时间</div></td>
		<td class="list_bg1"> 
		<input type="text" name="txtActionTime" size="25" value="<tbl:writedate name="oneline" property="dtCreate"  includeTime="true"/>" class="textgray" readonly>   
		</td>
		<td class="list_bg2" ><div align="right" nowrap>创建来源</div></td>
         <td class="list_bg1">
          <input type="text" name="txtType" class="textgray"  size="25" readonly value="<o:getcmnname typeName="SET_W_JOBCARDCREATEMETHOD" match="oneline:createMethod" />" >
       	</td>
	</tr>
	<tr>
		<td class="list_bg2"><div align="right" nowrap>创建原因</div></td>
		<td class="list_bg1">
		<input type="text" name="txtType"  class="textgray" size="25" readonly value="<o:getcmnname typeName="SET_W_JOBCARDCREATEREASON" match="oneline:manualCreateReason" />" >
		</td>
		<td class="list_bg2" >
		<div align="right">第一次上门时间</div></td>
		<td class="list_bg1" > 
			<input type="text" name="txtFirstWorkDate"  size="25" value="<tbl:writedate name="oneline" property="firstWorkDate" includeTime="true"/>" class="textgray" readonly >
		</td>
	</tr>
	<tr>
		<td class="list_bg2" ><div align="right">本次上门时间</div></td>
		<td class="list_bg1"> 
		     <input type="text" name="txtWorkDate"  size="25"  class="textgray" readonly value="<tbl:writedate name="oneline" property="workDate" includeTime="true"/>" >
	        </td>
		</td>
		<td class="list_bg2" ><div align="right">本次上门时间段</div></td>
		<td class="list_bg1"> 
		<input type="text" name="txtWorkTime"  size="25" value="<o:getcmnname typeName="SET_C_CSIPREFEREDTIME" match="oneline:workTime" />" class="textgray" readonly >
		</td>
	</tr>
	<tr>
		<td class="list_bg2" ><div align="right" nowrap>状态变化日期</div></td>
		<td class="list_bg1" colspan=3>
			<input type="text" name="txtType" class="textgray"  size="25" readonly value="<tbl:write name="oneline" property="statusReasonDate"/>" >
		</td>
    </tr>
	<tr>
         <td class="list_bg2" ><div align="right" nowrap>支付状态</div></td>
         <td class="list_bg1">
          <input type="text" name="txtType" class="textgray"  size="25" readonly value="<o:getcmnname typeName="SET_W_JOBCARDPAYSTATUS" match="oneline:payStatus" />" >
         </td>
         <td class="list_bg2"><div align="right" nowrap>支付日期</div></td>
         <td class="list_bg1">
         <input type="text" name="txtType"  class="textgray" size="25" readonly value="<tbl:writedate name="oneline" property="PayDate" includeTime="false"/>" >
         </td>
    </tr>
</table>


<table width="810" border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">

<% 
	//工单
	java.util.Collection feecol=Postern.getAllFeeListByCsiAndType(jcdto.getJobCardId(),"J");
	if(feecol!=null&&!feecol.isEmpty()) {
%>
<tr><td valign="middle" colspan=4 > 
          <table width="810" border="0" align="center">
            <tr> 
              <td  class="import_tit" align="center"><font size="3">应收费用明细</strong></td>
	    </tr>
            <tr ><td align="center"> 
              <iframe SRC="csi_fee_list.do?CSIID=<%=jcdto.getJobCardId()%>&ReferType=<%="J"%>" id="FrameFeeList" name="FrameFeeList" width="100%" height="146"></iframe>
            </td></tr>
          </table>
</td></tr>

<% 	}
	feecol=Postern.getAllPaymentListByCsiAndType(jcdto.getJobCardId(),"J");
	if(feecol!=null&&!feecol.isEmpty()) {
%>
<tr><td valign="middle" colspan=4 >
          <table width="810" border="0" align="center">
            <tr> 
              <td  class="import_tit" align="center" ><font size="3">实收费用明细</strong></td>
	    </tr>
	    <tr ><td align="center"> 
               <iframe SRC="csi_payment_list.do?CSIID=<%=jcdto.getJobCardId()%>&SourceType=<%="J"%>" id="FramePaymentList" name="FramePaymentList" width="100%" height="146"></iframe>
            </td></tr>
           </table>
</td></tr>
<%}%>

	<%
	     Map materialMap = Postern.getServeyResultByCustIdForRealUser(jcdto.getJobCardId(),"t_materialusage","jobcardid");
       pageContext.setAttribute("materialMap",materialMap);
       if ("I".equals(jcdto.getJobType())){
	%>
	      <tbl:dynamicservey serveyType="U" serveySubType="IU"  showType="label" tdWordStyle="list_bg2" tdControlStyle="list_bg1" controlSize="25" match="materialMap"/>  
	<%
	     }else if ("R".equals(jcdto.getJobType())){
	%> 	
	 	   <tbl:dynamicservey serveyType="U" serveySubType="RU"  showType="label" tdWordStyle="list_bg2" tdControlStyle="list_bg1" controlSize="25" match="materialMap"/>  
	<%
	     }
	%>

</table>


  <br>
  <table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
  </table>
<% boolean vflag = Postern.buttonCanBeVisibleForRepair(jcdto.getJobCardId(),CurrentLogonOperator.getOperator(request).getOrgID());
       	   pageContext.setAttribute("canBeVisible", vflag+"");
%> 
  <br>
  <table align="center" border="0" cellspacing="0" cellpadding="0">
    <tr>
      <bk:canback url="jobcard_query.do/cp_query_detail.do/sheetrep_query.do/contactrep_query_result.do/in_job_card_query_result.do/rep_job_card_query_result.do/register_repair_info_query_result.do/catv_job_card_query.do/jobcard_query_result.do"> 
	      <td><img src="img/button2_r.gif" width="22" height="20"></td>  
        <td background="img/button_bg.gif">
      	<a href="<bk:backurl property="jobcard_query.do/cp_query_detail.do/sheetrep_query.do/contactrep_query_result.do/in_job_card_query_result.do/rep_job_card_query_result.do/register_repair_info_query_result.do/catv_job_card_query.do/jobcard_query_result.do" />" class="btn12">返&nbsp;回</a></td>
        <td><img src="img/button2_l.gif" width="13" height="20"></td>
      </bk:canback> 
        <td width="20"></td>
        <td><img src="img/button_l.gif" border="0" ></td>
      	<td background="img/button_bg.gif"  ><a href="javascript:historyRecord()"  class="btn12">历史记录</a></td>
        <td><img src="img/button_r.gif" border="0" ></td>        
<%        
	 if (strJobType.equals("I")){		//安装
        String strCsiUrl="service_interaction_view.do"; %>
        <o:displayControl id="button_jobcard_query_detail_refersheet_view" bean="oneline">
        <td width="20"></td>
        <td><img src="img/button_l.gif" border="0" ></td>
        <td background="img/button_bg.gif">
		     <a href="<%=strCsiUrl%>?txtID=<tbl:write name="oneline" property="ReferSheetId"/>" class="btn12">相关受理单</a></td>
        <td><img src="img/button_r.gif" border="0" ></td> 
        </o:displayControl>
        <pri:authorized name="contact_user_for_install.do"> 
        <o:displayControl id="button_query_detail_install_contact" bean="oneline,canBeVisible">
         <td width="20" ></td>
         <td><img src="img/button_l.gif" border="0" ></td>
         <td background="img/button_bg.gif"><a href="javascript:install_contact('<tbl:write name="oneline" property="JobCardId" />')" class="btn12">安装预约</a></td>
         <td><img src="img/button_r.gif" border="0" ></td>
        </o:displayControl>
        </pri:authorized>
        <pri:authorized name="job_card_view_for_re_contact_of_install.do">
        <o:displayControl id="button_query_detail_install_recontact" bean="oneline,canBeVisible">
         <td width="20" ></td>
         <td><img src="img/button_l.gif" border="0" ></td>
         <td background="img/button_bg.gif"><a href="javascript:install_recontact('<tbl:write name="oneline" property="JobCardId" />')" class="btn12">安装重预约</a></td>
         <td><img src="img/button_r.gif" border="0" ></td>
        </o:displayControl>
        </pri:authorized>
        <pri:authorized name="job_card_view_for_register_of_install.do">
        <o:displayControl id="button_query_detail_install_register" bean="oneline,canBeVisible">
         <td width="20" ></td>
         <td><img src="img/button_l.gif" border="0" ></td>
         <td background="img/button_bg.gif"><a href="javascript:install_register('<tbl:write name="oneline" property="JobCardId" />')" class="btn12">录入安装信息</a></td>
         <td><img src="img/button_r.gif" border="0" ></td>
        </o:displayControl>
        </pri:authorized>
        <pri:authorized name="ci_view_for_close.do">
        <o:displayControl id="button_query_detail_install_close" bean="oneline,canBeVisible">
         <td width="20" ></td>
         <td><img src="img/button_l.gif" border="0" ></td>
         <td background="img/button_bg.gif"><a href="javascript:install_close('<tbl:write name="oneline" property="JobCardId" />')" class="btn12">结束安装</a></td>
         <td><img src="img/button_r.gif" border="0" ></td>
        </o:displayControl>  
        </pri:authorized>  	            
<%       
   } else if (strJobType.equals("R")){     
   	   System.out.println("wrap.getJobCardDto().getStatus()------>"+wrap.getJobCardDto().getStatus());
   	
   	//维修  %>
	    <o:displayControl id="button_jobcard_query_detail_refersheet_view" bean="oneline">
         <td width="20"></td>
         <td><img src="img/button_l.gif" border="0" ></td>
         <td background="img/button_bg.gif">
		       <a href="cp_query_detail.do?txtCustomerProblemID=<tbl:write name="oneline" property="ReferSheetId"/>" class="btn12">相关报修单</a></td>
         <td><img src="img/button_r.gif" border="0" ></td>
      </o:displayControl>
      <o:displayControl id="button_customerproblem_query_detail_repair_contact" bean="oneline,canBeVisible">
         <td width="20" ></td>
         <td><img src="img/button_l.gif" border="0" ></td>
         <td background="img/button_bg.gif"><a href="javascript:repair_contact()" class="btn12">维修预约</a></td>
         <td><img src="img/button_r.gif" border="0" ></td>
      </o:displayControl>
      <o:displayControl id="button_customerproblem_query_detail_repair_register" bean="oneline,canBeVisible">
         <td width="20" ></td>
         <td><img src="img/button_l.gif" border="0" ></td>
         <td background="img/button_bg.gif"><a href="javascript:repair_register()" class="btn12">录入维修信息</a></td>
         <td><img src="img/button_r.gif" border="0" ></td>
      </o:displayControl>
      <o:displayControl id="button_customerproblem_query_detail_repair_close" bean="oneline,canBeVisible">
         <td width="20" ></td>
         <td><img src="img/button_l.gif" border="0" ></td>
         <td background="img/button_bg.gif"><a href="javascript:repair_close()" class="btn12">结束维修</a></td>
         <td><img src="img/button_r.gif" border="0" ></td>
      </o:displayControl>
      <pri:authorized name="maintain_print_for_mtg.do" >
      	 <td width="20" ></td>
       	 <td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
         <td><input name="Submit" type="button" class="button" value="门头沟维修单打印" onclick="javascript:maintain_mtg_print()"></td>
         <td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
      </pri:authorized>
<% } else if (strJobType.equals("C")){           //模拟施工
	     String strCsiUrl="service_interaction_view.do";  
%>
	     <o:displayControl id="button_jobcard_query_detail_refersheet_view" bean="oneline">
          <td width="20"></td>
          <td><img src="img/button_l.gif" border="0" ></td>
          <td background="img/button_bg.gif">
		      <a href="<%=strCsiUrl%>?txtID=<tbl:write name="oneline" property="ReferSheetId"/>" class="btn12">相关受理单</a></td>
          <td><img src="img/button_r.gif" border="0" ></td> 
        </o:displayControl>
        <pri:authorized name="contact_user_for_install.do"> 
         <o:displayControl id="button_query_detail_install_contact" bean="oneline,canBeVisible">
          <td width="20" ></td>
          <td><img src="img/button_l.gif" border="0" ></td>
          <td background="img/button_bg.gif"><a href="javascript:construction_contact_catv('<tbl:write name="oneline" property="JobCardId" />')" class="btn12">施工预约</a></td>
          <td><img src="img/button_r.gif" border="0" ></td>
         </o:displayControl>
        </pri:authorized>
    	<!--    <pri:authorized name="job_card_view_for_re_contact_of_install.do">
        <o:displayControl id="button_query_detail_install_recontact" bean="oneline,canBeVisible">
         <td width="20" ></td>
         <td><img src="img/button_l.gif" border="0" ></td>
         <td background="img/button_bg.gif"><a href="javascript:install_recontact('<tbl:write name="oneline" property="JobCardId" />')" class="btn12">施工重预约</a></td>
         <td><img src="img/button_r.gif" border="0" ></td>
        </o:displayControl>
        </pri:authorized>  -->
         <pri:authorized name="job_card_view_for_register_of_install.do">
          <o:displayControl id="button_query_detail_install_register" bean="oneline,canBeVisible">
            <td width="20" ></td>
            <td><img src="img/button_l.gif" border="0" ></td>
            <td background="img/button_bg.gif"><a href="javascript:construction_register_catv('<tbl:write name="oneline" property="JobCardId" />')" class="btn12">录入施工信息</a></td>
            <td><img src="img/button_r.gif" border="0" ></td>
          </o:displayControl>
         </pri:authorized>
         <pri:authorized name="ci_view_for_close.do">
          <o:displayControl id="button_query_detail_install_close" bean="oneline,canBeVisible">
            <td width="20" ></td>
            <td><img src="img/button_l.gif" border="0" ></td>
            <td background="img/button_bg.gif"><a href="javascript:construction_close_catv('<tbl:write name="oneline" property="JobCardId" />')" class="btn12">结束施工</a></td>
            <td><img src="img/button_r.gif" border="0" ></td>
           </o:displayControl>  
         </pri:authorized>   
         <pri:authorized name="install_print_for_mtg.do">
           <td width="20" ></td>
       	   <td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
           <td><input name="Submit" type="button" class="button" value="门头沟安装单打印" onclick="javascript:install_mtg_print()"></td>
           <td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
         </pri:authorized>     
<%
       if("Z".equals(wrap.getJobCardDto().getSubType())){
%>
          <pri:authorized name="construct_print_for_huairou.do" >
           <td width="20" ></td>
       	   <td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
           <td><input name="Submit" type="button" class="button" value="怀柔收据打印" onclick="javascript:huairou_print(<%=wrap.getJobCardDto().getJobCardId()%>,'J')"></td>
         	 <td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
          </pri:authorized>               
<%     
        }  
   } 
%>
	</tr>
	
 </table>           

<input type="hidden" name="txtFrom" size="20" value="1">
<input type="hidden" name="txtTo" size="20" value="10">
</form>  
 </lgc:bloop> 

 

   

 