package com.dtv.oss.service.listhandler.csr;

import java.util.ArrayList;
import java.util.List;

import com.dtv.oss.dto.custom.CommonQueryConditionDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.dao.csr.GenericImpDAO;
import com.dtv.oss.service.listhandler.ListHandlerException;
import com.dtv.oss.service.listhandler.ValueListHandler;
import com.dtv.oss.service.util.CommonConstDefinition;

public class OweSuspendListHandler extends ValueListHandler {
	private CommonQueryConditionDTO dto = null;
	private GenericImpDAO dao = null;
	private boolean outFlag =false;
	private static final Class clazz = OweSuspendListHandler.class;
    private List rowCountList =new ArrayList();
	public OweSuspendListHandler(){
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
		//�����ѯ�ַ���
		constructSelectQueryString(dto);
		
		if (dto.getSpareStr17().equals("out")){
		   dao.setSpeedFlag(false);
		}else{
		   dao.setFrom(getFrom());
		   dao.setTo(getTo());
		   dao.setSpeedFlag(true);
		   System.out.println("getFrom()----------------->"+getFrom());
		   System.out.println("getTo()---------------->"+getTo());
		}
		//ִ�����ݲ�ѯ
		executeSearch(dao);

		setTotalRecordSize(((Integer)(rowCountList.get(0))).intValue());
	}
	private void constructSelectQueryString(CommonQueryConditionDTO dto2) throws ListHandlerException {
		StringBuffer begin = new StringBuffer();
		boolean ServiceAcctFlag =true;
		//Ƿ�Ѳ�ѯʱ,ҵ���ʻ�id ��ͣ��ԭ����"-"
		if (CommonConstDefinition.SERVICEACCOUNT_STATUS_NORMAL.equals(dto2.getSpareStr14()) &&
		    (dto2.getSpareStr15() !=null || dto2.getSpareStr16() !=null)){
			ServiceAcctFlag =false;
		}
		
		begin.append(" select cust.customerid ,cust.name,");
		if (ServiceAcctFlag) {
			begin.append("servAcct.Serviceaccountid,");
		}else{
			begin.append(" '-' Serviceaccountid ,");
		}
		//�ͻ�����
		begin.append("(select t8.value from t_commonsettingdata t8 where t8.name ='SET_C_CUSTOMERTYPE' and t8.key =cust.customertype) custType,");
		//�ʻ���ַ
		begin.append("(select t7.name from t_districtsetting t7 where t7.id =(select belongto from t_districtsetting where id =subview.id))" 
				    +" || '��' || (select t3.name from t_districtsetting t3 where t3.id =subview.id)"
				    +" || '��' || addr.detailaddress ��ַ, ");
		if (ServiceAcctFlag){
			begin.append(" nvl(servAcct.Description,'-') Description ,");
		}else{
			begin.append(" '-' Description ,");
		}
		//�ͻ��绰
		begin.append("nvl(case when cust.telephone is not null and cust.telephonemobile is not null"
                    +" then cust.telephone || '/' || cust.telephonemobile "
                    +" else cust.telephone ||  cust.telephonemobile end,' ') tel,");
		//�ʻ����
		begin.append("b.balance ,");	

		//�ͻ���ע
		begin.append("cust.comments ");
		
		//	������SpareStr11
		begin.append(" from (select dir.id from t_districtsetting dir connect by prior dir.id =dir.belongto start with dir.id ="+dto2.getSpareStr11()+ ") subview, ");
		// �˻����
		begin.append(" (Select  * From (Select   tc.customerid, (select nvl(sum(pr.amount),0) amountSum from T_PaymentRecord pr where pr.custid=tc.customerid  and pr.status = 'V') "  
                    +"  -  "
                    +"  (select nvl(sum(ai.value),0) valueSum from T_AccountItem ai where ai.feetype<>'P' and ai.custid =tc.customerid and ai.status in ('V','1','3'))  As balance "
                    +" From t_customer tc "
                    +" )   a ");
		if (dto2.getSpareStr15() !=null && dto2.getSpareStr16() !=null){
			begin.append("  Where a.balance Between "+dto2.getSpareStr15() +" And  "+ dto2.getSpareStr16()+") b, ");
		}else if (dto2.getSpareStr15() !=null){
			begin.append("  Where a.balance >="+dto2.getSpareStr15()+") b, ");
		}else if (dto2.getSpareStr16() !=null){
			begin.append("  Where a.balance <="+dto2.getSpareStr16()+") b, ");
		}else {
			begin.append("  ) b,");
		}
		
		//���ӵı�
		if (ServiceAcctFlag) {
		    begin.append(" t_Serviceaccount servAcct,t_address addr,t_customer cust ");
		} else{
			begin.append(" t_address addr,t_customer cust ");
		}
		
		begin.append(" where  cust.addressid =addr.addressid ");
		begin.append(" and addr.districtid =subview.id  ");
		begin.append(" and cust.status !='C' ");
		begin.append(" and cust.customerid =b.customerid ");
		
        // �ͻ�����
		if (dto2.getSpareStr1() !=null)
           begin.append(" and cust.name like '%"+dto2.getSpareStr1()+ "%' ");	
		// �ͻ�����
		if (dto2.getSpareStr10() !=null) 
           begin.append(" and cust.customertype ='"+ dto2.getSpareStr10()+"'  ");
		// ��ַ
		if (dto2.getSpareStr12() !=null)
		   begin.append(" and addr.detailaddress like '"+ dto2.getSpareStr12()+"%' " );
		//��ͣ����
		if (dto2.getSpareTime1() !=null || dto2.getSpareTime2() !=null){
		   if (dto2.getSpareTime1() !=null){
			  begin.append(" and exists ( select * from t_customerproduct t9 where t9.customerid =cust.customerid and t9.status ='H' "
					      +" and t9.pausetime >=to_timestamp('"+dto2.getSpareTime1().toString()+"','YYYY-MM-DD HH24:MI:SSxff') ");
			  if (dto2.getSpareTime2() !=null){
				  begin.append(" and t9.pausetime <=to_timestamp('"+dto2.getSpareTime2().toString()+"','YYYY-MM-DD HH24:MI:SSxff') ");
			  }
			  begin.append(" ) ");
		   } else{
			  begin.append(" and exists ( select * from t_customerproduct t9 where t9.customerid =cust.customerid and t9.status ='H' "
					      +" and t9.pausetime <=to_timestamp('"+dto2.getSpareTime2().toString()+"','YYYY-MM-DD HH24:MI:SSxff') ) "); 
		   }
		}
		//ͣ��ԭ��
		if (dto2.getSpareStr13() !=null){
			begin.append(" and exists (select * from t_serviceaccount t7 where t7.customerid =cust.customerid and t7.description ='"+dto2.getSpareStr13()+"')");
		}
		//ҵ���ʻ�
		if (dto2.getSpareStr14() !=null){
			begin.append(" and exists (select * from  t_serviceaccount t6 where t6.customerid =cust.customerid and t6.status ='"+dto2.getSpareStr14()+"')");
		}
				
		//��Ʒ����
		if (dto2.getSpareStr18() !=null)
		   begin.append(" and exists( select * from t_customerproduct t9 where t9.customerid =cust.customerid and t9.productId ="+dto2.getSpareStr18()+")");
		
		//��������
		if (dto2.getSpareStr20() !=null){
			begin.append(" and exists( select * from t_account act where act.customerid =cust.customerid and act.mopid="+dto2.getSpareStr20()+" ) ");
		}
		
		if (ServiceAcctFlag) {
           begin.append(" and cust.customerid =servAcct.Customerid and servAcct.status !='C'");
		} 		
		
		//����ʽ
		if ("C".equals(dto2.getSpareStr19())){
		   begin.append(" order by name,customerid");
		}else{
		   begin.append(" order by ��ַ,customerid");
		}

		// ���õ�ǰ���ݲ�ѯsql
		setRecordDataQueryBuffer(begin);
		
	}
	
}
