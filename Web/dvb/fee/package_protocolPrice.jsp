<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="resultset" prefix="rs" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="osstags" prefix="d" %>

<%@ page import="com.dtv.oss.web.util.CommonKeys" %>
<%@ page import="com.dtv.oss.util.Postern" %>
<%@ page import="com.dtv.oss.util.TimestampUtility" %>
<%@ page import="java.util.*" %>
<%@ page import="com.dtv.oss.dto.ProtocolDTO,
                 com.dtv.oss.dto.ProductPackageDTO" %>
 
<div id="updselwaitlayer" style="position:absolute; left:400px; top:20px; width:150px; height:24px; display:none">
	<table width="100%" border="0" cellspacing="1" cellpadding="3">
		<tr>
			<td width="100%" align="center">
				<font size="2"> 正在获取内容。。。 </font>
			</td>
		</tr>
	</table>
</div>
<iframe SRC="update_select.screen" name="FrameUS" width="0" height="0" frameborder="0" scrolling="0">
</iframe> 
<table align="center" width="100%" border="0" cellpadding="6" cellspacing="1" class="list_bg">
    <tr class="list_head">
       <td class="list_head" width="30%" align="center">产品包</td>
       <td class="list_head" width="20%" align="center">账目类型</td>
       <td class="list_head" width="20%" align="center">费用科目</td>
       <td class="list_head" width="20%" align="center">单价</td>
       <td class="list_head" width="10%" align="center">操作</td>
    </tr>
</table>
<%
   Map packageMap=Postern.getAllProductPackageIDAndName();
   Map brfeeMap =Postern.getCommonDateKeyAndValueMap("SET_F_BRFEETYPE");
   Map feeTypeMap=Postern.getAllFeeType();
   String packagemapKey ="";
   String packagemapValue ="";
   java.sql.Timestamp currentDate =TimestampUtility.getSystemDate();
  
   Iterator packageItr =((Map)packageMap).entrySet().iterator();
   String packageString ="<select name='packageId'><option value='' >-----------------</option>";
   while(packageItr.hasNext()){
      Map.Entry item = (Map.Entry)packageItr.next();
      ProductPackageDTO dto =Postern.getProductPackageByPackageID(Integer.parseInt(item.getKey().toString()));
      if ("B".equals(dto.getPackageClass())) continue;
      if (currentDate.before(dto.getDateFrom()) || currentDate.after(dto.getDateTo())) continue;
      packageString =packageString+"<option value='"+item.getKey().toString()+"'>"+(String)item.getValue()+"</option>";
      if (packagemapKey.equals("")){
          packagemapKey =item.getKey().toString();
          packagemapValue =(String)item.getValue();
      }else {
        	packagemapKey =packagemapKey+";"+item.getKey().toString();
      	  packagemapValue =packagemapValue +";"+(String)item.getValue();
      }
   }
   packageString =packageString+"</select>";
   
   String feeTypemapKey ="";
   String feeTypemapValue ="";
   Iterator feeTypeItr =((Map)feeTypeMap).entrySet().iterator();
   while(feeTypeItr.hasNext()){
      Map.Entry item = (Map.Entry)feeTypeItr.next();
      if (feeTypemapKey.equals("")){
          feeTypemapKey =item.getKey().toString();
          feeTypemapValue =(String)item.getValue();
      }else{
      	  feeTypemapKey =feeTypemapKey+";"+item.getKey().toString();
        	feeTypemapValue =feeTypemapValue +";"+(String)item.getValue();
      }
   }
  
   String brfeemapKey ="";
   String brfeemapValue =""; 
   int    package_rownum =0;
   String packageTableBody ="";
%>

