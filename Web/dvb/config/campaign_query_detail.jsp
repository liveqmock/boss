<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="bookmark" prefix="bk" %>
<%@ taglib uri="osstags" prefix="d"%>
<%@ taglib uri="privilege" prefix="pri" %>
<%@ page import="com.dtv.oss.dto.CampaignDTO" %>
<%@ page import="com.dtv.oss.web.util.WebUtil" %>
<%@ page import="com.dtv.oss.util.Postern" %>

<SCRIPT LANGUAGE="JavaScript">
<!--
 function open_select(type,typeName,typeValue,parentType,parentTypeName,parentTypeValue){
	var param="batch_query_select.screen?";
	param=param + "type="+type;
	param=param + "&typeName="+typeName;
	param=param + "&typeValue=" + document.frmPost.elements(typeName).value;
	param=param + "&parentType="+parentType;
	param=param + "&parentTypeName="+parentTypeName;
	
	if(parentTypeName!=null && parentTypeName!="")
		param=param + "&parentTypeValue="+document.frmPost.elements(parentTypeName).value;
		
	var windowFeatures="toolbar=no,location=no,status=no,menubar=no,scrollbars=yes,width=330,height=250,screenX=340,screenY=270";

	window.open(param,"",windowFeatures);
}
 

 
 
function check_form(){
    if (check_Blank(document.frmPost.txtCampaignName, true, "名称"))
        return false;
        
   
    if (check_Blank(document.frmPost.txtCampaignType, true, "促销活动类型"))
        return false;
   
    if (check_Blank(document.frmPost.txtAllowPause, true, "是否允许暂停"))
        return false;
    if (check_Blank(document.frmPost.txtAllowTransition, true, "是否允许迁移"))
        return false;
     if (check_Blank(document.frmPost.txtAllowTransfer, true, "是否允许过户"))
        return false;
     if (check_Blank(document.frmPost.txtAllowClose, true, "是否允许销户"))
        return false;
     if (check_Blank(document.frmPost.txtAllowAlter, true, "是否允许变更"))
        return false;
         if (check_Blank(document.frmPost.txtKeepBilling, true, "系统暂停后是否计费"))
        return false;
     
     if (check_Blank(document.frmPost.txtAutoExtendFlag, true, "是否自动延期"))
        return false;
   
    if (check_Blank(document.frmPost.txtPaymentAwardFlag, true, "是否赠送金额"))
        return false;   
        
     
           
    if (check_Blank(document.frmPost.txtStatus, true, "状态"))
        return false;
     if (check_Blank(document.frmPost.txtTimeLenUnitNumeber, true, "绑定期限"))
        return false;
     if (!check_Num(document.frmPost.txtTimeLenUnitNumeber, true, "绑定期限"))
        return false;  
     
    if(document.frmPost.txtCampaignType.value=='A'){  
        if (check_Blank(document.frmPost.txtObligationFlag, true, "是否保底封顶促销"))
        return false;
       
      
   } 
    if(document.frmPost.txtCampaignType.value=='B'){  
        if (check_Blank(document.frmPost.txtRfBillFlag, true, "计费标志"))
        return false;
        if (check_Blank(document.frmPost.txtGroupBargainFlag, true, "是否用于团购"))
           return false;  
        if (check_Blank(document.frmPost.txtRfBillingCycleFlag, true, "付费方式"))
        return false;
       if (check_Blank(document.frmPost.txtBundlePrePaymentFlag, true, "套餐费用预付标记"))
        return false;
        
   }
    if (check_Blank(document.frmPost.txtDateFrom, true, "有效期起始时间")) {
        return false;
    } else {
        if (!check_TenDate(document.frmPost.txtDateFrom, true, "有效期起始时间"))
            return false;
    }
    if (check_Blank(document.frmPost.txtDateTo, true, "有效期截止时间")) {
        return false;
    } else {
        if (!check_TenDate(document.frmPost.txtDateTo, true, "有效期截止时间"))
            return false;
    }
    if (!compareDate(document.frmPost.txtDateFrom,document.frmPost.txtDateTo,"有效期起始时间应在有效期截止时间之前！")) {
        return false;
    }

    return true;

}
 
 
 
