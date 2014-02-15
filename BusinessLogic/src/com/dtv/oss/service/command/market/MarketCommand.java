/*
 * Created on 2006-3-20
 * 
 * @author chen jiang
 * 
 * 与市场活动的有关操作command
 */
package com.dtv.oss.service.command.market;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.FinderException;
import javax.ejb.ObjectNotFoundException;
import javax.ejb.RemoveException;

import com.dtv.oss.domain.BundlePaymentMethod;
import com.dtv.oss.domain.BundlePaymentMethodHome;
import com.dtv.oss.domain.BundlePaymentMethodPK;
import com.dtv.oss.domain.BundlePrepayment;
import com.dtv.oss.domain.BundlePrepaymentHome;
import com.dtv.oss.domain.CAWalletDefine;
import com.dtv.oss.domain.CAWalletDefineHome;
import com.dtv.oss.domain.Campaign;
import com.dtv.oss.domain.CampaignAgmtCampaign;
import com.dtv.oss.domain.CampaignAgmtCampaignHome;
import com.dtv.oss.domain.CampaignAgmtCampaignPK;
import com.dtv.oss.domain.CampaignCondCampaign;
import com.dtv.oss.domain.CampaignCondCampaignHome;
import com.dtv.oss.domain.CampaignCondPackage;
import com.dtv.oss.domain.CampaignCondPackageHome;
import com.dtv.oss.domain.CampaignCondProduct;
import com.dtv.oss.domain.CampaignCondProductHome;
import com.dtv.oss.domain.CampaignHome;
import com.dtv.oss.domain.DtvMigrationArea;
import com.dtv.oss.domain.DtvMigrationAreaHome;
import com.dtv.oss.domain.GroupBargain;
import com.dtv.oss.domain.GroupBargainDetail;
import com.dtv.oss.domain.GroupBargainDetailHome;
import com.dtv.oss.domain.GroupBargainHome;
import com.dtv.oss.domain.CampaignPaymentAwardHome;
import com.dtv.oss.domain.CampaignPaymentAward;
import com.dtv.oss.dto.BundlePaymentMethodDTO;
import com.dtv.oss.dto.BundlePrepaymentDTO;
import com.dtv.oss.dto.CAWalletDefineDTO;
import com.dtv.oss.dto.CampaignAgmtCampaignDTO;
import com.dtv.oss.dto.CampaignDTO;
import com.dtv.oss.dto.DtvMigrationAreaDTO;
import com.dtv.oss.dto.GroupBargainDTO;
import com.dtv.oss.dto.GroupBargainDetailDTO;
import com.dtv.oss.dto.CampaignPaymentAwardDTO;
import com.dtv.oss.dto.wrap.CreateGroupBargainResult;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.Service;
import com.dtv.oss.service.ServiceContext;
import com.dtv.oss.service.ServiceException;
import com.dtv.oss.service.command.Command;
import com.dtv.oss.service.command.CommandException;
import com.dtv.oss.service.commandresponse.CommandResponse;
import com.dtv.oss.service.commandresponse.CommandResponseImp;
import com.dtv.oss.service.component.MarketService;
import com.dtv.oss.service.component.PublicService;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.market.MarketEJBEvent;
import com.dtv.oss.service.factory.HomeFactoryException;
import com.dtv.oss.service.util.BusinessUtility;
import com.dtv.oss.service.util.CommonConstDefinition;
import com.dtv.oss.service.util.HomeLocater;
import com.dtv.oss.service.util.SystemLogRecorder;
import com.dtv.oss.util.TimestampUtility;

public class MarketCommand extends Command {
	private static final Class clazz = MarketCommand.class;

	private int operatorID = 0;

	private String machineName = "";

	CommandResponseImp response = null;

