<form name="frmPost" method="post" >
</form>  
<%
  String  txtCustomerID =request.getParameter("txtCustomerID");
  if (txtCustomerID ==null){
%>
<Script language=Javascript>
   alert("�������Ĳ������������������Ա��ϵ");
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
