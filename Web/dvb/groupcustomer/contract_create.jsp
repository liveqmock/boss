<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="bookmark" prefix="bk" %>
<%@ taglib uri="osstags" prefix="d"%>
<%@ taglib uri="privilege" prefix="pri" %>
<%@ page import="com.dtv.oss.util.Postern"%>

<SCRIPT LANGUAGE="JavaScript">
<!--
 
 
function check_form(){
    if (check_Blank(document.frmPost.txtName, true, "��ͬ����"))
        return false;
    if (check_Blank(document.frmPost.txtStatus, true, "״̬"))
        return false;
     if (check_Blank(document.frmPost.txtUserCount, true, "�����û���")) 
          
          return false;
          
         
    if (!check_Num(document.frmPost.txtUserCount, true, "�����û���"))
       return false;
    
    
    if (check_Blank(document.frmPost.txtOrgDesc, true, "������"))
        return false;
    if (!check_Float(document.frmPost.txtOneOffFeeTotal, true, "һ���Է��ܶ�"))
	    return false;   
	    
    if (!check_Float(document.frmPost.txtRentFeeTotal, true, "������ܶ�"))
	    return false;   
     if (!check_Float(document.frmPost.txtPrepayAmount, true, "Ԥ���ܶ�"))
	    return false; 	    
	      
        
    if (check_Blank(document.frmPost.txtContractNo, true, "��ͬ���"))
        return false;
  if (check_Blank(document.frmPost.txtSheetNo, true, "ֽ�ƺ�ͬ���"))
        return false;
     
     if (check_Blank(document.frmPost.txtNormalDate, true, "������ֹ����")) {
        return false;
      } else {
        if (!check_TenDate(document.frmPost.txtNormalDate, true, "������ֹ����"))
            return false;
    }
    if (check_Blank(document.frmPost.txtDateFrom, true, "��Ч����ʼʱ��")) {
        return false;
    } else {
        if (!check_TenDate(document.frmPost.txtDateFrom, true, "��Ч����ʼʱ��"))
            return false;
    }
    if (check_Blank(document.frmPost.txtDateTo, true, "��Ч�ڽ�ֹʱ��")) {
        return false;
    } else {
        if (!check_TenDate(document.frmPost.txtDateTo, true, "��Ч�ڽ�ֹʱ��"))
            return false;
    }
    if (!compareDate(document.frmPost.txtDateFrom,document.frmPost.txtDateTo,"��Ч����ʼʱ��Ӧ����Ч�ڽ�ֹʱ��֮ǰ��")) {
        return false;
    }
    
    if (check_Blank(document.frmPost.ProductPackageList, true, "��Ʒ��"))
        return false;
     
    return true;

}
 
 
function checkOther(){
   if((document.frmPost.txtPrepayAmount.value!='' && document.frmPost.txtPrepayMopID.value!='') ||
    (document.frmPost.txtPrepayAmount.value=='' && document.frmPost.txtPrepayMopID.value=='') )
      return true;
    
      alert("Ԥ���ܶ��֧����ʽ����ͬʱѡ��");
      return false; 
   
}
 

 
function frm_modify()
{  					
					
	
           if( check_form()&& checkOther()){
           //webAction��ȡʱ��";"��Ϊ�ָ����
					var tempProductPackageList=document.frmPost.ProductPackageList.value;
					tempProductPackageList=tempProductPackageList.replace(/\,/g,";");
					document.frmPost.ProductPackageList.value=tempProductPackageList;
            document.frmPost.action="contract_create.do";
           
            document.frmPost.submit();
            }
       
}

//��Ʒ��ѡ��
 function open_select(type,typeName,typeValue,parentType,parentTypeName,parentTypeValue){
	var param="batch_query_select.screen?";
	param=param + "type="+type;
	param=param + "&typeName="+typeName;
	param=param + "&typeValue=" + document.frmPost.elements(typeName).value;
	param=param + "&parentType="+parentType;
	param=param + "&parentTypeName="+parentTypeName;
	
	if(parentTypeName!=null && parentTypeName!="")
		param=param + "&parentTypeValue="+document.frmPost.elements(parentTypeName).value;
		
	var windowFeatures="toolbar=no,location=no,status=no,menubar=no,scrollbars=yes,width=330,height=250,screenX=340,screenY=270";

	window.open(param,"",windowFeatures);
}
 

