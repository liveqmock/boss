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
          <td width="15%" class="t12"><div align="center">����ID</div></td>
          <td width="20%" class="t12"><div align="center">��������*</div></td>
          <td width="20%" class="t12"><div align="center">��������</div></td>
          <td width="20%" class="t12"><div align="center">��������</div></td>
          <td width="20%" class="t12"><div align="center">������˾����</div></td>
        </tr>
        

<tr class="trline">
	  	  <td><div align="center"><input type="checkbox" name="Flags" value="0"></div></td>
          <input type="hidden" name="txtDtLastmod" value="2004-10-29 14:05:40.829"/>
          <td><div align="center"><input type="text" name="txtOrgID" maxlength="50" value="2" class="textgray" readonly/></div></td>
          <td><div align="center"><input type="text" name="txtOrgName" maxlength="50" value="��ʦ��"/></div></td>
          <td><div align="center"><input type="text" name="txtOrgDesc" maxlength="50" value=""/></div></td>
          <td><div align="center"><select name="txtOrgSubType"><option value="" >----------</option>
<option value="Z">��װ�����ṩ��</option>
<option value="S">������</option>
<option value="D">ǰ����ά</option>
<option value="I">ҵ������</option>
<option value="M">�г���</option>
<option value="A">��Ʒ�ṩ��</option>
<option value="F">Ӫ������</option>
<option value="C">�ͷ�����</option>
<option value="T">�绰����</option>
<option value="P">��������</option>
<option value="B">�ֹ�˾��ά��</option>
<option value="L">���ʲ�</option>
<option value="G">�豸�ṩ��</option>
<option value="E" selected="selected">��ʦ��</option>
</select></div></td>
          <td><div align="center"><input type="text" name="txtParOrgName" value="�ܹ�˾" class="textgray" readonly/></div></td></tr>


<tr class="trline">
	  	  <td><div align="center"><input type="checkbox" name="Flags" value="1"></div></td>
          <input type="hidden" name="txtDtLastmod" value="2004-10-29 14:05:40.839"/>
          <td><div align="center"><input type="text" name="txtOrgID" maxlength="50" value="3" class="textgray" readonly/></div></td>
          <td><div align="center"><input type="text" name="txtOrgName" maxlength="50" value="�г���"/></div></td>
          <td><div align="center"><input type="text" name="txtOrgDesc" maxlength="50" value=""/></div></td>
          <td><div align="center"><select name="txtOrgSubType"><option value="" >----------</option>
<option value="Z">��װ�����ṩ��</option>
<option value="S">������</option>
<option value="D">ǰ����ά</option>
<option value="I">ҵ������</option>
<option value="M" selected="selected">�г���</option>
<option value="A">��Ʒ�ṩ��</option>
<option value="F">Ӫ������</option>
<option value="C">�ͷ�����</option>
<option value="T">�绰����</option>
<option value="P">��������</option>
<option value="B">�ֹ�˾��ά��</option>
<option value="L">���ʲ�</option>
<option value="G">�豸�ṩ��</option>
<option value="E">��ʦ��</option>
</select></div></td>
          <td><div align="center"><input type="text" name="txtParOrgName" value="�ܹ�˾" class="textgray" readonly/></div></td></tr>


<tr class="trline">
	  	  <td><div align="center"><input type="checkbox" name="Flags" value="2"></div></td>
          <input type="hidden" name="txtDtLastmod" value="2004-10-29 14:05:40.849"/>
          <td><div align="center"><input type="text" name="txtOrgID" maxlength="50" value="4" class="textgray" readonly/></div></td>
          <td><div align="center"><input type="text" name="txtOrgName" maxlength="50" value="���ʲ�"/></div></td>
          <td><div align="center"><input type="text" name="txtOrgDesc" maxlength="50" value=""/></div></td>
          <td><div align="center"><select name="txtOrgSubType"><option value="" >----------</option>
<option value="Z">��װ�����ṩ��</option>
<option value="S">������</option>
<option value="D">ǰ����ά</option>
<option value="I">ҵ������</option>
<option value="M">�г���</option>
<option value="A">��Ʒ�ṩ��</option>
<option value="F">Ӫ������</option>
<option value="C">�ͷ�����</option>
<option value="T">�绰����</option>
<option value="P">��������</option>
<option value="B">�ֹ�˾��ά��</option>
<option value="L" selected="selected">���ʲ�</option>
<option value="G">�豸�ṩ��</option>
<option value="E">��ʦ��</option>
</select></div></td>
          <td><div align="center"><input type="text" name="txtParOrgName" value="�ܹ�˾" class="textgray" readonly/></div></td></tr>


