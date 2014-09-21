<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="/WEB-INF/oss.tld" prefix="d" %>
<%@ taglib uri="bookmark" prefix="bk" %>
<%@ page import="com.dtv.oss.util.Postern,
         com.dtv.oss.dto.UserPointsExchangeActivityDTO,
          com.dtv.oss.dto.UserPointsExchangeGoodsDTO,
          com.dtv.oss.dto.UserPointsExchangeCondDTO" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Iterator" %>
<Script language=javascript>
 
 
 
    

</SCRIPT>

<table border="0" cellspacing="0" cellpadding="0" width="820" >
<tr>
<td>
<table  border="0" align="center" cellpadding="0" cellspacing="10" >
  <tr>
    <td><img src="img/list_tit.gif" width="19" height="19"></td>
    <td class="title">积分兑换活动详细信息</td>
  </tr>
</table>
<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>
<br>
<form name="frmPost" method="post">
   
   <lgc:bloop requestObjectName="ResponseQueryResult" item="oneline" IsOne="true">
  <%
    com.dtv.oss.dto.UserPointsExchangeActivityDTO dto = (com.dtv.oss.dto.UserPointsExchangeActivityDTO)pageContext.getAttribute("oneline");
      List condDtoList= Postern.getActivityDetailById(dto.getId().intValue());
      List goodDtoList= Postern.getGoodDetailById(dto.getId().intValue());
     
   %> 
     
    <table align="center" width="98%" border="0" cellspacing="1" cellpadding="5" class="list_bg">
        <tr>
	        <td colspan="4" class="import_tit" align="center"><font size="3">积分活动基本信息</font></td>
        </tr>
        <tr>
            <td class="list_bg2"><div align="right">活动ID</div></td>
             <td class="list_bg1"><input type="text" name="txtID"  size="10"  value="<tbl:write name="oneline" property="Id" />"  class="textgray" readonly ></td>
             
            <td class="list_bg2"><div align="right">活动名称</div></td>
            <td class="list_bg1"><input type="text" name="txtName"  size="50" maxlength="100" value="<tbl:write name="oneline" property="name" />" class="textgray" readonly ></td>
        </tr>
       
       <tr>
           <td class="list_bg2"><div align="right">状态</div></td>
            <td class="list_bg1" colspan="3"><input type="text" name="txtID"  size="10"  
            value="<d:getcmnname typeName="SET_G_GENERALSTATUS" match="oneline:Status"/>" class="textgray" readonly > </td>
            
            
      </tr>
       <tr>
		<td class="list_bg2">活动描述</td>
		<td class="list_bg1" colspan="3"> 
			<textarea name="txtDescription"  length="5" cols=80 rows=3 disabled ><tbl:write  name="oneline" property ="descr"/></textarea>
		 </td>		
	  </tr>
        
      <tr>
       <td><img src="img/mao.gif" width="1" height="1"></td>
      </tr>
     
    </table>
     
    
    
    
    <table align="center" width="98%" border="0" cellspacing="1" cellpadding="5" class="list_bg">
         <tr>
	        <td colspan="6" class="import_tit" align="center"><font size="3">本积分活动客户条件</font></td>
        </tr>   
      
                <tbl:printcsstr style1="list_bg1" style2="list_bg2" overStyle="list_over" >
		        
			<td class="list_head">条件ID</td>
			<td class="list_head">客户类型列表 </td>
			<td class="list_head">积分下限 </td>
			<td class="list_head">积分上限 </td> 
			<td class="list_head">可兑换货物名称 </td>     
			<td class="list_head">状态</td>
		</tbl:printcsstr>
                <% 
                  UserPointsExchangeCondDTO currentDto=null;
                   String totalValue="";
                 Iterator itr= condDtoList.iterator();
                 while(itr.hasNext()){
                 
                 currentDto=(UserPointsExchangeCondDTO)itr.next();
                 pageContext.setAttribute("condDto",currentDto);
                 String custTypeList = currentDto.getCustTypeList();
                  int goodId = currentDto.getExchangeGoodsTypeID();
                  String goodName=Postern.getGoodNameByID(goodId);
                 System.out.println("the type of the customer is "+ custTypeList);
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
		
		       <td align="center"><tbl:write name="condDto" property="condId"/></td> 
		        <td align="center"><%=totalValue%></td>
      			<td align="center"><tbl:writenumber name="condDto" property="PointRange1" hideZero="true"/></td> 
      			<td align="center"><tbl:writenumber name="condDto" property="PointRange2"  hideZero="true"/></td> 
      			<td align="center"><%=goodName%></td>      			
      			<td align="center"><d:getcmnname typeName="SET_G_GENERALSTATUS" match="condDto:Status"/></td> 
      			 
      		</tr>
      		<%
      		totalValue="";
      		}%>
 
	 <tr>
            <td colspan="9" class="list_foot"></td>
         </tr>
 
          <tr>
	        <td colspan="6" class="import_tit" align="center"><font size="3">兑换物品</font></td>
        </tr>   
        
   
		<tbl:printcsstr style1="list_bg1" style2="list_bg2" overStyle="list_over" >
		        
			<td class="list_head">物品ID</td>
			<td class="list_head">物品名称 </td>
			<td class="list_head">物品描述 </td>
			<td class="list_head">单次兑换数量 </td> 
			<td class="list_head">状态</td>
			<td class="list_head"></td>
		</tbl:printcsstr>
                <% 
                  UserPointsExchangeGoodsDTO currentGoodDto=null;
                   
                 Iterator itrgood= goodDtoList.iterator();
                 while(itrgood.hasNext()){
                 
                 currentGoodDto=(UserPointsExchangeGoodsDTO)itrgood.next();
                 pageContext.setAttribute("goodDto",currentGoodDto);
                 
                  
                
                %>
		<tr class="list_bg1" onmouseover="this.className='list_over'" onmouseout="this.className='list_bg1'">
		
		       <td align="center"><tbl:write name="goodDto" property="Id"/></td> 
		        <td align="center"><tbl:write name="goodDto" property="name"/></td> 
      			<td align="center"><tbl:write name="goodDto" property="descr"/></td> 
      			<td align="center"><tbl:write name="goodDto" property="amount"/></td> 
      			     			
      			<td align="center"><d:getcmnname typeName="SET_G_GENERALSTATUS" match="goodDto:Status"/></td> 
      			<td align="center"></td>  
      		</tr>
      		<%}%>
 
	 <tr>
    <td colspan="9" class="list_foot"></td>
  </tr>
</table>
 </lgc:bloop>
    <table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
      
	    <tr align="center">
	        <td class="list_bg1"><table  border="0" cellspacing="0" cellpadding="0">
		        <tr>
			        <td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
			         <td background="img/button_bg.gif"><a href="<bk:backurl property="select_activity_for_points.do" />" class="btn12">返&nbsp;回</a></td>
			        <td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
			         
		        </tr>
	        </table></td>
	    </tr>
    </table>
</form>
</td>
</tr>
</table>