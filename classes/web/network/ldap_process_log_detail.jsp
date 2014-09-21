<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="resultset" prefix="rs" %>
<%@ taglib uri="osstags" prefix="o" %>
<%@ taglib uri="bookmark" prefix="bk" %>

 
 
 
<script language=javascript>

 
</Script>
<br>
<table width="820" align="center" cellpadding="0" cellspacing="0">
<tr> 
<td align="center" valign="top"><table  border="0" align="center" cellpadding="0" cellspacing="10">
      <tr>
        <td><img src="img/list_tit.gif" width="19" height="19"></td>
        <td class="title">LDAP接口日志记录详细信息</td>
      </tr>
    </table>
      <table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
        <tr>
          <td><img src="img/mao.gif" width="1" height="1"></td>
        </tr>
      </table>
         <br>
 
<form name="frmPost" method="post" >
<lgc:bloop requestObjectName="ResponseQueryResult" item="oneline" >
 

 <table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
        <tr>
        <td class="list_bg2" align="right">流水号</td>
        <td class="list_bg1" align="left">
	<tbl:writenumber name="oneline" digit="8"  hideZero="true" property="seqno" />
	</td>
	 <td class="list_bg2" align="right">创建时间</td>
               <td class="list_bg1" align="left"> 
               <tbl:writedate name="oneline" property="createTime"  includeTime="true"/>
        </td>	
	</tr>
	 <tr>
		<td class="list_bg2" align="right">事件ID</td>
		<td class="list_bg1" align="left"> 
			<tbl:write name="oneline" property="eventID" />
                </td>
		<td class="list_bg2" align="right">命令类型</td>
		<td class="list_bg1" align="left"> 
		<tbl:write name="oneline" property="commandName" />
	       </td>
	</tr>
	<tr>
    	        <td class="list_bg2" align="right">客户ID</td>
		<td class="list_bg1" align="left"> 
		<tbl:writenumber digit="8"  hideZero="true" name="oneline" property="customerID"/>
		 
		<td class="list_bg2" align="right">帐户ID</td>
		<td class="list_bg1" align="left"> 
		<tbl:writenumber name="oneline" digit="7"  hideZero="true" property="accountID" />
	</tr>
        <tr>
            <td class="list_bg2" align="right" >业务账户号</td>
          <td class="list_bg1" align="left"> 
            <tbl:writenumber name="oneline" digit="7"  hideZero="true" property="serviceAccountID" />
            </td>
          <td class="list_bg2" align="right" >客户产品号</td>
         <td class="list_bg1" align="left"> 
          <tbl:writenumber name="oneline" digit="8"  hideZero="true" property="psID" />
         </td>
      </tr> 
      
      <tr>
		<td class="list_bg2" align="right" >设备ID</td>
		<td class="list_bg1" align="left"> 
		        <tbl:writenumber name="oneline" digit="7"  hideZero="true"  property="deviceID"/>
	        </td>
		<td class="list_bg2" align="right" >状态</td>
		<td class="list_bg1" align="left"> 
		   <o:getcmnname  typeName="SET_G_GENERALSTATUS" match="oneline:status"/></td>
		         
	        </td> 
		 
	</tr>
	 <tr>
		<td class="list_bg2" align="right" >QueueID</td>
		<td class="list_bg1" align="left" colspan="3"> 
		        <tbl:writenumber name="oneline" property="queueID" hideZero="true" digit="9"/>
	        </td>
		 
	</tr>
	
      <tr>
		<td class="list_bg2" align="right" >描述</td>
		<td class="list_bg1" align="left" colspan="3"> 
		        <tbl:write name="oneline" property="description"/>
	        </td>
		 
	</tr>
	      
	  
	  
	 
	 
  </table>
  <table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>  
  <br>
  <table align="center" border="0" cellspacing="0" cellpadding="0">
     
      <td width="20" ></td>
          <td><img src="img/button2_r.gif" border="0" ></td>
          <td background="img/button_bg.gif"><a href="<bk:backurl property="ldap_log_query_result.do" />" class="btn12">返&nbsp;回</a></td>
          <td><img src="img/button2_l.gif" border="0" ></td>
        
 
	  </tr>
 </table>           
 <br>
 
  
</form>  
 </lgc:bloop> 
 </td>
 </tr>
</table> 
 

   

 