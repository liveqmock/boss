<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="osstags" prefix="o" %>
<%@ taglib uri="bookmark" prefix="bk" %>
<%@ taglib uri="privilege" prefix="pri" %>
<%@ taglib uri="osstags" prefix="d" %>

<%@ page import="com.dtv.oss.web.util.WebUtil" %>
<%@ page import="com.dtv.oss.web.util.WebOperationUtil" %>
<%@ page import="com.dtv.oss.util.Postern" %>
<%@ page import="com.dtv.oss.service.commandresponse.CommandResponse"%>
<%@ page import="com.dtv.oss.service.commandresponse.CommandResponseImp" %>
<%@ page import="com.dtv.oss.web.util.CommonKeys" %>
<%@ page import="java.util.*" %>
<%@ page import="com.dtv.oss.service.util.CommonConstDefinition"%>
<%@ page import="com.dtv.oss.web.util.CurrentLogonOperator"%>
<%@ page import="com.dtv.oss.dto.CustServiceInteractionDTO" %>
<%@ page import="com.dtv.oss.dto.CustomerDTO" %>

<BR>
<%
       String sFlag=request.getParameter("func_flag");
       System.out.println("sFlag========================================================"+sFlag);
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
       String custId= request.getParameter("txtCustomerID");
       String txtCsiType= request.getParameter("txtCsiType");
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
function batchUpdateAcctSubmit(customerID,csiID){
        document.frmPost.txtCustomerID.value =customerID;
        document.frmPost.txtCsiID.value =csiID;
        document.frmPost.txtAccountStatus.value="N;O;T";
        document.frmPost.action ="modify_account_prepare_view.do";
        document.frmPost.submit();

}
function returnBookAccountQuery(custID,txtCsiType){
	txtstatus ="P;W";
	self.location.href="query_book_account.do?txtCustomerID="+custID+"&txtCsiType="+txtCsiType+"&txtStatus="+txtstatus;
}
function queryFriendPhoneNO(serAccID,cusID){
	
	self.location.href="query_friend_phoneno.do?txtServiceAccountID="+serAccID+"&txtCustomerID="+cusID;
}
function cust_camplain_query(actiontype){
	self.location.href="cust_complain_query.do?actiontype="+actiontype+"&txtFrom=1&txtTo=10";
}
function bookProduct(serAccID,cusID){
	
	self.location.href="menu_book_product.do?txtServiceAccountID="+serAccID+"&txtCustomerID="+cusID;
}


function customer_prepay_print(customerID,csiID)
{
		document.frmPost.target="_blank";
		document.frmPost.action="customer_service_receipt_print_lijiang.do?txtCustomerID="+customerID+"&csiID="+csiID;
		document.frmPost.submit();
		document.frmPost.target="_self";
	
}
//-->
</script>
<TABLE border="0" align="center" cellspacing="0" cellpadding="0" width="816">
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
        <font color="red"><i>�������ɹ���������Ϣ����:</i></font>
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
        �����ɹ���
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
	<input type="hidden" name="txtFrom" size="20" value="1">
  <input type="hidden" name="txtTo" size="20" value="10">
      <table width="50%"  border="0" align="center" cellpadding="0" cellspacing="10">
        <tr align="center">
          <td height="37">
		  	  <table  border="0" cellspacing="0" cellpadding="0">
				  <tr>
				  
				  
				  
<%
   //-------�д�Ҫ��ʾ�İ�ť-------
