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
        
  if (check_Blank(document.frmPost.txtCampaignName, true, "名称"))
        return false;
    if (check_Blank(document.frmPost.txtCampaignType, true, "促销活动类型"))
        return false;
    if (check_Blank(document.frmPost.txtCustTypeList, true, "客户类型"))
        return false;
    if (check_Blank(document.frmPost.txtOpenSourceTypeList, true, "来源渠道列表"))
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
     if (check_Blank(document.frmPost.txtObligationFlag, true, "是否保底封顶促销"))
        return false;
     
           
    if (check_Blank(document.frmPost.txtStatus, true, "状态"))
        return false;
      if (document.frmPost.txtTimeLenUnitNumeber.value != '') {
          if (!check_Num(document.frmPost.txtTimeLenUnitNumeber, true, "绑定期限"))
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
function check_newMarketlist()
{
        document.frmPost.MarketSegmentList.value="";
	 
	if (document.frames.FrameMarketSegment.ListID!=null)
	{
        if (document.frames.FrameMarketSegment.ListID.length > 1) {
	    for (i=0;i<document.frames.FrameMarketSegment.ListID.length;i++)
		if (document.frames.FrameMarketSegment.ListID[i].checked)
		{
			if (document.frmPost.MarketSegmentList.value!="")
		        document.frmPost.MarketSegmentList.value = document.frmPost.MarketSegmentList.value + ";";
			document.frmPost.MarketSegmentList.value=document.frmPost.MarketSegmentList.value + document.frames.FrameMarketSegment.ListID[i].value;
		}
        } else {
            if (document.frames.FrameMarketSegment.ListID.checked) {
                document.frmPost.MarketSegmentList.value=document.frames.FrameMarketSegment.ListID.value + ";";
            } else {
                document.frmPost.MarketSegmentList.value = '';
            }
        }
	}
}
function check_newproductlist() {
     document.frmPost.ProductPackageList.value="";
	 
	if (document.frames.FrameProductPackage.ListID!=null)
	{
        if (document.frames.FrameProductPackage.ListID.length > 1) {
	    for (i=0;i<document.frames.FrameProductPackage.ListID.length;i++)
		if (document.frames.FrameProductPackage.ListID[i].checked)
		{
			if (document.frmPost.ProductPackageList.value!="")
		        document.frmPost.ProductPackageList.value = document.frmPost.ProductPackageList.value + ";";
			document.frmPost.ProductPackageList.value=document.frmPost.ProductPackageList.value + document.frames.FrameProductPackage.ListID[i].value;
		}
        } else {
            if (document.frames.FrameProduct.ListID.checked) {
                document.frmPost.ProductPackageList.value=document.frames.FrameProductPackage.ListID.value + ";";
            } else {
                document.frmPost.ProductPackageList.value = '';
            }
        }
	}
}

function check_oldproductlist() {

        document.frmPost.OldProductList.value="";
	 
	if (document.frames.FrameOldProduct.ListID!=null)
	{
        if (document.frames.FrameOldProduct.ListID.length > 1) {
	    for (i=0;i<document.frames.FrameOldProduct.ListID.length;i++)
		if (document.frames.FrameOldProduct.ListID[i].checked)
		{
			if (document.frmPost.OldProductList.value!="")
		        document.frmPost.OldProductList.value = document.frmPost.OldProductList.value + ";";
			document.frmPost.OldProductList.value=document.frmPost.OldProductList.value + document.frames.FrameOldProduct.ListID[i].value;
		}
        } else {
            if (document.frames.FrameOldProduct.ListID.checked) {
                document.frmPost.OldProductList.value=document.frames.FrameOldProduct.ListID.value + ";";
            } else {
                document.frmPost.OldProductList.value = '';
            }
        }
	}
	  
}
function frm_modify()
{
           if(check_form()){
            check_oldproductlist();
            check_newproductlist();
            check_newMarketlist();
            
            document.frmPost.action="campaign_modify.do";
           
            document.frmPost.submit();
            }
       
}

 

//-->
</SCRIPT>
 

<form name="frmPost" method="post">
 
 

 <input type="hidden" name="Action" value="update">
  
 <table  border="0" align="center" cellpadding="0" cellspacing="10" >
  <tr>
    <td><img src="img/list_tit.gif" width="19" height="19"></td>
    <td class="title">促销活动明细</td>
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
                 
    
           
                
 %>    
       
        <tr> 
          <td class="list_bg2" ><div align="right">促销活动ID</div></td>
          <td class="list_bg1" ><font size="2">                               
              <input type="text" name="txtCampaignID" size="25"  value="<tbl:write name="oneline" property="CampaignID"/>" class="textgray" readonly >
              </font></td>
          <td class="list_bg2"><div align="right">状态*</div></td>
          <td class="list_bg1"><font size="2">
              <d:selcmn name="txtStatus" mapName="SET_G_GENERALSTATUS" match="oneline:status" width="25"/>
              </font></td>
        </tr>

        <tr>
          <td  class="list_bg2" ><div align="right">名称*</div></td>
          <td class="list_bg1"><font size="2">
              <input type="text" name="txtCampaignName" size="25"  maxlength="50" value="<tbl:write name="oneline" property="CampaignName"/>">
              </font></td>
          <td  class="list_bg2"><div align="right">类型*</div></td>
          <td class="list_bg1"><font size="2">
              <d:selcmn name="txtCampaignType" mapName="SET_M_CAMPAIGNCAMPAIGNTYPE" match="oneline:campaignType" width="25"/>
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
	 <td  class="list_bg2" align="right">客户类型*</td>
	   <td class="list_bg1">
	   	<input name="txtCustTypeListValue" type="text" class="text" maxlength="200" size="25" value="<%=totalValue%>">
	   	<input name="txtCustTypeList" type="hidden" value="<tbl:write name="oneline"  property="CustTypeList"/>">
	   	<input name="checkbutton" type="button" class="button" value="请选择" onClick="javascript:open_select('SET_C_CUSTOMERTYPE','txtCustTypeList',document.frmPost.txtCustTypeList.value,'','','')"> 
	   </td>	   
          <td  class="list_bg2"><div align="right">绑定期限(月)*</div></td>
          <td  class="list_bg1"><font size="2">
               <input type="text" name="txtTimeLenUnitNumeber" size="10"   value="<tbl:write name="oneline" property="TimeLengthUnitNumber"/>">
              </font></td>
          
        </tr>

	<tr>
	   <td  class="list_bg2"><div align="right">来源渠道列表*</div></td>
	   <td class="list_bg1">
	   	<input name="txtOpenSourceTypeListValue" type="text" class="text" maxlength="200" size="25" value="<%=disPlayOpenSource%>">
	   	<input name="txtOpenSourceTypeList" type="hidden" value="<tbl:write name="oneline"  property="OpenSourceTypeList"/>">
	   	<input name="checkbutton" type="button" class="button" value="请选择" onClick="javascript:open_select('SET_C_CUSTOPENSOURCETYPE','txtOpenSourceTypeList',document.frmPost.txtOpenSourceTypeList.value,'','','')"> 
	   </td>	
           <td  class="list_bg2"><div align="right">是否允许暂停*</div></td>
           <td class="list_bg1"><font size="2">
              <d:selcmn name="txtAllowPause" mapName="SET_G_YESNOFLAG" match="oneline:AllowPause" width="25"/>
          </font></td>
          
        </tr>
        <tr>
          <td  class="list_bg2"><div align="right">是否允许迁移*</div></td>
          <td class="list_bg1"><font size="2">	
          <d:selcmn name="txtAllowTransition" mapName="SET_G_YESNOFLAG" match="oneline:AllowTransition" width="25"/>
          </font></td>
           <td  class="list_bg2"><div align="right">是否允许过户*</div></td>
           <td class="list_bg1"><font size="2">
           <d:selcmn name="txtAllowTransfer" mapName="SET_G_YESNOFLAG" match="oneline:AllowTransfer" width="25"/>
          </font></td>
        </tr>
        
        <tr>
          <td  class="list_bg2"><div align="right">是否允许销户*</div></td>
          <td class="list_bg1"><font size="2">
            <d:selcmn name="txtAllowClose" mapName="SET_G_YESNOFLAG" match="oneline:AllowClose" width="25"/>
           </font></td>
           <td  class="list_bg2"><div align="right">是否允许变更*</div></td>
           <td class="list_bg1"><font size="2">
           <d:selcmn name="txtAllowAlter" mapName="SET_G_YESNOFLAG" match="oneline:AllowAlter" width="25"/>
          </font></td>
        </tr>
        
	<tr>
           <td  class="list_bg2"><div align="right">系统暂停后是否计费*</div></td>
           <td class="list_bg1"><font size="2">
          <d:selcmn name="txtKeepBilling" mapName="SET_G_YESNOFLAG" match="oneline:KeepBilling" width="25"/>
          </font></td>
           <td  class="list_bg2"><div align="right">是否保底封顶促销*</div></td>
           <td class="list_bg1"><font size="2">
          <d:selcmn name="txtObligationFlag" mapName="SET_G_YESNOFLAG" match="oneline:obligationFlag" width="25"/>
          </font></td>
        </tr>
         
        <tr> 
          <td  class="list_bg2"><div align="right">描述</div></td>
          <td class="list_bg1" colspan="3"><font size="2">
              <input type="text" name="txtDescription" size="75" value="<tbl:write name="oneline" property="Description"/>">
              </font></td>
        </tr>
    </table>
    
    <table align="center" width="100%" border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg" >
      <tr>
	  <td class="import_tit" align="center"><font size="2">允许的市场分区信息</font></td>
     </tr>
       
    <tr>
       <td colspan="4"><font size="2">
        <iframe SRC="market_segment.screen?campaignID=<tbl:write name="oneline" property="CampaignID"/>" name="FrameMarketSegment" width="500" height="200"></iframe>
      </font></td> 
    </tr>
      <tr>
	  <td class="import_tit" align="center"><font size="2">新增产品包信息</font></td>
     </tr>
     <tr>
      <td  colspan="4"><font size="2">
        <iframe SRC="add_product_package.screen?campaignID=<tbl:write name="oneline" property="CampaignID"/>" name="FrameProductPackage" width="500" height="200"></iframe>
      </font></td> 
     </tr>
      <tr>
	  <td class="import_tit" align="center"><font size="2">已经定购产品信息</font></td>
     </tr>
     <tr>
          <td colspan="4"><font size="2">
          <iframe SRC="list_oldproduct.screen?campaignID=<tbl:write name="oneline" property="CampaignID"/>" name="FrameOldProduct" width="500" height="200"></iframe>
      </font></td> 
     </tr>
    </table>
    
        
        
      
      <input type="hidden" name="dtLastmod" value="<tbl:write name="oneline" property="DtLastmod" />">
      <input type="hidden" name="MarketSegmentList" value="">
      <input type="hidden" name="ProductPackageList" value="">
      <input type="hidden" name="OldProductList" value="">
      <input type="hidden" name="func_flag" value="5004" > 
 </lgc:bloop>



<br>   
    <table border="0" cellspacing="0" cellpadding="0" align=center>
        <tr>
           <td><img src="img/button2_r.gif" width="22" height="20"></td>
            <td background="img/button_bg.gif"><a href="<bk:backurl property="campaign_query.do/group_bargain_query_result.do" />" class="btn12">返&nbsp;回</a></td>
          <td><img src="img/button2_l.gif" width="11" height="20"></td>
            <td width="20" ></td>  
            
           <td><img src="img/button_l.gif" border="0" ></td>
            <td background="img/button_bg.gif"><a href="javascript:frm_modify()" class="btn12">修&nbsp;改</a></td>
          <td><img src="img/button_r.gif" border="0" ></td>
          
           
           
          </tr>
    </table>        
 
</form>