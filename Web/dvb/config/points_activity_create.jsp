<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="osstags" prefix="d" %>
<%@ taglib uri="bookmark" prefix="bk" %>
<%@ page import="com.dtv.oss.util.Postern" %> 
 <%@ page import="com.dtv.oss.web.util.WebUtil" %>

<script language=javascript>
function check_frm()
{
	
	
    if (check_Blank(document.frmPost.txtName, true, "活动名称"))
	    return false;
    
    if (check_Blank(document.frmPost.txtStatus, true, "状态"))
		return false;

	return true;
}
function checkDate(){
 
	if (check_Blank(document.frmPost.txtStartTime, true, "开始日期")){
			return false;
	}

	if (!check_TenDate(document.frmPost.txtStartTime, true, "开始日期")){
			return false;
	}
	 
	if (check_Blank(document.frmPost.txtEndTime, true, "结束日期")){
			return false;
	}

	if (!check_TenDate(document.frmPost.txtEndTime, true, "结束日期")){
			return false;
	}
	 
	if(!compareDate(document.frmPost.txtStartTime,document.frmPost.txtEndTime,"结束日期必须大于等于开始日期")){
		
		return false;
	}
	 
	            
	return true;
}
 function activity_create_submit(){
  if (check_frm() && checkDate()){
	    document.frmPost.action="create_poin_activity.do";
	    document.frmPost.Action.value="create";
	    document.frmPost.submit();
	  }
}
 
 
 function back(){
    document.location.href= "activity_query_result.do?txtTo=10&txtFrom=1"
} 

 
</script>
<table  border="0" align="center" cellpadding="0" cellspacing="10" >

 <tr>
    <td colspan="2" height="8"></td>
  </tr>
  <tr>
    <td><img src="img/list_tit.gif" width="19" height="19"></td>
    <td class="title">创建积分兑换活动</td>
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
      
 
 
 
 <table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
         <tr>
	        
		<td class="list_bg2"><div align="right">活动名称*</div></td>
		<td class="list_bg1" align="left">
		  <input type="text" name="txtName" size="25" maxlength="25" value="<tbl:writeparam name="txtName" />" >
		 </td>
		 <td class="list_bg2"><div align="right">状态*</div></td>
		 <td class="list_bg1" align="left">
			 <d:selcmn name="txtStatus" mapName="SET_G_GENERALSTATUS" match="txtStatus" width="23" />
		 </td>
	</tr>
	 <tr>  
             <td class="list_bg2"><div align="right">活动开始时间*</div></td>
             <td class="list_bg1" align="left">
            <input maxlength="10" onfocus="viewModule(this)" onKeydown="inputDate(document.frmPost.txtStartTime)" onblur="lostFocus(this)" type="text" name="txtStartTime" size="25" value="<tbl:writeparam name="txtStartTime"/>" ><IMG onclick="MyCalendar.SetDate(this,document.frmPost.txtStartTime,'Y')" src="img/calendar.gif" style=cursor:hand border="0">
           </td>
             <td class="list_bg2"><div align="right">活动结束时间*</div></td>
               <td class="list_bg1" align="left">
           <input maxlength="10" onfocus="viewModule(this)" onKeydown="inputDate(document.frmPost.txtEndTime)" onblur="lostFocus(this)" type="text" name="txtEndTime" size="25" value="<tbl:writeparam name="txtEndTime"/>" ><IMG onclick="MyCalendar.SetDate(this,document.frmPost.txtEndTime,'Y')" src="img/calendar.gif" style=cursor:hand border="0">
     	</td>
    </tr>
          
	 <tr>   
		<td class="list_bg2"><div align="right">描述</div></td>
		<td class="list_bg1" colspan="3"> 
		 <input type="text" name="txtDescription" size="83"  maxlength="120" value="<tbl:writeparam name="txtDescription" />" >
		</td>
	</tr>
	  
      </tr>
       	
	 
        
        
      
  </table>
 <input type="hidden" name="func_flag" value="5079" >
  <input type="hidden" name="Action" value=""/>
  <input type="hidden" name="confirm" value ="true"/>
    <table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
	  <tr align="center">
	  <td class="list_bg1">
<table align="center" border="0" cellspacing="0" cellpadding="0">
        <tr>
            <td><img src="img/button2_r.gif" width="20" height="20"></td>
	    <td><input name="Submit" type="button" class="button"
		value="返&nbsp;回" onclick="javascript:back()"></td>
		 <td><img src="img/button2_l.gif" width="11" height="20"></td>
	    <td width="20" ></td>
            
           <td><img src="img/button_l.gif" border="0" ></td>
           <td background="img/button_bg.gif"><a href="javascript:activity_create_submit()" class="btn12">确&nbsp;认</a></td>
           <td><img src="img/button_r.gif" border="0" ></td>
          
          
         </tr>
      </table>   
	  </td>
  </tr>
  </table>  
   
</form>