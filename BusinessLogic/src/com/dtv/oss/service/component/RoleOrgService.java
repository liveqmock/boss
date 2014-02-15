package com.dtv.oss.service.component;

import javax.ejb.CreateException;
import javax.ejb.FinderException;
import javax.ejb.RemoveException;

import com.dtv.oss.domain.Pallet;
import com.dtv.oss.domain.PalletHome;
import com.dtv.oss.domain.RoleOrganization;
import com.dtv.oss.domain.RoleOrganizationHome;
import com.dtv.oss.dto.PalletDTO;
import com.dtv.oss.dto.RoleOrganizationDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.ServiceContext;
import com.dtv.oss.service.ServiceException;
import com.dtv.oss.service.factory.HomeFactoryException;
import com.dtv.oss.service.util.HomeLocater;

public class RoleOrgService extends AbstractService {
	private ServiceContext context = null;

	public RoleOrgService() {
		super();
	}

	public RoleOrgService(ServiceContext context) {
		super();
		this.context = context;
	}

	public void createRoleOrg(RoleOrganizationDTO dto) throws ServiceException {
		LogUtility.log(this.getClass(), LogLevel.DEBUG, "������֯��ɫ����");
		try {
			RoleOrganizationHome home = HomeLocater.getRoleOrganizationHome();
			RoleOrganization roleOrganization =home.create(dto);
			dto.setId(roleOrganization.getId().intValue());
		} catch (HomeFactoryException e1) {
			LogUtility.log(this.getClass(), LogLevel.ERROR, "������֯��ɫ��λ����");
			throw new ServiceException("������֯��ɫ��λ����");
		} catch (CreateException e3) {
			LogUtility.log(this.getClass(), LogLevel.ERROR, "��֯��ɫ��������");
			throw new ServiceException("��֯��ɫ��������");
		}

	}

	public void modifyRoleOrg(RoleOrganizationDTO dto) throws ServiceException {
		LogUtility.log(this.getClass(), LogLevel.DEBUG, "��֯��ɫ����");
		try {
			RoleOrganizationHome home = HomeLocater.getRoleOrganizationHome();
			RoleOrganization roleOrganization = home.findByPrimaryKey(new Integer(dto.getId()));
			roleOrganization.ejbUpdate(dto);
		} catch (FinderException e) {
			LogUtility.log(this.getClass(), LogLevel.ERROR, "��֯��ɫ���¶�λ����");
			throw new ServiceException("��֯��ɫ���¶�λ����");
		} catch (HomeFactoryException e1) {
			LogUtility.log(this.getClass(), LogLevel.ERROR, "��֯��ɫ���·������");
			throw new ServiceException("��֯��ɫ���³���");
		}

	}

	public void deleteRoleOrg(int roleOrgID) throws ServiceException {
		LogUtility.log(this.getClass(), LogLevel.DEBUG, "��֯��ɫɾ��");
		try {
			RoleOrganizationHome home = HomeLocater.getRoleOrganizationHome();
			RoleOrganization roleOrganization = home.findByPrimaryKey(new Integer(roleOrgID));
			roleOrganization.remove();
		} catch (FinderException e) {
			LogUtility.log(this.getClass(), LogLevel.ERROR, "��֯��ɫɾ����λ����");
			throw new ServiceException("��֯��ɫɾ����λ����");
		} catch (HomeFactoryException e1) {
			LogUtility.log(this.getClass(), LogLevel.ERROR, "��֯��ɫɾ���������");
			throw new ServiceException("��֯��ɫɾ���������");
		} catch (RemoveException e2) {
			LogUtility.log(this.getClass(), LogLevel.ERROR, "��֯��ɫɾ������");
			throw new ServiceException("��֯��ɫɾ������");
		}

	}
}
