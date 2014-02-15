package com.dtv.oss.service.component;

import javax.ejb.CreateException;
import javax.ejb.FinderException;
import javax.ejb.RemoveException;

import com.dtv.oss.domain.VodHost;
import com.dtv.oss.domain.VodHostHome;
import com.dtv.oss.domain.VodProduct;
import com.dtv.oss.domain.VodProductHome;
import com.dtv.oss.dto.VODInterfaceHostDTO;
import com.dtv.oss.dto.VODInterfaceProductDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.ServiceException;
import com.dtv.oss.service.factory.HomeFactoryException;
import com.dtv.oss.service.util.BusinessUtility;
import com.dtv.oss.service.util.HomeLocater;

public class VODInterfaceService extends AbstractService {
	private SystemConfigNetServiceLoggerTool loggerTool = SystemConfigNetServiceLoggerTool
			.getInstance(null, null, 0);

	public VODInterfaceService(String remoteHostAddress, int operatorID) {
		loggerTool.setRemoteHostAddress(remoteHostAddress);
		loggerTool.setOperatorID(operatorID);
	}

	public void createVODHostObject(VODInterfaceHostDTO dto)
			throws ServiceException {
		try {
			VodHostHome home = HomeLocater.getVodHostHome();
			VodHost bean = home.create(dto);
			dto.setHostID(bean.getHostID().intValue());
			loggerTool.logCreate("VOD�ӿ���������", dto.toString());
		} catch (HomeFactoryException e) {
			LogUtility.log(this.getClass(), LogLevel.ERROR, e);
			throw new ServiceException("����VOD�ӿ���������ʱ����" + e);
		} catch (CreateException e) {
			LogUtility.log(this.getClass(), LogLevel.ERROR, e);
			throw new ServiceException("����VOD�ӿ���������ʱ����");
		}

	}

	public void updateVODHostObject(VODInterfaceHostDTO dto)
			throws ServiceException {
		try {
			VodHostHome home = HomeLocater.getVodHostHome();
			VodHost bean = home.findByPrimaryKey(new Integer(dto.getHostID()));
			if (bean.ejbUpdate(dto) == -1) {
				throw new ServiceException("�޸�VOD�ӿ���������ʱ����,��ٲ���������Ƿ�����Ҫ��");
			}
			loggerTool.logUpdate("VOD�ӿ���������", dto.toString());
		} catch (HomeFactoryException e) {
			LogUtility.log(this.getClass(), LogLevel.ERROR, e);
			throw new ServiceException("�޸�VOD�ӿ���������ʱ����" + e);
		} catch (FinderException e) {
			LogUtility.log(this.getClass(), LogLevel.ERROR, e);
			throw new ServiceException("�޸�VOD�ӿ���������ʱ����");
		}
	}

	public void deleteVODHostObject(VODInterfaceHostDTO dto)
			throws ServiceException {
		try {
			VodHostHome home = HomeLocater.getVodHostHome();
			VodHost bean = home.findByPrimaryKey(new Integer(dto.getHostID()));
			bean.remove();
			loggerTool.logDelete("VOD�ӿ���������", dto.toString());
		} catch (HomeFactoryException e) {
			LogUtility.log(this.getClass(), LogLevel.ERROR, e);
			throw new ServiceException("ɾ��VOD�ӿ���������ʱ����" + e);
		} catch (FinderException e) {
			LogUtility.log(this.getClass(), LogLevel.ERROR, e);
			throw new ServiceException("ɾ��VOD�ӿ���������ʱ����");
		} catch (RemoveException e) {
			LogUtility.log(this.getClass(), LogLevel.ERROR, e);
			throw new ServiceException("ɾ��VOD�ӿ���������ʱ����");
		}
	}

	public void createVODProductObject(VODInterfaceProductDTO dto)
			throws ServiceException {
		try {
			VodProductHome home = HomeLocater.getVodProductHome();
		//	VodProduct remote=home.findByPrimaryKey(new Integer(dto.getSmsProductID()));
		//	if(remote!=null)
		//		throw new ServiceException("��SMS��Ʒ�Ѿ�����,�����´���" );
			if(BusinessUtility.getDtoBySmsProductID(dto.getSmsProductID())!=null)
				throw new ServiceException("��SMS��Ʒ�Ѿ�����,�����´���" );
			home.create(dto);
			loggerTool.logCreate("VOD�ӿڲ�Ʒ", dto.toString());
		} catch (HomeFactoryException e) {
			LogUtility.log(this.getClass(), LogLevel.ERROR, e);
			throw new ServiceException("����VOD�ӿڲ�Ʒ����ʱ����" );
		} catch (CreateException e) {
			LogUtility.log(this.getClass(), LogLevel.ERROR, e);
			throw new ServiceException("��SMS��Ʒ�Ѿ�����,�����´�����");
		} 

	}

	public void updateVODProductObject(VODInterfaceProductDTO dto)
			throws ServiceException {
		try {
			VodProductHome home = HomeLocater.getVodProductHome();
			VodProduct bean = home.findByPrimaryKey(new Integer(dto
					.getSmsProductID()));
			if (bean.ejbUpdate(dto) == -1) {
				throw new ServiceException("�޸�VOD�ӿڲ�Ʒ����ʱ����,��ٲ���������Ƿ�����Ҫ��");
			}
			loggerTool.logUpdate("VOD�ӿڲ�Ʒ", dto.toString());
		} catch (HomeFactoryException e) {
			LogUtility.log(this.getClass(), LogLevel.ERROR, e);
			throw new ServiceException("�޸�VOD�ӿڲ�Ʒ����ʱ����" );
		} catch (FinderException e) {
			LogUtility.log(this.getClass(), LogLevel.ERROR, e);
			throw new ServiceException("VOD�ӿڲ�Ʒ����ʱ����");
		}
	}

	public void deleteVODProductObject(VODInterfaceProductDTO dto)
			throws ServiceException {
		try {
			VodProductHome home = HomeLocater.getVodProductHome();
			VodProduct bean = home.findByPrimaryKey(new Integer(dto
					.getSmsProductID()));
			bean.remove();
			loggerTool.logDelete("VOD�ӿڲ�Ʒ", dto.toString());
		} catch (HomeFactoryException e) {
			LogUtility.log(this.getClass(), LogLevel.ERROR, e);
			throw new ServiceException("ɾ��VOD�ӿڲ�Ʒ����ʱ����" + e);
		} catch (FinderException e) {
			LogUtility.log(this.getClass(), LogLevel.ERROR, e);
			throw new ServiceException("ɾ��VOD�ӿڲ�Ʒ����ʱ����");
		} catch (RemoveException e) {
			LogUtility.log(this.getClass(), LogLevel.ERROR, e);
			throw new ServiceException("ɾ��VOD�ӿڲ�Ʒ����ʱ����");
		}
	}

}
