<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl"%>
<%@ taglib uri="osstags" prefix="d"%>
<%@ taglib uri="bookmark" prefix="bk"%>

<%@ page import="com.dtv.oss.util.Postern,java.util.*"%>
<%@ page import="com.dtv.oss.web.util.WebUtil"%>
<%@ page import="com.dtv.oss.web.util.CommonKeys"%>
<%@ page import="com.dtv.oss.dto.MethodOfPaymentDTO"%>
<%@ page import="com.dtv.oss.util.Organization"%>
<%@ page import="com.dtv.oss.dto.OperatorDTO"%>
<%@ page import="com.dtv.oss.web.util.WebCurrentOperatorData"%>
<%@ page import="com.dtv.oss.web.util.WebKeys"%>

<%
  StringBuffer lstBankMop = Postern.getBankMopBuffer();
	pageContext.setAttribute("AllMOPList", Postern.getOpeningMop());
	String open_flag = (request.getParameter("OpenFlag") == null) ? "": request.getParameter("OpenFlag");
	String txtMopID =(request.getParameter("txtMopID")==null) ? "7" :request.getParameter("txtMopID");
	
	if (open_flag.equals("")) {
%>
<Script language=JavaScript>
        alert("û�д���Ĳ�����ϵͳ��֪����ʲô���͵Ŀ�����");
        window.close();
</Script>
<%
	}
	
		String title = "�ͻ�����";

	double txtForceDepostMoney = (double) ((request.getParameter("txtForceDepostMoney") == null) ? 0.0 : Double.parseDouble(request.getParameter("txtForceDepostMoney")));
	String catvIdSelectFlag = (Postern.getCatvIdSelectFlag() == null) ? "": Postern.getCatvIdSelectFlag();
	String openSourceType = (request.getParameter("txtOpenSourceType") == null) ? "": request.getParameter("txtOpenSourceType");
	String openSourceID = (request.getParameter("txtOpenSourceID") == null) ? "": request.getParameter("txtOpenSourceID");
	String openSourceName = Postern.getOrgNameByID(WebUtil.StringToInt(openSourceID));
	if (openSourceName == null)
		openSourceName = "";
	String preferTime = (request.getParameter("txtPreferedTime") == null) ? "": request.getParameter("txtPreferedTime");
	String timeFlag = "SET_C_CSIPREFEREDTIME";
	Map mapPreferedTime = Postern.getAllPreferedTime(timeFlag);
	pageContext.setAttribute("AllPREFEREDTIME", mapPreferedTime);
	
	String catvFieldName =Postern.getFieldNameByFieldInterID("CUSTOMER_CATVID");
	int catvIdLength =Postern.getSystemsettingIntValue("SET_V_CATVIDLENGTH",10);
%>


<Script language=JavaScript>
<!--
var listBankMop=["9#9"<%=lstBankMop%>];
var open_flag =<%=open_flag%>;
var booking =<%=CommonKeys.ACTION_OF_BOOKING%>;
var bookingAccount =<%=CommonKeys.ACTION_OF_BOOKINGACCOUNT%>;
var shopAccount =<%=CommonKeys.ACTION_OF_SHOPACCOUNT%>;
var bookingAgent =<%=CommonKeys.ACTION_OF_BOOKINGAGENT%>;
var selfInstall ="<%=CommonKeys.CUSTSERVICEINTERACTIONIN_STYPE_SELFINSTALL%>" ;
var install ="<%=CommonKeys.CUSTSERVICEINTERACTIONIN_STYPE_INSTALL%>";
var proxy ="<%=CommonKeys.OPENSOURCETYPE_PROXY%>";
var catvIdSelectFlag ="<%=catvIdSelectFlag%>";
var openDirectly="<%=CommonKeys.ACTION_OF_BOOK_DIRECTLY%>";

function IndexOfBankMop(str)
{
    for (var i=0; i<listBankMop.length; i++)
    {
        if (listBankMop[i] == str) return i;
    }

    return -1;
}


function changePrefed(){
   if (document.frmPost.txtIsInstall.value ==selfInstall){
      document.all("prefered").style.display ="none";

   } else{
      document.all("prefered").style.display ="";
   }
 
}


