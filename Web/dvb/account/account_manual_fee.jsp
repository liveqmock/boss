<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="privilege" prefix="pri" %>
<%@ taglib uri="osstags" prefix="o" %>
<%@ taglib uri="/WEB-INF/oss.tld" prefix="d" %>
<%@ taglib uri="bookmark" prefix="bk" %>

<%@ page import="com.dtv.oss.util.Postern,
                 java.util.Map,
                 java.util.HashMap,
                 java.util.Iterator,
                 com.dtv.oss.web.util.CommonKeys" %>
<%@ page import="com.dtv.oss.web.util.WebUtil" %>
<%@ page import="com.dtv.oss.dto.MethodOfPaymentDTO" %>
<%@ page import="com.dtv.oss.web.util.WebOperationUtil,
                 com.dtv.oss.service.util.CommonConstDefinition" %>

<SCRIPT language="JAVASCRIPT">

var xmlHttp;
var actiontype;
var feecount = 0,feeid = 0;
var paycount = 0,payid = 0;
function back_submit(){
	document.frmPost.action = '<bk:backurl property='account_view.do' />';
        document.frmPost.submit();
}
function createQueryString(){
	var selectId = document.all.frmPost.txtFeeType.value;
	var queryString = "feeType=" + selectId +"&actiontype=" + actiontype;
	return queryString;
}
function sendRequest(queryStr,strurl,flag){
	createXMLHttpRequest();
        var url = strurl + "?timeStamp=" + new Date().getTime();        
        xmlHttp.open("POST", url,flag);
        xmlHttp.onreadystatechange = handleStateChange;
        xmlHttp.setRequestHeader("Content-Type","application/x-www-form-urlencoded;");
        xmlHttp.send(queryStr);
}
function sendXML(xml,url){
	createXMLHttpRequest();
	xmlHttp.open("POST",url,false);
	xmlHttp.setRequestHeader("Content-Type","application/x-www-form-urlencoded;");
        xmlHttp.send(xml);
}
function changeAcctItemType(){	        
        actiontype = "changesel";
        var queryStr  = createQueryString();
        sendRequest(queryStr,"data_prepare.screen",true);		
}
function createXMLHttpRequest(){
	if(window.XMLHttpRequest) { //Mozilla 浏览器
            xmlHttp = new XMLHttpRequest();
        }else if (window.ActiveXObject) { // IE浏览器
            try {
                xmlHttp = new ActiveXObject("Msxml2.XMLHTTP");
            } catch (e) {
                try {
                    xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
                } catch (e) {}
            }
        }
}
function handleStateChange(){
	if(xmlHttp.readyState == 4){
		if(xmlHttp.status == 200){
			if("changesel" == actiontype){
				upstateAcctItemList();
			}else if("addfee" == actiontype){
				updateFeeList();
			}else if("addpay" == actiontype){
				updatePayList();
			}else if("submit" == actiontype){
				forwardPage();
			}
		}
	}
}
function forwardPage(){
	self.location.href = "return.screen"; 
}
function upstateAcctItemList(){	  
	  var xmlDoc = xmlHttp.responseXML.getElementsByTagName("acctitemtype");	  
	  var acctItemType = document.all.txtAcctItemType;
	  acctItemType.options.length = 1;	  	
	  for(var i=0;i<xmlDoc.length;i++){
		var typeid = xmlDoc[i].getElementsByTagName("typeid")[0].firstChild.nodeValue;		
		var typename = xmlDoc[i].getElementsByTagName("typename")[0].firstChild.nodeValue;		
		acctItemType.add(new Option(typename,typeid));
	  }
}
function updateFeeList(){
	 var xmlDoc = xmlHttp.responseXML.getElementsByTagName("fee");
	 var datalist = new Array(4); 
	 
	 datalist[0] = xmlDoc[0].getElementsByTagName("feetype")[0].firstChild.nodeValue;
	 datalist[1] = xmlDoc[0].getElementsByTagName("acctitemtype")[0].firstChild.nodeValue;
	 if(xmlDoc[0].getElementsByTagName("productid")[0].firstChild == null)
	 	datalist[2] = "";
	 else
	 	datalist[2] = xmlDoc[0].getElementsByTagName("productid")[0].firstChild.nodeValue;
	 datalist[3] = xmlDoc[0].getElementsByTagName("amount")[0].firstChild.nodeValue;
	 feeid++;
	 feecount++;
	 var id = "fee_" + feeid;
	 document.getElementById("feelist").appendChild(add_row(datalist,id));
	 var generalFeeTotal = document.all.generalFeeTotal.value;	 
	 if(datalist[0]=='P')
	 	document.all.forceFeeTotal.value = formatValue(eval(document.all.forceFeeTotal.value + "+" + datalist[3])+"",2);
	 else
	 	document.all.generalFeeTotal.value = formatValue(eval(generalFeeTotal + "+" + datalist[3])+"",2);
	 document.all.acctFeeSum.value = formatValue(eval(document.all.generalFeeTotal.value + "+" + document.all.forceFeeTotal.value)+"",2);
	 clearAddLine("fee");
}
function updatePayList(){
	var xmlDoc = xmlHttp.responseXML.getElementsByTagName("pay");
	var datalist = new Array(4);
	datalist[0] = xmlDoc[0].getElementsByTagName("mopid")[0].firstChild.nodeValue;
	if(xmlDoc[0].getElementsByTagName("ticketno")[0].firstChild == null)
		datalist[1] = "";
	else
		datalist[1] = xmlDoc[0].getElementsByTagName("ticketno")[0].firstChild.nodeValue;
	datalist[2] = xmlDoc[0].getElementsByTagName("value")[0].firstChild.nodeValue;
	datalist[3] = xmlDoc[0].getElementsByTagName("preductionvalue")[0].firstChild.nodeValue;
	payid++;
	paycount++;
	var id = "pay_" + payid;
	document.getElementById("paylist").appendChild(add_row(datalist,id));
	var generalPayTotal = document.all.generalPayTotal.value;
	document.all.generalPayTotal.value = formatValue(eval(generalPayTotal + "+" + datalist[2])+"",2);
	document.all.forcePayTotal.value = formatValue(eval(document.all.forcePayTotal.value + "+" + datalist[3])+"",2);
	document.all.acctPaySum.value = formatValue(eval(document.all.generalPayTotal.value + "+" + document.all.forcePayTotal.value)+"",2);
	clearAddLine("pay");
}
function clearAddLine(type){
	if(type == "fee"){
		document.all.txtFeeType.selectedIndex = 0;
		document.all.txtAcctItemType.options.length =1;
		document.all.txtAmount.value = "0.00";
		document.all.txtProductID.selectedIndex = 0;
	}else if(type == "pay"){
		document.all.txtMopID.selectedIndex = 0;
		document.all.txtTicketNo.value = "";
		document.all.txtValue.value = "0.00";
		document.all.txtPreDuctionValue.value = "0.00";
	}	
}
function add_row(datalist,id){
	var row = document.createElement("tr");
	var typeid = id.substr(0,3);
	if((typeid == "fee" && feecount % 2 == 0)||(typeid == "pay" && paycount%2 ==0 )){
		row.className = "list_bg2";
	}else {
		row.className = "list_bg1";
	}	
	row.onmouseover = function(){row.className = "list_over";};	
	if(row.className == "list_bg1"){
		row.onmouseout = function(){row.className = "list_bg1";};
	}else{
		row.onmouseout = function(){row.className = "list_bg2";};
	}	
	row.setAttribute("id",id);
	var cell;
	createCell(row,id,datalist);	
	row.appendChild(createCellWithButton(id));
	return row;	
}
function createCell(row,id,datalist){
	var cell = document.createElement("td");
	if(id.substr(0,3)=="fee"){
		var feetypetext = document.all.txtFeeType.options[document.all.txtFeeType.selectedIndex].text;
		var acctitemtypetext = document.all.txtAcctItemType.options[document.all.txtAcctItemType.selectedIndex].text;	
		var producttext = document.all.txtProductID.options[document.all.txtProductID.selectedIndex].text;
		cell = createCellWithHidden("feename"+id,datalist[0]);		
		cell.appendChild(document.createTextNode(feetypetext));		
		row.appendChild(cell);
		cell = createCellWithHidden("acctname"+id,datalist[1]);
		cell.appendChild(document.createTextNode(acctitemtypetext));
		row.appendChild(cell);
		cell = createCellWithHidden("productname"+id,datalist[2]);
		if(document.all.txtProductID.value == "")
			cell.appendChild(document.createTextNode(""));
		else
			cell.appendChild(document.createTextNode(producttext));
		row.appendChild(cell);
		cell = createCellWithText(datalist[3]);
		row.appendChild(cell);
			
	}else if(id.substr(0,3) == "pay"){
		var moptext = document.all.txtMopID.options[document.all.txtMopID.selectedIndex].text;
		cell = createCellWithHidden("mopname"+id,datalist[0]);		
		cell.appendChild(document.createTextNode(moptext));		
		row.appendChild(cell);
		for(var i = 1;i<datalist.length;i++){
			cell = createCellWithText(datalist[i]);
			row.appendChild(cell);
		}
	}
}
function createCellWithText(text){
	var cell = document.createElement("td");
	var textNode = document.createTextNode(text);
	cell.appendChild(textNode);
	cell.align = "center";
	return cell;
}
function createCellWithHidden(name,value){
	var cell = document.createElement("td");
	var hiddenNode = document.createElement("input");
	hiddenNode.setAttribute("type","hidden");
	hiddenNode.setAttribute("name",name);
	hiddenNode.setAttribute('value',value);
	cell.appendChild(hiddenNode);
	cell.align = "center";
	return cell;
}
function createCellWithButton(id){
	var cell = document.createElement("td");
	var bton = document.createElement("input");
	bton.setAttribute('value',"删除");
	bton.setAttribute("name","del"+id);	
	bton.setAttribute("type","button");
	bton.className = "button";
	bton.onclick = function(){del_row(id)};
	cell.appendChild(bton);
	cell.align = "center";
	return cell;
}
function add_fee(){
	if(!checkFee()){
		return false;
	}	
	actiontype = "addfee";	
        var queryStr  = "txtFeeType="+document.all.txtFeeType.value + "&txtAcctItemType=" + document.all.txtAcctItemType.value
        		+ "&txtProductID=" + document.all.txtProductID.value + "&txtAmount=" + document.all.txtAmount.value
        		+ "&actiontype=" + actiontype;
        sendRequest(queryStr,"data_prepare.screen",true);
}
function add_pay(){
	if(!checkPay()){
		return false;
	}
	actiontype = "addpay";
	var queryStr  = "txtMopID=" + document.all.txtMopID.value + "&txtTicketNo=" + document.all.txtTicketNo.value
        		+ "&txtValue=" + document.all.txtValue.value + "&txtPreDuctionValue=" + document.all.txtPreDuctionValue.value
        		+ "&actiontype=" + actiontype;
        sendRequest(queryStr,"data_prepare.screen",true);
}
function del_row(id){
	var listname = (id.substr(0,3) == "fee"?"feelist":"paylist");
	var listrow = document.getElementById(listname);	
	var delrow = document.getElementById(id);

	listrow.removeChild(delrow); 
	if(listname == "feelist"){		
		if(delrow.firstChild.firstChild.value == "P")
			document.all.forceFeeTotal.value = formatValue(eval(document.all.forceFeeTotal.value*1 - delrow.lastChild.previousSibling.firstChild.nodeValue*1)+"",2);
		else
			document.all.generalFeeTotal.value = formatValue(eval(document.all.generalFeeTotal.value*1 - delrow.lastChild.previousSibling.firstChild.nodeValue*1)+"",2);
		document.all.acctFeeSum.value = formatValue(eval(document.all.generalFeeTotal.value + "+" + document.all.forceFeeTotal.value)+"",2);
	}else if(listname == "paylist"){		
		document.all.generalPayTotal.value = formatValue(eval(document.all.generalPayTotal.value*1 - delrow.lastChild.previousSibling.previousSibling.firstChild.nodeValue*1)+"",2);
		document.all.forcePayTotal.value = formatValue(eval(document.all.forcePayTotal.value*1 - delrow.lastChild.previousSibling.firstChild.nodeValue*1)+"",2);
		document.all.acctPaySum.value = formatValue(eval(document.all.generalPayTotal.value + "+" + document.all.forcePayTotal.value)+"",2);
	}
	updateListStyle(listname);
}
function checkFee(){
	if(check_Blank(document.frmPost.txtFeeType,true,"费用类型")){
		return false;
	}
	if(check_Blank(document.frmPost.txtAcctItemType,true,"账目类型")){
		return false;
	}
	if(check_Blank(document.frmPost.txtAmount, true, "费用金额")||!check_Float(document.frmPost.txtAmount,true,"费用金额")){		
		return false;
	}else if(document.frmPost.txtAmount.value == 0){
		alert("费用金额不是有效数值！");
		document.frmPost.txtAmount.focus();
		return false;
	}
	return true;
}
function checkPay(){
	if(check_Blank(document.frmPost.txtMopID,true,"支付方式")){
		return false;
	}
	if(check_Blank(document.frmPost.txtValue,true, "支付金额")||check_Blank(document.frmPost.txtPreDuctionValue,true, "预存金额") ||!check_Float(document.frmPost.txtValue,true,"支付金额")||!check_Float(document.frmPost.txtPreDuctionValue,true,"预存金额")){		
		return false;
	}else if(document.frmPost.txtValue.value == 0 && document.frmPost.txtPreDuctionValue.value == 0){
		alert("支付金额不是有效数值！");
		document.frmPost.txtValue.focus();
		return false;
	}
	if(check_Blank(document.frmPost.txtPreDuctionValue,true, "预存金额")||!check_Float(document.frmPost.txtPreDuctionValue,true,"预存金额")){
		return false;
	}
	return true;
}
function check_frm(){

	if(check_Blank(document.frmPost.acctFeeSum,true, "应收金额")||!check_Float(document.frmPost.acctFeeSum,true,"应收金额")){
		return false;
	}
	if(check_Blank(document.frmPost.acctPaySum,true, "支付金额")|| !check_Float(document.frmPost.acctPaySum,true,"支付金额")){
		return false;
	}
	if(document.frmPost.acctFeeSum.value != document.frmPost.acctPaySum.value){
		alert("支付金额与应收金额不符！");
		return false;
	}
	if(document.frmPost.forceFeeTotal.value != document.frmPost.forcePayTotal.value){
		alert("强制预存金额不符！");
		return false;
	}
	if(document.frmPost.acctFeeSum.value == 0 ){
		alert("费用不是有效金额！");
		return false;
	}
	return true;
}
function frm_submit(){
	if(check_frm()){
		/*actiontype = "submit";
		var strxml = "xml=" + prepareXML() + "&txtCustomerID=" + document.frmPost.txtCustomerID.value + "&txtAccountID=" + document.frmPost.txtAccountID.value;
		sendRequest(strxml,"account_manual_fee.do",false);*/
		var strxml = "xml=" + prepareXML();
        //20140222
        strxml+=("&fapiao_serialno="+document.frmPost.fapiao_serialno.value+"&fapiao_haoma="+document.frmPost.fapiao_haoma.value)
		document.frmPost.action = "account_manual_fee.do?"+strxml;	
		document.frmPost.submit();
	}
}
function prepareXML(){
	var xml = "<result>";
	var feerows = document.getElementById("feelist");
	var feechild = feerows.firstChild;
	while(feechild !=null ){
		xml += "<feelist>";
		xml += "<feetype>" + feechild.firstChild.firstChild.value + "<\/feetype>";
		xml += "<acctitemtype>" + feechild.firstChild.nextSibling.firstChild.value + "<\/acctitemtype>";
		xml += "<productid>" + feechild.firstChild.nextSibling.nextSibling.firstChild.value + "<\/productid>";
		xml += "<amount>" + feechild.lastChild.previousSibling.firstChild.nodeValue + "<\/amount>";
		xml += "<\/feelist>";
		feechild = feechild.nextSibling;		
	} 
	
	var payrows = document.getElementById("paylist");
	var paychild = payrows.firstChild;
	while(paychild != null){
	 	xml += "<paylist>";
		xml += "<mopid>" + paychild.firstChild.firstChild.value + "<\/mopid>";
		xml += "<ticketno>" + paychild.firstChild.nextSibling.firstChild.nodeValue +"<\/ticketno>";
		xml += "<value>" + paychild.firstChild.nextSibling.nextSibling.firstChild.nodeValue + "<\/value>";
		xml += "<preductionvalue>" + paychild.lastChild.previousSibling.firstChild.nodeValue + "<\/preductionvalue>";
		xml += "<\/paylist>";
		paychild = paychild.nextSibling;
	}
	xml += "<\/result>";
	return xml;	
}
function updateListStyle(listname){
	var pflist = document.getElementById(listname);
	if(pflist != null && pflist.firstChild != null){
		var pfchild = pflist.firstChild;
		var i = 0;
		while(pfchild != null){			
			if( i%2 == 0 ){
				pfchild.className = "list_bg1";	
				pfchild.onmouseover = function(){this.className = "list_over";};
				pfchild.onmouseout = function(){this.className = "list_bg1";};
			}else{	
				pfchild.className = "list_bg2";
				pfchild.onmouseover = function(){this.className = "list_over";};
				pfchild.onmouseout = function(){this.className = "list_bg2";};
			}
			i++;
			pfchild = pfchild.nextSibling;
		}
	}	
}
function selectFapiao(){
    var  strFeatures = "width=650px,height=500px,resizable=no,toolbar=no,scrollbars=yes,status=yes";
    var res=showModalDialog("<%=request.getContextPath()%>/fapiao/template.jsp","发票号码检索",strFeatures);
    var arrRes=res.split(',');
    document.getElementById("fapiao_serialno").value=arrRes[1];
    document.getElementById("fapiao_haoma").value=arrRes[2];
}
function formatValue(srcStr,nAfterDot){
	var resultStr,nTen;
　　    strLen = srcStr.length;
　　    dotPos = srcStr.indexOf(".");
　　    if (dotPos == -1){
	　　　　resultStr = srcStr+".";
	　　　　for (i=0;i<nAfterDot;i++){
	　　　　	resultStr = resultStr+"0";
	　　　　}
	　　　　	return resultStr;
　　    }else{
　　　　        if ((strLen - dotPos - 1) >= nAfterDot){
　　　　　　        nAfter = dotPos + nAfterDot + 1;
　　　　　　        nTen =1;
　　　　　　        for(j=0;j<nAfterDot;j++){
　　　　　　　　        nTen = nTen*10;
　　　　　　        }
　　　　　　        resultStr = Math.round(parseFloat(srcStr)*nTen)/nTen;
　　　　　　        return resultStr;
　　　　        }else{
　　　　　　        resultStr = srcStr;
　　　　　　        for (i=0;i<(nAfterDot - strLen + dotPos + 1);i++){
　　　　　　　　        resultStr = resultStr+"0";
　　　　　　        }
　　　　　　        return resultStr;
　　　　        }
　　   }
} 

