<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="osstags" prefix="d" %>

 <table width="810"  border="0" align="center" cellpadding="4" cellspacing="1" class="list_bg">
         <tr>
          <td class="list_bg2" width="17%" align="right">�û�֤��</td>
          <td class="list_bg1" width="33%" align="left"><font size="2">
             <input type="text" name="txtCustomerID" maxlength="10" size="22"  value="<tbl:writeparam name="txtCustomerID" />" class="textgray" readonly="true">
          </font> </td>                      
          <td class="list_bg2" width="17%" align="right">�ʻ�����</td>          
          <td class="list_bg1" width="33%" align="left"><font size="2"> 
             <input type="text" name="txtAccountName" maxlength="10" size="22"  value="<tbl:writeparam name="txtAccountName" />" class="textgray" readonly="true">
          </font> </td>
         </tr> 
         <tr>
           <td class="list_bg2" align="right">�˻���</td>
           <td class="list_bg1" align="left"><font size="2">
             <input type="text" name="txtAccountID" maxlength="10" size="22" value="<tbl:writeparam name="txtAccountID"  />"  class="textgray" readonly="true">
           </font> </td>                      
           <td class="list_bg2" align="right">����������</td>          
           <td class="list_bg1" align="left"><font size="2">
             <input type="text" name="txtTokenDeposit" size="22" value="<tbl:writeparam name="txtTokenDeposit" />" class="textgray" readonly="true">
           </font> </td>
         </tr> 
         <tr>
           <td class="list_bg2" align="right">��ʵ�������</td>
           <td class="list_bg1" align="left"><font size="2">
              <input type="text" name="txtCashDeposit" size="22" value="<tbl:writeparam name="txtCashDeposit" />" class="textgray" readonly="true"> 
           </font> </td>                      
           <td class="list_bg2" align="right">����ԭ��*</td>          
           <td class="list_bg1" align="left"><font size="2">
              <d:selcmn name="txtAdjustReason" mapName="SET_F_ACCOUNTADJUSTMENTREASON" match="txtAdjustReason" width="22" />
           </font> </td>
         </tr> 
         <tr>
           <td class="list_bg2" align="right">���˱�ע</td>
           <td class="list_bg1" colspan="3" align="left"><font size="2">
              <input type="text" name="txtadjustReMark" value="<tbl:writeparam name="txtadjustReMark" />" size="50"  class="text">
           </font> </td>                      
         </tr> 
     </table>