<tr class="trline">
	  	  <td><div align="center"><input type="checkbox" name="Flags" value="3"></div></td>
          <input type="hidden" name="txtDtLastmod" value="2004-10-29 14:05:40.949"/>
          <td><div align="center"><input type="text" name="txtOrgID" maxlength="50" value="5" class="textgray" readonly/></div></td>
          <td><div align="center"><input type="text" name="txtOrgName" maxlength="50" value="�ͷ�����"/></div></td>
          <td><div align="center"><input type="text" name="txtOrgDesc" maxlength="50" value=""/></div></td>
          <td><div align="center"><select name="txtOrgSubType"><option value="" >----------</option>
<option value="Z">��װ�����ṩ��</option>
<option value="S">������</option>
<option value="D">ǰ����ά</option>
<option value="I">ҵ������</option>
<option value="M">�г���</option>
<option value="A">��Ʒ�ṩ��</option>
<option value="F">Ӫ������</option>
<option value="C" selected="selected">�ͷ�����</option>
<option value="T">�绰����</option>
<option value="P">��������</option>
<option value="B">�ֹ�˾��ά��</option>
<option value="L">���ʲ�</option>
<option value="G">�豸�ṩ��</option>
<option value="E">��ʦ��</option>
</select></div></td>
          <td><div align="center"><input type="text" name="txtParOrgName" value="�ܹ�˾" class="textgray" readonly/></div></td></tr>


<tr class="trline">
	  	  <td><div align="center"><input type="checkbox" name="Flags" value="4"></div></td>
          <input type="hidden" name="txtDtLastmod" value="2004-10-29 14:05:40.999"/>
          <td><div align="center"><input type="text" name="txtOrgID" maxlength="50" value="6" class="textgray" readonly/></div></td>
          <td><div align="center"><input type="text" name="txtOrgName" maxlength="50" value="�绰����"/></div></td>
          <td><div align="center"><input type="text" name="txtOrgDesc" maxlength="50" value=""/></div></td>
          <td><div align="center"><select name="txtOrgSubType"><option value="" >----------</option>
<option value="Z">��װ�����ṩ��</option>
<option value="S">������</option>
<option value="D">ǰ����ά</option>
<option value="I">ҵ������</option>
<option value="M">�г���</option>
<option value="A">��Ʒ�ṩ��</option>
<option value="F">Ӫ������</option>
<option value="C">�ͷ�����</option>
<option value="T" selected="selected">�绰����</option>
<option value="P">��������</option>
<option value="B">�ֹ�˾��ά��</option>
<option value="L">���ʲ�</option>
<option value="G">�豸�ṩ��</option>
<option value="E">��ʦ��</option>
</select></div></td>
          <td><div align="center"><input type="text" name="txtParOrgName" value="�ܹ�˾" class="textgray" readonly/></div></td></tr>


<tr class="trline">
	  	  <td><div align="center"><input type="checkbox" name="Flags" value="5"></div></td>
          <input type="hidden" name="txtDtLastmod" value="2004-10-29 14:05:41.009"/>
          <td><div align="center"><input type="text" name="txtOrgID" maxlength="50" value="7" class="textgray" readonly/></div></td>
          <td><div align="center"><input type="text" name="txtOrgName" maxlength="50" value="Ӫ������"/></div></td>
          <td><div align="center"><input type="text" name="txtOrgDesc" maxlength="50" value=""/></div></td>
          <td><div align="center"><select name="txtOrgSubType"><option value="" >----------</option>
<option value="Z">��װ�����ṩ��</option>
<option value="S">������</option>
<option value="D">ǰ����ά</option>
<option value="I">ҵ������</option>
<option value="M">�г���</option>
<option value="A">��Ʒ�ṩ��</option>
<option value="F" selected="selected">Ӫ������</option>
<option value="C">�ͷ�����</option>
<option value="T">�绰����</option>
<option value="P">��������</option>
<option value="B">�ֹ�˾��ά��</option>
<option value="L">���ʲ�</option>
<option value="G">�豸�ṩ��</option>
<option value="E">��ʦ��</option>
</select></div></td>
          <td><div align="center"><input type="text" name="txtParOrgName" value="�ܹ�˾" class="textgray" readonly/></div></td></tr>


<tr class="trline">
	  	  <td><div align="center"><input type="checkbox" name="Flags" value="6"></div></td>
          <input type="hidden" name="txtDtLastmod" value="2004-10-29 14:05:41.029"/>
          <td><div align="center"><input type="text" name="txtOrgID" maxlength="50" value="8" class="textgray" readonly/></div></td>
          <td><div align="center"><input type="text" name="txtOrgName" maxlength="50" value="ǰ����ά"/></div></td>
          <td><div align="center"><input type="text" name="txtOrgDesc" maxlength="50" value=""/></div></td>
          <td><div align="center"><select name="txtOrgSubType"><option value="" >----------</option>
