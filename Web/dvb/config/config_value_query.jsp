<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="resultset" prefix="rs" %>
<%@ taglib uri="osstags" prefix="d" %>
<%@ taglib uri="bookmark" prefix="bk" %> 
<%@ page import="com.dtv.oss.util.Postern" %>
<%@ page import="com.dtv.oss.web.util.WebUtil"%>
<%@ page import="com.dtv.oss.dto.BidimConfigSettingValueDTO" %>

<script language=javascript>
 
function view_detail_click(strId)
{
	self.location.href="value_detail.do?txtID="+strId;
}
 
function detail_delete(id) {
     
    document.frmPost.varID.value = id;
    
    document.frmPost.action="value_delete_detail.do?Action=deleteDetail";
    document.frmPost.submit();
}
 
function create_item_submit() {
    
   document.frmPost.action="config_value_create.screen";
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
    <td  class="title">维护配置选项值</td>
  </tr>
  </table>
  <table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>
 

 
<form name="frmPost" method="post" >
 
   
	    
  
   
    
 <table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
   <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
 </table>
 
  
 
 <table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
         <tr class="list_head">
           <td class="list_head">记录ID</td>
           <td class="list_head">取值代码</td>
           <td class="list_head">取值描述</td>
           <td class="list_head">状态</td>
           <td class="list_head"><div align="center">操作</div></td>
           
        </tr>

<lgc:bloop requestObjectName="ResponseQueryResult" item="oneline" >
 
          
 
	 <tr class="list_bg1" onmouseover="this.className='list_over'" onmouseout="this.className='list_bg1'">
	     <td align="center"><a href="javascript:view_detail_click('<tbl:write name="oneline" property="Id"/>')" class="link12" ><tbl:writenumber name="oneline" property="Id" digit="7"/></a></td>
      	     
      	     <td align="center"><tbl:write name="oneline" property="code"/></td>
      	     <td align="center"><tbl:write name="oneline" property="description"/></td>
      	     <td align="center"><d:getcmnname typeName="SET_G_GENERALSTATUS" match="oneline:Status"/></td>  
      	     			
      	     <td align="center">
             <A href="javascript:detail_delete('<tbl:write name="oneline" property="id"/>')">删除</A</td>
    	</tr>
</lgc:bloop>  

<tr>
    <td colspan="8" class="list_foot"></td>
  </tr>
</table>
 
<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
 </table>
 <input type="hidden" name="varID" value=""/>
 <input type="hidden" name="txtvarSettingID" value="<%=request.getParameter("txtSettingID")%>"/>
 <input type="hidden" name="Action" value=""/>
 <table align="center" border="0" cellspacing="0" cellpadding="0">
        <tr>
           <td><img src="img/button_l.gif" border="0" ></td>
          <td background="img/button_bg.gif"><a href="javascript:create_item_submit()" class="btn12">创&nbsp;建</a></td>
          <td><img src="img/button_r.gif" border="0" ></td>
          <td width="20" ></td>
            <td><img src="img/button_l.gif" border="0" ></td>
            <td background="img/button_bg.gif"><a href="<bk:backurl property="config_detail.do" />" class="btn12">返&nbsp;回</a></td>
           <td><img src="img/button_r.gif" border="0" ></td>
       </tr>
      </table>    
	 
    </td>
  </tr>
</form>  
</table>  
 
