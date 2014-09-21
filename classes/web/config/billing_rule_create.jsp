<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="resultset" prefix="rs" %>
<%@ taglib uri="osstags" prefix="d" %>
<%@ taglib uri="bookmark" prefix="bk" %>
<%@ page import="com.dtv.oss.util.Postern,
              
               java.util.*" %>
 

<script language=javascript>
 

function ChangeReason()
{
    
    document.FrameUS.submit_update_select('geteventreason', document.frmPost.txtEventClass.value, 'txtEventReason', '');
} 
function check_form(){	
	if (check_Blank(document.frmPost.txtEventClass, true, "事件类型"))
		return false;
	 
	if (check_Blank(document.frmPost.txtValue, true, "金额") )
		return false;
	 
	if (check_Blank(document.frmPost.txtStatus, true, "状态"))
		return false;
	if (check_Blank(document.frmPost.txtAcctItemTypeID, true, "账目类型"))
		return false;	
	if (!check_Float(document.frmPost.txtValue, true, "金额"))
		return false;		
	if (!check_Num(document.frmPost.txtFeeSplitPlanID, true, "费用摊消计划ID"))
		return false;	
	if (!check_Num(document.frmPost.txtOldPortNo, true, "已有端口数"))
		return false;	
	if (!check_Num(document.frmPost.txtNewPortNo, true, "新增端口数"))
		return false;		
	return true;
} 
function checkDate(){
 
	if (document.frmPost.txtValidFrom.value != ''){
		if (!check_TenDate(document.frmPost.txtValidFrom, true, "开始日期")){
			return false;
		}
	}
	 
	if (document.frmPost.txtValidTo.value != ''){
		if (!check_TenDate(document.frmPost.txtValidTo, true, "结束日期")){
			return false;
		}
	}
	 
	if(!compareDate(document.frmPost.txtValidFrom,document.frmPost.txtValidTo,"结束日期必须大于等于开始日期")){
		
		return false;
	}
	 
	            
	return true;
} 
function billing_rule_create_submit() {
 if (check_form() && checkDate()){
	    document.frmPost.action="billing_rule_create.do";
	    document.frmPost.Action.value="create";
	    document.frmPost.submit();
	  }
   
  
  
}
function back_submit(){
    document.location.href= "billing_rule_query.do?txtFrom=1&txtTo=10";
}
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

</script>
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
 
 
<table width="820" align="center" cellpadding="0" cellspacing="0">
<tr><td>
<table  border="0" align="center" cellpadding="0" cellspacing="10">
 <tr>
    <td colspan="2" height="8"></td>
  </tr>
  <tr>
    <td><img src="img/list_tit.gif" width="19" height="19"></td>
    <td  class="title">计费规则管理-创建</td>
  </tr>
  </table>
  <table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>
  <%
     Map envetMap = null;
     envetMap=Postern.getBillingSystemEvent("B");
     pageContext.setAttribute("EVENTNAME",envetMap);
     Map productMap = null;
     productMap = Postern.getAllProductIDAndName();
     pageContext.setAttribute("PRODUCTMAP",productMap);
     Map productPackageMap = null;
     productPackageMap = Postern.getAllProductPackageIDAndName();
     pageContext.setAttribute("PRODUCTPACKAGEMAP",productPackageMap);
     Map acctTypeMap = null;
     acctTypeMap = Postern.getAllFeeType();
     pageContext.setAttribute("ACCTTYPEMAP",acctTypeMap); 
     Map custList = Postern.getBillingRuleByCustType("C");
     pageContext.setAttribute("CUSTLIST",custList);
      Map accountList = Postern.getBillingRuleByCustType("A");
     pageContext.setAttribute("ACCOUNTLIST",accountList);
      Map  campaignList = Postern.getBillingRuleByCustType("M");
     pageContext.setAttribute("CAMPAIGNLIST",campaignList);
      Map planMap = Postern.getPlanMap();
    pageContext.setAttribute("PLANMAP",planMap);
    
     Map catvTermType = Postern.getBillingRuleByCustType("TT");
     pageContext.setAttribute("CATVTERMTYPE",catvTermType);
     Map cableType = Postern.getBillingRuleByCustType("CT");
      pageContext.setAttribute("CABLETYPE",cableType);
      
       Map doubleFlag= Postern.getBillingRuleByCustType("BT");
     pageContext.setAttribute("DOUBLE",doubleFlag);
     
     //S	业务帐户类型列表
       Map serviceAccountList= Postern.getBillingRuleByCustType("S");
     pageContext.setAttribute("SERVICEACCOUNTLIST",serviceAccountList);
     
     //G	市场分区列表
       Map marketList = Postern.getBillingRuleByCustType("G");
     pageContext.setAttribute("MARKETLIST",marketList);
  %>

 
