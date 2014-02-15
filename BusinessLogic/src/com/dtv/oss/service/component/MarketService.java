package com.dtv.oss.service.component;

import java.util.Collection;
import java.util.Iterator;

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.FinderException;
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
import com.dtv.oss.domain.CampaignAgmtPackage;
import com.dtv.oss.domain.CampaignAgmtPackageHome;
import com.dtv.oss.domain.CampaignAgmtProduct;
import com.dtv.oss.domain.CampaignAgmtProductHome;
import com.dtv.oss.domain.CampaignCondCampaign;
import com.dtv.oss.domain.CampaignCondCampaignHome;
import com.dtv.oss.domain.CampaignCondPackage;
import com.dtv.oss.domain.CampaignCondPackageHome;
import com.dtv.oss.domain.CampaignCondProduct;
import com.dtv.oss.domain.CampaignCondProductHome;
import com.dtv.oss.domain.CampaignHome;
import com.dtv.oss.domain.CampaignToMarketSegment;
import com.dtv.oss.domain.CampaignToMarketSegmentHome;
import com.dtv.oss.domain.DtvMigrationArea;
import com.dtv.oss.domain.DtvMigrationAreaHome;
import com.dtv.oss.domain.GroupBargain;
import com.dtv.oss.domain.GroupBargainHome;
import com.dtv.oss.domain.CampaignPaymentAwardHome;
import com.dtv.oss.domain.CampaignPaymentAward;
import com.dtv.oss.dto.BundlePaymentMethodDTO;
import com.dtv.oss.dto.BundlePrepaymentDTO;
import com.dtv.oss.dto.CAWalletDefineDTO;
import com.dtv.oss.dto.CampaignAgmtCampaignDTO;
import com.dtv.oss.dto.CampaignAgmtPackageDTO;
import com.dtv.oss.dto.CampaignAgmtProductDTO;
import com.dtv.oss.dto.CampaignCondCampaignDTO;
import com.dtv.oss.dto.CampaignCondPackageDTO;
import com.dtv.oss.dto.CampaignCondProductDTO;
import com.dtv.oss.dto.CampaignDTO;
import com.dtv.oss.dto.CampaignToMarketSegmentDTO;
import com.dtv.oss.dto.DtvMigrationAreaDTO;
import com.dtv.oss.dto.GroupBargainDTO;
import com.dtv.oss.dto.CampaignPaymentAwardDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.ServiceContext;
import com.dtv.oss.service.ServiceException;
import com.dtv.oss.service.ejbevent.market.MarketEJBEvent;
import com.dtv.oss.service.factory.HomeFactoryException;
import com.dtv.oss.service.util.BusinessUtility;
import com.dtv.oss.service.util.HomeLocater;

public class MarketService extends AbstractService {

	private static final Class clazz = MarketService.class;

	private int campaignID;

	private int groupBargainID;

	private CampaignCondProductDTO productDto;

	private CampaignCondPackageDTO packageDto;

	private CampaignCondCampaignDTO campaignDto;

	private BundlePaymentMethodDTO bundlePayMethodDto;

	private BundlePrepaymentDTO bundlePreDto;

	private CampaignAgmtCampaignDTO agmtCamDto;

	private String ippvWalletCode;

	public MarketService(ServiceContext inContext) {
	}

	public MarketService(ServiceContext inContext, int campaignID) {
		this.campaignID = campaignID;
	}

	private void getDto(MarketEJBEvent marketEvent) {
		productDto = marketEvent.getCondProdDto();

		packageDto = marketEvent.getCondPackDto();

		campaignDto = marketEvent.getCondCampDto();

		bundlePayMethodDto = marketEvent.getBundlePayMethodDto();

		bundlePreDto = marketEvent.getBundPreMethDto();

		agmtCamDto = marketEvent.getAgmtCampaignDto();

	}

