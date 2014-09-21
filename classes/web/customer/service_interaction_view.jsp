<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="bookmark" prefix="bk" %>
<%@ taglib uri="osstags" prefix="d" %>
<%@ taglib uri="privilege" prefix="pri" %>

<%@ page import="java.util.*" %>
<%@ page import="com.dtv.oss.util.Postern" %>
<%@ page import="com.dtv.oss.web.util.CommonKeys,
                 com.dtv.oss.web.util.WebUtil" %>
<%@ page import="com.dtv.oss.dto.TerminalDeviceDTO" %>
<%@ page import="com.dtv.oss.dto.CsiCustProductInfoDTO" %>
<%@ page import="com.dtv.oss.dto.NewCustomerInfoDTO,
                 com.dtv.oss.dto.CustomerDTO" %>
<%@ page import="com.dtv.oss.dto.NewCustAccountInfoDTO" %>
<%@ page import="com.dtv.oss.dto.CustServiceInteractionDTO" %>
<%@ page import="com.dtv.oss.dto.OldCustomerInfoDTO" %>
<%@ page import="com.dtv.oss.dto.OldCustAccountInfoDTO" %>
<%
String systemSettingPrecise="";
%>

<script language=javascript>
function callback_submit(){
	 document.frmPost.action = "openCallback_create_info.screen";
	 document.frmPost.submit();
}

function returnfee_submit(){
	
	 document.frmPost.action = "return_fee_for_open.screen";
	 document.frmPost.submit();
}

function csi_adjust_submit(){
	 document.frmPost.action = "csi_adjust.do";
	 document.frmPost.submit();
}
function callback_view(){
   document.frmPost.action ="openCallback_view_info.screen";
   document.frmPost.submit();
}
function customerRegister_print_submit(str){	
	 document.frmPost.target="_blank";
	 document.frmPost.action=str;	
	 document.frmPost.submit();
}

function alter_submit(){
   var strId = document.frmPost.txtID.value;
   self.location.href="book_alter_prepare.do?txtID="+strId;
}
function cancel_submit(){
   if(!check_csiReason())
		return;
     
   var strId =document.frmPost.txtID.value;
   if (window.confirm('您确认要取消该预约单吗?')){
    	document.frmDelPost.txtID.value = strId;
	    document.frmDelPost.submit();
   }
}

function customer_service_print(customerID,csiID)
{
	
		document.frmPost.target="_blank";
		document.frmPost.action="<d:config prefix="" showName="SYSTEMSYMBOLNAME" suffix="_" />customer_service_print.do?txtCustomerID="+customerID+"&csiID="+csiID;
		document.frmPost.submit();
		document.frmPost.target="_self";
	
}
function customer_service_receipt_print(customerID,csiID)
{
		document.frmPost.target="_blank";
		<%systemSettingPrecise = Postern.getSystemsettingValueByName("SET_W_USER_SERVICE_RECEIPT_PRINT");
        if(systemSettingPrecise!=null&&("miyun").equalsIgnoreCase(systemSettingPrecise)){%>
            document.frmPost.action="customer_service_receipt_print_moni.do?txtCustomerID="+customerID+"&csiID="+csiID;
        <%}else if (systemSettingPrecise!=null&&("lijian").equalsIgnoreCase(systemSettingPrecise)){%>
            document.frmPost.action="customer_service_receipt_print_lijiang.do?txtCustomerID="+customerID+"&csiID="+csiID;
        <%	
          }else	{%>
            document.frmPost.action="customer_service_receipt_print.do?txtCustomerID="+customerID+"&csiID="+csiID;
        <%}%>
		document.frmPost.submit();
		document.frmPost.target="_self";
}
function customer_service_receipt_for_preypay_print(customerID,csiID)
{
		document.frmPost.target="_blank";
		document.frmPost.action="customer_prepay_print.do?txtCustomerID="+customerID+"&csiID="+csiID;
		document.frmPost.submit();
		document.frmPost.target="_self";
	
}

