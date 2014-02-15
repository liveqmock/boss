/*
 * Created on 2006-2-14
 *
 * @author Stone Liang
 * this QueryCommand is for statistics query only
 */
package com.dtv.oss.service.command.statistics;

import com.dtv.oss.service.command.CommandException;
import com.dtv.oss.service.command.QueryCommand;
import com.dtv.oss.service.listhandler.ValueListHandler;
import com.dtv.oss.service.listhandler.stat.*;
import com.dtv.oss.service.ejbevent.QueryEJBEvent;
import com.dtv.oss.service.ejbevent.statistics.StatQueryEJBEvent;
import com.dtv.oss.service.listhandler.stat.DeviceDaySalesStatListHandler;

import com.dtv.oss.service.commandresponse.ErrorCode;

public class StatQueryCommand extends QueryCommand {
	
	public ValueListHandler getActualListHandler(QueryEJBEvent event) {
		
		ValueListHandler handler = null;
		switch(event.getType()) {
			//����ͳ��
			case StatQueryEJBEvent.QUERY_TYPE_STAT_FEE:
				handler = new FeeStatListHandler();
				break;
			//֧��ͳ��/Ԥ��ͳ��
			case StatQueryEJBEvent.QUERY_TYPE_STAT_PAY:
				handler=new PayStatListHandler();
				break;
			//Ԥ��ֿ�ͳ��
			case StatQueryEJBEvent.QUERY_TYPE_STAT_PREPAYDEDUCTION:
				handler=new PrepayDeductionStatListHandler();
				break;
			//����ͳ��
			case StatQueryEJBEvent.QUERY_TYPE_STAT_ADJUSTACCOUNT:
				handler=new AdjustAccountStatListHandler();
				break;
			//�ʵ�ͳ��
			case StatQueryEJBEvent.QUERY_TYPE_STAT_BILL:
				handler=new BillStatListHandler();
				break;
			//����ͳ��
			case StatQueryEJBEvent.QUERY_TYPE_STAT_SETOFF:
				handler=new SetOffListHandler();
				break;
			//�������ͳ��
			case StatQueryEJBEvent.QUERY_TYPE_STAT_OPEN_CUSTOMER:
				handler=new OpenCustomerListHandler();
				break;
			//��������ͳ��
			case StatQueryEJBEvent.QUERY_TYPE_STAT_OTHER_CSI:
				handler=new OtherCSIStatListHandler();
				break;
            //��װʧ��ͳ��
			case StatQueryEJBEvent.QUERY_TYPE_STAT_FAILED_INSTALLATION:
				handler=new FailedInstallationStatListHandler();
				break;
	        //ԤԼͳ��
			case StatQueryEJBEvent.QUERY_TYPE_STAT_BOOKING:
				handler=new BookingStatListHandler();
				break;
		    //ԤԼȡ��ͳ��
			case StatQueryEJBEvent.QUERY_TYPE_STAT_CANCELED_BOOKING:
				handler=new CancelledBookingStatListHandler();
				break;
            //��������ͳ��
			case StatQueryEJBEvent.QUERY_TYPE_STAT_APPLY_FOR_RAIR:
				handler=new ApplyForRepairStatListHandler();
				break;
			//Ӫҵ�ֽ���ͳ�ƣ�Ӫҵ�շ�ͳ�ƣ�
			case StatQueryEJBEvent.QUERY_TYPE_STAT_BUSINESS_CASH:
				handler=new BusinessCashStatListHandler();
				break;
			//Ӫҵ���շ�ͳ��
			case StatQueryEJBEvent.QUERY_TYPE_STAT_BUSINESS_POINT_CASH:
				handler=new BusinessPointCashStatListHandler();
				break;
             //�ͻ���ͳ��
			case StatQueryEJBEvent.QUERY_TYPE_STAT_CUSTOMER:
				handler=new CustomerStatListHandler();
				break;
	        //�ͻ���Ʒ��ͳ��
			case StatQueryEJBEvent.QUERY_TYPE_STAT_CUSTOMER_PRODUCT:
				handler=new CustomerProductStatListHandler();
				break;
			//�ͻ���Ʒ��ͳ��
			case StatQueryEJBEvent.QUERY_TYPE_STAT_CUSTOMER_PRODUCT_PACKAGE:
				handler=new CustomerProductPackageStatListHandler();
				break;
			//�ǳɹ�ƥ���ʻ���ͳ��
			case StatQueryEJBEvent.QUERY_TYPE_STAT_FAILED_BANK_ACCT_CHECK:
				handler=new FailedBankAcctCheckstatListHandler();
				break;
			//��Ʒ���������ͳ��
			case StatQueryEJBEvent.QUERY_TYPE_STAT_SALED_PRODUCT_PACKAGE:
				handler=new SaledProductPackageStatListHandler();
				break;
			//�ͻ��ײ�ͳ��
			case StatQueryEJBEvent.QUERY_TYPE_STAT_CUSTOMERMEAL:
				handler=new CustomerCampaignStatListHandler(StatQueryEJBEvent.QUERY_TYPE_STAT_CUSTOMERMEAL);
				break;
			//������ͳ��
			case StatQueryEJBEvent.QUERY_TYPE_STAT_CUSTOMERSA:
				handler=new CustomerSAStatListHandler();
				break;
			//�ͻ������ͳ��
			case StatQueryEJBEvent.QUERY_TYPE_STAT_CUSTOMERCAMPAIGN:
				handler=new CustomerCampaignStatListHandler(StatQueryEJBEvent.QUERY_TYPE_STAT_CUSTOMERCAMPAIGN);
				break;
				
			//�ͻ��ʻ���ͳ��    add by jason 2007-1-12
			case StatQueryEJBEvent.QUERY_TYPE_STAT_ACCOUNT_INFO:
				handler=new AccountInfoStatListHandler();
				break;
			//�ʻ�Ԥ��ͳ��      add by jason 2007-1-12
			case StatQueryEJBEvent.QUERY_TYPE_STAT_ACCOUNT_DEPOSIT:
				handler=new AccountDepositStatListHandler();
				break;
			//�ʻ�Ƿ��ͳ��      add by jason 2007-1-12
			case StatQueryEJBEvent.QUERY_TYPE_STAT_ACCOUNT_OWE:
				handler=new AccountOweStatListHandler();
				break;
			//�����ʻ�����ͳ��  add by jason 2007-1-12
			case StatQueryEJBEvent.QUERY_TYPE_STAT_ACCOUNT_BANK_MATCH:
				handler=new AcctBankMatchStatListHandler();
				break;
			//Ѻ�����Ȩͳ��   add by jason 2007-1-16
			case StatQueryEJBEvent.QUERY_TYPE_STAT_FORCEDEPOSIT_FUTURERIGHT:
				handler=new ForceRightFutureRightStatListHandler();
				break;
			//�豸ͳ��   add by jason 2007-1-16
			case StatQueryEJBEvent.QUERY_TYPE_STAT_DEVICE_INFO:
				handler=new DeviceInfoStatListHandler();
				break;
			//�豸���ͳ��   add by jason 2007-1-16
			case StatQueryEJBEvent.QUERY_TYPE_STAT_DEVICE_STORE:
				handler=new DeviceStoreStatListHandler();
				break;
			//�豸��תͳ��   add by jason 2007-1-16
			case StatQueryEJBEvent.QUERY_TYPE_STAT_DEVICE_TRAN:
				handler=new DeviceTranStatListHandler();
				break;
			//��Ʒ����ͳ��  
			case StatQueryEJBEvent.QUERY_TYPE_STAT_PRODUCTSELL:
				handler=new ProductSellStatListHandler();
				break;
			//��Ʒ������ͳ��
			case StatQueryEJBEvent.QUERY_TYPE_STAT_PRODUCTPACKAGESELL:
				handler=new ProductpackageSellStatListHandler();
				break;
			//�ײ�ͳ��   
			case StatQueryEJBEvent.QUERY_TYPE_STAT_MEAL:
				handler=new MealStatListHandler();
				break;
			//���������ͳ��
			case StatQueryEJBEvent.QUERY_TYPE_STAT_CAMPAIGNCATCH:
				handler=new CampaignCatchStatListHandler();
				break;
			//����ͳ��-Ѻ�����Ȩͳ��
			case StatQueryEJBEvent.QUERY_TYPE_STAT_FORCEDEPOSIT_FUTURERIGHT_FOR_FINACE:
				handler=new ForceRightFutureRightForFinaceStatListHandler();
				break;
			//ӪҵӦ�շ�ͳ��
			case StatQueryEJBEvent.QUERY_TYPE_STAT_BUSINESS_ACCT_ITEM:
				handler=new BusinessAccountItemStatListHandler();
				break;
			//ӪҵӦ�շ�ͳ��
			case StatQueryEJBEvent.QUERY_TYPE_STAT_CALL_BACK:
				handler=new CallBackStatListHandler();
				break;
			//ԤԼ������ͳ��
			case StatQueryEJBEvent.QUERY_TYPE_BOOK_INTERACTION_SUM_STAT:
				handler=new BookInteractionSumStatListHandler();
				break;
			//������ͳ��
			case StatQueryEJBEvent.QUERY_TYPE_CUSTOMER_PROBLEM_SUM_STAT:
				handler=new CustomerProblemSumStatListHandler();
				break;
			//�豸�û�ͳ��
			case StatQueryEJBEvent.QUERY_TYPE_STAT_DEVICE_SWAP:
				handler=new DeviceSwapStatListHandler();
				break;
			//�豸����ͳ��
			case StatQueryEJBEvent.QUERY_TYPE_STAT_DEVICE_SALES:
				handler=new DeviceSalesStatListHandler();
				break;
			//����������ͳ���ձ���
			case StatQueryEJBEvent.QUERY_TYPE_STAT_DEVICE_DAY_SALES:
				handler=new DeviceDaySalesStatListHandler();
				break;
			default:
		}
		return handler;
	}

	private void checkFrom2To(int from, int to) throws CommandException {
	  if ( (from < 0) || (to < 0) ||
		  (to - from > 500) || (to - from < 0)) {
		throw new CommandException(ErrorCode.APP_ILL_PARAMETER);
	  }
	}
}