<option value="Z">��װ�����ṩ��</option>
<option value="S">������</option>
<option value="D" selected="selected">ǰ����ά</option>
<option value="I">ҵ������</option>
<option value="M">�г���</option>
<option value="A">��Ʒ�ṩ��</option>
<option value="F">Ӫ������</option>
<option value="C">�ͷ�����</option>
<option value="T">�绰����</option>
<option value="P">��������</option>
<option value="B">�ֹ�˾��ά��</option>
<option value="L">���ʲ�</option>
<option value="G">�豸�ṩ��</option>
<option value="E">��ʦ��</option>
</select></div></td>
          <td><div align="center"><input type="text" name="txtParOrgName" value="�ܹ�˾" class="textgray" readonly/></div></td></tr>



    <tr class="trline" >
                 <td align="right" class="t12" colspan="8" >
                 ����7��




	  &nbsp;
          <a href="javascript:goto_onClick(document.frmPost, 1)" class="link12">ת��</a>
          <input type="text" name="txtPage" style="width:20">ҳ ��ǰҳ״̬(1/1ҳ)
          </td>
          </tr>


	<tr>
	   <td colspan="8" >
		   <table align="center" border="0" cellspacing="0" cellpadding="0">
			   <tr>
                   <td><img src="images/button/button_l.gif" border="0" ></td>
			       <td background="images/button/button_m.gif"  ><a href="javascript:product_device_modify()" class="btn12">�޸�</a></td>
			       <td><img src="images/button/button_r.gif" border="0" ></td>
                   <td width="20"></td>
			       <td><img src="images/button/button_l.gif" border="0" ></td>
			       <td background="images/button/button_m.gif"  ><a href="javascript:product_device_delete()" class="btn12">ɾ��</a></td>
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
      <td colspan="8" nowrap><div align="center">��Ϣ¼��</div></td>
     </tr>
     <tr class="trtitle" >
          <td width="5%"><div align="center"><input type="checkbox" name="selall" onclick="javascript:select_all_by_name(document.productsForm,'txtFlag', this.checked)"></div></td>
          <td width="20%" class="t12"><div align="center">��������*</div></td>
          <td width="20%" class="t12"><div align="center">��������</div></td>
          <td width="20%" class="t12"><div align="center">��������</div></td>
          <td width="20%" class="t12"><div align="center">������˾����</div></td>
     </tr>
     
    <tr class="trline">
	  	  <td><div align="center"><input type="checkbox" name="txtFlag" value="0"></div></td>
          <td><div align="center"><input type="text" name="txtOrgName_add" size="25" maxlength="50" value=""/></div></td>
          <td><div align="center"><input type="text" name="txtOrgDesc_add" size="25" maxlength="50" value=""/></div></td>
          <td><div align="center"><select name="txtOrgSubType_add"><option value="" >----------</option>
<option value="Z">��װ�����ṩ��</option>
<option value="S">������</option>
<option value="D">ǰ����ά</option>
<option value="I">ҵ������</option>
<option value="M">�г���</option>
<option value="A">��Ʒ�ṩ��</option>
<option value="F">Ӫ������</option>
<option value="C">�ͷ�����</option>
<option value="T">�绰����</option>
<option value="P">��������</option>
<option value="B">�ֹ�˾��ά��</option>
<option value="L">���ʲ�</option>
<option value="G">�豸�ṩ��</option>
<option value="E">��ʦ��</option>
</select></div></td>
          <td><div align="center"><input type="text" name="txtParOrgName_add" size="25" maxlength="50" value="�ܹ�˾" class="textgray" readonly/></div></td>
	</tr>
    
    <tr class="trline">
	  	  <td><div align="center"><input type="checkbox" name="txtFlag" value="1"></div></td>
          <td><div align="center"><input type="text" name="txtOrgName_add" size="25" maxlength="50" value=""/></div></td>
          <td><div align="center"><input type="text" name="txtOrgDesc_add" size="25" maxlength="50" value=""/></div></td>
          <td><div align="center"><select name="txtOrgSubType_add"><option value="" >----------</option>
