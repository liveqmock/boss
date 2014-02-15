/*
 * Created on 2006-10-16
 * @author fiona
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.dtv.oss.service.listhandler.voice;

 
import com.dtv.oss.dto.VoiceFriendPhoneNoDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.dao.voice.VoiceFriendPhoneNoDAO;
import com.dtv.oss.service.listhandler.ListHandlerException;
import com.dtv.oss.service.listhandler.ValueListHandler;

public class VoiceListHandler extends ValueListHandler {

	 private VoiceFriendPhoneNoDAO dao = null;
	 final private String tableName = "t_voice_friend_phoneno t";
	 private VoiceFriendPhoneNoDTO dto = null;
  /**
   * ���캯�����趨DAO����ΪDeviceDAO
   */
  public VoiceListHandler() {
  	   this.dao=new VoiceFriendPhoneNoDAO();
  }
  
  public void setCriteria(Object dto1)  throws ListHandlerException{
	  LogUtility.log(VoiceListHandler.class,LogLevel.DEBUG,"in setCriteria begin setCriteria...");
      if (dto1 instanceof VoiceFriendPhoneNoDTO) 
 	     this.dto = (VoiceFriendPhoneNoDTO)dto1;
      else {
 	  LogUtility.log(VoiceListHandler.class,LogLevel.DEBUG,"in setCriteria method the dto type is not proper...");
		throw new ListHandlerException("the dto type is not proper...");
  }
  
 //�����ѯ�ַ���
  constructSelectQueryString(dto);
 //ִ�����ݲ�ѯ
 executeSearch(dao,true,true);
}
  private void constructSelectQueryString(VoiceFriendPhoneNoDTO dto) {
	StringBuffer begin = new StringBuffer();
	begin.append("select t.* ");
	begin.append(" from " + tableName);
	
	StringBuffer selectStatement = new StringBuffer();
	selectStatement.append(" where 1=1 ");
    makeSQLByIntField("t.SERVICEACCOUNTID", dto.getServiceAccountID(), selectStatement);	
	selectStatement.append(" order by t.SEQNO desc");     
   
	//���ù���ȡ�õ�ǰ��ѯ�ܼ�¼����sql
	setRecordCountQueryTable(tableName);
	setRecordCountSuffixBuffer(selectStatement);
	//���õ�ǰ���ݲ�ѯsql
	setRecordDataQueryBuffer(begin.append(selectStatement));
  }

}
  