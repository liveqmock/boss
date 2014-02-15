<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="resultset" prefix="rs" %>
<%@ taglib uri="osstags" prefix="d" %>
<%@ taglib uri="bookmark" prefix="bk" %>

<%@ page import="com.dtv.oss.util.Postern,java.util.*"%>
<%@ page import="com.dtv.oss.dto.wrap.VOIPEventWrap"%>
<%@ page import="com.dtv.oss.dto.SystemEventDTO"%>

<SCRIPT language="JavaScript">
function check_frm(){
	 if (check_Blank(document.frmPost.txtConditionName, true, "条件名称"))
             return false;
         if (check_Blank(document.frmPost.txtConditionString, true, "条件内容"))
             return false;  
             
         if (document.frmPost.txtConditionName.value.length>50){
    					alert('条件名称长度不能超过50');
    					return false;
    		}     
              
	return true;
}
function modify_submit()
{
          if (window.confirm('您确认要修改语音条件吗?')){
	   if(check_frm()){
		document.frmPost.submit();
	}
}
}
function back_submit(){
	document.frmPost.action="voip_condition_list.do?txtQueryType=C";
	document.frmPost.submit();
}
function choose_product(){
	var strFeatures = "width=600px,height=500px,resizable=no,toolbar=no,scrollbars=yes,status=yes";
	var conditionString=document.frmPost.txtConditionString.value;
  	window.open("voip_list_product.do?txtConditionString="+conditionString,"产品查询",strFeatures);
}

</SCRIPT>

<form name="frmPost" method="post" action="voip_condition_modify.do">
<INPUT TYPE="hidden" name="actionFlag" value="modify">
<input type="hidden" name="txtFrom"  value="1" > 
<input type="hidden" name="txtTo"  value="10" >
<%String id=request.getParameter("id");%>
<input type="hidden" name="txtConditionID" value="<%=id%>">
<br>
<table border="0" align="center" cellpadding="0" cellspacing="10">
	<tr>
		<td><img src="img/list_tit.gif" width="19px" height="19px"></td>
		<td class="title">语音条件修改</td>
	</tr>
</table>
<table width="100%" border="0" align="center" cellpadding="0"
	cellspacing="0" background="img/line_01.gif">
	<tr>
		<td><img src="img/mao.gif" width="1px" height="1px"></td>
	</tr>
</table>
<br>

<table width="100%" align="center" border="0" cellspacing="1"
	cellpadding="3">
	<tr>
	        <td class="list_bg2" align="right" width="17%">条件ID</td>
		<td class="list_bg1"><input name="txtConditionID"
			type="text" readonly class="textgray" maxlength="200" size="25"
			value="<tbl:writeparam name="id" />"></td>
		<td class="list_bg2" align="right" width="17%">条件名称*</td>
		<td class="list_bg1"><input name="txtConditionName"
			type="text" class="text" maxlength="200" size="25"
			value="<tbl:writeparam name="txtConditionName" />"></td>
	</tr>
	<tr>
		<td class="list_bg2" align="right">条件内容*</td>
		<td class="list_bg1" colspan="3">
	    <input type="hidden" name="txtConditionString" value="<tbl:writeparam name="txtConditionString" />" >
	    <textarea name="txtProductNameList" length="5" cols=82 rows=4 readonly ><tbl:writeparam name="txtProductNameList" /></textarea>
            <input name="selProductButton" type="button" class="button" value="查询" onClick="javascript:choose_product()"> </td>
	</tr>
	<tr>
		<td class="list_bg2" align="right">描述</td>
		<td class="list_bg1" colspan="3"><input name="txtDescription"
			type="text" class="text" maxlength="200" size="83"
			value="<tbl:writeparam name="txtDescription" />"></td>
	</tr>
</table>
<table width="100%" border="0" align="center" cellpadding="0"
	cellspacing="0" background="img/line_01.gif">
	<tr>
		<td><img src="img/mao.gif" width="1px" height="1px"></td>
	</tr>
</table>
<br>
<table align="center"  border="0" cellspacing="0" cellpadding="0">
          <tr>  
            <td><img src="img/button2_r.gif" width="22" height="20"></td>
			<td><input name="Submit" type="button" class="button"
					value="返&nbsp;回" onclick="javascript:back_submit()"></td>
            <td><img src="img/button2_l.gif" width="11" height="20"></td>

            <td width="20" ></td>  

            <td><img src="img/button_l.gif" width="11" height="20"></td>
            <td><input name="Submit" type="button" class="button"
					value="保&nbsp;存" onclick="javascript:modify_submit()"></td>
            <td><img src="img/button_r.gif" width="22" height="20"></td>          
        </tr>
      </table>	
    
	   </td>
	</tr>
    </table>    
 <br>
 <INPUT TYPE="hidden" name="func_flag" value="2002"> 
</form>
