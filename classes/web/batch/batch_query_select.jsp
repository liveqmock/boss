<%@ taglib uri="/WEB-INF/oss.tld" prefix="d" %>
<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
                
<%@ page import="com.dtv.oss.util.Postern, java.util.*" %>
<%@ page import="com.dtv.oss.dto.OperatorDTO,
                com.dtv.oss.dto.BrconditionDTO,
                com.dtv.oss.web.util.WebUtil,
                   com.dtv.oss.util.Postern" %>
<%@ page import="com.dtv.oss.web.util.CurrentOperator,com.dtv.oss.web.util.LogonWebCurrentOperator" %>
<%
	String strType=request.getParameter("type");
	String strTypeName=request.getParameter("typeName");
	String strTypeValue=request.getParameter("typeValue");
	String strParentType=request.getParameter("parentType");
	String strParentTypeName=request.getParameter("parentTypeName");
	String strParentTypeValue=request.getParameter("parentTypeValue");
	String strTitle="";
	
	if("SET_C_CUSTOMERTYPE".equals(strType))
		strTitle="客户类型";
	if("SET_D_DEVICEUSEFORPURPOSE".equals(strType))
		strTitle="设备用途";
	if("SET_F_ACCOUNTBANKACCOUNTSTATUS".equals(strType))
		strTitle="银行账户串配状态";
	if("SET_P_PRODUCTCLASS".equals(strType))
		strTitle="产品类别";
	if("SET_F_ACCOUNTCLASS".equals(strType))
		strTitle="帐户类别";	
	else if("SET_C_CUSTOMERSTATUS".equals(strType))
		strTitle="客户状态";
	else if("SET_C_CUSTOPENSOURCETYPE".equals(strType))
		strTitle="开户来源渠道";
	else if("CustOpenSourceTypeID".equals(strType))
		strTitle="开户来源渠道ID";
	else if("DISTRICTTYPE".equals(strType))
		strTitle="地址所在区域";
	else if("DISTRICT".equals(strType))
		strTitle="行政区域";
	else if("SET_F_ACCOUNTTYPE".equals(strType))
		strTitle="账户类型";
	else if("SET_F_ACCOUNTSTATUS".equals(strType))
		strTitle="账户状态";
	else if("SET_V_CUSTSERVICEINTERACTIONTYPE".equals(strType))
		strTitle="使用场合";	
	else if("SET_A_CATVTERMTYPE".equals(strType))
		strTitle="终端类型";
	else if("SET_A_CABLETYPE".equals(strType))
		strTitle="线缆类型";			
	else if("SET_G_YESNOFLAG".equals(strType))
		strTitle="双向网标";	 
	else if("ALLMOPLIST".equals(strType))
		strTitle="账户支付类型";
	else if("SET_F_INVOICESTATUS".equals(strType))
		strTitle="账单状态";
	else if("SET_C_CUSTOMERPRODUCTPSTATUS".equals(strType))
		strTitle="客户产品状态";
	else if("PRODUCT".equals(strType))
		strTitle="对应的运营商产品";
	else if("CAMPAIGN".equals(strType))
		strTitle="参加的促销活动列表";
	else if("PRODUCTPACKAGE".equals(strType))
		strTitle="运营商产品包";
	else if("CUSTOMERTYPE".equals(strType))
		strTitle="客户类型";
	else if("CAMPAIGNTYPE".equals(strType))
		strTitle="促销活动";	
	else if("ACCTITEMTYPE".equals(strType))
		strTitle="账户类型";
	else if("MARKETSEGMENT".equals(strType))
		strTitle="市场分区";	
	else if("SWAPDEVICEREASON".equals(strType))
		strTitle="设备更换原因";	
	else if("SET_D_DEVICEUSEFORPURPOSE".equals(strType))
		strTitle="设备用途";	
	else if("PACKAGE".equals(strType))
		strTitle="对应的运营商产品包";
	else if("SET_C_USER_TYPE".equals(strType))
		strTitle="业务帐户类型";	
		
		else if("devicemodel".equals(strType))
		strTitle="设备型号";	
	else if("CSICREATEREASON".equals(strType))
		strTitle="受理原因";			
		else if("csiCreateReason2".equals(strType))
		strTitle="受理原因";				
	 
	pageContext.setAttribute("MATCH",strTypeValue);

%>

<script language=javascript>
<!--

