/*
 * Created on 2006-4-14
 * 
 * @author chen jiang
 * 
 * ���г�����йز���command
 */
package com.dtv.oss.service.command.config;

import java.util.Collection;
import java.util.Iterator;

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.FinderException;
import javax.ejb.RemoveException;

import com.dtv.oss.domain.MarketSegment;
import com.dtv.oss.domain.MarketSegmentHome;
import com.dtv.oss.domain.MarketSegmentToDistrict;
import com.dtv.oss.domain.MarketSegmentToDistrictHome;
import com.dtv.oss.domain.MarketSegmentToDistrictPK;
import com.dtv.oss.domain.UserPointsCumulatedRule;
import com.dtv.oss.domain.UserPointsCumulatedRuleHome;
import com.dtv.oss.domain.UserPointsExchangeActivity;
import com.dtv.oss.domain.UserPointsExchangeActivityHome;
import com.dtv.oss.domain.UserPointsExchangeCond;
import com.dtv.oss.domain.UserPointsExchangeCondHome;
import com.dtv.oss.domain.UserPointsExchangeGoods;
import com.dtv.oss.domain.UserPointsExchangeGoodsHome;
import com.dtv.oss.domain.UserPointsExchangeRule;
import com.dtv.oss.domain.UserPointsExchangeRuleHome;
import com.dtv.oss.dto.MarketSegmentDTO;
import com.dtv.oss.dto.MarketSegmentToDistrictDTO;
import com.dtv.oss.dto.UserPointsCumulatedRuleDTO;
import com.dtv.oss.dto.UserPointsExchangeActivityDTO;
import com.dtv.oss.dto.UserPointsExchangeCondDTO;
import com.dtv.oss.dto.UserPointsExchangeGoodsDTO;
import com.dtv.oss.dto.UserPointsExchangeRuleDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.Service;
import com.dtv.oss.service.ServiceContext;
import com.dtv.oss.service.ServiceException;
import com.dtv.oss.service.command.Command;
import com.dtv.oss.service.command.CommandException;
import com.dtv.oss.service.commandresponse.CommandResponse;
import com.dtv.oss.service.commandresponse.CommandResponseImp;
import com.dtv.oss.service.component.PublicService;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.config.ConfigActivityEJBEvent;
import com.dtv.oss.service.factory.HomeFactoryException;
import com.dtv.oss.service.util.HomeLocater;
import com.dtv.oss.service.util.SystemLogRecorder;

public class ConfigActivityCommand extends Command {
	private static final Class clazz = ConfigActivityCommand.class;

	private int operatorID = 0;

	private String machineName = "";

	CommandResponseImp response = null;

	public CommandResponse execute(EJBEvent ev) throws CommandException {
		response = new CommandResponseImp(null);
		ConfigActivityEJBEvent inEvent = (ConfigActivityEJBEvent) ev;
		operatorID = inEvent.getOperatorID();
		machineName = ev.getRemoteHostAddress();
		LogUtility.log(clazz, LogLevel.DEBUG, "Enter execute() now.");

		try {
			switch (inEvent.getActionType()) {

			case EJBEvent.CONFIG_ACTIVITY_CREATE: //�һ������
				createActivity(inEvent.getActivityDto());
				break;
			case EJBEvent.CONFIG_ACTIVITY_UPDATE:
				updateActivity(inEvent.getActivityDto()); //�һ������
				break;
			case EJBEvent.CONFIG_ACTIVITY_DELETE:
				deleteActivity(inEvent.getActivityDto()); //�һ��ɾ��
				break;
			case EJBEvent.CONFIG_GOODS_DELETE:
				deleteGoods(inEvent.getGoodsDto()); //�һ�����ɾ��
				break;
			case EJBEvent.CONFIG_GOODS_CREATE:
				createGoods(inEvent.getGoodsDto()); //�һ����ﴴ��
				break;
			case EJBEvent.CONFIG_GOODS_EDIT:
				updateGoods(inEvent.getGoodsDto()); //�һ��������
				break;
			case EJBEvent.CONFIG_RULE_DELETE:
				deleteRule(inEvent.getUserPointsExchRuleDto()); //�һ�����ɾ��
				break;
			case EJBEvent.CONFIG_RULE_CREATE:
				createRule(inEvent.getUserPointsExchRuleDto()); //�һ����򴴽�
				break;
			case EJBEvent.CONFIG_RULE_EDIT:
				updateRule(inEvent.getUserPointsExchRuleDto()); //�һ��������
				break;
			case EJBEvent.CONFIG_COND_DELETE:
				deleteCond(inEvent.getUserPointsExchCondDto()); //�һ�����ɾ��
				break;
			case EJBEvent.CONFIG_COND_CREATE:
				createCond(inEvent.getUserPointsExchCondDto()); //�һ���������
				break;
			case EJBEvent.CONFIG_COND_EDIT:
				updateCond(inEvent.getUserPointsExchCondDto()); //�һ���������
				break;
			case EJBEvent.CONFIG_CUMULATED_RULE_EDIT:
				updateCumulatedRule(inEvent.getCumulatedRuleDto()); //���ֶһ��ۼӹ������
				break;
			case EJBEvent.CONFIG_CUMULATED_RULE_CREATE:
				createCumulatedRule(inEvent.getCumulatedRuleDto()); //���ֶһ��ۼӹ��򴴽�
				break;
			case EJBEvent.CONFIG_CUMULATED_RULE_DELETE:
				deleteCumulatedRule(inEvent.getCumulatedRuleDto()); //���ֶһ��ۼӹ���ɾ��
				break;
			case EJBEvent.CONFIG_MARKET_SEGMENT_EDIT:
				updateMarketSegment(inEvent.getMarketSegmentDTO(),inEvent.getMarketSegmentToDistrictDtoCol()); //�г���������
				break;
			case EJBEvent.CONFIG_MARKET_SEGMENT_CREATE:
				createMarketSegment(inEvent.getMarketSegmentDTO(),inEvent.getMarketSegmentToDistrictDtoCol()); //�г���������
				break;
			case EJBEvent.CONFIG_MARKET_SEGMENT_DELETE:
				deleteMarketSegment(inEvent.getMarketSegmentDTO()); //�г�����ɾ��
				break;

			case EJBEvent.CONFIG_DISTRICT_CREATE:
				createMarketSegmentToDistrict(inEvent
						.getMarketSegmentToDistrictDtoCol(), inEvent
						.getSegmentId()); //�г���������
				break;

			default:
				throw new IllegalArgumentException(
						"ConfigActivityEJBEvent��actionType�����ò���ȷ��");
			}
		}catch(ServiceException ce){
	        LogUtility.log(clazz,LogLevel.ERROR,this,ce);
	        throw new CommandException(ce.getMessage());
	    }catch(Throwable unkown) {
		    LogUtility.log(clazz,LogLevel.FATAL, this, unkown);
		    throw new CommandException("����δ֪�Ĵ���");
		}
		return response;
	}