	public void updateCond(MarketEJBEvent marketEvent) throws ServiceException {

		LogUtility.log(clazz, LogLevel.DEBUG, "�޸Ĵ������������ʼ");
		getDto(marketEvent);
		/**
		 * CampaignCondProductDTO productDto= marketEvent.getCondProdDto();
		 * 
		 * CampaignCondPackageDTO packageDto= marketEvent.getCondPackDto();
		 * 
		 * CampaignCondCampaignDTO campaignDto = marketEvent.getCondCampDto();
		 * 
		 * BundlePaymentMethodDTO bundlePayMethodDto =
		 * marketEvent.getBundlePayMethodDto();
		 * 
		 */

		if (productDto != null)
			updateCondProduct(productDto);
		if (packageDto != null)
			updateCondPackage(packageDto);
		if (campaignDto != null)
			updateCampaignCond(campaignDto);
		if (bundlePayMethodDto != null) {

			updateBundlePayMethod(bundlePayMethodDto);
		}
		if (bundlePreDto != null) {

			updateBundlePrepayment(bundlePreDto);
		}
		if (agmtCamDto != null) {

			updateAgmtCampaign(agmtCamDto);
		}

	}

	public void create(MarketEJBEvent marketEvent) throws ServiceException {

		LogUtility.log(clazz, LogLevel.DEBUG, "����������Ĳ�Ʒ������ʼ");
		/**
		 * CampaignCondProductDTO productDto= marketEvent.getCondProdDto();
		 * 
		 * CampaignCondPackageDTO packageDto= marketEvent.getCondPackDto();
		 * 
		 * CampaignCondCampaignDTO campaignDto = marketEvent.getCondCampDto();
		 * 
		 * BundlePaymentMethodDTO bundlePayMethodDto =
		 * marketEvent.getBundlePayMethodDto();
		 */
		getDto(marketEvent);
		if (productDto != null)
			createCondProduct(productDto);
		if (packageDto != null)
			createCondPackage(packageDto);
		if (campaignDto != null)
			createCampaignCond(campaignDto);
		if (bundlePayMethodDto != null)
			createBundlePayMethod(bundlePayMethodDto);
		if (bundlePreDto != null)
			createBundlePrepayment(bundlePreDto);
		if (agmtCamDto != null)
			createAgmtCampaign(agmtCamDto);

	}

	/**
	 * @param marketCol
	 */
	private void createCondSegment(Collection marketCol)
			throws ServiceException {
		// TODO Auto-generated method stub
		try {
			Iterator segmentIte = marketCol.iterator();
			while (segmentIte.hasNext()) {
				CampaignToMarketSegmentHome camMarketHome = HomeLocater
						.getCampaignToMarketSegmentHome();
				CampaignToMarketSegmentDTO camSegmentDto = (CampaignToMarketSegmentDTO) segmentIte
						.next();
				camSegmentDto.setCampaignId(campaignID);
				camMarketHome.create(camSegmentDto);
			}
		} catch (HomeFactoryException e1) {
			LogUtility.log(clazz, LogLevel.ERROR, "������г�������λ����");
			throw new ServiceException("������г�������λ����");
		}

		catch (CreateException e3) {
			LogUtility.log(clazz, LogLevel.ERROR, "������г�������������");
			throw new ServiceException("������г�������������");
		}
	}

	/**
	 * @param marketCol
	 */
	private void createAgmtPack(Collection packCol) throws ServiceException {
		// TODO Auto-generated method stub
		try {
			Iterator segmentIte = packCol.iterator();
			while (segmentIte.hasNext()) {
				CampaignAgmtPackageHome camMarketHome = HomeLocater
						.getCampaignAgmtPackageHome();
				CampaignAgmtPackageDTO packageDto = (CampaignAgmtPackageDTO) segmentIte
						.next();
				packageDto.setCampaignID(campaignID);
				camMarketHome.create(packageDto);
			}
		} catch (HomeFactoryException e1) {
			LogUtility.log(clazz, LogLevel.ERROR, "Э���Ʒ����λ����");
			throw new ServiceException("Э���Ʒ����λ����");
		}

		catch (CreateException e3) {
			LogUtility.log(clazz, LogLevel.ERROR, "Э���Ʒ����������");
			throw new ServiceException("Э���Ʒ����������");
		}
	}