function AllChoose(){
     	
     if (document.frmPost.allchoose.checked){
        if (document.frmPost.partchoose[0]!=null){
            for (i=1 ;i< document.frmPost.partchoose.length ;i++){
              document.frmPost.partchoose[i].checked =true;
           }
       }
     }
      else {
        if (document.frmPost.partchoose[0]!=null){
            for (i=1 ;i< document.frmPost.partchoose.length ;i++){
               document.frmPost.partchoose[i].checked =false;
            }
       }
     }   
  }
 
 
   
function ok_submit(){
	
	if(document.frmPost.partchoose[0]==null){
     		alert("您没有选择选项");
     		return;
     	}
     		
	var hiddenValue="";
	var textValue="";
	
	var hiddenName="" + "<%=strTypeName %>";
	
	var textName=hiddenName + "Value";
	
	//得到选择的隐含和文本值
	if (document.frmPost.partchoose[0]!=null){
		for (i=1 ;i< document.frmPost.partchoose.length ;i++){
              		if(document.frmPost.partchoose[i].checked){
              	
              			if(hiddenValue!="")
              				hiddenValue=hiddenValue + ",";
              			if(textValue!="")
              				textValue=textValue + ",";
              	
              			hiddenValue=hiddenValue + document.frmPost.partchoose[i].value;
              			textValue=textValue + document.frmPost.partchoose_Value[i].value;
              		}
           	}
       }
       
       //把textValue中的“|”、“-”去掉
       while(textValue.indexOf("|")!=-1){
       		textValue=textValue.replace("|","");
      	}
      	while(textValue.indexOf("-")!=-1){
      		textValue=textValue.replace("-","")
      	}
        
       //给主页设置值
       window.opener.frmPost.elements[hiddenName].value=hiddenValue;
       
       window.opener.frmPost.elements[textName].value=textValue;
        
       //alert(window.parent.frmPost.elements[textName].value);
       //alert(window.opener.frmPost.txtQueryName);
	
	self.close();
}
   
function ok_submit1(){
	
	if(document.frmPost.ListID[0]==null){
     		alert("您没有选择选项");
     		return;
     	}
     		
	var hiddenValue="";
	var textValue="";
	
	var hiddenName="" + "<%=strTypeName %>";
	 
	var textName=hiddenName + "Value";
	 
	//得到选择的隐含和文本值
	if (document.frmPost.ListID[0]!=null){
		for (i=1 ;i< document.frmPost.ListID.length ;i++){
              		if(document.frmPost.ListID[i].checked){
              	
              			if(hiddenValue!="")
              				hiddenValue=hiddenValue + ",";
              			if(textValue!="")
              				textValue=textValue + ",";
              	
              			hiddenValue=hiddenValue + document.frmPost.ListID[i].value;
              			textValue=textValue + document.frmPost.ListID_Value[i].value;
              		}
           	}
       }
       
       //把textValue中的“|”、“-”去掉
       while(textValue.indexOf("|")!=-1){
       		textValue=textValue.replace("|","");
      	}
      	while(textValue.indexOf("-")!=-1){
      		textValue=textValue.replace("-","")
      	}
       
       //给主页设置值
       window.opener.frmPost.elements[hiddenName].value=hiddenValue;
       window.opener.frmPost.elements[textName].value=textValue;
       //alert(window.parent.frmPost.elements[textName].value);
       //alert(window.opener.frmPost.txtQueryName);
	
	self.close();
}


//-->
</script>

<form name="frmPost" method="post" action="">
<input type="hidden" name="partchoose" value="">
<input type="hidden" name="ListID" value="">
<input type="hidden" name="ListID_Value" value="">
<input type="hidden" name="partchoose_Value" value="">

<table  border="0" align="center" cellpadding="0" cellspacing="10" >
  <tr>
    <td><img src="img/list_tit.gif" width="16" height="16"></td>
    <td class="title" ><font size="2">请选择<%=strTitle%></font></td>
  </tr>
</table>

