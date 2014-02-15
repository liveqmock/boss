package com.dtv.oss.service.ejbevent.logistics;

import java.util.ArrayList;
import java.util.List;

import com.dtv.oss.dto.FaPiaoDTO;
import com.dtv.oss.dto.FapiaoVolumnDTO;
import com.dtv.oss.dto.custom.CommonQueryConditionDTO;

/**
 * 发票模块用的EJBEvent author ： date : 2008-3-11 description:
 * 
 * @author
 * 
 */
public class FaPiaoEJBEvent extends LogisticsEJBEvent {

	// 通用的dto
	private CommonQueryConditionDTO commDTO;

	// 马上提交还是只是预检
	private boolean doPost;
	
	
	private FaPiaoDTO faPisoDto;
	
	private FapiaoVolumnDTO fapiaoVolumnDto;
	
	//是启起用发票册管理 "Y"--是
	private boolean isVolumnManage = false; 
	
	//数据生成方式 "1"--文件 ; "2"--手工
	private String makeStyle; 
	
	//运入后立即领用
	private boolean  isDirUse = false;
	
	//运入后立即领用,领用的组织机构ID
	private int outOrgID;
	
	//批量操作的发票序列号
	List serailNoList = new ArrayList();
	
	//文件处理时的文件名
	private String fileName;

	//报废方式 "1"--发票册报废; "2" -- 发票报废
	private String discardStyle;	
	
	private String dtLastmods;
	
	//批量操作的发票序列号
	private String serailnos;
	
	
	public String getSerailnos() {
		return serailnos;
	}

	public void setSerailnos(String serailnos) {
		this.serailnos = serailnos;
	}

	/**
	 * 空的构造函数
	 */
	public FaPiaoEJBEvent() {

	}

	public void setCommDTO(CommonQueryConditionDTO theDTO) {
		this.commDTO = theDTO;
	}

	public CommonQueryConditionDTO getCommDTO() {
		return commDTO;
	}

	public boolean isDoPost() {
		return doPost;
	}

	public void setDoPost(boolean doPost) {
		this.doPost = doPost;
	}
	public void setFaPiaoDTO(FaPiaoDTO faPisoDto) {
		this.faPisoDto = faPisoDto;
	}

	public FaPiaoDTO getFaPiaoDTO() {
		return faPisoDto;
	}
	
	public void setFapiaoVolumnDTO(FapiaoVolumnDTO fapiaoVolumnDto) {
		this.fapiaoVolumnDto = fapiaoVolumnDto;
	}

	public FapiaoVolumnDTO getFapiaoVolumnDTO() {
		return fapiaoVolumnDto;
	}
	
	public boolean isVolumnManage() {
		return isVolumnManage;
	}
	public void setIsVolumnManage(boolean isVolumnManage) {
		this.isVolumnManage = isVolumnManage;
	}
	
	public String getMakeStyle() {
		return makeStyle;
	}
	public void setMakeStyle(String makeStyle) {
		this.makeStyle = makeStyle;
	}
	
	
	public boolean isDirUse() {
		return isDirUse;
	}
	public void setIsDirUse(boolean isDirUse) {
		this.isDirUse = isDirUse;
	}
	
	public int getOutOrgID() {
		return outOrgID;
	}
	public void setOutOrgID(int outOrgID) {
		this.outOrgID = outOrgID;
	}
	
	public List getSerailNoList() {
		return serailNoList;
	}
	public void setSerailNoList(List serailNoList) {
		this.serailNoList = serailNoList;
	}
	
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getDiscardStyle() {
		return discardStyle;
	}

	public void setDiscardStyle(String discardStyle) {
		this.discardStyle = discardStyle;
	}

	public String getDtLastmods() {
		return dtLastmods;
	}

	public void setDtLastmods(String dtLastmods) {
		this.dtLastmods = dtLastmods;
	}
	

}