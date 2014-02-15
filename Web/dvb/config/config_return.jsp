<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="bookmark" prefix="bk" %>

<%@ page import="com.dtv.oss.web.util.WebUtil" %>
<%@ page import="com.dtv.oss.web.util.WebOperationUtil" %>
<%@ page import="com.dtv.oss.util.Postern" %>
<%@ page import="com.dtv.oss.service.commandresponse.CommandResponse"%>
<%@ page import="com.dtv.oss.web.util.CommonKeys" %>

<BR>
<%
       String sFlag=request.getParameter("func_flag");
        
       int iFlag=0;
       
       if ((sFlag!=null)&&(sFlag.compareTo("")!=0))
       {
           try
           {
               iFlag=Integer.valueOf(sFlag).intValue();
           }
           catch (Exception ex)
           {
           }
       }
       
%>
<script language="JavaScript" type="text/JavaScript">
<!--
function frmSubmit(url){
		document.frmPost.action = url;
        document.frmPost.submit();
}
function frmOpenSubmit(url){
        
	document.frmPost.action = url+"&forwardFlag="+1+"&OpenFlag="+1;;
        document.frmPost.submit();
}

//-->
</script>
<TABLE border="0" align="center" cellspacing="0" cellpadding="0" width="100%">
<TR>
	<TD align="center">
		<table width="50%" border="0" align="center" cellpadding="0" cellspacing="10">
		 <tr>
                 <td colspan="2" height="8"></td>
                 </tr>
			  <tr>
				<td class="title" align="center" valign="middle"><img src="img/list_tit.gif" width="19" height="19">&nbsp;&nbsp;提示信息</td>
			  </tr>
		</table>
      <table width="50%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
        <tr>
          <td><img src="img/mao.gif" width="1" height="1"></td>
        </tr>
      </table>
      <br>
 <lgc:haserror>      
 <table width="50%" border="0" cellspacing="10" cellpadding="0">
        <tr align="center">
          <td width="182"><img src="img/icon_error.gif" width="182" height="182"></td>
          <td class="ok">
        <font color="red"><i>操作不成功，错误信息如下:</i></font>
<br><tbl:errmsg />
			</td>
        </tr>
      </table>
</lgc:haserror>
<lgc:hasnoterror>  
      <table width="50%" border="0" cellspacing="10" cellpadding="0">
        <tr align="center">
          <td width="182"><img src="img/icon_ok.gif" width="182" height="182"></td>
          <td class="ok">
        操作成功。
			</td>
        </tr>
      </table>
</lgc:hasnoterror>      
<br>
<br>
      <table width="50%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
        <tr>
          <td><img src="img/mao.gif" width="1" height="1"></td>
        </tr>
      </table>
<form name="frmPost" method="post">
      <table width="50%"  border="0" align="center" cellpadding="0" cellspacing="10">
        <tr align="center">
          <td height="37">
		  	  <table  border="0" cellspacing="0" cellpadding="0">
				  <tr>
<%
   //-------有错要显示的按钮-------
