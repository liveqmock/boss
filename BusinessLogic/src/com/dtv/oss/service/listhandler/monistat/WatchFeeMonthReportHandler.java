package com.dtv.oss.service.listhandler.monistat;

import java.util.ArrayList;
import java.util.List;

import com.dtv.oss.dto.custom.CommonQueryConditionDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.dao.csr.GenericImpDAO;
import com.dtv.oss.service.listhandler.ListHandlerException;
import com.dtv.oss.service.listhandler.ValueListHandler;

public class WatchFeeMonthReportHandler extends ValueListHandler{
	private CommonQueryConditionDTO dto = null;
	private GenericImpDAO dao = null;
	private static final Class clazz = WatchFeeMonthReportHandler.class;
	private List rowCountList =new ArrayList();
	public WatchFeeMonthReportHandler(){
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
		if (dto.getSpareStr1().equals("out")){
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
		String sql =" select v_dist.distname,sum(t11.用户数),t11.单位金额,sum(t11.合计金额) from v_watchfeemonthreport t11, "
                   +" (select t.id,t.name distName from t_districtsetting t where belongto ="+dto2.getSpareStr2() +" or id ="+dto2.getSpareStr2() +") v_dist "
                   +"  where v_dist.id =(select  t.id  from t_districtsetting t  "               
                   +"                    where (t.belongto ="+dto2.getSpareStr2()+") connect by prior belongto =id start with id =t11.districtid)"
                   +"  or (t11.districtid ="+dto2.getSpareStr2()+" and t11.DISTRICTID =v_dist.id) "
                   +"  group by v_dist.distname,t11.单位金额 ";
                  
		sql =sql +"  order by v_dist.distname,t11.单位金额 " ;
		// 设置当前数据查询sql
		setRecordDataQueryBuffer(begin.append(sql));		
	}

}
