/**
 * Created on 2006-10-16
 * @author fiona
 */
package com.dtv.oss.service.ejbevent.voice;
import com.dtv.oss.dto.VoiceFriendPhoneNoDTO;
import com.dtv.oss.service.ejbevent.AbstractEJBEvent;

public class VoiceEJBEvent extends AbstractEJBEvent {
	
	private boolean doPost;
	public static final int FRIEND_PHONENO_CREATE	= 201; //亲情号码新增 	 
	public static final int FRIEND_PHONENO_DELETE	= 202; //亲情号码删除
	public static final int FRIEND_PHONENO_UPDATE	= 203; //亲情号码更新
	private VoiceFriendPhoneNoDTO voiceFriendPhoneNoDTO;
	private String seqNo;
	public VoiceFriendPhoneNoDTO getVoiceFriendPhoneNoDTO() {
		return voiceFriendPhoneNoDTO;
	}
	public void setVoiceFriendPhoneNoDTO(VoiceFriendPhoneNoDTO voiceFriendPhoneNoDTO) {
		this.voiceFriendPhoneNoDTO = voiceFriendPhoneNoDTO;
	}
	public boolean isDoPost() {
		return doPost;
	}
	public void setDoPost(boolean doPost) {
		this.doPost = doPost;
	}
	public String getSeqNo() {
		return seqNo;
	}
	public void setSeqNo(String seqNo) {
		this.seqNo = seqNo;
	}


	
}
