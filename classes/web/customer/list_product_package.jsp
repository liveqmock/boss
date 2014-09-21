<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>



<script language=javascript>
function view_package_detail(strID,strName)
{
   window.open('list_product_package_detail.do?txtPackageID='+strID+'&txtPackageName='+strName,'','toolbar=no,location=no,status=no,menubar=no,scrollbar=no,width=330,height=250');
}
</script>

  <table align="center" width="100%" border="0" cellspacing="1" cellpadding="3" class="list_bg">
      <tr class="list_head">
        <td width="10%" class="list_head">&nbsp;</td> 
        <td width="90%" class="list_head" align="center">Ãû³Æ</td> 
       </tr>

       <lgc:bloop requestObjectName="ResponseQueryResult" item="oneline" > 
            <tbl:printcsstr style1="list_bg1" style2="list_bg2" overStyle="list_over" >
                 <td align="center" ><tbl:checkbox name="listID"  value="oneline:packageID" match="ProductList" multipleMatchFlag="true" /></td>  
                 <td align="center" ><a href="JavaScript:view_package_detail('<tbl:write name="oneline" property="packageID" />','<tbl:write name="oneline" property="packageName" />')"><tbl:write name="oneline" property="packageName" /></a></td>                 
             </tbl:printcsstr>
        </lgc:bloop>                     
  </table>
 
       

