<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="resultset" prefix="rs" %>
<%@ taglib uri="/WEB-INF/oss.tld" prefix="d" %>
                 
<%@ page import="com.dtv.oss.util.Postern, java.util.*" %>
<%@ page import="com.dtv.oss.web.util.WebUtil" %>
<%@ page import="com.dtv.oss.web.util.CommonKeys" %>
<%@ page import="com.dtv.oss.web.util.WebOperationUtil" %>
<%@ page import="com.dtv.oss.dto.MethodOfPaymentDTO" %>
<%@ page import="com.dtv.oss.dto.OrganizationDTO" %>
<%@ page import="com.dtv.oss.service.commandresponse.CommandResponseImp" %>
<%@ page import="com.dtv.oss.dto.wrap.customer.ServiceAccountWrap"%>
<%@ page import="com.dtv.oss.dto.CustomerProductDTO, 
                 com.dtv.oss.dto.ServiceAccountDTO, 
                 com.dtv.oss.dto.CPCampaignMapDTO,
                 com.dtv.oss.dto.CustomerCampaignDTO"%>


<% 
  String executeType ="";
  
  String txtEventType=request.getParameter("txtEventType")  ;
  if ("PS".equals(txtEventType)){
     executeType="执行暂停";
  } else if ("PC".equals(txtEventType)){
     executeType="执行取消";
  } else if ("PR".equals(txtEventType)){
     executeType="执行恢复";
  }
 
  String  txtCustomerID =request.getParameter("txtCustomerID");
  String  txtExecuteDate =request.getParameter("txtExecuteDate");
  String  txtReason = request.getParameter("txtEventReason");
  String  memo=(txtReason==null ||txtReason.equals("")) ? "" :Postern.getCommonSettingDataValueByNameAndKey("SET_V_CC_CUSTSERVICEINTERACTIONSTATUSREASON",txtReason);
%>


<script language=javascript>

  function create_submit(){
 
     document.frmPost.txtDoPost.value ="true";
     document.frmPost.action="create_schedule_step2.do";
     document.frmPost.submit();
  }
  
  function back(){
     history.back(-1);
  }

</script>

<form name="frmPost" method="post" action="create_schedule_step2.do">

<input type="hidden" name ="txtMemo" value="<%=memo%>">

<table  border="0" align="center" cellpadding="0" cellspacing="10" >
  <tr>
    <td><img src="img/list_tit.gif" width="19" height="19"></td>
    <td class="title">排程任务创建----确认</td>
  </tr>
</table>

<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>
<br>

<table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
  <tr class="list_head">
    <td class="list_head">产品名称</td>
    <td class="list_head">产品包名</td>
    <td class="list_head">帐户号</td>
    <td class="list_head">优惠说明</td>
    <td class="list_head">优惠起始日期</td>
    <td class="list_head">优惠结束日期</td>
  </tr>

<%
        //产品ID
	String cpIDs =(request.getParameter("txtCPIDs")==null) ? "" : request.getParameter("txtCPIDs");
	Collection cpIDList=new ArrayList();
	String[] psID =cpIDs.split(";");
	for(int i=0; i<psID.length; i++)
	{
		cpIDList.add(new Integer(WebUtil.StringToInt(psID[i])));
	}

    	String strProdName="";
    	String strPackName="";
 
    	CommandResponseImp cpIDListImp= new CommandResponseImp(cpIDList);
    	request.setAttribute("ResponseQueryResult",cpIDListImp);
%>
   
<lgc:bloop requestObjectName="ResponseQueryResult" item="oneline" >
<%
    int cpID = ((Integer)pageContext.getAttribute("oneline")).intValue();
    CustomerProductDTO cpDto = Postern.getCustomerProductDTOByPSID(cpID);

    strProdName = Postern.getProductNameByID(cpDto.getProductID());
    CPCampaignMapDTO cPCampaignMapDTO = Postern.getCPCampaignMapByCustProductID(cpID);   
    CustomerCampaignDTO custCampaignDto =Postern.getCustomerCampaignByccID(cPCampaignMapDTO.getCcId());
    if (custCampaignDto ==null) custCampaignDto =new CustomerCampaignDTO();
    pageContext.setAttribute("CustomerCampaignDTO",custCampaignDto);
    
    strPackName = Postern.getPackagenameByID(cpDto.getReferPackageID());
    if (strProdName==null) strProdName="";
    if (strPackName==null) strPackName="";
    
%>
<tbl:printcsstr style1="list_bg1" style2="list_bg2" overStyle="list_over" > 
    <td><%=strProdName%></td>
    <td><%=strPackName%></td>
    <td><tbl:writeparam name="txtAccountID" /></td>
	
	<%
   		if (custCampaignDto == null || custCampaignDto.getCampaignID()==0) {
	%>
	<td align="center" class="t12">(没有优惠)</td>
	<%  } else {
		String capName = Postern.getCampaignNameByID(custCampaignDto.getCampaignID());
		if (capName == null) capName = ""; 
	%>
	<td align="center" class="t12">
	
	<%=capName%>
	</td>
	<%
		}
	%>

	<%if(custCampaignDto!=null || custCampaignDto.getDateFrom()!=null || custCampaignDto.getDateTo() !=null){%>
    		<td><tbl:writedate  name="CPCampaignMapDTO" property="dateFrom" /></td>
    		<td><tbl:writedate  name="CPCampaignMapDTO" property="dateTo" /></td>
	<%} else {%>
		
    		
    		<td></td>
    		<td></td>
	<%
		}
	%>

</tbl:printcsstr>
</lgc:bloop>
<tbl:generatetoken />

  <tr>
    <td colspan="6" class="list_foot"></td>
  </tr>
<tbl:hiddenparameters pass="txtEventReason/txtExecuteDate/txtEventType/txtAccountID" />
<tbl:hiddenparameters pass="txtActionType/txtCPIDs/txtCustomerID/func_flag" />

</table>

    <br>
    <%
       String txtReasonShow =(txtReason==null ||txtReason.equals("")) ? "" :"因("+ Postern.getCsiReasonByCsiTypeAndReason(txtEventType,txtReason)+")";
    %>
    <div align="center"><font color="red">以上产品<%=txtReasonShow%>要求于<%=txtExecuteDate%>时间<%=executeType%>,请确认！</font></div>               

                
<BR>
<input type="hidden" name="txtDoPost" value ="false" >
<table border="0" cellspacing="0" cellpadding="0">
  <tr>
  	<td><img src="img/button2_r.gif" width="22" height="20"></td>
    <td background="img/button_bg.gif"  height="20" >
       <a href="javascript:back()" class="btn12">上一步</a></td>
    <td><img src="img/button2_l.gif" width="13" height="20"></td>
  	    
    <td width="20" ></td>		
    <td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
    <td><input name="Submit" type="button" class="button" value="确  认" onclick="javascript:create_submit()"></td>
    <td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
  	
  </tr>
</table>     

</form>  
         

      