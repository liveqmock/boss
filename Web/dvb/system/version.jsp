<%@ taglib uri="/WEB-INF/html.tld" prefix="tbl" %>
<%@ taglib uri="logic" prefix="lgc" %>
<%@ taglib uri="bookmark" prefix="bk" %>
<%@ taglib uri="osstags" prefix="d" %>
<table  border="0" align="center" cellpadding="0" cellspacing="10">
<tr>
    <td><img src="img/list_tit.gif" width="19" height="19"></td>
    <td class="title">版本说明</td>
  </tr>
</table>
<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0" background="img/line_01.gif">
  <tr>
    <td><img src="img/mao.gif" width="1" height="1"></td>
  </tr>
</table>
<br>

 <table width="810" border="0" cellspacing="1" cellpadding="3" align=center class="list_bg">
        <tr>
	  <td class="list_bg2" align="right">软件名称</td>
          <td class="list_bg1" align="left">
             <d:config prefix="" showName="SOFTWARENAME" suffix="" />
          </td>
        </tr>
        <tr>
	  <td class="list_bg2" align="right">软件版本</td>
          <td class="list_bg1" align="left">
             <d:config prefix="" showName="SOFTWAREVERSION" suffix="" />
          </td>
        </tr>
        <tr>
	   <td class="list_bg2" align="right">Build No.</div></td>
           <td class="list_bg1" align="left">
            <d:config prefix="" showName="DATABASEVERSION" suffix="" />
           </td>
        </tr>
        <tr>
	  <td class="list_bg2" align="right">开发商</td>
          <td class="list_bg1" align="left">
            <d:config prefix="" showName="DEVELOPER" suffix="" />
          </td>
        </tr>
        <tr>
	  <td class="list_bg2" align="right">使用者</div></td>
          <td class="list_bg1" align="left">
            <d:config prefix="" showName="OPERATOR" suffix="" />
          </td>
        </tr>

 </table>

      
