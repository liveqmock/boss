<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="resultset" prefix="rs" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="osstags" prefix="d" %>

<%@ page import="com.dtv.oss.util.Postern" %>
<%@ page import="com.dtv.oss.web.util.CommonKeys" %>
<%@ page import="com.dtv.oss.web.util.WebUtil" %>
<script language=javascript>

function frm_submit()
{
	
	       document.frmPost.submit();
	
}
function query_customer(){
	if(document.frmPost.txtCustomerID.value==""){
		alert("客户号不能为空！");
		return;
	}
	var strcustomerid = document.frmPost.txtCustomerID.value;
	var strFeatures = "width=600px,height=500px,resizable=no,toolbar=no,scrollbars=yes,status=yes";
  	window.open("query_complain_customer.do?newCustomerID="+strcustomerid,"电话号码查询",strFeatures);
}
function back_submit(){
	document.frmPost.action="open_group_subcustomer.do";
	document.frmPost.submit();
}
</script>
<%
    String lstProdPkg = request.getParameter("ProductList");
    String lstCamp = request.getParameter("CampaignList");
   
    String strPkgName = "";
    String strCampName = "";
    
    int iTmp;
    int iGroupBargainDetailID = 0;
    Collection colPackage = new ArrayList();
    Collection colCampaign = new ArrayList();
    
    //与类型类型对应的设备
    String terminalDeviceList=(request.getParameter("TerminalDeviceList")==null) ? "" : request.getParameter("TerminalDeviceList");
    String deviceClassDescription =(request.getParameter("DeviceClassDescription")==null) ? "" : request.getParameter("DeviceClassDescription");
    String deviceClassList=(request.getParameter("DeviceClassList")==null) ? "" : request.getParameter("DeviceClassList");
	String txtID=(String)request.getParameter("txtID");
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
<form name="frmPost" method="post" action="group_subcustomer_info_create.do" >
<table  border="0" align="center" cellpadding="0" cellspacing="10">
  <tr>
    <td><img src="img/list_tit.gif" width="19" height="19"></td>
    <td class="title">子客户开户确认</td>
  </tr>
