/*
 * Created on 2004-8-9
 *  
 */
package com.dtv.oss.service.listhandler.system;

import com.dtv.oss.dto.CPCampaignMapDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.dao.system.CustCampaignDAO;
import com.dtv.oss.service.listhandler.ListHandlerException;
import com.dtv.oss.service.listhandler.ValueListHandler;

public class CustCampaignAllListHandler extends ValueListHandler {
	private CustCampaignDAO dao = null;
	final private String tableName = " T_CustomerCampaign ";

	/**
	 * use DTO as a template to determine search criteria
	 */
	private CPCampaignMapDTO dto = null;

	public CustCampaignAllListHandler() {
		this.dao = new CustCampaignDAO();
	}
	  
	public void setCriteria(Object dto1)  throws ListHandlerException{
		LogUtility.log(CustCampaignListHandler.class,LogLevel.DEBUG,"in setCriteria begin setCriteria...");
	    if (dto1 instanceof CPCampaignMapDTO) 
	 	     this.dto = (CPCampaignMapDTO)dto1;
	    else {
	 	  LogUtility.log(CustCampaignListHandler.class,LogLevel.DEBUG,"in setCriteria method the dto type is not proper...");
		  throw new ListHandlerException("the dto type is not proper...");
	    }
	  
	    //�����ѯ�ַ���
	    constructSelectQueryString(dto);
	    //ִ�����ݲ�ѯ
	    executeSearch(dao,true,true);
	}
	
	private void constructSelectQueryString(CPCampaignMapDTO dto) {
		//StringBuffer begin = new StringBuffer();
		//begin.append("select * ");
		//begin.append(" from " + tableName);
			
		StringBuffer selectStatement = new StringBuffer();

		
		//���ݿͻ���ƷID�õ����пͻ��Ż��б�
		if (dto.getCustProductID()!=0)
		{
			selectStatement.append("select * from T_CustomerCampaign " +
					"where CCID in(select CCID from T_CPCampaignMap where CustProductID="+
					dto.getCustProductID()+")");
		}
			
		selectStatement.append(" order by CCID desc ");
		
		//���ù���ȡ�õ�ǰ��ѯ�ܼ�¼����sql
		setRecordCountQueryTable(tableName);
		//���õ�ǰ���ݲ�ѯsql
		setRecordDataQueryBuffer(selectStatement);
	}
}
