<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl"%>
<%@ taglib uri="logic" prefix="lgc"%>
<%@ taglib uri="resultset" prefix="rs"%>
<%@ taglib uri="/WEB-INF/oss.tld" prefix="d"%>
<%@ taglib uri="privilege" prefix="pri" %>

<%@ page import="java.util.*"%>
<%@ page import="com.dtv.oss.dto.*" %>
<%@ page import="com.dtv.oss.util.*"%>
<%@ page import="com.dtv.oss.service.commandresponse.QueryCommandResponseImpl"%>
<%@ page import="com.dtv.oss.dto.wrap.customer.ServiceAccountWrap,com.dtv.oss.web.util.CommonKeys"%>
<%@ page import="com.dtv.oss.web.util.WebUtil" %>
<%
  //是否起用设备用途：
	//String devicePurpose = Postern.getSystemsettingValueByName("SET_D_DEVICEPURPOSE");
    int customer=0;
	String txtCPIDs = request.getParameter("txtCPIDs");
	System.out.println(":::txtCPIDs="+txtCPIDs);
	if(txtCPIDs == null)txtCPIDs="";
%>
<Script language=JavaScript>
function cancel_submit()
{

  if(!checkselectCancel()){
		alert("请选择要退订的产品");
		return;
	}
  if (window.confirm('您确认要退订该产品吗?')){
//      document.frmPost.action="customer_product_cancel.screen";
      document.frmPost.action="customer_product_cancel.do";
	    document.frmPost.txtActionType.value="<%=CommonKeys.CUSTOMER_PRODUCT_CANCEL%>";
	    document.frmPost.submit();
	}
}
function productmove_submit(){
  if(!checkselectCancel()){
		alert("请选择要迁移的产品");
		return;
	}
  if (window.confirm('您确认要迁移该产品吗?')){
      document.frmPost.action="customer_product_move.do";
	    document.frmPost.txtActionType.value="<%=CommonKeys.CUSTOMER_PRODUCT_MOVE%>";
	    document.frmPost.submit();
	}
}

function suspend_submit(){
	if(!checkselectSuspend()){
		alert("请选择要暂停的产品");
		return;
	}

  if (window.confirm('您确认要暂停该产品吗?')){
	    document.frmPost.action="customer_product_stop.screen";
	   // document.frmPost.txtActionType.value="<%=CommonKeys.CUSTOMER_PRODUCT_PAUSE%>";
	    document.frmPost.submit();
	}
}

function checkselectSuspend() {
    var iCount = 0;
    var chekstateList = document.frmPost.chckState;
    var txtProductIDList =  document.frmPost.txtProductID;
    var cpIDs = "";
    var productids = "";

    if (document.frmPost.ListID.length!=null)
    {
        for (var i=0;i<document.frmPost.ListID.length;i++)
        {
    	    if (document.frmPost.ListID[i].checked)
    	    {
    	        iCount++;
    	        if (chekstateList[i].value!="N") return false;
		if(iCount == 1)
		{
		   productids = productids + txtProductIDList[i].value;
		   cpIDs = cpIDs + document.frmPost.ListID[i].value;
		}
		else
		{
                   productids = productids + ";" + txtProductIDList[i].value;
                   cpIDs = cpIDs + ";" + document.frmPost.ListID[i].value;
		}
    	    }
        }
    }
    else
    {
        if (document.frmPost.ListID.checked)
        {
            iCount++;
            productids =txtProductIDList.value;
            cpIDs =document.frmPost.ListID.value;
    	    if (chekstateList.value!="N") return false;
        }
    }

    if (iCount<=0) return false;
    document.frmPost.txtProductIDs.value =  productids;
    document.frmPost.txtCPIDs.value =  cpIDs;
    return true;
}

