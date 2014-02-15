<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="bookmark" prefix="bk" %>
<%@ taglib uri="resultset" prefix="rs" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="osstags" prefix="d" %>

<%@ page import="com.dtv.oss.util.Postern" %>
<%@ page import="com.dtv.oss.dto.FutureRightDTO" %>

<script language=javascript>
function check_frm()
{
    if (check_Blank(document.frmPost.txtAccountId, true, "账户ID"))
   	return false;
	
    return true;
}
function frm_submit()
{
    if (check_frm()){
      var msg =confirm("是否要兑现期权？");
      if (msg==false){
         return;
      } else {
        document.frmPost.operatFlag.value ="Encash";
        document.frmPost.action ="futureRight_modify.do";
        document.frmPost.submit();
      }
   }
}
function cancel_submit(){
	if(confirm("是否要取消期权？")){
   		document.frmPost.operatFlag.value ="Cancel";
   		document.frmPost.action ="futureRight_cancle.do";
   		document.frmPost.submit();
   	}
}

function back_submit(){
    document.frmPost.txtSeqNo.value ="";
    document.frmPost.action ="futureRight_query_result.do";
    document.frmPost.submit();
}

</script>
<table  border="0" align="center" cellpadding="0" cellspacing="10" >
  <tr>
    <td><img src="img/list_tit.gif" width="19" height="19"></td>
    <td class="title">期权维护</td>
  </tr>