function frm_modify(){
   if(check_form()){     
      document.frmPost.action="campaign_modify.do";    
      document.frmPost.submit();
   }   
}
 
 function frm_cond_product_create(){
      var campaignID = document.frmPost.txtCampaignID.value;
      
      document.location.href= "product_cond_campaign.screen?txtCampaignID="+campaignID;
      
} 
function frm_cond_package_create(){
      var campaignID = document.frmPost.txtCampaignID.value;
       var campaignType = document.frmPost.txtCampaignType.value;   
      document.location.href= "package_cond_campaign.screen?txtCampaignID="+campaignID;
      
} 
function frm_cond_campaign_create(){
      var campaignID = document.frmPost.txtCampaignID.value;
        var campaignType = document.frmPost.txtCampaignType.value;   
      document.location.href= "campaign_cond_campaign.screen?txtCampaignID="+campaignID;
      
} 
function frm_bundle_payment_manage(){
      var campaignID = document.frmPost.txtCampaignID.value;
      
      document.location.href= "bundle_payment_query.screen?txtCampaignID="+campaignID;
      
} 
function frm_bundle_prepayment_manage(){
      var campaignID = document.frmPost.txtCampaignID.value;
      
      document.location.href= "bundle_prepayment_query.screen?txtCampaignID="+campaignID;
      
} 
function frm_agmt_campaign_create(){
      var campaignID = document.frmPost.txtCampaignID.value;
      
      document.location.href= "campaign_agmt_campaign_query.screen?txtCampaignID="+campaignID;
      
} 
function showButton(){
	if(document.frmPost.txtPaymentAwardFlag.value=="Y"){
		document.all.table1.style.display="";
	}
	if(document.frmPost.txtPaymentAwardFlag.value=="N"){
		document.all.table1.style.display="none";
	}
}
function frm_paymentaward(){
	var campaignID = document.frmPost.txtCampaignID.value;
	
	document.location.href= "campaign_payment_award_query.screen?txtCampaignID="+campaignID;
}
//-->
</SCRIPT>
 

<form name="frmPost" method="post">
  <% 
    
  String  campaignID = request.getParameter("txtCampaignID");
  String campaignType=Postern.getCampaignByID(WebUtil.StringToInt(campaignID));
  String title="";
  String name="";
  String typeName="";
  String url="";
  if("B".equals(campaignType)){
   title="套餐活动基本信息";
   name = "套餐ID";
   typeName="套餐";
   url="taocan_query.do?txtFrom=1&txtTo=10&txtCampaignType=B";
   }
   else {
   title="促销活动基本信息";
   name="促销活动ID";
    typeName="普通促销";
    url="campaign_query.do?txtFrom=1&txtTo=10&txtCampaignType=A";
  } 
  String paymentAwardFlag="";
%>
 

 <input type="hidden" name="Action" value="update">
 
  
   
   <%
      if("B".equals(campaignType)){
 %>
   <input type="hidden" name="func_flag" value="5005" > 
   <%} else {%>
     <input type="hidden" name="func_flag" value="5004" > 
   <%}%>
   
 <table  border="0" align="center" cellpadding="0" cellspacing="10" >
  <tr>
    <td><img src="img/list_tit.gif" width="19" height="19"></td>
    <td class="title"><%=title%></td>
  </tr>
</table>
<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>
       