%> 
<lgc:haserror> 
<%
       switch (iFlag)
       {
         case 101: //产品添加
%> 
		<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
		<td><input name="Submit22" type="button" class="button" onClick="javascript:frmSubmit('<bk:backurl property='product_manage_create.do' />')" value="返回"></td>
		<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
		<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
		<td><input name="Submit22" type="button" class="button" onClick="javascript:frmSubmit('menu_product_manage_query.do')" value="返回到产品查询"></td>
		<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
<%
          break;
         case 102: //产品修改
%>
		<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
		<td><input name="Submit22" type="button" class="button" onClick="javascript:frmSubmit('<bk:backurl property='product_manage_view.do' />')" value="返回"></td>
		<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
		<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
		<td><input name="Submit22" type="button" class="button" onClick="javascript:frmSubmit('menu_product_manage_query.do')" value="返回到产品查询"></td>
		<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
          
<%
          break;
         case 103: //产品属性操作
%>
		<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
		<td><input name="Submit22" type="button" class="button" onClick="javascript:frmSubmit('<bk:backurl property='product_property_create.do' />')" value="返回"></td>
		<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
		
		<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
		<td><input name="Submit22" type="button" class="button" onClick="javascript:frmSubmit('product_property_query.do?txtProductID=<tbl:writeparam name="txtProductID"/>')" value="返回到产品属性查询"></td>
		<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
				
		<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
		<td><input name="Submit22" type="button" class="button" onClick="javascript:frmSubmit('product_manage_query.do')" value="返回到产品查询"></td>
		<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
<%
          break;
         case 198: //操作员修改
         
          String opID = request.getParameter("txtOperatorID");
%>
	<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
   	<td background="img/button_bg.gif"><a href="config_operator_detail.do?txtOperatorID=<%=opID%>" class="btn12">返 回</a></td>
	<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>  
	<%	
          break;
          case 1000: //产品包修改
         
          String packageID = request.getParameter("txtPackageID");
        %>
	<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
   	<td background="img/button_bg.gif"><a href="product_package_detail.do?txtPackageID=<%=packageID%>" class="btn12">返 回</a></td>
	<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>  		
		
	<%	
          break;
          case 1003: // 
         
          
        %>
        <td><img src="img/button2_r.gif" width="22" height="20"></td>  
        <td background="img/button_bg.gif">
	 <a href="account_type_editing.do?editing_type=new" class="btn12">返 回</a></td>
          <td><img src="img/button2_l.gif" width="13" height="20"></td>
          
          <%	
          break;
          case 1019: // 
         
          
        %>
         <td><img src="img/button2_r.gif" border="0" ></td>
          <td background="img/button_bg.gif"><a href="<bk:backurl property="fiber_node_detail_config.do" />" class="btn12">返&nbsp;回</a></td>
          <td><img src="img/button2_l.gif" border="0" ></td>
          <td width="20" ></td>
             <%	
          break; 
          case 1041: // 
         
          
        %>
         <td><img src="img/button2_r.gif" border="0" ></td>
           <td background="img/button_bg.gif"><a href="<bk:backurl property="fiber_transmitter_detail_config.do" />" class="btn12">返&nbsp;回</a></td>
          <td><img src="img/button2_l.gif" border="0" ></td>
          <td width="20" ></td>
           <%	
          break; 
          case 1042: // 
         
          
        %>
         <td><img src="img/button2_r.gif" border="0" ></td>
           <td background="img/button_bg.gif"><a href="<bk:backurl property="machine_room_detail_config.do" />" class="btn12">返&nbsp;回</a></td>
          <td><img src="img/button2_l.gif" border="0" ></td>
          <td width="20" ></td>
         
          <%	
          break;
          case 1046: // 
         
          
        %>
         <td><img src="img/button2_r.gif" border="0" ></td>
          <td background="img/button_bg.gif"><a href="<bk:backurl property="fiber_receiver_detail_config.do" />" class="btn12">返&nbsp;回</a></td>
          <td><img src="img/button2_l.gif" border="0" ></td>
          <td width="20" ></td>
             
	 	 
<%
          break;

	  case 104: //产品关系操作
%>
		<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
		<td><input name="Submit22" type="button" class="button" onClick="javascript:frmSubmit('<bk:backurl property='product_relation_create.do/product_relation_view.do' />')" value="返回"></td>
		<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
		
 		<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
		<td><input name="Submit22" type="button" class="button" onClick="javascript:frmSubmit('menu_product_relation_query.do?txtProductID=<tbl:writeparam name="txtProductID"/>')" value="返回到产品关系查询"></td>
		<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
				
		<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
		<td><input name="Submit22" type="button" class="button" onClick="javascript:frmSubmit('menu_product_manage_query.do')" value="返回到产品查询"></td>
		<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
<%
          break;
          
    case 1024: // 奖励金额-添加
		String txtCampaignID = request.getParameter("txtCampaignID");
%>	
		<td><img src="img/button2_r.gif" border="0" ></td>
        <td background="img/button_bg.gif"><a href="campaign_payment_award_query.screen?txtCampaignID=<%=txtCampaignID%>" class="btn12">返 回</a></td>
	 	<td><img src="img/button2_l.gif" border="0" ></td>
<%		
		break;		
		
	case 1005:
		String feeId = request.getParameter("txtFeeSplitPlanID");
	
%>
       <td><img src="img/button2_r.gif" border="0" ></td>
        <td background="img/button_bg.gif"><a href="fee_split_item.screen?txtFeeSplitPlanID=<%=feeId%>" class="btn12">返 回</a></td>
	 
	 <td><img src="img/button2_l.gif" border="0" ></td>
<%
          break; 	
		}
