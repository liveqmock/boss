<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ page import="java.util.*" %>
<%@ page import="com.dtv.oss.service.util.CsrBusinessUtility" %>
<%@ page import="com.dtv.oss.web.util.WebUtil" %>
<%@ page import="com.dtv.oss.util.Postern" %>


 <%
     //获取产品包，优惠，费用    
    String lstProdPkg = request.getParameter("ProductList");
    String lstCamp = request.getParameter("CampaignList");
    
    String strGroupBargainDetailNo = request.getParameter("txtGroupBargainDetailNo");

    String strPkgName = "";
    String strCampName = "";
    
    int iTmp;
    int iGroupBargainDetailID = 0;
    double fTotalValueMustPay = 0;
    Collection colPackage = new ArrayList();  
    
    if (WebUtil.StringHaveContent(strGroupBargainDetailNo))
    {
        CsrBusinessUtility.fillPackageColByGroupBargainDetailNo(colPackage, strGroupBargainDetailNo);
        
        //获取团购的产品包
        Iterator it = colPackage.iterator();
        while (it.hasNext())
        {
            Integer iPkgId = (Integer)it.next();
            strPkgName = strPkgName + Postern.getPackagenameByID(iPkgId.intValue()) +"(团购:"+strGroupBargainDetailNo+")";
        }
        strCampName =Postern.getCampaignNameByBargainDetailNo(strGroupBargainDetailNo);
    }
    else
    {
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
    }    
String temp_flag =(request.getParameter("OpenFlag")==null) ? "" : request.getParameter("OpenFlag");    
  %>
 <table align="center" width="100%" border="0" cellpadding="4" cellspacing="1" class="list_bg">
    <tr > 
       <td colspan="3" class="import_tit" align="center"><font size="3">产品包购买信息</font></td>
       <td align=right class="import_tit"><A href="javascript:drawSubMenu('1')"><IMG id="arr1" alt="展开产品属性配置信息" src="img/icon_bottom.gif" border=0></A></td>
    </tr>
    <tr > 
      <td  align="right" class="list_bg2" width="17%"><font size="2"> 产品包</font></td> 
      <td colspan="3" class="list_bg1" ><font size="2"><%=strPkgName%></font></td>   
    </tr>         
    <tr> 
      <td  align="right" class="list_bg2" width="17%"><font size="2">优惠活动</font></td> 
      <td colspan="3" class="list_bg1" ><font size="2"><%=strCampName%></font></td>
    </tr>
<%if(request.getParameter("phoneNo")!=null) {       
         %>
         
		<tr> 
          <td  align="right" class="list_bg2"><font size="2">
          电话号码
          </font></td> 
          <td colspan="3" class="list_bg1"><font size="2">
           <%=request.getParameter("phoneNo")%>  
          </font></td>         
         </tr>      
<%}%>         
  </table>
  
  <br>
 <%
     String terminalDeviceNameList  =request.getParameter("DeviceClassDescription");
     String deviceClassList =request.getParameter("DeviceClassList");
     String terminalDeviceList =request.getParameter("TerminalDeviceList");

     if (WebUtil.StringHaveContent(terminalDeviceNameList)) {
  %>
  <table align="center" width="100%" border="0" cellpadding="4" cellspacing="1" class="list_bg" >
     <tr > 
       <td colspan="3" class="import_tit" align="center"><font size="3">硬件产品购买信息</font></td>
     </tr>
     <tr class="list_head" > 
      <td  align="center" class="list_head"><font size="2"> 设备型号</font></td> 
      <td  align="center" class="list_head"><font size="2"> 设备类型</font></td>   
      <td  align="center" class="list_head"><font size="2"> 设备名  </font></td>   
    </tr>   
 
 <%
        String[] aTerminalDeviceName = terminalDeviceNameList.split(";");
        String[] aDeviceClass =deviceClassList.split(";");
        String[] aTerminalDevice =terminalDeviceList.split(";");
        for (int i=0; i<aTerminalDeviceName.length; i++) {  
  %>
      <tbl:printcsstr style1="list_bg1" style2="list_bg2" overStyle="list_over" >
        <td  align="center"><%=aTerminalDeviceName[i]%></td> 
        <td  align="center"><%=aDeviceClass[i]%></td>
        <td  align="center"><%=aTerminalDevice[i]%></td>    
      </tbl:printcsstr>
      <% } %>
  </table>
  <% } %> 
          <table align="center" width="100%" border="0" cellspacing="1" cellpadding="3" class="list_bg" id="mnu1" style="display:none">
         <tr> 
           <td colspan="4" class="import_tit" align="center"><font size="3">产品属性配置信息</font></td>
         </tr>
		<tr ><td width="17%"></td><td width="33%"></td><td width="17%"></td><td width="33%"></td></tr>

		
<%
//产品属性配置
    if (WebUtil.StringHaveContent(strGroupBargainDetailNo))
    {
    	//获取团购的产品包
        Iterator it = colPackage.iterator();
        while (it.hasNext())
        {
            Integer iPkgId = (Integer)it.next();
%>
	<tbl:productproperty serveyName="txtProductProperty" packageID="<%=iPkgId.toString()%>"  showType="label" tdWordStyle="list_bg2" tdControlStyle="list_bg1"  controlSize="25" headStyle="list_head"/>

<%            
        }
    }
    else
    {
     String campaignList ="";
     if(lstCamp != null && lstCamp.length()>0 && ';'==lstCamp.charAt(lstCamp.length()-1))
				campaignList = lstCamp.substring(0,lstCamp.length()-1);
     String campaignProList = Postern.getBundle2CampaignPackageColStr(campaignList);
		 String allProductList = campaignProList+lstProdPkg;
		 System.out.println("+++allProductList="+allProductList);
    	if (WebUtil.StringHaveContent(allProductList))
        {
            String[] aProdPkg = allProductList.split(";");
            for (int i=0; i<aProdPkg.length; i++)
            {
               iTmp = WebUtil.StringToInt(aProdPkg[i]);
               String strPkgID = Integer.toString(iTmp);
%>

	<tbl:productproperty serveyName="txtProductProperty" packageID="<%=strPkgID%>"  showType="label" tdWordStyle="list_bg2"  tdControlStyle="list_bg1" controlSize="25" headStyle="list_head" />
<%               
            }
        }    
    }

%>      
	</table>
 