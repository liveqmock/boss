package com.dtv.oss.service.component;

import javax.ejb.CreateException;
import javax.ejb.FinderException;
import javax.ejb.RemoveException;

import com.dtv.oss.domain.BillBoard;
import com.dtv.oss.domain.BillBoardHome;
import com.dtv.oss.dto.BillBoardDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.ServiceContext;
import com.dtv.oss.service.ServiceException;
import com.dtv.oss.service.factory.HomeFactoryException;
import com.dtv.oss.service.util.HomeLocater;

/**
 * ������Ϣ����
 * 
 * @author chenjiang
 * 
 */
public class BoardService extends AbstractService {
	private ServiceContext context = null;
	private static Class clazz=BoardService.class;
	public BoardService() {
		super();
	}

	public BoardService(ServiceContext context) {
		super();
		this.context = context;
	}

	public void createBoard(BillBoardDTO dto) throws ServiceException {
		LogUtility.log(this.getClass(), LogLevel.DEBUG, "����������Ϣ");
		try {
			BillBoardHome home = HomeLocater.getBillBoardHome();
			BillBoard pallet=home.create(dto);
			 
		} catch (HomeFactoryException e1) {
			LogUtility.log(this.getClass(), LogLevel.ERROR, "����������Ϣ����");
			throw new ServiceException("����������Ϣ����");
		} catch (CreateException e3) {
			LogUtility.log(this.getClass(), LogLevel.ERROR, "����������Ϣ����");
			throw new ServiceException("����������Ϣ����");
		}

	}

	public void updateBoard(BillBoardDTO dto) throws ServiceException {
		LogUtility.log(this.getClass(), LogLevel.DEBUG, "������Ϣ����");
		try {
			BillBoardHome home = HomeLocater.getBillBoardHome();
			BillBoard billBoard = home
					.findByPrimaryKey(new Integer(dto.getSeqNo()));
			if(billBoard.ejbUpdate(dto)==-1){
				LogUtility.log(clazz,LogLevel.WARN,"�ϴ��޸�ʱ��û������!");
				throw new ServiceException("�ϴ��޸�ʱ��û������");
			}
			 
		} catch (FinderException e) {
			LogUtility.log(this.getClass(), LogLevel.ERROR, "������Ϣ���³���");
			throw new ServiceException("������Ϣ���³���");
		} catch (HomeFactoryException e1) {
			LogUtility.log(this.getClass(), LogLevel.ERROR, "������Ϣ���³���");
			throw new ServiceException("������Ϣ���³���");
		}

	}

	public void deleteBoard(BillBoardDTO dto) throws ServiceException {
		LogUtility.log(this.getClass(), LogLevel.DEBUG, "������Ϣɾ��");
		try {
			BillBoardHome home = HomeLocater.getBillBoardHome();
			BillBoard billBoard = home.findByPrimaryKey(new Integer(dto.getSeqNo()));
			billBoard.remove();
		} catch (FinderException e) {
			LogUtility.log(this.getClass(), LogLevel.ERROR, "������Ϣɾ������");
			throw new ServiceException("������Ϣɾ������");
		} catch (HomeFactoryException e1) {
			LogUtility.log(this.getClass(), LogLevel.ERROR, "������Ϣɾ������");
			throw new ServiceException("������Ϣɾ������");
		} catch (RemoveException e2) {
			LogUtility.log(this.getClass(), LogLevel.ERROR, "������Ϣɾ������");
			throw new ServiceException("������Ϣɾ������");
		}

	}
}
