package com.dtv.oss.service.component;

import java.sql.Timestamp;

import javax.ejb.CreateException;
import javax.ejb.FinderException;

import com.dtv.oss.domain.FinancialSetting;
import com.dtv.oss.domain.FinancialSettingHome;
import com.dtv.oss.dto.FinancialSettingDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.ServiceException;
import com.dtv.oss.service.factory.HomeFactoryException;
import com.dtv.oss.service.util.HomeLocater;

public class FinancialConfigGeneralSettingService extends AbstractService {
	private SystemConfigModifyServiceLoggerTool loggerTool = SystemConfigModifyServiceLoggerTool
			.getInstance("�Ʒ�����", null, 0);

	// loggerTool.setRemoteHostAddress(remoteHostAddress);
	// loggerTool.setOperatorID(operatorID);

	public FinancialConfigGeneralSettingService(String remoteHostAddress,
			int operatorID) {
		loggerTool.setRemoteHostAddress(remoteHostAddress);
		loggerTool.setOperatorID(operatorID);
	}

	public void updateObject(FinancialSettingDTO dto) throws ServiceException {
		FinancialSettingHome home = null;
		try {
			 
			home = HomeLocater.getFinancialSettingHome();
			FinancialSetting beanInstance = home
					.findByPrimaryKey(dto.getName());
			if (beanInstance == null) {
				loggerTool.logCreate(dto.toString());
				dto.setDtCreate(new Timestamp(System.currentTimeMillis()));
				dto.setDtLastmod(new Timestamp(System.currentTimeMillis()));
				home.create(dto);
			} else {
				loggerTool.logUpdate(dto.toString());
				if (beanInstance.ejbUpdate(dto) == -1) {
					throw new ServiceException("�޸�ȫ������ʱ����,������������Ƿ�����Ҫ��");
				}
			}

		} catch (HomeFactoryException e) {
			LogUtility.log(this.getClass(), LogLevel.ERROR, e);
			throw new ServiceException("����ȫ������ʱ����" + e);
		} catch (FinderException e) {
			try {
				home.create(dto);
			} catch (CreateException ce) {
				LogUtility.log(this.getClass(), LogLevel.ERROR, e);
				LogUtility.log(this.getClass(), LogLevel.ERROR, ce);
			}
		} catch (CreateException e) {
			LogUtility.log(this.getClass(), LogLevel.ERROR, e);
			throw new ServiceException("����ȫ������ʱ����");
		}
	}
}
