/*
 * Created on 2006-3-20
 * 
 * @author chen jiang
 * 
 * ���г�����йز���command
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

			case EJBEvent.CAMPAIGN_CREATE: // ���������
				createCampaign(inEvent);
				break;
			case EJBEvent.CAMPAIGN_UPDATE:
				updateCampaign(inEvent); // ���������
				break;
			case EJBEvent.GROUPBARGAIN_CREATE:
				createGroupBargain(inEvent); // �����Ź�ȯ
				break;
			case EJBEvent.GROUPBARGAIN_UPDATE:
				updateGroupBargain(inEvent.getGroupBargainDto()); // �����Ź���ͬ
				break;
			case EJBEvent.GROUPBARGAIN_DELETE:
				deleteGroupBargain(inEvent.getGroupBargainDto()); // ȡ���Ź���ͬ
				break;
			case EJBEvent.GROUPBARGAIN_SALE:
				saleGroupBargain(inEvent.getGroupBargainDto()); // �۳��Ź���ͬ
				break;
			case MarketEJBEvent.GROUPBARGAIN_DETAIL_DELETE:
				detailDelete(inEvent.getDetailDto());
				break;
			case EJBEvent.CAMPAIGN_COND_CREATE:
				createCondForCampaign(inEvent); // �������������
				break;
			case EJBEvent.CAMPAIGN_COND_UPDATE:
				modifyCondForCampaign(inEvent); // �޸Ĵ��������
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
			case EJBEvent.CAMPAIGN_PAYMENT_AWARD_CREATE: // ����� ������� ����
				createCampaignPaymentAward(inEvent.getCampaignPaymentAwardDto());
				break;
			case EJBEvent.CAMPAIGN_PAYMENT_AWARD_DELETE: // ����� ������� ɾ��
				deleteCampaignPaymentAward(inEvent.getCampaignPaymentAwardDto());
				break;

			case EJBEvent.IPPV_CREATE: // IPPVǮ������
				createIppvWallet(inEvent);
				break;
			case EJBEvent.IPPV_UPDATE:
				updateIppvWallet(inEvent); // IPPVǮ������
				break;
			case EJBEvent.IPPV_DELETE:
				deleteIppvWallet(inEvent); // IPPVǮ��ɾ��
				break;

			case EJBEvent.DTV_MGT_CREATE: // ƽ��С������
				createDtvMigration(inEvent);
				break;
			case EJBEvent.DTV_MGT_UPDATE: // ƽ��С������
				updateDtvMigration(inEvent);
				break;

			default:
				throw new IllegalArgumentException(
						"MarketEJBEvent��actionType�����ò���ȷ��");
			}
		} catch (ServiceException ce) {
			LogUtility.log(clazz, LogLevel.ERROR, this, ce);
			throw new CommandException(ce.getMessage());
		} catch (Throwable unkown) {
			LogUtility.log(clazz, LogLevel.FATAL, this, unkown);
			throw new CommandException("����δ֪�Ĵ���");
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

				throw new ServiceException("�Ź�ȯֻ�ܴ��ڴ���״̬���Ѿ��۳����Ź�ȯ�����޸�");
			}

			if (groupBargain.ejbUpdate(groupBargainDto) == -1) {
				SystemLogRecorder.keyLog("�����Ź�ȯʧ��", "�����Ź�ȯ", machineName,
						SystemLogRecorder.LOGMODULE_CUSTSERV, operatorID);
				throw new RuntimeException(
						"groupBargainDto update fail, ID:"
								+ groupBargainDto.getId()
								+ ". Please make sure that dt_lastmod of groupBargainDto is set.");
			}

			SystemLogRecorder.createSystemLog(PublicService
					.getRemoteHostAddress(context), PublicService
					.getCurrentOperatorID(context), 0,
					SystemLogRecorder.LOGMODULE_CONFIG, "�޸��Ź�ȯ", "�޸��Ź�ȯ��ID��"
							+ groupBargainDto.getId(),
					SystemLogRecorder.LOGCLASS_NORMAL,
					SystemLogRecorder.LOGTYPE_APP);

		} catch (HomeFactoryException e) {
			LogUtility.log(clazz, LogLevel.ERROR, e);
			throw new ServiceException("�����Ź�ȯʱ�����Ź�ȯID��"
					+ groupBargainDto.getId());
		} catch (FinderException e) {
			LogUtility.log(clazz, LogLevel.ERROR, e);
			throw new ServiceException("�����Ź�ȯ�����Ź�ȯID��"
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
				LogUtility.log(clazz, LogLevel.WARN, "�Ź�ȯֻ�ܴ����½�״̬�ſ���ɾ����");
				throw new ServiceException("�Ź�ȯֻ�ܴ����½�״̬�ſ���ɾ����");

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

			// ������ص���־��¼��
			SystemLogRecorder.keyLog("ɾ���Ź�", "ɾ���Ź�", machineName,
					SystemLogRecorder.LOGMODULE_CUSTSERV, operatorID);
		} catch (HomeFactoryException e) {
			LogUtility.log(clazz, LogLevel.ERROR, e);
			throw new ServiceException("�����Ź�ȯʱ�����Ź�ȯID��" + dto.getId());
		} catch (FinderException e) {
			LogUtility.log(clazz, LogLevel.ERROR, e);
			throw new ServiceException("�����Ź�ȯ�����Ź�ȯID��" + dto.getId());
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
				// ����ʹ�õ�����

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
			//wangpeng@20080225,ע����������䣬������һ�����
			//groupBargain.setUsedSheets(i);
			groupBargain.setUsedSheets(0);
			
			
			
			groupBargain
					.setStatus(CommonConstDefinition.GROUPBARGAIN_STATUS_SALED);
			groupBargain.setDtLastmod(TimestampUtility.getCurrentTimestamp());

			// ������ص���־��¼��
			SystemLogRecorder.keyLog("�Ź�ȯ�۳�,�Ź�ȯID" + dto.getId(), "�Ź�ȯ�۳�",
					machineName, SystemLogRecorder.LOGMODULE_CUSTSERV,
					operatorID);

		} catch (HomeFactoryException e) {
			LogUtility.log(clazz, LogLevel.ERROR, e);
			throw new ServiceException("�����Ź�ȯʱ�����Ź�ȯID��" + dto.getId());
		} catch (FinderException e) {
			LogUtility.log(clazz, LogLevel.ERROR, e);
			throw new ServiceException("�����Ź�ȯ�����Ź�ȯID��" + dto.getId());
		} catch (ServiceException e) {
			e.printStackTrace();
		}
	}

	// ���������
	private void createCampaign(MarketEJBEvent marketEvent)
			throws ServiceException {

		ServiceContext context = initServiceContext();
		CampaignDTO campaignDto = marketEvent.getCampaignDto();
		campaignDto.setTimeLengthUnitType("M");
		MarketService ms = new MarketService(context);

		ms.createCampaign(campaignDto, marketEvent);
		response.setPayload(new Integer(ms.getCampaignID()));
		String description = "����������� �ID" + ms.getCampaignID();
		SystemLogRecorder.keyLog(description, "���������", machineName,
				SystemLogRecorder.LOGMODULE_CONFIG, operatorID);

	}

	// �����������Ʒɾ��
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

	// �����������Ʒ��ɾ��
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

	// �ײ����ѷ�ʽɾ��
	private void bundlePayMethDelete(BundlePaymentMethodDTO dto)
			throws ServiceException {

		try {
			if (dto == null) {
				throw new ServiceException("BundlePaymentMethodDTOû��ֵ");
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

	// �Э��ɾ��
	private void agmtCampaignDelete(CampaignAgmtCampaignDTO dto)
			throws ServiceException {

		try {
			if (dto == null) {
				throw new ServiceException("CampaignAgmtCampaignDTOû��ֵ");
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

	// �ײ�Ԥ������ɾ��
	private void bundlePrementDelete(BundlePrepaymentDTO dto)
			throws ServiceException {

		try {
			if (dto == null) {
				throw new ServiceException("BundlePrepaymentDTOû��ֵ");
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

	// ����������ɾ��
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
	 * ServiceException("��Ʒ��,��Ʒֻ��ѡ������һ��"); ServiceContext context =
	 * initServiceContext(); CampaignDTO campaignDto =
	 * marketEvent.getCampaignDto(); campaignDto.setTimeLengthUnitType("M");
	 * MarketService ms = new MarketService(context);
	 * ms.create(campaignDto,marketEvent.getCamOldProductCondDtoCol(),marketEvent.getCamPackageCondDtoCol(),marketEvent.getCamToMarketDtoCol());
	 * String description = "����������� �ID"+ms.getCampaignID();
	 * SystemLogRecorder.keyLog(description, "���������", machineName,
	 * SystemLogRecorder.LOGMODULE_CONFIG, operatorID, ms.getCampaignID()); }
	 */
	// ���������
	private void updateCampaign(MarketEJBEvent marketEvent)
			throws ServiceException {
		/**
		 * if (marketEvent.getCamOldProductCondDtoCol() !=null &&
		 * marketEvent.getCamPackageCondDtoCol() !=null) throw new
		 * ServiceException("��Ʒ��,��Ʒֻ��ѡ������һ��");
		 */
		CampaignDTO campaignDto = marketEvent.getCampaignDto();

		try {
			CampaignHome cpHome = HomeLocater.getCampaignHome();
			ServiceContext context = initServiceContext();

			Campaign campaign;
			campaign = cpHome.findByPrimaryKey(new Integer(campaignDto
					.getCampaignID()));
			if ((campaign.ejbUpdate(marketEvent.getCampaignDto())) == -1) {
				SystemLogRecorder.keyLog("�����Ż�ʧ��", "�����Ż�", machineName,
						SystemLogRecorder.LOGMODULE_CONFIG, operatorID);
				throw new ServiceException("�����Ż�ʧ��");
			}

			MarketService ms = new MarketService(context, marketEvent
					.getCampaignID());
			ms.updateCampaign(marketEvent);

			SystemLogRecorder.createSystemLog(PublicService
					.getRemoteHostAddress(context), PublicService
					.getCurrentOperatorID(context), 0,
					SystemLogRecorder.LOGMODULE_CONFIG, "���������",
					"��������ģ��ID��" + campaignDto.getCampaignID(),
					SystemLogRecorder.LOGCLASS_NORMAL,
					SystemLogRecorder.LOGTYPE_SYSTEM);
		}

		catch (HomeFactoryException e1) {

			throw new ServiceException("�������λ����");
		} catch (FinderException e2) {

			e2.printStackTrace();
		}

	}

	// �����Ź�ȯ
	private void createGroupBargain(MarketEJBEvent marketEvent)
			throws ServiceException {

		int startNum = marketEvent.getFromBegin();

		String detailNoFormat = marketEvent.getFormat();
		GroupBargainDTO dto = marketEvent.getGroupBargainDto();
		int size = dto.getTotalSheets();
		if (startNum < 0 || size < 1 || startNum + size > Integer.MAX_VALUE
				|| detailNoFormat.indexOf('*') == -1) {
			throw new ServiceException("����������Ϸ���detailNoFormat="
					+ detailNoFormat + ", from=" + startNum + ", size=" + size);
		}
		dto.setCreateTime(TimestampUtility.getCurrentDate());
		dto.setUsedSheets(0);
		dto.setAbortSheets(0);
		dto.setStatus(CommonConstDefinition.GROUPBARGAIN_STATUS_NEW);
		// �����Ź�ȯ
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
				throw new ServiceException("����������Ϸ���detailNoFormat="
						+ detailNoFormat + ", from=" + startNum + ", size="
						+ size + ", �����Ǳ����ʽ̫����ɵġ�");
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
							+ detailDto.getDetailNo() + "�ѱ�ʹ�á�");

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

			throw new ServiceException("�������λ����");
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
			LogUtility.log(clazz, LogLevel.DEBUG, "GroupBargainDetailDTO�� "
					+ dto);
			GroupBargain groupBargain = groupBarHome
					.findByPrimaryKey(new Integer(dto.getGroupBargainID()));
			LogUtility.log(clazz, LogLevel.DEBUG, "groupBargain��״̬Ϊ�� "
					+ groupBargain.getStatus());
			if (!groupBargain.getStatus().equals(
					CommonConstDefinition.GROUPBARGAIN_STATUS_NEW)) {
				throw new ServiceException("ֻ�д��ڴ���״̬���Ź�ȯ����ɾ��");
			}
			groupBargain.setAbortsheets(groupBargain.getAbortsheets() + 1);
			groupBargain.setDtLastmod(TimestampUtility.getCurrentTimestamp());
			GroupBargainDetail detail = groupBarDetailHome
					.findByPrimaryKey(new Integer(dto.getId()));
			LogUtility.log(clazz, LogLevel.DEBUG, "groupBarDetailHome����״̬Ϊ "
					+ detail.getStatus());
			if (!detail.getStatus().equals(
					CommonConstDefinition.GROUPBARGAINDETAIL_STATUS_WAIT)) {

				throw new ServiceException("ֻ�д��ڴ���״̬���Ź�ȯ����ɾ�����Ѿ��۳����Ź�ȯ����ɾ��");
			}

			detail
					.setStatus(CommonConstDefinition.GROUPBARGAINDETAIL_STATUS_CANCEL);
			detail.setDtLastmod(TimestampUtility.getCurrentTimestamp());
			SystemLogRecorder.createSystemLog(PublicService
					.getRemoteHostAddress(context), PublicService
					.getCurrentOperatorID(context), dto.getId(),
					SystemLogRecorder.LOGMODULE_CONFIG, "�Ź�ȯɾ��", "�Ź�ȯɾ�����Ź�ȯID��"
							+ dto.getId(), SystemLogRecorder.LOGCLASS_NORMAL,
					SystemLogRecorder.LOGTYPE_SYSTEM);

		} catch (HomeFactoryException e) {
			LogUtility.log(clazz, LogLevel.ERROR, e);
			throw new ServiceException("�����Ź�ȯʱ�����Ź�ȯID��" + dto.getId());
		} catch (FinderException e) {
			LogUtility.log(clazz, LogLevel.ERROR, e);
			throw new ServiceException("�����Ź�ȯ�����Ź�ȯID��" + dto.getId());
		}
	}

	/**
	 * ϵͳ���ã��ײ͡��Żݻ��Ӧ�Ľ������ ����
	 * 
	 * @param inEvent
	 */
	private void createCampaignPaymentAward(CampaignPaymentAwardDTO dto)
			throws ServiceException {
		ServiceContext context = initServiceContext();

		MarketService ms = new MarketService(context);
		ms.createCampaignPaymentAward(dto);
	}

	// ϵͳ���ã��ײ͡��Żݻ��Ӧ�Ľ������ ɾ��
	private void deleteCampaignPaymentAward(CampaignPaymentAwardDTO dto)
			throws ServiceException {

		try {
			if (dto == null) {
				throw new ServiceException("CampaignAgmtCampaignDTOû��ֵ");
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

	// IPPVǮ������
	private void createIppvWallet(MarketEJBEvent marketEvent)
			throws ServiceException {

		ServiceContext context = initServiceContext();
		CAWalletDefineDTO ippvDTO = marketEvent.getIppvDTO();
		MarketService ms = new MarketService(context);

		String caCode = ippvDTO.getCaWalletCode();

		ms.createIppvWallet(ippvDTO, marketEvent);

		// ϵͳ��־
		StringBuffer logDesc = new StringBuffer();
		logDesc.append("�½�IPPVǮ������,");
		logDesc.append("IPPVǮ������:");
		logDesc.append(caCode);

		logDesc.append(";Ǯ������:" + ippvDTO.getCaWalletName());

		logDesc.append(";�����豸�б�:" + ippvDTO.getDeviceModelList());

		logDesc.append(";�Ƿ����:" + ippvDTO.getRequired());

		logDesc.append(";�һ���:" + ippvDTO.getRate());

		String sysModule = "";

		sysModule = SystemLogRecorder.LOGMODULE_CONFIG;

		SystemLogRecorder.createSystemLog(machineName, operatorID, 0,
				sysModule, "�½�IPPVǮ������", logDesc.toString(),
				SystemLogRecorder.LOGCLASS_NORMAL,
				SystemLogRecorder.LOGTYPE_APP);

		response.setPayload(ms.getIppvWalletCode());

	}

	// IppvǮ������
	private void updateIppvWallet(MarketEJBEvent marketEvent)
			throws ServiceException {
		/**
		 * if (marketEvent.getCamOldProductCondDtoCol() !=null &&
		 * marketEvent.getCamPackageCondDtoCol() !=null) throw new
		 * ServiceException("��Ʒ��,��Ʒֻ��ѡ������һ��");
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
				SystemLogRecorder.keyLog("����IPPVǮ��ʧ��", "����IPPVǮ��", machineName,
						SystemLogRecorder.LOGMODULE_CONFIG, operatorID);
				throw new ServiceException("����IPPVǮ��ʧ��");
			}

			// ϵͳ��־
			StringBuffer logDesc = new StringBuffer();
			logDesc.append("�޸�IPPVǮ������,");
			logDesc.append("IPPVǮ������:");
			logDesc.append(oldCA.getCaWalletCode());

			logDesc.append(SystemLogRecorder.appendDescString(";Ǯ������:", oldCA
					.getCaWalletName(), ippvDTO.getCaWalletName()));

			logDesc.append(SystemLogRecorder.appendDescString(";�����豸�б�:", oldCA
					.getDeviceModelList(), ippvDTO.getDeviceModelList()));

			logDesc.append(SystemLogRecorder.appendDescString(";�Ƿ����:",
					BusinessUtility.getCommonNameByKey("SET_G_YESNOFLAG", oldCA
							.getRequired()), BusinessUtility
							.getCommonNameByKey("SET_G_YESNOFLAG", ippvDTO
									.getRequired())));

			logDesc.append(SystemLogRecorder.appendDescString(";�һ���:", String
					.valueOf(oldCA.getRate()), String
					.valueOf(ippvDTO.getRate())));

			String sysModule = "";

			sysModule = SystemLogRecorder.LOGMODULE_CONFIG;

			SystemLogRecorder.createSystemLog(machineName, operatorID, 0,
					sysModule, "�޸�IPPVǮ������", logDesc.toString(),
					SystemLogRecorder.LOGCLASS_NORMAL,
					SystemLogRecorder.LOGTYPE_APP);
		}

		catch (HomeFactoryException e1) {

			throw new ServiceException("IPPVǮ����λ����");
		} catch (FinderException e2) {

			e2.printStackTrace();
		}

	}

	// IPPVǮ��ɾ��
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

			// ϵͳ��־
			StringBuffer logDesc = new StringBuffer();
			logDesc.append("ɾ��IPPVǮ������,");
			logDesc.append("IPPVǮ������:");
			logDesc.append(caCode);

			logDesc.append(";Ǯ������:" + caName);

			logDesc.append(";�����豸�б�:" + caDevice);

			logDesc.append(";�Ƿ����:" + caRequire);

			logDesc.append(";�һ���:" + caRate);

			String sysModule = "";

			sysModule = SystemLogRecorder.LOGMODULE_CONFIG;

			SystemLogRecorder.createSystemLog(machineName, operatorID, 0,
					sysModule, "ɾ��IPPVǮ������", logDesc.toString(),
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
	 * ��ʼ��ServiceContext ��һЩͨ�õ���Ϣ����ServiceContext
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

		// ϵͳ��־
		StringBuffer logDesc = new StringBuffer();
		logDesc.append("����ƽ��С������,");
		logDesc.append("ƽ��С������:");
		logDesc.append(dto.getAreaCode());

		logDesc.append(";ƽ��С������:" + dto.getAreaName());
		logDesc.append(";ƽ��С�����:" + dto.getAreaCode());
		logDesc.append(";����:"
				+ BusinessUtility.getDistrictNameById(dto.getRegionalAreaId()));
		logDesc.append(";����ƽ�ƿ�ʼ����:" + dto.getBatchStartDate());
		logDesc.append(";����ƽ�ƽ�������:" + dto.getBatchEndDate());
		logDesc.append(";�ƻ�ƽ���û���:" + dto.getPlanMigrationRoomNum());
		logDesc.append(";ƽ��ʩ����λ:" + dto.getMigrationTeamName());
		logDesc.append(";״̬:"
				+ BusinessUtility.getCommonNameByKey(
						"SET_M_CUSTOMERCAMPAIGNSTATUS", dto.getStatus()));

		if (dto.getDescription() != null) {
			logDesc.append(";������Ϣ:" + dto.getDescription());
		} else {
			logDesc.append(";������Ϣ:(��ֵ)");
		}

		String sysModule = "";

		sysModule = SystemLogRecorder.LOGMODULE_CONFIG;

		SystemLogRecorder.createSystemLog(machineName, operatorID, 0,
				sysModule, "����ƽ��С������", logDesc.toString(),
				SystemLogRecorder.LOGCLASS_NORMAL,
				SystemLogRecorder.LOGTYPE_APP);
	}

	// ƽ��С������
	private void updateDtvMigration(MarketEJBEvent marketEvent)
			throws ServiceException {
		/**
		 * if (marketEvent.getCamOldProductCondDtoCol() !=null &&
		 * marketEvent.getCamPackageCondDtoCol() !=null) throw new
		 * ServiceException("��Ʒ��,��Ʒֻ��ѡ������һ��");
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
				SystemLogRecorder.keyLog("����ƽ��С��ʧ��", "����ƽ��С��", machineName,
						SystemLogRecorder.LOGMODULE_CONFIG, operatorID);
				throw new ServiceException("����ƽ��С��ʧ��");
			}

			// ϵͳ��־
			StringBuffer logDesc = new StringBuffer();
			logDesc.append("�޸�ƽ��С������,");
			logDesc.append("ƽ��С������:");
			logDesc.append(oldDto.getAreaCode());

			logDesc.append(SystemLogRecorder.appendDescString(";ƽ��С������:",
					oldDto.getAreaName(), dto.getAreaName()));

			logDesc.append(SystemLogRecorder.appendDescString(";ƽ��С�����:",
					oldDto.getAreaCode(), dto.getAreaCode()));

			logDesc.append(SystemLogRecorder.appendDescString(";����:",
					BusinessUtility.getDistrictNameById(oldDto
							.getRegionalAreaId()), BusinessUtility
							.getDistrictNameById(dto.getRegionalAreaId())));

			logDesc.append(SystemLogRecorder.appendDescString(";ƽ��ʩ����λ:",
					oldDto.getMigrationTeamName(), dto.getMigrationTeamName()));

			logDesc.append(SystemLogRecorder.appendDescString(";����ƽ�ƿ�ʼ����:",
					oldDto.getBatchStartDate().toString(), dto
							.getBatchStartDate().toString()));

			logDesc.append(SystemLogRecorder.appendDescString(";����ƽ�ƽ�������:",
					oldDto.getBatchEndDate().toString(), dto.getBatchEndDate()
							.toString()));

			logDesc.append(SystemLogRecorder.appendDescString(";�ƻ�ƽ���û���:",
					String.valueOf(oldDto.getPlanMigrationRoomNum()), String
							.valueOf(dto.getPlanMigrationRoomNum())));

			logDesc.append(SystemLogRecorder.appendDescString(";ƽ��ʩ����λ:",
					oldDto.getMigrationTeamName(), dto.getMigrationTeamName()));

			logDesc.append(SystemLogRecorder
					.appendDescString(";״̬:", BusinessUtility
							.getCommonNameByKey("SET_M_CUSTOMERCAMPAIGNSTATUS",
									oldDto.getStatus()), BusinessUtility
							.getCommonNameByKey("SET_M_CUSTOMERCAMPAIGNSTATUS",
									dto.getStatus())));

			logDesc.append(SystemLogRecorder.appendDescString(";������Ϣ:", oldDto
					.getDescription(), dto.getDescription()));

			String sysModule = "";

			sysModule = SystemLogRecorder.LOGMODULE_CONFIG;

			SystemLogRecorder.createSystemLog(machineName, operatorID, 0,
					sysModule, "�޸�ƽ��С��", logDesc.toString(),
					SystemLogRecorder.LOGCLASS_NORMAL,
					SystemLogRecorder.LOGTYPE_APP);

		}

		catch (HomeFactoryException e1) {

			throw new ServiceException("IPPVǮ����λ����");
		} catch (FinderException e2) {

			e2.printStackTrace();
		}

	}

	public static void main(String[] args) {
	}
}
