<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="osstags" prefix="d" %>
<%@ taglib uri="bookmark" prefix="bk" %>
<%@ page import="com.dtv.oss.util.Postern" %> 
 <%@ page import="com.dtv.oss.web.util.WebUtil" %>

<script language=javascript>
function check_frm()
{
	
	
    if (check_Blank(document.frmPost.txtGoodTypeID, true, "兑换物品名称"))
	    return false;
    
    if (check_Blank(document.frmPost.txtStatus, true, "状态"))
		return false;

    if (check_Blank(document.frmPost.txtExchangeGoodsAmount, true, "每次兑换数量")||!check_Num(document.frmPost.txtExchangeGoodsAmount, true, "每次兑换数量"))
		return false;
   if (check_Blank(document.frmPost.txtExchangeGoodsValue, true, "所需要积分数"))
		return false;	 
	 
     		
	 

	return true;
}
function rule_create_submit(){
  if (check_frm()){
	    document.frmPost.action="create_poin_rule.do";
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
    <td colspan="2" height="8"></td>
  </tr>
  <tr>
    <td><img src="img/list_tit.gif" width="19" height="19"></td>
    <td class="title">创建积分兑换规则</td>
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
  String  activityId = request.getParameter("txtActivityID");
  
  Map goodsMap = null;
  goodsMap = Postern.getGoodNameByActivityID(WebUtil.StringToInt(activityId));
  pageContext.setAttribute("GOODNAME",goodsMap);
    
%>
 
 <table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
         <tr>
	        
		<td class="list_bg2" align="right">兑换物品名称*</td>
		<td class="list_bg1">
		   <tbl:select name="txtGoodTypeID" set="GOODNAME" match="txtGoodTypeID" width="23" />   
		 </td>
		 <td class="list_bg2" align="right">每次兑换数量*</td>
		 <td class="list_bg1">
		     <input type="text" name="txtExchangeGoodsAmount" size="25"  value="<tbl:writeparam name="txtExchangeGoodsAmount" />" >
		</td>
	</tr>
	  
	<tr>
		<td class="list_bg2" align="right">状态*</td>
		 <td class="list_bg1">
			 <d:selcmn name="txtStatus" mapName="SET_G_GENERALSTATUS" match="txtStatus" width="23" />
		 </td>
		<td class="list_bg2" align="right">所需要积分数*</td>
		 <td class="list_bg1">
			 <input type="text" name="txtExchangeGoodsValue" size="25" value="<tbl:writeparam name="txtExchangeGoodsValue" />" >
		 </td>
		
	</tr>
	  
      </tr>
       	
	<tr>
		
		
		<td valign="middle" class="list_bg2" align="right">客户类型</td>
	          <td  class="list_bg1" colspan="3">
	   	  <input name="txtCustTypeListValue" type="text" class="text" maxlength="200" size="25" value="<tbl:writeparam name="txtCustTypeListValue" />">
	   	  <input name="txtCustTypeList" type="hidden" value="<tbl:writeparam name="txtCustTypeList" />">
	   	  <input name="checkbutton" type="button" class="button" value="请选择" onClick="javascript:open_select('SET_C_CUSTOMERTYPE','txtCustTypeList',document.frmPost.txtCustTypeList.value,'','','')"> 
	   </td>	   
	</tr>
        
        
      
  </table>
 <input type="hidden" name="func_flag" value="5060" >
  <input type="hidden" name="Action" value=""/>
  <input type="hidden" name="confirm" value ="true"/>
  <input type="hidden" name="vartxtActivityID" value="<tbl:writeparam name="txtActivityID" />"/>
    <table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
	  <tr align="center">
	  <td class="list_bg1">
     <table align="center" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td><img src="img/button2_r.gif" border="0" ></td>
          <td background="img/button_bg.gif"><a href="<bk:backurl property="query_points_rule.do" />" class="btn12">返&nbsp;回</a></td>
          <td><img src="img/button2_l.gif" border="0" ></td>

          <td width="20" ></td>      
           <td><img src="img/button_l.gif" border="0" ></td>
          <td background="img/button_bg.gif"><a href="javascript:rule_create_submit()" class="btn12">确&nbsp;认</a></td>
          <td><img src="img/button_r.gif" border="0" ></td>
        </tr>
      </table>   
             </td>
         </tr>
</table>         
      <table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
        <tr>
          <td><img src="img/mao.gif" width="1" height="1"></td>
        </tr>
      </table>
       <br>      
   
</form>
