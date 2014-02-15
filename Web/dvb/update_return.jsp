<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="bookmark" prefix="bk" %>

<BR>
<%
       String sFlag = request.getParameter("func_flag");
       int iFlag = 0;
       String sName = "";//修改的用例名称
	   String url = "";//返回的地址
	   String btnValue= "返 回";//返回按扭上显示的文字
       
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
             sName = "帐户信息";
			 url = "account_view.do";
             break;
         
         case 3: //customer
             sName="客户基本信息";
			       url = "customer_view.do";
             break;
         case 4: //customer
             sName="积分累加信息";
			       url = "cumulated_detail.do";
             break;   
         case 5:
             sName="大客户基本信息";
			       url = "groupcustomer_view.do";
             break;      
         case 51: //opening for booking
             sName="预约开户受理单";
			       url = "service_interaction_view.do";
             break;
         case 30:  //group bargain
               sName="团购明细";
			         url = "group_bargain_query_result.do/group_bargain_query.do";
               break;
         case 110:  //booking
               sName="预约受理单";
			         url = "agent_csi_query.do/service_interaction_query_result.do/service_interaction_view.do";
               break;
         case 114:  //custAdditionalInfo
               sName="客户附加信息";
			         url = "";
               break;
         case 115:  //agent
               sName="代理商预约";
			         url = "agent_csi_query_result.do";
               break;
         case 250: //withdraw forced deposit
               sName = "退还押金";
			         url = "cust_forced_deposit_query_result.do";
               break;
        case 1120:  //
               sName="修改用户类型";
			         url = "customer_view.do";
               break; 
               
        case 2001:       //voipproductadd，voipproductmodify
        	sName="语音产品";
        	url="voip_product_list.do?txtQueryType=P&txtFrom=1&txtTo=10";
        	break;
        case 2002:       //voipconditionadd，voipconditionmodify
        	sName="语音条件";
        	url="voip_condition_list.do?txtQueryType=C&txtFrom=1&txtTo=10";
        	break;
        case 2003:      //voipgatewayadd，voipgatewaymodify
        	sName="网关配置";
        	url="voip_gateway_list.do?txtQueryType=G&txtFrom=1&txtTo=10";
        	break;   
        case 2005:      //voipgatewayadd，voipgatewaymodify
        	sName="设备信息";
        	url="dev_detail.do";
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
        <font color="red"><i>修改<%=sName%>不成功，错误信息如下:</i></font>
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
        修改<%=sName%>成功。
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
<form name="frmPost" method="post">
      <table width="50%"  border="0" align="center" cellpadding="0" cellspacing="10">
        <tr align="center">
          <td height="37">
<%
       switch (iFlag)
       {
         case 114: //customer account
%>
			  <table  border="0" cellspacing="0" cellpadding="0">
				  <tr>
					<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
					<td><input name="Submit22" type="button" class="button" onClick="javascript:frmSubmit('<bk:backurl property='customer_view.do' />')" value="返回客户资料"></td>
					<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
	&nbsp;&nbsp;<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
					<td><input name="Submit22" type="button" class="button" onClick="javascript:frmSubmit('<bk:backurl property='customer_additional_info.do?txtCustomerID=<tbl:writeparam name='txtCustomerID'/>' />')" value="返回客户附加信息"></td>
					<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
				  </tr>
			  </table>
	<%
          break;
         case 250: //booking
%>
        <table align="center" border="0" cellspacing="0" cellpadding="0">
        <tr>

            <td><img src="img/button_l.gif" border="0" ></td>
            <td background="img/button_bg.gif"><a href="<bk:backurl property="cust_forced_deposit_query_result.do" />" class="btn12">返&nbsp;回</a></td>
            <td><img src="img/button_r.gif" border="0" ></td>
        </tr>
	</table>	  
<%
         break;
         case 116: 
%>
		<table align="center" border="0" cellspacing="0" cellpadding="0">
		        <tr>
		
		            <td><img src="img/button2_r.gif" width="22" height="20"></td> 
		            <td background="img/button_bg.gif"><a href="<bk:backurl property="query_book_account.do" />" class="btn12">返&nbsp;回</a></td>
		            <td><img src="img/button2_l.gif" width="13" height="20"></td>
		        </tr>
		</table>
<%
         break;
         case 2001:
         case 2002:
         case 2003:
%>
		<table align="center" border="0" cellspacing="0" cellpadding="0">
		        <tr>
		<td width="11" height="20"><img src="img/button2_r.gif" width="22" height="20"></td>
		<td><input name="Submit22" type="button" class="button" onClick="javascript:frmSubmit('<%=url%>')" value="<%=btnValue%>"></td>
		<td width="22" height="20"><img src="img/button2_l.gif" width="11" height="20"></td>
	     </tr>
		</table>
<%    
	break;     
         default: //other
%> 
	  <table  border="0" cellspacing="0" cellpadding="0">
	      <tr>
	      	
	      	
	 <bk:canback url="<%=url%>">
      <td width="20">&nbsp;&nbsp;</td> 
      <td><img src="img/button2_r.gif" width="22" height="20"></td>
      <td background="img/button_bg.gif"  ><a href="<bk:backurl property='<%=url%>' />" class="btn12"><%=btnValue%></a></td>           
      <td><img src="img/button2_l.gif" width="11" height="20"></td>
      <td width="20" >&nbsp;&nbsp;&nbsp</td>
  </bk:canback>
  
		
	     </tr>
	  </table>
<%}
%>
         </td>
       </tr>
    </table>    
</form>
     

</TD>
</TR>
</TABLE>


 
           

  
 
