<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="bookmark" prefix="bk" %>
<%@ taglib uri="privilege" prefix="pri" %>
<%@ taglib uri="/WEB-INF/oss.tld" prefix="d" %>
<%@ page import="com.dtv.oss.util.Postern,
                 com.dtv.oss.dto.JqPrintConfigDTO,
                 com.dtv.oss.dto.CustomerDTO,
                 com.dtv.oss.dto.JobCardDTO,
                 com.dtv.oss.util.Organization,
                 com.dtv.oss.dto.CustServiceInteractionDTO,
                 com.dtv.oss.dto.OldCustomerInfoDTO,
                 com.dtv.oss.dto.AccountItemDTO,
                 com.dtv.oss.util.TimestampUtility" %>
<%@ page import="com.dtv.oss.web.util.WebUtil" %>
<%@ page import="java.util.*" %>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<style type="text/css">
body,tr,td {
	font-family: "宋体", System;
	font-size: 14px;
	color: #181818;
	text-decoration: none;
}
</style>
</head>
<td>
<%
    String txtpagetype =request.getParameter("txtpagetype");
    CustomerDTO custDto = null;
    // 平谷、门头沟客户资料打印
    if (txtpagetype.equals("customerInfo")){
       int customerId =WebUtil.StringToInt(request.getParameter("txtcustomerID"));
       custDto =Postern.getCustomerByID(customerId);
       pageContext.setAttribute("customerid", String.valueOf(customerId));
       pageContext.setAttribute("addressid", String.valueOf(custDto.getAddressID()));
       pageContext.setAttribute("addressDetail",Postern.getSimpleDetailAddress(custDto.getAddressID()));
    }
    else if (txtpagetype.equals("maintain_Mtg")){    //门头沟维修单打印
    	 int jobcardId =WebUtil.StringToInt(request.getParameter("txtJobcardId"));
    	 JobCardDTO jobDto =Postern.getJobCardDto(jobcardId);
    	 int custId =jobDto.getCustId();
    	 custDto =Postern.getCustomerByID(custId);
    	 String referTime ="";
       if (jobDto.getPreferedDate() !=null){
          referTime = TimestampUtility.TimestampToDateString(jobDto.getPreferedDate())
                    + Postern.getCommonSettingDataValueByNameAndKey("SET_C_CSIPREFEREDTIME",jobDto.getPreferedTime());
       }
       String custName =custDto.getName();
       String tel ="";
       if (custDto.getTelephone() !=null && !custDto.getTelephone().trim().equals("")){
              tel =custDto.getTelephone();
       }
       if (custDto.getTelephoneMobile() !=null && !custDto.getTelephoneMobile().trim().equals("")){
          if (!tel.equals("")){
              tel =tel +"/" +custDto.getTelephoneMobile();
          }else{
      	      tel =custDto.getTelephoneMobile();
          }
       }
       String custdistrictdes=Postern.getSimpleDetailAddress(jobDto.getAddressId());
 	     if(custdistrictdes==null) custdistrictdes="";
 	     String problemDesc =(jobDto.getDescription()==null) ? "" :jobDto.getDescription();
 	     pageContext.setAttribute("jobcardId",String.valueOf(jobcardId));
 	     pageContext.setAttribute("customerid",String.valueOf(custId));
 	     pageContext.setAttribute("referTime",referTime);
 	     pageContext.setAttribute("custName",custName);
 	     pageContext.setAttribute("tel",tel);
 	     pageContext.setAttribute("custdistrictdes",custdistrictdes);
 	     pageContext.setAttribute("problemDesc",problemDesc);
    }
    else if (txtpagetype.equals("install_Mtg")){  //门头沟安装单打印
    	 int custId =0;
       String contactPerson ="";
       String installdesc ="";
       String familytel ="";
       String contactTel ="";
       String cardType ="";
       String cardId ="";
       String serviceType ="";
       double serviceMoney =0;
       double otherMoney =0;
       String moveOldDetaildesc ="";
       String tsName ="";
       String tsTelephone ="";
       String tsCardType ="";
       String tsCardId ="";
       String referTime ="";
       Collection feeCols = new ArrayList();
       int no_id =0;
       int jobCardId =WebUtil.StringToInt(request.getParameter("txtJobCardId"));
       if (jobCardId !=0){
          no_id =jobCardId;
          JobCardDTO dto = Postern.getJobCardDto(no_id);
          custId =dto.getCustId();    
          custDto =Postern.getCustomerByID(custId);
          if (dto.getContactPhone() !=null) contactTel =dto.getContactPhone();
          if (dto.getContactPerson() !=null) contactPerson =dto.getContactPerson();
          installdesc =Postern.getSimpleDetailAddress(dto.getAddressId());
 	        if(installdesc==null) installdesc="";
          if (custDto.getTelephone() !=null && !custDto.getTelephone().trim().equals("")){
                familytel =custDto.getTelephone();
          }
          if (custDto.getTelephoneMobile() !=null && !custDto.getTelephoneMobile().trim().equals("")){
             if (!familytel.equals("")){
                 familytel =familytel +"/" +custDto.getTelephoneMobile();
             }else{
      	         familytel =custDto.getTelephoneMobile();
             }
          }
          cardType =(String)(Postern.getHashKeyValueByName("SET_C_CUSTOMERCARDTYPE").get(custDto.getCardType()));
          cardId =custDto.getCardID();
          if (dto.getPreferedDate() !=null)
              referTime = TimestampUtility.TimestampToDateString(dto.getPreferedDate())
                        + Postern.getCommonSettingDataValueByNameAndKey("SET_C_CSIPREFEREDTIME",dto.getPreferedTime());
         
          if ("C".equals(dto.getJobType())){
              serviceType =Postern.getCommonSettingDataValueByNameAndKey("SET_W_JOBCARDSUBTYPE",dto.getSubType());
              feeCols =Postern.getAllFeeListByCsiAndType(dto.getJobCardId(),"J"); 
              if (dto.getSubType().equals("Z")){
                  int addPortNumber = dto.getAddPortNumber();
						      if(addPortNumber != 0) serviceType = serviceType+"(增加端口数:"+addPortNumber+")";
					    }
					    if (dto.getSubType().equals("H")){
					        moveOldDetaildesc =Postern.getSimpleDetailAddress(dto.getOldAddressId());
 	                if(moveOldDetaildesc==null) moveOldDetaildesc="";
					    }
          }
       }
       int csi_id =WebUtil.StringToInt(request.getParameter("txtCsiID"));
       if (csi_id !=0){
           no_id =csi_id;
           CustServiceInteractionDTO csiDto =Postern.getCsiDTOByCSIID(no_id);
           serviceType =Postern.getCommonSettingDataValueByNameAndKey("SET_V_CUSTSERVICEINTERACTIONTYPE",csiDto.getType());
           feeCols =Postern.getAllFeeListByCsiAndType(csi_id,"M");  
           custId =csiDto.getCustomerID();
           custDto =Postern.getCustomerByID(custId);
           cardType =(String)(Postern.getHashKeyValueByName("SET_C_CUSTOMERCARDTYPE").get(custDto.getCardType()));
           cardId =custDto.getCardID();
           if (csiDto.getType().equals("OS") || csiDto.getType().equals("UO")){
              JobCardDTO dto =Postern.getJobCardDto("I",csi_id);
              if (csiDto.getType().equals("OS")){
                 contactTel =(custDto.getTelephone()==null) ? "" : custDto.getTelephone();
                 contactPerson =custDto.getName();
                 familytel =(custDto.getTelephoneMobile()==null) ? "" :custDto.getTelephoneMobile();
                 installdesc =Postern.getSimpleDetailAddress(custDto.getAddressID());
                 if(installdesc==null) installdesc="";
              }else{
         	       if (dto.getContactPhone() !=null) contactTel =dto.getContactPhone();
                 if (dto.getContactPerson() !=null) contactPerson =dto.getContactPerson();
                 installdesc =Postern.getSimpleDetailAddress(dto.getAddressId());
 	               if(installdesc==null) installdesc="";
                 if (custDto.getTelephone() !=null && !custDto.getTelephone().trim().equals("")){
                     familytel =custDto.getTelephone();
                 }
                 if (custDto.getTelephoneMobile() !=null && !custDto.getTelephoneMobile().trim().equals("")){
                    if (!familytel.equals("")){
                        familytel =familytel +"/" +custDto.getTelephoneMobile();
                    }else{
      	                familytel =custDto.getTelephoneMobile();
                    }
                 }
              }

              if (dto.getPreferedDate() !=null)
                   referTime = TimestampUtility.TimestampToDateString(dto.getPreferedDate())
                             + Postern.getCommonSettingDataValueByNameAndKey("SET_C_CSIPREFEREDTIME",dto.getPreferedTime());
           } else{
      	       OldCustomerInfoDTO oldcustDto =Postern.getOldCustomerInfoDTOByCSIID(csi_id);
      	       if (csiDto.getType().equals("MD")){
      	           moveOldDetaildesc =Postern.getSimpleDetailAddress(oldcustDto.getAddressID());
 	                 if(moveOldDetaildesc==null) moveOldDetaildesc="";
      	       } else if(csiDto.getType().equals("TS")){
      		         tsName =oldcustDto.getName();
      		         tsCardType =(String)(Postern.getHashKeyValueByName("SET_C_CUSTOMERCARDTYPE").get(oldcustDto.getCardType()));
      		         tsCardId =oldcustDto.getCardID();
      		         if (oldcustDto.getTelephone() !=null && !oldcustDto.getTelephone().trim().equals("")){
                      tsTelephone =oldcustDto.getTelephone();
                   }
                   if (oldcustDto.getMobileNumber() !=null && !oldcustDto.getMobileNumber().trim().equals("")){
                      if (!tsTelephone.equals("")){
                          tsTelephone =tsTelephone +"/" +oldcustDto.getMobileNumber();
                      }else{
      	                  tsTelephone =oldcustDto.getMobileNumber();
                      }
                   }
      	       }
               contactPerson =custDto.getName();
               contactTel =(custDto.getTelephone()==null) ? "" : custDto.getTelephone();
               familytel =(custDto.getTelephoneMobile()==null) ? "" :custDto.getTelephoneMobile();         
               installdesc =Postern.getSimpleDetailAddress(custDto.getAddressID());
               if(installdesc==null) installdesc="";     	
           }
       }
       
       Iterator feeIter =feeCols.iterator();
       while (feeIter.hasNext()){
           AccountItemDTO feeDto =(AccountItemDTO)feeIter.next();  
           if (!feeDto.getAcctItemTypeID().equals("A000001") && !feeDto.getAcctItemTypeID().equals("D000007")){
               serviceMoney =serviceMoney +feeDto.getValue();
           } else if (feeDto.getAcctItemTypeID().equals("D000007")){
        	     otherMoney =otherMoney +feeDto.getValue();
           }
       }
       pageContext.setAttribute("no_id",String.valueOf(no_id));
       pageContext.setAttribute("contactPerson",contactPerson);
       pageContext.setAttribute("installdesc",installdesc);
       pageContext.setAttribute("familytel",familytel);
       pageContext.setAttribute("contactTel",contactTel);
       pageContext.setAttribute("cardType",cardType);
       pageContext.setAttribute("cardId",cardId);
       pageContext.setAttribute("serviceType",serviceType);
       pageContext.setAttribute("serviceMoney",String.valueOf(serviceMoney));
       pageContext.setAttribute("otherMoney",String.valueOf(otherMoney));
       pageContext.setAttribute("moveOldDetaildesc",moveOldDetaildesc);
       pageContext.setAttribute("tsName",tsName);
       pageContext.setAttribute("tsTelephone",tsTelephone);
       pageContext.setAttribute("tsCardType",tsCardType);
       pageContext.setAttribute("tsCardId",tsCardId);
       pageContext.setAttribute("referTime",referTime);
    }
    ArrayList configList =new ArrayList();
    int     acctDistId =Postern.getAddressDtoByID(custDto.getAddressID()).getDistrictID();
    Map     orgMp =Postern.getAllOrganizationCache();
    Iterator it = orgMp.keySet().iterator();
		while (it.hasNext()) {
		     Organization org = (Organization) orgMp.get(it.next());
		     if (org.getParentOrgID() ==1 && "B".equals(org.getOrgType())){
		        if (Postern.getDistrictSettingByOrgID(org.getOrgID()).containsKey(String.valueOf(acctDistId))){
                configList =(ArrayList)Postern.getJqPrintConfigDTO(org.getOrgID(),txtpagetype);
                break;
		        }
		     }
		}
		System.out.println("configList--------->"+configList);
    /*
    //平谷
    if (Postern.getDistrictSettingByOrgID(130).containsKey(String.valueOf(acctDistId))){
        configList =(ArrayList)Postern.getJqPrintConfigDTO(618,txtpagetype);
    //门头沟
    }else if (Postern.getDistrictSettingByOrgID(124).containsKey(String.valueOf(acctDistId))){
        configList =(ArrayList)Postern.getJqPrintConfigDTO(614,txtpagetype);
    //房山
    }else if (Postern.getDistrictSettingByOrgID(123).containsKey(String.valueOf(acctDistId))){
    	  configList =(ArrayList)Postern.getJqPrintConfigDTO(613,txtpagetype);
    //密云
    }else if (Postern.getDistrictSettingByOrgID(128).containsKey(String.valueOf(acctDistId))){
    	  configList =(ArrayList)Postern.getJqPrintConfigDTO(420,txtpagetype);
    //延庆
    }else if (Postern.getDistrictSettingByOrgID(127).containsKey(String.valueOf(acctDistId))){
    	  configList =(ArrayList)Postern.getJqPrintConfigDTO(616,txtpagetype);
     //顺义
    }else if (Postern.getDistrictSettingByOrgID(125).containsKey(String.valueOf(acctDistId))){
    	 configList =(ArrayList)Postern.getJqPrintConfigDTO(615,txtpagetype);
     //怀柔
    }else if (Postern.getDistrictSettingByOrgID(129).containsKey(String.valueOf(acctDistId))){
       configList =(ArrayList)Postern.getJqPrintConfigDTO(617,txtpagetype);
    }
    */
    int postionHeight =0; 
    int postionWidth =0;
    JqPrintConfigDTO configDto =null ;   
    for (int k=0; k<configList.size();k++){
       configDto=(JqPrintConfigDTO)configList.get(k);
       if (!"A".equals(configDto.getParamtype())){ 
          if (postionHeight !=configDto.getPosition_height()){
%>
       <table>
    	  <tr>
    	  	 <td align="left" width="<%=configDto.getPosition_width()%>" height="<%=configDto.getPosition_height()-postionHeight%>" valign="bottom">
        <%
          }else {
        %>
           <td align="left" width="<%=configDto.getPosition_width()-postionWidth%>" valign="bottom">
        <%
          }
              String tdContext =""; 
              for (int i=0;i<configDto.getPrintName_Blank();i++){	
                  tdContext =tdContext+"&nbsp;";
              }
              if ("Y".equals(configDto.getPrintFlag())) 
                  tdContext =tdContext+configDto.getPrintName();
              for (int i=0;i<configDto.getPrintContext_Blank();i++){	
                  tdContext =tdContext+"&nbsp;";
              }
              if("B".equals(configDto.getParamtype())){
                String  paramname =configDto.getParamname();
                String  paramValue ="";
                if (paramname !=null && !paramname.trim().equals("")){
                   paramValue =(String)pageContext.getAttribute(paramname);
                }
                if (paramValue ==null) paramValue ="";
                String  printContext =configDto.getPrintContext();
                String  sql =printContext+paramValue;
                tdContext =tdContext+Postern.getJqPrintConfigValue(sql);
           %>
              <%=tdContext%>
          <% 
            }else if ("C".equals(configDto.getParamtype())){
   	           String  paramname =configDto.getParamname();
               String  paramValue =(String)pageContext.getAttribute(paramname);
               tdContext =tdContext+paramValue;
          %>
	          <%=tdContext%>
          <% 
            }else if ("D".equals(configDto.getParamtype())){
            	 String  printContext =(configDto.getPrintContext() ==null) ? "" :configDto.getPrintContext();
            	 tdContext =tdContext+printContext;
          %>
            <%=tdContext%>
          <%
            }
            postionHeight = configDto.getPosition_height();
            postionWidth =configDto.getPosition_width();
            if (k<configList.size()-1){
               configDto=(JqPrintConfigDTO)configList.get(k+1);
            }
          %>
          </td>
          <%
            if (postionHeight !=configDto.getPosition_height() || k==configList.size()-1){
          %>
        </tr>
        </table>
          <%
            }
         }
     }
     %>

</td>
<script language="javascript" >
  function window.onload(){
    window.print();
  }
</script>

