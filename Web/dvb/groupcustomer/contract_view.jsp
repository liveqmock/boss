<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="bookmark" prefix="bk" %>
<%@ taglib uri="osstags" prefix="d"%>
<%@ taglib uri="privilege" prefix="pri" %>
<%@ page import="com.dtv.oss.util.Postern"%>
<%@ page import="com.dtv.oss.web.util.WebUtil"%>

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
      if (!check_Float(document.frmPost.txtOneOffFeeTotal, true, "һ���Է��ܶ�"))
	    return false;   
	    
    if (!check_Float(document.frmPost.txtRentFeeTotal, true, "������ܶ�"))
	    return false;   
     if (!check_Float(document.frmPost.txtPrepayAmount, true, "Ԥ���ܶ�"))
	    return false; 	    
	      
    if (check_Blank(document.frmPost.txtOrgDesc, true, "������"))
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
   
   if((document.frmPost.txtPrepayAmount.value!='' && document.frmPost.txtPrepayMopID.value!='') ||(document.frmPost.txtPrepayAmount.value=='' && document.frmPost.txtPrepayMopID.value=='') )
       return true;
      
   else if(document.frmPost.txtPrepayMopID.value!=''&&document.frmPost.txtPrepayAmount.value==''){
   	   alert("ѡ����Ԥ����ʽһ��Ҫ����Ԥ���ܶ�");
       return false; 
   }
   else if(document.frmPost.txtPrepayMopID.value==''&&document.frmPost.txtPrepayAmount.value!=''){
   	   alert("û��ѡ��Ԥ����ʽ��Ӧ������Ԥ���ܶ�");
       return false; 
   }
}
  /**
function check_newproductlist() {
     document.frmPost.ProductPackageList.value="";
	 var m=0; 
	if (document.frames.FrameProductPackage.ListID!=null)
	{
        if (document.frames.FrameProductPackage.ListID.length > 1) {
	    for (i=0;i<document.frames.FrameProductPackage.ListID.length;i++)
		if (document.frames.FrameProductPackage.ListID[i].checked)
		{
			m=1;
			if (document.frmPost.ProductPackageList.value!="")
		        document.frmPost.ProductPackageList.value = document.frmPost.ProductPackageList.value + ";";
			document.frmPost.ProductPackageList.value=document.frmPost.ProductPackageList.value + document.frames.FrameProductPackage.ListID[i].value;
		}		
        } else {
            if (document.frames.FrameProduct.ListID.checked) {
            m=1;
                document.frmPost.ProductPackageList.value=document.frames.FrameProductPackage.ListID.value + ";";
            } else {
                document.frmPost.ProductPackageList.value = '';
            }
        }
	}
	if (m==0){
            alert("û��ѡ���Ʒ��,��ѡ��!");
	    return false;
      }
         return true;
}
*/

 
function frm_modify()
{
	
 //alert(document.frmPost.ProductPackageList.value);
           if(check_form() && checkOther()){
					 //webAction��ȡʱ��";"��Ϊ�ָ����
					var tempProductPackageList=document.frmPost.ProductPackageList.value;
					tempProductPackageList=tempProductPackageList.replace(/\,/g,";");
					document.frmPost.ProductPackageList.value=tempProductPackageList;
         
            document.frmPost.action="contract_update.do";
           
            document.frmPost.submit();
            }
       
}
function frm_history()
{
          
          
           
            document.frmPost.action="contract_process_log.do";
           
            document.frmPost.submit();
            
       
}

function view_open_click()
{	
	var contractNo=document.frmPost.txtContractNo.value;
	if(contractNo!=null&&contractNo!=""){
		document.frmPost.action="group_customer_open_info.do?txtContractNo="+contractNo;
		document.frmPost.submit();	
	}
} 

