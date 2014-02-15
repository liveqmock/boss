<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="resultset" prefix="rs" %>
<%@ taglib uri="osstags" prefix="d" %>
 <%@ page import="com.dtv.oss.web.util.WebUtil" %>
<%@ page import="com.dtv.oss.util.Postern,
               com.dtv.oss.dto.FeeSplitPlanItemDTO,
               java.util.*" %>
 

<script language=javascript>

 
 

function view_detail_click(strId)
{
	self.location.href="fee_split_item_detail.screen?txtSeqNo="+strId;
} 
 
 
 
function create_fee_split_submit() {
    
   document.frmPost.action="fee_split_item_create.screen";
   document.frmPost.submit();
}

</script>
<table width="820" align="center" cellpadding="0" cellspacing="0">
<tr><td>
<table  border="0" align="center" cellpadding="0" cellspacing="10">
 <tr>
    <td colspan="2" height="8"></td>
  </tr>
  <tr>
    <td><img src="img/list_tit.gif" width="19" height="19"></td>
    <td  class="title">费用摊消计划条目列表</td>
  </tr>
  </table>
  <table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>
 

 
<form name="frmPost" method="post" action="" >
  <table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
         <tr class="list_head">
            <td class="list_head">流水号</td>
            <td class="list_head">费用摊消计划ID</td>
            <td class="list_head">摊消数量</td>
             <td class="list_head">金额</td>
          </tr>
 
 
 <%
 
    int feeSplitPlanID = WebUtil.StringToInt(request.getParameter("txtFeeSplitPlanID"));
    
    List dtoList =Postern.getFeeSplitItemDTOList(feeSplitPlanID); 
    if (dtoList!=null){
        Iterator ite = dtoList.iterator();
      while (ite.hasNext()){ 
        FeeSplitPlanItemDTO feeDto= (FeeSplitPlanItemDTO) ite.next();
        pageContext.setAttribute("oneline",feeDto);
          
 %>
 	<tbl:printcsstr style1="list_bg1" style2="list_bg2" overStyle="list_over" >
	<td align="center"><a href="javascript:view_detail_click('<tbl:write name="oneline" property="seqNo"/>')" class="link12" ><tbl:write name="oneline" property="seqNo" /></a></td>
	<td align="center"><tbl:write name="oneline" property="feeSplitPlanID"/></td>
	<td align="center"><tbl:write name="oneline" property="timeCycleNO"/></td>
	<td align="center"><tbl:write name="oneline" property="value"/></td>
   </tbl:printcsstr>	
      <%
      	  }
       }
    %>
  
    <td colspan="4" class="list_foot"></td>
  </tr>
 <input type="hidden" name="txtFeeSplitPlanID" value="<%=feeSplitPlanID%>" >
             
</table>   
 
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
          <td background="img/button_bg.gif"><a href="javascript:create_fee_split_submit()" class="btn12">新&nbsp; 增</a></td>
          <td><img src="img/button_r.gif" border="0" ></td>
        </tr>
      </table>   
        </td>
        </tr>
      </table>      
 
	 
    </td>
  </tr>
</form>  
</table>  
 
