<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="osstags" prefix="d" %>
<%@ taglib uri="bookmark" prefix="bk" %>
<%@ page import="com.dtv.oss.util.Postern" %> 
 <%@ page import="com.dtv.oss.web.util.WebUtil" %>

<script language=javascript>
function check_frm()
{
	
	
    if (check_Blank(document.frmPost.txtOpGroupName, true, "操作员组名称"))
	    return false;
     return true;
}
 
function opgroup_create_submit(){
  if (check_frm()){
	    document.frmPost.action="create_opgroup.do";
	    document.frmPost.Action.value="CREATE";
	    document.frmPost.submit();
	  }
}
 
function back_submit(){
    document.location.href= "opgroup_query.do?txtFrom=1&txtTo=10";
} 

 
</script>
 
 
 
<table  border="0" align="center" cellpadding="0" cellspacing="10" >
 <tr>
    <td colspan="2" height="8"></td>
  </tr>
  <tr>
    <td><img src="img/list_tit.gif" width="19" height="19"></td>
    <td class="title">操作员组创建</td>
  </tr>
</table>
<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>
<br>
 
<form name="frmPost" method="post" >   
 
  
 
 <table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
      <tr> 
          <td  class="list_bg2"><div align="right">操作员组名称*</div></td>         
          <td class="list_bg1" align="left">
           <input type="text" name="txtOpGroupName" size="25"  maxlength="50"  value="<tbl:writeparam name="txtOpGroupName" />" >
           </td>      
           <td  class="list_bg2"><div align="right">操作员组级别</div></td>         
             <td class="list_bg1" align="left">
             <d:selcmn name="txtOpGroupLevel" mapName="SET_S_OPGROUPLEVEL" match="txtOpGroupLevel" width="23" />
             </td>      
      </tr>
       <tr>
          <td class="list_bg2"><div align="right">操作员组描述</div></td>
           <td class="list_bg1" align="left" colspan="3">
          <input type="text" name="txtDescription" maxlength="100" size="83"   value="<tbl:writeparam name="txtDescription" />" >
          </td>    
          
         
      </tr>
       
 </table>
 <input type="hidden" name="func_flag" value="178" >
  <input type="hidden" name="Action" value=""/>
  <input type="hidden" name="txtSystemFlag" value="N"/>
 
  
<br>
 <table align="center" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td><img src="img/button2_r.gif" width="22" height="20"></td>
			<td><input name="Submit" type="button" class="button"
					value="返&nbsp;回" onclick="javascript:back_submit()"></td>
            <td><img src="img/button2_l.gif" width="11" height="20"></td>
              <td width="20" ></td>  
	
           <td><img src="img/button_l.gif" border="0" ></td>
           <td background="img/button_bg.gif"><a href="javascript:opgroup_create_submit()" class="btn12">添 加</a></td>
           <td><img src="img/button_r.gif" border="0" ></td>
           <td width="20" ></td>
            
          
        </tr>
      </table>   
  
      
</form>
