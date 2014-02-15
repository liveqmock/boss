package com.dtv.oss.service.ejbevent.config;


import com.dtv.oss.dto.RoleOrganizationDTO;
import com.dtv.oss.service.ejbevent.csr.CsrAbstractEJbevent;

public class ConfigRoleOrganizationEJBEvent extends CsrAbstractEJbevent {

	public final static int CREATE_ROLE_ORGANIZATION= 1; // 添加组织角色
	
	public final static int MODIFY_ROLE_ORGANIZATION= 2; // 修改组织角色
	
	public final static int DELETE_ROLE_ORGANIZATION= 3; // 删除组织角色
	
    private RoleOrganizationDTO roleOrgDto;
    
	public RoleOrganizationDTO getRoleOrgDto() {
		return roleOrgDto;
	}
	public void setRoleOrgDto(RoleOrganizationDTO roleOrgDto) {
		this.roleOrgDto = roleOrgDto;
	}
}
