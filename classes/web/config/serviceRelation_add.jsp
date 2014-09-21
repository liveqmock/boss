<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="resultset" prefix="rs" %>
<%@ taglib uri="osstags" prefix="d" %>
<%@ page import="com.dtv.oss.util.Postern" %>
<%@ page import="com.dtv.oss.web.util.WebUtil" %>

<script language="JavaScript" >
  function back_submit(){
      document.frmPost.action ="config_serviceRelation_query.do";
      document.frmPost.submit();
  }
  
  function check_frm(){
      if (check_Blank(document.frmPost.txtType, true, "关系类型"))
	  return false;
      if (check_Blank(document.frmPost.txtReferServiceId, true, "依赖业务名称"))
	  return false; 

      return true; 
  }
  
  function add_submit(){
      if (check_frm()){
         document.frmPost.action ="config_serviceRelation_add.do" ;
         document.frmPost.submit();
      }
  }


</script>

<table width="820" align="center" cellpadding="0" cellspacing="0">
<tr><td>
   <table  border="0" align="center" cellpadding="0" cellspacing="10">
     <tr>
       <td><img src="img/list_tit.gif" width="19" height="19"></td>
       <td  class="title">新增业务关系信息</td>
     </tr>
   </table>
   <table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
    <tr>
      <td><img src="img/mao.gif" width="1" height="1"></td>
    </tr>
   </table>
   <br>
   <form name="frmPost" method="post" action="" >
      <input type="hidden" name ="ActionFlag" value ="add_serviceDependence" >
      <input type="hidden" name ="func_flag" value ="20002" >
      <input type="hidden" name="confirm_post"  value="true" >
      <table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
        <tr> 
           <td  class="list_bg2"><div align="right">业务名称</div></td>
            <td class="list_bg1"> 
                <input type="hidden" name="txtServiceID" value="<tbl:writeparam name="txtServiceID" />" >
                <%
                   String serviceName = Postern.getServiceNameByID(WebUtil.StringToInt(request.getParameter("txtServiceID"))); 
                %>
                <input type="text" name="txtServiceName" size="25" value="<%=serviceName%>" class="textgray" readonly width="27"/>   
            </td>             
           <td  class="list_bg2"><div align="right">关系类型*</div></td>
           <td class="list_bg1"> 
               <d:selcmn name="txtType" mapName="SET_P_SERVICEDEPENDENCYTYPE"  match="txtType"  width="23" />
           </td>     
        </tr> 
        <tr>
          <td class="list_bg2"><div align="right">依赖业务名称*</div></td>
          <td class="list_bg1" colspan="3"><font size="2">
              <%
                  pageContext.setAttribute("AllService" , Postern.getAllService()); 
              %>
              <tbl:select name="txtReferServiceId" set="AllService" match="txtReferServiceId" width="23" />
          </font></td>
        </tr>
       </table>
       <tbl:generatetoken /> 
    </form>
</td></tr>
</table>
 <BR>  
  <table align="center"  border="0" cellspacing="0" cellpadding="0">
      <tr>  
         <td><img src="img/button2_r.gif" width="22" height="20"></td> 
         <td><input name="next" type="button" class="button" onClick="back_submit()" value="返  回"></td>
         <td><img src="img/button2_l.gif" width="11" height="20"></td>
         <td width="20" ></td>  
         <td><img src="img/button_l.gif" width="11" height="20"></td>
         <td><input name="next" type="button" class="button" onClick="add_submit()" value="新  增"></td>
         <td><img src="img/button_r.gif" width="22" height="20"></td>          
      </tr>
   </table>	