package com.dtv.oss.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import com.dtv.oss.dto.PackageLineDTO;

public class PackageLineDtoAssembler {
	public static PackageLineDTO createDto(PackageLine packageLine) {
		PackageLineDTO packageLineDTO = new PackageLineDTO();

		if (packageLine != null) {
			packageLineDTO.setDtCreate(packageLine.getDtCreate());
			packageLineDTO.setDtLastmod(packageLine.getDtLastmod());
			packageLineDTO.setPackageId(packageLine.getPackageId());
			packageLineDTO.setProductId(packageLine.getProductId());
			packageLineDTO.setProductNum(packageLine.getProductNum());
		}
		return packageLineDTO;
	}

	public static PackageLineDTO[] createDtos(Collection packageLines) {
		List list = new ArrayList();

		if (packageLines != null) {
			Iterator iterator = packageLines.iterator();
			while (iterator.hasNext()) {
				list.add(createDto((PackageLine) iterator.next()));
			}
		}
		PackageLineDTO[] returnArray = new PackageLineDTO[list.size()];
		return (PackageLineDTO[]) list.toArray(returnArray);
	}
}