	/**
	 * �޸Ļ��ֶһ����Ϣ
	 * @param UserPointsExchangeActivityDTO
	 */
	private void updateActivity(UserPointsExchangeActivityDTO dto)
			throws ServiceException {

		try {
			ServiceContext context = initServiceContext();
			UserPointsExchangeActivityHome activityHome = HomeLocater
					.getUserPointsExchangeActivityHome();

			UserPointsExchangeActivity activity = activityHome
					.findByPrimaryKey(dto.getId());
			

			if (activity.ejbUpdate(dto) == -1) {
				SystemLogRecorder.keyLog("���»��ֶһ��ʧ��", "���»��ֶһ��", machineName,
						SystemLogRecorder.LOGMODULE_CONFIG, operatorID);
				throw new RuntimeException(
						"UserPointsExchangeActivityDTO update fail, ID:"
								+ dto.getId()
								+ ". Please make sure that dt_lastmod of UserPointsExchangeActivityDTO is set.");
			}

			SystemLogRecorder.createSystemLog(PublicService
					.getRemoteHostAddress(context), PublicService
					.getCurrentOperatorID(context), 0,
					SystemLogRecorder.LOGMODULE_CONFIG, "�޸Ļ��ֶһ��",
					"�޸Ļ��ֶһ����ID��" + dto.getId(),
					SystemLogRecorder.LOGCLASS_NORMAL,
					SystemLogRecorder.LOGTYPE_APP);

		} catch (HomeFactoryException e) {
			LogUtility.log(clazz, LogLevel.ERROR, e);
			throw new ServiceException("���һ��ֶһ��ʱ�����ID��" + dto.getId());
		} catch (FinderException e) {
			LogUtility.log(clazz, LogLevel.ERROR, e);
			throw new ServiceException("���һ��ֶһ�������ID��" + dto.getId());
		}
	}

	/**
	 * �������ֻ
	 * 
	 * @param UserPointsExchangeActivityDTO dto
	 * @throws ServiceException
	 */
	private void createActivity(UserPointsExchangeActivityDTO dto)
			throws ServiceException {

		try {
			UserPointsExchangeActivityHome home = HomeLocater
					.getUserPointsExchangeActivityHome();

			UserPointsExchangeActivity activity = home.create(dto);

		} catch (HomeFactoryException e) {
			LogUtility.log(this.getClass(), LogLevel.ERROR, e);
			throw new ServiceException("�������ֶһ������");
		} catch (CreateException e) {
			LogUtility.log(this.getClass(), LogLevel.ERROR, e);
			throw new ServiceException("�������ֶһ������");
		}
	}

