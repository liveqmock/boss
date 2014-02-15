package com.dtv.oss.service.component;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.ejb.CreateException;
import javax.ejb.FinderException;
import javax.ejb.RemoveException;

import com.dtv.oss.domain.CommonSettingData;
import com.dtv.oss.domain.CommonSettingDataHome;
import com.dtv.oss.domain.CommonSettingDataPK;
import com.dtv.oss.domain.CsiActionReasonDetail;
import com.dtv.oss.domain.CsiActionReasonDetailHome;
import com.dtv.oss.domain.CsiActionReasonSetting;
import com.dtv.oss.domain.CsiActionReasonSettingHome;
import com.dtv.oss.dto.CommonSettingDataDTO;
import com.dtv.oss.dto.CsiActionReasonDetailDTO;
import com.dtv.oss.dto.CsiActionReasonSettingDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.ServiceContext;
import com.dtv.oss.service.ServiceException;
import com.dtv.oss.service.command.config.ConfigCustomerModifyCommand;
import com.dtv.oss.service.factory.HomeFactoryException;
import com.dtv.oss.service.util.HomeLocater;
import com.dtv.oss.util.DBUtil;
import com.dtv.oss.service.util.BusinessUtility;

public class ConfigCustomerModifyService extends AbstractService {

	ServiceContext context = null;

	public ConfigCustomerModifyService(ServiceContext s) {
		this.context = s;
	}

	private static Class clazz = ConfigCustomerModifyCommand.class;

	// private SystemConfigModifyServiceLoggerTool loggerTool =
	// SystemConfigModifyServiceLoggerTool.getInstance(null, hostAdd,
	// operatorID.intValue());

	public void createOneObject(CommonSettingDataDTO dto)
			throws ServiceException {

		// LogUtility.log(this.getClass(), LogLevel.DEBUG,
		// "create config of customer in common setting data");
		try {
			CommonSettingDataHome home = HomeLocater.getCommonSettingDataHome();
			checkExist(dto);
			home.create(dto);
			// loggerTool.logCreate("公共数据", dto.toString());
		} catch (HomeFactoryException e1) {
			LogUtility
					.log(this.getClass(), LogLevel.ERROR,
							"error when create config of customer in common setting data");
			throw new ServiceException(
					"error when create config of customer in common setting data.");
		} catch (CreateException e3) {
			LogUtility
					.log(this.getClass(), LogLevel.ERROR,
							"error when create config of customer in common setting data");
			throw new ServiceException(
					"error when create config of customer in common setting data.");
		}

	}

