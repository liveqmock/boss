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
       System.out.println("sFlag====="+sFlag);
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
         case 101: //��Ʒ���
%> 
		<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
		<td><input name="Submit22" type="button" class="button" onClick="javascript:frmSubmit('<bk:backurl property='product_manage_create.do' />')" value="����"></td>
		<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
		<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
		<td><input name="Submit22" type="button" class="button" onClick="javascript:frmSubmit('menu_product_manage_query.do')" value="���ص���Ʒ��ѯ"></td>
		<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
<%
          break;
         case 102: //��Ʒ�޸�
%>
		<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
		<td><input name="Submit22" type="button" class="button" onClick="javascript:frmSubmit('<bk:backurl property='product_manage_view.do' />')" value="����"></td>
		<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
		<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
		<td><input name="Submit22" type="button" class="button" onClick="javascript:frmSubmit('menu_product_manage_query.do')" value="���ص���Ʒ��ѯ"></td>
		<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
          
<%
          break;
         case 103: //��Ʒ���Բ���
%>
		<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
		<td><input name="Submit22" type="button" class="button" onClick="javascript:frmSubmit('<bk:backurl property='product_property_create.do' />')" value="����"></td>
		<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
		
		<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
		<td><input name="Submit22" type="button" class="button" onClick="javascript:frmSubmit('product_property_query.do?txtProductID=<tbl:writeparam name="txtProductID"/>')" value="���ص���Ʒ���Բ�ѯ"></td>
		<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
				
		<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
		<td><input name="Submit22" type="button" class="button" onClick="javascript:frmSubmit('product_manage_query.do')" value="���ص���Ʒ��ѯ"></td>
		<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
<%
          break;

	  case 104: //��Ʒ��ϵ����
%>
		<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
		<td><input name="Submit22" type="button" class="button" onClick="javascript:frmSubmit('<bk:backurl property='product_relation_create.do/product_relation_view.do' />')" value="����"></td>
		<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
		
 		<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
		<td><input name="Submit22" type="button" class="button" onClick="javascript:frmSubmit('menu_product_relation_query.do?txtProductID=<tbl:writeparam name="txtProductID"/>')" value="���ص���Ʒ��ϵ��ѯ"></td>
		<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
				
		<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
		<td><input name="Submit22" type="button" class="button" onClick="javascript:frmSubmit('menu_product_manage_query.do')" value="���ص���Ʒ��ѯ"></td>
		<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
	<%	
		   break;
                  case 227:  
                  String condId = request.getParameter("txtCondID");
         
%>
		 
	 <td><img src="img/button2_r.gif" width="22" height="20"></td>
   	<td background="img/button_bg.gif"><a href="ldap_cond_detail.do?txtCondID=<%=condId%>" class="btn12">�� ��</a></td>
	<td><img src="img/button2_l.gif" width="11" height="20"></td>
		 
          
<%
          break;
     }
          
%>
</lgc:haserror>

<%
   //-------û��Ҫ��ʾ�İ�ť-------
