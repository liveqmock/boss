<!--˵������ҳ��Ҫ������ʾЭ����û�¼��Э��۸����ʾ¼���Э��� -->
<!--����˵����showAgreementType=label:ֻ��������ʾ�Ѿ�¼���Э��ۣ�����ʱ��ʾ��������Э���-->
<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl"%>
<%@ taglib uri="osstags" prefix="d"%>
<%@ taglib uri="bookmark" prefix="bk"%>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ page import="com.dtv.oss.util.Postern,java.util.*"%>
<%@ page import="com.dtv.oss.web.util.WebUtil"%>
<%@ page import="com.dtv.oss.web.util.CommonKeys"%>
<%@ page import="com.dtv.oss.web.util.WebOperationUtil"%>
<%@ page import="com.dtv.oss.dto.MethodOfPaymentDTO"%>
<%@ page import="com.dtv.oss.util.Organization"%>
<%@ page import="com.dtv.oss.dto.OperatorDTO"%>
<%@ page import="com.dtv.oss.web.util.WebCurrentOperatorData"%>
<%@ page import="com.dtv.oss.web.util.WebKeys"%>
<%@ page import="com.dtv.oss.service.util.CsrBusinessUtility" %>

<Script language=JavaScript>
<!--
function fill_The_Data(){
	if (document.frmPost.txtAgreementHeadStartDate.value != "" && document.frmPost.txtAgreementHeadEndDate.value != ""){
		if(!compareDate(document.frmPost.txtAgreementHeadStartDate,document.frmPost.txtAgreementHeadEndDate,"�������ڱ�����ڵ��ڿ�ʼ����"))
			return;
	}
    if (document.frmPost.txtAgreementHeadStartDate.value != ""){
		if (!check_TenDate(document.frmPost.txtAgreementHeadStartDate, true, "��ʼ����")){
			return;
		}
		//��俪ʼ����
		if (document.frmPost.txtAgreementStartDate.length > 0) {
			 for (i=0;i<document.frmPost.txtAgreementStartDate.length;i++)
			 	document.frmPost.txtAgreementStartDate[i].value=document.frmPost.txtAgreementHeadStartDate.value;
		}else{
		    document.frmPost.txtAgreementStartDate.value=document.frmPost.txtAgreementHeadStartDate.value;
		}
	}
	if (document.frmPost.txtAgreementHeadEndDate.value != ""){
		if (!check_TenDate(document.frmPost.txtAgreementHeadEndDate, true, "��������")){
			return;
		}
		//����������
		if (document.frmPost.txtAgreementEndDate.length > 0) {
			 for (i=0;i<document.frmPost.txtAgreementEndDate.length;i++)
			 	document.frmPost.txtAgreementEndDate[i].value=document.frmPost.txtAgreementHeadEndDate.value;
		}else{
		    document.frmPost.txtAgreementEndDate.value=document.frmPost.txtAgreementHeadEndDate.value;
		}
	}
}

