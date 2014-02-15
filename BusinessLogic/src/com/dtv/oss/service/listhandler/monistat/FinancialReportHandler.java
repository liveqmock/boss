package com.dtv.oss.service.listhandler.monistat;

import java.util.ArrayList;
import java.util.List;

import com.dtv.oss.dto.custom.CommonQueryConditionDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.dao.csr.GenericImpDAO;
import com.dtv.oss.service.listhandler.ListHandlerException;
import com.dtv.oss.service.listhandler.ValueListHandler;

public class FinancialReportHandler extends ValueListHandler{
	private CommonQueryConditionDTO dto = null;
	private GenericImpDAO dao = null;
	private static final Class clazz = FinancialReportHandler.class;
	private List rowCountList =new ArrayList();
	public FinancialReportHandler(){
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
		begin.append(" select sub_1.name,sum(t.bqsfje),sum(t.bqtfje),sum(t.bqssje),sum(t.bqyjsrje),sum(t.sqyzrbqsrje),sum(t.bqsrje),sum(t.sqycje),sum(t.bqycje) ");
		begin.append(" from RPT_AREA_FEEDETAIL t, ");
		begin.append(" (select dir.id from t_districtsetting dir connect by prior dir.id =dir.belongto start with dir.id ="+dto2.getSpareStr1() +") subview, ");
		begin.append(" (select dir.id,dir.name from t_districtsetting dir where dir.belongto ="+dto2.getSpareStr1()+" or (dir.id ="+dto2.getSpareStr1()+" and Not Exists(Select 1 From t_districtsetting ds Where ds.belongto =dir.id))) sub_1 ");
		begin.append(" where t.districtid =subview.id ");
		begin.append(" and subview.id in (select dir.id from t_districtsetting dir connect by prior dir.id =dir.belongto start with dir.id =sub_1.id) ");
		begin.append(" and t.flldate ='"+dto2.getSpareStr2()+"'");
		begin.append(" group by sub_1.name ");
		begin.append(" union all ");
		begin.append(" select '本月合计',nvl(sum(t.bqsfje),0),nvl(sum(t.bqtfje),0),nvl(sum(t.bqssje),0),nvl(sum(t.bqyjsrje),0),nvl(sum(t.sqyzrbqsrje),0),nvl(sum(t.bqsrje),0),nvl(sum(t.sqycje),0),nvl(sum(t.bqycje),0) ");
		begin.append(" from RPT_AREA_FEEDETAIL t, ");
		begin.append(" (select dir.id from t_districtsetting dir connect by prior dir.id =dir.belongto start with dir.id ="+dto2.getSpareStr1()+") subview ");
		begin.append(" where t.districtid =subview.id ");
		begin.append(" and t.flldate='"+dto2.getSpareStr2()+"' ");
		begin.append(" union all ");
	    begin.append(" select '本年合计',nvl(sum(t.bqsfje),0),nvl(sum(t.bqtfje),0),nvl(sum(t.bqssje),0),nvl(sum(t.bqyjsrje),0),nvl(sum(t.sqyzrbqsrje),0),nvl(sum(t.bqsrje),0),nvl(sum(t.sqycje),0),nvl(sum(t.bqycje),0) ");
		begin.append(" from RPT_AREA_FEEDETAIL t, ");
		begin.append(" (select dir.id from t_districtsetting dir connect by prior dir.id =dir.belongto start with dir.id ="+dto2.getSpareStr1()+") subview ");
		begin.append(" where t.districtid =subview.id ");
		String firstMonth =dto2.getSpareStr2().substring(0,4)+"01";
		begin.append(" and to_number(t.flldate) >=to_number('"+firstMonth+"') and to_number(t.flldate) <=to_number('"+dto2.getSpareStr2()+"')");

		// 设置当前数据查询sql
		setRecordDataQueryBuffer(begin);
	}
}
