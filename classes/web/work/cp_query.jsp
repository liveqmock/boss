<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="resultset" prefix="rs" %>
<%@ taglib uri="osstags" prefix="d" %>
<%@ page import="com.dtv.oss.dto.wrap.work.CustomerProblem2CPProcessWrap" %>
<%@ page import="com.dtv.oss.dto.CustomerProblemDTO" %>
<%@ page import="com.dtv.oss.dto.CustProblemProcessDTO" %>
<%@ page import="com.dtv.oss.dto.AddressDTO" %>
<%@ page import="com.dtv.oss.util.Postern" %>
<%@ page import="com.dtv.oss.web.util.CurrentLogonOperator"%>
<%@ page import="com.dtv.oss.dto.OperatorDTO"%>

<script language=javascript>
function query_submit()
{
	if(checkDate()){
		if(query_all()){
			document.frmPost.txtQueryType.value = 'queryAll';			
		}else{
			document.frmPost.txtQueryType.value = 'queryPart';
		}
		document.frmPost.submit();
	}
}

function view_detail_click(strId)
{
	self.location.href="cp_query_detail.do?txtCustomerProblemID="+strId;
}
 

function checkDate(){
 
	if (document.frmPost.txtCreateStartDateDatePart.value != ''){
		if (!check_TenDate(document.frmPost.txtCreateStartDateDatePart, true, "开始日期")){
			return false;
		}
	}
	 
	if (document.frmPost.txtCreateEndDateDatePart.value != ''){
		if (!check_TenDate(document.frmPost.txtCreateEndDateDatePart, true, "结束日期")){
			return false;
		}
	}
	 
	if(!compareDate(document.frmPost.txtCreateStartDateDatePart,document.frmPost.txtCreateEndDateDatePart,"结束日期必须大于等于开始日期")){
		
		return false;
	}
	if(!checkPlainNum(document.frmPost.txtCustomerProblemID,true,9,"报修单编号"))
		return false; 
    	if(!checkPlainNum(document.frmPost.txtCustID,true,9,"客户证号"))
		return false;
	
	            
	return true;
}

function ChangeDeviceClass()
{
    document.FrameUS.submit_update_select('devclasstomodel',document.frmPost.txtDeviceClass.value, 'txtDeviceModel', '');
}
function query_all(){
	if(document.frmPost.queryType.checked == true)
		return true;
	return false;
}
</script>
<div id="updselwaitlayer"
	style="position:absolute; left:650px; top:170px; width:250px; height:24px; display:none">
<table width="100%" border="0" cellspacing="1" cellpadding="3">
	<tr>
		<td width="100%">
		<div align="center"><font size="2"> 正在获取内容。。。 </font>
		</td>
	</tr>
</table>
</div>
<table width="820" align="center" cellpadding="0" cellspacing="0">
<tr><td>
<table  border="0" align="center" cellpadding="0" cellspacing="10">
  <tr>
    <td><img src="img/list_tit.gif" width="19" height="19"></td>
    <td  class="title">报修单查询</td>
  </tr>
  </table>
  <table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>
<table align="center" border="0" cellpadding="0" cellspacing="0" width="820">
  <tr>
    <td aglin="center">
<iframe SRC="update_select.screen" name="FrameUS" width="0" height="0"
	frameborder="0" scrolling="0"> </iframe>
	    </td>
  </tr>
