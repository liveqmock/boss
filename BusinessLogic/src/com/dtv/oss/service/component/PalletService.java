package com.dtv.oss.service.component;

import javax.ejb.CreateException;
import javax.ejb.FinderException;
import javax.ejb.RemoveException;

import com.dtv.oss.domain.Pallet;
import com.dtv.oss.domain.PalletHome;
import com.dtv.oss.dto.PalletDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.ServiceContext;
import com.dtv.oss.service.ServiceException;
import com.dtv.oss.service.factory.HomeFactoryException;
import com.dtv.oss.service.util.HomeLocater;

/**
 * 货架操作
 * 
 * @author 260327h
 * 
 */
public class PalletService extends AbstractService {
	private ServiceContext context = null;

	public PalletService() {
		super();
	}

	public PalletService(ServiceContext context) {
		super();
		this.context = context;
	}

	public void createPallet(PalletDTO dto) throws ServiceException {
		LogUtility.log(this.getClass(), LogLevel.DEBUG, "创建货架定义");
		try {
			PalletHome home = HomeLocater.getPalletHome();
			Pallet pallet=home.create(dto);
			dto.setDepotID(pallet.getPalletID().intValue());
		} catch (HomeFactoryException e1) {
			LogUtility.log(this.getClass(), LogLevel.ERROR, "创建货架定义定位出错！");
			throw new ServiceException("创建货架定义定位出错！");
		} catch (CreateException e3) {
			LogUtility.log(this.getClass(), LogLevel.ERROR, "货架定义创建出错！");
			throw new ServiceException("货架定义创建出错！");
		}

	}

	public void updatePallet(PalletDTO dto) throws ServiceException {
		LogUtility.log(this.getClass(), LogLevel.DEBUG, "货架更新");
		try {
			PalletHome home = HomeLocater.getPalletHome();
			Pallet pallet = home
					.findByPrimaryKey(new Integer(dto.getPalletID()));
			pallet.setPalletName(dto.getPalletName());
			pallet.setStatus(dto.getStatus());
			pallet.setMaxNumberAllowed(dto.getMaxNumberAllowed());
			pallet.setDepotID(dto.getDepotID());
			pallet.setPalletDesc(dto.getPalletDesc());
			pallet.setDtLastmod(dto.getDtLastmod());
		} catch (FinderException e) {
			LogUtility.log(this.getClass(), LogLevel.ERROR, "货架更新定位出错！");
			throw new ServiceException("货架更新定位出错");
		} catch (HomeFactoryException e1) {
			LogUtility.log(this.getClass(), LogLevel.ERROR, "货架更新服务出错！");
			throw new ServiceException("货架更新出错！");
		}

	}

	public void deletePallet(String pk) throws ServiceException {
		LogUtility.log(this.getClass(), LogLevel.DEBUG, "货架删除");
		try {
			PalletHome home = HomeLocater.getPalletHome();
			Pallet pallet = home.findByPrimaryKey(Integer.valueOf(pk));
			pallet.remove();
		} catch (FinderException e) {
			LogUtility.log(this.getClass(), LogLevel.ERROR, "货架删除定位出错！");
			throw new ServiceException("货架删除定位出错");
		} catch (HomeFactoryException e1) {
			LogUtility.log(this.getClass(), LogLevel.ERROR, "货架删除服务出错！");
			throw new ServiceException("货架删除服务出错！");
		} catch (RemoveException e2) {
			LogUtility.log(this.getClass(), LogLevel.ERROR, "货架删除出错！");
			throw new ServiceException("货架删除出错！");
		}

	}
}
