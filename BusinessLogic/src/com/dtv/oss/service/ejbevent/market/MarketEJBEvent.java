/*
 * Created on 2004-8-12
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.dtv.oss.service.ejbevent.market;

import java.util.Collection;

import com.dtv.oss.dto.BundlePaymentMethodDTO;
import com.dtv.oss.dto.BundlePrepaymentDTO;
import com.dtv.oss.dto.CAWalletDefineDTO;
import com.dtv.oss.dto.CampaignAgmtCampaignDTO;
import com.dtv.oss.dto.CampaignCondCampaignDTO;
import com.dtv.oss.dto.CampaignCondPackageDTO;
import com.dtv.oss.dto.CampaignCondProductDTO;
import com.dtv.oss.dto.CampaignDTO;
import com.dtv.oss.dto.DtvMigrationAreaDTO;
import com.dtv.oss.dto.GroupBargainDTO;
import com.dtv.oss.dto.GroupBargainDetailDTO;
import com.dtv.oss.dto.CampaignPaymentAwardDTO;
import com.dtv.oss.service.ejbevent.AbstractEJBEvent;

/**
 * @author 240322y
 * 
 * To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Generation - Code and Comments
 */
public class MarketEJBEvent extends AbstractEJBEvent {

	private CAWalletDefineDTO ippvDTO;
	
	private DtvMigrationAreaDTO dtvMigrationDto;

	public static final int CAMPAIGN_DELETE = 202; // 优惠活动 删除

	public static final int GROUPBARGAIN_DETAIL_DELETE = 401; // 团购 删除

	public static final int GROUPBARGAIN_DETAIL_UPDATE = 402; // 团购 更新

	private CampaignCondProductDTO condProdDto;

	private CampaignCondPackageDTO condPackDto;

	private BundlePaymentMethodDTO bundPayMethDto;

	private BundlePrepaymentDTO bundPreMethDto;

	private CampaignCondCampaignDTO condCampDto;

	private Collection condMarketDtoCol;

	private Collection agmtProdDtoCol;

	private Collection agmtPackDtoCol;

	private boolean doPost;

	private int seqNo;

	private CampaignDTO campaignDto;

	private GroupBargainDTO groupBargainDto;

	private GroupBargainDetailDTO detailDto;

	private CampaignAgmtCampaignDTO agmtCampaignDto;

	private CampaignPaymentAwardDTO paymentAwardDto;

	public CampaignPaymentAwardDTO getCampaignPaymentAwardDto() {
		return paymentAwardDto;
	}

	public void setCampaignPaymentAwardDto(
			CampaignPaymentAwardDTO paymentAwardDto) {
		this.paymentAwardDto = paymentAwardDto;
	}

	public CampaignAgmtCampaignDTO getAgmtCampaignDto() {
		return agmtCampaignDto;
	}

	public void setAgmtCampaignDto(CampaignAgmtCampaignDTO agmtCampaignDto) {
		this.agmtCampaignDto = agmtCampaignDto;
	}

	/**
	 * @return Returns the groupBargainDto.
	 */
	public GroupBargainDTO getGroupBargainDto() {
		return groupBargainDto;
	}

	/**
	 * @param groupBargainDto
	 *            The groupBargainDto to set.
	 */
	public void setGroupBargainDto(GroupBargainDTO groupBargainDto) {
		this.groupBargainDto = groupBargainDto;
	}

	// 团购券的编码格式
	private String format;

	// 起始编码
	private int fromBegin;

	private int campaignID;

	private int size;

	/**
	 * @return Returns the format.
	 */
	public String getFormat() {
		return format;
	}

	/**
	 * @param format
	 *            The format to set.
	 */
	public void setFormat(String format) {
		this.format = format;
	}

	/**
	 * @return Returns the fromBegin.
	 */
	public int getFromBegin() {
		return fromBegin;
	}

	/**
	 * @param fromBegin
	 *            The fromBegin to set.
	 */
	public void setFromBegin(int fromBegin) {
		this.fromBegin = fromBegin;
	}

	/**
	 * @return Returns the doPost.
	 */
	public boolean isDoPost() {
		return doPost;
	}