//��Э��۵Ĳ����á���������
function oragnization_Param_agreement(){
    document.frmPost.txtAgreementProductList.value="";
    document.frmPost.txtAgreementPackageList.value="";
    document.frmPost.txtAgreementPriceList.value="";
	document.frmPost.txtAgreementStartDateList.value="";
	document.frmPost.txtAgreementEndDateList.value="";
	document.frmPost.txtAgreementAcctTypeList.value="";
	if(document.frmPost.txtAgreementProdID!=null){
	if (document.frmPost.txtAgreementProdID.length > 0) {
		 for (i=0;i<document.frmPost.txtAgreementProdID.length;i++){
			if(document.frmPost.txtStartAgreement.checked){
    			if(check_Blank(document.frmPost.txtAgreementPrice[i],true,"Э��۸�"))
    		 		return false;
    		 	if(!check_Money(document.frmPost.txtAgreementPrice[i],true,"Э��۸�"))
    		 		return false;
    		 	if(check_Blank(document.frmPost.txtAgreementStartDate[i],true,"Э��ۿ�ʼ����"))
    		 		return false;
    		 	if(!check_TenDate(document.frmPost.txtAgreementStartDate[i], true, "Э��ۿ�ʼ����"))
    				return false;
    		 	if(check_Blank(document.frmPost.txtAgreementEndDate[i],true,"Э��۽�������"))
    		 		return false;
    		 	if(!check_TenDate(document.frmPost.txtAgreementEndDate[i], true, "Э��۽�������"))
    				return false;
    		 	if(!compareDate(document.frmPost.txtAgreementStartDate[i],document.frmPost.txtAgreementEndDate[i],"�������ڱ�����ڵ��ڿ�ʼ����"))
    				return false;
    			if(check_Blank(document.frmPost.txtAgreementAcctType[i],true,"��Ŀ����"))
    		 		return false;	
    				
			}	
		 	if(document.frmPost.txtAgreementPriceList.value!="")
		 		document.frmPost.txtAgreementPriceList.value=document.frmPost.txtAgreementPriceList.value +",";
		 	document.frmPost.txtAgreementPriceList.value=document.frmPost.txtAgreementPriceList.value+document.frmPost.txtAgreementPrice[i].value;

		 	if(document.frmPost.txtAgreementStartDateList.value!="")
		 		document.frmPost.txtAgreementStartDateList.value=document.frmPost.txtAgreementStartDateList.value +",";
		 	document.frmPost.txtAgreementStartDateList.value=document.frmPost.txtAgreementStartDateList.value+document.frmPost.txtAgreementStartDate[i].value;
		 	
		 	if(document.frmPost.txtAgreementEndDateList.value!="")
		 		document.frmPost.txtAgreementEndDateList.value=document.frmPost.txtAgreementEndDateList.value +",";
		 	document.frmPost.txtAgreementEndDateList.value=document.frmPost.txtAgreementEndDateList.value+document.frmPost.txtAgreementEndDate[i].value;
		 	
		 	if(document.frmPost.txtAgreementProductList.value!="")
		 		document.frmPost.txtAgreementProductList.value=document.frmPost.txtAgreementProductList.value +",";
		 	document.frmPost.txtAgreementProductList.value=document.frmPost.txtAgreementProductList.value+document.frmPost.txtAgreementProdID[i].value;
		 	
		 	if(document.frmPost.txtAgreementPackageList.value!="")
		 		document.frmPost.txtAgreementPackageList.value=document.frmPost.txtAgreementPackageList.value +",";
		 	document.frmPost.txtAgreementPackageList.value=document.frmPost.txtAgreementPackageList.value+document.frmPost.txtAgreementpackID[i].value;
		 	
		 	if(document.frmPost.txtAgreementAcctTypeList.value!="")
		 		document.frmPost.txtAgreementAcctTypeList.value=document.frmPost.txtAgreementAcctTypeList.value +",";
		 	document.frmPost.txtAgreementAcctTypeList.value=document.frmPost.txtAgreementAcctTypeList.value+document.frmPost.txtAgreementAcctType[i].value;
		 }
	}else{
	    if(document.frmPost.txtStartAgreement.checked){
    			if(check_Blank(document.frmPost.txtAgreementPrice,true,"Э��۸�"))
    		 		return false;
    		 	if(!check_Money(document.frmPost.txtAgreementPrice,true,"Э��۸�"))
    		 		return false;
    		 	if(check_Blank(document.frmPost.txtAgreementStartDate,true,"Э��ۿ�ʼ����"))
    		 		return false;
    		 	if(!check_TenDate(document.frmPost.txtAgreementStartDate, true, "Э��ۿ�ʼ����"))
    				return false;
    		 	if(check_Blank(document.frmPost.txtAgreementEndDate,true,"Э��۽�������"))
    		 		return false;
    		 	if(!check_TenDate(document.frmPost.txtAgreementEndDate, true, "Э��۽�������"))
    				return false;
    		 	if(!compareDate(document.frmPost.txtAgreementStartDate,document.frmPost.txtAgreementEndDate,"�������ڱ�����ڵ��ڿ�ʼ����"))
    				return false;
    			if(check_Blank(document.frmPost.txtAgreementAcctType,true,"��Ŀ����"))
    		 		return false;
		}
	 	document.frmPost.txtAgreementPriceList.value=document.frmPost.txtAgreementPrice.value;
	 	document.frmPost.txtAgreementStartDateList.value=document.frmPost.txtAgreementStartDate.value;
	 	document.frmPost.txtAgreementEndDateList.value=document.frmPost.txtAgreementEndDate.value;
	 	document.frmPost.txtAgreementProductList.value=document.frmPost.txtAgreementProdID.value;
	 	document.frmPost.txtAgreementPackageList.value=document.frmPost.txtAgreementpackID.value;
	 	document.frmPost.txtAgreementAcctTypeList.value=document.frmPost.txtAgreementAcctType.value;
	}
	}
	return true;
}
function handleInput(){
    if(document.frmPost.txtStartAgreement.checked){
        document.frmPost.txtAgreementHeadStartDate.disabled="";
        document.frmPost.txtAgreementHeadEndDate.disabled="";
        document.frmPost.fillbutton.disabled="";
        if(document.frmPost.txtAgreementProdID!=null){
        if (document.frmPost.txtAgreementProdID.length > 0) {
            for (i=0;i<document.frmPost.txtAgreementProdID.length;i++){
                document.frmPost.txtAgreementPrice[i].disabled="";
                document.frmPost.txtAgreementStartDate[i].disabled="";
                document.frmPost.txtAgreementEndDate[i].disabled="";
            }
        }else{
            document.frmPost.txtAgreementPrice.disabled="";
            document.frmPost.txtAgreementStartDate.disabled="";
            document.frmPost.txtAgreementEndDate.disabled="";
        }
        }
    }else{
        document.frmPost.txtAgreementHeadStartDate.value="";
        document.frmPost.txtAgreementHeadStartDate.disabled=true;
        document.frmPost.txtAgreementHeadEndDate.value="";
        document.frmPost.txtAgreementHeadEndDate.disabled=true;
        document.frmPost.fillbutton.disabled=true;
        if(document.frmPost.txtAgreementProdID!=null){
        if (document.frmPost.txtAgreementProdID.length > 0) {
            for (i=0;i<document.frmPost.txtAgreementProdID.length;i++){
                document.frmPost.txtAgreementPrice[i].value="";
                document.frmPost.txtAgreementPrice[i].disabled=true;
                document.frmPost.txtAgreementStartDate[i].value="";
                document.frmPost.txtAgreementStartDate[i].disabled=true;
                document.frmPost.txtAgreementEndDate[i].value="";
                document.frmPost.txtAgreementEndDate[i].disabled=true;
            }
        }else{
            document.frmPost.txtAgreementPrice.value="";
            document.frmPost.txtAgreementPrice.disabled=true;
            document.frmPost.txtAgreementStartDate.value="";
            document.frmPost.txtAgreementStartDate.disabled=true;
            document.frmPost.txtAgreementEndDate.value="";
            document.frmPost.txtAgreementEndDate.disabled=true;
        }
        }
    }
}
//-->
</Script>
<%
    String agreementProductList=request.getParameter("txtAgreementProductList");
    String agreementChecked=request.getParameter("txtStartAgreement");
    Map acctItemTypeMap =Postern.getAcctItemMapWithFeeType(CommonKeys.BRFEETYPE_INFORMATION);
    pageContext.setAttribute("agreement_acctitemtypemap", acctItemTypeMap);
    boolean isExistProduct=false;
