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
<%@ page import="com.dtv.oss.dto.JobCardProcessDTO"%>
<%@ page import="com.dtv.oss.util.Postern"%>
<%@ page import="com.dtv.oss.web.util.WebOperationUtil"%>
<%@ page import="com.dtv.oss.web.util.CommonKeys" %>
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
 function config_print(jobCardID,PrintSheetType)
{
		document.frmPost.target="_blank";
		document.frmPost.action="config_print.do?txtJobCardIDList="+JobCardID+"&txtPrintSheetType="+PrintSheetType;
		document.frmPost.submit();
		document.frmPost.target="_self";
}
</Script>

<table width="820" align="center" cellpadding="0" cellspacing="0">
<tr> 
<td align="center" valign="top"><table  border="0" align="center" cellpadding="0" cellspacing="10">
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


<%
    //com.dtv.oss.dto.wrap.work.JobCard2JobCardProcessWrap   wrap=(com.dtv.oss.dto.wrap.work.JobCard2JobCardProcessWrap)pageContext.getAttribute("oneline");
    
    JobCardDTO jcdto = Postern.getJobCardDto("R",10000007);
    pageContext.setAttribute("oneline", jcdto);
    //JobCardDTO jcdto= wrap.getJobCardDto();
    String strJobType = jcdto.getJobType();
    if (strJobType == null) strJobType = "";
    String status = jcdto.getStatus();
     String strAddress = Postern.getAddressById(jcdto.getAddressId());
     AddressDTO addrDto = Postern.getAddressDtoByID(jcdto.getAddressId());
     pageContext.setAttribute("custaddr", addrDto);
    if (strAddress == null) strAddress="";
    pageContext.setAttribute("jobcardprocess",new JobCardProcessDTO());
    //JobCardProcessDTO jbcDto= wrap.getJobCardProcessDto();
    String referType = Postern.getReferSourceTypeByID(jcdto.getJobCardId()); 
