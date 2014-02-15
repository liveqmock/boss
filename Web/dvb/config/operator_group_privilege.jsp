<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="resultset" prefix="rs" %>
<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="osstags" prefix="d" %>
<%@ taglib uri="bookmark" prefix="bk" %>
<%@ page import="com.dtv.oss.util.Postern,
               com.dtv.oss.dto.OpGroupDTO,
               java.util.*" %>
 
<%
String flag = request.getParameter("txtSystemFlag");
System.out.println("*******systemflag********************"+flag);

String checkFlags = request.getParameter("checkFlags");
if (checkFlags == null)
    checkFlags = "";%>
    
 <form name="frmPost" method="post">
   <table border="0" align="center" cellpadding="0" cellspacing="10">
    <tr>
    <td colspan="2" height="8"></td>
  </tr>
	<tr>
		<td><img src="img/list_tit.gif" width="19" height="19"></td>
		<td class="title">操作员组权限配置</td>
	</tr>
</table>
<table width="100%" border="0" align="center" cellpadding="0"
	cellspacing="0" background="img/line_01.gif">
	<tr>
		<td><img src="img/mao.gif" width="1" height="1"></td>
	</tr>
</table>
<br> 

 <table width="98%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">

  
      <tr>
	   <td class="list_bg2"><div align="right">操作员组ID</div></td>         
           <td class="list_bg1" align="left">
             <input type="text" name="txtOpGroupID" size="25"  value="<tbl:writeparam name="txtOpGroupID"/>" class="textgray" readonly >
           </td>       
           <td class="list_bg2"><div align="right">操作员组名称 </div></td>
           <td class="list_bg1" align="left">
           <input type="text" name="txtOpGroupName" maxlength="10" size="25"  value="<tbl:writeparam name="txtOpGroupName"/>" class="textgray" readonly >
           </td>
     </tr> 
     
     <tr>
         <td class="list_bg2"><div align="right">操作员组描述 </div></td>
           <td class="list_bg1" align="left" colspan="3">
          <input type="text" name="txtOpGroupDesc" maxlength="100" size="83"   value="<tbl:writeparam name="txtOpGroupDesc"/>" class="textgray" readonly >
          </td>
      </tr>
       <tr>
          <td class="import_tit"   colspan="4"><font size="3"><div align="center"> 操作员组拥有的权限</div></font></td>
     </tr>
      <tr>
          <td class="list_bg2" colspan="2"><div align="center">拥有权限</div></td>
            
          <td class="list_bg2" colspan="2"><div align="center">可选权限</div></td>
     </tr>
     
    </table>
     <table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" class="list_bg">
     <tr>
          <td class="list_bg2" align="center" ><font size="2">
          <br>
     
          <iframe SRC="possessed_group_privilege.screen?txtOpGroupID=<tbl:writeparam name="txtOpGroupID" />&checkFlags=<%=checkFlags%>"
            id="PossessedPrivilege" name="PossessedPrivilege" width="350" height="300">
  	      </iframe><br><br>
          </font></td>
          <%
            if("Y".equals(flag)){
          %>
           <td width=40 class="list_bg2"> <table align="center" border="0" cellspacing="0" cellpadding="0">
    	<tr>
    	<td><img src="img/button2_r.gif" width="22" height="20" style="position:relative;top:4px;"><input name="Submit" type="button" class="button"
				disabled=true	 value="添&nbsp;加" onclick="javascript:add_privilege()"><img src="img/button2_l.gif" width="11" height="20" style="position:relative;top:4px;"></td>
           
           </tr><tr>
           <td height=20 ></td>
           </tr>
           <tr>
           <td ><img src="img/button_l.gif" width="11" height="20" style="position:relative;top:4px;"><input name="Submit" type="button" class="button"
					disabled=true value="去&nbsp;除" onclick="javascript:delete_privilege()"><img src="img/button_r.gif" width="22" height="20" style="position:relative;top:4px;"></td> 
					<%} else {%>   
	  <td width=40 class="list_bg2"> <table align="center" border="0" cellspacing="0" cellpadding="0">
    	<tr>
    	<td><img src="img/button2_r.gif" width="22" height="20" style="position:relative;top:4px;"><input name="Submit" type="button" class="button"
				 	 value="添&nbsp;加" onclick="javascript:add_privilege()"><img src="img/button2_l.gif" width="11" height="20" style="position:relative;top:4px;"></td>
           
           </tr><tr>
           <td height=20 ></td>
           </tr>
           <tr>
           <td ><img src="img/button_l.gif" width="11" height="20" style="position:relative;top:4px;"><input name="Submit" type="button" class="button"
					  value="去&nbsp;除" onclick="javascript:delete_privilege()"><img src="img/button_r.gif" width="22" height="20" style="position:relative;top:4px;"></td> 
					<%}%>				  
           
         </tr>
      </table>   
      </td>
          <td valign="middle" class="list_bg2" align="center" colspan="2"><font size="2">
          <iframe SRC="optional_group_privilege.screen?txtOpGroupID=<tbl:writeparam name="txtOpGroupID" />&checkFlags=<%=checkFlags%>"
            id="OptionalPrivilege" name="OptionalPrivilege" width="350" height="300">
  	      </iframe> <br><br>
          </font></td>
     </tr>
      </table> 
      <br>
 <table align="center" border="0" cellspacing="0" cellpadding="0">
        <tr>
                        
           <td><img src="img/button2_r.gif" border="0" ></td>
           <td background="img/button_bg.gif"><a href="<bk:backurl property="opgroup_query.do/op_group_detail.do"/>" class="btn12">返&nbsp;回</a></td>
           <td><img src="img/button2_l.gif" border="0" ></td>
         </tr>
      </table>   
       
      
      
 
