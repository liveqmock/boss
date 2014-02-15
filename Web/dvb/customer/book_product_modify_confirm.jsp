<%@ page import="com.dtv.oss.util.Postern,com.dtv.oss.web.util.CommonKeys"%>
<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="osstags" prefix="d" %>
<%@ taglib uri="bookmark" prefix="bk" %>

<%@ page import="com.dtv.oss.util.Postern,
                 java.util.*" %>
<%@ page import="com.dtv.oss.service.util.CsrBusinessUtility" %>
<%@ page import="com.dtv.oss.web.util.WebUtil" %>
<%@ page import="com.dtv.oss.util.Postern" %>
<%@ page import="com.dtv.oss.web.util.CommonKeys" %>

<%
String timeFlag="SET_C_CSIPREFEREDTIME";
Map mapPreferedTime = Postern.getAllPreferedTime(timeFlag);
pageContext.setAttribute("AllPREFEREDTIME", mapPreferedTime);

String txtPreferedTime = (request.getParameter("txtPreferedTime")==null) ? "" : request.getParameter("txtPreferedTime");
String txtContactPerson = (request.getParameter("txtContactPerson")==null) ? "" : request.getParameter("txtContactPerson");
String txtContactPhone = (request.getParameter("txtContactPhone")==null) ? "" : request.getParameter("txtContactPhone");
String txtPreferedDate = (request.getParameter("txtPreferedDate")==null) ? "" : request.getParameter("txtPreferedDate");

String ProductList =(request.getParameter("ProductList")==null) ? "" : request.getParameter("ProductList");
String CampaignList =(request.getParameter("CampaignList")==null) ? "" : request.getParameter("CampaignList");
String lstProdPkg = request.getParameter("ProductList");
String lstCamp = request.getParameter("CampaignList");

String txtCustomerID = request.getParameter( "txtCustomerID");
String txtAccount=request.getParameter( "txtAccount");

String strPkgName = "";
String strCampName = "";
int iTmp;
if (WebUtil.StringHaveContent(lstProdPkg))
{
    String[] aProdPkg = lstProdPkg.split(";");
    for (int i=0; i<aProdPkg.length; i++)
    {
       iTmp = WebUtil.StringToInt(aProdPkg[i]);
       strPkgName = strPkgName + String.valueOf(i+1) + ":" + Postern.getPackagenameByID(iTmp)+" ";
    }
}    

if (WebUtil.StringHaveContent(lstCamp))
{
    String[] aCamp = lstCamp.split(";");
    for (int i=0; i<aCamp.length; i++)
    {
       iTmp = WebUtil.StringToInt(aCamp[i]);
       strCampName = strCampName + String.valueOf(i+1) + ":" + Postern.getCampaignNameByID(iTmp)+" ";
    }   
}   
%>
<Script language=JavaScript>

function frm_submit() {
	document.frmPost.submit();
}
function frm_back(){
	document.frmPost.action= "menu_book_product.do";
	document.frmPost.submit();
}
</script>
<div id="updselwaitlayer" style="position:absolute; left:650px; top:170px; width:250px; height:24px; display:none">
    <table width="100%" border="0" cellspacing="1" cellpadding="3">
        <tr>
          <td width="100%"><div align="center">
          <font size="2">
          正在获取内容。。。
          </font>
          </td>
        </tr>
    </table>
</div>
  <form name="frmPost" method="post" action="book_product_modify_confirm.do" >
<table  border="0" align="center" cellpadding="0" cellspacing="10" >
  <tr>
    <td><img src="img/list_tit.gif" width="19" height="19"></td>
    <td class="title">预约新增产品修改确认</td>
  </tr>
</table>
  <br>
  <table width="100%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
	<tr>
	  <td><img src="img/mao.gif" width="1" height="1"></td>
	</tr>
   </table>
   <br>
   <table width="100%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
  <tr>
    <td class="list_bg2" width="17%"><div align="right">联系人</div></td>
    <td class="list_bg1" width="33%"><%=txtContactPerson%></td>
    <td class="list_bg2" width="17%"><div align="right">联系电话</div></td>
    <td class="list_bg1" width="33%"><%=txtContactPhone%></td>
  </tr>
  <tr>
    <td class="list_bg2" width="17%"><div align="right">业务帐户ID</div></td>
    <td class="list_bg1" width="33%"><tbl:writeparam name="txtServiceAccountID"/>
    </td>
    <td class="list_bg2" width="17%"><div align="right">付费帐户</div></td>    
    <td class="list_bg1" width="33%"><tbl:writeparam name="txtAccount"/></td>
  </tr>
    <tr>
    <td class="list_bg2" width="17%"><div align="right">预约生效时间</div></td>
    <td class="list_bg1" width="33%"><tbl:writeparam name="txtScheduleTime"/></td>
    <tbl:csiReason csiType="BP" name="txtCsiCreateReason" action="N" showType="label" tdWordStyle="list_bg2" tdControlStyle="list_bg1" forceDisplayTD="true" match="txtCSICreateReason" />     
  </tr>
  </table>

<table align="center" width="100%" border="0" cellspacing="1" cellpadding="3" class="list_bg">
      <tr> 
           <td colspan="2" class="import_tit" align="center"><font size="3">产品包购买信息</font></td>
      </tr>
	<tr>
		<td class="list_bg2" width="17%"><div align="right">产品包</div></td>
	    <td class="list_bg1"><%=strPkgName%></td>
	</tr>  
	<tr>
		<td class="list_bg2" width="17%"><div align="right">优惠活动</div></td>
	    <td class="list_bg1"><%=strCampName%></td>
	</tr>  
</TABLE> 
<input type="hidden" name="txtCustomerID" value="<%=txtCustomerID%>">
<input type="hidden" name="txtAccount" value="<%=txtAccount%>">
<input type="hidden" name="txtServiceAccountID" value="<tbl:writeparam name="txtServiceAccountID"/>">
<input type="hidden" name="ProductList" value="<%=ProductList%>">
<input type="hidden" name="CampaignList" value="<%=CampaignList%>">

<input type="hidden" name="txtContactPerson" value="<%=txtContactPerson%>">
<input type="hidden" name="txtContactPhone" value="<%=txtContactPhone%>">

<input type="hidden" name="txtScheduleTime" value="<tbl:writeparam name="txtScheduleTime"/>">
<input type="hidden" name="txtActionType" value="<%=CommonKeys.BOOK_PRODUCT_MODIFY%>" />

<input type="hidden" name="func_flag" value="121">
<input type="hidden" name="txtType" value="<%=CommonKeys.CUSTSERVICEINTERACTIONTYPE_BP%>">
<tbl:hiddenparameters pass="OpenCampaignList/MutiProductList/SingleProductList/GeneralCampaignList/txtID/txtDtLastmod/txtCsiCreateReason" /> 
</form> 
<br>
<table align="center"  border="0" cellspacing="0" cellpadding="0">
	<tr>
	   <td width="20" ></td>
	   <td width="11" height="20"><img src="img/button2_r.gif" width="22" height="20"></td>
	   <td height="20">
		 <input name="prev" type="button" class="button" onClick="javascript:frm_back()" value="上一步">
	   </td>
	   <td><img src="img/button2_l.gif" width="11" height="20"></td> 
	   <td width="20" >&nbsp;</td> 
	   <td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
	   <td height="20">
		 <input name="next" type="button" class="button" onClick="javascript:frm_submit()" value="确  认">
	   </td>
	   <td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>

	</tr>
</table>

