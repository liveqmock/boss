<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="bookmark" prefix="bk" %>
<%@ taglib uri="osstags" prefix="d"%>
<%@ page import="com.dtv.oss.dto.CampaignDTO" %>
<%@ page import="com.dtv.oss.util.Postern" %>
<%@ page import="com.dtv.oss.web.util.WebUtil" %>
<script language ="javascript">
   function cancel_submit(){
		window.close();
	}
</script>
         
 <table align="center" width="95%" border="0" cellspacing="1" cellpadding="3"  class="list_bg" >
     <tr> 
       <td colspan="2" align="center" class="import_tit"><strong>�Żݻϸ��</strong></td>
    </tr>
    <lgc:bloop requestObjectName="ResponseQueryResult" item="oneline" isOne="true">  
    <%
       CampaignDTO dto = (CampaignDTO) pageContext.findAttribute("oneline");
       int campaignID=dto.getCampaignID();
       /*
       String productIDstr = Postern.getProductIDByCampaignID(campaignID);
       String packageIDstr = Postern.getPackageIDByCampaignID(campaignID);
       String packageNameStr ="";
       String productName="";
       if(productIDstr!=null){
         String[] s =  productIDstr.split(";");
         for(int i=0;i<s.length;i++)
          productName = productName+Postern.getProductNameByProductID(WebUtil.StringToInt(s[i]))+";";
       }
        if(packageIDstr!=null){
         String[] s =  packageIDstr.split(";");
         for(int i=0;i<s.length;i++)
          packageNameStr = packageNameStr+Postern.getPackagenameByID(WebUtil.StringToInt(s[i]))+";";
       }
       */
    %>         
        <tr> 
          <td class="list_bg2" width="30%"  align="center">�����</td>
          <td class="list_bg1" width="70%"  align="center"><tbl:write name="oneline" property="campaignName" /></td>
        </tr>
        <tr>
          <td  class="list_bg2" align="center">�ʱ��</td>
          <td class="list_bg1" align="center">��<tbl:writedate name="oneline" property="DateFrom" />��<tbl:writedate name="oneline" property="DateTo" /></td>
        </tr>
        <tr>
          <td  class="list_bg2" align="center">���������</td>
          <td class="list_bg1" align="center"><tbl:write name="oneline" property="Description" /></td>
        </tr>
        <tr>
          <td  class="list_bg2" align="center">�Ƿ�������ͣ</td>
           
          <td class="list_bg1" align="center">
          <d:getcmnname typeName="SET_G_YESNOFLAG" match="oneline:AllowPause"/>
          </td>
        </tr> 
        <tr>
          <td  class="list_bg2" align="center">�Ƿ�����Ǩ��</td>
           <td class="list_bg1" align="center">
          <d:getcmnname typeName="SET_G_YESNOFLAG" match="oneline:AllowTransition"/>
          </td>
        </tr> 
         <tr>
          <td  class="list_bg2" align="center">�Ƿ��������</td>
           <td class="list_bg1" align="center">
          <d:getcmnname typeName="SET_G_YESNOFLAG" match="oneline:AllowTransfer"/>
          </td>
        </tr> 
        <tr>
          <td  class="list_bg2" align="center">�Ƿ���������</div></td>
            <td class="list_bg1" align="center">
            <d:getcmnname typeName="SET_G_YESNOFLAG" match="oneline:AllowClose"/>
           </font></td>
            </tr> 
           <tr>
            <td  class="list_bg2" align="center">�Ƿ�������</div></td>
           <td class="list_bg1" align="center">
           <d:getcmnname typeName="SET_G_YESNOFLAG" match="oneline:AllowAlter"/>
          </font></td>
        </tr>
         <tr>
            <td  class="list_bg2" align="center">ϵͳ��ͣ���Ƿ�Ʒ�</div></td>
           <td class="list_bg1" align="center">
           <d:getcmnname typeName="SET_G_YESNOFLAG" match="oneline:KeepBilling"/>
          </font></td>
        </tr>
        
</lgc:bloop>           
</table>

<br>
	  	<table align="center"  border="0" cellspacing="0" cellpadding="0">
         <tr>  
           <td><img src="img/button_l.gif" width="11" height="20"></td>
            <td><input name="Submit" type="button" class="button"
					value="�رմ���" onclick="javascript:cancel_submit()"></td>
            <td><img src="img/button_r.gif" width="22" height="20"></td>   
            
        </tr>
      </table>	 
 
 
                       


