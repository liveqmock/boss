package com.dtv.oss.service.component;

import javax.ejb.CreateException;
import javax.ejb.FinderException;
import javax.ejb.RemoveException;

import com.dtv.oss.domain.BillingCycleType;
import com.dtv.oss.domain.BillingCycleTypeHome;
import com.dtv.oss.dto.BillingCycleTypeDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.ServiceException;
import com.dtv.oss.service.factory.HomeFactoryException;
import com.dtv.oss.service.util.HomeLocater;

public class FinancialConfigAccountCycleTypeService extends AbstractService {
	private SystemConfigModifyServiceLoggerTool loggerTool = SystemConfigModifyServiceLoggerTool
			.getInstance("������������", null, 0);

	// loggerTool.setRemoteHostAddress(remoteHostAddress);
	// loggerTool.setOperatorID(operatorID);

	public FinancialConfigAccountCycleTypeService(String remoteHostAddress,
			int operatorID) {
		loggerTool.setRemoteHostAddress(remoteHostAddress);
		loggerTool.setOperatorID(operatorID);
	}

	public void createObject(BillingCycleTypeDTO dto) throws ServiceException {
		if (!"I".equals(dto.getCType())) {
			throw new ServiceException("��Ҫ���µ����ݲ��Ǽ����������ݣ�");
		}
		try {
			System.out.println(getClass() + " crateObject;dto:" + dto);
			dto.setUnifiedDisctFlag("Y");
			dto.setUnifiedCycleFlag("Y");
			BillingCycleTypeHome home = HomeLocater.getBillingCycleTypeHome();
			BillingCycleType bean = home.create(dto);
			dto.setId(bean.getId().intValue());
			loggerTool.logCreate(dto.toString());
		} catch (HomeFactoryException e) {
			LogUtility.log(this.getClass(), LogLevel.ERROR, e);
			throw new ServiceException("������������ʱ����" + e);
		} catch (CreateException e) {
			LogUtility.log(this.getClass(), LogLevel.ERROR, e);
			throw new ServiceException("������������ʱ����");
		}

	}

	public void updateObject(BillingCycleTypeDTO dto) throws ServiceException {
		try {
			BillingCycleTypeHome home = HomeLocater.getBillingCycleTypeHome();
			BillingCycleType beanInstance = home.findByPrimaryKey(new Integer(
					dto.getId()));
			if (beanInstance.ejbUpdate(dto) == -1) {
				throw new ServiceException("�޸ļ�������ʱ����,��ٲ���������Ƿ�����Ҫ��");
			}
			loggerTool.logUpdate(dto.toString());
		} catch (HomeFactoryException e) {
			LogUtility.log(this.getClass(), LogLevel.ERROR, e);
			throw new ServiceException("�޸ļ�������ʱ����" + e);
		} catch (FinderException e) {
			LogUtility.log(this.getClass(), LogLevel.ERROR, e);
			throw new ServiceException("�޸ļ�������ʱ����");
		}
	}

	public void deleteObject(BillingCycleTypeDTO dto) throws ServiceException {
		try {
			BillingCycleTypeHome home = HomeLocater.getBillingCycleTypeHome();
			BillingCycleType beanInstance = home.findByPrimaryKey(new Integer(
					dto.getId()));
			beanInstance.remove();
			loggerTool.logDelete(dto.toString());
		} catch (HomeFactoryException e) {
			LogUtility.log(this.getClass(), LogLevel.ERROR, e);
			throw new ServiceException("ɾ����������ʱ����" + e);
		} catch (FinderException e) {
			LogUtility.log(this.getClass(), LogLevel.ERROR, e);
			throw new ServiceException("ɾ����������ʱ����");
		} catch (RemoveException e) {
			LogUtility.log(this.getClass(), LogLevel.ERROR, e);
			throw new ServiceException("ɾ����������ʱ����");
		}
	}

}