</table>
<form name="frmPost" method="post" action="" >
     <table width="100%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
        <tr>
          <td width="17%" class="list_bg2" align="right">单据号</td>
          <td width="33%"  class="list_bg1" align="left">
          <input type="text" name="txtRefersheetId" size="25" maxlength="10" value="<tbl:writeparam   name="txtRefersheetId" />" class="textgray"  readonly>
          </td>
          <td width="17%" class="list_bg2" align="right">受理单号</td>
          <td width="33%"  class="list_bg1" align="left">                                  
          <input type="text" name="txtCsiID" size="25"  value="<tbl:writeparam name="txtCsiID" />" class="textgray" readonly>
          </td> 
        </tr>  
        <tr>
          <td width="17%" class="list_bg2" align="right">客户ID</td>
          <td width="33%"  class="list_bg1" align="left">
            <input type="text" name="txtCustomerID" size="25"  value="<tbl:writeparam name="txtCustomerID" />" class="textgray" readonly>
          </td>          
          <td width="17%" class="list_bg2" align="right"> 账户ID</td>
          <td width="33%"  class="list_bg1" align="left">
          <%if("V".equals(request.getParameter("txtstatus"))){ %>
           <d:selAccByCustId name="txtAccountId" mapName="self" canDirect="true"  match="txtAccountId" empty="false" width="23"  />
          <%}else{ %>
          <d:selAccByCustId name="txtAccountId" mapName="self" canDirect="true"  match="txtAccountId" empty="false" width="23" disabled="true" />        
          <%} %>
          </td> 
        </tr>
        <tr>
          <td width="17%" class="list_bg2" align="right">金额</td>
          <td width="33%"  class="list_bg1" align="left">
          <input type="text" name="txtValue" size="25"  value="<tbl:writeparam name="txtValue" />" class="textgray" readonly>
          </td>
          <td width="17%" class="list_bg2" align="right">状态</td>
          <td width="33%"  class="list_bg1" align="left">
             <input type="text" name="txtstatusshow" size="25" value="<tbl:writeparam name="txtstatusshow" />" class="textgray" readonly>
            <input type="hidden" name="txtstatus" size="25"  value="<tbl:writeparam name="txtstatus" />" >
          </td> 
        </tr>
        <tr>
          <td width="17%" class="list_bg2" align="right">期权锁定日期</td>
          <td width="33%"  class="list_bg1" align="left">
          <input type="text" name="txtLockTodate" size="25"  value="<tbl:writeparam name="txtLockTodate" />" class="textgray" readonly>
          </td> 
          <td width="17%" class="list_bg2" align="right">创建日期</td>
          <td width="33%"  class="list_bg1" align="left">
          <input type="text" name="txtCreateDate" size="25"  value="<tbl:writeparam name="txtCreateDate" />" class="textgray" readonly>
          </td> 
        </tr>
        <tr>
          <td width="17%" class="list_bg2" align="right">创建操作员</td>
          <td width="33%"  class="list_bg1" align="left">
            <input type="text" name="operatorName" size="25" value="<tbl:writeparam name="operatorName"/>" class="textgray" readonly>
          </td> 
          <td width="17%" class="list_bg2" align="right">创建部门</td>
          <td width="33%"  class="list_bg1" align="left">
              <input type="text" name="txtCreateOrgID" size="25"  value="<tbl:writeparam  name="txtCreateOrgID" />" class="textgray" readonly>
          </td> 
        </tr>
        <tr>
          <td width="17%" class="list_bg2" align="right">实际行权日期</td>
          <td width="33%"  class="list_bg1" align="left">
            <input type="text" name="txtExcuteDate" size="25" value="<tbl:writeparam  name="txtExcuteDate" />" class="textgray" readonly>
          </td> 
          <td width="17%" class="list_bg2" align="right">行权操作员</td>
          <td width="33%"  class="list_bg1" align="left">
            <input type="text" name="excutorName" size="25" value="<tbl:writeparam name="excutorName"/>" class="textgray" readonly>
          </td> 
        </tr>  
        <tr>
          <td width="17%" class="list_bg2" align="right">行权部门</td>
          <td width="33%"  class="list_bg1" align="left">
            <input type="text" name="txtExcuteOrgId" size="25"  value="<tbl:writeparam name="txtExcuteOrgId" />" class="textgray" readonly>
          </td> 
          <td width="17%" class="list_bg2" align="right">收回取消日期</td>
          <td width="33%"  class="list_bg1" align="left">
          <input type="text" name="txtCancelDate" size="25" value="<tbl:writeparam  name="txtCancelDate" />" class="textgray" readonly>
          </td> 
        </tr>
        <tr>
          <td width="17%" class="list_bg2" align="right">收回取消操作员</td>
          <td width="33%"  class="list_bg1" align="left">
            <input type="text" name="cancelOpName" size="25" value="<tbl:writeparam name="cancelOpName"/>" class="textgray" readonly>
          </td>  
          <td width="17%" class="list_bg2" align="right">收回取消部门</td>
          <td width="33%"  class="list_bg1" align="left">
            <input type="text" name="txtCancelOrgId" size="25"  value="<tbl:writeparam name="txtCancelOrgId" />" class="textgray" readonly>
          </td> 
        </tr>    
        <tr>  
          <td width="17%" class="list_bg2" align="right">描述</td>
          <td width="33%"  class="list_bg1" align="left"  colspan="3">
           <input type="text" name="txtDescription" size="75" value="<tbl:writeparam  name="txtDescription" />" class="textgray" readonly>
          </td> 
        </tr> 
      </table>
      <BR>  
      <table align="center"  border="0" cellspacing="0" cellpadding="0">
         <tr>
         	<td width="11" height="20"><img src="img/button2_r.gif" width="22" height="20"></td>
          	<td><input name="Submit" type="button" class="button" value="返    回" onclick="javascript:back_submit()"></td>
		  	<td width="22" height="20"><img src="img/button2_l.gif" width="11" height="20"></td>
		  	
          <% 
              if ("V".equals(request.getParameter("txtstatus"))){ 
          %>
          	<td width="20" ></td>
          	<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
          	<td><input name="Submit" type="button" class="button" value="兑现期权" onclick="javascript:frm_submit()"></td>
		  	<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
          	<td width="20" ></td>
          	<td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
          	<td><input name="Submit" type="button" class="button" value="取消期权" onclick="javascript:cancel_submit()"></td>
		  	<td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
          	
           <% } %>
           	
        </tr>
      </table>
      <input type="hidden" name="confirm_post"  value="true" >
      <input type="hidden" name="operatFlag" value="">
      <tbl:hiddenparameters pass="txtSeqNo" />
      <tbl:hiddenparameters pass="txtCreateStartDate/txtCreateEndDate/txtExcuteStartDate/txtExcuteEndDate/txtCancelStartDate/txtCancelEndDate" />
      <tbl:generatetoken />
 </form>
           