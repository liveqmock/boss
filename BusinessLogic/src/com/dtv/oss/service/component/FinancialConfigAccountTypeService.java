package com.dtv.oss.service.component;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.ejb.CreateException;
import javax.ejb.FinderException;
import javax.ejb.RemoveException;

import com.dtv.oss.domain.AcctItemType;
import com.dtv.oss.domain.AcctItemTypeHome;
import com.dtv.oss.dto.AcctItemTypeDTO;
import com.dtv.oss.dto.Bundle2CampaignDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.ServiceException;
import com.dtv.oss.service.factory.HomeFactoryException;
import com.dtv.oss.service.util.BusinessUtility;
import com.dtv.oss.service.util.HomeLocater;
import com.dtv.oss.util.DBUtil;

public class FinancialConfigAccountTypeService extends AbstractService {
	private SystemConfigModifyServiceLoggerTool loggerTool = SystemConfigModifyServiceLoggerTool
			.getInstance("帐目类型", null, 0);

	// loggerTool.setRemoteHostAddress(remoteHostAddress);
	// loggerTool.setOperatorID(operatorID);

	public FinancialConfigAccountTypeService(String remoteHostAddress,
			int operatorID) {
		loggerTool.setRemoteHostAddress(remoteHostAddress);
		loggerTool.setOperatorID(operatorID);
	}

	public void createObject(AcctItemTypeDTO dto) throws ServiceException {
		try {
			AcctItemTypeHome home = HomeLocater.getAcctItemTypeHome();
			AcctItemTypeDTO acctDto =	BusinessUtility.getAcctItemTypeDTOByAcctTypeID(dto.getAcctItemTypeID());
			 
			if(acctDto!=null){
				throw new ServiceException("该账目标识已经存在,请重新输入。" );
			}
			AcctItemType bean1 = home.create(dto);
			dto.setAcctItemTypeID(bean1.getAcctItemTypeID());
			loggerTool.logCreate(dto.toString());
		} catch (HomeFactoryException e) {
			LogUtility.log(this.getClass(), LogLevel.ERROR, e);
			throw new ServiceException("创建帐目类型时出错。" + e);
		} catch (CreateException e) {
			LogUtility.log(this.getClass(), LogLevel.ERROR, e);
			throw new ServiceException("创建帐目类型时出错。");
		}  

	}

	public void updateObject(AcctItemTypeDTO dto) throws ServiceException {
		try {
			AcctItemTypeHome home = HomeLocater.getAcctItemTypeHome();
			AcctItemType beanInstance = home.findByPrimaryKey(dto
					.getAcctItemTypeID());
			if (beanInstance.ejbUpdate(dto) == -1) {
				throw new ServiceException("修改帐目类型时出错,请假查各项数据是否满足要求。");
			}
			beanInstance.setSummaryTo(dto.getSummaryTo());
			 
			loggerTool.logUpdate(dto.toString());
		} catch (HomeFactoryException e) {
			LogUtility.log(this.getClass(), LogLevel.ERROR, e);
			throw new ServiceException("修改帐目类型时出错。" + e);
		} catch (FinderException e) {
			LogUtility.log(this.getClass(), LogLevel.ERROR, e);
			throw new ServiceException("修改帐目类型时出错。");
		}
	}

	public void deleteObject(AcctItemTypeDTO dto) throws ServiceException {
		try {
			checkBeforeDelete(dto);
			AcctItemTypeHome home = HomeLocater.getAcctItemTypeHome();
			AcctItemType beanInstance = home.findByPrimaryKey(dto
					.getAcctItemTypeID());
			beanInstance.remove();
			loggerTool.logDelete(dto.toString());
		} catch (HomeFactoryException e) {
			LogUtility.log(this.getClass(), LogLevel.ERROR, e);
			throw new ServiceException("删除帐目类型时出错。" + e);
		} catch (FinderException e) {
			LogUtility.log(this.getClass(), LogLevel.ERROR, e);
			throw new ServiceException("删除帐目类型时出错。");
		} catch (RemoveException e) {
			LogUtility.log(this.getClass(), LogLevel.ERROR, e);
			throw new ServiceException("删除帐目类型时出错。");
		}
	}

	private void checkBeforeDelete(AcctItemTypeDTO dto) throws ServiceException {
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			String sql1 = "select count(ACCTITEMTYPEID) from T_billingrule where ACCTITEMTYPEID='"
					+ dto.getAcctItemTypeID() + "'";
			String sql2 = "select count(ACCTITEMTYPEID) from T_BillingRuleItem where ACCTITEMTYPEID='"
					+ dto.getAcctItemTypeID() + "'";
			String sql3 = "select count(ACCTITEMTYPEID) from T_AccountItem where ACCTITEMTYPEID='"
					+ dto.getAcctItemTypeID() + "'";
			con = DBUtil.getConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql1);
			while (rs.next()) {
				if (rs.getInt(1) > 0) {
					throw new ServiceException("数据已被billingrule引用，不能删除。");
				}
			}
			rs.close();
			rs = stmt.executeQuery(sql2);
			while (rs.next()) {
				if (rs.getInt(1) > 0) {
					throw new ServiceException("数据已被BillingRuleItem引用，不能删除。");
				}
			}
			rs.close();
			rs = stmt.executeQuery(sql3);
			while (rs.next()) {
				if (rs.getInt(1) > 0) {
					throw new ServiceException("数据已被AccountItem引用，不能删除。");
				}
			}

		} catch (SQLException e) {
			throw new ServiceException(e);
		} finally {
			try {
				rs.close();
			} catch (Exception e) {
			}
			try {
				stmt.close();
			} catch (Exception e) {
			}
			try {
				con.close();
			} catch (Exception e) {
			}
		}
	}

}
