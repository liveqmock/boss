<form name="frmPost" action="department_config.do" method="post" >
<input type="hidden" name="Action" value=""/>
<input type="hidden" name="txtFrom" value="1"/>
<input type="hidden" name="txtTo" value="10"/>
<input type="hidden" name="modFlags" value=""/>
<input type="hidden" name="func_flag" value="6013"/>
<input type="hidden" name="txtParentOrgID" value="1"/>
<input type="hidden" name="txtOrgType" value="D"/>
<input type="hidden" name="orgID" value="1">
<input type="hidden" name="orgType" value="D">


<BR>
<table width="100%" border="0" cellpadding="2" cellspacing="1" class="fulltable" >
        <tr class="trtitle">
          <td width="5%"><div align="center"><input type="checkbox" name="selall" onclick="javascript:select_all_by_name(document.frmPost,'Flags', this.checked)"></div></td>
          <td width="15%" class="t12"><div align="center">部门ID</div></td>
          <td width="20%" class="t12"><div align="center">部门名称*</div></td>
          <td width="20%" class="t12"><div align="center">部门描述</div></td>
          <td width="20%" class="t12"><div align="center">部门类型</div></td>
          <td width="20%" class="t12"><div align="center">所属公司名称</div></td>
        </tr>
        

<tr class="trline">
	  	  <td><div align="center"><input type="checkbox" name="Flags" value="0"></div></td>
          <input type="hidden" name="txtDtLastmod" value="2004-10-29 14:05:40.829"/>
          <td><div align="center"><input type="text" name="txtOrgID" maxlength="50" value="2" class="textgray" readonly/></div></td>
          <td><div align="center"><input type="text" name="txtOrgName" maxlength="50" value="总师办"/></div></td>
          <td><div align="center"><input type="text" name="txtOrgDesc" maxlength="50" value=""/></div></td>
          <td><div align="center"><select name="txtOrgSubType"><option value="" >----------</option>
<option value="Z">安装服务提供商</option>
<option value="S">代理商</option>
<option value="D">前端运维</option>
<option value="I">业务受理部</option>
<option value="M">市场部</option>
<option value="A">产品提供商</option>
<option value="F">营帐中心</option>
<option value="C">客服中心</option>
<option value="T">电话中心</option>
<option value="P">付费渠道</option>
<option value="B">分公司运维部</option>
<option value="L">物资部</option>
<option value="G">设备提供商</option>
<option value="E" selected="selected">总师办</option>
</select></div></td>
          <td><div align="center"><input type="text" name="txtParOrgName" value="总公司" class="textgray" readonly/></div></td></tr>


<tr class="trline">
	  	  <td><div align="center"><input type="checkbox" name="Flags" value="1"></div></td>
          <input type="hidden" name="txtDtLastmod" value="2004-10-29 14:05:40.839"/>
          <td><div align="center"><input type="text" name="txtOrgID" maxlength="50" value="3" class="textgray" readonly/></div></td>
          <td><div align="center"><input type="text" name="txtOrgName" maxlength="50" value="市场部"/></div></td>
          <td><div align="center"><input type="text" name="txtOrgDesc" maxlength="50" value=""/></div></td>
          <td><div align="center"><select name="txtOrgSubType"><option value="" >----------</option>
<option value="Z">安装服务提供商</option>
<option value="S">代理商</option>
<option value="D">前端运维</option>
<option value="I">业务受理部</option>
<option value="M" selected="selected">市场部</option>
<option value="A">产品提供商</option>
<option value="F">营帐中心</option>
<option value="C">客服中心</option>
<option value="T">电话中心</option>
<option value="P">付费渠道</option>
<option value="B">分公司运维部</option>
<option value="L">物资部</option>
<option value="G">设备提供商</option>
<option value="E">总师办</option>
</select></div></td>
          <td><div align="center"><input type="text" name="txtParOrgName" value="总公司" class="textgray" readonly/></div></td></tr>


<tr class="trline">
	  	  <td><div align="center"><input type="checkbox" name="Flags" value="2"></div></td>
          <input type="hidden" name="txtDtLastmod" value="2004-10-29 14:05:40.849"/>
          <td><div align="center"><input type="text" name="txtOrgID" maxlength="50" value="4" class="textgray" readonly/></div></td>
          <td><div align="center"><input type="text" name="txtOrgName" maxlength="50" value="物资部"/></div></td>
          <td><div align="center"><input type="text" name="txtOrgDesc" maxlength="50" value=""/></div></td>
          <td><div align="center"><select name="txtOrgSubType"><option value="" >----------</option>