%>
<lgc:hasnoterror>
<%
 int iReferSheetID = 0;
       switch (iFlag)
       {
        
         case 101: 
       
%>
  	<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
	<td><input name="Submit22" type="button" class="button" onClick="javascript:frmSubmit('product_manage_query.do?txtFrom=1&txtTo=10')" value="���ص���Ʒ��ѯ"></td>
	<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
 
<%

          break; 
          case 102:  
           
%>
  	<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
	<td><input name="Submit22" type="button" class="button" onClick="javascript:frmSubmit('product_manage_query.do?txtFrom=1&txtTo=10')" value="���ص���Ʒ��ѯ"></td>
	<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>	
            
<%

         break;
           
	 case 103: //��Ʒ���Բ���
	   
%>
  	<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
	<td><input name="Submit22" type="button" class="button" onClick="javascript:frmSubmit('product_property_query.do?txtProductID=<tbl:writeparam name="txtProductID"/>')" value="���ص���Ʒ���Բ�ѯ"></td>
	<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
				
	<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
	<td><input name="Submit22" type="button" class="button" onClick="javascript:frmSubmit('menu_product_manage_query.do')" value="���ص���Ʒ��ѯ"></td>
	<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>			
<%
	  break;
	 
	 case 104: //��Ʒ��ϵ����
%>
  	<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
	<td><input name="Submit22" type="button" class="button" onClick="javascript:frmSubmit('menu_product_relation_query.do?txtProductID=<tbl:writeparam name="txtProductID"/>')" value="���ص���Ʒ��ϵ��ѯ"></td>
	<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
				
	<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
	<td><input name="Submit22" type="button" class="button" onClick="javascript:frmSubmit('menu_product_manage_query.do')" value="���ص���Ʒ��ѯ"></td>
	<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
	<%
	  break;
	 
	 case 105: //�ֿ�ȯ����
%>
  	<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
	<td><input name="Submit22" type="button" class="button" onClick="javascript:frmSubmit('menu_payment_type.do')" value="���ص��ֿ�ȯ���Ͳ�ѯ"></td>
	<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
	<%
	  break;
	 case 106: //ɾ����Ʒ����
%>
	<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
	<td><input name="Submit22" type="button" class="button" onClick="javascript:frmSubmit('menu_product_manage_query.do')" value="���ص���Ʒ��ѯ"></td>
	<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
	<%
	  break;
	 case 107: //��֯��ɫ
%>
	<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
	<td><input name="Submit22" type="button" class="button" onClick="javascript:frmSubmit('role_organization_query.do?txtFrom=1&txtTo=10')" value="���ز�ѯ"></td>
	<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
<%
	 break;
	 
	 case 110: //֧������
%>
  	<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
	<td><input name="Submit22" type="button" class="button" onClick="javascript:frmSubmit('brcondition_query.do')" value="���ص��Ʒ�������ѯ"></td>
	<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
	<%
	  break;
	 
	 case 111: //֧������
%>
  	<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
	<td><input name="Submit22" type="button" class="button" onClick="javascript:frmSubmit('brcondition_query.do')" value="���ص��Ʒ�������ѯ"></td>
	<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
				
<%
	  break;
	 
	 case 115: //֧����ʽ
%>
  	<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
	<td><input name="Submit22" type="button" class="button" onClick="javascript:frmSubmit('method_of_payment_query.do')" value="���ص����ѷ�ʽ�б�"></td>
	<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
<%
	  break;
	 
	 case 130: //֧������
%>
  	<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
	<td><input name="Submit22" type="button" class="button" onClick="javascript:frmSubmit('billing_rule_query.do?txtFrom=0&txtTo=10')" value="���ص�֧�������ѯ"></td>
	<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
								
	<%
	  break;
	 
	 case 132: //ϵͳ����
%>
  	<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
	<td><input name="Submit22" type="button" class="button" onClick="javascript:frmSubmit('boss_config.screen')" value="���ص�����ҳ��"></td>
	<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
<%
	  break;
	 
	 case 178: //ϵͳ����
%>
  	<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
	<td><input name="Submit22" type="button" class="button" onClick="javascript:frmSubmit('menu_opgroup_query.do')" value="���ص���ѯҳ��"></td>
	<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>																
<%
	  break;
	 
	 case 197: //ϵͳ����
	 String txtOrgID = request.getParameter("txtOrgID");
%>
        <td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
   	<td background="img/button_bg.gif"><a href="operator_query.do?txtOrgID=<%=txtOrgID%>" class="btn12">����</a></td>
	<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>  
	<%
	  break;
	 
	 case 200: //�г�����
	 
%>
        <td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
	<td><input name="Submit22" type="button" class="button" onClick="javascript:frmSubmit('menu_market_segment.do')" value="���ص���ѯҳ��"></td>
	<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>	
	
	<%
	  break;
	 
	 case 201: //�г�����
	 
%>
        <td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
	<td><input name="Submit22" type="button" class="button" onClick="javascript:frmSubmit('menu_campaign_query.do')" value="���ص���ѯҳ��"></td>
	<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>	
        	 
	<%
	  break;
	 
	 case 202: //�г�����
	 
%>

    <td><img src="img/button2_r.gif" width="22" height="20"></td>
			<td><input name="Submit" type="button" class="button"
					value="��&nbsp;��" onclick="javascript:frmSubmit('billing_rule_query.do?txtFrom=1&txtTo=10')"></td>
            <td><img src="img/button2_l.gif" width="11" height="20"></td>
            
            
         
	<%
	  break;
	 
	 case 245:  
	 
%>
        <td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
	<td><input name="Submit22" type="button" class="button" onClick="javascript:frmSubmit('menu_ldap_log_query.do')" value="���ص���ѯҳ��"></td>
	<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>	
        	  											
        	  													  		
<%
	  break;
	 
	 case 225:  
	 
%>
        <td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
	<td><input name="Submit22" type="button" class="button" onClick="javascript:frmSubmit('ldap_host_query.do')" value="���ص���ѯҳ��"></td>
	<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
	<%
	  break;
	 
	 case 226:  
	 
%>
        <td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
	<td><input name="Submit22" type="button" class="button" onClick="javascript:frmSubmit('ldap_product_query.do')" value="���ص���ѯҳ��"></td>
	<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>	
	<%
	  break;
	 
	 case 227:  
	 
%>
        <td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
	<td><input name="Submit22" type="button" class="button" onClick="javascript:frmSubmit('ldap_cond_query.do')" value="���ص���ѯҳ��"></td>
	<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>		
   <%
	  break;
	 
	 case 228:  
	 
%>
        <td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
	<td><input name="Submit22" type="button" class="button" onClick="javascript:frmSubmit('ldap_eventcmdmap_query.do')" value="���ص���ѯҳ��"></td>
	<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
		 	
   <%
	  break;
	 
	 case 230:  
	 
%>
        <td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
	<td><input name="Submit22" type="button" class="button" onClick="javascript:frmSubmit('ldap_prod_to_smsprod.do')" value="���ص���ѯҳ��"></td>
	<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
	 <%
	  break;
	 
	 case 232:  
	 
%>
        <td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
	<td><input name="Submit22" type="button" class="button" onClick="javascript:frmSubmit('ldap_attr_query.do')" value="���ص���ѯҳ��"></td>
	<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
	 <%
	  break;
	 
	 case 255:  
	 
%>
        <td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
	<td><input name="Submit22" type="button" class="button" onClick="javascript:frmSubmit('system_setting_query.do')" value="���ص���ѯҳ��"></td>
	<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
	 <%
	  break;
	 
	 case 800:  
	 
%>
        <td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
	<td><input name="Submit22" type="button" class="button" onClick="javascript:frmSubmit('bill_board_query_config.do')" value="���ص���ѯҳ��"></td>
	<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>	  	  	     	  																		
 <%
	  break;
	 
	 case 1002:  
	 
%>
        <td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
	<td><input name="Submit22" type="button" class="button" onClick="javascript:frmSubmit('customer_support_enter.do')" value="���ص���ʼҳ��"></td>
	<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>	 
 <%
	  break;
	 
	 case 2001:  
	 
%>
        <td><img src="img/button2_r.gif" border="0" width=22 height=20 ></td>
	<td><input name="Submit22" type="button" class="button" onClick="javascript:frmSubmit('voip_product_list.do?txtQueryType=P&txtFrom=1&txtTo=10')" value="�� ��"></td>
	<td><img src="img/button2_l.gif" border="0" width=11 height=20 ></td>		
 <%
	  break;
	 
	 case 2002:  
	 
%>
       <td><img src="img/button2_r.gif" border="0" width=22 height=20 ></td>
	<td><input name="Submit22" type="button" class="button" onClick="javascript:frmSubmit('voip_condition_list.do?txtQueryType=C&txtFrom=1&txtTo=10')" value="�� ��"></td>
	<td><img src="img/button2_l.gif" border="0" width=11 height=20 ></td>		
<%
	  break;
	 
	 case 2003:  
	 
%>
      <td><img src="img/button2_r.gif" border="0" width=22 height=20 ></td>
	<td><input name="Submit22" type="button" class="button" onClick="javascript:frmSubmit('voip_gateway_list.do?txtQueryType=G&txtFrom=1&txtTo=10')" value="�� ��"></td>
	<td><img src="img/button2_l.gif" border="0" width=11 height=20 ></td>			
<%
          break; 
      }      				
