<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="resultset" prefix="rs" %>
<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="osstags" prefix="d" %>
<%@ taglib uri="bookmark" prefix="bk" %>
<%@ page import="com.dtv.oss.util.Postern,
               com.dtv.oss.dto.OpGroupDTO,
               java.util.*" %>
 
<%String checkFlags = request.getParameter("checkFlags");
if (checkFlags == null)
    checkFlags = "";%>
    
 <form name="frmPost" method="post">
   <table border="0" align="center" cellpadding="0" cellspacing="10">
    <tr>
    <td colspan="2" height="8"></td>
  </tr>
	<tr>
		<td><img src="img/list_tit.gif" width="19" height="19"></td>
		<td class="title">管辖区域配置</td>
	</tr>
</table>
<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
	<tr>
		<td><img src="img/mao.gif" width="1" height="1"></td>
	</tr>
</table>
<br> 

<table width="810" align="center" border="0" cellspacing="1" cellpadding="3">

  
      <tr>
	   <td class="list_bg2"><div align="right">组织ID</div></td>         
           <td class="list_bg1" align="left">
             <input type="text" name="txtOrgID" size="22"  value="<tbl:writeparam name="txtOrgID"/>" class="textgray" readonly >
           </td>       
           <td class="list_bg2"><div align="right">组织名称 </div></td>
           <td class="list_bg1" align="left">
           <input type="text" name="txtOrgName" maxlength="10" size="22"  value="<tbl:writeparam name="txtOrgName"/>" class="textgray" readonly >
           </td>
     </tr> 
     
     <tr>
         <td class="list_bg2"><div align="right">组织描述 </div></td>
           <td class="list_bg1" align="left" colspan="3">
          <input type="text" name="txtOrgDesc" maxlength="10" size="22"   value="<tbl:writeparam name="txtOrgDesc"/>" class="textgray" readonly >
          </td>
      </tr>
      
     <tr>
          <td class="list_bg2" colspan="4"><div align="center"><strong>管辖的区域</strong></div></td>
     </tr>
     <tr>
          <td class="list_bg2" colspan="2"><div align="center">已有区域</div></td>
          <td class="list_bg2" colspan="2"><div align="center">可选区域</div></td>
     </tr>
      </table>  
     <table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" class="list_bg">
       <tr>
          <td class="list_bg2" align="center" ><font size="2">
          <br>
     
          <td class="list_bg2" align="center" colspan="2"><font size="2">
           
          <iframe SRC="existed_org_district.screen?txtOrgID=<tbl:writeparam name="txtOrgID" />&checkFlags=<%=checkFlags%>"
            id="ExistedOrgDistrict" name="ExistedOrgDistrict" width="350" height="300">
  	      </iframe><br><br>
          </font></td>
          <td width=40 class="list_bg2"> <table align="center" border="0" cellspacing="0" cellpadding="0">
    	 <tr>
    	  <td><img src="img/button2_r.gif" width="22" height="20" style="position:relative;top:4px;"><input name="Submit" type="button" class="button"
					value="添&nbsp;加" onclick="javascript:add_org_district()"><img src="img/button2_l.gif" width="11" height="20" style="position:relative;top:4px;"></td>
           
           </tr>
           <tr>
           <td height=20 ></td>
           </tr>
           <tr>
           <td ><img src="img/button_l.gif" width="11" height="20" style="position:relative;top:4px;"><input name="Submit" type="button" class="button"
					value="去&nbsp;除" onclick="javascript:delete_org_district()"><img src="img/button_r.gif" width="22" height="20" style="position:relative;top:4px;"></td>      
           
         </tr>
      </table>   
      </td>
          <td valign="middle" class="list_bg2" align="center" colspan="2"><font size="2">
          <iframe SRC="optional_org_district.screen?txtOrgID=<tbl:writeparam name="txtOrgID" />&checkFlags=<%=checkFlags%>"
            id="OptionalOrgDistrict" name="OptionalOrgDistrict" width="350" height="300">
  	      </iframe> <br><br>
         </font></td>
          </tr>
      </table> 
     <br>
     
      <table align="center" border="0" cellspacing="0" cellpadding="0">
        <tr>
     
    
         <td valign="middle"  colspan="4">
            <table align="center" border="0" cellspacing="0" cellpadding="0">
			   <tr>
			       <td><img src="img/button2_r.gif" border="0" ></td>
			        <td background="img/button_bg.gif"><a href="<bk:backurl property="organization_query_result.do"/>" class="btn12">返&nbsp;回</a></td>
			        <td><img src="img/button2_l.gif" border="0" ></td>
		       </tr>
	       </table>
         </td>
     </tr>
     