</table>
<table width="820"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>
<br>

   <table width="820" border="0" align="center" cellpadding="4" cellspacing="1" class="list_bg">
	<tr>
	  <td width="100%" class="import_tit" align="center">子客户基本信息</td>
     </tr>
     </table>
   <table width="100%"  border="0" align="center" cellpadding="4" cellspacing="1" class="list_bg">
        <tr>
          <td width="17%" align="right" class="list_bg2">姓名*</td>
          <td width="33%" class="list_bg1" align="left"><font size="2">
             <input type="text" name="txtName" size="25" maxlength="20" value="<tbl:writeparam name="txtName" />"  class="text" readonly>
          </font> </td>                      
          <td width="17%" class="list_bg2" align="right">性别</td>          
          <td width="33%" class="list_bg1" align="left"><font size="2"> 
            <d:selcmn name="txtGender" mapName="SET_C_CUSTOMERGENDER" match="txtGender" width="23" />
          </font> </td>
        </tr> 
        <tr>  
           <td  class="list_bg2" align="right">证件类型*</td>
           <td  class="list_bg1" align="left"><font size="2">
           
             <d:selcmn name="txtCardType" mapName="SET_C_CUSTOMERCARDTYPE" match="txtCardType" width="23" />
           </font></td>  
           <td  class="list_bg2" align="right">证件号*</td>
           <td  class="list_bg1" align="left"><font size="2">        
             <input type="text" name="txtCardID" size="25" maxlength="25" value="<tbl:writeparam name="txtCardID" />" class="text" readonly>               
           </font></td>
        </tr>
        <tr>            
           <td  class="list_bg2" align="right">联系电话*</td>
           <td  class="list_bg1" align="left"><font size="2">        
             <input type="text" name="txtTelephone" size="25" maxlength="20" value="<tbl:writeparam name="txtTelephone"/>" class="text"  readonly>             
           </font></td>
           <td  class="list_bg2" align="right">移动电话</td>
           <td  class="list_bg1" align="left"><font size="2">
             <input type="text" name="txtMobileNumber" size="25" maxlength="20" value="<tbl:writeparam name="txtMobileNumber" />" class="text"  readonly>
           </font></td>
        </tr>
        <tr>  
           <td  class="list_bg2" align="right">所在区*</td>
           <td  class="list_bg1" align="left"><font size="2">
             <input type="hidden" name="txtCounty" value="<tbl:writeparam name="txtCounty" />" >
             <input type="text" name="txtCountyDesc" size="25" maxlength="50" readonly value="<tbl:WriteDistrictInfo property="txtCounty" />" class="text" readonly>
           </font></td>  
           <td  class="list_bg2" align="right">详细地址*</td>
           <td  class="list_bg1" align="left"><font size="2">        
            <input type="text" name="txtDetailAddress" size="25" maxlength="100" value="<tbl:writeparam name="txtDetailAddress" />" class="text" readonly>            
           </font></td>
        </tr>
        <tr>  
           <td  class="list_bg2" align="right">邮编*</td>
           <td  class="list_bg1" align="left" colspan="3"><font size="2">
           <input type="text" name="txtPostcode" size="25" maxlength="6" value="<tbl:writeparam name="txtPostcode" />" class="text" readonly>
           </font></td>           
        </tr>
        <tr>   
           <td  class="list_bg2" align="right">&nbsp;<%=Postern.getFieldNameByFieldInterID("CUSTOMER_CATVID")%></td>
           <td  class="list_bg1" align="left"><font size="2">        
             <input type="text" name="txtCatvID" size="25" maxlength="50" value="<tbl:writeparam name="txtCatvID" />" class="text"  readonly>            
           </font></td>
           <td  class="list_bg2" align="right">出生日期</td>
           <td  class="list_bg1" align="left"><font size="2">
             <input type="text" name="txtBirthday" size="25" maxlength="50" value="<tbl:writeparam name="txtBirthday" />" class="text"  readonly>  
           </font></td>  
        </tr>
        <tr>            
           <td  class="list_bg2" align="right">国籍</td>
           <td  class="list_bg1" align="left"><font size="2">        
             <d:selcmn name="txtNationality" mapName="SET_C_NATIONALITY" match="txtNationality" width="23" />               
           </font></td>
           <td  class="list_bg2" align="right">电子邮件</td>
           <td  class="list_bg1" align="left"><font size="2">
             <input type="text" name="txtEmail" size="25" maxlength="100" value="<tbl:writeparam name="txtEmail" />"  class="text"  readonly>
           </font></td> 
        </tr>        
        <tr>          
           <td  class="list_bg2" align="right">社保卡编号</td>
           <td  class="list_bg1" align="left"><font size="2">
             <input type="text" name="txtSocialSecCardID" size="25" maxlength="25" value="<tbl:writeparam name="txtSocialSecCardID" />" class="text" readonly>
           </font></td>   
           <td  class="list_bg2" align="right">传真</td>
           <td  class="list_bg1" align="left"><font size="2">        
             <input type="text" name="txtFaxNumber" size="25" maxlength="100" value="<tbl:writeparam name="txtFaxNumber" />" class="text"  readonly>            
           </font></td>
        </tr>        
     </table>
      <table width="820" border="0" align="center" cellpadding="4" cellspacing="1" class="list_bg">
	<tr>
	  <td width="100%" class="import_tit" align="center">合同信息</td>
	</tr>
    </table>
   <table width="100%"  border="0" align="center" cellpadding="4" cellspacing="1" class="list_bg">
        <tr>  
           <td  width="17%" class="list_bg2" align="right">合同编号</td>
           <td  width="33%" class="list_bg1" align="left">
            <tbl:writeparam name="txtContractNo" />
           </td>  
           <td  width="17%" class="list_bg2" align="right">合同名称</td>
           <td  width="33%" class="list_bg1" align="left">        
            <tbl:writeparam name="txtContractName" />               
           </td>
        </tr>
        <tr>  
           <td  class="list_bg2" align="right">合同描述</td>
           <td  class="list_bg1" align="left" colspan="3">
            <tbl:writeparam name="txtContractDescription" /> 
           </td>  
        </tr>
        <tr>  
           <td  class="list_bg2" align="right">产品包</td>
           <td  class="list_bg1" align="left" colspan="3">
             <tbl:writeparam name="txtPackageName" />  
           </td>  
        </tr>
        <%	
	 String terminalDeviceNameList = "";
	 if(!deviceClassDescription.equals(""))
         {
         	
         	String [] aDeviceClassDescription=deviceClassDescription.split(";");
         	String [] aTerminalDeviceList=terminalDeviceList.split(";");
		String [] aDeviceClassList=deviceClassList.split(";");
    	  	for(int i=0;i<aDeviceClassDescription.length;i++) 	{ 
		String terminalDeviceName=Postern.getDeviceClassDescByDeviceClass((aDeviceClassList[i]));
		if(!"".equals(terminalDeviceNameList))
		
    	 		terminalDeviceNameList=terminalDeviceNameList+";";
    	 		
    	 	terminalDeviceNameList=terminalDeviceNameList+ terminalDeviceName;

    	 %>
         <tr> 
          <td class="list_bg2" align="right"><%=aDeviceClassDescription[i]%></td>
          <td class="list_bg1"  align="left" colspan="3" ><font size="2">
	        	<%=aTerminalDeviceList[i]%>
          </font></td>
         </tr>
         <%
         	}
        }
        if(request.getParameter("phoneNo")!=null) {       
      %>
         
		<tr> 
          <td  align="right" class="list_bg2"><font size="2">
          电话号码
          </font></td> 
          <td colspan="4" class="list_bg1"><font size="2">
           <%=request.getParameter("phoneNo")%>  
          </font></td>  
		</tr>      
<%   }  %> 
     </table>
     <table align="center" width="100%" border="0" cellspacing="1" cellpadding="3" class="list_bg" id="mnu1" style="display:">
         <tr> 
           <td colspan="4" class="import_tit" align="center"><font size="3">产品属性配置信息</font></td>
         </tr>
		     <tr ><td width="17%"></td><td width="33%"></td><td width="17%"></td><td width="33%"></td></tr>