%>

</lgc:hasnoterror>

<%
   //-------�д�û��Ҫ��ʾ�İ�ť-------
%>
<%
       //ͨ���Ƿ���
       String strBackUrl = "";
       String strBtnName = "���ز�ѯ���";
       switch (iFlag)
       {
        
         case 110: //cancel booking
           strBackUrl = "service_interaction_query_result_for_booking.do";
           break;
         case 120: //cancel customer problem
           strBackUrl = "cp_query_detail.do";
           strBtnName = "������ر��޵�";
           break;
          case 201: //customer move �ͻ�Ǩ��
           strBackUrl = "customer_view.do?txtCustomerID="+request.getParameter("txtCustomerID");
           strBtnName = "���ؿͻ�����";
           break;
      
       }
       
       if (!strBackUrl.equals(""))
       {
       System.out.println("aaaa " + strBackUrl);
%>
<bk:canback url="<%=strBackUrl%>" >
  				<td width="11" height="20"><img src="img/button2_r.gif" width="11" height="20"></td>
				<td><input name="Submit22" type="button" class="button" onClick="javascript:frmSubmit('<bk:backurl property='<%=strBackUrl%>' />')" value="<%=strBtnName%>"></td>
				<td width="22" height="20"><img src="img/button2_l.gif" width="22" height="20"></td>
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