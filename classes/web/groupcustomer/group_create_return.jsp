<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="bookmark" prefix="bk" %>

<%@ page import="com.dtv.oss.service.commandresponse.CommandResponseImp" %>
<%@ page import="com.dtv.oss.web.util.WebUtil" %>
<%@ page import="com.dtv.oss.web.util.CommonKeys" %>
<%@ page import="com.dtv.oss.util.Postern" %>
                 
<BR>
<%
       
       int iFlag=WebUtil.StringToInt(request.getParameter("func_flag"));
       System.out.println("iFlag===="+iFlag);
       String sName="";
       String sAdditionalInfo="";
       Integer iID=null;
       Integer iAdditionalID=null;
       String strID="";
       Map map = null;
       
       CommandResponseImp CmdRep = (CommandResponseImp)request.getAttribute("ResponseFromEJBEvent");    
       if (CmdRep!=null)
       {
           try
           {
               switch (iFlag)
       		{
         	   case 201:
         	     map = (Map)CmdRep.getPayload();
         	     iID = (Integer)map.get("AccountID");
         	     iAdditionalID = (Integer)map.get("CustServiceInteractionID");
         	     
         	     break;
         	   default:   
         	     System.out.println("CmdRep.getPayload()======="+CmdRep.getPayload()); 
                     iID = (Integer)CmdRep.getPayload();
                     break;
                }     
           }
           catch (Exception ex)
           {}
       } 
    	     
       if (iID!=null) strID=String.valueOf(iID);      
       
       switch (iFlag)
       {
         case 1:              
             sName="业务帐户";
             sAdditionalInfo="业务帐户编号为:"+strID+"。";
             break;  
         case 101:              
             sName="预约信息";
             sAdditionalInfo="预约受理单编号为:"+strID+"。";
             break;  
         case 102:              
             sName="预约开户信息";
             sAdditionalInfo="客户证号为:"+strID+"。";
             break; 
         case 103:              
             sName="门店开户信息";
             sAdditionalInfo="客户证号为:"+strID+"。";
             break; 
         case 104:
             sName="代理商预约";
             sAdditionalInfo="预约受理单编号为:"+strID+"。";  
             break;  
         case 1001:
         	sName="报修信息";
         	sAdditionalInfo="报修单编号为"+strID+"。 ";
         	break;
         	 
          case 1002:
              sName="报修派工信息";
              break;
          case 1003:
              sName="新设备入库";
              break;
          case 1005:
              sName="客户积分兑换";    
               break;      
         case 1006:
              sName="智能卡配对与预售权";    
               break;    
        case 1007:
              sName="智能卡预授权";    
               break;  
         case 1008:
              sName="解除配对";    
               break;   
        case 1011:
              sName="设备送修";    
               break;  
         case 1012:
              sName="设备回库";    
               break;                                             
          case 5059:
         	sName="积分兑换货物";
          case 5060:
         	sName="积分兑换规则";	 
         	break;    
           case 5079:
         	sName="积分兑换活动";	 
         	break;    
          case 5097:
         	sName="配置二维信息";	 
         	break;  
          case 5066:
         	sName="配置二维值信息";	 
         	break; 
          case 5069:
         	sName="创建产品包";	 
         	break;  	 
          case 5074:
         	sName="积分累加规则";	 
         	break;  
          case 5085:
         	sName="市场分区";	 
         	break;   
           case 5099:
         	sName="市场分区所在行政区域";	 
         	break;   				 	 	  		    		 
       }
       
%>
<script language="JavaScript" type="text/JavaScript">
<!--
function frmSubmit(url){
	document.frmPost.action = url;
        document.frmPost.submit();
}
//-->
</script>

<TABLE border="0" align="center" cellspacing="0" cellpadding="0" width="100%">
<TR>
	<TD align="center">
		<table width="50%" border="0" align="center" cellpadding="0" cellspacing="10">
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
        <font color="red"><i>创建<%=sName%>不成功，错误信息如下:</i></font>
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
          <%=sName%>操作成功。<%=sAdditionalInfo%>
			</td>
        </tr>
      </table>
