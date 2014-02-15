<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="resultset" prefix="rs" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="osstags" prefix="d" %>

<%@ page import="com.dtv.oss.web.util.CommonKeys" %>
<%@ page import="com.dtv.oss.web.util.WebOperationUtil" %>
<%@ page import="com.dtv.oss.util.Postern" %>
<%@ page import="java.util.Map" %>

<%
    String strMopName1 = null;
    String csiType =(String)request.getAttribute("PayCsiType");
    System.out.println("csiType==============="+csiType);
    Map optSet =Postern.getOpeningPaymentMop(csiType);
    System.out.println("optSet================"+optSet);
    Iterator iterator = ((Map)optSet).entrySet().iterator();
    String openPaymapKey ="";
    String openPaymapValue =""; 
    String payMapString ="<select name='pay_realMOP' onChange='javaScript:move_addInfo();'><option value='' >-----------------</option>";
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
    }
    payMapString =payMapString+"</select>";
    String checkMoneyFlag =(pageContext.getRequest().getAttribute("checkMoneyFlag")==null) ? "2" :(String)pageContext.getRequest().getAttribute("checkMoneyFlag");
    System.out.println("checkMoneyFlag=================="+checkMoneyFlag);
        
%>

  
 <table align="center" width="100%" border="0" cellpadding="4" cellspacing="1" class="list_bg">
 	  <tr> 
       <td class="import_tit" align="center" colspan="5"><font size="3">预存清单</font></td>
    </tr>
    <tr class="list_head">
       <td class="list_head" width="30%" align="center">支付方式</td>
       <td class="list_head" width="30%" align="center">票据编号</td>
       <td class="list_head" width="30%" align="center">预存金额</td>
       <td class="list_head" width="10%" align="center">操作</td>
    </tr>
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
      if (pay_realPay !=null){ 
         for (int i=0; i<pay_realPay.length; i++) {
             //获得支付方式的中文名
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
        <td align="center" width="30%"><%=payMapString%></td>
        <td align="center" width="30%"><input name="pay_realaddInfo" type="text" size="16" value="" class="text" onBlur='javaScript:check_addInfo();'></td>
        <td align="center" width="30%"><input name="pay_realpay" type="text" size="12"  style="text-align:right" value="0.0" class="text" onBlur='javaScript:pay_repeatProgression();' ></td>
        <td align="center" width="10%"><input type='button' name='addbutton' value='增加' onClick='javascript:add_pay();' class='button'></td> 
      </tr>
    </table>
</span>  

<table align="center" width="100%" border="0" cellspacing="1" cellpadding="3" class="list_bg"> 
	  <tr class="list_bg2">
	   	<td  valign="middle" align="right"  width="60%" colspan="2">预存金额总计:</td>
      <td  valign="middle" align="center" width="30%" ><input type="text" size="12" style="text-align:right" name="generalPayTotal" value="0.0" class="textgray" readonly> </td>
 	    <td  valign="middle" align="center" width="10%" >&nbsp;</td>
 	  </tr>
 </table>

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
     	var pay_tickettype =pay_mop[column].value.substring(pay_mop[column].value.indexOf("-")+1,pay_mop[column].value.indexOf("_"));
	    //抵扣券支付，输入票号
	    if (pay_tickettype ==pay_paymentTicket){
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
       var pay_tempPayTotal =0.0;
       init();
       if (pay_pay) {
           for (i=0; i<pay_pay.length;i++){
           	   if (!check_Pay(i)) return ;
           	   if (pay_mop[i].value !=''){  
                  pay_tempPayTotal =pay_tempPayTotal+pay_pay[i].value*1.0;
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
	    if (check_Blank(pay_pay[rowncolumn], true, "预存金额"))
	        return false;
	    if (!check_Float(pay_pay[rowncolumn], true, "预存金额"))
	        return false;
	    	         
	    if (pay_checkMoney=='0'){
          if (pay_pay[rowncolumn].value*1.0 <0){
               alert("预存金额不能为负数");
               pay_pay[rowncolumn].focus();
               return false;
          }
      }else if (pay_checkMoney=='1'){
      	 if (pay_pay[rowncolumn].value*1.0 >0){
              alert("预存金额不能为正数");
              pay_pay[rowncolumn].focus();
              return false;
          }
      }
      return true;
   }
  pay_repeatProgression();
</script>

