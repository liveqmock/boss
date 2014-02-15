<%@ taglib uri="osstags" prefix="d" %>
<%@ page import="java.util.*,com.dtv.oss.util.Organization"%>
<%@ page import="com.dtv.oss.util.Postern"%>
<%@ page import="com.dtv.oss.web.util.WebUtil"%>
<%@ page import="com.dtv.oss.dto.OrganizationDTO"%>
<%@ page import="com.dtv.oss.dto.DistrictSettingDTO"%>
<%@ page import="com.dtv.oss.web.util.WebOperationUtil"%>
<%@ page import="com.dtv.oss.web.util.CurrentLogonOperator"%>
<%@ page import="com.dtv.oss.web.util.CommonKeys"%>
<%@ page import="com.dtv.oss.dto.CustomerProductDTO" %> 
<%@ page import="com.dtv.oss.dto.AcctItemTypeDTO" %>

<script language=javascript>
//选中下拉匡中被选中的项
function setSelected(sel, str)
{
    for (var i=0; i<sel.options.length; i++)  
    {
        if (sel.options(i).value == str)
        {
            sel.selectedIndex = i;
            return ;
        }         
    }

}

function get_select_by_name(sName)
{
    var a  = parent.document.all.tags("select");
    if (a==null) return null;
    if (a.length==null) return null;
    
    for (j=0; j<a.length; j++) {
        if (a(j).name==sName) return a(j);
    }
    
    return null;
}

function clear_current_select(e)
{
    if (e==null) return;
    
    if (e.options.length<=1) return;     
    for (var i=e.options.length-1; i>0; i--)      
        e.remove(i);    
    
    e.selectedIndex = 0;
}

function clear_select(sel)
{
    if (sel==null) return;
    
    var everysel = sel.split('/');    
    
    if (everysel == null) return;    
    if (everysel.length==null) return;      
    
    for (i=0; i<everysel.length; i++)
    {
        var e = get_select_by_name(everysel[i]);
	if (e!=null) clear_current_select(e);
    }      
	  
}

function submit_update_select(curaction, pk, updsel, othersel){
        parent.updselwaitlayer.style.display = "";
        
        clear_select(othersel);
        clear_select(updsel);
        document.formUpdateSelect.Action.value = curaction;
        document.formUpdateSelect.txtParentKey.value = pk;
        document.formUpdateSelect.txtUpdatedSelect.value = updsel;
        document.formUpdateSelect.submit();
}