</lgc:hasnoterror>      
<br>
      <table width="50%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
        <tr>
          <td><img src="img/mao.gif" width="1" height="1"></td>
        </tr>
      </table>
       <BR>
       <table align="center" border="0" cellspacing="0" cellpadding="0">
         <tr> 
<form name="frmPost" method="post">
<lgc:haserror>          
<%
       switch (iFlag)
       {
          case 104:
%>
			<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
			<td><input name="Submit22" type="button" class="button" onClick="javascript:frmSubmit('<bk:backurl property='agent_csi_query.screen' />')" value="返回代理商预约单查询"></td>
			<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>

<%
          break;
         case 1001:
%>
			<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
			<td><input name="Submit22" type="button" class="button" onClick="javascript:frmSubmit('<bk:backurl property='cp_create.screen' />')" value="新增报修"></td>
			<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
<%
          break; 
           case 1002:
%>
   			<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
			<td><input name="Submit22" type="button" class="button" onClick="javascript:frmSubmit('<bk:backurl property='assignrep_query.do' />')" value="返回报修派工"></td>
			<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
<%
          break; 
           case 1003:
%>
   			<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
			<td><input name="Submit22" type="button" class="button" onClick="javascript:frmSubmit('dev_into_depot.screen')" value="返回新设备入库"></td>
			      <tbl:hiddenparameters
	pass="txtDeviceClass/txtDeviceModel/txtDeviceProvider/txtBatchNo/txtGuaranteeLength/txtDepotID/txtComments/seriallength/checkModelField/checkModelDesc/txtTerminalDevices" />
			<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
<%
          break; 
           case 1005:
%>
   			<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
			<td><input name="Submit22" type="button" class="button" onClick="javascript:frmSubmit('<bk:backurl property='query_goods_by_activity.do' />')" value="返回积分兑换选择活动"></td>
			<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
	<%
            break; 
           case 502:
        %>
   			<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
			<td><input name="Submit22" type="button" class="button" onClick="javascript:frmSubmit('<bk:backurl property='customer_device_swap_op.do' />')" value="返回更换设备"></td>
			<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>		
 
					
 
			
<%
          break; 
          }
