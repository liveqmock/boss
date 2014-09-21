<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="resultset" prefix="rs" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="osstags" prefix="d" %>

<%@ page import="com.dtv.oss.dto.MethodOfPaymentDTO" %>
<%@ page import="com.dtv.oss.dto.PaymentRecordDTO" %>
<%@ page import="com.dtv.oss.web.util.CommonKeys" %>
<%@ page import="com.dtv.oss.util.Postern" %>
<%@ page import="java.util.Map" %>

<%
    boolean  acceptPayFlag =false;
    if (request.getAttribute("acceptPayFlag")==null || ((String)request.getAttribute("acceptPayFlag")).equalsIgnoreCase("true")){ 
       acceptPayFlag = true;
    }
    boolean  refundFlag =false;
    if (request.getAttribute("refundFlag")!=null && ((String)request.getAttribute("refundFlag")).equalsIgnoreCase("true")){ 
       refundFlag =true;
    } 
    String strMopName1 = null;
    String csiType =(String)request.getAttribute("PayCsiType");
    System.out.println("csiType==============="+csiType);
    Map optSet =Postern.getOpeningPaymentMop(csiType);
    System.out.println("optSet================"+optSet);
    Iterator iterator = ((Map)optSet).entrySet().iterator();
    String openPaymapKey ="";
    String openPaymapValue =""; 
    String payMapString ="<select name='pay_realMOP' onChange='javaScript:move_addInfo();'><option value='' >-----------------</option>";
    String defaultpayMapString =payMapString; 
    boolean defaultfirstCheck =true;
    while(iterator.hasNext()){
        Map.Entry item = (Map.Entry)iterator.next();
        payMapString =payMapString+"<option value='"+item.getKey().toString()+"'>"+(String)item.getValue()+"</option>";
        if (openPaymapKey.equals("")){
           openPaymapKey =item.getKey().toString();
           openPaymapValue =(String)item.getValue();
        }
        else {
        	openPaymapKey =openPaymapKey+";"+item.getKey().toString();
        	openPaymapValue =openPaymapValue +";"+(String)item.getValue();
        }
        
        String itemKey =item.getKey().toString();
        String item_mopid =itemKey.substring(0,itemKey.indexOf("-"));
        String item_paytype =itemKey.substring(itemKey.indexOf("-")+1,itemKey.indexOf("_"));
        
        if ("CH".equals(item_paytype) && !"10".equals(item_mopid)
                                                      && !"11".equals(item_mopid) && defaultfirstCheck){
           defaultpayMapString =defaultpayMapString+"<option value='"+item.getKey().toString()+"' selected='selected'>"+(String)item.getValue()+"</option>";
           defaultfirstCheck =false;
        }else{
        	 defaultpayMapString =defaultpayMapString+"<option value='"+item.getKey().toString()+"'>"+(String)item.getValue()+"</option>";
        }
    }
    payMapString =payMapString+"</select>";
    defaultpayMapString =defaultpayMapString +"</select>";
    String checkMoneyFlag =(pageContext.getRequest().getAttribute("checkMoneyFlag")==null) ? "2" :(String)pageContext.getRequest().getAttribute("checkMoneyFlag");
    System.out.println("checkMoney=========="+checkMoneyFlag);
    
