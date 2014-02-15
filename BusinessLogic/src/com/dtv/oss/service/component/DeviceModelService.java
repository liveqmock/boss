package com.dtv.oss.service.component;

import javax.ejb.CreateException;
import javax.ejb.FinderException;
import javax.ejb.RemoveException;

import com.dtv.oss.domain.CsiTypeReason2Device;
import com.dtv.oss.domain.CsiTypeReason2DeviceHome;
import com.dtv.oss.domain.DeviceModelHome;
import com.dtv.oss.domain.DeviceModel;
import com.dtv.oss.domain.SwapDeviceReason2Status;
import com.dtv.oss.domain.SwapDeviceReason2StatusHome;
import com.dtv.oss.dto.CsiTypeReason2DeviceDTO;
import com.dtv.oss.dto.DeviceModelDTO;
import com.dtv.oss.dto.SwapDeviceReason2StatusDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.ServiceContext;
import com.dtv.oss.service.ServiceException;
import com.dtv.oss.service.factory.HomeFactoryException;
import com.dtv.oss.service.util.HomeLocater;

/**
 * �豸�ͺŲ���
 * 
 * @author 260327h
 * 
 */
public class DeviceModelService extends AbstractService {
	private ServiceContext context = null;

	public DeviceModelService() {
		super();
	}

	public DeviceModelService(ServiceContext context) {
		super();
		this.context = context;
	}

	public void createDeviceModel(DeviceModelDTO dto) throws ServiceException {
		LogUtility.log(this.getClass(), LogLevel.DEBUG, "�����豸�ͺŶ���");
		try {
			DeviceModelHome home = HomeLocater.getDeviceModelHome();
			DeviceModel deviceModel=home.create(dto);
			dto.setDeviceModel(deviceModel.getDeviceModel());
		} catch (HomeFactoryException e1) {
			LogUtility.log(this.getClass(), LogLevel.ERROR, "�����豸�ͺŶ��嶨λ����");
			throw new ServiceException("�����豸�ͺŶ��嶨λ����");
		} catch (CreateException e3) {
			LogUtility.log(this.getClass(), LogLevel.ERROR, "�豸�ͺŶ��崴������");
			throw new ServiceException("�豸�ͺŶ��崴������");
		}

	}

	public void updateDeviceModel(DeviceModelDTO dto) throws ServiceException {
		LogUtility.log(this.getClass(), LogLevel.DEBUG, "�豸�ͺŸ���");
		try {
			DeviceModelHome home = HomeLocater.getDeviceModelHome();
			DeviceModel deviceModel = home.findByPrimaryKey(dto
					.getDeviceModel());
			if (deviceModel.ejbUpdate(dto) == -1) {
			   throw new ServiceException("�����豸���ͳ���");
			}

			 
		} catch (FinderException e) {
			LogUtility.log(this.getClass(), LogLevel.ERROR, "�豸�ͺŸ��¶�λ����");
			throw new ServiceException("�豸�ͺŸ��¶�λ����");
		} catch (HomeFactoryException e1) {
			LogUtility.log(this.getClass(), LogLevel.ERROR, "�豸�ͺŸ��·������");
			throw new ServiceException("�豸�ͺŸ��³���");
		}

	}