%>
</lgc:haserror>

<%
   //-------没错要显示的按钮-------
%>
<lgc:hasnoterror>
<%
 int iReferSheetID = 0;
       switch (iFlag)
       {
        
         case 101: 
       
%>
  	<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
	<td><input name="Submit22" type="button" class="button" onClick="javascript:frmSubmit('product_manage_query.do?txtFrom=1&txtTo=10')" value="返回到产品查询"></td>
	<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
 
<%

          break; 
          case 102:  
           
%>
  	<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
	<td><input name="Submit22" type="button" class="button" onClick="javascript:frmSubmit('product_manage_query.do?txtFrom=1&txtTo=10')" value="返回到产品查询"></td>
	<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>	
            
<%

         break;
           
	 case 103: //产品属性操作
	   
%>
  	<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
	<td><input name="Submit22" type="button" class="button" onClick="javascript:frmSubmit('product_property_query.do?txtProductID=<tbl:writeparam name="txtProductID"/>')" value="返回到产品属性查询"></td>
	<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
				
	<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
	<td><input name="Submit22" type="button" class="button" onClick="javascript:frmSubmit('menu_product_manage_query.do')" value="返回到产品查询"></td>
	<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>			
<%
	  break;
	 
	 case 104: //产品关系操作
%>
  	<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
	<td><input name="Submit22" type="button" class="button" onClick="javascript:frmSubmit('menu_product_relation_query.do?txtProductID=<tbl:writeparam name="txtProductID"/>')" value="返回到产品关系查询"></td>
	<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
				
	<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
	<td><input name="Submit22" type="button" class="button" onClick="javascript:frmSubmit('menu_product_manage_query.do')" value="返回到产品查询"></td>
	<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
	<%
	  break;
	 
	 case 105: //抵扣券类型
%>
  	<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
	<td><input name="Submit22" type="button" class="button" onClick="javascript:frmSubmit('menu_payment_type.do')" value="返回到抵扣券类型查询"></td>
	<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
	<%
	  break;
	 case 106: //删除产品操作
%>
	<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
	<td><input name="Submit22" type="button" class="button" onClick="javascript:frmSubmit('menu_product_manage_query.do')" value="返回到产品查询"></td>
	<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
	<%
	  break;
	 case 107: //组织角色
	 String roleId = request.getParameter("txtId");
	 
%>
	 <td><img src="img/button2_r.gif" width="22px" height="20px"></td>
	<td><input name="Submit22" type="button" class="button" onClick="javascript:frmSubmit('role_organization_modify.do?txtId=<%=roleId%>')" value="返回查询"></td>
	<td><img src="img/button2_l.gif" width="11" height="20"></td> 
	<%
	  break;
	 case 119:  
	 
%>
            <td><img src="img/button2_r.gif" width="22" height="20"></td>  
                        <td background="img/button_bg.gif">
	                <a href="<bk:backurl property="role_organization_query.do" />" class="btn12">返&nbsp;回</a></td>
                        <td><img src="img/button2_l.gif" width="13" height="20"></td>
	 
	<%
	  break;
	 case 108: //组织角色
	 
	 
%>
	 <td><img src="img/button2_r.gif" width="22px" height="20px"></td>
	<td><input name="Submit22" type="button" class="button" onClick="javascript:frmSubmit('role_organization_query.do?txtFrom=1&txtTo=10')" value="返回查询"></td>
	<td><img src="img/button2_l.gif" width="11" height="20"></td> 
<%


	 break;
	 
	 case 110: // 
%>
  	 <td><img src="img/button2_r.gif" width="20" height="20"></td>
	<td><input name="Submit22" type="button" class="button" onClick="javascript:frmSubmit('brcondition_query.do?txtFrom=1&txtTo=10')" value="返回到计费条件查询"></td>
	 <td><img src="img/button2_l.gif" width="11" height="20"></td>
	<%
	  break;
	 
	 case 111: //支付条件
	 String brId = request.getParameter("txtBrcntID");
%>
  	  <td><img src="img/button2_r.gif" width="20" height="20"></td>
	  <td><input name="Submit22" type="button" class="button" onClick="javascript:frmSubmit('brcondition_detail.do?txtBrcntID=<%=brId%>')" value="返 回"></td>
	    <td><img src="img/button2_l.gif" width="11" height="20"></td>
	 
				 
<%
	  break;
	 
	 case 115: //支付方式
%>
  	 <td><img src="img/button2_r.gif" width="20" height="20"></td>
	<td><input name="Submit22" type="button" class="button" onClick="javascript:frmSubmit('method_of_payment_query.do')" value="返回到付费方式列表"></td>
	<td><img src="img/button2_l.gif" width="11" height="20"></td>
<%
	  break;
	 
	 case 130: //支付规则
	 String id = request.getParameter("txtID");
%>
  	 <td><img src="img/button2_r.gif" width="20" height="20"></td>
	<td><input name="Submit22" type="button" class="button" onClick="javascript:frmSubmit('billing_rule_view.do?txtID=<%=id%>')" value="返 回"></td>
	<td><img src="img/button2_l.gif" width="11" height="20"></td>
	<%
	  break;
	 
	 case 131: //支付规则
	 
%>
  	 <td><img src="img/button2_r.gif" width="20" height="20"></td>
	<td><input name="Submit22" type="button" class="button" onClick="javascript:frmSubmit('billing_rule_query.do?txtFrom=1&txtTo=10')" value="返 回"></td>
	<td><img src="img/button2_l.gif" width="11" height="20"></td>
								
	<%
	  break;
	 
	 case 132: //系统配置
%>
  	 <td><img src="img/button2_r.gif" width="20" height="20"></td>
	<td><input name="Submit22" type="button" class="button" onClick="javascript:frmSubmit('boss_config.screen')" value="返回到配置页面"></td>
        <td><img src="img/button2_l.gif" width="11" height="20"></td>
	<%
	  break;
	 
	 case 133: //受理单类型原因
%>
  	  <td><img src="img/button2_r.gif" border="0" ></td>
	  <td><input name="Submit22" type="button" class="button" onClick="javascript:frmSubmit('csi_reason_query.do?txtFrom=0&txtTo=10')" value="返 回"></td>
	 <td><img src="img/button2_l.gif" border="0" ></td>
	 <%
	  break;
	 
	 case 134: //受理单类型原因
	  String referSeqNo = request.getParameter("txtReferSeqNo");
%>
  	  <td><img src="img/button2_r.gif" border="0" ></td>
	<td><input name="Submit22" type="button" class="button" onClick="javascript:frmSubmit('csi_reason_item.screen?txtReferSeqNo=<%=referSeqNo%>')" value="返 回"></td>
	 <td><img src="img/button2_l.gif" border="0" ></td>
	  <%
	  break;
	 
	 case 135: //设备用途
	   
%>
  	  <td><img src="img/button2_r.gif" border="0" ></td>
	<td><input name="Submit22" type="button" class="button" onClick="javascript:frmSubmit('csi_reason_device_query.do?txtFrom=1&txtTo=10')" value="返 回"></td>
	 <td><img src="img/button2_l.gif" border="0" ></td>
	   <%
	  break;
	 
	 case 136: //设备用途
	   
%>
  	  <td><img src="img/button2_r.gif" border="0" ></td>
	<td><input name="Submit22" type="button" class="button" onClick="javascript:frmSubmit('swap_device_status_query.do')" value="返 回"></td>
	 <td><img src="img/button2_l.gif" border="0" ></td>
<%
	  break;
	 
	 case 178: //系统配置
	 
%>
  	 <td><img src="img/button2_r.gif" border="0" ></td>
	<td><input name="Submit22" type="button" class="button" onClick="javascript:frmSubmit('opgroup_query.do?txtFrom=1&txtTo=10')" value="返回到查询页面"></td>
	 <td><img src="img/button2_l.gif" border="0" ></td>	
	 <%
	  break;
	 
	 case 179: //系统配置
	 String txtOpGID = request.getParameter("txtOpGroupID");
%>
  	 <td><img src="img/button2_r.gif" border="0" ></td>
	<td><input name="Submit22" type="button" class="button" onClick="javascript:frmSubmit('op_group_detail.do?txtOpGroupID=<%=txtOpGID%>')" value="返 回"></td>
	 <td><img src="img/button2_l.gif" border="0" ></td>																
<%
	  break;
	 
	 case 197: //系统配置
	 String txtOrgID = request.getParameter("txtOrgID");
%>
         <td><img src="img/button2_r.gif" border="0" ></td>
   	<td background="img/button_bg.gif"><a href="operator_query.do?txtOrgID=<%=txtOrgID%>" class="btn12">返 回</a></td>
	 <td><img src="img/button2_l.gif" border="0" ></td>  
	<%
	  break;
	 
	 case 198: //系统配置
	 String txtOpID = request.getParameter("txtOperatorID");
	 
%>
         <td><img src="img/button2_r.gif" border="0" ></td>
   	<td background="img/button_bg.gif"><a href="config_operator_detail.do?txtOperatorID=<%=txtOpID%>" class="btn12">返 回</a></td>
	 <td><img src="img/button2_l.gif" border="0" ></td>  
	
	<%
	  break;
	 
	 case 200: //市场分区
	 
%>
        <td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
	<td><input name="Submit22" type="button" class="button" onClick="javascript:frmSubmit('menu_market_segment.do')" value="返回到查询页面"></td>
	<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>	
	
	<%
	  break;
	 
	 case 201: //市场分区
	 
%>
        <td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
	<td><input name="Submit22" type="button" class="button" onClick="javascript:frmSubmit('menu_campaign_query.do')" value="返回到查询页面"></td>
	<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>	
        	 
	<%
	  break;
	 
	 case 202: //市场分区
	 
%>

    <td><img src="img/button2_r.gif" width="22" height="20"></td>
			<td><input name="Submit" type="button" class="button"
					value="返&nbsp;回" onclick="javascript:frmSubmit('billing_rule_query.do?txtFrom=1&txtTo=10')"></td>
            <td><img src="img/button2_l.gif" width="11" height="20"></td>
            
      	 
	<%
	  break;
	 
	 case 203: //taocan
	String campaignId=request.getParameter("txtCampaignID");
%>

         <td><img src="img/button2_r.gif" border="0" ></td>
   	<td background="img/button_bg.gif"><a href="taocan_detail.do?txtCampaignID=<%=campaignId%>" class="btn12">返  回</a></td>
	  <td><img src="img/button2_l.gif" border="0" ></td>
     
         
	<%
	  break;
	 
	 case 245:  
	 
%>
        <td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
	<td><input name="Submit22" type="button" class="button" onClick="javascript:frmSubmit('menu_ldap_log_query.do')" value="返回到查询页面"></td>
	<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>	
        	  											
        	  													  		
<%
	  break;
	 
	 case 225:  
	 
%>
        <td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
	<td><input name="Submit22" type="button" class="button" onClick="javascript:frmSubmit('ldap_host_query.do')" value="返回到查询页面"></td>
	<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
	<%
	  break;
	 
	 case 226:  
	 
%>
        <td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
	<td><input name="Submit22" type="button" class="button" onClick="javascript:frmSubmit('ldap_product_query.do')" value="返回到查询页面"></td>
	<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>		
   <%
	  break;
	 
	 case 228:  
	 
%>
        <td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
	<td><input name="Submit22" type="button" class="button" onClick="javascript:frmSubmit('ldap_eventcmdmap_query.do')" value="返回到查询页面"></td>
	<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
		 	
   <%
	  break;
	 
	 case 230:  
	 
%>
        <td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
	<td><input name="Submit22" type="button" class="button" onClick="javascript:frmSubmit('ldap_prod_to_smsprod.do')" value="返回到查询页面"></td>
	<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
	 <%
	  break;
	 
	 case 232:  
	 
%>
        <td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
	<td><input name="Submit22" type="button" class="button" onClick="javascript:frmSubmit('ldap_attr_query.do')" value="返回到查询页面"></td>
	<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
	 <%
	  break;
	 
	 case 255:  
	 
%>
        <td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
	<td><input name="Submit22" type="button" class="button" onClick="javascript:frmSubmit('system_setting_query.do')" value="返回到查询页面"></td>
	<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
	 <%
	  break;
	 
	 case 800:  
	 
%>
        <td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
	<td><input name="Submit22" type="button" class="button" onClick="javascript:frmSubmit('bill_board_query_config.do')" value="返回到查询页面"></td>
	<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>	  	  	     	  																		
 <%
	  break;
	 
	 case 1002:  
	 
%>
        <td width="22" height="20"><img src="img/button2_r.gif" width="22" height="20"></td>
	<td><input name="Submit22" type="button" class="button" onClick="javascript:frmSubmit('customer_support_enter.do')" value="返回到起始页面"></td>
	<td width="11" height="20"><img src="img/button2_l.gif" width="11" height="20"></td>	
	 <%
	  break;
	 
	  case 1011:  
	 
%>
        <td><img src="img/button2_r.gif" border="0" ></td>
        <td background="img/button_bg.gif"><a href="charge_cycle_type_brief.do" class="btn12">返 回</a></td>
	 
	  <td><img src="img/button2_l.gif" border="0" ></td>
	   <%
	  break;
	 
	  case 1012:  
	 
%>
        <td><img src="img/button2_r.gif" border="0" ></td>
        <td background="img/button_bg.gif"><a href="account_cycle_type_brief.do" class="btn12">返 回</a></td>
	 
	  <td><img src="img/button2_l.gif" border="0" ></td>
	     <%
	  break;
	 
	  case 1013:  
	 
%>
        <td><img src="img/button2_r.gif" border="0" ></td>
        <td background="img/button_bg.gif"><a href="customer_account_cycle_type_brief.do" class="btn12">返 回</a></td>
	 
	  <td><img src="img/button2_l.gif" border="0" ></td>
	 <%
	  break;
	 
	  case 1015:  
	  String campaignID = request.getParameter("txtCampaignID");
%>
        <td><img src="img/button2_r.gif" border="0" ></td>
        <td background="img/button_bg.gif"><a href="product_cond_campaign.screen?txtCampaignID=<%=campaignID%>" class="btn12">返 回</a></td>
	 
	  <td><img src="img/button2_l.gif" border="0" ></td>
	  
	 <%
	  break;
	 
	  case 1016:  
	  String campaignID1 = request.getParameter("txtCampaignID");
%>
        <td><img src="img/button2_r.gif" border="0" ></td>
        <td background="img/button_bg.gif"><a href="package_cond_campaign.screen?txtCampaignID=<%=campaignID1%>" class="btn12">返 回</a></td>
	 
	  <td><img src="img/button2_l.gif" border="0" ></td>
	 <%
	  break;
	 
	  case 1017:  
	  String campaignID2 = request.getParameter("txtCampaignID");
%>
         <td><img src="img/button2_r.gif" border="0" ></td>
         <td background="img/button_bg.gif"><a href="campaign_cond_campaign.screen?txtCampaignID=<%=campaignID2%>" class="btn12">返 回</a></td>
	 
	 <td><img src="img/button2_l.gif" border="0" ></td>
	  <%
	  break;
	 
	  case 1018:  
	  String campaignID3 = request.getParameter("txtCampaignID");
%>
         <td><img src="img/button2_r.gif" border="0" ></td>
         <td background="img/button_bg.gif"><a href="bundle_payment_query.screen?txtCampaignID=<%=campaignID3%>" class="btn12">返 回</a></td>
	 
	 <td><img src="img/button2_l.gif" border="0" ></td>
	  <%	
          break;
          case 1019: // 
         
          
        %>
         <td><img src="img/button2_r.gif" border="0" ></td>
          <td background="img/button_bg.gif"><a href="<bk:backurl property="fiber_node_detail_config.do" />" class="btn12">返&nbsp;回</a></td>
          <td><img src="img/button2_l.gif" border="0" ></td>
          <td width="20" ></td>
           <%	
          break;
          case 1038: // 
         
          
        %>
         <td><img src="img/button2_r.gif" border="0" ></td>
          <td background="img/button_bg.gif"><a href="fibernode_query.do" class="btn12">返&nbsp;回</a></td>
          <td><img src="img/button2_l.gif" border="0" ></td>
          <td width="20" ></td>
          
            <%	
          break;
          case 1039: // 
         
          
        %>
         <td><img src="img/button2_r.gif" border="0" ></td>
          <td background="img/button_bg.gif"><a href="fiberreceiver_query.do" class="btn12">返&nbsp;回</a></td>
          <td><img src="img/button2_l.gif" border="0" ></td>
          <td width="20" ></td>
             <%	
          break; 
          case 1040: // 
         
          
        %>
         <td><img src="img/button2_r.gif" border="0" ></td>
          <td background="img/button_bg.gif"><a href="fibertransmitter_query.do" class="btn12">返&nbsp;回</a></td>
          <td><img src="img/button2_l.gif" border="0" ></td>
          <td width="20" ></td>
            <%	
          break; 
          case 1041: // 
         
          
        %>
         <td><img src="img/button2_r.gif" border="0" ></td>
           <td background="img/button_bg.gif"><a href="<bk:backurl property="fiber_transmitter_detail_config.do" />" class="btn12">返&nbsp;回</a></td>
          <td><img src="img/button2_l.gif" border="0" ></td>
          <td width="20" ></td>
           <%	
          break; 
          case 1042: // 
         
          
        %>
         <td><img src="img/button2_r.gif" border="0" ></td>
           <td background="img/button_bg.gif"><a href="<bk:backurl property="machine_room_detail_config.do" />" class="btn12">返&nbsp;回</a></td>
          <td><img src="img/button2_l.gif" border="0" ></td>
          <td width="20" ></td>
          <%	
          break; 
          case 1043: // 
         
          
        %>
         <td><img src="img/button2_r.gif" border="0" ></td>
           <td background="img/button_bg.gif"><a href="machineroom_query.do" class="btn12">返&nbsp;回</a></td>
          <td><img src="img/button2_l.gif" border="0" ></td>
          <td width="20" ></td>
        <%	
          break;
          case 1046: // 
         
          
        %>
         <td><img src="img/button2_r.gif" border="0" ></td>
          <td background="img/button_bg.gif"><a href="<bk:backurl property="fiber_receiver_detail_config.do" />" class="btn12">返&nbsp;回</a></td>
          <td><img src="img/button2_l.gif" border="0" ></td>
          <td width="20" ></td>
       
       
	  <%
	  break;
	  
	  case 1020:  
	  String campaignID4 = request.getParameter("txtCampaignID");
%>
         <td><img src="img/button2_r.gif" border="0" ></td>
         <td background="img/button_bg.gif"><a href="bundle_payment_query.screen?txtCampaignID=<%=campaignID4%>" class="btn12">返 回</a></td>
	 
	 <td><img src="img/button2_l.gif" border="0" ></td>
	    <%
	  break;
	  
	  case 1000:  
	  String packID = request.getParameter("txtPackageID");
%>
         <td><img src="img/button2_r.gif" border="0" ></td>
         <td background="img/button_bg.gif"><a href="product_package_detail.do?txtPackageID=<%=packID%>" class="btn12">返 回</a></td>
	 
	 <td><img src="img/button2_l.gif" border="0" ></td>
	   <%
	  break;
	  
	  case 1021:  
	  String campaignID5 = request.getParameter("txtCampaignID");
%>
         <td><img src="img/button2_r.gif" border="0" ></td>
         <td background="img/button_bg.gif"><a href="bundle_prepayment_query.screen?txtCampaignID=<%=campaignID5%>" class="btn12">返 回</a></td>
	 
	 <td><img src="img/button2_l.gif" border="0" ></td>
	   <%
	  break;
	  
	  case 1022:  
	  String campaignID6 = request.getParameter("txtCampaignID");
%>
         <td><img src="img/button2_r.gif" border="0" ></td>
         <td background="img/button_bg.gif"><a href="campaign_agmt_campaign_query.screen?txtCampaignID=<%=campaignID6%>" class="btn12">返 回</a></td>
	 
	 <td><img src="img/button2_l.gif" border="0" ></td>
	  <%
	  break;
	  
	  case 1023:  
	  
%>
         <td><img src="img/button2_r.gif" border="0" ></td>
         <td background="img/button_bg.gif"><a href="manual_transfer_query.do?txtFrom=1&txtTo=10" class="btn12">返 回</a></td>
	 
	 <td><img src="img/button2_l.gif" border="0" ></td>
	<%
	  break;
	 
	 case 1003:  
	 
%>
        <td><img src="img/button2_r.gif" border="0" ></td>
	<td><input name="Submit22" type="button" class="button" onClick="javascript:frmSubmit('account_type_brief_rusult.do?txtFrom=1&txtTo=10')" value="返回到查询页面"></td>
	 <td><img src="img/button2_l.gif" border="0" ></td>
	 <%
	  break;
	 
	 case 1008:  
	 String acctTypeId= request.getParameter("txtAcctItemTypeID");
	 
%>
       <td><img src="img/button2_r.gif" border="0" ></td>
        <td background="img/button_bg.gif"><a href="account_type_editing.do?editing_type=view_update&txtAcctItemTypeID=<%=acctTypeId%>" class="btn12">返回明细页面</a></td>
	 
	 <td><img src="img/button2_l.gif" border="0" ></td>
        
	<%
	  break;
	 
	 case 1004:  
	 
       %>
       <td><img src="img/button2_r.gif" border="0" ></td>
	<td><input name="Submit22" type="button" class="button" onClick="javascript:frmSubmit('fee_split_plan_query.screen')" value="返回到查询页面"></td>
	 <td><img src="img/button2_l.gif" border="0" ></td>
	 <%
	  break;
	 
	 case 1044:  
	 
       %>
       <td><img src="img/button2_r.gif" border="0" ></td>
	<td><input name="Submit22" type="button" class="button" onClick="javascript:frmSubmit('financial_general_setting_editing.do')" value="返&nbsp;回"></td>
	 <td><img src="img/button2_l.gif" border="0" ></td>
	 <%
	  break;
	 
	 case 1005:  
	 String feeId = request.getParameter("txtFeeSplitPlanID");
	 
%>
       <td><img src="img/button2_r.gif" border="0" ></td>
        <td background="img/button_bg.gif"><a href="fee_split_item.screen?txtFeeSplitPlanID=<%=feeId%>" class="btn12">返 回</a></td>
	 
	 <td><img src="img/button2_l.gif" border="0" ></td>
<%
          break; 
          
     case 1024: // 奖励金额-添加
		String txtCampaignID = request.getParameter("txtCampaignID");
%>	
		<td><img src="img/button2_r.gif" border="0" ></td>
        <td background="img/button_bg.gif"><a href="campaign_payment_award_query.screen?txtCampaignID=<%=txtCampaignID%>" class="btn12">返 回</a></td>
	 	<td><img src="img/button2_l.gif" border="0" ></td>
<%		
		break;		
		
		}
%>

</lgc:hasnoterror>

<%
   //-------有错没错都要显示的按钮-------
%>
<%
       //通常是返回
       String strBackUrl = "";
       String strBtnName = "返回查询结果";
       switch (iFlag)
       {
        
         case 110: //cancel booking
           strBackUrl = "service_interaction_query_result_for_booking.do";
           break;
         case 120: //cancel customer problem
           strBackUrl = "cp_query_detail.do";
           strBtnName = "返回相关报修单";
           break;
          case 201: //customer move 客户迁移
           strBackUrl = "customer_view.do?txtCustomerID="+request.getParameter("txtCustomerID");
           strBtnName = "返回客户资料";
           break;
      
       }
       
       if (!strBackUrl.equals(""))
       {
       System.out.println("aaaa " + strBackUrl);
%>
<bk:canback url="<%=strBackUrl%>" >
  				<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
				<td><input name="Submit22" type="button" class="button" onClick="javascript:frmSubmit('<bk:backurl property='<%=strBackUrl%>' />')" value="<%=strBtnName%>"></td>
				<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
</bk:canback>
<%
        }
%>
        </tr>
      </table> 
        </td>
          <td width="20%">&nbsp;</td>
        </tr>
      </table>