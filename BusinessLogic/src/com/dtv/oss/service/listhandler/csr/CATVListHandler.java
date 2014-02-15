package com.dtv.oss.service.listhandler.csr;

import com.dtv.oss.dto.CatvTerminalDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.dao.csr.CATVDAO;
import com.dtv.oss.service.listhandler.ListHandlerException;
import com.dtv.oss.service.listhandler.ValueListHandler;
import com.dtv.oss.service.util.CommonConstDefinition;
import com.dtv.oss.service.util.HelperCommonUtil;

/**
 * @author david.Yang
 * Created on 2005-10-11
 */
public class CATVListHandler extends ValueListHandler {
    private CATVDAO dao = null;
    private String selectCriteria = "";
    private CatvTerminalDTO dto = null;
    final private String tableName = "t_catvterminal a";

    public CATVListHandler() {
	  	this.dao = new CATVDAO();
	}
    
    public void setCriteria(Object dto)  throws ListHandlerException{
    	LogUtility.log(CATVListHandler.class,LogLevel.DEBUG,"�ն˲�ѯ...");  
        if (dto instanceof CatvTerminalDTO) 
        	this.dto = (CatvTerminalDTO)dto;
        else {
        	LogUtility.log(CATVListHandler.class,LogLevel.DEBUG,"����Ĳ��Ҳ�������...");
			throw new ListHandlerException("the dto type is not proper...");	
        }
               
        
        //�����ѯ�ַ���
        constructSelectQueryString(this.dto);   
        //ִ�����ݲ�ѯ
        executeSearch(dao,true,true);
    }

    private void constructSelectQueryString(CatvTerminalDTO dto) {
        StringBuffer begin = new StringBuffer();
       	begin.append("select a.* "  + " from " + tableName);
   
		StringBuffer selectStatement = new StringBuffer();
		selectStatement.append(" where 1=1 ");
		
		 /*
		 * Search Condition:
		 * ID,CatvID,County
		 * DetailedAddress, status, statusreason
		 */
		makeSQLByStringField("a.id", dto.getId(), selectStatement);
		/**
		if (HelperCommonUtil.StringHaveContent(dto.getCatvCode())){
			String[] arrIDs = dto.getCatvCode().split(";");
			if (arrIDs.length > 1) {
				appendString("a.Catvid >= '" + arrIDs[0] + "'", selectStatement);
				appendString("a.Catvid <= '" + arrIDs[1] + "'", selectStatement);
			} else
				makeSQLByStringFieldSuffix("a.Catvid", dto.getCatvCode(), selectStatement, "like");
		}
		**/
		//makeSQLByIntField("a.County", dto.getCounty(), selectStatement);
		makeSQLByStringField("a.DetailedAddress", dto.getDetailedAddress(), selectStatement, "like");
		makeSQLByStringFieldIn("a.status", dto.getStatus(), selectStatement);
		makeSQLByStringFieldIn("a.statusreason", dto.getStatusReason(), selectStatement);				
		appendString("a.status <> '" + CommonConstDefinition.CATVTERMINAL_STATUS_DESTROY + "'", selectStatement);
		
		appendOrderByString(selectStatement);
        // ���ù���ȡ�õ�ǰ��ѯ�ܼ�¼����sql
		setRecordCountQueryTable(tableName);
		setRecordCountSuffixBuffer(selectStatement);
		//���õ�ǰ���ݲ�ѯsql
		setRecordDataQueryBuffer(begin.append(selectStatement.toString()));
    }

	private void appendOrderByString(StringBuffer selectStatement) {
			selectStatement.append(" order by a.id desc");
	}
    
    
}
