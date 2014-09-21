<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="resultset" prefix="rs" %>
<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="osstags" prefix="d" %>
<%@ taglib uri="bookmark" prefix="bk" %>
<%@ page import="com.dtv.oss.util.Postern,
               com.dtv.oss.dto.OpGroupDTO,
               java.util.*" %>
 
<%String checkFlags = request.getParameter("checkFlags");
String flag = request.getParameter("txtSystemFlag");
if (checkFlags == null)
    checkFlags = "";%>
    
 <form name="frmPost" method="post">
   <table border="0" align="center" cellpadding="0" cellspacing="10">
    <tr>
    <td colspan="2" height="8"></td>
  </tr>
	<tr>
		<td><img src="img/list_tit.gif" width="19" height="19"></td>
		<td class="title">操作员组操作员配置</td>
	</tr>
</table>
<table width="100%" border="0" align="center" cellpadding="0"
	cellspacing="0" background="img/line_01.gif">
	<tr>
		<td><img src="img/mao.gif" width="1" height="1"></td>
	</tr>
</table>
<br> 

<table width="810" align="center" border="0" cellspacing="1" cellpadding="3">

  
      <tr>
	   <td class="list_bg2"><div align="right">操作员组ID</div></td>         
           <td class="list_bg1" align="left">
             <input type="text" name="txtOpGroupID" size="22"  value="<tbl:writeparam name="txtOpGroupID"/>" class="textgray" readonly >
           </td>       
           <td class="list_bg2"><div align="right">操作员组名称 </div></td>
           <td class="list_bg1" align="left">
           <input type="text" name="txtOpGroupName" maxlength="10" size="22"  value="<tbl:writeparam name="txtOpGroupName"/>" class="textgray" readonly >
           </td>
     </tr> 
     
     <tr>
         <td class="list_bg2"><div align="right">操作员组描述 </div></td>
           <td class="list_bg1" align="left" colspan="3">
          <input type="text" name="txtOpGroupDesc" maxlength="10" size="22"   value="<tbl:writeparam name="txtOpGroupDesc"/>" class="textgray" readonly >
          </td>
      </tr>
       <tr>
          <td class="import_tit"   colspan="4"><font size="3"><div align="center"> 操作员组拥有的操作员</div></font></td>
     </tr>
      <tr>
          <td class="list_bg2" colspan="2"><div align="center">所属的操作员</div></td>
            
          <td class="list_bg2" colspan="2"><div align="center">可选的操作员</div></td>
     </tr>
      
     
    </table>  
     <table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" class="list_bg">
      <tr>
          <td class="list_bg2" align="center" ><font size="2">
          <br>
     
          <td class="list_bg2" align="center" colspan="2"><font size="2">
          <iframe SRC="existed_group_member.screen?txtOpGroupID=<tbl:writeparam name="txtOpGroupID" />&checkFlags=<%=checkFlags%>"
            id="ExistedGroupmember" name="ExistedGroupmember" width="360" height="300">
  	      </iframe><br><br>
          </font></td>
           <%
            if("Y".equals(flag)){
          %>
            <td width=40 class="list_bg2"> <table align="center" border="0" cellspacing="0" cellpadding="0">
    	 <tr>
    	  <td><img src="img/button2_r.gif" width="22" height="20" style="position:relative;top:4px;"><input name="Submit" type="button" class="button"
					disabled=true value="添&nbsp;加" onclick="javascript:add_group_member()"><img src="img/button2_l.gif" width="11" height="20" style="position:relative;top:4px;"></td>
           
           </tr>
           <tr>
           <td height=20 ></td>
           </tr>
           <tr>
           <td ><img src="img/button_l.gif" width="11" height="20" style="position:relative;top:4px;"><input name="Submit" type="button" class="button"
					disabled=true value="去&nbsp;除" onclick="javascript:delete_group_member()"><img src="img/button_r.gif" width="22" height="20" style="position:relative;top:4px;"></td>      
           
           <%} else {%> 
            <td width=40 class="list_bg2"> <table align="center" border="0" cellspacing="0" cellpadding="0">
    	 <tr>
    	  <td><img src="img/button2_r.gif" width="22" height="20" style="position:relative;top:4px;"><input name="Submit" type="button" class="button"
					value="添&nbsp;加" onclick="javascript:add_group_member()"><img src="img/button2_l.gif" width="11" height="20" style="position:relative;top:4px;"></td>
           
           </tr>
           <tr>
           <td height=20 ></td>
           </tr>
           <tr>
           <td ><img src="img/button_l.gif" width="11" height="20" style="position:relative;top:4px;"><input name="Submit" type="button" class="button"
					value="去&nbsp;除" onclick="javascript:delete_group_member()"><img src="img/button_r.gif" width="22" height="20" style="position:relative;top:4px;"></td>      
					<%}%>  
         </tr>
      </table>   
      </td>
          <td valign="middle" class="list_bg2" align="center" colspan="2"><font size="2">
          <iframe SRC="optional_group_member.screen?txtOpGroupID=<tbl:writeparam name="txtOpGroupID" />&checkFlags=<%=checkFlags%>"
            id="OptionalGroupmember" name="OptionalGroupmember" width="360" height="300">
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
			        <td background="img/button_bg.gif"><a href="<bk:backurl property="opgroup_query.do"/>" class="btn12">返 回</a></td>
			        <td><img src="img/button2_l.gif" border="0" ></td>
		       </tr>
	       </table>
         </td>
     </tr>
     