</SCRIPT>

<table width="820" align="center" cellpadding="0" cellspacing="0">
<tr> 
<td align="center" valign="top">
	<table  border="0" align="center" cellpadding="0" cellspacing="10">
      <tr>
        <td><img src="img/list_tit.gif" width="19" height="19"></td>
        <td class="title">手工收费</td>
      </tr>
	</table>
<table width="100%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
      <tr>
         <td><img src="img/mao.gif" width="1" height="1"></td>
      </tr>
</table>
<br>

<form name="frmPost" method="post" action="account_manual_fee.do">
   	     
<table align="center" width="100%" border="0" cellspacing="1" cellpadding="5" class="fulltable" > 
	<lgc:bloop requestObjectName="ResponseQueryResult" item="oneline">
	<%com.dtv.oss.dto.wrap.customer.Account2AddressWrap wrap = (com.dtv.oss.dto.wrap.customer.Account2AddressWrap)pageContext.getAttribute("oneline");
	  int custID = wrap.getAcctDto().getCustomerID();
	  Map mapPayType = Postern.getOpeningPaymentMop(CommonKeys.CUSTSERVICEINTERACTIONTYPE_MB);
	  Map mapProduct = Postern.getProductByAccountID(wrap.getAcctDto().getAccountID());
	  pageContext.setAttribute("oneline", wrap.getAcctDto());
	  pageContext.setAttribute("mapPayType",mapPayType);
	  pageContext.setAttribute("mapProduct",mapProduct);
	%>
	<tr>  
		<td class="list_bg2"><div align="right">客户证号</div></td>
		<td class="list_bg1">
			<input type="text" name="txtCustomerID" size="25"  value="<tbl:write name="oneline" property="customerID" />" class="textgray" readonly   >
		</td>
		<td class="list_bg2"><div align="right">客户姓名</div></td>
		<td class="list_bg1">
			<input type="text" name="txtCustomerName" size="25"  value="<%=Postern.getCustomerNameById(custID)%>"	class="textgray" readonly   >
		</td>
	</tr>
	<tr>
		<td class="list_bg2"><div align="right">帐&nbsp&nbsp号</div></td>
		<td class="list_bg1">
			<input type="text" name="txtAccountID" size="25" value="<tbl:write name="oneline" property="accountID"/>" class="textgray" readonly />
		</td> 
		<td class="list_bg2"><div align="right">帐户名称</div></td>
		<td class="list_bg1">
			<input type="text" name="txtAccountName" size="25" value="<tbl:write name="oneline" property="accountName"/>" class="textgray" readonly />
		</td>
	</tr>
	<tr>
		<td class="list_bg2"><div align="right">帐户类型</div></td>
		<td class="list_bg1">
			<input type="text" name="txtAccountType" size="25" value="<o:getcmnname typeName="SET_F_ACCOUNTTYPE" match="oneline:accountType" />" class="textgray" readonly />
		</td>
		<td class="list_bg2"><div align="right">帐户状态</div></td>
		<td class="list_bg1">
			<input type="text" name="txtStatus" size="25" value="<o:getcmnname typeName="SET_F_ACCOUNTSTATUS" match="oneline:status" />" class="textgray" readonly />
		</td>
	</tr>
	<tr>
		<td class="list_bg2"><div align="right">付费类型</div></td>
		<td class="list_bg1">
			<input type="text" name="txtPayType" size="25" value="手工付费" class="textgray" readonly />
		</td>
		<td class="list_bg2"><div align="right">预存现金</div></td>
		<td class="list_bg1">
			<input type="text" name="txtCashDeposit" value="<tbl:write name="oneline" property="cashDeposit"/>" size="25" class="textgray" readonly/>
		</td>
	</tr>
	<tr>
		<td class="list_bg2"><div align="right">预存虚拟货币</div></td>
		<td class="list_bg1" colspan="3">
			<input type="text" name="txtTokenDeposit" value="<tbl:write name="oneline" property="tokenDeposit"/>" size="25" class="textgray" readonly/>
		</td>
	</tr>
	</lgc:bloop> 