	/**
	 * ɾ�����ֻ
	 * 
	 * @param UserPointsExchangeActivityDTO dto
	 * @throws ServiceException
	 * @throws RemoveException
	 * @throws 
	 */
	private void deleteActivity(UserPointsExchangeActivityDTO dto)
			throws ServiceException, RemoveException {

		try {
			ServiceContext context = initServiceContext();
			UserPointsExchangeActivityHome activityHome = HomeLocater
					.getUserPointsExchangeActivityHome();
			UserPointsExchangeActivity userPointsExchangeActivity = activityHome
					.findByPrimaryKey(dto.getId());
			userPointsExchangeActivity.remove();
			//ɾ�����ֻ��ͬʱ,ҲҪɾ����֮������ı�ļ�¼
			UserPointsExchangeGoodsHome goodsHome = HomeLocater
					.getUserPointsExchangeGoodsHome();
			Collection goodCol = goodsHome.findByActivityID(dto.getId()
					.intValue());
			Iterator goodIte = goodCol.iterator();
			while (goodIte.hasNext()) {
				UserPointsExchangeGoods exchangeGoods = (UserPointsExchangeGoods) goodIte
						.next();
				exchangeGoods.remove();
			}
			UserPointsExchangeRuleHome ruleHome = HomeLocater
					.getUserPointsExchangeRuleHome();
			Collection ruleCol = ruleHome.findByActivityID(dto.getId()
					.intValue());
			Iterator ruleIte = ruleCol.iterator();
			while (ruleIte.hasNext()) {
				UserPointsExchangeRule exchangeRule = (UserPointsExchangeRule) ruleIte
						.next();
				exchangeRule.remove();
			}
			UserPointsExchangeCondHome condHome = HomeLocater
					.getUserPointsExchangeCondHome();
			Collection condCol = condHome.findByActivityId(dto.getId()
					.intValue());
			Iterator condIte = condCol.iterator();
			while (condIte.hasNext()) {
				UserPointsExchangeCond exchangeCond = (UserPointsExchangeCond) condIte
						.next();
				exchangeCond.remove();
			}

			SystemLogRecorder.createSystemLog(PublicService
					.getRemoteHostAddress(context), PublicService
					.getCurrentOperatorID(context), 0,
					SystemLogRecorder.LOGMODULE_CONFIG, "ɾ�����ֶһ��",
					"ɾ�����ֶһ����ID��" + dto.getId(),
					SystemLogRecorder.LOGCLASS_NORMAL,
					SystemLogRecorder.LOGTYPE_APP);

		} catch (HomeFactoryException e) {
			LogUtility.log(this.getClass(), LogLevel.ERROR, e);
			throw new ServiceException("��λ���ֶһ������");
		} catch (FinderException e) {
			LogUtility.log(clazz, LogLevel.ERROR, e);
			throw new ServiceException("���һ��ֶһ�������ID��" + dto.getId());
		}
	}

	/**
	 * ɾ�����ֶһ�����
	 * 
	 * @param UserPointsExchangeGoodsDTO dto
	 * @throws ServiceException
	 * @throws RemoveException
	 * @throws 
	 */
	private void deleteGoods(UserPointsExchangeGoodsDTO dto)
			throws ServiceException, RemoveException {

		try {
			ServiceContext context = initServiceContext();
			UserPointsExchangeGoodsHome goodsHome = HomeLocater
					.getUserPointsExchangeGoodsHome();
			UserPointsExchangeGoods userPointsExchangeGoods = goodsHome
					.findByPrimaryKey(new Integer(dto.getId()));
			userPointsExchangeGoods.remove();

			SystemLogRecorder.createSystemLog(PublicService
					.getRemoteHostAddress(context), PublicService
					.getCurrentOperatorID(context), 0,
					SystemLogRecorder.LOGMODULE_CONFIG, "ɾ�����ֶһ�����",
					"ɾ�����ֶһ����ID��" + dto.getId(),
					SystemLogRecorder.LOGCLASS_NORMAL,
					SystemLogRecorder.LOGTYPE_APP);

		} catch (HomeFactoryException e) {
			LogUtility.log(this.getClass(), LogLevel.ERROR, e);
			throw new ServiceException("��λ���ֶһ��������");
		} catch (FinderException e) {
			LogUtility.log(clazz, LogLevel.ERROR, e);
			throw new ServiceException("���һ��ֶһ���������ID��" + dto.getId());
		}
	}

	/**
	 * �������ֶһ�����
	 * 
	 * @param UserPointsExchangeGoodsDTO dto
	 * @throws ServiceException
	 */
	private void createGoods(UserPointsExchangeGoodsDTO dto)
			throws ServiceException {

		try {
			ServiceContext context = initServiceContext();
			UserPointsExchangeGoodsHome home = HomeLocater
					.getUserPointsExchangeGoodsHome();

			UserPointsExchangeGoods goods = home.create(dto);

			SystemLogRecorder.createSystemLog(PublicService
					.getRemoteHostAddress(context), PublicService
					.getCurrentOperatorID(context), 0,
					SystemLogRecorder.LOGMODULE_CONFIG, "�������ֶһ�����",
					"�������ֶһ����ID��" + goods.getId(),
					SystemLogRecorder.LOGCLASS_NORMAL,
					SystemLogRecorder.LOGTYPE_APP);

		} catch (HomeFactoryException e) {
			LogUtility.log(this.getClass(), LogLevel.ERROR, e);
			throw new ServiceException("�������ֶһ��������");
		} catch (CreateException e) {
			LogUtility.log(this.getClass(), LogLevel.ERROR, e);
			throw new ServiceException("�������ֶһ��������");
		}
	}