%> 
<lgc:haserror> 
<%
       switch (iFlag)
       {
         case 101: //contact user for installing
%> 
					<td><img src="img/button2_r.gif" border="0" width=22 height=20 ></td>
					<td><input name="Submit22" type="button" class="button" onClick="javascript:frmSubmit('<bk:backurl property='job_card_view_for_contact_of_install.do' />')" value="��дԤԼ��Ϣ"></td>
					<td><img src="img/button2_l.gif" border="0" width=11 height=20 ></td> 
<%
          break;
         case 102: //install_info_update
%>
				<td><img src="img/button2_r.gif" border="0" width=22 height=20 ></td>
				<td><input name="Submit22" type="button" class="button" onClick="javascript:frmSubmit('<bk:backurl property='job_card_view_for_register_of_install.do/catv_job_card_view_for_register.do' />')" value="��д��װ��Ϣ"></td>
				<td><img src="img/button2_l.gif" border="0" width=11 height=20 ></td> 
          
<%
          break;
          case 105: //install_info_update
%>
				<td><img src="img/button2_r.gif" border="0" width=22 height=20 ></td>
				<td><input name="Submit22" type="button" class="button" onClick="javascript:frmSubmit('<bk:backurl property='catv_job_card_view_for_register.do' />')" value="��дʩ����Ϣ"></td>
				<td><img src="img/button2_l.gif" border="0" width=11 height=20 ></td> 
          
<%
          break;
         case 204: //install_close
%>
				<td><img src="img/button2_r.gif" border="0" width=22 height=20 ></td>
				<td><input name="Submit22" type="button" class="button" onClick="javascript:frmSubmit('<bk:backurl property='cust_forced_deposit_query_result.do' />')" value="�ͻ�Ѻ���б�"></td>
				<td><img src="img/button2_l.gif" border="0" width=11 height=20 ></td> 
<%
          break;

	  case 5002: //install_close
%>
 				<td><img src="img/button2_r.gif" border="0" width=22 height=20 ></td>
				<td><input name="Submit22" type="button" class="button" onClick="javascript:frmSubmit('<bk:backurl property='change_current_account.do' />')" value="����"></td>
				<td><img src="img/button2_l.gif" border="0" width=11 height=20 ></td> 
<%
          break;
          
          case 11008: //�ų����񴴽� zhouxushun
          
%>        
  				<td><img src="img/button2_r.gif" border="0" width=22 height=20 ></td>
				<td><input name="Submit22" type="button" class="button" onClick="javascript:frmSubmit('<bk:backurl property='show_sacp_for_schedule.do' />')" value="�ų����񴴽�����"></td>
				<td><img src="img/button2_l.gif" border="0" width=11 height=20 ></td> 
<%
          break;
          
          case 11009: //�ų�����ά�� davidy.yang
%>
  				<td><img src="img/button2_r.gif" border="0" width=22 height=20 ></td>
				<td><input name="Submit22" type="button" class="button" onClick="javascript:frmSubmit('<bk:backurl property='schedule_add_of_sidebar.do?txtCustomerID=<%=request.getParameter("txtCustomerID")%>' />')" value="�ų����񴴽�����"></td>
				<td><img src="img/button2_l.gif" border="0" width=11 height=20 ></td> 
<%
          break;  
          
          case 6001:  //Ԥ����
%>
	<td><img src="img/button2_r.gif" border="0" width=22 height=20 ></td>
				<td><input name="Submit22" type="button" class="button" onClick="javascript:frmSubmit('account_view.do?txtCustomerID=<%=request.getParameter("txtCustomerID") %>&txtAccountID=<%=request.getParameter("txtAccountID") %>')" value="���ص��ʻ���Ϣ"></td>
				<td><img src="img/button2_l.gif" border="0" width=11 height=20 ></td> 

<%
          break;  
          
          case 8001:  //����������ѯ
%>
	<td><img src="img/button2_r.gif" border="0" width=22 height=20 ></td>
	<td><input name="Submit22" type="button" class="button" onClick="javascript:frmSubmit('menu_batch_query_query.do')" value="����������ѯά��"></td>
	<td><img src="img/button2_l.gif" border="0" width=11 height=20 ></td> 
	
<%	break;

	case 1101:  //�����������񵥣�һ��Ƿ������/����Ƿ������/��Ʒ��ͣ/������ͣ/����ȡ��/����������Ϣ��
%>
	<td><img src="img/button2_r.gif" border="0" width=22 height=20 ></td>
	<td><input name="Submit22" type="button" class="button" onClick="javascript:frmSubmit('<bk:backurl property='batchJob_create_common.do' />')" value="����"></td>
	<td><img src="img/button2_l.gif" border="0" width=11 height=20 ></td> 
	
<%	break;

	case 1102:  //�޸ġ�ȡ���������񵥣�һ��Ƿ������/����Ƿ������/��Ʒ��ͣ/������ͣ/����ȡ��/����������Ϣ��
%>
	<td><img src="img/button2_r.gif" border="0" width=22 height=20 ></td>
	<td><input name="Submit22" type="button" class="button" onClick="javascript:frmSubmit('<bk:backurl property='batchJob_update.do' />')" value="����"></td>
	<td><img src="img/button2_l.gif" border="0" width=11 height=20 ></td> 
	
<%	break;

	case 999:  //�޸ķ������
%>
	<tbl:hiddenparameters pass="txtServiceCode/txtServiceAccountID/txtCustomerID/txtAccount/" /> 
	<td><img src="img/button2_r.gif" border="0" width=22 height=20 ></td>
	<td><input name="Submit22" type="button" class="button" onClick="javascript:frmSubmit('service_account_code_modify.screen')" value="���ط�������޸�"></td>
	<td><img src="img/button2_l.gif" border="0" width=11 height=20 ></td> 
	
<%	break;

	case 7001: //��Ʒ��ͣ
  case 7002: //��Ʒ�˶�
  case 7003: //��Ʒ�ָ�
       %>
   <bk:canback url="service_account_query_result_by_sa.do">
    <td width="20">&nbsp;&nbsp;</td> 
    <td><img src="img/button2_r.gif" width="22" height="20"></td>
             <td background="img/button_bg.gif"  ><a href="<bk:backurl property='service_account_query_result_by_sa.do' />" class="btn12">����ҵ���ʻ���Ϣ</a></td>           
             <td><img src="img/button2_l.gif" width="11" height="20"></td>
              <td width="20" >&nbsp;&nbsp;&nbsp</td>
   </bk:canback>
       <%
           break;
        case 11020: //���Ի������޸� 
%>
  				<td><img src="img/button2_r.gif" border="0" width=22 height=20 ></td>
				<td><input name="Submit22" type="button" class="button" onClick="javascript:frmSubmit('<bk:backurl property='customer_billing_rule_query.do/customer_product_view.do' />')" value="����"></td>
				<td><img src="img/button2_l.gif" border="0" width=11 height=20 ></td>
<%
          break;    
          
          case 11021: //���Ի��������� 
%>
  				<td><img src="img/button2_r.gif" border="0" width=22 height=20 ></td>
				<td><input name="Submit22" type="button" class="button" onClick="javascript:frmSubmit('<bk:backurl property='customer_billing_rule_query.do/customer_product_view.do' />')" value="����"></td>
				<td><img src="img/button2_l.gif" border="0" width=11 height=20 ></td>
<%
          break;  	
          
          case 11022: //ҵ���ʻ���������
%>
  				<td><img src="img/button2_r.gif" border="0" width=22 height=20 ></td>
				<td><input name="Submit22" type="button" class="button" onClick="javascript:frmSubmit('<bk:backurl property='service_account_resume_view.do' />')" value="����"></td>
				<td><img src="img/button2_l.gif" border="0" width=11 height=20 ></td>
<%
          break;  
          
		  case 11024: //����ģ���ն�ά��ҳ��
%>
  				<td><img src="img/button2_r.gif" border="0" width=22 height=20 ></td>
				<td><input name="Submit22" type="button" class="button" onClick="javascript:frmSubmit('catv_terminal_update.screen?id=<%=request.getParameter("txtID")%>')" value="����"></td>
				<td><img src="img/button2_l.gif" border="0" width=11 height=20 ></td>
<%
          break;
          
          case 11025: //����ģ���ն�¼��ҳ��
%>
  				<td><img src="img/button2_r.gif" border="0" width=22 height=20 ></td>
  				<td><input name="Submit22" type="button" class="button" onClick="javascript:frmSubmit('catv_terminal_create.screen')" value="����"></td>
				<td><img src="img/button2_l.gif" border="0" width=11 height=20 ></td>
<%
          break;		
    } 
   	%> 
</lgc:haserror>

