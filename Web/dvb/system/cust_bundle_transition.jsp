<%--Copyright 2003 Digivision, Inc. All rights reserved.--%>
<%--$Id: cust_bundle_transition.jsp,v 1.1.1.1 2010/01/25 09:11:16 yangyong Exp $--%>
<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="osstags" prefix="d" %>
<%@ taglib uri="bookmark" prefix="bk" %>
<%@ page import="com.dtv.oss.util.Postern,
				 com.dtv.oss.dto.CustomerDTO,
                 java.util.*" %>
<%@ page import="com.dtv.oss.web.util.WebUtil" %>

<Script language=JavaScript>

function check_frm()
{	
	var num=0;
	document.frmPost.txtCampaignID.value="";
	if (document.frames.FrameCampaign.listID!=null){
             if (document.frames.FrameCampaign.listID.length > 1) {
	    	for (j=0;j<document.frames.FrameCampaign.listID.length;j++){
			if (document.frames.FrameCampaign.listID[j].checked){
				document.frmPost.txtCampaignID.value=document.frames.FrameCampaign.listID[j].value;
				num++;
			}
	    	}
	    } else{
	        if (document.frames.FrameCampaign.listID.checked){
	           document.frmPost.txtCampaignID.value=document.frames.FrameCampaign.listID.value;
	           num++;
	        }
	    }    
	}
	if(num>1 || document.frmPost.txtCampaignID.value==""){
		alert("您没有选择套餐，或者您选择的套餐数目大于1");
		return false;
	}
	
	return true;
}

function frm_submit(){
	if(check_frm()){
		if(confirm("确定要转换客户套餐？")){
			document.frmPost.submit();
		}
	}

}

</Script>
<table  border="0" align="center" cellpadding="0" cellspacing="10" >
  <tr>
    <td><img src="img/list_tit.gif" width="19" height="19"></td>
    <td class="title">套餐转换</td>
  </tr>
</table>
 
<table width="100%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>

<form name="frmPost" method="post" action="cust_campaign_op.do" >
<input type="hidden" name="txtActionType" value="bundleTransition" >
<input type="hidden" name="txtCampaignID" value="" >
      <table align="center" width="100%" border="0" cellspacing="1" cellpadding="3" class="list_bg" >
        <tr>
          <td class="list_bg2" align="right" width="17%">客户套餐ID(老)</td>
          <td class="list_bg1"  align="left" width="33%">
          <input type="text" name="txtCcID" size="25"  value="<tbl:writeparam name="txtCcID"/>" class="textgray" readonly >
          </td>
          <td class="list_bg2" align="right" width="17%">套餐名称(老)</td>
          <td class="list_bg1"  align="left" width="33%">
             <input type="text" name="txtCampaignName" size="25"  value="<tbl:writeparam name="txtCampaignName"/>" class="textgray" readonly >
          </td> 
        </tr>
        <tr>
          
          <td class="list_bg2" align="right">业务账户ID(老)</td>
          <td class="list_bg1"  align="left" >
          <input type="text" name="txtServiceAccoutID" size="25"  value="<tbl:writeparam name="txtServiceAccoutID"/>" class="textgray" readonly >
          </td>
          <td class="list_bg2" align="right">账户ID(老)</td>
          <td class="list_bg1"  align="left"  >
          <input type="text" name="txtAccoutID" size="25"  value="<tbl:writeparam name="txtAccoutID"/>" class="textgray" readonly >
          </td>
        </tr>

			  <tr>
				  <td class="list_bg2" align="right">套餐有效期不变</td>
				  <td class="list_bg1" align="left" colspan="3" ><font size="2">
					   <d:selcmn name="txtChange" mapName="SET_G_YESNOFLAG" match="Y" width="23" />
				  </td>
			  </tr>
    	  <tr>
          <td colspan="4" class="import_tit" align="center"><font size="3">请选择新套餐</font></td>
    		</tr>
      	<tr> 
          <td class="list_bg1" colspan="4" align="center"><font size="2">
          <%
          	String strsrc="list_campaign.do?txtCampaignType=B"; 
          %>
             <iframe SRC="<%=strsrc%>" name="FrameCampaign" width="320" height="260">  	  
  	     </iframe>
          </font></td>
      	</tr>
    </table>  
      <BR>  
      
      <table align="center"  border="0" cellspacing="0" cellpadding="0">
        <tr>
           <td width="11" height="20"><img src="img/button2_r.gif" width="22" height="20"></td>
		   <td background="img/button_bg.gif"><a href="<bk:backurl property="cust_bundle_detail.do" />" class="btn12">返    回</a></td> 
		   <td width="22" height="20"><img src="img/button2_l.gif" width="11" height="20"></td>  
	       <td width="20" ></td>
	       <td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
		   <td background="img/button_bg.gif"><a href="javascript:frm_submit()" class="btn12">确&nbsp;&nbsp;定</a></td> 
		   <td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>       
        </tr>
      </table>
</form>