<option value="Z">安装服务提供商</option>
<option value="S">代理商</option>
<option value="D">前端运维</option>
<option value="I">业务受理部</option>
<option value="M">市场部</option>
<option value="A">产品提供商</option>
<option value="F">营帐中心</option>
<option value="C">客服中心</option>
<option value="T">电话中心</option>
<option value="P">付费渠道</option>
<option value="B">分公司运维部</option>
<option value="L" selected="selected">物资部</option>
<option value="G">设备提供商</option>
<option value="E">总师办</option>
</select></div></td>
          <td><div align="center"><input type="text" name="txtParOrgName" value="总公司" class="textgray" readonly/></div></td></tr>


<tr class="trline">
	  	  <td><div align="center"><input type="checkbox" name="Flags" value="3"></div></td>
          <input type="hidden" name="txtDtLastmod" value="2004-10-29 14:05:40.949"/>
          <td><div align="center"><input type="text" name="txtOrgID" maxlength="50" value="5" class="textgray" readonly/></div></td>
          <td><div align="center"><input type="text" name="txtOrgName" maxlength="50" value="客服中心"/></div></td>
          <td><div align="center"><input type="text" name="txtOrgDesc" maxlength="50" value=""/></div></td>
          <td><div align="center"><select name="txtOrgSubType"><option value="" >----------</option>
<option value="Z">安装服务提供商</option>
<option value="S">代理商</option>
<option value="D">前端运维</option>
<option value="I">业务受理部</option>
<option value="M">市场部</option>
<option value="A">产品提供商</option>
<option value="F">营帐中心</option>
<option value="C" selected="selected">客服中心</option>
<option value="T">电话中心</option>
<option value="P">付费渠道</option>
<option value="B">分公司运维部</option>
<option value="L">物资部</option>
<option value="G">设备提供商</option>
<option value="E">总师办</option>
</select></div></td>
          <td><div align="center"><input type="text" name="txtParOrgName" value="总公司" class="textgray" readonly/></div></td></tr>


<tr class="trline">
	  	  <td><div align="center"><input type="checkbox" name="Flags" value="4"></div></td>
          <input type="hidden" name="txtDtLastmod" value="2004-10-29 14:05:40.999"/>
          <td><div align="center"><input type="text" name="txtOrgID" maxlength="50" value="6" class="textgray" readonly/></div></td>
          <td><div align="center"><input type="text" name="txtOrgName" maxlength="50" value="电话中心"/></div></td>
          <td><div align="center"><input type="text" name="txtOrgDesc" maxlength="50" value=""/></div></td>
          <td><div align="center"><select name="txtOrgSubType"><option value="" >----------</option>
<option value="Z">安装服务提供商</option>
<option value="S">代理商</option>
<option value="D">前端运维</option>
<option value="I">业务受理部</option>
<option value="M">市场部</option>
<option value="A">产品提供商</option>
<option value="F">营帐中心</option>
<option value="C">客服中心</option>
<option value="T" selected="selected">电话中心</option>
<option value="P">付费渠道</option>
<option value="B">分公司运维部</option>
<option value="L">物资部</option>
<option value="G">设备提供商</option>
<option value="E">总师办</option>
</select></div></td>
          <td><div align="center"><input type="text" name="txtParOrgName" value="总公司" class="textgray" readonly/></div></td></tr>


<tr class="trline">
	  	  <td><div align="center"><input type="checkbox" name="Flags" value="5"></div></td>
          <input type="hidden" name="txtDtLastmod" value="2004-10-29 14:05:41.009"/>
          <td><div align="center"><input type="text" name="txtOrgID" maxlength="50" value="7" class="textgray" readonly/></div></td>
          <td><div align="center"><input type="text" name="txtOrgName" maxlength="50" value="营帐中心"/></div></td>
          <td><div align="center"><input type="text" name="txtOrgDesc" maxlength="50" value=""/></div></td>
          <td><div align="center"><select name="txtOrgSubType"><option value="" >----------</option>
<option value="Z">安装服务提供商</option>
<option value="S">代理商</option>
<option value="D">前端运维</option>
<option value="I">业务受理部</option>
<option value="M">市场部</option>
<option value="A">产品提供商</option>
<option value="F" selected="selected">营帐中心</option>
<option value="C">客服中心</option>
<option value="T">电话中心</option>
<option value="P">付费渠道</option>
<option value="B">分公司运维部</option>
<option value="L">物资部</option>
<option value="G">设备提供商</option>
<option value="E">总师办</option>
</select></div></td>
          <td><div align="center"><input type="text" name="txtParOrgName" value="总公司" class="textgray" readonly/></div></td></tr>


<tr class="trline">
	  	  <td><div align="center"><input type="checkbox" name="Flags" value="6"></div></td>
          <input type="hidden" name="txtDtLastmod" value="2004-10-29 14:05:41.029"/>
          <td><div align="center"><input type="text" name="txtOrgID" maxlength="50" value="8" class="textgray" readonly/></div></td>
          <td><div align="center"><input type="text" name="txtOrgName" maxlength="50" value="前端运维"/></div></td>
          <td><div align="center"><input type="text" name="txtOrgDesc" maxlength="50" value=""/></div></td>
          <td><div align="center"><select name="txtOrgSubType"><option value="" >----------</option>