<form name="frmPost" method="post" >
  <input type="hidden" name="func_flag" value="131" >
  <input type="hidden" name="Action" value=""/>
 
   <table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
      <tr> 
          <td  class="list_bg2"><div align="right">计费规则名称</div></td>         
          <td class="list_bg1"   colspan="3" align="left">
           <input type="text" name="txtBrName" maxlength="100" size="87"  value="<tbl:writeparam name="txtBrName" />" >
           </td>      
            
      </tr>
       <tr> 
          
            <td class="list_bg2"><div align="right">计费规则描述</div></td>
	    <td class="list_bg1"  colspan="3" align="left">
	     <input type="text" name="txtBrDesc" maxlength="100" size="87"  value="<tbl:write name="oneline" property="brDesc" />" >
               
            </td>
      </tr>
       <tr> 
           <td class="list_bg2"><div align="right">事件类型*</div></td>
	   <td class="list_bg1" align="left" >
	   <tbl:select name="txtEventClass" set="EVENTNAME" match="txtEventClass" width="25" onchange="ChangeReason()"/>   
	   </td>
	   <td class="list_bg2"><div align="right">事件原因</div></td>
	   <td class="list_bg1" align="left">
	    <d:selRes name="txtEventReason" parentMatch="txtEventClass" match="txtEventReason" width="25" />
	    </td>
       </tr>
       <tr> 
            <td class="list_bg2"><div align="right">运营商产品</div></td>
	    <td class="list_bg1" align="left" >
		 <tbl:select name="txtProductID" set="PRODUCTMAP" match="txtProductID" width="25" />
	   </td>
	    <td class="list_bg2"><div align="right">运营商产品包</div></td>
	    <td class="list_bg1" align="left">
		 <tbl:select name="txtProductPackageID" set="PRODUCTPACKAGEMAP" match="txtProductPackageID" width="25" />
	    </td>
       </tr> 
        <tr> 
              <td class="list_bg2"><div align="right">目标运营商产品</div></td>
	      <td class="list_bg1" align="left">
	   	<tbl:select name="txtDestProductID" set="PRODUCTMAP" match="txtDestProductID" width="25" />
	   	
	     </td>
	      <td class="list_bg2"><div align="right">目标运营商产品包</div></td>
	      <td class="list_bg1" align="left">
	   	<tbl:select name="txtDestPackageID" set="PRODUCTPACKAGEMAP" match="txtDestPackageID" width="25" />	   
              
      </tr>
       <tr>   
               <td class="list_bg2"><div align="right">客户类型条件列表</div></td>
	      <td class="list_bg1" align="left">
		  <tbl:select name="txtCustType" set="CUSTLIST" match="txtCustType" width="25" />
	      </td> 
               <td class="list_bg2"><div align="right">帐户类型条件列表</div></td>
	       <td class="list_bg1" align="left" >
		 <tbl:select name="txtAcctItem" set="ACCOUNTLIST" match="txtAcctItem" width="25" />
	      </td>
	      
         </tr> 
        <tr>   
               <td class="list_bg2"><div align="right">业务帐户类型条件列表</div></td>
	      <td class="list_bg1" align="left">
		  <tbl:select name="txtUserType" set="SERVICEACCOUNTLIST" match="txtCustType" width="25" />
	      </td> 
               <td class="list_bg2"><div align="right">市场分区条件列表</div></td>
	       <td class="list_bg1" align="left" >
		 <tbl:select name="txtMarketSegmentID" set="MARKETLIST" match="txtAcctItem" width="25" />
	      </td>
	      
         </tr>   
       <tr> 
            <td class="list_bg2"><div align="right">促销活动条件列表</div></td>
	       <td class="list_bg1" align="left">
		 <tbl:select name="txtCampaign" set="CAMPAIGNLIST" match="txtCampaign" width="25" />
	   	 
	      </td> 
           <td class="list_bg2"><div align="right">账目类型*</div></td>
	   <td class="list_bg1" align="left">
	    <tbl:select name="txtAcctItemTypeID" set="ACCTTYPEMAP" match="txtAcctItemTypeID" width="25" />
	   </td>
	    
         </tr>
         <tr> 
             
              <td class="list_bg2"><div align="right">线缆类型计费条件</div></td>
	       <td class="list_bg1" align="left">
		 <tbl:select name="txtCableType" set="CABLETYPE" match="txtCableType" width="25" /> 
	   	 
	      </td>
	        <td class="list_bg2"><div align="right">双向网标记计费条件</div></td>
	       <td class="list_bg1" align="left">
		 <tbl:select name="txtDoubleFlag" set="DOUBLE" match="txtDoubleFlag" width="25" /> 
	   	 
	      </td>
         </tr>
         <tr> 
         
          <td class="list_bg2"><div align="right">终端类型计费条件</div></td>
	       <td class="list_bg1" align="left">
		  <tbl:select name="txtCatvTermType" set="CATVTERMTYPE" match="txtCatvTermType" width="25" />
	      </td> 
         <td  class="list_bg2"><div align="right">金额*</div></td>         
             <td class="list_bg1" align="left">
             <input type="text" name="txtValue" maxlength="10" size="27"  value="<tbl:writeparam name="txtValue" />" >
             </td>    	   
            
          
         </tr>
          <tr> 
           <td class="list_bg2"><div align="right">是否允许退还</div></td>
	     <td class="list_bg1" align="left">
	       <d:selcmn name="txtAllowFlag" mapName="SET_G_YESNOFLAG" match="txtAllowFlag" width="25" />
             </td>
             <td class="list_bg2"><div align="right">状态*</div></td>
              <td class="list_bg1" align="left">
              <d:selcmn name="txtStatus" mapName="SET_G_GENERALSTATUS" match="txtStatus" width="25" /> 
              </td>  
            
           
         </tr>
         <tr>  
          <td class="list_bg2"><div align="right">租费计算周期类型</div></td>
	     <td class="list_bg1" align="left">
	        <d:selcmn name="txtRfBillingCycleFlag" mapName="SET_F_RFBILLINGCYCLEFLAG" match="txtRfBillingCycleFlag" width="25" />
	       
             </td>
          <td class="list_bg2"><div align="right">费用摊消计划</div></td>
            <td class="list_bg1">
             <input type="text" name="txtFeeSplitPlanID" maxlength="10" size="27"  value="<tbl:writeparam name="txtFeeSplitPlanID" />" >
               
           </td>
            
           
            
    </tr>
    <tr>
      <td class="list_bg2"><div align="right">有效期开始</div></td>
             <td class="list_bg1" align="left">
            <input maxlength="10" onfocus="viewModule(this)" onKeydown="inputDate(document.frmPost.txtValidFrom)" onblur="lostFocus(this)" type="text" name="txtValidFrom" size="27" value="<tbl:writeparam name="txtValidFrom"/>" ><IMG onclick="MyCalendar.SetDate(this,document.frmPost.txtValidFrom,'Y')" src="img/calendar.gif" style=cursor:hand border="0">
           </td>
       <td class="list_bg2"><div align="right">有效期结束</div></td>
               <td class="list_bg1"   align="left">
           <input maxlength="10" onfocus="viewModule(this)" onKeydown="inputDate(document.frmPost.txtValidTo)" onblur="lostFocus(this)" type="text" name="txtValidTo" size="27" value="<tbl:writeparam name="txtValidTo"/>" ><IMG onclick="MyCalendar.SetDate(this,document.frmPost.txtValidTo,'Y')" src="img/calendar.gif" style=cursor:hand border="0">
     	</td>
    </tr>
     <tr> 
          <td  class="list_bg2"><div align="right">已有端口数</div></td>         
          <td class="list_bg1" align="left">
           <input type="text" name="txtOldPortNo" maxlength="10" size="27"  value="<tbl:writeparam name="txtOldPortNo" />" >
           </td>      
           <td  class="list_bg2"><div align="right">新增端口数</div></td>         
          <td class="list_bg1" align="left">
           <input type="text" name="txtNewPortNo" maxlength="10" size="27"  value="<tbl:writeparam name="txtNewPortNo" />" >
           </td>      
      </tr>
      </table>
      <br>
        <table align="center"  border="0" cellspacing="0" cellpadding="0">
         <tr>  
            <td><img src="img/button2_r.gif" width="22" height="20"></td>
			<td><input name="Submit" type="button" class="button"
					value="返&nbsp;回" onclick="javascript:back_submit()"></td>
            <td><img src="img/button2_l.gif" width="11" height="20"></td>
            <td width="20" ></td>  

            <td><img src="img/button_l.gif" width="11" height="20"></td>
            <td><input name="Submit" type="button" class="button"
					value="保&nbsp;存" onclick="javascript:billing_rule_create_submit()"></td>
            <td><img src="img/button_r.gif" width="22" height="20"></td>          
        </tr>
      </table>	
      
     
     
  </td>
  </tr>
</form>  
</table>  
 