function config_print(csiId,PrintSheetType,SheetSubType,CsiReason,PrintReason)
{
		
		document.frmPost.target="_blank";
		document.frmPost.action="config_print.do?txtCsiId="+csiId+"&txtPrintSheetType="+PrintSheetType+"&txtSheetSubType="+SheetSubType+"&txtCsiReason="+CsiReason+"&txtPrintReason="+PrintReason;
		document.frmPost.submit();
		document.frmPost.target="_self";
		
}

</script>


<table  border="0" align="center" cellpadding="0" cellspacing="10">
  <tr>
    <td><img src="img/list_tit.gif" width="19" height="19"></td>
    <td class="title">查看受理单明细</td>
  </tr>
</table>
<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>
<br>

<lgc:bloop requestObjectName="ResponseQueryResult" item="oneline" IsOne="true">
<%
   
	 CustServiceInteractionDTO csiDto = (CustServiceInteractionDTO)pageContext.getAttribute("oneline");
	 CustomerDTO custDto =Postern.getCustomerByID(csiDto.getCustomerID());
	 NewCustomerInfoDTO newCustomerDto =Postern.getNewCustomerInfoDTOByCSIID(csiDto.getId());
	 Map newcustmarkMap = Postern.getServeyResultByCustIdForRealUser(csiDto.getId(),"T_NEWCUSTOMERMARKETINFO","CSIID");
   System.out.println("newcustmarkMap========"+newcustmarkMap);
   Collection newCustAccountInfoCols =Postern.getNewCustAccountInfoByCsiId(csiDto.getId());
   
   OldCustomerInfoDTO oldCustomerDto =Postern.getOldCustomerInfoDTOByCSIID(csiDto.getId());
   Map oldcustmarkMap = Postern.getServeyResultByCustIdForRealUser(csiDto.getId(),"T_OLDCUSTOMERMARKETINFO","CSIID");
   Collection oldCustAccountInfoCols =Postern.getOldCustAccountInfoByCsiId(csiDto.getId());
   
	 pageContext.setAttribute("csiDTO",csiDto);
	 pageContext.setAttribute("customerDTO", (custDto==null) ? new CustomerDTO() : custDto);
	 pageContext.setAttribute("newCustomerDTO",(newCustomerDto==null) ? new NewCustomerInfoDTO() : newCustomerDto);
	 pageContext.setAttribute("newserveyMarketMap",(newcustmarkMap==null) ? new HashMap() : newcustmarkMap);
	 pageContext.setAttribute("newCustAcctInfoCols",(newCustAccountInfoCols==null) ? new ArrayList() : newCustAccountInfoCols );
	 pageContext.setAttribute("oldCustomerDTO",(oldCustomerDto==null) ? new OldCustomerInfoDTO() : oldCustomerDto); 
	 pageContext.setAttribute("oldserveyMarketMap",(oldcustmarkMap==null) ? new HashMap() : oldcustmarkMap );
	 pageContext.setAttribute("oldCustAcctInfoCols",(oldCustAccountInfoCols==null) ? new ArrayList() :  oldCustAccountInfoCols);
	 pageContext.setAttribute("SYSTEMSYMBOLNAME", Postern.getSystemSymbolName());
	 
	 String serviceAccoutID = Postern.getServiceAccountIdByCsiId(csiDto.getId());
	 System.out.println("oldCustomerDTO==========="+oldCustomerDto);
	 String scheduleTime ="";
	 if (CommonKeys.CUSTSERVICEINTERACTIONTYPE_BP.equals(csiDto.getType())){
	     scheduleTime =WebUtil.TimestampToString(csiDto.getScheduleTime(),"yyyy-MM-dd");
	 }
	 int usedMonth =csiDto.getPoint();
	 //受理单头信息
%>
   <tbl:dynamicShowAttributeTag dtoName="CustServiceInteractionDTO" bean="csiDTO" primaryKey="id" tdWordStyle="list_bg2" tdControlStyle="list_bg1" tdWidth1="17%" tdWidth2="33%" controlSize="25" tdHeight="30" />