//-->
</SCRIPT>

 
 

<form name="frmPost" method="post">
 
<%
pageContext.setAttribute("AllMOPList" ,Postern.getOpeningPaymentNoWrapMop());
%>

 <input type="hidden" name="Action" value="create">
  
 <table  border="0" align="center" cellpadding="0" cellspacing="10" >
  <tr>
    <td><img src="img/list_tit.gif" width="19" height="19"></td>
    <td class="title">��ͬ����</td>
  </tr>
</table>
<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>
       
 

 <table width="100%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
       
        <tr>
         <td  class="list_bg2"><div align="right">��ͬ���*</div></td>
          <td class="list_bg1"><font size="2">	
          <input type="text" name="txtContractNo" size="25"  maxlength="50"  value="<tbl:writeparam name="txtContractNo" />" >
          </font></td>
           <td class="list_bg2"><div align="right">������ֹ����*</div></td>
          <td class="list_bg1">
          <input maxlength="10" onfocus="viewModule(this)" onKeydown="inputDate(document.frmPost.txtNormalDate)" onblur="lostFocus(this)" type="text" name="txtNormalDate" size="25"  value="<tbl:writeparam name="txtNormalDate"/>">          <IMG onclick="MyCalendar.SetDate(this,document.frmPost.txtNormalDate,'Y')" src="img/calendar.gif" style=cursor:hand border="0"/>
          </font></td>
         
          
        </tr>
        <tr>
         <td  class="list_bg2" ><div align="right">��ͬ����*</div></td>
          <td class="list_bg1" colspan="3"><font size="2">
              <input type="text" name="txtName" size="83"  maxlength="200" value="<tbl:writeparam name="txtName"/>">
              </font></td>
            </tr> 
          <tr>
         <td class="list_bg2"><div align="right">��Ч����ʼʱ��*</div></td>
          <td class="list_bg1">
          <input maxlength="10" onfocus="viewModule(this)" onKeydown="inputDate(document.frmPost.txtDateFrom)" onblur="lostFocus(this)" type="text" name="txtDateFrom" size="25"  value="<tbl:writeparam name="txtDateFrom"/>">          <IMG onclick="MyCalendar.SetDate(this,document.frmPost.txtDateFrom,'Y')" src="img/calendar.gif" style=cursor:hand border="0"/>
          </font></td>
          <td class="list_bg2"><div align="right">��Ч�ڽ�ֹʱ��*</div></td>
          <td class="list_bg1">
          <input maxlength="10" onfocus="viewModule(this)" onKeydown="inputDate(document.frmPost.txtDateTo)" onblur="lostFocus(this)" type="text" name="txtDateTo" size="25" value="<tbl:writeparam name="txtDateTo"/>" >          <IMG onclick="MyCalendar.SetDate(this,document.frmPost.txtDateTo,'Y')" src="img/calendar.gif" style=cursor:hand border="0"/>
          </font></td>
        </tr>
        <tr>
	 <td  class="list_bg2"><div align="right">һ���Է��ܶ�</div></td>
           <td class="list_bg1"><font size="2">
               <input type="text" name="txtOneOffFeeTotal" size="25"   value="<tbl:writeparam name="txtOneOffFeeTotal" />" >
          </font></td>
          <td  class="list_bg2"><div align="right">������ܶ�</div></td>
          <td  class="list_bg1"><font size="2">
               <input type="text" name="txtRentFeeTotal" size="25"   value="<tbl:writeparam name="txtRentFeeTotal" />" >
              </font></td>
          
        </tr>
        <tr>
	   <td  class="list_bg2"><div align="right">Ԥ���ܶ�</div></td>
	   <td  class="list_bg1"><font size="2">
               <input type="text" name="txtPrepayAmount" size="25"   value="<tbl:writeparam name="txtPrepayAmount" />" >
              </font></td>
            
           <td class="list_bg2" align="right">֧����ʽ</td>
		<td class="list_bg1"><tbl:select name="txtPrepayMopID" set="AllMOPList" match="txtPrepayMopID" width="25" />
		 </td>
          
        </tr>
        <tr>
          <td  class="list_bg2"><div align="right">ֽ�ƺ�ͬ���*</div></td>
           <td class="list_bg1"><font size="2">
           <input type="text" name="txtSheetNo" size="25"   value="<tbl:writeparam name="txtSheetNo" />" >
          </font></td>
           <td  class="list_bg2"><div align="right">�����û���*</div></td>
           <td class="list_bg1"><font size="2">
           <input type="text" name="txtUserCount" size="25"  value="<tbl:writeparam name="txtUserCount" />" >
          </font></td>
        </tr>
     
        <tr>
           <td class="list_bg2"><div align="right">������*</div></td>
          <td class="list_bg1">
    	<input type="hidden" name="txtOrgID" value="<tbl:writeparam name="txtOrgID" />">
	  <input type="text" name="txtOrgDesc" size="25" maxlength="50" readonly value="<tbl:WriteOrganizationInfo property="txtOrgID" />" class="text">
	 <input name="selOrgButton" type="button" class="button" value="ѡ��" onClick="javascript:sel_organization('C,B,O,S','txtOrgID','txtOrgDesc')">
        </td>		
           
           <td class="list_bg2"><div align="right">״̬*</div></td>
           <td class="list_bg1"><font size="2">
              <d:selcmn name="txtStatus" mapName="SET_C_CONTRACTSTATUS" match="txtStatus" width="25"/>
              </font></td>
        </tr>
        
        <tr>
	 	<td class="list_bg2"><div align="right">��Ʒ��*</div></td>
          <td class="list_bg1" colspan="3">
	    		<input type="text" name="ProductPackageListValue" size="83" maxlength="500" readonly value="<tbl:writeparam name="ProductPackageListValue" />" class="text">
	 				<input type=button value="ѡ��" class="button" onclick="javascript:open_select('PRODUCTPACKAGE','ProductPackageList',document.frmPost.ProductPackageList.value,'','','')">
	 				</td>	
	</tr>
	 
        <tr> 
          <td  class="list_bg2"><div align="right">��ͬ����</div></td>
          <td class="list_bg1" colspan="3"><font size="2">
              <input type="text" name="txtDescription" size="83"  value="<tbl:writeparam name="txtDescription" />" >
              </font></td>
        </tr>
    </table>
    
    <!--
    
    <table align="center" width="100%" border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg" >
      
   
      <tr>
	  <td class="import_tit" align="center"><font size="2">��Ʒ��ѡ��</font></td>
     </tr>
     <tr>
      <td align="center" colspan="4"><font size="2">
        <iframe SRC="add_product_package.screen" name="FrameProductPackage" width="500" height="200"></iframe>
      </font></td> 
     </tr>
       
     
    </table>
    -->
    
        
        
      
     
       
      <input type="hidden" name="ProductPackageList" value="">
      
      <input type="hidden" name="func_flag" value="256">
 



<br>   
 
    <table border="0" cellspacing="0" cellpadding="0" align=center>
     <tr>
     			<bk:canback url="menu_contract_query.do/contract_query_result.do">
          <td><img src="img/button2_r.gif" width="22" height="20"></td>
            <td background="img/button_bg.gif"><a href="<bk:backurl property="menu_contract_query.do/contract_query_result.do" />" class="btn12">��&nbsp;��</a></td>
          <td><img src="img/button2_l.gif" width="11" height="20"></td>
            <td width="20" ></td>
          </bk:canback>  
            
           <td><img src="img/button_l.gif" border="0" ></td>
            <td background="img/button_bg.gif"><a href="javascript:frm_modify()" class="btn12">��&nbsp;��</a></td>
          <td><img src="img/button_r.gif" border="0" ></td>
          </tr>
        
    </table>        
 
</form>