	/**
	 * @param marketCol
	 */
	private void createAgmtProd(Collection packCol) throws ServiceException {
		// TODO Auto-generated method stub
		try {
			Iterator segmentIte = packCol.iterator();
			while (segmentIte.hasNext()) {
				CampaignAgmtProductHome prodHome = HomeLocater
						.getCampaignAgmtProductHome();
				CampaignAgmtProductDTO prodDto = (CampaignAgmtProductDTO) segmentIte
						.next();
				prodDto.setCampaignID(campaignID);
				prodHome.create(prodDto);
			}
		} catch (HomeFactoryException e1) {
			LogUtility.log(clazz, LogLevel.ERROR, "Э���Ʒ��λ����");
			throw new ServiceException("Э���Ʒ��λ����");
		}

		catch (CreateException e3) {
			LogUtility.log(clazz, LogLevel.ERROR, "Э���Ʒ��������");
			throw new ServiceException("Э���Ʒ��������");
		}
	}

	/**
	 * @param camDto
	 *            �����dto
	 * @param oldProdCol
	 *            ���в�Ʒ����
	 * @param packageCol
	 *            �²�Ʒ��
	 * @throws ServiceException
	 */

	public void createCampaign(CampaignDTO camDto, MarketEJBEvent marketEvent)
			throws ServiceException {
		LogUtility.log(clazz, LogLevel.DEBUG, "���������");

		try {
			CampaignHome cpHome = HomeLocater.getCampaignHome();
			Campaign cp = null;
			cp = cpHome.create(camDto);
			campaignID = cp.getCampaignID().intValue();

			Collection marketCol = marketEvent.getCondMarketDtoCol();

			Collection packCol = marketEvent.getAgmtPackDtoCol();

			Collection prodCol = marketEvent.getAgmtProdDtoCol();

			if (marketCol != null)
				createCondSegment(marketCol);

			if (packCol != null)
				createAgmtPack(packCol);

			if (prodCol != null)
				createAgmtProd(prodCol);
		} catch (HomeFactoryException e1) {
			LogUtility.log(clazz, LogLevel.ERROR, "�������λ����");
			throw new ServiceException("�������λ����");
		}

		catch (CreateException e3) {
			LogUtility.log(clazz, LogLevel.ERROR, "�������������");
			throw new ServiceException("�������������");
		}
	}

	public void updateCampaign(MarketEJBEvent marketEvent)
			throws ServiceException {

		LogUtility.log(clazz, LogLevel.DEBUG, "����������Ĳ�Ʒ������ʼ");

		Collection marketCol = marketEvent.getCondMarketDtoCol();

		Collection packCol = marketEvent.getAgmtPackDtoCol();

		Collection prodCol = marketEvent.getAgmtProdDtoCol();
		campaignID = marketEvent.getCampaignID();
		if (marketCol != null) {
			removeOldMarket();
			createCondSegment(marketCol);
		} else {
			removeOldMarket();
		}

		if (packCol != null) {
			removeOldPack();
			createAgmtPack(packCol);
		} else
			removeOldPack();
		if (prodCol != null) {
			removeOldProduct();
			createAgmtProd(prodCol);
		} else

		{
			removeOldProduct();
		}
	}

	/**
	 * @param campaignDto
	 */
	private void createCampaignCond(CampaignCondCampaignDTO campaignDto)
			throws ServiceException {

		try {

			CampaignCondCampaignHome camHome = HomeLocater
					.getCampaignCondCampaignHome();

			camHome.create(campaignDto);
		} catch (HomeFactoryException e1) {
			LogUtility.log(clazz, LogLevel.ERROR, "������г����λ����");
			throw new ServiceException("������г����λ����");
		}

		catch (CreateException e3) {
			LogUtility.log(clazz, LogLevel.ERROR, "������г����������");
			throw new ServiceException("������г����������");
		}
	}