<%
    if (newCustomerDto !=null){
   //受理客户信息
%>
   <tbl:dynamicShowAttributeTag dtoName="NewCustomerInfoDTO"  bean="newCustomerDTO" primaryKey="id" tdWordStyle="list_bg2" tdControlStyle="list_bg1" tdWidth1="17%" tdWidth2="33%" controlSize="25" tdHeight="30" title="受理客户信息" titleClass="import_tit" 
   	                     compareDtoname="OldCustomerInfoDTO"  compareBean="oldCustomerDTO" comparePrimaryKey="id" />
<%  }
   //客户市场信息
    if (newcustmarkMap !=null && !newcustmarkMap.isEmpty()){
%>
   <table align="center" width="810" border="0" cellspacing="1" cellpadding="3" class="list_bg" >
   	 <tbl:dynamicservey serveyType="M"  showType="label" tdHeight="30" tdWidth1="17%" tdWidth2="33%" tdWordStyle="list_bg2" tdControlStyle="list_bg1"  controlSize="25"  match="newserveyMarketMap" compareTo="oldserveyMarketMap"/>
   </table>
<%
   }
   //受理客户帐户信息
%>
    <tbl:dynamicShowAttributeTag dtoName="NewCustAccountInfoDTO" bean="newCustAcctInfoCols" primaryKey="Id" tdWordStyle="list_bg2" tdControlStyle="list_bg1" tdWidth1="17%" tdWidth2="33%" controlSize="25" tdHeight="30" title="受理客户帐户信息" titleClass="import_tit"  
    	                  compareDtoname="OldCustAccountInfoDTO"  compareBean="oldCustAcctInfoCols" comparePrimaryKey="id" /> 
<%
    //受理客户备份信息
    if (oldCustomerDto !=null){
%>
      <tbl:dynamicShowAttributeTag dtoName="OldCustomerInfoDTO" bean="oldCustomerDTO" primaryKey="id" tdWordStyle="list_bg2" tdControlStyle="list_bg1" tdWidth1="17%" tdWidth2="33%" controlSize="25" tdHeight="30" title="老客户信息" titleClass="import_tit" />
<%
   }
    //受理客户市场备份信息
    if (oldcustmarkMap !=null && !oldcustmarkMap.isEmpty()){  
%>
   <table align="center" width="810" border="0" cellspacing="1" cellpadding="3" class="list_bg" >
   	 <tbl:dynamicservey serveyType="M"  showType="label" tdHeight="30" tdWidth1="17%" tdWidth2="33%" tdWordStyle="list_bg2" tdControlStyle="list_bg1"  controlSize="25"  match="oldserveyMarketMap" />
   </table>
<%
   }
   //受理客户帐户备份信息
%>
   <tbl:dynamicShowAttributeTag dtoName="OldCustAccountInfoDTO" bean="oldCustAcctInfoCols" primaryKey="id" tdWordStyle="list_bg2" tdControlStyle="list_bg1" tdWidth1="17%" tdWidth2="33%" controlSize="25" tdHeight="30" title="老帐户信息" titleClass="import_tit" />      