	/**
	 * @param doPost
	 *            The doPost to set.
	 */
	public void setDoPost(boolean doPost) {
		this.doPost = doPost;
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
	 * @return Returns the campaignDto.
	 */
	public CampaignDTO getCampaignDto() {
		return campaignDto;
	}

	/**
	 * @param campaignDto
	 *            The campaignDto to set.
	 */
	public void setCampaignDto(CampaignDTO campaignDto) {
		this.campaignDto = campaignDto;
	}

	/**
	 * @return Returns the camToMarketDtoCol.
	 */

	/**
	 * @return Returns the size.
	 */
	public int getSize() {
		return size;
	}

	/**
	 * @param size
	 *            The size to set.
	 */
	public void setSize(int size) {
		this.size = size;
	}

	/**
	 * @return Returns the detailDto.
	 */
	public GroupBargainDetailDTO getDetailDto() {
		return detailDto;
	}

	/**
	 * @param detailDto
	 *            The detailDto to set.
	 */
	public void setDetailDto(GroupBargainDetailDTO detailDto) {
		this.detailDto = detailDto;
	}

	/**
	 * @return Returns the condCampDto.
	 */
	public CampaignCondCampaignDTO getCondCampDto() {
		return condCampDto;
	}

	/**
	 * @param condCampDto
	 *            The condCampDto to set.
	 */
	public void setCondCampDto(CampaignCondCampaignDTO condCampDto) {
		this.condCampDto = condCampDto;
	}

	/**
	 * @return Returns the condPackDto.
	 */
	public CampaignCondPackageDTO getCondPackDto() {
		return condPackDto;
	}

	/**
	 * @param condPackDto
	 *            The condPackDto to set.
	 */
	public void setCondPackDto(CampaignCondPackageDTO condPackDto) {
		this.condPackDto = condPackDto;
	}

	/**
	 * @return Returns the condProdDto.
	 */
	public CampaignCondProductDTO getCondProdDto() {
		return condProdDto;
	}

	/**
	 * @param condProdDto
	 *            The condProdDto to set.
	 */
	public void setCondProdDto(CampaignCondProductDTO condProdDto) {
		this.condProdDto = condProdDto;
	}

	/**
	 * @return Returns the condMarketDtoCol.
	 */
	public Collection getCondMarketDtoCol() {
		return condMarketDtoCol;
	}

	/**
	 * @param condMarketDtoCol
	 *            The condMarketDtoCol to set.
	 */
	public void setCondMarketDtoCol(Collection condMarketDtoCol) {
		this.condMarketDtoCol = condMarketDtoCol;
	}

	/**
	 * @return Returns the agmtPackDtoCol.
	 */
	public Collection getAgmtPackDtoCol() {
		return agmtPackDtoCol;
	}

	/**
	 * @param agmtPackDtoCol
	 *            The agmtPackDtoCol to set.
	 */
	public void setAgmtPackDtoCol(Collection agmtPackDtoCol) {
		this.agmtPackDtoCol = agmtPackDtoCol;
	}

	/**
	 * @return Returns the agmtProdDtoCol.
	 */
	public Collection getAgmtProdDtoCol() {
		return agmtProdDtoCol;
	}

	/**
	 * @param agmtProdDtoCol
	 *            The agmtProdDtoCol to set.
	 */
	public void setAgmtProdDtoCol(Collection agmtProdDtoCol) {
		this.agmtProdDtoCol = agmtProdDtoCol;
	}

	/**
	 * @return Returns the seqNo.
	 */
	public int getSeqNo() {
		return seqNo;
	}

	/**
	 * @param seqNo
	 *            The seqNo to set.
	 */
	public void setSeqNo(int seqNo) {
		this.seqNo = seqNo;
	}

	/**
	 * @return Returns the condCampDto.
	 */
	public BundlePaymentMethodDTO getBundlePayMethodDto() {
		return bundPayMethDto;
	}

	/**
	 * @param condCampDto
	 *            The condCampDto to set.
	 */
	public void setBundlePayMethodDto(BundlePaymentMethodDTO bundPayMethDto) {
		this.bundPayMethDto = bundPayMethDto;
	}

	public BundlePrepaymentDTO getBundPreMethDto() {
		return bundPreMethDto;
	}

	public void setBundPreMethDto(BundlePrepaymentDTO bundPreMethDto) {
		this.bundPreMethDto = bundPreMethDto;
	}

	public CAWalletDefineDTO getIppvDTO() {
		return ippvDTO;
	}

	public void setIppvDTO(CAWalletDefineDTO ippvDTO) {
		this.ippvDTO = ippvDTO;
	}

	public DtvMigrationAreaDTO getDtvMigrationDto() {
		return dtvMigrationDto;
	}

	public void setDtvMigrationDto(DtvMigrationAreaDTO dtvMigrationDto) {
		this.dtvMigrationDto = dtvMigrationDto;
	}

}
