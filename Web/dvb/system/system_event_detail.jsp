<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="resultset" prefix="rs" %>
<%@ taglib uri="osstags" prefix="o" %>
<%@ taglib uri="bookmark" prefix="bk" %>

 
<%@ page import="com.dtv.oss.util.Postern"%>
 
 

<table width="820" align="center" cellpadding="0" cellspacing="0">
<tr> 
<td align="center" valign="top"><table  border="0" align="center" cellpadding="0" cellspacing="10">
      <tr>
        <td><img src="img/list_tit.gif" width="19" height="19"></td>
        <td class="title">系统事件明细信息</td>
      </tr>
    </table>
      <table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
        <tr>
          <td><img src="img/mao.gif" width="1" height="1"></td>
        </tr>
      </table>
         <br>
 
<form name="frmPost" method="post" action="install_info_update.do" >
<lgc:bloop requestObjectName="ResponseQueryResult" item="oneline" >

<%
     com.dtv.oss.dto.SystemEventDTO eventDto = (com.dtv.oss.dto.SystemEventDTO)pageContext.getAttribute("oneline");
            int eventClass = eventDto.getEventClass();
            String eventName = Postern.getEventNameByEventClass(eventClass);
            String eventReason = Postern.getReasonByReasonCode(eventDto.getEventReason(),eventClass);
            if(eventReason==null) eventReason="";
            String productName = Postern.getProductNameByID(eventDto.getProductID());
              if(productName==null) productName="";
            String oldProductName = Postern.getProductNameByID(eventDto.getOldProductID());
              if(oldProductName==null) oldProductName="";
            String operatorName = Postern.getOperatorNameByID(eventDto.getOperatorID());
              if(operatorName==null) operatorName="";
             