function check_frm()
{
    if (check_Blank(document.frmPost.txtCustType, true, "�û�����"))
			return false;

    if (check_Blank(document.frmPost.txtName, true, "����"))
			return false;
    if (open_flag ==bookingAccount || open_flag ==shopAccount || open_flag == openDirectly) {
        if (document.frmPost.txtSocialSecCardID.value=='' && (document.frmPost.txtCardType.value=='' && document.frmPost.txtCardID.value=='')) {
           alert("������֤����Ϣ�����籣���ţ�");
           document.frmPost.txtCardType.focus();
           return false;
        } else if (document.frmPost.txtSocialSecCardID.value=='') {
            if (document.frmPost.txtCardType.value=='') {
               alert("��ѡ��֤�����ͣ�");
               document.frmPost.txtCardType.focus();
               return false;
            } else if (document.frmPost.txtCardID.value=='') {
               alert("������֤����!");
               document.frmPost.txtCardID.focus();
               return false;
           }
       }
       
       if (!check_Blank(document.frmPost.txtForceDepostMoney, false, "Ѻ��")){     
          if (!check_Float(document.frmPost.txtForceDepostMoney,true,"Ѻ��"))
              return false;
       }
          
       if (catvIdSelectFlag =='Y'){
           if (check_Blank(document.frmPost.txtCatvID, true,"�û��ն˱��"))
               return false;
       }
          
   }	
    if (document.frmPost.txtTelephone.value=='' && document.frmPost.txtMobileNumber.value=='') {
			alert("�̶��绰���ƶ��绰����ͬʱΪ��!");
			document.frmPost.txtTelephone.focus();
			return false;
    }
       
    if (document.frmPost.txtCounty.value==null||document.frmPost.txtCounty.value==''){
    	alert("��ѡ��ͻ�������!");
			return false;		
		}
    if (check_Blank(document.frmPost.txtDetailAddress, true, "��ϸ��ַ"))
			return false;	

    if (open_flag ==shopAccount){
       if (check_Blank(document.frmPost.txtIsInstall,true,"�Ƿ����Ű�װ"))
           return false;
    }

    var groupbrain ="<%=CommonKeys.OPENSOURCETYPE_GROUPBARGAIN%>";
    if (document.frmPost.txtOpenSourceType.value ==groupbrain){
       if (check_Blank(document.frmPost.txtGroupBargainDetailNo,true,"�Ź�ȯ��"))
           return false;
    } else{
       if (document.frmPost.txtGroupBargainDetailNo.value !=""){
          alert("���Ź�������ʽ������д�Ź�ȯ��!");
          return false;
       }
    }
    
    if (!check_Blank(document.frmPost.txtBirthday, false, "��������")){
      if (!check_TenDate(document.frmPost.txtBirthday, true, "��������")) 
	   		return false;
    }
    if (check_Blank(document.frmPost.txtAccountType, true, "�ʻ�����"))
			return false;

  if (check_Blank(document.frmPost.txtAccountName, true,"�ʻ���"))
        return false;
    
    if (check_Blank(document.frmPost.txtMopID, true, "��������"))
				return false;

    if (IndexOfBankMop(document.frmPost.txtMopID.value)>=0){
        if (trim(document.frmPost.txtBankAccount.value)=='' || trim(document.frmPost.txtBankAccountName.value) == ''){
            alert("���ָ������ͱ������������ʻ����ʻ�����");
            if (trim(document.frmPost.txtBankAccount.value)==''){
               document.frmPost.txtBankAccount.focus();
               return false;
            }
            if (trim(document.frmPost.txtBankAccountName.value) ==''){
            	 document.frmPost.txtBankAccountName.focus();
            	 return false;
            }
         }   
    } else  {
         if (trim(document.frmPost.txtBankAccount.value) != '' || trim(document.frmPost.txtBankAccountName.value) != '') {
             alert("���ָ������Ͳ��������������ʻ����ʻ�����!");
             return false;
         }
    }
    
    if (!check_Bidimconfig()) return false;
    return true;

    return true;
}
 
        
function frm_submit()
{
  if (check_frm()) document.frmPost.submit();
}

