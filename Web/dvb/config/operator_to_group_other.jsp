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
		<td class="title">操作员所属操作员组配置</td>
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
	   <td class="list_bg2"><div align="right">操作员姓名</div></td>         
           <td class="list_bg1" align="left">
             <input type="text" name="txtOperatorName" size="22"  value="<tbl:writeparam name="txtOperatorName"/>" class="textgray" readonly >
           </td>       
           <td class="list_bg2"><div align="right">操作员所属组织 </div></td>
           <td class="list_bg1" align="left">
           <input type="text" name="txtOrgName" maxlength="10" size="22"  value="<tbl:writeparam name="txtOrgName"/>" class="textgray" readonly >
           </td>
     </tr> 
     
     <tr>
         <td class="list_bg2"><div align="right">操作员描述 </div></td>
           <td class="list_bg1" align="left" colspan="3">
          <input type="text" name="txtOperatorDesc" maxlength="10" size="22"   value="<tbl:writeparam name="txtOperatorDesc"/>" class="textgray" readonly >
          </td>
      </tr>
      
     <tr>
          <td class="list_bg2" colspan="4"><div align="center"><strong>操作员组</strong></div></td>
     </tr>
     <tr>
          <td class="list_bg2" colspan="2"><div align="center">所属的操作员组</div></td>
          <td class="list_bg2" colspan="2"><div align="center">可选的操作员组</div></td>
     </tr>
     </table>
     <table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" class="list_bg">
     <tr>
          <td class="list_bg2" align="center" ><font size="2">
          <br>
         <iframe SRC="possessed_opgroup.screen?txtOperatorID=<tbl:writeparam name="txtOperatorID" />&checkFlags=<%=checkFlags%>"
            id="PossessedOpGroup" name="PossessedOpGroup" width="350" height="300">
  	      </iframe>
          </font></td>
           <td width=40 class="list_bg2"> <table align="center" border="0" cellspacing="0" cellpadding="0">
    	<tr>
    	<td><img src="img/button2_r.gif" width="22" height="20" style="position:relative;top:4px;"><input name="Submit" type="button" class="button"
					value="添&nbsp;加" onclick="javascript:add_opgroup()"><img src="img/button2_l.gif" width="11" height="20" style="position:relative;top:4px;"></td>
           
           </tr><tr>
           <td height=20 ></td>
           </tr>
           <tr>
           <td ><img src="img/button_l.gif" width="11" height="20" style="position:relative;top:4px;"><input name="Submit" type="button" class="button"
					value="去&nbsp;除" onclick="javascript:delete_opgroup()"><img src="img/button_r.gif" width="22" height="20" style="position:relative;top:4px;"></td>      
           
         </tr>
      </table>   
       </td>
          <td valign="middle" class="list_bg2" align="center" colspan="2"><font size="2">
          <iframe SRC="optional_opgroup.screen?txtOperatorID=<tbl:writeparam name="txtOperatorID" />&checkFlags=<%=checkFlags%>"
            id="OptionalOpGroup" name="OptionalOpGroup" width="350" height="300">
  	      </iframe>
          </font></td>
     </tr>
      </table> 
     <br>
 <table align="center" border="0" cellspacing="0" cellpadding="0">
         <td valign="middle"  colspan="4">
            <table align="center" border="0" cellspacing="0" cellpadding="0">
			   <tr>
			       <td><img src="img/button2_r.gif" border="0" ></td>
			        <td background="img/button_bg.gif"><a href="<bk:backurl property="operator_query.do/config_operator_detail.do"/>" class="btn12">返回</a></td>
			        <td><img src="img/button2_l.gif" border="0" ></td>
		       </tr>
	       </table>
         </td>
     </tr>
     
</table>
<form name="frmPost" action="" method="post" >
<tbl:generatetoken />
<tbl:hiddenparameters pass="txtOperatorID/txtOperatorName/txtOrgName/txtOperatorDesc"/> 
<input type="hidden" name="operatorID" value="<tbl:writeparam name="txtOperatorID"/>"/>
<input type="hidden" name="opGroupID" value=""/>
<input type="hidden" name="checkFlags" value=""/>
<input type="hidden" name="Action" value=""/>
<input type="hidden" name="func_flag" value="110"/>
</form>

<Script language="javascript">
<!--
function checkForm() {
    document.frmPost.opGroupID.value = '';
    document.frmPost.checkFlags.value = ";";
    if (document.frames.PossessedOpGroup.txtOpGroupID != null) {
        if (document.frames.PossessedOpGroup.txtOpGroupID.length > 1) {
            for (i = 0; i < document.frames.PossessedOpGroup.txtOpGroupID.length; i++) {
                if (document.frames.PossessedOpGroup.Flags[i].checked) {
                    document.frmPost.checkFlags.value=document.frames.PossessedOpGroup.Flags[i].value+";"+document.frmPost.checkFlags.value;
                    document.frmPost.opGroupID.value=document.frames.PossessedOpGroup.txtOpGroupID[i].value+ ";" + document.frmPost.opGroupID.value ;
                }
            }
        } else {
            if (document.frames.PossessedOpGroup.Flags.checked) {
                document.frmPost.checkFlags.value=document.frames.PossessedOpGroup.Flags.value;
                document.frmPost.opGroupID.value = document.frames.PossessedOpGroup.txtOpGroupID.value;
            }
        }
        if (document.frmPost.opGroupID.value == '') {
            alert("请选定操作员组!");
            return false;
        }
    } else {
        alert("没有数据！");
        return false;
    }
    return true;
}
function checkAddForm() {
    document.frmPost.opGroupID.value = '';
    document.frmPost.checkFlags.value = ";";
    if (document.frames.OptionalOpGroup.txtOpGroupID != null) {
        if (document.frames.OptionalOpGroup.txtOpGroupID.length > 1) {
            for (i = 0; i < document.frames.OptionalOpGroup.txtOpGroupID.length; i++) {
                if (document.frames.OptionalOpGroup.Flags[i].checked) {
                    document.frmPost.checkFlags.value=document.frames.OptionalOpGroup.Flags[i].value+";"+document.frmPost.checkFlags.value;
                    document.frmPost.opGroupID.value=document.frames.OptionalOpGroup.txtOpGroupID[i].value+ ";" + document.frmPost.opGroupID.value ;
                }
            }
        } else {
            if (document.frames.OptionalOpGroup.Flags.checked) {
                document.frmPost.checkFlags.value=document.frames.OptionalOpGroup.Flags.value;
                document.frmPost.opGroupID.value = document.frames.OptionalOpGroup.txtOpGroupID.value;
            }
        }
        if (document.frmPost.opGroupID.value == '') {
            alert("请选定操作员组!");
            return false;
        }
    } else {
        alert("没有数据！");
        return false;
    }
    return true;
}

function delete_opgroup() {
    if (checkForm()) {
        document.frmPost.Action.value="DeleteOperatorGroup";
        document.frmPost.action="delete_opgroup_config.do";
        document.frmPost.submit();
    }
}

function add_opgroup() {
    if (checkAddForm()) {
        document.frmPost.Action.value="AddOperatorGroup";
        document.frmPost.action="add_opgroup_config.do";
        document.frmPost.submit();
    }
}
//-->
</Script>