<option value="Z">��װ�����ṩ��</option>
<option value="S">������</option>
<option value="D">ǰ����ά</option>
<option value="I">ҵ������</option>
<option value="M">�г���</option>
<option value="A">��Ʒ�ṩ��</option>
<option value="F">Ӫ������</option>
<option value="C">�ͷ�����</option>
<option value="T">�绰����</option>
<option value="P">��������</option>
<option value="B">�ֹ�˾��ά��</option>
<option value="L">���ʲ�</option>
<option value="G">�豸�ṩ��</option>
<option value="E">��ʦ��</option>
</select></div></td>
          <td><div align="center"><input type="text" name="txtParOrgName_add" size="25" maxlength="50" value="�ܹ�˾" class="textgray" readonly/></div></td>
	</tr>
    
    <tr class="trline">
	  	  <td><div align="center"><input type="checkbox" name="txtFlag" value="2"></div></td>
          <td><div align="center"><input type="text" name="txtOrgName_add" size="25" maxlength="50" value=""/></div></td>
          <td><div align="center"><input type="text" name="txtOrgDesc_add" size="25" maxlength="50" value=""/></div></td>
          <td><div align="center"><select name="txtOrgSubType_add"><option value="" >----------</option>
<option value="Z">��װ�����ṩ��</option>
<option value="S">������</option>
<option value="D">ǰ����ά</option>
<option value="I">ҵ������</option>
<option value="M">�г���</option>
<option value="A">��Ʒ�ṩ��</option>
<option value="F">Ӫ������</option>
<option value="C">�ͷ�����</option>
<option value="T">�绰����</option>
<option value="P">��������</option>
<option value="B">�ֹ�˾��ά��</option>
<option value="L">���ʲ�</option>
<option value="G">�豸�ṩ��</option>
<option value="E">��ʦ��</option>
</select></div></td>
          <td><div align="center"><input type="text" name="txtParOrgName_add" size="25" maxlength="50" value="�ܹ�˾" class="textgray" readonly/></div></td>
	</tr>
    
    <tr class="trline">
	  	  <td><div align="center"><input type="checkbox" name="txtFlag" value="3"></div></td>
          <td><div align="center"><input type="text" name="txtOrgName_add" size="25" maxlength="50" value=""/></div></td>
          <td><div align="center"><input type="text" name="txtOrgDesc_add" size="25" maxlength="50" value=""/></div></td>
          <td><div align="center"><select name="txtOrgSubType_add"><option value="" >----------</option>
<option value="Z">��װ�����ṩ��</option>
<option value="S">������</option>
<option value="D">ǰ����ά</option>
<option value="I">ҵ������</option>
<option value="M">�г���</option>
<option value="A">��Ʒ�ṩ��</option>
<option value="F">Ӫ������</option>
<option value="C">�ͷ�����</option>
<option value="T">�绰����</option>
<option value="P">��������</option>
<option value="B">�ֹ�˾��ά��</option>
<option value="L">���ʲ�</option>
<option value="G">�豸�ṩ��</option>
<option value="E">��ʦ��</option>
</select></div></td>
          <td><div align="center"><input type="text" name="txtParOrgName_add" size="25" maxlength="50" value="�ܹ�˾" class="textgray" readonly/></div></td>
	</tr>
    
    <tr class="trline">
	  	  <td><div align="center"><input type="checkbox" name="txtFlag" value="4"></div></td>
          <td><div align="center"><input type="text" name="txtOrgName_add" size="25" maxlength="50" value=""/></div></td>
          <td><div align="center"><input type="text" name="txtOrgDesc_add" size="25" maxlength="50" value=""/></div></td>
          <td><div align="center"><select name="txtOrgSubType_add"><option value="" >----------</option>
<option value="Z">��װ�����ṩ��</option>
<option value="S">������</option>
<option value="D">ǰ����ά</option>
<option value="I">ҵ������</option>
<option value="M">�г���</option>
<option value="A">��Ʒ�ṩ��</option>
<option value="F">Ӫ������</option>
<option value="C">�ͷ�����</option>
<option value="T">�绰����</option>
<option value="P">��������</option>
<option value="B">�ֹ�˾��ά��</option>
<option value="L">���ʲ�</option>
<option value="G">�豸�ṩ��</option>
<option value="E">��ʦ��</option>
</select></div></td>
          <td><div align="center"><input type="text" name="txtParOrgName_add" size="25" maxlength="50" value="�ܹ�˾" class="textgray" readonly/></div></td>
	</tr>
    

</table>
<table  width="100%" border="0" cellpadding="2" cellspacing="1" class="fulltable" >
	<tr>
	   <td width="100%" align="center">
		   <table align="center" border="0" cellspacing="0" cellpadding="0">
			   <tr>
			       <td><img src="images/button/button_l.gif" border="0" ></td>
			       <td background="images/button/button_m.gif"  ><a href="javascript:product_device_add()" class="btn12">׷��</a></td>
			       <td><img src="images/button/button_r.gif" border="0" ></td>
		       </tr>
	       </table>
	    </td>
	</tr>
</table>
 </form>