	/**
	 * �޸Ļ��ֶһ�������Ϣ
	 * @param UserPointsExchangeGoodsDTO
	 */
	private void updateGoods(UserPointsExchangeGoodsDTO dto)
			throws ServiceException {

		try {
			ServiceContext context = initServiceContext();
			UserPointsExchangeGoodsHome goodsHome = HomeLocater
					.getUserPointsExchangeGoodsHome();
			UserPointsExchangeGoods goods = goodsHome
					.findByPrimaryKey(new Integer(dto.getId()));

			if (goods.ejbUpdate(dto) == -1) {
				SystemLogRecorder.keyLog("���»��ֶһ�����ʧ��", "���»��ֶһ�����", machineName,
						SystemLogRecorder.LOGMODULE_CONFIG, operatorID);
				throw new RuntimeException(
						"UserPointsExchangeGoodsDTO update fail, ID:"
								+ dto.getId()
								+ ". Please make sure that dt_lastmod of UserPointsExchangeGoodsDTO is set.");
			}

			SystemLogRecorder.createSystemLog(PublicService
					.getRemoteHostAddress(context), PublicService
					.getCurrentOperatorID(context), 0,
					SystemLogRecorder.LOGMODULE_CONFIG, "�޸Ļ��ֶһ�����",
					"�޸Ļ��ֶһ����ID��" + dto.getId(),
					SystemLogRecorder.LOGCLASS_NORMAL,
					SystemLogRecorder.LOGTYPE_APP);

		} catch (HomeFactoryException e) {
			LogUtility.log(clazz, LogLevel.ERROR, e);
			throw new ServiceException("���һ��ֶһ�����ʱ��������ID��" + dto.getId());
		} catch (FinderException e) {
			LogUtility.log(clazz, LogLevel.ERROR, e);
			throw new ServiceException("���һ��ֶһ������������ID��" + dto.getId());
		}
	}

	/**
	 * ɾ�����ֶһ�����
	 * 
	 * @param UserPointsExchangeRuleDTO dto
	 * @throws ServiceException
	 * @throws RemoveException
	 * @throws 
	 */
	private void deleteRule(UserPointsExchangeRuleDTO dto)
			throws ServiceException, RemoveException {

		try {
			ServiceContext context = initServiceContext();
			UserPointsExchangeRuleHome ruleHome = HomeLocater
					.getUserPointsExchangeRuleHome();
			UserPointsExchangeRule userPointsExchangeRule = ruleHome
					.findByPrimaryKey(new Integer(dto.getId()));
			userPointsExchangeRule.remove();

			SystemLogRecorder.createSystemLog(PublicService
					.getRemoteHostAddress(context), PublicService
					.getCurrentOperatorID(context), 0,
					SystemLogRecorder.LOGMODULE_CONFIG, "ɾ�����ֶһ�����",
					"ɾ�����ֶһ�����ID��" + dto.getId(),
					SystemLogRecorder.LOGCLASS_NORMAL,
					SystemLogRecorder.LOGTYPE_APP);

		} catch (HomeFactoryException e) {
			LogUtility.log(this.getClass(), LogLevel.ERROR, e);
			throw new ServiceException("��λ���ֶһ��������");
		} catch (FinderException e) {
			LogUtility.log(clazz, LogLevel.ERROR, e);
			throw new ServiceException("���һ��ֶһ���������ID��" + dto.getId());
		}
	}

	/**
	 * �������ֶһ�����
	 * 
	 * @param UserPointsExchangeRuleDTO dto
	 * @throws ServiceException
	 */
	private void createRule(UserPointsExchangeRuleDTO dto)
			throws ServiceException {

		try {
			ServiceContext context = initServiceContext();
			UserPointsExchangeRuleHome home = HomeLocater
					.getUserPointsExchangeRuleHome();

			UserPointsExchangeRule rule = home.create(dto);
			SystemLogRecorder.createSystemLog(PublicService
					.getRemoteHostAddress(context), PublicService
					.getCurrentOperatorID(context), 0,
					SystemLogRecorder.LOGMODULE_CONFIG, "�������ֶһ�����",
					"�������ֶһ�����:ID=" + rule.getId(),
					SystemLogRecorder.LOGCLASS_NORMAL,
					SystemLogRecorder.LOGTYPE_APP);

		} catch (HomeFactoryException e) {
			LogUtility.log(this.getClass(), LogLevel.ERROR, e);
			throw new ServiceException("�������ֶһ��������");
		} catch (CreateException e) {
			LogUtility.log(this.getClass(), LogLevel.ERROR, e);
			throw new ServiceException("�������ֶһ��������");
		}
	}

	/**
	 * �޸Ļ��ֶһ�������Ϣ
	 * @param UserPointsExchangeRuleDTO
	 */
	private void updateRule(UserPointsExchangeRuleDTO dto)
			throws ServiceException {

		try {
			ServiceContext context = initServiceContext();
			UserPointsExchangeRuleHome home = HomeLocater
					.getUserPointsExchangeRuleHome();
			UserPointsExchangeRule rule = home.findByPrimaryKey(new Integer(dto
					.getId()));

			if (rule.ejbUpdate(dto) == -1) {
				SystemLogRecorder.keyLog("���»��ֶһ�������Ϣ", "���»��ֶһ�������Ϣ",
						machineName, SystemLogRecorder.LOGMODULE_CONFIG,
						operatorID);
				throw new RuntimeException(
						"UserPointsExchangeRuleDTO update fail, ID:"
								+ dto.getId()
								+ ". Please make sure that dt_lastmod of UserPointsExchangeRuleDTO is set.");
			}

			SystemLogRecorder.createSystemLog(PublicService
					.getRemoteHostAddress(context), PublicService
					.getCurrentOperatorID(context), 0,
					SystemLogRecorder.LOGMODULE_CONFIG, "�޸Ļ��ֶһ�����",
					"�޸Ļ��ֶһ�����ID��" + dto.getId(),
					SystemLogRecorder.LOGCLASS_NORMAL,
					SystemLogRecorder.LOGTYPE_APP);

		} catch (HomeFactoryException e) {
			LogUtility.log(clazz, LogLevel.ERROR, e);
			throw new ServiceException("���һ��ֶһ�����ʱ��������ID��" + dto.getId());
		} catch (FinderException e) {
			LogUtility.log(clazz, LogLevel.ERROR, e);
			throw new ServiceException("���һ��ֶһ������������ID��" + dto.getId());
		}
	}