	public CommandResponse execute(EJBEvent ev) throws CommandException {
		response = new CommandResponseImp(null);
		MarketEJBEvent inEvent = (MarketEJBEvent) ev;
		operatorID = inEvent.getOperatorID();
		machineName = ev.getRemoteHostAddress();
		LogUtility.log(clazz, LogLevel.DEBUG, "Enter execute() now.");

		try {
			switch (inEvent.getActionType()) {

			case EJBEvent.CAMPAIGN_CREATE: // 促销活动创建
				createCampaign(inEvent);
				break;
			case EJBEvent.CAMPAIGN_UPDATE:
				updateCampaign(inEvent); // 促销活动更改
				break;
			case EJBEvent.GROUPBARGAIN_CREATE:
				createGroupBargain(inEvent); // 创建团购券
				break;
			case EJBEvent.GROUPBARGAIN_UPDATE:
				updateGroupBargain(inEvent.getGroupBargainDto()); // 更新团购合同
				break;
			case EJBEvent.GROUPBARGAIN_DELETE:
				deleteGroupBargain(inEvent.getGroupBargainDto()); // 取消团购合同
				break;
			case EJBEvent.GROUPBARGAIN_SALE:
				saleGroupBargain(inEvent.getGroupBargainDto()); // 售出团购合同
				break;
			case MarketEJBEvent.GROUPBARGAIN_DETAIL_DELETE:
				detailDelete(inEvent.getDetailDto());
				break;
			case EJBEvent.CAMPAIGN_COND_CREATE:
				createCondForCampaign(inEvent); // 创建促销活动条件
				break;
			case EJBEvent.CAMPAIGN_COND_UPDATE:
				modifyCondForCampaign(inEvent); // 修改促销活动条件
				break;
			case MarketEJBEvent.CAMPAIGN_COND_PRODUCT:
				productCondDelete(inEvent.getSeqNo());
				break;
			case MarketEJBEvent.CAMPAIGN_COND_PACKAGE:
				packageCondDelete(inEvent.getSeqNo());
			case MarketEJBEvent.CAMPAIGN_COND_CAMPAIGN:
				campaignCondDelete(inEvent.getSeqNo());
				break;
			case MarketEJBEvent.CAMPAIGN_PAYMENTMETHOD_DELETE:
				bundlePayMethDelete(inEvent.getBundlePayMethodDto());
				break;
			case MarketEJBEvent.BUNDLE_PREPAYMENT_DELETE:
				bundlePrementDelete(inEvent.getBundPreMethDto());
				break;
			case MarketEJBEvent.AGMT_CAMPAIGN_DELETE:
				agmtCampaignDelete(inEvent.getAgmtCampaignDto());
				break;
			case EJBEvent.CAMPAIGN_PAYMENT_AWARD_CREATE: // 促销活动 奖励金额 新增
				createCampaignPaymentAward(inEvent.getCampaignPaymentAwardDto());
				break;
			case EJBEvent.CAMPAIGN_PAYMENT_AWARD_DELETE: // 促销活动 奖励金额 删除
				deleteCampaignPaymentAward(inEvent.getCampaignPaymentAwardDto());
				break;

			case EJBEvent.IPPV_CREATE: // IPPV钱包创建
				createIppvWallet(inEvent);
				break;
			case EJBEvent.IPPV_UPDATE:
				updateIppvWallet(inEvent); // IPPV钱包更改
				break;
			case EJBEvent.IPPV_DELETE:
				deleteIppvWallet(inEvent); // IPPV钱包删除
				break;

			case EJBEvent.DTV_MGT_CREATE: // 平移小区创建
				createDtvMigration(inEvent);
				break;
			case EJBEvent.DTV_MGT_UPDATE: // 平移小区更改
				updateDtvMigration(inEvent);
				break;

			default:
				throw new IllegalArgumentException(
						"MarketEJBEvent中actionType的设置不正确。");
			}
		} catch (ServiceException ce) {
			LogUtility.log(clazz, LogLevel.ERROR, this, ce);
			throw new CommandException(ce.getMessage());
		} catch (Throwable unkown) {
			LogUtility.log(clazz, LogLevel.FATAL, this, unkown);
			throw new CommandException("发生未知的错误。");
		}
		LogUtility.log(clazz, LogLevel.DEBUG, "Leave execute() now.");
		return response;
	}

	/**
	 * @param inEvent
	 */
	private void createCondForCampaign(MarketEJBEvent inEvent)
			throws ServiceException {
		ServiceContext context = initServiceContext();

		MarketService ms = new MarketService(context);
		ms.create(inEvent);
	}

	/**
	 * @param inEvent
	 */
	private void modifyCondForCampaign(MarketEJBEvent inEvent)
			throws ServiceException {
		ServiceContext context = initServiceContext();

		MarketService ms = new MarketService(context, inEvent.getCampaignID());
		ms.updateCond(inEvent);
	}

	/**
	 * @param groupBargainDto
	 */
	private void updateGroupBargain(GroupBargainDTO groupBargainDto)
			throws ServiceException {

		try {
			ServiceContext context = initServiceContext();
			GroupBargainHome gbHome = HomeLocater.getGroupBargainHome();

			GroupBargain groupBargain = gbHome.findByPrimaryKey(new Integer(
					groupBargainDto.getId()));
			if (!groupBargain.getStatus().equals(
					CommonConstDefinition.GROUPBARGAIN_STATUS_NEW)) {

				throw new ServiceException("团购券只能处于创建状态，已经售出的团购券不能修改");
			}

			if (groupBargain.ejbUpdate(groupBargainDto) == -1) {
				SystemLogRecorder.keyLog("更新团购券失败", "更新团购券", machineName,
						SystemLogRecorder.LOGMODULE_CUSTSERV, operatorID);
				throw new RuntimeException(
						"groupBargainDto update fail, ID:"
								+ groupBargainDto.getId()
								+ ". Please make sure that dt_lastmod of groupBargainDto is set.");
			}

			SystemLogRecorder.createSystemLog(PublicService
					.getRemoteHostAddress(context), PublicService
					.getCurrentOperatorID(context), 0,
					SystemLogRecorder.LOGMODULE_CONFIG, "修改团购券", "修改团购券，ID："
							+ groupBargainDto.getId(),
					SystemLogRecorder.LOGCLASS_NORMAL,
					SystemLogRecorder.LOGTYPE_APP);

		} catch (HomeFactoryException e) {
			LogUtility.log(clazz, LogLevel.ERROR, e);
			throw new ServiceException("查找团购券时出错，团购券ID："
					+ groupBargainDto.getId());
		} catch (FinderException e) {
			LogUtility.log(clazz, LogLevel.ERROR, e);
			throw new ServiceException("查找团购券出错，团购券ID："
					+ groupBargainDto.getId());
		}
	}

