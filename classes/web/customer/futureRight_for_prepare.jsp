<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="bookmark" prefix="bk" %>
<%@ taglib uri="resultset" prefix="rs" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="osstags" prefix="d" %>

<%@ page import="com.dtv.oss.util.Postern" %>
<%@ page import="com.dtv.oss.dto.FutureRightDTO" %>


<table  border="0" align="center" cellpadding="0" cellspacing="10" >
  <tr>
    <td><img src="img/list_tit.gif" width="19" height="19"></td>
    <td class="title">期权维护</td>
  </tr>
</table>
<form name="frmPost" method="post" action="" >
 <lgc:bloop requestObjectName="ResponseQueryResult" item="oneline" isOne="true">
 <%
    FutureRightDTO futureRightDto = (FutureRightDTO)pageContext.getAttribute("oneline");
    String operatorName=(Postern.getOperatorNameByID(futureRightDto.getCreateOpID())==null) ? "" :Postern.getOperatorNameByID(futureRightDto.getCreateOpID());
    String excutorName =(Postern.getOperatorNameByID(futureRightDto.getExcuteOpID())==null) ? "" :Postern.getOperatorNameByID(futureRightDto.getExcuteOpID());
    String cancelOpName =(Postern.getOperatorNameByID(futureRightDto.getCancelOpID())==null) ? "" :Postern.getOperatorNameByID(futureRightDto.getCancelOpID());
 %>
     <table width="100%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
        <tr>
          <td width="17%" class="list_bg2" align="right">单据号</td>
          <td width="33%"  class="list_bg1" align="left">
          <input type="text" name="txtRefersheetId" size="25" maxlength="10" value="<tbl:write  name="oneline" property="referSheetID" />" class="textgray"  readonly>
          </td>
          <td width="17%" class="list_bg2" align="right">受理单号</td>
          <td width="33%"  class="list_bg1" align="left">                                  
          <input type="text" name="txtCsiID" size="25"  value="<tbl:writenumber name="oneline" property="csiId" digit="7" hideZero="true" />" class="textgray" readonly>
          </td> 
        </tr>  
        <tr>
          <td width="17%" class="list_bg2" align="right">客户ID</td>
          <td width="33%"  class="list_bg1" align="left">
            <input type="text" name="txtCustomerID" size="25"  value="<tbl:write name="oneline" property="customerID" />" class="textgray" readonly>
          </td>          
          <td width="17%" class="list_bg2" align="right"> 账户ID</td>
          <td width="33%"  class="list_bg1" align="left">
           <d:selAccByCustId name="txtAccountId" mapName="self" canDirect="true"  match="txtAccountId" empty="false" width="23"  />
          </td> 
        </tr>
        <tr>
          <td width="17%" class="list_bg2" align="right">金额</td>
          <td width="33%"  class="list_bg1" align="left">
          <input type="text" name="txtValue" size="25"  value="<tbl:write name="oneline" property="value" />" class="textgray" readonly>
          </td>
          <td width="17%" class="list_bg2" align="right">状态</td>
          <td width="33%"  class="list_bg1" align="left">
              <input type="text" name="txtstatusshow" size="25" value="<d:getcmnname typeName="SET_F_FUTURERIGHTSTATUS" match="oneline:status" />" class="textgray" readonly>
           <input type="hidden" name="txtstatus" size="25"  value="<tbl:write name="oneline" property="status" />" >
           </td> 
        </tr>
        <tr>
          <td width="17%" class="list_bg2" align="right">期权锁定日期</td>
          <td width="33%"  class="list_bg1" align="left">
          <input type="text" name="txtLockTodate" size="25"  value="<tbl:writedate name="oneline" property="lockToDate" />" class="textgray" readonly>
          </td> 
          <td width="17%" class="list_bg2" align="right">创建日期</td>
          <td width="33%"  class="list_bg1" align="left">
          <input type="text" name="txtCreateDate" size="25"  value="<tbl:writedate name="oneline" property="createDate" />" class="textgray" readonly>
          </td> 
        </tr>
        <tr>
          <td width="17%" class="list_bg2" align="right">创建操作员</td>
          <td width="33%"  class="list_bg1" align="left">
            <input type="text" name="operatorName" size="25" value="<%=operatorName%>" class="textgray" readonly>
          </td> 
          <td width="17%" class="list_bg2" align="right">创建部门</td>
          <td width="33%"  class="list_bg1" align="left">
              <input type="text" name="txtCreateOrgID" size="25"  value="<d:getorgname match="oneline:createOrgID" />" class="textgray" readonly>
          </td> 
        </tr>
        <tr>
          <td width="17%" class="list_bg2" align="right">实际行权日期</td>
          <td width="33%"  class="list_bg1" align="left">
            <input type="text" name="txtExcuteDate" size="25" value="<tbl:writedate name="oneline" property="excuteDate" />" class="textgray" readonly>
          </td> 
          <td width="17%" class="list_bg2" align="right">行权操作员</td>
          <td width="33%"  class="list_bg1" align="left">
            <input type="text" name="excutorName" size="25" value="<%=excutorName%>" class="textgray" readonly>
          </td> 
        </tr>  
        <tr>
          <td width="17%" class="list_bg2" align="right">行权部门</td>
          <td width="33%"  class="list_bg1" align="left">
            <input type="text" name="txtExcuteOrgId" size="25"  value="<d:getorgname match="oneline:excuteOrgID" />" class="textgray" readonly>
          </td> 
          <td width="17%" class="list_bg2" align="right">收回取消日期</td>
          <td width="33%"  class="list_bg1" align="left">
          <input type="text" name="txtCancelDate" size="25" value="<tbl:writedate name="oneline" property="cancelDate" />" class="textgray" readonly>
          </td> 
        </tr>
        <tr>
          <td width="17%" class="list_bg2" align="right">收回取消操作员</td>
          <td width="33%"  class="list_bg1" align="left">
            <input type="text" name="cancelOpName" size="25" value="<%=cancelOpName%>" class="textgray" readonly>
          </td>  
          <td width="17%" class="list_bg2" align="right">收回取消部门</td>
          <td width="33%"  class="list_bg1" align="left">
            <input type="text" name="txtCancelOrgId" size="25"  value="<d:getorgname match="oneline:cancelOrgID" />" class="textgray" readonly>
          </td> 
        </tr>    
        <tr>  
          <td width="17%" class="list_bg2" align="right">描述</td>
          <td width="33%"  class="list_bg1" align="left"  colspan="3">
           <input type="text" name="txtDescription" size="75" value="<tbl:write name="oneline" property="Description" />" class="textgray" readonly>
          </td> 
        </tr> 
      </table>
      <BR>  
  </lgc:bloop>
      <input type="hidden" name="confirm_post"  value="true" >
      <input type="hidden" name="operatFlag" value="">
      <tbl:hiddenparameters pass="txtSeqNo" />
      <tbl:hiddenparameters pass="txtCreateStartDate/txtCreateEndDate/txtExcuteStartDate/txtExcuteEndDate/txtCancelStartDate/txtCancelEndDate" />
      <tbl:generatetoken />
 </form>
 <script language=javascript>

function showpage_submit(){
    document.frmPost.action ="futureRight_for_modify.do";
    document.frmPost.submit();
}
showpage_submit();
</script>
           