<span id="packageList">
  <table align="center" width="100%" border="0" cellpadding="6" cellspacing="1" class="list_bg">
  <%     
      String txtCustomerID =request.getParameter("txtCustomerID");
      Collection protocolDtoCol =Postern.getProtocolDTOCol(Integer.parseInt(txtCustomerID));

      String styletr ="";
      if (protocolDtoCol !=null){ 
        Iterator protocolItr =protocolDtoCol.iterator();
        while (protocolItr.hasNext()){
             ProtocolDTO dto =(ProtocolDTO)protocolItr.next();
             String packageName =(String)packageMap.get(String.valueOf(dto.getProductPackageID()));
             String brfeeName =(String)brfeeMap.get(dto.getFeeType());
             String feeTypeName =(String)feeTypeMap.get(dto.getAcctitemTypeID());
                          
             if ( package_rownum % 2 ==0 ){
                styletr  ="list_bg1";  
	           } else {
	              styletr  ="list_bg2";
	           }

	           packageTableBody =packageTableBody +"<tr id='"+package_rownum+"'  class='"+styletr+"' >"    	
	                        +"  <input type='hidden' name='tr_realid' value='"+package_rownum+"'>"
	                        +"  <td align='center' width='30%'><input type='hidden' name='packageId' value='"+dto.getProductPackageID()+"'>"+packageName+"</td>" 							 							
 	                        +"  <td align='center' width='20%'><input type='hidden' name='brfeeId' value='"+dto.getFeeType()+"'>"+brfeeName+"</td>"
 	                        +"  <td align='center' width='20%'><input type='hidden' name='feeTypeId' value='"+dto.getAcctitemTypeID()+"'>"+feeTypeName+"</td>"
 	                        +"  <td align='center' width='20%'><input type='text' name='singlePrice' size='12' style='text-align:right' value="+dto.getSinglePrice()+" readonly='true' class='textgray'></td>"	
	                        +"  <td align='center' width='10%'><input type='button' name='delbutton' value='删除' onClick='javascript:del_Text("+package_rownum+");' class='button'></td>"
 	                        +"  </tr>"  ;
 	           package_rownum=package_rownum+1;
         }
         
    %>
         <%=packageTableBody%>
    <%
      }
      if (package_rownum % 2 ==0 ){
           styletr  ="list_bg1";  
	    } else {
	         styletr  ="list_bg2";
	    }
	    
	    Iterator brfeeItr =((Map)brfeeMap).entrySet().iterator();
      String brfeeString ="<select name='brfeeId' onchange='ChangeFeeType("+package_rownum+")'><option value='' >-----------------</option>";
      while(brfeeItr.hasNext()){
         Map.Entry item = (Map.Entry)brfeeItr.next();
         brfeeString =brfeeString+"<option value='"+item.getKey().toString()+"'>"+(String)item.getValue()+"</option>";
         if (brfeemapKey.equals("")){
            brfeemapKey =item.getKey().toString();
            brfeemapValue =(String)item.getValue();
         }else {
        	  brfeemapKey =brfeemapKey+";"+item.getKey().toString();
        	  brfeemapValue =brfeemapValue +";"+(String)item.getValue();
         }
      } 
      brfeeString =brfeeString+"</select>"; 
    %>
    	<tr id='<%=package_rownum%>'  class='<%=styletr%>' >
    		<input type="hidden" name="tr_realid" value='<%=package_rownum%>'>
        <td align="center" width="30%"><%=packageString%></td>
        <td align="center" width="20%"><%=brfeeString%></td>
        <td align="center" width="20%"><select name='feeTypeId'><option value='' >-----------------</option></select></td>
        <td align='center' width='20%'><input type='text' name='singlePrice' size='12' style='text-align:right' value=0.0 class='text'></td>
        <td align="center" width="10%"><input type='button' name='addbutton' value='增加' onClick='javascript:add_pay();' class='button'></td> 
      </tr>
    </table>
</span>  

