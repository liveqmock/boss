package com.dtv.oss.service.component;

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.FinderException;
import javax.ejb.RemoveException;

import com.dtv.oss.domain.Depot;
import com.dtv.oss.domain.DepotHome;
import com.dtv.oss.dto.DepotDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.ServiceContext;
import com.dtv.oss.service.ServiceException;
import com.dtv.oss.service.factory.HomeFactoryException;
import com.dtv.oss.service.util.HomeLocater;

public class DepotService extends AbstractService {
	private ServiceContext context = null;

	public DepotService() {
		super();
	}

	public DepotService(ServiceContext context) {
		super();
		this.context = context;
	}

	public void createDepot(DepotDTO dto) throws ServiceException {
		LogUtility.log(this.getClass(), LogLevel.DEBUG, "�����ֿⶨ��");
		try {
			DepotHome home = HomeLocater.getDepotHome();
			Depot depot=home.create(dto);
			dto.setDepotID(depot.getDepotID().intValue());
		} catch (HomeFactoryException e1) {
			LogUtility.log(this.getClass(), LogLevel.ERROR, "�����ֿⶨ�嶨λ����");
			throw new ServiceException("�����ֿⶨ�嶨λ����");
		} catch (CreateException e3) {
			LogUtility.log(this.getClass(), LogLevel.ERROR, "�ֿⶨ�崴������");
			throw new ServiceException("�ֿⶨ�崴������");
		}

	}
	public void updateDepot(DepotDTO dto) throws ServiceException {
		LogUtility.log(this.getClass(), LogLevel.DEBUG, "�ֿ����");
		try {
			DepotHome home = HomeLocater.getDepotHome();
			Depot depot=home.findByPrimaryKey(new Integer(dto.getDepotID()));
			depot.setDepotName(dto.getDepotName());
			depot.setDetailAddress(dto.getDetailAddress());
			depot.setDtLastmod(dto.getDtLastmod());
			depot.setOwnerID(dto.getOwnerID());
			depot.setStatus(dto.getStatus());
		}catch (FinderException e){
			LogUtility.log(this.getClass(), LogLevel.ERROR, "�ֿ���¶�λ����");
			throw new ServiceException("�ֿ���¶�λ����");
		} catch (HomeFactoryException e1) {
			LogUtility.log(this.getClass(), LogLevel.ERROR, "�ֿ���·������");
			throw new ServiceException("�ֿ���³���");
		}

	}
	
	public void deleteDepot(String pk) throws ServiceException{
		LogUtility.log(this.getClass(),LogLevel.DEBUG,"�ֿ�ɾ��");
		try {
			DepotHome home = HomeLocater.getDepotHome();
			Depot depot=home.findByPrimaryKey(Integer.valueOf(pk));
			depot.remove();
		} catch (NumberFormatException e) {
			LogUtility.log(this.getClass(), LogLevel.ERROR, "�ֿ�ɾ����־����");
			throw new ServiceException("�ֿ�ɾ����־����");
		} catch (EJBException e) {
			LogUtility.log(this.getClass(), LogLevel.ERROR, "�ֿ�ɾ���������");
			throw new ServiceException("�ֿ�ɾ���������");
		} catch (HomeFactoryException e) {
			LogUtility.log(this.getClass(), LogLevel.ERROR, "�ֿ�ɾ���������");
			throw new ServiceException("�ֿ�ɾ���������");
		} catch (FinderException e) {
			LogUtility.log(this.getClass(), LogLevel.ERROR, "�ֿ�ɾ����λ����");
			throw new ServiceException("�ֿ�ɾ����λ����");
		} catch (RemoveException e) {
			LogUtility.log(this.getClass(), LogLevel.ERROR, "�ֿ�ɾ������");
			throw new ServiceException("�ֿ�ɾ������");
		}
	}
}