%>
      </lgc:haserror>
      <lgc:hasnoterror> 
      <%
        switch (iFlag) {
           case 101:
      %>
   			 
   			<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
			<td><input name="Submit22" type="button" class="button" onClick="javascript:frmSubmit('menu_booking.do?OpenFlag=<%=CommonKeys.ACTION_OF_BOOKING%>')" value="创建新预约"></td>
			<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
       <%
          break;
          case 102:
	  if (iAdditionalID!=null) {
    	    String strJCStatus = Postern.getJobCardStatusByID(Postern.getJobCardIDByCsiID(iAdditionalID.intValue()));
    	    if (strJCStatus!=null) {
    	        if (strJCStatus.equals("W"))  { //待处理
        %>           
   			<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
			<td><input name="Submit22" type="button" class="button" onClick="javascript:frmSubmit('<bk:backurl property='job_card_view_for_contact_of_install.do?txtType=I&txtReferSheetID=<%=iAdditionalID%>' />')" value="进入安装预约"></td>
			<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
         <%
                 }
                 else if (strJCStatus.equals("B")) { //已预约
                 
         %>                            
   			<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
			<td><input name="Submit22" type="button" class="button" onClick="javascript:frmSubmit('<bk:backurl property='pi_view_for_register.do?txtType=I&txtReferSheetID=<%=iAdditionalID%>' />')" value="进入录入安装信息"></td>
			<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
         <%
		}
	    }
	  }
          %>
   			<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
			<td><input name="Submit22" type="button" class="button" onClick="javascript:frmSubmit('menu_open_for_booking.do?OpenFlag=<%=CommonKeys.ACTION_OF_BOOKINGACCOUNT%>')" value="创建新预约开户"></td>
			<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
        <%           
             break; 
             case 103:
        %>
   			<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
			<td><input name="Submit22" type="button" class="button" onClick="javascript:frmSubmit('catv_terminal_query_for_branch.do?OpenFlag=<%=CommonKeys.ACTION_OF_SHOPACCOUNT%>')" value="创建新门店开户"></td>
			<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
        <%
             break;  
             case 104:
        %>
   			<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
			<td><input name="Submit22" type="button" class="button" onClick="javascript:frmSubmit('agent_csi_booking.do?OpenFlag=<%=CommonKeys.ACTION_OF_BOOKINGAGENT%>')" value="创建代理商预约"></td>
			<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
        <%
             break;                 
             case 1001:
        %>
        
   			<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
   			<td background="img/button_bg.gif"><a href="assignrep_query_result.do?txtCustomerProblemID=<%=strID%>" class="btn12">进入报修派工</a></td>
			<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
                         <td width="20" ></td>
   			<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
			<td><input name="Submit22" type="button" class="button" onClick="javascript:frmSubmit('<bk:backurl property='cp_create.do' />')" value="新增报修"></td>
			<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
        
  <%
            break; 
           case 1003:
%>
   			<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
			<td><input name="Submit22" type="button" class="button" onClick="javascript:frmSubmit('dev_into_depot.screen')" value="返回新设备入库"></td>
			<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
<%
          break;             
            case 1006:
        %>
   			<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
   			<td background="img/button_bg.gif"><a href="match_and_preauth.do" class="btn12">返回配对预售权信息录入</a></td>
			 
			<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
     <%
            break; 
            case 1007:
        %>
   			<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
   			<td background="img/button_bg.gif"><a href="dev_query.do" class="btn12">返回设备查询</a></td>
			 
			<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
                         			
        <%
            break; 
            case 1008:
        %>
   			<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
   			<td background="img/button_bg.gif"><a href="dev_query.do" class="btn12">返回设备查询</a></td>
			 
			<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
                         			
       <%
            break; 
            case 1009:
        %>
   			<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
   			<td background="img/button_bg.gif"><a href="dev_query.do" class="btn12">返回设备查询</a></td>
			 
			<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>  
			<%
          break; 
           case 1005:
%>
   			<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
			<td><input name="Submit22" type="button" class="button" onClick="javascript:frmSubmit('<bk:backurl property='customer_view.do' />')" value="返回到客户信息"></td>
			<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
	  <%
            break; 
            case 1011:
        %>
   			<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
   			<td background="img/button_bg.gif"><a href="device_rep.do" class="btn12">返回设备送修信息</a></td>
			 
			<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
			<% 
			
           break; 
           case 1012:
        %>
   			<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
   			<td background="img/button_bg.gif"><a href="device_rep.do" class="btn12">返回设备回库信息</a></td>
			 
			<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>       			                                       			                                       			
                         
   	<% 
			
            break; 
           case 5059:
        %>
   			<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
   			<tbl:hiddenparameters pass="txtActivityID" />
   			<td background="img/button_bg.gif"><a href="<bk:backurl property='query_points_goods.do' />" class="btn12">返回兑换物品维护</a></td>
			 
			<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>     
	<% 
            break; 
           case 5060:
        %>
   			<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
   			<tbl:hiddenparameters pass="txtActivityID" />
   			<td background="img/button_bg.gif"><a href="<bk:backurl property='query_points_rule.do' />" class="btn12">返回兑换规则维护</a></td>
			 
			<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>     
	<% 			
            break; 
           case 5066:
             String   settingID = request.getParameter("txtSettingID");
        %>
   			<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
   			<td background="img/button_bg.gif"><a href="query_config_item.do?txtSettingID=<%=settingID%>" class="btn12">返回配置选项查询</a></td>
			 
			<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td> 
			
	 <% 
			
            break; 
            case 5069:
            
        %>
   			<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
   			 
   			<td background="img/button_bg.gif"><a href="menu_product_package_query.do" class="btn12">返回产品包查询</a></td>
			 
			<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td> 		    				                                       			                                       			
        <% 
			
            break; 
            case 5079:
            
        %>
   			<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
   			 
   			<td background="img/button_bg.gif"><a href="activity_query_result.do" class="btn12">返回积分活动查询</a></td>
			 
			<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td> 
 <% 
			
            break; 
            case 5074:
            
        %>
   			<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
   			 
   			<td background="img/button_bg.gif"><a href="query_points_acumulate_rule.do" class="btn12">返回积分累加规则查询</a></td>
			 
			<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td> 			
	 <% 
			
            break; 
            case 5080:
           String  activityID = request.getParameter("vartxtActivityID");
        %>
   			<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
   			 
   			<td background="img/button_bg.gif"><a href="activity_query_result.do?txtActivityID=<%=activityID%>" class="btn12">返回</a></td>
			 
			<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>  
	 <% 
			
            break; 
            case 5099:
           String  segmentID = request.getParameter("txtSegmentID");
        %>
   			<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
   			 
   			<td background="img/button_bg.gif"><a href="config_market_segment_detail.do?txtID=<%=segmentID%>" class="btn12">返回</a></td>
			 
			<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>  		
			 <% 
			
            break; 
            case 5085:
            
        %>
   			<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
   			 
   			<td background="img/button_bg.gif"><a href="market_segment_query_result.do" class="btn12">返回</a></td>
			 
			<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>  
 <% 
			
            break; 
            case 5097:
          
        %>
   			<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
   			 
   			<td background="img/button_bg.gif"><a href="config_query_result.do" class="btn12">返回</a></td>
			 
			<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>           			                          
   			 
       <%
           break;
           case 500:
       %>
                        <td ><img src="img/button2_r.gif"" width="22" height="20"></td>
			<td background="img/button_bg.gif"  height="20">
			  <a href="javascript:frmSubmit('<bk:backurl property='service_interaction_query_result_for_callback.do/service_interaction_query_result.do' />')" class="btn12">返&nbsp;回</a>
			</td>
		        <td><img src="img/button2_l.gif" width="13" height="20"></td>
						 
       <%
           break;
           case 501:
       %>
                        <td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
			<td><input name="Submit22" type="button" class="button" onClick="javascript:frmSubmit('<bk:backurl property='group_bargain_query_result.do'/>')" value="返  回"></td>
			<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
                      
      <%
          break;
          
          case 20001: //业务定义(创建，删除,更新)
      %>
        <td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
	<td><input name="Submit22" type="button" class="button" onClick="javascript:frmSubmit('<bk:backurl property='config_serviceInfo_query.do' />')" value="业务定义返回"></td>
	<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
      <%
          break;
         case 20002: //业务的自依赖关系(创建，删除)
      %>
        <td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
	<td><input name="Submit22" type="button" class="button" onClick="javascript:frmSubmit('<bk:backurl property='config_serviceRelation_query.do' />')" value="业务关系定义返回"></td>
	<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
      <%
         break;
         case 20003: //业务资源定义(创建，删除,更新)
      %>
        <td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>                          
	<td><input name="Submit22" type="button" class="button" onClick="javascript:frmSubmit('<bk:backurl property='config_resource_query.do' />')" value="业务资源定义返回"></td>
	<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
     <%
        break;
        case 20004: // 业务资源明细定义(创建，删除)
     %>
        <td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
	<td><input name="Submit22" type="button" class="button" onClick="javascript:frmSubmit('<bk:backurl property='config_resourceDetail_query.do' />')" value="业务资源定义返回"></td>
	<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
    <%
        break;    
        }
    %>
   
         </lgc:hasnoterror>
        </tr>
      </table>

   </form>
<Script language=JavaScript>
    function install_booking(strId){
      document.frmPost.action ="";
      document.frmPost.submit();
    }

</Script>
 </td>
         </tr>
      </table>
      
      
