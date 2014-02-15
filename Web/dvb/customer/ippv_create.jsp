<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="osstags" prefix="d" %>
<%@ taglib uri="bookmark" prefix="bk" %>

<%@ page import="com.dtv.oss.util.Postern" %>
<%@ page import="com.dtv.oss.web.util.*" %>
<%@ page import="java.util.*" %>

<Script language=JavaScript>
function next_submit(strcode){
	if(checkCode(strcode)){
		if (check_Blank(document.frmPost.txtCAWalletCode, true, "小钱包")){
			return false;	
		}	
		document.frmPost.submit();
	}
}
function back_submit(){
	document.frmPost.action="<bk:backurl property='ca_wallet_query.do/menu_ippv_create.do/ca_wallet_service_interaction_query.do' />";
	document.frmPost.submit();
}
function checkCode(strcode){
	strcode=strcode+"";
	var requiredlist = '<%=Postern.getRequiredCAWalletList()%>'+"";
	var rlist = requiredlist.split(";");
	var codelist=strcode.split(";");
	if(strcode ==''){
		for(j=0;j<rlist.length;j++){
			var walletj=rlist[j].split(",");

			if(document.frmPost.txtCAWalletCode.value != walletj[0]){				
				alert("必须先创建" + walletj[1]);
				return false;
			}
		}
	}
	for(i=0;i<codelist.length;i++){
		if(document.frmPost.txtCAWalletCode.value==codelist[i]){
			alert("该小钱包已存在，不能重复创建！");
			return false;
		}
	}
	return true;
	
}
</Script>


<form name="frmPost" method="post" action="ippv_create.do" >

    <table  border="0" align="center" cellpadding="0" cellspacing="5">
      <tr>
        <td><img src="img/list_tit.gif" width="19" height="19"></td>
        <td class="title">小钱包创建</td>
      </tr>
    </table>
    <br>
    <table width="100%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
      <tr>
        <td><img src="img/mao.gif" width="1" height="1"></td>
      </tr>
    </table>
    <br>
 
    <table width="100%"  border="0" align="center" cellpadding="4" cellspacing="1" class="list_bg">
       <tr>
	        <td valign="middle" class="list_bg2" align="right" width="17%">业务帐户ID</td>
	        <td class="list_bg1" width="33%">
	          <input name="txtServiceAccountID" value="<tbl:writeparam name="txtServiceAccountID"/>" size="25" readonly class="textgray"/>
	        </td>
	        <td valign="middle" class="list_bg2" align="right" width="17%">设备序列号</td>
	        <td class="list_bg1" width="33%">
	          <input name = "txtScSerialNo" value = "<tbl:writeparam name = "txtScSerialNo"/>" size="25" readonly class="textgray"/>
	        </td>
       </tr>
       <%Map walletMap = Postern.getCAWalletDefineMap();
         pageContext.setAttribute("mapWallet",walletMap);
         String codeList = Postern.getAllWalletCodeBySAID(WebUtil.StringToInt(request.getParameter("txtServiceAccountID")));
         
         %>
         
       <tr>
	        <td valign="middle" class="list_bg2" align="right" >小钱包名称*</td>
	        <td class="list_bg1" colspan="3">
	          <tbl:select name="txtCAWalletCode" set="mapWallet"  match="txtCAWalletCode" width="23" />
	        </td>	        
       </tr>
  </table>  
	<BR> 
	<input type="hidden" name="txtCsiType" value="CAM"/>

	<tbl:hiddenparameters pass="txtCustomerID/txtActionType"/>
     <table align="center"  border="0" cellspacing="0" cellpadding="0">
      
        <tr>  
          <td width="20" ></td>
          <td><img src="img/button2_r.gif" width="22" height="20"></td>
	        <td><input name="prev" type="button" class="button" onClick="back_submit()" value="返&nbsp;回"></td>
          <td><img src="img/button2_l.gif" width="11" height="20"></td>
          <td width="20" ></td>
          <td><img src="img/button_l.gif" width="11" height="20"></td>
          <td><input name="next" type="button" class="button" onClick="next_submit('<%=codeList%>')" value="下一步"></td>
          <td><img src="img/button_r.gif" width="22" height="20"></td>  
        </tr>
     </table>
</form>      
		 


