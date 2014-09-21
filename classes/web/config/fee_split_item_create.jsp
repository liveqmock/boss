<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="osstags" prefix="d" %>
<%@ taglib uri="bookmark" prefix="bk" %>
<%@ page import="com.dtv.oss.web.util.WebUtil,com.dtv.oss.util.Postern" %> 
<%@ page import="com.dtv.oss.dto.FeeSplitPlanDTO" %>

<script language=javascript>
function check_frm()
{
	
	if (check_Blank(document.frmPost.txtValue, true, "金额"))
	    return false;
	
    if (!check_Money(document.frmPost.txtValue, true, "金额"))
	    return false;
   
	
	if (check_Blank(document.frmPost.txtTimeCycleNo, true, "摊消数量"))
	    return false;
	    
	if (!checkPlainNum(document.frmPost.txtTimeCycleNo,false,9,"摊消数量"))
		return false;



	return true;
}
function fee_create_submit(){
    if (check_frm()){
   
	    document.frmPost.action="fee_split_item_create.do";
	    document.frmPost.Action.value="create";
	    document.frmPost.submit();
	   
	}
}
function back(){
             var id=document.frmPost.txtFeeSplitPlanID.value;
             var strUrl="fee_split_item.screen?txtFeeSplitPlanID="+id;
             self.location.href=strUrl;
	    
	  
	   
	 
} 

</script>
<table  border="0" align="center" cellpadding="0" cellspacing="10" >
  <tr>
    <td><img src="img/list_tit.gif" width="19" height="19"></td>
    <td class="title">费用摊消计划条目-创建</td>
  </tr>
</table>
<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>
<br>
 
<form name="frmPost" method="post" >   
  
 <table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
         <tr>
	        
		<td class="list_bg2" align="right">金额*</td>
		 <td class="list_bg1">
		     <input type="text" name="txtValue" size="25"  maxlength="12"  value="<tbl:writeparam name="txtValue" />" >
		</td>
	 
		<td class="list_bg2" align="right">摊消数量(月)*</td>
		 
		 <td class="list_bg1" colspan="3">
		     <input type="text" name="txtTimeCycleNo" size="25"  maxlength="10"  value="<tbl:writeparam name="txtTimeCycleNo" />" >
		</td>
	</tr>
        
      
  </table>
 <%
  String feeId = request.getParameter("txtFeeSplitPlanID");
 %>
<input type="hidden" name="func_flag" value="1005" >
<input type="hidden" name="Action" value="">
<input type="hidden" name="txtFeeSplitPlanID" value="<%=feeId%>" >
   <table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
	  <tr align="center">
	  <td class="list_bg1"> 
     <table align="center" border="0" cellspacing="0" cellpadding="0">
        <tr>
           <td><img src="img/button2_r.gif" border="0" ></td>
          <td background="img/button_bg.gif"><a href="javascript:back()" class="btn12">返&nbsp;回</a></td>
          <td><img src="img/button2_l.gif" border="0" ></td>
          <td width="20" ></td>         	 	
           <td><img src="img/button_l.gif" border="0" ></td>
          <td background="img/button_bg.gif"><a href="javascript:fee_create_submit()" class="btn12">保&nbsp; 存</a></td>
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
