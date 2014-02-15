package com.dtv.oss.service.listhandler.config;

 
import com.dtv.oss.dto.ManualTransferSettingDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.dao.config.ManualTransferSettingDAO;
import com.dtv.oss.service.listhandler.ListHandlerException;
import com.dtv.oss.service.listhandler.ValueListHandler;

public class ManualTransferSettingListHandler extends ValueListHandler {
    private ManualTransferSettingDAO dao =null;
    private final String  tableName = "t_manualtransfersetting t"; 
	private ManualTransferSettingDTO dto =null;
	
	public ManualTransferSettingListHandler(){
		this.dao =new ManualTransferSettingDAO(); 
	}
	
	
	public void setCriteria(Object dto1)  throws ListHandlerException{
		LogUtility.log(RoleOrgListHandler.class,LogLevel.DEBUG,"in setCriteria begin setCriteria...");
	    if (dto1 instanceof ManualTransferSettingDTO) 
	       this.dto = (ManualTransferSettingDTO)dto1;
	    else {
	        LogUtility.log(RoleOrgListHandler.class,LogLevel.DEBUG,"in setCriteria method the dto type is not proper...");
			throw new ListHandlerException("the dto type is not proper...");
	    }

	    //�����ѯ�ַ���
	    constructSelectQueryString(dto);
	     //ִ�����ݲ�ѯ
	     executeSearch(dao,true,true);
	}
	
	 private void constructSelectQueryString(ManualTransferSettingDTO dto) {
	     StringBuffer begin = new StringBuffer();
	     begin.append("select * ");
	     begin.append(" from " + tableName);
	    	
	     StringBuffer selectStatement = new StringBuffer();
	     selectStatement.append(" where 1=1 ");
	     makeSQLByIntField("seqno", dto.getSeqNo(), selectStatement);
	     if(dto.getFromOrgId()!=0)
		    	appendStringWithOrgID("fromOrgId",dto.getFromOrgId(),selectStatement);
	     if(dto.getToOrgId()!=0)
		    	appendStringWithOrgID("ToOrgId",dto.getToOrgId(),selectStatement); 
	    
	     makeSQLByStringField("sheetType", dto.getSheetType(), selectStatement);
	     makeSQLByStringField("orgSubRole", dto.getOrgSubRole(), selectStatement);
	    
	  //   makeSQLByIntField(" SERVICEID ", roleOrgdto.getServiceId(), selectStatement);
	 
	     selectStatement.append(" order by seqno desc"); 
	    	 
	        
	       
		 //���ù���ȡ�õ�ǰ��ѯ�ܼ�¼����sql
		 setRecordCountQueryTable(tableName);
		 setRecordCountSuffixBuffer(selectStatement);
		 //���õ�ǰ���ݲ�ѯsql
		 setRecordDataQueryBuffer(begin.append(selectStatement));
    }

}
