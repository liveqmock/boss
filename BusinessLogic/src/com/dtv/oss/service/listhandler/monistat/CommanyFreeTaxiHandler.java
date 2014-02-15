package com.dtv.oss.service.listhandler.monistat;

import java.util.ArrayList;
import java.util.List;

import com.dtv.oss.dto.custom.CommonQueryConditionDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.dao.csr.GenericImpDAO;
import com.dtv.oss.service.listhandler.ListHandlerException;
import com.dtv.oss.service.listhandler.ValueListHandler;

public class CommanyFreeTaxiHandler extends ValueListHandler{
	private CommonQueryConditionDTO dto = null;
	private GenericImpDAO dao = null;
	private static final Class clazz = CommanyFreeTaxiHandler.class;
	private List rowCountList =new ArrayList();
	public CommanyFreeTaxiHandler(){
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
		String sql ="";
        if ("A".equals(dto.getSpareStr3())){
        	sql = "select " 
        	    +" cu.customerid,cu.name, to_char(pd.createtime,'yyyy-mm-dd') createtime,pd.amount,mp.name mop_name,op.operatorname,pd.ticketno "
                +" From t_customer  cu,t_custmarketinfo  cmk, t_address ad,t_paymentrecord pd,t_operator op,t_methodofpayment mp "
                +" where cmk.infosettingid = 105 "
                +" and cmk.infovalueid = 603 "
                +" and cu.status <> 'C' "
                +" and cu.customerid = cmk.customerid "
                +" and cu.customerid = pd.custid "
                +" and pd.custid = cu.customerid "
                +" and mp.mopid = pd.mopid "
                +" and pd.sourcetype in  ('M','T') "
                +" and pd.paytype in ('P','N') "
                +" and pd.opid = op.operatorid "
                +" and cu.addressid=ad.addressid";
        	 if (dto.getSpareStr4() !=null){
        		 sql =sql + " and pd.createtime >= to_date('"+dto.getSpareStr4()+"', 'yyyy-mm-dd') ";
        	 }
        	 if (dto.getSpareStr5() !=null){
        		 sql =sql + " and to_date(to_char(pd.createtime,'yyyy-mm-dd'),'yyyy-mm-dd') <= to_date('"+dto.getSpareStr5()+"', 'yyyy-mm-dd') ";
        	 }
        	 if (dto.getSpareStr2() !=null){
        		sql =sql + " and ad.districtid in "
                         + " (select id from t_districtsetting ds connect by prior ds.id=ds.belongto "
                         + "  start with ds.id="+dto.getSpareStr2()+") ";
        	 }
        	 if (dto.getSpareStr5() !=null){
        		sql =sql + "  and cu.customerid in "
                         + "  (select xjc.customerid  from ghams_jk.gehua_xyjk3 xjc "
                         + "   where to_char(xjc.dt_run,'yyyymm') <= to_char(to_date('"+dto.getSpareStr5()+"', 'yyyy-mm-dd'),'yyyymm') and xjc.status='E') ";
        	 }
        	 if (dto.getSpareStr6() !=null){
        		 sql =sql + " and pd.opid ="+dto.getSpareStr6()+ " ";
        	 }       	 
        } else if ("B".equals(dto.getSpareStr3())){
        	sql ="select " 
        		+" cu.customerid,cu.name, to_char(pd.createtime,'yyyy-mm-dd') createtime,pd.amount,mp.name mop_name,op.operatorname,pd.ticketno "
                +" From t_customer  cu,t_custmarketinfo  cmk, t_address ad,t_paymentrecord pd,t_operator op,t_methodofpayment mp "
                +" where cmk.infosettingid = 105 "
                +" and cmk.infovalueid = 603 "
                +" and cu.customerid = cmk.customerid "
                +" and cu.customerid = pd.custid "
                +" and pd.custid = cu.customerid "
                +" and mp.mopid = pd.mopid "
                +" and pd.sourcetype = 'O' "
                +" and pd.invoicedflag = 'Y' "
                +" and pd.opid = op.operatorid "
                +" and cu.addressid=ad.addressid ";
        	if (dto.getSpareStr4() !=null){
        		sql =sql + " and pd.createtime >= to_date('"+dto.getSpareStr4()+"', 'yyyy-mm-dd') ";
        	}
        	if (dto.getSpareStr5() !=null){
        		sql =sql + " and to_char(pd.createtime,'yyyymm') <= to_char(to_date('"+dto.getSpareStr5()+"', 'yyyy-mm-dd'),'yyyymm') ";
        	}
        	if (dto.getSpareStr2() !=null){
        		sql =sql + " and ad.districtid in "
                         + " (select id from t_districtsetting ds connect by prior ds.id=ds.belongto "
                         + "  start with ds.id="+dto.getSpareStr2()+") ";
        	}    
        	if (dto.getSpareStr5() !=null){
        		sql =sql + "  and cu.customerid in "
                         + "  (select xjc.customerid  from ghams_jk.gehua_xyjk3 xjc "
                         + "   where to_char(xjc.dt_run,'yyyymm') <= to_char(to_date('"+dto.getSpareStr5()+"', 'yyyy-mm-dd'),'yyyymm') and xjc.status='E') ";
        	} 
        	if (dto.getSpareStr6() !=null){
       		    sql =sql + " and pd.opid ="+dto.getSpareStr6()+ " ";
       	    }
        }else{
        	sql = "select " 
        	    +" cu.customerid,cu.name, to_char(pd.createtime,'yyyy-mm-dd') createtime,pd.amount,mp.name mop_name,op.operatorname,pd.ticketno "
                +" From t_customer  cu,t_custmarketinfo  cmk, t_address ad,t_paymentrecord pd,t_operator op,t_methodofpayment mp "
                +" where cmk.infosettingid = 105 "
                +" and cmk.infovalueid = 603 "
                +" and cu.status <> 'C' "
                +" and cu.customerid = cmk.customerid "
                +" and cu.customerid = pd.custid "
                +" and pd.custid = cu.customerid "
                +" and mp.mopid = pd.mopid "
                +" and pd.sourcetype in  ('M','T') "
                +" and pd.paytype in ('P','N') "
                +" and pd.opid = op.operatorid "
                +" and cu.addressid=ad.addressid";
        	 if (dto.getSpareStr4() !=null){
        		 sql =sql + " and pd.createtime >= to_date('"+dto.getSpareStr4()+"', 'yyyy-mm-dd') ";
        	 }
        	 if (dto.getSpareStr5() !=null){
        		 sql =sql + " and to_date(to_char(pd.createtime,'yyyy-mm-dd'),'yyyy-mm-dd') <= to_date('"+dto.getSpareStr5()+"', 'yyyy-mm-dd') ";
        	 }
        	 if (dto.getSpareStr2() !=null){
        		sql =sql + " and ad.districtid in "
                         + " (select id from t_districtsetting ds connect by prior ds.id=ds.belongto "
                         + "  start with ds.id="+dto.getSpareStr2()+") ";
        	 }
        	 if (dto.getSpareStr5() !=null){
        		sql =sql + "  and cu.customerid in "
                         + "  (select xjc.customerid  from ghams_jk.gehua_xyjk3 xjc "
                         + "   where to_char(xjc.dt_run,'yyyymm') <= to_char(to_date('"+dto.getSpareStr5()+"', 'yyyy-mm-dd'),'yyyymm') and xjc.status='E') ";
        	 }
        	 if (dto.getSpareStr6() !=null){
        		 sql =sql + " and pd.opid ="+dto.getSpareStr6()+ " ";
        	 }        	 
             sql =sql  +" union all ";
             sql = sql +" select " 
        	 	 +" cu.customerid,cu.name, to_char(pd.createtime,'yyyy-mm-dd') createtime,pd.amount,mp.name mop_name,op.operatorname,pd.ticketno "
                 +" From t_customer  cu,t_custmarketinfo  cmk, t_address ad,t_paymentrecord pd,t_operator op,t_methodofpayment mp "
                 +" where cmk.infosettingid = 105 "
                 +" and cmk.infovalueid = 603 "
                 +" and cu.customerid = cmk.customerid "
                 +" and cu.customerid = pd.custid "
                 +" and pd.custid = cu.customerid "
                 +" and mp.mopid = pd.mopid "
                 +" and pd.sourcetype = 'O' "
                 +" and pd.invoicedflag = 'Y' "
                 +" and pd.opid = op.operatorid "
                 +" and cu.addressid=ad.addressid ";
        	 if (dto.getSpareStr4() !=null){
        		sql =sql + " and pd.createtime >= to_date('"+dto.getSpareStr4()+"', 'yyyy-mm-dd') ";
        	 }
        	 if (dto.getSpareStr5() !=null){
        		sql =sql + " and to_char(pd.createtime,'yyyymm') <= to_char(to_date('"+dto.getSpareStr5()+"', 'yyyy-mm-dd'),'yyyymm') ";
        	 }
        	 if (dto.getSpareStr2() !=null){
        		sql =sql + " and ad.districtid in "
                         + " (select id from t_districtsetting ds connect by prior ds.id=ds.belongto "
                         + "  start with ds.id="+dto.getSpareStr2()+") ";
        	 }    
        	 if (dto.getSpareStr5() !=null){
        		sql =sql + "  and cu.customerid in "
                         + "  (select xjc.customerid  from ghams_jk.gehua_xyjk3 xjc "
                         + "   where to_char(xjc.dt_run,'yyyymm') <= to_char(to_date('"+dto.getSpareStr5()+"', 'yyyy-mm-dd'),'yyyymm') and xjc.status='E') ";
        	 } 
        	 if (dto.getSpareStr6() !=null){
       		    sql =sql + " and pd.opid ="+dto.getSpareStr6()+ " ";
       	     }        	 	
        }
        
        if ("A".equals(dto.getSpareStr3()) || "B".equals(dto.getSpareStr3())){
        	begin.append(sql);
    		begin.append(" order by cu.customerid ");
            //  设置当前数据查询sql
    		setRecordDataQueryBuffer(begin);
        }else{
        	begin.append(sql);
            //  设置当前数据查询sql
     		begin.append(" order by customerid ");
     		setRecordDataQueryBuffer(begin);
        }
	}
}