	private void deleteGroupBargain(GroupBargainDTO dto)
			throws ServiceException {

		try {

			GroupBargainHome gbHome = HomeLocater.getGroupBargainHome();
			GroupBargainDetailHome gbDetailHome = HomeLocater
					.getGroupBargainDetailHome();
			GroupBargain groupBargain = gbHome.findByPrimaryKey(new Integer(dto
					.getId()));
			if (!groupBargain.getStatus().equals(
					CommonConstDefinition.GROUPBARGAIN_STATUS_NEW)) {
				LogUtility.log(clazz, LogLevel.WARN, "团购券只能处于新建状态才可以删除！");
				throw new ServiceException("团购券只能处于新建状态才可以删除！");

			}

			groupBargain
					.setStatus(CommonConstDefinition.GROUPBARGAIN_STATUS_CANCEL);
			groupBargain.setDtLastmod(TimestampUtility.getCurrentTimestamp());

			Collection list = gbDetailHome.findByGroupBargainID(dto.getId());
			int i = 0;
			for (Iterator it = list.iterator(); it.hasNext();) {
				GroupBargainDetail detail = (GroupBargainDetail) it.next();
				if (!CommonConstDefinition.GROUPBARGAINDETAIL_STATUS_WAIT
						.equals(detail.getStatus())) {
					continue;

				}
				i++;
				detail
						.setStatus(CommonConstDefinition.GROUPBARGAINDETAIL_STATUS_CANCEL);
				detail.setDtLastmod(TimestampUtility.getCurrentTimestamp());
			}
			groupBargain.setAbortsheets(groupBargain.getAbortsheets() + i);

			// 插入相关的日志记录。
			SystemLogRecorder.keyLog("删除团购", "删除团购", machineName,
					SystemLogRecorder.LOGMODULE_CUSTSERV, operatorID);
		} catch (HomeFactoryException e) {
			LogUtility.log(clazz, LogLevel.ERROR, e);
			throw new ServiceException("查找团购券时出错，团购券ID：" + dto.getId());
		} catch (FinderException e) {
			LogUtility.log(clazz, LogLevel.ERROR, e);
			throw new ServiceException("查找团购券出错，团购券ID：" + dto.getId());
		}
	}

	private void saleGroupBargain(GroupBargainDTO dto) throws ServiceException {
		try {

			GroupBargainHome gbHome = HomeLocater.getGroupBargainHome();
			GroupBargainDetailHome gbDetailHome = HomeLocater
					.getGroupBargainDetailHome();
			GroupBargain groupBargain = gbHome.findByPrimaryKey(new Integer(dto
					.getId()));
			int i = 0;

			Collection list = gbDetailHome.findByGroupBargainID(dto.getId());
			for (Iterator it = list.iterator(); it.hasNext();) {
				// 计算使用的张数

				GroupBargainDetail detail = (GroupBargainDetail) it.next();
				if (!CommonConstDefinition.GROUPBARGAINDETAIL_STATUS_WAIT
						.equals(detail.getStatus())) {
					continue;

				}
				i++;

				detail
						.setStatus(CommonConstDefinition.GROUPBARGAIN_STATUS_SALED);
				detail.setDtLastmod(TimestampUtility.getCurrentTimestamp());
			}
			//wangpeng@20080225,注释了下面语句，并加入一条语句
			//groupBargain.setUsedSheets(i);
			groupBargain.setUsedSheets(0);
			
			
			
			groupBargain
					.setStatus(CommonConstDefinition.GROUPBARGAIN_STATUS_SALED);
			groupBargain.setDtLastmod(TimestampUtility.getCurrentTimestamp());

			// 插入相关的日志记录。
			SystemLogRecorder.keyLog("团购券售出,团购券ID" + dto.getId(), "团购券售出",
					machineName, SystemLogRecorder.LOGMODULE_CUSTSERV,
					operatorID);

		} catch (HomeFactoryException e) {
			LogUtility.log(clazz, LogLevel.ERROR, e);
			throw new ServiceException("查找团购券时出错，团购券ID：" + dto.getId());
		} catch (FinderException e) {
			LogUtility.log(clazz, LogLevel.ERROR, e);
			throw new ServiceException("查找团购券出错，团购券ID：" + dto.getId());
		} catch (ServiceException e) {
			e.printStackTrace();
		}
	}

	// 促销活动创建
	private void createCampaign(MarketEJBEvent marketEvent)
			throws ServiceException {

		ServiceContext context = initServiceContext();
		CampaignDTO campaignDto = marketEvent.getCampaignDto();
		campaignDto.setTimeLengthUnitType("M");
		MarketService ms = new MarketService(context);

		ms.createCampaign(campaignDto, marketEvent);
		response.setPayload(new Integer(ms.getCampaignID()));
		String description = "促销活动创建， 活动ID" + ms.getCampaignID();
		SystemLogRecorder.keyLog(description, "促销活动创建", machineName,
				SystemLogRecorder.LOGMODULE_CONFIG, operatorID);

	}

