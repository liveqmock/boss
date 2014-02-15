<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl"%>
<%@ taglib uri="logic" prefix="lgc"%>
<%@ taglib uri="resultset" prefix="rs"%>
<%@ taglib uri="/WEB-INF/oss.tld" prefix="d"%>
<%@ taglib uri="bookmark" prefix="bk" %>

<%@ page import="com.dtv.oss.util.Postern,java.util.*"%>


<SCRIPT language="JavaScript">
function check_form ()
{
    if (check_Blank(document.frmPost.txtSheetType, true, "单据类型"))
	    return false;
    if (check_Blank(document.frmPost.txtFromOrgId, true, "流转起源组织"))
	    return false;
    if (check_Blank(document.frmPost.txtToOrgId, true, "流转目标组织"))
	    return false;
 if (!check_Num(document.frmPost.txtPriority, true, "排列顺序"))
	    return false;
	return true;
}

function ChangeSubType(){    
     if(document.frmPost.txtSheetType.value=="M")
      
       document.frmPost.txtSubType.disabled=false;
        
     else {
        document.frmPost.txtSubType.disabled=true;
        document.frmPost.txtSubType.value="";
    } 
}

function manual_create()
{
    if (check_form())
    {
    	
        document.frmPost.submit();
    }
}

function back_submit(){
	url="<bk:backurl property='manual_transfer_query.do' />";
	if(url=="")
		url="manual_transfer_query.screen";
    document.location.href= url;

}

</SCRIPT>

<form name="frmPost" method="post" action="manual_transfer_create.do">
	<input type="hidden" name="txtFrom" size="20" value="1">
	<input type="hidden" name="txtTo" size="20" value="10">
	<!-- 定义当前操作 -->
	<input type="hidden" name="txtActionType" size="20" value="CREATE">
	<input type="hidden" name="func_flag" size="20" value="1023">
<table border="0" align="center" cellpadding="0" cellspacing="10">
	<tr>
		<td><img src="img/list_tit.gif" width="19" height="19"></td>
		<td class="title">手工流转设置-创建</td>
	</tr>
</table>
<table width="100%" border="0" align="center" cellpadding="0"
	cellspacing="0" background="img/line_01.gif">
	<tr>
		<td><img src="img/mao.gif" width="1" height="1"></td>
	</tr>
</table>
<br>

<table width="810" align="center" border="0" cellspacing="1"
	cellpadding="3">
	 
	<tr>
		 <td class="list_bg2"><div align="right">单据类型*</div></td>
                <td class="list_bg1"  align="left">
                <d:selcmn name="txtSheetType" mapName="SET_S_ROLEORGNIZATIONORGROLE" match="txtSheetType" width="23" onchange="ChangeSubType()"/>
              </td>    	
              <td class="list_bg2" align="right">流转起源组织*</td>
              <td class="list_bg1">
    	      <input type="hidden" name="txtFromOrgId" value="<tbl:writeparam name="txtFromOrgId" />">
	      <input type="text" name="txtFromOrgDesc" size="22" maxlength="50" readonly value="<tbl:WriteOrganizationInfo property="txtFromOrgId" />" >
	      <input name="selOrgButton" type="button" class="button" value="选择" onClick="javascript:sel_organization('C,B,D,P,O','txtFromOrgId','txtFromOrgDesc')">
	      </td>
	</tr>
	<tr>
	     <td class="list_bg2"><div align="right">单据子类型</div></td>
               <td class="list_bg1"  align="left">
               <d:selcmn name="txtSubType" mapName="SET_W_JOBCARDSUBTYPE" match="txtSubType" width="23"  />
             </td>  
              <td class="list_bg2" align="right">流转目标组织*</td>
		<td class="list_bg1" ><font size="2">  
		<input type="hidden" name="txtToOrgId" value="<tbl:writeparam name="txtToOrgId" />">
	      <input type="text" name="txtToOrgDesc" size="22" maxlength="50" readonly value="<tbl:WriteOrganizationInfo property="txtToOrgId" />" >
	      <input name="selOrgButton" type="button" class="button" value="选择" onClick="javascript:sel_organization('C,B,D,P,O','txtToOrgId','txtToOrgDesc')">
	      </td>   	
	      
	   </tr>
	   <tr>	 
            <td class="list_bg2" align="right">排列顺序</td>
		<td class="list_bg1" colspan="3"><input name="txtPriority"
			type="text"   maxlength="10" size="25"
			value="<tbl:writeparam name="txtPriority" />"></td>
	</tr>
	 
</table>

	<table width="100%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
	<tr align="center">
	     <td class="list_bg1">

	  <table align="center"  border="0" cellspacing="0" cellpadding="0">
         <tr>  
            <td><img src="img/button2_r.gif" width="22" height="20"></td>
			<td><input name="Submit" type="button" class="button"
					value="返  回" onclick="javascript:back_submit()"></td>
            <td><img src="img/button2_l.gif" width="11" height="20"></td>

            <td width="20" ></td>  
          
            <td><img src="img/button_l.gif" width="11" height="20"></td>
            <td><input name="Submit" type="button" class="button"
					value="保  存" onclick="javascript:manual_create()"></td>
            <td><img src="img/button_r.gif" width="22" height="20"></td>  
                
        </tr>
      </table>	
	   </td>
	</tr>
    </table>    

</form>