<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>
<%
  if("CUSTOMERTYPE".equals(strType)||"CAMPAIGNTYPE".equals(strType)||"ACCTITEMTYPE".equals(strType)){
     List brDtoList = null;
     if("CUSTOMERTYPE".equals(strType))
         brDtoList = Postern.getBillingRuleDTOByType("C");
      if("CAMPAIGNTYPE".equals(strType))
         brDtoList = Postern.getBillingRuleDTOByType("M");
       if("ACCTITEMTYPE".equals(strType))
         brDtoList = Postern.getBillingRuleDTOByType("A");
     %>
   <table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
    <tr class="list_head">
    
     <td class="list_head" height="5"></td>
    
    <td class="list_head" height="5">条件ID</td>
    <td class="list_head" height="5">条件名称</td>
    <td class="list_head" height="5">条件值</td>
   </tr>   
     
     <%
     if(brDtoList!=null){
     Iterator ite = brDtoList.iterator();
     while (ite.hasNext()){
        BrconditionDTO dto = (BrconditionDTO)ite.next();
        pageContext.setAttribute("oneline",  dto);
        boolean checkFlag =false;
         System.out.println("******strTypeValue**********"+strTypeValue);
        if (dto.getBrcntID() == WebUtil.StringToInt(strTypeValue)){
           checkFlag =true;
            
        }
        
      
     %>
     <tr class="list_bg1" onmouseover="this.className='list_over'" onmouseout="this.className='list_bg1'">
		
	 <td align="center"><input type="radio" <% if (checkFlag){ %> checked <% }%> name="ListID" value="<tbl:write name="oneline" property="brcntID"/>"></td>
	 <td align="center"><tbl:writenumber name="oneline" property="brcntID" digit="7" /></td>
      	 <td align="center">
      	    <input type="hidden" name="ListID_Value" value="<tbl:write name="oneline" property="CntName"/>" >
      	    <tbl:write name="oneline" property="CntName"/>
      	 </td> 
      	 <td align="center"><tbl:write name="oneline" property="CntValueStr"/></td> 
      			 
     </tr>
     <%
   
     }
   }
   
    
   
%>

  <tr>
    <td colspan="4" class="list_foot"></td>
  </tr>

</table>
<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
    <tr>
      <td><img src="img/mao.gif" width="1" height="1"></td>
    </tr>
  </table>
  
  <BR>
<table border="0" cellspacing="0" cellpadding="0">
  <tr>
  
  <%
  	if(!(strTypeName==null || "".equals(strTypeName))){
  %>
    <td width="20" ></td>		
    <td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
    <td><input name="Submit" type="button" class="button" value="确认" onclick="javascript:ok_submit1()"></td>
    <td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
  <%}
   %>	
    
    <td width="20" ></td>	
    <td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
    <td><input name="Submit" type="button" class="button" value="关闭窗口" onclick="javascript:self.close();"></td>
    <td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
  </tr>
</table>
            
 <%
  
  }else{
   
 %>
<table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
  <tr class="list_head">
    <td class="list_head" height="5"><div align="center"><input type="checkbox" name="allchoose" value="" onclick="AllChoose()"></div></td>
    <td class="list_head" height="5">键值</td>
    <td class="list_head" height="5">描叙</td>
  </tr>
  
  <%
  	//开户来源渠道ID，处理
  	if("CustOpenSourceTypeID".equals(strType)){
  		Map map=Postern.getOrgIDAndOrgNameByParentOrgIDs(strParentTypeValue);
  		pageContext.setAttribute("CUSTOPENSOURCETYPEID",map);
  %>
  	<d:showCommonSettingData controlName="partchoose" style1="list_bg1" style2="list_bg2" overStyle="list_over" setName="CUSTOPENSOURCETYPEID" match="MATCH" />
  <%
  	}
  	//运营商产品
  	else if("PRODUCT".equals(strType)){
  		Map map=Postern.getAllProductIDAndName();
  		pageContext.setAttribute("PRODUCT",map);
  %>
  	<d:showCommonSettingData controlName="partchoose" style1="list_bg1" style2="list_bg2" overStyle="list_over" setName="PRODUCT" match="MATCH" />
  	 <%
  	}
  	//运营商产品包
  	else if("PACKAGE".equals(strType)){
  		Map map=Postern.getAllProductPackageIDAndName();
  		pageContext.setAttribute("PACKAGE",map);
  %>
  	<d:showCommonSettingData controlName="partchoose" style1="list_bg1" style2="list_bg2" overStyle="list_over" setName="PACKAGE" match="MATCH" />
  	 <%
  	}
  	//行政区域
  	
  	else if("DISTRICT".equals(strType)){
  		Map map=Postern.getAllDistrictIDAndName();
  		System.out.println("********map******************"+map);
  		pageContext.setAttribute("DISTRICTMAP",map);
  %>
  	<d:showCommonSettingData controlName="partchoose" style1="list_bg1" style2="list_bg2" overStyle="list_over" setName="DISTRICTMAP" match="MATCH" />
  <%
   }
  	 
  	//优惠活动
  	else if("CAMPAIGN".equals(strType)){
  		Map map=Postern.getAllCampaignIDAndName();
  		 
  		pageContext.setAttribute("CAMPAIGN",map);
  %>
  	<d:showCommonSettingData controlName="partchoose" style1="list_bg1" style2="list_bg2" overStyle="list_over" setName="CAMPAIGN" match="MATCH" />
  <%
     }
        //市场分区
  	else if("MARKETSEGMENT".equals(strType)){
  	 
  		Map map=Postern.getAllMarketSegmentName();
  		 
  		pageContext.setAttribute("MARKETSEGMENT",map);
  %>
  	<d:showCommonSettingData controlName="partchoose" style1="list_bg1" style2="list_bg2" overStyle="list_over" setName="MARKETSEGMENT" match="MATCH" />
  <%
       
  
  	}
  	 //设备更换原因
  	else if("SWAPDEVICEREASON".equals(strType)){
  	 
  		Map map=Postern.getCsiReasonByDevice();
  		 
  		pageContext.setAttribute("SWAPDEVICEREASON",map);
  %>
  	<d:showCommonSettingData controlName="partchoose" style1="list_bg1" style2="list_bg2" overStyle="list_over" setName="SWAPDEVICEREASON" match="MATCH" />
  <%
       
  
  	}
  	
  	  	 //设备类型
  	else if("devicemodel".equals(strType)){
  	 
  		Map map=Postern.getAllDeviceModels();
  		 
  		pageContext.setAttribute("devicemodel",map);
  %>
  	<d:showCommonSettingData controlName="partchoose" style1="list_bg1" style2="list_bg2" overStyle="list_over" setName="devicemodel" match="MATCH" />
  <%
       
  
  	}
  	
  	
  	//运营商产品包
  	else if("PRODUCTPACKAGE".equals(strType)){
  		Map map=Postern.getAllProductPackageIDAndName();
  		pageContext.setAttribute("PRODUCTPACKAGE",map);
 %>
	<d:showCommonSettingData controlName="partchoose" style1="list_bg1" style2="list_bg2" overStyle="list_over" setName="PRODUCTPACKAGE" match="MATCH" />
  <%
  	}
  	//支付方式
  	else if("ALLMOPLIST".equals(strType)){
  		pageContext.setAttribute("AllMOPList" , Postern.getOpeningMop()); 
  %>
  	<d:showCommonSettingData controlName="partchoose" style1="list_bg1" style2="list_bg2" overStyle="list_over" setName="AllMOPList" match="MATCH" />
  <%
  	}
  	
  	//所在区域
  	else if("DISTRICTTYPE".equals(strType)){
  		Map districtTypMap = new HashMap();
  		LogonWebCurrentOperator wrapOper = (LogonWebCurrentOperator)CurrentOperator.GetCurrentOperator(pageContext.getSession());
  		if (wrapOper!=null)
        	{
            		OperatorDTO dtoOper = (OperatorDTO)wrapOper.getCurrentOperator();
            		districtTypMap=Postern.getChargeDistrictSettingByOpOrgID(dtoOper.getOrgID());
        	}
        	pageContext.setAttribute("AllDistrictList", districtTypMap);
  %>
  	<d:showCommonSettingData controlName="partchoose" style1="list_bg1" style2="list_bg2" overStyle="list_over" setName="AllDistrictList" match="MATCH" worldAlign="left" />
  <%
  	}
  	
  	else {
  %>
  	<d:showCommonSettingData controlName="partchoose" style1="list_bg1" style2="list_bg2" overStyle="list_over" mapName="<%=strType%>" match="MATCH" />
	
  <%
  	}
  	}
  %>
  
  <tr>
    <td colspan="3" class="list_foot"></td>
  </tr>

</table>

  
  <table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
    <tr>
      <td><img src="img/mao.gif" width="1" height="1"></td>
    </tr>
  </table>
  
  <BR>
<table border="0" align="center" cellspacing="0" cellpadding="0">
  <tr>
  
  <%
         if(!"CUSTOMERTYPE".equals(strType) &&  !"CAMPAIGNTYPE".equals(strType) && !"ACCTITEMTYPE".equals(strType)) { 
  	  if(!(strTypeName==null || "".equals(strTypeName))){
  %>
    <td width="20" ></td>		
    <td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
    <td><input name="Submit" type="button" class="button" value="确认" onclick="javascript:ok_submit()"></td>
    <td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
    <td width="20" ></td>	
         <%}
          %>	
    <td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
    <td><input name="Submit" type="button" class="button" value="关闭窗口" onclick="javascript:self.close();"></td>
    <td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
    <%}%>
  </tr>
</table>
            
<BR>

</form>  
         

      