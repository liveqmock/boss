package com.dtv.oss.util;

import java.util.*;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.DiagnosisInfoDTO;
import com.dtv.oss.dto.MaterialUsageDTO;
import com.dtv.oss.web.exception.WebActionException;
import com.dtv.oss.web.util.WebUtil;
import com.dtv.oss.util.Constant;

public class DynamicServey {
	// 回访信息
	public static Collection getCallBackInfo(HttpServletRequest request,
			String paramName, String sourceType, int referSourceID)
			throws WebActionException {
		String id = (request.getParameter(paramName) == null) ? "" : request
				.getParameter(paramName);
		String[] callbackIds = id.split(";");
		ArrayList callBackInfoList = new ArrayList();
		String valueTypeName = paramName + "_ValueType";
		String callbackValueTypeName = (request.getParameter(valueTypeName) == null) ? ""
				: request.getParameter(valueTypeName);
		String[] callbackValueType = callbackValueTypeName.split(";");

		try {
			for (int i = 0; i < callbackIds.length; i++) {
				String callBackInfoHiddenName = paramName + "_"
						+ callbackIds[i];
				String[] callBackInfoHiddenValues = request
						.getParameterValues(callBackInfoHiddenName);
				if (callBackInfoHiddenValues != null) {
					for (int j = 0; j < callBackInfoHiddenValues.length; j++) {
						com.dtv.oss.dto.CallBackInfoDTO callBackInfoDto = new com.dtv.oss.dto.CallBackInfoDTO();
						callBackInfoDto.setReferSourceId(referSourceID);
						callBackInfoDto.setReferSourceType(sourceType);
						callBackInfoDto.setInfoSettingId(Integer
								.parseInt(callbackIds[i]));
						if (Constant.SERVEY_TAGTYPE_TEXT
								.equals(callbackValueType[i])||
								Constant.SERVEY_TAGTYPE_DATE
								.equals(callbackValueType[i])) {
							callBackInfoDto
							.setMemo(callBackInfoHiddenValues[j]);
						} else
						{
							if (WebUtil
									.StringHaveContent(callBackInfoHiddenValues[j]))
								callBackInfoDto.setInfoValueId(Integer
										.parseInt(callBackInfoHiddenValues[j]));
						}
						callBackInfoList.add(callBackInfoDto);
					}
				}
			}
		} catch (Exception e) {
			throw new WebActionException("DynamicServey.getCallBackInfo****"
					+ e.toString());
		}
		return callBackInfoList;
	}

	// 客户市场信息
	public static Collection getNewCustomerMarketInfo(
			HttpServletRequest request, String paramName)
			throws WebActionException {
		ArrayList newcustMarketInfoList = new ArrayList();
		String marketId = (request.getParameter(paramName) == null) ? ""
				: request.getParameter(paramName);
		String[] maketIds = marketId.split(";");
		String valueTypeName = paramName + "_ValueType";
		String newcustMarketValueTypeName = (request
				.getParameter(valueTypeName) == null) ? "" : request
				.getParameter(valueTypeName);
		String[] newcustMarketValueType = newcustMarketValueTypeName.split(";");

		try {
			for (int i = 0; i < maketIds.length; i++) {
				String marketInfoHiddenName = paramName + "_" + maketIds[i];
				String[] marketInfoHiddenValues = request
						.getParameterValues(marketInfoHiddenName);
				if (marketInfoHiddenValues != null) {
					for (int j = 0; j < marketInfoHiddenValues.length; j++) {
						com.dtv.oss.dto.NewCustomerMarketInfoDTO newcustMarketInfoDto = new com.dtv.oss.dto.NewCustomerMarketInfoDTO();
						newcustMarketInfoDto.setInfoSettingId(Integer
								.parseInt(maketIds[i]));
						if (Constant.SERVEY_TAGTYPE_TEXT
								.equals(newcustMarketValueType[i])|| Constant.SERVEY_TAGTYPE_DATE
								.equals(newcustMarketValueType[i])) {
							newcustMarketInfoDto
							.setMemo(marketInfoHiddenValues[j]);
						} else{
							if (WebUtil
									.StringHaveContent(marketInfoHiddenValues[j]))
								newcustMarketInfoDto
										.setInfoValueId(WebUtil
												.StringToInt(marketInfoHiddenValues[j]));
						}

						newcustMarketInfoList.add(newcustMarketInfoDto);
					}
				}
			}
		} catch (Exception e) {
			throw new WebActionException(
					"DynamicServey.getNewCustomerMarketInfo****" + e.toString());
		}

		return newcustMarketInfoList;
	}