<lgc:bloop requestObjectName="ResponseQueryResult" item="oneline" >

 <table width="100%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
        <%
           CampaignDTO dto = (CampaignDTO) pageContext.findAttribute("oneline");
                  
                  
                   
              String csiTypeList = dto.getCsiTypeList();
             String totalValue1="";
             System.out.println("the type of the csi is "+ csiTypeList);
                 if(csiTypeList!=null)
                 { 
                     String[] custArray=csiTypeList.split(",");
                     for(int j=0;j<custArray.length;j++){
                     System.out.println("every customertpye is "+ custArray[j]);
                     String value = Postern.getHashValueByNameKey("SET_V_CUSTSERVICEINTERACTIONTYPE",custArray[j]);
                     if(totalValue1=="")
                      totalValue1=value;
                     else 
                       totalValue1=totalValue1+","+value;
                     }
                     
                 }
      String custTypeList = dto.getCustTypeList();
         String totalValue="";
         System.out.println("the type of the customer is "+ custTypeList);
                 if(custTypeList!=null)
                 { 
                     String[] custArray=custTypeList.split(",");
                     for(int j=0;j<custArray.length;j++){
                     System.out.println("every customertpye is "+ custArray[j]);
                     String value = Postern.getHashValueListByNameKey("SET_C_CUSTOMERTYPE",custArray[j]);
                     if(totalValue=="")
                      totalValue=value;
                     else 
                       totalValue=totalValue+","+value;
                     }
                     
                 }
                 String  openSource = dto.getOpenSourceTypeList();
                 String disPlayOpenSource = "";
                 if(openSource!=null)
                 { 
                     String[] custArray=openSource.split(",");
                     for(int j=0;j<custArray.length;j++){
                     
                     String value = Postern.getHashValueListByNameKey("SET_C_CUSTOPENSOURCETYPE",custArray[j]);
                     if(disPlayOpenSource=="")
                      disPlayOpenSource=value;
                     else 
                       disPlayOpenSource=disPlayOpenSource+","+value;
                     }
                     
                 }
                 int camId = dto.getCampaignID();
                 String segmentIDstr=Postern.getMarketSegmentIDByCampaignID(camId);
                 String sagmentName ="";
                 if(segmentIDstr!=null && !"".equals("segmentIDstr")){
                 String[] segmentArray=segmentIDstr.split(",");
                     for(int j=0;j<segmentArray.length;j++){
                       Map segmentMap = Postern.getAllMarketSegmentName();
                       String nameValue=(String)segmentMap.get(segmentArray[j]);
                       
                     if(sagmentName=="")
                      sagmentName=nameValue;
                     else 
                       sagmentName=sagmentName+","+nameValue;
                     }
                  if (sagmentName==null)
                     sagmentName="";
                 }
                  String packageIDstr=Postern.getPackageIDByCampaignID(camId);
                 String packageName ="";
                 if(packageIDstr!=null && !"".equals("packageIDstr")){
                 String[] segmentArray=packageIDstr.split(",");
                     for(int j=0;j<segmentArray.length;j++){
                       Map segmentMap = Postern.getAllProductPackageIDAndName();
                       String nameValue=(String)segmentMap.get(segmentArray[j]);
                       
                     if(packageName=="")
                      packageName=nameValue;
                     else 
                       packageName=packageName+","+nameValue;
                     }
                  if (packageName==null)
                     packageName="";
                 }
                 String productIDstr=Postern.getProductIDByCampaignID(camId);
                 String productName ="";
                 if(productIDstr!=null && !"".equals("productIDstr")){
                   String[] segmentArray=productIDstr.split(",");
                     for(int j=0;j<segmentArray.length;j++){
                       Map segmentMap = Postern.getAllProduct();
                       String nameValue=(String)segmentMap.get(segmentArray[j]);
                       
                     if(productName=="")
                      productName=nameValue;
                     else 
                       productName=productName+","+nameValue;
                     }
                  if (productName==null)
                     productName="";
                 }
                 
                 paymentAwardFlag = dto.getPaymentAwardFlag();
                 
 %>    
       
        <tr> 
          <td class="list_bg2" ><div align="right"><%=name%></div></td>
          <td class="list_bg1" ><font size="2">                               
              <input type="text" name="txtCampaignID" size="25"  value="<tbl:write name="oneline" property="CampaignID"/>" class="textgray" readonly >
              </font></td>
          <td class="list_bg2"><div align="right">状态*</div></td>
          <td class="list_bg1"><font size="2">
              <d:selcmn name="txtStatus" mapName="SET_G_GENERALSTATUS" match="oneline:status" width="23"/>
              </font></td>
        </tr>

        <tr>
          <td  class="list_bg2" ><div align="right">名称*</div></td>
          <td class="list_bg1"><font size="2">
              <input type="text" name="txtCampaignName" size="25"  maxlength="25"  value="<tbl:write name="oneline" property="CampaignName"/>">
              </font></td>
          <td  class="list_bg2"><div align="right">类型</div></td>
          <td class="list_bg1"><font size="2">
              <input type="text" name="campaignType" size="25"  value="<%=typeName%>" class="textgray" readonly >
              <input name="txtCampaignType" type="hidden" value="<tbl:write name="oneline"  property="campaignType"/>">
              </font></td>   
        </tr>

        <tr>
          <td  class="list_bg2"><div align="right">有效期起始时间*</div></td>
          <td class="list_bg1" ><font size="2">
              <input maxlength="10" onfocus="viewModule(this)" onKeydown="inputDate(document.frmPost.txtDateFrom)" onblur="lostFocus(this)" type="text" name="txtDateFrom" size="25"  value="<tbl:writedate name="oneline" property="DateFrom"/>">              </font><IMG onclick="MyCalendar.SetDate(this,document.frmPost.txtDateFrom,'Y')" src="img/calendar.gif" style=cursor:hand border="0"></td>
          <td  class="list_bg2" width="17%"><div align="right">有效期截止时间*</div></td>
          <td class="list_bg1"><font size="2">
              <input maxlength="10" onfocus="viewModule(this)" onKeydown="inputDate(document.frmPost.txtDateTo)" onblur="lostFocus(this)" type="text" name="txtDateTo" size="25" value="<tbl:writedate name="oneline" property="DateTo"/>">              </font><IMG onclick="MyCalendar.SetDate(this,document.frmPost.txtDateTo,'Y')" src="img/calendar.gif" style=cursor:hand border="0"></td>
        </tr>

	<tr>
	 <td  class="list_bg2" align="right">客户类型</td>
	   <td class="list_bg1">
	   	<input name="txtCustTypeListValue" type="text"  maxlength="200"  readonly size="25" value="<%=totalValue%>">
	   	<input name="txtCustTypeList" type="hidden" value="<tbl:write name="oneline"  property="CustTypeList"/>">
	   	<input name="checkbutton" type="button" class="button" value="请选择" onClick="javascript:open_select('SET_C_CUSTOMERTYPE','txtCustTypeList',document.frmPost.txtCustTypeList.value,'','','')"> 
	   </td>	   
          <td  class="list_bg2"><div align="right">绑定期限(月)*</div></td>
          <td  class="list_bg1"><font size="2">
               <input type="text" name="txtTimeLenUnitNumeber" size="25"   value="<tbl:write name="oneline" property="TimeLengthUnitNumber"/>">
              </font></td>
          
        </tr>

	<tr>
	   <td  class="list_bg2"><div align="right">来源渠道列表</div></td>
	   <td class="list_bg1">
	   	<input name="txtOpenSourceTypeListValue" type="text"  readonly  maxlength="200" size="25" value="<%=disPlayOpenSource%>">
	   	<input name="txtOpenSourceTypeList" type="hidden" value="<tbl:write name="oneline"  property="OpenSourceTypeList"/>">
	   	<input name="checkbutton" type="button" class="button" value="请选择" onClick="javascript:open_select('SET_C_CUSTOPENSOURCETYPE','txtOpenSourceTypeList',document.frmPost.txtOpenSourceTypeList.value,'','','')"> 
	   </td>	
           <td  class="list_bg2"><div align="right">是否允许暂停*</div></td>
           <td class="list_bg1"><font size="2">
              <d:selcmn name="txtAllowPause" mapName="SET_G_YESNOFLAG" match="oneline:AllowPause" width="23"/>
          </font></td>
          
        </tr>
        <tr>
          <td  class="list_bg2"><div align="right">是否允许迁移*</div></td>
          <td class="list_bg1"><font size="2">	
          <d:selcmn name="txtAllowTransition" mapName="SET_G_YESNOFLAG" match="oneline:AllowTransition" width="23"/>
          </font></td>
           <td  class="list_bg2"><div align="right">是否允许过户*</div></td>
           <td class="list_bg1"><font size="2">
           <d:selcmn name="txtAllowTransfer" mapName="SET_G_YESNOFLAG" match="oneline:AllowTransfer" width="23"/>
          </font></td>
        </tr>
        
        <tr>
          <td  class="list_bg2"><div align="right">是否允许销户*</div></td>
          <td class="list_bg1"><font size="2">
            <d:selcmn name="txtAllowClose" mapName="SET_G_YESNOFLAG" match="oneline:AllowClose" width="23"/>
           </font></td>
           <td  class="list_bg2"><div align="right">是否允许变更*</div></td>
           <td class="list_bg1"><font size="2">
           <d:selcmn name="txtAllowAlter" mapName="SET_G_YESNOFLAG" match="oneline:AllowAlter" width="23"/>
          </font></td>
        </tr>
        
	<tr>
           <td  class="list_bg2"><div align="right">系统暂停计费*</div></td>
           <td class="list_bg1"><font size="2">
          <d:selcmn name="txtKeepBilling" mapName="SET_G_YESNOFLAG" match="oneline:KeepBilling" width="23"/>
          </font></td>
           <td  class="list_bg2"><div align="right">是否保底封顶促销*</div></td>
           <td class="list_bg1"><font size="2">
          <d:selcmn name="txtObligationFlag" mapName="SET_G_YESNOFLAG" match="oneline:obligationFlag" width="23"/>
          </font></td>
        </tr>
         <tr>   
		 <td  class="list_bg2" align="right">使用场合</td>
	         <td class="list_bg1" colspan="3">
	   	<input name="txtCsiTypeListValue" type="text"  maxlength="200" readonly size="87" value="<%=totalValue1%>">
	   	<input name="txtCsiTypeList" type="hidden" value="<tbl:write name="oneline"  property="csiTypeList"/>">
	   	<input name="checkbutton" type="button" class="button" value="请选择" onClick="javascript:open_select('SET_V_CUSTSERVICEINTERACTIONTYPE','txtCsiTypeList',document.frmPost.txtCsiTypeList.value,'','','')"> 
	   </td>	   
          </tr>
          <% 
            if("B".equals(campaignType)){
          %>
        <tr> 
           <td  class="list_bg2"><div align="right">计费标志*</div></td>
           <td class="list_bg1"><font size="2">
           <d:selcmn name="txtRfBillFlag" mapName="SET_M_BUNDLERFBILLINGFLAG" match="oneline:rfBillingFlag" width="23"/>
          </font></td>
          <td  class="list_bg2"><div align="right">付费方式*</div></td>
           <td class="list_bg1"><font size="2">
           <d:selcmn name="txtRfBillingCycleFlag" mapName="SET_F_RFBILLINGCYCLEFLAG" match="oneline:rfBillingCycleFlag" width="23"/>
          </font></td>
        </tr>
        <tr>
         <td  class="list_bg2"><div align="right">是否用于团购*</div></td>
           <td class="list_bg1" colspan="3"><font size="2">
          <d:selcmn name="txtGroupBargainFlag" mapName="SET_G_YESNOFLAG" match="oneline:groupBargainFlag" width="23"/>
          </font></td>
          </tr>
        <%}%>
        <tr>
           <td  class="list_bg2"><div align="right">是否自动延期*</div></td>
           <td class="list_bg1" ><font size="2">
           <d:selcmn name="txtAutoExtendFlag" mapName="SET_G_YESNOFLAG" match="oneline:autoExtendFlag" width="23"/>
           </font></td>
           <td  class="list_bg2"><div align="right">排列顺序 </div></td>         
           <td class="list_bg1" align="left">
               <input type="text" name="txtCampainpriority" maxlength="9" size="25" value="<tbl:write name="oneline" property="campainpriority"/>" >
           </td> 
          
        </tr>
          <% 
            if("B".equals(campaignType)){
          %>
        <tr>
           <td  class="list_bg2"><div align="right">套餐费预付标记*</div></td>
           <td class="list_bg1"><font size="2">
           <d:selcmn name="txtBundlePrePaymentFlag" mapName="SET_G_YESNOFLAG" match="oneline:bundlePrePaymentFlag" width="23"/>
          </font></td>
           <td  class="list_bg2"><div align="right">是否赠送金额*</div></td>
           <td class="list_bg1"><font size="2">
		   <select name="txtPaymentAwardFlag" onChange="showButton()"><option value="" >-----------------------</option>
							<option value="N" selected="selected">否</option>
							<option value="Y">是</option>
		  </select>
          </font></td>
         
        </tr>
          <%}else {%>
           <tr>
            
           <td  class="list_bg2"><div align="right">是否赠送金额*</div></td>
           <td class="list_bg1" colspan="3"><font size="2">
          <select name="txtPaymentAwardFlag" onChange="showButton()"><option value="" >-----------------------</option>
							<option value="N" selected="selected">否</option>
							<option value="Y">是</option>
		  </select>
          </font></td>
         
        </tr>
        <%}%>

