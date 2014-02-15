package com.dtv.oss.service.ejbevent.config;

import java.util.Collection;

import com.dtv.oss.dto.ProductDTO;
import com.dtv.oss.dto.ProductDependencyDTO;
import com.dtv.oss.dto.ProductPackageDTO;
import com.dtv.oss.dto.ProductPropertyDTO;
import com.dtv.oss.service.ejbevent.AbstractEJBEvent;

public class ConfigProductEJBEvent extends AbstractEJBEvent {

    private ProductDTO productDTO;
    private ProductPackageDTO productPackageDTO;
    private ProductDependencyDTO productDependencyDTO;
    private String productIDstr;
    private int packageID;
    private String segmentIDstr;
    private String campaignIdStr;
    private ProductPropertyDTO productPropertyDTO;
    private String productDependencySeqNos;
    private String productPropertyNames;
    
	/**
	 * @return Returns the campaignIdStr.
	 */
	public String getCampaignIdStr() {
		return campaignIdStr;
	}
	/**
	 * @param campaignIdStr The campaignIdStr to set.
	 */
	public void setCampaignIdStr(String campaignIdStr) {
		this.campaignIdStr = campaignIdStr;
	}
    private Collection packageLineCol;
	/**
	 * @return Returns the packageLineCol.
	 */
	public Collection getPackageLineCol() {
		return packageLineCol;
	}
	/**
	 * @param packageLineCol The packageLineCol to set.
	 */
	public void setPackageLineCol(Collection packageLineCol) {
		this.packageLineCol = packageLineCol;
	}
	/**
	 * @return Returns the segmentIDstr.
	 */
	public String getSegmentIDstr() {
		return segmentIDstr;
	}
	/**
	 * @param segmentIDstr The segmentIDstr to set.
	 */
	public void setSegmentIDstr(String segmentIDstr) {
		this.segmentIDstr = segmentIDstr;
	}
	/**
	 * @return Returns the productPackageDTO.
	 */
	public ProductPackageDTO getProductPackageDTO() {
		return productPackageDTO;
	}
	/**
	 * @param productPackageDTO The productPackageDTO to set.
	 */
	public void setProductPackageDTO(ProductPackageDTO productPackageDTO) {
		this.productPackageDTO = productPackageDTO;
	}
  
    public String getProductDependencySeqNos() {
		return productDependencySeqNos;
	}

	public void setProductDependencySeqNos(String productDependencySeqNos) {
		this.productDependencySeqNos = productDependencySeqNos;
	}

	public String getProductPropertyNames() {
		return productPropertyNames;
	}

	public void setProductPropertyNames(String productPropertyNames) {
		this.productPropertyNames = productPropertyNames;
	}

	public ProductPropertyDTO getProductPropertyDTO() {
		return productPropertyDTO;
	}

	public void setProductPropertyDTO(ProductPropertyDTO productPropertyDTO) {
		this.productPropertyDTO = productPropertyDTO;
	}

	public ProductDependencyDTO getProductDependencyDTO() {
		return productDependencyDTO;
	}

	public void setProductDependencyDTO(ProductDependencyDTO productDependencyDTO) {
		this.productDependencyDTO = productDependencyDTO;
	}

	
    public ConfigProductEJBEvent(int actionType) {
    	this.actionType =actionType;
	}
    public ConfigProductEJBEvent() {
     
	}
	public ProductDTO getProductDTO() {
		return productDTO;
	}

	public void setProductDTO(ProductDTO productDTO) {
		this.productDTO = productDTO;
	}
	/**
	 * @return Returns the packageID.
	 */
	public int getPackageID() {
		return packageID;
	}
	/**
	 * @param packageID The packageID to set.
	 */
	public void setPackageID(int packageID) {
		this.packageID = packageID;
	}
	/**
	 * @return Returns the productIDstr.
	 */
	public String getProductIDstr() {
		return productIDstr;
	}
	/**
	 * @param productIDstr The productIDstr to set.
	 */
	public void setProductIDstr(String productIDstr) {
		this.productIDstr = productIDstr;
	}
}