%>

 <table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
        <tr>
        <td class="list_bg2"><div align="right">事件ID</div></td>
        <td class="list_bg1">
	<input type="text" name="txtEventClass" size="25" value="<tbl:write name="oneline" property="sequenceNo" />" class="textgray" readonly   >
	</td>
	 <td class="list_bg2"><div align="right">事件类型</div></td>
        <td class="list_bg1">
	<input type="text" name="txtEventClass" size="25" value="<%=eventName%>" class="textgray" readonly   >
	</td>
	</tr>
	 <tr>
		<td class="list_bg2" ><div align="right">事件原因</div></td>
		<td class="list_bg1"> 
			<input type="text" name="txtEventReason"  size="25" value="<%=eventReason%>" class="textgray" readonly>
                </td>
		<td class="list_bg2" ><div align="right">操作员名称</div></td>
		<td class="list_bg1"> 
		<input type="text" name="txtOperatorName"  size="25" class="textgray" readonly value="<%=operatorName%>" >
	       </td>
	</tr>
	<tr>
    	        <td class="list_bg2" ><div align="right">客户证号</div></td>
		<td class="list_bg1">
		<input type="text" name="txtCustomerID"  size="25" value="<tbl:write name="oneline" property="customerID"/>" class="textgray" readonly   >
		 
		<td class="list_bg2"><div align="right">资金账号</div></td>
		<td class="list_bg1">
		<input type="text" name="txtCustomerID"  size="25" value="<tbl:write name="oneline" property="accountID"/>" class="textgray" readonly   >
	</tr>
        <tr>
            <td class="list_bg2" ><div align="right">业务账号</div></td>
            <td class="list_bg1">
            <input type="text" name="serviceAccountID" size="25" value="<tbl:write name="oneline" property="serviceAccountID" />" class="textgray" readonly   >
            </td>
          <td class="list_bg2" ><div align="right">客户产品号</div></td>
          <td class="list_bg1">
          <input type="text" name="psID" class="textgray" size="25" readonly value="<tbl:write name="oneline" property="psID"/>" >
         </td>
      </tr> 
      <tr>
         <td class="list_bg2" ><div align="right">运营商产品</div></td>
         <td class="list_bg1">
          <input type="text" name="productID" class="textgray" size="25" readonly value="<%=productName%>" >
         </td>
         <td class="list_bg2"><div align="right">受理单号</div></td>
         <td class="list_bg1">
         <input type="text" name="txtType"  size="25" class="textgray" readonly value="<tbl:write name="oneline"  property="csiID"/>" >
         </td>
      </tr>
      <tr>
		<td class="list_bg2"><div align="right">卡设备ID</div></td>
		<td class="list_bg1"> 
		        <input type="text" name="scDeviceID"  size="25"  class="textgray" readonly value="<tbl:write name="oneline"   property="scDeviceID"/>" >
	        </td>
		</td>
		<td class="list_bg2" ><div align="right">卡设备序列号</div></td>
		<td class="list_bg1"> 
		 <input type="text" name="txtType" size="25" class="textgray" readonly value="<tbl:write name="oneline" property="scSerialNo"/>" >
		</td>
	</tr>
	  <tr>
		<td class="list_bg2" ><div align="right">盒设备ID</div></td>
		<td class="list_bg1"> 
		      <input type="text" name="txtType"  size="25" class="textgray" readonly value="<tbl:write name="oneline"   property="stbDeviceID"/>" >
	        </td>
		</td>
		<td class="list_bg2" ><div align="right">盒设备序列号</div></td>
		<td class="list_bg1"> 
		 <input type="text" name="txtType"  size="25" class="textgray" readonly value="<tbl:write name="oneline" property="stbSerialNo"/>" >
		</td>
	</tr>
	<tr>
	 
		<td class="list_bg2" ><div align="right">被更换的卡设备ID</div></td>
		<td class="list_bg1"> 
			<input type="text" name="txtType"  size="25" class="textgray" readonly value="<tbl:write name="oneline"  property="oldScDeviceID"/>" >
		 </td>
		 <td class="list_bg2" ><div align="right">被更换卡设备序列号</div></td>
		 <td class="list_bg1"> 
		 <input type="text" name="txtFirstWorkDate"  size="25" value="<tbl:write name="oneline" property="oldScSerialNo"/>" class="textgray" readonly >
		</td>
	
	</tr>
	  <tr>
		<td class="list_bg2" ><div align="right">被更换的盒设备ID</div></td>
		<td class="list_bg1"> 
		      <input type="text" name="txtType"  size="25" class="textgray" readonly value="<tbl:write name="oneline"  property="oldStbDviceID"/>" >
	        </td>
		</td>
		<td class="list_bg2" ><div align="right">被更换的盒设备序列号</div></td>
		<td class="list_bg1"> 
		 <input type="text" name="txtType" size="25"  class="textgray" readonly value="<tbl:write name="oneline" property="oldStbSerialNo"/>" >
		</td>
	</tr>
	 <tr>
		<td class="list_bg2" ><div align="right">升级前的运营商产品</div></td>
		<td class="list_bg1"> 
		 <input type="text" name="txtType" size="25" class="textgray" readonly value="<%=oldProductName%>" >
	        </td>
	        <td class="list_bg2" ><div align="right">手工发送命令ID</div></td>
		<td class="list_bg1"> 
		 <input type="text" name="txtType" size="25" class="textgray" readonly value="<tbl:write  name="oneline"   property="commandID"/>" >
		</td>
	</tr>
	 
	<tr>
		<td class="list_bg2" ><div align="right">手工发送消息内容</div></td>
		<td class="list_bg1"> 
		 <input type="text" name="txtType"  size="25" class="textgray" readonly value="<tbl:write name="oneline" property="recordData"/>" >
		</td>
		<td class="list_bg2" ><div align="right">原来客户产品状态</div></td>
		<td class="list_bg1"> 
		<input type="text" name="txtType" size="25" value="<o:getcmnname typeName="SET_C_CUSTOMERPRODUCTPSTATUS" match="oneline:oldCustProductStatus"/>" class="textgray" readonly>
		   
			 
		 </td>
		 
	</tr>
	 
	 <tr>
	 <td class="list_bg2" ><div align="right">关联的帐单ID</div></td>
               <td class="list_bg1"> 
               <input type="text" name="txtType"  class="textgray" size="25" readonly value="<tbl:write  name="oneline"   property="invoiceNo"/>" >
        </td>	
	  <td valign="middle" class="list_bg2"  ><div align="right">金额</div></td>
          <td class="list_bg1"> <font size="2">
           <input type="text" name="txtType" size="25"  class="textgray" readonly value="<tbl:write name="oneline" property="amount"/>" >
          </font></td>
	</tr>
	
	<tr>
	 <td class="list_bg2" ><div align="right">服务号码</div></td>
               <td class="list_bg1"> 
               <input type="text" name="txtType"  class="textgray" size="25" readonly value="<tbl:write  name="oneline"   property="serviceCode"/>" >
        </td>	
	  <td valign="middle" class="list_bg2"  ><div align="right">旧服务号码</div></td>
          <td class="list_bg1"> <font size="2">
           <input type="text" name="txtType" size="25"  class="textgray" readonly value="<tbl:write name="oneline" property="oldServiceCode"/>" >
          </font></td>
	</tr>
	
	 <tr>
	 <td class="list_bg2" ><div align="right">小钱包代码</div></td>
               <td class="list_bg1"> 
                
               <input type="text" name="txtType"  size="25" class="textgray" readonly value="<tbl:write name="oneline" property="caWalletCode"/>" >
        </td>	
	  
	 <td class="list_bg2" ><div align="right">创建时间</div></td>
               <td class="list_bg1"> 
                
               <input type="text" name="txtType"  size="25" class="textgray" readonly value="<tbl:writedate name="oneline" property="dtCreate" includeTime = "true"/>" >
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
     
       <tr> 
	<td><img src="img/button2_r.gif" width="22" height="20"></td>  
        <td background="img/button_bg.gif">
         <a href="<bk:backurl property="system_event_result.do" />" class="btn12">返&nbsp;回</a></td>
          
         <td><img src="img/button2_l.gif" width="13" height="20"></td>
 
	  </tr>
 </table>           
 <br>
 
 <input type="hidden" name="txtFrom" size="20" value="1">
<input type="hidden" name="txtTo" size="20" value="10">
</form>  
 </lgc:bloop> 
 </td>
 </tr>
</table> 
 

   

 