%>

 <table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
        <tr>
        <td class="list_bg2"><div align="right">工单编号</div></td>
        <td class="list_bg1">
	<input type="text" name="txtJobCardID"  size="25"  value="<tbl:write name="oneline" property="JobCardId" />" class="textgray" readonly   >
	</td>
	
	<td class="list_bg2" ><div align="right">用户证号</div></td>
		<td class="list_bg1"> 
		<input type="text" name="txtCustID" size="25" class="textgray" readonly value="<tbl:write name="oneline" property="CustId"/>" >
	       </td>
	       
	
	
	</tr>
	 <tr>
		<td class="list_bg2" ><div align="right">工单类型</div></td>
		<td class="list_bg1"> 
			<input type="text" name="txtType" size="25" value="<o:getcmnname typeName="SET_W_JOBCARDTYPE" match="oneline:JobType"/>" class="textgray" readonly>
                </td>
                
        <td class="list_bg2" ><div align="right">工单子类型</div></td>
		<td class="list_bg1"> 
			<input type="text" name="txtType" size="25" value="<o:getcmnname typeName="SET_W_JOBCARDSUBTYPE" match="oneline:SubType"/>" class="textgray" readonly>
                </td>        
		
	</tr>
	
	</tr>
	 <tr>
		<td class="list_bg2"><div align="right">受理单号</div></td>
        <td class="list_bg1">
		<input type="text" name="txtReferSheetID"  size="25"  value="<tbl:write name="oneline" property="ReferSheetId" />" class="textgray" readonly   >
	</td>
                
        <td class="list_bg2" ><div align="right">端口数</div></td>
		<td class="list_bg1"> 
			<input type="text" name="txtType" size="25" value="<tbl:write name="oneline" property="portNo" />" class="textgray" readonly>
                </td>        
		
	</tr>
	
	<tr>
    	         <td class="list_bg2" ><div align="right">所属区域</div></td>
		<td class="list_bg1">
		 <input type="text" name="txtDistrictDesc" size="25" maxlength="50" class="textgray" readonly value="<tbl:WriteDistrictInfo name="custaddr" property="DistrictID" />" class="text">
		 
		<td class="list_bg2"><div align="right">联系地址</div></td>
		<td class="list_bg1">
		<input type="text" name="txtCustID"  size="25" class="textgray" readonly value="<%=strAddress%>"></td>
		 
        </tr>
        <tr>
           
            <td class="list_bg2"><div align="right">创建时间</div></td>
		<td class="list_bg1" > 
		<input type="text" name="txtActionTime" size="25" value="<tbl:writedate name="oneline" property="dtCreate"  includeTime="true"/>" class="textgray" readonly>   
            </td>            
            <td class="list_bg2"><div align="right">当前处理部门</div></td>
		<td class="list_bg1" > 
		<input type="text" name="txtProcessOrgId" size="25" value="<tbl:WriteOrganizationInfo name="oneline" property="processOrgId" />" class="textgray" readonly>   
            </td>
      </tr> 
      <!--
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
      -->
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
		<td class="list_bg2" ><div align="right">本次上门时间</div></td>
		<td class="list_bg1"> 
		     <input type="text" name="txtWorkDate"  size="25"  class="textgray" readonly value="<tbl:writedate name="oneline" property="workDate" includeTime="false"/>" >
	        </td>
		</td>
		<td class="list_bg2" ><div align="right">本次上门时间段</div></td>
		<td class="list_bg1"> 
		<input type="text" name="txtWorkTime"  size="25" value="<o:getcmnname typeName="SET_C_CSIPREFEREDTIME" match="oneline:workTime" />" class="textgray" readonly >
		</td>
	</tr>
	<tr> 
	        
	        <td class="list_bg2" ><div align="right">施工结果</div></td>
		<td class="list_bg1"> 
		        <input type="text" name="txtWorkResult"   size="25"  class="textgray" readonly value="<o:getcmnname typeName="SET_W_JOBWORKRESULT" match="oneline:workResult"/>" >
	        </td>
		</td>
		<td class="list_bg2"><div align="right">状态</div></td>
		<td class="list_bg1"> 
		<input type="text" name="txtStatus"  size="25"  value="<o:getcmnname typeName="SET_W_JOBCARDSTATUS" match="oneline:status" />" class="textgray" readonly >
		</td>
	</tr>
	
	<tr>
	 
		<td class="list_bg2" ><div align="right">施工人员</div></td>
		<td class="list_bg1"> 
			<input type="text" name="txtProcessMan" size="25"  class="textgray"  readonly value="<tbl:write name="jobcardprocess" property="ProcessMan" />"   >
		 </td>
		 <td class="list_bg2" ><div align="right">第一次上门时间</div></td>
		 <td class="list_bg1"> 
		 <input type="text" name="txtFirstWorkDate"  size="25" value="<tbl:writedate name="oneline" property="firstWorkDate" includeTime="false"/>" class="textgray" readonly >
		</td>
	
	</tr>

	
	 <tr>
		<td class="list_bg2" ><div align="right">施工成功描述</div></td>
		<td class="list_bg1" colspan="3"> 
		<input type="text" name="txtType" size="83"  class="textgray" readonly value="<tbl:write name="jobcardprocess" property="Memo"/>" >
	        </td>
	</tr>
	
	</tr>

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
  <br>
  <table align="center" border="0" cellspacing="0" cellpadding="0">
    <tr> 
      <bk:canback url="jobcard_query.do/cp_query_detail.do/sheetrep_query.do/contactrep_query_result.do/in_job_card_query_result.do/rep_job_card_query_result.do/register_repair_info_query_result.do"> 
	      <td><img src="img/button2_r.gif" width="22" height="20"></td>  
        <td background="img/button_bg.gif">
      	<a href="<bk:backurl property="jobcard_query.do/cp_query_detail.do/sheetrep_query.do/contactrep_query_result.do/in_job_card_query_result.do/rep_job_card_query_result.do/register_repair_info_query_result.do" />" class="btn12">返&nbsp;回</a></td>
        <td><img src="img/button2_l.gif" width="13" height="20"></td>
      </bk:canback> 
        <td width="20"></td>
        <td><img src="img/button_l.gif" border="0" ></td>
      	<td background="img/button_bg.gif"  ><a href="javascript:historyRecord()"  class="btn12">历史记录</a></td>
        <td><img src="img/button_r.gif" border="0" ></td>
        