<script>        
	frmPost.txtPaymentAwardFlag.value='<%=paymentAwardFlag%>';
</script>
        <tr> 
          <td  class="list_bg2"><div align="right">描述</div></td>
          <td class="list_bg1" colspan="3"><font size="2">
              <input type="text" name="txtDescription" size="87" value="<tbl:write name="oneline" property="Description"/>">
              </font></td>
        </tr>
       
          <tr> 
            <td  class="list_bg2" align="right">允许市场分区</td>
	    <td class="list_bg1" colspan="3">
	        <input name="txtMarketSegmentListValue" type="text"  readonly maxlength="200" size="87" value="<%=sagmentName%>">
	   	<input name="txtMarketSegmentList" type="hidden" value="<%=segmentIDstr%>">
	   	<input name="checkbutton" type="button" class="button" value="请选择" onClick="javascript:open_select('MARKETSEGMENT','txtMarketSegmentList',document.frmPost.txtMarketSegmentList.value,'','','')"> 
            </td>
         </tr>
        
          <tr> 
	     <td class="list_bg2" align="right">产品包协议</td>
	     <td class="list_bg1" colspan="3">
	        <input name="txtAgmtPackageListValue" type="text"   readonly maxlength="200" size="87" value="<%=packageName%>">
	   	<input name="txtAgmtPackageList" type="hidden" value="<%=packageIDstr%>">
	   	<input name="checkbutton" type="button" class="button" value="请选择" onClick="javascript:open_select('PRODUCTPACKAGE','txtAgmtPackageList',document.frmPost.txtAgmtPackageList.value,'','','')"> 
          </td>  
           <tr> 
	     <td class="list_bg2" align="right">产品协议</td>
	     <td class="list_bg1" colspan="3">
	        <input name="txtAgmtProdcutListValue" type="text" readonly   maxlength="200" size="87" value="<%=productName%>">
	   	<input name="txtAgmtProdcutList" type="hidden" value="<%=productIDstr%>">
	   	<input name="checkbutton" type="button" class="button" value="请选择" onClick="javascript:open_select('PRODUCT','txtAgmtProdcutList',document.frmPost.txtAgmtProdcutList.value,'','','')"> 
            </td>  
          </tr>
         
    </table>
     
        
        
      
      <input type="hidden" name="dtLastmod" value="<tbl:write name="oneline" property="dtLastmod" />">
      
       
     
 </lgc:bloop>