	/**
	 * ɾ�����ֶһ�����
	 * 
	 * @param UserPointsExchangeRuleDTO dto
	 * @throws ServiceException
	 * @throws RemoveException
	 * @throws 
	 */
	private void deleteCond(UserPointsExchangeCondDTO dto)
			throws ServiceException, RemoveException {

		try {
			ServiceContext context = initServiceContext();
			UserPointsExchangeCondHome condHome = HomeLocater
					.getUserPointsExchangeCondHome();
			UserPointsExchangeCond userPointsExchangeCond = condHome
					.findByPrimaryKey(new Integer(dto.getCondId()));
			userPointsExchangeCond.remove();

			SystemLogRecorder.createSystemLog(PublicService
					.getRemoteHostAddress(context), PublicService
					.getCurrentOperatorID(context), 0,
					SystemLogRecorder.LOGMODULE_CONFIG, "ɾ�����ֶһ�����",
					"ɾ�����ֶһ�������ID��" + dto.getCondId(),
					SystemLogRecorder.LOGCLASS_NORMAL,
					SystemLogRecorder.LOGTYPE_APP);

		} catch (HomeFactoryException e) {
			LogUtility.log(this.getClass(), LogLevel.ERROR, e);
			throw new ServiceException("��λ���ֶһ���������");
		} catch (FinderException e) {
			LogUtility.log(clazz, LogLevel.ERROR, e);
			throw new ServiceException("���һ��ֶһ����������ID��" + dto.getCondId());
		}
	}

	/**
	 * �������ֶһ�����
	 * 
	 * @param UserPointsExchangeRuleDTO dto
	 * @throws ServiceException
	 */
	private void createCond(UserPointsExchangeCondDTO dto)
			throws ServiceException {

		try {
			ServiceContext context = initServiceContext();
			UserPointsExchangeCondHome home = HomeLocater
					.getUserPointsExchangeCondHome();

			UserPointsExchangeCond rule = home.create(dto);
			SystemLogRecorder.createSystemLog(PublicService
					.getRemoteHostAddress(context), PublicService
					.getCurrentOperatorID(context), 0,
					SystemLogRecorder.LOGMODULE_CONFIG, "�������ֶһ�����",
					"�������ֶһ�����:ID=" + rule.getCondId(),
					SystemLogRecorder.LOGCLASS_NORMAL,
					SystemLogRecorder.LOGTYPE_APP);

		} catch (HomeFactoryException e) {
			LogUtility.log(this.getClass(), LogLevel.ERROR, e);
			throw new ServiceException("�������ֶһ���������");
		} catch (CreateException e) {
			LogUtility.log(this.getClass(), LogLevel.ERROR, e);
			throw new ServiceException("�������ֶһ���������");
		}
	}

	/**
	 * �޸Ļ��ֶһ�������Ϣ
	 * @param UserPointsExchangeCondDTO
	 * @throws CommandException 
	 */
	private void updateCond(UserPointsExchangeCondDTO dto)
			throws ServiceException, CommandException {

		try {
			ServiceContext context = initServiceContext();
			UserPointsExchangeCondHome home = HomeLocater
					.getUserPointsExchangeCondHome();
			LogUtility.log(clazz, LogLevel.DEBUG, dto.getActivityId()+"");
			LogUtility.log(clazz, LogLevel.DEBUG, "******************** "+dto.toString());
			if(dto.getActivityId()==0){
				throw new CommandException("���ֻ��Ŷ�ʧ.");
			}
			UserPointsExchangeCond cond = home.findByPrimaryKey(new Integer(dto
					.getCondId()));

			if (cond.ejbUpdate(dto) == -1) {
				 
				throw new ServiceException("���»��ֶһ�������Ϣʧ��");
				 
			}

			SystemLogRecorder.createSystemLog(PublicService
					.getRemoteHostAddress(context), PublicService
					.getCurrentOperatorID(context), 0,
					SystemLogRecorder.LOGMODULE_CONFIG, "�޸Ļ��ֶһ�����",
					"�޸Ļ��ֶһ�������ID��" + dto.getCondId(),
					SystemLogRecorder.LOGCLASS_NORMAL,
					SystemLogRecorder.LOGTYPE_APP);

		} catch (HomeFactoryException e) {
			LogUtility.log(clazz, LogLevel.ERROR, e);
			throw new ServiceException("���һ��ֶһ���������condID��" + dto.getCondId());
		} catch (FinderException e) {
			LogUtility.log(clazz, LogLevel.ERROR, e);
			throw new ServiceException("���һ��ֶһ���������condID��" + dto.getCondId());
		}
	}

