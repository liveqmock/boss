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

		LogUtility.log(clazz, LogLevel.DEBUG, "修改促销活动的条件开始");
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

		LogUtility.log(clazz, LogLevel.DEBUG, "创建促销活动的产品条件开始");
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
			LogUtility.log(clazz, LogLevel.ERROR, "促销活动市场分区定位出错！");
			throw new ServiceException("促销活动市场分区定位出错！");
		}

		catch (CreateException e3) {
			LogUtility.log(clazz, LogLevel.ERROR, "促销活动市场分区创建出错！");
			throw new ServiceException("促销活动市场分区创建出错！");
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
			LogUtility.log(clazz, LogLevel.ERROR, "协议产品包定位出错！");
			throw new ServiceException("协议产品包定位出错！");
		}

		catch (CreateException e3) {
			LogUtility.log(clazz, LogLevel.ERROR, "协议产品包创建出错！");
			throw new ServiceException("协议产品包创建出错！");
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
			LogUtility.log(clazz, LogLevel.ERROR, "协议产品定位出错！");
			throw new ServiceException("协议产品定位出错！");
		}

		catch (CreateException e3) {
			LogUtility.log(clazz, LogLevel.ERROR, "协议产品创建出错！");
			throw new ServiceException("协议产品创建出错！");
		}
	}

	/**
	 * @param camDto
	 *            促销活动dto
	 * @param oldProdCol
	 *            已有产品集合
	 * @param packageCol
	 *            新产品包
	 * @throws ServiceException
	 */

	public void createCampaign(CampaignDTO camDto, MarketEJBEvent marketEvent)
			throws ServiceException {
		LogUtility.log(clazz, LogLevel.DEBUG, "创建促销活动");

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
			LogUtility.log(clazz, LogLevel.ERROR, "促销活动定位出错！");
			throw new ServiceException("促销活动定位出错！");
		}

		catch (CreateException e3) {
			LogUtility.log(clazz, LogLevel.ERROR, "促销活动创建出错！");
			throw new ServiceException("促销活动创建出错！");
		}
	}

	public void updateCampaign(MarketEJBEvent marketEvent)
			throws ServiceException {

		LogUtility.log(clazz, LogLevel.DEBUG, "创建促销活动的产品条件开始");

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
			LogUtility.log(clazz, LogLevel.ERROR, "促销活动市场活动定位出错！");
			throw new ServiceException("促销活动市场活动定位出错！");
		}

		catch (CreateException e3) {
			LogUtility.log(clazz, LogLevel.ERROR, "促销活动市场活动创建出错！");
			throw new ServiceException("促销活动市场活动创建出错！");
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
			LogUtility.log(clazz, LogLevel.ERROR, "预付费定位出错！");
			throw new ServiceException("预付费定位出错！");
		}

		catch (CreateException e3) {
			LogUtility.log(clazz, LogLevel.ERROR, "预付费创建出错！");
			throw new ServiceException("预付费创建出错！");
		}
	}

	/**
	 * @param bundlePreDto
	 */
	private void createAgmtCampaign(CampaignAgmtCampaignDTO agmtCamDto)
			throws ServiceException {

		if (agmtCamDto.getCampaignId() == 0
				|| agmtCamDto.getTargetBundleId() == 0)
			throw new ServiceException("活动ID,跟目标活动ID都不能为空！");

		try {

			CampaignAgmtCampaignHome camHome = HomeLocater
					.getCampaignAgmtCampaignHome();

			camHome.create(agmtCamDto);
		} catch (HomeFactoryException e1) {
			LogUtility.log(clazz, LogLevel.ERROR, "取得活动协议的home接口处错！");
			throw new ServiceException("没有找到活动协议！");
		}

		catch (CreateException e3) {
			LogUtility.log(clazz, LogLevel.ERROR, "活动协议创建出错！");
			throw new ServiceException("活动协议创建出错！");
		}
	}

	private void checkUnique(int bundlePrepaymentPlanId)
			throws ServiceException {
		// TODO Auto-generated method stub
		boolean isExist = BusinessUtility.isExitPlanID(bundlePrepaymentPlanId);
		if (isExist)
			throw new ServiceException("套餐预付计划ID已经存在，请重新选择！");

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
			LogUtility.log(clazz, LogLevel.ERROR, "促销活动产品包定位出错！");
			throw new ServiceException("促销活动产品包定位出错！");
		}

		catch (CreateException e3) {
			LogUtility.log(clazz, LogLevel.ERROR, "促销活动产品包创建出错！");
			throw new ServiceException("促销活动产品包创建出错！");
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
			LogUtility.log(clazz, LogLevel.ERROR, "促销活动产品定位出错！");
			throw new ServiceException("促销活动产品定位出错！");
		}

		catch (CreateException e3) {
			LogUtility.log(clazz, LogLevel.ERROR, "促销活动产品创建出错！");
			throw new ServiceException("促销活动产品创建出错！");
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
			LogUtility.log(clazz, LogLevel.ERROR, "促销活动产品定位出错！");
			throw new ServiceException("促销活动产品定位出错！");
		}

		catch (FinderException e) {
			LogUtility.log(clazz, LogLevel.ERROR, e);
			throw new ServiceException("查找促销活动产品出错，流水号为："
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
			LogUtility.log(clazz, LogLevel.ERROR, "促销活动产品定位出错！");
			throw new ServiceException("促销活动产品定位出错！");
		}

		catch (FinderException e) {
			LogUtility.log(clazz, LogLevel.ERROR, e);
			throw new ServiceException("查找促销活动产品出错，流水号为："
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
			LogUtility.log(clazz, LogLevel.ERROR, "促销活动市场活动定位出错！");
			throw new ServiceException("促销活动市场活动定位出错！");
		}

		catch (FinderException e) {
			LogUtility.log(clazz, LogLevel.ERROR, e);
			throw new ServiceException("查找促销活动市场活动出错，流水号为："
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
			LogUtility.log(clazz, LogLevel.ERROR, "预付费设置定位出错！");
			throw new ServiceException("预付费设置定位出错！");
		}

		catch (FinderException e) {
			LogUtility.log(clazz, LogLevel.ERROR, e);
			throw new ServiceException("查找预付费出错，活动ID为："
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
				throw new ServiceException("目标活动，跟活动都不能为空");
			CampaignAgmtCampaignPK pk = new CampaignAgmtCampaignPK(campaignId,
					bundleId);
			CampaignAgmtCampaign agmtCampaign = agmtCamHome
					.findByPrimaryKey(pk);
			if (agmtCampaign.ejbUpdate(agmtCamDto) == -1) {

				throw new RuntimeException(
						"CampaignAgmtCampaign update fail,  Please make sure that dt_lastmod of CampaignAgmtCampaign is set.");
			}

		} catch (HomeFactoryException e1) {
			LogUtility.log(clazz, LogLevel.ERROR, "市场活动协议定位出错！");
			throw new ServiceException("市场活动协议定位出错！");
		}

		catch (FinderException e) {
			LogUtility.log(clazz, LogLevel.ERROR, e);
			throw new ServiceException("查找市场活动协议出错");
		}
	}

	/**
	 * @param bundlePayDto
	 */
	private void updateBundlePayMethod(BundlePaymentMethodDTO bundlePayDto)
			throws ServiceException {

		try {
			LogUtility.log(clazz, LogLevel.DEBUG, "修改套餐续费方式开始");
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
			LogUtility.log(clazz, LogLevel.ERROR, "促销活动市场活动定位出错！");
			throw new ServiceException("促销活动市场活动定位出错！");
		}

		catch (FinderException e) {
			LogUtility.log(clazz, LogLevel.ERROR, e);
			throw new ServiceException("查找促销活动市场活动出错，流水号为："
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
		LogUtility.log(clazz, LogLevel.DEBUG, "创建团购券开始");

		try {
			GroupBargainHome gbHome = HomeLocater.getGroupBargainHome();
			GroupBargain gb = null;

			gb = gbHome.create(headDto);

			groupBargainID = gb.getId().intValue();

			return gb;

		} catch (HomeFactoryException e1) {
			LogUtility.log(clazz, LogLevel.ERROR, "创建团购券定位出错！");
			throw new ServiceException("创建团购券定位出错！");
		}

		catch (CreateException e3) {
			LogUtility.log(clazz, LogLevel.ERROR, "创建团购券出错！");
			throw new ServiceException("创建团购券出错！");
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
			LogUtility.log(clazz, LogLevel.ERROR, "创建套餐续费方式出错！");
			throw new ServiceException("套餐续费方式定位出错！");
		}

		catch (CreateException e3) {
			LogUtility.log(clazz, LogLevel.ERROR, "创建套餐续费方式出错！");
			throw new ServiceException("创建套餐续费方式出错！");
		}
	}

	private void checkExist(int bundleID, String rfBillingCycleFlag)
			throws ServiceException {

		LogUtility.log(clazz, LogLevel.DEBUG, "是否已经存在套餐续费方式");

		String flag = BusinessUtility.getPaymentMethedByBundleIdAndFlag(
				bundleID, rfBillingCycleFlag);
		if (flag != null)
			throw new ServiceException("已经存在套餐续费方式！");
	}

	/**
	 * 系统配置，套餐、优惠活动对应的奖励金额 新增
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
			LogUtility.log(clazz, LogLevel.ERROR, "促销活动 奖励金额 定位出错！");
			throw new ServiceException("促销活动 奖励金额 定位出错！");
		} catch (CreateException e3) {
			LogUtility.log(clazz, LogLevel.ERROR, "促销活动 奖励金额 创建出错！");
			throw new ServiceException("促销活动 奖励金额 创建出错！");
		}
	}

	public void createIppvWallet(CAWalletDefineDTO ippvDTO,
			MarketEJBEvent marketEvent) throws ServiceException {
		LogUtility.log(clazz, LogLevel.DEBUG, "创建Ippv钱包");

		try {

			CAWalletDefineHome caHome = HomeLocater.getCAWalletDefineHome();
			// 校验主键唯一性
			String submitKey = ippvDTO.getCaWalletCode();
			try {
				caHome.findByPrimaryKey(submitKey);
				throw new ServiceException("您输入的钱包编码已存在！");
			} catch (FinderException e) {
			}
			CAWalletDefine ca = null;
			ca = caHome.create(ippvDTO);
			ippvWalletCode = ca.getCaWalletCode();
			// TODO Auto-generated catch block

		} catch (HomeFactoryException e1) {
			LogUtility.log(clazz, LogLevel.ERROR, "Ippv钱包定位出错！");
			throw new ServiceException("Ippv钱包定位出错！");
		} catch (CreateException e3) {
			LogUtility.log(clazz, LogLevel.ERROR, "Ippv钱包创建出错！");
			throw new ServiceException("Ippv钱包创建出错！");
		}
	}
	public void createDtvMigration(DtvMigrationAreaDTO dto,
			MarketEJBEvent marketEvent) throws ServiceException {
		LogUtility.log(clazz, LogLevel.DEBUG, "创建DtvMigration");

		try {

			DtvMigrationAreaHome home = HomeLocater
					.getDtvMigrationAreaHome();

			DtvMigrationArea domain = null;
			domain = home.create(dto);

		} catch (HomeFactoryException e1) {
			LogUtility.log(clazz, LogLevel.ERROR, "平移小区定位出错！");
			throw new ServiceException("平移小区定位出错！");
		} catch (CreateException e3) {
			LogUtility.log(clazz, LogLevel.ERROR, "平移小区创建出错！");
			throw new ServiceException("平移小区创建出错！");
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