	/**
	 * @param bundlePreDto
	 */
	private void createBundlePrepayment(BundlePrepaymentDTO bundlePreDto)
			throws ServiceException {

		try {
			checkUnique(bundlePreDto.getBundlePrepaymentPlanId());
			BundlePrepaymentHome camHome = HomeLocater
					.getBundlePrepaymentHome();

			camHome.create(bundlePreDto);
		} catch (HomeFactoryException e1) {
			LogUtility.log(clazz, LogLevel.ERROR, "Ԥ���Ѷ�λ����");
			throw new ServiceException("Ԥ���Ѷ�λ����");
		}

		catch (CreateException e3) {
			LogUtility.log(clazz, LogLevel.ERROR, "Ԥ���Ѵ�������");
			throw new ServiceException("Ԥ���Ѵ�������");
		}
	}

	/**
	 * @param bundlePreDto
	 */
	private void createAgmtCampaign(CampaignAgmtCampaignDTO agmtCamDto)
			throws ServiceException {

		if (agmtCamDto.getCampaignId() == 0
				|| agmtCamDto.getTargetBundleId() == 0)
			throw new ServiceException("�ID,��Ŀ��ID������Ϊ�գ�");

		try {

			CampaignAgmtCampaignHome camHome = HomeLocater
					.getCampaignAgmtCampaignHome();

			camHome.create(agmtCamDto);
		} catch (HomeFactoryException e1) {
			LogUtility.log(clazz, LogLevel.ERROR, "ȡ�ûЭ���home�ӿڴ���");
			throw new ServiceException("û���ҵ��Э�飡");
		}

		catch (CreateException e3) {
			LogUtility.log(clazz, LogLevel.ERROR, "�Э�鴴������");
			throw new ServiceException("�Э�鴴������");
		}
	}

	private void checkUnique(int bundlePrepaymentPlanId)
			throws ServiceException {
		// TODO Auto-generated method stub
		boolean isExist = BusinessUtility.isExitPlanID(bundlePrepaymentPlanId);
		if (isExist)
			throw new ServiceException("�ײ�Ԥ���ƻ�ID�Ѿ����ڣ�������ѡ��");

	}

	/**
	 * @param packageDto
	 */
	private void createCondPackage(CampaignCondPackageDTO packageDto)
			throws ServiceException {

		try {

			CampaignCondPackageHome camPackHome = HomeLocater
					.getCampaignCondPackageHome();

			camPackHome.create(packageDto);
		} catch (HomeFactoryException e1) {
			LogUtility.log(clazz, LogLevel.ERROR, "�������Ʒ����λ����");
			throw new ServiceException("�������Ʒ����λ����");
		}

		catch (CreateException e3) {
			LogUtility.log(clazz, LogLevel.ERROR, "�������Ʒ����������");
			throw new ServiceException("�������Ʒ����������");
		}
	}

	/**
	 * @param productDto
	 */
	private void createCondProduct(CampaignCondProductDTO productDto)
			throws ServiceException {

		try {

			CampaignCondProductHome camProdHome = HomeLocater
					.getCampaignCondProductHome();

			camProdHome.create(productDto);
		} catch (HomeFactoryException e1) {
			LogUtility.log(clazz, LogLevel.ERROR, "�������Ʒ��λ����");
			throw new ServiceException("�������Ʒ��λ����");
		}

		catch (CreateException e3) {
			LogUtility.log(clazz, LogLevel.ERROR, "�������Ʒ��������");
			throw new ServiceException("�������Ʒ��������");
		}
	}