<script language=javascript>
	 function ChangeFeeType(i){
		  var brfeeValue =document.getElementsByName("brfeeId")[i].value;
		  var feeTypeIdValue ="feeTypeId";
	    document.FrameUS.submit_update_select('feetype', brfeeValue, feeTypeIdValue, '');
	 }
	
	 var package_rownum =<%=package_rownum%>; 
   var tr_realid;
   var package_Id;
   var brfee_Id;
   var feeType_Id;
   var single_Price;
	 var package_TableBody="<%=packageTableBody%>"; 
   var package_tableHead="<table align='center' width='100%'  border='0' cellpadding='5' cellspacing='1' class='list_bg'>";
   var package_tableTie="</table>";
   var package_String ="<%=packageString%>";
   var brfee_mapKey ="<%=brfeemapKey%>";
   var brfee_mapValue ="<%=brfeemapValue%>"; 
   
   var package_mapKey ="<%=packagemapKey%>";
   var package_mapValue ="<%=packagemapValue%>";
   var feeType_mapKey ="<%=feeTypemapKey%>";
   var feeType_mapValue ="<%=feeTypemapValue%>";
   
      
   //支付函数
   function add_pay(){
   	   init();
       var column = maxColumn()-1;
     	 
       if (check_Blank(package_Id[column], true, "产品包"))
	        return false;
	     if (check_Blank(brfee_Id[column],true,"账目类型"))  
	        return false;
	     if (check_Blank(feeType_Id[column],true,"费用科目"))
	        return false;
	     if (check_Blank(single_Price[column],true,"单价"))
	        return false;
	     if (single_Price[column].value*1.0 <0){
           alert("单价金额不能为负数");
           single_Price[column].focus();
           return false;
       }
       
	        
	     package_name =package_Id[column].options[package_Id[column].selectedIndex].text;
	     brfee_name   =brfee_Id[column].options[brfee_Id[column].selectedIndex].text;
	     feeType_name =feeType_Id[column].options[feeType_Id[column].selectedIndex].text;
       
	     var styletr ="";
       if (package_rownum % 2 ==0 ){
           styletr  ="list_bg1";  
	     } else {
	         styletr  ="list_bg2";
	     } 
	      
	     var  tempBody="<tr id='"+package_rownum+"'  class='" +styletr+"' >";  	
       tempBody=tempBody+"<input type='hidden' name='tr_realid' value='"+package_rownum+"'>";
	     tempBody=tempBody+"<td align='center' width='30%'><input type='hidden' name='packageId' value='"+package_Id[column].value+"'>"+package_name+"</td>";   //产品包 							 							
 	     tempBody=tempBody+"<td align='center' width='20%'><input type='hidden' name='brfeeId'   value='"+brfee_Id[column].value +"'>"+brfee_name+"</td>";			//账目类型
	     tempBody=tempBody+"<td align='center' width='20%'><input type='hidden' name='feeTypeId' value='"+feeType_Id[column].value+"'>"+feeType_name+"</td>";   //费用科目
	     tempBody=tempBody+"<td align='center' width='20%'><input type='text'   name='singlePrice' value='"+single_Price[column].value+"' style='text-align:right' size='12' readonly='true' class='textgray'></td>";   //单价
	     tempBody=tempBody+"<td align='center' width='10%'><input type='button' name='delbutton' value='删除' onClick='javascript:del_Text("+ package_rownum +");' class='button'></td>";		//操作
 	     tempBody=tempBody+"</tr>"; 	    
	      
	     clear_payText(tr_realid[column].value);
	
	     package_rownum++;
	     if (package_rownum % 2 ==0 ){
          styletr  ="list_bg1";  
	     } else {
	        styletr  ="list_bg2";
	     }

	    package_TableBody=package_TableBody+tempBody+"<tr id='"+package_rownum+"'  class='" +styletr+"' >"; 
	    package_TableBody=package_TableBody+"<input type='hidden' name='tr_realid' value='"+package_rownum+"'>";  //序号
	    package_TableBody=package_TableBody
	                     +"<td align='center' width='30%'>"+ package_String +"</td>"                          //产品包
 	                     +"<td align='center' width='20%'>"+ make_SelectText(package_rownum)+"</td>"	        //账目类型
	                     +"<td align='center' width='20%'><select name='feeTypeId'><option value='' >-----------------</option>></select></td>"			//费用科目
	                     +"<td align='center' width='20%'><input type='text' name='singlePrice' size='12' style='text-align:right' value=0.0 class='text'></td>" //单价
	                     +"<td align='center' width='10%'><input type='button' name='addbutton' value='增加'  class='button' onClick='javascript:add_pay();'></td>"; //操作                       
 	    package_TableBody=package_TableBody+"</tr>";
 	    document.getElementById("packageList").innerHTML=package_tableHead+package_TableBody+package_tableTie;  
   }
   
   function maxColumn(){
      return document.getElementsByName("tr_realid").length;     
   }
   
   function make_SelectText(column){
   	   brfee_mapKeyStr =brfee_mapKey.split(";");
   	   brfee_mapValueStr =brfee_mapValue.split(";");
   	   brfee_mapString ="<select name='brfeeId' onChange='javaScript:ChangeFeeType("+column+");'><option value='' >-----------------</option>";  
   	   for (i=0; i< brfee_mapKeyStr.length; i++){
   	   	  brfee_mapString =brfee_mapString+"<option value='"+brfee_mapKeyStr[i]+"'>"+brfee_mapValueStr[i]+"</option>";
   	   }
   	   brfee_mapString =brfee_mapString+" </select>";
   	   return brfee_mapString;
   }
   
   function init(){
   	  tr_realid =document.getElementsByName("tr_realid");
   	  package_Id =document.getElementsByName("packageId");
      brfee_Id=document.getElementsByName("brfeeId");
      feeType_Id=document.getElementsByName("feeTypeId");
      single_Price =document.getElementsByName("singlePrice");
   }
   function clear_payText(rownum){
	   indexStart=0;
	   indexEnd=0;
	   startStr="<tr id='"+rownum+"'";
	   endStr="</tr>";
	   indexStart=package_TableBody.indexOf(startStr);
	   if(indexStart<0)  return;           
	   indexEnd=package_TableBody.indexOf(endStr,indexStart);
	   if (indexEnd<=0||indexEnd<indexStart) return;
	   indexEnd=indexEnd+5;
	
	   subStr=package_TableBody.substring(indexStart,indexEnd);
	   package_TableBody=package_TableBody.replace(subStr,"");
	   document.getElementById("packageList").innerHTML=package_tableHead+package_TableBody+package_tableTie;
   }
   
   function del_Text(rownum) {
   	  init();  	
   	   
	    var column = maxColumn()-1;
   	  var maxtrid =tr_realid[column].value; 
   	  	

   	  var tempBody ="";
   	  package_rownum=rownum;
	    for (p=rownum+1;p<column;p++){
	    	  packageid_value=package_Id[p].value;	  
	        package_name =getSelectValue("package", packageid_value);
	    	  brfeeid_value  =brfee_Id[p].value;
	    	  brfee_name   =getSelectValue("brfee" ,  brfeeid_value);
	    	 
	        feeTypeid_value =feeType_Id[p].value;
	        feeType_name =getSelectValue("feeType", feeTypeid_value);
         
	        var styletr ="";
          if (p % 2 ==0 ){
              styletr  ="list_bg1";  
	        } else {
	            styletr  ="list_bg2";
	        } 
	        
	        tempBody=tempBody+"<tr id='"+package_rownum+"'  class='" +styletr+"' >";  	
          tempBody=tempBody+"<input type='hidden' name='tr_realid' value='"+package_rownum+"'>";
	        tempBody=tempBody+"<td align='center' width='30%'><input type='hidden' name='packageId' value='"+packageid_value+"'>"+package_name+"</td>";   //产品包 							 							
 	        tempBody=tempBody+"<td align='center' width='20%'><input type='hidden' name='brfeeId'   value='"+brfeeid_value +"'>"+brfee_name+"</td>";			//账目类型
	        tempBody=tempBody+"<td align='center' width='20%'><input type='hidden' name='feeTypeId' value='"+feeTypeid_value+"'>"+feeType_name+"</td>";   //费用科目
	        tempBody=tempBody+"<td align='center' width='20%'><input type='text'   name='singlePrice' value='"+single_Price[p].value+"' style='text-align:right' size='12' readonly='true' class='textgray'></td>";   //单价
	        tempBody=tempBody+"<td align='center' width='10%'><input type='button' name='delbutton' value='删除' onClick='javascript:del_Text("+ package_rownum +");' class='button'></td>";		//操作
 	        tempBody=tempBody+"</tr>"; 	 
 	        package_rownum =package_rownum+1;
	    }
      
   	  var styletr ="";
      if (package_rownum % 2 ==0 ){
          styletr  ="list_bg1";  
	    } else {
	        styletr  ="list_bg2";
	    }
	    tempBody=tempBody +"<tr id='"+package_rownum+"' class='"+styletr+"'> " 
	                      +"<input type='hidden' name='tr_realid' value='"+package_rownum+"'> "
	                      +"<td align='center' width='30%'>"+copy_Select("packageId",package_Id[column])+"  </td>"
 	                      +"<td align='center' width='20%'>"+copy_brfeeId("brfeeId",brfee_Id[column],package_rownum) +" </td>"	
 	                      +"<td align='center' width='20%'>"+copy_Select("feeTypeId",feeType_Id[column]) +" </td>"				
	                      +"<td align='center' width='20%'><input name='singlePrice' type='text' size='12' style='text-align:right' value='"+single_Price[column].value+"'  class='text'></td>"			
	                      +"<td align='center' width='10%'><input type='button' name='addbutton' value='增加' onClick='javascript:add_pay();' class='button'></td>";
	    tempBody=tempBody+"</tr>";
	    
	    
	    /**清楚修改记录和它下方的记录 begin */ 
   	  for (k=rownum; k<=column; k++){
   	 	    clear_payText(k);
   	  }
   	  /**清楚修改记录下方的记录 end */
	    package_TableBody =package_TableBody+tempBody;
   	 
   	  document.getElementById("packageList").innerHTML=package_tableHead+package_TableBody+package_tableTie;
   
   } 
   
   
   function copy_Select(elementname,sel){
   	   var copyElement ="<select name='"+elementname+"'><option value='' >-----------------</option>";
       for(i=0;i<sel.options.length;i++){
          if(sel.options[i].selected){
          	  copyElement =copyElement+"<option value='"+sel.options[i].value+"' selected>"+sel.options[i].text+"</option>";
          }else{
          	  copyElement =copyElement+"<option value='"+sel.options[i].value+"' >"+sel.options[i].text+"</option>";
          }
       }
       return copyElement;
   }
   
   function copy_brfeeId(elementname,sel,column){
   	   var copyElement ="<select name='"+elementname+"' onChange='javaScript:ChangeFeeType("+column+");'><option value='' >-----------------</option>";
       for(i=0;i<sel.options.length;i++){
          if(sel.options[i].selected){
          	  copyElement =copyElement+"<option value='"+sel.options[i].value+"' selected>"+sel.options[i].text+"</option>";
          }else{
          	  copyElement =copyElement+"<option value='"+sel.options[i].value+"' >"+sel.options[i].text+"</option>";
          }
       }
       return copyElement;
   }
   
   function getSelectValue(elementname,elementKey){
   	   var elementKeyStr;
   	   var elementValueStr;
   	   if (elementname =='package'){
   	   	   elementKeyStr =package_mapKey.split(";");
   	   	   elementValueStr =package_mapValue.split(";");
   	   	  
   	   }
   	   if (elementname =='brfee'){
   	   	   elementKeyStr =brfee_mapKey.split(";");
   	   	   elementValueStr =brfee_mapValue.split(";");   	   	
   	   }
   	   if (elementname =='feeType'){
   	   	   elementKeyStr =feeType_mapKey.split(";");
   	   	   elementValueStr =feeType_mapValue.split(";");
   	   }
   	   
   	   for (j=0;j<elementKeyStr.length;j++){
   	   	    if (elementKeyStr[j] ==elementKey){
   	   	    	  return elementValueStr[j];
   	   	    }
   	   }
   	
   }
   
  
  //javascript格式化     格式化金额
