<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="bookmark" prefix="bk" %>
<script language="JavaScript" type="text/JavaScript">
<!--
function frmSubmit(url){
		document.frmPost.action = url;
    document.frmPost.submit();
}
//-->
</script>
<%
       String sFlag=request.getParameter("func_flag");
       System.out.println("sFlag====="+sFlag);
       int iFlag=0;
       
       if ((sFlag!=null)&&(sFlag.compareTo("")!=0)){ 
           try
           {
               iFlag=Integer.valueOf(sFlag).intValue();
           }
           catch (Exception ex)
           {
           }
       } 
%>
<TABLE border="0" align="center" cellspacing="0" cellpadding="0" width="816">
<TR>
	<TD align="center">
    <table width="50%" border="0" cellspacing="10" cellpadding="0">
        <tr align="center">
          <td width="182"><img src="img/icon_error.gif" width="182" height="182"></td>
          <td class="ok">
          <font color="red"><i>操作不成功，错误信息如下:</i></font>
          <br><tbl:errmsg />
			    </td>
        </tr>
    </table>
  </td>
</tr>
</table>  
<form name="frmPost" method="post">
<table width="50%"  border="0" align="center" cellpadding="0" cellspacing="10">
   <tr align="center">
   	 <td height="37">
	  	  <table  border="0" cellspacing="0" cellpadding="0">
				  <tr>
<%
       switch (iFlag){
         case 5002: //返回到业务帐户
%>
           <td><img src="img/button2_r.gif" width="22" height="20"></td>
					 <td><input name="Submit22" type="button" class="button" onClick="javascript:frmSubmit('<bk:backurl property='service_account_query_result_by_sa.do' />')" value="返回"></td>
					 <td><img src="img/button2_l.gif" width="11" height="20"></td>
<%
           break;
         case 2:   //返回到业务帐户暂停/销户/过户
%>                                                      
         <bk:canback url="service_account_pause_view.do/service_account_transfer_view.do/service_account_close_view.do/catv_service_account_op_view.do" >
           <td><img src="img/button2_r.gif" width="22" height="20"></td>
					 <td><input name="Submit22" type="button" class="button" onClick="javascript:frmSubmit('<bk:backurl property='service_account_pause_view.do/service_account_transfer_view.do/service_account_close_view.do/catv_service_account_op_view.do' />')" value="返回"></td>
					 <td><img src="img/button2_l.gif" width="11" height="20"></td>
				 </bk:canback> 
<%
           break;
           
         case 102: //返回录入安装反馈信息
%>
          <bk:canback url="job_card_view_for_register_of_install.do" >
            <td><img src="img/button2_r.gif" width="22" height="20"></td>
					  <td><input name="Submit22" type="button" class="button" onClick="javascript:frmSubmit('<bk:backurl property='job_card_view_for_register_of_install.do' />')" value="返回"></td>
					  <td><img src="img/button2_l.gif" width="11" height="20"></td>
          </bk:canback>
<%
           break;
         default:
           break;
      }
%>
          </tr>
        </table>
     </td>
  </tr>
</table>
</form>