//��Ʒ��ѡ��
//function query_Package_item(){
//var checkedPackage=document.frmPost.ProductPackageList.value;
//	strFeatures = "width=500px,height=380px,resizable=no,toolbar=no,scrollbars=yes";
//	window.open("all_product_package.screen?checkedPackage="+checkedPackage,"��Ʒ��",strFeatures);
//}
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
<%
 String checkedPackage="";
 String checkedPackageStr="";
 String contractNo = request.getParameter("txtContractNo");  
 System.out.println("++contractNo="+contractNo);
 if(contractNo!=null)
   checkedPackage = Postern.getPackageIDByContractNo(contractNo);
 checkedPackage = checkedPackage.replaceAll(";",",");
 String [] tempPackageIDs= checkedPackage.split(",");
 for(int i=0;i<tempPackageIDs.length;i++)
 {
 	 
 	 if(!"".equals(checkedPackageStr))checkedPackageStr += ",";
 	 checkedPackageStr += Postern.getPackagenameByID(WebUtil.StringToInt(tempPackageIDs[i]));
 }
 if(checkedPackage == null)checkedPackage="";
 if(checkedPackageStr == null || checkedPackageStr.equals("null"))
 {
   checkedPackageStr="";
 }
 
%>
 

<form name="frmPost" method="post">
 
 <%
pageContext.setAttribute("AllMOPList" ,Postern.getContractPaymentMop());
%>

 <input type="hidden" name="Action" value="update">
  
 <table  border="0" align="center" cellpadding="0" cellspacing="10" >
  <tr>
    <td><img src="img/list_tit.gif" width="19" height="19"></td>
    <td class="title">��ͬ��ϸ</td>
  </tr>
</table>
<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>
       
 
<lgc:bloop requestObjectName="ResponseQueryResult" item="oneline" >
<%
 com.dtv.oss.dto.ContractDTO dto = (com.dtv.oss.dto.ContractDTO )pageContext.getAttribute("oneline");
 
 String prepayAmount =String.valueOf(dto.getPrepayAmount());
 String status = dto.getStatus();
 
 if(prepayAmount.equals("0.0"))
   prepayAmount="";