%>
<table align="center" width="100%" border="0" cellspacing="1" cellpadding="3" class="list_bg"> 
	  <tr>
	   	<td  valign="middle" align="right" class="list_bg2"><%if(refundFlag) { %> 预存退费金额总计: <%} else {%> 支付金额总计: <%} %>
	   		<input type="text" size="10" style="text-align:right" name="generalPayTotal" value="0.0" class="textgray" readonly> 
	   	</td>
 	  </tr>
 </table>
  
 <table align="center" width="100%" border="0" cellpadding="4" cellspacing="1" class="list_bg">
 	  <tr> 
      <td class="import_tit" align="center" colspan="4"><font size="3">
      <%
        if (refundFlag){
      %>
         退费清单("-":表示退给用户，"+":表示收取费用)
      <% 
         } else { 
      %>
         支付清单
      <%
         }
      %>
       </font></td>
    </tr>
    <tr class="list_head">
       <td class="list_head" width="30%" align="center">支付方式</td>
       <td class="list_head" width="30%" align="center">票据编号</td>
       <td class="list_head" width="30%" align="center"><% if(refundFlag) {%> 预存退费金额  <%} else{%> 支付金额 <%} %></td>
       <td class="list_head" width="10%" align="center">操作</td>
    </tr>
   <%
       //付费方式
       Map mapBankMop1=Postern.getAllMethodOfPayments();
       MethodOfPaymentDTO dtoMOP1 =null;
       float intpayTotal =0;
       //团购预付，应该来说这个页面不可能用到！
   %>
    <lgc:bloop requestObjectName="groupBargainPayedImp" item="oneline">
      <tbl:printcsstr style1="list_bg1" style2="list_bg2" overStyle="list_over" > 
        <%
           PaymentRecordDTO paymentDto =(PaymentRecordDTO)pageContext.getAttribute("oneline");
           if (paymentDto !=null) dtoMOP1 = (MethodOfPaymentDTO)mapBankMop1.get(String.valueOf(paymentDto.getMopID()));
           if (dtoMOP1!=null) strMopName1=dtoMOP1.getName();
           if (strMopName1==null) strMopName1="";
           System.out.println("paymentDto.getPayType()=============="+paymentDto.getPayType());
           //intpayTotal =intpayTotal + paymentDto.getAmount();
           intpayTotal=(float)(Math.floor(intpayTotal*100+paymentDto.getAmount()*100+0.01)/100);
        %>
           <td align="center"><%=strMopName1%></td>
           <td align="center"><tbl:write name="oneline" property="ticketNo" /></td>
           <td align="center"><tbl:write name="oneline" property="Amount" /></td>
           <td align="center">&nbsp;</td>
       </tbl:printcsstr>
   </lgc:bloop>
   <input type="hidden" name="intpayTotal" value="<%=intpayTotal%>"  /> 
 </table>
 
<%
   int pay_rownum =0;
   String payTableBody ="";
%>
<span id="payList">
  <table align="center" width="100%" border="0" cellpadding="4" cellspacing="1" class="list_bg">
  <%
       //扑获从确定页面返回的费用
      String[] pay_realMOP =request.getParameterValues("pay_realMOP");
      String[] pay_realaddInfo =request.getParameterValues("pay_realaddInfo"); 
      String[] pay_realPay =request.getParameterValues("pay_realpay");

      String styletr ="";
      if (pay_realPay !=null && acceptPayFlag){ 
        for (int i=0; i<pay_realPay.length; i++) {
             //获得支付方式的中文名
             if (pay_realMOP[i].equals("")) continue;
             strMopName1 = (String)optSet.get(pay_realMOP[i]);
             if (strMopName1==null) strMopName1="";
             if ( i % 2 ==0 ){
                styletr  ="list_bg1";  
	           } else {
	              styletr  ="list_bg2";
	           }

	           pay_rownum =pay_rownum+1;
	           payTableBody =payTableBody +"<tr id='"+i+"'  class='"+styletr+"' >"    	
	                        +"  <input type='hidden' name='pay_realtrid' value='"+i+"'>"
	                        +"  <td align='center' width='30%'><input type='hidden' name='pay_realMOP' value='"+pay_realMOP[i]+"'>"+strMopName1+"</td>" 							 							
 	                        +"  <td align='center' width='30%'><input type='text' name='pay_realaddInfo'  size='16' value='"+pay_realaddInfo[i]+"' readonly='true' class='textgray'></td>"
 	                        +"  <td align='center' width='30%'><input type='text' name='pay_realpay' size='12' style='text-align:right' value="+pay_realPay[i]+" readonly='true' class='textgray'></td>"				
	                        +"  <td align='center' width='10%'><input type='button' name='delbutton' value='删除' onClick='javascript:del_payText("+i+");' class='button'></td>"
 	                        +"  </tr>"  ;
         }
    %>
         <%=payTableBody%>
    <%
      }
      if ( pay_rownum % 2 ==0 ){
           styletr  ="list_bg1";  
	    } else {
	         styletr  ="list_bg2";
	    } 
    %>
    	<tr id='<%=pay_rownum%>'  class='<%=styletr%>' >
    		<input type="hidden" name="pay_realtrid" value='<%=pay_rownum%>'>
    		<%
    	     String systemSettingValue = Postern.getSystemsettingValueByName("SET_V_AUTOPAYMENT");
           if ((pay_realPay !=null && pay_realPay.length > 0 && acceptPayFlag) || 
                (systemSettingValue ==null || "N".equals(systemSettingValue))) {
        %>
        <td align="center" width="30%"><%=payMapString%></td>
        <td align="center" width="30%"><input name="pay_realaddInfo" type="text" size="16" value="" class="text" onBlur='javaScript:check_addInfo();'></td>
        <td align="center" width="30%"><input name="pay_realpay" type="text" size="12"  style="text-align:right" value="0.0" class="text" onBlur='javaScript:pay_repeatProgression();' ></td>
        <td align="center" width="10%"><input type='button' name='addbutton' value='增加' onClick='javascript:add_pay();' class='button'></td> 
       <% } else{ %>
        <td align="center" width="30%"><%=defaultpayMapString%></td>
        <td align="center" width="30%"><input name="pay_realaddInfo" type="text" size="16" value="" class="text" onBlur='javaScript:check_addInfo();'></td>
        <%
           double defaultGeneralFeeTotal =(request.getAttribute("defaultGeneralFeeTotal")==null) ? 0 : ((Double)request.getAttribute("defaultGeneralFeeTotal")).doubleValue();
           double default_generalFee = defaultGeneralFeeTotal-intpayTotal ;
        %>
        <td align="center" width="30%"><input name="pay_realpay" type="text" size="12"  style="text-align:right" value="<%=default_generalFee%>" class="text" onBlur='javaScript:pay_repeatProgression();' ></td>
        <td align="center" width="10%"><input type='button' name='addbutton' value='增加' onClick='javascript:add_pay();' class='button'></td> 
       <% } %>
      </tr>
    </table>
