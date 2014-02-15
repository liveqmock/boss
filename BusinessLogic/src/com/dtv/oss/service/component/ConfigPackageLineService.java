package com.dtv.oss.service.component;

import java.util.Collection;
import java.util.Iterator;

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.FinderException;
import javax.ejb.RemoveException;

import com.dtv.oss.domain.PackageLine;
import com.dtv.oss.domain.PackageLineHome;
import com.dtv.oss.domain.PackageLinePK;
import com.dtv.oss.domain.ProductDependency;
import com.dtv.oss.domain.ProductDependencyHome;
import com.dtv.oss.dto.PackageLineDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.ServiceContext;
import com.dtv.oss.service.ServiceException;
import com.dtv.oss.service.factory.HomeFactoryException;
import com.dtv.oss.service.util.CommonConstDefinition;
import com.dtv.oss.service.util.HomeLocater;
import com.dtv.oss.service.util.SystemLogRecorder;

public class ConfigPackageLineService extends AbstractService {

	ServiceContext context = null;

	private static Class clazz = ConfigPackageLineService.class;

	public ConfigPackageLineService(ServiceContext s) {
		this.context = s;
	}

	public void deleteProductForPackage(Collection col) throws ServiceException {

		if (col == null || col.size() == 0) {
			LogUtility.log(clazz, LogLevel.WARN, "传入的参数为空!");
			return;
		}
		try {
			Iterator iter = col.iterator();
			PackageLineHome plHome = HomeLocater.getPackageLineHome();
			while (iter.hasNext()) {
				PackageLineDTO dto = (PackageLineDTO) iter.next();
				PackageLinePK pk = new PackageLinePK(dto.getPackageId(), dto
						.getProductId());
				PackageLine packageLine = plHome.findByPrimaryKey(pk);
				packageLine.remove();
				/**
				SystemLogRecorder.createSystemLog(PublicService
						.getRemoteHostAddress(context), PublicService
						.getCurrentOperatorID(context), 0,
						SystemLogRecorder.LOGMODULE_CONFIG, "删除",
						"删除产品包PackageID为:" + dto.getPackageId(),

						SystemLogRecorder.LOGCLASS_NORMAL,
						SystemLogRecorder.LOGTYPE_APP);
						**/
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

	public void addProductForPackage(Collection col, String productStr)
			throws ServiceException {
		if (col == null || col.size() == 0) {
			LogUtility.log(clazz, LogLevel.WARN, "传入的参数为空!");
			return;
		}

		try {
			excludeCheck(productStr);
			Iterator iter = col.iterator();
			PackageLineHome plHome = HomeLocater.getPackageLineHome();
			while (iter.hasNext()) {
				PackageLineDTO dto = (PackageLineDTO) iter.next();
				PackageLine pl = plHome.create(dto);
				pl.setProductNum(1);
               /**
				SystemLogRecorder.createSystemLog(PublicService
						.getRemoteHostAddress(context), PublicService
						.getCurrentOperatorID(context), 0,
						SystemLogRecorder.LOGMODULE_CONFIG, "新增",
						"新增产品包中产品信息 PackageID为:" + dto.getPackageId(),

						SystemLogRecorder.LOGCLASS_NORMAL,
						SystemLogRecorder.LOGTYPE_APP);
						**/
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

	private void excludeCheck(String productStr) throws ServiceException {

		try {
			Collection col = null;
			ProductDependency pd = null;
			int productId = 0;
			ProductDependencyHome pdHome = HomeLocater
					.getProductDependencyHome();
			String referProduct = ";";
			String[] productCol = productStr.split(";");
			for (int i = 0; i < productCol.length; i++) {
				if(productCol[i]!=null ||productCol[i]!="") {      
			 	 productId = Integer.parseInt(productCol[i]);

				col = pdHome.findByProductIdAndType(productId,
						CommonConstDefinition.PRODUCTDEPENDENCYTYPE_C);
				}

				if (col != null && !col.isEmpty()) {
					Iterator pdIte = col.iterator();
					while (pdIte.hasNext()) {

						pd = (ProductDependency) pdIte.next();
/*
						referProduct = referProduct + pd.getReferProductId1()
								+ pd.getReferProductId2() +

								pd.getReferProductId3()
								+ pd.getReferProductId4()
								+ pd.getReferProductId5();
*/
						for (int j = i + 1; j < productCol.length; j++) {

							if (referProduct.indexOf(productCol[j]) != -1)

								throw new ServiceException("产品ID =" + productId
										+ " 跟产品ID 为 " + productCol[j] + "排斥");
						}

					}

				}
			}
		} catch (HomeFactoryException e) {
			LogUtility.log(clazz, LogLevel.WARN, "定位出错");
			throw new ServiceException("定位出错");
		} catch (FinderException e) {
			LogUtility.log(clazz, LogLevel.WARN, "查找出错");
			throw new ServiceException("查找出错");
		}

	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
