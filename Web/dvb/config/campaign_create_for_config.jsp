<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="bookmark" prefix="bk" %>
<%@ taglib uri="osstags" prefix="d"%>
<%@ taglib uri="privilege" prefix="pri" %>
<%@ page import="com.dtv.oss.dto.CampaignDTO" %>
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
    if(document.frmPost.txtCampaignType.value=='A'){  
        if (check_Blank(document.frmPost.txtObligationFlag, true, "是否保底封顶促销"))
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
 
function frm_create()
{
           if(check_form()){
           
            document.frmPost.action="campaign_create.do";
           
            document.frmPost.submit();
            }
       
}

 

//-->
</SCRIPT>

<div id="updselwaitlayer" style="position:absolute; left:650px; top:180px; width:250px; height:24px; display:none">
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

<iframe SRC="update_select.screen" name="FrameUS" width="0" height="0" frameborder="0" scrolling="0"  >
</iframe>

<form name="frmPost" method="post">
 <% 
  String  campaignType = request.getParameter("txtCampaignType");
  
  String title="";
 String url="";
 String typeName="";
 
  if("B".equals(campaignType)){
   title="套餐活动基本信息";
    typeName="套餐";
   url="taocan_query.do?txtFrom=1&txtTo=10&txtCampaignType=B";
   }  
   else{ 
   title="促销活动基本信息";
   typeName="普通促销";
   url="campaign_query.do?txtFrom=1&txtTo=10&txtCampaignType=A";
 } 
   
%>
 <input type="hidden" name="Action" value="create">
  
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
       
 

 <table width="100%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
       
        <tr>
          <td  class="list_bg2" ><div align="right">名称*</div></td>
          <td class="list_bg1"><font size="2">
              <input type="text" name="txtCampaignName" size="25"  maxlength="50" value="<tbl:writeparam name="txtCampaignName"/>">
              </font></td>
          <td  class="list_bg2"><div align="right">类型</div></td>
          <td class="list_bg1"><font size="2">
              <input type="text" name="campaignType" size="25"  value="<%=typeName%>" class="textgray" readonly >
              <input name="txtCampaignType" type="hidden" value="<tbl:writeparam name="txtCampaignType"/>">
              </font></td>   
        </tr>
          <tr>
         <td class="list_bg2"><div align="right">有效期起始时间*</div></td>
          <td class="list_bg1">
          <input maxlength="10" onfocus="viewModule(this)" onKeydown="inputDate(document.frmPost.txtDateFrom)" onblur="lostFocus(this)" type="text" name="txtDateFrom" size="25"  value="<tbl:writeparam name="txtDateFrom"/>">          <IMG onclick="MyCalendar.SetDate(this,document.frmPost.txtDateFrom,'Y')" src="img/calendar.gif" style=cursor:hand border="0"/>
          </font></td>
          <td class="list_bg2"><div align="right">有效期截止时间*</div></td>
          <td class="list_bg1">
          <input maxlength="10" onfocus="viewModule(this)" onKeydown="inputDate(document.frmPost.txtDateTo)" onblur="lostFocus(this)" type="text" name="txtDateTo" size="25" value="<tbl:writeparam name="txtDateTo"/>" >          <IMG onclick="MyCalendar.SetDate(this,document.frmPost.txtDateTo,'Y')" src="img/calendar.gif" style=cursor:hand border="0"/>
          </font></td>
        </tr>
        <tr>
	 <td  class="list_bg2" align="right">客户类型</td>
	   <td class="list_bg1">
	   	<input name="txtCustTypeListValue" type="text"  maxlength="200" size="25" readonly value="<tbl:writeparam name="txtCustTypeListValue"/>">
	   	<input name="txtCustTypeList" type="hidden" value="<tbl:writeparam name="txtCustTypeList" />" >
	   	<input name="checkbutton" type="button" class="button" value="请选择" onClick="javascript:open_select('SET_C_CUSTOMERTYPE','txtCustTypeList',document.frmPost.txtCustTypeList.value,'','','')"> 
	   </td>	   
          <td  class="list_bg2"><div align="right">绑定期限(月)*</div></td>
          <td  class="list_bg1"><font size="2">
               <input type="text" name="txtTimeLenUnitNumeber" size="25"   value="<tbl:writeparam name="txtTimeLenUnitNumeber" />" >
              </font></td>
          
        </tr>
        <tr>
	   <td  class="list_bg2"><div align="right">来源渠道列表</div></td>
	   <td class="list_bg1">
	   	<input name="txtOpenSourceTypeListValue" type="text"  maxlength="200" readonly size="25" value="<tbl:writeparam name="txtOpenSourceTypeListValue"/>">
	   	<input name="txtOpenSourceTypeList" type="hidden"  value="<tbl:writeparam name="txtOpenSourceTypeList" />" >
	   	<input name="checkbutton" type="button" class="button" value="请选择" onClick="javascript:open_select('SET_C_CUSTOPENSOURCETYPE','txtOpenSourceTypeList',document.frmPost.txtOpenSourceTypeList.value,'','','')"> 
	   </td>	
           <td  class="list_bg2"><div align="right">是否允许暂停*</div></td>
           <td class="list_bg1"><font size="2">
              <d:selcmn name="txtAllowPause" mapName="SET_G_YESNOFLAG" match="txtAllowPause" width="23"/>
          </font></td>
          
        </tr>
        <tr>
          <td  class="list_bg2"><div align="right">是否允许迁移*</div></td>
          <td class="list_bg1"><font size="2">	
          <d:selcmn name="txtAllowTransition" mapName="SET_G_YESNOFLAG" match="txtAllowTransition" width="23"/>
          </font></td>
           <td  class="list_bg2"><div align="right">是否允许过户*</div></td>
           <td class="list_bg1"><font size="2">
           <d:selcmn name="txtAllowTransfer" mapName="SET_G_YESNOFLAG" match="txtAllowTransfer" width="23"/>
          </font></td>
        </tr>
        
        <tr>
          <td  class="list_bg2"><div align="right">是否允许销户*</div></td>
          <td class="list_bg1"><font size="2">
            <d:selcmn name="txtAllowClose" mapName="SET_G_YESNOFLAG" match="txtAllowClose" width="23"/>
           </font></td>
           <td  class="list_bg2"><div align="right">是否允许变更*</div></td>
           <td class="list_bg1"><font size="2">
           <d:selcmn name="txtAllowAlter" mapName="SET_G_YESNOFLAG" match="txtAllowAlter" width="23"/>
          </font></td>
        </tr>
        
	<tr>
           <td  class="list_bg2"><div align="right">系统暂停后是否计费*</div></td>
           <td class="list_bg1"><font size="2">
          <d:selcmn name="txtKeepBilling" mapName="SET_G_YESNOFLAG" match="txtKeepBilling" width="23"/>
          </font></td>
           <td class="list_bg2"><div align="right">状态*</div></td>
          <td class="list_bg1"><font size="2">
              <d:selcmn name="txtStatus" mapName="SET_G_GENERALSTATUS" match="txtStatus" width="23"/>
              </font></td>
        </tr>
         <tr> 
         
        </tr> 
        <tr>   
		 <td  class="list_bg2" align="right">使用场合</td>
	         <td class="list_bg1" colspan="3">
	   	<input name="txtCsiTypeListValue" type="text"  maxlength="200" size="83" readonly value="<tbl:writeparam name="txtCsiTypeListValue" />" >
	   	<input name="txtCsiTypeList" type="hidden" value="<tbl:writeparam name="txtCsiTypeList" />" >
	   	<input name="checkbutton" type="button" class="button" value="请选择" onClick="javascript:open_select('SET_V_CUSTSERVICEINTERACTIONTYPE','txtCsiTypeList',document.frmPost.txtCsiTypeList.value,'','','')"> 
	   </td>	   
          </tr>
           <% 
            if("B".equals(campaignType)){
          %>
        <tr>
           <td  class="list_bg2"><div align="right">计费标志*</div></td>
           <td class="list_bg1"><font size="2">
           <d:selcmn name="txtRfBillFlag" mapName="SET_M_BUNDLERFBILLINGFLAG" match="txtRfBillFlag" width="23"/>
          </font></td>
          <td  class="list_bg2"><div align="right">付费方式*</div></td>
           <td class="list_bg1"><font size="2">
           <d:selcmn name="txtRfBillingCycleFlag" mapName="SET_F_RFBILLINGCYCLEFLAG" match="txtRfBillingCycleFlag" width="23"/>
          </font></td>
         
        </tr>
       <tr>
           <td  class="list_bg2"><div align="right">是否用于团购*</div></td>
           <td class="list_bg1" colspan="3"><font size="2" >
          <d:selcmn name="txtGroupBargainFlag" mapName="SET_G_YESNOFLAG" match="txtGroupBargainFlag" width="23"/>
          </font></td>
           
         
        </tr>
          <%}%>
        <tr>
           <td  class="list_bg2"><div align="right">是否自动延期*</div></td>
           <td class="list_bg1" colspan="3"><font size="2">
           <d:selcmn name="txtAutoExtendFlag" mapName="SET_G_YESNOFLAG" match="txtAutoExtendFlag" width="23"/>
          </font></td>
         </tr> 
        
         <% 
            if("B".equals(campaignType)){
          %>
        <tr>
           <td  class="list_bg2"><div align="right">套餐费用预付标记*</div></td>
           <td class="list_bg1"><font size="2">
           <d:selcmn name="txtBundlePrePaymentFlag" mapName="SET_G_YESNOFLAG" match="txtBundlePrePaymentFlag" width="23"/>
          </font></td>
          <td  class="list_bg2"><div align="right">是否赠送金额*</div></td>
           <td class="list_bg1"><font size="2">
          <d:selcmn name="txtPaymentAwardFlag" mapName="SET_G_YESNOFLAG" match="txtPaymentAwardFlag" width="23"/>
          </font></td> 
        </tr>
        <%}else {%>
        <tr>
         <td  class="list_bg2"><div align="right">是否赠送金额*</div></td>
           <td class="list_bg1"><font size="2">
          <d:selcmn name="txtPaymentAwardFlag" mapName="SET_G_YESNOFLAG" match="txtPaymentAwardFlag" width="23"/>
          </font></td> 
           <td  class="list_bg2"><div align="right">是否保底封顶促销*</div></td>
          <td class="list_bg1"><font size="2">
              <d:selcmn name="txtObligationFlag" mapName="SET_G_YESNOFLAG" match="txtObligationFlag" width="23"/>
              </font></td>
        </tr>
        <%}%>
        <tr>  
          <td  class="list_bg2"><div align="right">描述</div></td>
          <td class="list_bg1" colspan="3"><font size="2">
              <input type="text" name="txtDescription" size="83"  value="<tbl:writeparam name="txtDescription" />" >
              </font></td>
        </tr>
        <tr> 
            <td  class="list_bg2" align="right">允许市场分区</td>
	    <td class="list_bg1" colspan="3">
	        <input name="txtMarketSegmentListValue" type="text"  readonly maxlength="200" size="83" value="<tbl:writeparam name="txtMarketSegmentListValue"/>">
	   	<input name="txtMarketSegmentList" type="hidden" value="<tbl:writeparam name="txtMarketSegmentList"/>">
	   	<input name="checkbutton" type="button" class="button" value="请选择" onClick="javascript:open_select('MARKETSEGMENT','txtMarketSegmentList',document.frmPost.txtMarketSegmentList.value,'','','')"> 
            </td>
         </tr>
            <tr> 
	     <td class="list_bg2" align="right">协议产品包</td>
	     <td class="list_bg1" colspan="3">
	        <input name="txtAgmtPackageListValue" type="text"   readonly maxlength="200" size="83" value="<tbl:writeparam name="txtAgmtPackageListValue"/>">
	   	<input name="txtAgmtPackageList" type="hidden" value="<tbl:writeparam name="txtAgmtPackageList"/>">
	   	<input name="checkbutton" type="button" class="button" value="请选择" onClick="javascript:open_select('PRODUCTPACKAGE','txtAgmtPackageList',document.frmPost.txtAgmtPackageList.value,'','','')"> 
          </td>   
           <tr> 
	     <td class="list_bg2" align="right">协议产品</td>
	     <td class="list_bg1" colspan="3">
	        <input name="txtAgmtProdcutListValue" type="text"  readonly  maxlength="200" size="83" value="<tbl:writeparam name="txtAgmtProdcutListValue"/>">
	   	<input name="txtAgmtProdcutList" type="hidden" value="<tbl:writeparam name="txtAgmtProdcutList"/>">
	   	<input name="checkbutton" type="button" class="button" value="请选择" onClick="javascript:open_select('PRODUCT','txtAgmtProdcutList',document.frmPost.txtAgmtProdcutList.value,'','','')"> 
            </td>  
          </tr>
            
    </table>
    <%
      if("B".equals(campaignType)){
 %>
   <input type="hidden" name="func_flag" value="503" > 
   <%} else {%>
     <input type="hidden" name="func_flag" value="504" > 
   <%}%>
  
  <br>   
    <table border="0" cellspacing="0" cellpadding="0" align=center>
        <tr>
          <td><img src="img/button2_r.gif" width="22" height="20"></td>
            <td background="img/button_bg.gif">
            
            <a href=<%=url%> class="btn12">返&nbsp;回</a></td>
          <td><img src="img/button2_l.gif" width="11" height="20"></td>
            <td width="20" ></td>  
          
          <td><img src="img/button_l.gif" border="0" ></td>
            <td background="img/button_bg.gif"><a href="javascript:frm_create()" class="btn12">保&nbsp;存</a></td>
          <td><img src="img/button_r.gif" border="0" ></td>
          </tr>
    </table>        
 
</form>