function checkselectCancel() {
    var iCount = 0;
    var chekstateList = document.frmPost.chckState;
    var txtProductIDList =  document.frmPost.txtProductID;
    var cpIDs = "";
    var productids = "";

    if (document.frmPost.ListID.length!=null)
    {
        for (var i=0;i<document.frmPost.ListID.length;i++)
        {
    	    if (document.frmPost.ListID[i].checked)
    	    {
    	        iCount++;
    	        //只有正常和主动暂停的可以退订
    	        if (!(chekstateList[i].value=="N" || chekstateList[i].value=="H")) return false;
		if(iCount == 1)
		{
		   productids = productids + txtProductIDList[i].value;
		   cpIDs = cpIDs + document.frmPost.ListID[i].value;
		}
		else
		{
                   productids = productids + ";" + txtProductIDList[i].value;
                   cpIDs = cpIDs + ";" + document.frmPost.ListID[i].value;
		}
    	    }
        }
    }
    else
    {
        if (document.frmPost.ListID.checked)
        {
            iCount++;
            productids =txtProductIDList.value;
            cpIDs =document.frmPost.ListID.value;
    	    if (!(chekstateList.value=="N" || chekstateList.value=="H")) return false;
        }
    }

    if (iCount<=0) return false;
    document.frmPost.txtProductIDs.value =  productids;
    document.frmPost.txtCPIDs.value =  cpIDs;
    return true;
}

function view_campaign_detail(strId)
{
   window.open('list_campaign_detail.do?txtCampaignID='+strId,'','toolbar=no,location=no,status=no,menubar=no,scrollbars=yes,width=330,height=250');
}
function view_all_campaign(psId)
{
   window.open('list_all_campaign.do?txtPsId='+psId+'&txtActionType=campaignList','','toolbar=no,location=no,status=no,menubar=no,scrollbars=yes,width=430,height=350');
}

function checkselectResume() {
    var iCount=0;
    var chekstateList=document.frmPost.chckState;
	var txtProductIDList =  document.frmPost.txtProductID;
	var cpIDs = "";
	var productids = "";

    if (document.frmPost.ListID.length!=null)
    {
        for (var i=0;i<document.frmPost.ListID.length;i++)
        {
    	    if (document.frmPost.ListID[i].checked)
    	    {
    	        iCount++;
    	        if (!(chekstateList[i].value=="S" || chekstateList[i].value=="H")) return false;
				if(iCount == 1)
				{
					productids = productids + txtProductIDList[i].value;
					cpIDs = cpIDs + document.frmPost.ListID[i].value;
				}
				else
				{
					productids = productids + ";" + txtProductIDList[i].value;
					cpIDs = cpIDs + ";" + document.frmPost.ListID[i].value;
				}
    	    }
        }

    }
    else
    {
        if (document.frmPost.ListID.checked)
        {
            iCount++;
            productids =txtProductIDList.value;
            cpIDs =document.frmPost.ListID.value;
    	    if (!(chekstateList.value=="S" || chekstateList.value=="H")) return false;
        }
    }
    if (iCount<=0) return false;
    document.frmPost.txtProductIDs.value =  productids;
    document.frmPost.txtCPIDs.value =  cpIDs;
    return true;
}
function change_current_account(currentPsID)
{	if (window.confirm('您确认要改变该产品的付费账户吗?'))
	{
	 	document.frmChangeAccountPost.txtPsID.value=currentPsID;
	    document.frmChangeAccountPost.submit();
	}
}
function resume_submit()
{	
	if(!checkselectResume()){
		alert("请选择要恢复的产品");
		return;
	}
    if (window.confirm('您确认要恢复该产品吗?'))
	{
	    //document.frmPost.action="customer_product_resume_create.do";
	    document.frmPost.action="customer_product_resume.screen";
	    //document.frmPost.txtActionType.value="<%=CommonKeys.CUSTOMER_PRODUCT_RESUME%>";
	    document.frmPost.submit();
	}
}
function view_detail_click(strId)
{
	self.location.href="customer_product_view.do?txtPsID="+strId;
}

function create_problem_submit(){
   document.frmPost.action="cp_create.do";
	 document.frmPost.submit();
}