<br>   
    <table border="0" cellspacing="0" cellpadding="0" align=center>
        <tr>
           <td><img src="img/button2_r.gif" width="22" height="20"></td>
               <td background="img/button_bg.gif"><a href="<bk:backurl property="menu_campaign_query.do/group_bargain_query_result.do/menu_taocan_query.do/campaign_query.do/taocan_query.do" />" class="btn12">返回</a></td>
          
          <td><img src="img/button2_l.gif" width="11" height="20"></td>
            <td width="10" ></td>  
             <td><img src="img/button_l.gif" border="0" ></td>
            <td background="img/button_bg.gif"><a href="javascript:frm_cond_product_create()" class="btn12">产品条件</a></td>
          <td><img src="img/button_r.gif" border="0" ></td>
           <td width="10" ></td>  
             <td><img src="img/button_l.gif" border="0" ></td>
            <td background="img/button_bg.gif"><a href="javascript:frm_cond_package_create()" class="btn12">产品包条件</a></td>
          <td><img src="img/button_r.gif" border="0" ></td>
          
         <td width="10" ></td>  
         <td><img src="img/button_l.gif" border="0" ></td>
            <td background="img/button_bg.gif"><a href="javascript:frm_cond_campaign_create()" class="btn12">活动条件</a></td>
          <td><img src="img/button_r.gif" border="0" ></td>
           <td width="10" ></td>  
         <td><img src="img/button_l.gif" border="0" ></td>
            <td background="img/button_bg.gif"><a href="javascript:frm_agmt_campaign_create()" class="btn12">活动协议</a></td>
          <td><img src="img/button_r.gif" border="0" ></td>
          <%
             if("B".equals(campaignType)){
          %>
           <td width="10" ></td>  
          <td><img src="img/button_l.gif" border="0" ></td>
            <td background="img/button_bg.gif"><a href="javascript:frm_bundle_payment_manage()" class="btn12">续费方式</a></td>
          <td><img src="img/button_r.gif" border="0" ></td>
           <td width="10" ></td>  
            <td><img src="img/button_l.gif" border="0" ></td>
            <td background="img/button_bg.gif"><a href="javascript:frm_bundle_prepayment_manage()" class="btn12">预付设置</a></td>
          <td><img src="img/button_r.gif" border="0" ></td>
          <%}%>
          <%
          	if("Y".equals(paymentAwardFlag)){
          %>
          <td>
          <table border="0" cellspacing="0" cellpadding="0" id="table1">
          <td width="10" ></td>
           <td><img src="img/button_l.gif" border="0" ></td>
            <td background="img/button_bg.gif"><a href="javascript:frm_paymentaward()" class="btn12">奖励金额</a></td>
          <td><img src="img/button_r.gif" border="0" ></td>
          </table>
          </td>
          <%
          	}else{
          %>
          <td>
          <table border="0" cellspacing="0" cellpadding="0" id="table1" style="display:none">
          <td width="10" ></td>
           <td><img src="img/button_l.gif" border="0" ></td>
            <td background="img/button_bg.gif"><a href="javascript:frm_paymentaward()" class="btn12">奖励金额</a></td>
          <td><img src="img/button_r.gif" border="0" ></td>
          </table>
          </td>
          <%}%>
          <td width="10" ></td>
           <td><img src="img/button_l.gif" border="0" ></td>
            <td background="img/button_bg.gif"><a href="javascript:frm_modify()" class="btn12">保存</a></td>
          <td><img src="img/button_r.gif" border="0" ></td>
           
           
          </tr>
    </table>        
 
</form>