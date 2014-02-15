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
			loggerTool.logCreate("VOD接口主机对象", dto.toString());
		} catch (HomeFactoryException e) {
			LogUtility.log(this.getClass(), LogLevel.ERROR, e);
			throw new ServiceException("创建VOD接口主机对象时出错。" + e);
		} catch (CreateException e) {
			LogUtility.log(this.getClass(), LogLevel.ERROR, e);
			throw new ServiceException("创建VOD接口主机对象时出错。");
		}

	}

	public void updateVODHostObject(VODInterfaceHostDTO dto)
			throws ServiceException {
		try {
			VodHostHome home = HomeLocater.getVodHostHome();
			VodHost bean = home.findByPrimaryKey(new Integer(dto.getHostID()));
			if (bean.ejbUpdate(dto) == -1) {
				throw new ServiceException("修改VOD接口主机对象时出错,请假查各项数据是否满足要求。");
			}
			loggerTool.logUpdate("VOD接口主机对象", dto.toString());
		} catch (HomeFactoryException e) {
			LogUtility.log(this.getClass(), LogLevel.ERROR, e);
			throw new ServiceException("修改VOD接口主机对象时出错。" + e);
		} catch (FinderException e) {
			LogUtility.log(this.getClass(), LogLevel.ERROR, e);
			throw new ServiceException("修改VOD接口主机对象时出错。");
		}
	}

	public void deleteVODHostObject(VODInterfaceHostDTO dto)
			throws ServiceException {
		try {
			VodHostHome home = HomeLocater.getVodHostHome();
			VodHost bean = home.findByPrimaryKey(new Integer(dto.getHostID()));
			bean.remove();
			loggerTool.logDelete("VOD接口主机对象", dto.toString());
		} catch (HomeFactoryException e) {
			LogUtility.log(this.getClass(), LogLevel.ERROR, e);
			throw new ServiceException("删除VOD接口主机对象时出错。" + e);
		} catch (FinderException e) {
			LogUtility.log(this.getClass(), LogLevel.ERROR, e);
			throw new ServiceException("删除VOD接口主机对象时出错。");
		} catch (RemoveException e) {
			LogUtility.log(this.getClass(), LogLevel.ERROR, e);
			throw new ServiceException("删除VOD接口主机对象时出错。");
		}
	}

	public void createVODProductObject(VODInterfaceProductDTO dto)
			throws ServiceException {
		try {
			VodProductHome home = HomeLocater.getVodProductHome();
		//	VodProduct remote=home.findByPrimaryKey(new Integer(dto.getSmsProductID()));
		//	if(remote!=null)
		//		throw new ServiceException("该SMS产品已经存在,请重新创建" );
			if(BusinessUtility.getDtoBySmsProductID(dto.getSmsProductID())!=null)
				throw new ServiceException("该SMS产品已经存在,请重新创建" );
			home.create(dto);
			loggerTool.logCreate("VOD接口产品", dto.toString());
		} catch (HomeFactoryException e) {
			LogUtility.log(this.getClass(), LogLevel.ERROR, e);
			throw new ServiceException("创建VOD接口产品对象时出错。" );
		} catch (CreateException e) {
			LogUtility.log(this.getClass(), LogLevel.ERROR, e);
			throw new ServiceException("该SMS产品已经存在,请重新创建。");
		} 

	}

	public void updateVODProductObject(VODInterfaceProductDTO dto)
			throws ServiceException {
		try {
			VodProductHome home = HomeLocater.getVodProductHome();
			VodProduct bean = home.findByPrimaryKey(new Integer(dto
					.getSmsProductID()));
			if (bean.ejbUpdate(dto) == -1) {
				throw new ServiceException("修改VOD接口产品对象时出错,请假查各项数据是否满足要求。");
			}
			loggerTool.logUpdate("VOD接口产品", dto.toString());
		} catch (HomeFactoryException e) {
			LogUtility.log(this.getClass(), LogLevel.ERROR, e);
			throw new ServiceException("修改VOD接口产品对象时出错。" );
		} catch (FinderException e) {
			LogUtility.log(this.getClass(), LogLevel.ERROR, e);
			throw new ServiceException("VOD接口产品对象时出错。");
		}
	}

	public void deleteVODProductObject(VODInterfaceProductDTO dto)
			throws ServiceException {
		try {
			VodProductHome home = HomeLocater.getVodProductHome();
			VodProduct bean = home.findByPrimaryKey(new Integer(dto
					.getSmsProductID()));
			bean.remove();
			loggerTool.logDelete("VOD接口产品", dto.toString());
		} catch (HomeFactoryException e) {
			LogUtility.log(this.getClass(), LogLevel.ERROR, e);
			throw new ServiceException("删除VOD接口产品对象时出错。" + e);
		} catch (FinderException e) {
			LogUtility.log(this.getClass(), LogLevel.ERROR, e);
			throw new ServiceException("删除VOD接口产品对象时出错。");
		} catch (RemoveException e) {
			LogUtility.log(this.getClass(), LogLevel.ERROR, e);
			throw new ServiceException("删除VOD接口产品对象时出错。");
		}
	}

}
