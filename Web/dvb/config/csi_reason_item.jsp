<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="resultset" prefix="rs" %>
<%@ taglib uri="osstags" prefix="d" %>
<%@ page import="com.dtv.oss.util.Postern,
       java.util.Iterator,
     com.dtv.oss.dto.* "%>
 

<script language=javascript>
 

function view_detail_click(strId)
{
       
	self.location.href="csi_reason_item_edit.screen?txtSeqNo="+strId;
	 
}
function create_submit() {
    
   document.frmPost.action="csi_reason_item_create.screen";
   
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
    <td  class="title">受理单原因配对的明细信息列表</td>
  </tr>
  </table>
  <table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>
 

 
<form name="frmPost" method="post" >
 
 <table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
         <tr class="list_head">
            <td class="list_head">流水号</td>
            <td class="list_head">头记录ID</td>
            <td class="list_head">键</td>
            <td class="list_head">值</td>
            <td class="list_head">优先级</td> 
            <td class="list_head">是否为默认值</td>
             <td class="list_head">状态</td>
             
          </tr>

 
        <%
            
           String referSeqNo =  request.getParameter("txtReferSeqNo");
           List dtoList = Postern.getCsiReasonDetailDto(referSeqNo);
           Iterator ite = dtoList.iterator();
		  while(ite.hasNext()){
			  CsiActionReasonDetailDTO dto=(CsiActionReasonDetailDTO)ite.next();  
			   pageContext.setAttribute("oneline",dto);
		   
           %>
          <tr class="list_bg1" onmouseover="this.className='list_over'" onmouseout="this.className='list_bg1'">
	     <td align="center"><a href="javascript:view_detail_click('<tbl:write name="oneline" property="seqNo"/>')" class="link12" ><tbl:writenumber name="oneline" property="seqNo" digit="9"/></a></td>
      	     <td align="center"><tbl:write name="oneline" property="referSeqNo"/></td>
      	     <td align="center"><tbl:write name="oneline" property="key"/></td>
      	     <td align="center"><tbl:write name="oneline" property="value"/></td>
      	     <td align="center"><tbl:writenumber hidezero="true" name="oneline" property="priority"/></td>
      	      <td align="center"><d:getcmnname typeName="SET_G_YESNOFLAG" match="oneline:defaultValueFlag"/></td>
      	     <td align="center"><d:getcmnname typeName="SET_G_GENERALSTATUS" match="oneline:Status"/></td>   
         </tr>    	    
  <%}%>
      <input type="hidden" name="txtReferSeqNo1" size="20" value="<%=referSeqNo%>">
<tr>
    <td colspan="8" class="list_foot"></td>
  </tr>
</table>

<br>
  
 <table align="center" border="0" cellspacing="0" cellpadding="0">
        <tr>
         <td><img src="img/button2_r.gif" border="0" ></td>
          <td background="img/button_bg.gif"><a href="csi_reason_query.do?txtFrom=1&txtTo=10" class="btn12">返&nbsp;回</a></td>
          <td><img src="img/button2_l.gif" border="0" ></td>
           <td width="20" ></td>   
           <td><img src="img/button_l.gif" border="0" ></td>
          <td background="img/button_bg.gif"><a href="javascript:create_submit()" class="btn12">新&nbsp; 增</a></td>
          <td><img src="img/button_r.gif" border="0" ></td>
          <td width="20" ></td>
        
	
        </tr>
   </table>   
<br>

 
   
 
	 
    </td>
  </tr>
</form>  
</table>  
 
