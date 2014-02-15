<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="/WEB-INF/oss.tld" prefix="d" %>
<%@ taglib uri="bookmark" prefix="bk" %>
<%@ page import="com.dtv.oss.util.Postern" %>
<%@ page import="com.dtv.oss.web.util.CommonKeys" %>

<%request.setAttribute("feePageType","2");%>
<Script language=javascript>

function create_submit()
{
    document.frmPost.confirm_post.value="true";
    if(document.frmPost.transferType.value=='strange'){
    	document.frmPost.action="customer_strange_transfer_commit.do";
  	}else{
    	document.frmPost.action="customer_vicinal_transfer_commit.do";
 		}
    document.frmPost.submit();
}

function back_submit()
{
    document.frmPost.confirm_post.value="false";
    document.frmPost.forwardFlag.value = '2';
    if(document.frmPost.transferType.value=='strange'){
		if('caculatefee'=='<%=request.getParameter("txtConfirmBackFlag")%>'){
			document.frmPost.action="customer_strange_transfer_fee.screen";
		}else{
			document.frmPost.action="customer_strange_transfer_pay.screen";
		}
  	}else{
  		if('caculatefee'=='<%=request.getParameter("txtConfirmBackFlag")%>'){
			document.frmPost.action="customer_vicinal_transfer_fee.screen";
		}else{
    		document.frmPost.action="customer_vicinal_transfer_pay.screen";
    	}
 	}
    document.frmPost.submit();
}
</SCRIPT>

<table  border="0" align="center" cellpadding="0" cellspacing="10" >
  <tr>
    <td><img src="img/list_tit.gif" width="19" height="19"></td>
    <td class="title">确认过户信息</td>
  </tr>
