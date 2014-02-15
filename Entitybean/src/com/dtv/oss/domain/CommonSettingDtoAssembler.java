package com.dtv.oss.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import com.dtv.oss.dto.CommonSettingDTO;

public class CommonSettingDtoAssembler {
	public static CommonSettingDTO createDto(CommonSetting commonSetting) {
		CommonSettingDTO commonSettingDTO = new CommonSettingDTO();

		if (commonSetting != null) {
			commonSettingDTO.setName(commonSetting.getName());
			commonSettingDTO.setDescription(commonSetting.getDescription());
			commonSettingDTO.setModule(commonSetting.getModule());
			commonSettingDTO.setAttable(commonSetting.getAttable());
			commonSettingDTO.setField(commonSetting.getField());
			commonSettingDTO.setFixed(commonSetting.getFixed());
			commonSettingDTO.setType(commonSetting.getType());
			commonSettingDTO.setDtCreate(commonSetting.getDtCreate());
			commonSettingDTO.setDtLastmod(commonSetting.getDtLastmod());
		}
		return commonSettingDTO;
	}

	public static CommonSettingDTO[] createDtos(Collection commonSettings) {
		List list = new ArrayList();

		if (commonSettings != null) {
			Iterator iterator = commonSettings.iterator();
			while (iterator.hasNext()) {
				list.add(createDto((CommonSetting) iterator.next()));
			}
		}
		CommonSettingDTO[] returnArray = new CommonSettingDTO[list.size()];
		return (CommonSettingDTO[]) list.toArray(returnArray);
	}
}