<option value="Z">安装服务提供商</option>
<option value="S">代理商</option>
<option value="D" selected="selected">前端运维</option>
<option value="I">业务受理部</option>
<option value="M">市场部</option>
<option value="A">产品提供商</option>
<option value="F">营帐中心</option>
<option value="C">客服中心</option>
<option value="T">电话中心</option>
<option value="P">付费渠道</option>
<option value="B">分公司运维部</option>
<option value="L">物资部</option>
<option value="G">设备提供商</option>
<option value="E">总师办</option>
</select></div></td>
          <td><div align="center"><input type="text" name="txtParOrgName" value="总公司" class="textgray" readonly/></div></td></tr>



    <tr class="trline" >
                 <td align="right" class="t12" colspan="8" >
                 共有7条




	  &nbsp;
          <a href="javascript:goto_onClick(document.frmPost, 1)" class="link12">转到</a>
          <input type="text" name="txtPage" style="width:20">页 当前页状态(1/1页)
          </td>
          </tr>


	<tr>
	   <td colspan="8" >
		   <table align="center" border="0" cellspacing="0" cellpadding="0">
			   <tr>
                   <td><img src="images/button/button_l.gif" border="0" ></td>
			       <td background="images/button/button_m.gif"  ><a href="javascript:product_device_modify()" class="btn12">修改</a></td>
			       <td><img src="images/button/button_r.gif" border="0" ></td>
                   <td width="20"></td>
			       <td><img src="images/button/button_l.gif" border="0" ></td>
			       <td background="images/button/button_m.gif"  ><a href="javascript:product_device_delete()" class="btn12">删除</a></td>
			       <td><img src="images/button/button_r.gif" border="0" ></td>
		       </tr>
	       </table>
        </td>
    </tr>
</table>
</form>
<form name="productsForm" method="post" action="">
<input type="hidden" name="func_flag" value="6013"/>
<input type="hidden" name="addFlags" value=""/>
<input type="hidden" name="txtParentOrgID" value="1"/>
<input type="hidden" name="orgID" value="1">
<input type="hidden" name="orgType" value="D">


<input type="hidden" name="txtOrgType" value="D"/>
<input type="hidden" name="webpage.TOKEN" value="9beb74c07a096ebf9121c848dd13428e">
<table width="100%" border="0" cellpadding="2" cellspacing="1" class="fulltable" >
	<tr class="trtitle" >
      <td colspan="8" nowrap><div align="center">信息录入</div></td>
     </tr>
     <tr class="trtitle" >
          <td width="5%"><div align="center"><input type="checkbox" name="selall" onclick="javascript:select_all_by_name(document.productsForm,'txtFlag', this.checked)"></div></td>
          <td width="20%" class="t12"><div align="center">部门名称*</div></td>
          <td width="20%" class="t12"><div align="center">部门描述</div></td>
          <td width="20%" class="t12"><div align="center">部门类型</div></td>
          <td width="20%" class="t12"><div align="center">所属公司名称</div></td>
     </tr>
     
    <tr class="trline">
	  	  <td><div align="center"><input type="checkbox" name="txtFlag" value="0"></div></td>
          <td><div align="center"><input type="text" name="txtOrgName_add" size="25" maxlength="50" value=""/></div></td>
          <td><div align="center"><input type="text" name="txtOrgDesc_add" size="25" maxlength="50" value=""/></div></td>
          <td><div align="center"><select name="txtOrgSubType_add"><option value="" >----------</option>
<option value="Z">安装服务提供商</option>
<option value="S">代理商</option>
<option value="D">前端运维</option>
<option value="I">业务受理部</option>
<option value="M">市场部</option>
<option value="A">产品提供商</option>
<option value="F">营帐中心</option>
<option value="C">客服中心</option>
<option value="T">电话中心</option>
<option value="P">付费渠道</option>
<option value="B">分公司运维部</option>
<option value="L">物资部</option>
<option value="G">设备提供商</option>
<option value="E">总师办</option>
</select></div></td>
          <td><div align="center"><input type="text" name="txtParOrgName_add" size="25" maxlength="50" value="总公司" class="textgray" readonly/></div></td>
	</tr>
    
    <tr class="trline">
	  	  <td><div align="center"><input type="checkbox" name="txtFlag" value="1"></div></td>
          <td><div align="center"><input type="text" name="txtOrgName_add" size="25" maxlength="50" value=""/></div></td>
          <td><div align="center"><input type="text" name="txtOrgDesc_add" size="25" maxlength="50" value=""/></div></td>
          <td><div align="center"><select name="txtOrgSubType_add"><option value="" >----------</option>