function check_addressOrcatv(){
   if (check_Blank(document.frmPost.txtCatvID, false, "<%=catvFieldName%>")&& check_Blank(document.frmPost.txtDetailAddress, false, "��ϸ��ַ")){
			alert("����������һ����ѯ����");		
			return false;
   }
   if(!check_Blank(document.frmPost.txtCatvID, false, "<%=catvFieldName%>")){
      if(document.frmPost.txtCatvID.value.length!=<%=catvIdLength%>){
			  alert("<%=catvFieldName%>��������<%=catvIdLength%>λ");
			  return false;	
	}
    }
    if(!check_Blank(document.frmPost.txtDetailAddress, false, "��ϸ��ַ")){
			if(document.frmPost.txtDetailAddress.value.length<=3){
			   alert("��ϸ��ַ��������4λ");
			   return false;		
			}
    }		
    return true;
}

function check_submit()
{
   if (check_addressOrcatv()) {
      var  catvId =document.frmPost.txtCatvID.value;
      var  detailAddress =document.frmPost.txtDetailAddress.value;
      var  strFeatures = "width=600px,height=500px,resizable=no,toolbar=no,scrollbars=yes";
      window.open("catv_terminal_query.do?txtFrom=1&txtTo=10&catvId="+catvId+"&detailAddress="+detailAddress,"<%=catvFieldName%>��ѯ",strFeatures);
   }
}

function ChangeOpenSourceType()
{
   document.FrameUS.submit_update_select('osttoid', document.frmPost.txtOpenSourceType.value, 'txtOpenSourceID', '');
}
function setAcctName(){
	if (document.frmPost.txtAccountName.value =='')
	   document.frmPost.txtAccountName.value =document.frmPost.txtName.value;
}

</Script>

<div id="updselwaitlayer" style="position:absolute; left:650px; top:180px; width:250px; height:24px; display:none">
	<table width="100%" border="0" cellspacing="1" cellpadding="3">
		<tr>
			<td width="100%" align="center">
				<font size="2"> ���ڻ�ȡ���ݡ����� </font>
			</td>
		</tr>
	</table>
</div>

<iframe SRC="update_select.screen" name="FrameUS" width="0" height="0" frameborder="0" scrolling="0">
</iframe>