</table>
<form name="frmPost" method="post" action="cp_query_result.do" >
 <input type="hidden" name="txtQueryType" value="<tbl:writeparam name="txtQueryType"/>"> 
   <table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
     <%
     
           Map mapDeviceModel = Postern.getAllDeviceModels();
          pageContext.setAttribute("AllDeviceModel",mapDeviceModel);
         /* String custID=(String)request.getAttribute("CUSTOMERID");
          
          if (custID==null ||"".equals(custID)){
         
       */%> 
        <tr> 
          <td  class="list_bg2" width="17%"><div align="right">报修单编号</div></td>          
          <td class="list_bg1" width="33%">
           <input type="text" name="txtCustomerProblemID" maxlength="9" size="25"  value="<tbl:writeparam name="txtCustomerProblemID" />" >
           </td>      
           <td class="list_bg2" width="17%"><div align="right">客户证号</div></td>
	      <td class="list_bg1" width="33%">
              <input type="text" size="25" maxlength="9" name="txtCustID" value="<tbl:writeparam name="txtCustID"/>">
             </td>
           
        </tr>
        <tr>
           <td  class="list_bg2"><div align="right">所在区域</div></td> 
          <td class="list_bg1"  valign="middle">
            <input type="hidden" name="txtCounty" value="<tbl:writeparam name="txtCounty" />">
	    <input type="text" name="txtCountyDesc" size="25" maxlength="50" readonly value="<tbl:WriteDistrictInfo property="txtCounty" />" class="text">
            <input name="selDistButton" type="button" class="button" value="选择" onClick="javascript:sel_district('all','txtCounty','txtCountyDesc')">
         </td>
    <td class="list_bg2"><div align="right">所属组织</div></td>
    <td class="list_bg1">
    	<input type="hidden" name="txtOrgID" value="<tbl:writeparam name="txtOrgID" />">
	<input type="text" name="txtOrgDesc" size="25" maxlength="50" readonly value="<tbl:WriteOrganizationInfo property="txtOrgID" />" class="text">
	<input name="selOrgButton" type="button" class="button" value="选择" onClick="javascript:sel_organization('C,B,O,S','txtOrgID','txtOrgDesc')">
    </td>		
      </tr> 
        
       <%// } else {%>
      <!--   <input type="hidden" name="txtCustID" size="20" value="<tbl:writeparam name="txtCustID"/>">  -->
         <%//}%>
           <tr>  
              <td class="list_bg2"><div align="right">报修类别</div></td> 
	      <td class="list_bg1"> 
	      <d:selcmn name="txtProblemCategory" mapName="SET_C_CPPROBLEMCATEGORY" match="txtProblemCategory" width="23" />
	     </td>   
	       <td class="list_bg2"><div align="right">安装地址</div></td>
	      <td class="list_bg1">
              <input type="text" size="25" maxlength="100" name="txtAddress" value="<tbl:writeparam name="txtAddress"/>">
             </td>
        </tr>
         <tr>  
              <td class="list_bg2"><div align="right">服务号码</div></td> 
	     	  <td class="list_bg1" > 
	     	  <input type="text" size="25" maxlength="100" name="txtServiceCode" value="<tbl:writeparam name="txtServiceCode"/>">
	          </td>	
	          <td class="list_bg2"><div align="right">是否手工流转</div></td> 
	     	  <td class="list_bg1" > 
	     	  <d:selcmn name="txtIsManualTransfer" mapName="SET_G_YESNOFLAG" match="txtIsManualTransfer" width="23" />
	          </td>     
	     	  
        </tr>
        <tr>
				<td class="list_bg2" width="17%">
				<div align="right">设备类型*</div>
				</td>
				<td class="list_bg1" width="33%"><%OperatorDTO operDto = CurrentLogonOperator.getOperator(request);
			String currentOrgType = Postern.getOrganizationTypeByID(operDto
					.getOrgID());
			Map mapDepots = null;
			//if (!CommonConstDefinition.ORGANIZATIONORGTYPE_GENERALCOMPANY.equals(currentOrgType)) {
			//	mapDepots = Postern.getDepotByOwnerID(operDto.getOrgID());
			//} else {
				mapDepots = Postern.getAllDepot();
			//}
			pageContext.setAttribute("AllDepots", mapDepots);
			Map mapDeviceClasses = Postern.getAllDeviceClasses();
			pageContext.setAttribute("AllDeviceClasses", mapDeviceClasses);

		%> <tbl:select name="txtDeviceClass" set="AllDeviceClasses"
					match="txtDeviceClass" width="23" onchange="ChangeDeviceClass()"/></td>
				<td class="list_bg2" width="17%">
				<div align="right">设备型号*</div>
				</td>
				<td class="list_bg1" width="33%"><d:seldevicemodel name="txtDeviceModel"
					deviceClass="txtDeviceClass" match="txtDeviceModel" width="23" /></td>

       <tr>
         <td class="list_bg2"><div align="right">收费类别</div></td>
	 <td class="list_bg1">
           <d:selcmn name="txtFeeClass" mapName="SET_C_CPFEECLASS" match="txtFeeClass" width="23" />
         </td>
          
           <td class="list_bg2"><div align="right">状态</div></td>
            <td class="list_bg1">
           <d:selcmn name="txtStatus" mapName="SET_C_PROBLEMSTATUS" match="txtStatus" width="23" /> </td>   
         </td>
      </tr> 
         <tr>
          <td  class="list_bg2"><div align="right">创建时间开始于</div></td> 
           <td class="list_bg1" > 
            <d:datetime name="txtCreateStartDate" size="16" match="txtCreateStartDate" includeHour="true" onclick="calendar(document.frmPost.txtCreateStartDateDatePart)" picURL="img/calendar.gif" style="cursor:hand" /></td> 
          <td  class="list_bg2"><div align="right">创建时间结束于</div></td> 
           <td class="list_bg1" > 
             <d:datetime name="txtCreateEndDate" size="16" match="txtCreateEndDate" includeHour="true" onclick="calendar(document.frmPost.txtCreateEndDateDatePart)" picURL="img/calendar.gif" style="cursor:hand" /></td> 
             </tr>
             <tr>
             <td class="list_bg1" colspan="4" align="center"> 
	     	  <input type="checkbox" name="queryType" >&nbsp查询所有
	     	  
	          </td>     
             </tr>
      </table>
       <table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
	  <tr align="center">
	  <td class="list_bg1"><table  border="0" cellspacing="0" cellpadding="0">
		  <tr>
			<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
			<td><input name="Submit" type="button" class="button" value="查 询" onclick="javascript:query_submit()"></td>
			<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
		  </tr>
	  </table> 
	 </td>
  </tr>
  </table>
    <input type="hidden" name="txtFrom" size="20" value="1">
      <input type="hidden" name="txtTo" size="20" value="10">
     
      
       
    
  
  <table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
   <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
 </table>
 
  
  <rs:hasresultset>
 <table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
         <tr class="list_head">
           <td class="list_head">报修单号</td>
           <td class="list_head">客户证号</td>
           <td class="list_head">业务帐号</td>
           <td class="list_head">所属分区</td>
           <td class="list_head">报修类别</td>
           <td class="list_head">状态</td>
           <td class="list_head">收费类别</td>
           <td class="list_head">创建时间</td>
           
        </tr>

