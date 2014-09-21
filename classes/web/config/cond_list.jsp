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
	self.location.href="cond_detail.do?txtCondID="+strId;
}
 
function delete_click(id){
	if(confirm("确认要删除该记录吗?")){
		document.frmPost.action="cond_delete_done.do?txtCondID="+id;
		document.frmPost.submit();		
	}
}

function create_cond_submit(){
   document.frmPost.action="cond_create.screen";
   document.frmPost.submit();
}

</script>
<table width="820" align="center" cellpadding="0" cellspacing="0">
<tr><td>
<table  border="0" align="center" cellpadding="0" cellspacing="10">
  <tr>
    <td><img src="img/list_tit.gif" width="19" height="19"></td>
    <td  class="title">兑换条件维护</td>
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
          <td class="list_head">条件ID</td>
	  <td class="list_head">客户类型列表</td>
	  <td class="list_head">积分下限 </td>
	  <td class="list_head">积分上限 </td> 
	  <td class="list_head">可兑换货物名称 </td>     
	  <td class="list_head">状态</td>
	  <td class="list_head">操作</td>
         </tr>

<lgc:bloop requestObjectName="ResponseQueryResult" item="oneline" >
 <%
         com.dtv.oss.dto.UserPointsExchangeCondDTO dto = (com.dtv.oss.dto.UserPointsExchangeCondDTO)pageContext.getAttribute("oneline");
         int activityID = dto.getActivityId();
         String activityName = Postern.getActivityNameByID(activityID);
         int goodId = dto.getExchangeGoodsTypeID();
         String goodName=Postern.getGoodNameByID(goodId);
         String custTypeList = dto.getCustTypeList();
         String pointStr1=null;
   String pointStr2=null;
  int pointsBelow = dto.getPointRange1();
  if (pointsBelow == 0)
      pointStr1 ="";
   else pointStr1 = String.valueOf(pointsBelow);
  int pointsOver= dto.getPointRange2();
  if (pointsOver == 0)
      pointStr2 ="";
   else pointStr2 = String.valueOf(pointsOver); 
     
         String totalValue="";
         
                 if(custTypeList!=null)
                 { 
                     String[] custArray=custTypeList.split(",");
                   	 System.out.println("custTypeList"+custTypeList);
                   	 System.out.println("custTypeList"+custArray.length);
                     for(int j=0;j<custArray.length;j++){
	                     String value = Postern.getHashValueListByNameKey("SET_C_CUSTOMERTYPE",custArray[j].trim());
                     	 System.out.println("当前客户类型"+value);
	                     if(totalValue=="")
	                       totalValue=value;
	                     else if(value!="")
	                       totalValue=totalValue+","+value;
                     }
                     
                 }
 
 %>
 
	 <tr class="list_bg1" onmouseover="this.className='list_over'" onmouseout="this.className='list_bg1'">
	     <td align="center"><a href="javascript:view_detail_click('<tbl:write name="oneline" property="condId"/>')" class="link12" ><tbl:writenumber name="oneline" property="condId" digit="7"/></a></td>
      	      <td align="center"><%=totalValue%></td>   
      	      <td align="center"><%=pointStr1%></td>
      	      <td align="center"><%=pointStr2%></td>
      	      <td align="center"><%=goodName%></td>
      	     
      	     <td align="center"><d:getcmnname typeName="SET_G_GENERALSTATUS" match="oneline:Status"/></td>  
	     <td align="center"><a href="javascript:delete_click('<tbl:write name="oneline" property="condId"/>')" class="link12" >删除</a></td>
    	</tr>
</lgc:bloop>  

<tr>
    <td colspan="8" class="list_foot"></td>
  </tr>
</table>
 

 <input type="hidden" name="txtActivityID" value="<%=activityId%>"/>
 <input type="hidden" name="txtActionType" value="cond_delete">
 <input type="hidden" name="txtHiddenValues" value="txtActivityID" >
 	<table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
	<tr align="center">
	     <td class="list_bg1">

 <table align="center" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td><img src="img/button_l.gif" border="0" ></td>
          <td background="img/button_bg.gif"><a href="<bk:backurl property="activity_detail.do" />" class="btn12">返&nbsp;回</a></td>
          <td><img src="img/button_r.gif" border="0" ></td>

          <td width="20" ></td>
          
           <td><img src="img/button_l.gif" border="0" ></td>
          <td background="img/button_bg.gif"><a href="javascript:create_cond_submit()" class="btn12">创&nbsp;建</a></td>
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
 
