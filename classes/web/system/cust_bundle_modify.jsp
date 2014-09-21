<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="bookmark" prefix="bk" %>
<%@ taglib uri="resultset" prefix="rs" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="osstags" prefix="d" %>

<%@ page import="com.dtv.oss.dto.CustomerCampaignDTO" %>
<%@ page import="com.dtv.oss.util.Postern,java.util.*"%>
<%@ page import="com.dtv.oss.web.util.WebUtil" %>
<Script language=JavaScript>
<!--
function bundle_modify(){
	document.frmPost.action="cust_bundle_modify_op.do";
	document.frmPost.submit();	
}
//-->
</Script>

<table  border="0" align="center" cellpadding="0" cellspacing="10" >
  <tr>
    <td><img src="img/list_tit.gif" width="19" height="19"></td>
    <td class="title">客户套餐维护</td>
  </tr>
</table>
 
<table width="100%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>
<form name="frmPost" method="post" action="" >
      <table align="center" width="100%" border="0" cellspacing="1" cellpadding="3" class="list_bg" > 
        <tr>
          <td class="list_bg2" align="right" width="17%">客户套餐ID</td>
          <td class="list_bg1"  align="left" width="33%">
          <input type="text" name="txtCcID" size="25"  value="<tbl:writeparam name="txtCcID"/>" class="textgray" readonly >
          </td>
          <td class="list_bg2" align="right" width="17%">套餐名称</td>
          <td class="list_bg1"  align="left" width="33%">
             <input type="text" name="txtCampaignName" size="25"  value="<tbl:writeparam name="txtCampaignName"/>" class="textgray" readonly >
          </td> 
        </tr>
        <tr>
          <td class="list_bg2" align="right">账户ID</td>
          <td class="list_bg1"  align="left"  >
          <input type="text" name="txtAccoutID" size="25"  value="<tbl:writeparam name="txtAccoutID"/>" class="textgray" readonly >
          </td>
          <td class="list_bg2" align="right">业务账户ID</td>
          <td class="list_bg1"  align="left" >
          <input type="text" name="txtServiceAccoutID" size="25"  value="<tbl:writeparam name="txtServiceAccoutID"/>" class="textgray" readonly >
          </td>
        </tr>
        <tr>
          <td class="list_bg2" align="right">协议期（开始）</td>
          <td class="list_bg1"  align="left">
             <input type="text" name="txtDateFrom" size="25"  value="<tbl:writeparam name="txtDateFrom"/>" class="textgray" readonly >
          </td>
          <td class="list_bg2" align="right">协议期（结束）</td>
          <td class="list_bg1"  align="left">
            <input type="text" name="txtDateTo" size="25"  value="<tbl:writeparam name="txtDateTo"/>" class="textgray" readonly >
          </td>
        </tr>
        <tr>
          <td class="list_bg2" align="right">状态</td>
          <td class="list_bg1"  align="left" >
          	<input type="text" name="txtStatus" size="25" value="<tbl:writeparam name="txtStatus"/>" class="textgray" readonly >    
          </td>
          <td class="list_bg2" align="right">是否自动延伸</td>
          <td class="list_bg1"  align="left" colspan="3">
          	<d:selcmn name="txtAutoExtendFlag" mapName="SET_G_YESNOFLAG" match="txtAutoExtendFlag" width="23" />
          </td>        </tr>
        <tr>
          <td class="list_bg2" align="right">是否允许暂停</td>
          <td class="list_bg1"  align="left">
          <d:selcmn name="txtAllowPause" mapName="SET_G_YESNOFLAG" match="txtAllowPause" width="23" />
          </td>
        
          <td class="list_bg2" align="right">是否允许迁移</td>
          <td class="list_bg1"  align="left">
          <d:selcmn name="txtAllowTransition" mapName="SET_G_YESNOFLAG" match="txtAllowTransition" width="23" />
					</td> 
        </tr>
        <tr>
          <td class="list_bg2" align="right">是否允许过户</td>
          <td class="list_bg1"  align="left">
          <d:selcmn name="txtAllowTransfer" mapName="SET_G_YESNOFLAG" match="txtAllowTransfer" width="23" />
          </td>
          <td class="list_bg2" align="right">是否允许销户</td>
          <td class="list_bg1"  align="left">
          <d:selcmn name="txtAllowClose" mapName="SET_G_YESNOFLAG" match="txtAllowClose" width="23" />
          </td> 
        </tr>
        <tr>
          <td class="list_bg2" align="right">是否允许变更</td>
          <td class="list_bg1"  align="left" colspan="3" >
          <d:selcmn name="txtAllowAlter" mapName="SET_G_YESNOFLAG" match="txtAllowAlter" width="23" />
          </td>        
        </tr>
        <tr>
          <td class="list_bg2" align="right">备注</td>
          <td class="list_bg1"  align="left" colspan="3" >
 			<input type="text" name="txtComments" size="83" value="<tbl:writeparam name="txtComments"/>" class="text" >
          </td>
        </tr>                
      </table>   
	<input type="hidden" name="txtCustomerID"  value="<tbl:writeparam name="txtCustomerID"/>" >
    <BR>  
      
<table align="center"  border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td width="11" height="20"><img src="img/button2_r.gif" width="22" height="20"></td>
	  <td background="img/button_bg.gif"><a href="<bk:backurl property="cust_bundle_detail.do" />" class="btn12">返&nbsp;&nbsp;回</a></td> 
	  <td width="22" height="20"><img src="img/button2_l.gif" width="11" height="20"></td>  
	  <td width="20" ></td>
    <td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
	  <td background="img/button_bg.gif"><a href="javascript:bundle_modify()" class="btn12">确&nbsp;&nbsp;定</a></td> 
	  <td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>     
	</tr>
</table>
<input type="hidden" name="txtActionType"  value="modifyCustBundle" >
          
</form>

