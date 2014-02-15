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
 * 公告信息操作
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
		LogUtility.log(this.getClass(), LogLevel.DEBUG, "创建公告信息");
		try {
			BillBoardHome home = HomeLocater.getBillBoardHome();
			BillBoard pallet=home.create(dto);
			 
		} catch (HomeFactoryException e1) {
			LogUtility.log(this.getClass(), LogLevel.ERROR, "创建公告信息出错！");
			throw new ServiceException("创建公告信息出错！");
		} catch (CreateException e3) {
			LogUtility.log(this.getClass(), LogLevel.ERROR, "创建公告信息出错！");
			throw new ServiceException("创建公告信息出错！");
		}

	}

	public void updateBoard(BillBoardDTO dto) throws ServiceException {
		LogUtility.log(this.getClass(), LogLevel.DEBUG, "公告信息更新");
		try {
			BillBoardHome home = HomeLocater.getBillBoardHome();
			BillBoard billBoard = home
					.findByPrimaryKey(new Integer(dto.getSeqNo()));
			if(billBoard.ejbUpdate(dto)==-1){
				LogUtility.log(clazz,LogLevel.WARN,"上次修改时间没有设置!");
				throw new ServiceException("上次修改时间没有设置");
			}
			 
		} catch (FinderException e) {
			LogUtility.log(this.getClass(), LogLevel.ERROR, "公告信息更新出错！");
			throw new ServiceException("公告信息更新出错");
		} catch (HomeFactoryException e1) {
			LogUtility.log(this.getClass(), LogLevel.ERROR, "公告信息更新出错！");
			throw new ServiceException("公告信息更新出错！");
		}

	}

	public void deleteBoard(BillBoardDTO dto) throws ServiceException {
		LogUtility.log(this.getClass(), LogLevel.DEBUG, "公告信息删除");
		try {
			BillBoardHome home = HomeLocater.getBillBoardHome();
			BillBoard billBoard = home.findByPrimaryKey(new Integer(dto.getSeqNo()));
			billBoard.remove();
		} catch (FinderException e) {
			LogUtility.log(this.getClass(), LogLevel.ERROR, "公告信息删除出错！");
			throw new ServiceException("公告信息删除出错");
		} catch (HomeFactoryException e1) {
			LogUtility.log(this.getClass(), LogLevel.ERROR, "公告信息删除出错！");
			throw new ServiceException("公告信息删除出错！");
		} catch (RemoveException e2) {
			LogUtility.log(this.getClass(), LogLevel.ERROR, "公告信息删除出错！");
			throw new ServiceException("公告信息删除出错！");
		}

	}
}
