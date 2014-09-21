<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="/WEB-INF/oss.tld" prefix="d" %>
<%@ taglib uri="bookmark" prefix="bk" %>
<%@ page import="com.dtv.oss.util.Postern" %>
<%@ page import="com.dtv.oss.dto.CampaignDTO" %>
<%@ page import="java.util.*" %>

<Script language=javascript>
function checkSelected(){
       var m=0;
       if (document.frmPost.ListID !=null){
       
          for( i=0; i<document.frmPost.ListID.length; i++){
           
               if (document.frmPost.ListID[i].checked){
                document.frmPost.txtClassID.value=document.frmPost.ListID[i].value;
                 m=m+1;
              }
          }
      }
     
      if (m==0){
         alert("没有选择促销活动");
	 return false;
      }
      
      
      return true;
}
 
 
function create_submit()
{
    
    if(checkSelected()){
      document.frmPost.action="group_bargain_create.screen";
      document.frmPost.submit();
    }
}

 

 
    

</SCRIPT>

<table border="0" cellspacing="0" cellpadding="0" width="820" >
<tr>
<td>
<table  border="0" align="center" cellpadding="0" cellspacing="10" >
  <tr>
    <td><img src="img/list_tit.gif" width="19" height="19"></td>
    <td class="title">促销活动选择</td>
  </tr>
</table>
<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>
<br>
<form name="frmPost" method="post">
    
     <input type="hidden" name="txtClassID"/>
 
    <table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
		<tbl:printcsstr style1="list_bg1" style2="list_bg2" overStyle="list_over" >
		       <td width="5%" class="list_head"></td>
			<td width="8%" class="list_head">活动ID</td>
			<td width="20%" class="list_head">活动名称</td>
			<td width="30%" class="list_head">活动描述</td>
			<td width="12%" class="list_head">优惠时间跨度(月)</td>
			<td width="8%" class="list_head">开始时间</td>
			<td width="8%" class="list_head">结束时间</td>
			<td width="5%" class="list_head">状态</td>      
			 
			 
		</tbl:printcsstr>
		
	        <%
	          String campaignType = "'B'";
	          ArrayList campaignDtoList =  Postern.getCampaignDTO(campaignType);
	          Iterator ite =  campaignDtoList.iterator();
	          while (ite.hasNext()){
	          CampaignDTO camdto = (CampaignDTO) ite.next();
	           pageContext.setAttribute("oneline",  camdto);
	           
	        %>
	 
		<tr class="list_bg1" onmouseover="this.className='list_over'" onmouseout="this.className='list_bg1'">
		
		        <td align="center"><input type="radio" name="ListID" value="<tbl:write name="oneline" property="campaignID"/>"></td>
		         
		           <input type="hidden" name="ListID" value="">
		         <input type="hidden" name="txtCampaignID" value="<tbl:write name="oneline" property="campaignID"/>"> 
		        <td align="center"><tbl:writenumber name="oneline" property="campaignID" digit="7" /></td>
      			<td align="center"><tbl:write name="oneline" property="campaignName"/></td> 
      			<td align="center"><tbl:write name="oneline" property="description"/></td> 
      			<td align="center"><tbl:write name="oneline" property="timeLengthUnitNumber"/></td> 
      			<td align="center"><tbl:writedate includeTime="false" name="oneline" property="dateFrom"/></td> 
      			<td align="center"><tbl:writedate includeTime="false" name="oneline" property="dateTo"/></td> 
      		 
      			<td align="center"><d:getcmnname typeName="SET_G_GENERALSTATUS" match="oneline:Status"/></td>      			
      			 
      			 
      		</tr>
      		<%}%>
	 
    <tr>
    <td colspan="8" class="list_foot"></td>
  </tr>
</table>
  
    
    <table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
	    <tr align="center">
	        <td class="list_bg1"><table  border="0" cellspacing="0" cellpadding="0">
		        <tr>
			        
			        <td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
			        <td><input name="Submit" type="button" class="button" value="下一步" onclick="javascript:create_submit()"></td>
			        <td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
		        </tr>
	        </table></td>
	    </tr>
    </table>
</form>
</td>
</tr>
</table>