</table>
<form name="frmPost" action="" method="post" >
<tbl:generatetoken />
<tbl:hiddenparameters pass="txtOpGroupID/txtOpGroupName/txtOpGroupDesc"/> 
<input type="hidden" name="opGroupID" value="<tbl:writeparam name="txtOpGroupID"/>"/>
<input type="hidden" name="operatorID" value=""/>
<input type="hidden" name="checkFlags" value=""/>
<input type="hidden" name="Action" value=""/>
<input type="hidden" name="func_flag" value="110"/>
</form>

<Script language="javascript">
<!--
function checkForm() {
    document.frmPost.operatorID.value = '';
    document.frmPost.checkFlags.value = ";";
    if (document.frames.ExistedGroupmember.txtOperatorID != null) {
        if (document.frames.ExistedGroupmember.txtOperatorID.length > 1) {
            for (i = 0; i < document.frames.ExistedGroupmember.txtOperatorID.length; i++) {
                if (document.frames.ExistedGroupmember.Flags[i].checked) {
                    document.frmPost.checkFlags.value=document.frames.ExistedGroupmember.Flags[i].value+";"+document.frmPost.checkFlags.value;
                    document.frmPost.operatorID.value=document.frames.ExistedGroupmember.txtOperatorID[i].value+ ";" + document.frmPost.operatorID.value ;
                }
            }
        } else {
            if (document.frames.ExistedGroupmember.Flags.checked) {
                document.frmPost.checkFlags.value=document.frames.ExistedGroupmember.Flags.value;
                document.frmPost.operatorID.value = document.frames.ExistedGroupmember.txtOperatorID.value;
            }
        }
        if (document.frmPost.operatorID.value == '') {
            alert("请选定操作组成员!");
            return false;
        }
    } else {
        alert("没有数据！");
        return false;
    }
    return true;
}
function checkAddForm() {
    document.frmPost.operatorID.value = '';
    document.frmPost.checkFlags.value = ";";
    if (document.frames.OptionalGroupmember.txtOperatorID != null) {
        if (document.frames.OptionalGroupmember.txtOperatorID.length > 1) {
            for (i = 0; i < document.frames.OptionalGroupmember.txtOperatorID.length; i++) {
                if (document.frames.OptionalGroupmember.Flags[i].checked) {
                    document.frmPost.checkFlags.value=document.frames.OptionalGroupmember.Flags[i].value+";"+document.frmPost.checkFlags.value;
                    document.frmPost.operatorID.value=document.frames.OptionalGroupmember.txtOperatorID[i].value+ ";" + document.frmPost.operatorID.value ;
                }
            }
        } else {
            if (document.frames.OptionalGroupmember.Flags.checked) {
                document.frmPost.checkFlags.value=document.frames.OptionalGroupmember.Flags.value;
                document.frmPost.operatorID.value = document.frames.OptionalGroupmember.txtOperatorID.value;
            }
        }
        if (document.frmPost.operatorID.value == '') {
            alert("请选定操作组成员!");
            return false;
        }
    } else {
        alert("没有数据！");
        return false;
    }
    return true;
}

function delete_group_member() {
    if (checkForm()) {
        document.frmPost.Action.value="DeleteGroupMember";
        document.frmPost.action="delete_group_member.do";
        document.frmPost.submit();
    }
}

function add_group_member() {
    if (checkAddForm()) {
        document.frmPost.Action.value="AddGroupMember";
        document.frmPost.action="add_group_member.do";
        document.frmPost.submit();
    }
}
//-->
</Script>