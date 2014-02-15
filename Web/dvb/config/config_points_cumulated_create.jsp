<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="osstags" prefix="d" %>
<%@ taglib uri="bookmark" prefix="bk" %>
<%@ page import="com.dtv.oss.util.Postern" %>
 


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
	
	if (!check_Float(document.frmPost.txtCondValue1, true, "积分上限"))
	    return false;	 
 if (!check_Float(document.frmPost.txtCondValue2, true, "积分下限"))
	    return false;  
	    	
   if (check_Blank(document.frmPost.txtCondValue2, true, "积分下限"))
		return false;	
  if (check_Blank(document.frmPost.txtDescription, true, "描述"))
		return false;			
  if(document.frmPost.txtCondValue1.value*1<document.frmPost.txtCondValue2.value*1){
 	
            alert("积分下限不能超过积分上限");
         	 return false
   }  		

	return true;
}
 function cumulate_create_submit(){
  if (check_frm()){
	    document.frmPost.action="create_cumulate_rule.do";
	    document.frmPost.Action.value="create";
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
    <td class="title">创建积分累加规则</td>
  </tr>
</table>
<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>
<br>
 
<form name="frmPost" method="post" >   
 <tbl:generatetoken />  
  <%
     Map envetMap = null;
     envetMap=Postern.getAllSystemEvent();
     pageContext.setAttribute("EVENTNAME",envetMap);
     Map productMap = null;
     productMap = Postern.getAllProductIDAndName();
     pageContext.setAttribute("PRODUCTMAP",productMap);
 %>    
 
 <table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
         <tr>
	   <td class="list_bg2">增加积分*</td>
	   <td class="list_bg1">
		<input type="text" name="txtAddedPoints" size="25" value="<tbl:writeparam name="txtAddedPoints" />" >
	  </td>
	  <td valign="middle" class="list_bg2" align="left" >客户类型*</td>
	          <td  class="list_bg1">
	   	  <input name="txtCustTypeListValue" type="text"  readonly class="text" maxlength="200" size="25" value="<tbl:writeparam name="txtCustTypeListValue" />" >
	   	  <input name="txtCustTypeList" type="hidden" value="<tbl:writeparam name="txtCustTypeList" />" >
	   	  <input name="checkbutton" type="button" class="button" value="请选择" onClick="javascript:open_select('SET_C_CUSTOMERTYPE','txtCustTypeList',document.frmPost.txtCustTypeList.value,'','','')"> 
	   </td>	   
	 
	</tr>
        <tr>   
		<td class="list_bg2">产品名称*</td>
		<td class="list_bg1"> 
		 <tbl:select name="txtProductID" set="PRODUCTMAP" match="txtProductID" width="23" />
		 </td>
		 <td class="list_bg2"><div align="right">事件类型*</div></td>
	       <td class="list_bg1" align="left">
               <tbl:select name="txtCondEvent" set="EVENTNAME" match="txtCondEvent" width="23" />   
              </td>
		 
	</tr>
	 <tr>
	   <td class="list_bg2">积分上限*</td>
	   <td class="list_bg1">
		<input type="text" name="txtCondValue1" size="25" value="<tbl:writeparam name="txtCondValue1" />" >
	  </td>
	 <td class="list_bg2">积分下限*</td>
	   <td class="list_bg1">
		<input type="text" name="txtCondValue2" size="25" value="<tbl:writeparam name="txtCondValue2" />" >
	 </td>
	 </tr>
	 <tr>   
	    <td class="list_bg2">状态*</td>
	     <td class="list_bg1" colspan="3"> 
	     <d:selcmn name="txtStatus" mapName="SET_G_GENERALSTATUS" match="txtStatus" width="23" />
	  </td>
	</tr>
	<tr>
		<td class="list_bg2">描述*</td>
		<td  class="list_bg1" colspan="3"><font size="2">
			<textArea name="txtDescription" cols=80 rows=3 ><tbl:writeSpeChar name="txtDescription" /></textarea>
		</font></td>
	</tr>
  </table>
 <table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
        <tr>
          <td><img src="img/mao.gif" width="1" height="1"></td>
        </tr>
      </table>
 
<input type="hidden" name="func_flag" value="5074" >
<input type="hidden" name="Action" value="">
 <input type="hidden" name="confirm" value ="true"/>
<br>

     <table align="center" border="0" cellspacing="0" cellpadding="0">
        <tr>
             <td width="11" height="20"><img src="img/button2_r.gif"  height="20"></td>
            <td background="img/button_bg.gif"><a href="query_points_acumulate_rule.do?txtFrom=1&txtTo=10" class="btn12">返&nbsp;回</a></td>
            <td width="22" height="20"><img src="img/button2_l.gif"  height="20"></td>     	
           <td width="20" ></td>
          <td width="11" height="20"><img src="img/button_l.gif" height="20"></td>
          <td background="img/button_bg.gif"><a href="javascript:cumulate_create_submit()" class="btn12">确&nbsp; 认</a></td>
           <td width="22" height="20"><img src="img/button_r.gif" height="20"></td> 
         
         
	
	
        </tr>
      </table>   
      
      
 
</form>
