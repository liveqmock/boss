<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="osstags" prefix="d" %>
<%@ taglib uri="bookmark" prefix="bk" %>
<%@ page import="com.dtv.oss.util.Postern" %>

<%@ page import="com.dtv.oss.dto.UserPointsCumulatedRuleDTO" %>
 


<script language=javascript>
function check_frm()
{
	
	
    if (check_Blank(document.frmPost.txtAddedPoints, true, "增加积分"))
	    return false;
     if (!check_Num(document.frmPost.txtAddedPoints, true, "增加积分"))
	    return false;
    if (check_Blank(document.frmPost.txtStatus, true, "状态"))
		return false;

   if (check_Blank(document.frmPost.txtCustTypeList, true, "客户类型"))
		return false;
   if (check_Blank(document.frmPost.txtProductID, true, "产品名称"))
		return false;
		
   if (check_Blank(document.frmPost.txtCondEvent, true, "事件类型"))
		return false;	
 if (check_Blank(document.frmPost.txtCondValue1, true, "积分上限"))
		return false;
		
   if (check_Blank(document.frmPost.txtCondValue2, true, "积分下限"))
		return false;	
  if (check_Blank(document.frmPost.txtDescription, true, "描述"))
		return false;			
if (!check_Float(document.frmPost.txtCondValue1, true, "积分上限"))
	    return false;	 
 if (!check_Float(document.frmPost.txtCondValue2, true, "积分下限"))
	    return false;  
	    
if(document.frmPost.txtCondValue1.value<document.frmPost.txtCondValue2.value){
 	
            alert("积分下限不能超过积分上限");
         	 return false
   }  		
	 	 

	return true;
}
 function cumulate_edit_submit(){
  if (check_frm()){
	    document.frmPost.action="edit_cumulate_rule.do";
	    document.frmPost.Action.value="update";
	    document.frmPost.submit();
	  }
}
function open_select(type,typeName,typeValue,parentType,parentTypeName,parentTypeValue){
	var param="batch_query_select.screen?";
	param=param + "type="+type;
	param=param + "&typeName="+typeName;
	param=param + "&typeValue=" + document.frmPost.elements(typeName).value;
	param=param + "&parentType="+parentType;
	param=param + "&parentTypeName="+parentTypeName;
	
	if(parentTypeName!=null && parentTypeName!="")
		param=param + "&parentTypeValue="+document.frmPost.elements(parentTypeName).value;
		
	var windowFeatures="toolbar=no,location=no,status=no,menubar=no,scrollbars=yes,width=330,height=250,screenX=340,screenY=270";

	window.open(param,"",windowFeatures);
} 

</script>
<table  border="0" align="center" cellpadding="0" cellspacing="10" >
  <tr>
    <td><img src="img/list_tit.gif" width="19" height="19"></td>
    <td class="title">修改积分累加规则</td>
  </tr>
</table>
<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>
<br>
 
