package com.dtv.oss.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import com.dtv.oss.dto.CAProductDTO;

public class CAProductDtoAssembler {
	public static CAProductDTO createDto(CAProduct cAProduct) {
		CAProductDTO cAProductDTO = new CAProductDTO();

		if (cAProduct != null) {
			cAProductDTO.setEntitlement(cAProduct.getEntitlement());
			cAProductDTO.setDescription(cAProduct.getDescription());
			cAProductDTO.setDtCreate(cAProduct.getDtCreate());
			cAProductDTO.setDtLastmod(cAProduct.getDtLastmod());
			cAProductDTO.setProductId(cAProduct.getProductId());
			cAProductDTO.setConditionId(cAProduct.getConditionId());
		}
		return cAProductDTO;
	}

	public static CAProductDTO[] createDtos(Collection cAProducts) {
		List list = new ArrayList();

		if (cAProducts != null) {
			Iterator iterator = cAProducts.iterator();
			while (iterator.hasNext()) {
				list.add(createDto((CAProduct) iterator.next()));
			}
		}
		CAProductDTO[] returnArray = new CAProductDTO[list.size()];
		return (CAProductDTO[]) list.toArray(returnArray);
	}
}