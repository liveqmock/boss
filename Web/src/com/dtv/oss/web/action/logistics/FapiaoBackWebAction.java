package com.dtv.oss.web.action.logistics;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.dtv.oss.dto.FaPiaoDTO;
import com.dtv.oss.dto.FapiaoVolumnDTO;
import com.dtv.oss.dto.custom.CommonQueryConditionDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.logistics.FaPiaoEJBEvent;
import com.dtv.oss.service.ejbevent.logistics.LogisticsEJBEvent;
import com.dtv.oss.web.action.GeneralWebAction;
import com.dtv.oss.web.exception.WebActionException;
import com.dtv.oss.web.util.FileUpload;
import com.dtv.oss.web.util.FileUtility;
import com.dtv.oss.web.util.WebUtil;

/*
 * 发票回库
 */
public class FapiaoBackWebAction extends GeneralWebAction {
	boolean doPost = false;

	protected boolean needCheckToken() {
		return doPost;
	}

	public void doStart(HttpServletRequest request) {
		super.doStart(request);
		if (WebUtil.StringHaveContent(request.getParameter("confirm_post"))) {
			if (request.getParameter("confirm_post").equalsIgnoreCase("true")) {
				doPost = true;

			}
		}
	}

	protected EJBEvent encapsulateData(HttpServletRequest request)
			throws WebActionException {
		FaPiaoEJBEvent theEvent = new FaPiaoEJBEvent();
		FaPiaoDTO faPiaoDto = new FaPiaoDTO();
		FapiaoVolumnDTO fapiaoVolumnDto = new FapiaoVolumnDTO();
		if (!WebUtil.StringHaveContent(request.getParameter("txtIsVolumnManage")))
			throw new WebActionException("参数错误:是否启用发票册管理未知!");
		//启用发票册管理
		if("Y".equals(request.getParameter("txtIsVolumnManage")))
		{
			if (!WebUtil.StringHaveContent(request.getParameter("txtVolumnSN")))
				throw new WebActionException("参数错误:发票册序列号未知!");
		}
		if (!WebUtil.StringHaveContent(request.getParameter("txtType")))
			throw new WebActionException("参数错误:发票类型未知!");
		if (!WebUtil.StringHaveContent(request.getParameter("txtDepotID")))
			throw new WebActionException("参数错误:仓库未知!");

		//发票册序列号
		if(WebUtil.StringHaveContent(request.getParameter("txtVolumnSN")))
			fapiaoVolumnDto.setVolumnSn(request.getParameter("txtVolumnSN"));
		//发票类型
		if(WebUtil.StringHaveContent(request.getParameter("txtType")))
			faPiaoDto.setType(request.getParameter("txtType"));
		// 运入方式: "1"--文件运入 "2"--手工运入
		if(WebUtil.StringHaveContent(request.getParameter("txtMakeStyle")))
			theEvent.setMakeStyle(request.getParameter("txtMakeStyle"));
		// 目标地址ID :回到库存
		if(WebUtil.StringHaveContent(request.getParameter("txtDepotID")))
			faPiaoDto.setAddressID(WebUtil.StringToInt(request.getParameter("txtDepotID")));
		List serailNoList = new ArrayList();
		// 发票序列号
		if(WebUtil.StringHaveContent(request.getParameter("txtSerailNos")))
		{
			String[] serialsArray = request.getParameter("txtSerailNos").split("\r\n");
			
			for (int i = 0; i < serialsArray.length; i++) {
				if(serialsArray[i]!=null && !"".equals(serialsArray[i].trim()))
					serailNoList.add(serialsArray[i].trim());
			}
		}
		theEvent.setSerailNoList(serailNoList);

		//文件名
		if(WebUtil.StringHaveContent(request.getParameter("txtFilePath")))
			theEvent.setFileName(request.getParameter("txtFilePath"));
		//发票是否按册管理 "Y"--是
		if("Y".equals(request.getParameter("txtIsVolumnManage")))
			theEvent.setIsVolumnManage(true);
		//LogUtility.log(this.getClass(), LogLevel.DEBUG, "__1_txtSerailnos="+theDTO.getSpareStr7());
		try {
			// 不是提交,仅仅检查数据
			if (!doPost) {
				// 是文件运入,需要upload文件
				if ("1".equals(theEvent.getMakeStyle())) {
					// 最大可以上传的文件大小为 1024000 , 允许上传的文件格式为:csv 格式的
					FileUpload upload = new FileUpload(request, 1024000, "csv");
					String filePath = upload.saveFile();
					// 发票序列号
					theEvent.setSerailNoList(parseFile(filePath));
					// 返回服务器上的文件路径给确认页面使用
					 request.setAttribute("txtFilePath", filePath);
				}
			}
			else
			{
				List temList = new ArrayList();
				//发票序列号
				if(WebUtil.StringHaveContent(request.getParameter("txtNoCols")))
				{
					String[] serialsArray = request.getParameter("txtNoCols").split("\r\n");
					
					for (int i = 0; i < serialsArray.length; i++) {
						if(serialsArray[i]!=null && !"".equals(serialsArray[i].trim()))
							temList.add(serialsArray[i].trim());
					}
				}
				theEvent.setSerailNoList(temList);
			}
				
		} catch (Exception e) {
			e.printStackTrace();
			throw new WebActionException(e.getMessage());
		}
		theEvent.setActionType(LogisticsEJBEvent.FAPIAO_BACK);
		theEvent.setDoPost(doPost);
		theEvent.setFaPiaoDTO(faPiaoDto);
		theEvent.setFapiaoVolumnDTO(fapiaoVolumnDto);

		return theEvent;

	}

	private List parseFile(String filePath) throws WebActionException {
		return FileUtility.parseCSVFileWithFapiaoIn(filePath);
	}

}