</table>
<table align="center" width="100%" border="0" cellspacing="1" cellpadding="3" class="list_bg" >
	<tr> <!--20140222 手动收费添加发票信息--><%String fapiaostr=Postern.getFapiaoSerialNo();
        String fapiaodaima="";
        String fapiaohaoma="";
     if(fapiaostr!=null&&fapiaostr.split("-").length==2){
         fapiaodaima=fapiaostr.split("-")[0];
         fapiaohaoma=fapiaostr.split("-")[1];
     }
    %>
        	<td  class="import_tit" align="center"><font size="3">费用清单</font></td>
               <td colspan="1" class="import_tit"><input type="button" value="选取发票" onclick="selectFapiao()"/></td>
             <td colspan="2" class="import_tit"><label style="color:red">发票代码*</label> <input type="text" id="fapiao_serialno" name="fapiao_serialno" value="<%=fapiaodaima%>"/></td>
            <td colspan="1" class="import_tit"><label style="color:red">发票号码*</label> <input type="text" id="fapiao_haoma" name="fapiao_haoma"  value="<%=fapiaohaoma%>"/></td>
    	</tr>
    	<tr class="list_head">
 		<td  class="list_head"  width="10%">费用类型*</td>
		<td  class="list_head"  width="10%">账目类型*</td>
		<td  class="list_head"  width="8%">产品</td>
		<td  class="list_head"  width="10%">金额(元)*</td>
		<td  class="list_head"  width="10%">操作</td>
       </tr>
       <tbody id="feelist" > 
       </tbody>
       <tr id = "feeadd" class="list_bg1">
		<td valign="middle" align="left">
			<d:selcmn name="txtFeeType" mapName="SET_F_BRFEETYPE" width="20" match="txtFeeType" onchange="javascript:changeAcctItemType()" style='width:120px;'/>
			
		</td>
		<td valign="middle" align="left">			
			<tbl:select name="txtAcctItemType" set="" width="20" match="txtAcctItemType" style='width:120px;'/>
			
		</td>
		<td valign="middle" align="left">
			<tbl:select name="txtProductID" set="mapProduct" match="txtProductID" width="20" match="" style='width:120px;'/>
		</td>
		<td valign="middle" align="center">
			<input type='text' name="txtAmount" value="0.00">
		</td>
		<td valign="middle" align="center">
			<input type="button" name="addfeebutton" value="增加" onClick="javascript:add_fee();" class="button">
		</td>
       </tr>
