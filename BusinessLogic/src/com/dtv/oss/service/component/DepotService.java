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
		LogUtility.log(this.getClass(), LogLevel.DEBUG, "创建仓库定义");
		try {
			DepotHome home = HomeLocater.getDepotHome();
			Depot depot=home.create(dto);
			dto.setDepotID(depot.getDepotID().intValue());
		} catch (HomeFactoryException e1) {
			LogUtility.log(this.getClass(), LogLevel.ERROR, "创建仓库定义定位出错！");
			throw new ServiceException("创建仓库定义定位出错！");
		} catch (CreateException e3) {
			LogUtility.log(this.getClass(), LogLevel.ERROR, "仓库定义创建出错！");
			throw new ServiceException("仓库定义创建出错！");
		}

	}
	public void updateDepot(DepotDTO dto) throws ServiceException {
		LogUtility.log(this.getClass(), LogLevel.DEBUG, "仓库更新");
		try {
			DepotHome home = HomeLocater.getDepotHome();
			Depot depot=home.findByPrimaryKey(new Integer(dto.getDepotID()));
			depot.setDepotName(dto.getDepotName());
			depot.setDetailAddress(dto.getDetailAddress());
			depot.setDtLastmod(dto.getDtLastmod());
			depot.setOwnerID(dto.getOwnerID());
			depot.setStatus(dto.getStatus());
		}catch (FinderException e){
			LogUtility.log(this.getClass(), LogLevel.ERROR, "仓库更新定位出错！");
			throw new ServiceException("仓库更新定位出错");
		} catch (HomeFactoryException e1) {
			LogUtility.log(this.getClass(), LogLevel.ERROR, "仓库更新服务出错！");
			throw new ServiceException("仓库更新出错！");
		}

	}
	
	public void deleteDepot(String pk) throws ServiceException{
		LogUtility.log(this.getClass(),LogLevel.DEBUG,"仓库删除");
		try {
			DepotHome home = HomeLocater.getDepotHome();
			Depot depot=home.findByPrimaryKey(Integer.valueOf(pk));
			depot.remove();
		} catch (NumberFormatException e) {
			LogUtility.log(this.getClass(), LogLevel.ERROR, "仓库删除标志出错！");
			throw new ServiceException("仓库删除标志出错");
		} catch (EJBException e) {
			LogUtility.log(this.getClass(), LogLevel.ERROR, "仓库删除服务出错！");
			throw new ServiceException("仓库删除服务出错");
		} catch (HomeFactoryException e) {
			LogUtility.log(this.getClass(), LogLevel.ERROR, "仓库删除服务出错！");
			throw new ServiceException("仓库删除服务出错");
		} catch (FinderException e) {
			LogUtility.log(this.getClass(), LogLevel.ERROR, "仓库删除定位出错！");
			throw new ServiceException("仓库删除定位出错");
		} catch (RemoveException e) {
			LogUtility.log(this.getClass(), LogLevel.ERROR, "仓库删除出错！");
			throw new ServiceException("仓库删除出错");
		}
	}
}