<%

       //ͨ���Ƿ���
       String strBackUrl = "";
       String strBtnName = "���ز�ѯ���";
       
       switch (iFlag)
       {
         case 101: //contact user for installing
           strBackUrl = "job_card_query_result_for_re_contact_of_install.do/job_card_query_result_for_contact_of_install.do/job_card_query_result_for_contact_of_install_redo.do/catv_job_card_query_result_for_contact_of_install.do";
           break;
         //case 102: //install_info_update
         //  strBackUrl = "pi_query_result_for_register.do";
         //   break; 
       //    case 103: //install_close 
        //   strBackUrl = "ci_query_result_for_close.do/catv_query_result_for_close.do";
         //   strBtnName = "�����ɹ���װ������ѯ";
        //   break;
           
         case 110: //cancel booking,confirm_booking
           strBackUrl = "agent_csi_query.do/service_interaction_query_result.do";
           strBtnName ="�������ԤԼ����ѯ";
           break;
          case 111: //contact user for installing
           strBackUrl = "job_card_query_result_for_contact_of_install.do/job_card_query_result_for_re_contact_of_install.do/catv_job_card_view_for_contact.do";
           break;           
         case 120: //cancel customer problem
           strBackUrl = "cp_query_detail.do";
           strBtnName = "������ر��޵�";
           break;
          case 201: //customer move �ͻ�Ǩ��
           strBackUrl = "customer_view.do?txtCustomerID="+request.getParameter("txtCustomerID");
           strBtnName = "���ؿͻ�����";
           break;
         case 202: //customer move �ͻ�����
           strBackUrl = "customer_view.do?txtCustomerID="+request.getParameter("txtCustomerID");
           strBtnName = "���ؿͻ�����";
           break;
      
         case 501: //close open
           strBackUrl = "service_interaction_view.do";
           break;
         case 502: //customer product op
           strBackUrl = "customer_product_view.do";
           strBtnName = "���ز鿴��Ʒ";
           break;
         case 601: //service account resend
           strBackUrl = "customer_product_query_result_by_sa.do";
           strBtnName = "���ز鿴��Ʒ";
           break;
         case 1003: //����ԤԼ  
           strBackUrl = "contactrep_query_result.do";
           break;
           /**
         case 1004: //���޻ط�  
           strBackUrl = "cp_query_for_repair.do";
           strBtnName = "���ز鿴���޻ط���Ϣ";
           break;
           **/
         case 1005: //�ֹ��������޵�
           strBackUrl = "cp_query_for_manual_close.do";
            strBtnName = "�����ֹ��������޵�";
           break;
         case 1006: //�ֹ���ת���޵�
           strBackUrl = "sheetrep_query.do";
           break;  
         case 1007: //���޵�ԤԼ
           strBackUrl = "contactrep_query_result.do";
           break; 
           /** 
           case 1008: //¼��ά����Ϣ
           strBackUrl = "register_repair_info_query_result.do";
           strBtnName = "���ز鿴¼��ά����Ϣ";
           break;
           **/
         case 1009: //����ά����Ϣ
           strBackUrl = "cp_query_result.do";
           break; 
         case 1100://�������
           strBackUrl = "cp_query_detail.do";
           break;
         case 1115: //��ֹ���޵�
           strBackUrl = "cp_query_for_terminate.do";
           break;
         case 1116: //�ֹ�¼������ʩ����
           strBackUrl = "catv_job_card_internet_register.do";
           break;
         
         case 5002: //���ز�Ʒ�б�
           strBackUrl = "customer_product_query_result_by_sa.do/service_account_query_result_by_sa.do";
           strBtnName = "���ز�Ʒ�б�";
           break; 
           
         case 5003: //�����Ź������û���ѯ
           strBackUrl = "customer_groupbargain_end_query.do";
           strBtnName = "����";
           break;  
         case 5004: //���ص��������ѯ
           strBackUrl = "campaign_detail.do";
           
           break;  
          case 5005: //���ص��������ѯ
           strBackUrl = "taocan_detail.do";
           
           break;     
        
        case 11010: //ȡ���ų̷��ص��ų����
           strBackUrl = "schedule_show_detail.do";    
           strBtnName = "����";
           break; 
        case 11011: //�޸��ų̷��ص��ų��޸�ҳ��
           strBackUrl = "schedule_modify_view.do";    
           strBtnName = "����";
           break; 
        case 1105: //��������
           strBackUrl = "service_interaction_view.do";
           strBtnName = "����";
           break;  
		case 1106: //�ͻ�Ѻ���˻����ͻ�Ѻ��û��
           strBackUrl = "customer_deposit_query_result.do";
           strBtnName = "���ؿͻ�Ѻ���ѯ";
           break; 
      
		case 210: //�ͻ�Ͷ�߲�ѯ
        	strBtnName = "����";
        	break; 
    	case 1015: //���ܿ�ˢ��Ԥ��Ȩ
           strBackUrl = "dev_detail.do";
           strBtnName = "����";
           
           break;
           
        case 4001: //���ص�IPPVǮ����ϸ
           strBackUrl = "ippv_detail.do";
           
           break;  
           
           
           case 4003: //���ص�IPPVǮ����ѯ
           strBackUrl = "ippv_query.do";
           break;
           
           case 4081: //���ص�ƽ��С����ϸ
           strBackUrl = "dtv_migration_detail.do";
           
           break; 

        } 
%>
<%
       
       if (!strBackUrl.equals(""))
       {
       System.out.println("���ص�URL: " + strBackUrl);
%>

 <bk:canback url="<%=strBackUrl%>">
    <td width="20">&nbsp;&nbsp;</td> 
    <td><img src="img/button2_r.gif" width="22" height="20"></td>
             <td background="img/button_bg.gif"  ><a href="<bk:backurl property='<%=strBackUrl%>' />" class="btn12">��&nbsp;��</a></td>           
             <td><img src="img/button2_l.gif" width="11" height="20"></td>
              <td width="20" >&nbsp;&nbsp;&nbsp</td>
  </bk:canback>                              
 
<%
        }
%>

<%
   //-------û��Ҫ��ʾ�İ�ť-------
%>
<lgc:hasnoterror>


