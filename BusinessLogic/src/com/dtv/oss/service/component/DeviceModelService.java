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
 * 设备型号操作
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
		LogUtility.log(this.getClass(), LogLevel.DEBUG, "创建设备型号定义");
		try {
			DeviceModelHome home = HomeLocater.getDeviceModelHome();
			DeviceModel deviceModel=home.create(dto);
			dto.setDeviceModel(deviceModel.getDeviceModel());
		} catch (HomeFactoryException e1) {
			LogUtility.log(this.getClass(), LogLevel.ERROR, "创建设备型号定义定位出错！");
			throw new ServiceException("创建设备型号定义定位出错！");
		} catch (CreateException e3) {
			LogUtility.log(this.getClass(), LogLevel.ERROR, "设备型号定义创建出错！");
			throw new ServiceException("设备型号定义创建出错！");
		}

	}

	public void updateDeviceModel(DeviceModelDTO dto) throws ServiceException {
		LogUtility.log(this.getClass(), LogLevel.DEBUG, "设备型号更新");
		try {
			DeviceModelHome home = HomeLocater.getDeviceModelHome();
			DeviceModel deviceModel = home.findByPrimaryKey(dto
					.getDeviceModel());
			if (deviceModel.ejbUpdate(dto) == -1) {
			   throw new ServiceException("更新设备类型出错！");
			}

			 
		} catch (FinderException e) {
			LogUtility.log(this.getClass(), LogLevel.ERROR, "设备型号更新定位出错！");
			throw new ServiceException("设备型号更新定位出错");
		} catch (HomeFactoryException e1) {
			LogUtility.log(this.getClass(), LogLevel.ERROR, "设备型号更新服务出错！");
			throw new ServiceException("设备型号更新出错！");
		}

	}

	public void deleteDeviceModel(String pk) throws ServiceException {
		try {
			DeviceModelHome home = HomeLocater.getDeviceModelHome();
			DeviceModel deviceModel = home.findByPrimaryKey(pk);
			deviceModel.remove();
		} catch (HomeFactoryException e) {
			LogUtility.log(this.getClass(), LogLevel.ERROR, "设备型号删除服务出错！");
			throw new ServiceException("设备型号删除服务出错");
		} catch (FinderException e) {
			LogUtility.log(this.getClass(), LogLevel.ERROR, "设备型号删除定位出错！");
			throw new ServiceException("设备型号删除定位出错");
		} catch (RemoveException e) {
			LogUtility.log(this.getClass(), LogLevel.ERROR, "设备型号删除出错！");
			throw new ServiceException("设备型号删除出错");
		}
	}
	public void createDeviceReason(CsiTypeReason2DeviceDTO dto) throws ServiceException {
		LogUtility.log(this.getClass(), LogLevel.DEBUG, "设备用途记录创建");
		try {
			CsiTypeReason2DeviceHome home = HomeLocater.getCsiTypeReason2DeviceHome();
			CsiTypeReason2Device remote=home.create(dto);
			context.put("SEQNO", remote.getSeqNo());
			 
		} catch (HomeFactoryException e1) {
			LogUtility.log(this.getClass(), LogLevel.ERROR, "设备用途记录创建出错！");
			throw new ServiceException("设备用途记录创建出错！");
		} catch (CreateException e3) {
			LogUtility.log(this.getClass(), LogLevel.ERROR, "设备用途记录创建出错！");
			throw new ServiceException("设备用途记录创建出错！");
		}

	}

	public void updateDeviceReason(CsiTypeReason2DeviceDTO dto) throws ServiceException {
		LogUtility.log(this.getClass(), LogLevel.DEBUG, "设备型号更新");
		try {
			CsiTypeReason2DeviceHome home = HomeLocater.getCsiTypeReason2DeviceHome();
			CsiTypeReason2Device csiReason = home.findByPrimaryKey(new Integer(dto
					.getSeqNo()));
			if (csiReason.ejbUpdate(dto) == -1) {
			   throw new ServiceException("更新设备用途出错！");
			}

			 
		} catch (FinderException e) {
			LogUtility.log(this.getClass(), LogLevel.ERROR, "设备用途更新定位出错！");
			throw new ServiceException("设备用途更新定位出错");
		} catch (HomeFactoryException e1) {
			LogUtility.log(this.getClass(), LogLevel.ERROR, "设备用途更新服务出错！");
			throw new ServiceException("设备用途更新出错！");
		}

	}
	public void createSwapDeviceStatus(SwapDeviceReason2StatusDTO dto) throws ServiceException {
		LogUtility.log(this.getClass(), LogLevel.DEBUG, "设备更换状态创建");
		try {
			SwapDeviceReason2StatusHome home = HomeLocater.getSwapDeviceReason2StatusHome();
			SwapDeviceReason2Status remote=home.create(dto);
			context.put("SEQNO", remote.getSeqNo());
			 
		} catch (HomeFactoryException e1) {
			LogUtility.log(this.getClass(), LogLevel.ERROR, "设备更换状态创建出错！");
			throw new ServiceException("设备更换状态创建出错！");
		} catch (CreateException e3) {
			LogUtility.log(this.getClass(), LogLevel.ERROR, "设备更换状态创建出错！");
			throw new ServiceException("设备更换状态创建出错！");
		}

	}

	public void updateSwapDeviceStatus(SwapDeviceReason2StatusDTO dto) throws ServiceException {
		LogUtility.log(this.getClass(), LogLevel.DEBUG, "设备更换状态更新");
		try {
			SwapDeviceReason2StatusHome home = HomeLocater.getSwapDeviceReason2StatusHome();
			SwapDeviceReason2Status swapStatus = home.findByPrimaryKey(new Integer(dto
					.getSeqNo()));
			if (swapStatus.ejbUpdate(dto) == -1) {
			   throw new ServiceException("更新设备更换状态出错！");
			}

			 
		} catch (FinderException e) {
			LogUtility.log(this.getClass(), LogLevel.ERROR, "设备更换状态更新定位出错！");
			throw new ServiceException("设备更换状态更新定位出错");
		} catch (HomeFactoryException e1) {
			LogUtility.log(this.getClass(), LogLevel.ERROR, "设备更换状态更新服务出错！");
			throw new ServiceException("设备更换状态更新服务出错！");
		}

	}


}