<%        
	 if (strJobType.equals("I")){
        String strCsiUrl="service_interaction_view.do";             
%>
        <td width="20"></td>
        <td><img src="img/button_l.gif" border="0" ></td>
        <td background="img/button_bg.gif">
		     <a href="<%=strCsiUrl%>?txtID=<tbl:write name="oneline" property="ReferSheetId"/>" class="btn12">相关受理单</a></td>
        <td><img src="img/button_r.gif" border="0" ></td>
        
<%       
   } else if (strJobType.equals("R")){
%>
         <td width="20"></td>
         <td><img src="img/button_l.gif" border="0" ></td>
         <td background="img/button_bg.gif">
		       <a href="cp_query_detail.do?txtCustomerProblemID=<tbl:write name="oneline" property="ReferSheetId"/>" class="btn12">相关报修单</a></td>
         <td><img src="img/button_r.gif" border="0" ></td>
<%        
    //}
        
   // if (referType!=null) { 
  %>
   <!--
         <td width="20" ></td>
         <td><img src="img/button_l.gif" border="0" ></td>
         <td background="img/button_bg.gif">
	       <a href="diagnosis_info_view_p.do?txtReferJobCardID=<tbl:write name="oneline" property="jobCardId"/>" class="btn12">诊断信息</a></td>
         <td><img src="img/button_r.gif" border="0" ></td>
    -->
	<% boolean vflag = Postern.buttonCanBeVisibleForRepair(jcdto.getJobCardId(),CurrentLogonOperator.getOperator(request).getOrgID());
       	   pageContext.setAttribute("canBeVisible", vflag+"");
       	   System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$"+vflag+"&&&status:"+jcdto.getStatus());
//} 
%>
      <pri:authorized name="catv_job_card_view_for_contact.do">	
      <o:displayControl id="button_customerproblem_query_detail_repair_contact" bean="oneline,canBeVisible">
         <td width="20" ></td>
         <td><img src="img/button_l.gif" border="0" ></td>
         <td background="img/button_bg.gif"><a href="javascript:repair_contact()" class="btn12">施工预约</a></td>
         <td><img src="img/button_r.gif" border="0" ></td>
      </o:displayControl>
      </pri:authorized>
      <pri:authorized name="catv_jobcard_query_result_for_register.do">
      <o:displayControl id="button_customerproblem_query_detail_repair_register" bean="oneline,canBeVisible">
         <td width="20" ></td>
         <td><img src="img/button_l.gif" border="0" ></td>
         <td background="img/button_bg.gif"><a href="javascript:repair_register()" class="btn12">录入施工信息</a></td>
         <td><img src="img/button_r.gif" border="0" ></td>
      </o:displayControl>
      </pri:authorized>
      <pri:authorized name="catv_query_result_for_close.do">
      <o:displayControl id="button_customerproblem_query_detail_repair_close" bean="oneline,canBeVisible">
         <td width="20" ></td>
         <td><img src="img/button_l.gif" border="0" ></td>
         <td background="img/button_bg.gif"><a href="javascript:repair_close()" class="btn12">结束施工</a></td>
         <td><img src="img/button_r.gif" border="0" ></td>
      </o:displayControl>
      </pri:authorized>
      <pri:authorized name="catv_job_card_view_print_config.do">
         <td width="20" ></td>
         <td><img src="img/button_l.gif" border="0" ></td>
         <td background="img/button_bg.gif"><a href="javascript:config_print('<tbl:write name="oneline" property="jobCardId"/>','<%=CommonKeys.SET_V_PRINTSHEETTYPE_M%>')" class="btn12">工单打印</a></td>
         <td><img src="img/button_r.gif" border="0" ></td>
      </pri:authorized>
      <%}%>
      </tr>
      
 </table>           
  
 
 <input type="hidden" name="txtFrom" size="20" value="1">
<input type="hidden" name="txtTo" size="20" value="10">
</form>  

 </td>
 </tr>
</table> 
 

   

 