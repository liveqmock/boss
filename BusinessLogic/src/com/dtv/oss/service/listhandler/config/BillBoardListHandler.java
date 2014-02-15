package com.dtv.oss.service.listhandler.config;

import com.dtv.oss.dto.BillBoardDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.dao.config.BillBoardDAO;
import com.dtv.oss.service.listhandler.ListHandlerException;
import com.dtv.oss.service.listhandler.ValueListHandler;

/**
 * @author Chen jiang
 */

public class BillBoardListHandler extends ValueListHandler {
    private BillBoardDAO dao = null;
    final private String tableName = "t_billboard t";


	private BillBoardDTO dto = null;

	public BillBoardListHandler() {
	  	this.dao = new BillBoardDAO();
	}
	
	public void setCriteria(Object dto1)  throws ListHandlerException{
		  LogUtility.log(BillBoardListHandler.class,LogLevel.DEBUG,"in setCriteria begin setCriteria...");
       if (dto1 instanceof BillBoardDTO) 
       	 this.dto = (BillBoardDTO)dto1;
       else {
       	LogUtility.log(BillBoardListHandler.class,LogLevel.DEBUG,"in setCriteria method the dto type is not proper...");
			throw new ListHandlerException("the dto type is not proper...");
        }
       // added 
       //   if (fillDTOWithPrivilege(dto) == false) return;
       //�����ѯ�ַ���
       constructSelectQueryString(dto);
       //ִ�����ݲ�ѯ
       executeSearch(dao,true,true);
   }

    private void constructSelectQueryString(BillBoardDTO dto) {
    	StringBuffer begin = new StringBuffer();
    	begin.append("select t.* ");
    	begin.append(" from " + tableName);
    	
    	StringBuffer selectStatement = new StringBuffer();
    	selectStatement.append(" where 1=1 ");
    	//��������
    	makeSQLByStringField("t.name", dto.getName(), selectStatement,"like");
    	//������
    	makeSQLByStringField("t.PublishPerson", dto.getPublishPerson(), selectStatement);
    	//����ԭ��
    	makeSQLByStringField("t.PublishReason", dto.getPublishReason(), selectStatement);
    	//״̬
    	makeSQLByStringField("t.status", dto.getStatus(), selectStatement);
    	//���ȼ�
    	makeSQLByStringField("t.grade", dto.getGrade(), selectStatement);
    	//seqno
    	makeSQLByIntField("t.seqno", dto.getSeqNo(), selectStatement);
    	
    	//dateFrom����ǲ�ѯҳ���з������ڵĿ�ʼʱ��
		if (dto.getDateFrom()!=null) 
		        selectStatement.append("and t.publishDate>=to_timestamp('").append(dto.getDateFrom().toString()).append("', 'YYYY-MM-DD-HH24:MI:SSxff')"); 
//		dateFrom����ǲ�ѯҳ���з������ڵĽ���ʱ��
		if (dto.getDateTo()!=null) 
			   selectStatement.append("and t.publishDate<=to_timestamp('").append(dto.getDateTo().toString()).append("', 'YYYY-MM-DD-HH24:MI:SSxff')"); 
    	
		selectStatement.append(" order by t.seqno desc");     
       
		//���ù���ȡ�õ�ǰ��ѯ�ܼ�¼����sq
		setRecordCountQueryTable(tableName);
		setRecordCountSuffixBuffer(selectStatement);
		//���õ�ǰ���ݲ�ѯsql
		setRecordDataQueryBuffer(begin.append(selectStatement));
      }
    
}