	/**
	 * �޸Ļ��ֶһ��ۼӹ�����Ϣ
	 * @param UserPointsCumulatedRuleDTO
	 */
	private void updateCumulatedRule(UserPointsCumulatedRuleDTO dto)
			throws ServiceException {

		try {
			ServiceContext context = initServiceContext();
			UserPointsCumulatedRuleHome home = HomeLocater
					.getUserPointsCumulatedRuleHome();
			UserPointsCumulatedRule cumulatedRule = home
					.findByPrimaryKey(new Integer(dto.getId()));

			if (cumulatedRule.ejbUpdate(dto) == -1) {
				SystemLogRecorder.keyLog("�޸Ļ��ֶһ��ۼӹ�����Ϣ", "�޸Ļ��ֶһ��ۼӹ�����Ϣ",
						machineName, SystemLogRecorder.LOGMODULE_CONFIG,
						operatorID);
				throw new ServiceException("���»��ֶһ��ۼӹ���ʧ��");
			}

			SystemLogRecorder.createSystemLog(PublicService
					.getRemoteHostAddress(context), PublicService
					.getCurrentOperatorID(context), 0,
					SystemLogRecorder.LOGMODULE_CONFIG, "�޸Ļ��ֶһ��ۼӹ�����Ϣ",
					"�޸Ļ��ֶһ��ۼӹ�����Ϣ��ID��" + dto.getId(),
					SystemLogRecorder.LOGCLASS_NORMAL,
					SystemLogRecorder.LOGTYPE_APP);

		} catch (HomeFactoryException e) {
			LogUtility.log(clazz, LogLevel.ERROR, e);
			throw new ServiceException("���һ��ֶһ��ۼӹ������ID��" + dto.getId());
		} catch (FinderException e) {
			LogUtility.log(clazz, LogLevel.ERROR, e);
			throw new ServiceException("���Ҷһ��ۼӹ������ID��" + dto.getId());
		}
	}

	/**
	 * �������ֶһ��ۼӹ�����Ϣ
	 * 
	 * @param UserPointsExchangeRuleDTO dto
	 * @throws ServiceException
	 */
	private void createCumulatedRule(UserPointsCumulatedRuleDTO dto)
			throws ServiceException {

		try {
			ServiceContext context = initServiceContext();

			UserPointsCumulatedRuleHome home = HomeLocater
					.getUserPointsCumulatedRuleHome();

			UserPointsCumulatedRule cumulatedRule = home.create(dto);

			//       	������ص���־��¼��

			SystemLogRecorder.createSystemLog(PublicService
					.getRemoteHostAddress(context), PublicService
					.getCurrentOperatorID(context), 0,
					SystemLogRecorder.LOGMODULE_CONFIG, "�������ֶһ��ۼӹ���",
					"�������ֶһ��ۼӹ���:ID��" + cumulatedRule.getId(),
					SystemLogRecorder.LOGCLASS_NORMAL,
					SystemLogRecorder.LOGTYPE_APP);

		} catch (HomeFactoryException e) {
			LogUtility.log(this.getClass(), LogLevel.ERROR, e);
			throw new ServiceException("���һ��ֶһ��ۼӹ���ӿڳ���");

		} catch (CreateException e) {
			LogUtility.log(this.getClass(), LogLevel.ERROR, e);
			throw new ServiceException("�������ֶһ��ۼӹ�����Ϣ����");
		}
	}

	/**
	 * ɾ�����ֶһ��ۼӹ�����Ϣ
	 * 
	 * @param UserPointsCumulatedRuleDTO dto
	 * @throws ServiceException
	 * @throws RemoveException
	 * @throws 
	 */
	private void deleteCumulatedRule(UserPointsCumulatedRuleDTO dto)
			throws ServiceException, RemoveException {

		try {
			ServiceContext context = initServiceContext();
			UserPointsCumulatedRuleHome home = HomeLocater
					.getUserPointsCumulatedRuleHome();
			UserPointsCumulatedRule cumulatedRule = home
					.findByPrimaryKey(new Integer(dto.getId()));
			cumulatedRule.remove();

			SystemLogRecorder.createSystemLog(PublicService
					.getRemoteHostAddress(context), PublicService
					.getCurrentOperatorID(context), 0,
					SystemLogRecorder.LOGMODULE_CONFIG, "ɾ�����ֶһ��ۼӹ�����Ϣ",
					"ɾ�����ֶһ��ۼӹ�����Ϣ:ID��" + dto.getId(),
					SystemLogRecorder.LOGCLASS_NORMAL,
					SystemLogRecorder.LOGTYPE_APP);

		} catch (HomeFactoryException e) {
			LogUtility.log(this.getClass(), LogLevel.ERROR, e);
			throw new ServiceException("��λ���ֶһ��ۼӹ�����Ϣ����");
		} catch (FinderException e) {
			LogUtility.log(clazz, LogLevel.ERROR, e);
			throw new ServiceException("���һ��ֶһ��ۼӹ�����Ϣ����ID��" + dto.getId());
		}
	}