%>

 <table width="100%"  border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg">
       
        <tr>
         <td  class="list_bg2"><div align="right">��ͬ���</div></td>
          <td class="list_bg1"><font size="2">	
          <input type="text" name="txtContractNo" size="25"  class="textgray" readonly  value="<tbl:write name="oneline" property="contractNo"/>">
          </font></td>
         
          <td class="list_bg2"><div align="right">������ֹ����*</div></td>
          <td class="list_bg1">
           <input maxlength="10" onfocus="viewModule(this)" onKeydown="inputDate(document.frmPost.txtNormalDate)" onblur="lostFocus(this)" type="text" name="txtNormalDate" size="25"  value="<tbl:writedate name="oneline" property="normaldate"/>">                     <IMG onclick="MyCalendar.SetDate(this,document.frmPost.txtNormalDate,'Y')" src="img/calendar.gif" style=cursor:hand border="0"/>
          </font></td>
          
        </tr>
        <tr>
         <td  class="list_bg2" ><div align="right">��ͬ����*</div></td>
          <td class="list_bg1" colspan="3"><font size="2">
              <input type="text" name="txtName" size="83" class="textgray" readonly maxlength="50" value="<tbl:write name="oneline" property="name"/>">
              
              </font></td>
              </tr>
          <tr>
         <td class="list_bg2"><div align="right">��Ч����ʼʱ��*</div></td>
          <td class="list_bg1">
           <input maxlength="10" onfocus="viewModule(this)" onKeydown="inputDate(document.frmPost.txtDateFrom)" onblur="lostFocus(this)" type="text" name="txtDateFrom" size="25"  value="<tbl:writedate name="oneline" property="datefrom"/>">                    <IMG onclick="MyCalendar.SetDate(this,document.frmPost.txtDateFrom,'Y')" src="img/calendar.gif" style=cursor:hand border="0"/>
          </font></td>
          <td class="list_bg2"><div align="right">��Ч�ڽ�ֹʱ��*</div></td>
          <td class="list_bg1">
          <input maxlength="10" onfocus="viewModule(this)" onKeydown="inputDate(document.frmPost.txtDateTo)" onblur="lostFocus(this)" type="text" name="txtDateTo" size="25" value="<tbl:writedate name="oneline" property="dateto"/>">          <IMG onclick="MyCalendar.SetDate(this,document.frmPost.txtDateTo,'Y')" src="img/calendar.gif" style=cursor:hand border="0"/>
          </font></td>
        </tr>
        <tr>
	 <td  class="list_bg2"><div align="right">һ���Է��ܶ�</div></td>
           <td class="list_bg1"><font size="2">
               <input type="text" name="txtOneOffFeeTotal" size="25"   value="<tbl:write name="oneline" property="oneOffFeeTotal"/>">
          </font></td>
          <td  class="list_bg2"><div align="right">������ܶ�</div></td>
          <td  class="list_bg1"><font size="2">
               <input type="text" name="txtRentFeeTotal" size="25"    value="<tbl:write name="oneline" property="rentFeeTotal"/>">
              </font></td>
          
        </tr>
        <tr>
        	
     <td class="list_bg2" align="right">Ԥ����ʽ</td>
     <td class="list_bg1">
           <d:selcmn name="txtPrepayMopID" set="AllMOPList" match="oneline:prepayMopID" width="25"/>
	   </td>
                  	
	   <td  class="list_bg2"><div align="right">Ԥ���ܶ�</div></td>
	   <td  class="list_bg1"><font size="2">
	         <input type="text" name="txtPrepayAmount" size="25"  value="<%=prepayAmount%>">
     </font></td>
            

        </tr>
        <tr>
          <td  class="list_bg2"><div align="right">ֽ�ƺ�ͬ���*</div></td>
           <td class="list_bg1"><font size="2">
           <input type="text" name="txtSheetNo" size="25"  value="<tbl:write name="oneline" property="sheetNo"/>">
          </font></td>
           <td  class="list_bg2"><div align="right">�����û���*</div></td>
           <td class="list_bg1"><font size="2">
           <input type="text" name="txtUserCount" size="25"  value="<tbl:write name="oneline" property="userCount"/>">
          </font></td>
        </tr>
     
         <tr>
          <td  class="list_bg2"><div align="right">ʵ���û���</div></td>
           <td class="list_bg1"><font size="2">
           <input type="text" name="txtUseredCount" size="25"  value="<tbl:write name="oneline" property="usedCount"/>" class="textgray" readonly >
          </font></td>
           <td class="list_bg2"><div align="right">������*</div></td>
          <td class="list_bg1">
          <input type="hidden" name="txtOrgID" value="<tbl:write name="oneline" property="sourceOrg" />">
	    <input type="text" name="txtOrgDesc" size="25" maxlength="50" readonly value="<tbl:WriteOrganizationInfo name="oneline" property="sourceOrg" />" class="text">
    	 
	 <input name="selOrgButton" type="button" class="button" value="ѡ��" onClick="javascript:sel_organization('C,B,O,S','txtOrgID','txtOrgDesc')">
        </td>		
         </tr>
          <%
                 if("U".equals(status)){
          %>
         <tr>  
           <td class="list_bg2"><div align="right">״̬*</div></td>
           <td class="list_bg1" colspan="3"><font size="2">
            <input type="text" name="txtStatus1" size="25"  value="<d:getcmnname typeName="SET_C_CONTRACTSTATUS" match="oneline:status"/>" readonly class="textgray"></font></td>
             <input type="hidden" name="txtStatus" value="<tbl:write name="oneline" property="status"/>"> 
               
               
        </tr>
          <%} else {%>
          
          <tr>  
           <td class="list_bg2"><div align="right">״̬*</div></td>
           <td class="list_bg1" colspan="3"><font size="2">
              <d:selcmn name="txtStatus" mapName="SET_C_CONTRACTSTATUS" match="oneline:status" width="25"/>
               
              </font></td>
        </tr> 
	 <%}%>
	 <tr>
	 	<td class="list_bg2"><div align="right">��Ʒ��*</div></td>
          <td class="list_bg1" colspan="3">
          	<!--
          <input type="hidden" name="ProductPackageList" value="<%=checkedPackage%>">
          -->
	    		<input type="text" name="ProductPackageListValue" size="83" maxlength="500" readonly value="<%=checkedPackageStr%>" class="text">
	 				<input type=button value="ѡ��" class="button" onclick="javascript:open_select('PRODUCTPACKAGE','ProductPackageList',document.frmPost.ProductPackageList.value,'','','')">
	 				</td>	
	</tr>
        <tr> 
          <td  class="list_bg2"><div align="right">��ͬ����</div></td>
          <td class="list_bg1" colspan="3"><font size="2">
              <input type="text" name="txtDescription" size="83"  value="<tbl:write name="oneline" property="Description"/>">
              </font></td>
        </tr>
    </table>
    <!--
    <table align="center" width="100%" border="0" align="center" cellpadding="5" cellspacing="1" class="list_bg" >
      
   
      <tr>
	  <td class="import_tit" align="center"><font size="2">��Ʒ��ѡ��</font></td>
     </tr>
     <tr>
      <td  align="center" colspan="4"><font size="2">
        <iframe SRC="add_product_package.screen?contractNo=<tbl:write name="oneline" property="contractNo"/>" name="FrameProductPackage" width="500" height="200"></iframe>
      </font></td>  
     </tr>
       
     
    </table>
    -->
    
        
        
    
     
       
      <input type="hidden" name="ProductPackageList" value="<%=checkedPackage%>">
        
       <input type="hidden" name="dtLastmod" value="<tbl:write name="oneline" property="DtLastmod" />">
      <input type="hidden" name="func_flag" value="256">
 
 </lgc:bloop>  