<% 
   
   Map saMap = Postern.getCsicustProductinfoBycsiID(csiDto.getId());
   String strPkgName ="";
   String strCampName ="";
   String strSerAcctId ="";
   String strProductName ="";
   String strDeviceName ="";
   String strDestProductName ="";
   String strDestDevicceName ="";
   int i=1,j=1;
   Map pakageIdMap   =new HashMap();
   Map campaignIdMap =new HashMap();
   Map serAcctIdMap  =new HashMap();
   Map allProductMap=Postern.getAllProductName();
   
   if(saMap!=null&&!saMap.isEmpty()){
		   %>
	   <table width="810" border="0" align="center">
	    <tr> 
	        <td colspan="4" class="import_tit" align="center"><font size="3">受理业务信息</font></td>
	    </tr>	   
		   <%	   
	   for(Iterator sait=saMap.entrySet().iterator();sait.hasNext();){
		   java.util.Map.Entry saen=(java.util.Map.Entry)sait.next();
		   Integer said=(Integer)saen.getKey();
		   ArrayList psList=(ArrayList)saen.getValue();
		   StringBuffer packageDesc=new StringBuffer();
		   StringBuffer campaignDesc=new StringBuffer();
		   StringBuffer productDesc=new StringBuffer();
		   StringBuffer deviceDesc=new StringBuffer();
		   StringBuffer destProductDesc=new StringBuffer();
		   StringBuffer destDeviceDesc=new StringBuffer();
		   int packageIndex=0,campaignIndex=0,productIndex=0,deviceIndex=0,destProductIndex=0,destDeviceIndex=0;
		   if(psList!=null){
			   for(Iterator psit=psList.iterator();psit.hasNext();){
				   CsiCustProductInfoDTO cpDto =(CsiCustProductInfoDTO)psit.next();
				   int packageid=cpDto.getReferPackageID();
				   if(packageid!=0){
					   String tempDesc=Postern.getPackagenameByID(packageid);
					   if(packageDesc.indexOf(tempDesc)<0){
						   packageDesc.append(++packageIndex).append(":").append(tempDesc).append(";");
					   }
				   }
				   int campaignid=cpDto.getReferCampaignID();
				   if(campaignid!=0){
					   String tempDesc=Postern.getCampaignNameByID(campaignid);
					   if(campaignDesc.indexOf(tempDesc)<0){
						   campaignDesc.append(++campaignIndex).append(":").append(tempDesc).append(";");
					   }
				   }
				   int productid=cpDto.getProductID();
				   if(productid!=0){
					   productDesc.append(++productIndex).append(":").append(allProductMap.get(productid+"")).append(";");
				   }
				   int deviceid=cpDto.getReferDeviceID();
				   TerminalDeviceDTO  oldTdDto=null;
				   if(deviceid!=0){
					   oldTdDto =Postern.getTerminalDeviceByID(deviceid);
					   deviceDesc.append(++deviceIndex).append(":").append(oldTdDto.getDeviceModel()).append("/").append(oldTdDto.getSerialNo()).append(";");
				   }
				   int destProductid=cpDto.getDestProductID();
				   if(destProductid!=0){
					   destProductDesc.append(++destProductIndex).append(":")
					   .append("现在的产品为:").append(allProductMap.get(destProductid+"")).append(",")
					   .append("以前的产品为:").append(allProductMap.get(productid+"")).append(";");
				   }
				   int destDeviceid=cpDto.getReferDestDeviceID();
				   if(destDeviceid!=0){
					   TerminalDeviceDTO  tdDto =Postern.getTerminalDeviceByID(destDeviceid);
					   destDeviceDesc.append(++destDeviceIndex).append(":")
					   .append("现在的设备为:").append(tdDto.getDeviceModel()).append("/").append(tdDto.getSerialNo()).append(",");
					   if(oldTdDto!=null){
						   destDeviceDesc.append("以前的设备为:").append(oldTdDto.getDeviceModel()).append("/").append(oldTdDto.getSerialNo()).append(";");
					   }else{
						   destDeviceDesc.append(";");
					   }
				   }
			   }
		   }
		   %>
	    <tr>
	    	  <td height="30" align="center" colspan="4" class="list_bg2" width="17%"><b>业务帐户:<%=said%></b></td>
	    </tr>
	    <%if (!scheduleTime.equals("")){ %>
	    <tr> 
	        <td height="30" align="right" class="list_bg2" width="17%">预约生效时间</td> 
	        <td height="30" colspan="3"    class="list_bg1" ><%=scheduleTime%></td>
	    </tr>
	    <%}if(usedMonth !=0){ %>
	    <tr> 
	        <td height="30" align="right" class="list_bg2" width="17%">续费月数</td> 
	        <td height="30" colspan="3"    class="list_bg1" ><%=usedMonth%></td>
	    </tr>   
	    <%}if(packageIndex>0){%>
	    <tr> 
	        <td height="30" align="right" class="list_bg2" width="17%">产品包</td> 
	        <td height="30" colspan="3"    class="list_bg1" ><%=packageDesc%></td>
	    </tr>
	    <%} if(campaignIndex>0){%>
	    <tr> 
	        <td height="30" align="right" class="list_bg2" width="17%">优惠活动</td> 
	        <td height="30" colspan="3"   class="list_bg1" ><%=campaignDesc%></td>
	    </tr> 
	    <%} if(productIndex>0){%>
	    <tr>
	    	  <td height="30" align="right" class="list_bg2" width="17%">产品</td>
	    	  <td height="30" colspan="3"    class="list_bg1" ><%=destProductIndex>0?destProductDesc:productDesc%></td>
	    </tr>
	    <%} if(deviceIndex>0){%>
	    <tr>
	      	<td height="30" align="right" class="list_bg2" width="17%">设备</td>
	    	  <td height="30" colspan="3"    class="list_bg1" ><%=destDeviceIndex>0?destDeviceDesc:deviceDesc%></td>
	    </tr>
		   <%
	   }
   }
		   %>
			</table>
		   <%	   

}
%>	