	/**
	 * �����г�����
	 * 
	 * @param MarketSegmentDTO dto
	 * @throws ServiceException
	 */
	private void createMarketSegment(MarketSegmentDTO dto,Collection segmentDistrictCol)
			throws ServiceException {

		try {
			ServiceContext context = initServiceContext();

			MarketSegmentHome home = HomeLocater.getMarketSegmentHome();

			MarketSegment marketSegment = home.create(dto);
			Integer id=marketSegment.getId();
			if(segmentDistrictCol!=null){
			Iterator ite = segmentDistrictCol.iterator();   
			MarketSegmentToDistrictHome districthome = HomeLocater
            .getMarketSegmentToDistrictHome();
            while (ite.hasNext()) {
            	MarketSegmentToDistrictDTO	 disDto = (MarketSegmentToDistrictDTO )ite.next();
            	disDto.setMarketSegmentId(id.intValue());
            	districthome.create(disDto);
            }
			}
			//       	������ص���־��¼��

			SystemLogRecorder.createSystemLog(PublicService
					.getRemoteHostAddress(context), PublicService
					.getCurrentOperatorID(context), 0,
					SystemLogRecorder.LOGMODULE_CONFIG, "�����г�����", "�����г�����:ID��"
							+ marketSegment.getId(),
					SystemLogRecorder.LOGCLASS_NORMAL,
					SystemLogRecorder.LOGTYPE_APP);

		} catch (HomeFactoryException e) {
			LogUtility.log(this.getClass(), LogLevel.ERROR, e);
			throw new ServiceException("���Ҵ����г������ӿڳ���");

		} catch (CreateException e) {
			LogUtility.log(this.getClass(), LogLevel.ERROR, e);
			throw new ServiceException("���������г�������Ϣ����");
		}
	}