%>
<table width="820" border="0" align="center" cellpadding="4" cellspacing="1" class="list_bg">
	<input type="hidden" name="txtAgreementProductList" value="<tbl:writeparam name="txtAgreementProductList" />">   
    <input type="hidden" name="txtAgreementPackageList" value="<tbl:writeparam name="txtAgreementPackageList" />">   
    <input type="hidden" name="txtAgreementPriceList" value="<tbl:writeparam name="txtAgreementPriceList" />">   
    <input type="hidden" name="txtAgreementStartDateList" value="<tbl:writeparam name="txtAgreementStartDateList" />">
    <input type="hidden" name="txtAgreementEndDateList" value="<tbl:writeparam name="txtAgreementEndDateList" />"> 
    <input type="hidden" name="txtAgreementAcctTypeList" value="<tbl:writeparam name="txtAgreementAcctTypeList" />"> 
	<% 
	if(!"label".equals(request.getParameter("showAgreementType"))){
	%>
	<tr>
    	<td colspan="7" class="import_tit" align="center">
    		<font size="3">Э��۸�¼��</font>
    	</td>
	</tr>
	
	<tr>
	    <td class="list_bg2"  align="right"><div align="center">����Э���</div></td>
  		<td class="list_bg1" align="left" ><div align="center"><input type="checkbox" name="txtStartAgreement" value ="Y"  onclick="javascript:handleInput()" <%if("Y".equals(agreementChecked)) out.print("checked");%> >��<br></div></td>
        <td class="list_bg2" align="right">��ֹ����</td>
        <td class="list_bg1" align="left" colspan="4">
            <d:datetime name="txtAgreementHeadStartDate" size="10" myClass="text" match="txtAgreementHeadStartDate"  onclick="MyCalendar.SetDate(this,document.frmPost.txtAgreementHeadStartDate,'Y')" picURL="img/calendar.gif" style="cursor:hand" />
             -
            <d:datetime name="txtAgreementHeadEndDate" size="10" myClass="text" match="txtAgreementHeadEndDate"  onclick="MyCalendar.SetDate(this,document.frmPost.txtAgreementHeadEndDate,'Y')" picURL="img/calendar.gif" style="cursor:hand" />
            &nbsp;&nbsp;<input type=button value="���" name="fillbutton" onclick="javascript:fill_The_Data()" >
        </td>
	</tr>
	<%}else{%>
	<tr>
    	<td colspan="7" class="import_tit" align="center">
    		<font size="3">Э��۸��嵥</font>
    	</td>
	 </tr>
	    <input type="hidden" name="txtStartAgreement" value="<tbl:writeparam name="txtStartAgreement" />">   
        <input type="hidden" name="txtAgreementStartDate" value="<tbl:writeparam name="txtAgreementStartDate" />">   
        <input type="hidden" name="txtAgreementEndDate" value="<tbl:writeparam name="txtAgreementEndDate" />">
	<%}%>
	<tr class="list_head">
  		<td width="12%"  class="list_head"  nowrap><div align="center" >��Ʒ���</div></td>
  		<td width="17%" class="list_head"  nowrap><div align="center">��Ʒ����</div></td>
  		<td width="17%" class="list_head"  nowrap><div align="center">��Ʒ������</div></td>
  		<td width="20%"  class="list_head"  nowrap><div align="center">��Ŀ����</div></td>
  		<td width="14%"  class="list_head"  nowrap><div align="center">Э���</div></td>
  	    <td width="10%"  class="list_head"  nowrap><div align="center">��ʼʱ��</div></td>
  	    <td width="10%"  class="list_head"  nowrap><div align="center">����ʱ��</div></td>
	</tr>
	<%
	if(agreementProductList==null || "".equals(agreementProductList)){
	    //��ȡ��Ʒ��   
        String lstProdPkg = request.getParameter("ProductList");
        //ȡ���Ź�ȯ��
        String strGroupBargainDetailNo = request.getParameter("txtGroupBargainDetailNo");
        //��Ʒ������
        Collection colPackage = new ArrayList();
        //�����Ź�ȯ��ȡ�ö�Ӧ�Ĳ�Ʒ
        if (WebUtil.StringHaveContent(strGroupBargainDetailNo))
        {
            CsrBusinessUtility.fillPackageColByGroupBargainDetailNo(colPackage, strGroupBargainDetailNo);
        }
        else
        {
            if (WebUtil.StringHaveContent(lstProdPkg))
            {
                String[] aProdPkg = lstProdPkg.split(";");
                for (int i=0; i<aProdPkg.length; i++)
                {
                   colPackage.add(new Integer(aProdPkg[i]));
                }
            }		
        }
	    if(!colPackage.isEmpty()){
	    Iterator colPackageIter=colPackage.iterator();
	    while(colPackageIter.hasNext()){
	        Integer packageId=(Integer)colPackageIter.next();
	        Collection productCol=Postern.getProductIDByPackageID(packageId.intValue(),"S");
	        String packageName=Postern.getPackagenameByID(packageId.intValue());
	        if(!productCol.isEmpty()){
	            isExistProduct=true;
	            Iterator prodIter=productCol.iterator();
	            while(prodIter.hasNext()){
	                Integer productID=(Integer)prodIter.next();
	                String oneAcctType=Postern.getAcctItmeTypeIDByProductID(productID.intValue());
	                if(oneAcctType==null)oneAcctType=" ";
	                
	%>
                	<tr>
                	    <input type="hidden" name="txtAgreementProdID" value="<%=productID%>">
                	    <input type="hidden" name="txtAgreementpackID" value="<%=packageId%>">
                  		<td   class="list_bg2"  ><div align="center"><%=productID%></div></td>
                  		<td   class="list_bg2"  ><div align="center"><%=Postern.getProductNameByID(productID.intValue())%></div></td>
                  		<td   class="list_bg2"  ><div align="center"><%=packageName%></div></td>
                  		<td   class="list_bg2"  ><div align="center">
                  		<input type="hidden" name="txtAgreementAcctType" value="<%=oneAcctType%>">
                  		<d:getcmnname typeName="agreement_acctitemtypemap" match="<%=oneAcctType%>" />
                  		</div></td>
                  		<td   class="list_bg2"  ><div align="center"><input type="text" name="txtAgreementPrice" size="10" value=""></div></td>
                  	    <td   class="list_bg2"  ><div align="center"><input type="text" name="txtAgreementStartDate" size="10" value=""></div></td>
                  	    <td   class="list_bg2"  ><div align="center"><input type="text" name="txtAgreementEndDate" size="10" value=""></div></td>
                	</tr>
	<%
	            }
            }
	    }
	    }
	}else{      
	  	String [] strProductID=agreementProductList.split(",");
	  	
	  	String agreementPackageList=request.getParameter("txtAgreementPackageList");
	  	String [] strPackageID=agreementPackageList.split(",");
	  	
	  	String agreementPriceList=request.getParameter("txtAgreementPriceList");
	  	String [] strPrice=agreementPriceList.split(",");
	  	
	  	String agreementStartDateList=request.getParameter("txtAgreementStartDateList");
	  	String [] strStartDate=agreementStartDateList.split(",");
	  	
	  	String agreementEndDateList=request.getParameter("txtAgreementEndDateList");
	  	String [] strEndDate=agreementEndDateList.split(",");
	  	
	  	String agreementAcctTypeList=request.getParameter("txtAgreementAcctTypeList");
	  	String [] strAcctType=agreementAcctTypeList.split(",");
        if(strProductID!=null &&strProductID.length>0){
        isExistProduct=true;
		for (int i=0; i<strProductID.length ; i++){
			String oneAgreementPrice="";
			String oneStartDate="";
			String oneEndDate="";
			String oneAcctType="";
			if(agreementPriceList!=null &&!"".equals(agreementPriceList)){
			if(strPrice[i]!=null &&!"".equals(strPrice[i]))
				oneAgreementPrice=strPrice[i];
			}
			if(agreementStartDateList!=null &&!"".equals(agreementStartDateList)){
			if(strStartDate[i]!=null &&!"".equals(strStartDate[i]))
				oneStartDate=strStartDate[i];
			}
			if(agreementEndDateList!=null &&!"".equals(agreementEndDateList)){
			if(strEndDate[i]!=null &&!"".equals(strEndDate[i]))
				oneEndDate=strEndDate[i];
			}
			if(agreementAcctTypeList!=null &&!"".equals(agreementAcctTypeList)){
			if(strAcctType[i]!=null &&!"".equals(strAcctType[i]))
				oneAcctType=strAcctType[i];
			}
			if(!"label".equals(request.getParameter("showAgreementType"))){
	%>
        	<tr>
        	    <input type="hidden" name="txtAgreementProdID" value="<%=strProductID[i]%>">
        	    <input type="hidden" name="txtAgreementpackID" value="<%=strPackageID[i]%>">
          		<td  class="list_bg2"  ><div align="center"><%=strProductID[i]%></div></td>
          		<td  class="list_bg2"  ><div align="center"><%=Postern.getProductNameByID(Integer.parseInt(strProductID[i]))%></div></td>
          		<td  class="list_bg2"  ><div align="center"><%=Postern.getPackagenameByID(Integer.parseInt(strPackageID[i]))%></div></td>
          	    <td  class="list_bg2"  ><div align="center">
          	    <input type="hidden" name="txtAgreementAcctType" value="<%=oneAcctType%>">
          	    <d:getcmnname typeName="agreement_acctitemtypemap" match="<%=oneAcctType%>" />
          		</div></td>
          		<td  class="list_bg2"  ><div align="center"><input type="text" name="txtAgreementPrice" size="10" value="<%=oneAgreementPrice%>"></div></td>
          	    <td  class="list_bg2"  ><div align="center"><input type="text" name="txtAgreementStartDate" size="10" value="<%=oneStartDate%>"></div></td>
          	    <td  class="list_bg2"  ><div align="center"><input type="text" name="txtAgreementEndDate" size="10" value="<%=oneEndDate%>"></div></td>
        	</tr>
	        <%}else{%>
    	    <tr>
          		<td  class="list_bg2"  ><div align="center"><%=strProductID[i]%></div></td>
          		<td  class="list_bg2"  ><div align="center"><%=Postern.getProductNameByID(Integer.parseInt(strProductID[i]))%></div></td>
          		<td  class="list_bg2"  ><div align="center"><%=Postern.getPackagenameByID(Integer.parseInt(strPackageID[i]))%></div></td>
          		
          		<td  class="list_bg2"  ><div align="center"><d:getcmnname typeName="agreement_acctitemtypemap" match="<%=oneAcctType%>" /></div></td>
          	    <td  class="list_bg2"  ><div align="center"><%=oneAgreementPrice%></div></td>
          	    <td  class="list_bg2"  ><div align="center"><%=oneStartDate%></div></td>
          	    <td  class="list_bg2"  ><div align="center"><%=oneEndDate%></div></td>
        	</tr>
	<%      }
	    }
	    }
	}%>
