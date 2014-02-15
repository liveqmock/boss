package com.dtv.oss.service.ejbevent.config;

import com.dtv.oss.dto.ProductDTO;
import com.dtv.oss.dto.ProductDependencyDTO;
import com.dtv.oss.dto.ProductPropertyDTO;
import com.dtv.oss.dto.ServiceDTO;
import com.dtv.oss.dto.ServiceDependencyDTO;
import com.dtv.oss.dto.ServiceResourceDTO;
import com.dtv.oss.dto.ServiceResourceDetailDTO;
import com.dtv.oss.service.ejbevent.csr.CsrAbstractEJbevent;

public class ConfigServiceEJBEvent extends CsrAbstractEJbevent {
    private ServiceDTO serviceDto;
    private ServiceDependencyDTO serviceDependencyDto;
    private String  serviceIds;
    private String  referServiceIds;
    private ServiceResourceDTO serviceResourceDto;
    private ServiceResourceDetailDTO serviceResourceDetailDto;
    private ProductDTO productDTO;
    private ProductDependencyDTO productDependencyDTO;
    private ProductPropertyDTO productPropertyDTO;
    private String productDependencySeqNos;
    private String productPropertyNames;
    private String resourceNames;
    private String resourceDetailIds ;
    
    public String getResourceDetailIds() {
		return resourceDetailIds;
	}

	public void setResourceDetailIds(String resourceDetailIds) {
		this.resourceDetailIds = resourceDetailIds;
	}

	public String getResourceNames() {
		return resourceNames;
	}

	public void setResourceNames(String resourceNames) {
		this.resourceNames = resourceNames;
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

	
    public ConfigServiceEJBEvent(int actionType) {
    	this.actionType =actionType;
	}
    
	public ServiceDependencyDTO getServiceDependencyDto() {
		return serviceDependencyDto;
	}
	public void setServiceDependencyDto(ServiceDependencyDTO serviceDependencyDto) {
		this.serviceDependencyDto = serviceDependencyDto;
	}
	public ServiceDTO getServiceDto() {
		return serviceDto;
	}
	public void setServiceDto(ServiceDTO serviceDto) {
		this.serviceDto = serviceDto;
	}
	public String getServiceIds() {
		return serviceIds;
	}
	public void setServiceIds(String serviceIds) {
		this.serviceIds = serviceIds;
	}
	public ServiceResourceDetailDTO getServiceResourceDetailDto() {
		return serviceResourceDetailDto;
	}
	public void setServiceResourceDetailDto(
			ServiceResourceDetailDTO serviceResourceDetailDto) {
		this.serviceResourceDetailDto = serviceResourceDetailDto;
	}
	public ServiceResourceDTO getServiceResourceDto() {
		return serviceResourceDto;
	}
	public void setServiceResourceDto(ServiceResourceDTO serviceResourceDto) {
		this.serviceResourceDto = serviceResourceDto;
	}

	public ProductDTO getProductDTO() {
		return productDTO;
	}

	public void setProductDTO(ProductDTO productDTO) {
		this.productDTO = productDTO;
	}

	public String getReferServiceIds() {
		return referServiceIds;
	}

	public void setReferServiceIds(String referServiceIds) {
		this.referServiceIds = referServiceIds;
	}
}