</table>
<table width="100%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>
<br>
<form name="frmPost" method="post">
    <input type="hidden" name="confirm_post" value="true">
    	
    <tbl:hiddenparameters pass="transferType/txtcsiPayOption" />
		<tbl:hiddenparameters pass="txtCustomerSerialNo/txtContractNo/txtSocialSecCardID/txtEmail/txtNationality/txtFaxNumber"/>
			
   <tbl:hiddenparameters pass="forwardFlag/txtCustomerDTlastmod/txtAddressDTlastmod/txtCustomerID/txtAddressID/txtInstallBill/txtNewName/txtOldDetailAddress/txtCustomerStatusName/txtCustomerStatus/txtNewGender/txtNewDistrict/txtDetailAddress/txtNewPostcode/txtCatvID/txtNewBirthday/txtCustAccount/txtNewCardType/txtNewCardID/txtNewTelephone/txtNewTelephoneMobile/txtName/txtCustomerTypeShow/txtCustomerType/txtDistrict/txtDistrictName/txtPostcode/txtIsInstall/txtIsInstallName/txtPreferedDate/txtPreferedTime/txtPreferedTimeName/txtLoginID/txtLoginPwd/txtOpenSourceType" />
    <input type="hidden" name="func_flag" value="3001" >
    <table align="center" width="100%" border="0" cellspacing="1" cellpadding="5" class="list_bg">
        <tr>
	        <td colspan="4" class="import_tit" align="center"><font size="3">原客户信息</font></td>
        </tr>
        <tr>
            <td class="list_bg2"><div align="right">姓名</div></td>
            <td class="list_bg1"><tbl:writeparam name="txtName"/></td>
            <td class="list_bg2"><div align="right">客户状态</div></td>
            <td class="list_bg1"><tbl:writeparam name="txtCustomerStatusName"/></td>
        </tr>
        <tr>
            <td class="list_bg2"><div align="right">客户类型</div></td>
            <td class="list_bg1"><tbl:writeparam name="txtCustomerTypeShow"/></td>
            <td class="list_bg2"><div align="right">所在区域</div></td>
            <td class="list_bg1"><tbl:writeparam name="txtDistrictName"/></td>
        </tr>
        <tr>
            <td class="list_bg2"><div align="right">安装地址</div></td>
            <td class="list_bg1"><tbl:writeparam name="txtOldDetailAddress"/></td>
            <td class="list_bg2"><div align="right">邮编</div></td>
            <td class="list_bg1"><tbl:writeparam name="txtPostcode"/></td>
        </tr>
        <tr>
	        <td colspan="4" class="import_tit" align="center"><font size="3">新客户信息</font></td>
        </tr>
        <lgc:showcontentbysetting settingName="SET_V_SHOW_CUSTSERIALNO" >
        <tr>
    		<td valign="middle" class="list_bg2" align="right" width="17%">
    			客户条形码<lgc:showcontentbysetting settingName="SET_V_CHECK_CUSTSERIALNO" >*</lgc:showcontentbysetting>
    		</td>
    		<td class="list_bg1" colspan="3">
    			<tbl:writeparam name="txtCustomerSerialNo" />
    		</td>
    	</tr>
    	</lgc:showcontentbysetting>
        <tr>
            <td class="list_bg2"><div align="right">姓名</div></td>
            <td class="list_bg1"><tbl:writeparam name="txtNewName"/></td>
            <td class="list_bg2"><div align="right">性别</div></td>
            <td class="list_bg1"><d:getcmnname typeName="SET_C_CUSTOMERGENDER" match="txtNewGender"/></td>
        </tr>
        <tr>
            <td class="list_bg2"><div align="right">所在区域</div></td>
            <td class="list_bg1">
            	<tbl:writeparam name="txtNewDistrictDesc"/>
	       		</td>
            <td class="list_bg2"><div align="right">新安装地址</div></td>
            <td class="list_bg1"><tbl:writeparam name="txtDetailAddress"/></td>
        </tr>
        <tr>
            <td class="list_bg2"><div align="right">邮编</div></td>
            <td class="list_bg1"><tbl:writeparam name="txtNewPostcode"/></td>
            <td class="list_bg2"><div align="right">&nbsp;<%=Postern.getFieldNameByFieldInterID("CUSTOMER_CATVID")%></div></td>
            <td class="list_bg1"><tbl:writeparam name="txtCatvID"/></td>
        </tr>
        <tr>
	        <td class="list_bg2"><div align="right">出生日期</div></td>
	        <td class="list_bg1"><tbl:writeparam name="txtNewBirthday"/></td>
	        <td class="list_bg2"><div align="right">有效帐户</div></td>
            <td class="list_bg1"><tbl:writeparam name="txtCustAccount"/></td>
	    </tr>
        <tr>
            <td class="list_bg2"><div align="right">证件类型</div></td>
            <td class="list_bg1"><d:getcmnname typeName="SET_C_CUSTOMERCARDTYPE" match="txtNewCardType"/></td>
            <td class="list_bg2"><div align="right">证件号码</div></td>
            <td class="list_bg1"><tbl:writeparam name="txtNewCardID"/></td>
        </tr>
        <tr>
            <td class="list_bg2"><div align="right">固定电话</div></td>
            <td class="list_bg1"><tbl:writeparam name="txtNewTelephone"/></td>
            <td class="list_bg2"><div align="right">移动电话</div></td>
            <td class="list_bg1"><tbl:writeparam name="txtNewTelephoneMobile"/></td>
        </tr>
        <tr>
			    <td valign="middle" class="list_bg2" align="right" >社保卡编号</td>
			    <td class="list_bg1"><tbl:writeparam name="txtSocialSecCardID"/>
			    </td>
			    <td valign="middle" class="list_bg2" align="right" >EMAIL地址</td>
			    <td class="list_bg1"><tbl:writeparam name="txtEmail"/>
			      </td>
	      </tr>
	      <tr>
			    <td valign="middle" class="list_bg2" align="right" >国籍</td>
			    <td class="list_bg1"><d:getcmnname typeName="SET_C_NATIONALITY" match="txtNationality"   />
			      </td>
			    <td valign="middle" class="list_bg2" align="right" >传真</td>
			    <td class="list_bg1"><tbl:writeparam name="txtFaxNumber"/>
			      </td>
        </tr>  
        <tr>
            <td class="list_bg2" align="right">协议编号</td>
            <td class="list_bg1" align="left" colspan="3">
                <tbl:writeparam name="txtContractNo" />
            </td>
        </tr>       
        <tbl:dynamicservey serveyType="M"  showType="label" tdWordStyle="list_bg2" tdControlStyle="list_bg1"  controlSize="23"/>

    </table>

    <tbl:CommonFeeAndPaymentInfo includeParameter="Fee_Pay" payCsiType="<%=CommonKeys.CUSTSERVICEINTERACTIONTYPE_UT%>" acctshowFlag ="true"  confirmFlag="true" />		 

    <br>
		<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
		  <tr>
		    <td><img src="img/mao.gif" width="1" height="1"></td>
		  </tr>
		</table>
    <br>

     <table  border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td width="11" height="20"><img src="img/button2_r.gif" width="22" height="20"></td>
        <td><input name="Submit" type="button" class="button" value="上一步" onclick="javascript:back_submit()"></td>
        <td width="22" height="20"><img src="img/button2_l.gif" width="11" height="20"></td>
        <td width="20" ></td>
        <td width="11" height="20"><img src="img/button_l.gif" width="11" height="20"></td>
        <td><input name="Submit" type="button" class="button" value="确 认" onclick="javascript:create_submit()"></td>
        <td width="22" height="20"><img src="img/button_r.gif" width="22" height="20"></td>
      </tr>
    </table>
    <tbl:generatetoken />
</form>