<% 
	java.util.Collection feecol=Postern.getAllFeeListByCsiAndType(csiDto.getId(),CommonKeys.AIREFERTYPE_M);
  if(feecol!=null&&!feecol.isEmpty()) {
%>
    <table width="810" border="0" align="center">
       <tr> 
         <td  class="import_tit" align="center"><font size="3">应收费用明细</strong></td>
	     </tr>
       <tr ><td align="center"> 
          <iframe SRC="csi_fee_list.do?CSIID=<%=csiDto.getId()%>&ReferType=<%="M"%>" id="FrameFeeList" name="FrameFeeList" width="100%" height="160"></iframe>
       </td></tr>
   </table>
<% }
	 feecol=Postern.getAllPaymentListByCsiAndType(csiDto.getId(),CommonKeys.PAYMENTRECORDSOURCETYPE_ACCEPT);
   if(feecol!=null&&!feecol.isEmpty()) {
%>
   <table width="810" border="0" align="center">
       <tr> 
          <td  class="import_tit" align="center" ><font size="3">实收费用明细</strong></td>
	     </tr>
	     <tr ><td align="center"> 
          <iframe SRC="csi_payment_list.do?CSIID=<%=csiDto.getId()%>&ReferType=<%="M"%>" id="FramePaymentList" name="FramePaymentList" width="100%" height="130"></iframe>
       </td></tr>
   </table>
<% }
	  feecol=Postern.getAllPrepaymentDeductionRecordListByCsiAndType(csiDto.getId(),CommonKeys.F_PDR_REFERRECORDTYPE_C);
    if(feecol!=null&&!feecol.isEmpty()) {
 %>
    <table width="810" border="0" align="center">
        <tr> 
            <td class="import_tit" align="center" ><font size="3">预存抵扣明细</strong></td>
	     </tr>	    
	     <tr ><td align="center"> 
        <iframe SRC="prepayment_list.do?txtReferRecordID=<%=csiDto.getId()%>&txtReferRecordType=<%=CommonKeys.F_PDR_REFERRECORDTYPE_C%>&ReferType=<%="M"%>" id="FramePaymentList" name="FramePaymentList"  height="130" width="100%"></iframe>
       </td></tr>
    </table>
<% } %>  