function add_option_to_select(){
  <%
    String strAction = request.getParameter("Action");
    //when it is not the direct son , there are all parent keys in txtParentKey which are seperated by /
    String strParentKey = request.getParameter("txtParentKey");
    //in some particular situation, there are multiple selects in txtUpdatedSelect which are seperated by /
    String strUpdatedSelect = request.getParameter("txtUpdatedSelect");  
        System.out.println("strParentKey:"+strParentKey);
        System.out.println("txtUpdatedSelect:"+strUpdatedSelect);
    int iAction = 0;
    List lst=new ArrayList();
    Map mapKeyAndValue = new LinkedHashMap();
    
    Map mapNeeded = null;  
    Collection lstTmp = null;   
    Map maptmp = null;
    String strValue = null;
      
    if ((strAction!=null)&&(strParentKey!=null)&&(strUpdatedSelect!=null)) 
    {
        String[] lstsel = strUpdatedSelect.split("/");
        String[] lstpk = null;
        
        if (strAction.equals("subctocounty"))    iAction = 101;       // get the list of county from subcompany
        else if (strAction.equals("countytoss")) iAction = 102;       // get the list of streetstation from county
        else if (strAction.equals("subctoss"))   iAction = 103;       // get the list of streetstation from subcompany
        else if (strAction.equals("subctodpt"))  iAction = 104;       // get the list of department from subcompany
        else if (strAction.equals("subctossanddpt"))  iAction = 105;  // get the list of streetstation and department and branch from subcompany
        else if (strAction.equals("devclasstomodel")) iAction = 151;       // get the list of device model from device class
        else if (strAction.equals("depottopallet")) iAction = 201;       // get the list of pallet from depot
        else if (strAction.equals("csireasonid")) iAction =300;     // get the list of csi reason by csitype
        else if (strAction.equals("osttoid")) iAction = 301;       // get the list of open source id from open source type
        else if (strAction.equals("suborg4cal")) iAction = 302;
        else if (strAction.equals("acccsifee")) iAction = 303;    //收费记录查询
        else if (strAction.equals("subagent")) iAction = 304;
        else if (strAction.equals("devprovider")) iAction = 305;  //get the provider from devicemodel
        else if (strAction.equals("csi_adjust")) iAction=306;
        else if (strAction.equals("configsubtype")) iAction=307;
        else if (strAction.equals("geteventreason")) iAction=308;
        else if (strAction.equals("feetype")) iAction=309;//费用类型和帐目类型关联
        else if (strAction.equals("configcsireason")) iAction=310;
        else if (strAction.equals("customerSelectOption")) iAction=311; //个性化费率新增，选择业务帐户，显示客户产品列表
        else if (strAction.equals("serviceaccount")) iAction=312;//通过业务帐户id得到产品
        else if (strAction.equals("csitypetoreason")) iAction=313;//根据受理单类型取得受理原因
        else if (strAction.equals("jobtypetosubtype")) iAction=314;//根据工单类型得到工单子类型
        else if(strAction.equals("devclasstomodelnewstatus")) iAction=600;
        else if(strAction.equals("customerProductSelectOption")) iAction=601;//根据客户产品id取得对应的费用类型和帐户类型
        else if(strAction.equals("subtypetocustomizefee")) iAction=602;//根据工单子类型得到对应的定制华费用列表
        //get collection
        switch (iAction)
        {
           case 151: // get the list of device model from device class   
             mapNeeded = Postern.getDeviceModelByClass(strParentKey);
             lst.add(mapNeeded);
             break;   
            case 600: // get the list of device model from device class   
             mapNeeded = Postern.getDeviceModelByClass(strParentKey,"N");
             lst.add(mapNeeded);
             break;   
           case 300: 
             mapNeeded =Postern.getCsiReasonByCsitype(strParentKey);
             lst.add(mapNeeded);
             break;             
           case 301: // get the list of open source id from open source type
            System.out.println("CurrentLogonOperator.getCurrentOperatorOrgID(request)====="+CurrentLogonOperator.getCurrentOperatorOrgID(request));
            if (strParentKey.equals(CommonKeys.OPENSOURCETYPE_PROXY))
                 mapNeeded = WebOperationUtil.getProxyByOrgID(CurrentLogonOperator.getCurrentOperatorOrgID(request)); 
            else
              mapNeeded = new HashMap();
             
            System.out.println("update_select.jsp  mapNeeded======"+mapNeeded);
            lst.add(mapNeeded);
            break;  
           case 304: // get the list of agent model from orgid   
            mapNeeded = Postern.getProxyOrg(WebUtil.StringToInt(strParentKey));
            System.out.println("***************"+strParentKey);
            lst.add(mapNeeded);
            break;      
           case 305: // get the list of device model from device class   
             mapNeeded = Postern.getDeviceProviderByModel(strParentKey);
             System.out.println("*****deviceProvide*****"+mapNeeded);
             String seriallenth = Postern.getSerialLengthProviderByModel(strParentKey);
             System.out.println("*****seriallenth*****"+seriallenth);
             String checkFields=Postern.getFieldNameByModel(strParentKey);
             String[] arrFieldName=checkFields.split(",");
             System.out.println("*****checkFields*****"+checkFields);
             String strFieldName="";
             String strFieldDesc="";
             if(arrFieldName.length==2){
	             strFieldName=arrFieldName[0];
	             strFieldDesc=arrFieldName[1];
             }
            lst.add(mapNeeded);
         %>
            parent.document.getElementById('seriallength').value="<%=seriallenth%>";
            parent.document.getElementById('checkModelField').value="<%=strFieldName%>";
            parent.document.getElementById('checkModelDesc').value="";
            parent.document.getElementById('checkModelDesc').value="设备的序列号|"+"<%=strFieldDesc%>";
            
            var fieldDesc=parent.document.frmPost.checkModelDesc.value;
   
         <%
            break; 
            case 601:
                CustomerProductDTO cpDTO=Postern.getCustomerProductDTOByPSID(WebUtil.StringToInt(strParentKey));
                int productID=cpDTO.getProductID();
                String acctType=Postern.getAcctItmeTypeIDByProductID(productID);
                if(acctType==null)acctType="";
                Map acctItemTypeMap =Postern.getAllAcctItemType();
                pageContext.setAttribute("agreement_acctitemtypemap", acctItemTypeMap);
                String feeType="";
                AcctItemTypeDTO acctItemTypeDTO=Postern.getAcctItemTypeDTOByAcctItemTypeID(acctType);
                if(acctItemTypeDTO!=null)
                    feeType=acctItemTypeDTO.getFeeType();
                if(feeType==null)feeType="";
        %>    
            parent.document.frmPost.txtFeeType.value="<%=feeType%>";
            parent.document.frmPost.txtAcctItemTypeID.value="<%=acctType%>";
            parent.document.frmPost.txtShowMessageFeeType.value="<d:getcmnname typeName="SET_F_BRFEETYPE" match="<%=feeType%>" />";
            parent.document.frmPost.txtShowMessageAcctItemTypeID.value="<d:getcmnname typeName="agreement_acctitemtypemap" match="<%=acctType%>" />";
        <%
             break;
             case 602:
                mapNeeded =Postern.getConfigCustomizeFeeMap("SET_W_CUSTOMIZEFEECONFIGURATION",strParentKey);
                System.out.println("strParentKey:"+strParentKey);
                System.out.println("mapNeeded:"+mapNeeded);
                lst.add(mapNeeded);
              break; 
            case 306:
             mapNeeded =Postern.getAcctitemtype(strParentKey);
             lst.add(mapNeeded);
             break;
           case 307:
              mapNeeded =Postern.getSubConfigType(strParentKey);
             lst.add(mapNeeded);
             break;  
            case 308:
              System.out.println("***********************="+strParentKey);
              mapNeeded =Postern.getEventReasonById(strParentKey);
             lst.add(mapNeeded);
             break;     
            case 309:
              mapNeeded =Postern.getAcctItemMapWithFeeType(strParentKey);
              System.out.println("strParentKey:"+strParentKey);
              System.out.println("mapNeeded:"+mapNeeded);
             lst.add(mapNeeded);
             break;  
             case 310:
              mapNeeded =Postern.getCsiReasonByCsiType(strParentKey);
              System.out.println("strParentKey:"+strParentKey);
              System.out.println("mapNeeded:"+mapNeeded);
             lst.add(mapNeeded);
             break;     
             case 311:
              mapNeeded =Postern.getCustomerProductByServiceAccountID(strParentKey);
              System.out.println("strParentKey:"+strParentKey);
              System.out.println("mapNeeded:"+mapNeeded);
             lst.add(mapNeeded);
             break; 
             case 312:
              String []  temParentKey = strParentKey.split(";");
              if(temParentKey.length==2)
              {
              	mapNeeded = Postern.getPsMapByCon(WebUtil.StringToInt(temParentKey[0]),WebUtil.StringToInt(temParentKey[1]),0,true,"<>'C'");
              }
              System.out.println("strParentKey:"+strParentKey);
              System.out.println("mapNeeded:"+mapNeeded);
             lst.add(mapNeeded);
             break;  
             case 313:
             mapNeeded =Postern.getCsiReasonByType(strParentKey);
             if(mapNeeded==null)
             	mapNeeded=new LinkedHashMap();
             lst.add(mapNeeded);
             break;  
             case 314:
             if("C".equals(strParentKey))
                mapNeeded =Postern.getCommonDateKeyAndValueMap("SET_W_JOBCARDSUBTYPE");
             if(mapNeeded==null)
             	mapNeeded=new LinkedHashMap();
             lst.add(mapNeeded);    
         }
            for (int i=0; i<lst.size(); i++) {
            //add key and value to mapKeyAndValue
            Object obj = (Object)lst.get(i);
            Iterator itItem = null;
            
            if (obj instanceof Map){
                itItem = ((Map)obj).entrySet().iterator();
            }
            else{
                Collection curlst = (Collection)lst.get(i);
                itItem = curlst.iterator();
            }    
            
            switch (iAction){  
               case 300:  
               case 301: // get the list of open source id from open source type
               case 302:
               case 151: // get the list of device model from device class
               case 303:
               case 304:
               case 305:
               case 306:
               case 307:
               case 308:
               case 309:
               case 310:
               case 311:
               case 312:
               case 313:
               case 314:
               case 600:
               case 602:
               while (itItem.hasNext()){
                    Map.Entry item = (Map.Entry)itItem.next();
                    if (item.getValue()!=null)
                      strValue = item.getValue().toString();
                    else strValue = "";
                    mapKeyAndValue.put(item.getKey().toString(), strValue);

               }    
               break;   

            }
%>
    var e = get_select_by_name('<%=strUpdatedSelect%>');
    if (e==null) return;
<%    
            Iterator itkv = mapKeyAndValue.entrySet().iterator();            
            
            while (itkv.hasNext())
            {
                Map.Entry kvItem = (Map.Entry)itkv.next();
                if (kvItem.getValue()!=null) strValue = kvItem.getValue().toString();
                else strValue = "";  

%>     
    e.options.add(new Option('<%=strValue%>', '<%=kvItem.getKey().toString()%>'));
<%
           }
       }
    }   
%>     
      
}
add_option_to_select();
parent.updselwaitlayer.style.display = "none";
</script>
<form name="formUpdateSelect" method="post" action="update_select.screen">
	<input type="hidden" name="Action" value="">
	<input type="hidden" name="txtParentKey" value="">
	<input type="hidden" name="txtUpdatedSelect" value="">
</form>