	/**
	 * @param productDto
	 */
	private void updateCondProduct(CampaignCondProductDTO productDto)
			throws ServiceException {

		try {

			CampaignCondProductHome camProdHome = HomeLocater
					.getCampaignCondProductHome();
			CampaignCondProduct camProd = camProdHome
					.findByPrimaryKey(new Integer(productDto.getSeqNo()));
			if (camProd.ejbUpdate(productDto) == -1) {

				throw new RuntimeException(
						"CampaignCondProductDTO update fail, ID:"
								+ productDto.getSeqNo()
								+ ". Please make sure that dt_lastmod of CampaignCondProductDTO is set.");
			}

		} catch (HomeFactoryException e1) {
			LogUtility.log(clazz, LogLevel.ERROR, "�������Ʒ��λ����");
			throw new ServiceException("�������Ʒ��λ����");
		}

		catch (FinderException e) {
			LogUtility.log(clazz, LogLevel.ERROR, e);
			throw new ServiceException("���Ҵ������Ʒ������ˮ��Ϊ��"
					+ productDto.getSeqNo());
		}
	}

	/**
	 * @param productDto
	 */
	private void updateCondPackage(CampaignCondPackageDTO packageDto)
			throws ServiceException {

		try {

			CampaignCondPackageHome camPackHome = HomeLocater
					.getCampaignCondPackageHome();
			CampaignCondPackage camProd = camPackHome
					.findByPrimaryKey(new Integer(packageDto.getSeqNo()));
			if (camProd.ejbUpdate(packageDto) == -1) {

				throw new RuntimeException(
						"CampaignCondPackageDTO update fail, ID:"
								+ packageDto.getSeqNo()
								+ ". Please make sure that dt_lastmod of CampaignCondPackageDTO is set.");
			}

		} catch (HomeFactoryException e1) {
			LogUtility.log(clazz, LogLevel.ERROR, "�������Ʒ��λ����");
			throw new ServiceException("�������Ʒ��λ����");
		}

		catch (FinderException e) {
			LogUtility.log(clazz, LogLevel.ERROR, e);
			throw new ServiceException("���Ҵ������Ʒ������ˮ��Ϊ��"
					+ packageDto.getSeqNo());
		}
	}

	/**
	 * @param productDto
	 */
	private void updateCampaignCond(CampaignCondCampaignDTO campaignCondDto)
			throws ServiceException {

		try {

			CampaignCondCampaignHome camCondHome = HomeLocater
					.getCampaignCondCampaignHome();
			CampaignCondCampaign camProd = camCondHome
					.findByPrimaryKey(new Integer(campaignCondDto.getSeqNo()));
			if (camProd.ejbUpdate(campaignCondDto) == -1) {

				throw new RuntimeException(
						"CampaignCondCampaignDTO update fail, ID:"
								+ campaignCondDto.getSeqNo()
								+ ". Please make sure that dt_lastmod of CampaignCondCampaignDTO is set.");
			}

		} catch (HomeFactoryException e1) {
			LogUtility.log(clazz, LogLevel.ERROR, "������г����λ����");
			throw new ServiceException("������г����λ����");
		}

		catch (FinderException e) {
			LogUtility.log(clazz, LogLevel.ERROR, e);
			throw new ServiceException("���Ҵ�����г��������ˮ��Ϊ��"
					+ campaignCondDto.getSeqNo());
		}
	}