<table align="center"  border="0" cellspacing="0" cellpadding="0">
    <tr>
       <bk:canback url="service_interaction_info.do/customer_serviceInteraction_query.do/service_interaction_query_result.do/service_interaction_query_result_for_alter.do/service_interaction_query_result_for_callback.do/job_card_query_result_for_closing_open.do/service_interaction_query_result_for_payfee.do/service_interaction_query_result_for_returnfee.do/service_interaction_query_result_by_customer.do/job_card_query_result_for_contact_of_install.do/agent_csi_query.do/agent_csi_operator.do" >
          <td width="20" ></td>  
           <td><img src="img/button2_r.gif" width="22" height="20"></td>      
           <td background="img/button_bg.gif"  height="20" >
           <a href="<bk:backurl property="service_interaction_info.do/customer_serviceInteraction_query.do/service_interaction_query_result.do/service_interaction_query_result_for_alter.do/service_interaction_query_result_for_callback.do/job_card_query_result_for_closing_open.do/service_interaction_query_result_for_payfee.do/service_interaction_query_result_for_returnfee.do/service_interaction_query_result_by_customer.do/job_card_query_result_for_contact_of_install.do/agent_csi_query.do/agent_csi_operator.do" />" class="btn12">返&nbsp;回</a></td>
           <td><img src="img/button2_l.gif" width="13" height="20"></td>
       </bk:canback> 
       <d:displayControl id="button_service_interaction_view_for_booking_alter" bean="oneline">
       	   <td width="20"></td>
           <td><img src="img/button_l.gif" width="13" height="20" ></td>
           <td><input name="alertSubmit"  type="button" class="button" onClick="javascript:alter_submit()" value="变 更"></td>
           <td><img src="img/button_r.gif" width="22" height="20"></td>
			 </d:displayControl>
       <d:displayControl id="button_service_interaction_view_for_booking_cancel" bean="oneline">
           <td width="20"></td>
           <td><img src="img/button_l.gif" width="13" height="20" ></td>
           <td><input name="cancel" type="button" class="button" onClick="javascript:cancel_submit()" value="取 消"></td>
           <td><img src="img/button_r.gif" width="22" height="20"></td>
			 </d:displayControl> 
			 <d:displayControl id="button_service_interaction_view_callback_create" bean="oneline">
          <td width="20" ></td>  
          <td><img src="img/button_l.gif" width="13" height="20"></td>
          <td background="img/button_bg.gif"  ><a href="javascript:callback_submit()" class="btn12">创建回访信息</a></td>
          <td><img src="img/button_r.gif" width="22" height="20"></td>     
			</d:displayControl>           
      <d:displayControl id="button_service_interaction_view_callback_view" bean="oneline">
           <td width="20" ></td>  
           <td><img src="img/button_l.gif" width="13" height="20"></td>
           <td background="img/button_bg.gif"  ><a href="javascript:callback_view()" class="btn12">回访信息查询</a></td>
           <td><img src="img/button_r.gif" width="22" height="20"></td> 
			</d:displayControl>           
      <d:displayControl id="button_service_interaction_view_adjust" bean="customerDTO,oneline">
          <td width="20" ></td>  
          <td><img src="img/button_l.gif" width="13" height="20"></td>
          <td background="img/button_bg.gif"  ><a href="javascript:csi_adjust_submit()" class="btn12">受理单调帐</a></td>
          <td><img src="img/button_r.gif" width="22" height="20"></td>  
			</d:displayControl>           
      <d:displayControl id="button_service_interaction_view_for_open_returnfee" bean="customerDTO,oneline">
          <td width="20" ></td>  
          <td><img src="img/button_l.gif" width="13" height="20"></td>
          <td background="img/button_bg.gif"  ><a href="javascript:returnfee_submit()" class="btn12">结束受理并退费</a></td>
          <td><img src="img/button_r.gif" width="22" height="20"></td>  
			</d:displayControl>           
          <td width="20" ></td>  
          <td><img src="img/button_l.gif" width="13" height="20"></td>
          <td background="img/button_bg.gif"  >
	         <a href="service_interaction_process_query.do?txtCsiID=<tbl:write name="oneline" property="Id"/>" class="btn12">历史记录</a></td>
          <td><img src="img/button_r.gif" width="22" height="20"></td> 
      <d:displayControl id="button_service_interaction_view_for_open_customerRegister_print_conversion" bean="customerDTO,oneline,SYSTEMSYMBOLNAME"><!--整转的开户-->
          <td width="20" ></td>            
          <td><img src="img/button_l.gif" width="13" height="20"></td>
          <td background="img/button_bg.gif"  >
	         <a href="javascript:customerRegister_print_submit('customerRegister_print.do?txtID=<%=csiDto.getId()%>')" class="btn12">登记单打印</a></td>
          <td><img src="img/button_r.gif" width="22" height="20"></td>
          <td width="20" height="20"></td> 
    	  <td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
    	  <td><input name="Submit22" type="button" class="button" onClick="javascript:customer_service_receipt_print('<%=csiDto.getCustomerID()%>','<%=csiDto.getId()%>')" value="发票单据打印"></td>
    	  <td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td> 
      </d:displayControl>
      <d:displayControl id="button_service_interaction_view_for_open_customerRegister_print_buy" bean="customerDTO,oneline,SYSTEMSYMBOLNAME"><!--购买的开户-->
         	<td width="20" height="20"></td> 
     		<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
     		<td><input name="Submit22" type="button" class="button" onClick="javascript:customer_service_print('<%=csiDto.getCustomerID()%>','<%=csiDto.getId()%>')" value="登记单打印"></td>
     		<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
     		<td width="20" height="20"></td> 
    	   <td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
    	   <td><input name="Submit22" type="button" class="button" onClick="javascript:customer_service_receipt_print('<%=csiDto.getCustomerID()%>','<%=csiDto.getId()%>')" value="发票单据打印"></td>
    	   <td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td> 	 
     </d:displayControl>
     <d:displayControl id="button_service_interaction_view_for_addsa_customerRegister_print_buy" bean="customerDTO,oneline,SYSTEMSYMBOLNAME"><!--添加用户-->
         	<td width="20" height="20"></td> 
     		<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
     		<td><input name="Submit22" type="button" class="button" onClick="javascript:customer_service_print('<%=csiDto.getCustomerID()%>','<%=csiDto.getId()%>')" value="登记单打印"></td>
     		<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
     		<td width="20" height="20"></td> 
    	   <td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
    	   <td><input name="Submit22" type="button" class="button" onClick="javascript:customer_service_receipt_print('<%=csiDto.getCustomerID()%>','<%=csiDto.getId()%>')" value="发票单据打印"></td>
    	   <td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td> 	 
     </d:displayControl>
     <d:displayControl id="button_service_interaction_view_jobcard_view" bean="oneline">
           <td width="20" ></td>  
           <td><img src="img/button_l.gif" width="13" height="20"></td>
           <td background="img/button_bg.gif"  >
	         <a href="job_card_view.do?txtJobCardID=<tbl:write name="oneline" property="ReferJobCardID"/>" class="btn12">相关工单</a></td>
           <td><img src="img/button_r.gif" width="22" height="20"></td>  
	 </d:displayControl>
	<%systemSettingPrecise = Postern.getSystemSettingValue("SET_W_USER_PREYPAY_PRINT");
    if(systemSettingPrecise!=null&&("lijian").equalsIgnoreCase(systemSettingPrecise)){%>
	 <d:displayControl id="button_service_interaction_view_for_preypay_print_conversion" bean="customerDTO,oneline,SYSTEMSYMBOLNAME"><!--预存-->
	     	<td width="20" height="20"></td> 
    		<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
    		<td><input name="Submit22" type="button" class="button" onClick="javascript:customer_service_receipt_print('<%=csiDto.getCustomerID()%>','<%=csiDto.getId()%>')" value="发票单据打印"></td>
    		<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
     </d:displayControl>
	 <%}%>	
	 
	 <d:displayControl id="button_service_interaction_view_for_print_registration_config" bean="customerDTO,oneline,SYSTEMSYMBOLNAME"><!--登记单 配置-->	   
	 <pri:authorized name="service_interaction_view_for_print_registration_config.do" >
     	   <td width="20" height="20"></td>
    	   <td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
    	   <td><input name="Submit22" type="button" class="button" onClick="javascript:config_print('<%=csiDto.getId()%>','<%=CommonKeys.SET_V_PRINTSHEETTYPE_S%>','<%=csiDto.getType()%>','<%=csiDto.getCreateReason()%>','<%=CommonKeys.SET_V_PRINTSHEETREASON_R%>')" value="登记单打印"></td>
    	   <td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td> 	
     </pri:authorized>
     </d:displayControl>
     
     <d:displayControl id="button_service_interaction_view_for_print_billing_config" bean="customerDTO,oneline,SYSTEMSYMBOLNAME"><!--收费单 配置-->	   
     <pri:authorized name="service_interaction_view_for_print_billing_config.do" >
     	   <td width="20" height="20"></td>
    	   <td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
    	   <td><input name="Submit22" type="button" class="button" onClick="javascript:customer_service_receipt_print('<%=csiDto.getCustomerID()%>','<%=csiDto.getId()%>')" value="发票单据打印"></td>
    	   <td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td> 	 
     </pri:authorized>
     </d:displayControl>
     
   </tr>
