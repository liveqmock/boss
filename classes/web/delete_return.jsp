<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="bookmark" prefix="bk" %>

<BR>
<%
    String sFlag=request.getParameter("func_flag");
    int iFlag=0;
    String sName="";
       
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
       
    switch (iFlag)
    {
      case 1: //customer account
          sName="�ʻ���Ϣ";
          break;
      case 3: //customer
          sName="�ͻ�";
          break;
      case 502: //customer product
          sName="�ͻ���Ʒ��Ϣ";
          break;     
      case 503:
         sName="�ͻ�����";
         break;
      
    }
       
%>
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
           <font color="red"><i><i>ɾ��<%=sName%>���ɹ���������Ϣ����:</i></font>
            <br><tbl:errmsg />
	  </td>
        </tr>
      </table>
</lgc:haserror>   

<lgc:hasnoterror>
<%
   String showStr = "";
   switch (iFlag)
      {
        case 3: //customer
          showStr = "�ͻ����������ɹ���";
          break;
        case 4: //customer
          showStr = "�ͻ��˻������ɹ���";
          break;      
       default:
          showStr = "ɾ��"+sName+"�ɹ���";
          break;
    }
%>
    <table width="50%" border="0" cellspacing="10" cellpadding="0">
        <tr align="center">
          <td width="182"><img src="img/icon_ok.gif" width="182" height="182"></td>
          <td class="ok">
            <%=showStr%>
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
<Script language=javascript>       
function customer_view(customerID){
	self.location.herf="customer_view.do?txtCustomerID="+customerID;
}
function cancle_customer_view(customerID){
	self.location.herf="customer_view.do?txtCustomerID="+customerID+"&txtStatus=C";
}


</SCRIPT>
<form name="frmPost" method="post">
<lgc:haserror>
<% 
       switch (iFlag)
       {
         case 1: //account
%>
         <table align="center" border="0" cellspacing="0" cellpadding="0">
        <tr>
           <td><img src="img/button_l.gif" border="0" ></td>
           <td background="img/button_bg.gif"  ><a href="<bk:backurl property="account_view.do" />" class="btn12">���ؿͻ��ʻ�����</a></td>           
           <td><img src="img/button_r.gif" border="0" ></td>

        </tr>
      </table>
<%
         break;
         case 3: //customer
%>
       <table align="center" border="0" cellspacing="0" cellpadding="0">
        <tr>  
            <td><img src="img/button_l.gif" border="0" ></td>
            <td background="img/button_bg.gif"><a href="javascript:customer_view('<%=request.getParameter("txtCustomerID") %>')" />" class="btn12">���ؿͻ�����</a></td>           
            <td><img src="img/button_r.gif" border="0" ></td>   
           
        </tr>
      </table>     
<%         
           break;               
         case 502: //customer product
         
%>
       
      <table align="center" border="0" cellspacing="0" cellpadding="0">
        <tr>  
            
           <td><img src="images/button/button_l.gif" border="0" ></td>
            <td background="images/button/button_m.gif"  ><a href="<bk:backurl property="customer_product_view.do" />" class="btn12">���ز鿴��Ʒ</a></td>           
           <td><img src="images/button/button_r.gif" border="0" ></td>
        </tr>
      </table>     
<%
          break;
       }     
%>
</lgc:haserror>

<lgc:hasnoterror>
<%
        switch (iFlag)
       {
         case 1: //customer account
         case 3: //customer close
         case 4: //customer withdraw
         
          break;
         case 502: //customer product
         
%>
       
      <table align="center" border="0" cellspacing="0" cellpadding="0">
        <tr>  
            
           <td><img src="images/button/button_l.gif" border="0" ></td>
            <td background="images/button/button_m.gif"  ><a href="<bk:backurl property="customer_product_query_result_by_sa.do" />" class="btn12">���ز�Ʒ�б�</a></td>           
           <td><img src="images/button/button_r.gif" border="0" ></td>
        </tr>
      </table>     
<%
          break;  
       }
%>
</lgc:hasnoterror>
     </form>
     </td>
   </tr>
 </table>  
  
  	
      