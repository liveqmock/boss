/*
 * Created on 2006-4-14
 * 
 * @author chen jiang
 * 
 * 与市场活动的有关操作command
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

			case EJBEvent.CONFIG_ACTIVITY_CREATE: //兑换活动创建
				createActivity(inEvent.getActivityDto());
				break;
			case EJBEvent.CONFIG_ACTIVITY_UPDATE:
				updateActivity(inEvent.getActivityDto()); //兑换活动更改
				break;
			case EJBEvent.CONFIG_ACTIVITY_DELETE:
				deleteActivity(inEvent.getActivityDto()); //兑换活动删除
				break;
			case EJBEvent.CONFIG_GOODS_DELETE:
				deleteGoods(inEvent.getGoodsDto()); //兑换货物删除
				break;
			case EJBEvent.CONFIG_GOODS_CREATE:
				createGoods(inEvent.getGoodsDto()); //兑换货物创建
				break;
			case EJBEvent.CONFIG_GOODS_EDIT:
				updateGoods(inEvent.getGoodsDto()); //兑换货物更改
				break;
			case EJBEvent.CONFIG_RULE_DELETE:
				deleteRule(inEvent.getUserPointsExchRuleDto()); //兑换规则删除
				break;
			case EJBEvent.CONFIG_RULE_CREATE:
				createRule(inEvent.getUserPointsExchRuleDto()); //兑换规则创建
				break;
			case EJBEvent.CONFIG_RULE_EDIT:
				updateRule(inEvent.getUserPointsExchRuleDto()); //兑换规则更改
				break;
			case EJBEvent.CONFIG_COND_DELETE:
				deleteCond(inEvent.getUserPointsExchCondDto()); //兑换条件删除
				break;
			case EJBEvent.CONFIG_COND_CREATE:
				createCond(inEvent.getUserPointsExchCondDto()); //兑换条件创建
				break;
			case EJBEvent.CONFIG_COND_EDIT:
				updateCond(inEvent.getUserPointsExchCondDto()); //兑换条件更改
				break;
			case EJBEvent.CONFIG_CUMULATED_RULE_EDIT:
				updateCumulatedRule(inEvent.getCumulatedRuleDto()); //积分兑换累加规则更改
				break;
			case EJBEvent.CONFIG_CUMULATED_RULE_CREATE:
				createCumulatedRule(inEvent.getCumulatedRuleDto()); //积分兑换累加规则创建
				break;
			case EJBEvent.CONFIG_CUMULATED_RULE_DELETE:
				deleteCumulatedRule(inEvent.getCumulatedRuleDto()); //积分兑换累加规则删除
				break;
			case EJBEvent.CONFIG_MARKET_SEGMENT_EDIT:
				updateMarketSegment(inEvent.getMarketSegmentDTO(),inEvent.getMarketSegmentToDistrictDtoCol()); //市场分区更改
				break;
			case EJBEvent.CONFIG_MARKET_SEGMENT_CREATE:
				createMarketSegment(inEvent.getMarketSegmentDTO(),inEvent.getMarketSegmentToDistrictDtoCol()); //市场分区创建
				break;
			case EJBEvent.CONFIG_MARKET_SEGMENT_DELETE:
				deleteMarketSegment(inEvent.getMarketSegmentDTO()); //市场分区删除
				break;

			case EJBEvent.CONFIG_DISTRICT_CREATE:
				createMarketSegmentToDistrict(inEvent
						.getMarketSegmentToDistrictDtoCol(), inEvent
						.getSegmentId()); //市场分区创建
				break;

			default:
				throw new IllegalArgumentException(
						"ConfigActivityEJBEvent中actionType的设置不正确。");
			}
		}catch(ServiceException ce){
	        LogUtility.log(clazz,LogLevel.ERROR,this,ce);
	        throw new CommandException(ce.getMessage());
	    }catch(Throwable unkown) {
		    LogUtility.log(clazz,LogLevel.FATAL, this, unkown);
		    throw new CommandException("发生未知的错误。");
		}
		return response;
	}


	/**
	 * 修改积分兑换活动信息
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
				SystemLogRecorder.keyLog("更新积分兑换活动失败", "更新积分兑换活动", machineName,
						SystemLogRecorder.LOGMODULE_CONFIG, operatorID);
				throw new RuntimeException(
						"UserPointsExchangeActivityDTO update fail, ID:"
								+ dto.getId()
								+ ". Please make sure that dt_lastmod of UserPointsExchangeActivityDTO is set.");
			}

			SystemLogRecorder.createSystemLog(PublicService
					.getRemoteHostAddress(context), PublicService
					.getCurrentOperatorID(context), 0,
					SystemLogRecorder.LOGMODULE_CONFIG, "修改积分兑换活动",
					"修改积分兑换活动，ID：" + dto.getId(),
					SystemLogRecorder.LOGCLASS_NORMAL,
					SystemLogRecorder.LOGTYPE_APP);

		} catch (HomeFactoryException e) {
			LogUtility.log(clazz, LogLevel.ERROR, e);
			throw new ServiceException("查找积分兑换活动时出错，活动ID：" + dto.getId());
		} catch (FinderException e) {
			LogUtility.log(clazz, LogLevel.ERROR, e);
			throw new ServiceException("查找积分兑换活动出错，活动ID：" + dto.getId());
		}
	}

	/**
	 * 创建积分活动
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
			throw new ServiceException("创建积分兑换活动出错。");
		} catch (CreateException e) {
			LogUtility.log(this.getClass(), LogLevel.ERROR, e);
			throw new ServiceException("创建积分兑换活动出错。");
		}
	}

	/**
	 * 删除积分活动
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
			//删除积分活动的同时,也要删除与之相关联的表的记录
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
					SystemLogRecorder.LOGMODULE_CONFIG, "删除积分兑换活动",
					"删除积分兑换活动，ID：" + dto.getId(),
					SystemLogRecorder.LOGCLASS_NORMAL,
					SystemLogRecorder.LOGTYPE_APP);

		} catch (HomeFactoryException e) {
			LogUtility.log(this.getClass(), LogLevel.ERROR, e);
			throw new ServiceException("定位积分兑换活动出错。");
		} catch (FinderException e) {
			LogUtility.log(clazz, LogLevel.ERROR, e);
			throw new ServiceException("查找积分兑换活动出错，活动ID：" + dto.getId());
		}
	}

	/**
	 * 删除积分兑换货物
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
					SystemLogRecorder.LOGMODULE_CONFIG, "删除积分兑换货物",
					"删除积分兑换货物，ID：" + dto.getId(),
					SystemLogRecorder.LOGCLASS_NORMAL,
					SystemLogRecorder.LOGTYPE_APP);

		} catch (HomeFactoryException e) {
			LogUtility.log(this.getClass(), LogLevel.ERROR, e);
			throw new ServiceException("定位积分兑换货物出错。");
		} catch (FinderException e) {
			LogUtility.log(clazz, LogLevel.ERROR, e);
			throw new ServiceException("查找积分兑换货物出错，活动ID：" + dto.getId());
		}
	}

	/**
	 * 创建积分兑换货物
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
					SystemLogRecorder.LOGMODULE_CONFIG, "创建积分兑换货物",
					"创建积分兑换货物，ID：" + goods.getId(),
					SystemLogRecorder.LOGCLASS_NORMAL,
					SystemLogRecorder.LOGTYPE_APP);

		} catch (HomeFactoryException e) {
			LogUtility.log(this.getClass(), LogLevel.ERROR, e);
			throw new ServiceException("创建积分兑换货物出错。");
		} catch (CreateException e) {
			LogUtility.log(this.getClass(), LogLevel.ERROR, e);
			throw new ServiceException("创建积分兑换货物出错。");
		}
	}

	/**
	 * 修改积分兑换货物信息
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
				SystemLogRecorder.keyLog("更新积分兑换货物失败", "更新积分兑换货物", machineName,
						SystemLogRecorder.LOGMODULE_CONFIG, operatorID);
				throw new RuntimeException(
						"UserPointsExchangeGoodsDTO update fail, ID:"
								+ dto.getId()
								+ ". Please make sure that dt_lastmod of UserPointsExchangeGoodsDTO is set.");
			}

			SystemLogRecorder.createSystemLog(PublicService
					.getRemoteHostAddress(context), PublicService
					.getCurrentOperatorID(context), 0,
					SystemLogRecorder.LOGMODULE_CONFIG, "修改积分兑换货物",
					"修改积分兑换货物，ID：" + dto.getId(),
					SystemLogRecorder.LOGCLASS_NORMAL,
					SystemLogRecorder.LOGTYPE_APP);

		} catch (HomeFactoryException e) {
			LogUtility.log(clazz, LogLevel.ERROR, e);
			throw new ServiceException("查找积分兑换货物时出错，货物ID：" + dto.getId());
		} catch (FinderException e) {
			LogUtility.log(clazz, LogLevel.ERROR, e);
			throw new ServiceException("查找积分兑换货物出错，货物ID：" + dto.getId());
		}
	}

	/**
	 * 删除积分兑换规则
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
					SystemLogRecorder.LOGMODULE_CONFIG, "删除积分兑换规则",
					"删除积分兑换规则，ID：" + dto.getId(),
					SystemLogRecorder.LOGCLASS_NORMAL,
					SystemLogRecorder.LOGTYPE_APP);

		} catch (HomeFactoryException e) {
			LogUtility.log(this.getClass(), LogLevel.ERROR, e);
			throw new ServiceException("定位积分兑换规则出错。");
		} catch (FinderException e) {
			LogUtility.log(clazz, LogLevel.ERROR, e);
			throw new ServiceException("查找积分兑换规则出错，活动ID：" + dto.getId());
		}
	}

	/**
	 * 创建积分兑换规则
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
					SystemLogRecorder.LOGMODULE_CONFIG, "创建积分兑换货物",
					"创建积分兑换货物:ID=" + rule.getId(),
					SystemLogRecorder.LOGCLASS_NORMAL,
					SystemLogRecorder.LOGTYPE_APP);

		} catch (HomeFactoryException e) {
			LogUtility.log(this.getClass(), LogLevel.ERROR, e);
			throw new ServiceException("创建积分兑换规则出错。");
		} catch (CreateException e) {
			LogUtility.log(this.getClass(), LogLevel.ERROR, e);
			throw new ServiceException("创建积分兑换规则出错。");
		}
	}

	/**
	 * 修改积分兑换规则信息
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
				SystemLogRecorder.keyLog("更新积分兑换规则信息", "更新积分兑换规则信息",
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
					SystemLogRecorder.LOGMODULE_CONFIG, "修改积分兑换规则",
					"修改积分兑换规则，ID：" + dto.getId(),
					SystemLogRecorder.LOGCLASS_NORMAL,
					SystemLogRecorder.LOGTYPE_APP);

		} catch (HomeFactoryException e) {
			LogUtility.log(clazz, LogLevel.ERROR, e);
			throw new ServiceException("查找积分兑换规则时出错，货物ID：" + dto.getId());
		} catch (FinderException e) {
			LogUtility.log(clazz, LogLevel.ERROR, e);
			throw new ServiceException("查找积分兑换规则出错，货物ID：" + dto.getId());
		}
	}

	/**
	 * 删除积分兑换规则
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
					SystemLogRecorder.LOGMODULE_CONFIG, "删除积分兑换条件",
					"删除积分兑换条件，ID：" + dto.getCondId(),
					SystemLogRecorder.LOGCLASS_NORMAL,
					SystemLogRecorder.LOGTYPE_APP);

		} catch (HomeFactoryException e) {
			LogUtility.log(this.getClass(), LogLevel.ERROR, e);
			throw new ServiceException("定位积分兑换条件出错。");
		} catch (FinderException e) {
			LogUtility.log(clazz, LogLevel.ERROR, e);
			throw new ServiceException("查找积分兑换条件出错，活动ID：" + dto.getCondId());
		}
	}

	/**
	 * 创建积分兑换规则
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
					SystemLogRecorder.LOGMODULE_CONFIG, "创建积分兑换条件",
					"创建积分兑换条件:ID=" + rule.getCondId(),
					SystemLogRecorder.LOGCLASS_NORMAL,
					SystemLogRecorder.LOGTYPE_APP);

		} catch (HomeFactoryException e) {
			LogUtility.log(this.getClass(), LogLevel.ERROR, e);
			throw new ServiceException("创建积分兑换条件出错。");
		} catch (CreateException e) {
			LogUtility.log(this.getClass(), LogLevel.ERROR, e);
			throw new ServiceException("创建积分兑换条件出错。");
		}
	}

	/**
	 * 修改积分兑换规则信息
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
				throw new CommandException("积分活动编号丢失.");
			}
			UserPointsExchangeCond cond = home.findByPrimaryKey(new Integer(dto
					.getCondId()));

			if (cond.ejbUpdate(dto) == -1) {
				 
				throw new ServiceException("更新积分兑换条件信息失败");
				 
			}

			SystemLogRecorder.createSystemLog(PublicService
					.getRemoteHostAddress(context), PublicService
					.getCurrentOperatorID(context), 0,
					SystemLogRecorder.LOGMODULE_CONFIG, "修改积分兑换条件",
					"修改积分兑换条件，ID：" + dto.getCondId(),
					SystemLogRecorder.LOGCLASS_NORMAL,
					SystemLogRecorder.LOGTYPE_APP);

		} catch (HomeFactoryException e) {
			LogUtility.log(clazz, LogLevel.ERROR, e);
			throw new ServiceException("查找积分兑换条件出错，condID：" + dto.getCondId());
		} catch (FinderException e) {
			LogUtility.log(clazz, LogLevel.ERROR, e);
			throw new ServiceException("查找积分兑换条件出错，condID：" + dto.getCondId());
		}
	}

	/**
	 * 修改积分兑换累加规则信息
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
				SystemLogRecorder.keyLog("修改积分兑换累加规则信息", "修改积分兑换累加规则信息",
						machineName, SystemLogRecorder.LOGMODULE_CONFIG,
						operatorID);
				throw new ServiceException("更新积分兑换累加规则失败");
			}

			SystemLogRecorder.createSystemLog(PublicService
					.getRemoteHostAddress(context), PublicService
					.getCurrentOperatorID(context), 0,
					SystemLogRecorder.LOGMODULE_CONFIG, "修改积分兑换累加规则信息",
					"修改积分兑换累加规则信息，ID：" + dto.getId(),
					SystemLogRecorder.LOGCLASS_NORMAL,
					SystemLogRecorder.LOGTYPE_APP);

		} catch (HomeFactoryException e) {
			LogUtility.log(clazz, LogLevel.ERROR, e);
			throw new ServiceException("查找积分兑换累加规则出错，ID：" + dto.getId());
		} catch (FinderException e) {
			LogUtility.log(clazz, LogLevel.ERROR, e);
			throw new ServiceException("查找兑换累加规则出错，ID：" + dto.getId());
		}
	}

	/**
	 * 创建积分兑换累加规则信息
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

			//       	插入相关的日志记录。

			SystemLogRecorder.createSystemLog(PublicService
					.getRemoteHostAddress(context), PublicService
					.getCurrentOperatorID(context), 0,
					SystemLogRecorder.LOGMODULE_CONFIG, "创建积分兑换累加规则",
					"创建积分兑换累加规则:ID：" + cumulatedRule.getId(),
					SystemLogRecorder.LOGCLASS_NORMAL,
					SystemLogRecorder.LOGTYPE_APP);

		} catch (HomeFactoryException e) {
			LogUtility.log(this.getClass(), LogLevel.ERROR, e);
			throw new ServiceException("查找积分兑换累加规则接口出错。");

		} catch (CreateException e) {
			LogUtility.log(this.getClass(), LogLevel.ERROR, e);
			throw new ServiceException("创建积分兑换累加规则信息出错。");
		}
	}

	/**
	 * 删除积分兑换累加规则信息
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
					SystemLogRecorder.LOGMODULE_CONFIG, "删除积分兑换累加规则信息",
					"删除积分兑换累加规则信息:ID：" + dto.getId(),
					SystemLogRecorder.LOGCLASS_NORMAL,
					SystemLogRecorder.LOGTYPE_APP);

		} catch (HomeFactoryException e) {
			LogUtility.log(this.getClass(), LogLevel.ERROR, e);
			throw new ServiceException("定位积分兑换累加规则信息出错。");
		} catch (FinderException e) {
			LogUtility.log(clazz, LogLevel.ERROR, e);
			throw new ServiceException("查找积分兑换累加规则信息出错，ID：" + dto.getId());
		}
	}

	/**
	 * 创建市场分区
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
			//       	插入相关的日志记录。

			SystemLogRecorder.createSystemLog(PublicService
					.getRemoteHostAddress(context), PublicService
					.getCurrentOperatorID(context), 0,
					SystemLogRecorder.LOGMODULE_CONFIG, "创建市场分区", "创建市场分区:ID："
							+ marketSegment.getId(),
					SystemLogRecorder.LOGCLASS_NORMAL,
					SystemLogRecorder.LOGTYPE_APP);

		} catch (HomeFactoryException e) {
			LogUtility.log(this.getClass(), LogLevel.ERROR, e);
			throw new ServiceException("查找创建市场分区接口出错。");

		} catch (CreateException e) {
			LogUtility.log(this.getClass(), LogLevel.ERROR, e);
			throw new ServiceException("创建创建市场分区信息出错。");
		}
	}

	/**
	 * 修改市场分区
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
				SystemLogRecorder.keyLog("修改市场分区", "修改市场分区", machineName,
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
					SystemLogRecorder.LOGMODULE_CONFIG, "修改市场分区", "修改市场分区，ID："
							+ dto.getId(), SystemLogRecorder.LOGCLASS_NORMAL,
					SystemLogRecorder.LOGTYPE_APP);

		} catch (HomeFactoryException e) {
			LogUtility.log(clazz, LogLevel.ERROR, e);
			throw new ServiceException("查找市场分区出错，ID：" + dto.getId());
		} catch (FinderException e) {
			LogUtility.log(clazz, LogLevel.ERROR, e);
			throw new ServiceException("查找市场分区出错，ID：" + dto.getId());
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
	 * 删除市场分区信息
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
				LogUtility.log(clazz, LogLevel.WARN, "传入的参数为空，或则主键为空!");
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
					SystemLogRecorder.LOGMODULE_CONFIG, "删除市场分区信息",
					"删除市场分区信息:ID：" + dto.getId(),
					SystemLogRecorder.LOGCLASS_NORMAL,
					SystemLogRecorder.LOGTYPE_APP);

		} catch (HomeFactoryException e) {
			LogUtility.log(this.getClass(), LogLevel.ERROR, e);
			throw new ServiceException("定位市场分区信息出错。");
		} catch (FinderException e) {
			LogUtility.log(clazz, LogLevel.ERROR, e);
			throw new ServiceException("查找市场分区信息出错，ID：" + dto.getId());
		}
	}

	/**
	 * 删除市场分区信息
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
				LogUtility.log(clazz, LogLevel.WARN, "传入的参数为空，不能修改市场分区!");
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
					SystemLogRecorder.LOGMODULE_CONFIG, "删除市场分区,分区ID:"
							+ dto.getMarketSegmentId(), "删除市场分区下的行政信息:ID："
							+ dto.getDistrictId(),
					SystemLogRecorder.LOGCLASS_NORMAL,
					SystemLogRecorder.LOGTYPE_APP);

		} catch (HomeFactoryException e) {
			LogUtility.log(this.getClass(), LogLevel.ERROR, e);
			throw new ServiceException("定位行政分区信息出错。");
		} catch (FinderException e) {
			LogUtility.log(clazz, LogLevel.ERROR, e);
			throw new ServiceException("查找行政分区信息出错");
		}
	}

	/**
	 * 创建市场分区
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

			//       	插入相关的日志记录。

			SystemLogRecorder.createSystemLog(PublicService
					.getRemoteHostAddress(context), PublicService
					.getCurrentOperatorID(context), 0,
					SystemLogRecorder.LOGMODULE_CONFIG, "创建市场分区", "在市场分区ID :"
							+ segmentId + "下创建市场分区:ID：",
					SystemLogRecorder.LOGCLASS_NORMAL,
					SystemLogRecorder.LOGTYPE_APP);

		} catch (HomeFactoryException e) {
			LogUtility.log(this.getClass(), LogLevel.ERROR, e);
			throw new ServiceException("查找创建市场分区接口出错。");

		} catch (CreateException e) {
			LogUtility.log(this.getClass(), LogLevel.ERROR, e);
			throw new ServiceException("创建创建市场分区信息出错。");
		} catch (FinderException e) {
			LogUtility.log(clazz, LogLevel.ERROR, e);
			throw new ServiceException("查找市场分区信息出错");
		} catch (RemoveException e) {
			LogUtility.log(clazz, LogLevel.ERROR, e);
			throw new ServiceException("删除市场分区信息出错");
		}
	}

	/**
	 * 初始化ServiceContext
	 * 将一些通用的信息放入ServiceContext
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