</table>
<form name="frmPost" method="post" action="" >
	    <input type="hidden" name="txtID" value="<tbl:write name="oneline" property="id" />" >
	    <input type="hidden" name="txtType" value="<tbl:write name="oneline" property="type" />"  >
	    <input type="hidden" name="txtTypeShow" value="<d:getcmnname typeName="SET_V_CUSTSERVICEINTERACTIONTYPE" match="oneline:type" />" >
	    <input type="hidden" name="txtCustomerID" value="<tbl:write name="oneline" property="customerID" />" >
	    <%
	       String	txtName ="";
	       String txtGeneder ="";
	       if (newCustomerDto !=null) {
	           txtName = newCustomerDto.getName();
	           txtGeneder =newCustomerDto.getGender();
	       }
	       else {
	       	  if (custDto !=null){
	       	     txtName =custDto.getName();
	       	     txtGeneder =custDto.getGender();
	       	  }
	       }
	       	  
	       if (txtName ==null) txtName="";
	       txtGeneder =Postern.getHashValueByNameKey("SET_C_CUSTOMERGENDER",txtGeneder);
	       if (txtGeneder ==null) txtGeneder="";
	    %>
	    <input type="hidden" name="txtName" value="<%=txtName%>" >
	    <input type="hidden" name="txtAccountID" value="<tbl:write name="oneline" property="accountID" />" >
	    <input type="hidden" name="txtGeneder" value="<%=txtGeneder%>" >
	    <input type="hidden" name="txtContactPhone"  value="<tbl:write name="oneline" property="contactPhone" />" >
	    <input type="hidden" name="txtContactPerson"  value="<tbl:write name="oneline" property="contactPerson" />" >
      
      <input type="hidden" name="txtDtLastmod"   value="<tbl:write name="oneline" property="DtLastmod" />"    >   
      <input type="hidden" name="func_flag" value="501">
      <input type="hidden" name="Action" value="close">
      <input type="hidden" name="txtConditionType" value="<tbl:write name="oneline" property="Type"/>">
     
</form>

<d:displayControl id="button_service_interaction_view_for_booking_cancel" bean="oneline">
<form name="frmDelPost" method="post" action="cancel_booking_op.do" >
 <table align="center" border="0" cellspacing="0" cellpadding="0">
    <tr>
      <td >
  	<table>
  	   <tr>
	      <!--td valign="middle"  align="right" >取消预约单的原因</td>
	      <td ><font size="2">
		<d:selcmn name="txtStatusReason" mapName="SET_V_CUSTSERVICEINTERACTIONSTATUSREASON" width="20" />
              </font></td-->
       <tbl:csiReason csiType="BK" name="txtStatusReason" action="C" showType="text"  checkScricptName="check_csiReason()" forceDisplayTD="true" match="txtStatusReason"  controlSize="25" tdControlColspan="3" />
	     </tr>
	 </table>
      </td>
    </tr>

  </table>
<input type="hidden" name="txtID"  value="">
<input type="hidden" name="Action"  value="<%=CommonKeys.CANCEL_OF_BOOKING%>">
<input type="hidden" name="func_flag"  value="110">
<input type="hidden" name="confirm_post" value="true">
<tbl:generatetoken />
</form>
</d:displayControl>
</lgc:bloop> 

