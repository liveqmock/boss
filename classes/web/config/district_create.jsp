<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="osstags" prefix="d" %>
<%@ taglib uri="bookmark" prefix="bk" %>
<%@ page import="com.dtv.oss.util.Postern" %>

<script language=javascript>
function check_frm()
{
	
	
    if (check_Blank(document.frmPost.txtdistrictID, true, "行政区域"))
	    return false;
    
     
     		
	 

	return true;
}
 function district_create_submit(){
  if (check_frm()){
	    document.frmPost.action="create_district.do";
	    document.frmPost.Action.value="create";
	    document.frmPost.submit();
	  }
}
 

</script>
<table  border="0" align="center" cellpadding="0" cellspacing="10" >
  <tr>
    <td><img src="img/list_tit.gif" width="19" height="19"></td>
    <td class="title">创建行政区域</td>
  </tr>
</table>
<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>
<br>
 
<form name="frmPost" method="post" >   
 <tbl:generatetoken />  
  <% 
   Map districtMap = null;
  districtMap = Postern.getAllDistrictName();
  pageContext.setAttribute("DISTRICTNAME",districtMap);
  %>    
 
 <table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
 
         <tr>
	   <td class="list_bg2">行政区域*</td>
	   <td class="list_bg1">
		  <tbl:select name="txtdistrictID" set="DISTRICTNAME" match="txtdistrictID" width="23" />
	  </td>
	  </tr>
	  
  </table>
 
<input type="hidden" name="txtSettingID"  value="<tbl:writeparam name="txtSegmentID" />">
<input type="hidden" name="func_flag" value="5099" >
<input type="hidden" name="Action" value="">
 <input type="hidden" name="confirm" value ="true"/>
<br>

     <table align="center" border="0" cellspacing="0" cellpadding="0">
        <tr>
           <td><img src="img/button_l.gif" border="0" ></td>
          <td background="img/button_bg.gif"><a href="javascript:district_create_submit()" class="btn12">确&nbsp; 认</a></td>
          <td><img src="img/button_r.gif" border="0" ></td>
          <td width="20" ></td>
        
	
        </tr>
      </table>   
      <table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
        <tr>
          <td><img src="img/mao.gif" width="1" height="1"></td>
        </tr>
      </table>
       <br>      
 
</form>
