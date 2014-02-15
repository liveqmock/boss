package com.dtv.oss.service.listhandler.csr;

import com.dtv.oss.dto.custom.CommonQueryConditionDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.dao.csr.PointsRecordDAO;
import com.dtv.oss.service.listhandler.ListHandlerException;
import com.dtv.oss.service.listhandler.ValueListHandler;
import com.dtv.oss.service.util.HelperCommonUtil;

/**
 * @author Chen jiang
 */

public class PointsRecordListHandler extends ValueListHandler {
    private PointsRecordDAO dao = null;
    final private String tableName = "T_USERPOINTSEXCHANGERCD t";


	private CommonQueryConditionDTO dto = null;

	public PointsRecordListHandler() {
	  	this.dao = new PointsRecordDAO();
	}
	
	public void setCriteria(Object dto1)  throws ListHandlerException{
		  LogUtility.log(PointsRecordListHandler.class,LogLevel.DEBUG,"in setCriteria begin setCriteria...");
       if (dto1 instanceof CommonQueryConditionDTO) 
       	 this.dto = (CommonQueryConditionDTO)dto1;
       else {
       	LogUtility.log(PointsRecordListHandler.class,LogLevel.DEBUG,"in setCriteria method the dto type is not proper...");
			throw new ListHandlerException("the dto type is not proper...");
        }
       // added 
       //   if (fillDTOWithPrivilege(dto) == false) return;
       //�����ѯ�ַ���
       constructSelectQueryString(dto);
       //ִ�����ݲ�ѯ
       executeSearch(dao,true,true);
   }

    private void constructSelectQueryString(CommonQueryConditionDTO dto) {
    	StringBuffer begin = new StringBuffer();
    	begin.append("select t.* ");
    	begin.append(" from " + tableName);
    	
    	StringBuffer selectStatement = new StringBuffer();
    	selectStatement.append(" where 1=1 ");
    	//�ID
    	makeSQLByStringField("t.ACTIVITYID",dto.getSpareStr1(),selectStatement);
    	// ����id
    	makeSQLByStringField("t.EXCHANGEGOODSTYPEID",dto.getSpareStr2(),selectStatement);
   	   //�û�֤��id
    	makeSQLByStringField("t.USERID",dto.getSpareStr3(),selectStatement);
    	if (HelperCommonUtil.StringHaveContent(dto.getSpareStr4())) {
    		selectStatement.append(" and t.userid in (select a.customerid from t_customer a where a.name like '%"+dto.getSpareStr4()+"%')");	
    	}
    	//��ϸ��ַ
    	if (HelperCommonUtil.StringHaveContent(dto.getSpareStr5())) {
    		selectStatement.append(" and t.userid in (select a.customerid from t_customer a where a.addressid in (select b.addressid from t_address b where b.detailaddress like '%"+dto.getSpareStr5()+"%'))");	
    	}
//		 ��������
		if (dto.getDistrict() != 0) {
		    selectStatement.append(" and t.userid in (select a.customerid from t_customer a where a.addressid in (select addressid from t_address where districtId in " +
		    		"( select ds.id from t_districtsetting ds connect by prior ds.id=belongto start with id =").append(dto.getDistrict()).append(")))");
		}
        
            //����ʱ��
    		if (dto.getBeginTime() != null)
    			selectStatement.append(" and t.createtime>=to_timestamp('").append(dto.getBeginTime().toString()).append("', 'YYYY-MM-DD-HH24:MI:SSxff')");

    		if (dto.getEndTime() != null)
    			selectStatement.append(" and t.createtime<=to_timestamp('").append(dto.getEndTime().toString()).append("', 'YYYY-MM-DD-HH24:MI:SSxff')");
		selectStatement.append(" order by t.id desc");     
       
		//���ù���ȡ�õ�ǰ��ѯ�ܼ�¼����sq
		setRecordCountQueryTable(tableName);
		setRecordCountSuffixBuffer(selectStatement);
		//���õ�ǰ���ݲ�ѯsql
		setRecordDataQueryBuffer(begin.append(selectStatement));
      }
    
}