<form name="frmPost" method="post" action="customer_info_input_directly_submit.do">
	<input type="hidden" name="func_flag" value="105">
	<input type="hidden" name="confirm_post"  value="true" >
	<input type="hidden" name="OpenFlag" value="<%=open_flag%>" />
	<input type="hidden" name="txtActionType" value="<%=CommonKeys.CHECK_CUSTOMERINFO%>" />
	<tbl:hiddenparameters pass="txtReferBookingSheetID" />
	<tbl:hiddenparameters pass="OpenCampaignList/MutiProductList/SingleProductList/GeneralCampaignList" />
	<tbl:hiddenparameters pass="txtAccount" />
	<table border="0" align="center" cellpadding="0" cellspacing="5">
		<tr>
			<td>
				<img src="img/list_tit.gif" width="19" height="19">
			</td>
			<td class="title">
				<%=title%>
			</td>
		</tr>
	</table>
	<br>
	<table width="98%" border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
		<tr>
			<td><img src="img/mao.gif" width="1" height="1"></td>
		</tr>
	</table>
	<br>

	<table width="820" border="0" align="center" cellpadding="4" cellspacing="1" class="list_bg">
		<tr>
			<td colspan="4" class="import_tit" align="center">
				<font size="3">�ͻ���Ϣ</font>
			</td>
		</tr>
		<tr>
			<td valign="middle" class="list_bg2" align="right" width="17%">
				�ͻ�����*
			</td>
			<td width="33%" class="list_bg1">
				<d:selcmn name="txtCustType" mapName="SET_C_CUSTOMERTYPE" match="txtCustType" width="23" defaultFlag ="true" showAllFlag="false" judgeGradeFlag="true" />
			</td>			
			<td valign="middle" class="list_bg2" align="right" width="17%">
				����*
			</td>
			<td width="33%" class="list_bg1">
				<input type="text" name="txtName" size="25" maxlength="20"
						value="<tbl:writeparam name="txtName" />" class="text"  onBlur="javaScript:setAcctName();" >
			</td>
		</tr>
   		
		<tr>
			<td valign="middle" class="list_bg2" align="right">
				֤������*
			</td>
			<td class="list_bg1">
				<d:selcmn name="txtCardType" mapName="SET_C_CUSTOMERCARDTYPE" match="txtCardType" width="23" defaultFlag="true" />
			</td>
			<td valign="middle" class="list_bg2" align="right">
				֤����*
			</td>
			<td class="list_bg1">
				<font size="2"> <input type="text" name="txtCardID" size="25" maxlength="25"
						value="<tbl:writeparam name="txtCardID" />" class="text"> </font>
			</td>
		</tr>
	
		<tr>
			<td valign="middle" class="list_bg2" align="right">
				�̶��绰*
			</td>
			<td class="list_bg1">
				<font size="2"> <input type="text" name="txtTelephone" size="25" maxlength="20"
						value="<tbl:writeparam name="txtTelephone"/>" class="text"> </font>
			</td>
			<td valign="middle" class="list_bg2" align="right">
				�ƶ��绰
			</td>
			<td class="list_bg1">
				<font size="2"> <input type="text" name="txtMobileNumber" size="25" maxlength="20"
						value="<tbl:writeparam name="txtMobileNumber" />" class="text"> </font>
			</td>
		</tr>
		<tr>
			<td valign="middle" class="list_bg2" align="right">
				������*
			</td>
			<td class="list_bg1">
				<input type="hidden" name="txtCounty" value="<tbl:writeparam name="txtCounty" />"> <input
						type="text" name="txtCountyDesc" size="25" maxlength="50" readonly
						value="<tbl:WriteDistrictInfo property="txtCounty" />" class="text"> <input name="selDistButton"
						type="button" class="button" value="ѡ��" onClick="javascript:sel_district('leaf','txtCounty','txtCountyDesc')">
			</td>
			<td valign="middle" class="list_bg2" align="right">
				��ϸ��ַ*
			</td>
			<td class="list_bg1">
				<input type="text" name="txtDetailAddress" size="25" maxlength="100"
						value="<tbl:writeparam name="txtDetailAddress" />" class="text">
			</td>
		</tr>
		<tr>
			<td valign="middle" class="list_bg2">
				<div align="right">
					&nbsp;<%=catvFieldName%>
					<%if (catvIdSelectFlag.equals("Y")){  %>
					*
					<%}%>
				</div>
			</td>
			<td class="list_bg1">
				<font size="2"> <input type="text" name="txtCatvID" size="25" maxlength="50"
						value="<tbl:writeparam name="txtCatvID" />" class="text"> </font>
				<input name="checkbutton" type="button" class="button" value="���" onClick="javascript:check_submit()">
			</td>
		
			<td valign="middle" class="list_bg2" align="right">
			</td>
			<td class="list_bg1">
			</td>

		</tr>		
		<%
			OperatorDTO operatorDto = (OperatorDTO) ((WebCurrentOperatorData) request.getSession().getAttribute(WebKeys.OPERATOR_SESSION_NAME)).getCurrentOperator();
			Organization org = Postern.getOrganizationByID(operatorDto.getOrgID());
			if (org.getOrgType().equals("P") && org.getOrgSubType().equals("S")) {
		%>
		<tr>
			<td valign="middle" class="list_bg2">
				<div align="right">
					��Դ����*
				</div>
			</td>
			<td class="list_bg1">
				<font size="2"> <input type="hidden" name="txtOpenSourceType" value="P"> <input type="text"
						name="source_TypeName" value="�ֹ�˾������" size="25" class="textgray" readonly> </font>
			</td>
			<td valign="middle" class="list_bg2">
				<div align="right">
					��Դ����ID
				</div>
			</td>
			<td class="list_bg1">
				<input type="hidden" name="txtOpenSourceID" value="<%=org.getOrgID()%>"> <input
						type="text" name="source_IDName" value="<%=org.getOrgName()%>" size="25" class="textgray" readonly> 						</td>
		</tr>
		<%
		} else {
		%>
		<tr>
			<td valign="middle" class="list_bg2">
				<div align="right">
					��Դ����*
				</div>
			</td>
			<td class="list_bg1">
	
 				<d:selcmn
						name="txtOpenSourceType" match="txtOpenSourceType" mapName="SET_C_CUSTOPENSOURCETYPE" width="23"
						onchange="ChangeOpenSourceType()" /> 
		 
			</td>
			<td valign="middle" class="list_bg2" align="right">
				��Դ����ID
			</td>
			<td class="list_bg1">
 				<d:selopensourceid
						name="txtOpenSourceID" parentMatch="txtOpenSourceType" match="txtOpenSourceID" width="23" /> 
			</td>
		</tr>
		<%
		}
		%>
		<tr>
			<td valign="middle" class="list_bg2">
				<div align="right">
					�Ź�ȯ��
				</div>
			</td>
			<td class="list_bg1">
				<font size="2"> <input type="text" name="txtGroupBargainDetailNo" size="25"
						value="<tbl:writeparam name="txtGroupBargainDetailNo" />" class="text"> </font>
			</td>
			<td valign="middle" class="list_bg2" align="right">
				Ѻ��*
			</td>
			<td class="list_bg1" colspan="3">
				<font size="2"> <input type="text" name="txtForceDepostMoney" size="25" maxlength="10"
						value="<%=WebUtil.bigDecimalFormat(txtForceDepostMoney)%>" class="text"> </font>
			</td>
		</tr>
				
		<tr>
			<td valign="middle" class="list_bg2" align="right">
				����
			</td>
			<td class="list_bg1">
				<d:selcmn name="txtNationality" mapName="SET_C_NATIONALITY" match="txtNationality" width="23" />
			</td>
			<td valign="middle" class="list_bg2" align="right" width="17%">
				�Ա�
			</td>
			<td width="33%" class="list_bg1">
				<d:selcmn name="txtGender" mapName="SET_C_CUSTOMERGENDER" match="txtGender" width="23" />
			</td>
		</tr>
		<tr>
			<td valign="middle" class="list_bg2" align="right">
				�籣�����
			</td>
			<td class="list_bg1">
				<font size="2"> <input type="text" name="txtSocialSecCardID" size="25" maxlength="25"
						value="<tbl:writeparam name="txtSocialSecCardID" />" class="text"> </font>
			</td>
			<td valign="middle" class="list_bg2" align="right">
				��������
			</td>
			<td class="list_bg1">
				<input onfocus="viewModule(this)" onKeydown="inputDate(document.frmPost.txtBirthday)" onblur="lostFocus(this)" type="text" name="txtBirthday" size="25" maxlength="10"						value="<tbl:writeparam name="txtBirthday" />" class="text" > <IMG						onclick="MyCalendar.SetDate(this,document.frmPost.txtBirthday,'N')" src="img/calendar.gif" style=cursor:hand border="0">
			</td>
		</tr>

		<tr>
			<td valign="middle" class="list_bg2" align="right">
				EMAIL��ַ
			</td>
			<td class="list_bg1">
				<font size="2"> <input type="text" name="txtEmail" size="25" maxlength="100"
						value="<tbl:writeparam name="txtEmail" />" class="text"> </font>
			</td>
			<td valign="middle" class="list_bg2" align="right">
				����
			</td>
			<td class="list_bg1">
				<input type="text" name="txtFaxNumber" size="25" maxlength="100"
						value="<tbl:writeparam name="txtFaxNumber" />" class="text">
			</td>
		</tr>
		<tr>
	    	<td class="list_bg2" align="right">��½ID</td>
	    	<td class="list_bg1"><input type="text" name="txtLoginID" size="25"  value="<tbl:writeparam name="txtLoginID" />" class="text"></td>
	    	<td class="list_bg2" align="right">��½����</td>
	    	<td class="list_bg1"><input type="text" name="txtLoginPwd" size="25"  value="<tbl:writeparam name="txtLoginPwd" />" class="text"></td>
	  </tr>
		<tr>
			<td valign="middle" class="list_bg2" align="right">
				��ע
			</td>
			<td class="list_bg1" colspan="3">
				<input type="text" name="txtComments" size="83" maxlength="100"
						value="<tbl:writeparam name="txtComments" />" class="text">
			</td>
		</tr>

		<tbl:dynamicservey serveyType="M" showType="text" tdWordStyle="list_bg2" tdControlStyle="list_bg1" controlSize="25" checkScricptName ="check_Bidimconfig()" />

		<tr>
			<td colspan="4" class="import_tit" align="center">
				<font size="3">�ʻ�������Ϣ(�ʵ���ַ�粻��д��Ĭ��Ϊ�û���ַ)</font>
			</td>
		</tr>
		<tr>
			<td valign="middle" class="list_bg2" align="right">
				�ʻ�����*
			</td>
			<td class="list_bg1">
				<font size="2"> <d:selcmn name="txtAccountType" mapName="SET_F_ACCOUNTTYPE" match="txtAccountType" width="23" defaultFlag ="true" />
				</font>
			</td>
			<td valign="middle" class="list_bg2" align="right">
				�ʻ���*
			</td>
			<td class="list_bg1">
				<font size="2"> <input type="text" name="txtAccountName" size="25" maxlength="25"
						value="<tbl:writeparam name="txtAccountName" />" class="text"> </font>
			</td>
		</tr>
		<tr>
			<td valign="middle" class="list_bg2" align="right">
				��������*
			</td>
			<td class="list_bg1">
				<tbl:select name="txtMopID" set="AllMOPList" match="<%=txtMopID%>" width="23" />
			</td>
			<td valign="middle" class="list_bg2" align="right">
				�����ʻ�
			</td>
			<td class="list_bg1">
				<input type="text" name="txtBankAccount" size="25" maxlength="25"
						value="<tbl:writeparam name="txtBankAccount" />" class="text">
			</td>
		</tr>
		<tr>
			<td valign="middle" class="list_bg2" align="right">
				�����ʻ���
			</td>
			<td class="list_bg1">
				<input type="text" name="txtBankAccountName" size="25" maxlength="25"
						value="<tbl:writeparam name="txtBankAccountName" />" class="text">
			</td>
			<td valign="middle" class="list_bg2" align="right">
				�ʵ�������
			</td>
			<td class="list_bg1">
				<input type="hidden" name="txtbillCounty" value="<tbl:writeparam name="txtbillCounty" />">
					<input type="text" name="txtbillCountyDesc" size="25" maxlength="50" readonly
						value="<tbl:WriteDistrictInfo property="txtbillCounty" />" class="text"> <input name="selDistButton1"
						type="button" class="button" value="ѡ��"
						onClick="javascript:sel_district('all;leaf','txtbillCounty','txtbillCountyDesc')">
			</td>
		</tr>
		<tr>
			<td class="list_bg2" align="right">
				�ʵ����͵�ַ
			</td>
			<td class="list_bg1">
				<font size="2"> <input type="text" name="txtBillDetailAddress" size="25" maxlength="100"
						value="<tbl:writeparam name="txtBillDetailAddress" />" class="text"> </font>
			</td>
			<td class="list_bg2" align="right">
				�ʵ������ʱ�
			</td>
			<td class="list_bg1">
				<font size="2"> <input type="text" name="txtBillPostcode" size="25" maxlength="6"
						value="<tbl:writeparam name="txtBillPostcode" />" class="text"> </font>
			</td>
		</tr>
	</table>
	<BR>
	<table align="center" border="0" cellspacing="0" cellpadding="0">
		<tr>
			
			<td width="20"></td>
			<td><img src="img/button_l.gif" width="11" height="20"></td>
			<td><input name="next" type="button" class="button" onClick="frm_submit()" value="ȷ��"></td>
			<td><img src="img/button_r.gif" width="22" height="20"></td>
		</tr>
	</table>
	<tbl:generatetoken />  
</form>
<Script language="JavaScript">
window.onload = input_init;
 	//function date_default(){
 	//	if(document.frmPost.txtBirthday.value == "")
 	//		document.frmPost.txtBirthday.value = "YYYY-MM-DD";
 	//}
 	
 	function input_init(){
 	var fm;
 	for(var i=0;i<document.forms.length;i++){
 		fm = document.forms[i];
 		for(var j=0;j<fm.length;j++){
 			if(fm[j].getAttribute("type").toLowerCase()!="button")
 				addKeyDownEvent(fm[j]);
 		}
 	}
 	//date_default();		
 	}
 	function addKeyDownEvent(inp){
 		var oldpress = inp.onkeydown;
 		if(typeof inp.onkeydown != "function"){
 			inp.onkeydown = jumpNext;
 		}else{
 			inp.onkeydown = function(){
 				oldpress();
 				jumpNext();
 			};
 		}
 	}
	function jumpNext(){
            if(event.keyCode ==13){
                event.keyCode = 9;
            }            
        }
</Script>