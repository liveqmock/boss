/*
 * Created on 2005-11-1
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.dtv.oss.service.ejbevent.catv;

import java.util.Collection;

import com.dtv.oss.dto.*;
import com.dtv.oss.service.ejbevent.AbstractEJBEvent;


public class CatvEJBEvent extends AbstractEJBEvent {
	public final static int CONSTRUCTIONBATCHIMPORT=1001;
	
	public CatvEJBEvent(int actionType) {
		this.actionType = actionType;
	}
	private boolean isConfirm;
	private ConstructionBatchDTO constructionBatchDTO;
	private Collection constructionBatchItems;
	
	public ConstructionBatchDTO getConstructionBatchDTO() {
		return constructionBatchDTO;
	}
	public void setConstructionBatchDTO(ConstructionBatchDTO constructionBatchDTO) {
		this.constructionBatchDTO = constructionBatchDTO;
	}
	public Collection getConstructionBatchItems() {
		return constructionBatchItems;
	}
	public void setConstructionBatchItems(Collection constructionBatchItems) {
		this.constructionBatchItems = constructionBatchItems;
	}
	public boolean isConfirm() {
		return isConfirm;
	}
	public void setConfirm(boolean isConfirm) {
		this.isConfirm = isConfirm;
	}
}