function formatnumber(fnumber,fdivide,fpoint,fround){
	
    var fnum = fnumber + '';
    var revalue="";

    if(fnum==null){
        for(var i=0;i<fpoint;i++)revalue+="0";
        return "0."+revalue;
    }
    fnum = fnum.replace(/^\s*|\s*$/g,'');
    if(fnum==""){
        for(var i=0;i<fpoint;i++)revalue+="0";
        return "0."+revalue;
    }

    fnum=fnum.replace(/,/g,"");

    if(fround){
        var temp = "0.";
        for(var i=0;i<fpoint;i++)temp+="0";
        temp += "5";

        fnum = Number(fnum) + Number(temp);
        fnum += '';
    }

    var arrayf=fnum.split(".");

    if(fdivide){
        if(arrayf[0].length>3){
            while(arrayf[0].length>3){
                revalue=","+arrayf[0].substring(arrayf[0].length-3,arrayf[0].length)+revalue;
                arrayf[0]=arrayf[0].substring(0,arrayf[0].length-3);
            }
        }
    }
    revalue=arrayf[0]+revalue;

    if(arrayf.length==2&&fpoint!=0){
        arrayf[1]=arrayf[1].substring(0,(arrayf[1].length<=fpoint)?arrayf[1].length:fpoint);

        if(arrayf[1].length<fpoint)
            for(var i=0;i<fpoint-arrayf[1].length;i++)arrayf[1]+="0";
        revalue+="."+arrayf[1];
    }else if(arrayf.length==1&&fpoint!=0){
        revalue+=".";
        for(var i=0;i<fpoint;i++)revalue+="0";
    }

    return revalue;
}

</script>

 