function resendEMM_submit(){
	if (window.confirm('您确认要重发授权吗?')){
		 document.frmPost.action="resendEMM_submit.do";
		 document.frmPost.txtActionType.value="<%=CommonKeys.SERVICE_ACCOUNT_RESEND%>";
		 document.frmPost.submit();
	}
} 
function phoneNumber_submit(){
	document.frmPost.action="service_account_code_modify.screen";
	document.frmPost.txtActionType.value="<%=CommonKeys.CUSTOMER_PRODUCT_RESEND%>";
	document.frmPost.submit();
} 
function device_upgrade_submit(){
     document.frmPost.action="customer_device_upgrade.do";
     //document.frmPost.Action.value="suspend";
     //document.frmPost.confirm_post.value ="false";
     document.frmPost.submit();
}

function protocolDate_modify(){
    var iCount = 0;
    var cpIDs = "";
    if (document.frmPost.ListID.length!=null){
        for (var i=0;i<document.frmPost.ListID.length;i++){
    	    if (document.frmPost.ListID[i].checked){
    	        iCount++;
	          	if(iCount == 1){
		      		   cpIDs = cpIDs + document.frmPost.ListID[i].value;
	          	}else{
                 cpIDs = cpIDs + ";" + document.frmPost.ListID[i].value;
		          }
    	    }
        }
    }else{
        if (document.frmPost.ListID.checked){
            iCount++;
            cpIDs =document.frmPost.ListID.value;
        }
    }

    if (iCount<=0){
       alert("请选择要维护的产品！");
       return false;
    }
    document.frmPost.txtCPIDs.value =  cpIDs;
    document.frmPost.action="protocolDate_modify.screen";
    document.frmPost.submit();
}
</Script>
<TABLE border="0" cellspacing="0" cellpadding="0" width="820">
   <TR>
     <TD>
	<table border="0" align="center" cellpadding="0" cellspacing="10">
	   <tr>
		<td><img src="img/list_tit.gif" width="19" height="19"></td>
		<td class="title">业务帐户信息</td>
	    </tr>
	</table>
	<table width="98%" border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
	    <tr>
		<td><img src="img/mao.gif" width="1" height="1"></td>
	    </tr>
	</table>
	<br>
	<form name="frmPost" method="post" action="service_account_query_result_by_sa.do">
			<input type="hidden" name="txtCCID" value="">
		  <input type="hidden" name="txtFrom" value="1">
      <input type="hidden" name="txtTo" value="500">
		  <input type="hidden" name="func_flag" value="5002"> 
		   <%
		        int customerID = 0;
			      int saID = 0;
			      String serviceCode = "";
			      ServiceAccountWrap wrap = new ServiceAccountWrap();
       %> 
   <rs:hasresultset>
		<% 
		   QueryCommandResponseImpl RepCmd = (QueryCommandResponseImpl) pageContext
						.getRequest().getAttribute("ResponseQueryResult");
				Collection collection = (Collection) RepCmd.getPayload();
				String districtID="";
				Iterator iterator = collection.iterator();
				if (iterator.hasNext()) {
					wrap = (ServiceAccountWrap) iterator.next();
					pageContext.setAttribute("sa", wrap.getSaDto());
					customerID = wrap.getSaDto().getCustomerID();
					saID = wrap.getSaDto().getServiceAccountID();
          serviceCode = wrap.getSaDto().getServiceCode();                              
          districtID=Postern.getAddressCountyByCustomerID(customerID);
				}
				String deviceStr = "";
				List devList=Postern.getDeviceInfoByServiceAccountID(saID);
				if(devList!=null){
					for(int i=0;i<devList.size();i++){
						TerminalDeviceDTO tdto=(TerminalDeviceDTO)devList.get(i);
						if("C".equals(tdto.getStatus())) continue;
				    String dClass = tdto.getDeviceClass();
				    String serialNo = tdto.getSerialNo();
				    String className="";
				    if(dClass!=null)
				    	className = Postern.getClassNameByDeviceClass(dClass);
				    	 if(deviceStr != "")
				    	 {
				    	   deviceStr = deviceStr+","+className+":"+serialNo; 
				    	 }
				    	 else
				    	 {
				    	   deviceStr = className+":"+serialNo;
				    	 }
				    	 String purposeStrList = Postern.getPurposeStrListByID(serialNo);
				    	   if(purposeStrList!= null && !purposeStrList.equals(""))
				    	   {
				    	   	deviceStr = deviceStr+"(用途:"+purposeStrList+")";
				    	   }
					}
					
				}
			      
				
