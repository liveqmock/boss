<form name="frmPost" method="post" >
</form>  
<%
  String  txtCustomerID =request.getParameter("txtCustomerID");
  if (txtCustomerID ==null){
%>
<Script language=Javascript>
   alert("开工单的参数传递有误，请与管理员联系");
   window.close();
</Script>  
<%
   }else{
%>
<Script language=Javascript>
   document.frmPost.action = 'query_customer_for_create_jobcard.do?txtCustomerID=<%=txtCustomerID%>';
   document.frmPost.submit();
</Script>
<%
   }
%>
