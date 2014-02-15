/**
 * Created on 2006-10-16
 * @author fiona
 */
package com.dtv.oss.service.ejbevent.voice;

import com.dtv.oss.dto.NewCustomerInfoDTO;
import com.dtv.oss.service.ejbevent.QueryEJBEvent;

public class VoiceQueryEJBEvent extends QueryEJBEvent {
	
	public static final int QUERY_FRIEND_PHONENO	= 101; //Ç×ÇéºÅÂë²éÑ¯
	
	private NewCustomerInfoDTO newCustInfo;
	//definition for stat query

	public VoiceQueryEJBEvent() {
		super();
	}
	public VoiceQueryEJBEvent(Object dto, int from, int to, int actionType) {
		super(dto, from, to, actionType);
	}
}