%>
			<table width="98%" border="0" align="center" cellpadding="5"
				cellspacing="1" class="list_bg">
				<tr>
					<td class="list_bg2">
					<div align="right">业务帐户ID</div>
					</td>
					<td class="list_bg1"><input name="txtServiceAccountID" type="text"
						class="textgray" readonly maxlength="20" size="22"
						value="<tbl:write name='sa' property='serviceAccountID' />"></td>
					<td class="list_bg2">
					<div align="right">业务名称</div>
					</td>
					<td class="list_bg1"><input name="txtSAName" type="text"
						class="textgray" readonly maxlength="10" size="22"
						value="<%=Postern.getServiceNameByID(wrap.getSaDto().getServiceID())%>"></td>
				</tr>
				<tr>
					<td class="list_bg2">
					<div align="right">类型</div>
					</td>
					<td class="list_bg1"><input name="txtUserType" type="text"
						class="textgray" readonly maxlength="20" size="22"
						value="<d:getcmnname typeName="SET_C_USER_TYPE" match="sa:userType" />"></td>
					 
					<td class="list_bg2">
					<div align="right">创建时间</div>
					</td>
					<td class="list_bg1"><input name="txtSAC" type="text"
						class="textgray" readonly maxlength="10" size="22"
						value="<tbl:write name='sa' property='createTime' />"></td>
				</tr>
				<tr>
					<td class="list_bg2">
					<div align="right">状态</div>
					</td>
					<td class="list_bg1"><input name="txtSAStatus" type="text"
						class="textgray" readonly maxlength="20" size="22"
						value="<d:getcmnname typeName="SET_C_SERVICEACCOUNTSTATUS" match="sa:status" />"></td>
					<td class="list_bg2">
					<div align="right">最后更新时间</div>
					</td>
					<td class="list_bg1"><input name="txtDTLastmod" type="text"
						class="textgray" readonly maxlength="10" size="22"
						value="<tbl:write name='sa' property='dtLastmod' />"></td>
					
				</tr>
       	<d:displayControl id="button_serviceaccount_detail_servicecodemodify" bean="sa">
				<tr>
					<td class="list_bg2">
					<div align="right">服务号码</div>
					</td>
					<td class="list_bg1"><input name="txtServiceCode" type="text"
						class="textgray" readonly maxlength="20" size="22"
						value="<tbl:write name='sa' property='serviceCode' />"></td>
					<td class="list_bg2">
					<div align="right"></div>
					</td>
					<td class="list_bg1"></td>
				</tr>
        </d:displayControl>
        <tr>
				<td class="list_bg2">
				<div align="right">设备</div>
				</td>
				<td class="list_bg1" colspan="3"><input name="txtDescription" type="text"
					class="textgray" readonly maxlength="800" size="80"
					value="<%=deviceStr%>" >
				</td>
			</tr>		
       		<tr>
				<td class="list_bg2">
				<div align="right">备注信息</div>
				</td>
				<td class="list_bg1" colspan="3"><input name="txtDescription" type="text"
					class="textgray" readonly maxlength="80" size="80"
					value="<tbl:write name='sa' property='description' />"></td>
			</tr>		
			</table>
			<br>
			<table width="98%" border="0" align="center" cellpadding="0"
				cellspacing="0" background="img/line_01.gif">
				<tr>
					<td><img src="img/mao.gif" width="1" height="1"></td>
				</tr>
			</table>
			<BR>
			<table width="98%" border="0" align="center" cellpadding="5"
				cellspacing="1" class="list_bg">
				<tr class="list_head">
					<td class="list_head">
					<div align="center"><input type="checkbox" name="selall"
						onclick="javascript:javascript:select_all_by_name(document.frmPost,'ListID', this.checked)"></div>
					</td>
					<td class="list_head" nowrap>PSID</td>
					<td class="list_head" nowrap>产品名称</td>
					<td class="list_head" nowrap>付费帐户</td>
					<td class="list_head" nowrap>产品包</td>
					<td class="list_head" nowrap>状态</td>
					<td class="list_head" nowrap>创建时间</td>
					<td class="list_head" nowrap>激活时间</td>
					<td class="list_head" nowrap>暂停时间</td>
					<td class="list_head" nowrap>套餐</td>
					<td class="list_head" nowrap>截止日期</td>
				</tr>
				<%String strProdName = "";
				String strPackName = "";
				int campainID = 0;
				int groupbargainID = 0;
				String cpStatusCol="";
