package com.dtv.oss.web.action.catv;

import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.ConstructionBatchDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.commandresponse.CommandResponse;
import com.dtv.oss.service.commandresponse.QueryCommandResponseImpl;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.catv.CatvEJBEvent;
import com.dtv.oss.web.action.GeneralWebAction;
import com.dtv.oss.web.action.QueryWebAction;
import com.dtv.oss.web.exception.WebActionException;
import com.dtv.oss.web.util.FileUpload;
import com.dtv.oss.web.util.FileUtility;
import com.dtv.oss.web.util.WebUtil;
import com.dtv.oss.web.util.smartUpload.Request;

public class ConstructionBatchImportWebAction extends QueryWebAction {
	String actionType = "";

	boolean confirmPost = false;

	private ArrayList constructionBatchItems = null;

	protected boolean needCheckToken() {
		return confirmPost;
	}

	public void doStart(HttpServletRequest request) {
		super.doStart(request);
		actionType = request.getParameter("txtActionType");
		if (WebUtil.StringHaveContent(actionType)) {
			if (("confirm".equalsIgnoreCase(actionType))) {
				confirmPost = true;
			}
		}
	}

	protected EJBEvent encapsulateData(HttpServletRequest request)
			throws Exception {
		CatvEJBEvent event = new CatvEJBEvent(
				CatvEJBEvent.CONSTRUCTIONBATCHIMPORT);
		event.setConfirm(confirmPost);
		ConstructionBatchDTO dto = new ConstructionBatchDTO();
		if (!WebUtil.StringHaveContent(actionType)) {
			throw new WebActionException("没有有效的操作.");
		}
		
		String sheetNo = request.getParameter("txtSheetNo");
		dto.setReferSheetNo(sheetNo);
		String constructionName = request.getParameter("txtConstructionName");
		if (WebUtil.StringHaveContent(constructionName)) {
			dto.setConstructionName(constructionName);
		} else {
			throw new WebActionException("没有有效的小区名称.");
		}
		String district = request.getParameter("txtDistrict");
		if (WebUtil.StringHaveContent(district)) {
			dto.setDistrictId(WebUtil.StringToInt(district));
		} else {
			throw new WebActionException("没有有效的所在区域.");
		}
		String builderName = request.getParameter("txtBuilderName");
		dto.setBuilderName(builderName);
		String terminalType=request.getParameter("txtCatvTerminalType");
		if (WebUtil.StringHaveContent(terminalType)) {
			dto.setCatvTermType(terminalType);
		} else {
			throw new WebActionException("没有有效的终端类型.");
		}
		
		String terStatus=request.getParameter("txtCatvTerminalStatus");
		if (WebUtil.StringHaveContent(terStatus)) {
			dto.setCatvTermStatus(terStatus);
		} else {
			throw new WebActionException("没有有效的终端状态.");
		}
		
		String cableType=request.getParameter("txtCableType");
		if (WebUtil.StringHaveContent(cableType)) {
			dto.setCableType(cableType);
		} else {
			throw new WebActionException("没有有效的电缆类型.");
		}
		String biDirection=request.getParameter("txtBiDirectionFlag");
		if (WebUtil.StringHaveContent(biDirection)) {
			dto.setBiDirectionFlag(biDirection);
		} else {
			throw new WebActionException("没有有效的是否双向网信息.");
		}
		
		String postCode = request.getParameter("txtPostCode");
		if (WebUtil.StringHaveContent(postCode)) {
			dto.setPostCode(postCode);
		} else {
			throw new WebActionException("没有有效的邮编.");
		}
		String tatolHouseNumber = request.getParameter("txtTatolHouseNumber");
		dto.setTotalHouseNumber(WebUtil.StringToInt(tatolHouseNumber));

		String addressPrefix = request.getParameter("txtAddressPrefix");
		dto.setAddressPrefix(addressPrefix);
		String fiberNode = request.getParameter("txtFiberNodeID");
		dto.setFiberNodeId(WebUtil.StringToInt(fiberNode));

		String desc = request.getParameter("txtDesc");
		dto.setDescription(desc);
		event.setConstructionBatchDTO(dto);
		
		if ("fileUpload".equalsIgnoreCase(actionType)) {
			FileUpload upload = new FileUpload(request, 1024000, "csv");
			String fileName = upload.saveFile();
			LogUtility.log(this.getClass(), LogLevel.DEBUG, "文件保存路径:"
					+ fileName);
			dto.setInputFileName(fileName);

			constructionBatchItems = FileUtility
					.parseCSVFileWithConstruction(fileName);

			event.setConstructionBatchItems(constructionBatchItems);
			request.setAttribute("constructionBatchFile", fileName);
		}else if("query".equalsIgnoreCase(actionType)){
			String fileName=request.getParameter("txtFileName");
			dto.setInputFileName(fileName);
			constructionBatchItems = FileUtility
			.parseCSVFileWithConstruction(dto.getInputFileName());
			request.setAttribute("constructionBatchFile", fileName);
			return null;
		}else if(confirmPost){
			String fileName=request.getParameter("txtFileName");
			dto.setInputFileName(fileName);
			constructionBatchItems = FileUtility
			.parseCSVFileWithConstruction(dto.getInputFileName());
			event.setConstructionBatchItems(constructionBatchItems);
		}else{
			throw new WebActionException("没有有效的操作.");
		}

		return event;
	}

	/**
	 * 设置页面上分页数据和结果集.
	 */
  public void doEnd(HttpServletRequest request, CommandResponse cmdResponse) {
		try {
			LogUtility.log(this.getClass(), LogLevel.DEBUG,
					"afterSuccessfulResponse.actionType:", actionType);
			if ("fileUpload".equalsIgnoreCase(actionType)
					|| "query".equalsIgnoreCase(actionType)) {
				int fromIndex = pageFrom-1;
				int toIndex = pageTo;
				if (toIndex > constructionBatchItems.size()) {
					toIndex = constructionBatchItems.size();
				}
				if (fromIndex > toIndex) {
					fromIndex = toIndex;
				}
				cmdResponse = new QueryCommandResponseImpl(
						constructionBatchItems.size(), constructionBatchItems
								.subList(fromIndex, toIndex), pageFrom, pageTo);
				LogUtility.log(this.getClass(), LogLevel.DEBUG,
						"afterSuccessfulResponse:", cmdResponse);
				LogUtility.log(this.getClass(), LogLevel.DEBUG,
						"afterSuccessfulResponse:", cmdResponse);
				request.setAttribute(getResponseAttributeName(), cmdResponse);
			}
		} catch (Exception e) {
			e.printStackTrace();
			LogUtility.log(this.getClass(), LogLevel.DEBUG,
					"afterSuccessfulResponse:", e.getMessage());
		}
		super.doEnd(request, cmdResponse);
	}
}
