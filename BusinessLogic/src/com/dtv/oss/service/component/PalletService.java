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
 * ���ܲ���
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
		LogUtility.log(this.getClass(), LogLevel.DEBUG, "�������ܶ���");
		try {
			PalletHome home = HomeLocater.getPalletHome();
			Pallet pallet=home.create(dto);
			dto.setDepotID(pallet.getPalletID().intValue());
		} catch (HomeFactoryException e1) {
			LogUtility.log(this.getClass(), LogLevel.ERROR, "�������ܶ��嶨λ����");
			throw new ServiceException("�������ܶ��嶨λ����");
		} catch (CreateException e3) {
			LogUtility.log(this.getClass(), LogLevel.ERROR, "���ܶ��崴������");
			throw new ServiceException("���ܶ��崴������");
		}

	}

	public void updatePallet(PalletDTO dto) throws ServiceException {
		LogUtility.log(this.getClass(), LogLevel.DEBUG, "���ܸ���");
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
			LogUtility.log(this.getClass(), LogLevel.ERROR, "���ܸ��¶�λ����");
			throw new ServiceException("���ܸ��¶�λ����");
		} catch (HomeFactoryException e1) {
			LogUtility.log(this.getClass(), LogLevel.ERROR, "���ܸ��·������");
			throw new ServiceException("���ܸ��³���");
		}

	}

	public void deletePallet(String pk) throws ServiceException {
		LogUtility.log(this.getClass(), LogLevel.DEBUG, "����ɾ��");
		try {
			PalletHome home = HomeLocater.getPalletHome();
			Pallet pallet = home.findByPrimaryKey(Integer.valueOf(pk));
			pallet.remove();
		} catch (FinderException e) {
			LogUtility.log(this.getClass(), LogLevel.ERROR, "����ɾ����λ����");
			throw new ServiceException("����ɾ����λ����");
		} catch (HomeFactoryException e1) {
			LogUtility.log(this.getClass(), LogLevel.ERROR, "����ɾ���������");
			throw new ServiceException("����ɾ���������");
		} catch (RemoveException e2) {
			LogUtility.log(this.getClass(), LogLevel.ERROR, "����ɾ������");
			throw new ServiceException("����ɾ������");
		}

	}
}
