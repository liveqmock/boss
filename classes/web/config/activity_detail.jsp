<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="osstags" prefix="d" %>
<%@ taglib uri="bookmark" prefix="bk" %>

<%@ page import="com.dtv.oss.dto.UserPointsExchangeActivityDTO" %>

<script language=javascript>
function check_frm()
{
	
	
    if (check_Blank(document.frmPost.txtName, true, "活动名称"))
	    return false;
    
    if (check_Blank(document.frmPost.txtStatus, true, "状态"))
		return false;

	 
		
	if (check_Blank(document.frmPost.txtStartTime, true, "活动开始时间"))
		return false;
	 
	if (check_Blank(document.frmPost.txtEndTime, true, "活动结束时间"))
		return false;
        

	if (!check_TenDate(document.frmPost.txtStartTime, true, "活动开始时间")){
			return false;
	}
	 
	if (!check_TenDate(document.frmPost.txtEndTime, true, "活动结束时间")){
			return false;
	}
	 
	if(!compareDate(document.frmPost.txtStartTime,document.frmPost.txtEndTime,"结束日期必须大于等于开始日期")){
		
		return false;
	}
	      		
	return true;
}
function activity_modify_submit(){
    if (check_frm()){
  if (window.confirm('您确认要修改该活动信息吗?')){
	    document.frmPost.action="activity_edit_done.do";
	    document.frmPost.Action.value="update";
	    document.frmPost.submit();
	  }
	}
}
function process_goods_submit(strId)
{
	self.location.href="query_points_goods.do?txtActivityID="+strId;
} 
function process_rule_submit(strId)
{
	self.location.href="query_points_rule.do?txtActivityID="+strId;
} 
function process_cond_submit(strId)
{
	self.location.href="query_points_cond.do?txtActivityID="+strId;
} 

</script>
<table  border="0" align="center" cellpadding="0" cellspacing="10" >
  <tr>
    <td><img src="img/list_tit.gif" width="19" height="19"></td>
    <td class="title">积分活动详细信息</td>
  </tr>
</table>
<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>
<br>
 
<form name="frmPost" method="post" >   
 <%
    String status=null;
 %>      
      
<lgc:bloop requestObjectName="ResponseQueryResult" item="oneline" IsOne="true"> 
 <%
     UserPointsExchangeActivityDTO dto = (UserPointsExchangeActivityDTO) pageContext.findAttribute("oneline");
     status = dto.getStatus();
     
 %>
 <table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
         <tr>
	        <td class="list_bg2" align="right">活动ID</td>
		 <td class="list_bg1">
			<input type="text" name="txtID" size="27"  value="<tbl:write name="oneline" property="Id" />" class="textgray" readonly >
		 </td>
		<td class="list_bg2" align="right">活动名称*</td>
		 <td class="list_bg1">
		     <input type="text" name="txtName" size="27" maxlength="25" value="<tbl:write name="oneline" property="name" />" >
		</td>
	</tr>
	  
	<tr>
		<td class="list_bg2" align="right">活动开始时间*</td>
		 <td class="list_bg1">
			<input onfocus="viewModule(this)" onKeydown="inputDate(document.frmPost.txtStartTime)" onblur="lostFocus(this)" type="text" name="txtStartTime" size="27" maxlength="10" value="<tbl:writedate name="oneline" property="DateStart" />"    ><IMG onclick="MyCalendar.SetDate(this,document.frmPost.txtStartTime,'Y')" src="img/calendar.gif" style=cursor:hand border="0">
		 </td>
		
		<td class="list_bg2" align="right">活动结束时间*</td>
		 <td class="list_bg1">
			<input onfocus="viewModule(this)" onKeydown="inputDate(document.frmPost.txtEndTime)" onblur="lostFocus(this)" type="text" name="txtEndTime" size="27" maxlength="10" value="<tbl:writedate name="oneline" property="DateEnd" />"    ><IMG onclick="MyCalendar.SetDate(this,document.frmPost.txtEndTime,'Y')" src="img/calendar.gif" style=cursor:hand border="0">
		 </td>
		
	</tr>
        <tr>
		<td class="list_bg2" align="right">状态*</td>
		<td class="list_bg1" colspan="3">
		 <d:selcmn name="txtStatus" mapName="SET_G_GENERALSTATUS" match="oneline:Status" width="25" />
		</td>
	</tr>
       <tr>   
		<td class="list_bg2" align="right">描述</td>
		<td class="list_bg1" colspan="3"> 
		 <input type="text" name="txtDescription" size="85"  maxlength="120" value="<tbl:write name="oneline" property="descr" />" >
		</td>
	</tr>
  </table>
<input type="hidden" name="txtDtLastmod"   value="<tbl:write name="oneline" property="DtLastmod" />">
<input type="hidden" name="func_flag" value="5001" >
<input type="hidden" name="Action" value="">

    <table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
	  <tr align="center">
	  <td class="list_bg1">
     <table align="center" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td><img src="img/button2_r.gif" border="0" ></td>
          <td background="img/button_bg.gif"><a href="<bk:backurl property="activity_query_result.do" />" class="btn12">返&nbsp;回</a></td>
          <td><img src="img/button2_l.gif" border="0" ></td>
          <td width="20" ></td> 
        	
           <td><img src="img/button_l.gif" border="0" ></td>
          <td background="img/button_bg.gif"><a href="javascript:activity_modify_submit()" class="btn12">修&nbsp; 改</a></td>
          <td><img src="img/button_r.gif" border="0" ></td>
          <td width="20" ></td>
          <% 
            if("V".equals(status)){
          %>
          <td><img src="img/button_l.gif" border="0" ></td>  
          <td background="img/button_bg.gif"><a href="javascript:process_goods_submit('<tbl:write name="oneline" property="Id"/>')" class="btn12">维护兑换物品</a></td>
          <td><img src="img/button_r.gif" border="0" ></td>
          <td width="20" ></td> 
            <td><img src="img/button_l.gif" border="0" ></td>  
          <td background="img/button_bg.gif"><a href="javascript:process_rule_submit('<tbl:write name="oneline" property="Id"/>')" class="btn12">维护兑换规则</a></td>
          <td><img src="img/button_r.gif" border="0" ></td>
          <td width="20" ></td> 
            <td><img src="img/button_l.gif" border="0" ></td>  
          <td background="img/button_bg.gif"><a href="javascript:process_cond_submit('<tbl:write name="oneline" property="Id"/>')" class="btn12">维护兑换条件</a></td>
          <td><img src="img/button_r.gif" border="0" ></td>
           <%}%>  
          
	
        </tr>
      </table>   
	  </td>
  </tr>
  </table>  
       <br>      
</lgc:bloop>   
</form>