</table>  
      
<form name="frmPost" action="" method="post" >
<tbl:generatetoken />
<tbl:hiddenparameters pass="txtOrgID/txtOrgName/txtOrgDesc"/> 
<input type="hidden" name="orgID" value="<tbl:writeparam name="txtOrgID"/>"/>
<input type="hidden" name="districtID" value=""/>
<input type="hidden" name="checkFlags" value=""/>
<input type="hidden" name="Action" value=""/>
<input type="hidden" name="func_flag" value="110"/>
</form>

<Script language="javascript">
<!--
function checkForm() {
    document.frmPost.districtID.value = '';
    document.frmPost.checkFlags.value = ";";
    if (document.frames.ExistedOrgDistrict.txtdistrictID != null) {
        if (document.frames.ExistedOrgDistrict.txtdistrictID.length > 1) {
            for (i = 0; i < document.frames.ExistedOrgDistrict.txtdistrictID.length; i++) {
                if (document.frames.ExistedOrgDistrict.Flags[i].checked) {
                    document.frmPost.checkFlags.value=document.frames.ExistedOrgDistrict.Flags[i].value+";"+document.frmPost.checkFlags.value;
                    document.frmPost.districtID.value=document.frames.ExistedOrgDistrict.txtdistrictID[i].value+ ";" + document.frmPost.districtID.value ;
                }
            }
        } else {
            if (document.frames.ExistedOrgDistrict.Flags.checked) {
                document.frmPost.checkFlags.value=document.frames.ExistedOrgDistrict.Flags.value;
                document.frmPost.districtID.value = document.frames.ExistedOrgDistrict.txtdistrictID.value;
            }
        }
        if (document.frmPost.districtID.value == '') {
            alert("请选定管辖区域!");
            return false;
        }
    } else {
        alert("没有数据！");
        return false;
    }
    return true;
}
function checkAddForm() {
    document.frmPost.districtID.value = '';
    document.frmPost.checkFlags.value = ";";
    if (document.frames.OptionalOrgDistrict.txtdistrictID != null) {
        if (document.frames.OptionalOrgDistrict.txtdistrictID.length > 1) {
            for (i = 0; i < document.frames.OptionalOrgDistrict.txtdistrictID.length; i++) {
                if (document.frames.OptionalOrgDistrict.Flags[i].checked) {
                    document.frmPost.checkFlags.value=document.frames.OptionalOrgDistrict.Flags[i].value+";"+document.frmPost.checkFlags.value;
                    document.frmPost.districtID.value=document.frames.OptionalOrgDistrict.txtdistrictID[i].value+ ";" + document.frmPost.districtID.value ;
                }
            }
        } else {
            if (document.frames.OptionalOrgDistrict.Flags.checked) {
                document.frmPost.checkFlags.value=document.frames.OptionalOrgDistrict.Flags.value;
                document.frmPost.districtID.value = document.frames.OptionalOrgDistrict.txtdistrictID.value;
            }
        }
        if (document.frmPost.districtID.value == '') {
            alert("请选定管辖区域!");
            return false;
        }
    } else {
        alert("没有数据！");
        return false;
    }
    return true;
}

function delete_org_district() {
    if (checkForm()) {
        document.frmPost.Action.value="DeleteOrgDistrict";
        document.frmPost.action="delete_org_district.do";
        document.frmPost.submit();
    }
}

function add_org_district() {
    if (checkAddForm()) {
        document.frmPost.Action.value="AddOrgDistrict";
        document.frmPost.action="add_org_district.do";
        document.frmPost.submit();
    }
}
//-->
</Script>