<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl"%>
<%@ taglib uri="logic" prefix="lgc"%>
<%@ taglib uri="resultset" prefix="rs"%>
<%@ taglib uri="/WEB-INF/oss.tld" prefix="d"%>
<%@ taglib uri="bookmark" prefix="bk" %>
<%@ page import="com.dtv.oss.util.Postern,java.util.*"%>
 

<SCRIPT language="JavaScript">
function check_form ()
{
    if (check_Blank(document.frmPost.txtConditionType, true, "计费条件类型"))
	    return false;
    if (check_Blank(document.frmPost.txtConditionName, true, "计费条件名称"))
	    return false;
   if (check_Blank(document.frmPost.txtStatus, true, "计费条件状态"))
	    return false;
  if (check_Blank(document.frmPost.txtCustTypeList, true, "计费条件内容"))
	    return false;	    
	return true;
}

function billing_condition_create()
{
    if (check_form())
    
    {    	
        document.frmPost.action="billing_condition_create.do";
        document.frmPost.Action.value="create";
        document.frmPost.submit();
    }
}

function back_submit(){
    document.location.href= "billing_condition_query.screen";
}

function open_select(typeName,typeValue,parentType,parentTypeName,parentTypeValue){
	var param="batch_query_select.screen?";
	
	if(document.frmPost.txtConditionType.value=='TT')
	 
	 var type="SET_A_CATVTERMTYPE";
	 
	 if(document.frmPost.txtConditionType.value=='S')
	 
	 var type="SET_C_USER_TYPE";
	 
	 if(document.frmPost.txtConditionType.value=='G')
	 
	 var type="MARKETSEGMENT";
	 
	 if(document.frmPost.txtConditionType.value=='CT')
	 
	 var type="SET_A_CABLETYPE";
	 
	 if(document.frmPost.txtConditionType.value=='BT')
	 
	 var type="SET_G_YESNOFLAG";
	  
	if(document.frmPost.txtConditionType.value=='C')
	 
	 var type="SET_C_CUSTOMERTYPE";
       if(document.frmPost.txtConditionType.value=='A')
	 
	 var type="SET_F_ACCOUNTTYPE";
	 
	if(document.frmPost.txtConditionType.value=='M') 
	 var type="CAMPAIGN";
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
function ChangeType()
{
    
      document.frmPost.submit();
    
}
 function back_submit()
{
	self.location.href="brcondition_query.do?txtFrom=1&txtTo=10";
} 


</SCRIPT>

<form name="frmPost" method="post">
	<input type="hidden" name="txtFrom" size="20" value="1">
	<input type="hidden" name="txtTo" size="20" value="10">
	<input type="hidden" name="Action"  value="">
	<input type="hidden" name="func_flag" value="110" >
	 
	<!-- 定义当前操作 -->
	 
    <table border="0" align="center" cellpadding="0" cellspacing="10">
     <tr>
    <td colspan="2" height="8"></td>
  </tr>
	<tr>
		<td><img src="img/list_tit.gif" width="19" height="19"></td>
		<td class="title">计费条件管理-新建</td>
	</tr>
</table>
<table width="100%" border="0" align="center" cellpadding="0"
	cellspacing="0" background="img/line_01.gif">
	<tr>
		<td><img src="img/mao.gif" width="1" height="1"></td>
	</tr>
</table>
<br>

<table width="810" align="center" border="0" cellspacing="1" cellpadding="3">
	 
	<tr>
		<td  class="list_bg2"><div align="right">计费条件类型*</div></td>         
                <td class="list_bg1" align="left">
                <d:selcmn name="txtConditionType" mapName="SET_F_BRCONDITIONCNTTYPE" match="txtConditionType" width="23" onchange="ChangeType()" />
                </td> 
                 <td  class="list_bg2"><div align="right">计费条件名称*</div></td>         
                 <td class="list_bg1" align="left">
                 <input type="text" name="txtConditionName" maxlength="200" size="22"  value="<tbl:writeparam name="txtConditionName" />" >
                 </td>        
       </tr>
	 
	<tr>
		 <td class="list_bg2"><div align="right">计费条件状态*</div></td>
                 <td class="list_bg1"  colspan="3">
                 <d:selcmn name="txtStatus" mapName="SET_G_GENERALSTATUS" match="txtStatus" width="23" />
		 
	</tr>
	<tr>
	<td valign="middle" class="list_bg2" align="left" >计费条件内容*</td>
	          
	           <td class="list_bg1" colspan="4"> 
	           <textarea  name="txtCustTypeListValue" readonly  length="5" cols=80 rows=9><tbl:writeSpeCharParam name="txtCustTypeListValue" /></textarea>
	           <input name="txtCustTypeList"    type="hidden" value="<tbl:writeparam name="txtCustTypeList" />" >
	           <input name="checkbutton" type="button" class="button" value="请选择" onClick="javascript:open_select('txtCustTypeList',document.frmPost.txtCustTypeList.value,'','','')"> 
                   </td>		
	   	   
	  </tr>   
</table>
<br>
<table width="100%" border="0" align="center" cellpadding="0"
	cellspacing="0" background="img/line_01.gif">
	<tr>
		<td><img src="img/mao.gif" width="1" height="1"></td>
	</tr>
</table>
<br>

	  <table align="center"  border="0" cellspacing="0" cellpadding="0">
	    <tr>
             <td><img src="img/button2_r.gif" border="0" ></td>
             <td background="img/button_bg.gif"><a href="javascript:back_submit()" class="btn12">返&nbsp;回</a></td>
             <td><img src="img/button2_l.gif" border="0" ></td>
              <td width="20" ></td>    

            <td><img src="img/button_l.gif" width="11" height="20"></td>
            <td><input name="Submit" type="button" class="button"
					value="确  认" onclick="javascript:billing_condition_create()"></td>
            <td><img src="img/button_r.gif" width="22" height="20"></td>          
        </tr>
      </table>	

</form>
