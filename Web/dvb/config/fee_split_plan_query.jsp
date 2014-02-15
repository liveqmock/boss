<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="resultset" prefix="rs" %>
<%@ taglib uri="/WEB-INF/oss.tld" prefix="d" %>
<%@ page import="com.dtv.oss.util.Postern,
               com.dtv.oss.dto.FeeSplitPlanDTO,
               java.util.*" %>
 

<script language=javascript>

 
 

function view_detail_click(strId)
{
	self.location.href="fee_split_detail.screen?txtFeeSplitPlanID="+strId;
} 
 
 
 
function create_fee_split_submit() {
    
   document.frmPost.action="fee_split_create.screen";
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
    <td  class="title">费用摊消计划列表</td>
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
            <td class="list_head">费用摊消计划ID</td>
            <td class="list_head">费用摊消计划名称</td>
            <td class="list_head">总共摊消数量</td>
             <td class="list_head"></td>
          </tr>
 
 
 <%
        
    List dtoList =Postern.getFeeSplitDTOList(); 
    if (dtoList!=null){
        Iterator ite = dtoList.iterator();
        while (ite.hasNext()){ 
        FeeSplitPlanDTO feeDto= (FeeSplitPlanDTO) ite.next();
       pageContext.setAttribute("oneline",feeDto);
          
 %>
 
	 <tbl:printcsstr style1="list_bg1" style2="list_bg2" overStyle="list_over" > 
		<td align="center"><a href="javascript:view_detail_click('<tbl:write name="oneline" property="feeSplitPlanID"/>')" class="link12" ><tbl:write name="oneline" property="feeSplitPlanID" /></a></td>
		<td align="center"><tbl:write name="oneline" property="planName"/></td>
		<td align="center"><tbl:write name="oneline" property="totalTimeCycleNo"/></td>
		<td align="center">
			<a href="fee_split_item.screen?txtFeeSplitPlanID=<tbl:write name="oneline" property="feeSplitPlanID"/>">费用摊消计划条目</a>
		</td>
      </tbl:printcsstr>	   
      <%
      	  }
       }
    %>
  	
    <td colspan="4" class="list_foot"></td>
  </tr>
 
             
</table>   
 
   <table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
	  <tr align="center">
	  <td class="list_bg1"> 
     <table align="center" border="0" cellspacing="0" cellpadding="0">
        <tr>
          
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
 