	/**
	 * �޸��г�����
	 * @param MarketSegmentDTO
	 */
	private void updateMarketSegment(MarketSegmentDTO dto,Collection segmentDistrictCol)
			throws ServiceException {

		try {
			ServiceContext context = initServiceContext();
			MarketSegmentHome home = HomeLocater.getMarketSegmentHome();
			MarketSegment marketSegment = home.findByPrimaryKey(new Integer(dto
					.getId()));

			if (marketSegment.ejbUpdate(dto) == -1) {
				SystemLogRecorder.keyLog("�޸��г�����", "�޸��г�����", machineName,
						SystemLogRecorder.LOGMODULE_CONFIG, operatorID);
				throw new RuntimeException(
						"MarketSegmentDTO update fail, ID:"
								+ dto.getId()
								+ ". Please make sure that dt_lastmod of MarketSegmentDTO is set.");
			}
			     MarketSegmentToDistrictHome districthome = HomeLocater
			                           .getMarketSegmentToDistrictHome();
	            Collection districtCol = districthome.findByMarketSegmentID(dto
			                                                          .getId());
	            if(districtCol!=null){
	              Iterator ite = districtCol.iterator();
	               while (ite.hasNext()) {

		             MarketSegmentToDistrict district = (MarketSegmentToDistrict) ite.next();
		             district.remove();
	               }
	            }
	             if(segmentDistrictCol!=null){
	               Iterator ite1 = segmentDistrictCol.iterator();   
	                while (ite1.hasNext()) {
	             	MarketSegmentToDistrictDTO	 disDto = (MarketSegmentToDistrictDTO )ite1.next();
	             	districthome.create(disDto);
	             }
	         }    
			SystemLogRecorder.createSystemLog(PublicService
					.getRemoteHostAddress(context), PublicService
					.getCurrentOperatorID(context), 0,
					SystemLogRecorder.LOGMODULE_CONFIG, "�޸��г�����", "�޸��г�������ID��"
							+ dto.getId(), SystemLogRecorder.LOGCLASS_NORMAL,
					SystemLogRecorder.LOGTYPE_APP);

		} catch (HomeFactoryException e) {
			LogUtility.log(clazz, LogLevel.ERROR, e);
			throw new ServiceException("�����г���������ID��" + dto.getId());
		} catch (FinderException e) {
			LogUtility.log(clazz, LogLevel.ERROR, e);
			throw new ServiceException("�����г���������ID��" + dto.getId());
		} catch (EJBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RemoveException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CreateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * ɾ���г�������Ϣ
	 * 
	 * @param MarketSegmentDTO dto
	 * @throws ServiceException
	 * @throws RemoveException
	 * @throws 
	 */
	private void deleteMarketSegment(MarketSegmentDTO dto)
			throws ServiceException, RemoveException {

		try {
			ServiceContext context = initServiceContext();
			if (dto == null || dto.getId() == 0) {
				LogUtility.log(clazz, LogLevel.WARN, "����Ĳ���Ϊ�գ���������Ϊ��!");
				return;
			}
			MarketSegmentHome home = HomeLocater.getMarketSegmentHome();
			MarketSegment marketSegment = home.findByPrimaryKey(new Integer(dto
					.getId()));
			marketSegment.remove();
			MarketSegmentToDistrictHome districthome = HomeLocater
					.getMarketSegmentToDistrictHome();
			Collection districtCol = districthome.findByMarketSegmentID(dto
					.getId());
			Iterator ite = districtCol.iterator();
			while (ite.hasNext()) {

				MarketSegmentToDistrict district = (MarketSegmentToDistrict) ite
						.next();
				district.remove();
			}
			SystemLogRecorder.createSystemLog(PublicService
					.getRemoteHostAddress(context), PublicService
					.getCurrentOperatorID(context), 0,
					SystemLogRecorder.LOGMODULE_CONFIG, "ɾ���г�������Ϣ",
					"ɾ���г�������Ϣ:ID��" + dto.getId(),
					SystemLogRecorder.LOGCLASS_NORMAL,
					SystemLogRecorder.LOGTYPE_APP);

		} catch (HomeFactoryException e) {
			LogUtility.log(this.getClass(), LogLevel.ERROR, e);
			throw new ServiceException("��λ�г�������Ϣ����");
		} catch (FinderException e) {
			LogUtility.log(clazz, LogLevel.ERROR, e);
			throw new ServiceException("�����г�������Ϣ����ID��" + dto.getId());
		}
	}

	/**
	 * ɾ���г�������Ϣ
	 * 
	 * @param MarketSegmentToDistrictDTO dto
	 * @throws ServiceException
	 * @throws RemoveException
	 * @throws 
	 */
	private void deleteDistrict(MarketSegmentToDistrictDTO dto)
			throws ServiceException, RemoveException {

		try {
			ServiceContext context = initServiceContext();
			MarketSegmentToDistrictHome home = HomeLocater
					.getMarketSegmentToDistrictHome();
			if (dto == null || dto.getDistrictId() == 0
					|| dto.getMarketSegmentId() == 00) {
				LogUtility.log(clazz, LogLevel.WARN, "����Ĳ���Ϊ�գ������޸��г�����!");
				return;
			}

			int districtID = dto.getDistrictId();
			int segmentID = dto.getMarketSegmentId();

			MarketSegmentToDistrictPK pk = new MarketSegmentToDistrictPK(
					segmentID, districtID);
			MarketSegmentToDistrict district = home.findByPrimaryKey(pk);
			district.remove();

			SystemLogRecorder.createSystemLog(PublicService
					.getRemoteHostAddress(context), PublicService
					.getCurrentOperatorID(context), 0,
					SystemLogRecorder.LOGMODULE_CONFIG, "ɾ���г�����,����ID:"
							+ dto.getMarketSegmentId(), "ɾ���г������µ�������Ϣ:ID��"
							+ dto.getDistrictId(),
					SystemLogRecorder.LOGCLASS_NORMAL,
					SystemLogRecorder.LOGTYPE_APP);

		} catch (HomeFactoryException e) {
			LogUtility.log(this.getClass(), LogLevel.ERROR, e);
			throw new ServiceException("��λ����������Ϣ����");
		} catch (FinderException e) {
			LogUtility.log(clazz, LogLevel.ERROR, e);
			throw new ServiceException("��������������Ϣ����");
		}
	}

	/**
	 * �����г�����
	 * 
	 * @param Collection MarketSegmentToDistrictDTOCol
	 * @throws ServiceException
	 */
	private void createMarketSegmentToDistrict(Collection districtCol,
			int segmentId) throws ServiceException {

		try {
			ServiceContext context = initServiceContext();

			MarketSegmentToDistrictHome home = HomeLocater
					.getMarketSegmentToDistrictHome();
			Collection col = home.findByMarketSegmentID(segmentId);
			Iterator ite1 = col.iterator();
			while (ite1.hasNext()) {
				MarketSegmentToDistrict district = (MarketSegmentToDistrict) ite1
						.next();

				district.remove();

			}
			if (!districtCol.isEmpty() && districtCol != null) {
				Iterator ite = districtCol.iterator();
				while (ite.hasNext()) {
					MarketSegmentToDistrictDTO dto = (MarketSegmentToDistrictDTO) ite
							.next();

					MarketSegmentToDistrict district = home.create(dto);
				}
			}

			//       	������ص���־��¼��

			SystemLogRecorder.createSystemLog(PublicService
					.getRemoteHostAddress(context), PublicService
					.getCurrentOperatorID(context), 0,
					SystemLogRecorder.LOGMODULE_CONFIG, "�����г�����", "���г�����ID :"
							+ segmentId + "�´����г�����:ID��",
					SystemLogRecorder.LOGCLASS_NORMAL,
					SystemLogRecorder.LOGTYPE_APP);

		} catch (HomeFactoryException e) {
			LogUtility.log(this.getClass(), LogLevel.ERROR, e);
			throw new ServiceException("���Ҵ����г������ӿڳ���");

		} catch (CreateException e) {
			LogUtility.log(this.getClass(), LogLevel.ERROR, e);
			throw new ServiceException("���������г�������Ϣ����");
		} catch (FinderException e) {
			LogUtility.log(clazz, LogLevel.ERROR, e);
			throw new ServiceException("�����г�������Ϣ����");
		} catch (RemoveException e) {
			LogUtility.log(clazz, LogLevel.ERROR, e);
			throw new ServiceException("ɾ���г�������Ϣ����");
		}
	}

	/**
	 * ��ʼ��ServiceContext
	 * ��һЩͨ�õ���Ϣ����ServiceContext
	 */
	private ServiceContext initServiceContext() {
		ServiceContext serviceContext = new ServiceContext();
		serviceContext.put(Service.OPERATIOR_ID, new Integer(operatorID));
		serviceContext.put(Service.REMOTE_HOST_ADDRESS,machineName);
		return serviceContext;
	}

	public static void main(String[] args) {
	}
}
