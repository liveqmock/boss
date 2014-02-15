<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="bookmark" prefix="bk" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="resultset" prefix="rs" %>
<%@ taglib uri="/WEB-INF/oss.tld" prefix="d" %>
<%@ page import="com.dtv.oss.service.commandresponse.QueryCommandResponseImpl"%>
<%@ page import="com.dtv.oss.dto.wrap.customer.ServiceAccountWrap,
                com.dtv.oss.web.util.CommonKeys,
                com.dtv.oss.util.Postern"%>
<%@ page import="com.dtv.oss.dto.CustomerProductDTO, 
                 com.dtv.oss.dto.ServiceAccountDTO, 
                 com.dtv.oss.dto.CPCampaignMapDTO,
                 com.dtv.oss.dto.CustomerCampaignDTO,
                 com.dtv.oss.dto.ProductDTO"%>

<%@ page import="java.util.*"%>
<%@ page import="com.dtv.oss.util.*"%>
<%@ page import="java.lang.Throwable" %>
<%@ page import="com.dtv.oss.web.util.WebKeys" %>


<Script language=JavaScript>
function resume_submit()
{
  if(!checkselectSuspend()){
	alert("��ѡ��Ҫ�ָ��Ĳ�Ʒ");
	return;
  }
	if(document.frmPost.txtCsiCreateReason!=null&&!check_csiReason()){
		return false;
	}
  if (window.confirm("��ȷ��Ҫ�ָ���ҵ���ʻ���?")){
	document.frmPost.action="service_account_resume_create.do";
	document.frmPost.submit();
  }
}

