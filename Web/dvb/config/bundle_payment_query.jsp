<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="/WEB-INF/oss.tld" prefix="d" %>
<%@ taglib uri="resultset" prefix="rs" %>
<%@ taglib uri="bookmark" prefix="bk" %>
<%@ page import="com.dtv.oss.web.util.CommonKeys" %>
<%@ page import="com.dtv.oss.dto.*" %>
<%@ page import="com.dtv.oss.util.Postern,java.util.*" %>

<Script language=javascript>
<!--
  
  
 

 
function add_submit(){
	document.frmOther.action="add_bundle_payment.screen";
	document.frmOther.submit();
}

function view_submit(bundleID,rfBillingCycleFlag){

	document.frmOther.action="bundle_payment_modify.screen?txtBundleID="+bundleID+"&txtRfBillingCycleFlag="+rfBillingCycleFlag;
	 
	document.frmOther.submit();
}

 
function back(){
 
    var campaignID=document.frmOther.txtCampaignID.value;
    
   
     document.location.href= "campaign_detail.do?txtCampaignID="+campaignID;
     
} 
function delete_object(strFlag){
    if( confirm("确定要删除该记录吗?") ){
        document.frmOther.action="delete_bundle_payment.do?txtRfBillingCycleFlag="+strFlag;
        document.frmOther.txtActionType.value="deletepaymentmethod";
         
        document.frmOther.submit();
    }
	
} 
 
//-->
</SCRIPT>

<form name="frmOther" method="post">
	   
	 <tbl:hiddenparameters pass="txtCampaignID" />
	  
	 <input type="hidden" name="txtActionType" value="">
</form>

<table border="0" cellspacing="0" cellpadding="0" width="820" >
<tr>
<td>
<table  border="0" align="center" cellpadding="0" cellspacing="10" >
  <tr>
    <td><img src="img/list_tit.gif" width="19" height="19"></td>
    <td class="title">套餐续费方式-查询</td>
  </tr>
</table>

<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>

<% 
   
   int campaignID=0;
   if(!(request.getParameter("txtCampaignID")==null || "".equals(request.getParameter("txtCampaignID"))))
   	campaignID=Integer.parseInt(request.getParameter("txtCampaignID"));
   	
   CampaignDTO cDTO=Postern.getCampaignDTOByCampaignID(campaignID);
  
   String status=cDTO.getStatus();
   pageContext.setAttribute("camDTO",cDTO);
    
 %>

<table align="center" width="98%" border="0" cellspacing="1" cellpadding="5" class="list_bg">
	<tr>
	    <td class="import_tit" align="center" colspan="4"><font size="2">套餐活动信息</font></td>
	</tr>
	
        <tr>
            <td class="list_bg2" width="25%"><div align="right">套餐活动ID</div></td>
            <td class="list_bg1" width="25%"><tbl:write name="camDTO" property="campaignID" /></td>
            <td class="list_bg2" width="25%"><div align="right">名称</div></td>
            <td class="list_bg1" width="25%"><tbl:write name="camDTO" property="campaignName" /></td>
        </tr>
       
        <tr>
            <td class="list_bg2"><div align="right">有效开始时间</div></td>
            <td class="list_bg1"><tbl:writedate name="camDTO" property="dateFrom" /></td>
            <td class="list_bg2"><div align="right">有效结束时间</div></td>
            <td class="list_bg1"><tbl:writedate name="camDTO" property="dateTo" /></td>
        </tr>
        <tr>
            <td class="list_bg2"><div align="right">状态</div></td>
            <td class="list_bg1"><d:getcmnname typeName="SET_G_GENERALSTATUS" match="camDTO:status"/></td>
            <td class="list_bg2"></td>
            <td class="list_bg1"></td>
        </tr> 
    </table>
    
    <table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>
 

<form name="frmPost" method="post">
    
     
    
   
    
   
    <input type="hidden" name="func_flag" size="20" value="104">
    
    
        
     
        
    
    <table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
	<tr align="center">
	     <td class="list_bg1">
	     <table  border="0" cellspacing="0" cellpadding="0">
		  <tr>
		     
		         <td><img src="img/button2_r.gif" width="20" height="20"></td>
	                 <td><input name="Submit" type="button" class="button"
					value="返&nbsp;回" onclick="javascript:back()"></td>
                         <td><img src="img/button2_l.gif" width="11" height="20"></td>
		       <% if (!"I".equals(status)){ %>  
		  	 
			<td width="20" ></td>	
			<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
			<td><input name="Submit" type="button" class="button" value="添 加" onclick="javascript:add_submit()"></td>
			<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
			<%}%>
			 
		  </tr>
	   </table>
	   </td>
	</tr>
    </table>    
    
    
    <table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  	<tr><td><img src="img/mao.gif" width="1" height="1"></td></tr>
    </table>
    <br>

 

<table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
  <tr class="list_head">
    
    <td class="list_head">套餐ID</td>
    <td class="list_head">租费计算周期类型</td>
    <td class="list_head">续费名称</td>
     <td class="list_head">续费时长单位</td>
    <td class="list_head">套餐预付时长</td>
    <td class="list_head">创建时间</td>
   <td class="list_head"></td> 
  </tr> 
   <%
         
        List  dtoList =   Postern.getBundlePayMethodDTOList(campaignID);
        
        if (dtoList!=null){
          Iterator ite = dtoList.iterator();
          while (ite.hasNext()){ 
          BundlePaymentMethodDTO bundlePayMethodDto= (BundlePaymentMethodDTO) ite.next();
          pageContext.setAttribute("oneline",bundlePayMethodDto);
          
    %>
 
<tbl:printcsstr style1="list_bg1" style2="list_bg2" overStyle="list_over" >

  
    
    <td><a href="javascript:view_submit('<tbl:write name="oneline" property="bundleID"/>','<tbl:write name="oneline" property="rfBillingCycleFlag"/>')"><tbl:write name="oneline" property="bundleID"/></a></td>
    <td><d:getcmnname typeName="SET_F_RFBILLINGCYCLEFLAG" match="oneline:rfBillingCycleFlag" /></td> 
     <td><tbl:write name="oneline" property="bundlePaymentName"/> </td>
     <td><d:getcmnname typeName="SET_M_CAMPAIGNTIMELENGTHUNITTYPE" match="oneline:timeUnitLengthType" /></td>  
     <td><tbl:write name="oneline" property="timeUnitLengthNumber"/> </td>
     <td><tbl:writedate name="oneline" property="dtCreate" includeTime="true"/> </td>
    <td align="center" ><a href="javascript:delete_object('<tbl:write name="oneline" property="rfBillingCycleFlag"/>')"  class="link12">删除</a></td>
</tbl:printcsstr>
 <%
         }
     }
     %>
</table>
 
   
                  
<BR>
  
</form>