<%
String actionType=request.getParameter("ActionType");
%>
<br>   
<%if(actionType!=null&&actionType.equals("GCOpen")){%>
    <table border="0" cellspacing="0" cellpadding="0" align=center>
        <tr>
          <td><img src="img/button2_r.gif" width="22" height="20"></td>
            <td background="img/button_bg.gif"><a href="<bk:backurl property="group_customer_open_contract_query_result.do" />" class="btn12">��&nbsp;��</a></td>
          <td><img src="img/button2_l.gif" width="11" height="20"></td>
          <td width="20" ></td>  
           <td><img src="img/button_l.gif" border="0" ></td>
            <td background="img/button_bg.gif"><a href="javascript:view_open_click()" class="btn12">��&nbsp;��</a></td>
          <td><img src="img/button_r.gif" border="0" ></td>
          </tr>
    </table>        
<%}else{%>
    <table border="0" cellspacing="0" cellpadding="0" align=center>
        <tr>
          <td><img src="img/button2_r.gif" width="22" height="20"></td>
            <td background="img/button_bg.gif"><a href="<bk:backurl property="contract_query_result.do" />" class="btn12">��&nbsp;��</a></td>
          <td><img src="img/button2_l.gif" width="11" height="20"></td>
            <td width="20" ></td>  
            <td><img src="img/button_l.gif" border="0" ></td>
            <td background="img/button_bg.gif"><a href="javascript:frm_history()" class="btn12">��ʷ��¼</a></td>
          <td><img src="img/button_r.gif" border="0" ></td>
           <td width="20" ></td>  
           <td><img src="img/button_l.gif" border="0" ></td>
            <td background="img/button_bg.gif"><a href="javascript:frm_modify()" class="btn12">��&nbsp;��</a></td>
          <td><img src="img/button_r.gif" border="0" ></td>
          </tr>
    </table>        
 <%}%>
</form>