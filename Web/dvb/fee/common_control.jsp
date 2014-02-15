<%@ taglib uri="logic" prefix="lgc" %>
<%@ page import="com.dtv.oss.util.Postern,
                 com.dtv.oss.web.util.CommonKeys" %>

<%
       String payCsiType =(String)request.getAttribute("PayCsiType");
       System.out.println("______payCsiType="+payCsiType);
       String csiPaymentOption =Postern.getCsiPaymentOption(payCsiType); 
       double  generalFeeTotalAttr =(request.getAttribute("defaultGeneralFeeTotal")==null) ? 0 : ((Double)request.getAttribute("defaultGeneralFeeTotal")).doubleValue();
       double  forceFeeTotalAttr =(request.getAttribute("defaultForceFeeTotal")==null) ? 0 :((Double)request.getAttribute("defaultForceFeeTotal")).doubleValue();
       String optionIMButton ="立即支付";
       //if (generalFeeTotalAttr ==0 && forceFeeTotalAttr ==0){
       if (Math.abs(generalFeeTotalAttr)<0.0001 && Math.abs(forceFeeTotalAttr)<0.0001){
       			optionIMButton ="下一步";
       }
       if (CommonKeys.CSI_PAYMENT_OPTION_OP.equals(csiPaymentOption)){
%>
       <td width="20" ></td>
       <td><img src="img/button_l.gif" border="0" width="11" height="20"></td>
       <td><input name="next" type="button" class="button" onClick="javascript:frm_next('<%=CommonKeys.CSI_PAYMENT_OPTION_IM%>')" value="<%=optionIMButton%>"></td>
       <td><img src="img/button_r.gif" border="0" width="22" height="20"></td>
       <td width="20" ></td>
       <td><img src="img/button_l.gif" border="0" width="11" height="20"></td>
       <td><input name="next" type="button" class="button" onClick="javascript:frm_finish('<%=CommonKeys.CSI_PAYMENT_OPTION_IV%>')" value="确认(月底支付)"></td>
       <td><img src="img/button_r.gif" border="0" width="22" height="20"></td>
<% 
       } else if (CommonKeys.CSI_PAYMENT_OPTION_IM.equals(csiPaymentOption)){
%>
       <td width="20" ></td>
       <td><img src="img/button_l.gif" border="0" width="11" height="20"></td>
       <td><input name="next" type="button" class="button" onClick="javascript:frm_next('<%=CommonKeys.CSI_PAYMENT_OPTION_IM%>')" value="<%=optionIMButton%>"></td>
       <td><img src="img/button_r.gif" border="0" width="22" height="20"></td>
<%       	
       } else if (CommonKeys.CSI_PAYMENT_OPTION_IV.equals(csiPaymentOption)){
%>
       <td width="20" ></td>
       <td><img src="img/button_l.gif" border="0" width="11" height="20"></td>
       <td><input name="next" type="button" class="button" onClick="javascript:frm_finish('<%=CommonKeys.CSI_PAYMENT_OPTION_IV%>')" value="确认(月底支付)"></td>
       <td><img src="img/button_r.gif" border="0" width="22" height="20"></td>
<%
      } else if (CommonKeys.CSI_PAYMENT_OPTION_SP.equals(csiPaymentOption)){
%>
       <td width="20" ></td>
       <td><img src="img/button_l.gif" border="0" width="11" height="20"></td>
       <td><input name="next" type="button" class="button" onClick="javascript:frm_finish('<%=CommonKeys.CSI_PAYMENT_OPTION_SP%>')" value="确认(上门收取)"></td>
       <td><img src="img/button_r.gif" border="0" width="22" height="20"></td>
<%
     }	else if (CommonKeys.METHODOFPAYMENT_KHBX.equals(csiPaymentOption)){
%>
       <td width="20" ></td>
       <td><img src="img/button_l.gif" border="0" width="11" height="20"></td>
       <td><input name="next" type="button" class="button" onClick="javascript:frm_finish('<%=CommonKeys.CSI_PAYMENT_OPTION_IM%>')" value="<%=optionIMButton%>"></td>
       <td><img src="img/button_r.gif" border="0" width="22" height="20"></td>
<%       	
       }
%>