	/**
	 * @param dto
	 * @throws ServiceException
	 */
	private void checkExist(CommonSettingDataDTO dto) throws ServiceException {

		try {
			CommonSettingDataHome home;

			home = HomeLocater.getCommonSettingDataHome();

			CommonSettingDataPK pk = new CommonSettingDataPK(dto.getName(), dto
					.getKey());
			CommonSettingData bean = home.findByPrimaryKey(pk);

			if (bean != null) {
				throw new ServiceException("该键值已经存在,不能重复创建");
			}
		} catch (FinderException fe) {

			fe.printStackTrace();
		} catch (HomeFactoryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void updateObject(CommonSettingDataDTO dto) throws ServiceException {

		LogUtility.log(this.getClass(), LogLevel.DEBUG,
				"update config of customer in common setting data");
		try {
			CommonSettingDataHome home = HomeLocater.getCommonSettingDataHome();
			CommonSettingDataPK pk = new CommonSettingDataPK(dto.getName(), dto
					.getKey());
			CommonSettingData bean = home.findByPrimaryKey(pk);
			checkDefaultOrNot(dto);   //检查“是否默认值”，有没有冲突
			if (bean.ejbUpdate(dto) == -1) {
				LogUtility.log(clazz, LogLevel.WARN, "更新抵扣券类型出错。");
				throw new ServiceException("更新抵扣券类型出错！");
			}
			// loggerTool.logUpdate("公共数据" , dto.toString());
			// home.remove(bean);
			// home.create(dto);
		} catch (HomeFactoryException e1) {
			LogUtility.log(this.getClass(), LogLevel.ERROR,
					"error when update config of customer in common setting data"
							+ e1);
			throw new ServiceException(
					"error when update config of customer in common setting data."
							+ e1);
		} catch (FinderException fe) {

			LogUtility.log(this.getClass(), LogLevel.ERROR,
					"error when update config of customer in common setting data"
							+ fe);
			throw new ServiceException(
					"error when update config of customer in common setting data."
							+ fe);
		}

	}
	
	/**
	 * 检查默认值是否冲突
	 * 
	 * @param key
	 * @param name
	 * @throws ServiceException
	 */
	private void checkDefaultOrNot(CommonSettingDataDTO dto) throws ServiceException {
		try {
			CommonSettingDataHome home;
			home = HomeLocater.getCommonSettingDataHome();
			
			String strDefaultOrNot = "";
			if(dto.getDefaultOrNot() != null)
				strDefaultOrNot = dto.getDefaultOrNot();
			if("Y".equals(strDefaultOrNot)){
			if(BusinessUtility.IsSetDefault(dto.getName())){
				throw new ServiceException("默认值已设置");
			}
			}
		} catch (HomeFactoryException fe) {

			fe.printStackTrace();
		}
	}
	
	/**
	 * 更改默认值
	 * 
	 * @param key
	 * @param name
	 * @throws ServiceException
	 */
	public void changeDefaultValue(String key, String name, String defaultvalue)
			throws ServiceException {

		LogUtility.log(this.getClass(), LogLevel.DEBUG,
				"changeDefaultValue update config of customer in common setting data");
		try {
			CommonSettingDataHome home = HomeLocater.getCommonSettingDataHome();
			CommonSettingDataPK pk = new CommonSettingDataPK(name, key);
			CommonSettingData bean = home.findByPrimaryKey(pk);
			bean.setDefaultOrNot(defaultvalue);

		} catch (HomeFactoryException e1) {
			throw new ServiceException("changeDefaultValue" + e1);
		} catch (FinderException fe) {

			LogUtility.log(this.getClass(), LogLevel.ERROR,
					"changeDefaultValue" + fe);
			throw new ServiceException("changeDefaultValue" + fe);
		}

	}

	public void deleteObject(CommonSettingDataDTO dto) throws ServiceException {

		LogUtility.log(this.getClass(), LogLevel.DEBUG,
				"delete config of customer in common setting data");
		if (beUsed(dto)) {
			throw new ServiceException("该公用数据已被使用，不能删除！");
		}

		try {
			CommonSettingDataHome home = HomeLocater.getCommonSettingDataHome();
			CommonSettingDataPK pk = new CommonSettingDataPK(dto.getName(), dto
					.getKey());
			CommonSettingData bean = home.findByPrimaryKey(pk);
			bean.remove();
			// loggerTool.logCreate("公共数据", dto.toString());
		} catch (HomeFactoryException e1) {
			LogUtility.log(this.getClass(), LogLevel.ERROR,
					"error when update config of customer in common setting data"
							+ e1);
			throw new ServiceException(
					"error when update config of customer in common setting data."
							+ e1);
		} catch (FinderException fe) {

			LogUtility.log(this.getClass(), LogLevel.ERROR,
					"error when update config of customer in common setting data"
							+ fe);
			throw new ServiceException(
					"error when update config of customer in common setting data."
							+ fe);
		} catch (RemoveException e3) {
			LogUtility.log(this.getClass(), LogLevel.ERROR,
					"error when update config of customer in common setting data"
							+ e3);
			throw new ServiceException(
					"error when update config of customer in common setting data."
							+ e3);
		}

	}

	private boolean beUsed(CommonSettingDataDTO dto) {
		// SET_F_CURRENCY SET_C_CUSTOMERCARDTYPE SET_C_CUSTOMERTYPE
		// SET_C_CUSTOMERTITLE SET_C_NATIONALITY
		System.out.println();
		String name = dto.getName();
		if (name == null || (name = name.trim()).length() == 0) {
			return false;
		}
		// T_Customer.CustomerID Title CustomerType CardType
		// t_account.AccountID Currency
		String checkSQL = null;
		if ("SET_F_CURRENCY".equals(name)) {
			checkSQL = "select count(AccountID) from t_account where Currency='"
					+ dto.getKey() + "'";
		} else if ("SET_C_CUSTOMERCARDTYPE".equals(name)) {
			checkSQL = "select count(CustomerID) from T_Customer where CardType='"
					+ dto.getKey() + "'";
		} else if ("SET_C_CUSTOMERTYPE".equals(name)) {
			checkSQL = "select count(CustomerID) from T_Customer where CustomerType='"
					+ dto.getKey() + "'";
		} else if ("SET_C_CUSTOMERTITLE".equals(name)) {
			checkSQL = "select count(CustomerID) from T_Customer where Title='"
					+ dto.getKey() + "'";
		} else if ("SET_C_NATIONALITY".equals(name)) {
			checkSQL = "select count(CustomerID) from T_Customer where NATIONALITY='"
					+ dto.getKey() + "'";
		}
		if (checkSQL != null) {
			Connection con = null;
			Statement stmt = null;
			ResultSet rs = null;
			try {
				con = DBUtil.getConnection();
				stmt = con.createStatement();
				rs = stmt.executeQuery(checkSQL);
				if (rs.next()) {
					int count = rs.getInt(1);
					if (count > 0) {
						return true;
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					if (rs != null) {
						rs.close();
					}
				} catch (Exception e) {
				}
				try {
					if (stmt != null) {
						stmt.close();
					}
				} catch (Exception e) {
				}
				try {
					if (con != null) {
						con.close();
					}
				} catch (Exception e) {
				}
			}
		}

		return false;
	}

	public void createCsiReason(CsiActionReasonSettingDTO dto)
			throws ServiceException {

		try {
			CsiActionReasonSettingHome home = HomeLocater
					.getCsiActionReasonSettingHome();

			home.create(dto);
			 
		} catch (HomeFactoryException e1) {
			 
			throw new ServiceException(
					"error when create config of csi action reason .");
		} catch (CreateException e3) {
			 
			throw new ServiceException(
					"error when create config of csi action reason");
		}

	}
	public void updateCsiReason(CsiActionReasonSettingDTO dto)
	        throws ServiceException {

      try {
	      CsiActionReasonSettingHome home = HomeLocater
			.getCsiActionReasonSettingHome();
	      CsiActionReasonSetting  csiReason = home.findByPrimaryKey(new Integer(dto.getSeqNo()));
	      if (csiReason.ejbUpdate(dto) == -1) {
				LogUtility.log(clazz, LogLevel.WARN, "更新受理单类型原因配置出错。");
				throw new ServiceException("更新受理单类型原因配置出错！");
			}
	 
} catch (HomeFactoryException e1) {
	 
	throw new ServiceException(
			"error when update config of csi action reason .");
} catch (FinderException fe) {

	LogUtility.log(this.getClass(), LogLevel.ERROR,
			"该对象不存在" + fe);
	throw new ServiceException("该对象不存在" + fe);
}

}
	public void createCsiReasonDetail(CsiActionReasonDetailDTO dto)
	  throws ServiceException {

    try {
	CsiActionReasonDetailHome home = HomeLocater
			.getCsiActionReasonDetailHome();

	home.create(dto);
	 
} catch (HomeFactoryException e1) {
	 
	throw new ServiceException(
			"error when create config of csi action reason .");
} catch (CreateException e3) {
	 
	throw new ServiceException(
			"error when create config of csi action reason");
}

}
public void updateCsiReasonDetail(CsiActionReasonDetailDTO dto)
    throws ServiceException {

try {
	CsiActionReasonDetailHome home = HomeLocater
	.getCsiActionReasonDetailHome();
	CsiActionReasonDetail  csiReasonDetail = home.findByPrimaryKey(new Integer(dto.getSeqNo()));
	csiReasonDetail.setKey(dto.getKey());
	csiReasonDetail.setDefaultValueFlag(dto.getDefaultValueFlag());
	csiReasonDetail.setPriority(dto.getPriority());
	csiReasonDetail.setValue(dto.getValue());
	csiReasonDetail.setStatus(dto.getStatus());
	 
	

} catch (HomeFactoryException e1) {

throw new ServiceException(
	"error when update config of csi action reason .");
} catch (FinderException fe) {

LogUtility.log(this.getClass(), LogLevel.ERROR,
	"该对象不存在" + fe);
throw new ServiceException("该对象不存在" + fe);
}

}

}