<lgc:bloop requestObjectName="ResponseQueryResult" item="oneline" >
 
<% 
	CustomerProblem2CPProcessWrap wrap = (CustomerProblem2CPProcessWrap)pageContext.getAttribute("oneline");
	CustomerProblemDTO dto = wrap.getCpDto();
	String flag = dto.getCallBackFlag();
	CustProblemProcessDTO cppDto=wrap.getCppDto();
	 String strAddress = Postern.getAddressById(dto.getAddressID());
	 AddressDTO addrDto = Postern.getAddressDtoByID(dto.getAddressID());
         pageContext.setAttribute("oneline", dto);
           pageContext.setAttribute("custaddr", addrDto);
          pageContext.setAttribute("cpp", cppDto);
	if (strAddress==null) strAddress="";
%>
	 <tr class="list_bg1" onmouseover="this.className='list_over'" onmouseout="this.className='list_bg1'">
	     <td align="center"><a href="javascript:view_detail_click('<tbl:write name="oneline" property="Id"/>')" class="link12" ><tbl:writenumber name="oneline" property="Id" digit="7"/></a></td>
      	     <td align="center"><tbl:writenumber name="oneline" property="CustID" digit="7" hideZero="true"/></td>
      	     <td align="center"><tbl:write name="oneline" property="custServiceAccountID"/></td>
      	      <td align="center"><tbl:WriteDistrictInfo name="custaddr" property="districtID" /></td>
      	     <td align="center"><d:getcmnname typeName="SET_C_CPPROBLEMCATEGORY" match="oneline:ProblemCategory"/></td>
      	      <td align="center"><d:getcmnname typeName="SET_C_PROBLEMSTATUS" match="oneline:Status"/></td>
      	     <td align="center"><d:getcmnname typeName="SET_C_CPFEECLASS" match="oneline:feeClass" /></td> 
      	     <td align="center"><tbl:writedate includeTime="true"  name="oneline" property="CreateDate" /></td>
      	     
    	</tr>
</lgc:bloop>  

<tr>
    <td colspan="9" class="list_foot"></td>
  </tr>
 
            <tr>
              <td align="right" class="t12" colspan="9" >
                 第<span class="en_8pt"><rs:prop property="curpageno" /></span>页
                 <span class="en_8pt">/</span>共<span class="en_8pt"><rs:prop property="pageamount" />页
                 共有<span class="en_8pt"><rs:prop property="recordcount" /></span>条记录&nbsp;&nbsp;&nbsp;&nbsp;             
                 <rs:notfirst>
                   <img src="img/dot_top.gif" width="17" height="11">
                   <a href="javascript:firstPage_onClick(document.frmPost, <rs:prop property="firstto" />)" >首页 </a>
                   <img src="img/dot_pre.gif" width="6" height="11">
                   <a href="javascript:previous_onClick(document.frmPost, <rs:prop property="prefrom" />, <rs:prop property="preto" />)" >上一页</a>
                 </rs:notfirst>
          
                <rs:notlast>
                 &nbsp;
                 <img src="img/dot_next.gif" width="6" height="11">
                 <a href="javascript:next_onClick(document.frmPost, <rs:prop property="nextfrom" />, <rs:prop property="nextto" />)" >下一页</a>
                 <img src="img/dot_end.gif" width="17" height="11">
                 <a href="javascript:lastPage_onClick(document.frmPost, <rs:prop property="lastfrom" />, <rs:prop property="lastto" />)" >末页</a>
                </rs:notlast>
                &nbsp;
                转到
               <input type="text" name="txtPage" class="page_txt">页 
               <a href="javascript:goto_onClick(document.frmPost, <rs:prop property="pageamount" />)" >
                 <input name="imageField" type="image" src="img/button_go.gif" width="28" height="15" border="0">
               </a>
             </td>
         </tr>
</table>   
 
</rs:hasresultset> 
	 
    </td>
  </tr>
</form>  
</table>  
<script language=javascript>
function window_open(){
	if(document.frmPost.txtQueryType.value == 'queryAll')
		document.frmPost.queryType.checked = true;
}
window_open();

</script> 