<option value="Z">安装服务提供商</option>
<option value="S">代理商</option>
<option value="D">前端运维</option>
<option value="I">业务受理部</option>
<option value="M">市场部</option>
<option value="A">产品提供商</option>
<option value="F">营帐中心</option>
<option value="C">客服中心</option>
<option value="T">电话中心</option>
<option value="P">付费渠道</option>
<option value="B">分公司运维部</option>
<option value="L">物资部</option>
<option value="G">设备提供商</option>
<option value="E">总师办</option>
</select></div></td>
          <td><div align="center"><input type="text" name="txtParOrgName_add" size="25" maxlength="50" value="总公司" class="textgray" readonly/></div></td>
	</tr>
    
    <tr class="trline">
	  	  <td><div align="center"><input type="checkbox" name="txtFlag" value="2"></div></td>
          <td><div align="center"><input type="text" name="txtOrgName_add" size="25" maxlength="50" value=""/></div></td>
          <td><div align="center"><input type="text" name="txtOrgDesc_add" size="25" maxlength="50" value=""/></div></td>
          <td><div align="center"><select name="txtOrgSubType_add"><option value="" >----------</option>
<option value="Z">安装服务提供商</option>
<option value="S">代理商</option>
<option value="D">前端运维</option>
<option value="I">业务受理部</option>
<option value="M">市场部</option>
<option value="A">产品提供商</option>
<option value="F">营帐中心</option>
<option value="C">客服中心</option>
<option value="T">电话中心</option>
<option value="P">付费渠道</option>
<option value="B">分公司运维部</option>
<option value="L">物资部</option>
<option value="G">设备提供商</option>
<option value="E">总师办</option>
</select></div></td>
          <td><div align="center"><input type="text" name="txtParOrgName_add" size="25" maxlength="50" value="总公司" class="textgray" readonly/></div></td>
	</tr>
    
    <tr class="trline">
	  	  <td><div align="center"><input type="checkbox" name="txtFlag" value="3"></div></td>
          <td><div align="center"><input type="text" name="txtOrgName_add" size="25" maxlength="50" value=""/></div></td>
          <td><div align="center"><input type="text" name="txtOrgDesc_add" size="25" maxlength="50" value=""/></div></td>
          <td><div align="center"><select name="txtOrgSubType_add"><option value="" >----------</option>
<option value="Z">安装服务提供商</option>
<option value="S">代理商</option>
<option value="D">前端运维</option>
<option value="I">业务受理部</option>
<option value="M">市场部</option>
<option value="A">产品提供商</option>
<option value="F">营帐中心</option>
<option value="C">客服中心</option>
<option value="T">电话中心</option>
<option value="P">付费渠道</option>
<option value="B">分公司运维部</option>
<option value="L">物资部</option>
<option value="G">设备提供商</option>
<option value="E">总师办</option>
</select></div></td>
          <td><div align="center"><input type="text" name="txtParOrgName_add" size="25" maxlength="50" value="总公司" class="textgray" readonly/></div></td>
	</tr>
    
    <tr class="trline">
	  	  <td><div align="center"><input type="checkbox" name="txtFlag" value="4"></div></td>
          <td><div align="center"><input type="text" name="txtOrgName_add" size="25" maxlength="50" value=""/></div></td>
          <td><div align="center"><input type="text" name="txtOrgDesc_add" size="25" maxlength="50" value=""/></div></td>
          <td><div align="center"><select name="txtOrgSubType_add"><option value="" >----------</option>
<option value="Z">安装服务提供商</option>
<option value="S">代理商</option>
<option value="D">前端运维</option>
<option value="I">业务受理部</option>
<option value="M">市场部</option>
<option value="A">产品提供商</option>
<option value="F">营帐中心</option>
<option value="C">客服中心</option>
<option value="T">电话中心</option>
<option value="P">付费渠道</option>
<option value="B">分公司运维部</option>
<option value="L">物资部</option>
<option value="G">设备提供商</option>
<option value="E">总师办</option>
</select></div></td>
          <td><div align="center"><input type="text" name="txtParOrgName_add" size="25" maxlength="50" value="总公司" class="textgray" readonly/></div></td>
	</tr>
    

</table>
<table  width="100%" border="0" cellpadding="2" cellspacing="1" class="fulltable" >
	<tr>
	   <td width="100%" align="center">
		   <table align="center" border="0" cellspacing="0" cellpadding="0">
			   <tr>
			       <td><img src="images/button/button_l.gif" border="0" ></td>
			       <td background="images/button/button_m.gif"  ><a href="javascript:product_device_add()" class="btn12">追加</a></td>
			       <td><img src="images/button/button_r.gif" border="0" ></td>
		       </tr>
	       </table>
	    </td>
	</tr>
</table>
 </form>


