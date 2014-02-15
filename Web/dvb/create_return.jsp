<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="bookmark" prefix="bk" %>
<%@ taglib uri="osstags" prefix="d" %>
<%@ taglib uri="privilege" prefix="pri" %>

<%@ page import="com.dtv.oss.service.commandresponse.CommandResponseImp" %>
<%@ page import="com.dtv.oss.dto.CustServiceInteractionDTO" %>
<%@ page import="com.dtv.oss.web.util.WebUtil" %>
<%@ page import="com.dtv.oss.web.util.CommonKeys" %>
<%@ page import="com.dtv.oss.util.Postern" %>
<%@ page import="com.dtv.oss.web.util.CurrentLogonOperator"%>
<%@ page import="com.dtv.oss.dto.CustomerDTO" %>

<BR>
<%     
     int iFlag=WebUtil.StringToInt(request.getParameter("func_flag"));
     System.out.println("iFlag===="+iFlag);
     String sName="";
     String sAdditionalInfo="";
     Integer iID=null;
     Integer iAdditionalID=null;
     String strID="";
     CustServiceInteractionDTO csiDto = null;
     Map map = null;
      
     CommandResponseImp CmdRep = (CommandResponseImp)request.getAttribute("ResponseFromEJBEvent");    
     if (CmdRep!=null){
        try{
           switch (iFlag){
         	   case 503:
         	   case 504:
         	   case 1114:  //Э���û�����
         	     iAdditionalID = (Integer)CmdRep.getPayload();
         	     
         	     break;
         	      case 201:
         	     map = (Map)CmdRep.getPayload();
         	     iID = (Integer)map.get("AccountID");
         	     iAdditionalID = (Integer)map.get("CustServiceInteractionID");  	     
         	     break;
         	     
         	     case 102:
         	     case 103:
         	     case 105:
         	     map = (Map)CmdRep.getPayload();
         	     iID = (Integer)map.get("CustomerID");
         	     iAdditionalID = (Integer)map.get("CustServiceInteractionID");
         	     
         	     break; 
         	     case 106:
         	     map = (Map)CmdRep.getPayload();
         	     iID = (Integer)map.get("CustomerID");
         	     iAdditionalID = (Integer)map.get("CustServiceInteractionID");
         	     
         	     break; 
         	   case 1:
         	     map = (Map)CmdRep.getPayload();
         	     strID = map.get("saID").toString();
         	     iAdditionalID = (Integer)map.get("CustServiceInteractionID");
         	     break;
         	   case 1112:
         	       sAdditionalInfo=CmdRep.getPayload().toString();
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
             sAdditionalInfo="ҵ���ʻ����Ϊ:"+strID+"; �������Ϊ��"+iAdditionalID.intValue();
             break;  
         case 101:              
             sName="ԤԼ��Ϣ";
             sAdditionalInfo="ԤԼ�������Ϊ:"+strID+"��";
             break;  
         case 102:
             sName="ԤԼ������Ϣ";
             sAdditionalInfo="�ͻ�֤��Ϊ:"+strID+";"+"�������Ϊ��" +iAdditionalID.intValue();
             break; 
             
         case 103:              
             sName="�ŵ꿪����Ϣ";
             sAdditionalInfo="�ͻ�֤��Ϊ:"+strID+";"+"�������Ϊ��"+iAdditionalID.intValue();
             break; 
         case 104:
             sName="������ԤԼ";
             sAdditionalInfo="ԤԼ�������Ϊ:"+strID+"��";  
             break; 
         case 105:
             sName="�ͻ�����";
             sAdditionalInfo="�ͻ�֤��Ϊ:"+strID+";"+"�������Ϊ��"+iAdditionalID.intValue();  
             break;    
         case 106:
             sName="ģ����ӿ���";
             sAdditionalInfo="�ͻ�֤��Ϊ:"+strID+";"+"�������Ϊ��"+iAdditionalID.intValue();  
             break;        
         case 256:
             sName="��ͬ����";
            
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
              if(!(request.getParameter("txtActionType")==null || "".equals(request.getParameter("txtActionType"))))
              	sName="��Ӧ���豸���";
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
         case 1014:
              sName="�豸����Ȩ";    
               break;  
         
         case 1112:
              sName="�豸����";    
               break; 
          case 1113:
              sName="�豸����";    
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
         case 5100:
         	sName="�ӿͻ�����";
         	break;  
         case 5101:
         	sName="Ͷ�ߴ���";
         	break;	
         case 6000:
         	sName="��Ʊ����";

         	break;			
          
          case 6005:
         	sName="��Ʊ����";
         	break;  

          case 6001:
         	sName="��Ʊ�ؿ�";
         	break;
         
         case 6002:
         	sName="��Ʊ����";
         	break;
         	
         case 6003:
         sName="��Ʊ����";
         	break;				
                   	
         	case 6006:
         	sName="��Ʊ����";
         	break;         	 	 	  		    		 
       }
       
%>
<script language="JavaScript" type="text/JavaScript">

function frmSubmit(url){
	 document.frmPost.action = url;
   document.frmPost.submit();
}

function customer_protocol(txtCustomerID){
	self.location="customer_protocol.do?txtCustomerID="+txtCustomerID;
}

function customer_service_print(customerID,csiID)
{
	
		document.frmPost.target="_blank";
		document.frmPost.action="<d:config prefix="" showName="SYSTEMSYMBOLNAME" suffix="_" />customer_service_print.do?txtCustomerID="+customerID+"&csiID="+csiID;
		document.frmPost.submit();
		document.frmPost.target="_self";
	
}
function customerRegister_print_submit(str){	
	 document.frmPost.target="_blank";
	 document.frmPost.action=str;	
	 document.frmPost.submit();
}
function show_customer_info_opened(strId){
	self.location.href="customer_view.do?txtCustomerID="+strId;
}

function customer_service_receipt_print(customerID,csiID)
{
	 document.frmPost.target="_blank";
	 document.frmPost.action="customer_service_receipt_print_lijiang.do?txtCustomerID="+customerID+"&csiID="+csiID;
	 document.frmPost.submit();
	 document.frmPost.target="_self";
}
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
			<td><input name="Submit22" type="button" class="button" onClick="javascript:frmSubmit('agent_csi_operator.do')" value="���ش�����ԤԼ"></td>
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
           	String strButtonName="�������豸���";
           	if(!(request.getParameter("txtActionType")==null || "".equals(request.getParameter("txtActionType"))))
           		strButtonName="���ع�Ӧ���豸���";
%>
   			<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
			<td><input name="Submit22" type="button" class="button" onClick="javascript:frmSubmit('dev_into_depot.screen?txtActionType=<tbl:writeparam name="txtActionType" />')" value="<%=strButtonName %>"></td>
			<tbl:hiddenparameters pass="txtDeviceClass/txtDeviceModel/txtDeviceProvider/txtBatchNo/txtGuaranteeLength/txtDepotID/txtComments/seriallength/checkModelField/checkModelDesc/txtTerminalDevices/txtStatusReason/txtOutOrgID/txtInAndOut/txtActionType/txtNotExistSerialNo" />
			<tbl:hiddenparameters pass="txtPurposeStrList/txtPurposeStrListValue" />
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
           		pageContext.setAttribute("SYSTEMSYMBOLNAME", Postern.getSystemSymbolName());
            	pageContext.setAttribute("customerDTO", Postern.getCustomerByID(Integer.parseInt(strID)));
            	csiDto = Postern.getCsiDTOByCSIID(iAdditionalID.intValue());
            	pageContext.setAttribute("csiDto", Postern.getCsiDTOByCSIID(iAdditionalID.intValue()));
	     %>
   			<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
		  	<td><input name="Submit22" type="button" class="button" onClick="javascript:frmSubmit('menu_open_for_booking.do?OpenFlag=<%=CommonKeys.ACTION_OF_BOOKINGACCOUNT%>')" value="������ԤԼ����"></td>
			<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
			  
			<pri:authorized name="service_interaction_view_for_print_registration_config.do" >
		    <d:displayControl id="button_service_interaction_view_for_print_registration_config" bean="customerDTO,csiDto,SYSTEMSYMBOLNAME"><!--ԤԼ���� ��ӡ����-->
		    <td width="20" height="20"></td>
		    <td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
		    <td><input name="Submit22" type="button" class="button" onClick="javascript:config_print('<%=csiDto.getId()%>','<%=CommonKeys.SET_V_PRINTSHEETTYPE_S%>','<%=csiDto.getType()%>','<%=csiDto.getCreateReason()%>','<%=CommonKeys.SET_V_PRINTSHEETREASON_R%>')" value="����ǼǴ�ӡ"></td>
		    <td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>	
		    </d:displayControl>
		    </pri:authorized>
		    
		    <pri:authorized name="service_interaction_view_for_print_billing_config.do" >
		    <d:displayControl id="button_service_interaction_view_for_print_billing_config" bean="customerDTO,csiDto,SYSTEMSYMBOLNAME"><!--ԤԼ���� ��ӡ����-->
		    <td width="20" height="20"></td>
		    <td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
		    <td><input name="Submit22" type="button" class="button" onClick="javascript:config_print('<%=csiDto.getId()%>','<%=CommonKeys.SET_V_PRINTSHEETTYPE_S%>','<%=csiDto.getType()%>','<%=csiDto.getCreateReason()%>','<%=CommonKeys.SET_V_PRINTSHEETREASON_F%>')" value="�շѵ��ݴ�ӡ"></td>
		    <td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
		    </d:displayControl>
		    </pri:authorized>  
		    
		    <pri:authorized name="customer_view.do" >
		    <lgc:showcontentbysetting settingName="SET_V_SHOW_CUSTINFOBUTTONFOROPEN" >
		    <td width="20" height="20"></td>
		    <td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
		    <td><input name="Submit22" type="button" class="button" onClick="javascript:show_customer_info_opened('<%=strID%>')" value="�鿴�ͻ���Ϣ"></td>
		    <td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>  
		    </lgc:showcontentbysetting> 
		    </pri:authorized>
		    
        <%           
            break; 
            //�ŵ꿪��
            case 103:
            	pageContext.setAttribute("SYSTEMSYMBOLNAME", Postern.getSystemSymbolName());
            	pageContext.setAttribute("customerDTO", Postern.getCustomerByID(Integer.parseInt(strID)));
            	csiDto = Postern.getCsiDTOByCSIID(iAdditionalID.intValue());
            	pageContext.setAttribute("csiDto", Postern.getCsiDTOByCSIID(iAdditionalID.intValue()));
        %>
   			
   			<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
			  <td><input name="Submit22" type="button" class="button" onClick="javascript:frmSubmit('catv_terminal_query_for_branch.do?OpenFlag=<%=CommonKeys.ACTION_OF_SHOPACCOUNT%>')" value="�������ŵ꿪��"></td>
		    <td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>  
		    <td width="20" height="20"></td>
		    <td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
		    <td><input name="Submit22" type="button" class="button" onClick="javascript:customer_service_receipt_print('<%=csiDto.getCustomerID()%>','<%=csiDto.getId()%>')" value="��Ʊ���ݴ�ӡ"></td>
		    <td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td> 
		    
		    <pri:authorized name="customer_view.do" >
		    <lgc:showcontentbysetting settingName="SET_V_SHOW_CUSTINFOBUTTONFOROPEN" >
		    <td width="20" height="20"></td>
		    <td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
		    <td><input name="Submit22" type="button" class="button" onClick="javascript:show_customer_info_opened('<%=strID%>')" value="�鿴�ͻ���Ϣ"></td>
		    <td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>  
		    </lgc:showcontentbysetting>
		    </pri:authorized> 
        <%
             break; 
            case 1:  //�ŵ�����
            	pageContext.setAttribute("SYSTEMSYMBOLNAME", Postern.getSystemSymbolName());
            	csiDto = Postern.getCsiDTOByCSIID(iAdditionalID.intValue());
            	pageContext.setAttribute("csiDTO", csiDto);            	
        %>
            <input type="hidden" name="saids" value="<%=strID%>" >
            <tbl:hiddenparameters pass="txtUsedMonth/txtBuyNum" />
            <td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
		        <td><input name="Submit22" type="button" class="button" onClick="javascript:customer_service_receipt_print('<%=csiDto.getCustomerID()%>','<%=csiDto.getId()%>')" value="��Ʊ���ݴ�ӡ"></td>
		        <td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>  
         <%
             break; 
             case 1114:  //Э���û�����
            	csiDto = Postern.getCsiDTOByCSIID(iAdditionalID.intValue());
            	pageContext.setAttribute("csiDTO", csiDto);	
        %>  
            <tbl:hiddenparameters pass="saId_indexs/ProductList/txtUsedMonth" />
            <td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
		        <td><input name="Submit22" type="button" class="button" onClick="javascript:customer_service_receipt_print('<%=csiDto.getCustomerID()%>','<%=csiDto.getId()%>')" value="��Ʊ���ݴ�ӡ"></td>
		        <td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>  
         <%
             break; 
             case 104:
        %>
   			<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
			  <td><input name="Submit22" type="button" class="button" onClick="javascript:frmSubmit('agent_csi_operator.do')" value="���ش�����ԤԼ"></td>
			  <td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
			 <%
             break;
             case 105:
        %>
        <td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
			  <td><input name="Submit22" type="button" class="button" onClick="javascript:frmSubmit('menu_customer_info_input_directly.do?OpenFlag=<%=CommonKeys.ACTION_OF_BOOK_DIRECTLY%>')" value="�� ��"></td>
			  <td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
			  <td width="20" ></td>
			  <td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
			  <td><input name="Submit22" type="button" class="button" onClick="javascript:customer_protocol(<%=strID%>)" value="Э���ά��"></td>
			  <td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
        <% 
             break;
             case 106:
        %>
        		<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
			<td><input name="Submit22" type="button" class="button" onClick="javascript:frmSubmit('open_catv_create_info.screen')" value="�� ��"></td>
			<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
        <% 
             break;      
             case 256:
        %>
   			<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
			<td><input name="Submit22" type="button" class="button" onClick="javascript:frmSubmit('contract_query_result.do?txtFrom=1&txtTo=10')" value="�� ��"></td>
			<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
        <%
             break;                 
             case 1001:
        
        boolean vflag = Postern.buttonCanBeVisibleForCP(WebUtil.StringToInt(strID),CurrentLogonOperator.getOperator(request).getOrgID());
       pageContext.setAttribute("canBeVisible", vflag+"");
       pageContext.setAttribute("custproblem",Postern.getCustomerProblemDto(WebUtil.StringToInt(strID)));%>
        		<pri:authorized name="assignrep_query_result.do" >
			<d:displayControl id="button_customerproblem_query_detail_repair_assign" bean="custproblem,canBeVisible">
   			<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
   			<td background="img/button_bg.gif"><a href="assignrep_query_result.do?txtCustomerProblemID=<%=strID%>" class="btn12">���뱨���ɹ�</a></td>
			<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
			</d:displayControl>
			</pri:authorized>
                         <td width="20" ></td>
   			<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
			<td><input name="Submit22" type="button" class="button" onClick="javascript:frmSubmit('<bk:backurl property='cp_create.do' />')" value="��������"></td>
			<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
        
  <%
            break; 
           case 1003:
           	String strButtonName="�������豸���";
           	if(!(request.getParameter("txtActionType")==null || "".equals(request.getParameter("txtActionType"))))
           		strButtonName="���ع�Ӧ���豸���";
%>
   			<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
			<td><input name="Submit22" type="button" class="button" onClick="javascript:frmSubmit('dev_into_depot.screen?txtActionType=<tbl:writeparam name="txtActionType" />')" value="<%=strButtonName %>"></td>
			<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
<%
          break;             
            case 1006:
        %>
   			<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
   			<td background="img/button_bg.gif"><a href="device_match.do" class="btn12">���������Ϣ¼��</a></td>
			 
			<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
     <%
            break; 
            case 1007:
        %>
   			<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
   			<td background="img/button_bg.gif"><a href="device_auth.do" class="btn12">�������ܿ�Ԥ��Ȩ</a></td>
			<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
            
            <td width="20" ></td>
            <%if(request.getParameter("txtDeviceID")!=null && !"".equals(request.getParameter("txtDeviceID"))){ %>
			<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
        	<td background="img/button_bg.gif"><a href="dev_detail.do?txtDeviceID=<%=request.getParameter("txtDeviceID")%>" class="btn12">�����豸��ѯ��ϸ��Ϣ</a></td>
			<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
			<td width="20" ></td>		
			<%}%>
        <%
            break; 
            case 1008:
        %>
   			<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
   			<td background="img/button_bg.gif"><a href="device_dismatch.do" class="btn12">�����豸�����</a></td>
			<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
			
            <td width="20" ></td>
            <%if(request.getParameter("txtDeviceID")!=null && !"".equals(request.getParameter("txtDeviceID"))){ %>
			<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
        	<td background="img/button_bg.gif"><a href="dev_detail.do?txtDeviceID=<%=request.getParameter("txtDeviceID")%>" class="btn12">�����豸��ѯ��ϸ��Ϣ</a></td>
			<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
			<td width="20" ></td>		
			<%}%>             			
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
   			<td background="img/button_bg.gif"><a href="device_return.do" class="btn12">�����豸�ؿ���Ϣ¼��</a></td>
			 
			<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>       			                                       			                                       			
       <% 
			
           break; 
           case 1014:
        %>
   			<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
   			<td background="img/button_bg.gif"><a href="device_disauth.do" class="btn12">�������ܿ�����Ȩ</a></td>
			<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
			
			<td width="20" ></td>
            <%if(request.getParameter("txtDeviceID")!=null && !"".equals(request.getParameter("txtDeviceID"))){ %>
			<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
        	<td background="img/button_bg.gif"><a href="dev_detail.do?txtDeviceID=<%=request.getParameter("txtDeviceID")%>" class="btn12">�����豸��ѯ��ϸ��Ϣ</a></td>
			<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
			<td width="20" ></td>
			<%}%>
                         			
        <%
            break; 

           case 1112:
        %>
   			<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
   			<td background="img/button_bg.gif"><a href="menu_device_out_stock.do" class="btn12">�����豸������Ϣ¼��</a></td>
			 
			<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>       			                                       			                                       			
       <% 
			
           break; 
       case 1113:
        %>
   			<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
   			<td background="img/button_bg.gif"><a href="menu_device_scrap.do" class="btn12">�����豸������Ϣ¼��</a></td>
			 
			<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>       			                                       			                                       			
       <% 
			
           break; 
          
           case 1013:
             	pageContext.setAttribute("SYSTEMSYMBOLNAME", Postern.getSystemSymbolName());
				csiDto = Postern.getCsiDTOByCSIID(Integer.parseInt(strID));
            	//System.out.println("customerDTO:"+pageContext.getAttribute("customerDTO"));
        %>
        <tbl:hiddenparameters pass="saId_indexs/ProductList/txtUsedMonth/CampaignList" />
		<td width="20" height="20"></td>
		<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
		<td><input name="Submit22" type="button" class="button" onClick="javascript:customer_service_receipt_print('<%=csiDto.getCustomerID()%>','<%=csiDto.getId()%>')" value="�շѵ��ݴ�ӡ"></td>
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
   			 
   			<td background="img/button_bg.gif"><a href="package_query_result.do?txtFrom=1&txtTo=10" class="btn12">���ز�Ʒ����ѯ</a></td>
			 
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
   			 
   			<td background="img/button_bg.gif"><a href="query_points_acumulate_rule.do?txtFrom=1&txtTo=10" class="btn12">���ػ����ۼӹ����ѯ</a></td>
			 
			<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td> 			
	 <% 
			
            break; 
            case 5080:
           String  activityID = request.getParameter("vartxtActivityID");
        %>
   			 <td><img src="img/button2_r.gif" border="0" ></td>
   			 
   			<td background="img/button_bg.gif"><a href="activity_query_result.do?txtActivityID=<%=activityID%>" class="btn12">����</a></td>
			 
			 <td><img src="img/button2_l.gif" border="0" ></td>	 
	 <% 
			
            break; 
            case 5099:
           String  segmentID = request.getParameter("txtSegmentID");
        %>
   			   <td><img src="img/button2_r.gif" border="0" ></td>
   			 
   			<td background="img/button_bg.gif"><a href="config_market_segment_detail.do?txtID=<%=segmentID%>" class="btn12">�� ��</a></td>
			 
			 <td><img src="img/button2_l.gif" border="0" ></td>	
			 <% 
			
            break; 
            case 5085:
            
        %>
   			  <td><img src="img/button2_r.gif" border="0" ></td>
   			 
   			<td background="img/button_bg.gif"><a href="market_segment_query_result.do?txtFrom=1&txtTo=10" class="btn12">����</a></td>
			 
			 <td><img src="img/button2_l.gif" border="0" ></td>	  
 <% 
			
            break; 
            case 5097:
          
        %>
   			  <td><img src="img/button2_r.gif" border="0" ></td>
   			 
   			<td background="img/button_bg.gif"><a href="config_query_result.do?txtFrom=1&txtTo=10" class="btn12">����</a></td>
			 <td><img src="img/button2_l.gif" border="0" ></td>	      			                          
   			 
       <%
           break;
           case 500:
       %>
                        <td ><img src="img/button2_r.gif"" width="22" height="20"></td>
			<td background="img/button_bg.gif"  height="20">
			  <a href="javascript:frmSubmit('<bk:backurl property='service_interaction_query_result_for_callback.do/service_interaction_query_result.do/service_interaction_view.do' />')" class="btn12">��&nbsp;��</a>
			</td>
		        <td><img src="img/button2_l.gif" width="13" height="20"></td>
	 <% 
			
            break; 
            case 503:
            
           
        %>
   			 <td><img src="img/button2_r.gif" border="0" ></td>
   			 
   			<td background="img/button_bg.gif"><a href="taocan_query.do?txtCampaignID=<%=iAdditionalID%>" class="btn12">����</a></td>
			 
			 <td><img src="img/button2_l.gif" border="0" ></td>	 
 <% 
			
            break; 
            case 504:
            
           
        %>
   			 <td><img src="img/button2_r.gif" border="0" ></td>
   			 
   			<td background="img/button_bg.gif"><a href="campaign_query.do?txtCampaignID=<%=iAdditionalID%>" class="btn12">����</a></td>
			 
			 <td><img src="img/button2_l.gif" border="0" ></td>	 				 	        
						 
       <%
           break;
           case 501:
       %>
                        <td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
			<td><input name="Submit22" type="button" class="button" onClick="javascript:frmSubmit('<bk:backurl property='group_bargain_query_result.do'/>')" value="��  ��"></td>
			<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
                      
      <%
          break;
          case 5100:
       %>
                        <td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
			<td><input name="Submit22" type="button" class="button" onClick="javascript:frmSubmit('<bk:backurl property='open_group_subcustomer.do'/>')" value="��  ��"></td>
			<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
	<%
          break;
          case 5101:
       %>
           		<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
			<td><input name="Submit22" type="button" class="button" onClick="javascript:frmSubmit('<bk:backurl property='cust_complain_queryAction.do'/>')" value="��  ��"></td>
			<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>          
      <%
          break;
         case 6000:
       %>
       <td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
   			<td background="img/button_bg.gif"><a href="menu_fapiao_into_depot.do" class="btn12">����</a></td>
			<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td> 
           		          
      <%
          break;

         case 6005: 
       %>
       <td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
   			<td background="img/button_bg.gif"><a href="menu_fapiao_discard.do" class="btn12">����</a></td>
			<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td> 
           		          
      <%
          break;

           case 6001:
       %>
       <td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
   			<td background="img/button_bg.gif"><a href="menu_fapiao_back.do" class="btn12">����</a></td>
			<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td> 	          
      <%
       break;	
      case 6002:
       %>
       <td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
   			<td background="img/button_bg.gif"><a href="menu_fapiao_move.do" class="btn12">����</a></td>
			<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td> 
           		          
      <%
      break;	
      case 6003:
       %>
       <td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
   			<td background="img/button_bg.gif"><a href="menu_fapiao_use.do" class="btn12">����</a></td>
			<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td> 
           		          
      <%
          break;
          
                    
         case 6006: 
       %>
       <td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
   			<td background="img/button_bg.gif"><a href="menu_fapiao_abandon.do" class="btn12">����</a></td>
			<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td> 
           		          
      <%
          break;
                    
          case 20001: //ҵ����(������ɾ��,����)
      %>
       <td width="11" height="20"><img src="img/button2_r.gif" width="22" height="20"></td>
	<td><input name="Submit22" type="button" class="button" onClick="javascript:frmSubmit('<bk:backurl property='config_serviceInfo_query.do' />')" value="����ҵ���ѯ"></td>
	<td width="22" height="20"><img src="img/button2_l.gif" width="11" height="20"></td>
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
	
	function config_print(csiId,PrintSheetType,SheetSubType,CsiReason,PrintReason)
	{
		
		document.frmPost.target="_blank";
		document.frmPost.action="config_print.do?txtCsiId="+csiId+"&txtPrintSheetType="+PrintSheetType+"&txtSheetSubType="+SheetSubType+"&txtCsiReason="+CsiReason+"&txtPrintReason="+PrintReason;
		document.frmPost.submit();
		document.frmPost.target="_self";
		
	}
</Script>
 </td>
         </tr>
      </table>
      
      