%>
				<lgc:bloop requestObjectName="ResponseQueryResult" item="oneline">
					<%wrap = (ServiceAccountWrap) pageContext
							.getAttribute("oneline");
					CustomerProductDTO cpDto = wrap.getCpDto();
					ServiceAccountDTO saDto = wrap.getSaDto();
					customer=saDto.getCustomerID();
					ProductDTO pDto = Postern.getProductDTOByProductID(cpDto
							.getProductID());
					String newsaFlag = "";
					if(pDto != null) newsaFlag=pDto.getNewsaFlag();
					
					String productClass = pDto.getProductClass();
					cpStatusCol=cpStatusCol+","+cpDto.getStatus()+"-"+(newsaFlag==null?"N":newsaFlag)+"-"+productClass;

					if (newsaFlag == null)
						newsaFlag = "";

					pageContext.setAttribute("sa", saDto);
					pageContext.setAttribute("cp", cpDto);
					pageContext.setAttribute("cpStatusCol", cpStatusCol);
					
					if(pDto != null)
						strProdName = pDto.getProductName();
					//取得状态为“有效”和“创建”的套餐，有效的在前，创建的在后
					List cPCampaignMapList = Postern
							.getCPCampaignMapListByCustProductID(cpDto.getPsID(),"'V','N'",true);
					//取得所有套餐
					List cPCampaignMapAllList = Postern
							.getCPCampaignMapListByCustProductID(cpDto.getPsID(),null,false);

					boolean hase=true; 
					if(cPCampaignMapAllList == null || cPCampaignMapAllList.size() == 0)
						hase = false;					
					
					String acctName = null;
					if (Postern.getAcctNameById(cpDto.getAccountID())!=null)
					    acctName=Postern.getAcctNameById(cpDto.getAccountID());
					   else
					    acctName = String.valueOf(cpDto.getAccountID()); 				 
					strPackName = Postern.getPackagenameByID(cpDto
							.getReferPackageID());
					if (strProdName == null) {
						strProdName = "";
					}
					if (strPackName == null) {
						strPackName = "";
					}
                              
					%>
					<tbl:printcsstr style1="list_bg1" style2="list_bg2"
						overStyle="list_over">
						<td align="center" class="t12">
							<%//if (!newsaFlag.equalsIgnoreCase("Y")) {
								if(!CommonKeys.PRODUCTCLASS_H.equals(productClass)){
							%> 
							<input type="checkbox" name="ListID" value="<tbl:write name="cp" property="PsID"/>" 
							<%if(txtCPIDs.indexOf(cpDto.getPsID()+"") != -1){%> checked <%}%> >
							
							<input type="hidden" name="chckState" value="<%=cpDto.getStatus()%>"> 
							<input type="hidden" name="txtProductID" value="<%=cpDto.getProductID()%>"> 
						  	<input type="hidden" name="txtProductName" value="<%=strProdName%>">
							<%}%>
							<%//} %>
							<% if (cpDto.getDeviceID() != 0) { %>
						  	<input type="hidden" name="txtDeviceId" value="<%=cpDto.getDeviceID()%>"> 
						  	<input type="hidden" name="txtSystemEventProductID" value="<%=cpDto.getProductID()%>">
							<% } %></td>
							
						<td align="center" width="50" nowrap><tbl:write name="cp" property="PsID" /></td>
						<td width="100" align="center"><a href="javascript:view_detail_click('<tbl:write name="cp" property="PsID"/>')"
							class="link12"><%=strProdName%></a>
						</td>
						<td align="center"><tbl:write name="cp" property="AccountID" /></td>
						<td align="center"><%=strPackName%></td>
						<td align="center"><d:getcmnname typeName="SET_C_CUSTOMERPRODUCTPSTATUS"	match="cp:status" /></td>
						<td align="center" width="60" nowrap><tbl:writedate name="cp" property="createTime" /><br><tbl:writedate name="cp" property="createTime" onlyTime="true"/></td>
						<td align="center" width="60" nowrap><tbl:writedate name="cp" property="activateTime" /><br><tbl:writedate name="cp" property="activateTime" onlyTime="true"/></td>
						<td align="center" width="60" nowrap><tbl:writedate name="cp" property="pauseTime" /><br><tbl:writedate name="cp" property="pauseTime" onlyTime="true"/></td>
						<td align="center" nowrap width="160">
							<%
							for(int i=0;i<cPCampaignMapList.size();i++)
							{
							 CPCampaignMapDTO tempDTO = (CPCampaignMapDTO)cPCampaignMapList.get(i);
							 CustomerCampaignDTO custCampaignDto = Postern.getCustomerCampaignByccID(tempDTO.getCcId());
							 String capName = Postern.getCampaignNameByID(custCampaignDto.getCampaignID());
							 if(capName == null)capName = "";
							 String capNameShow = capName;
							 boolean showHint = false;
							 if(capName.length()>13)
							 {
								capNameShow = capName.substring(0,11)+"...";
								showHint = true;
							 }
							 if(showHint)
							 {
							 %><SPAN TITLE="<%=capName%>"><%=capNameShow%></SPAN><br><%
							 }
							else
								{
								 %><%=capNameShow%><br><%
								}
							}
							if(hase)
							{%>
								<a href="JavaScript:view_all_campaign('<%=cpDto.getPsID()%>')" class="link12">全部</a>
							<%}%>
						</td>
						<td align="center" width="60" nowrap>
					  <%
					    String endTime ="";
					    if (hase){
						     CPCampaignMapDTO	cpCpgDto =Postern.getCPCampaignMapByCustProductID(cpDto.getPsID());
						     CustomerCampaignDTO ccDto =Postern.getCustomerCampaignByccID(cpCpgDto.getCcId());
						     endTime = WebUtil.TimestampToString(ccDto.getDateTo(),"yyyy-MM-dd");
						  }else{
						  	 endTime = WebUtil.TimestampToString(cpDto.getEndTime(),"yyyy-MM-dd");
						  }
						  if (endTime ==null) endTime="";
						%>
						<%=endTime%> 
						</td>
					</tbl:printcsstr>
				</lgc:bloop>
				<%System.out.println("cpStatusCol:"+cpStatusCol);%>
				<tbl:generatetoken />
				<tr>
					<td colspan="11" class="list_foot"></td>
				</tr>
				<input type="hidden" name="txtCustomerID" size="22" value="<%=Integer.toString(customerID)%>">
				<input type="hidden" name="txtDistrictID" size="22" value="<%=districtID%>">
				<input type="hidden" name="txtProductIDs" size="22" value="">
				<input type="hidden" name="txtCPIDs" size="22" value="">
				<input type="hidden" name="txtActionType" size="22" value="">
				<input type="hidden" name="txtDoPost" size="22" value="FALSE">
			 
  			 <tr>
              <td align="right" class="t12" colspan="11" >
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
                 <input name="imageField" type="image" src="img/button_go.gif" width="28" height="15" border="0" onclick="return goto_onClick(document.frmPost, <rs:prop property="pageamount" />)"/>
             </td>
         </tr>
			</table>
		
			<table width="98%" border="0" align="center" cellpadding="5"
				cellspacing="1" class="list_bg">
				<tr align="center">
					<td >
					<table border="0" cellspacing="0" cellpadding="0">
						<tr>
						 <d:displayControl id="button_serviceaccount_detail_problem" bean="sa">
							<td width="20"></td>
							<td width="11" height="20"><img src="img/button_l.gif" width="11"
								height="20"></td>
							<td><input name="Submit" type="button" class="button"
								value="报&nbsp;修" onclick="javascript:create_problem_submit()"></td>
							<td width="22" height="20"><img src="img/button_r.gif" width="22"
								height="20"></td>
				    </d:displayControl>
			 <%
			     Collection protocolList =Postern.getProtocolDTOCol(customerID);
	         if (protocolList !=null && !protocolList.isEmpty()){
			 %>
			 <pri:authorized name="protocolDate_modify.do"> 
        	   <td width="20" ></td>
       	     <td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
             <td><input name="button_6" type="button" class="button" value="截止日期维护" onclick="javascript:protocolDate_modify()"></td>
       	     <td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
        </pri:authorized>     
				<%
				   }
			     if (wrap.getSaDto().getServiceID() !=6){
	      %>
						<d:displayControl id="button_serviceaccount_detail_device_upgrade" bean="cpStatusCol">
						<pri:authorized name="customer_device_upgrade.do" >
						<input name="modify" type="button" class="button" value="设备升级" onclick="javascript:device_upgrade_submit()">
						</pri:authorized>
						</d:displayControl>
    	      <d:displayControl id="button_serviceaccount_detail_suspend" bean="cpStatusCol">
						<pri:authorized name="customer_product_stop_op.do">
								<td width="20"></td>
								<td width="11" height="20"><img src="img/button_l.gif"
									width="11" height="20"></td>
								<td><input name="Submit" type="button" class="button"
									value="暂&nbsp;停" onclick="javascript:suspend_submit()"></td>
								<td width="22" height="20"><img src="img/button_r.gif"
									width="22" height="20"></td>
							</pri:authorized>
	           </d:displayControl>
           	<d:displayControl id="button_serviceaccount_detail_stop" bean="cpStatusCol">
							&nbsp;&nbsp;
							<pri:authorized name="customer_product_stop_op.do">
								<td width="20"></td>
								<td width="11" height="20"><img src="img/button_l.gif"
									width="11" height="20"></td>
								<td><input name="Submit" type="button" class="button"
									value="退&nbsp;订" onclick="javascript:cancel_submit()"></td>
								<td width="22" height="20"><img src="img/button_r.gif"
									width="22" height="20"></td>
							</pri:authorized>
	          </d:displayControl>
	          <d:displayControl id="button_serviceaccount_detail_resume" bean="cpStatusCol">
							<pri:authorized name="customer_product_resume_op.do">
								<td width="20"></td>
								<td width="11" height="20"><img src="img/button_l.gif"
									width="11" height="20"></td>
								<td><input name="Submit" type="button" class="button"
									value="恢&nbsp;复" onclick="javascript:resume_submit()"></td>
								<td width="22" height="20"><img src="img/button_r.gif"
									width="22" height="20"></td>
							</pri:authorized>
	          </d:displayControl>
	       	  <d:displayControl id="button_serviceaccount_detail_resentEMM" bean="sa">
							<td width="20"></td>
							<td width="11" height="20">
								<img src="img/button_l.gif" width="11" height="20"></td>
							<td><input name="Submit" type="button" class="button"
								value="重发授权" onclick="javascript:resendEMM_submit()"></td>
							<td width="22" height="20"><img src="img/button_r.gif" width="22"
								height="20"></td>
	          </d:displayControl>
	         	<d:displayControl id="button_serviceaccount_detail_servicecodemodify" bean="sa">
							<td width="20"></td>
							<td width="11" height="20">
								<img src="img/button_l.gif" width="11" height="20"></td>
							<td><input name="Submit" type="button" class="button"
								value="服务号码修改" onclick="javascript:phoneNumber_submit()"></td>
							<td width="22" height="20"><img src="img/button_r.gif" width="22"
								height="20"></td>
			   	</d:displayControl>
			  	<d:displayControl id="button_serviceaccount_move" bean="sa">
				  <%
				    Map nomalsa=Postern.getNPServiceAccountMapByCustID(customer); 
			    	if(nomalsa.size()>1){
			    %>
				      <td width="20"></td>
							<td width="11" height="20">
								<img src="img/button_l.gif" width="11" height="20"></td>
							<td><input name="Submit" type="button" class="button"
								value="产品迁移" onclick="javascript:productmove_submit()"></td>
							<td width="22" height="20"><img src="img/button_r.gif" width="22"
								height="20"></td>
					 <%} %>
					</d:displayControl>
					<%}%>
						</tr>
					</table>
					</td>
				</tr>
			</table>
		</rs:hasresultset>
</form>