	/**
	 * @param BundlePrepaymentDTO
	 *            bundlePreDto
	 */
	private void updateBundlePrepayment(BundlePrepaymentDTO bundlePreDto)
			throws ServiceException {

		try {

			BundlePrepaymentHome bundlePrepaymentHome = HomeLocater
					.getBundlePrepaymentHome();
			BundlePrepayment bundleRomote = bundlePrepaymentHome
					.findByPrimaryKey(new Integer(bundlePreDto.getCampaignId()));
			if (bundlePreDto.getBundlePrepaymentPlanId() == bundleRomote
					.getBundlePrepaymentPlanId()) {
				if (bundleRomote.ejbUpdate(bundlePreDto) == -1) {

					throw new RuntimeException(
							"BundlePrepaymentDTO update fail,  Please make sure that dt_lastmod of BundlePrepaymentDTO is set.");
				}
			} else {
				checkUnique(bundlePreDto.getBundlePrepaymentPlanId());
				if (bundleRomote.ejbUpdate(bundlePreDto) == -1) {

					throw new RuntimeException(
							"BundlePrepaymentDTO update fail,  Please make sure that dt_lastmod of BundlePrepaymentDTO is set.");
				}
			}

		} catch (HomeFactoryException e1) {
			LogUtility.log(clazz, LogLevel.ERROR, "Ԥ�������ö�λ����");
			throw new ServiceException("Ԥ�������ö�λ����");
		}

		catch (FinderException e) {
			LogUtility.log(clazz, LogLevel.ERROR, e);
			throw new ServiceException("����Ԥ���ѳ����IDΪ��"
					+ bundlePreDto.getCampaignId());
		}
	}

	/**
	 * @param CampaignAgmtCampaignDTO
	 *            agmtCamDto
	 */
	private void updateAgmtCampaign(CampaignAgmtCampaignDTO agmtCamDto)
			throws ServiceException {

		try {

			CampaignAgmtCampaignHome agmtCamHome = HomeLocater
					.getCampaignAgmtCampaignHome();
			int bundleId = agmtCamDto.getTargetBundleId();
			int campaignId = agmtCamDto.getCampaignId();
			if (bundleId == 0 || campaignId == 0)
				throw new ServiceException("Ŀ�������������Ϊ��");
			CampaignAgmtCampaignPK pk = new CampaignAgmtCampaignPK(campaignId,
					bundleId);
			CampaignAgmtCampaign agmtCampaign = agmtCamHome
					.findByPrimaryKey(pk);
			if (agmtCampaign.ejbUpdate(agmtCamDto) == -1) {

				throw new RuntimeException(
						"CampaignAgmtCampaign update fail,  Please make sure that dt_lastmod of CampaignAgmtCampaign is set.");
			}

		} catch (HomeFactoryException e1) {
			LogUtility.log(clazz, LogLevel.ERROR, "�г��Э�鶨λ����");
			throw new ServiceException("�г��Э�鶨λ����");
		}

		catch (FinderException e) {
			LogUtility.log(clazz, LogLevel.ERROR, e);
			throw new ServiceException("�����г��Э�����");
		}
	}

	/**
	 * @param bundlePayDto
	 */
	private void updateBundlePayMethod(BundlePaymentMethodDTO bundlePayDto)
			throws ServiceException {

		try {
			LogUtility.log(clazz, LogLevel.DEBUG, "�޸��ײ����ѷ�ʽ��ʼ");
			BundlePaymentMethodHome bundleHome = HomeLocater
					.getBundlePaymentMethodHome();
			int bundleID = bundlePayDto.getBundleID();
			String flag = bundlePayDto.getRfBillingCycleFlag();
			System.out.println("***bundleID*******=" + bundleID);
			System.out.println("***flag*******=" + flag);
			BundlePaymentMethodPK pk = new BundlePaymentMethodPK(bundleID, flag);
			BundlePaymentMethod bundle = bundleHome.findByPrimaryKey(pk);
			System.out.println("***bundle.getDtLastmod()*******="
					+ bundle.getDtLastmod());
			System.out.println("***bundlePayDto*******="
					+ bundlePayDto.getDtLastmod());
			if (bundle.ejbUpdate(bundlePayDto) == -1) {
				throw new ServiceException(
						"BundlePaymentMethodDTO update fail. Please make sure that dt_lastmod of BundlePaymentMethodDTO is set.");
			}

		} catch (HomeFactoryException e1) {
			LogUtility.log(clazz, LogLevel.ERROR, "������г����λ����");
			throw new ServiceException("������г����λ����");
		}

		catch (FinderException e) {
			LogUtility.log(clazz, LogLevel.ERROR, e);
			throw new ServiceException("���Ҵ�����г��������ˮ��Ϊ��"
					+ bundlePayDto.getBundleID());
		}
	}

