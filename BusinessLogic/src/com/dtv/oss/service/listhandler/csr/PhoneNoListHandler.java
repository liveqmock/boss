package com.dtv.oss.service.listhandler.csr;

import com.dtv.oss.dto.ResourcePhoneNoDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.dao.csr.PhoneNoDAO;
import com.dtv.oss.service.listhandler.ListHandlerException;
import com.dtv.oss.service.listhandler.ValueListHandler;
import com.dtv.oss.service.util.CommonConstDefinition;
import com.dtv.oss.service.util.HelperCommonUtil;

/**
 * @author Stone Liang
 * Created on 2006-5-26
 */
public class PhoneNoListHandler extends ValueListHandler {
    private PhoneNoDAO dao = null;
    private String selectCriteria = "";
    private ResourcePhoneNoDTO dto = null;
    final private String tableName = "t_resource_phoneno a";

    public PhoneNoListHandler() {
	  	this.dao = new PhoneNoDAO();
	}
    
    public void setCriteria(Object dto)  throws ListHandlerException{
    	LogUtility.log(PhoneNoListHandler.class,LogLevel.DEBUG,"�绰�����ѯ...");  
        if (dto instanceof ResourcePhoneNoDTO) 
        	this.dto = (ResourcePhoneNoDTO)dto;
        else {
        	LogUtility.log(PhoneNoListHandler.class,LogLevel.DEBUG,"����Ĳ��Ҳ�������...");
			throw new ListHandlerException("the dto type is not proper...");	
        }
               
        
        //�����ѯ�ַ���
        constructSelectQueryString(this.dto);   
        //ִ�����ݲ�ѯ
        executeSearch(dao,true,true);
    }

    private void constructSelectQueryString(ResourcePhoneNoDTO dto) {
        StringBuffer begin = new StringBuffer();
       	begin.append("select a.* "  + " from " + tableName);
   
		StringBuffer selectStatement = new StringBuffer();
		selectStatement.append(" where 1=1 ");
		
		makeSQLByStringField("grade",dto.getGrade(),selectStatement);
		//���ӵ���ѡ��
//		makeSQLByIntField("DISTRICTID",dto.getDistrictId(),selectStatement);
		if (dto.getDistrictId() != 0) {
			selectStatement.append(" and DISTRICTID in ");
			selectStatement
					.append("(Select id  From T_DISTRICTSETTING bbb Where ((Select Count(*) from t_resource_phoneno aa Where aa.districtid=bbb.Id)>0) connect by prior belongto=Id start with ID=");
			selectStatement.append(dto.getDistrictId());
			selectStatement.append(")");
		}
		if (HelperCommonUtil.StringHaveContent(dto.getPhoneNo()))
				makeSQLByStringFieldSuffix("a.phoneno", dto.getPhoneNo(), selectStatement, "like");
		appendString("a.status = '" + CommonConstDefinition.PHONENO_STATUS_AVAILABLE + "'", selectStatement);
		
		appendOrderByString(selectStatement);
        // ���ù���ȡ�õ�ǰ��ѯ�ܼ�¼����sql
		setRecordCountQueryTable(tableName);
		setRecordCountSuffixBuffer(selectStatement);
		//���õ�ǰ���ݲ�ѯsql
		setRecordDataQueryBuffer(begin.append(selectStatement.toString()));
    	LogUtility.log(PhoneNoListHandler.class,LogLevel.DEBUG,"��ѯ�绰��sql"+begin.toString());
    }

	private void appendOrderByString(StringBuffer selectStatement) {
			selectStatement.append(" order by a.itemid desc");
	}
    
    
}
