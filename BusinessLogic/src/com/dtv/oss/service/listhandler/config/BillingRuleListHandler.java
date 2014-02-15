package com.dtv.oss.service.listhandler.config;

import com.dtv.oss.dto.BillingRuleDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.dao.config.BillingRuleDAO;
import com.dtv.oss.service.listhandler.ListHandlerException;
import com.dtv.oss.service.listhandler.ValueListHandler;

/**
 * @author Chen jiang
 */

public class BillingRuleListHandler extends ValueListHandler {
    private BillingRuleDAO dao = null;
    final private String tableName = "T_BILLINGRULE t";


	private BillingRuleDTO dto = null;

	public BillingRuleListHandler() {
	  	this.dao = new BillingRuleDAO();
	}
	
	public void setCriteria(Object dto1)  throws ListHandlerException{
		  LogUtility.log(BillingRuleListHandler.class,LogLevel.DEBUG,"in setCriteria begin setCriteria...");
       if (dto1 instanceof BillingRuleDTO) 
       	 this.dto = (BillingRuleDTO)dto1;
       else {
       	LogUtility.log(MarketSegmentListHandler.class,LogLevel.DEBUG,"in setCriteria method the dto type is not proper...");
			throw new ListHandlerException("the dto type is not proper...");
        }
       // added 
       //   if (fillDTOWithPrivilege(dto) == false) return;
       //构造查询字符串
       constructSelectQueryString(dto);
       //执行数据查询
       executeSearch(dao,true,true);
   }

    private void constructSelectQueryString(BillingRuleDTO dto) {
    	StringBuffer begin = new StringBuffer();
    	begin.append("select t.* ");
    	begin.append(" from " + tableName);
    	
    	StringBuffer selectStatement = new StringBuffer();
    	selectStatement.append(" where 1=1 ");
    	 
        makeSQLByIntField("t.id",dto.getId(),selectStatement);
        makeSQLByIntField("t.eventclass",dto.getEventClass(),selectStatement);
        makeSQLByIntField("t.productId",dto.getProductId(),selectStatement);
        makeSQLByStringField("t.eventreason",dto.getEventReason(),selectStatement);
        makeSQLByIntField("t.packageId",dto.getPackageId(),selectStatement);		
        makeSQLByIntField("t.destproductid",dto.getDestProductId(),selectStatement);	
        makeSQLByIntField("t.destpackageid",dto.getDestProductId(),selectStatement);	
        makeSQLByIntField("t.brcntid_accttype",dto.getBrcntIdAcctType(),selectStatement);	
        makeSQLByIntField("t.brcntid_campaign",dto.getBrcntIdCampaign(),selectStatement);
        makeSQLByIntField("t.brcntid_custtype",dto.getBrcntIdCustType(),selectStatement);
        
        makeSQLByIntField("t.BRCNTID_CATVTERMTYPE",dto.getBrCntIDCATVTermType(),selectStatement);	
        makeSQLByIntField("t.BRCNTID_CABLETYPE",dto.getBrCntIDCableType(),selectStatement);
        makeSQLByIntField("t.BRCNTID_BIDRECTIONFLAG",dto.getBrCntIDBiDrectionFlag(),selectStatement);
        makeSQLByStringField("t.acctitemtypeid",dto.getAcctItemTypeId(),selectStatement);
        makeSQLByStringField("t.status",dto.getStatus(),selectStatement);
        makeSQLByIntField("t.BrCntID_UserType",dto.getBrCntIDUserType(),selectStatement);
        makeSQLByIntField("t.BrCntID_MarketSegmentID",dto.getBrCntIDMarketSegmentID(),selectStatement);
		selectStatement.append(" order by t.id desc");
       
		//设置构造取得当前查询总纪录数的sq
		setRecordCountQueryTable(tableName);
		setRecordCountSuffixBuffer(selectStatement);
		//设置当前数据查询sql
		setRecordDataQueryBuffer(begin.append(selectStatement));
      }
    
}