function checkselectSuspend() {
    var iCount = 0;
    var cpIDs = "";
    if (document.frmPost.ListID.length!=null)
    {
        for (var i=0;i<document.frmPost.ListID.length;i++)
        {
    	    if (document.frmPost.ListID[i].checked)
    	    {
    	        iCount++;
    	        if(iCount == 1)
		{
		   cpIDs = cpIDs + document.frmPost.ListID[i].value;
		}
		else
		{
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
            cpIDs =document.frmPost.ListID.value;
        }
    }
    if (iCount<=0) return false;
    document.frmPost.txtCPIDs.value =  cpIDs;
    return true;
}
 
function view_all_campaign(psId){
   window.open('list_all_campaign.do?txtPsId='+psId+'&txtActionType=campaignList','','toolbar=no,location=no,status=no,menubar=no,scrollbars=yes,width=430,height=350');
}
function view_detail_click(strId){
	self.location.href="customer_product_view.do?txtPsID="+strId;
}  



</Script>

<form name="frmPost" method="post">

<TABLE border="0" cellspacing="0" cellpadding="0" width="820" >
<TR>
<TD>
<table  border="0" align="center" cellpadding="0" cellspacing="10" >
  <tr>
    <td><img src="img/list_tit.gif" width="19" height="19"></td>
    <td class="title">ҵ���ʻ���Ϣ</td>
  </tr>
</table>
<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>
<br>

<%	
  int customerID = 0;
	int saID = 0;
	ServiceAccountWrap wrap = new ServiceAccountWrap();
	String txtActionType = request.getParameter("txtActionType");
%>
<rs:hasresultset>
<%
	QueryCommandResponseImpl RepCmd = (QueryCommandResponseImpl)pageContext.getRequest().getAttribute("ResponseQueryResult");
	Collection collection = (Collection) RepCmd.getPayload();
	String serviceCode ="";
	Iterator iterator = collection.iterator();
	if(iterator.hasNext()){
		wrap = (ServiceAccountWrap)iterator.next();
		pageContext.setAttribute("sa", wrap.getSaDto());
		customerID = wrap.getSaDto().getCustomerID();
		saID = wrap.getSaDto().getServiceAccountID();
		serviceCode =wrap.getSaDto().getServiceCode();
	}
%>


<table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
  <tr>
    <td class="list_bg2" width="17%"><div align="right">ҵ���ʻ�ID</div></td>
    <td class="list_bg1" width="33%"><input name="txtSAID" type="text" class="textgray" readonly maxlength="20" size="22" value="<tbl:write name='sa' property='serviceAccountID' />"></td>
    <td class="list_bg2" width="17%"><div align="right">ҵ������</div></td>
    <td class="list_bg1" width="33%"><input name="txtSAName" type="text" readonly class="textgray" maxlength="10" size="22" value="<%=Postern.getServiceNameByID(wrap.getSaDto().getServiceID())%>"></td>
  </tr>
  <tr>
    <td class="list_bg2"><div align="right">״̬</div></td>
    <td class="list_bg1"><input name="txtSAStatus" type="text" readonly class="textgray" maxlength="20" size="22" value="<d:getcmnname typeName="SET_C_SERVICEACCOUNTSTATUS" match="sa:status" />"></td>
    <td class="list_bg2"><div align="right">��������</div></td>
    <td class="list_bg1"><input name="txtSAC" type="text" readonly class="textgray" maxlength="10" size="22" value="<tbl:write name='sa' property='createTime' />"></td>
  </tr>
  <%
     if (serviceCode !=null && !serviceCode.trim().equals("")){
  %>
    <tr>
      <td class="list_bg2">
	 <div align="right">�������</div>
      </td>
      <td class="list_bg1">
         <input name="txtServiceCode" type="text" class="textgray" readonly maxlength="20" size="22" value="<tbl:write name='sa' property='serviceCode' />">
      </td>
      <td class="list_bg2">
	 <div align="right"></div>
      </td>
      <td class="list_bg1"></td>
    </tr>
  <% 
     }
  %> 

</table>

<BR>
<table width="98%" border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
		<tr class="list_head">
			<td class="list_head">
			   <div align="center"><input type="checkbox" name="selall" onclick="javascript:javascript:select_all_by_name(document.frmPost,'ListID', this.checked)"></div>
			 </td>
			 <td class="list_head" nowrap>PSID</td>
			 <td class="list_head" nowrap>��Ʒ����</td>
			 <td class="list_head" nowrap>�����ʻ�</td>
			 <td class="list_head" nowrap>��Ʒ��</td>
			 <td class="list_head" nowrap>״̬</td>
			 <td class="list_head" nowrap>����ʱ��</td>
			 <td class="list_head" nowrap>����ʱ��</td>
			 <td class="list_head" nowrap>��ͣʱ��</td>
			 <td class="list_head" nowrap>�ײ�</td>
		</tr>
		<%
		    String strProdName = "";
				String strPackName = "";
				int campainID = 0;
				int groupbargainID = 0;
				String cpStatusCol="";
%>
 	<lgc:bloop requestObjectName="ResponseQueryResult" item="oneline">
		 <%
		    wrap = (ServiceAccountWrap) pageContext.getAttribute("oneline");
				CustomerProductDTO cpDto = wrap.getCpDto();
				ServiceAccountDTO saDto = wrap.getSaDto();
					
				ProductDTO pDto = Postern.getProductDTOByProductID(cpDto.getProductID());
				
				pageContext.setAttribute("cp", cpDto);
						
				if(pDto != null) strProdName = pDto.getProductName();
				CPCampaignMapDTO cPCampaignMapDTO = Postern.getValidCPCampaignMapByCustProductID(cpDto.getPsID());
				//ȡ�����һ����Ʒ�ײ�
				CPCampaignMapDTO cPCampaignMapDTOLast = Postern.getCPCampaignMapByCustProductID(cpDto.getPsID());
				boolean hase=true; 
				if(cPCampaignMapDTOLast == null || cPCampaignMapDTOLast.getCcId() == 0)
						hase = false;
			
				CustomerCampaignDTO custCampaignDto =Postern.getCustomerCampaignByccID(cPCampaignMapDTO.getCcId());
        if (custCampaignDto ==null) custCampaignDto =new CustomerCampaignDTO();
				pageContext.setAttribute("CustomerCampaignDTO",custCampaignDto);
				campainID = custCampaignDto.getCampaignID();

				boolean sFlag=false; 
				java.sql.Timestamp currentTime = TimestampUtility.getCurrentDateWithoutTime();

				java.sql.Timestamp endTime = custCampaignDto.getDateTo();
				java.sql.Timestamp startTime = custCampaignDto.getDateFrom();
				 
				if(endTime!=null && startTime!=null )
					  sFlag = currentTime.before(endTime) && currentTime.after(startTime);
					 
				if (currentTime.equals(startTime)) sFlag =true;
					
				String acctName = null;
				if (Postern.getAcctNameById(cpDto.getAccountID())!=null)
				    acctName=Postern.getAcctNameById(cpDto.getAccountID());
				else
				   acctName = String.valueOf(cpDto.getAccountID()); 
					 
				strPackName = Postern.getPackagenameByID(cpDto.getReferPackageID());
				if (strProdName == null) strProdName = "";

				if (strPackName == null) strPackName = "";
					
                              
		%>
		  <tbl:printcsstr style1="list_bg1" style2="list_bg2" overStyle="list_over">
			      <td align="center" class="t12">
				    <%
             if (cpDto.getDeviceID() == 0){
            %>
							<input type="checkbox" name="ListID" value="<tbl:write name="cp" property="PsID"/>"> 
							<%} else {%>
						  &nbsp;&nbsp;
							<%}%></td>
						<td><tbl:write name="cp" property="PsID" /></td>
						<td><a href="javascript:view_detail_click('<tbl:write name="cp" property="PsID"/>')"
							class="link12"><%=strProdName%></a>
						</td>
						
						<td align="center"><tbl:write name="cp" property="AccountID" /></td>
						<td><%=strPackName%></td>
						<td><d:getcmnname typeName="SET_C_CUSTOMERPRODUCTPSTATUS"	match="cp:status" /></td>
						<td nowrap><tbl:writedate name="cp" property="createTime" /><br><tbl:writedate name="cp" property="createTime" onlyTime="true"/></td>
						<td nowrap><tbl:writedate name="cp" property="activateTime" /><br><tbl:writedate name="cp" property="activateTime" onlyTime="true"/></td>
						<td nowrap><tbl:writedate name="cp" property="pauseTime" /><br><tbl:writedate name="cp" property="pauseTime" onlyTime="true"/></td>
						 
						<% 
						  String capName = Postern.getCampaignNameByID(campainID);
						  if (capName == null) capName = "";
						  if(sFlag==true){ 
						%>
						<td align="center">
						 
						  <%=capName%>
						   <a
							href="JavaScript:view_all_campaign('<%=cpDto.getPsID()%>')"
							title="��ǰ�û�ȫ���ײ�" class="link12">ȫ��</a></td>
							
						<% 
						 } else if(hase){ %>
						 
						  <td align="center">
						  	<a
							href="JavaScript:view_all_campaign('<%=cpDto.getPsID()%>')"
							title="��ǰ�û�ȫ���ײ�" class="link12">ȫ��</a>
						</td>
						<% } else{ %>
						  <td align="center">
						</td>
						<% } %>
			</tbl:printcsstr>
	</lgc:bloop>

		<tr>
			<td colspan="10" class="list_foot"></td>
		</tr>
	</table>
 

<TABLE width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
  <TR>
  <td  width="17%" align="right" >��ѡ����Ч�����ʻ�*  </td>
	  <td  width="33%" align="left" ><d:selAccByCustId name="txtAccount" mapName="self" canDirect="true"  match="txtAccount" empty="false" width="25" /></td>
	  <tbl:csiReason csiType="UR" name="txtCsiCreateReason" match="txtCsiCreateReason" action="N" showType="text" checkScricptName="check_csiReason()" controlSize="25" tdControlColspan="3" forceDisplayTD="true"/>
  </TR>

</TABLE> 
<br>
<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>
<br>
<table border="0" cellspacing="0" cellpadding="0" align="center">
    <tr align="center">
	<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
	<td><input name="Submit" type="button" class="button" value="��&nbsp;��" onclick="javascript:resume_submit()"></td>
	<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
   </tr>
</table>
</rs:hasresultset>   



<input type="hidden" name="txtDoPost" value="FALSE" >
<input type="hidden" name="txtCustomerID" size="22" value="<%=Integer.toString(customerID)%>">
<input type="hidden" name="txtServiceAccountID" size="22" value="<%=saID%>">
<input type="hidden" name="txtActionType" value="<%=txtActionType%>" />
<input type="hidden" name="txtCPIDs" size="22" value="">
<input type="hidden" name="func_flag" value="11022">
<tbl:generatetoken />
</form>
