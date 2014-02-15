/*
 * Created on 2006-3-24
 * 
 * To change the template for this generated file go to Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.dtv.oss.dto.wrap;

import java.io.Serializable;
import com.dtv.oss.dto.*;
import java.util.*;
/**
 * @author Chen jiang
 * 
 * To change the template for this generated type comment go to Window - Preferences - Java - Code Generation - Code and
 * Comments
 */
public class CreateGroupBargainResult implements Serializable {
	GroupBargainDTO groupBargain;
	Collection detailList;
	
	public CreateGroupBargainResult() {
	}
	
	public CreateGroupBargainResult(GroupBargainDTO groupBargain, Collection detailList) {
		super();
		this.groupBargain = groupBargain;
		this.detailList = detailList;
	}

	public Collection getDetailList() {
		return detailList;
	}

	public void setDetailList(Collection detailList) {
		this.detailList = detailList;
	}

	public GroupBargainDTO getGroupBargain() {
		return groupBargain;
	}

	public void setGroupBargain(GroupBargainDTO groupBargain) {
		this.groupBargain = groupBargain;
	}

}