<%
 String txtActivityID = null;
 int iReferSheetID = 0;
       switch (iFlag)
       {
         case 101: //contact user for installing
         String jobcardID =request.getParameter("txtJobCardID");
         if (WebOperationUtil.isBookingOfJobCardConfirmed(WebUtil.StringToInt(jobcardID)))
         {
              iReferSheetID = WebUtil.StringToInt(request.getParameter("txtReferSheetID"));
              boolean showFlag =false;
              Collection lst = Postern.getDeviceIDByCSIID(iReferSheetID);
              Iterator it = lst.iterator();
              while (it.hasNext()){
                 Integer deviceId = (Integer)it.next();
                 if (deviceId.intValue() !=0){
                     showFlag =true;
                     break;
                 }
             }
           
           if (WebOperationUtil.isBookingCustServiceInteraction(iReferSheetID)){
           
%>
           <td><img src="img/button2_r.gif" border="0" width=22 height=20 ></td>
           <td><input name="Submit22" type="button" class="button" onClick="javascript:frmOpenSubmit('open_create_prepare_for_booking.do?txtID=<%=iReferSheetID%>')" value="����ԤԼ����"></td>
           <td><img src="img/button2_l.gif" border="0" width=11 height=20 ></td> 
           <td width="20">&nbsp;&nbsp;&nbsp</td>
<%
	          }
	          else if (showFlag) {
		boolean vflag = Postern.buttonCanBeVisibleForRepair(WebUtil.StringToInt(jobcardID),CurrentLogonOperator.getOperator(request).getOrgID());
       	        pageContext.setAttribute("canBeVisible", vflag+"");
       	        pageContext.setAttribute("jcdtoforbutton",Postern.getJobCardDto(WebUtil.StringToInt(jobcardID)));
%>
	<pri:authorized name="job_card_view_for_register_of_install.do">
        <o:displayControl id="button_query_detail_install_register" bean="jcdtoforbutton,canBeVisible">	 
           <td><img src="img/button2_r.gif" border="0" width=22 height=20 ></td>
	         <td><input name="Submit22" type="button" class="button" onClick="javascript:frmSubmit('job_card_view_for_register_of_install.do?txtJobCardID=<%=jobcardID%>')" value="����¼�밲װ��Ϣ"></td>
           <td><img src="img/button2_l.gif" border="0" width=11 height=20 ></td> 
           </o:displayControl>
        </pri:authorized>
           <td width="20">&nbsp;&nbsp;&nbsp</td>
<%
	         }
	      } 
         break;
         
         case 102: //install_info_update 
           //����ǰ�װʧ�ܣ����Խ��������װ��������������ԤԼ
           String strIsOK = request.getParameter("txtIsSuccess");
           
           if ((strIsOK!=null)&&(strIsOK.compareToIgnoreCase("false")==0))
           { 
             String jobCardId = request.getParameter("txtJobCardID");
%>
                                <td width="20">&nbsp;&nbsp;</td>
  				<td><img src="img/button2_r.gif" border="0" width=22 height=20 ></td>
				<td><input name="Submit22" type="button" class="button" onClick="javascript:frmSubmit('job_card_view_for_contact_of_install.do?txtJobCardID=<%=jobCardId%>')" value="����ԤԼ"></td>
				<td><img src="img/button2_l.gif" border="0" width=11 height=20 ></td> 
                                <td width="20">&nbsp;&nbsp;</td>
  				<td><img src="img/button2_r.gif" border="0" width=22 height=20 ></td>
				<td><input name="Submit22" type="button" class="button" onClick="javascript:frmSubmit('ci_view_for_close.do?txtJobCardID=<%=jobCardId%>')" value="������װ����"></td>
				<td><img src="img/button2_l.gif" border="0" width=11 height=20 ></td> 
<%			  
	   	   }
	   	   else if((strIsOK!=null)&&(strIsOK.compareToIgnoreCase("true")==0))
	   	   {
%>	   	   
	   	   		<td><img src="img/button2_r.gif" width="22" height="20"></td>
       			<td background="img/button_bg.gif"><a href="<bk:backurl property="pi_query_result_for_register.do/job_card_view.do/job_card_query_result_for_contact_of_install.do" />" class="btn12">��&nbsp;��</a></td>           
       			<td><img src="img/button2_l.gif" width="11" height="20"></td>
       			<td width="20" ></td>
<%	   	   
	   	   }
	    break;
         
          case 103: //install_info_update 
          %>
                                <td width="20">&nbsp;&nbsp;</td>
  				<td><img src="img/button2_r.gif" border="0" width=22 height=20 ></td>
				<td><input name="Submit22" type="button" class="button" onClick="javascript:frmSubmit('ci_query_result_for_close.do?txtFrom=1&txtTo=10&txtStatus=F&txtType=I')" value="���ؽ�����װ������ѯ"></td>
				<td><img src="img/button2_l.gif" border="0" width=11 height=20 ></td> 
	<%			  
	    
          
          break; 
         case 104: //install_info_update 
          %>
                                <td width="20">&nbsp;&nbsp;</td>
  				<td><img src="img/button2_r.gif" border="0" width=22 height=20 ></td>
				<td><input name="Submit22" type="button" class="button" onClick="javascript:frmSubmit('catv_query_result_for_close.do?txtFrom=1&txtTo=10&txtStatus=F&txtType=C')" value="���ؽ���ʩ������ѯ"></td>
				<td><img src="img/button2_l.gif" border="0" width=11 height=20 ></td> 
	<%			  
	    
          
          break;
          case 105: //ʩ��¼����Ϣ
           
           strIsOK = request.getParameter("txtIsSuccess");
           
           if ((strIsOK!=null)&&(strIsOK.compareToIgnoreCase("false")==0))
           { 
             String jobCardId = request.getParameter("txtJobCardID");
             int jid = jobCardId==null?0:WebUtil.StringToInt(jobCardId);
%>
				
                                <td width="20">&nbsp;&nbsp;</td>
  				<td><img src="img/button2_r.gif" border="0" width=22 height=20 ></td>
				<td><input name="Submit22" type="button" class="button" onClick="javascript:frmSubmit('catv_jobcard_view_for_recontact_of_install.do?txtJobCardID=<%=jobCardId%>')" value="����ԤԼ"></td>
				<td><img src="img/button2_l.gif" border="0" width=11 height=20 ></td> 
				
                                <td width="20">&nbsp;&nbsp;</td>
  				<td><img src="img/button2_r.gif" border="0" width=22 height=20 ></td>
				<td><input name="Submit22" type="button" class="button" onClick="javascript:frmSubmit('catv_view_for_close.do?txtJobCardID=<%=jobCardId%>')" value="����ʩ����"></td>
				<td><img src="img/button2_l.gif" border="0" width=11 height=20 ></td> 
				
	<%			  
	   }
	    break;
         case 1008:  
           //�����ά��ʧ�ܣ����Խ������ά�޹�������������ά��ԤԼ
            String isOK = request.getParameter("isSuccess");
           
           if ((isOK!=null)&&(isOK.compareToIgnoreCase("false")==0))
           {
             String jobCardId = request.getParameter("txtJobCardID");
%>
                                   <td width="20">&nbsp;&nbsp;</td> 
  				<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
				<td><input name="Submit22" type="button" class="button" onClick="javascript:frmSubmit('recontactrep_view.do?txtJobCardID=<%=jobCardId%>')" value="����ԤԼ"></td>
				<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
                                <td width="20">&nbsp;&nbsp;</td>
				<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
				<td><input name="Submit22" type="button" class="button" onClick="javascript:frmSubmit('close_repair_info_view.do?txtJobCardID=<%=jobCardId%>')" value="����ά�޹���"></td>
				<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
           <%
           }   
            
           
           break;
           
	 case 1007: //���޵�ԤԼ
	   int iJobCardID = WebUtil.StringToInt(request.getParameter("txtJobCardID"));
	   String strJCStatus = Postern.getJobCardStatusByID(iJobCardID);
	   if (strJCStatus!=null)
    	   {
    	       if (strJCStatus.equals("B")) //��ԤԼ
               {
 	   boolean vflag = Postern.buttonCanBeVisibleForRepair(iJobCardID,CurrentLogonOperator.getOperator(request).getOrgID());
       	   pageContext.setAttribute("canBeVisible", vflag+"");
       	   pageContext.setAttribute("jcdtoforbutton",Postern.getJobCardDto(iJobCardID));
%>

				<o:displayControl id="button_customerproblem_query_detail_repair_register" bean="jcdtoforbutton,canBeVisible">
  				<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
  				 
				<td><input name="Submit22" type="button" class="button" onClick="javascript:frmSubmit('register_repair_info_view.do?txtJobCardID=<%=iJobCardID%>')" value="����¼��ά����Ϣ"></td>
				<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
				</o:displayControl>
<%
			}
		   }
	  break;
	 case 1009: //ά�޽���
	 	String jcId=request.getParameter("txtJobCardID");
	 	int jcID=0;
	 	if(jcId!=null)
	 		jcID = Integer.parseInt(jcId);
	 	int custProblemID = Postern.getCustProblemIDByJobcardID(jcID);	
	 	boolean vflag = Postern.buttonCanBeVisibleForCP(custProblemID,CurrentLogonOperator.getOperator(request).getOrgID());
         	pageContext.setAttribute("canBeVisible", vflag+"");
%>
			    <d:displayControl id="button_customerproblem_return_transfer" bean="canBeVisible">
  				<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>  				 
				<td><input name="Submit22" type="button" class="button" onClick="javascript:frmSubmit('sheetrep_view.do?txtCustomerProblemID=<%=custProblemID%>')" value="�����ֹ���ת���޵�"></td>
				<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
			    </d:displayControl>
<%
	  break;
	 case 11008: //�ų����񴴽� zhouxushun
	   String txtCustomerID =request.getParameter("txtCustomerID");
%>
  				<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
				<td><input name="Submit22" type="button" class="button" onClick="javascript:frmSubmit('<bk:backurl property='show_sacp_for_schedule.do' />')" value="�ų����񴴽�����"></td>
				<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
 
<%
          break;
         
         case 11009: //�ų�����ά��
          String customerID =request.getParameter("txtCustomerID");
  %>
  				<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
				<td><input name="Submit22" type="button" class="button" onClick="javascript:frmSubmit('<bk:backurl property='schedule_show.do?txtCustomerID=<%=customerID%>&txtFrom=1&txtTo=10' />')" value="�ų�����ά������"></td>
				<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
<%
          break; 
          
          case 6001:   //Ԥ����
            Integer returnCsiid=null;
            CommandResponseImp CmdRepforPreyPay = (CommandResponseImp)request.getAttribute("ResponseFromEJBEvent");    
            if (CmdRepforPreyPay!=null){
            try{
                returnCsiid = (Integer)CmdRepforPreyPay.getPayload();
            }catch (Exception ex){}
            }
           CustServiceInteractionDTO csiDto = Postern.getCsiDTOByCSIID(returnCsiid.intValue());
           String custID =request.getParameter("txtCustomerID");
           String accID =request.getParameter("txtAccountID");
           
           CustomerDTO custDto = new CustomerDTO();
		   if(custID!=null)
				custDto = Postern.getCustomerByID(Integer.parseInt(custID));
		   pageContext.setAttribute("customerDTO", (custDto==null) ? new CustomerDTO() : custDto);
		   pageContext.setAttribute("SYSTEMSYMBOLNAME", Postern.getSystemSymbolName());
           pageContext.setAttribute("oneline",csiDto);
           
%>
	            <td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
				<td><input name="Submit22" type="button" class="button" onClick="javascript:frmSubmit('account_view.do?txtCustomerID=<%=custID %>&txtAccountID=<%=accID %>')" value="���ص��ʻ���Ϣ"></td>
				<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
				<td width="20" >&nbsp;&nbsp;&nbsp</td>
				<%String systemSettingPrecise = Postern.getSystemSettingValue("SET_W_USER_PREYPAY_PRINT");
                if(systemSettingPrecise!=null&&("lijian").equalsIgnoreCase(systemSettingPrecise)){%>
				<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
				<td><input name="Submit22" type="button" class="button" onClick="javascript:customer_prepay_print('<%=custID%>','<%=returnCsiid%>')" value="�շѵ��ݴ�ӡ"></td>
				<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
				<td width="20" >&nbsp;&nbsp;&nbsp</td>
				<%}%>
				<pri:authorized name="service_interaction_view_for_print_billing_config.do" >
				<d:displayControl id="button_service_interaction_view_for_print_billing_config" bean="customerDTO,oneline,SYSTEMSYMBOLNAME"><!--Ԥ�� ����-->
				<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
				<td><input name="Submit22" type="button" class="button" onClick="javascript:config_print('<%=csiDto.getId()%>','<%=CommonKeys.SET_V_PRINTSHEETTYPE_S%>','<%=csiDto.getType()%>','<%=csiDto.getCreateReason()%>','<%=CommonKeys.SET_V_PRINTSHEETREASON_F%>')" value="�շѵ��ݴ�ӡ"></td>
				<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
				<td width="20" >&nbsp;&nbsp;&nbsp</td>
				</d:displayControl>
				</pri:authorized>
				
<%
          break; 
          case 6002:    
         
%>
	<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
	<td><input name="Submit22" type="button" class="button" onClick="javascript:frmSubmit('cp_query_for_manual_close.do?txtStatus=W')" value="���ص���ѯ"></td>
	<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
				
<%
          break;  
          
          case 8001:  //����������ѯ
        String templateFlag = request.getParameter("txtTemplateFlag");
          CommandResponse RepCmd = (CommandResponse)pageContext.getRequest().getAttribute("ResponseFromEJBEvent");
	  int queryID =0;
	  queryID= RepCmd.getPayload()!=null ? ((Integer)RepCmd.getPayload()).intValue() : 0;
	  System.out.println("the result aaaaa :" + queryID);
	  
	  if(!"Y".equals(request.getParameter("txtTemplateFlag"))) {
%>
	<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
	<td><input name="Submit22" type="button" class="button" onClick="javascript:frmSubmit('menu_batch_query_query.do')" value="����������ѯά��"></td>
	<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
	<td width="20" >&nbsp;&nbsp;&nbsp</td>
	<%
	}
	if(CommonConstDefinition.YESNOFLAG_YES.equals(templateFlag)){%>
	<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
	<td><input name="Submit22" type="button" class="button" onClick="javascript:frmSubmit('menu_batch_query_model_query.do?txtActionType=batch&txtTemplateFlag=<%=templateFlag%>')" value="�鿴�Ѵ���ģ��"></td>
	<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>	
	
	<%} else{%>
	<!--
	<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
	<td><input name="Submit22" type="button" class="button" onClick="javascript:frmSubmit('batch_query_query.do?txtActionType=batch&txtTemplateFlag=<%=templateFlag%>')" value="�鿴�Ѵ�����ѯ"></td>
	<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>	
	-->
	<td width="20" >&nbsp;&nbsp;&nbsp</td>
	<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
	<td><input name="Submit22" type="button" class="button" onClick="javascript:frmSubmit('batch_query_excute.do?txtActionType=excute&txtQueryIDs=<%=queryID%>')" value="ִ��������ѯ"></td>
	<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
	
	
	
<%}
          break;  
          
          case 8002:  //�޸ķ���
          String txt1QueryID="";
          txt1QueryID=request.getParameter("txtQueryID")==null ? "" : request.getParameter("txtQueryID");
%>
	<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
	<td><input name="Submit22" type="button" class="button" onClick="javascript:frmSubmit('<bk:backurl property='batch_query_show_detail.do' />')" value="���ص��޸�"></td>
	<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
	<!--
	<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
	<td><input name="Submit22" type="button" class="button" onClick="javascript:frmSubmit('menu_batch_query_query.do')" value="����������ѯά��"></td>
	<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
	-->
	<%if(CommonKeys.SCHEDULE_TYPE_I.equals(request.getParameter("txtScheduleType")) && (!"Y".equals(request.getParameter("txtTemplateFlag")))) {%>
		<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
		<td><input name="Submit22" type="button" class="button" onClick="javascript:frmSubmit('batch_query_excute.do?txtActionType=excute&txtQueryIDs=<%=txt1QueryID%>')" value="ִ��������ѯ"></td>
		<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
	<%}%>

<%

          break;  
          
          case 8003:  //ȡ������
                    
%>
	<bk:canback url="batch_query_query.do/batch_query_model_query.do">
	<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
	<td background="img/button_bg.gif"  height="20">
		<a href="<bk:backurl property="batch_query_query.do/batch_query_model_query.do"/>" class="btn12">&nbsp;����&nbsp;</a></td>
	<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
	</bk:canback>
<%
          break;  
          
          case 8004:  //ִ��������ѯ����
          String txtQueryID =request.getParameter("txtQueryIDs");
%>
	<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
	<td><input name="Submit22" type="button" class="button" onClick="javascript:frmSubmit('menu_batch_query_query.do')" value="����"></td>
	<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
	<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
	<td><input name="Submit22" type="button" class="button" onClick="javascript:frmSubmit('goto_batch_query_show_result.do?txtQueryID=<%=txtQueryID %>')" value="�鿴�����Ľ����"></td>
	<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>	
	
<%
          break;  
          
          case 8005:  //�޸ķ��ؼ�¼���
%>
	<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
	<td><input name="Submit22" type="button" class="button" onClick="javascript:frmSubmit('<bk:backurl property='batch_query_show_result.do' />')" value="����"></td>
	<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>	
	<%
          break;  
          
          case 1004:  //���ؼ�¼���
%>
	<td width="11" height="20"><img src="img/button2_r.gif" width="22" height="20"></td>
	<td><input name="Submit22" type="button" class="button" onClick="javascript:frmSubmit('<bk:backurl property='cp_query_for_callback.do/cp_query_detail.do'/>')" value="����"></td>
	<td width="22" height="20"><img src="img/button2_l.gif" width="11" height="20"></td>		
<%
	break;
	
	case 1101:  //�����������񵥣�һ��Ƿ������/����Ƿ������/��Ʒ��ͣ/������ͣ/����ȡ��/����������Ϣ��
	String txtJobType=request.getParameter("txtJobType")==null ? "" : request.getParameter("txtJobType");
%>
	<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
	<td><input name="Submit22" type="button" class="button" onClick="javascript:frmSubmit('<bk:backurl property='ownFeeBatch_query_result.do' />')" value="������������"></td>
	<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
	<td width="20" >&nbsp;&nbsp;&nbsp</td>
	<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
	<td><input name="Submit22" type="button" class="button" onClick="javascript:frmSubmit('batchJobStatus_query.do?txtBatchJobType=<%=txtJobType %>')" value="����������״̬��ѯ"></td>
	<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
<%	break;
	
	case 1102:  //�޸ġ�ȡ���������񵥣�һ��Ƿ������/����Ƿ������/��Ʒ��ͣ/������ͣ/����ȡ��/����������Ϣ��
%>
	<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
	<td><input name="Submit22" type="button" class="button" onClick="javascript:frmSubmit('<bk:backurl property='batchJob_query_result.do' />')" value="����ά������"></td>
	<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
	<td width="20" >&nbsp;&nbsp;&nbsp</td>
	<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
	<td><input name="Submit22" type="button" class="button" onClick="javascript:frmSubmit('batchJobStatus_query.do?')" value="��������״̬��ѯ"></td>
	<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
<%	break;
	
	case 5001:  //�޸Ļ��ֶһ��
%>
	<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
	<td><input name="Submit22" type="button" class="button" onClick="javascript:frmSubmit('<bk:backurl property='activity_query_result.do' />')" value="�� ��"></td>
	<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
	<td width="20" >&nbsp;&nbsp;&nbsp</td>
<%	break;
	
	case 5053:  //�޸Ļ��ֶһ��
	  txtActivityID = request.getParameter("txtActivityID");
%>
	<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
	<td><input name="Submit22" type="button" class="button" onClick="javascript:frmSubmit('query_points_goods.do?txtActivityID=<%=txtActivityID %>')" value="���ضһ���Ʒά��"></td>
	 
	<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
	<td width="20" >&nbsp;&nbsp;&nbsp</td>
	<%	
	break;
	
	case 1107:  // 
	  String id = request.getParameter("txtID");
%>
	 <td><img src="img/button2_r.gif" width="22" height="20"></td>
	<td><input name="Submit22" type="button" class="button" onClick="javascript:frmSubmit('group_bargain_query_detail.do?txtID=<%=id %>')" value="�� ��"></td>
	 
	 <td><img src="img/button2_l.gif" width="11" height="20"></td>
	<td width="20" >&nbsp;&nbsp;&nbsp</td>
	<%
	break;
	
	case 5054:  //�޸Ļ��ֶһ��
	  txtActivityID = request.getParameter("txtActivityID");
%>
	<td><img src="img/button2_r.gif" border="0" width=22 height=20 ></td>
	<td><input name="Submit22" type="button" class="button" onClick="javascript:frmSubmit('query_points_rule.do?txtActivityID=<%=txtActivityID %>')" value="�� ��"></td>
	 
	 <td><img src="img/button2_l.gif" border="0" width=11 height=20 ></td> 
	<td width="20" >&nbsp;&nbsp;&nbsp</td>
<%
	break;
	
	case 5059:  //�޸�������Ϣ
	 String  ID = request.getParameter("txtID");
%>
	<td><img src="img/button2_r.gif" border="0" width=22 height=20 ></td>
	<td><input name="Submit22" type="button" class="button" onClick="javascript:frmSubmit('config_query_result.do?txtID=<%=ID%>')" value="�� ��"></td>
	 
	 <td><img src="img/button2_l.gif" border="0" width=11 height=20 ></td> 
	<td width="20" >&nbsp;&nbsp;&nbsp</td>
	<%
	break;
	
	case 5060:  //�޸�����ֵ��Ϣ
	String  settingID = request.getParameter("txtSettingID");
%>
	<td><img src="img/button2_r.gif" border="0" width=22 height=20 ></td>
	<td><input name="Submit22" type="button" class="button" onClick="javascript:frmSubmit('query_config_item.do?txtSettingID=<%=settingID%>')" value="�� ��"></td>
	 
	 <td><img src="img/button2_l.gif" border="0" width=11 height=20 ></td> 
	<td width="20" >&nbsp;&nbsp;&nbsp</td>
	<%
	break;
	
	case 5071:  //�޸��г�����
	String  segmentID = request.getParameter("txtID"); 
%>
	<td><img src="img/button2_r.gif" border="0" width=22 height=20 ></td>
	<td><input name="Submit22" type="button" class="button" onClick="javascript:frmSubmit('config_market_segment_detail.do?txtID=<%=segmentID%>')" value="�� ��"></td>
	 
	 <td><img src="img/button2_l.gif" border="0" width=11 height=20 ></td> 
	<td width="20" >&nbsp;&nbsp;&nbsp</td>
<%	
         break;
	
	case 5074:  //�޸Ļ����ۼӹ���
	 
%>
	<td><img src="img/button2_r.gif" border="0" width=22 height=20 ></td>
	<td><input name="Submit22" type="button" class="button" onClick="javascript:frmSubmit('query_points_acumulate_rule.do')" value="�� ��"></td>
	 
	<td><img src="img/button2_l.gif" border="0" width=11 height=20 ></td> 
	<td width="20" >&nbsp;&nbsp;&nbsp</td>	 
	<%	
         break;
	
	case 5075:  //�޸Ļ����ۼӹ���
	 
%>
	<td><img src="img/button2_r.gif" border="0" width=22 height=20 ></td>
	<td><input name="Submit22" type="button" class="button" onClick="javascript:frmSubmit('package_query_result.do?txtFrom=0&txtTo=10')" value="���ز�Ʒ����ѯ"></td>
	 
	<td><img src="img/button2_l.gif" border="0" width=11 height=20 ></td> 
	<td width="20" >&nbsp;&nbsp;&nbsp</td>	
	<%	
         break;
	
	case 5089:  //����packageLine
	String  packageID = request.getParameter("txtPackageID");
	 
%>
	<td><img src="img/button2_r.gif" border="0" width=22 height=20 ></td>
	<td><input name="Submit22" type="button" class="button" onClick="javascript:frmSubmit('product_package_detail.do?txtPackageID=<%=packageID%>')" value="���ز�Ʒ����ϸ"></td>
	 
	<td><img src="img/button2_l.gif" border="0" width=11 height=20 ></td> 
	<td width="20" >&nbsp;&nbsp;&nbsp</td>	
	 	
	  		 	
	 
	
<%	break;
	case 999:  //����packageLine
	 
%>
	<tbl:hiddenparameters pass="txtServiceCode/txtServiceAccountID/txtCustomerID/txtAccount/" /> 
	<td><img src="img/button2_r.gif" border="0" width=22 height=20 ></td>
	<td><input name="Submit22" type="button" class="button" onClick="javascript:frmSubmit('service_account_query_result_by_sa.do')" value="����ҵ���ʻ���Ϣ"></td>
	 
	<td><img src="img/button2_l.gif" border="0" width=11 height=20 ></td> 
	<td width="20" >&nbsp;&nbsp;&nbsp</td>	 	
<%	break;
        case 2001: //�޸�����
%>
        <td><img src="img/button2_r.gif" border="0" width=22 height=20 ></td>
	<td><input name="Submit22" type="button" class="button" onClick="javascript:frmSubmit('change_pwd.do')" value="��  ��"></td>
	 
	<td><img src="img/button2_l.gif" border="0" width=11 height=20 ></td> 
	<td width="20" >&nbsp;&nbsp;&nbsp</td>
<%
        break;  
	
	case 3001:
        CommandResponseImp CmdRep = (CommandResponseImp)request.getAttribute("ResponseFromEJBEvent");  
        int cust_ID =0;  
        int iAdditionalID =0;
        if (CmdRep!=null)
        {
           try
           {	
               Map map = (Map)CmdRep.getPayload();
               cust_ID = ((Integer)map.get("CustomerID")).intValue();
               iAdditionalID = ((Integer)map.get("CustServiceInteractionID")).intValue();
           }
           catch (Exception ex)
           {
               System.out.println("ex======"+ex);     
           }
%>
        <input type="hidden" name="txtCustomerID" value="">
        <input type="hidden" name="txtCsiID"   value="">
	<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
	<td><input name="Submit22" type="button" class="button" onClick="javascript:batchUpdateAcctSubmit(<%=cust_ID%>,<%=iAdditionalID%>)" value="���������˻�"></td>
	<input type="hidden" name="txtAccountStatus" value="N;O;T">
	<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
	<td width="20" >&nbsp;&nbsp;&nbsp</td>
<%
        }
        break;  
        
	    case 116:
%>
			<table align="center" border="0" cellspacing="0" cellpadding="0">
			        <tr>
			
			            <td><img src="img/button2_r.gif" width="22" height="20"></td> 
			            <td background="img/button_bg.gif"><a href="<bk:backurl property="query_book_account.do" />" class="btn12">��&nbsp;��</a></td>
			            <td><img src="img/button2_l.gif" width="13" height="20"></td>
			        </tr>
			</table>
<%
    	break;
	    case 117:
	    	%>
	    	<table align="center" border="0" cellspacing="0" cellpadding="0">
  				        <tr>
  				
  				            <td><img src="img/button2_r.gif" width="22" height="20"></td> 
  				            <td background="img/button_bg.gif"><a href="javascript:returnBookAccountQuery('<%=custId%>','<%=txtCsiType%>')" class="btn12">��&nbsp;��</a></td>
  				            <td><img src="img/button2_l.gif" width="13" height="20"></td>
  				        </tr>
	    	</table>
   	<%
   	    	break;
	    case 118:
	    	
	    	%>
	    	<table align="center" border="0" cellspacing="0" cellpadding="0">
  				        <tr>
  				
  				            <td><img src="img/button2_r.gif" width="22" height="20"></td> 
  				            <td background="img/button_bg.gif"><a href="javascript:queryFriendPhoneNO('<%=request.getParameter("txtServiceAccountID")%>','<%=request.getParameter("txtCustomerID")%>')" class="btn12">��&nbsp;��</a></td>
  				            <td><img src="img/button2_l.gif" width="13" height="20"></td>
  				        </tr>
	    	</table>
   	<%
   	    	break;
   	    	case 119:
	    	
	    	%>
	    	<table align="center" border="0" cellspacing="0" cellpadding="0">
  				        <tr>  				
  				            <td><img src="img/button2_r.gif" width="22" height="20"></td> 
  				            <td background="img/button_bg.gif"><a href="javascript:bookProduct('<%=request.getParameter("txtServiceAccountID")%>','<%=request.getParameter("txtCustomerID")%>')" class="btn12">��&nbsp;��</a></td>
  				            <td><img src="img/button2_l.gif" width="13" height="20"></td>
  				        </tr>
	    	</table>
   	<%
   	    	break;
   	    	
   	    	
   	    	
   	    	         	case 4002:  
	 
%>
	<td><img src="img/button2_r.gif" border="0" width=22 height=20 ></td>
	<td><input name="Submit22" type="button" class="button" onClick="javascript:frmSubmit('ippv_query.do')" value="�� ��"></td>
	 
	<td><img src="img/button2_l.gif" border="0" width=11 height=20 ></td> 
	<td width="20" >&nbsp;&nbsp;&nbsp</td>	 
	<%	
         break;
         
                  	case 4080:  
	 
%>
	<td><img src="img/button2_r.gif" border="0" width=22 height=20 ></td>
	<td><input name="Submit22" type="button" class="button" onClick="javascript:frmSubmit('menu_dtv_migration.do')" value="�� ��"></td>
	 
	<td><img src="img/button2_l.gif" border="0" width=11 height=20 ></td> 
	<td width="20" >&nbsp;&nbsp;&nbsp</td>	 
	<%	
         break;         
         
   	    	case 121:
	    	
	    	%>
	    	<table align="center" border="0" cellspacing="0" cellpadding="0">
  				        <tr>  				
  				            <td><img src="img/button2_r.gif" width="22" height="20"></td> 
  				            <td background="img/button_bg.gif"><a href="<bk:backurl property="query_book_product.do" />" class="btn12">��&nbsp;��</a></td>
  				            <td><img src="img/button2_l.gif" width="13" height="20"></td>
  				        </tr>
	    	</table>
   	<%
   	    	break;
 		case 210:
	    	
	    	%>
	    	<table align="center" border="0" cellspacing="0" cellpadding="0">
  				        <tr>
  				
  				            <td><img src="img/button2_r.gif" width="22" height="20"></td> 
  				            <td background="img/button_bg.gif"><a href="javascript:cust_camplain_query('<%=request.getParameter("actiontype")%>')" class="btn12">��&nbsp;��</a></td>
  				            <td><img src="img/button2_l.gif" width="13" height="20"></td>
  				        </tr>
	    	</table>
   	<%
   	    	break;
  case 7001: //��Ʒ��ͣ
  case 7002: //��Ʒ�˶�
  case 7003: //��Ʒ�ָ�
           strBackUrl = "service_account_query_result_by_sa.do";
           strBtnName = "����ҵ���ʻ���Ϣ";
       %>
   <bk:canback url="<%=strBackUrl%>">
    <td width="20">&nbsp;&nbsp;</td> 
    <td><img src="img/button2_r.gif" width="22" height="20"></td>
             <td background="img/button_bg.gif"  ><a href="<bk:backurl property='<%=strBackUrl%>' />" class="btn12"><%=strBtnName%></a></td>           
             <td><img src="img/button2_l.gif" width="11" height="20"></td>
              <td width="20" >&nbsp;&nbsp;&nbsp</td>
   </bk:canback>
       <%
           break;
   					
    case 11020: //���Ի������޸� 
%>
  				<td><img src="img/button2_r.gif" border="0" width=22 height=20 ></td>
				<td><input name="Submit22" type="button" class="button" onClick="javascript:frmSubmit('<bk:backurl property='customer_billing_rule_query.do/customer_product_view.do' />')" value="����"></td>
				<td><img src="img/button2_l.gif" border="0" width=11 height=20 ></td>
<%
          break;    
          
          case 11021: //���Ի��������� 
%>
  				<td><img src="img/button2_r.gif" border="0" width=22 height=20 ></td>
				<td><input name="Submit22" type="button" class="button" onClick="javascript:frmSubmit('<bk:backurl property='customer_billing_rule_query.do/customer_product_view.do' />')" value="����"></td>
				<td><img src="img/button2_l.gif" border="0" width=11 height=20 ></td>
<%
          break;  	
          
          case 11024: //����ģ���ն�ά��ҳ��
%>
  				<td><img src="img/button2_r.gif" border="0" width=22 height=20 ></td>
				<td><input name="Submit22" type="button" class="button" onClick="javascript:frmSubmit('catv_terminal_update.screen?id=<%=request.getParameter("txtID")%>')" value="����"></td>
				<td><img src="img/button2_l.gif" border="0" width=11 height=20 ></td>
<%
          break;
          
          case 11025: //����ģ���ն�¼��ҳ��
%>
  				<td><img src="img/button2_r.gif" border="0" width=22 height=20 ></td>
				<td><input name="Submit22" type="button" class="button" onClick="javascript:frmSubmit('catv_terminal_create.screen')" value="����"></td>
				<td><img src="img/button2_l.gif" border="0" width=11 height=20 ></td>
<%
          break;
          case 11026: //����СǮ������ҳ��
%>
  				<td><img src="img/button2_r.gif" border="0" width=22 height=20 ></td>
				<td><input name="Submit22" type="button" class="button" onClick="javascript:frmSubmit('<bk:backurl property='ca_wallet_query.do/ca_wallet_service_interaction_query.do'/>')" value="����"></td>
				<td><img src="img/button2_l.gif" border="0" width=11 height=20 ></td>
<%
          break;
          			
    } 
   	%> 

</lgc:hasnoterror>
 
        </tr>
      </table> 
        </td>
          <td width="20%">&nbsp;</td>
        </tr>
      </table>

<Script language=JavaScript>      
      function config_print(csiId,PrintSheetType,SheetSubType,CsiReason,PrintReason)
	{
		
		document.frmPost.target="_blank";
		document.frmPost.action="config_print.do?txtCsiId="+csiId+"&txtPrintSheetType="+PrintSheetType+"&txtSheetSubType="+SheetSubType+"&txtCsiReason="+CsiReason+"&txtPrintReason="+PrintReason;
		document.frmPost.submit();
		document.frmPost.target="_self";
		
	}
</Script>	      