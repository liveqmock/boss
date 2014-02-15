package com.dtv.oss.service.component;

import javax.ejb.CreateException;
import javax.ejb.FinderException;
import javax.ejb.RemoveException;

import com.dtv.oss.domain.CustAcctCycleCfg;
import com.dtv.oss.domain.CustAcctCycleCfgHome;
import com.dtv.oss.dto.CustAcctCycleCfgDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.ServiceException;
import com.dtv.oss.service.factory.HomeFactoryException;
import com.dtv.oss.service.util.HomeLocater;

public class FinancialConfigCustomerAccountCycleService extends AbstractService {
	private SystemConfigModifyServiceLoggerTool loggerTool = SystemConfigModifyServiceLoggerTool
			.getInstance("�ͻ�������������", null, 0);

	// loggerTool.setRemoteHostAddress(remoteHostAddress);
	// loggerTool.setOperatorID(operatorID);

	public FinancialConfigCustomerAccountCycleService(String remoteHostAddress,
			int operatorID) {
		loggerTool.setRemoteHostAddress(remoteHostAddress);
		loggerTool.setOperatorID(operatorID);
	}

	public void createObject(CustAcctCycleCfgDTO dto) throws ServiceException {
		try {
			CustAcctCycleCfgHome home = HomeLocater.getCustAcctCycleCfgHome();
			CustAcctCycleCfg bean = home.create(dto);
			dto.setSeqNo(bean.getSeqNo().intValue());
			loggerTool.logCreate(dto.toString());
		} catch (HomeFactoryException e) {
			LogUtility.log(this.getClass(), LogLevel.ERROR, e);
			throw new ServiceException("�����ͻ���������ʱ����" + e);
		} catch (CreateException e) {
			LogUtility.log(this.getClass(), LogLevel.ERROR, e);
			throw new ServiceException("�����ͻ���������ʱ����");
		}

	}

	public void updateObject(CustAcctCycleCfgDTO dto) throws ServiceException {
		try {
			CustAcctCycleCfgHome home = HomeLocater.getCustAcctCycleCfgHome();
			CustAcctCycleCfg beanInstance = home.findByPrimaryKey(new Integer(
					dto.getSeqNo()));
			if (beanInstance.ejbUpdate(dto) == -1) {
				throw new ServiceException("�޸Ŀͻ���������ʱ����,��ٲ���������Ƿ�����Ҫ��");
			}
			loggerTool.logUpdate(dto.toString());
		} catch (HomeFactoryException e) {
			LogUtility.log(this.getClass(), LogLevel.ERROR, e);
			throw new ServiceException("�޸Ŀͻ���������ʱ����" + e);
		} catch (FinderException e) {
			LogUtility.log(this.getClass(), LogLevel.ERROR, e);
			throw new ServiceException("�޸Ŀͻ���������ʱ����");
		}
	}

	public void deleteObject(CustAcctCycleCfgDTO dto) throws ServiceException {
		try {
			CustAcctCycleCfgHome home = HomeLocater.getCustAcctCycleCfgHome();
			CustAcctCycleCfg beanInstance = home.findByPrimaryKey(new Integer(
					dto.getSeqNo()));
			beanInstance.remove();
			loggerTool.logDelete(dto.toString());
		} catch (HomeFactoryException e) {
			LogUtility.log(this.getClass(), LogLevel.ERROR, e);
			throw new ServiceException("ɾ���ͻ���������ʱ����" + e);
		} catch (FinderException e) {
			LogUtility.log(this.getClass(), LogLevel.ERROR, e);
			throw new ServiceException("ɾ���ͻ���������ʱ����");
		} catch (RemoveException e) {
			LogUtility.log(this.getClass(), LogLevel.ERROR, e);
			throw new ServiceException("ɾ���ͻ���������ʱ����");
		}
	}

}
