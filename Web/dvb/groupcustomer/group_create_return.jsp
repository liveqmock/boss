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
             sName="ҵ���ʻ�";
             sAdditionalInfo="ҵ���ʻ����Ϊ:"+strID+"��";
             break;  
         case 101:              
             sName="ԤԼ��Ϣ";
             sAdditionalInfo="ԤԼ�������Ϊ:"+strID+"��";
             break;  
         case 102:              
             sName="ԤԼ������Ϣ";
             sAdditionalInfo="�ͻ�֤��Ϊ:"+strID+"��";
             break; 
         case 103:              
             sName="�ŵ꿪����Ϣ";
             sAdditionalInfo="�ͻ�֤��Ϊ:"+strID+"��";
             break; 
         case 104:
             sName="������ԤԼ";
             sAdditionalInfo="ԤԼ�������Ϊ:"+strID+"��";  
             break;  
         case 1001:
         	sName="������Ϣ";
         	sAdditionalInfo="���޵����Ϊ"+strID+"�� ";
         	break;
         	 
          case 1002:
              sName="�����ɹ���Ϣ";
              break;
          case 1003:
              sName="���豸���";
              break;
          case 1005:
              sName="�ͻ����ֶһ�";    
               break;      
         case 1006:
              sName="���ܿ������Ԥ��Ȩ";    
               break;    
        case 1007:
              sName="���ܿ�Ԥ��Ȩ";    
               break;  
         case 1008:
              sName="������";    
               break;   
        case 1011:
              sName="�豸����";    
               break;  
         case 1012:
              sName="�豸�ؿ�";    
               break;                                             
          case 5059:
         	sName="���ֶһ�����";
          case 5060:
         	sName="���ֶһ�����";	 
         	break;    
           case 5079:
         	sName="���ֶһ��";	 
         	break;    
          case 5097:
         	sName="���ö�ά��Ϣ";	 
         	break;  
          case 5066:
         	sName="���ö�άֵ��Ϣ";	 
         	break; 
          case 5069:
         	sName="������Ʒ��";	 
         	break;  	 
          case 5074:
         	sName="�����ۼӹ���";	 
         	break;  
          case 5085:
         	sName="�г�����";	 
         	break;   
           case 5099:
         	sName="�г�����������������";	 
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
				<td class="title" align="center" valign="middle"><img src="img/list_tit.gif" width="19" height="19">&nbsp;&nbsp;��ʾ��Ϣ</td>
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
        <font color="red"><i>����<%=sName%>���ɹ���������Ϣ����:</i></font>
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
          <%=sName%>�����ɹ���<%=sAdditionalInfo%>
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
			<td><input name="Submit22" type="button" class="button" onClick="javascript:frmSubmit('<bk:backurl property='agent_csi_query.screen' />')" value="���ش�����ԤԼ����ѯ"></td>
			<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>

<%
          break;
         case 1001:
%>
			<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
			<td><input name="Submit22" type="button" class="button" onClick="javascript:frmSubmit('<bk:backurl property='cp_create.screen' />')" value="��������"></td>
			<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
<%
          break; 
           case 1002:
%>
   			<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
			<td><input name="Submit22" type="button" class="button" onClick="javascript:frmSubmit('<bk:backurl property='assignrep_query.do' />')" value="���ر����ɹ�"></td>
			<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
<%
          break; 
           case 1003:
%>
   			<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
			<td><input name="Submit22" type="button" class="button" onClick="javascript:frmSubmit('dev_into_depot.screen')" value="�������豸���"></td>
			      <tbl:hiddenparameters
	pass="txtDeviceClass/txtDeviceModel/txtDeviceProvider/txtBatchNo/txtGuaranteeLength/txtDepotID/txtComments/seriallength/checkModelField/checkModelDesc/txtTerminalDevices" />
			<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
<%
          break; 
           case 1005:
%>
   			<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
			<td><input name="Submit22" type="button" class="button" onClick="javascript:frmSubmit('<bk:backurl property='query_goods_by_activity.do' />')" value="���ػ��ֶһ�ѡ��"></td>
			<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
	<%
            break; 
           case 502:
        %>
   			<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
			<td><input name="Submit22" type="button" class="button" onClick="javascript:frmSubmit('<bk:backurl property='customer_device_swap_op.do' />')" value="���ظ����豸"></td>
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
			<td><input name="Submit22" type="button" class="button" onClick="javascript:frmSubmit('menu_booking.do?OpenFlag=<%=CommonKeys.ACTION_OF_BOOKING%>')" value="������ԤԼ"></td>
			<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
       <%
          break;
          case 102:
	  if (iAdditionalID!=null) {
    	    String strJCStatus = Postern.getJobCardStatusByID(Postern.getJobCardIDByCsiID(iAdditionalID.intValue()));
    	    if (strJCStatus!=null) {
    	        if (strJCStatus.equals("W"))  { //������
        %>           
   			<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
			<td><input name="Submit22" type="button" class="button" onClick="javascript:frmSubmit('<bk:backurl property='job_card_view_for_contact_of_install.do?txtType=I&txtReferSheetID=<%=iAdditionalID%>' />')" value="���밲װԤԼ"></td>
			<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
         <%
                 }
                 else if (strJCStatus.equals("B")) { //��ԤԼ
                 
         %>                            
   			<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
			<td><input name="Submit22" type="button" class="button" onClick="javascript:frmSubmit('<bk:backurl property='pi_view_for_register.do?txtType=I&txtReferSheetID=<%=iAdditionalID%>' />')" value="����¼�밲װ��Ϣ"></td>
			<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
         <%
		}
	    }
	  }
          %>
   			<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
			<td><input name="Submit22" type="button" class="button" onClick="javascript:frmSubmit('menu_open_for_booking.do?OpenFlag=<%=CommonKeys.ACTION_OF_BOOKINGACCOUNT%>')" value="������ԤԼ����"></td>
			<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
        <%           
             break; 
             case 103:
        %>
   			<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
			<td><input name="Submit22" type="button" class="button" onClick="javascript:frmSubmit('catv_terminal_query_for_branch.do?OpenFlag=<%=CommonKeys.ACTION_OF_SHOPACCOUNT%>')" value="�������ŵ꿪��"></td>
			<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
        <%
             break;  
             case 104:
        %>
   			<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
			<td><input name="Submit22" type="button" class="button" onClick="javascript:frmSubmit('agent_csi_booking.do?OpenFlag=<%=CommonKeys.ACTION_OF_BOOKINGAGENT%>')" value="����������ԤԼ"></td>
			<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
        <%
             break;                 
             case 1001:
        %>
        
   			<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
   			<td background="img/button_bg.gif"><a href="assignrep_query_result.do?txtCustomerProblemID=<%=strID%>" class="btn12">���뱨���ɹ�</a></td>
			<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
                         <td width="20" ></td>
   			<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
			<td><input name="Submit22" type="button" class="button" onClick="javascript:frmSubmit('<bk:backurl property='cp_create.do' />')" value="��������"></td>
			<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
        
  <%
            break; 
           case 1003:
%>
   			<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
			<td><input name="Submit22" type="button" class="button" onClick="javascript:frmSubmit('dev_into_depot.screen')" value="�������豸���"></td>
			<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
<%
          break;             
            case 1006:
        %>
   			<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
   			<td background="img/button_bg.gif"><a href="match_and_preauth.do" class="btn12">�������Ԥ��Ȩ��Ϣ¼��</a></td>
			 
			<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
     <%
            break; 
            case 1007:
        %>
   			<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
   			<td background="img/button_bg.gif"><a href="dev_query.do" class="btn12">�����豸��ѯ</a></td>
			 
			<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
                         			
        <%
            break; 
            case 1008:
        %>
   			<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
   			<td background="img/button_bg.gif"><a href="dev_query.do" class="btn12">�����豸��ѯ</a></td>
			 
			<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
                         			
       <%
            break; 
            case 1009:
        %>
   			<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
   			<td background="img/button_bg.gif"><a href="dev_query.do" class="btn12">�����豸��ѯ</a></td>
			 
			<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>  
			<%
          break; 
           case 1005:
%>
   			<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
			<td><input name="Submit22" type="button" class="button" onClick="javascript:frmSubmit('<bk:backurl property='customer_view.do' />')" value="���ص��ͻ���Ϣ"></td>
			<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
	  <%
            break; 
            case 1011:
        %>
   			<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
   			<td background="img/button_bg.gif"><a href="device_rep.do" class="btn12">�����豸������Ϣ</a></td>
			 
			<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
			<% 
			
           break; 
           case 1012:
        %>
   			<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
   			<td background="img/button_bg.gif"><a href="device_rep.do" class="btn12">�����豸�ؿ���Ϣ</a></td>
			 
			<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>       			                                       			                                       			
                         
   	<% 
			
            break; 
           case 5059:
        %>
   			<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
   			<tbl:hiddenparameters pass="txtActivityID" />
   			<td background="img/button_bg.gif"><a href="<bk:backurl property='query_points_goods.do' />" class="btn12">���ضһ���Ʒά��</a></td>
			 
			<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>     
	<% 
            break; 
           case 5060:
        %>
   			<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
   			<tbl:hiddenparameters pass="txtActivityID" />
   			<td background="img/button_bg.gif"><a href="<bk:backurl property='query_points_rule.do' />" class="btn12">���ضһ�����ά��</a></td>
			 
			<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>     
	<% 			
            break; 
           case 5066:
             String   settingID = request.getParameter("txtSettingID");
        %>
   			<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
   			<td background="img/button_bg.gif"><a href="query_config_item.do?txtSettingID=<%=settingID%>" class="btn12">��������ѡ���ѯ</a></td>
			 
			<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td> 
			
	 <% 
			
            break; 
            case 5069:
            
        %>
   			<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
   			 
   			<td background="img/button_bg.gif"><a href="menu_product_package_query.do" class="btn12">���ز�Ʒ����ѯ</a></td>
			 
			<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td> 		    				                                       			                                       			
        <% 
			
            break; 
            case 5079:
            
        %>
   			<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
   			 
   			<td background="img/button_bg.gif"><a href="activity_query_result.do" class="btn12">���ػ��ֻ��ѯ</a></td>
			 
			<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td> 
 <% 
			
            break; 
            case 5074:
            
        %>
   			<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
   			 
   			<td background="img/button_bg.gif"><a href="query_points_acumulate_rule.do" class="btn12">���ػ����ۼӹ����ѯ</a></td>
			 
			<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td> 			
	 <% 
			
            break; 
            case 5080:
           String  activityID = request.getParameter("vartxtActivityID");
        %>
   			<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
   			 
   			<td background="img/button_bg.gif"><a href="activity_query_result.do?txtActivityID=<%=activityID%>" class="btn12">����</a></td>
			 
			<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>  
	 <% 
			
            break; 
            case 5099:
           String  segmentID = request.getParameter("txtSegmentID");
        %>
   			<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
   			 
   			<td background="img/button_bg.gif"><a href="config_market_segment_detail.do?txtID=<%=segmentID%>" class="btn12">����</a></td>
			 
			<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>  		
			 <% 
			
            break; 
            case 5085:
            
        %>
   			<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
   			 
   			<td background="img/button_bg.gif"><a href="market_segment_query_result.do" class="btn12">����</a></td>
			 
			<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>  
 <% 
			
            break; 
            case 5097:
          
        %>
   			<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
   			 
   			<td background="img/button_bg.gif"><a href="config_query_result.do" class="btn12">����</a></td>
			 
			<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>           			                          
   			 
       <%
           break;
           case 500:
       %>
                        <td ><img src="img/button2_r.gif"" width="22" height="20"></td>
			<td background="img/button_bg.gif"  height="20">
			  <a href="javascript:frmSubmit('<bk:backurl property='service_interaction_query_result_for_callback.do/service_interaction_query_result.do' />')" class="btn12">��&nbsp;��</a>
			</td>
		        <td><img src="img/button2_l.gif" width="13" height="20"></td>
						 
       <%
           break;
           case 501:
       %>
                        <td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
			<td><input name="Submit22" type="button" class="button" onClick="javascript:frmSubmit('<bk:backurl property='group_bargain_query_result.do'/>')" value="��  ��"></td>
			<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
                      
      <%
          break;
          
          case 20001: //ҵ����(������ɾ��,����)
      %>
        <td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
	<td><input name="Submit22" type="button" class="button" onClick="javascript:frmSubmit('<bk:backurl property='config_serviceInfo_query.do' />')" value="ҵ���巵��"></td>
	<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
      <%
          break;
         case 20002: //ҵ�����������ϵ(������ɾ��)
      %>
        <td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
	<td><input name="Submit22" type="button" class="button" onClick="javascript:frmSubmit('<bk:backurl property='config_serviceRelation_query.do' />')" value="ҵ���ϵ���巵��"></td>
	<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
      <%
         break;
         case 20003: //ҵ����Դ����(������ɾ��,����)
      %>
        <td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>                          
	<td><input name="Submit22" type="button" class="button" onClick="javascript:frmSubmit('<bk:backurl property='config_resource_query.do' />')" value="ҵ����Դ���巵��"></td>
	<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
     <%
        break;
        case 20004: // ҵ����Դ��ϸ����(������ɾ��)
     %>
        <td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
	<td><input name="Submit22" type="button" class="button" onClick="javascript:frmSubmit('<bk:backurl property='config_resourceDetail_query.do' />')" value="ҵ����Դ���巵��"></td>
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
      
      
