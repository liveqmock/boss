package com.dtv.oss.service.component;

import java.util.Collection;
import java.util.Iterator;

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.FinderException;
import javax.ejb.RemoveException;

import com.dtv.oss.domain.ProductToService;
import com.dtv.oss.domain.ProductToServiceHome;
import com.dtv.oss.domain.ProductToServicePK;
import com.dtv.oss.dto.ProductToServiceDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.ServiceContext;
import com.dtv.oss.service.ServiceException;
import com.dtv.oss.service.factory.HomeFactoryException;
import com.dtv.oss.service.util.HomeLocater;
import com.dtv.oss.service.util.SystemLogRecorder;

public class ConfigServiceToProductService extends AbstractService {

	ServiceContext context = null;

	private static Class clazz = ConfigPackageLineService.class;

	public ConfigServiceToProductService(ServiceContext s) {
		this.context = s;
	}

	public void deleteService(Collection col) throws ServiceException {

		if (col == null || col.size() == 0) {
			LogUtility.log(clazz, LogLevel.WARN, "传入的参数为空!");
			return;
		}
		try {
			Iterator iter = col.iterator();
			ProductToServiceHome psHome = HomeLocater.getProductToServiceHome();
			while (iter.hasNext()) {
				ProductToServiceDTO dto = (ProductToServiceDTO) iter.next();
				ProductToServicePK pk = new ProductToServicePK(dto.getProductId(), dto
						.getServiceId());
				ProductToService productToService = psHome.findByPrimaryKey(pk);
				productToService.remove();
				SystemLogRecorder.createSystemLog(PublicService
						.getRemoteHostAddress(context), PublicService
						.getCurrentOperatorID(context), 0,
						SystemLogRecorder.LOGMODULE_CONFIG, "删除产品对应业务关系",
						"产品ID:" + dto.getProductId()+"对应的业务"+dto.getServiceId()+"被删除",

						SystemLogRecorder.LOGCLASS_NORMAL,
						SystemLogRecorder.LOGTYPE_APP);
			}

		} catch (HomeFactoryException e) {
			LogUtility.log(clazz, LogLevel.WARN, "定位出错");
			throw new ServiceException("定位出错");
		} catch (FinderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (EJBException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (RemoveException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}

	public void addService(Collection col)
			throws ServiceException {
		if (col == null || col.size() == 0) {
			LogUtility.log(clazz, LogLevel.WARN, "传入的参数为空!");
			return;
		}

		try {
			 
			Iterator iter = col.iterator();
			ProductToServiceHome psHome = HomeLocater.getProductToServiceHome();
			while (iter.hasNext()) {
				ProductToServiceDTO dto = (ProductToServiceDTO) iter.next();
				ProductToService pl = psHome.create(dto);
				SystemLogRecorder.createSystemLog(PublicService
						.getRemoteHostAddress(context), PublicService
						.getCurrentOperatorID(context), 0,
						SystemLogRecorder.LOGMODULE_CONFIG, "新增产品对应业务关系",
						"产品ID:" + dto.getProductId()+"增加的业务ID为"+dto.getServiceId(),

						SystemLogRecorder.LOGCLASS_NORMAL,
						SystemLogRecorder.LOGTYPE_APP);
			}

		} catch (HomeFactoryException e) {
			LogUtility.log(clazz, LogLevel.WARN, "定位出错");
			throw new ServiceException("定位出错");
		} catch (CreateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (EJBException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}

	 
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