</table>
<table align="center" width="100%" border="0" cellspacing="1" cellpadding="5" class="fulltable" >
	<tr>
		<td class="list_bg2"><div align="right">应收金额总计：</div></td>
		<td class="list_bg1">
			<input type="text" size="12" name="generalFeeTotal" value="0.00" class="textgray" readonly>
		</td>
		<td class="list_bg2"><div align="right">强制预存总计：</div></td>
		<td class="list_bg1">
			<input type="text" size="12" name="forceFeeTotal" value="0.00" class="textgray" readonly>
		</td>
		<td class="list_bg2"><div align="right">应收金额总计：</div></td>
		<td class="list_bg1">
			<input type="text" size="12" name="acctFeeSum" value="0.00" class="textgray" readonly>
		</td>
	</tr>
	<tr>
		<td class="list_bg2"><div align="right">支付金额总计：</div></td>
		<td class="list_bg1">
			<input type="text" size="12" name="generalPayTotal" value="0.00" class="textgray" readonly>
		</td>
		<td class="list_bg2"><div align="right">强制预存总计：</div></td>
		<td class="list_bg1">
			<input type="text" size="12" name="forcePayTotal" value="0.00" class="textgray" readonly>
		</td>
		<td class="list_bg2"><div align="right">支付金额总计：</div></td>
		<td class="list_bg1">
			<input type="text" size="12" name="acctPaySum" value="0.00" class="textgray" readonly>
		</td>
	</tr>
