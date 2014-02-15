package com.dtv.oss.service.ejbevent.logistics;

import java.util.ArrayList;
import java.util.List;

import com.dtv.oss.dto.FaPiaoDTO;
import com.dtv.oss.dto.FapiaoVolumnDTO;
import com.dtv.oss.dto.custom.CommonQueryConditionDTO;

/**
 * ��Ʊģ���õ�EJBEvent author �� date : 2008-3-11 description:
 * 
 * @author
 * 
 */
public class FaPiaoEJBEvent extends LogisticsEJBEvent {

	// ͨ�õ�dto
	private CommonQueryConditionDTO commDTO;

	// �����ύ����ֻ��Ԥ��
	private boolean doPost;
	
	
	private FaPiaoDTO faPisoDto;
	
	private FapiaoVolumnDTO fapiaoVolumnDto;
	
	//�������÷�Ʊ����� "Y"--��
	private boolean isVolumnManage = false; 
	
	//�������ɷ�ʽ "1"--�ļ� ; "2"--�ֹ�
	private String makeStyle; 
	
	//�������������
	private boolean  isDirUse = false;
	
	//�������������,���õ���֯����ID
	private int outOrgID;
	
	//���������ķ�Ʊ���к�
	List serailNoList = new ArrayList();
	
	//�ļ�����ʱ���ļ���
	private String fileName;

	//���Ϸ�ʽ "1"--��Ʊ�ᱨ��; "2" -- ��Ʊ����
	private String discardStyle;	
	
	private String dtLastmods;
	
	//���������ķ�Ʊ���к�
	private String serailnos;
	
	
	public String getSerailnos() {
		return serailnos;
	}

	public void setSerailnos(String serailnos) {
		this.serailnos = serailnos;
	}

	/**
	 * �յĹ��캯��
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