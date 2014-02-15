<%@ page import="com.dtv.oss.web.util.CommonKeys" %>
<Script language=JavaScript>
function changePrefed(){
   var selfInstall ="<%=CommonKeys.CUSTSERVICEINTERACTIONIN_STYPE_SELFINSTALL%>" ;
   var install ="<%=CommonKeys.CUSTSERVICEINTERACTIONIN_STYPE_INSTALL%>";
   if (document.frmPost.txtIsInstall.value ==selfInstall){
      parent.document.all("prefered").style.display ="none";
      parent.document.frmPost.txtPreferedDate.value ="";
      parent.document.frmPost.txtPreferedTime.value ="";
   } else{
      parent.document.all("prefered").style.display ="";
   }
 
}
</Script>