	public void deleteDeviceModel(String pk) throws ServiceException {
		try {
			DeviceModelHome home = HomeLocater.getDeviceModelHome();
			DeviceModel deviceModel = home.findByPrimaryKey(pk);
			deviceModel.remove();
		} catch (HomeFactoryException e) {
			LogUtility.log(this.getClass(), LogLevel.ERROR, "�豸�ͺ�ɾ���������");
			throw new ServiceException("�豸�ͺ�ɾ���������");
		} catch (FinderException e) {
			LogUtility.log(this.getClass(), LogLevel.ERROR, "�豸�ͺ�ɾ����λ����");
			throw new ServiceException("�豸�ͺ�ɾ����λ����");
		} catch (RemoveException e) {
			LogUtility.log(this.getClass(), LogLevel.ERROR, "�豸�ͺ�ɾ������");
			throw new ServiceException("�豸�ͺ�ɾ������");
		}
	}
	public void createDeviceReason(CsiTypeReason2DeviceDTO dto) throws ServiceException {
		LogUtility.log(this.getClass(), LogLevel.DEBUG, "�豸��;��¼����");
		try {
			CsiTypeReason2DeviceHome home = HomeLocater.getCsiTypeReason2DeviceHome();
			CsiTypeReason2Device remote=home.create(dto);
			context.put("SEQNO", remote.getSeqNo());
			 
		} catch (HomeFactoryException e1) {
			LogUtility.log(this.getClass(), LogLevel.ERROR, "�豸��;��¼��������");
			throw new ServiceException("�豸��;��¼��������");
		} catch (CreateException e3) {
			LogUtility.log(this.getClass(), LogLevel.ERROR, "�豸��;��¼��������");
			throw new ServiceException("�豸��;��¼��������");
		}

	}

	public void updateDeviceReason(CsiTypeReason2DeviceDTO dto) throws ServiceException {
		LogUtility.log(this.getClass(), LogLevel.DEBUG, "�豸�ͺŸ���");
		try {
			CsiTypeReason2DeviceHome home = HomeLocater.getCsiTypeReason2DeviceHome();
			CsiTypeReason2Device csiReason = home.findByPrimaryKey(new Integer(dto
					.getSeqNo()));
			if (csiReason.ejbUpdate(dto) == -1) {
			   throw new ServiceException("�����豸��;����");
			}

			 
		} catch (FinderException e) {
			LogUtility.log(this.getClass(), LogLevel.ERROR, "�豸��;���¶�λ����");
			throw new ServiceException("�豸��;���¶�λ����");
		} catch (HomeFactoryException e1) {
			LogUtility.log(this.getClass(), LogLevel.ERROR, "�豸��;���·������");
			throw new ServiceException("�豸��;���³���");
		}

	}
	public void createSwapDeviceStatus(SwapDeviceReason2StatusDTO dto) throws ServiceException {
		LogUtility.log(this.getClass(), LogLevel.DEBUG, "�豸����״̬����");
		try {
			SwapDeviceReason2StatusHome home = HomeLocater.getSwapDeviceReason2StatusHome();
			SwapDeviceReason2Status remote=home.create(dto);
			context.put("SEQNO", remote.getSeqNo());
			 
		} catch (HomeFactoryException e1) {
			LogUtility.log(this.getClass(), LogLevel.ERROR, "�豸����״̬��������");
			throw new ServiceException("�豸����״̬��������");
		} catch (CreateException e3) {
			LogUtility.log(this.getClass(), LogLevel.ERROR, "�豸����״̬��������");
			throw new ServiceException("�豸����״̬��������");
		}

	}

	public void updateSwapDeviceStatus(SwapDeviceReason2StatusDTO dto) throws ServiceException {
		LogUtility.log(this.getClass(), LogLevel.DEBUG, "�豸����״̬����");
		try {
			SwapDeviceReason2StatusHome home = HomeLocater.getSwapDeviceReason2StatusHome();
			SwapDeviceReason2Status swapStatus = home.findByPrimaryKey(new Integer(dto
					.getSeqNo()));
			if (swapStatus.ejbUpdate(dto) == -1) {
			   throw new ServiceException("�����豸����״̬����");
			}

			 
		} catch (FinderException e) {
			LogUtility.log(this.getClass(), LogLevel.ERROR, "�豸����״̬���¶�λ����");
			throw new ServiceException("�豸����״̬���¶�λ����");
		} catch (HomeFactoryException e1) {
			LogUtility.log(this.getClass(), LogLevel.ERROR, "�豸����״̬���·������");
			throw new ServiceException("�豸����״̬���·������");
		}

	}


}