	// 客户市场信息
	public static Collection getCustomermarketInfo(HttpServletRequest request,
			String paramName, int customerID) throws WebActionException {
		ArrayList custMarketInfoList = new ArrayList();
		String marketId = (request.getParameter(paramName) == null) ? ""
				: request.getParameter(paramName);
		String[] maketIds = marketId.split(";");
		String valueTypeName = paramName + "_ValueType";
		String custMarketValueTypeName = (request.getParameter(valueTypeName) == null) ? ""
				: request.getParameter(valueTypeName);
		String[] custMarketValueType = custMarketValueTypeName.split(";");

		try {
			for (int i = 0; i < maketIds.length; i++) {
				String marketInfoHiddenName = paramName + "_" + maketIds[i];
				String[] marketInfoHiddenValues = request
						.getParameterValues(marketInfoHiddenName);
				if (marketInfoHiddenValues != null) {
					for (int j = 0; j < marketInfoHiddenValues.length; j++) {
						com.dtv.oss.dto.CustMarketInfoDTO custMarketInfoDto = new com.dtv.oss.dto.CustMarketInfoDTO();
						custMarketInfoDto.setInfoSettingId(Integer
								.parseInt(maketIds[i]));
						if (Constant.SERVEY_TAGTYPE_TEXT
								.equals(custMarketValueType[i])
								|| Constant.SERVEY_TAGTYPE_DATE
										.equals(custMarketValueType[i])) {
							custMarketInfoDto
									.setMemo(marketInfoHiddenValues[j]);
						} else {
							if (WebUtil
									.StringHaveContent(marketInfoHiddenValues[j]))
								custMarketInfoDto
										.setInfoValueId(WebUtil
												.StringToInt(marketInfoHiddenValues[j]));
						}

						custMarketInfoDto.setCustomerId(customerID);
						custMarketInfoList.add(custMarketInfoDto);
					}
				}
			}
		} catch (Exception e) {
			throw new WebActionException(
					"DynamicServey.getCustomermarketInfo*****" + e.toString());
		}
		return custMarketInfoList;

	}

	// 诊断信息
	public static Collection getDiagnosisInfo(HttpServletRequest request,
			String paramName) throws WebActionException {
		Collection diaIdList = new ArrayList();
		String diaName = (request.getParameter(paramName) == null) ? ""
				: request.getParameter(paramName);
		String[] diaIds = diaName.split(";");
		String valueTypeName = paramName + "_ValueType";
		String diaValuetypeName = (request.getParameter(valueTypeName) == null) ? ""
				: request.getParameter(valueTypeName);
		String[] diaValuetype = diaValuetypeName.split(";");
		try {
			for (int i = 0; i < diaIds.length; i++) {
				String[] diaIdValues = request.getParameterValues(paramName
						+ "_" + diaIds[i]);
				if (diaIdValues != null) {
					for (int j = 0; j < diaIdValues.length; j++) {
						DiagnosisInfoDTO diaDto = new DiagnosisInfoDTO();
						diaDto.setInfoSettingId(WebUtil.StringToInt(diaIds[i]));
						if (Constant.SERVEY_TAGTYPE_TEXT
								.equals(diaValuetype[i])||
								Constant.SERVEY_TAGTYPE_DATE
								.equals(diaValuetype[i])
								) {
							diaDto.setMemo(diaIdValues[j]);
						} else{
							if (WebUtil.StringHaveContent(diaIdValues[j]))
								diaDto.setInfoValueId(WebUtil
										.StringToInt(diaIdValues[j]));
						}
						diaIdList.add(diaDto);
					}
				}
			}
		} catch (Exception e) {
			throw new WebActionException("DynamicServey.getDiagnosisInfo*****"
					+ e.toString());
		}
		return diaIdList;
	}

	// 物料使用情况
	public static Collection getMaterialUsage(HttpServletRequest request,
			String paramName, int jobCardID) throws WebActionException {
		Collection materialList = new ArrayList();
		String materialName = (request.getParameter(paramName) == null) ? ""
				: request.getParameter(paramName);
		String[] materialIds = materialName.split(";");
		String valueTypeName = paramName + "_ValueType";
		String materialValuetypeName = (request.getParameter(valueTypeName) == null) ? ""
				: request.getParameter(valueTypeName);
		String[] materialValuetype = materialValuetypeName.split(";");
		try {
			for (int i = 0; i < materialIds.length; i++) {
				String[] materialIdValues = request
						.getParameterValues(paramName + "_" + materialIds[i]);
				if (materialIdValues != null) {
					for (int j = 0; j < materialIdValues.length; j++) {
						MaterialUsageDTO materialUsageDto = new MaterialUsageDTO();
						materialUsageDto.setInfoSettingId(WebUtil
								.StringToInt(materialIds[i]));
						if (Constant.SERVEY_TAGTYPE_TEXT
								.equals(materialValuetype[i])||
								Constant.SERVEY_TAGTYPE_DATE
								.equals(materialValuetype[i])) {
							materialUsageDto.setMemo(materialIdValues[j]);
						} else{
							if (WebUtil.StringHaveContent(materialIdValues[j]))
								materialUsageDto.setInfoValueId(WebUtil
										.StringToInt(materialIdValues[j]));
						}

						materialUsageDto.setJobCardId(jobCardID);
						materialList.add(materialUsageDto);
					}
				}
			}
		} catch (Exception e) {
			throw new WebActionException("DynamicServey.getMeterialUsage*****"
					+ e.toString());
		}
		return materialList;
	}

}