<form name="frmPost" method="post" >   
 <lgc:bloop requestObjectName="ResponseQueryResult" item="oneline" IsOne="true"> 
  <%
   UserPointsCumulatedRuleDTO dto = (UserPointsCumulatedRuleDTO) pageContext.findAttribute("oneline");
     Map envetMap = null;
     envetMap=Postern.getAllSystemEvent();
     pageContext.setAttribute("EVENTNAME",envetMap);
     Map productMap = null;
     productMap = Postern.getAllProductIDAndName();
     pageContext.setAttribute("PRODUCTMAP",productMap);
      String custTypeList = dto.getCustTypeList();
         String totalValue="";
         System.out.println("the type of the customer is "+ custTypeList);
                 if(custTypeList!=null)
                 { 
                     String[] custArray=custTypeList.split(",");
                      System.out.println("the length of the array "+ custArray.length);
                     for(int j=0;j<custArray.length;j++){
                     System.out.println("every customertpye is "+ custArray[j]);
                     String value = Postern.getHashValueByNameKey("SET_C_CUSTOMERTYPE",custArray[j]);
                     System.out.println("*******the value******** "+ value);
                     if(totalValue=="")
                      totalValue=value;
                     else 
                       totalValue=totalValue+","+value;
                     }
                    
                 }
                 
                 
 %>    
 
 <table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
     <tr>  
	  <td class="list_bg2">流水号</td>
	     <td class="list_bg1"> 
	   <input type="text" name="txtID" size="25"  value="<tbl:write name="oneline" property="Id" />" class="textgray" readonly >
	    </td>
	    <td class="list_bg2">状态*</td>
	     <td class="list_bg1"> 
	     <d:selcmn name="txtStatus" mapName="SET_G_GENERALSTATUS" match="oneline:Status" width="23" />
	  </td>
	</tr>
         <tr>
	   <td class="list_bg2">增加积分*</td>
	   <td class="list_bg1">
		<input type="text" name="txtAddedPoints" maxlength="10" size="25" value="<tbl:write name="oneline" property="AddedPoints" />" >
	  </td>
	  <td valign="middle" class="list_bg2" align="left" >客户类型*</td>
	          <td  class="list_bg1">
	   	  <input name="txtCustTypeListValue" type="text" class="text" readonly maxlength="200" size="25" value="<%=totalValue%>" />
	   	  <input name="txtCustTypeList" type="hidden" value="<tbl:write name="oneline" property="custTypeList" />" >
	   	  <input name="checkbutton" type="button" class="button" value="请选择" onClick="javascript:open_select('SET_C_CUSTOMERTYPE','txtCustTypeList',document.frmPost.txtCustTypeList.value,'','','')"> 
	   </td>	   
	 
	</tr>
        <tr>   
		<td class="list_bg2">产品名称*</td>
		<td class="list_bg1"> 
		 <tbl:select name="txtProductID" set="PRODUCTMAP" match="oneline:ProductID" width="23" />
		 </td>
		 <td class="list_bg2"><div align="right">事件类型*</div></td>
	       <td class="list_bg1" align="left">
               <tbl:select name="txtCondEvent" set="EVENTNAME" match="oneline:CondEvent" width="23" />   
              </td>
		 
	</tr>
	 <tr>
	   <td class="list_bg2">积分上限*</td>
	   <td class="list_bg1">
		<input type="text" name="txtCondValue1" maxlength="16" size="25" max value="<tbl:write name="oneline" property="CondValue1" />" >
	  </td>
	 <td class="list_bg2">积分下限*</td>
	   <td class="list_bg1">
		<input type="text" name="txtCondValue2" maxlength="16" size="25" value="<tbl:write name="oneline" property="CondValue2" />" >
	 </td>
	 </tr>
	
	<tr>
		<td class="list_bg2">描述*</td>
		<td class="list_bg1" colspan="3"><font size="2">
			<textArea name="txtDescription" cols=80 rows=3 ><tbl:writeSpeChar name="oneline" property="descr"/></textarea>
		</font></td>
	</tr>
  </table>
 <table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
        <tr>
          <td><img src="img/mao.gif" width="1" height="1"></td>
        </tr>
      </table>
 
<input type="hidden" name="func_flag" value="4" >
<input type="hidden" name="Action" value="">
<input type="hidden" name="txtDtLastmod"   value="<tbl:write name="oneline" property="DtLastmod" />">
  
<br>

     <table align="center" border="0" cellspacing="0" cellpadding="0">
        <tr>
           <td><img src="img/button2_r.gif" width="22" height="20"></td>
            <td background="img/button_bg.gif"><a href="<bk:backurl property="query_points_acumulate_rule.do" />" class="btn12">返&nbsp;回</a></td>
            <td><img src="img/button2_l.gif" width="11" height="20"></td>
                      
            <td width="20" ></td>  
            <td><img src="img/button_l.gif" width="11px" height="20px"></td>
            <td><input name="Submit" type="button" class="button"
	    value="修&nbsp; 改" onclick="javascript:cumulate_edit_submit()"></td>
            <td><img src="img/button_r.gif" width="22px" height="20px"></td>    
             
           
        </tr>
      </table>   
      
       <br>      
 </lgc:bloop>  
</form>