	// 促销活动条件产品删除
	private void productCondDelete(int seqNo) throws ServiceException {

		try {

			CampaignCondProductHome prodHome = HomeLocater
					.getCampaignCondProductHome();
			CampaignCondProduct prod = prodHome.findByPrimaryKey(new Integer(
					seqNo));
			if (prod != null)
				prod.remove();
		} catch (EJBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RemoveException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FinderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (HomeFactoryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// 促销活动条件产品包删除
	private void packageCondDelete(int seqNo) throws ServiceException {

		try {

			CampaignCondPackageHome packageHome = HomeLocater
					.getCampaignCondPackageHome();
			CampaignCondPackage pack = packageHome
					.findByPrimaryKey(new Integer(seqNo));
			if (pack != null)
				pack.remove();
		} catch (EJBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RemoveException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FinderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (HomeFactoryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// 套餐续费方式删除
	private void bundlePayMethDelete(BundlePaymentMethodDTO dto)
			throws ServiceException {

		try {
			if (dto == null) {
				throw new ServiceException("BundlePaymentMethodDTO没有值");
			}
			int bundleID = dto.getBundleID();
			String flag = dto.getRfBillingCycleFlag();
			BundlePaymentMethodHome bundleHome = HomeLocater
					.getBundlePaymentMethodHome();
			BundlePaymentMethodPK pk = new BundlePaymentMethodPK(bundleID, flag);
			BundlePaymentMethod campaign = bundleHome.findByPrimaryKey(pk);
			if (campaign != null)
				campaign.remove();
		} catch (EJBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RemoveException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FinderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (HomeFactoryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// 活动协议删除
	private void agmtCampaignDelete(CampaignAgmtCampaignDTO dto)
			throws ServiceException {

		try {
			if (dto == null) {
				throw new ServiceException("CampaignAgmtCampaignDTO没有值");
			}
			int bundleID = dto.getTargetBundleId();
			int campaignId = dto.getCampaignId();
			CampaignAgmtCampaignHome bundleHome = HomeLocater
					.getCampaignAgmtCampaignHome();
			CampaignAgmtCampaignPK pk = new CampaignAgmtCampaignPK(campaignId,
					bundleID);
			CampaignAgmtCampaign campaign = bundleHome.findByPrimaryKey(pk);
			if (campaign != null)
				campaign.remove();
		} catch (EJBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RemoveException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FinderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (HomeFactoryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// 套餐预付设置删除
	private void bundlePrementDelete(BundlePrepaymentDTO dto)
			throws ServiceException {

		try {
			if (dto == null) {
				throw new ServiceException("BundlePrepaymentDTO没有值");
			}
			int campaignID = dto.getCampaignId();

			BundlePrepaymentHome bundleHome = HomeLocater
					.getBundlePrepaymentHome();

			BundlePrepayment campaign = bundleHome
					.findByPrimaryKey(new Integer(campaignID));
			if (campaign != null)
				campaign.remove();
		} catch (EJBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RemoveException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FinderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (HomeFactoryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// 促销活动条件活动删除
	private void campaignCondDelete(int seqNo) throws ServiceException {

		try {

			CampaignCondCampaignHome camHome = HomeLocater
					.getCampaignCondCampaignHome();
			CampaignCondCampaign campaign = camHome
					.findByPrimaryKey(new Integer(seqNo));
			if (campaign != null)
				campaign.remove();
		} catch (EJBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RemoveException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FinderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (HomeFactoryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * private void createCampaign(MarketEJBEvent marketEvent) throws
	 * ServiceException { if (marketEvent.getCamOldProductCondDtoCol() !=null &&
	 * marketEvent.getCamPackageCondDtoCol() !=null) throw new
	 * ServiceException("产品包,产品只能选择其中一组"); ServiceContext context =
	 * initServiceContext(); CampaignDTO campaignDto =
	 * marketEvent.getCampaignDto(); campaignDto.setTimeLengthUnitType("M");
	 * MarketService ms = new MarketService(context);
	 * ms.create(campaignDto,marketEvent.getCamOldProductCondDtoCol(),marketEvent.getCamPackageCondDtoCol(),marketEvent.getCamToMarketDtoCol());
	 * String description = "促销活动创建， 活动ID"+ms.getCampaignID();
	 * SystemLogRecorder.keyLog(description, "促销活动创建", machineName,
	 * SystemLogRecorder.LOGMODULE_CONFIG, operatorID, ms.getCampaignID()); }
	 */
	// 促销活动更改
	private void updateCampaign(MarketEJBEvent marketEvent)
			throws ServiceException {
		/**
		 * if (marketEvent.getCamOldProductCondDtoCol() !=null &&
		 * marketEvent.getCamPackageCondDtoCol() !=null) throw new
		 * ServiceException("产品包,产品只能选择其中一组");
		 */
		CampaignDTO campaignDto = marketEvent.getCampaignDto();

		try {
			CampaignHome cpHome = HomeLocater.getCampaignHome();
			ServiceContext context = initServiceContext();

			Campaign campaign;
			campaign = cpHome.findByPrimaryKey(new Integer(campaignDto
					.getCampaignID()));
			if ((campaign.ejbUpdate(marketEvent.getCampaignDto())) == -1) {
				SystemLogRecorder.keyLog("更新优惠失败", "更新优惠", machineName,
						SystemLogRecorder.LOGMODULE_CONFIG, operatorID);
				throw new ServiceException("更新优惠失败");
			}

			MarketService ms = new MarketService(context, marketEvent
					.getCampaignID());
			ms.updateCampaign(marketEvent);

			SystemLogRecorder.createSystemLog(PublicService
					.getRemoteHostAddress(context), PublicService
					.getCurrentOperatorID(context), 0,
					SystemLogRecorder.LOGMODULE_CONFIG, "促销活动更改",
					"促销活动更改，活动ID：" + campaignDto.getCampaignID(),
					SystemLogRecorder.LOGCLASS_NORMAL,
					SystemLogRecorder.LOGTYPE_SYSTEM);
		}

		catch (HomeFactoryException e1) {

			throw new ServiceException("促销活动定位出错！");
		} catch (FinderException e2) {

			e2.printStackTrace();
		}

	}

	// 创建团购券
	private void createGroupBargain(MarketEJBEvent marketEvent)
			throws ServiceException {

		int startNum = marketEvent.getFromBegin();

		String detailNoFormat = marketEvent.getFormat();
		GroupBargainDTO dto = marketEvent.getGroupBargainDto();
		int size = dto.getTotalSheets();
		if (startNum < 0 || size < 1 || startNum + size > Integer.MAX_VALUE
				|| detailNoFormat.indexOf('*') == -1) {
			throw new ServiceException("传入参数不合法，detailNoFormat="
					+ detailNoFormat + ", from=" + startNum + ", size=" + size);
		}
		dto.setCreateTime(TimestampUtility.getCurrentDate());
		dto.setUsedSheets(0);
		dto.setAbortSheets(0);
		dto.setStatus(CommonConstDefinition.GROUPBARGAIN_STATUS_NEW);
		// 创建团购券
		try {

			ServiceContext context = initServiceContext();
			MarketService ms = new MarketService(context);
			GroupBargain gb = ms.create(dto);
			dto.setDtLastmod(gb.getDtLastmod());
			dto.setDtCreate(gb.getDtCreate());

			dto.setId(ms.getGroupBargainID());
			int formatSize = detailNoFormat.lastIndexOf('*')
					- detailNoFormat.indexOf("*") + 1;
			if (Math.pow(10, formatSize) < startNum + size) {
				throw new ServiceException("传入参数不合法，detailNoFormat="
						+ detailNoFormat + ", from=" + startNum + ", size="
						+ size + ", 可能是编码格式太短造成的。");
			}

			String formatHead = detailNoFormat.substring(0, detailNoFormat
					.indexOf('*'));
			String detailNo = "0000000000";
			List detailList = new ArrayList();

			GroupBargainDetailHome groupBargainDetailHome = HomeLocater
					.getGroupBargainDetailHome();

			for (int i = startNum; i < startNum + size; i++) {
				GroupBargainDetailDTO detailDto = new GroupBargainDetailDTO();
				detailDto.setGroupBargainID(ms.getGroupBargainID());
				detailDto
						.setStatus(CommonConstDefinition.GROUPBARGAINDETAIL_STATUS_WAIT);
				detailDto.setDetailNo(formatHead
						+ (detailNo + i).substring((detailNo + i).length()
								- formatSize));

				int tmp = BusinessUtility.findIDByDetailNo(detailDto
						.getDetailNo());
				System.out.println("*****tmptmptmp**********" + tmp);
				if (tmp > 0)
					throw new ServiceException("detailNO="
							+ detailDto.getDetailNo() + "已被使用。");

				GroupBargainDetail detail = groupBargainDetailHome
						.create(detailDto);

				detailDto.setId(detail.getId().intValue());
				detailDto.setDtCreate(detail.getDtCreate());
				detailDto.setDtLastmod(detail.getDtLastmod());
				detailList.add(detailDto);

			}

			response.setPayload(new CreateGroupBargainResult(dto, detailList));

		}

		catch (HomeFactoryException e1) {

			throw new ServiceException("促销活动定位出错！");
		} catch (CreateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void detailDelete(GroupBargainDetailDTO dto)
			throws ServiceException {

		try {
			ServiceContext context = initServiceContext();
			GroupBargainHome groupBarHome = HomeLocater.getGroupBargainHome();
			GroupBargainDetailHome groupBarDetailHome = HomeLocater
					.getGroupBargainDetailHome();
			LogUtility.log(clazz, LogLevel.DEBUG, "GroupBargainDetailDTO： "
					+ dto);
			GroupBargain groupBargain = groupBarHome
					.findByPrimaryKey(new Integer(dto.getGroupBargainID()));
			LogUtility.log(clazz, LogLevel.DEBUG, "groupBargain的状态为： "
					+ groupBargain.getStatus());
			if (!groupBargain.getStatus().equals(
					CommonConstDefinition.GROUPBARGAIN_STATUS_NEW)) {
				throw new ServiceException("只有处于创建状态的团购券才能删除");
			}
			groupBargain.setAbortsheets(groupBargain.getAbortsheets() + 1);
			groupBargain.setDtLastmod(TimestampUtility.getCurrentTimestamp());
			GroupBargainDetail detail = groupBarDetailHome
					.findByPrimaryKey(new Integer(dto.getId()));
			LogUtility.log(clazz, LogLevel.DEBUG, "groupBarDetailHome：的状态为 "
					+ detail.getStatus());
			if (!detail.getStatus().equals(
					CommonConstDefinition.GROUPBARGAINDETAIL_STATUS_WAIT)) {

				throw new ServiceException("只有处于创建状态的团购券才能删除，已经售出的团购券不能删除");
			}

			detail
					.setStatus(CommonConstDefinition.GROUPBARGAINDETAIL_STATUS_CANCEL);
			detail.setDtLastmod(TimestampUtility.getCurrentTimestamp());
			SystemLogRecorder.createSystemLog(PublicService
					.getRemoteHostAddress(context), PublicService
					.getCurrentOperatorID(context), dto.getId(),
					SystemLogRecorder.LOGMODULE_CONFIG, "团购券删除", "团购券删除，团购券ID："
							+ dto.getId(), SystemLogRecorder.LOGCLASS_NORMAL,
					SystemLogRecorder.LOGTYPE_SYSTEM);

		} catch (HomeFactoryException e) {
			LogUtility.log(clazz, LogLevel.ERROR, e);
			throw new ServiceException("查找团购券时出错，团购券ID：" + dto.getId());
		} catch (FinderException e) {
			LogUtility.log(clazz, LogLevel.ERROR, e);
			throw new ServiceException("查找团购券出错，团购券ID：" + dto.getId());
		}
	}

	/**
	 * 系统配置，套餐、优惠活动对应的奖励金额 新增
	 * 
	 * @param inEvent
	 */
	private void createCampaignPaymentAward(CampaignPaymentAwardDTO dto)
			throws ServiceException {
		ServiceContext context = initServiceContext();

		MarketService ms = new MarketService(context);
		ms.createCampaignPaymentAward(dto);
	}

	// 系统配置，套餐、优惠活动对应的奖励金额 删除
	private void deleteCampaignPaymentAward(CampaignPaymentAwardDTO dto)
			throws ServiceException {

		try {
			if (dto == null) {
				throw new ServiceException("CampaignAgmtCampaignDTO没有值");
			}
			int seqNo = dto.getSeqNo();
			CampaignPaymentAwardHome campayHome = HomeLocater
					.getCampaignPaymentAwardHome();
			CampaignPaymentAward campaignPaymentAward = campayHome
					.findByPrimaryKey(new Integer(seqNo));
			if (campaignPaymentAward != null)
				campaignPaymentAward.remove();
		} catch (EJBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RemoveException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FinderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (HomeFactoryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// IPPV钱包创建
	private void createIppvWallet(MarketEJBEvent marketEvent)
			throws ServiceException {

		ServiceContext context = initServiceContext();
		CAWalletDefineDTO ippvDTO = marketEvent.getIppvDTO();
		MarketService ms = new MarketService(context);

		String caCode = ippvDTO.getCaWalletCode();

		ms.createIppvWallet(ippvDTO, marketEvent);

		// 系统日志
		StringBuffer logDesc = new StringBuffer();
		logDesc.append("新建IPPV钱包资料,");
		logDesc.append("IPPV钱包编码:");
		logDesc.append(caCode);

		logDesc.append(";钱包名称:" + ippvDTO.getCaWalletName());

		logDesc.append(";依赖设备列表:" + ippvDTO.getDeviceModelList());

		logDesc.append(";是否必须:" + ippvDTO.getRequired());

		logDesc.append(";兑换率:" + ippvDTO.getRate());

		String sysModule = "";

		sysModule = SystemLogRecorder.LOGMODULE_CONFIG;

		SystemLogRecorder.createSystemLog(machineName, operatorID, 0,
				sysModule, "新建IPPV钱包资料", logDesc.toString(),
				SystemLogRecorder.LOGCLASS_NORMAL,
				SystemLogRecorder.LOGTYPE_APP);

		response.setPayload(ms.getIppvWalletCode());

	}

	// Ippv钱包更改
	private void updateIppvWallet(MarketEJBEvent marketEvent)
			throws ServiceException {
		/**
		 * if (marketEvent.getCamOldProductCondDtoCol() !=null &&
		 * marketEvent.getCamPackageCondDtoCol() !=null) throw new
		 * ServiceException("产品包,产品只能选择其中一组");
		 */
		CAWalletDefineDTO ippvDTO = marketEvent.getIppvDTO();

		try {

			List oldCAList = BusinessUtility.getCAWalletDefineByCode(ippvDTO
					.getCaWalletCode());

			CAWalletDefineDTO oldCA = (CAWalletDefineDTO) oldCAList.get(0);

			CAWalletDefineHome caHome = HomeLocater.getCAWalletDefineHome();
			CAWalletDefine caWallet;
			caWallet = caHome.findByPrimaryKey(ippvDTO.getCaWalletCode());

			if ((caWallet.ejbUpdate(marketEvent.getIppvDTO())) == -1) {
				SystemLogRecorder.keyLog("更新IPPV钱包失败", "更新IPPV钱包", machineName,
						SystemLogRecorder.LOGMODULE_CONFIG, operatorID);
				throw new ServiceException("更新IPPV钱包失败");
			}

			// 系统日志
			StringBuffer logDesc = new StringBuffer();
			logDesc.append("修改IPPV钱包资料,");
			logDesc.append("IPPV钱包编码:");
			logDesc.append(oldCA.getCaWalletCode());

			logDesc.append(SystemLogRecorder.appendDescString(";钱包名称:", oldCA
					.getCaWalletName(), ippvDTO.getCaWalletName()));

			logDesc.append(SystemLogRecorder.appendDescString(";依赖设备列表:", oldCA
					.getDeviceModelList(), ippvDTO.getDeviceModelList()));

			logDesc.append(SystemLogRecorder.appendDescString(";是否必须:",
					BusinessUtility.getCommonNameByKey("SET_G_YESNOFLAG", oldCA
							.getRequired()), BusinessUtility
							.getCommonNameByKey("SET_G_YESNOFLAG", ippvDTO
									.getRequired())));

			logDesc.append(SystemLogRecorder.appendDescString(";兑换率:", String
					.valueOf(oldCA.getRate()), String
					.valueOf(ippvDTO.getRate())));

			String sysModule = "";

			sysModule = SystemLogRecorder.LOGMODULE_CONFIG;

			SystemLogRecorder.createSystemLog(machineName, operatorID, 0,
					sysModule, "修改IPPV钱包资料", logDesc.toString(),
					SystemLogRecorder.LOGCLASS_NORMAL,
					SystemLogRecorder.LOGTYPE_APP);
		}

		catch (HomeFactoryException e1) {

			throw new ServiceException("IPPV钱包定位出错！");
		} catch (FinderException e2) {

			e2.printStackTrace();
		}

	}

	// IPPV钱包删除
	private void deleteIppvWallet(MarketEJBEvent marketEvent)
			throws ServiceException {
		CAWalletDefineDTO ippvDTO = marketEvent.getIppvDTO();

		String caWalletCode = ippvDTO.getCaWalletCode();

		try {

			CAWalletDefineHome caHome = HomeLocater.getCAWalletDefineHome();

			CAWalletDefine caWallet;
			caWallet = caHome.findByPrimaryKey(caWalletCode);
			String caCode = caWallet.getCaWalletCode();
			String caName = caWallet.getCaWalletName();
			String caDevice = caWallet.getDeviceModelList();
			String caRequire = caWallet.getRequired();
			String caRate = String.valueOf(caWallet.getRate());

			if (caWallet != null)
				caWallet.remove();

			// 系统日志
			StringBuffer logDesc = new StringBuffer();
			logDesc.append("删除IPPV钱包资料,");
			logDesc.append("IPPV钱包编码:");
			logDesc.append(caCode);

			logDesc.append(";钱包名称:" + caName);

			logDesc.append(";依赖设备列表:" + caDevice);

			logDesc.append(";是否必须:" + caRequire);

			logDesc.append(";兑换率:" + caRate);

			String sysModule = "";

			sysModule = SystemLogRecorder.LOGMODULE_CONFIG;

			SystemLogRecorder.createSystemLog(machineName, operatorID, 0,
					sysModule, "删除IPPV钱包资料", logDesc.toString(),
					SystemLogRecorder.LOGCLASS_NORMAL,
					SystemLogRecorder.LOGTYPE_APP);

		} catch (EJBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RemoveException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FinderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (HomeFactoryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 初始化ServiceContext 将一些通用的信息放入ServiceContext
	 */
	private ServiceContext initServiceContext() {
		ServiceContext serviceContext = new ServiceContext();
		serviceContext.put(Service.OPERATIOR_ID, new Integer(operatorID));

		return serviceContext;
	}

	private void createDtvMigration(MarketEJBEvent marketEvent)
			throws ServiceException {

		ServiceContext context = initServiceContext();
		DtvMigrationAreaDTO dto = marketEvent.getDtvMigrationDto();
		dto.setCreateOpId(new Integer(operatorID));
		MarketService ms = new MarketService(context);

		ms.createDtvMigration(dto, marketEvent);

		// 系统日志
		StringBuffer logDesc = new StringBuffer();
		logDesc.append("创建平移小区资料,");
		logDesc.append("平移小区编码:");
		logDesc.append(dto.getAreaCode());

		logDesc.append(";平移小区名称:" + dto.getAreaName());
		logDesc.append(";平移小区编号:" + dto.getAreaCode());
		logDesc.append(";区域:"
				+ BusinessUtility.getDistrictNameById(dto.getRegionalAreaId()));
		logDesc.append(";批量平移开始日期:" + dto.getBatchStartDate());
		logDesc.append(";批量平移结束日期:" + dto.getBatchEndDate());
		logDesc.append(";计划平移用户数:" + dto.getPlanMigrationRoomNum());
		logDesc.append(";平移施工单位:" + dto.getMigrationTeamName());
		logDesc.append(";状态:"
				+ BusinessUtility.getCommonNameByKey(
						"SET_M_CUSTOMERCAMPAIGNSTATUS", dto.getStatus()));

		if (dto.getDescription() != null) {
			logDesc.append(";描述信息:" + dto.getDescription());
		} else {
			logDesc.append(";描述信息:(空值)");
		}

		String sysModule = "";

		sysModule = SystemLogRecorder.LOGMODULE_CONFIG;

		SystemLogRecorder.createSystemLog(machineName, operatorID, 0,
				sysModule, "创建平移小区资料", logDesc.toString(),
				SystemLogRecorder.LOGCLASS_NORMAL,
				SystemLogRecorder.LOGTYPE_APP);
	}

	// 平移小区管理
	private void updateDtvMigration(MarketEJBEvent marketEvent)
			throws ServiceException {
		/**
		 * if (marketEvent.getCamOldProductCondDtoCol() !=null &&
		 * marketEvent.getCamPackageCondDtoCol() !=null) throw new
		 * ServiceException("产品包,产品只能选择其中一组");
		 */
		DtvMigrationAreaDTO dto = marketEvent.getDtvMigrationDto();

		try {

			List oldDtoList = BusinessUtility.getDtvMigrationByID(dto.getId());

			DtvMigrationAreaDTO oldDto = (DtvMigrationAreaDTO) oldDtoList
					.get(0);

			DtvMigrationAreaHome home = HomeLocater.getDtvMigrationAreaHome();
			DtvMigrationArea dtvDomain;
			dtvDomain = home.findByPrimaryKey(new Integer(oldDto.getId()));

			if ((dtvDomain.ejbUpdate(marketEvent.getDtvMigrationDto())) == -1) {
				SystemLogRecorder.keyLog("更新平移小区失败", "更新平移小区", machineName,
						SystemLogRecorder.LOGMODULE_CONFIG, operatorID);
				throw new ServiceException("更新平移小区失败");
			}

			// 系统日志
			StringBuffer logDesc = new StringBuffer();
			logDesc.append("修改平移小区资料,");
			logDesc.append("平移小区编码:");
			logDesc.append(oldDto.getAreaCode());

			logDesc.append(SystemLogRecorder.appendDescString(";平移小区名称:",
					oldDto.getAreaName(), dto.getAreaName()));

			logDesc.append(SystemLogRecorder.appendDescString(";平移小区编号:",
					oldDto.getAreaCode(), dto.getAreaCode()));

			logDesc.append(SystemLogRecorder.appendDescString(";区域:",
					BusinessUtility.getDistrictNameById(oldDto
							.getRegionalAreaId()), BusinessUtility
							.getDistrictNameById(dto.getRegionalAreaId())));

			logDesc.append(SystemLogRecorder.appendDescString(";平移施工单位:",
					oldDto.getMigrationTeamName(), dto.getMigrationTeamName()));

			logDesc.append(SystemLogRecorder.appendDescString(";批量平移开始日期:",
					oldDto.getBatchStartDate().toString(), dto
							.getBatchStartDate().toString()));

			logDesc.append(SystemLogRecorder.appendDescString(";批量平移结束日期:",
					oldDto.getBatchEndDate().toString(), dto.getBatchEndDate()
							.toString()));

			logDesc.append(SystemLogRecorder.appendDescString(";计划平移用户数:",
					String.valueOf(oldDto.getPlanMigrationRoomNum()), String
							.valueOf(dto.getPlanMigrationRoomNum())));

			logDesc.append(SystemLogRecorder.appendDescString(";平移施工单位:",
					oldDto.getMigrationTeamName(), dto.getMigrationTeamName()));

			logDesc.append(SystemLogRecorder
					.appendDescString(";状态:", BusinessUtility
							.getCommonNameByKey("SET_M_CUSTOMERCAMPAIGNSTATUS",
									oldDto.getStatus()), BusinessUtility
							.getCommonNameByKey("SET_M_CUSTOMERCAMPAIGNSTATUS",
									dto.getStatus())));

			logDesc.append(SystemLogRecorder.appendDescString(";描述信息:", oldDto
					.getDescription(), dto.getDescription()));

			String sysModule = "";

			sysModule = SystemLogRecorder.LOGMODULE_CONFIG;

			SystemLogRecorder.createSystemLog(machineName, operatorID, 0,
					sysModule, "修改平移小区", logDesc.toString(),
					SystemLogRecorder.LOGCLASS_NORMAL,
					SystemLogRecorder.LOGTYPE_APP);

		}

		catch (HomeFactoryException e1) {

			throw new ServiceException("IPPV钱包定位出错！");
		} catch (FinderException e2) {

			e2.printStackTrace();
		}

	}

	public static void main(String[] args) {
	}
}
