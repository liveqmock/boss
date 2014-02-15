package com.dtv.oss.service.command.voice;

import com.dtv.oss.service.command.QueryCommand;
import com.dtv.oss.service.ejbevent.QueryEJBEvent;
import com.dtv.oss.service.ejbevent.voice.VoiceQueryEJBEvent;
import com.dtv.oss.service.listhandler.ValueListHandler;
import com.dtv.oss.service.listhandler.voice.VoiceListHandler;

public class VoiceQueryCommand extends QueryCommand {

	public ValueListHandler getActualListHandler(QueryEJBEvent event) {
		ValueListHandler handler = null;
		switch (event.getType()) {
		    case VoiceQueryEJBEvent.QUERY_FRIEND_PHONENO:
         	    handler=new VoiceListHandler();
         	    break;
		    default :
				break;
		}

		return handler;
	}
}