<%
//产品属性配置
    	if (WebUtil.StringHaveContent(lstProdPkg))
        {
            String[] aProdPkg = lstProdPkg.split(";");
            for (int i=0; i<aProdPkg.length; i++)
            {
               iTmp = WebUtil.StringToInt(aProdPkg[i]);
               String strPkgID = Integer.toString(iTmp);
%>
	<tbl:productproperty serveyName="txtProductProperty" packageID="<%=strPkgID%>"  showType="text" tdWordStyle="list_bg2"  tdControlStyle="list_bg1" controlSize="25" headStyle="list_head" />
<%               
            }
        }    
%>    
      </table> 
     
      <tbl:CommonFeeAndPaymentInfo includeParameter="Fee_PayAndPrep" payCsiType="<%=CommonKeys.CUSTSERVICEINTERACTIONTYPE_GO%>" acctshowFlag ="false" confirmFlag="true" />
      	
      <tbl:hiddenparameters pass="ProductList/CampaignList/DeviceClassList/DeviceClassDescription/TerminalDeviceList/DeviceProductIds/txtIsInstall/txtAccount/txtPreferedTime/txtPreferedDate/phoneNo/itemID" />
      <tbl:hiddenparameters pass="txtDistrictID/txtForceDepostMoney/txtID/txtPackageID/txtGroupCustID/txtPackageNamet/txtOpenSourceType" />
      <tbl:hiddenparameters pass="OpenCampaignList/MutiProductList/SingleProductList/GeneralCampaignList" /> 
      <tbl:hiddenparameters pass="txtContractNo/txtContractName/txtContractDescription/selStatus" />

      <input type="hidden" name="func_flag" value="2004" />
      <input type="hidden" name="confirm_post" value="true" />
      <tbl:generatetoken />      
       
</form>   
       <br>
        <table align="center" border="0" cellspacing="0" cellpadding="0" >
	<tr >
	   <td><img src="img/button2_r.gif" width="22" height="20"></td>
	   <td><input name="prev" type="button" class="button" onClick="javascript:back_submit()" value="上一步"></td>
	   <td><img src="img/button2_l.gif" width="11" height="20"></td>
	   <td width="20" ></td>
	   <td><img src="img/button_l.gif" border="0" width="11" height="20"></td>
	   <td><input name="next" type="button" class="button" onClick="javascript:frm_submit()" value="确 定"></td>
	   <td><img src="img/button_r.gif" border="0" width="22" height="20"></td>
	</tr>
    </table>
    
     
    <br>  
    <table width="100%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
	  <tr>
	     <td><img src="img/mao.gif" width="1" height="1"></td>
	   </tr>
    </table>