</table>
<table align="center" width="100%" border="0" cellspacing="1" cellpadding="3" class="list_bg" >
    	<tr> 
        	<td colspan="5" class="import_tit" align="center"><font size="3">支付清单</font></td>
    	</tr>
    	<tr class="list_head">
          <td  class="list_head"  width="10%">支付方式</td>
          <td  class="list_head"  width="10%">票据编号</td>
          <td  class="list_head"  width="8%">费用支付金额</td>
          <td  class="list_head"  width="10%">预存金额</td>
          <td  class="list_head"  width="10%">操作</td>
       </tr>
       <tbody id="paylist" >
       </tbody>
       <tr id = "payadd" class="list_bg1">
	  <td valign="middle" align="left">
		<d:selcmn name="txtMopID" set="mapPayType" width="20" match="txtMopID"  style='width:120px;'/>
	  </td>
	  <td valign="middle" align="left">
		<input type="text" name="txtTicketNo" value="">
	  </td>
	  <td valign="middle" align="left">
		<input type="text" name="txtValue" value="0.00">
	  </td>
	  <td valign="middle" align="center">
		<input type="text" name="txtPreDuctionValue" value="0.00">
	  </td>
	  <td valign="middle" align="center">
		<input type="button" name="addpaybutton" value="增加" onClick="javascript:add_pay();" class="button">
	  </td>
	</tr>	
</table>
<br>  
<table align="center" border="0" cellspacing="0" cellpadding="0">
        
           <td width="20" ></td> 
           <td><img src="img/button2_r.gif" border="0" width=22 height=20 ></td>
           <td background="img/button_bg.gif"><a href="javascript:back_submit()" class="btn12">返回</a></td>
	   <td><img src="img/button2_l.gif" border="0" width=11 height=20 ></td>           

				
	   <td width="20" ></td>           
           <td><img src="img/button_l.gif" border="0" ></td>
           <td background="img/button_bg.gif"><a href="javascript:frm_submit()" class="btn12">确定</a></td>
           <td><img src="img/button_r.gif" border="0" ></td>
    </tr>

</table>
   <tbl:generatetoken />     

</form>