</table>
<%if(!"label".equals(request.getParameter("showAgreementType"))){%>
<Script language=JavaScript>
<!--
if(!document.frmPost.txtStartAgreement.checked){  
    if(  document.frmPost.txtAgreementHeadStartDate!=null){
        document.frmPost.txtAgreementHeadStartDate.value="";
        document.frmPost.txtAgreementHeadStartDate.disabled=true;
    }
    if(document.frmPost.txtAgreementHeadEndDate!=null){
        document.frmPost.txtAgreementHeadEndDate.value="";
        document.frmPost.txtAgreementHeadEndDate.disabled=true;
    }
    if(document.frmPost.fillbutton!=null){
        document.frmPost.fillbutton.disabled=true;
    }
    if(document.frmPost.txtAgreementProdID!=null){
    if (document.frmPost.txtAgreementProdID.length > 0) {
        for (i=0;i<document.frmPost.txtAgreementProdID.length;i++){
            document.frmPost.txtAgreementPrice[i].value="";
            document.frmPost.txtAgreementPrice[i].disabled=true;
            document.frmPost.txtAgreementStartDate[i].value="";
            document.frmPost.txtAgreementStartDate[i].disabled=true;
            document.frmPost.txtAgreementEndDate[i].value="";
            document.frmPost.txtAgreementEndDate[i].disabled=true;
        }
    }else{
        document.frmPost.txtAgreementPrice.value="";
        document.frmPost.txtAgreementPrice.disabled=true;
        document.frmPost.txtAgreementStartDate.value="";
        document.frmPost.txtAgreementStartDate.disabled=true;
        document.frmPost.txtAgreementEndDate.value="";
        document.frmPost.txtAgreementEndDate.disabled=true;
    }
    }
}
//-->
</Script>
<%}%>


<% if(!isExistProduct){%>
<Script language=JavaScript>
<!--
if(document.frmPost.txtStartAgreement!=null){
        document.frmPost.txtStartAgreement.disabled=true;
}
//-->
</Script>
<%}%>