<tbl:generatetoken />
<tbl:hiddenparameters pass="txtOpGroupID/txtOpGroupName/txtOpGroupDesc"/> 
<input type="hidden" name="opGroupID" value="<tbl:writeparam name="txtOpGroupID"/>"/>
<input type="hidden" name="privID" value=""/>
<input type="hidden" name="checkFlags" value=""/>
<input type="hidden" name="Action" value=""/>
<input type="hidden" name="func_flag" value="110"/>
</form>

<Script language="javascript">
<!--
function checkForm() {
    document.frmPost.privID.value = '';
    document.frmPost.checkFlags.value = ";";
    if (document.frames.PossessedPrivilege.txtPrivID != null) {
        if (document.frames.PossessedPrivilege.txtPrivID.length > 1) {
            for (i = 0; i < document.frames.PossessedPrivilege.txtPrivID.length; i++) {
                if (document.frames.PossessedPrivilege.Flags[i].checked) {
                    document.frmPost.checkFlags.value=document.frames.PossessedPrivilege.Flags[i].value+";"+document.frmPost.checkFlags.value;
                    document.frmPost.privID.value=document.frames.PossessedPrivilege.txtPrivID[i].value+ ";" + document.frmPost.privID.value ;
                }
            }
        } else {
            if (document.frames.PossessedPrivilege.Flags.checked) {
                document.frmPost.checkFlags.value=document.frames.PossessedPrivilege.Flags.value;
                document.frmPost.privID.value = document.frames.PossessedPrivilege.txtPrivID.value;
            }
        }
        if (document.frmPost.privID.value == '') {
            alert("请选定权限!");
            return false;
        }
    } else {
        alert("没有数据！");
        return false;
    }
    return true;
}
function checkAddForm() {
    document.frmPost.privID.value = '';
    document.frmPost.checkFlags.value = ";";
    if (document.frames.OptionalPrivilege.txtPrivID != null) {
        if (document.frames.OptionalPrivilege.txtPrivID.length > 1) {
            for (i = 0; i < document.frames.OptionalPrivilege.txtPrivID.length; i++) {
                if (document.frames.OptionalPrivilege.Flags[i].checked) {
                    document.frmPost.checkFlags.value=document.frames.OptionalPrivilege.Flags[i].value+";"+document.frmPost.checkFlags.value;
                    document.frmPost.privID.value=document.frames.OptionalPrivilege.txtPrivID[i].value+ ";" + document.frmPost.privID.value ;
                }
            }
        } else {
            if (document.frames.OptionalPrivilege.Flags.checked) {
                document.frmPost.checkFlags.value=document.frames.OptionalPrivilege.Flags.value;
                document.frmPost.privID.value = document.frames.OptionalPrivilege.txtPrivID.value;
            }
        }
        if (document.frmPost.privID.value == '') {
            alert("请选定权限!");
            return false;
        }
    } else {
        alert("没有数据！");
        return false;
    }
    return true;
}

function delete_privilege() {
    if (checkForm()) {
        document.frmPost.Action.value="DeleteOperatorGroupPrivilege";
        document.frmPost.action="delete_privilege.do";
        document.frmPost.submit();
    }
}

function add_privilege() {
    if (checkAddForm()) {
        document.frmPost.Action.value="AddOperatorGroupPrivilege";
        document.frmPost.action="add_privilege.do";
        document.frmPost.submit();
    }
}
//-->
</Script>