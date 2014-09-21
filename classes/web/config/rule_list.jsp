<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="resultset" prefix="rs" %>
<%@ taglib uri="osstags" prefix="d" %>
<%@ taglib uri="bookmark" prefix="bk" %> 
<%@ page import="com.dtv.oss.util.Postern" %>
<%@ page import="com.dtv.oss.web.util.WebUtil"%>

<script language=javascript>
 
function view_detail_click(strId)
{
	self.location.href="rule_detail.do?txtID="+strId;
}
 
function detail_delete(id) {
       if (window.confirm('您确认要删除该规则信息吗?')){

    document.frmPost.txtVarID.value = id;
    document.frmPost.vartxtActivityID.value = document.frmPost.txtActivityID.value;
    document.frmPost.action="points_rule_delete_detail.do?Action=deleteDetail";
    document.frmPost.submit();
}}
 
function create_rule_submit() {
    
   document.frmPost.action="points_rule_create.screen";
   document.frmPost.submit();
}

 

</script>
<table width="820" align="center" cellpadding="0" cellspacing="0">
<tr><td>
<table  border="0" align="center" cellpadding="0" cellspacing="10">
  <tr>
    <td><img src="img/list_tit.gif" width="19" height="19"></td>
    <td  class="title">兑换规则维护</td>
  </tr>
  </table>
  <table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>
 

 
<form name="frmPost" method="post" >
 
 <%
   int activityId = WebUtil.StringToInt(request.getParameter("txtActivityID"));
   
 %>    
	    
  
     
 
 
 <table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
         <tr class="list_head">
           <td class="list_head">规则ID</td>
           <td class="list_head">活动名称</td>
           <td class="list_head">兑换物品名称</td>
           <td class="list_head">兑换物品数量</td>
            <td class="list_head">所需积分值</td>
           <td class="list_head">客户类型</td>
            <td class="list_head">状态</td>
           <td class="list_head"><div align="center">操作</div></td>
           
        </tr>

<lgc:bloop requestObjectName="ResponseQueryResult" item="oneline" >
 <%
         com.dtv.oss.dto.UserPointsExchangeRuleDTO dto = (com.dtv.oss.dto.UserPointsExchangeRuleDTO)pageContext.getAttribute("oneline");
         int activityID = dto.getActivityId();
         String activityName = Postern.getActivityNameByID(activityID);
         int goodId = dto.getExchangeGoodsTypeId();
         String goodName=Postern.getGoodNameByID(goodId);
         String custTypeList = dto.getCustTypeList();
         String totalValue="";
       //  System.out.println("the type of the customer is "+ custTypeList);
                 if(custTypeList!=null)
                 { 
                     String[] custArray=custTypeList.split(",");
                     for(int j=0;j<custArray.length;j++){
                     System.out.println("every customertpye is "+ custArray[j]);
                     String value = Postern.getHashValueListByNameKey("SET_C_CUSTOMERTYPE",custArray[j]);
                     if(totalValue=="")
                      totalValue=value;
                     else 
                       totalValue=totalValue+","+value;
                     }
                     
                 }
 
 %>
 
	 <tr class="list_bg1" onmouseover="this.className='list_over'" onmouseout="this.className='list_bg1'">
	     <td align="center"><a href="javascript:view_detail_click('<tbl:write name="oneline" property="Id"/>')" class="link12" ><tbl:writenumber name="oneline" property="Id" digit="7"/></a></td>
      	     <td align="center"><%=activityName%></td>
      	      <td align="center"><%=goodName%></td>
      	     <td align="center"><tbl:write name="oneline" property="exchangeGoodsAmount"/></td>
      	     <td align="center"><tbl:write name="oneline" property="exchangePointsValue"/></td>
      	     <td align="center"><%=totalValue%></td>    			
      	     <td align="center"><d:getcmnname typeName="SET_G_GENERALSTATUS" match="oneline:Status"/></td>  
      	     <td align="center">
             <A href="javascript:detail_delete('<tbl:write name="oneline" property="id"/>')">删除</A</td>
    	</tr>
</lgc:bloop>  

<tr>
    <td colspan="8" class="list_foot"></td>
  </tr>
</table>
 

 <input type="hidden" name="txtVarID" value=""/>
 <input type="hidden" name="vartxtActivityID" value=""/>
 <input type="hidden" name="txtActivityID" value="<%=activityId%>"/>
 <input type="hidden" name="Action" value=""/>
     <table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
	  <tr align="center">
	  <td class="list_bg1">
 <table align="center" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td><img src="img/button2_r.gif" border="0" ></td>
          <td background="img/button_bg.gif"><a href="<bk:backurl property="activity_detail.do" />" class="btn12">返&nbsp;回</a></td>
          <td><img src="img/button2_l.gif" border="0" ></td>

          <td width="20" ></td>

           <td><img src="img/button_l.gif" border="0" ></td>
          <td background="img/button_bg.gif"><a href="javascript:create_rule_submit()" class="btn12">创&nbsp;建</a></td>
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
    </td>
  </tr>
</form>  
</table>  
 
