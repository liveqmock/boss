package com.dtv.oss.service.listhandler.monistat;

import java.util.ArrayList;
import java.util.List;

import com.dtv.oss.dto.custom.CommonQueryConditionDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.dao.csr.GenericImpDAO;
import com.dtv.oss.service.listhandler.ListHandlerException;
import com.dtv.oss.service.listhandler.ValueListHandler;

public class FinancialDetailReportHandler extends ValueListHandler{
	private CommonQueryConditionDTO dto = null;
	private GenericImpDAO dao = null;
	private static final Class clazz = FinancialDetailReportHandler.class;
	private List rowCountList =new ArrayList();
	public FinancialDetailReportHandler(){
		dao=new GenericImpDAO(rowCountList);
	}

	public void setCriteria(Object dto1) throws ListHandlerException {
		LogUtility.log(clazz, LogLevel.DEBUG,"in setCriteria begin setCriteria...");
		if (dto1 instanceof CommonQueryConditionDTO)
			this.dto = (CommonQueryConditionDTO)dto1;
		else {
			LogUtility.log(clazz, LogLevel.DEBUG,"in setCriteria method the dto type is not proper...");
			throw new ListHandlerException("the dto type is not proper...");
		}
		//构造查询字符串
		constructSelectQueryString(dto);
		
		if (dto.getSpareStr3().equals("out")){
			dao.setSpeedFlag(false);
		}else{
			dao.setFrom(getFrom());
		    dao.setTo(getTo());
			dao.setSpeedFlag(true);
			System.out.println("getFrom()----------------->"+getFrom());
			System.out.println("getTo()---------------->"+getTo());
		}
		//执行数据查询
		executeSearch(dao);

		setTotalRecordSize(((Integer)(rowCountList.get(0))).intValue());
	}
	
	private void constructSelectQueryString(CommonQueryConditionDTO dto2) throws ListHandlerException {
		StringBuffer begin = new StringBuffer();
		begin.append(" select t.projectname,sum(t.ysfcount),sum(t.qfcount),sum(t.yscount),sum(t.rgjfje),sum(t.yhhkje),sum(t.hjje) ");
        begin.append(" from RPT_PRODUCT_TOTAL t,");
        begin.append(" (select dir.id from t_districtsetting dir connect by prior dir.id =dir.belongto start with dir.id ="+dto2.getSpareStr1()+") subview ");
        begin.append(" where t.districtid =subview.id ");
        begin.append(" and t.flldate ='"+dto2.getSpareStr2()+"'"); 
        begin.append(" group by t.projectname ");
        begin.append(" union all ");
        begin.append(" select '合计:',nvl(sum(t.ysfcount),0),nvl(sum(t.qfcount),0),nvl(sum(t.yscount),0),nvl(sum(t.rgjfje),0),nvl(sum(t.yhhkje),0),nvl(sum(t.hjje),0) ");
        begin.append(" from RPT_PRODUCT_TOTAL t, ");
        begin.append(" (select dir.id from t_districtsetting dir connect by prior dir.id =dir.belongto start with dir.id ="+dto2.getSpareStr1()+") subview ");
        begin.append(" where t.districtid =subview.id ");
        begin.append(" and t.flldate ='"+dto2.getSpareStr2()+"'");
        
        // 设置当前数据查询sql
		setRecordDataQueryBuffer(begin);
	}
}

