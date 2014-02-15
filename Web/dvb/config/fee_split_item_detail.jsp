<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="osstags" prefix="d" %>
<%@ taglib uri="bookmark" prefix="bk" %>
<%@ page import="com.dtv.oss.web.util.WebUtil,com.dtv.oss.util.Postern" %> 
<%@ page import="com.dtv.oss.dto.FeeSplitPlanItemDTO" %>

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
function fee_modify_submit(){
    if (check_frm()){
  if (window.confirm('您确认要修改该费用摊消计划条目吗?')){
	    document.frmPost.action="fee_split_item_edit_done.do";
	    document.frmPost.Action.value="update";
	    document.frmPost.submit();
	  }
	}
}
 

</script>
<table  border="0" align="center" cellpadding="0" cellspacing="10" >
  <tr>
    <td><img src="img/list_tit.gif" width="19" height="19"></td>
    <td class="title">费用摊消计划条目-修改</td>
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
      String seqno=request.getParameter("txtSeqNo");
      
      int seqNO=WebUtil.StringToInt(seqno);
       
      FeeSplitPlanItemDTO feeDto=Postern.getFeeSplitPlanItemDTO(seqNO);
     
      if(feeDto!=null)
         pageContext.setAttribute("oneline",feeDto);
      else 
        pageContext.setAttribute("oneline",new FeeSplitPlanItemDTO());
  %>
      
 
 
 <table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
         <tr>
	        <td class="list_bg2" align="right">流水号</td>
		 <td class="list_bg1">
			<input type="text" name="txtSeqNo" size="25"  value="<tbl:write name="oneline" property="seqNo" />" class="textgray" readonly >
		 </td>
		<td class="list_bg2" align="right">费用摊消计划ID</td>
		 <td class="list_bg1">
		     <input type="text" name="txtFeeSplitPlanID" size="25"  value="<tbl:write name="oneline" property="feeSplitPlanID" />" class="textgray" readonly >
		</td>
	</tr>
	  
	<tr>
		<td class="list_bg2" align="right">摊消数量(月)*</td>
		 
		 <td class="list_bg1">
		     <input type="text" name="txtTimeCycleNo" size="25"  maxlength="12" value="<tbl:write name="oneline" property="timeCycleNO" />" >
		</td>
		<td class="list_bg2" align="right">金额*</td>
		 
		 <td class="list_bg1">
		     <input type="text" name="txtValue" size="25"  maxlength="10" value="<tbl:write name="oneline" property="value" />" >
		</td>
	</tr>
        
      
  </table>
 
 
<input type="hidden" name="func_flag" value="1005" >
<input type="hidden" name="Action" value="">
   <table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
	  <tr align="center">
	  <td class="list_bg1"> 
     <table align="center" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td><img src="img/button2_r.gif" border="0" ></td>
          <td background="img/button_bg.gif"><a href="fee_split_plan_query.screen" class="btn12">返&nbsp;回</a></td>
          <td><img src="img/button2_l.gif" border="0" ></td>
          <td width="20" ></td>         	
           <td><img src="img/button_l.gif" border="0" ></td>
          <td background="img/button_bg.gif"><a href="javascript:fee_modify_submit()" class="btn12">修&nbsp; 改</a></td>
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