</span>  

<script language=javascript>
	 var pay_rownum=<%=pay_rownum%>; 
   var pay_trid;
	 var pay_mop;
   var pay_mopName ;
   var pay_pay;
   var pay_addInfo;
	 var pay_checkMoney='<%=checkMoneyFlag%>' ;
	 var pay_paymentTicket="<%=CommonKeys.PAYMENTTICKETTY_DK%>";
   var pay_mopid;
   var pay_tickettype ;
   var pay_tableBody="<%=payTableBody%>"; 
   var tempBody="";
   var pay_tableHead="<table align='center' width='100%'  border='0' cellpadding='4' cellspacing='1' class='list_bg'>";
   var pay_tableTie="</table>";
   var pay_MapString ="<%=payMapString%>";
   var pay_mapKey ="<%=openPaymapKey%>";
   var pay_mapValue ="<%=openPaymapValue%>";
   
   //支付函数
   function add_pay(){
       var column = maxColumn()-1;
     	 
       if (check_Blank(pay_mop[column], true, "支付方式"))
	        return false;
	     pay_mopName =pay_mop[column].options[pay_mop[column].selectedIndex].text;
       
	     if (!check_Pay(column)) return ;
      
	     var styletr ="";
       if (pay_rownum % 2 ==0 ){
           styletr  ="list_bg1";  
	     } else {
	         styletr  ="list_bg2";
	     }
	       
	      
	     tempBody="<tr id='"+pay_rownum+"'  class='" +styletr+"' >";  	
       tempBody=tempBody+"<input type='hidden' name='pay_realtrid' value='"+pay_rownum+"'>"
	     tempBody=tempBody+"<td align='center' width='30%'><input type='hidden' name='pay_realMOP' value='"+pay_mop[column].value+"'>"+pay_mopName+"</td>"; //支付方式 							 							
 	     tempBody=tempBody+"<td align='center' width='30%'><input name='pay_realaddInfo' type='text' size='16' value='"+ pay_addInfo[column].value +"' readonly='true' class='textgray'></td>";					//附加信息
	     tempBody=tempBody+"<td align='center' width='30%'><input name='pay_realpay' type='text' size='12' style='text-align:right' value='"+ pay_pay[column].value +"' readonly='true' class='textgray'></td>";						//支付金额
	     tempBody=tempBody+"<td align='center' width='10%'><input type='button' name='delbutton' value='删除' onClick='javascript:del_payText("+ pay_rownum +");' class='button'></td>";		//操作
 	     tempBody=tempBody+"</tr>"; 	    
	      
	     clear_payText(pay_trid[column].value);
	
	    pay_rownum++;
	    if (pay_rownum % 2 ==0 ){
          styletr  ="list_bg1";  
	    } else {
	        styletr  ="list_bg2";
	    }
	    
	    pay_tableBody=pay_tableBody+tempBody+"<tr id='"+pay_rownum+"'  class='" +styletr+"' >"; 
	    pay_tableBody=pay_tableBody+"<input type='hidden' name='pay_realtrid' value='"+pay_rownum+"'>" 																										//序号
	    pay_tableBody=pay_tableBody+"<td align='center' width='30%'>"
	                               + pay_MapString
	                               +"</td>";
 	    pay_tableBody=pay_tableBody+"<td align='center' width='30%'><input name='pay_realaddInfo' type='text' size='16' value='' class='text' onBlur='javaScript:check_addInfo();'></td>";					//附加信息
	    pay_tableBody=pay_tableBody+"<td align='center' width='30%'><input name='pay_realpay' type='text' size='12' style='text-align:right' value='0.0'  class='text' onBlur='javaScript:pay_repeatProgression();'></td>";			//支付金额
	    pay_tableBody=pay_tableBody+"<td align='center' width='10%'><input type='button' name='addbutton' value='增加'  class='button' onClick='javascript:add_pay();'></td>";//操作                       
 	    pay_tableBody=pay_tableBody+"</tr>";
 	    
 	    document.getElementById("payList").innerHTML=pay_tableHead+pay_tableBody+pay_tableTie;
 	    
   }
   
   function maxColumn(){
      return document.getElementsByName("pay_realpay").length;     
   }
   
   function move_addInfo(){
   	  var column = maxColumn()-1;
   	  var pay_mopid =pay_mop[column].value.substring(0,pay_mop[column].value.indexOf("-"));
      var pay_tickettype =pay_mop[column].value.substring(pay_mop[column].value.indexOf("-")+1,pay_mop[column].value.indexOf("_"));
	    var pay_paymentType =pay_mop[column].value.substr(pay_mop[column].value.indexOf("_")+1);
	
	    pay_repeatProgression();
	    pay_addInfo[column].value ="";
	    //抵扣券支付，输入票号
	    if (pay_tickettype ==pay_paymentTicket && pay_mopid !=10 && pay_mopid !=11){
	         pay_addInfo[column].focus();
      }
      //支票，输入票号
      if (pay_mopid ==10000000){
	    	 pay_addInfo[column].focus();	
	    }
   }
   
   function check_addInfo(){
     	var column = maxColumn()-1;
     	var pay_mopid =pay_mop[column].value.substring(0,pay_mop[column].value.indexOf("-"));
     	var pay_tickettype =pay_mop[column].value.substring(pay_mop[column].value.indexOf("-")+1,pay_mop[column].value.indexOf("_"));
	    if (pay_tickettype ==pay_paymentTicket && pay_mopid !=10 && pay_mopid !=11){
         if (check_Blank(pay_addInfo[column], true, "票据编号")){
	          pay_addInfo[column].focus();
	       }
	    }
	    var pay_mopid =pay_mop[column].value.substring(0,pay_mop[column].value.indexOf("-"));
	    //如果是支票，则要求输入4位
	    if (pay_mopid ==10000000){
	    	 if (check_Blank(pay_addInfo[column], true, "票据编号")){
	    	 	   pay_addInfo[column].focus();	
	    	 }else{
	    	   if (!checkPlainNum_1(pay_addInfo[column],true,4,"票据编号")){
	    	 	    pay_addInfo[column].focus();	
	    	   }
	    	 }
	    }else if (pay_mopid ==1){
	    	 if (pay_addInfo[column].value !='' && pay_addInfo[column].value.length !=12){
	    	 	   alert("现金付费,票据编号应为12位");
	    	 	   pay_addInfo[column].focus();	
	    	 }
	    }   
   }
   
   function pay_repeatProgression(){  
       var pay_tempPayTotal =parseFloat(document.all("intpayTotal").value);
       init();
       if (pay_pay) {
           for (i=0; i<pay_pay.length;i++){
           	   if (!check_Pay(i)) return ;
           	   if (pay_mop[i].value !=''){  
                  pay_tempPayTotal =Math.floor(pay_tempPayTotal*100+parseFloat(pay_pay[i].value)*100+0.01)/100;
               }
           }
       }
      document.all("generalPayTotal").value=pay_tempPayTotal;
   }
   
   function init(){
   	  pay_pay =document.getElementsByName("pay_realpay");
      pay_mop=document.getElementsByName("pay_realMOP");
      pay_addInfo=document.getElementsByName("pay_realaddInfo");
      pay_trid =document.getElementsByName("pay_realtrid");
   }
   function clear_payText(rownum){
	   indexStart=0;
	   indexEnd=0;
	   startStr="<tr id='"+rownum+"'";
	   endStr="</tr>";
	   indexStart=pay_tableBody.indexOf(startStr);
	   if(indexStart<0)  return;           
	   indexEnd=pay_tableBody.indexOf(endStr,indexStart);
	   if (indexEnd<=0||indexEnd<indexStart) return;
	   indexEnd=indexEnd+5;
	
	   subStr=pay_tableBody.substring(indexStart,indexEnd);
	   pay_tableBody=pay_tableBody.replace(subStr,"");
	   document.getElementById("payList").innerHTML=pay_tableHead+pay_tableBody+pay_tableTie;
   }
   
   function del_payText(rownum) {
   	  
   	  var column = maxColumn()-1;
   	  var maxtrid =pay_trid[column].value;
      //保留最后一个增加项目的信息
      var styletr ="";
      if (pay_rownum % 2 ==0 ){
          styletr  ="list_bg1";  
	    } else {
	        styletr  ="list_bg2";
	    }
	    tempBody="<tr id='"+maxtrid+"' class='"+styletr+"'> " 
	            +"  <input type='hidden' name='pay_realtrid' value='"+maxtrid+"'> "
	            +"  <td align='center' width='30%'>"
	            + make_SelectText(pay_mop[column].value)
	            +"  </td>";
 	    tempBody=tempBody+"<td align='center' width='30%'><input name='pay_realaddInfo' type='text' size='16' value='"+pay_addInfo[column].value+"' class='text' onBlur='javaScript:check_addInfo();'></td>";					//附加信息
	    tempBody=tempBody+"<td align='center' width='30%'><input name='pay_realpay' type='text' size='12' style='text-align:right' value='"+pay_pay[column].value+"'  class='text' onBlur='javaScript:pay_repeatProgression();'></td>";						//支付金额
	    tempBody=tempBody+"<td align='center' width='10%'><input type='button' name='addbutton' value='增加' onClick='javascript:add_pay();' class='button'></td>";
	    tempBody=tempBody+"</tr>";
	    clear_payText(rownum);
	    clear_payText(maxtrid);
	    pay_tableBody =pay_tableBody+tempBody;
   	 
   	  document.getElementById("payList").innerHTML=pay_tableHead+pay_tableBody+pay_tableTie;
   	  
   	  pay_repeatProgression();
   } 
   
   function make_SelectText(matchValue){
   	   pay_mapKeyStr =pay_mapKey.split(";");
   	   pay_mapValueStr =pay_mapValue.split(";");
   	   pay_mapString ="<select name='pay_realMOP' onChange='javaScript:move_addInfo();'><option value='' >-----------------</option>";  
   	   pay_mapSelect ="";
   	   for (i=0; i< pay_mapKeyStr.length; i++){
   	   	   if (pay_mapKeyStr[i] ==matchValue) 
   	   	      pay_mapSelect ="selected='selected'";
   	   	   else
   	   	   	  pay_mapSelect =""; 
   	   	   pay_mapString =pay_mapString+"<option value='"+pay_mapKeyStr[i]+"' "+ pay_mapSelect+">"+pay_mapValueStr[i]+"</option>";
   	   }
   	  pay_mapString =pay_mapString+" </select>";
   	  return pay_mapString;
   }
   
   function check_Pay(rowncolumn){
	    if (check_Blank(pay_pay[rowncolumn], true, "费用支付金额"))
	        return false;
	    if (!check_Float(pay_pay[rowncolumn], true, "费用支付金额"))
	        return false;
	    	         
	    if (pay_checkMoney=='0'){
          if (pay_pay[rowncolumn].value*1.0 <0){
              alert("费用支付金额不能为负数");
              pay_pay[rowncolumn].focus();
              return false;
          }
      } else if (pay_checkMoney=='1'){
      	 if (pay_pay[rowncolumn].value*1.0 >0){
              alert("费用支付金额不能为正数");
              pay_pay[rowncolumn].focus();
              return false;
          }
      }
      
      pay_mopid =pay_mop[rowncolumn].value.substring(0,pay_mop[rowncolumn].value.indexOf("-"));
      if (pay_mopid ==10 || pay_mopid ==11){
      	  if (pay_pay[rowncolumn].value*1.0 <0){
              alert("采用预存抵扣方式支付金额不能为负数");
              pay_pay[rowncolumn].focus();
              return false;
          }
      }
      return true;
   }
  pay_repeatProgression();
</script>

 