	private void removeOldMarket() throws ServiceException {

		try {

			CampaignToMarketSegmentHome camMarketHome = HomeLocater
					.getCampaignToMarketSegmentHome();
			Collection camMarket = camMarketHome.findByCampainId(campaignID);

			if (camMarket != null && !camMarket.isEmpty()) {
				Iterator camMarketIte = camMarket.iterator();
				while (camMarketIte.hasNext()) {
					CampaignToMarketSegment oldSegment = (CampaignToMarketSegment) camMarketIte
							.next();

					oldSegment.remove();
				}

			}

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

	private void removeOldProduct() throws ServiceException {

		try {

			CampaignAgmtProductHome camProdHome = HomeLocater
					.getCampaignAgmtProductHome();
			Collection oldprod = camProdHome.findByCampaignID(campaignID);
			if (oldprod != null && !oldprod.isEmpty()) {
				Iterator oldProdIte = oldprod.iterator();
				while (oldProdIte.hasNext()) {
					CampaignAgmtProduct oldprodCond = (CampaignAgmtProduct) oldProdIte
							.next();
					oldprodCond.remove();
				}

			}
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

	private void removeOldPack() throws ServiceException {

		try {

			CampaignAgmtPackageHome packageHome = HomeLocater
					.getCampaignAgmtPackageHome();
			Collection oldpack = packageHome.findByCampaignID(campaignID);
			if (oldpack != null && !oldpack.isEmpty()) {
				Iterator oldPackIte = oldpack.iterator();
				while (oldPackIte.hasNext()) {
					CampaignAgmtPackage oldpackCond = (CampaignAgmtPackage) oldPackIte
							.next();
					oldpackCond.remove();
				}

			}

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

	public GroupBargain create(GroupBargainDTO headDto) throws ServiceException {
		LogUtility.log(clazz, LogLevel.DEBUG, "�����Ź�ȯ��ʼ");

		try {
			GroupBargainHome gbHome = HomeLocater.getGroupBargainHome();
			GroupBargain gb = null;

			gb = gbHome.create(headDto);

			groupBargainID = gb.getId().intValue();

			return gb;

		} catch (HomeFactoryException e1) {
			LogUtility.log(clazz, LogLevel.ERROR, "�����Ź�ȯ��λ����");
			throw new ServiceException("�����Ź�ȯ��λ����");
		}

		catch (CreateException e3) {
			LogUtility.log(clazz, LogLevel.ERROR, "�����Ź�ȯ����");
			throw new ServiceException("�����Ź�ȯ����");
		}
	}

	/**
	 * @param BundlePaymentMethodDTO
	 *            bundlePayMethDto
	 */
	private void createBundlePayMethod(BundlePaymentMethodDTO bundlePayMethDto)
			throws ServiceException {

		try {

			BundlePaymentMethodHome bundleHome = HomeLocater
					.getBundlePaymentMethodHome();
			checkExist(bundlePayMethDto.getBundleID(), bundlePayMethDto
					.getRfBillingCycleFlag());
			bundleHome.create(bundlePayMethDto);
		} catch (HomeFactoryException e1) {
			LogUtility.log(clazz, LogLevel.ERROR, "�����ײ����ѷ�ʽ����");
			throw new ServiceException("�ײ����ѷ�ʽ��λ����");
		}

		catch (CreateException e3) {
			LogUtility.log(clazz, LogLevel.ERROR, "�����ײ����ѷ�ʽ����");
			throw new ServiceException("�����ײ����ѷ�ʽ����");
		}
	}

	private void checkExist(int bundleID, String rfBillingCycleFlag)
			throws ServiceException {

		LogUtility.log(clazz, LogLevel.DEBUG, "�Ƿ��Ѿ������ײ����ѷ�ʽ");

		String flag = BusinessUtility.getPaymentMethedByBundleIdAndFlag(
				bundleID, rfBillingCycleFlag);
		if (flag != null)
			throw new ServiceException("�Ѿ������ײ����ѷ�ʽ��");
	}

	/**
	 * ϵͳ���ã��ײ͡��Żݻ��Ӧ�Ľ������ ����
	 * 
	 * @param CampaignPaymentAwardDTO
	 */
	public void createCampaignPaymentAward(CampaignPaymentAwardDTO dto)
			throws ServiceException {

		try {
			CampaignPaymentAwardHome campayHome = HomeLocater
					.getCampaignPaymentAwardHome();
			campayHome.create(dto);
		} catch (HomeFactoryException e1) {
			LogUtility.log(clazz, LogLevel.ERROR, "����� ������� ��λ����");
			throw new ServiceException("����� ������� ��λ����");
		} catch (CreateException e3) {
			LogUtility.log(clazz, LogLevel.ERROR, "����� ������� ��������");
			throw new ServiceException("����� ������� ��������");
		}
	}

	public void createIppvWallet(CAWalletDefineDTO ippvDTO,
			MarketEJBEvent marketEvent) throws ServiceException {
		LogUtility.log(clazz, LogLevel.DEBUG, "����IppvǮ��");

		try {

			CAWalletDefineHome caHome = HomeLocater.getCAWalletDefineHome();
			// У������Ψһ��
			String submitKey = ippvDTO.getCaWalletCode();
			try {
				caHome.findByPrimaryKey(submitKey);
				throw new ServiceException("�������Ǯ�������Ѵ��ڣ�");
			} catch (FinderException e) {
			}
			CAWalletDefine ca = null;
			ca = caHome.create(ippvDTO);
			ippvWalletCode = ca.getCaWalletCode();
			// TODO Auto-generated catch block

		} catch (HomeFactoryException e1) {
			LogUtility.log(clazz, LogLevel.ERROR, "IppvǮ����λ����");
			throw new ServiceException("IppvǮ����λ����");
		} catch (CreateException e3) {
			LogUtility.log(clazz, LogLevel.ERROR, "IppvǮ����������");
			throw new ServiceException("IppvǮ����������");
		}
	}
	public void createDtvMigration(DtvMigrationAreaDTO dto,
			MarketEJBEvent marketEvent) throws ServiceException {
		LogUtility.log(clazz, LogLevel.DEBUG, "����DtvMigration");

		try {

			DtvMigrationAreaHome home = HomeLocater
					.getDtvMigrationAreaHome();

			DtvMigrationArea domain = null;
			domain = home.create(dto);

		} catch (HomeFactoryException e1) {
			LogUtility.log(clazz, LogLevel.ERROR, "ƽ��С����λ����");
			throw new ServiceException("ƽ��С����λ����");
		} catch (CreateException e3) {
			LogUtility.log(clazz, LogLevel.ERROR, "ƽ��С����������");
			throw new ServiceException("ƽ��С����������");
		}
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	/**
	 * @return Returns the campaignID.
	 */
	public int getCampaignID() {
		return campaignID;
	}

	/**
	 * @param campaignID
	 *            The campaignID to set.
	 */
	public void setCampaignID(int campaignID) {
		this.campaignID = campaignID;
	}

	/**
	 * @return Returns the groupBargainID.
	 */
	public int getGroupBargainID() {
		return groupBargainID;
	}

	/**
	 * @param groupBargainID
	 *            The groupBargainID to set.
	 */
	public void setGroupBargainID(int groupBargainID) {
		this.groupBargainID = groupBargainID;
	}

	public String getIppvWalletCode() {
		return ippvWalletCode;
	}

	public void setIppvWalletCode(String ippvWalletCode) {
		this.ippvWalletCode = ippvWalletCode;
	}
}
