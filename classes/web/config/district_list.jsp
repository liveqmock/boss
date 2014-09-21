<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="resultset" prefix="rs" %>
<%@ taglib uri="osstags" prefix="d" %>
<%@ taglib uri="bookmark" prefix="bk" %> 
<%@ page import="com.dtv.oss.util.Postern" %>
<%@ page import="com.dtv.oss.web.util.WebUtil"%>
<%@ page import="com.dtv.oss.dto.MarketSegmentToDistrictDTO" %>

<script language=javascript>
 

function confirm_submit() {
    
    
    document.frmPost.action="create_district.do";
    document.frmPost.Action.value="create";
    document.frmPost.submit();
}


</script>
<table width="820" align="center" cellpadding="0" cellspacing="0">
<tr><td>
<table  border="0" align="center" cellpadding="0" cellspacing="10">
  <tr>
    <td><img src="img/list_tit.gif" width="19" height="19"></td>
    <td  class="title">行政区域维护</td>
  </tr>
  </table>
  <table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>
 

 
<form name="frmPost" method="post" >
<input type="hidden" name="DistrictList" value="">
 
   <%
    int marketSegmentID = WebUtil.StringToInt(request.getParameter("txtMarketSegmentID"));
    
  %>

<table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">

   <tr> 
    <td colspan="4" class="import_tit" align="center"><font size="3">可选行政区域
   </font></td>
  </tr>
         <tr class="list_head">
            <td class="list_head"> 
            <input class="check" type="checkbox" name="selall" onclick="javascript:select_all_by_name(document.frmPost,'lstForSel', this.checked)">
       
          </td>
           <td class="list_head">行政区域ID</td>
           <td class="list_head">行政区域</td>
           </tr>
           <%
               List districtList=Postern.getDistrictIDListByID(marketSegmentID);
               
               List disList = Postern.getDistrictIDList();
               if(disList!=null){
                  Iterator valueIter=disList.iterator();
                  //boolean b= false;
			String checked = "";
		  while(valueIter.hasNext()){
				//b= false;
		    checked = "";
                   Integer districtId=(Integer) valueIter.next();
                
                  String name = Postern.getStrictNameByID(districtId.intValue()); 
              	  if(districtList!=null) {
                    for(int i=0; i<districtList.size();i++){
                 	 if(districtId.equals((Integer)districtList.get(i))){
                 	    checked = " checked=true ";
                 	    break;
                 	}	
                 }
			 
              }
           %>
            <tr class="list_bg1" onmouseover="this.className='list_over'" onmouseout="this.className='list_bg1'">
    	       <td align="center">
    	       <input type="checkbox" name="lstForSel" value="<%=districtId.intValue()%>" <%=checked%> >
    	       
               </td>
      		 
      		<td align="center"><%=districtId %></td>
      		<td align="center"><%=name %></td>
      		 
    </tr>
    <%}}%>
</table>
 
<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
 </table>

 <input type="hidden" name="vartxtDistrickID" value=""/>
  <input type="hidden" name="func_flag" value="5099" >
 
  
 <input type="hidden" name="txtSegmentID" value="<%=marketSegmentID%>"/>
 <input type="hidden" name="txtMarketSegmentID" value=""/>
  <input type="hidden" name="txtCurAction" value="">
 <input type="hidden" name="Action" value=""/>
  
 <table align="center" border="0" cellspacing="0" cellpadding="0">
        <tr>
           <td><img src="img/button2_r.gif" width="22" height="20"></td>
          <td background="img/button_bg.gif"><a href="<bk:backurl property="menu_market_segment.do" />" class="btn12">返&nbsp;回</a></td>
         <td><img src="img/button2_l.gif" width="11" height="20"></td>
            <td width="20" ></td>  
           
           <td><img src="img/button_l.gif" border="0" ></td>
          <td background="img/button_bg.gif"><a href="javascript:confirm_submit()" class="btn12">确&nbsp;认</a></td>
          <td><img src="img/button_r.gif" border="0" ></td>
          
          
          
          
	
        </tr>
      </table>    
	 
    </td>
  </tr>
</form>  
</table>  
 
